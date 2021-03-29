package it.matlice.matlichess.view;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the representative of the view in the MCV architecture.
 */
public class View {

    private static View instance = null;
    private final JFrame frame;
    private final ChessboardView chessboardView;

    private View() {
        this.frame = new JFrame();
        this.chessboardView = new ChessboardView(frame);
    }

    //the instance is a singleton
    public static View getInstance() {
        if (instance == null) instance = new View();
        return instance;
    }

    /**
     * creates the main frame and adds the {@link ChessboardView} to it
     */
    public void initialize() {
        EventQueue.invokeLater(() -> {
            frame.setBounds(20, 20, 800, 800);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setTitle("MatliChess");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(chessboardView);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public ChessboardView getChessboardView() {
        return this.chessboardView;
    }
}
