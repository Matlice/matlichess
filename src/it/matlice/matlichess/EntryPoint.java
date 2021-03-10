package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.View;

/**
 * EntryPoint of the program
 */
public class EntryPoint {

    public static void main(String[] args) {
        var v = new View();
        var white = v.initialize();
        var s = new StockfishPlayer(12);

        while(Game.getInstance(white, s).mainloop());
    }
}
