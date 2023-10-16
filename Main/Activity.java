package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a fitness activity having  calories, steps, heart rate, and name of activity
public class Activity implements Writable {
    private int calories; // total calories burned during activity
    private int steps; // total steps during activity
    private int redZone; // time spent in heart rate red zone, over 90 bpm
    private String activityName; // name of activity completed
    private int activityNumber; // count of activity completed


    public Activity(int caloriesBurned, int stepsTaken, int redZoneTime, String name, int activityNumber) {
        this.calories = caloriesBurned;
        this.steps = stepsTaken;
        this.redZone = redZoneTime;
        this.activityName = name;
        this.activityNumber = activityNumber;
    }

    public int getCalories() {
        return calories;
    }

    // getters
    public String getActivityName() {
        return activityName;
    }

    public int getSteps() {
        return steps;
    }

    public int getRedZone() {
        return redZone;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calories", calories);
        json.put("steps", steps);
        json.put("red zone", redZone);
        json.put("activity name", activityName);
        json.put("activity number", activityNumber);
        return json;
    }

}
