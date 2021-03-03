package it.matlice.matlichess.view;

import it.matlice.CommunicationSemaphore;
import it.matlice.matlichess.model.Location;
import it.matlice.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ChessboardView extends JPanel implements MouseListener {

    private boolean asking_move = false;
    private Location move_from = null;

    public ChessboardView(){
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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.asking_move) this.move_from = pointerToLocation(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(asking_move){
            var to = pointerToLocation(e);
            if(!to.equals(move_from))
                this.wait_move.r_release(to);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void askMove() throws InterruptedException {
        this.asking_move = true;
        this.move_from = null;
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

    public void makeMove(Location frm, Location to){
    }
}
