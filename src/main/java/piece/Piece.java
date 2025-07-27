package main.java.piece;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.main.Board;
import main.java.main.GamePanel;

import java.awt.Graphics2D;

public class Piece {
    public BufferedImage image;
    public int x,y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece colidingPiece;

    public Piece(int color, int col, int row){
        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public BufferedImage getImage(String imagePath){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public int getX(int col){
        return col*Board.SQUARE_SIZE;
    }
    
    public int getY(int row){
        return row*Board.SQUARE_SIZE;
    }
    public int getCol(int x){
        return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public int getRow(int y){
        return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public int getIndex(){
        for(int i=0; i<GamePanel.simPieces.size();i++){
            if(this == GamePanel.simPieces.get(i)){
                return i;
            }
        }
        return -1;
    }

    public void updatePosition(){
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public boolean canMove(int targetCol, int targetRow){
        return false;
    }

    public boolean isWithinBoard(int targetCol, int targetRow){
        if(targetCol > 7 || targetCol < 0 || targetRow > 7 || targetRow < 0){
            return false;
        }
        return true; 
    }

    public Piece detectCollision(int targetCol, int targetRow){
        for (Piece p : GamePanel.simPieces) {
            if(targetCol == p.col && targetRow == p.row && p != this){
                return p;
            }
        }
        return null;
    }

    public boolean isValidSquare(int targetCol, int targetRow){
        colidingPiece = detectCollision(targetCol, targetRow);

        if(colidingPiece == null){
            return true;
        }
        else{
            if(colidingPiece.color != this.color){
                return true;
            }
            else{
                colidingPiece = null;
            }
        }
        return false;
    }
    //checks wether piece has to take other piece 
    public boolean hasForcedMove(){
        return false;
    }

    public void resetPosition(){
        x = getX(preCol);
        y = getY(preRow);
        row = preRow;
        col = preCol;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
