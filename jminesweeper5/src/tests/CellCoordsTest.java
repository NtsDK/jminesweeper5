package tests;

import static org.junit.Assert.assertEquals;
import gameLogic.FieldPoint;

import org.junit.Test;


public class CellCoordsTest {

  @Test
  public void testCellCoordsEquals() {
    FieldPoint cell1 = new FieldPoint(4,5);
    FieldPoint cell2 = new FieldPoint(4,5);
    assertEquals(cell1,cell2);
  }
}
