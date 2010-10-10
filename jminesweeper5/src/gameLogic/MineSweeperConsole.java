package gameLogic;

public class MineSweeperConsole {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    MineSweeperGame mineSweeperGame = new MineSweeperGame();
    while(!mineSweeperGame.isGameEnded()) {
      mineSweeperGame.printField();
      mineSweeperGame.makeMove(null, null);
    }
  }

}
