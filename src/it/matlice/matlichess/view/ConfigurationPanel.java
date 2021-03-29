package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;

/**
 * Represents an abstract JPanel for the initial configuration of the game.
 * Every different player has to explain what options to show
 */
public abstract class ConfigurationPanel extends JPanel {
    public ConfigurationPanel(){
        this.buildPanel();
    }
    public abstract PlayerInterface getInstance() throws Exception;
    public abstract void buildPanel();
}
