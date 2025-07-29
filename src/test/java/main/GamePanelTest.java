package main;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import piece.Pawn;
import piece.Queen;

class GamePanelTest {

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
    }

    @AfterEach
    void tearDown() {
        GamePanel.simPieces.clear();
        GamePanel.pieces.clear();
        GamePanel.activePiece = null;
        GamePanel.mustCapture = false;
    }

    @Test
    void shouldInitializeWithCorrectPieceCount() {
        long whitePieces = GamePanel.pieces.stream()
                .filter(p -> p.color == GamePanel.WHITE)
                .count();
        long blackPieces = GamePanel.pieces.stream()
                .filter(p -> p.color == GamePanel.BLACK)
                .count();

        assertThat(whitePieces).isEqualTo(12);
        assertThat(blackPieces).isEqualTo(12);
    }

    @Test
    void shouldInitializeWithCorrectPiecePositions() {
        boolean hasWhiteAt07 = GamePanel.pieces.stream()
                .anyMatch(p -> p.color == GamePanel.WHITE && p.col == 0 && p.row == 7);
        boolean hasWhiteAt25 = GamePanel.pieces.stream()
                .anyMatch(p -> p.color == GamePanel.WHITE && p.col == 2 && p.row == 5);

        assertTrue(hasWhiteAt07);
        assertTrue(hasWhiteAt25);

        boolean hasBlackAt10 = GamePanel.pieces.stream()
                .anyMatch(p -> p.color == GamePanel.BLACK && p.col == 1 && p.row == 0);
        boolean hasBlackAt32 = GamePanel.pieces.stream()
                .anyMatch(p -> p.color == GamePanel.BLACK && p.col == 3 && p.row == 2);

        assertTrue(hasBlackAt10);
        assertTrue(hasBlackAt32);
    }

    @Test
    void shouldDetectWinConditionForWhite() {
        GamePanel.pieces.removeIf(p -> p.color == GamePanel.BLACK);

        int winner = gamePanel.detectWin();
        assertThat(winner).isEqualTo(GamePanel.WHITE);
    }

    @Test
    void shouldDetectWinConditionForBlack() {
        GamePanel.pieces.removeIf(p -> p.color == GamePanel.WHITE);

        int winner = gamePanel.detectWin();
        assertThat(winner).isEqualTo(GamePanel.BLACK);
    }

    @Test
    void shouldDetectNoWinnerWhenBothHavePieces() {
        int winner = gamePanel.detectWin();
        assertThat(winner).isEqualTo(-1);
    }

    @Test
    void shouldStartWithWhitePlayer() {
        assertThat(gamePanel.currentColor).isEqualTo(GamePanel.WHITE);
    }

    @Test
    void shouldInitializeAllPiecesAsPawns() {
        boolean allArePawns = GamePanel.pieces.stream()
                .allMatch(p -> p instanceof Pawn);

        assertTrue(allArePawns);
    }

    @Test
    void shouldCheckDrawConditionWithOnlyQueens() {
        GamePanel.simPieces.clear();
        GamePanel.simPieces.add(new Queen(GamePanel.WHITE, 0, 0));
        GamePanel.simPieces.add(new Queen(GamePanel.BLACK, 7, 7));

        long whitePieces = GamePanel.simPieces.stream()
                .filter(p -> p.color == GamePanel.WHITE)
                .count();
        long blackPieces = GamePanel.simPieces.stream()
                .filter(p -> p.color == GamePanel.BLACK)
                .count();

        assertThat(whitePieces).isEqualTo(1);
        assertThat(blackPieces).isEqualTo(1);
    }

    @Test
    void shouldNotDrawWithMixedPieces() {
        GamePanel.simPieces.clear();
        GamePanel.simPieces.add(new Queen(GamePanel.WHITE, 0, 0));
        GamePanel.simPieces.add(new Pawn(GamePanel.BLACK, 7, 7));

        long whitePieces = GamePanel.simPieces.stream()
                .filter(p -> p.color == GamePanel.WHITE)
                .count();
        long blackPieces = GamePanel.simPieces.stream()
                .filter(p -> p.color == GamePanel.BLACK)
                .count();

        assertThat(whitePieces).isEqualTo(1);
        assertThat(blackPieces).isEqualTo(1);
    }

    @Test
    void shouldHaveLegalMovesInStartingPosition() {
        gamePanel.currentColor = GamePanel.WHITE;

        boolean hasWhiteMoves = GamePanel.simPieces.stream()
                .filter(p -> p.color == GamePanel.WHITE)
                .anyMatch(p -> {
                    for(int row = 0; row < 8; row++) {
                        for(int col = 0; col < 8; col++) {
                            if(p.canMove(col, row)) {
                                return true;
                            }
                        }
                    }
                    return false;
                });

        assertTrue(hasWhiteMoves);
    }

    @Test
    void shouldSwitchPlayerAfterValidMove() {
        int initialColor = gamePanel.currentColor;

        if(gamePanel.currentColor == GamePanel.WHITE) {
            gamePanel.currentColor = GamePanel.BLACK;
        } else {
            gamePanel.currentColor = GamePanel.WHITE;
        }

        assertThat(gamePanel.currentColor).isNotEqualTo(initialColor);
    }

    @Test
    void shouldInitializeGameStateCorrectly() {
        assertThat(gamePanel.currentColor).isEqualTo(GamePanel.WHITE);
        assertThat(GamePanel.activePiece).isNull();
        assertThat(GamePanel.mustCapture).isFalse();
        assertThat(GamePanel.pieces.size()).isEqualTo(24);
        assertThat(GamePanel.simPieces.size()).isEqualTo(24);
    }


}