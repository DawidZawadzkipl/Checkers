package main;
import javax.swing.JPanel;

import piece.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class GamePanel extends JPanel implements Runnable{
    final int originalSquareSize = 100;
    final int scale = 1;

    final int squareSize = originalSquareSize * scale;
    final int maxScreenCol = 8;
    final int maxScreenRow = 8;
    final int boardWidth = squareSize * maxScreenCol;
    final int sidebarWidth = 300;
    final int screenWidth = squareSize * maxScreenCol + sidebarWidth;
    final int screenHeight =  squareSize * maxScreenRow;
    final int FPS = 60;

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    
    int currentColor = WHITE;

    private long whiteTime = 0; 
    private long blackTime = 0;
    private long turnStartTime = 0;

    //Pieces
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    public static Piece activePiece;

    boolean canMove;
    boolean validSquare;
    public static boolean mustCapture = false;


    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        //this.setDoubleBuffered(true);
        addMouseListener(mouse);
        addMouseMotionListener(mouse); 
        setPieces();
        copyPieces(pieces, simPieces);
        turnStartTime = System.currentTimeMillis();
    }

    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update(){
        if(gameThread != null) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - turnStartTime;
            
            if(currentColor == WHITE) {
                whiteTime += elapsedTime;
            } else {
                blackTime += elapsedTime;
            }
            turnStartTime = currentTime;
        }
        if(mouse.pressed){
            if(activePiece == null){
                for(Piece p : simPieces){
                    if(p.color == currentColor &&  p.row == mouse.y/Board.SQUARE_SIZE && p.col == mouse.x/Board.SQUARE_SIZE){
                        if(!mustCapture || p.hasForcedMove()){
                            activePiece = p;
                            break;
                        }
                    }
                }
            }
            else{
                simulate();
            }
        }
        if(mouse.pressed == false){
            if(activePiece != null){
                if(validSquare){
                    copyPieces(simPieces, pieces);
                    activePiece.updatePosition();
                    if(activePiece.colidingPiece == null){
                        activePiece = null;
                        canMove = false;
                        validSquare = false;
                        changePlayer();
                    }
                    else{
                        simPieces.remove(activePiece.colidingPiece.getIndex());
                        canMove = false;
                        validSquare = false;
                        if (!activePiece.hasForcedMove()) {
                            activePiece = null;
                            changePlayer();
                        }
                    }
                }
                else{
                    activePiece.resetPosition();
                    activePiece = null;
                }
            }
        }
    }

    private void simulate(){
        canMove = false;
        validSquare = false;

        activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activePiece.col = activePiece.getCol(activePiece.x);
        activePiece.row = activePiece.getRow(activePiece.y);

        if(activePiece.canMove(activePiece.col, activePiece.row)){
            canMove = true;
            validSquare = true;
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        board.draw(g2);

        for(Piece i : simPieces){
            i.draw(g2);
        }
        if(activePiece != null){
            if(canMove){
                g2.setColor(Color.white);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                activePiece.draw(g2);
            }
        }

        drawSidebar(g2);
    }

    private void changePlayer(){
        if(currentColor == WHITE){
            currentColor = BLACK;
        }
        else{
            currentColor = WHITE;
        }
        activePiece = null;
        mustCapture = checkForForcedMoves();
        checkGameEnd();
    }
    
    public void setPieces(){
        pieces.add(new Pawn(WHITE, 0, 7));
        pieces.add(new Pawn(WHITE, 2, 7));
        pieces.add(new Pawn(WHITE, 4, 7));
        pieces.add(new Pawn(WHITE, 6, 7));
        pieces.add(new Pawn(WHITE, 1, 6));
        pieces.add(new Pawn(WHITE, 3, 6));
        pieces.add(new Pawn(WHITE, 5, 6));
        pieces.add(new Pawn(WHITE, 7, 6));
        pieces.add(new Pawn(WHITE, 0, 5));
        pieces.add(new Pawn(WHITE, 2, 5));
        pieces.add(new Pawn(WHITE, 4, 5));
        pieces.add(new Pawn(WHITE, 6, 5));

        pieces.add(new Pawn(BLACK, 1, 0));
        pieces.add(new Pawn(BLACK, 3, 0));
        pieces.add(new Pawn(BLACK, 5, 0));
        pieces.add(new Pawn(BLACK, 7, 0));
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 1, 2));
        pieces.add(new Pawn(BLACK, 3,2));
        pieces.add(new Pawn(BLACK, 5, 2));
        pieces.add(new Pawn(BLACK, 7, 2));
    }

    private void copyPieces(ArrayList<Piece>source, ArrayList<Piece>dest){
        dest.clear();
        for (Piece i : source) {
            dest.add(i);
        }
    }

    private boolean checkForForcedMoves(){
        for (Piece piece : simPieces) {
            if(piece.color == currentColor && piece.hasForcedMove()){
                return true;
            }
        }
        return false;
    }

    public int detectWin(){
        int blackPieces = 0;
        int whitePieces = 0;
        for (Piece piece : pieces) {
            if(piece.color == BLACK){
                blackPieces++;
            }
            else{
                whitePieces++;
            }
        }
        if(blackPieces==0){
            return 0;//white wins
        }
        else if(whitePieces == 0){
            return 1;//black wins
        }
        else{
            return -1;//both have pieces left
        }
    }

    private boolean hasAnyLegalMoves(){
        for(Piece piece : simPieces){
            if(piece.color == currentColor){
                // check all possible moves
                for(int row = 0; row < 8; row++){
                    for(int col = 0; col < 8; col++){
                        if(piece.canMove(col, row)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private void checkGameEnd(){
        // no pieces = loss
        int winner = detectWin();
        if(winner != -1){
            endGame(winner == 0 ? "Białe wygrały!" : "Czarne wygrały!");
            return;
        }
        
        if(!hasAnyLegalMoves()){
            // no moves = loss
            String winnerText = currentColor == WHITE ? "Czarne wygrały!" : "Białe wygrały!";
            endGame(winnerText + " (brak możliwych ruchów)");
            return;
        }
        
        // draw?
        if(checkDrawCondition()){
            endGame("Remis!");
        }
    }

    private boolean checkDrawCondition(){
        //only queens left
        int queens = 0;
        int totalPieces = 0;
        for(Piece p : simPieces){
            totalPieces++;
            if(p instanceof Queen){
                queens++;
            }
        }
        
        if(queens == totalPieces && totalPieces <= 2){
            return true;
        }
        
        return false;
    }

    private void endGame(String message){
        System.out.println(message);
        javax.swing.JOptionPane.showMessageDialog(this, message, "Koniec gry", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        gameThread = null;
    }

    private void drawSidebar(Graphics2D g2) {
        // background
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(boardWidth, 0, sidebarWidth, screenHeight);
        
        // title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("WARCABY", boardWidth + 75, 40);
        
        
        g2.setColor(new Color(100, 100, 100));
        g2.drawLine(boardWidth + 20, 60, boardWidth + sidebarWidth - 20, 60);
        
        
        drawPlayerInfo(g2, "BIAŁE", WHITE, boardWidth + 30, 100);
        drawPlayerInfo(g2, "CZARNE", BLACK, boardWidth + 30, 250);
        
        // playe highlight
        int highlightY = (currentColor == WHITE) ? 85 : 235;
        g2.setColor(new Color(100, 200, 100, 50));
        g2.fillRoundRect(boardWidth + 20, highlightY, sidebarWidth - 40, 130, 10, 10);
        g2.setColor(new Color(100, 200, 100));
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawRoundRect(boardWidth + 20, highlightY, sidebarWidth - 40, 130, 10, 10);
        
        // current player
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(new Color(100, 200, 100));
        int textY = (currentColor == WHITE) ? 75 : 225;
        g2.drawString(">>> TURA GRACZA <<<", boardWidth + 55, textY);
        
        // must capture
        if(mustCapture) {
            g2.setColor(new Color(255, 100, 100));
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("PRZYMUS BICIA!", boardWidth + 80, 420);
        }
        
        // pieces count
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("Pozostałe pionki:", boardWidth + 30, 460);
        
        int whitePieces = 0, blackPieces = 0;
        int whiteQueens = 0, blackQueens = 0;
        for(Piece p : simPieces) {
            if(p.color == WHITE) {
                whitePieces++;
                if(p instanceof Queen) whiteQueens++;
            } else {
                blackPieces++;
                if(p instanceof Queen) blackQueens++;
            }
        }
        
        g2.drawString("Białe: " + whitePieces + " (Królowe: " + whiteQueens + ")", boardWidth + 30, 485);
        g2.drawString("Czarne: " + blackPieces + " (Królowe: " + blackQueens + ")", boardWidth + 30, 505);
    }
    
    private void drawPlayerInfo(Graphics2D g2, String playerName, int playerColor, int x, int y) {
        // player info
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(playerColor == WHITE ? Color.WHITE : new Color(50, 50, 50));
        g2.fillRect(x, y, 50, 50);
        g2.setColor(playerColor == WHITE ? new Color(50, 50, 50) : Color.WHITE);
        g2.drawRect(x, y, 50, 50);
        
        g2.setColor(Color.WHITE);
        g2.drawString(playerName, x + 60, y + 30);
        
        // time
        long timeInSeconds = (playerColor == WHITE ? whiteTime : blackTime) / 1000;
        
        
        if(playerColor == currentColor && gameThread != null) {
            timeInSeconds = ((playerColor == WHITE ? whiteTime : blackTime) + 
                           (System.currentTimeMillis() - turnStartTime)) / 1000;
        }
        
        long minutes = timeInSeconds / 60;
        long seconds = timeInSeconds % 60;
        
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(new Color(200, 200, 200));
        g2.drawString("Czas: " + String.format("%02d:%02d", minutes, seconds), x + 10, y + 80);
    }
}

