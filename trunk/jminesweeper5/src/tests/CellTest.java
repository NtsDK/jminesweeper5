package tests;

import java.util.ArrayList;

import gameLogic.Cell;
import gameLogic.CellCoords;
import gameLogic.CellState;
import gameLogic.GameEventType;

import org.junit.Assert;
import org.junit.Test;


public class CellTest {
  
//  isGameEnded() changed visibility to private
//  @Test
//  public void testIsGameEnded() {
//    Cell cell = new Cell(0, 0);
//    
//    Assert.assertTrue(cell.isGameEnded()==false); // no mine, cell close
//    cell.setMine();
//    Assert.assertTrue(cell.isGameEnded()==false); // has mine, cell close
//    cell.openCell();
//    Assert.assertTrue(cell.isGameEnded()==true); // has mine, cell open
//    
//    cell = new Cell(0, 0);
//    cell.openCell();
//    Assert.assertTrue(cell.isGameEnded()==false); // no mine, cell open
//  }
  
  @Test
  public void testIsGameEndedDifficultCase() { // if there was mine with multiopening operation
    Cell cell = new Cell(0, 0);
    ArrayList<Cell> neighboursList = new ArrayList<Cell>();
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(0, 0));
    cell.setNeighbourList(neighboursList);
    neighboursList.get(0).setMine();
    neighboursList.get(1).flagCell();
    cell.setCellValue(1);
    cell.openCell();
    
    //cell.makeMove(GameEventType.LEFT_BUTTON_CLICK)
    Assert.assertTrue(cell.makeMove(GameEventType.LEFT_BUTTON_CLICK)==true);
    
  }
  
  @Test
  public void testCountOpenNeighbours() {
    Cell cell = new Cell(new CellCoords(0, 0));

    ArrayList<Cell> neighboursList = new ArrayList<Cell>();
    cell.setNeighbourList(neighboursList);
    
    neighboursList.add(new Cell(0, 1));
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(1, 2));
    neighboursList.add(new Cell(1, 3));
    Assert.assertTrue(cell.countOpenNeighbours()==0);
    neighboursList.get(2).openCell();
    neighboursList.get(3).openCell();

    Assert.assertTrue(cell.countOpenNeighbours()==2);
    neighboursList.get(0).openCell();
    neighboursList.get(1).openCell();   
    neighboursList.get(4).openCell();
    Assert.assertTrue(cell.countOpenNeighbours()==5);
  }
  
  @Test
  public void testCountNeighboursFlags() {
    Cell cell = new Cell(new CellCoords(0, 0));

    ArrayList<Cell> neighboursList = new ArrayList<Cell>();
    cell.setNeighbourList(neighboursList);
    
    neighboursList.add(new Cell(0, 1));
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(1, 2));
    neighboursList.add(new Cell(1, 3));
    Assert.assertTrue(cell.countNeighboursFlags()==0);
    neighboursList.get(2).flagCell();
    neighboursList.get(3).flagCell();

    Assert.assertTrue(cell.countNeighboursFlags()==2);
    neighboursList.get(0).flagCell();
    neighboursList.get(1).flagCell();
    neighboursList.get(4).flagCell();
    Assert.assertTrue(cell.countNeighboursFlags()==5);
  }
  
  @Test
  public void testOpenCell() {
    Cell cell = new Cell(0, 0);
    ArrayList<Cell> neighboursList = new ArrayList<Cell>();
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(0, 0));
    cell.setNeighbourList(neighboursList );
    cell.setCellValue(2);
    cell.openCell();
    for(Cell neighbour:neighboursList) {
      Assert.assertTrue(neighbour.getCellState()==CellState.CLOSE);
    }
    
    cell = new Cell(0, 0);
    neighboursList = new ArrayList<Cell>();
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(0, 0));
    cell.setNeighbourList(neighboursList );
    cell.setCellValue(0);
    cell.openCell();
    for(Cell neighbour:neighboursList) {
      Assert.assertTrue(neighbour.getCellState()==CellState.OPEN);
    }
  }
  
}
