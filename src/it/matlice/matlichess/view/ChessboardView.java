package it.matlice.matlichess.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessboardView extends JPanel implements MouseListener {

    public ChessboardView(){}

    public void draw(Graphics2D g2){
        for(int row =0; row < 8; row++){
            for(int column = 0; column < 8; column++){
                if((column+row)%2 == 0) g2.setColor(new Color(152, 118, 54));
                else g2.setColor(new Color(222, 184, 135));
                g2.fillRect(column * 60, (7-row) * 60,60, 60);
            }
        }
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
