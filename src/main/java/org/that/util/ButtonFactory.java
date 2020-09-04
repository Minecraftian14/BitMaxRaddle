package org.that.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ButtonFactory {

    public static JButton get(String text, ActionListener list) {

        JButton butt = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
                super.paintComponent(g);
            }
        };

        butt.setBorderPainted(false);
        butt.setContentAreaFilled(false);

        butt.setBackground(new Color(240, 255, 251));

        if (list != null) butt.addActionListener(list);
        return butt;

    }

    public static JButton getCloser(JFrame frame) {
        JButton b = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 255, 234));

                g.drawLine(7, 7, getWidth() - 7, getHeight() - 7);
                g.drawLine(8, 9, getWidth() - 9, getHeight() - 8);
                g.drawLine(9, 8, getWidth() - 8, getHeight() - 9);

                g.drawLine(getWidth() - 7, 7, 7, getHeight() - 7);
                g.drawLine(getWidth() - 9, 8, 8, getHeight() - 9);
                g.drawLine(getWidth() - 8, 9, 9, getHeight() - 8);

                super.paintComponent(g);
            }
        };
        b.setPreferredSize(new Dimension(30, 30));

        b.setBorderPainted(false);
        b.setContentAreaFilled(false);

        b.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));

        return b;
    }
}
