package gameUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GameLauncher {
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        MinesweeperGame frame = new MinesweeperGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}