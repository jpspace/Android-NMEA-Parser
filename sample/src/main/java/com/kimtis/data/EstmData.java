package com.kimtis.data;

import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.NEV;
import com.ppsoln.commons.utility.TransformationFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by uiseok on 2016-09-25.
 */

public class EstmData implements Serializable{

    private LatLngAlt before_latLngAlt;
    private LatLngAlt modified_latLngAlt;
    private Date time;


    public NEV getNEV(LatLngAlt lla){
        return TransformationFactory.toTopo(TransformationFactory.toXyz(lla),
                CachedData.getInstance().getDatum());
    }

    public LatLngAlt getBefore_latLngAlt() {
        return before_latLngAlt;
    }

    public void setBefore_latLngAlt(LatLngAlt before_latLngAlt) {
        this.before_latLngAlt = before_latLngAlt;
    }

    public LatLngAlt getModified_latLngAlt() {
        return modified_latLngAlt;
    }

    public void setModified_latLngAlt(LatLngAlt modified_latLngAlt) {
        this.modified_latLngAlt = modified_latLngAlt;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
