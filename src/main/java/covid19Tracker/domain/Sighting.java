package covid19Tracker.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sighting {
    private double latitude;
    private double longitude;

    private Date instant;

    public Sighting(double latitude, double longitude, Date instant){
        this.latitude = latitude;
        this.longitude = longitude;
        this.instant = instant;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
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

    public boolean comparePlaces(double firstLatitude, double firstLongitude, double secondLatitude, double secondLongitude){
        double earthRadius = 6371000;
        double dLat = Math.toRadians(secondLatitude-firstLatitude);
        double dLng = Math.toRadians(secondLongitude-firstLongitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(firstLatitude)) * Math.cos(Math.toRadians(secondLatitude)) * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        System.out.printf("latitude #1: %s , longitude #1 : %s , latitude #2 : %s , longitude #2 : %s", firstLatitude, firstLongitude, secondLatitude, secondLongitude);
        System.out.println(" dist: " + dist);
        return dist <= 5;
    }

    public boolean compareTimes(Date firstInstant, Date secondInstant){
        System.out.printf("instant #1 : %s , instant #2 : %s", firstInstant, secondInstant);
        System.out.println(" dist: " + TimeUnit.MILLISECONDS.toMinutes(Math.abs(firstInstant.getTime() - secondInstant.getTime())) + " minutes");
        return (TimeUnit.MILLISECONDS.toMinutes(Math.abs(firstInstant.getTime() - secondInstant.getTime()))) <= 5;
    }
}
