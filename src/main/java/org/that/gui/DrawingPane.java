package org.that.gui;

import main.generalLogger.LOGGER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.BinaryOperator;

public class DrawingPane extends JPanel {

    private BufferedImage image;
    private BufferedImage overlay = null;
    private boolean drawBoolOnly = true;
    private int radius = 1;
    private File reference = null;
    private boolean drawOnes = false;
    private boolean moveImage = false;
    private double zoom = 1;
    private int root_x = 0;
    private int root_y = 0;
    private Color zero = Color.BLACK;
    private Color one = Color.WHITE;
    private Color ol_zero = Color.BLACK;
    private Color ol_one = Color.WHITE;
    private int view_width = 0;
    private int view_height = 0;

    public DrawingPane() {
        image = new BufferedImage(200, 200, 2);
        resetOverlay();
        draw((a, b) -> (a + b) % 2 == 1 ? 255 : 0);

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = (int) ((double) e.getX() * (double) image.getWidth() / (double) getWidth());
                int y = (int) ((double) e.getY() * (double) image.getHeight() / (double) getHeight());
                if (x < 0) x = 0;
                if (x >= image.getWidth()) x = image.getWidth();
                if (y < 0) y = 0;
                if (y >= image.getHeight()) y = image.getHeight();

                Graphics2D g = image.createGraphics();
                if (drawOnes) g.setColor(one);
                else g.setColor(zero);
                g.fillOval(x - radius + root_x, y - radius + root_y, radius, radius);

//                repaint();
                mouseMoved(e);
            }

            int x, y;

            @Override
            public void mouseMoved(MouseEvent e) {
                if (image.getHeight() < 1000)
                    for (int i = Math.max(0, x - radius / 2 - 2); i < Math.min(x - radius / 2 + 2, overlay.getWidth()); i++)
                        for (int j = 0; j < overlay.getHeight(); j++) overlay.setRGB(i, j, 0);
                if (image.getWidth() < 1000)
                    for (int i = 0; i < overlay.getWidth(); i++)
                        for (int j = Math.max(0, y - radius / 2 - 2); j < Math.min(y - radius / 2 + 2, overlay.getHeight()); j++)
                            overlay.setRGB(i, j, 0);
                for (int i = Math.max(x - radius - 2, 0); i < Math.min(x + 2, overlay.getWidth()); i++)
                    for (int j = Math.max(y - radius - 2, 0); j < Math.min(y + 2, overlay.getHeight()); j++)
                        overlay.setRGB(i, j, 0);

                if (e != null) {
                    x = (int) ((double) e.getX() * (double) image.getWidth() / (double) getWidth());
                    y = (int) ((double) e.getY() * (double) image.getHeight() / (double) getHeight());
                    if (x < 0) x = 0;
                    if (x >= image.getWidth()) x = image.getWidth();
                    if (y < 0) y = 0;
                    if (y >= image.getHeight()) y = image.getHeight();
                }

                Graphics2D g = overlay.createGraphics();
                g.setColor(Color.RED);
                g.drawOval(x - radius, y - radius, radius, radius);
                if (image.getHeight() < 1000)
                    g.drawLine(x - radius / 2, 0, x - radius / 2, overlay.getHeight());
                if (image.getWidth() < 1000)
                    g.drawLine(0, y - radius / 2, overlay.getWidth(), y - radius / 2);

                repaint();
            }
        });

        addMouseWheelListener(e -> {
            radius = Math.max(1, Math.min(100, radius + e.getWheelRotation()));
            getMouseMotionListeners()[0].mouseMoved(null);

        });

        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
    }

    public void draw(BinaryOperator<Integer> o) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int c = o.apply(i, j) % 256;
                if (drawBoolOnly) c = c > 128 ? 255 : 0;
                image.setRGB(i, j, new Color(c, c, c).getRGB());
            }
        }
        setImage(image);
    }

    private void mappedBoolize() {

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                if (c.equals(ol_one)) image.setRGB(i, j, one.getRGB());
                else if (c.equals(ol_zero)) image.setRGB(i, j, zero.getRGB());
                else {
                    LOGGER.error(c + " " + ol_one);
                    LOGGER.isAudioAllowed = false;
                }

            }
        }
        ol_one = one;
        ol_zero = zero;

    }

    public void comparableBoolize() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                c = (c.getRed()+c.getBlue()+c.getGreen())> 382 ? one:zero;
                image.setRGB(i, j, c.getRGB());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getWidth() / (double) getHeight() < image.getWidth() / (double) image.getHeight()) {
            view_width = (int) (getWidth() / zoom);
            view_height = (int) (image.getHeight() * getWidth() / zoom / image.getWidth());
        } else {
            view_width = (int) (image.getWidth() * getHeight() / zoom / image.getHeight());
            view_height = (int) (getHeight() / zoom);
        }
        if (drawBoolOnly) mappedBoolize();
//        g.drawImage(image, (getWidth() - view_width) / 2 - root_x, (getHeight() - view_height) / 2 - root_y, view_width, view_height, null);
//        g.drawImage(overlay, (getWidth() - view_width) / 2, (getHeight() - view_height) / 2, view_width, view_height, null);
        g.drawImage(image, root_x, root_y, view_width, view_height, null);
        g.drawImage(overlay, root_x, root_y, view_width, view_height, null);
    }

    public void setImage(BufferedImage _image) {
        image = _image;
        ol_one = Color.WHITE;
        ol_zero = Color.BLACK;
        comparableBoolize();
        resetOverlay();
    }

    private void resetOverlay() {
        overlay = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        for (int i = 0; i < overlay.getWidth(); i++)
            for (int j = 0; j < overlay.getHeight(); j++) overlay.setRGB(i, j, 0);
    }

    public void setDrawBoolOnly(boolean drawBoolOnly) {
        this.drawBoolOnly = drawBoolOnly;
    }

    public File getReference() {
        return reference;
    }

    public void setReference(File reference) {
        this.reference = reference;
    }

    public BufferedImage getImage() {
        Color o = getOne();
        Color z = getZero();
        setColorTheme(Color.WHITE, Color.BLACK);
        mappedBoolize();
        BufferedImage im = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        im.createGraphics().drawImage(image, 0, 0, null);
        setColorTheme(o, z);
        mappedBoolize();
        return im;
    }

    public void setRadius(int i) {
        radius = Math.max(i, 0);
    }

    public void setColorTheme(Color _one, Color _zero) {
        one = _one;
        zero = _zero;
    }

    public boolean isOnes() {
        return drawOnes;
    }

    public void toggleOnes() {
        drawOnes = !drawOnes;
    }

    public Color getZero() {
        return zero;
    }

    public Color getOne() {
        return one;
    }

    public void setZoom(double i) {
        zoom = i;
        repaint();
    }

    public void toggleMove() {
        moveImage = !moveImage;
    }

    public boolean isMove() {
        return moveImage;
    }

    public void setRoot_x(int root_x) {
        this.root_x = root_x;
        repaint();
    }

    public void setRoot_y(int root_y) {
        this.root_y = root_y;
        repaint();
    }

    public int getViewWidth() {
        return view_width;
    }

    public int getViewHeight() {
        return view_height;
    }
}
