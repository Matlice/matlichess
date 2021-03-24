package it.matlice.matlichess.controller;

import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.view.ConfigurablePlayer;
import it.matlice.matlichess.view.View;

import javax.swing.*;

public class PhisicPlayer implements ConfigurablePlayer {

    public static JPanel getConfigurationInterface(){
        return new JPanel();
    }

    public static PlayerInterface getInstance(JPanel configured) throws InstantiationError{
        return View.getInstance().getPlayerInterface();
    }

    public static String getName() {return "Person";}
}
