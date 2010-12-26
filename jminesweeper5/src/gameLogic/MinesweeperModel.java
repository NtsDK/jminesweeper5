package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 * This class represents minesweeper model game. It provides interface for working with game field.
 * Information:
 * - max and min restrictions for field size and mines number
 * - field info (xSize, ySize, minesNumber)
 * - game state flags (isGameEnded, isGamerWin)
 * - game options info (autoFlagging, autoOpening)
 * Logic:
 * - getters and setters
 * - field construction methods (getNeighboursCoords, makeField, countCellValues)
 * - reset method (resetGame)
 * - actions and logic (makeMove, testWinConditions)
 * - getting cell representation info (printCellInfo - it is placed here because only MinesweeperModel contains information about game state flags)
 * @author tima
 *
 */
public class MinesweeperModel{
  
  public static final int MAX_FIELD_SIZE = 50;
  public static final int MIN_FIELD_SIZE = 8;
  public static final int MAX_MINES_NUMBER = 2401;
  public static final int MIN_MINES_NUMBER = 10;

  private Map<FieldPoint,Cell> gameField = new HashMap<FieldPoint, Cell>(); 
  private int xSize, ySize;
  private boolean isGameEnded;
  private boolean isGamerWin;
  private boolean autoFlagging;
  private boolean autoOpening;
  private int minesNumber;
  
  public MinesweeperModel(int xSize, int ySize) {
    this(xSize, ySize, 10);
  }
  
  public MinesweeperModel(int xSize, int ySize, int minesNumber) {
    setFieldSize(xSize, ySize);
    setMinesNumber(minesNumber);
    this.isGameEnded = false;
    this.isGamerWin = false;
    makeField();
    setAutoFlagging(true);
    setAutoOpening(true);
  }
  
  public int getXSize() {
    return xSize;
  }
  
  public int getYSize() {
    return ySize;
  }
  
  /** Mines restrictions: min number - 10 mines, max number - (xSize-1)*(ySize-1) mines. 
   * @param minesNumber
   */
  public void setMinesNumber(int minesNumber) {
    if(minesNumber<MIN_MINES_NUMBER) {
      minesNumber = MIN_MINES_NUMBER;
    }
    
    if(minesNumber>(xSize-1)*(ySize-1)){
      minesNumber = (xSize-1)*(ySize-1);
    }
    
    this.minesNumber = minesNumber;
  }
  
  /** Field restrictions: min size - 8 cells, max size - 50 cells. 
   * @param xSize
   * @param ySize
   */
  public void setFieldSize(int xSize,int ySize){
    if(xSize<MIN_FIELD_SIZE) {
      xSize = MIN_FIELD_SIZE;
    }
    if(ySize<MIN_FIELD_SIZE) {
      ySize = MIN_FIELD_SIZE;
    }
    if(xSize>MAX_FIELD_SIZE) {
      xSize = MAX_FIELD_SIZE;
    }
    if(ySize>MAX_FIELD_SIZE) {
      ySize = MAX_FIELD_SIZE;
    }
    
    this.xSize = xSize;
    this.ySize = ySize;
  }
  
  public void resetGame() {
    isGameEnded = false;
    isGamerWin = false;
    gameField.clear();
    makeField();
    
    int localMinesNumber = minesNumber;
    Random random = new Random();
    while(localMinesNumber>0) {
      int x = random.nextInt(xSize);
      int y = random.nextInt(ySize);
      Cell cell = gameField.get(new FieldPoint(x, y));
      if(!cell.isMine()) {
        cell.setMine();
        localMinesNumber--;
      }
    }
    countCellValues();
  }

  private void makeField() {
    for(int y=0; y<this.ySize; ++y) {
      for(int x=0; x<this.xSize; ++x) {
        gameField.put(new FieldPoint(x, y), new Cell(x, y));
      }
    }
  }

  private void countCellValues() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        Cell cell = gameField.get(new FieldPoint(x, y));
        if(!cell.isMine()) {
          try {
            ArrayList<FieldPoint> neighboursCoords = getNeighboursCoords(x, y);
            ArrayList<Cell> neighboursList = new ArrayList<Cell>();
            for(FieldPoint cellCoords:neighboursCoords) {
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
  
  public ArrayList<FieldPoint> getNeighboursCoords(int x, int y) throws OutOfFieldException {
    ArrayList<FieldPoint> neighboursCoords = new ArrayList<FieldPoint>();
    if(x<0 || y<0 || x>=xSize || y>=ySize) {
      throw new OutOfFieldException();
    }
    if(x>0       && y>0      ) neighboursCoords.add(new FieldPoint(x-1, y-1));
    if(x>0                   ) neighboursCoords.add(new FieldPoint(x-1,  y ));
    if(x>0       && y<ySize-1) neighboursCoords.add(new FieldPoint(x-1, y+1));
    if(             y<ySize-1) neighboursCoords.add(new FieldPoint( x , y+1));
    if(x<xSize-1 && y<ySize-1) neighboursCoords.add(new FieldPoint(x+1, y+1));
    if(x<xSize-1             ) neighboursCoords.add(new FieldPoint(x+1,  y ));
    if(x<xSize-1 && y>0      ) neighboursCoords.add(new FieldPoint(x+1, y-1));
    if(             y>0      ) neighboursCoords.add(new FieldPoint( x , y-1));
    
    return neighboursCoords;
  }

  
  public Cell getCell(int x, int y) {
    return getCell(new FieldPoint(x, y));
  }
  
  private Cell getCell(FieldPoint cellCoords) {
    return gameField.get(cellCoords);
  }
  
  private void testWinCondition() {
    int openCellsCounter = 0;
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        if(this.getCell(new FieldPoint(x, y)).isOpen()) {
          openCellsCounter++;
        }
      }
    }
    if(openCellsCounter+minesNumber==xSize*ySize) {
      isGameEnded = true;
      isGamerWin = true;
    }
  }

  public boolean isGameEnded() {
    return isGameEnded;
  }


  public void makeMove(FieldPoint cellCoords, GameEventType eventType) {
    if(isGameEnded) {
      return;
    }
    Cell currentCell = this.gameField.get(cellCoords);
    isGameEnded = currentCell.makeMove(eventType,this);
    if(!isGameEnded) {
      testWinCondition();
    }
  }

  public void setAutoFlagging(boolean autoFlagging) {
    this.autoFlagging = autoFlagging;
    Cell.EventAction.setAutoFlaggingMode(autoFlagging);
  }

  public void setAutoOpening(boolean autoOpening) {
    this.autoOpening = autoOpening;
    Cell.EventAction.setAutoOpeningMode(autoOpening);
  }

  public boolean isAutoFlaggingEnabled() {
    return this.autoFlagging;
  }

  public boolean isAutoOpeningEnabled() {
    return this.autoOpening;
  }
  
  public class OutOfFieldException extends Exception {
    private static final long serialVersionUID = 5563721290754909080L;
  }

  public int getMinesNumber() {
    return minesNumber;
  }
  
  public boolean isGamerWin(){
    return isGamerWin;
  }
  
  public String printCellInfo(int y, int x) {
    Cell cell = this.getCell(new FieldPoint(x, y));
    if (!isGameEnded) {
      if (cell.isOpen()) {
        return ((Integer) cell.getCellValue()).toString();
      } else {
        return cell.getCellStateStringRepresentation();
      }
    } else if (!isGamerWin) {
      if (cell.isOpen() && cell.isMine()) {
        return "EM";
      } else if (cell.isFlag() && !cell.isMine()) {
        return "BF";
      } else if (cell.isClose() && cell.isMine()) {
        return "M";
      } else if (cell.isOpen()) {
        return ((Integer) cell.getCellValue()).toString();
      } else {
        return cell.getCellStateStringRepresentation();
      }
    } else {
      if (cell.isMine()) {
        return "F";
      } else if (cell.isOpen()) {
        return ((Integer) cell.getCellValue()).toString();
      } else {
        return cell.getCellStateStringRepresentation();
      }
    }
  }
  /** Used only for testing. This is a modified reset model method. */
  public void makeTestingModel(){
    isGameEnded = false;
    isGamerWin = false;
    gameField.clear();
    makeField();
    
    int xSize = getXSize();
    int ySize = getYSize();
    getCell(0, 0).setMine();
    getCell(0, 1).setMine();
    getCell(3, 0).setMine();
    getCell(xSize-1, 0).setMine();
    getCell(xSize-2, 0).setMine();
    getCell(xSize-3, 0).setMine();
    getCell(xSize-4, 0).setMine();
    getCell(xSize-5, 0).setMine();
    getCell(0, ySize-1).setMine();
    getCell(xSize-1, ySize-1).setMine();
    
    countCellValues();
  }

}
