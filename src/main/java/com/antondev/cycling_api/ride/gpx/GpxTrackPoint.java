package com.antondev.cycling_api.ride.gpx;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class GpxTrackPoint {

    @XmlAttribute
    private double lat;

    @XmlAttribute
    private double lon;

    @XmlElement(name = "ele", namespace = "http://www.topografix.com/GPX/1/1")
    private Double elevation;

    @XmlElement(name = "time", namespace = "http://www.topografix.com/GPX/1/1")
    private String time;

    private Integer heartRate;
    private Integer power;
}
