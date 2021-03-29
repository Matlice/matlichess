package it.matlice.matlichess;

import it.matlice.matlichess.controller.*;
import it.matlice.matlichess.view.PlayerPanel;
import it.matlice.matlichess.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * EntryPoint of the program, extends a JFrame for the initial menu
 */
public class EntryPoint extends JFrame implements ActionListener {
    private PlayerPanel white, black;

    /**
     * Constructor that initialises the frame and its panel,
     * which contains the main settings for the players
     *
     * @param players the possible classes that implements a PlayerInterface
     */
    public EntryPoint(Class<? extends PlayerInterface>[] players) {
        // option panel, which contains the settings for both the two players
        white = new PlayerPanel(players);
        black = new PlayerPanel(players);

        var contentPanel = new JPanel(new BorderLayout());

        // add players
        contentPanel.add(white, BorderLayout.PAGE_START);
        contentPanel.add(black, BorderLayout.PAGE_END);

        // add VS. label
        var vsLabel = new Label("VS.");
        vsLabel.setAlignment(Label.CENTER);
        contentPanel.add(vsLabel, BorderLayout.CENTER);

        // add the start button, to start the game with actual settings
        var b = new JButton("START");
        contentPanel.add(b, BorderLayout.LINE_END);
        b.addActionListener(this);

        // add another panel to add border
        JPanel containerPanel = new JPanel();
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(contentPanel,BorderLayout.CENTER);

        // set panel size
        containerPanel.setPreferredSize( new Dimension(650, 150) );

        // set the jframe options, title and exit behaviour
        this.getContentPane().add(containerPanel);
        this.setTitle("MatliChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    /**
     * Starts the application after having initialised the frame with the panel
     */
    public void startApplication(){
        this.setVisible(true);
    }

    /**
     * Main of the program, initialises the main frame and starts the program
     * @param args no args :(
     */
    public static void main(String[] args) {
        final Class<? extends PlayerInterface>[] players = new Class[]{PhysicalPlayer.class, StockfishPlayer.class, NetworkPlayer.class};
        new EntryPoint(players).startApplication();
    }

    /**
     * The callback for the start button, it starts the game
     *
     * @param e the event on the button
     */
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