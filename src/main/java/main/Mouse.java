package main.java.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter{
    //pozmieniac na private i zrobic settery i gettery
    public int x,y;
    boolean pressed;
    
    @Override
    public void mousePressed(MouseEvent e){
        pressed = true;
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        pressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }
}
