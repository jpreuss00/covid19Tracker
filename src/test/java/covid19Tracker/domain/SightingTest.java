package covid19Tracker.domain;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SightingTest {

    private Sighting sighting;
    private Sighting otherSighting;


    @Test
    public void Sighting_should_returnFalse_for_similarPlace_and_differentTime() throws IOException {
        otherSighting = new Sighting(52.1313922, 13.216249399999999, new java.util.Date());
        sighting = new Sighting(52.13139221, 13.216249399999999, new java.util.Date(System.currentTimeMillis()-24*60*60*1000));

        System.out.println("First Test: ");
        assertFalse(sighting.closeTo(otherSighting));
    }

    @Test
    public void Sighting_should_returnFalse_for_differentPlace_and_similarTime() throws IOException {
        otherSighting = new Sighting(52.1313922, 13.216249399999999, new java.util.Date());
        sighting = new Sighting(52.1313922, 5.216249399999999, new java.util.Date(System.currentTimeMillis()-3*60*1000));

        System.out.println("Second Test: ");
        assertFalse(sighting.closeTo(otherSighting));
    }

    @Test
    public void Sighting_should_returnFalse_for_differentPlace_and_differentTime() throws IOException {
        otherSighting = new Sighting(52.1313922, 13.216249399999999, new java.util.Date());
        sighting = new Sighting(52.1313922, 5.216249399999999, new java.util.Date(System.currentTimeMillis()-24*60*60*1000));

        System.out.println("Third Test: ");
        assertFalse(sighting.closeTo(otherSighting));
    }

    @Test
    public void Sighting_should_returnTrue_for_similarPlace_and_similarTime() throws IOException {
        otherSighting = new Sighting(52.1313922, 13.216249399999999, new java.util.Date());
        sighting = new Sighting(52.13139221, 13.216249399999999, new java.util.Date(System.currentTimeMillis()-3*60*1000));

        System.out.println("Fourth Test: ");
        assertTrue(sighting.closeTo(otherSighting));
    }

}
