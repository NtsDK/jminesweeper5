package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class MinesweeperModel extends AbstractTableModel{

  private Map<CellCoords,Cell> gameField = new HashMap<CellCoords, Cell>(); 
  private final int xSize, ySize;
  private boolean isGameEnded;
  private static Map<CellState, String> cellState2String = new HashMap<CellState, String>();
  private int minesNum;
  private boolean autoFlagging;
  private boolean autoOpening;
  
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
  
  public MinesweeperModel(int xSize, int ySize) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.isGameEnded = false;
    makeField();
  }
  
  public void resetGame(int minesNumber) {
    isGameEnded = false;
    gameField.clear();
    makeField();
    
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

  public void makeField() {
    for(int y=0; y<this.ySize; ++y) {
      for(int x=0; x<this.xSize; ++x) {
        gameField.put(new CellCoords(x, y), new Cell(new CellCoords(x, y)));
      }
    }
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

    /**
     * 
     */
    private static final long serialVersionUID = 5563721290754909080L;
    
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
        System.out.print(printCellInfo(y, x));
        
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

  public String printCellInfo(int y, int x) {
    CellState cellState = this.getCell(x, y).getCellState();
    if (!isGameEnded) {
      if (cellState == CellState.OPEN) {
        return ((Integer) this.getCell(x, y).getCellValue()).toString();
      } else {
        return cellState2String.get(cellState);
      }
    } else {
      if (cellState == CellState.OPEN && this.getCell(x, y).getCellType() == CellType.MINE) {
        return "EM";
      } else if(cellState == CellState.FLAG && this.getCell(x, y).getCellType() != CellType.MINE) {
        return "BF";
      } else if(cellState == CellState.CLOSE && this.getCell(x, y).getCellType() == CellType.MINE) {
        return "M";
      } else if (cellState == CellState.OPEN) {
        return ((Integer) this.getCell(x, y).getCellValue()).toString();
      } else {
        return cellState2String.get(cellState);
      }
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
    pictures.put("9", new ImageIcon("mine.png"));
    pictures.put("F", new ImageIcon("flag.png"));
    pictures.put("BF", new ImageIcon("badFlag.png"));
    pictures.put("EM", new ImageIcon("explodedMine.png"));
  }

  public void setAutoFlagging(boolean b) {
    // TODO Auto-generated method stub
    this.autoFlagging = b;
  }

  public void setAutoOpening(boolean b) {
    // TODO Auto-generated method stub
    this.autoOpening = b;
  }

  public boolean isAutoFlaggingEnabled() {
    // TODO Auto-generated method stub
    return this.autoFlagging;
  }

  public boolean isAutoOpeninEnabled() {
    // TODO Auto-generated method stub
    return this.autoOpening;
  }
  
}
