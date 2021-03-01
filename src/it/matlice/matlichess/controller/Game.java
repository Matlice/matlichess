package it.matlice.matlichess.controller;

import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.view.View;

public class Game {

    private Chessboard chessboard;
    private View view;

    public Game(){
        chessboard = Chessboard.getDefault();
        view = new View();
    }

}
