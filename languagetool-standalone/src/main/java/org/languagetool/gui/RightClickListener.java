package org.languagetool.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class RightClickListener extends MouseAdapter {
  public void mousePressed(MouseEvent e) {
    if (e.isPopupTrigger())
      doPop(e);
  }

  public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger())
      doPop(e);
  }

  private void doPop(MouseEvent e) {
    PopUpRemoveLangauge menu = new PopUpRemoveLangauge();
    menu.show(e.getComponent(), e.getX(), e.getY());
  }
}
