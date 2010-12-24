package tests;

import gameConsole.ConsoleUtilities;
import gameLogic.MinesweeperModel;

import org.junit.Before;
import org.junit.Test;

public class ConsoleUtilitiesTest {
  private MinesweeperModel mineSweeperGame;

  @Before
  public void initialize(){
    mineSweeperGame = new MinesweeperModel(20, 10,10);
  }
  
  @Test
  public void testPrintField() {
    mineSweeperGame.getCell(5, 5).flagCell();
    mineSweeperGame.getCell(4, 5).openCell();
    mineSweeperGame.getCell(8, 5).openCell();
    mineSweeperGame.getCell(8, 5).openCell();
    ConsoleUtilities.printField(mineSweeperGame);
  }
}
