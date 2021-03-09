package it.matlice.matlichess.view;

import it.matlice.CommunicationSemaphore;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.exceptions.InvalidMoveException;
import it.matlice.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.matlice.matlichess.view.PieceView.locationToPointer;
import static it.matlice.matlichess.view.PieceView.pointerToLocation;

public class ChessboardView extends JPanel implements MouseListener, MouseMotionListener {

    private boolean asking_move = false;
    private Location[] mouse = new Location[4];
    private int mouse_index = 0;
    private Location move_from;
    private Location selected;
    private PieceColor turn = PieceColor.WHITE;

    private ArrayList<PieceView> pieces = new ArrayList<>();

    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    public ChessboardView() {
        this.setPreferredSize(new Dimension(Settings.CHESSBOARD_SIZE, Settings.CHESSBOARD_SIZE));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private CommunicationSemaphore<Location> wait_move = new CommunicationSemaphore<>(1);

    public void drawBoard(Graphics2D g2) {
        Settings.CHESSBOARD_BG.accept(g2, new ScreenLocation());
    }

    public void drawPieces(Graphics2D g2) {
        PieceView drawLast = null;
        for (PieceView pv : pieces) {
            if (pv.getLocation().equals(mouse[0]))
                drawLast = pv;
            else
                pv.draw(g2);
        }
        if (drawLast != null)
            drawLast.draw(g2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBoard(g2);

        g2.setColor(new Color(0, 172, 151, 77));
        if (selected != null) {

            /*
             todo
               g2.fillOval((int) ( ((double) Settings.CHESSBOARD_SIZE /8) * ((double) selected.col() + 1.0/2 ) - Settings.MARKER_DIAMETER/2),
                       (int) ( ((double) Settings.CHESSBOARD_SIZE /8) * (7 - (double) selected.row() + 1.0/2 ) - Settings.MARKER_DIAMETER/2),
                        Settings.MARKER_DIAMETER, Settings.MARKER_DIAMETER);
             */

            g2.fillRect((int) (((double) Settings.CHESSBOARD_SIZE / 8) * ((double) selected.col())),
                    (int) (((double) Settings.CHESSBOARD_SIZE / 8) * (7 - (double) selected.row())),
                    Settings.CHESSBOARD_SIZE / 8, Settings.CHESSBOARD_SIZE / 8);
        }
        drawPieces(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3) {
            return;
        }

        Location pointerLoc;

        try {
            pointerLoc = pointerToLocation(e);
        } catch (InvalidMoveException exc) {
            return;
        }

        if (this.asking_move) {
            if(mouse_index == 0){
                if (!isMyPiece(pointerLoc)) return;
            }
            if (mouse_index == 2) {
                if (isMyPiece(pointerLoc)) {
                    resetClicks();
                }
            }
            this.mouse[this.mouse_index] = pointerLoc;
            if (isMyPiece(pointerLoc)) this.selected = this.mouse[0];
            mouse_index++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3) {

            var piece_at = this.pieces
                    .stream()
                    .filter(p -> p.getLocation().equals(this.mouse[2] != null ? this.mouse[2] : this.mouse[0]))
                    .findFirst().orElse(null);

            if (piece_at != null)
                piece_at.resetOffset();

            resetClicks();
            this.selected = null;

            this.repaint();
            return;
        }

        Location pointerLoc;

        try {
            pointerLoc = pointerToLocation(e);
        } catch (InvalidMoveException exc) {
            return;
        }

        if (asking_move) {
            this.mouse[this.mouse_index] = pointerLoc;
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
        this.pieces.forEach(PieceView::resetOffset);
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void resetClicks() {
        this.mouse = new Location[4];
        this.selected = null;
        this.mouse_index = 0;
    }

    private void askMove() throws InterruptedException {
        this.asking_move = true;
        this.resetClicks();
        this.wait_move.r_acquire();
    }

    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
        Location obtained;
        do {
            this.askMove();
            obtained = this.wait_move.r_acquire();
            this.wait_move.r_release(null);
            this.asking_move = false;
        } while (this.move_from == null || obtained == null);
        return Arrays.asList(this.move_from, obtained);
    }

    public void setPosition(ArrayList<PieceView> pieces) {
        this.pieces = pieces;
        this.repaint();
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        var piece_at = this.pieces
                .stream()
                .filter(p -> p.getLocation().equals(this.mouse[2] != null ? this.mouse[2] : this.mouse[0]))
                .findFirst().orElse(null);

        if (piece_at != null && isMyPiece(piece_at.getLocation())) {
            piece_at.setOffset(locationToPointer(piece_at.getLocation())
                    .diff(e.getPoint())
                    .diff(-Settings.CHESSBOARD_SIZE / 16, -Settings.CHESSBOARD_SIZE / 16));
            this.repaint();
        }

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) { }

    private boolean isMyPiece(Location l) {
        for (PieceView p : pieces) {
            if (p.getLocation().equals(l))
                if (p.getPieceType().getColor().equals(turn)) return true;
        }
        return false;
    }
}