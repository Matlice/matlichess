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
import it.matlice.settings.Settings;

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
    private Map<String, String> pieceNameToShortNameMap = getPieceNameToShortNameMap();

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
     * if the game is not initialized yet, we throw a RuntimeError because we need to pass arguments the first time.
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
     * @return the controller instance
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
     * @return the controller instance
     */
    public static Game getInstance(PlayerInterface white, PlayerInterface black, PlayerInterface nonPlayer) {
        List<PlayerInterface> nonPlayers = new ArrayList<>();
        nonPlayers.add(nonPlayer);
        return getInstance(white, black, nonPlayers);
    }

    /**
     * Singleton getter, passing the palyers and a list of "viewer" who will receive the updates
     * for the game in order to display the current position when a view is not necessary
     * - FE when we have two noninteractive players:
     * in that case we don't need the view, so that will result in a blank window.
     * by doing that we allow for external users to see the current position while playing.
     *
     * @param white white player
     * @param black black player
     * @param nonPlayers a list of external players
     * @return the controller instance
     */
    public static Game getInstance(PlayerInterface white, PlayerInterface black, List<PlayerInterface> nonPlayers) {
        ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
        players.add(white);
        players.add(black);
        if (instance == null) instance = new Game(players, nonPlayers);
        return instance;
    }

    /**
     * returns true if the game is already been initialized. this is used to wait (busy waiting sadly)
     * while the game has been instantiated to operate on it.
     * @return true if an instance of the controller is available.
     */
    public static boolean hasInstance() {
        return instance != null;
    }

    /**
     * This method allows to reinstantiate all the players to be reinstantiated in a given position, ready to rematch.
     * @param fen the fen string representative of the start position.
     * @param swapPlayers if true, the white player and the black player will be swapped.
     */
    public void reinitialize(String fen, boolean swapPlayers){
        if(swapPlayers){
            var t = this.players.get(0);
            this.players.set(0, this.players.get(1));
            this.players.set(1, t);
        }
        setup();
        interrupt();
    }


    /**
     * this methods prepares the environment to start a rematch starting from the base position given from the settings.
     * @param swapPlayers if true the players position will be swapped.
     */
    public void rematch(boolean swapPlayers) {
        this.chessboard = Chessboard.getDefault();
        this.turn = PieceColor.WHITE;
        this.players.forEach(e -> {
            e.setMove(null, null);
        });
        reinitialize(Settings.STARTING_POSITION_FEN, swapPlayers);
    }

    /**
     * sets up the players setting the required attributes and data structures.
     */
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

    /**
     * this in the main method.
     * while this method returns true it should be recalled cyclically.
     * it will return false if the game has ended
     * @return true in the game should proceed.
     */
    public boolean mainloop() {

        List<Location> move = null;
        List<Boolean> wants_rematch = new ArrayList<>();
        try {
            System.out.println("ask move for" + turn);
            move = players.get(turn.index).waitForUserMove();
            chessboard.move(move.get(0), move.get(1));
            System.out.println(move);

            GameState newState = chessboard.getGameState();

            List<Location> finalMove = move; // needed for the lambda below
            for (PlayerInterface e : this.players) {
                e.setPosition(convertChessboardToView(chessboard));
                e.setMove(finalMove.get(0), finalMove.get(1));
                e.setTurn(chessboard.getTurn());
            }

            if(!newState.equals(GameState.PLAYING)){
                System.out.println(newState);
                boolean rematch = false;
                if(!players.get(0).isInteractive() && !players.get(1).isInteractive())
                    rematch = players.get(2).setState(newState, true, true);
                else
                    rematch = players.get(0).setState(newState, false, players.get(1));

                if(rematch) rematch(true);
                return rematch;
            }
            turn = chessboard.getTurn();
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move " + move.get(0) + " " + move.get(1));
        } catch (InvalidTurnException e) {
            System.out.println("Wrong turn man " + move.get(0) + " " + move.get(1));
        } catch (InterruptedException e) {
            System.out.println("interrupted");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FUUUUUUUC");
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

    private Map<String, String> getPieceNameToShortNameMap() {

        Map<String, String> pieceNameConversionMap = new HashMap<>();

        pieceNameConversionMap.put("Pawn", "P");
        pieceNameConversionMap.put("Knight", "N");
        pieceNameConversionMap.put("Bishop", "B");
        pieceNameConversionMap.put("Rook", "R");
        pieceNameConversionMap.put("Queen", "Q");
        pieceNameConversionMap.put("King", "K");

        return pieceNameConversionMap;
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

    /**
     * sets the model next promotion chosen by the user before making a move.
     * @param promotion String representing the piece to be prometed to
     * @param player the player who's choosing.
     */
    public void setPromotion(String promotion, PieceColor player) {
        switch (promotion.toUpperCase()) {
            case "Q": chessboard.setPromotion(player, Queen.class); break;
            case "R": chessboard.setPromotion(player, Rook.class); break;
            case "B": chessboard.setPromotion(player, Bishop.class); break;
            case "N": chessboard.setPromotion(player, Knight.class); break;
            default: break;
        }
    }

    /**
     * sets the promotion for the current player
     * @param promotion String representing the piece to be prometed to
     */
    public void setPromotion(String promotion) {
        this.setPromotion(promotion, turn);
    }

    /**
     * sets the promotion piece for all the players, given as an array of two elements.
     * @param promotionTypes an array containing the strings for the piece to promote to as {<white promotion>, <black promotion>}
     */
    public void setPromotions(String[] promotionTypes) {
        this.setPromotion(pieceNameToShortNameMap.get(promotionTypes[PieceColor.WHITE.index]), PieceColor.WHITE);
        this.setPromotion(pieceNameToShortNameMap.get(promotionTypes[PieceColor.BLACK.index]), PieceColor.BLACK);
    }

    /**
     * @see Chessboard#toFEN()
     * @param complete true to get a complete fen
     * @return the fen
     */
    public String getPositionFen(boolean complete) {
        return chessboard.toFEN(complete);
    }

    /**
     * Returns a complete fen. @see Chessboard#toFEN()
     * @return the fen
     */
    public String getPositionFen() {
        return chessboard.toFEN(true);
    }

    /**
     * @see Chessboard#isPromoting(Location, Location)
     * @return the fen
     */
    public boolean isPromotionRequired(Location from, Location to){
        return chessboard.isPromoting(from, to);
    }

    public PieceColor getTurn() {
        return turn;
    }

    public HashMap<String, Integer> getPositions() {
        return chessboard.getPositions();
    }

    /**
     * interrupts all the pending asking moves.
     */
    public void interrupt(){
        this.players.forEach(PlayerInterface::interrupt);
    }

    /**
     * load the game state from a PositionInit class due to network reset. @see NetworkPlayer
     * @param pos
     */
    public void loadState(PositionInit pos){
        chessboard.setPositions(pos.getMoves(), pos.getMove_times());
        if(pos.getColor().equals(PieceColor.WHITE) && players.get(0) instanceof NetworkPlayer || pos.getColor().equals(PieceColor.BLACK) && players.get(1) instanceof NetworkPlayer)
            reinitialize(pos.getCurrentFEN(), true);
        else
            reinitialize(pos.getCurrentFEN(), false);

    }

    public boolean isMoveValid(Location src, Location dest){
        return chessboard.isMoveValid(src, dest);
    }

    public String[] getPromotions() {
        Class<? extends Piece>[] promTypes = chessboard.getPromotions();
        return new String[]{promTypes[0].getSimpleName(), promTypes[1].getSimpleName()};
    }
}
