package it.matlice.stockfish;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Stockfish {

    private static boolean stockfish_is_loaded = false;
    private static Stockfish instance = null;

    static {
        try {
            System.out.println("Loading stockfish...");
            System.loadLibrary("stockfishjni");
            stockfish_is_loaded = true;
        } catch (UnsatisfiedLinkError e){
            System.out.println("Cannot load stockfish from library path, using integrated distribution");
            try {
                String filenameBase = "stockfish/libstockfishjni.%s.%s";

                String operSys = null;
                if(System.getProperty("os.name").toLowerCase().contains("win")) operSys = "win";
                else if (System.getProperty("os.name").toLowerCase().contains("nux")) operSys = "linux";
                else if (System.getProperty("os.name").toLowerCase().contains("mac") ||
                        System.getProperty("os.name").toLowerCase().contains("darwin")) operSys = "osx";
                else {
                    System.err.println("Cannot identify your OS");
                    throw new UnsatisfiedLinkError();
                }

                // Linux            ->      amd64, i386
                // Windows          ->      x86_64, x86
                // Mac Mini (M1)    ->      aarch64
                String osArch = null;
                if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) osArch = "amd64";
                else if (System.getProperty("os.arch").equals("i386") || System.getProperty("os.arch").equals("x86")) osArch = "x86";
                else if (System.getProperty("os.arch").equals("aarch64") || System.getProperty("os.arch").equals("arm64")) osArch = "aarch64";
                else {
                    System.err.println("Cannot identify your OS architecture");
                    throw new UnsatisfiedLinkError();
                }

                System.out.println(String.format(filenameBase, operSys, osArch));

                String path = Stockfish.class.getClassLoader().getResource(String.format(filenameBase, operSys, osArch)).getPath();

                if(path == null)
                    throw new UnsatisfiedLinkError("No dist.");
                System.load(path);
                stockfish_is_loaded = true;
            } catch (UnsatisfiedLinkError err){
                System.err.println("Cannot load stockfish. Some functionalities will be compromised");
            }
        }
    }

    private Stockfish(boolean is_chess_960, Map<String, String> options) {
        Stockfish.nGetInstance(is_chess_960);
        if(options != null){
            options.forEach((k, v) -> {
                if(!Stockfish.nSetOption(k, v)) System.err.println("Unknown option " + k);
            });
        }
    }

    /**
     * Returns an instance of this class if not previously loaded, initializing it with given parameters
     * @param is_chess_960 as named
     * @param opt a map containing the options to be set
     * @return the instance if the native library is loaded, null if not
     */
    public static Stockfish getInstance(boolean is_chess_960, Map<String, String> opt) {
        if(!stockfish_is_loaded) return null;
        if(instance == null) instance = new Stockfish(is_chess_960, opt);
        return instance;
    }

    /**
     * returns the instance or null as {@link Stockfish#getInstance(boolean, Map)},
     * initializing values as default (is_chess_960=false, no parameters)
     * @return {@link Stockfish#getInstance(boolean, Map)}
     */
    public static Stockfish getInstance() {
        return Stockfish.getInstance(false, null);
    }

    public boolean setOption(String name, String value){
        return Stockfish.nSetOption(name, value);
    }

    //==================================================NATIVE METHODS
    //todo make public wrappers for native calls
    /**
     * Creates the instance
     * @param is_chess_960 as named
     */
    public static native void nGetInstance(boolean is_chess_960);
    /**
     * destroys the instance
     */
    public static native void nDestroyInstance();
    /**
     * set an option
     * @param name option name
     * @param value option value
     * @return true on success, false on error or name not found
     */
    public static native boolean nSetOption(String name, String value);
    /**
     * sets board position through fen string
     * @param fen
     */
    public static native void nSetPosition(String fen);
    /**
     * performs a move
     * @param move move description (long algebric notation)
     * @return true on success, false if the move is not possible (?)
     */
    public static native boolean nMakeMove(String move);
    /**
     * start best move search
     * @param depth depth
     * @param ponder set it to false =)
     */
    public static native void nSearchBestMove(int depth, boolean ponder);
    /**
     * waits until next move is found and returns it
     * @return integer representing the move ()
     */
    public static native int nGetFoundNextMove();
    /**
     * waits until next move is found and returns it
     * @return the move in long algebric notation
     */
    public static native String nGetFoundNextMoveStr();
    /**
     * inverts the sides
     */
    public static native void nFlip();
    /**
     * starts a new game
     */
    public static native void nNewGame();
    /**
     * starts a new game
     */
    public static native void nDbgDisplay();
    /**
     * returns the calculated score
     * @param print cout detailed scores
     * @return
     */
    public static native float nGetScore(boolean print);

    /**
     * returns current position fen
     * @return current position fen
     */
    public static native String nGetFen();
//
//    @Override
//    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
//        var move = StockfishPlayer.nGetFoundNextMoveStr();
//        System.out.println("STOCKFISH" + move);
//        return null;
//    }
//
//    @Override
//    public void setPosition(ArrayList<PieceView> pieces) {
//        StockfishPlayer.nSetPosition(Game.getInstance().getPositionFen());
//        StockfishPlayer.nDbgDisplay();
//    }
//
//    @Override
//    public void setTurn(PieceColor turn) {
//        StockfishPlayer.nFlip();
//    }
}
