package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public class PlayerPanel extends JPanel implements ItemListener {
    private final JPanel cards;
    private String selected;

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
                var config = (JPanel) e.getMethod("getConfigurationInterface").invoke(null);
                cards.add(config, (String) e.getMethod("getName").invoke(null));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                System.err.println("Cannot load player: " + ex.getMessage());
            }
        });
        this.selected = comboBoxItems[0];
        this.add(cards);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, (String) e.getItem());
        this.selected = (String) e.getItem();
    }

    public PlayerInterface getSelectedInterface() {
        return ((ConfigurationPanel) Arrays.stream(cards.getComponents()).filter(Component::isVisible).findFirst().orElseThrow()).getInstance();
    }
}
