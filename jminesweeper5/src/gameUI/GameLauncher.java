package gameUI;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

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