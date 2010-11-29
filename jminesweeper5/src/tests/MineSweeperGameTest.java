package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import junit.framework.Assert;

import gameLogic.Cell;
import gameLogic.CellCoords;
import gameLogic.MineSweeperGame;
import gameLogic.MineSweeperGame.OutOfFieldException;

import org.junit.Before;
import org.junit.Test;


public class MineSweeperGameTest{

  private MineSweeperGame mineSweeperGame;

  @Before
  public void initialize(){
    mineSweeperGame = new MineSweeperGame();
  }
  
  @Test
  public void testOpenCell() {
    assertEquals(mineSweeperGame.openCell(0, 0),8);
  }
  
  @Test
  public void testGetCell() {
    assertEquals(mineSweeperGame.getCell(new CellCoords(0, 0)),new Cell(new CellCoords(0, 0)));
  }
  
  @Test
  public void testMarkCell() {
    assertEquals(mineSweeperGame.markCell(0, 0),6);
  }
  
  @Test
  public void testPrintField() {
    mineSweeperGame.getCell(new CellCoords(5, 5)).flagCell();
    mineSweeperGame.getCell(new CellCoords(4, 5)).openCell();
    mineSweeperGame.getCell(new CellCoords(8, 5)).openCell();
    mineSweeperGame.getCell(new CellCoords(8, 5)).openCell();
    mineSweeperGame.printField();
  }
  
  @Test
  public void testPrintFieldContents() {
    mineSweeperGame.setMines(10);
    mineSweeperGame.getCell(new CellCoords(5, 5)).setCellValue(3);
    mineSweeperGame.getCell(new CellCoords(5, 6)).setCellValue(5);
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

}
