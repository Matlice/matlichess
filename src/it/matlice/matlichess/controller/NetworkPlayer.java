package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.PieceView;
import it.matlice.settings.Settings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NetworkPlayer implements PlayerInterface {

    private ServerSocket server;
    private Socket socket = null;
    private Scanner socketIn;
    private PrintWriter socketOut;

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
            this.socket = new Socket(address, Settings.NETWORK_PORT);
            this.socketIn = new Scanner(new InputStreamReader(this.socket.getInputStream()));
            this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);

        } catch (IOException e) {
            // todo remove?
            System.err.println("Connection lost");
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
                // todo remove?
                System.err.println("Connection lost");
            }
        }
    }

    private void handleConnection(Socket s) {
        try {
            Scanner socketIn = new Scanner(new InputStreamReader(s.getInputStream()));
            PrintWriter socketOut = new PrintWriter(s.getOutputStream(), true);
            if (this.socket != null && !this.socket.isConnected()) {
                // todo do bad things
                socketOut.println("Lezzo");
                s.close();
            } else {
                if (this.socketIn != null) this.socketIn.close();
                if (this.socketOut != null) this.socketOut.close();
                this.socket = s;
                this.socketIn = socketIn;
                this.socketOut = socketOut;
                this.socketOut.println("Yay");
            }
        } catch (IOException e) {
            // todo remove?
            System.err.println("Connection lost");
        }
    }

    @Override
    public List<Location> waitForUserMove(PieceColor side) {
        List<Location> move = null;
        try {
            while (!isReady());
            String s = this.socketIn.nextLine();
                if (s != null){
                    System.out.println(s); // todo
                    move = Location.fromExtendedMove(s);
                }
        } catch (RuntimeException e) {
            // todo remove?
            System.err.println("Move not valid");
        }
        return move;
    }

    //todo docs
    private boolean isReady(){
        try {
            return this.socket != null && !this.socket.getInetAddress().isReachable(500);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        if (Game.hasInstance() && this.socketOut != null)
            this.socketOut.println(Game.getInstance().getPositionFen());
    }

    @Override
    public void setTurn(PieceColor turn) {
        // turn is given by the fen
        return;
    }

}
