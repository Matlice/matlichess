package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.net.*;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.view.ConfigurationPanel;
import it.matlice.matlichess.view.PieceView;
import it.matlice.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * This class is responsible of client/server player management.
 * <p>
 * the communication is archived by a full duplex object stream setup across a tcp Socket.
 *
 * <h1>instantiation process</h1>
 * <h2>Server side</h2>
 * <p>
 * While instantiating, the server accepting thread is started and left waiting for connections.
 * for each connection, if no connection has been archived yet we send the current position sync packet to the
 * client which will load the state and ensure the syncronization is archeived.
 * <p>
 * if anoter connection is active, an error packet is sent and the connection is closed.
 *
 * <h2>Client side</h2>
 * while instantiating a connection and synchronization thread is started and a mutex lock for the socket activity is achieved.
 * more precisely we need to ensure full initialization is achieved before the first communication packet is obtained from the server.
 * <p>
 * <p>
 * when connection is ready and sync has happened, releasing the relative Semaphore, the communication can start with the implemented protocol.
 *
 * <h1>Protocol</h1>
 *
 * @see it.matlice.matlichess.controller.net classes.
 */
public class NetworkPlayer implements PlayerInterface {

    private ServerSocket server;
    private Socket socket = null;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private PieceColor mycolor = null;
    private Semaphore sem = new Semaphore(1);
    private Thread askingThread = null;
    private Thread semThread = null;
    private Move lastReceivedMove = null;

    /**
     * Server constructor.
     */
    public NetworkPlayer() throws IOException {
        boolean serverStarted = false;
        while (!serverStarted) {
            server = new ServerSocket(Settings.NETWORK_PORT);
            (new Thread(this::handleServer)).start();
            serverStarted = true;
        }
    }

    /**
     * Client constructor
     *
     * @param ip the server ip.
     */
    public NetworkPlayer(String ip) throws IOException, InterruptedException {
        this(getByNameSafe(ip));
    }

    public NetworkPlayer(String ip, int port) throws IOException, InterruptedException {
        this(getByNameSafe(ip), port);
    }

    public NetworkPlayer(InetAddress address) throws IOException, InterruptedException {
        this(address, Settings.NETWORK_PORT);
    }

    /**
     * Client constructor
     *
     * @param address the address of the server
     */
    public NetworkPlayer(InetAddress address, int port) throws IOException, InterruptedException {
        //todo if server fails, client becomes a server hoping for reconnection
        sem.acquire();
        this.socket = new Socket(address, port);
        this.socketOut = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        this.socketOut.flush();
        this.socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));

        //this must be done in a separate thread because wants an istance that is being creating while calling this constructor
        new Thread(() -> {
            //should receive a welcome
            while (!Game.hasInstance()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ComPacket p = null;
            try {
                p = (ComPacket) socketIn.readObject();
                if (!p.getPacketType().equals("POS_INIT"))
                    throw new ProtocolErrorException();
                Game.getInstance().loadState((PositionInit) p);
                sem.release();
            } catch (IOException | ClassNotFoundException e) {
                throw new ProtocolErrorException();
            }
        }).start();
    }

    private static InetAddress getByNameSafe(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return getByNameSafe("127.0.0.1");
        }
    }

    /**
     * @see it.matlice.matlichess.view.PlayerPanel
     */
    public static ConfigurationPanel getConfigurationInterface() {
        return new ConfigurationPanel() {
            private boolean isServer = true;
            private JTextField ip;
            private JTextField port;

            @Override
            public PlayerInterface getInstance() throws Exception {
                if (this.isServer) return new NetworkPlayer();
                return new NetworkPlayer(this.ip.getText(), Integer.parseInt(this.port.getText()));
            }

            @Override
            public void buildPanel() {
                JRadioButton server_radio = new JRadioButton("Server");
                JRadioButton client_radio = new JRadioButton("Client");
                ButtonGroup method = new ButtonGroup();
                method.add(server_radio);
                method.add(client_radio);
                ip = new JTextField();
                ip.setText("127.0.0.1");
                port = new JTextField();
                port.setText(String.valueOf(Settings.NETWORK_PORT));
                server_radio.addActionListener((e) -> {
                    ip.setEditable(false);
                    this.isServer = true;
                });
                client_radio.addActionListener((e) -> {
                    ip.setEditable(true);
                    this.isServer = false;
                });
                server_radio.setSelected(true);
                ip.setEditable(false);

                ip.setPreferredSize(new Dimension(100, 24));
                this.add(server_radio);
                this.add(client_radio);
                this.add(new Label("Server ip:"));
                this.add(ip);
                this.add(new Label("Port:"));
                this.add(port);
            }
        };
    }

    /**
     * @see it.matlice.matlichess.view.PlayerPanel
     */
    public static String getName() {
        return "Network";
    }

    /**
     * Handle the server connection
     *
     * @throws IOException
     */
    private void handleServer() {
        while (true) {
            try {
                Socket s = server.accept();
                handleConnection(s);
            } catch (IOException e) {
                System.err.println("Connection lost...");
            }
        }
    }

    /**
     * if no connection has been archived yet we send the current position sync packet to the
     * client which will load the state and ensure the syncronization is archeived.
     * <p>
     * if anoter connection is active, an error packet is sent and the connection is closed.
     *
     * @param s connection socket
     */
    private void handleConnection(Socket s) {
        try {
            ObjectOutputStream socketOut = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
            socketOut.flush();
            ObjectInputStream socketIn = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
            if (this.socket != null && !this.socket.isConnected()) {
                socketOut.writeObject(new ComError("User is already in a game."));
                socketOut.flush();
                s.close();
            } else {
                if (this.socketIn != null) this.socketIn.close();
                if (this.socketOut != null) this.socketOut.close();
                this.socket = s;
                this.socketIn = socketIn;
                this.socketOut = socketOut;
                while (this.mycolor == null) Thread.sleep(200);
                this.socketOut.writeObject(new PositionInit(this.mycolor));
                socketOut.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Connection lost...");
        }
    }

    @Override
    public void setColor(PieceColor color) {
        this.mycolor = color;
    }

    /**
     * Ask the other end for the next move if the sync Semaphore has been released or else waits for full synchronization
     * Socket read interruption is achieved by starting the blocking call in another thread and then waiting for joining.
     *
     * @return the move
     * @throws InterruptedException if the call has been interrupted from another thread.
     */
    @Override
    public List<Location> waitForUserMove() throws InterruptedException {
        while (socketIn == null) Thread.sleep(200); // no sockets has connected
        List<Location> move = null;
        do {
            this.semThread = Thread.currentThread();
            sem.acquire();
            try {
                ComPacket pk[] = {null};
                Exception e[] = {null};
                askingThread = new Thread(() -> {
                    try {
                        pk[0] = (ComPacket) socketIn.readObject();
                    } catch (IOException | ClassNotFoundException ex) {
                        e[0] = ex;
                    }
                });
                this.askingThread.start();
                this.askingThread.join();
                this.askingThread = null;

                if (e[0] != null) throw e[0];
                if (pk[0] == null) throw new InterruptedException();

                ComPacket p = pk[0];

                switch (p.getPacketType()) {
                    case "MOVE":
                        if (((Move) p).getExtendedMove() != null) {
                            // first set the correct promotion types...
                            String[] promTypes = ((Move) p).getPromotionTypes();
                            Game.getInstance().setPromotions(promTypes);
                            // ... and then do the move
                            move = Location.fromExtendedMove(((Move) p).getExtendedMove());
                            if (!Game.getInstance().isMoveValid(move.get(0), move.get(1)))
                                throw new InvalidMoveException();
                        }
                        break;
                    case "POS_INIT":
                        Game.getInstance().loadState((PositionInit) p);
                        break;
                    default:
                        throw new ProtocolErrorException();
                }
                if (!p.getPacketType().equals("MOVE"))
                    throw new ProtocolErrorException();

            } catch (IOException e) {
                // socket has been closed, repeat
                Thread.sleep(100);
            } catch (RuntimeException | ClassNotFoundException e) {
                Thread.sleep(100);
                System.err.println("Received an invalid move");
                this.safeSend(new ComError("Invalid move"));
            } catch (Exception e) {
                e.printStackTrace();
//                throw new RuntimeException(e);
            }
            sem.release();
            this.semThread = null;
        } while (move == null);
        safeSend(new Nop());
        this.lastReceivedMove = new Move(move.get(0).toString() + move.get(1).toString());
        return move;
    }

    private void safeSend(Object o) {
        try {
            this.socketOut.writeObject(o);
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object safeRead() {
        try {
            return this.socketIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        return;
    }

    @Override
    public void setMove(Location from, Location to) {
        if (from == null || to == null)
            return;
        if (Game.hasInstance() && this.socketOut != null && (lastReceivedMove == null || !lastReceivedMove.equals(new Move(from, to)))) {
            safeSend(new Move(from.toString() + to.toString()));
            ComPacket p = (ComPacket) safeRead();
            if (p == null || !p.getPacketType().equals("NOP")) throw new InvalidMoveException();
        }
    }

    @Override
    public void setTurn(PieceColor turn) {
        // turn is given by the fen
        return;
    }

    /**
     * this call allows other threads from who's calling waitForMove to interrupt the request and make the call throw InterruptedException
     */
    @Override
    public void interrupt() {
        if (this.askingThread != null) {
            try {
                this.askingThread.join(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.semThread != null)
            this.semThread.interrupt();

    }

    @Override
    public boolean setState(GameState state, boolean generic, PlayerInterface opponent) {
        return opponent.setState(state, generic, this);
    }

    @Override
    public boolean setState(GameState state, boolean generic, Boolean other_choice) {
        try {
            this.socketOut.writeObject(new RematchChoice(other_choice));
            this.socketOut.flush();
            ComPacket p = (ComPacket) this.socketIn.readObject();
            if (p instanceof RematchChoice)
                return other_choice && ((RematchChoice) p).rematch;
            else return false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}