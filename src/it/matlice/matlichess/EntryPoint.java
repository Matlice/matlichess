package it.matlice.matlichess;

import it.matlice.matlichess.controller.*;
import it.matlice.matlichess.view.PlayerPanel;
import it.matlice.matlichess.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * EntryPoint of the program
 */
public class EntryPoint extends JFrame implements ActionListener {
    private PlayerPanel white, black;

    public EntryPoint(Class<? extends PlayerInterface>[] players) {
        white = new PlayerPanel(players);
        black = new PlayerPanel(players);
        var l = new JPanel(new BorderLayout());
        l.add(white, BorderLayout.PAGE_START);
        l.add(new Label("VS."), BorderLayout.CENTER);
        l.add(black, BorderLayout.PAGE_END);

        var b = new JButton("START");
        l.add(b, BorderLayout.LINE_END);

        b.addActionListener(this);

        this.getContentPane().add(l);
        this.setTitle("MatliChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void startApplication(){
        this.setVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        final Class<? extends PlayerInterface>[] players = new Class[]{PhysicalPlayer.class, StockfishPlayer.class, NetworkPlayer.class};
        new EntryPoint(players).startApplication();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var t = new Thread(() -> {
            this.setVisible(false);
            View.getInstance().initialize();
            Game.getInstance(white.getSelectedInterface(), black.getSelectedInterface(), View.getInstance().getPlayerInterface()).setup();
            while (Game.getInstance().mainloop());
            this.dispose();
            System.exit(0);
        });
        t.start();
    }
}