package main.java.piece;

import main.java.main.GamePanel;

public class Queen extends Piece {

    public Queen(int color, int col, int row){
        super(color, col, row);
        if(color == GamePanel.WHITE){
            image = getImage("../../resources/white_queen");
        }
        else{
            image = getImage("../../resources/black_queen");
        }
    }

    @Override
    public boolean canMove(int targetCol, int targetRow){
        if(!isWithinBoard(targetCol, targetRow) || (targetCol == preCol && targetRow == preRow)){
            return false;
        }

        // is move diagonal
        if(Math.abs(targetCol - preCol) != Math.abs(targetRow - preRow)){
            return false;
        }

        //forced moves only
        if(GamePanel.mustCapture && !hasForcedMove()){
            return false;
        }

        
        return isPathClear(targetCol, targetRow) && canMakeMove(targetCol, targetRow);
    }

    private boolean isPathClear(int targetCol, int targetRow){
        int colDirection = targetCol > preCol ? 1 : -1;
        int rowDirection = targetRow > preRow ? 1 : -1;
        
        int currentCol = preCol + colDirection;
        int currentRow = preRow + rowDirection;
        
        Piece foundPiece = null;
        int pieceCount = 0;
        
        
        while(currentCol != targetCol && currentRow != targetRow){
            Piece piece = detectCollision(currentCol, currentRow);
            if(piece != null){
                foundPiece = piece;
                pieceCount++;
                if(pieceCount > 1){
                    return false; 
                }
            }
            currentCol += colDirection;
            currentRow += rowDirection;
        }
        
        //target square check
        Piece targetPiece = detectCollision(targetCol, targetRow);
        if(targetPiece != null){
            return false; // occupied
        }
        
        
        if(foundPiece != null){
            if(foundPiece.color == this.color){
                return false; 
            }
            colidingPiece = foundPiece;
        } else {
            colidingPiece = null;
        }
        
        return true;
    }

    private boolean canMakeMove(int targetCol, int targetRow){
        // if has to capture, move must be a capture move
        if(GamePanel.mustCapture){
            return colidingPiece != null && colidingPiece.color != this.color;
        }
        return true;
    }

    @Override
    public boolean hasForcedMove(){
        // check all possible directions
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        
        for(int[] dir : directions){
            if(canCaptureInDirection(dir[0], dir[1])){
                return true;
            }
        }
        return false;
    }

    private boolean canCaptureInDirection(int colDir, int rowDir){
        int currentCol = preCol + colDir;
        int currentRow = preRow + rowDir;
        
        // search for first piece in the way
        while(isWithinBoard(currentCol, currentRow)){
            Piece piece = detectCollision(currentCol, currentRow);
            if(piece != null){
                // piece found
                if(piece.color != this.color){
                    // enemies piece
                    int landCol = currentCol + colDir;
                    int landRow = currentRow + rowDir;
                    //check next square
                    if(isWithinBoard(landCol, landRow) && detectCollision(landCol, landRow) == null){
                        return true; // can capture
                    }
                }
                break; // piece found, stop
            }
            currentCol += colDir;
            currentRow += rowDir;
        }
        return false;
    }
}