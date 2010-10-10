package gameLogic;

import java.util.HashMap;
import java.util.Map;

public class MineSweeperGame {

  private Map<CellCoords,Cell> gameField = new HashMap<CellCoords, Cell>(); 
  private final int xSize = 20, ySize = 10;
  private boolean isGameEnded = false;
  private static Map<CellState, String> cellState2String = new HashMap<CellState, String>();
  
  static {
    cellState2String.put(CellState.OPEN, "O");
    cellState2String.put(CellState.CLOSE, "C");
    cellState2String.put(CellState.MARK, "M");
  }
  
  public MineSweeperGame() {
    for(int y=0; y<ySize; ++y) {
      for(int x=0; x<xSize; ++x) {
        gameField.put(new CellCoords(x, y), new Cell(new CellCoords(x, y)));
      }
    }
  }
  

  public Cell getCell(CellCoords cellCoords) {
    return gameField.get(cellCoords);
  }
  
  public int openCell(int x, int y) {
    return 8;
  }
  
  public int markCell(int x, int y) {
    return 6;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
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
    this.gameField.get(cellCoords).makeMove(eventType);
    isGameEnded = this.gameField.get(cellCoords).isGameEnded();
  }
  
}
