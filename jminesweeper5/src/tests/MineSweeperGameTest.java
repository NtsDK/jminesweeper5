package tests;

import static org.junit.Assert.assertEquals;
import gameLogic.Cell;
import gameLogic.CellCoords;
import gameLogic.MineSweeperGame;

import org.junit.Test;


public class MineSweeperGameTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
  
  @Test
  public void testOpenCell() {
    MineSweeperGame mineSweeperGame = new MineSweeperGame();
    assertEquals(mineSweeperGame.openCell(0, 0),8);
  }
  
  @Test
  public void testGetCell() {
    MineSweeperGame mineSweeperGame = new MineSweeperGame();
    assertEquals(mineSweeperGame.getCell(new CellCoords(0, 0)),new Cell(new CellCoords(0, 0)));
  }
  
  @Test
  public void testMarkCell() {
    MineSweeperGame mineSweeperGame = new MineSweeperGame();
    assertEquals(mineSweeperGame.markCell(0, 0),6);
  }
  
  @Test
  public void testPrintField() {
    MineSweeperGame mineSweeperGame = new MineSweeperGame();
    mineSweeperGame.getCell(new CellCoords(5, 5)).markCell();
    mineSweeperGame.getCell(new CellCoords(4, 5)).openCell();
    mineSweeperGame.getCell(new CellCoords(8, 5)).openCell();
    mineSweeperGame.getCell(new CellCoords(8, 5)).openCell();
    mineSweeperGame.printField();
  }

}
