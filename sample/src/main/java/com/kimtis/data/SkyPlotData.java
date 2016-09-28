package com.kimtis.data;

import com.ppsoln.commons.position.AzElAngle;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-25.
 */

public class SkyPlotData implements Serializable {
    private int prn;
    private AzElAngle azElAngle;
    private boolean havePRC;

    public boolean isHavePRC() {
        return havePRC;
    }

    public void setHavePRC(boolean havePRC) {
        this.havePRC = havePRC;
    }

    public SkyPlotData(int prn, AzElAngle azElAngle, boolean havePRC) {
        this.prn = prn;
        this.azElAngle = azElAngle;
        this.havePRC = havePRC;
    }

    public int getPrn() {
        return prn;
    }

    public void setPrn(int prn) {
        this.prn = prn;
    }

    public AzElAngle getAzElAngle() {
        return azElAngle;
    }

    public void setAzElAngle(AzElAngle azElAngle) {
        this.azElAngle = azElAngle;
    }
}
