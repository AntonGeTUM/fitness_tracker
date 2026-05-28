package com.antondev.cycling_api.ride.gpx;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class GpxTrackSegment {

    @XmlElement(name = "trkpt", namespace = "http://www.topografix.com/GPX/1/1")
    private List<GpxTrackPoint> trackPoints = new ArrayList<>();
}
