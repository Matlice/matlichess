package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.NetworkPlayer;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.View;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * EntryPoint of the program
 */
public class EntryPointStockfishVStockfish {


    public static void main(String[] args) {

        var v = new View().initialize();
        var white = new StockfishPlayer(8, 20);
        var black = new StockfishPlayer(1, 10);

        Game.getInstance(white, black, v).setup();
        while (Game.getInstance().mainloop());
    }

}
