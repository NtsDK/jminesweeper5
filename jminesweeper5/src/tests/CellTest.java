package tests;

import gameLogic.Cell;
import gameLogic.FieldPoint;
import gameLogic.GameEventType;
import gameLogic.MinesweeperModel;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


public class CellTest {
  
  @Test
  public void testIsGameEndedDifficultCase() { // if there was mine with multiopening operation
    Cell cell = new Cell(0, 0);
    ArrayList<Cell> neighboursList = new ArrayList<Cell>();
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(0, 0));
    neighboursList.get(0).setMine();
    neighboursList.get(1).flagCell();
    cell.setNeighbourList(neighboursList);
    
    cell.openCell();
    
    MinesweeperModel model = new MinesweeperModel(0,0,0);
    model.setAutoOpening(true);
    Assert.assertTrue(cell.makeMove(GameEventType.LEFT_BUTTON_CLICK, model)==true);
    
  }
  
  @Test
  public void testCountOpenNeighbours() {
    Cell cell = new Cell(new FieldPoint(0, 0));

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
    Cell cell = new Cell(new FieldPoint(0, 0));

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
    Cell tempCell = new Cell(1, 1);
    tempCell.setMine();
    neighboursList.add(tempCell);
    tempCell = new Cell(1, 0);
    tempCell.setMine();
    neighboursList.add(tempCell);
    neighboursList.add(new Cell(0, 0));
    cell.setNeighbourList(neighboursList );
    cell.openCell();
    for(Cell neighbour:neighboursList) {
      Assert.assertTrue(neighbour.isClose());
    }
    
    cell = new Cell(0, 0);
    neighboursList = new ArrayList<Cell>();
    neighboursList.add(new Cell(1, 1));
    neighboursList.add(new Cell(1, 0));
    neighboursList.add(new Cell(0, 0));
    cell.setNeighbourList(neighboursList );
    cell.openCell();
    for(Cell neighbour:neighboursList) {
      Assert.assertTrue(neighbour.isOpen());
    }
  }
  
  @Test
  public void testIsMine() {
    Cell cell = new Cell(0, 0);
    Assert.assertFalse(cell.isMine());
    cell.setMine();
    Assert.assertTrue(cell.isMine());
  }
  
}
