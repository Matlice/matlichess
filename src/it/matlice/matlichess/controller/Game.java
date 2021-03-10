package it.matlice.matlichess.controller;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.exceptions.InvalidTurnException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.model.Piece;
import it.matlice.matlichess.view.PieceType;
import it.matlice.matlichess.view.PieceView;

import java.util.*;

/**
 * Singleton entity which represents the game session,
 * This class is the controller in the MCV architecture:
 * it contains an instance of the model and an instance of the view and takes information from both of them
 */
public class Game {

    private static Game instance = null;
    PlayerInterface[] players;
    private Chessboard chessboard;
    private Map<PieceColor, Map<String, PieceType>> pieceConversionMap = getPieceConversionMap();
    private PieceColor turn = PieceColor.WHITE; //0 white, 1 black

    private Game(PlayerInterface[] players) {
        chessboard = Chessboard.getDefault();
        assert players.length == 2;
        this.players = players;
        Arrays.stream(this.players).forEach(e -> e.setPosition(convertChessboardToView(chessboard)));
    }

    /**
     * Singleton getter
     *
     * @return the instance of the current game
     */
    public static Game getInstance() {
        if (instance == null) throw new RuntimeException("Game has not been initialized yet");
        return instance;
    }

    public static Game getInstance(PlayerInterface white, PlayerInterface black) {
        if (instance == null) instance = new Game(new PlayerInterface[]{white, black});
        return instance;
    }

    public static boolean hasInstance() {
        return instance != null;
    }

    public boolean mainloop() {
        List<Location> move = null;
        try {
            move = players[turn.index].waitForUserMove(PieceColor.WHITE);
            chessboard.move(move.get(0), move.get(1));

            System.out.println(move);

            Arrays.stream(players).forEach(e -> {
                e.setPosition(convertChessboardToView(chessboard));
                e.setTurn(chessboard.getTurn());
            });
            turn = chessboard.getTurn();
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move " + move.get(0) + " " + move.get(1));
        } catch (InvalidTurnException e) {
            System.out.println("Wrong turn man " + move.get(0) + " " + move.get(1));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Utility method to convert a string representing a PieceType to the relative {@link PieceType}
     *
     * @return map that relates a string with the relative PieceType
     */
    private Map<PieceColor, Map<String, PieceType>> getPieceConversionMap() {

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

        Map<PieceColor, Map<String, PieceType>> conversionMap = new HashMap<>();
        conversionMap.put(PieceColor.WHITE, whiteConversionMap);
        conversionMap.put(PieceColor.BLACK, blackConversionMap);

        return conversionMap;
    }

    /**
     * Converts a given chessboard into an array of PieceView, which will be utilized by the view
     *
     * @param c the given chessboard to convert
     * @return the representative array of PieceView
     */
    private ArrayList<PieceView> convertChessboardToView(Chessboard c) {
        ArrayList<PieceView> pieces = new ArrayList<>();
        Piece[][] chessboardMatrix = c.getChessboardMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboardMatrix[i][j] != null)
                    pieces.add(new PieceView(getPieceType(chessboardMatrix[i][j]), new Location(i, j)));
            }
        }
        return pieces;
    }

    /**
     * Utility method to get the type of a piece
     *
     * @param piece the piece to get the type
     * @return the type of a piece
     */
    private PieceType getPieceType(Piece piece) {
        return this.pieceConversionMap.get(piece.getColor()).get(piece.getName());
    }

    public Set<Location> getAvailableMoves(Location piece) {
        return chessboard.getAvailableMoves(piece).keySet();
    }

    public String getPositionFen() {
        return chessboard.toFEN();
    }
}
