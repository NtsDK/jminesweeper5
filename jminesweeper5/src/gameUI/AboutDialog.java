package gameUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This dialog shows "about" information.
 * @author tima
 *
 */
public class AboutDialog extends JDialog {

  AboutDialog() {

    setModal(true);
    setTitle("About...");

    JPanel buttonPanel = new JPanel();
    add(buttonPanel, BorderLayout.SOUTH);

    okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        setVisible(false);
      }
    });
    buttonPanel.add(okButton);

    JTextArea text = new JTextArea();
    text.setEditable(false);
    text.setText("JMinesweeper5\nThis is a course project by Test-Driven-Development made by Timofey Rechkalov.\nSouth-Ural State University 2010");
    add(text, BorderLayout.CENTER);

    setResizable(false);
    pack();
    setVisible(true);
  }
  
  private JButton okButton;
}
