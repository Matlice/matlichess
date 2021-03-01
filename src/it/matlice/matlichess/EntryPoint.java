package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;

import java.awt.*;

/**
 * EntryPoint of the program
 */
public class EntryPoint {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {

                Game game = new Game();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
