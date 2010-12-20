package gameUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MinesweeperGame extends JFrame {
  
  public MinesweeperGame(){
    
    setTitle("JMinesweeper5");
    minesweeperField = new MinesweeperField();
    add(minesweeperField, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.NORTH);
    
    newGameButton = new JButton("New game");
    newGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        minesweeperField.resetGame();
        pack();
      }
    });
    buttonPanel.add(newGameButton);
    
    minesweeperField.resetGame();
    this.pack();
    this.setResizable(false);
  }

  private MinesweeperField minesweeperField;
  private JButton newGameButton;
}
