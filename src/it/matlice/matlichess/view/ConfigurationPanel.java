package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;

public abstract class ConfigurationPanel extends JPanel {
    public ConfigurationPanel(){
        this.buildPanel();
    }
    public abstract PlayerInterface getInstance();
    public abstract void buildPanel();
}
