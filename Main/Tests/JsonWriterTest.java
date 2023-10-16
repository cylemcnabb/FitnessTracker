package persistence;

import model.Activity;
import model.FitnessTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            JsonWriter writer = new JsonWriter("./data/GoalProgressTracker.json");
            writer.open();
            writer.write(fitnessTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/GoalProgressTracker.json");
            fitnessTracker = reader.read();
            assertEquals(0, fitnessTracker.getNumberActivities());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            FitnessTracker fitnessTracker = new FitnessTracker();
            fitnessTracker.addActivity(new Activity(100, 1000, 10,
                    "walk", 1));
            fitnessTracker.addActivity(new Activity(2000, 10000, 10,
                    "marathon", 2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFitnessTracker.json");
            writer.open();
            writer.write(fitnessTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFitnessTracker.json");
            fitnessTracker = reader.read();
            assertEquals(2, fitnessTracker.getNumberActivities());
            List<Activity> activities = fitnessTracker.getActivities();
            assertEquals(2, activities.size());
            checkActivity(100, 1000, 10, "walk", 1,activities.get(0));
            checkActivity(2000,10000, 10, "marathon", 2, activities.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
