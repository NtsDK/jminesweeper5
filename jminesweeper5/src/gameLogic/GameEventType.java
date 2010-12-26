package gameLogic;

/** Game event enumeration class. */
public class GameEventType {
  private GameEventType() {}
  // general game events
  public static final GameEventType LEFT_BUTTON_CLICK = new GameEventType();
  public static final GameEventType RIGHT_BUTTON_CLICK = new GameEventType();
  // console game events
  public static final GameEventType PRINT_MAN = new GameEventType();
  public static final GameEventType NEW_GAME = new GameEventType();
  public static final GameEventType EXIT_GAME = new GameEventType();
  public static final GameEventType PRINT_GAME_FIELD = new GameEventType();
  public static final GameEventType CHANGE_MINES_NUMBER = new GameEventType();
  public static final GameEventType PRINT_GAME_PROPERTIES = new GameEventType();
}
