package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.NetworkPlayer;
import it.matlice.matlichess.view.View;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * EntryPoint of the program
 */
public class EntryPoint {


    public static void main(String[] args) {
        var v = new View().initialize();

        Game.getInstance(v, new View().initialize()).setup();
        while (Game.getInstance().mainloop());
    }

}
