package gameUI;

import gameLogic.MinesweeperModel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * This dialog used for changing field parameters: x size (from 8 to 50), y size (from 8 to 50) and mines number (from 10 to 49*49 - max possible mines number).
 * @author tima
 *
 */
public class ParametersDialog extends JDialog{

  private static final long serialVersionUID = -2101415625252142415L;

  ParametersDialog(int xSize, int ySize, int minesMumber) {
    this.isDataChanged = false;
    
    setModal(true);
    setTitle("Edit field parameters");

    JPanel buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.SOUTH);

    saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        isDataChanged = true;
        setVisible(false);
      }
    });
    buttonPanel.add(saveButton);

    this.cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        isDataChanged = false;
        setVisible(false);
      }
    });
    buttonPanel.add(cancelButton);
    
    JPanel centralPanel = new JPanel();
    GridLayout layout = new GridLayout(3, 2);
    centralPanel.setLayout(layout);
    add(centralPanel, BorderLayout.CENTER);
    
    fieldXSizeLabel = new JLabel("Horizontal size:");
    fieldYSizeLabel = new JLabel("Vertical size:");
    minesNumberLabel = new JLabel("Mines number:");
    xSizeSpinner = new JSpinner(new SpinnerNumberModel(xSize, MinesweeperModel.MIN_FIELD_SIZE, MinesweeperModel.MAX_FIELD_SIZE, 1));
    ySizeSpinner = new JSpinner(new SpinnerNumberModel(ySize, MinesweeperModel.MIN_FIELD_SIZE, MinesweeperModel.MAX_FIELD_SIZE, 1));
    minesNumberSpinner = new JSpinner(new SpinnerNumberModel(minesMumber, MinesweeperModel.MIN_MINES_NUMBER, MinesweeperModel.MAX_MINES_NUMBER, 1));

    centralPanel.add(fieldXSizeLabel);
    centralPanel.add(xSizeSpinner);
    centralPanel.add(fieldYSizeLabel);
    centralPanel.add(ySizeSpinner);
    centralPanel.add(minesNumberLabel);
    centralPanel.add(minesNumberSpinner);

    setResizable(false);
    pack();
    setVisible(true);
  }
  
  private JButton saveButton;
  private JButton cancelButton;
  private boolean isDataChanged;
  
  JLabel fieldXSizeLabel;
  JLabel fieldYSizeLabel;
  JLabel minesNumberLabel;
  JSpinner xSizeSpinner;
  JSpinner ySizeSpinner;
  JSpinner minesNumberSpinner;

  public int getFieldXSize() {
    return (Integer)xSizeSpinner.getValue();
  }
  public int getFieldYSize() {
    return (Integer)ySizeSpinner.getValue();
  }
  public int getMinesNumber() {
    return (Integer)minesNumberSpinner.getValue();
  }
  public boolean isDataChanged() {
    return isDataChanged;
  }
}
