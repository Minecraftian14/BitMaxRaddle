package org.that.gui;

import main.generalLogger.LOGGER;
import org.that.logic.Bitmap;
import org.that.logic.BitmapUtil;
import org.that.util.ButtonFactory;
import org.that.util.MiscFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controls {

    public static class OpenImage implements ActionListener {

        private final Component parent;
        private final DrawingPane pane;
        private final JFileChooser chooser;

        public OpenImage(Component _parent, DrawingPane _pane) {
            parent = _parent;
            pane = _pane;
            chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image/ Bitmap", "png", "jpg", "gif", "bmp"));
            LOGGER.info("Open image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.showOpenDialog(parent);
            File f = chooser.getSelectedFile();
            try {

                if (f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif")) {
                    pane.setImage(ImageIO.read(f));
                    pane.setReference(f);
                    pane.repaint();
                    LOGGER.notice("Image read of size", pane.getImage().getWidth(), pane.getImage().getHeight(), "from", f.getAbsolutePath());

                } else if (f.getName().endsWith(".bmp")) {
                    BufferedImage i = BitmapUtil.read(f).asImage();
                    if (i == null) {
                        MiscFactory.getPopup(new JMenuItem("Null Pointer Exception")).show(parent, 50, 50 + pane.getY());
                        LOGGER.error("Reading bitmap failed for", f.getAbsolutePath());

                    } else {
                        pane.setImage(i);
                        pane.repaint();
                        LOGGER.notice("Bitmap read of size", pane.getImage().getWidth(), pane.getImage().getHeight(), "from", f.getAbsolutePath());
                    }
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static class NewImage implements ActionListener {

        private final Component parent;
        private final DrawingPane pane;
        private final JPopupMenu menu;
        private final JTextField width;
        private final JTextField height;

        public NewImage(Component _parent, DrawingPane _pane) {
            parent = _parent;
            pane = _pane;
            menu = MiscFactory.getPopup();
            menu.add(width = MiscFactory.getField("200"));
            menu.add(height = MiscFactory.getField("200"));
            menu.add(ButtonFactory.get("create", e -> {
                pane.setImage(new BufferedImage(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), 2));
                pane.draw((a, b) -> 255);
                pane.repaint();
                LOGGER.notice("New image created of size", width.getText(), height.getText());
            }));
            LOGGER.info("New image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pane.setReference(null);
            menu.show(parent, 100, 300);
        }
    }

    public static class DeleteImage implements ActionListener {

        private final Component parent;
        private final DrawingPane pane;

        public DeleteImage(Component _parent, DrawingPane _pane) {
            parent = _parent;
            pane = _pane;
            LOGGER.info("Delete image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (pane.getReference() != null) {
                if (pane.getReference().delete())
                    LOGGER.notice("File deleted at", pane.getReference().getAbsolutePath());
                else LOGGER.error("Deleting file failed");
                pane.setReference(null);
                pane.draw((a, b) -> 255);
                pane.repaint();

            } else MiscFactory.getPopup(new JMenuItem("No image loaded!")).show(parent, 50, 50 + pane.getY());
        }
    }

    public static class SaveImage implements ActionListener {

        private final Component parent;
        private final DrawingPane pane;
        private final JFileChooser chooser;

        public SaveImage(Component _parent, DrawingPane _pane) {
            parent = _parent;
            pane = _pane;
            chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image/ Bitmap", "png", "jpg", "gif", "bmp"));
            LOGGER.info("Save image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            chooser.showSaveDialog(parent);
            File f = chooser.getSelectedFile();

            try {
                if (f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif")) {
                    ImageIO.write(pane.getImage(), "PNG", f);
                    pane.setReference(f);
                    MiscFactory.getPopup(new JMenuItem("Image saved!")).show(parent, 50, 50 + pane.getY());
                    LOGGER.notice("Image saved at", f.getAbsolutePath());

                } else if (f.getName().endsWith(".bmp")) {
                    BitmapUtil.write(f, new Bitmap(pane.getImage()));
                    MiscFactory.getPopup(new JMenuItem("Bitmap saved!")).show(parent, 50, 50 + pane.getY());
                    LOGGER.notice("Bitmap saved at", f.getAbsolutePath());

                } else {
                    MiscFactory.getPopup(new JMenuItem("Format error!")).show(parent, 50, 50 + pane.getY());
                    LOGGER.error("Not a valid format! Choose from [jpg, gif, png] to save as image or [bmp] to save as bitmap");
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public static class RandomImage implements ActionListener {

        private final DrawingPane pane;

        public RandomImage(DrawingPane _pane) {
            pane = _pane;
            LOGGER.info("Random image generation behaviour created");

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = (int) (Math.random() * 5);
            switch (i) {
                case 0:
                    pane.draw((a, b) -> (int) ((Math.sin((a * a + b * b) * 0.001 * (0.9 + 0.1 * Math.random()) - 1) + 1) * 128));
                    break;
                case 1:
                    pane.draw((a, b) -> (int) ((Math.sin(a * a * 0.001 - b * 0.01) + 1) * 128 * Math.random()));
                    break;
                case 2:
                    pane.draw((a, b) -> (int) (256 * Math.random()));
                    break;
                case 3:
                    pane.draw((a, b) -> (int) ((Math.sin((a + b) * 0.01) + 1) * 127 * Math.random()));
                    break;
                default:
                    pane.draw((a, b) -> (int) ((Math.sin((a - b) * 0.01) + 1) * 127 * Math.random()));
            }
            pane.repaint();
            LOGGER.notice("Random image created using alg", i);
        }
    }

    public static class ToggleOnesMethod implements ActionListener {

        DrawingPane pane;

        public ToggleOnesMethod(DrawingPane _pane) {
            this.pane = _pane;
            LOGGER.info("Toggle 0 - 1 behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pane.toggleOnes();
            ((JButton) e.getSource()).setText(pane.isOnes() ? "One" : "Zero");
            LOGGER.notice("Drawing state changed to", pane.isOnes());
        }
    }

    public static class AskColorTheme implements ActionListener {

        Component parent;
        DrawingPane pane;

        public AskColorTheme(Component _parent, DrawingPane _pane) {
            this.parent = _parent;
            this.pane = _pane;
            LOGGER.info("Colour choosing behaviour for theme created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Color one = JColorChooser.showDialog(parent, "Choose a ONE color", pane.getOne());
            Color zero = JColorChooser.showDialog(parent, "Choose a ZERO color", pane.getZero());
            pane.setColorTheme(one, zero);
            LOGGER.notice("Theme color choosen as", one, zero);

            pane.repaint();

        }
    }

    public static class ZoomImage implements ActionListener {

        Component parent;
        DrawingPane pane;
        JSlider slider;
        JPopupMenu popupMenu;

        public ZoomImage(Component parent, DrawingPane pane) {
            this.parent = parent;
            this.pane = pane;
            slider = new JSlider(1, 100, 100);
            slider.addChangeListener(e -> pane.setZoom(slider.getValue() / 100.0));
            popupMenu = MiscFactory.getPopup();
            popupMenu.add(slider);
            LOGGER.info("Zoom image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            popupMenu.show(parent, 100, 300);
            LOGGER.notice("Zoom dialog shown");
        }
    }

    public static class MoveImage implements ActionListener {

        Component parent;
        DrawingPane pane;
        JPopupMenu m;
        JSlider sw, sh;

        public MoveImage(Component _parent, DrawingPane _pane) {
            parent = _parent;
            this.pane = _pane;
            m = MiscFactory.getPopup();
            sw = new JSlider(0, pane.getImage().getWidth() - pane.getWidth(), 0);
            sh = new JSlider(0, pane.getImage().getHeight() - pane.getHeight(), 0);
            sw.addChangeListener(e -> pane.setRoot_x(-sw.getValue()));
            sh.addChangeListener(e -> pane.setRoot_y(-sh.getValue()));
            m.add(sw);
            m.add(sh);
            LOGGER.info("Move image behaviour created");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            sw.setMaximum(Math.abs(pane.getViewWidth() - pane.getWidth()));
            sh.setMaximum(Math.abs(pane.getViewHeight() - pane.getHeight()));
            System.out.println(pane.getWidth());
            m.show(parent, 100, 300);
            LOGGER.notice("Move dialog shown");
        }
    }

}
