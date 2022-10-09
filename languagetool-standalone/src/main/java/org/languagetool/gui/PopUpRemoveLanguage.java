package org.languagetool.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

class PopUpRemoveLangauge extends JPopupMenu {

  private static final long serialVersionUID = 1L;
  JMenuItem removeOption = new JMenuItem("Remove");

  public PopUpRemoveLangauge() {

    add(removeOption);
    removeOption.addMouseListener(new MouseListener() {

      @Override
      public void mousePressed(MouseEvent e) {
        LanguageComboBoxModel comboBoxModel = LanguageComboBoxModel.languageComboBoxModel;
        int itemCount = comboBoxModel.getSize();
        Object selectedElement = comboBoxModel.getSelectedItem();

        int index = comboBoxModel.getIndexOf(selectedElement);
        // If only one language is left,we cannot remove it
        if (itemCount == 1) {
          JOptionPane.showMessageDialog(null, "Selection Cannot Be Removed");
        } else if (index == 0) {
          int newIndex = comboBoxModel.getIndexOf(selectedElement) + 1;
          Object newElement = comboBoxModel.getElementAt(newIndex);
          comboBoxModel.setSelectedItem(newElement);
          comboBoxModel.removeElement(selectedElement);

        } else if (index - 1 >= 0) {
          int newIndex = comboBoxModel.getIndexOf(selectedElement) - 1;
          Object newElement = comboBoxModel.getElementAt(newIndex);
          comboBoxModel.setSelectedItem(newElement);
          comboBoxModel.removeElement(selectedElement);

        }

      }

      @Override
      public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

    });
  }
}