package gameLogic;

public class CellType {
  private CellType() {}
  public static final CellType EMPTY_CELL = new CellType();
  public static final CellType MINE = new CellType();
}
