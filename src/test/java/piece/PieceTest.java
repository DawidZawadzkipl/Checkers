package piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PieceTest {
    @BeforeEach
    void setUp() {
        GamePanel.simPieces.clear();
    }

    @Test
    void shouldCreatePieceWithCorrectProperties() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 3, 5);

        assertThat(pawn.col).isEqualTo(3);
        assertThat(pawn.row).isEqualTo(5);
        assertThat(pawn.color).isEqualTo(GamePanel.WHITE);
        assertThat(pawn.preCol).isEqualTo(3);
        assertThat(pawn.preRow).isEqualTo(5);
    }

    @Test
    void shouldCalculatePositionCorrectly() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 2, 4);

        assertThat(pawn.getX(2)).isEqualTo(200);
        assertThat(pawn.getY(4)).isEqualTo(400);
        assertThat(pawn.getCol(200)).isEqualTo(2);
        assertThat(pawn.getRow(400)).isEqualTo(4);
    }

    @Test
    void shouldValidateBoundariesCorrectly() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 2, 7);

        assertTrue(pawn.isWithinBoard(0, 0));
        assertTrue(pawn.isWithinBoard(7, 7));
        assertFalse(pawn.isWithinBoard(-1, 0));
        assertFalse(pawn.isWithinBoard(0, -1));
        assertFalse(pawn.isWithinBoard(8, 0));
        assertFalse(pawn.isWithinBoard(0, 8));
    }

    @Test
    void shouldUpdatePositionCorrectly() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 0, 7);

        pawn.col = 1;
        pawn.row = 6;
        pawn.updatePosition();
        assertThat(pawn.x).isEqualTo(100);
        assertThat(pawn.y).isEqualTo(600);
        assertThat(pawn.preCol).isEqualTo(1);
        assertThat(pawn.preRow).isEqualTo(6);
    }

    @Test
    void shouldResetPositionCorrectly() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 2, 3);

        pawn.col = 1;
        pawn.row = 1;

        pawn.resetPosition();
        assertThat(pawn.col).isEqualTo(2);
        assertThat(pawn.row).isEqualTo(3);
        assertThat(pawn.x).isEqualTo(200);
        assertThat(pawn.y).isEqualTo(300);
    }


    @Test
    void shouldDetectCollisionCorrectly() {
        Pawn pawn = new Pawn(GamePanel.WHITE, 2, 3);
        Pawn pawn2 = new Pawn(GamePanel.WHITE, 1, 4);
        GamePanel.simPieces.add(pawn);
        GamePanel.simPieces.add(pawn2);

        assertThat(pawn2.detectCollision(2, 3)).isEqualTo(pawn);
    }

}