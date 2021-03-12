package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.PlayerInterface;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.View;

import java.util.ArrayList;

/**
 * EntryPoint of the program
 */
public class EntryPoint {

    public static void main(String[] args) {
        var v = new View().initialize();
        var white = new StockfishPlayer(16, 20);
        var black = new StockfishPlayer(16, 0);

        Game.getInstance(white, black, v).setup();
        while(Game.getInstance().mainloop());
    }
}
