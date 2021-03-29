package it.matlice.matlichess;

import it.matlice.matlichess.controller.*;
import it.matlice.matlichess.model.Piece;
import it.matlice.matlichess.view.PlayerPanel;
import it.matlice.matlichess.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.matlice.settings.Settings.LOOK_AND_FEEL;

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

        JPanel contentPanel = new JPanel(new BorderLayout());

        // add players
        contentPanel.add(white, BorderLayout.PAGE_START);
        contentPanel.add(black, BorderLayout.PAGE_END);

        // add VS. label
        Label vsLabel = new Label("VS.");
        vsLabel.setAlignment(Label.CENTER);
        contentPanel.add(vsLabel, BorderLayout.CENTER);

        // add the start button, to start the game with actual settings
        JButton b = new JButton("START");
        contentPanel.add(b, BorderLayout.LINE_END);
        b.addActionListener(this);

        // add another panel to add border
        JPanel containerPanel = new JPanel();
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(contentPanel,BorderLayout.CENTER);

        // set panel size
        containerPanel.setPreferredSize( new Dimension(770, 165) );

        // set the jframe options, title and exit behaviour
        this.getContentPane().add(containerPanel);
        this.setTitle("MatliChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(LOOK_AND_FEEL);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        this.pack();
    }

    /**
     * Main of the program, initialises the main frame and starts the program
     *
     * @param args no args :( ):
     */
    public static void main(String[] args) {
        Class<? extends PlayerInterface>[] players = new Class[]{PhysicalPlayer.class, StockfishPlayer.class, NetworkPlayer.class};
        new EntryPoint(players).startApplication();
    }

    /**
     * Starts the application after having initialised the frame with the panel
     */
    public void startApplication() {
        this.setVisible(true);
    }

    /**
     * The callback for the start button, it starts the game
     *
     * @param e the event on the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        EventQueue.invokeLater(() -> {
            try {
                PlayerInterface w = white.getSelectedInterface();
                PlayerInterface b = black.getSelectedInterface();
                runGame(w, b, () -> {
                    this.setVisible(true);
                });
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nell'inizializzazione: " + ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Given two players starts the game and keep it playing until mainloop() returns false
     * @param white white player
     * @param black black player
     * @param after Runnable to run after the game
     */
    private void runGame(PlayerInterface white, PlayerInterface black, Runnable after) {
        Thread t = new Thread(() -> {
            View.getInstance().initialize();
            Game.getInstance(white, black, View.getInstance().getPlayerInterface()).setup();
            while (Game.getInstance().mainloop()) ;
            if(after != null) after.run();
        });
        t.start();
    }
}