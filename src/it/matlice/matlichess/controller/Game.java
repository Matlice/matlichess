package it.matlice.matlichess.controller;

import it.matlice.matlichess.GameState;
import it.matlice.matlichess.controller.net.PositionInit;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.matlichess.exceptions.InvalidTurnException;
import it.matlice.matlichess.model.Chessboard;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.model.Piece;
import it.matlice.matlichess.model.pieces.Bishop;
import it.matlice.matlichess.model.pieces.Knight;
import it.matlice.matlichess.model.pieces.Queen;
import it.matlice.matlichess.model.pieces.Rook;
import it.matlice.matlichess.view.PieceType;
import it.matlice.matlichess.view.PieceView;

import java.util.*;

/**
 * Singleton entity which represents the game session,
 * This class is the controller in the MCV architecture:
 * it contains an instance of the model and an instance of the view and takes information from both of them
 */
public class Game {

    // singleton
    private static Game instance = null;

    // list of player interfaces; 0 is white, 1 is black, any others are watchers
    List<PlayerInterface> players;

    // chessboard model instance
    private Chessboard chessboard;

    // todo get from model instead of having it saved here
    private PieceColor turn = PieceColor.WHITE; //0 white, 1 black

    private Map<PieceColor, Map<String, PieceType>> pieceConversionMap = getPieceConversionMap();

    private Game(List<PlayerInterface> players, List<PlayerInterface> nonPlayers) {
        chessboard = Chessboard.getDefault();
        assert players.size() == 2;
        this.players = players;
        if (nonPlayers != null) {
            this.players.addAll(nonPlayers);
        }
        this.players.forEach(e -> e.setPosition(convertChessboardToView(chessboard)));
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

    /**
     * Singleton getter, passing only the two players
     *
     * @param white the white player
     * @param black the black player
     * @return
     */
    public static Game getInstance(PlayerInterface white, PlayerInterface black) {
        return getInstance(white, black, new ArrayList<>());
    }

    /**
     * Singleton getter, passing white, black and any other "only watching" player interfaces
     *
     * @param white the white player
     * @param black the black player
     * @param nonPlayer any other "player" who is not really playing
     * @return
     */
    public static Game getInstance(PlayerInterface white, PlayerInterface black, PlayerInterface nonPlayer) {
        List<PlayerInterface> nonPlayers = new ArrayList<>();
        nonPlayers.add(nonPlayer);
        return getInstance(white, black, nonPlayers);
    }

    public static Game getInstance(PlayerInterface white, PlayerInterface black, List<PlayerInterface> nonPlayers) {
        ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
        players.add(white);
        players.add(black);
        if (instance == null) instance = new Game(players, nonPlayers);
        return instance;
    }

    public static boolean hasInstance() {
        return instance != null;
    }

    public void setup() {
        this.players.forEach(e -> {
            e.setPosition(convertChessboardToView(chessboard));
            e.setTurn(chessboard.getTurn());
        });
        //in this case, when playing local v local, the last call to setColor will be for white, thus we get the correct view.
        this.players.get(1).setColor(PieceColor.BLACK);
        this.players.get(0).setColor(PieceColor.WHITE);
        turn = chessboard.getTurn();
    }

    public boolean mainloop() {
        List<Location> move = null;
        try {
            move = players.get(turn.index).waitForUserMove(PieceColor.WHITE);
            chessboard.move(move.get(0), move.get(1));
            System.out.println(move);

            List<Location> finalMove = move; // needed for the lambda below
            this.players.forEach(e -> {
                e.setPosition(convertChessboardToView(chessboard));
                e.setMove(finalMove.get(0), finalMove.get(1));
                e.setTurn(chessboard.getTurn());
            });
            GameState state = chessboard.getGameState();
            if(!state.equals(GameState.PLAYING)){
                System.out.println(state);
                throw new RuntimeException();
            }
            turn = chessboard.getTurn();
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move " + move.get(0) + " " + move.get(1));
        } catch (InvalidTurnException e) {
            System.out.println("Wrong turn man " + move.get(0) + " " + move.get(1));
        } catch (InterruptedException e) {
            return true;
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

    public void setPromotion(String promotion) {
        switch (promotion.toUpperCase()) {
            case "Q": chessboard.setPromotion(turn, Queen.class); break;
            case "R": chessboard.setPromotion(turn, Rook.class); break;
            case "B": chessboard.setPromotion(turn, Bishop.class); break;
            case "N": chessboard.setPromotion(turn, Knight.class); break;
            default: break;
        }
    }

    public String getPositionFen(boolean complete) {
        return chessboard.toFEN(complete);
    }

    public String getPositionFen() {
        return chessboard.toFEN(true);
    }

    public boolean isPromotionRequired(Location from, Location to){
        return chessboard.isPromoting(from, to);
    }

    public PieceColor getTurn() {
        return turn;
    }

    public HashMap<String, Integer> getPositions() {
        return chessboard.getPositions();
    }

    public void reinitialize(boolean swapPlayers){
        if(swapPlayers){
            var t = this.players.get(0);
            this.players.set(0, this.players.get(1));
            this.players.set(1, t);
        }
        setup();
        this.players.forEach(PlayerInterface::interrupt);
    }

    private void setPositionFromFen(String fen){

        //chessboard.setPosition(fen);
    }

    public void loadState(PositionInit pos){
        chessboard.setPositions(pos.getMoves(), pos.getMove_times());
        this.turn = pos.getTurn();
        this.chessboard.setTurn(this.turn);
        setPositionFromFen(pos.getCurrentFEN());
        reinitialize(pos.getColor().equals(PieceColor.BLACK) && this.players.get(1) instanceof NetworkPlayer);
    }

    public boolean isMoveValid(Location src, Location dest){
        return chessboard.isMoveValid(src, dest);
    }

}
