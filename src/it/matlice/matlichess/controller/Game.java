package it.matlice.matlichess.controller;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.view.View;

import java.awt.*;

public class Game {

    private Chessboard chessboard;
    private View view;
    private static Game instance = null;
    public static Game getInstance(){
        if(instance == null) instance = new Game();
        return instance;
    }

    private Game(){
        chessboard = Chessboard.getDefault();
        view = new View();
    }

    public void display(){
        view.initialize();
        while(true)
            System.out.println(view.waitForUserMove(Color.WHITE));
    }



}
