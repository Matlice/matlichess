package it.matlice.matlichess.view;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Semaphore;

public class PromotionChoiceBox extends JPanel implements MouseListener {

    private String selectedPiece;
    private Semaphore semaphore = new Semaphore(1);
    private PieceColor turn;

    public PromotionChoiceBox(PieceColor turn) {
        this.setPreferredSize(new Dimension(Settings.CHESSBOARD_SQUARE_SIZE, Settings.CHESSBOARD_SQUARE_SIZE*4));
        this.addMouseListener(this);
        this.turn = turn;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (Settings.USE_ANTIALIAS) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        drawPromotionChoiceBox(g2);
    }

    public void drawPromotionChoiceBox(Graphics2D g2) {
        int size = Settings.CHESSBOARD_SQUARE_SIZE;
        PieceView queen;
        PieceView knight;
        PieceView bishop;
        PieceView rook;
        Rectangle box = new Rectangle(0, 0, size, size*4);;

        if(turn.equals(PieceColor.WHITE)){
            queen = new PieceView(PieceType.QUEEN_WHITE, new Location("A8"));
            knight = new PieceView(PieceType.KNIGHT_WHITE, new Location("A7"));
            rook = new PieceView(PieceType.ROOK_WHITE, new Location("A6"));
            bishop = new PieceView(PieceType.BISHOP_WHITE, new Location("A5"));
        }
        else{
            queen = new PieceView(PieceType.QUEEN_BLACK, new Location("A5"));
            knight = new PieceView(PieceType.KNIGHT_BLACK, new Location("A6"));
            rook = new PieceView(PieceType.ROOK_BLACK, new Location("A7"));
            bishop = new PieceView(PieceType.BISHOP_BLACK, new Location("A8"));
        }

        g2.setColor(Color.WHITE);
        g2.fill(box);
        queen.draw(g2);
        knight.draw(g2);
        bishop.draw(g2);
        rook.draw(g2);
    }

    public String askPromotion(Point point){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        EventQueue.invokeLater(() -> {
            frame.setLocation(point);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.add(this);
            frame.setAlwaysOnTop(true);
            frame.pack();
        });
        try {
            semaphore.acquire();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            frame.dispose();
        }
        return selectedPiece;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        selectedPiece = "q";
        semaphore.release();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
