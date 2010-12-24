package gameConsole;

import gameLogic.Cell;
import gameLogic.MinesweeperModel;

public class ConsoleUtilities {
  /** Used testing only.*/
  public static void printFieldContents(MinesweeperModel model) {
    for(int y=0; y<model.getYSize(); ++y) {
      for(int x=0; x<model.getXSize(); ++x) {
        int cellValue = model.getCell(x, y).getCellValue();
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
  
  /** Used testing only.*/
  public static void printField(MinesweeperModel model) {
    for(int y=0; y<model.getYSize(); ++y) {
      for(int x=0; x<model.getXSize(); ++x) {
        Cell cell = model.getCell(x, y);
        System.out.print(cell.getCellStateStringRepresentation());
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
  
  public static void printGameField(MinesweeperModel model) {
    for(int y=0; y<model.getYSize(); ++y) {
      for(int x=0; x<model.getXSize(); ++x) {
        System.out.print(model.printCellInfo(y, x));
        
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
}
