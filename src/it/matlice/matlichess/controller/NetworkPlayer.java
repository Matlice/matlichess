package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.net.*;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.view.PieceView;
import it.matlice.settings.Settings;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class NetworkPlayer implements PlayerInterface {

    private ServerSocket server;
    private Socket socket = null;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private PieceColor mycolor = null;
    private Semaphore sem = new Semaphore(1);

    /**
     * Server constructor
     *
     * @throws IOException
     */
    public NetworkPlayer() {
        try {
            server = new ServerSocket(Settings.NETWORK_PORT);
        } catch (IOException e) {
            // todo remove?
            System.err.println("Connection lost");
        }
        (new Thread(this::handleServer)).start();
    }

    /**
     * Client constructor
     *
     * @param address the address of the server
     */
    public NetworkPlayer(InetAddress address) {
        try {
            sem.acquire();
            this.socket = new Socket(address, Settings.NETWORK_PORT);
            this.socketOut = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
            this.socketOut.flush();
            this.socketIn = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
            //this must be done in a separate thread because wants an istance that is being creating while calling this constructor
            EventQueue.invokeLater(() -> {
                //should receive a welcome
                while(!Game.hasInstance()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    var p = (ComPacket) socketIn.readObject();
                    if(!p.getPacketType().equals("POS_INIT"))
                        throw new ClassNotFoundException("Protocol error");
                    Game.getInstance().loadState((PositionInit) p);
                    sem.release();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException | InterruptedException e) {
            // todo remove?
            System.err.println("Connection lost or broken");
        }
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
                System.err.println("Connection lost");
            }
        }
    }

    private void handleConnection(Socket s) {
        try {
            var socketOut = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
            socketOut.flush();
            var socketIn = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
            if (this.socket != null && !this.socket.isConnected()) {
                // todo do bad things
                socketOut.writeObject(new ComError("User is already in a game."));
                socketOut.flush();
                s.close();
            } else {
                if (this.socketIn != null) this.socketIn.close();
                if (this.socketOut != null) this.socketOut.close();
                this.socket = s;
                this.socketIn = socketIn;
                this.socketOut = socketOut;
                while(this.mycolor == null) Thread.sleep(200);
                this.socketOut.writeObject(new PositionInit(this.mycolor, Game.getInstance().getTurn()));
                socketOut.flush();
            }
        } catch (IOException | InterruptedException e) {
            // todo remove?
            System.err.println("Connection lost");
        }
    }

    @Override
    public void setColor(PieceColor color) {
        this.mycolor = color;
    }

    @Override
    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
        while (socketIn == null) Thread.sleep(200); // no sockets has connected
        List<Location> move = null;
        do {
            sem.acquire();
            try {
                var p = (ComPacket) socketIn.readObject();
                if(!p.getPacketType().equals("MOVE"))
                    throw new ClassNotFoundException("Protocol error");
                if (((Move) p).getExtendedMove() != null) {
                    move = Location.fromExtendedMove(((Move) p).getExtendedMove());
                    if(!Game.getInstance().isMoveValid(move.get(0), move.get(1))) throw new InvalidMoveException();
                }
            } catch (IOException e) {
                // socket has been closed, repeat
                Thread.sleep(100);
            } catch (RuntimeException | ClassNotFoundException e) {
                Thread.sleep(100);
                System.err.println("received an invalid move");
                this.safeSend(new ComError("Invalid move"));
            }
            sem.release();
        } while (move == null);
        safeSend(new Nop());
        return move;
    }

    private void safeSend(Object o){
        try {
            this.socketOut.writeObject(o);
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Object safeRead(){
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
        if (Game.hasInstance() && this.socketOut != null) {
            safeSend(new Move(from.toString() + to.toString()));
            var p = (ComPacket) safeRead();
            if(p == null || !p.getPacketType().equals("NOP")) throw new InvalidMoveException();;
        }
    }

    @Override
    public void setTurn(PieceColor turn) {
        // turn is given by the fen
        return;
    }

}
