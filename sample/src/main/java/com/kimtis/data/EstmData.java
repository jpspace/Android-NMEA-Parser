package com.kimtis.data;

import com.ppsoln.commons.position.LatLngAlt;
import com.ppsoln.commons.position.NEV;
import com.ppsoln.commons.position.XYZ;

import java.io.Serializable;

/**
 * Created by uiseok on 2016-09-25.
 */

public class EstmData implements Serializable{
    private LatLngAlt before_latLngAlt;
    private XYZ before_xyz;
    private NEV before_nev;

    private LatLngAlt modified_latLngAlt;
    private XYZ modified_xyz;
    private NEV modified_nev;

    private String time;

    public LatLngAlt getBefore_latLngAlt() {
        return before_latLngAlt;
    }

    public void setBefore_latLngAlt(LatLngAlt before_latLngAlt) {
        this.before_latLngAlt = before_latLngAlt;
    }

    public XYZ getBefore_xyz() {
        return before_xyz;
    }

    public void setBefore_xyz(XYZ before_xyz) {
        this.before_xyz = before_xyz;
    }

    public NEV getBefore_nev() {
        return before_nev;
    }

    public void setBefore_nev(NEV before_nev) {
        this.before_nev = before_nev;
    }

    public LatLngAlt getModified_latLngAlt() {
        return modified_latLngAlt;
    }

    public void setModified_latLngAlt(LatLngAlt modified_latLngAlt) {
        this.modified_latLngAlt = modified_latLngAlt;
    }

    public XYZ getModified_xyz() {
        return modified_xyz;
    }

    public void setModified_xyz(XYZ modified_xyz) {
        this.modified_xyz = modified_xyz;
    }

    public NEV getModified_nev() {
        return modified_nev;
    }

    public void setModified_nev(NEV modified_nev) {
        this.modified_nev = modified_nev;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
