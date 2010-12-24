package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cell {
  private FieldPoint cellCoords;
  private CellState cellState;
  private ArrayList<Cell> neighboursList = new ArrayList<Cell>();
  private int cellValue = -1;
  private boolean isMine;
  
  private static class CellState {
    private CellState() {}
    public static final CellState OPEN = new CellState();
    public static final CellState CLOSE = new CellState();
    public static final CellState FLAG = new CellState();
  }
  
  public boolean isOpen() {
    return cellState == CellState.OPEN;
  }
  
  public boolean isFlag() {
    return cellState == CellState.FLAG;
  }
  
  public boolean isClose() {
    return cellState == CellState.CLOSE;
  }
  
  public Cell(int x, int y) {
    this(new FieldPoint(x, y));
  }
  
  public Cell(FieldPoint cellCoords) {
    this.cellCoords = cellCoords;
    cellState = CellState.CLOSE;
    isMine = false;
  }
  
  public void setNeighbourList(ArrayList<Cell> neighboursList) {
    this.neighboursList = neighboursList;
    int neighbourMineNumber = 0;
    for(Cell cell:neighboursList) {
      if(cell.isMine() ) {
        neighbourMineNumber++;
      }
    }
    setCellValue(neighbourMineNumber);
  }
  
  public void setCellValue(int value) {
    cellValue = value;
  }
  
  public int getCellValue() {
    return cellValue;
  }
  
  public boolean isMine() {
    return isMine;
  }
  
  /**
   * Used for multicell open operation
   * @return
   */
  public int countNeighboursFlags() {
    int neighboursCounter = 0;
    for(Cell cell: neighboursList) {
      if(cell.getCellState()==CellState.FLAG) {
        neighboursCounter++;
      }
    }
    return neighboursCounter;
  }
  
  /**
   * Used for multicell close operation
   * @return
   */
  public int countOpenNeighbours() {
    int neighboursCounter = 0;
    for(Cell cell: neighboursList) {
      if(cell.getCellState()==CellState.OPEN) {
        neighboursCounter++;
      }
    }
    return neighboursCounter;
  }
  
  /** Recursive opening operation */
  public void openCell() {
    if(cellState == CellState.CLOSE) {
      cellState = CellState.OPEN;
      if(cellValue == 0) {
        for(Cell cell: neighboursList) {
          cell.openCell();
        }
      }
    }
  }
  
  public void flagCell() {
    if(cellState == CellState.CLOSE) {
      cellState = CellState.FLAG;
    }
    else if(cellState == CellState.FLAG) {
      cellState = CellState.CLOSE;
    }
  }
  
  public CellState getCellState() {
    return cellState;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((cellCoords == null) ? 0 : cellCoords.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cell other = (Cell) obj;
    if (cellCoords == null) {
      if (other.cellCoords != null)
        return false;
    } else if (!cellCoords.equals(other.cellCoords))
      return false;
    return true;
  }
  
  /** Make move and return flag isGameEnded */ 
  public boolean makeMove(GameEventType eventType, MinesweeperModel model) {
    EventAction eventAction = EventAction.getEventAction(eventType, this);
    eventAction.ExecuteAction(this);
    if(eventAction.equals(EventAction.openAllNeighboursAction)) {
      return isGameEnded() || isGameEndedInNeighbours();
    } else {
      return isGameEnded();
    }
  }
  

  private static Map<CellState, String> cellState2String = new HashMap<CellState, String>();
  static {
    cellState2String.put(CellState.OPEN, "O");
    cellState2String.put(CellState.CLOSE, "C");
    cellState2String.put(CellState.FLAG, "F");
  }
  
  public String getCellStateStringRepresentation() {
    return cellState2String.get(cellState);
  }
  
  public static abstract class EventAction {
    private EventAction() {}
    // flag on/off
    private static final EventAction switchFlagAction  =
    new EventAction() {
      public void ExecuteAction(Cell cell) {
        cell.flagCell();
      }
    };
    private static final EventAction emptyAction  = 
      new EventAction() {
        public void ExecuteAction(Cell cell) {}
      };
    // flag all neighbours
    private static EventAction flagAllNeighboursAction = emptyAction;
    // open all neighbours
    private static EventAction openAllNeighboursAction = emptyAction;
    // open cell
    private static final EventAction openCellAction  = 
    new EventAction() {
      public void ExecuteAction(Cell cell) {
        cell.openCell();
      }
    };
      
      /** 
       * Determine action procedure for event type by cell.
       * @param eventType
       * @param cell state determine action type
       * @return
       */
    public static EventAction getEventAction(GameEventType eventType, Cell cell) {
      if(eventType == GameEventType.LEFT_BUTTON_CLICK) {
        if(cell.getCellState()==CellState.OPEN) {
          return openAllNeighboursAction;
        }
        if(cell.getCellState()==CellState.FLAG) {
          return emptyAction;
        }
        if(cell.getCellState()==CellState.CLOSE) {
          return openCellAction;
        }
      }
      else {
        if(cell.getCellState()==CellState.OPEN) {
          return flagAllNeighboursAction;
        }
        if(cell.getCellState()==CellState.FLAG) {
          return switchFlagAction;
        }
        if(cell.getCellState()==CellState.CLOSE) {
          return switchFlagAction;
        }
      }
      return emptyAction;
    }
    
    public static void setAutoFlaggingMode(boolean autoFlagging){
      if(autoFlagging) {
        flagAllNeighboursAction = new EventAction() {
          public void ExecuteAction(Cell cell) {
            if (cell.countOpenNeighbours() == cell.neighboursList.size()
                - cell.getCellValue()) {
              for (Cell neighbour : cell.neighboursList) {
                neighbour.flagCell();
              }
            }
          }
        };
      }
      else {
        flagAllNeighboursAction = emptyAction;
      }
    }
    
    public static void setAutoOpeningMode(boolean autoOpening) {
      if (autoOpening) {
        openAllNeighboursAction = new EventAction() {
          public void ExecuteAction(Cell cell) {
            if (cell.countNeighboursFlags() == cell.getCellValue()) {
              for (Cell neighbour : cell.neighboursList) {
                neighbour.openCell();
              }
            }
          }
        };
      } else {
        openAllNeighboursAction = emptyAction;
      }
    }
      
    abstract public void ExecuteAction(Cell cell);
  }
  
  public void setMine() {
    isMine = true;
  }

  private boolean isGameEnded() {
    return (isMine && isOpen());
  }
  
  private boolean isGameEndedInNeighbours() {
    if(!isOpen()) {
      return false;
    }
    for(Cell cell: neighboursList) {
      if(cell.isGameEnded()) {
        return true;
      }
    }
    return false;
  }
  
}
