package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a goal, having target calories, steps, total workouts, and total time in HR red zone
public class Goal implements Writable {
    private int calorieGoal;
    private int stepGoal;
    private int totalWorkoutGoal;
    private int redZoneGoal;

    public Goal(int targetCalories, int targetSteps, int targetSessions, int targetRedZone) {
        this.calorieGoal = targetCalories;
        this.stepGoal = targetSteps;
        this.totalWorkoutGoal = targetSessions;
        this.redZoneGoal = targetRedZone;

    }

    public Goal() {
        this.calorieGoal = 0;
        this.stepGoal = 0;
        this.totalWorkoutGoal = 0;
        this.redZoneGoal = 0;
    }

    // getters

    // EFFECTS: returns the total calorie goal
    public int getCalorieGoal() {
        return calorieGoal;
    }

    // EFFECTS: returns the total step goal
    public int getStepGoal() {
        return stepGoal;
    }

    // EFFECTS: returns the total workout goal
    public int getTotalWorkoutGoal() {
        return totalWorkoutGoal;
    }

    // EFFECTS: returns the total red zone goal
    public int getRedZoneGoal() {
        return redZoneGoal;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calorieGoal", calorieGoal);
        json.put("stepGoal", stepGoal);
        json.put("workoutGoal", totalWorkoutGoal);
        json.put("redZoneGoal", redZoneGoal);
        return json;
    }
}


