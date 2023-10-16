package ui;

import model.Activity;
import model.Goal;
import model.FitnessTracker;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FitnessTrackerApp {
    // citation: similar to Account ui
    private static final String JSON_STORE = "./data/testReaderGeneralFitnessTracker1.json1";
    private Scanner input;
    private int number = 1;
    private FitnessTracker fitnessTracker;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;





    // EFFECTS: creates fitness tracker and runs app
    public FitnessTrackerApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        fitnessTracker = new FitnessTracker();
        runFitnessTracker();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFitnessTracker() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> calorie progress");
        System.out.println("\tst -> step progress");
        System.out.println("\tt -> total workout progress");
        System.out.println("\trz -> time in red zone progress");
        System.out.println("\ta -> add activity");
        System.out.println("\tg -> add goal");
        System.out.println("\tca -> check activities");
        System.out.println("\ts -> save fitness tracker to file");
        System.out.println("\tl -> load fitness tracker to file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addActivity();
        } else if (command.equals("ca")) {
            fitnessTracker.printNames();
        } else if (command.equals("st")) {
            displayStepProgress();
        } else if (command.equals("c")) {
            displayCalorieProgress();
        } else if (command.equals("rz")) {
            displayRedZoneProgress();
        } else if (command.equals("t")) {
            displayWorkoutProgress();
        } else if (command.equals("g")) {
            addGoal();
        } else if (command.equals("s")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a new activity with the given input
    private void addActivity() {
        System.out.println("\nHow many calories did you burn?");
        int calories = input.nextInt();
        System.out.println("\nHow many steps did you take?");
        int steps = input.nextInt();
        System.out.println("\nHow long were you in the red zone?");
        int redZone = input.nextInt();
        System.out.println("\nWhat activity did you do?");
        String name = input.next();

        Activity activity = new Activity(calories, steps, redZone, name, number);

        fitnessTracker.addActivity(activity);
        number++;
    }


    //MODIFIES: this
    //EFFECTS: adds a new goal to fitness tracker with given input
    private void addGoal() {
        System.out.println("\nWhat is your calorie goal?");
        int calories = input.nextInt();
        System.out.println("\nWhat is your step goal?");
        int steps = input.nextInt();
        System.out.println("\nHow long do you want to spend in the red zone?");
        int redZone = input.nextInt();
        System.out.println("\nHow many workouts do you want to reach?");
        int sessions = input.nextInt();

        Goal newGoal = new Goal(calories, steps, sessions, redZone);
        fitnessTracker.updateGoal(newGoal);
    }

    //EFFECTS: prints the user's progress in the step goal
    private void displayStepProgress() {
        int remainingSteps = fitnessTracker.getStepProgress();
        System.out.println("You have " + remainingSteps + " steps remaining in your step goal!");
    }

    //EFFECTS: prints the user's progress in the calorie goal
    private void displayCalorieProgress() {
        int remainingCalories = fitnessTracker.getCalorieProgress();
        System.out.println("You have " + remainingCalories + " calories remaining in your calorie goal!");
    }

    //EFFECTS: prints the user's progress in the red zone goal
    private void displayRedZoneProgress() {
        int remainingRedZone = fitnessTracker.getRedZoneProgress();
        System.out.println("You have " + remainingRedZone + " minutes remaining in your red zone goal!");
    }

    //EFFECTS: prints the user's progress in the total workout goal
    private void displayWorkoutProgress() {
        int remainingWorkouts = fitnessTracker.getWorkoutProgress();
        System.out.println("You have " + remainingWorkouts + " workouts remaining in your total workout goal!");
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(fitnessTracker);
            jsonWriter.close();
            System.out.println("Your Fitness Tracker has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            fitnessTracker = jsonReader.read();
            System.out.println("Loaded your Fitness Tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}




