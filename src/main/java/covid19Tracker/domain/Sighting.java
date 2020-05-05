package covid19Tracker.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sighting {

    private final static java.util.logging.Logger logr = java.util.logging.Logger.getLogger("Logger");

    private double latitude;
    private double longitude;

    private Date instant;

    public Sighting(double latitude, double longitude, Date instant) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.instant = instant;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Date getInstant() {
        return instant;
    }

    public boolean closeTo(Sighting otherSighting) {
        double secondLatitude = otherSighting.getLatitude();
        double secondLongitude = otherSighting.getLongitude();
        Date secondInstant = otherSighting.getInstant();

        return (comparePlaces(latitude, longitude, secondLatitude, secondLongitude) && compareTimes(instant, secondInstant));
    }

    public boolean comparePlaces(double firstLatitude, double firstLongitude, double secondLatitude, double secondLongitude) {
        double earthRadius = 6371000;
        double dLat = Math.toRadians(secondLatitude - firstLatitude);
        double dLng = Math.toRadians(secondLongitude - firstLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(firstLatitude)) * Math.cos(Math.toRadians(secondLatitude)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);
        logr.finer("latitude #1: " + firstLatitude + ", longitude #1 : " + firstLongitude + ", latitude #2 : " + secondLatitude + ", longitude #2 : " + secondLongitude + ".");
        logr.finer(" distance: " + dist);
        return dist <= 5;
    }

    public boolean compareTimes(Date firstInstant, Date secondInstant) {
        logr.finer("instant #1 : " + firstInstant + ", instant #2 : " + secondInstant + ".");
        logr.finer(" dist: " + TimeUnit.MILLISECONDS.toMinutes(Math.abs(firstInstant.getTime() - secondInstant.getTime())) + " minutes");
        return (TimeUnit.MILLISECONDS.toMinutes(Math.abs(firstInstant.getTime() - secondInstant.getTime()))) <= 5;
    }
}
