package uiseok.data;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-19.
 */
public class NEVData implements Serializable {
    public double n;
    public double e;
    public double v;
    public int type;

    public NEVData(double n, double e, double v, int type) {
        this.n = n;
        this.e = e;
        this.v = v;
        this.type = type;
    }
}
