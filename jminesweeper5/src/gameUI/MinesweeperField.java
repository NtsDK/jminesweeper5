package gameUI;

import gameLogic.FieldPoint;
import gameLogic.GameEventType;
import gameLogic.MinesweeperModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * This class is a minesweeper UI representation container.
 * It controls showing game and provides interface for changing game parameters.
 * @author tima
 *
 */
public class MinesweeperField extends JPanel{
  private static final long serialVersionUID = -5893741954597139558L;
  
  private static final int PICTURE_SIZE = 40;
  
  public MinesweeperField() {
    gameModel = new MinesweeperModel(10,10,10);
    gameRepresentationModel = new MinesweeperRepresentationModel(gameModel);
    
    gameTable = new JTable(gameRepresentationModel);
    gameRepresentationModel.addTableModelListener(gameTable);
    
    gameTable.setCellSelectionEnabled(false);
    gameTable.setDefaultRenderer(Object.class, new CellRenderer());
    gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    gameTable.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        if (!gameModel.isGameEnded()) {

          Point p = e.getPoint();
          int row = gameTable.rowAtPoint(p);
          int column = gameTable.columnAtPoint(p);

          if (SwingUtilities.isLeftMouseButton(e)) {
            gameModel.makeMove(new FieldPoint(column, row),
                GameEventType.LEFT_BUTTON_CLICK);
            gameRepresentationModel.fireTableDataChanged();
          } else if (SwingUtilities.isRightMouseButton(e)) {
            gameModel.makeMove(new FieldPoint(column, row),
                GameEventType.RIGHT_BUTTON_CLICK);
            gameRepresentationModel.fireTableDataChanged();
          }
        }
      }
    });
    
    add(gameTable, BorderLayout.CENTER);
  }
  
  public void resetGame() {
    gameModel.resetGame();
    for(int i=0;i<gameTable.getColumnCount();++i){
      gameTable.getColumnModel().getColumn(i).setPreferredWidth(PICTURE_SIZE);
    }
    gameTable.setRowHeight(PICTURE_SIZE);
    gameRepresentationModel.fireTableDataChanged();
  }

  public class CellRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable arg0, Object arg1,
        boolean arg2, boolean arg3, int arg4, int arg5) {
      ImageIcon image = (ImageIcon)arg1;
      this.setIcon(image);
      Dimension size = new Dimension(image.getIconHeight(), image.getIconHeight());
      this.setMaximumSize(size);
      this.setMinimumSize(size);
      this.setPreferredSize(size);
      return this;
    }
  }

  public boolean isAutoFlaggingEnabled() {
    return gameModel.isAutoFlaggingEnabled();
  }

  public void setAutoFlaggingMode(boolean b) {
    gameModel.setAutoFlagging(b);
  }
  
  public boolean isAutoOpeningEnabled() {
    return gameModel.isAutoOpeningEnabled();
  }

  public void setAutoOpeningMode(boolean b) {
    gameModel.setAutoOpening(b);    
  }

  public int getMinesNumber() {
    return gameModel.getMinesNumber();
  }

  public int getFieldXSize() {
    return gameModel.getXSize();
  }

  public int getFieldYSize() {
    return gameModel.getYSize();
  }

  public void setFieldParameters(int fieldXSize, int fieldYSize, int minesNumber) {
    gameModel.setFieldSize(fieldXSize, fieldYSize);
    gameModel.setMinesNumber(minesNumber);
    gameRepresentationModel.fireTableStructureChanged();
    resetGame();
  }
  
  private MinesweeperModel gameModel;
  private MinesweeperRepresentationModel gameRepresentationModel;
  private JTable gameTable;
}