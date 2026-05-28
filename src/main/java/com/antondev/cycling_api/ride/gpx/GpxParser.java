package com.antondev.cycling_api.ride.gpx;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
@Component
public class GpxParser {

    public GpxRoot parse(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();

            // JAXB für Struktur
            JAXBContext context = JAXBContext.newInstance(GpxRoot.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GpxRoot gpx = (GpxRoot) unmarshaller.unmarshal(new ByteArrayInputStream(bytes));

            // DOM für Extensions
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(bytes));
            NodeList trkpts = doc.getElementsByTagNameNS("http://www.topografix.com/GPX/1/1", "trkpt");

            List<GpxTrackPoint> points = extractTrackPoints(gpx);

            for (int i = 0; i < trkpts.getLength() && i < points.size(); i++) {
                Element trkpt = (Element) trkpts.item(i);
                NodeList hrNodes = trkpt.getElementsByTagNameNS(
                        "http://www.garmin.com/xmlschemas/TrackPointExtension/v1", "hr");
                NodeList powerNodes = trkpt.getElementsByTagNameNS(
                        "http://www.garmin.com/xmlschemas/TrackPointExtension/v1", "power");

                if (hrNodes.getLength() > 0) {
                    points.get(i).setHeartRate(Integer.parseInt(hrNodes.item(0).getTextContent()));
                }
                if (powerNodes.getLength() > 0) {
                    points.get(i).setPower(Integer.parseInt(powerNodes.item(0).getTextContent()));
                }
            }

            return gpx;
        } catch (Exception e) {
            log.error("GPX parse error: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Invalid GPX file");
        }
    }

    public List<GpxTrackPoint> extractTrackPoints(GpxRoot gpx) {
        return gpx.getTracks().stream()
                .flatMap(t -> t.getSegments().stream())
                .flatMap(s -> s.getTrackPoints().stream())
                .toList();
    }
}