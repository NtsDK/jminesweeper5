package gameUI;

import java.util.HashMap;

import gameLogic.MinesweeperModel;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/** 
 * This class implements AbstractTableModel interface for MinesweeperModel.
 * It is used for showing field state.
 * @author tima
 *
 */
class MinesweeperRepresentationModel extends AbstractTableModel{
  
  private static final long serialVersionUID = 3171163557305920461L;
  public final static HashMap<String, ImageIcon> pictures = new HashMap<String, ImageIcon>();
  
  static {
    pictures.put("0", new ImageIcon("defaultPictures/0.jpeg"));
    pictures.put("C", new ImageIcon("defaultPictures/empty.jpeg"));
    pictures.put("1", new ImageIcon("defaultPictures/1.jpeg"));
    pictures.put("2", new ImageIcon("defaultPictures/2.jpeg"));
    pictures.put("3", new ImageIcon("defaultPictures/3.jpeg"));
    pictures.put("4", new ImageIcon("defaultPictures/4.jpeg"));
    pictures.put("5", new ImageIcon("defaultPictures/5.jpeg"));
    pictures.put("6", new ImageIcon("defaultPictures/6.jpeg"));
    pictures.put("7", new ImageIcon("defaultPictures/7.jpeg"));
    pictures.put("8", new ImageIcon("defaultPictures/8.jpeg"));
    pictures.put("M", new ImageIcon("defaultPictures/mine.jpeg"));
    pictures.put("F", new ImageIcon("defaultPictures/flag.jpeg"));
    pictures.put("BF", new ImageIcon("defaultPictures/brokenFlag.jpeg"));
    pictures.put("EM", new ImageIcon("defaultPictures/bombedMine.jpeg"));
  }
  
  public MinesweeperRepresentationModel(MinesweeperModel model) {
    this.model = model;
  }
  
  @Override
  public int getColumnCount() {
    return model.getXSize();
  }

  @Override
  public int getRowCount() {
    return model.getYSize();
  }

  @Override
  public Object getValueAt(int arg0, int arg1) {
    String cellText = model.printCellInfo(arg0, arg1);
    return pictures.get(cellText);
  }
  
  private MinesweeperModel model;
}