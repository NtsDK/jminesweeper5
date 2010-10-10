package tests;

import org.junit.Assert;
import org.junit.Test;

import gameLogic.Cell;
import gameLogic.CellCoords;


public class CellTest {
  @Test
  public void testCellTest() {
    Cell cell = new Cell(new CellCoords(0, 0));
    
    Assert.assertTrue(cell.isGameEnded()==false);
    cell.openCell();
    Assert.assertTrue(cell.isGameEnded()==false);
    cell.openCell();
    cell.setMine();
    Assert.assertTrue(cell.isGameEnded()==false);
    cell.openCell();
    Assert.assertTrue(cell.isGameEnded()==true);
  }
}
