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
public class EntryPointClient {


    public static void main(String[] args) {
        var v = new View().initialize();

        NetworkPlayer network = null;
        try {
            network = new NetworkPlayer(InetAddress.getByAddress(new byte[]{(byte) 127,(byte) 0,(byte) 0,(byte) 1}));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Game.getInstance(v, network).setup();
        while (Game.getInstance().mainloop());
    }

}