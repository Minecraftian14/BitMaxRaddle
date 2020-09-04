package org.that.util;

import javax.swing.*;
import java.awt.*;

public class MiscFactory {

    public static JPopupMenu getPopup(JMenuItem...items) {
        JPopupMenu m = new JPopupMenu();
        m.setBackground(new Color(240, 255, 252));
        for (JMenuItem item : items) m.add(item);
        return m;
    }

    public static JTextField getField(String txt) {
        JTextField fie = new JTextField(txt);
        fie.setBorder(null);
        fie.getInsets(new Insets(5,5,5,5));
        fie.setBackground(new Color(240, 255, 252));
        return fie;
    }

}
