package covid19Tracker.application;

import covid19Tracker.domain.Sighting;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SightingServiceTest {

    private Sighting sighting;
    private SightingRepository sightingRepository;
    private SightingService sightingService;

    @Before
    public void setUp(){
        this.sighting = Mockito.mock(Sighting.class);
        this.sightingRepository = Mockito.mock(SightingRepository.class);
        this.sightingService = new SightingService(sightingRepository);
    }

    @Test
    public void SightingService_should() throws IOException {
        List<Sighting> sightings = new ArrayList<>();
        Sighting compareSighting = new Sighting(52.1313922, 13.216249399999999, new java.util.Date());

        Mockito.doReturn(sightings).when(sightingRepository).getSightingsOutOfDBInNear(sighting);
        Mockito.doReturn(true).when(sighting).closeTo(compareSighting);

        sightingService.isEndangered(sighting);

        //Mockito.verify(sightingService, didReturn(true)).
    }

}
