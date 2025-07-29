package piece;

import main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {
    @BeforeEach
    void setUp() {
        GamePanel.simPieces.clear();
        GamePanel.mustCapture = false;
    }

    @Test
    void shouldMoveInAllDiagonalDirections() {
        Queen queen = new Queen(GamePanel.WHITE, 4, 4);
        GamePanel.simPieces.add(queen);

        assertTrue(queen.canMove(6, 6));
        assertTrue(queen.canMove(2, 2));
        assertTrue(queen.canMove(6, 2));
        assertTrue(queen.canMove(2, 6));
        assertTrue(queen.canMove(7, 7));
        assertTrue(queen.canMove(0, 0));
    }

    @Test
    void shouldNotMoveNonDiagonally() {
        Queen queen = new Queen(GamePanel.WHITE, 4, 4);
        GamePanel.simPieces.add(queen);

        assertFalse(queen.canMove(4, 6));
        assertFalse(queen.canMove(6, 4));
        assertFalse(queen.canMove(4, 2));
        assertFalse(queen.canMove(2, 4));
        assertFalse(queen.canMove(5, 6));
    }

    @Test
    void shouldCaptureOpponentPiece() {
        Queen whiteQueen = new Queen(GamePanel.WHITE, 2, 2);
        Pawn blackPawn = new Pawn(GamePanel.BLACK, 4, 4);

        GamePanel.simPieces.add(whiteQueen);
        GamePanel.simPieces.add(blackPawn);

        assertTrue(whiteQueen.canMove(5, 5));
        assertThat(whiteQueen.colidingPiece).isEqualTo(blackPawn);
    }

    @Test
    void shouldNotJumpOverOwnPiece() {
        Queen whiteQueen = new Queen(GamePanel.WHITE, 2, 2);
        Pawn whitePawn = new Pawn(GamePanel.WHITE, 4, 4);

        GamePanel.simPieces.add(whiteQueen);
        GamePanel.simPieces.add(whitePawn);

        assertFalse(whiteQueen.canMove(5, 5));
    }

    @Test
    void shouldNotJumpOverMultiplePieces() {
        Queen whiteQueen = new Queen(GamePanel.WHITE, 1, 1);
        Pawn blackPawn1 = new Pawn(GamePanel.BLACK, 3, 3);
        Pawn blackPawn2 = new Pawn(GamePanel.BLACK, 5, 5);

        GamePanel.simPieces.add(whiteQueen);
        GamePanel.simPieces.add(blackPawn1);
        GamePanel.simPieces.add(blackPawn2);

        assertFalse(whiteQueen.canMove(7, 7));
    }

    @Test
    void shouldDetectForcedCaptures() {
        Queen whiteQueen = new Queen(GamePanel.WHITE, 2, 2);
        Pawn blackPawn = new Pawn(GamePanel.BLACK, 4, 4);

        GamePanel.simPieces.add(whiteQueen);
        GamePanel.simPieces.add(blackPawn);

        assertTrue(whiteQueen.hasForcedMove());
    }

    @Test
    void shouldNotHaveForcedMoveWhenNoCapturePossible() {
        Queen whiteQueen = new Queen(GamePanel.WHITE, 4, 4);
        GamePanel.simPieces.add(whiteQueen);

        assertFalse(whiteQueen.hasForcedMove());
    }

    @Test
    void shouldNotMoveToSamePosition() {
        Queen queen = new Queen(GamePanel.WHITE, 3, 3);
        GamePanel.simPieces.add(queen);

        assertFalse(queen.canMove(3, 3));
    }
}