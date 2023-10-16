package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class FitnessTracker implements Writable {
    private List<Activity> activities; // all activities added
    private Goal goal; // goal



    // EFFECTS: constructs a new fitness tracker with an empty list of goals and empty activity
    public FitnessTracker() {
        activities = new ArrayList<>();
        goal = new Goal();
    }


    // EFFECTS: returns the total calories burned throughout all activities
    public int getCalorieProgress() {
        int totalCalories = 0;
        for (Activity activity : activities) {
            totalCalories += activity.getCalories();
        }
        return goal.getCalorieGoal() - totalCalories;
    }


    // EFFECTS: returns the remaining amount of calories needed to complete goal
    public int getStepProgress() {
        int totalSteps = 0;
        for (Activity activity : activities) {
            totalSteps += activity.getSteps();
        }
        return goal.getStepGoal() - totalSteps;
    }


    // EFFECTS: returns the remaining time needed in the red zone for goal
    public int getRedZoneProgress() {
        int totalRedZone = 0;
        for (Activity activity : activities) {
            totalRedZone += activity.getRedZone();
        }
        return goal.getRedZoneGoal() - totalRedZone;
    }


    // EFFECTS: returns the remaining number of workouts in goal
    public int getWorkoutProgress() {
        int totalWorkouts = 0;
        for (Activity activity : activities) {
            totalWorkouts++;
        }
        return goal.getTotalWorkoutGoal() - totalWorkouts;
    }

    public String getSummary() {
        String summary = null;
        summary = "You have " + getCalorieProgress() + " calories remaining in your calorie goal, " + getStepProgress()
                + " steps remaining in your step goal, " + getWorkoutProgress()
                + " workouts remaining in your total workout goal, and " + getRedZoneProgress()
                + " minutes remaining in your red zone goal!";
        return summary;
    }

    //MODIFIES: this
    // EFFECTS: adds activity to FitnessTracker
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    // MODIFIES: this
    // EFFECTS: updates the goal in fitness tracker
    public void updateGoal(Goal newGoal) {
        goal = newGoal;
    }


    //EFFECTS: prints the user's summary of total activities
    public String printNames() {
        StringBuilder message = new StringBuilder();
        for (Activity activity : activities) {
            message.append(activity.getActivityName()).append(": ").append(activity.getCalories()).append(
                    " calories, ").append(activity.getSteps()).append(" steps, ").append(activity.getRedZone()).append(
                    " minutes in red zone, and workout number ").append(activity.getActivityNumber()).append("!");
        }
        System.out.println(message.toString());
        return message.toString();
    }

    public String printName(Activity activity) {
        String summary = null;
        summary = activity.getActivityName() + ": " + activity.getCalories() + " calories, " + activity.getSteps()
                + " steps, " + activity.getRedZone() + " minutes in red zone, and workout number "
                + activity.getActivityNumber() + "!";

        return summary;
    }

    public String printGoal(Goal goal) {
        String thisGoal = null;
        thisGoal = "Your current goal: " + goal.getCalorieGoal() + " calories, " + goal.getStepGoal() + " steps, "
                + goal.getRedZoneGoal() + " minutes in the red zone, and " + goal.getTotalWorkoutGoal() + " workouts!";
        return thisGoal;
    }


    // getters


    // EFFECTS: returns all the activities in the list
    public List<Activity> getActivities() {
        return activities;
    }

    // EFFECTS: returns the total number of activities in list
    public int getNumberActivities() {
        return activities.size();
    }

    public Goal getGoals() {
        return goal;
    }

    // EFFECTS: returns the calorie goal
    public int getCalorieGoal() {
        return goal.getCalorieGoal();
    }

    // EFFECTS: returns the step goal
    public int getStepGoal() {
        return goal.getStepGoal();
    }

    // EFFECTS: returns the total workout goal
    public int getWorkoutGoal() {
        return goal.getTotalWorkoutGoal();
    }

    // EFFECTS: returns the total red zone goal
    public int getRedZoneGoal() {
        return goal.getRedZoneGoal();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("goal", goal.toJson());
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: returns activities listed in fitness tracker as json array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity activity : activities) {
            jsonArray.put(activity.toJson());
        }
        return jsonArray;
    }



}
