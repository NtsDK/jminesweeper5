package gameLogic;

public class Cell {
  private CellCoords cellCoords;
  private CellState cellState;
  private CellType cellType;
  
  public Cell(CellCoords cellCoords) {
    this.cellCoords = cellCoords;
    cellState = CellState.CLOSE;
    cellType = CellType.EMPTY_CELL;
  }
  
  public CellType getCellType() {
    return cellType;
  }
  
  public void openCell() {
    if(cellState == CellState.CLOSE) {
      cellState = CellState.OPEN;
    }
    else if(cellState == CellState.OPEN) {
      cellState = CellState.CLOSE;
    }
  }
  
  public void markCell() {
    if(cellState == CellState.CLOSE) {
      cellState = CellState.MARK;
    }
    else if(cellState == CellState.MARK) {
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
    
  }
  
  public void setMine() {
    cellType = CellType.MINE;
  }

  public boolean isGameEnded() {
    // TODO Auto-generated method stub
    return this.cellType == CellType.MINE && this.cellState == CellState.OPEN;
  }
  
}
