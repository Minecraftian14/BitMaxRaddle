package org.that.gui;

//import main.annotatedLogger.Format;
//import main.annotatedLogger.LOG;

import main.annotatedLogger.Format;
import main.annotatedLogger.LOG;
import org.that.util.ButtonFactory;
import org.that.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static main.utlities.ConsoleColors.ANSI.*;

public class Body {

    JFrame frame;
    GridBagLayout layout;
    JButton close;
    JPanel pan_tool;
    DrawingPane pan_disp;
    JPanel pan_plat;

    public Body() {
        init();
        touchUp();
        setLayouts();
        makeFinal();
    }

    @Format({RED + "b: ", GREEN + "n"})
    private void init() {
        frame = new JFrame("BitMap Viewer");
        frame.setLayout(layout = new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LOG.prt("Constructor", "Initialised Frame");

        close = ButtonFactory.getCloser(frame);

        pan_disp = new DrawingPane();

        pan_tool = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        pan_tool.add(ButtonFactory.get("New", new Controls.NewImage(frame, pan_disp)));
        pan_tool.add(ButtonFactory.get("Open", new Controls.OpenImage(frame, pan_disp)));
        pan_tool.add(ButtonFactory.get("Delete", new Controls.DeleteImage(frame, pan_disp)));
        pan_tool.add(ButtonFactory.get("Save", new Controls.SaveImage(frame, pan_disp)));

        pan_plat = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        pan_plat.add(ButtonFactory.get("Zero", new Controls.ToggleOnesMethod(pan_disp)));
//        pan_plat.add(ButtonFactory.get("Line",null));
        pan_plat.add(ButtonFactory.get("Theme", new Controls.AskColorTheme(frame, pan_disp)));
        pan_plat.add(ButtonFactory.get("Random", new Controls.RandomImage(pan_disp)));
        pan_plat.add(ButtonFactory.get("Zoom", new Controls.ZoomImage(frame, pan_disp)));
        pan_plat.add(ButtonFactory.get("Move", new Controls.MoveImage(frame, pan_disp)));
        LOG.prt("Constructor", "Initialised Panels");

    }

    @Format({RED + "b: ", CYAN + "n"})
    private void touchUp() {
        frame.setUndecorated(true);

        frame.getContentPane().setBackground(new Color(168, 255, 242));
        close.setBackground(frame.getContentPane().getBackground());
        pan_tool.setBackground(frame.getContentPane().getBackground());
        pan_disp.setBackground(new Color(240, 255, 252));
        pan_plat.setBackground(new Color(109, 255, 213));
        LOG.prt("Constructor", "Touch-up Done");
    }

    @Format({RED + "b: ", YELLOW + "n"})
    private void setLayouts() {

        layout.setConstraints(close/*   */, new GBC().setAnchor(GBC.EAST).setGridx(1).setInsets(new Insets(5, 5, 5, 5)));
        layout.setConstraints(pan_tool/**/, new GBC().setFill(GBC.BOTH/*  */).setWeighty(1).setAnchor(GBC.NORTH).setWeightx(1));
        layout.setConstraints(pan_disp/**/, new GBC().setFill(GBC.HORIZONTAL).setWeighty(1).setAnchor(GBC.CENTER).setGridy(1).setGridwidth(2).setIpady(250));
        layout.setConstraints(pan_plat/**/, new GBC().setFill(GBC.HORIZONTAL).setWeighty(1).setAnchor(GBC.SOUTH).setGridy(2).setGridwidth(2));
        LOG.prt("Constructor", "Layouts set successfully");

    }

    @Format({RED + "b: ", BLUE + "n"})
    private void makeFinal() {

        frame.add(pan_tool);
        frame.add(close);
        frame.add(pan_disp);
        frame.add(pan_plat);

        frame.pack();
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 20, 20));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        pan_disp.repaint();
        LOG.prt("Constructor", "Application Window Started");

    }
}
