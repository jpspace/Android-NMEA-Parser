package com.kimtis.data;

import com.ppsoln.commons.position.AzElAngle;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-25.
 */

public class SkyPlotData implements Serializable {
    private int prn;
    private AzElAngle azElAngle;


    public SkyPlotData(int prn, AzElAngle azElAngle) {
        this.prn = prn;
        this.azElAngle = azElAngle;
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
