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
public class EntryPointStockfish {


    public static void main(String[] args) {
        var v = new View().initialize();
        var white = new StockfishPlayer(1, 20);
        var black = new StockfishPlayer(1, 20);

        Game.getInstance(white, black, v).setup();
        while (Game.getInstance().mainloop());
        System.out.println("Final FEN: ");
        System.out.println(Game.getInstance().getPositionFen(true));
    }

}
