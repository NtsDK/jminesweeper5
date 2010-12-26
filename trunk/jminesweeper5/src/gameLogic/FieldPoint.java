package gameLogic;

/**
 * This class used for representing cell position and like key for cell.
 * @author tima
 *
 */
public class FieldPoint {
  private int x, y;
  
  public FieldPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
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
    FieldPoint other = (FieldPoint) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }
}
