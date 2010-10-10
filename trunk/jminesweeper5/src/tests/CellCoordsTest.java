package tests;

import static org.junit.Assert.assertEquals;
import gameLogic.CellCoords;
import gameLogic.MineSweeperGame;

import org.junit.Test;


public class CellCoordsTest {

  @Test
  public void testCellCoordsEquals() {
    CellCoords cell1 = new CellCoords(4,5);
    CellCoords cell2 = new CellCoords(4,5);
    assertEquals(cell1,cell2);
  }
}
