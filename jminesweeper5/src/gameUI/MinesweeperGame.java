package gameUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/** 
 * This class manages high levelled UI interactions.
 * It is used for building and making interaction between game UI elements: game menu, game field and button panel.
 * @author tima
 *
 */
public class MinesweeperGame extends JFrame {
  
  public MinesweeperGame(){
    
    setTitle("JMinesweeper5");
    minesweeperField = new MinesweeperField();
    add(minesweeperField, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.NORTH);
    
    newGameButton = new JButton("New game");
    newGameButton.addActionListener(newGame);
    buttonPanel.add(newGameButton);
    
    configureMenu();
    minesweeperField.resetGame();
    this.pack();
    this.setResizable(false);
  }
  
  private void configureMenu() {
    JMenuBar menu = new JMenuBar();
    setJMenuBar(menu);
    
    // game
    JMenu gameMenu = new JMenu("Game");
    menu.add(gameMenu);
    
    JMenuItem newGameMenuItem = new JMenuItem("New Game");
    newGameMenuItem.addActionListener(newGame);
    gameMenu.add(newGameMenuItem);
    
    gameMenu.addSeparator();
    
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addActionListener(exitGame);
    gameMenu.add(exitMenuItem);
    
    // edit
    JMenu editMenu = new JMenu("Edit");
    menu.add(editMenu);
    
    JCheckBoxMenuItem autoFlaggingMenuItem = new JCheckBoxMenuItem("Auto flagging");
    autoFlaggingMenuItem.setSelected(minesweeperField.isAutoFlaggingEnabled());
    autoFlaggingMenuItem.addActionListener(autoFlaggingListener);
    editMenu.add(autoFlaggingMenuItem);
    
    JCheckBoxMenuItem autoOpeningMenuItem = new JCheckBoxMenuItem("Auto opening");
    autoOpeningMenuItem.setSelected(minesweeperField.isAutoOpeningEnabled());
    autoOpeningMenuItem.addActionListener(autoOpeningListener);
    editMenu.add(autoOpeningMenuItem);
    
    editMenu.addSeparator();
    
    JMenuItem parametersMenuItem = new JMenuItem("Parameters");
    parametersMenuItem.addActionListener(changeFieldParameters);
    editMenu.add(parametersMenuItem);
        
    // about
    JMenu aboutMenu = new JMenu("About");
    menu.add(aboutMenu);
  
    JMenuItem aboutMenuItem = new JMenuItem("About...");
    aboutMenuItem.addActionListener(showAboutDialog);
    aboutMenu.add(aboutMenuItem);
  }
  
  
  private ActionListener newGame = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      minesweeperField.resetGame();
      pack();
    }
  };
  
  private ActionListener autoFlaggingListener = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      minesweeperField.setAutoFlaggingMode(!minesweeperField.isAutoFlaggingEnabled());
    }
  };
  
  private ActionListener autoOpeningListener = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      minesweeperField.setAutoOpeningMode(!minesweeperField.isAutoOpeningEnabled());
    }
  };
  
  public ActionListener exitGame = new ActionListener() {
    public void actionPerformed(ActionEvent event) {
      System.exit(0);
    }
  };
  
  private ActionListener changeFieldParameters = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      ParametersDialog dialog = new ParametersDialog(
          minesweeperField.getFieldXSize(),  minesweeperField.getFieldYSize(),  minesweeperField.getMinesNumber());     
      if(dialog.isDataChanged()) {
        minesweeperField.setFieldParameters(dialog.getFieldXSize(), dialog.getFieldYSize(),dialog.getMinesNumber());
        pack();
      }
    }
  };
  
  private ActionListener showAboutDialog = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      AboutDialog dialog = new AboutDialog();
    }
  };

  private MinesweeperField minesweeperField;
  private JButton newGameButton;
}
