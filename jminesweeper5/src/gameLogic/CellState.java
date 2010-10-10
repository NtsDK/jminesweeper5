package gameLogic;

public class CellState {
  private CellState() {}
  public static final CellState OPEN = new CellState();
  public static final CellState CLOSE = new CellState();
  public static final CellState MARK = new CellState();
}

//public final class Suit {
//  private Suit() {}
//  public static final Suit SPADES = new Suit();
//  public static final Suit HEARTS = new Suit();
//  public static final Suit DIAMONDS = new Suit();
//  public static final Suit CLUBS = new Suit();
//}