package it.matlice.matlichess.view;

import it.matlice.CommunicationSemaphore;
import it.matlice.matlichess.GameState;
import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.controller.Game;
import it.matlice.matlichess.controller.PlayerInterface;
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
import java.util.Set;

import static it.matlice.matlichess.view.PieceView.locationToPointer;
import static it.matlice.matlichess.view.PieceView.pointerToLocation;

public class ChessboardView extends JPanel implements MouseListener, MouseMotionListener, PlayerInterface {

    private boolean asking_move = false;
    private Location[] mouse = new Location[4];
    private int mouse_index = 0;
    private Location move_from;
    private Location selected;
    private Set<Location> feasableMoves = null;
    private PieceColor myColor = PieceColor.WHITE;
    private PieceColor turn = PieceColor.WHITE;
    private Thread t = null;

    private ArrayList<PieceView> pieces = new ArrayList<>();
    private CommunicationSemaphore<Location> wait_move = new CommunicationSemaphore<>(1);
    private Location[] lastMove = new Location[2];
    private JFrame parentFrame;

    public ChessboardView(JFrame frame) {
        this.parentFrame = frame;
        this.setPreferredSize(new Dimension(Settings.CHESSBOARD_SIZE, Settings.CHESSBOARD_SIZE));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    public void drawBoard(Graphics2D g2) {
        Settings.CHESSBOARD_BG.accept(g2, new ScreenLocation());
    }

    public void drawSelectedSquare(Graphics2D g2) {
        g2.setColor(Settings.SELECTION_BG_COLOR);
        if (selected != null) {
            if (this.myColor.equals(PieceColor.BLACK)) {
                g2.fillRect(Settings.CHESSBOARD_SQUARE_SIZE * (7 - selected.col()),
                        Settings.CHESSBOARD_SQUARE_SIZE * selected.row(),
                        Settings.CHESSBOARD_SQUARE_SIZE,
                        Settings.CHESSBOARD_SQUARE_SIZE);
            } else {
                g2.fillRect(Settings.CHESSBOARD_SQUARE_SIZE * selected.col(),
                        Settings.CHESSBOARD_SQUARE_SIZE * (7 - selected.row()),
                        Settings.CHESSBOARD_SQUARE_SIZE,
                        Settings.CHESSBOARD_SQUARE_SIZE);
            }
        }
    }

    public void drawLastMove(Graphics2D g2){
        g2.setColor(Settings.SELECTION_BG_COLOR);
        if(Arrays.equals(lastMove, new Location[2])) return;
        if (this.myColor.equals(PieceColor.BLACK)) {
            for (Location l: lastMove)
                g2.fillRect(Settings.CHESSBOARD_SQUARE_SIZE * (7-l.col()),
                        Settings.CHESSBOARD_SQUARE_SIZE * l.row(),
                        Settings.CHESSBOARD_SQUARE_SIZE,
                        Settings.CHESSBOARD_SQUARE_SIZE);
        }else{
            for (Location l: lastMove)
                g2.fillRect(Settings.CHESSBOARD_SQUARE_SIZE * l.col(),
                        Settings.CHESSBOARD_SQUARE_SIZE * (7 - l.row()),
                        Settings.CHESSBOARD_SQUARE_SIZE,
                        Settings.CHESSBOARD_SQUARE_SIZE);
        }
    }

    public void drawFeasableMoves(Graphics2D g2) {
        if (selected != null) {
            if (feasableMoves != null)
                feasableMoves.forEach((e) -> {
                    var p = locationToPointer(e, this.myColor.equals(PieceColor.BLACK));
                    final boolean[] is_capture = new boolean[]{false};
                    pieces.forEach(x -> {
                        if (x.getLocation().equals(e)) {
                            g2.setColor(Settings.CAPTURE_COLOR);
                            g2.fillOval(p.x + Settings.CHESSBOARD_SQUARE_SIZE / 2 - Settings.CAPTURE_DIAMETER / 2,
                                    p.y + Settings.CHESSBOARD_SQUARE_SIZE / 2 - Settings.CAPTURE_DIAMETER / 2,
                                    Settings.CAPTURE_DIAMETER,
                                    Settings.CAPTURE_DIAMETER);
                            is_capture[0] = true;
                        }
                    });
                    if (!is_capture[0]) {
                        g2.setColor(Settings.MOVE_COLOR);
                        g2.fillOval(p.x + Settings.CHESSBOARD_SQUARE_SIZE / 2 - Settings.MOVE_DIAMETER / 2,
                                p.y + Settings.CHESSBOARD_SQUARE_SIZE / 2 - Settings.MOVE_DIAMETER / 2,
                                Settings.MOVE_DIAMETER,
                                Settings.MOVE_DIAMETER);
                    }
                });
        }
    }

    public void drawPieces(Graphics2D g2) {
        PieceView drawLast = null;
        for (PieceView pv : pieces) {
            if (pv.getLocation().equals(mouse[0]))
                drawLast = pv;
            else
                pv.draw(g2, this.myColor.equals(PieceColor.BLACK));
        }
        if (drawLast != null) drawLast.draw(g2, this.myColor.equals(PieceColor.BLACK));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBoard(g2);

        if (Settings.USE_ANTIALIAS) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        drawSelectedSquare(g2);
        drawLastMove(g2);
        drawFeasableMoves(g2);
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
            pointerLoc = pointerToLocation(e, this.myColor.equals(PieceColor.BLACK));
        } catch (InvalidMoveException exc) {
            return;
        }

        if (this.asking_move) {
            if (mouse_index == 0) {
                if (!isMyPiece(pointerLoc)) return;
            }
            if (mouse_index == 2) {
                if (isMyPiece(pointerLoc)) {
                    resetClicks();
                }
            }
            this.mouse[this.mouse_index] = pointerLoc;
            if (isMyPiece(pointerLoc)) {
                this.selected = this.mouse[0];
                this.feasableMoves = Game.getInstance().getAvailableMoves(selected);
            }
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
            this.repaint();
            return;
        }

        Location pointerLoc;
        try {
            pointerLoc = pointerToLocation(e, this.myColor.equals(PieceColor.BLACK));
        } catch (InvalidMoveException exc) {
            return;
        }

        if (asking_move) {
            this.mouse[this.mouse_index] = pointerLoc;
            mouse_index += 1;
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
        if (mouse[2] != null) {
            mouse[2] = null;
            mouse[3] = null;
            mouse_index = 2;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (mouse[0] != null && mouse[0].equals(mouse[1]) && mouse[2] == null) return;
        this.pieces
                .stream()
                .filter(p -> p.getLocation().equals(this.mouse[2] != null ? this.mouse[2] : this.mouse[0]))
                .findFirst().ifPresent(PieceView::resetOffset);

        resetClicks();
        this.repaint();
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

    private String askPromotionPiece() {
        String input;
        do {
            input = JOptionPane.showInputDialog("Promotion: ");
        } while (!input.equals("q") && !input.equals("n") && !input.equals("r") && !input.equals("b"));
        return input;
    }

    @Override
    public void setColor(PieceColor color) {
        this.feasableMoves = null;
        this.myColor = color;
    }


    @Override
    public List<Location> waitForUserMove(PieceColor side) throws InterruptedException {
        Location obtained;
        do {
            this.askMove();
            //save thread
            t = Thread.currentThread();
            obtained = this.wait_move.r_acquire();
            t = null;
            this.wait_move.r_release(null);
            this.asking_move = false;
        } while (this.move_from == null || obtained == null);
        if (Game.getInstance().isPromotionRequired(move_from, obtained)) {
            String promotion = new PromotionChoiceBox(turn).askPromotion(getPromotionCoordinate(obtained));
            Game.getInstance().setPromotion(promotion);
        }
        this.feasableMoves = null;
        return Arrays.asList(this.move_from, obtained);
    }

    private Point getPromotionCoordinate(Location l) {
        Point origin = this.getLocationOnScreen();
        int xCoord;
        int yCoord;
        if (myColor.equals(PieceColor.WHITE)) {
            xCoord = l.col() * Settings.CHESSBOARD_SQUARE_SIZE;
            if (turn.equals(PieceColor.WHITE)) yCoord = (7 - l.row()) * Settings.CHESSBOARD_SQUARE_SIZE;
            else yCoord = (4 + l.row()) * Settings.CHESSBOARD_SQUARE_SIZE;
        } else {
            xCoord = (7 - l.col()) * Settings.CHESSBOARD_SQUARE_SIZE;
            if (turn.equals(PieceColor.BLACK)) yCoord = l.row() * Settings.CHESSBOARD_SQUARE_SIZE;
            else yCoord = (l.row() - 3) * Settings.CHESSBOARD_SQUARE_SIZE;
        }
        return new Point(origin.x + xCoord, origin.y + yCoord);
    }

    @Override
    public void interrupt() {
        if(t != null)
            t.interrupt();
    }

    @Override
    public void setPosition(ArrayList<PieceView> pieces) {
        this.pieces = pieces;
        this.repaint();
    }

    @Override
    public void setMove(Location from, Location to) {
        lastMove[0] = from;
        lastMove[1] = to;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        var piece_at = this.pieces
                .stream()
                .filter(p -> p.getLocation().equals(this.mouse[2] != null ? this.mouse[2] : this.mouse[0]))
                .findFirst().orElse(null);

        if (piece_at != null && isMyPiece(piece_at.getLocation())) {
            piece_at.setOffset(locationToPointer(piece_at.getLocation(), this.myColor.equals(PieceColor.BLACK))
                    .diff(e.getPoint())
                    .diff(-Settings.CHESSBOARD_SQUARE_SIZE / 2, -Settings.CHESSBOARD_SQUARE_SIZE / 2));
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private boolean isMyPiece(Location l) {
        for (PieceView p : pieces) {
            if (p.getLocation().equals(l))
                if (p.getPieceType().getColor().equals(turn)) return true;
        }
        return false;
    }

    private boolean ask_for_consensus(GameState state, boolean generic){
        if (state.equals(GameState.PLAYING)) return false;

        StringBuilder message = new StringBuilder();

        if (state.equals(GameState.DRAW))
            message.append(Settings.DRAW_MESSAGE);
        else if (generic)
            message.append(String.format(Settings.GENERIC_WIN_MESSAGE, state.getWinnerString()));
        else if (state.equals(GameState.WHITE_WIN)) {
            if (myColor.equals(PieceColor.WHITE))
                message.append(Settings.WIN_MESSAGE);
            else
                message.append(Settings.LOST_MESSAGE);
        } else {
            if (myColor.equals(PieceColor.WHITE))
                message.append(Settings.LOST_MESSAGE);
            else
                message.append(Settings.WIN_MESSAGE);
        }

        message.append("\n\n");
        message.append(Settings.REMATCH_MESSAGE);

        int selection = JOptionPane.showOptionDialog(this.getParent(),
                message,
                "Endgame",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                Settings.DIALOG_OPTIONS,
                Settings.DIALOG_OPTIONS[0]);

        if (selection == Settings.CANCEL_OPTION_INDEX || selection == Settings.DIALOG_CLOSED_INDEX)
            return false;
        else if (selection == Settings.EXIT_OPTION_INDEX) {
            parentFrame.dispose();
            return false;
        }
        return selection == Settings.REMATCH_OPTION_INDEX;
    }

    @Override
    public boolean setState(GameState state, boolean generic, PlayerInterface opponent) {
                // solo se color è settato, altrimenti è spettatore
        boolean consensus = ask_for_consensus(state, generic);
        return consensus && opponent == null || opponent instanceof ChessboardView || opponent.setState(state, generic, consensus);
    }

    public boolean setState(GameState state, boolean generic, Boolean other_choice){
        if(!other_choice) return false;
        return this.ask_for_consensus(state, generic);
    }
}