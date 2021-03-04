package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;

/**
 * EntryPoint of the program
 */
public class EntryPoint {

    public static void main(String[] args) {
        Game.getInstance().display();
    }
}
