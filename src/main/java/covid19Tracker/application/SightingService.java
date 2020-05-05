package covid19Tracker.application;

import covid19Tracker.domain.Sighting;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SightingService {

    private final static Logger logr = Logger.getLogger(SightingService.class.getName());

    private SightingRepository sightingRepository;

    public SightingService(SightingRepository sightingRepository) {
        this.sightingRepository = sightingRepository;
    }

    public boolean isEndangered(Sighting sighting){
        if (sightingRepository.getSightingsCloseTo(sighting) != null) {
            List<Sighting> sightings = new ArrayList<>(sightingRepository.getSightingsCloseTo(sighting));
            for (Sighting compareSighting : sightings) {
                if (sighting.closeTo(compareSighting)) {
                    logr.info("An endangered Sighting has been found in the near.");
                    return true;
                }
            }
        }
        logr.info("There has been no endangered sighting found in the near.");
        return false;
    }
}
