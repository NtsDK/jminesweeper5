package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MineSweeperGame {

  private Map<CellCoords,Cell> gameField = new HashMap<CellCoords, Cell>(); 
  private final int xSize = 20, ySize = 10;
  private boolean isGameEnded = false;
  private static Map<CellState, String> cellState2String = new HashMap<CellState, String>();
  private int minesNum;
  
  public int getXSize() {
    return xSize;
  }
  
  public int getYSize() {
    return ySize;
  }
  
  static {
    cellState2String.put(CellState.OPEN, "O");
    cellState2String.put(CellState.CLOSE, "C");
    cellState2String.put(CellState.FLAG, "F");
  }
  
  public MineSweeperGame() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        gameField.put(new CellCoords(x, y), new Cell(new CellCoords(x, y)));
      }
    }
  }
  
  public void setMines(int minesNumber) {
    minesNum = minesNumber;
    Random random = new Random();
    while(minesNumber>0) {
      int x = random.nextInt(xSize);
      int y = random.nextInt(ySize);
      Cell cell = gameField.get(new CellCoords(x, y));
      if(cell.getCellType()!=CellType.MINE) {
        cell.setMine();
        minesNumber--;
      }
    }
    countCellValues();
  }

  public void countCellValues() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        Cell cell = gameField.get(new CellCoords(x, y));
        if(cell.getCellType()!=CellType.MINE) {
          try {
            ArrayList<CellCoords> neighboursCoords = getNeighboursCoords(x, y);
            int neighbourMineNumber = 0;
            for(CellCoords cellCoords:neighboursCoords) {
              if(gameField.get(cellCoords).getCellType()==CellType.MINE ) {
                neighbourMineNumber++;
              }
            }
            cell.setCellValue(neighbourMineNumber);
            ArrayList<Cell> neighboursList = new ArrayList<Cell>();
            for(CellCoords cellCoords:neighboursCoords) {
              neighboursList.add(gameField.get(cellCoords));
            }
            cell.setNeighbourList(neighboursList);
          } catch (OutOfFieldException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
  
  public class OutOfFieldException extends Exception {
    
  }
  
  public ArrayList<CellCoords> getNeighboursCoords(int x, int y) throws OutOfFieldException {
    ArrayList<CellCoords> neighboursCoords = new ArrayList<CellCoords>();
    if(x<0 || y<0 || x>=xSize || y>=ySize) {
      throw new OutOfFieldException();
    }
    if(x>0       && y>0      ) neighboursCoords.add(new CellCoords(x-1, y-1));
    if(x>0                   ) neighboursCoords.add(new CellCoords(x-1,  y ));
    if(x>0       && y<ySize-1) neighboursCoords.add(new CellCoords(x-1, y+1));
    if(             y<ySize-1) neighboursCoords.add(new CellCoords( x , y+1));
    if(x<xSize-1 && y<ySize-1) neighboursCoords.add(new CellCoords(x+1, y+1));
    if(x<xSize-1             ) neighboursCoords.add(new CellCoords(x+1,  y ));
    if(x<xSize-1 && y>0      ) neighboursCoords.add(new CellCoords(x+1, y-1));
    if(             y>0      ) neighboursCoords.add(new CellCoords( x , y-1));
    
    return neighboursCoords;
  }

  public Cell getCell(int x, int y) {
    return getCell(new CellCoords(x, y));
  }
  
  private Cell getCell(CellCoords cellCoords) {
    return gameField.get(cellCoords);
  }
  
//  public int openCell(int x, int y) {
//    return 8;
//  }
//  
//  public int markCell(int x, int y) {
//    return 6;
//  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
  }
  
  public boolean testWinCondition() {
    int openCellsCounter = 0;
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        if(this.getCell(x, y).getCellState()==CellState.OPEN) {
          openCellsCounter++;
        }
      }
    }
    if(openCellsCounter+minesNum==xSize*ySize) {
      isGameEnded = true;
      return true;
    } else {
      return false;
    }
  }

  public void printField() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        CellState cellState = this.getCell(new CellCoords(x, y)).getCellState();
        System.out.print(cellState2String.get(cellState));
        if(x%5 == 4 ) {
          System.out.print(" ");
        }
      }
      if(y%5 == 4 ) {
        System.out.println();
      }
      System.out.println();
    }
  }
  
  public void printGameField() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        CellState cellState = this.getCell(x, y).getCellState();
        if(cellState==CellState.OPEN) {
          System.out.print(this.getCell(x, y).getCellValue());
        } else {
          System.out.print(cellState2String.get(cellState));
        }
        
        if(x%5 == 4 ) {
          System.out.print(" ");
        }
      }
      if(y%5 == 4 ) {
        System.out.println();
      }
      System.out.println();
    }
  }
  
  public void printFieldContents() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        int cellValue = this.getCell(new CellCoords(x, y)).getCellValue();
        System.out.print(cellValue);
        if(x%5 == 4 ) {
          System.out.print(" ");
        }
      }
      if(y%5 == 4 ) {
        System.out.println();
      }
      System.out.println();
    }
  }
  


  public boolean isGameEnded() {
    // TODO Auto-generated method stub
    return isGameEnded;
  }


  public void makeMove(CellCoords cellCoords, GameEventType eventType) {
    // TODO Auto-generated method stub
    //isGameEnded = true;
    if(isGameEnded) {
      return;
    }
    Cell currentCell = this.gameField.get(cellCoords);
    isGameEnded = currentCell.makeMove(eventType);
  }
  
}
