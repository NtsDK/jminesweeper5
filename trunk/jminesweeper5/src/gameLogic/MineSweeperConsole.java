package gameLogic;

import java.util.HashMap;
import java.util.Scanner;

public class MineSweeperConsole {

  private static HashMap<String, GameEventType> commandsMap = new HashMap<String, GameEventType>();
  private static int minesNumber = 10;
  private static MinesweeperModel mineSweeperGame = new MinesweeperModel(20,10);
  
  static {
    commandsMap.put("man", GameEventType.PRINT_MAN);
    commandsMap.put("lbc", GameEventType.LEFT_BUTTON_CLICK);
    commandsMap.put("left-button", GameEventType.LEFT_BUTTON_CLICK);
    commandsMap.put("rbc", GameEventType.RIGHT_BUTTON_CLICK);
    commandsMap.put("right-button", GameEventType.RIGHT_BUTTON_CLICK);
    commandsMap.put("ng", GameEventType.NEW_GAME);
    commandsMap.put("new-game", GameEventType.NEW_GAME);
    commandsMap.put("exit", GameEventType.EXIT_GAME);
    commandsMap.put("print", GameEventType.PRINT_GAME_FIELD);
    commandsMap.put("cmn", GameEventType.CHANGE_MINES_NUMBER);
    commandsMap.put("pgp", GameEventType.PRINT_GAME_PROPERTIES);
  }
  
  public static void printMan() {
    System.out.println("Commands list");
    System.out.println("man - show man");
    System.out.println("left-button(lbc) x y - left button click on cell (x,y)");
    System.out.println("right-button(rbc) x y - right button click on cell (x,y)");
    System.out.println("new-game(ng) - reset game");
    System.out.println("exit - exit game");
    System.out.println("cmn minesNumber - change mines number to minesNumber");
    System.out.println("pgp - print game properties: field size ang mines number");
  }
  
  public static void printGameProperties() {
    System.out.println("Mines number: " + minesNumber);
    System.out.println("Size x: " + mineSweeperGame.getXSize());
    System.out.println("Size y: " + mineSweeperGame.getYSize());
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    
    mineSweeperGame.resetGame(minesNumber);
    printMan();
    mineSweeperGame.printGameField();
    while(true) {
      System.out.print(">");
      GameEventType gameEvent = commandsMap.get(in.nextLine());
      if(gameEvent==null) {
        System.out.println("Unknown command");
        continue;
      }
      if(mineSweeperGame.isGameEnded() && (gameEvent==GameEventType.LEFT_BUTTON_CLICK || 
          gameEvent==GameEventType.RIGHT_BUTTON_CLICK)) {
        continue;
      }
      
      if(gameEvent==GameEventType.EXIT_GAME) {
        System.out.println("Thanks for playing!");
        break;
      }
      processGameEvent(in, gameEvent);
    }
  }

  public static void processGameEvent(Scanner in, GameEventType gameEvent) {
    if(gameEvent==GameEventType.LEFT_BUTTON_CLICK || gameEvent==GameEventType.RIGHT_BUTTON_CLICK) {
      try {
        int x = in.nextInt()-1;
        int y = in.nextInt()-1;
        mineSweeperGame.makeMove(new CellCoords(x, y), gameEvent);
        mineSweeperGame.printGameField();
        if(mineSweeperGame.isGameEnded()) {
          System.out.println("Defeat!");
        } else if(mineSweeperGame.testWinCondition()) {
          System.out.println("You win!");
        }
      } catch(Exception e) {
        System.out.println("Incorrect coordinates");
      }
    } else if(gameEvent==GameEventType.NEW_GAME){
      mineSweeperGame = new MinesweeperModel(20,10);
      mineSweeperGame.resetGame(minesNumber);
    } else if(gameEvent==GameEventType.PRINT_MAN) {
      printMan();
    } else if(gameEvent==GameEventType.CHANGE_MINES_NUMBER) {
      try {
        int newMinesNumber = in.nextInt();
        if(newMinesNumber<0 || newMinesNumber>(mineSweeperGame.getXSize()-1)*(mineSweeperGame.getYSize()-1)) {
          System.out.println("Incorrect mines number input");
          return;
        }
        minesNumber = newMinesNumber;
      } catch(Exception e) {
        System.out.println("Incorrect mines number input");
      }
    } else if(gameEvent==GameEventType.PRINT_GAME_FIELD) {
      if(!mineSweeperGame.isGameEnded()) {
        mineSweeperGame.printGameField();
      }
    }  else if(gameEvent==GameEventType.PRINT_GAME_PROPERTIES) {
      printGameProperties();
    } else {
      System.out.println("Unknown event");
    }
  }

}
