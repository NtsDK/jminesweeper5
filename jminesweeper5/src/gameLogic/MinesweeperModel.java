package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class MinesweeperModel extends AbstractTableModel{

  private static final long serialVersionUID = 3171163557305920461L;
  private Map<FieldPoint,Cell> gameField = new HashMap<FieldPoint, Cell>(); 
  private int xSize, ySize;
  private boolean isGameEnded;
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
    if(minesNumber<10) {
      minesNumber = 10;
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
    if(xSize<8) {
      xSize = 8;
    }
    if(ySize<8) {
      ySize = 8;
    }
    if(xSize>50) {
      xSize = 50;
    }
    if(ySize>50) {
      ySize = 50;
    }
    
    this.xSize = xSize;
    this.ySize = ySize;
  }
  
  public void resetGame() {
    isGameEnded = false;
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

  protected void makeField() {
    for(int y=0; y<this.ySize; ++y) {
      for(int x=0; x<this.xSize; ++x) {
        gameField.put(new FieldPoint(x, y), new Cell(x, y));
      }
    }
  }

  public void countCellValues() {
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
  
  public boolean testWinCondition() {
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
      return true;
    } else {
      return false;
    }
  }

  public String printCellInfo(int y, int x) {
    Cell cell = this.getCell(new FieldPoint(x, y));
    if (!isGameEnded) {
      if (cell.isOpen()) {
        return ((Integer) cell.getCellValue()).toString();
      } else {
        return cell.getCellStateStringRepresentation();
      }
    } else {
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
  }

  @Override
  public int getColumnCount() {
    return getXSize();
  }

  @Override
  public int getRowCount() {
    return getYSize();
  }

  @Override
  public Object getValueAt(int arg0, int arg1) {
    String cellText = printCellInfo(arg0, arg1);
    if(pictures.get(cellText)!=null) {
      return pictures.get(cellText);
    }
    else {
      return cellText;
    }
  }
  
  public final static HashMap<String, ImageIcon> pictures = new HashMap<String, ImageIcon>();
  
  static {
    pictures.put("0", new ImageIcon("zero.png"));
    pictures.put("C", new ImageIcon("closedCell.png"));
    pictures.put("1", new ImageIcon("one.png"));
    pictures.put("2", new ImageIcon("two.png"));
    pictures.put("3", new ImageIcon("three.png"));
    pictures.put("4", new ImageIcon("four.png"));
    pictures.put("5", new ImageIcon("five.png"));
    pictures.put("6", new ImageIcon("six.png"));
    pictures.put("7", new ImageIcon("seven.png"));
    pictures.put("8", new ImageIcon("eight.png"));
    pictures.put("M", new ImageIcon("mine.png"));
    pictures.put("-1", new ImageIcon("mine.png"));
    pictures.put("F", new ImageIcon("flag.png"));
    pictures.put("BF", new ImageIcon("badFlag.png"));
    pictures.put("EM", new ImageIcon("explodedMine.png"));
  }

  public void setAutoFlagging(boolean autoFlagging) {
    this.autoFlagging = autoFlagging;
    Cell.EventAction.setAutoFlaggingMode(autoFlagging);
  }

  public void setAutoOpening(boolean autoOpening) {
    this.autoOpening = autoOpening;
    Cell.EventAction.setAutoOpeningMode(autoOpening);
  }

//  public boolean isAutoFlaggingEnabled() {
//    return this.autoFlagging;
//  }
//
//  public boolean isAutoOpeninEnabled() {
//    return this.autoOpening;
//  }
  
  public class OutOfFieldException extends Exception {
    private static final long serialVersionUID = 5563721290754909080L;
  }

  public int getMinesNumber() {
    return minesNumber;
  }
  
}
