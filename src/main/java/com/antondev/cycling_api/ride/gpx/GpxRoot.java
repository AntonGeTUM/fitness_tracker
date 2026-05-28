package com.antondev.cycling_api.ride.gpx;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "gpx", namespace = "http://www.topografix.com/GPX/1/1")
@XmlAccessorType(XmlAccessType.FIELD)
public class GpxRoot {

    @XmlElement(name = "trk", namespace = "http://www.topografix.com/GPX/1/1")
    private List<GpxTrack> tracks = new ArrayList<>();
}