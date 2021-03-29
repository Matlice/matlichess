package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Used at the beginning of the execution, it allows to choose which type of player has to join the game
 * The selection is made by combo boxes and the user can insert additional options
 */
public class PlayerPanel extends JPanel implements ItemListener {
    private final JPanel cards;

    /**
     * Constructor of Player panel. By using a CardLayout displays only one of the possible configuration of a player
     * @param players The classes of different players
     */
    public PlayerPanel(Class<? extends PlayerInterface>[] players) {
        String[] comboBoxItems = Arrays.stream(players).map(e -> {
            try {
                return (String) e.getMethod("getName").invoke(null);
            } catch (Exception ex) {
                return null;
            }
        }).filter(Objects::nonNull).toArray(String[]::new);

        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        this.add(cb);

        cards = new JPanel(new CardLayout());
        Arrays.stream(players).forEach(e -> {
            try {
                JPanel config = (JPanel) e.getMethod("getConfigurationInterface").invoke(null);
                cards.add(config, e.getMethod("getName").invoke(null));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                System.err.println("Cannot load player: " + ex.getMessage());
            }
        });
        this.add(cards);
    }

    /**
     * Changes the showed card in the CardLayout
     * @param e ItemEvent
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) e.getItem());
    }

    /**
     * returns the selected interface in the combo box
     * @return the selected interface
     */
    public PlayerInterface getSelectedInterface() {
        return ((ConfigurationPanel) Arrays.stream(cards.getComponents()).filter(Component::isVisible).findFirst().orElseThrow()).getInstance();
    }
}
