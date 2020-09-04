package org.that.util;

import java.awt.*;

public class GBC extends GridBagConstraints {

    public GBC() {
        super();
    }

    public GBC setGridx(int gridx) {
        this.gridx = gridx;
        return this;
    }

    public GBC setGridy(int gridy) {
        this.gridy = gridy;
        return this;
    }

    public GBC setGridwidth(int gridwidth) {
        this.gridwidth = gridwidth;
        return this;
    }

    public GBC setGridheight(int gridheight) {
        this.gridheight = gridheight;
        return this;
    }

    public GBC setWeightx(double weightx) {
        this.weightx = weightx;
        return this;
    }

    public GBC setWeighty(double weighty) {
        this.weighty = weighty;
        return this;
    }

    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GBC setInsets(Insets insets) {
        this.insets = insets;
        return this;
    }

    public GBC setIpadx(int ipadx) {
        this.ipadx = ipadx;
        return this;
    }

    public GBC setIpady(int ipady) {
        this.ipady = ipady;
        return this;
    }
}
