package persistence;


import model.Activity;
import model.FitnessTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFitnessTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFitnessTracker.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            assertEquals(0, fitnessTracker.getNumberActivities());
            assertEquals(0, fitnessTracker.getRedZoneGoal());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFitnessTracker() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralFitnessTracker.json");
        try {
            FitnessTracker fitnessTracker = reader.read();
            assertEquals(2, fitnessTracker.getNumberActivities());
            List<Activity> activities = fitnessTracker.getActivities();
            assertEquals(2, activities.size());
            checkActivity(100, 1000, 10, "walk", 1,activities.get(0));
            checkActivity(2000,10000, 10, "marathon", 2, activities.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}
