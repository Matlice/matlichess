package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerPanel extends JPanel implements ItemListener {
    private JPanel cards;
    private Map<String, Class<? extends ConfigurablePlayer>> players = new HashMap<>();
    private Map<String, JPanel> panels = new HashMap<>();
    private String selected;

    public PlayerPanel(Class<? extends ConfigurablePlayer>[] players){
        String[] comboBoxItems = Arrays.stream(players).map(e -> {
            try{
                this.players.put((String) e.getMethod("getName").invoke(null), e);
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
                this.panels.put((String) e.getMethod("getName").invoke(null), config);
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
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String) e.getItem());
        this.selected = (String) e.getItem();
    }

    public PlayerInterface getSelectedInterface(){
        try {
            return (PlayerInterface) this.players.get(this.selected).getMethod("getInstance", JPanel.class).invoke(null, this.panels.get(this.selected));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

}
