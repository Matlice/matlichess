package it.matlice.matlichess;

import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.NetworkPlayer;
import it.matlice.matlichess.controller.PhisicPlayer;
import it.matlice.matlichess.controller.StockfishPlayer;
import it.matlice.matlichess.view.ConfigurablePlayer;
import it.matlice.matlichess.view.PlayerPanel;
import it.matlice.matlichess.view.View;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * EntryPoint of the program
 */
public class EntryPoint {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<? extends ConfigurablePlayer>[] players = new Class[]{PhisicPlayer.class, StockfishPlayer.class, NetworkPlayer.class};

        var white = new PlayerPanel(players);
        var black = new PlayerPanel(players);

        var frame = new JFrame();
        var l = new JPanel(new BorderLayout());
        l.add(white, BorderLayout.PAGE_START);
        l.add(new Label("VS."), BorderLayout.CENTER);
        l.add(black, BorderLayout.PAGE_END);

        var b = new JButton("START");
        l.add(b, BorderLayout.LINE_END);

        b.addActionListener((e) -> {
            new Thread(() -> {
                View.getInstance().initialize();
                Game.getInstance(white.getSelectedInterface(), black.getSelectedInterface(), View.getInstance().getPlayerInterface()).setup();
                while(Game.getInstance().mainloop());
            }).start();
            frame.dispose();
        });

        frame.getContentPane().add(l);

        frame.setTitle("MatliChess");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }
}
