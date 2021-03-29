package it.matlice.stockfish;

import java.util.Map;

/**
 * Class to call the JNI library of Stockfish, a chess engine
 */
public class Stockfish {

    private static boolean stockfish_is_loaded = false;
    private static Stockfish instance = null;

    // Note: tested on Mac Mini (M1) aarch64, Mac x86_64, Linux 64-bit, Windows 64-bit

    static {
        try {
            System.out.println("Loading stockfish...");
            System.loadLibrary("stockfishjni");
            stockfish_is_loaded = true;
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Cannot load stockfish from library path, using integrated distribution");
            try {
                String filenameBase = "stockfish/libstockfishjni.%s.%s";

                // detect operating systems
                String operSys;
                if (System.getProperty("os.name").toLowerCase().contains("win")) operSys = "win";
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

                // detect os architecture
                String osArch = null;
                if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64"))
                    osArch = "amd64";
                else if (System.getProperty("os.arch").equals("i386") || System.getProperty("os.arch").equals("x86"))
                    osArch = "x86";
                else if (System.getProperty("os.arch").equals("aarch64") || System.getProperty("os.arch").equals("arm64"))
                    osArch = "aarch64";
                else {
                    System.err.println("Cannot identify your OS architecture");
                    throw new UnsatisfiedLinkError();
                }

                String path = Stockfish.class.getClassLoader().getResource(String.format(filenameBase, operSys, osArch)).getPath();
                if (path == null) throw new UnsatisfiedLinkError("No dist.");
                System.load(path);

                System.out.println("Loaded Stockfish from " + String.format(filenameBase, operSys, osArch));
                stockfish_is_loaded = true;

            } catch (UnsatisfiedLinkError err) {
                System.err.println("Cannot load stockfish. Some functionalities will be compromised");
            }
        }
    }

    /**
     * Constructor that initializes a Stockfish instance, given <key, value> options
     *
     * @param is_chess_960 if is a chess 960 game
     * @param options      options to pass to the engine (see https://github.com/official-stockfish/Stockfish for available options)
     */
    private Stockfish(boolean is_chess_960, Map<String, String> options) {
        Stockfish.nGetInstance(is_chess_960);
        if (options != null) {
            options.forEach((k, v) -> {
                if (!Stockfish.nSetOption(k, v)) System.err.println("Unknown option " + k);
            });
        }
    }

    /**
     * Returns an instance of this class if not previously loaded, initializing it with given parameters
     *
     * @param is_chess_960 as named
     * @param opt          a map containing the options to be set
     * @return the instance if the native library is loaded, null if not
     */
    public static Stockfish getInstance(boolean is_chess_960, Map<String, String> opt) {
        if (!stockfish_is_loaded) return null;
        if (instance == null) instance = new Stockfish(is_chess_960, opt);
        return instance;
    }

    /**
     * Returns the instance or null as {@link Stockfish#getInstance(boolean, Map)},
     * initializing values as default (is_chess_960=false, no parameters)
     *
     * @return {@link Stockfish#getInstance(boolean, Map)}
     */
    public static Stockfish getInstance() {
        return Stockfish.getInstance(false, null);
    }

    /**
     * Creates the instance
     *
     * @param is_chess_960 as named
     */
    public static native void nGetInstance(boolean is_chess_960);

    //  NATIVE CALLS  //

    /**
     * Destroys the instance
     */
    public static native void nDestroyInstance();

    /**
     * Set an option
     *
     * @param name  option name
     * @param value option value
     * @return true on success, false on error or name not found
     */
    public static native boolean nSetOption(String name, String value);

    /**
     * Sets board position through fen string
     *
     * @param fen
     */
    public static native void nSetPosition(String fen);

    /**
     * Performs a move
     *
     * @param move move description (long algebric notation)
     * @return true on success, false if the move is not possible (?)
     */
    public static native boolean nMakeMove(String move);

    /**
     * Start best move search
     *
     * @param depth  depth
     * @param ponder set it to false =)
     */
    public static native void nSearchBestMove(int depth, boolean ponder);

    /**
     * Waits until next move is found and returns it
     *
     * @return integer representing the move ()
     */
    public static native int nGetFoundNextMove();

    /**
     * Waits until next move is found and returns it
     *
     * @return the move in long algebric notation
     */
    public static native String nGetFoundNextMoveStr();

    /**
     * Inverts the sides
     */
    public static native void nFlip();

    /**
     * Starts a new game
     */
    public static native void nNewGame();

    /**
     * Starts a new game
     */
    public static native void nDbgDisplay();

    /**
     * Returns the calculated score
     *
     * @param print cout detailed scores
     * @return
     */
    public static native float nGetScore(boolean print);

    /**
     * Returns current position fen
     *
     * @return current position fen
     */
    public static native String nGetFen();

    /**
     * Set an options for the engine
     * Available options are documented here: https://github.com/official-stockfish/Stockfish
     *
     * @param name  name of the option
     * @param value value of the option
     * @return
     */
    public boolean setOption(String name, String value) {
        return Stockfish.nSetOption(name, value);
    }

}
