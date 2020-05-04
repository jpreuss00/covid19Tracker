package covid19Tracker.application;

import covid19Tracker.domain.Sighting;

import java.util.ArrayList;
import java.util.List;

public class SightingService {

    private SightingRepository sightingRepository;

    public SightingService(SightingRepository sightingRepository) {
        this.sightingRepository = sightingRepository;
    }

    public boolean isEndangered(Sighting sighting){
        if (sightingRepository.getSightingsCloseTo(sighting) != null) {
            List<Sighting> sightings = new ArrayList<>(sightingRepository.getSightingsCloseTo(sighting));
            for (Sighting compareSighting : sightings) {
                if (sighting.closeTo(compareSighting)) {
                    return true;
                }
            }
        }
        return false;
    }
}
