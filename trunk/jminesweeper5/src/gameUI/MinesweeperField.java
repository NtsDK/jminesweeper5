package gameUI;

import gameLogic.CellCoords;
import gameLogic.GameEventType;
import gameLogic.MinesweeperModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

public class MinesweeperField extends JPanel{
  /**
   * 
   */
  private static final long serialVersionUID = -5893741954597139558L;
  public MinesweeperField() {
    loadProperties();
    
    gameModel = new MinesweeperModel(Integer.parseInt(getProperty("xSize")),Integer.parseInt(getProperty("ySize")));
    
    gameTable = new JTable(gameModel);
    gameModel.addTableModelListener(gameTable);
    
    
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
            gameModel.makeMove(new CellCoords(column, row),
                GameEventType.LEFT_BUTTON_CLICK);
            gameModel.fireTableDataChanged();
          } else if (SwingUtilities.isRightMouseButton(e)) {
            gameModel.makeMove(new CellCoords(column, row),
                GameEventType.RIGHT_BUTTON_CLICK);
            gameModel.fireTableDataChanged();
          }
        }
      }
    });
    
    add(gameTable, BorderLayout.CENTER);
  }
  
  public void resetGame() {
    gameModel.resetGame(Integer.parseInt(getProperty("minesNumber")));
    for(int i=0;i<gameTable.getColumnCount();++i){
      gameTable.getColumnModel().getColumn(i).setPreferredWidth(40);
    }
    gameTable.setRowHeight(40);
    gameModel.fireTableDataChanged();
  }

  public void loadProperties() {
    gameProperties = new Properties();
    
    try {
      FileInputStream in;
      in = new FileInputStream("minesweeper.properties");
      gameProperties.load(in);
      in.close();
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private String getProperty(String propertyName) {
    String property = gameProperties.getProperty(propertyName);
    if(property==null) {
      property = defaultProperties.get(propertyName);
    }
    
    if(propertyName.equals("minesNumber")) {
      // TODO validate mines number
    }
    return property;
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
  

  private MinesweeperModel gameModel;
  private JTable gameTable;
  private Properties gameProperties = new Properties();
  
  private static HashMap<String,String> defaultProperties = new HashMap<String, String>();
  static {
    defaultProperties.put("xSize", "20");
    defaultProperties.put("ySize", "10");
    defaultProperties.put("minesNumber", "15");
    //defaultProperties.put("gameType", "custom");
  }
  
  
//  public static final int DEFAULT_WIDTH = 400;
//  public static final int DEFAULT_HEIGHT = 400;
}

//public class CellRenderer extends JLabel implements TableCellRenderer {
//
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value,
//        boolean isSelected, boolean hasFocus, int row, int column) {
//      String text = (String)value;
//      this.setText(text);
//      this.setEnabled(false);
//      this.setSize(10, 10);
//      this.setPreferredSize(new Dimension(10, 10));
//      this.setToolTipText("Hello!");
//      setHorizontalAlignment(SwingConstants.CENTER);
//      setBackground(Color.green);
//      
////      this.addMouseListener(new MouseAdapter() {
////        public void mouseClicked(MouseEvent e) {
////          JTable target = (JTable) e.getSource();
////          int row = target.getSelectedRow();
////          int column = target.getSelectedColumn();
////          
////          //if(e.getButton()==InputEvent.BUTTON1_MASK){
////          
////          if(SwingUtilities.isLeftMouseButton(e)){
////            gameModel.makeMove(new CellCoords(column, row), GameEventType.LEFT_BUTTON_CLICK);
////            gameModel.fireTableDataChanged();
////          //}else if(e.getButton()==InputEvent.BUTTON2_MASK){
////          }else if(SwingUtilities.isRightMouseButton(e)){
////            gameModel.makeMove(new CellCoords(column, row), GameEventType.RIGHT_BUTTON_CLICK);
////            gameModel.fireTableDataChanged();
////          }
////        }
////      });
//      
//      return this;
//      // TODO Auto-generated method stub
//      //return null;
//    }
    
//  }
