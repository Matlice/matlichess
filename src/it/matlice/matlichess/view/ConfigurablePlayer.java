package it.matlice.matlichess.view;

import it.matlice.matlichess.controller.PlayerInterface;

import javax.swing.*;

public interface ConfigurablePlayer {
    public static JPanel getConfigurationInterface(){throw new RuntimeException("Not defined");}
    public static PlayerInterface getInstance(JPanel configured) throws InstantiationError{throw new RuntimeException("Not defined");}
    public static String getName() {return "not defined -.-";}
}
