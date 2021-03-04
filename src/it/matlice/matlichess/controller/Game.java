package it.matlice.matlichess.controller;

import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.exceptions.InvalidTurnException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Color;
import it.matlice.matlichess.model.Piece;
import it.matlice.matlichess.view.PieceType;
import it.matlice.matlichess.view.View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private Chessboard chessboard;
    private View view;

    private Map<Color, Map<String, PieceType>> pieceConversionMap = getPieceConversionMap();

    private static Game instance = null;

    public static Game getInstance(){
        if(instance == null) instance = new Game();
        return instance;
    }

    private Game(){
        chessboard = Chessboard.getDefault();
        view = new View();
        view.setPosition(convertChessboardToView(chessboard));
    }

    public void display(){
        view.initialize();
        while(true) {
            var move = view.waitForUserMove(Color.WHITE);
            try {
                chessboard.move(move.get(0), move.get(1));
                System.out.println(move);
                view.setPosition(convertChessboardToView(chessboard));
            } catch (InvalidMoveException e) {
                System.out.println("WTF");
            } catch (InvalidTurnException e) {
                System.out.println("Wrong turn man");
            }

        }
    }

    private Map<Color, Map<String, PieceType>> getPieceConversionMap() {

        Map<String, PieceType> whiteConversionMap = new HashMap<>();
        whiteConversionMap.put("Pawn", PieceType.PAWN_WHITE);
        whiteConversionMap.put("Bishop", PieceType.BISHOP_WHITE);
        whiteConversionMap.put("Knight", PieceType.KNIGHT_WHITE);
        whiteConversionMap.put("Rook", PieceType.ROOK_WHITE);
        whiteConversionMap.put("Queen", PieceType.QUEEN_WHITE);
        whiteConversionMap.put("King", PieceType.KING_WHITE);

        Map<String, PieceType> blackConversionMap = new HashMap<>();
        blackConversionMap.put("Pawn", PieceType.PAWN_BLACK);
        blackConversionMap.put("Bishop", PieceType.BISHOP_BLACK);
        blackConversionMap.put("Knight", PieceType.KNIGHT_BLACK);
        blackConversionMap.put("Rook", PieceType.ROOK_BLACK);
        blackConversionMap.put("Queen", PieceType.QUEEN_BLACK);
        blackConversionMap.put("King", PieceType.KING_BLACK);

        Map<Color, Map<String, PieceType>> conversionMap = new HashMap<>();
        conversionMap.put(Color.WHITE, whiteConversionMap);
        conversionMap.put(Color.BLACK, blackConversionMap);

        return conversionMap;
    }

    private PieceType[][] convertChessboardToView(Chessboard c) {
        PieceType[][] pieces = new PieceType[8][8];
        Piece[][] chessboardMatrix = c.getChessboardMatrix();

        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if (chessboardMatrix[i][j] != null)
                    pieces[i][j] = getPieceType(chessboardMatrix[i][j]);
            }
        }

        return pieces;
    }

    private PieceType getPieceType(Piece piece) {
        return this.pieceConversionMap.get(piece.getColor()).get(piece.getName());
    }

}
