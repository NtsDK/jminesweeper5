package tests;

import static org.junit.Assert.assertEquals;
import gameLogic.Cell;
import gameLogic.CellCoords;
import gameLogic.CellState;
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
    mineSweeperGame = new MinesweeperModel(20, 10);
  }
  
//  @Test
//  public void testOpenCell() {
//    assertEquals(mineSweeperGame.openCell(0, 0),8);
//  }
  
  @Test
  public void testGetCell() {
    assertEquals(mineSweeperGame.getCell(0, 0),new Cell(new CellCoords(0, 0)));
  }
  
//  @Test
//  public void testMarkCell() {
//    assertEquals(mineSweeperGame.markCell(0, 0),6);
//  }
  
  @Test
  public void testPrintField() {
    mineSweeperGame.getCell(5, 5).flagCell();
    mineSweeperGame.getCell(4, 5).openCell();
    mineSweeperGame.getCell(8, 5).openCell();
    mineSweeperGame.getCell(8, 5).openCell();
    mineSweeperGame.printField();
  }
  
  @Test
  public void testPrintFieldContents() {
    mineSweeperGame.resetGame(10);
    mineSweeperGame.getCell(5, 5).setCellValue(3);
    mineSweeperGame.getCell(5, 6).setCellValue(5);
    mineSweeperGame.printFieldContents();
  }
 
  @Test
  public void testGetNeighboursCoords() throws OutOfFieldException {
    int xSize = mineSweeperGame.getXSize();
    int ySize = mineSweeperGame.getYSize();
    
    ArrayList<CellCoords> neighboursCoords = mineSweeperGame.getNeighboursCoords(0,0);
    ArrayList<CellCoords> neighboursCoordsCorrectValue = new ArrayList<CellCoords>();
    neighboursCoordsCorrectValue.add(new CellCoords(0, 1));
    neighboursCoordsCorrectValue.add(new CellCoords(1, 1));
    neighboursCoordsCorrectValue.add(new CellCoords(1, 0));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(xSize-1,0);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-2, 0));
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-2, 1));
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-1, 1));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(xSize-1,ySize-1);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-2, ySize-2));
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-2, ySize-1));
    neighboursCoordsCorrectValue.add(new CellCoords(xSize-1, ySize-2));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    neighboursCoords = mineSweeperGame.getNeighboursCoords(0,ySize-1);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new CellCoords(1, ySize-1));
    neighboursCoordsCorrectValue.add(new CellCoords(1, ySize-2));
    neighboursCoordsCorrectValue.add(new CellCoords(0, ySize-2));
    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
    
    int centerX = xSize/2;
    int centerY = ySize/2;
    neighboursCoords = mineSweeperGame.getNeighboursCoords(centerX,centerY);
    neighboursCoordsCorrectValue.clear();
    neighboursCoordsCorrectValue.add(new CellCoords(centerX-1, centerY-1));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX-1, centerY));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX-1, centerY+1));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX, centerY+1));
    
    neighboursCoordsCorrectValue.add(new CellCoords(centerX+1, centerY+1));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX+1, centerY));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX+1, centerY-1));
    neighboursCoordsCorrectValue.add(new CellCoords(centerX, centerY-1));

    assertEquals(neighboursCoordsCorrectValue, neighboursCoords);
  }
  
  @Test
  public void testGameLogic() {
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    mineSweeperGame.getCell(1, 0).openCell();
    mineSweeperGame.getCell(1, 1).flagCell();
    assertEquals(mineSweeperGame.getCell(0, 0).getCellState(), CellState.CLOSE);
    assertEquals(mineSweeperGame.getCell(1, 0).getCellState(), CellState.OPEN);
    assertEquals(mineSweeperGame.getCell(1, 1).getCellState(), CellState.FLAG);
    
    assertEquals(mineSweeperGame.getCell(0, 0).getCellValue(), 9);
    assertEquals(mineSweeperGame.getCell(1, 0).getCellValue(), 2);
    assertEquals(mineSweeperGame.getCell(2, 0).getCellValue(), 0);
  }
  
  @Test
  public void testRecursiveOpening(){
    mineSweeperGame.getCell(1, 0).openCell();
    mineSweeperGame.getCell(1, 1).flagCell();
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new CellCoords(2, 2), GameEventType.LEFT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(3, 3).getCellState(), CellState.OPEN);
    
    mineSweeperGame.printField();    
  }
  
  @Test
  public void testCellOpening(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new CellCoords(1, 1), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(1, 1).getCellState(), CellState.OPEN);
    assertEquals(mineSweeperGame.getCell(2, 2).getCellState(), CellState.CLOSE);
    
    mineSweeperGame.makeMove(new CellCoords(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 1).getCellState(), CellState.OPEN);
    assertEquals(mineSweeperGame.getCell(2, 2).getCellState(), CellState.CLOSE);
  }
  
  @Test
  public void testCellFlagging(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.makeMove(new CellCoords(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(1, 1).getCellState(), CellState.FLAG);
    assertEquals(mineSweeperGame.getCell(2, 2).getCellState(), CellState.CLOSE);
    
    mineSweeperGame.makeMove(new CellCoords(1, 1), GameEventType.RIGHT_BUTTON_CLICK);
    assertEquals(mineSweeperGame.getCell(1, 1).getCellState(), CellState.CLOSE);
    assertEquals(mineSweeperGame.getCell(2, 2).getCellState(), CellState.CLOSE);
  }
  
  @Test
  public void testCellAutoFlagging(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoFlagging(true);
    mineSweeperGame.makeMove(new CellCoords(3, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.RIGHT_BUTTON_CLICK);
    
    
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(0, 0).getCellState(), CellState.FLAG);
    assertEquals(mineSweeperGame.getCell(0, 1).getCellState(), CellState.FLAG);
  }
  
  @Test
  public void testCellAutoFlaggingOff(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoFlagging(false);
    mineSweeperGame.makeMove(new CellCoords(3, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.RIGHT_BUTTON_CLICK);
    
    
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(0, 0).getCellState(), CellState.CLOSE);
    assertEquals(mineSweeperGame.getCell(0, 1).getCellState(), CellState.CLOSE);
  }
  
  @Test
  public void testCellAutoOpening(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoOpening(true);
    mineSweeperGame.makeMove(new CellCoords(0, 0), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(0, 1), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    
    
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(2, 0).getCellState(), CellState.OPEN);
  }
  
  @Test
  public void testCellAutoOpeningOff(){
    MinesweeperModel mineSweeperGame = getOverridedMineSweeperGame();
    
    mineSweeperGame.setAutoOpening(false);
    mineSweeperGame.makeMove(new CellCoords(0, 0), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(0, 1), GameEventType.RIGHT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    mineSweeperGame.makeMove(new CellCoords(1, 0), GameEventType.LEFT_BUTTON_CLICK);
    
    
    mineSweeperGame.printField();
    assertEquals(mineSweeperGame.getCell(2, 0).getCellState(), CellState.CLOSE);
  }

  public MinesweeperModel getOverridedMineSweeperGame() {
    MinesweeperModel mineSweeperGame = new MinesweeperModel(20,10) {
      @Override
      public void resetGame(int minesNumber) {
        this.makeField();
        int xSize = getXSize();
        int ySize = getYSize();
        getCell(0, 0).setMine();
        getCell(0, 1).setMine();
        getCell(xSize-1, 0).setMine();
        getCell(0, ySize-1).setMine();
        getCell(xSize-1, ySize-1).setMine();
      }
    };
    mineSweeperGame.resetGame(5);
    mineSweeperGame.countCellValues();
   
    return mineSweeperGame;
  }
  
  
}
