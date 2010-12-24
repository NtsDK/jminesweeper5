package tests;

import static org.junit.Assert.assertEquals;
import gameConsole.ConsoleUtilities;
import gameLogic.Cell;
import gameLogic.FieldPoint;
import gameLogic.GameEventType;
import gameLogic.MinesweeperModel;
import gameLogic.MinesweeperModel.OutOfFieldException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class MinesweeperModelTest{

  private MinesweeperModel mineSweeperGame;

  @Before
  public void initialize(){
    mineSweeperGame = new MinesweeperModel(20, 10,10);
  }
  
  @Test
  public void testGetCell() {
    assertEquals(mineSweeperGame.getCell(0, 0),new Cell(new FieldPoint(0, 0)));
  }
 
  @Test
  public void testGetNeighboursCoords() throws OutOfFieldException {
    int xSize = mineSweeperGame.getXSize();
    int ySize = mineSweeperGame.getYSize();
    
    ArrayList<FieldPoint> neighboursCoords = mineSweeperGame.getNeighboursCoords(0,0);
    ArrayList<FieldPoint> neighboursCoordsCorrectValue = new ArrayList<FieldPoint>();
    neighboursCoordsCorrectValue.add(new FieldPoint(0, 1));
    neighboursCoordsCorrectValue.add(new FieldPoint(1, 1));
    neighboursCoordsCorrectValue.add(new FieldPoint(1, 0));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(xSize-1,0);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-2, 0));
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-2, 1));
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-1, 1));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(xSize-1,ySize-1);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-2, ySize-2));
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-2, ySize-1));
    neighboursCoordsCorrectValue.add(new FieldPoint(xSize-1, ySize-2));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(0,ySize-1);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new FieldPoint(1, ySize-1));
    neighboursCoordsCorrectValue.add(new FieldPoint(1, ySize-2));
    neighboursCoordsCorrectValue.add(new FieldPoint(0, ySize-2));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    int centerX = xSize/2;
    int centerY = ySize/2;
    neighboursCoords = mineSweeperGame.getNeighboursCoords(centerX,centerY);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX-1, centerY-1));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX-1, centerY));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX-1, centerY+1));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX, centerY+1));
    
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX+1, centerY+1));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX+1, centerY));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX+1, centerY-1));
    neighboursCoordsCorrectValue.add(new FieldPoint(centerX, centerY-1));

    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
  }
  
  @Test
  public void testGameLogic() {
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    mineSweeperGame.getCell(1, 0).openCell();
    mineSweeperGame.getCell(1, 1).flagCell();
    
    assertEquals(mineSweeperGame.getCell(0, 0).isClose(), true);
    assertEquals(mineSweeperGame.getCell(1, 0).isOpen(), true);
    assertEquals(mineSweeperGame.getCell(1, 1).isFlag(), true);
    
    assertEquals(mineSweeperGame.getCell(0, 0).getCellValue(), -1);
    assertEquals(mineSweeperGame.getCell(1, 0).getCellValue(), 2);
    assertEquals(mineSweeperGame.getCell(2, 0).getCellValue(), 0);
  }
  
  @Test
  public void testRecursiveOpening(){
    mineSweeperGame.getCell(1, 0).openCell();
    mineSweeperGame.getCell(1, 1).flagCell();
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new FieldPoint(2, 2), GameEventType.LEFT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(3, 3).isOpen(), true);
    
    ConsoleUtilities.printField(mineSweeperGame);   
  }
  
  @Test
  public void testCellOpening(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new FieldPoint(1, 1), GameEventType.LEFT_BUTTON_CLICK);
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(1, 1).isOpen(), true);
    assertEquals(mineSweeperGame.getCell(2, 2).isClose(), true);
    
    mineSweeperGame.makeMove(new FieldPoint(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 1).isOpen(), true);
    assertEquals(mineSweeperGame.getCell(2, 2).isClose(), true);
  }
  
  @Test
  public void testCellFlagging(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new FieldPoint(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(1, 1).isFlag(), true);
    assertEquals(mineSweeperGame.getCell(2, 2).isClose(), true);
    
    mineSweeperGame.makeMove(new FieldPoint(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 1).isClose(), true);
    assertEquals(mineSweeperGame.getCell(2, 2).isClose(), true);
  }
  
  @Test
  public void testCellAutoFlagging(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoFlagging(true);
    mineSweeperGame.makeMove(new FieldPoint(3, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.RIGHT_BUTTON_CLICK);
    
    
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(0, 0).isFlag(), true);
    assertEquals(mineSweeperGame.getCell(0, 1).isFlag(), true);
  }
  
  @Test
  public void testCellAutoFlaggingOff(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoFlagging(false);
    mineSweeperGame.makeMove(new FieldPoint(3, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.RIGHT_BUTTON_CLICK);
    
    
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(0, 0).isClose(), true);
    assertEquals(mineSweeperGame.getCell(0, 1).isClose(), true);
  }
  
  @Test
  public void testCellAutoOpening(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoOpening(true);
    mineSweeperGame.makeMove(new FieldPoint(0, 0), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(0, 1), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    
    
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(2, 0).isOpen(), true);
  }
  
  @Test
  public void testCellAutoOpeningOff(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoOpening(false);
    mineSweeperGame.makeMove(new FieldPoint(0, 0), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(0, 1), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    
    
    ConsoleUtilities.printField(mineSweeperGame);
    assertEquals(mineSweeperGame.getCell(2, 0).isClose(), true);
  }

  public MinesweeperModel getOverridedMineSweeperGame() {
    MinesweeperModel mineSweeperGame = new MinesweeperModel(20,10,5) {
      @Override
      public void resetGame() {
        makeField();
        int xSize = getXSize();
        int ySize = getYSize();
        getCell(0, 0).setMine();
        getCell(0, 1).setMine();
        getCell(xSize-1, 0).setMine();
        getCell(0, ySize-1).setMine();
        getCell(xSize-1, ySize-1).setMine();
      }
    };
    mineSweeperGame.resetGame();
    mineSweeperGame.countCellValues();
   
    return mineSweeperGame;
  }
  
  @Test
  public void testResetGame(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    assertEquals(mineSweeperGame.getCell(1, 0).isClose(), true);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 0).isOpen(), true);
    
    
    mineSweeperGame.resetGame();
    assertEquals(mineSweeperGame.getCell(1, 0).isClose(), true);
    mineSweeperGame.makeMove(new FieldPoint(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 0).isOpen(), true);
  }
  
  @Test
  public void testSetMinesNumberAntSetFieldSize(){
    MinesweeperModel model = new MinesweeperModel(5, 5, -25);
    
    assertEquals(model.getXSize(), 8);
    assertEquals(model.getYSize(), 8);
    assertEquals(model.getMinesNumber(), 10);
    
    model.setMinesNumber(100);
    assertEquals(model.getMinesNumber(), 49); // (8-1)*(8-1)
    model.setFieldSize(100, 100);
    assertEquals(model.getXSize(), 50);
    assertEquals(model.getYSize(), 50);
  }
  
}
