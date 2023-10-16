package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Activity;
import model.FitnessTracker;
import model.Goal;
import org.json.*;

// * INSPIRED BY SERIALIZATION DEMO *

public class JsonReader {
    private String source;

    // * inspired by demo*
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads source file as string and returns it
    // * Inspired by demo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }


        return contentBuilder.toString();
    }



    // EFFECTS: reads fitness tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FitnessTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFitnessTracker(jsonObject);
    }

    // EFFECTS: parses fitness tracker from JSON object and returns it
    private FitnessTracker parseFitnessTracker(JSONObject jsonObject) {
        FitnessTracker fitnessTracker = new FitnessTracker();
        addActivities(fitnessTracker, jsonObject);
        addGoal(fitnessTracker, jsonObject.getJSONObject("goal"));
        return fitnessTracker;
    }

    // MODIFIES: fitnessTracker
    // EFFECTS: parses activities from JSON object and adds them to workroom
    private void addActivities(FitnessTracker fitnessTracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(fitnessTracker, nextActivity);
        }
    }

    // MODIFIES: activityTracker
    // EFFECTS: parses activity from JSON object and adds it to workroom
    private void addActivity(FitnessTracker fitnessTracker, JSONObject jsonObject) {
        int calories = jsonObject.getInt("calories");
        int steps = jsonObject.getInt("steps");
        int redZone = jsonObject.getInt("red zone");
        String activityName = jsonObject.getString("activity name");
        int activityNumber = jsonObject.getInt("activity number");
        Activity activity = new Activity(calories, steps, redZone, activityName, activityNumber);
        fitnessTracker.addActivity(activity);
    }

    // MODIFIES: activityTracker
    // EFFECTS: parses goal from JSON object and adds it to workroom
    private void addGoal(FitnessTracker fitnessTracker, JSONObject jsonObject) {
        int calorieGoal = jsonObject.getInt("calorieGoal");
        int stepGoal = jsonObject.getInt("stepGoal");
        int totalWorkoutGoal = jsonObject.getInt("workoutGoal");
        int redZoneGoal = jsonObject.getInt("redZoneGoal");
        Goal goal = new Goal(calorieGoal, stepGoal, totalWorkoutGoal, redZoneGoal);
        fitnessTracker.updateGoal(goal);
    }
}


