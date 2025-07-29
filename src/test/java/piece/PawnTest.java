package piece;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PawnTest {

    @BeforeEach
    void setUp(){
        GamePanel.simPieces.clear();
        GamePanel.mustCapture = false;
    }

    @Test
    void shouldAllowValidForwardMoveForWhitePawn(){
        Pawn pawn = new Pawn(GamePanel.WHITE,2, 7 );
        GamePanel.simPieces.add(pawn);

        assertTrue(pawn.canMove(1, 6));
        assertTrue(pawn.canMove(3, 6));
    }

    @Test
    void shouldAllowValidForwardMoveForBlackPawn(){
        Pawn pawn = new Pawn(GamePanel.BLACK,5, 0 );
        GamePanel.simPieces.add(pawn);

        assertTrue(pawn.canMove(4,1));
        assertTrue(pawn.canMove(6,1));
    }

    @Test
    void shouldNotAllowBackwardMoveForWhitePawn(){
        Pawn pawn = new Pawn(GamePanel.WHITE,3, 6 );
        GamePanel.simPieces.add(pawn);

        assertFalse(pawn.canMove(2,7));
        assertFalse(pawn.canMove(4,7));
    }

    @Test
    void shouldNotAllowBackwardMoveForBlackPawn(){
        Pawn pawn = new Pawn(GamePanel.BLACK,4, 1 );
        GamePanel.simPieces.add(pawn);

        assertFalse(pawn.canMove(3,0));
        assertFalse(pawn.canMove(5,0));
    }

    @Test
    void shouldNotAllowNonDiagonalMoveForWhitePawn(){
        Pawn pawn = new Pawn(GamePanel.WHITE,3, 6 );
        GamePanel.simPieces.add(pawn);

        assertFalse(pawn.canMove(3,5));
        assertFalse(pawn.canMove(3,7));
        assertFalse(pawn.canMove(2,6));
        assertFalse(pawn.canMove(4,6));
    }

    @Test
    void shouldNotAllowNonDiagonalMoveForBlackPawn(){
        Pawn pawn = new Pawn(GamePanel.BLACK,4, 1 );
        GamePanel.simPieces.add(pawn);

        assertFalse(pawn.canMove(4,2));
        assertFalse(pawn.canMove(4,0));
        assertFalse(pawn.canMove(3,1));
        assertFalse(pawn.canMove(5,1));
    }

    @Test
    void shouldNotAllowMoveToCurrentSqure() {
        Pawn pawn = new Pawn(GamePanel.WHITE,3, 6 );
        GamePanel.simPieces.add(pawn);

        assertFalse(pawn.canMove(3, 6));
    }

    @Test
    void shouldNotAllowMoveToOccupiedSquare() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,0, 7 );
        GamePanel.simPieces.add(pawn1);
        Pawn pawn2 = new Pawn(GamePanel.WHITE,1, 6 );
        GamePanel.simPieces.add(pawn2);

        assertFalse(pawn1.canMove(1, 6));
    }

    @Test
    void canCaptureForawrdWhenPossible() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,1, 6 );
        GamePanel.simPieces.add(pawn1);
        Pawn pawn2 = new Pawn(GamePanel.BLACK,2, 5 );
        GamePanel.simPieces.add(pawn2);
        GamePanel.mustCapture = true;

        assertTrue(pawn1.canMove(3,4));
        assertThat(pawn1.colidingPiece).isEqualTo(pawn2);
    }
    @Test
    void shouldNotMoveWhenHasToCapture() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,1, 6 );
        GamePanel.simPieces.add(pawn1);
        Pawn pawn2 = new Pawn(GamePanel.BLACK,2, 5 );
        GamePanel.simPieces.add(pawn2);
        GamePanel.mustCapture = true;

        assertFalse(pawn1.canMove(0,5));
    }

    @Test
    void shouldNotMoveWhenOtherPieceMustCapture() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,1, 6 );
        GamePanel.simPieces.add(pawn1);
        GamePanel.mustCapture = true;

        assertFalse(pawn1.canMove(0,5));
    }

    @Test
    void shouldNotCaptureAllyPiece() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,3, 6 );
        GamePanel.simPieces.add(pawn1);
        Pawn pawn2 = new Pawn(GamePanel.WHITE,4, 5 );
        GamePanel.simPieces.add(pawn2);
        Pawn pawn3 = new Pawn(GamePanel.BLACK,2, 5 );
        GamePanel.simPieces.add(pawn3);
        GamePanel.mustCapture = true;

        assertFalse(pawn1.canMove(5,4));
    }

    @Test
    void canCaptureBackward() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,4, 5 );
        GamePanel.simPieces.add(pawn1);
        Pawn pawn2 = new Pawn(GamePanel.BLACK,3, 6 );
        GamePanel.simPieces.add(pawn2);
        GamePanel.mustCapture = true;

        assertTrue(pawn1.canMove(2,7));
    }

    @Test
    void promotesToQueenCorrectly() {
        Pawn pawn1 = new Pawn(GamePanel.WHITE,1, 0 );
        GamePanel.simPieces.add(pawn1);
        int pawnIndex = pawn1.getIndex();

        pawn1.updatePosition();

        assertThat(GamePanel.simPieces.get(pawnIndex)).isInstanceOf(Queen.class);
        assertThat(GamePanel.simPieces.get(pawnIndex).color).isEqualTo(GamePanel.WHITE);
    }

    @Test
    void shouldDetectForcedMoves() {
        Pawn whitePawn = new Pawn(GamePanel.WHITE, 1, 4);
        Pawn blackPawn = new Pawn(GamePanel.BLACK, 2, 3);
        Pawn whitePawn1 = new Pawn(GamePanel.WHITE, 5, 4);
        Pawn blackPawn1 = new Pawn(GamePanel.BLACK, 4, 3);
        Pawn alonePawn = new Pawn(GamePanel.WHITE, 4, 1);

        GamePanel.simPieces.add(whitePawn);
        GamePanel.simPieces.add(blackPawn);
        GamePanel.simPieces.add(whitePawn1);
        GamePanel.simPieces.add(blackPawn1);
        GamePanel.simPieces.add(alonePawn);

        assertTrue(whitePawn.hasForcedMove());
        assertTrue(blackPawn.hasForcedMove());
        assertTrue(whitePawn1.hasForcedMove());
        assertTrue(blackPawn1.hasForcedMove());
        assertFalse(alonePawn.hasForcedMove());
    }


}