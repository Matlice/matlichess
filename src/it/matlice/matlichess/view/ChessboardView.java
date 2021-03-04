package it.matlice.matlichess.view;

import it.matlice.CommunicationSemaphore;
import it.matlice.matlichess.model.Location;
import it.matlice.settings.Settings;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

public class ChessboardView extends JPanel implements MouseListener {

    private boolean asking_move = false;
    private Location[] mouse = new Location[4];
    private int mouse_index = 0;
    private Location move_from;
    private Location selected;

    private PieceType[][] pieces = new PieceType[8][8];

    public ChessboardView(){
        this.setPreferredSize( new Dimension(Settings.CHESSBOARD_SIZE, Settings.CHESSBOARD_SIZE));
        this.addMouseListener(this);
    }

    private CommunicationSemaphore<Location> wait_move = new CommunicationSemaphore<>(1);

    public void draw(Graphics2D g2){
        Settings.CHESSBOARD_BG.accept(g2, new Dimension(0, 0));
    }

    private Location pointerToLocation(MouseEvent e){
        return new Location(e.getX()/(Settings.CHESSBOARD_SIZE/8), (Settings.CHESSBOARD_SIZE - e.getY())/(Settings.CHESSBOARD_SIZE/8));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        draw(g2);

        // vv todo remove vv
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (pieces[j][7-i] != null)
                    System.out.print(pieces[j][7-i].toString() + "  \t");
                else
                    System.out.print("---  \t");
            }
            System.out.println();
        }

        System.out.println("\n\n\n");
        // ^^ todo remove ^^

        g2.setColor(new Color(0, 172, 151, 77));
        if (selected != null) {
            // todo
//                g2.fillOval((int) ( ((double) Settings.CHESSBOARD_SIZE /8) * ((double) selected.col() + 1.0/2 ) - Settings.MARKER_DIAMETER/2),
//                        (int) ( ((double) Settings.CHESSBOARD_SIZE /8) * (7 - (double) selected.row() + 1.0/2 ) - Settings.MARKER_DIAMETER/2),
//                        Settings.MARKER_DIAMETER, Settings.MARKER_DIAMETER);
            g2.fillRect((int) ( ((double) Settings.CHESSBOARD_SIZE /8) * ((double) selected.col())),
                    (int) ( ((double) Settings.CHESSBOARD_SIZE /8) * (7 - (double) selected.row())),
                    Settings.CHESSBOARD_SIZE /8, Settings.CHESSBOARD_SIZE /8);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.asking_move) {
            this.mouse[this.mouse_index] = pointerToLocation(e);
            mouse_index++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(asking_move){
            this.mouse[this.mouse_index] = pointerToLocation(e);
            mouse_index++;
        }

        if (this.mouse[0] == null) return;

        if (!this.mouse[0].equals(this.mouse[1])) {
            // if first press and first release are different, it's a drag-and-drop move
            this.move_from = this.mouse[0];
            this.wait_move.r_release(this.mouse[1]);
        } else if (this.mouse_index > 2) {
            // if a second click is made
            if (this.mouse[2].equals(this.mouse[3])) {
                // if second press and second release are equal, the move is from first to second click ...
                if (!this.mouse[0].equals(this.mouse[2])) {
                    // ... but only if from and to are different locations
                    this.move_from = this.mouse[0];
                    this.wait_move.r_release(this.mouse[2]);
                }
            }
            this.resetClicks();
        } else {
            this.selected = this.mouse[0];
        }

        this.repaint();

    }

    private void resetClicks() {
        this.mouse = new Location[4];
        this.selected = null;
        this.mouse_index = 0;
    }



    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void askMove() throws InterruptedException {
        this.asking_move = true;
        this.resetClicks();
        this.wait_move.r_acquire();
        //todo
    }

    public List<Location> waitForUserMove(it.matlice.matlichess.model.Color side) throws InterruptedException {
        this.askMove();
        var obtained = this.wait_move.r_acquire();
        this.wait_move.r_release(null);
        this.asking_move = false;
        return Arrays.asList(this.move_from, obtained);
    }

//    public void makeMove(int from_col, int from_row, int to_col, int to_row) {
//        this.pieces[to_col][to_row] = this.pieces[from_col][from_row];
//        this.pieces[from_col][from_row] = null;
//
//        this.repaint();
//    }

    public void setPosition(PieceType[][] pieces) {
        this.pieces = pieces;

        this.repaint();
    }
}