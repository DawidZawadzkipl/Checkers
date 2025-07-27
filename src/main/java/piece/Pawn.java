package main.java.piece;

import main.java.main.GamePanel;

public class Pawn extends Piece {

    public Pawn(int color, int col, int row) {
        super(color, col, row);
        
        if(color == GamePanel.WHITE){
            image = getImage("../../resources/white_piece");
        }
        else{
            image = getImage("../../resources/black_piece");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(!isWithinBoard(targetCol, targetRow) || targetCol == preCol || targetRow == preRow){
            return false;
        }
        int moveValue = color == GamePanel.WHITE?1:-1;
            //1 square across
            if(!GamePanel.mustCapture && preRow - targetRow == moveValue && Math.abs(preCol - targetCol) == 1){ //!hasForcedMove() && 
                if(isValidSquare(targetCol, targetRow) && colidingPiece == null){
                    return true;
                }
            }
            //2 squares across(takes)
            if(Math.abs(targetCol - preCol) == 2 && Math.abs(targetRow - preRow) == 2){//isValidSquare(targetCol, targetRow) &&
                int colMove = targetCol>preCol?1:-1;
                int rowMove = targetRow>preRow?1:-1;
                if(detectCollision(targetCol, targetRow) == null && detectCollision(preCol + colMove, preRow + rowMove) != null){
                    colidingPiece = detectCollision(preCol + colMove, preRow + rowMove);
                    if(colidingPiece.color == this.color){
                        return false;
                    }
                    else{
                        return true;
                    }
                }
            }
        return false;
    }
    
    @Override
    public void updatePosition(){
        super.updatePosition();
        //pawn to queen promotion
        if ((color == GamePanel.WHITE && row == 0) || (color == GamePanel.BLACK && row == 7)) {
            int index = getIndex();
            if (index != -1) {
                GamePanel.simPieces.set(index, new Queen(color, col, row));
            }
        }
    }

    @Override
    public boolean hasForcedMove(){
        // up-right
        if(isWithinBoard(preCol+1, preRow+1)){
            Piece adjacentPiece = detectCollision(preCol+1, preRow+1);
            if(adjacentPiece != null && adjacentPiece.color != this.color){
                if(isWithinBoard(preCol+2, preRow+2) && detectCollision(preCol+2, preRow+2) == null){
                    return true;
                }
            }
        }
        
        // up-left 
        if(isWithinBoard(preCol-1, preRow+1)){
            Piece adjacentPiece = detectCollision(preCol-1, preRow+1);
            if(adjacentPiece != null && adjacentPiece.color != this.color){
                if(isWithinBoard(preCol-2, preRow+2) && detectCollision(preCol-2, preRow+2) == null){
                    return true;
                }
            }
        }
        
        // down-right
        if(isWithinBoard(preCol+1, preRow-1)){
            Piece adjacentPiece = detectCollision(preCol+1, preRow-1);
            if(adjacentPiece != null && adjacentPiece.color != this.color){
                if(isWithinBoard(preCol+2, preRow-2) && detectCollision(preCol+2, preRow-2) == null){
                    return true;
                }
            }
        }
        
        // down-left
        if(isWithinBoard(preCol-1, preRow-1)){
            Piece adjacentPiece = detectCollision(preCol-1, preRow-1);
            if(adjacentPiece != null && adjacentPiece.color != this.color){
                if(isWithinBoard(preCol-2, preRow-2) && detectCollision(preCol-2, preRow-2) == null){
                    return true;
                }
            }
        }
        
        return false;
    }
}
