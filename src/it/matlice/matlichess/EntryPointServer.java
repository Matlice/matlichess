package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.NetworkPlayer;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.View;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * EntryPoint of the program
 */
public class EntryPointServer {

    public static void main(String[] args) {
        var v = new View().initialize();
        var white = new StockfishPlayer(16, 10);
        var black = new StockfishPlayer(16, 20);
        var network = new NetworkPlayer();

        Game.getInstance(v, network).setup();
        while (Game.getInstance().mainloop());
    }

}
