package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.View;

/**
 * EntryPoint of the program
 */
public class EntryPointStockfish {


    public static void main(String[] args) {

        var v = new View().initialize();
        var stockfish = new StockfishPlayer(8, 20);

        Game.getInstance(v, stockfish).setup();
        while (Game.getInstance().mainloop());
    }

}
