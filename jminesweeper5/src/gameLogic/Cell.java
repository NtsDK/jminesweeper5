package gameLogic;

import java.util.ArrayList;

public class Cell {
  private CellCoords cellCoords;
  private CellState cellState;
  private CellType cellType;
  private ArrayList<Cell> neighboursList = new ArrayList<Cell>();
  private int cellValue = 9;
  
  public Cell(CellCoords cellCoords) {
    this.cellCoords = cellCoords;
    cellState = CellState.CLOSE;
    cellType = CellType.EMPTY_CELL;
  }
  
  public void setNeighbourList(ArrayList<Cell> neighboursList) {
    this.neighboursList = neighboursList;
  }
  
  public void setCellValue(int value) {
    cellValue = value;
  }
  
  public int getCellValue() {
    return cellValue;
  }
  
  public CellType getCellType() {
    return cellType;
  }
  
  public int countNeighboursFlags() {
    int neighboursCounter = 0;
    for(Cell cell: neighboursList) {
      if(cell.getCellState()==CellState.FLAG) {
        neighboursCounter++;
      }
    }
    return neighboursCounter;
  }
  
  public int countOpenNeighbours() {
    int neighboursCounter = 0;
    for(Cell cell: neighboursList) {
      if(cell.getCellState()==CellState.OPEN) {
        neighboursCounter++;
      }
    }
    return neighboursCounter;
  }
  
  public void openCell() {
    if(cellState == CellState.CLOSE) {
      cellState = CellState.OPEN;
      if(cellValue == 0) {
        for(Cell cell: neighboursList) {
          cell.openCell();
        }
      }
    }
    
//    else if(cellState == CellState.OPEN) {
//      cellState = CellState.CLOSE;
//    }
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

  public void makeMove(GameEventType eventType) {
    // TODO Auto-generated method stub
    EventAction.getEventAction(eventType, this).ExecuteAction(this);
  }
  
  static abstract class EventAction {
    private EventAction() {}
    // flag on/off
    private static final EventAction switchFlagAction  =
    new EventAction() {
      public void ExecuteAction(Cell cell) {
        cell.flagCell();
      }
    };
    // flag all neighbours
    private static final EventAction flagAllNeighboursAction  = 
      new EventAction() {
        public void ExecuteAction(Cell cell) {
          if(cell.countOpenNeighbours()==8-cell.getCellValue()) {
            for(Cell neighbour: cell.neighboursList) {
              neighbour.flagCell();
            }
          }
        }
      };
    // open all neighbours  
    private static final EventAction openAllNeighboursAction  = 
      new EventAction() {
        public void ExecuteAction(Cell cell) {
          if(cell.countNeighboursFlags()==cell.getCellValue()) {
            for(Cell neighbour: cell.neighboursList) {
              neighbour.openCell();
            }
          }
        }
      };
    // open cell
    private static final EventAction openCellAction  = 
    new EventAction() {
      public void ExecuteAction(Cell cell) {
        cell.openCell();
      }
    };
    
    private static final EventAction emptyAction  = 
      new EventAction() {
        public void ExecuteAction(Cell cell) {}
      };
      
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
      System.out.println("Can't reach this place in EventAction.getEventAction");
      return emptyAction;
    }
      
    abstract public void ExecuteAction(Cell cell);
  }
  
  public void setMine() {
    cellType = CellType.MINE;
  }

  public boolean isGameEnded() {
    // TODO Auto-generated method stub
    return this.cellType == CellType.MINE && this.cellState == CellState.OPEN;
  }
  
}
