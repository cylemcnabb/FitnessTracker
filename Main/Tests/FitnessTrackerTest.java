package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class FitnessTrackerTest {
    private FitnessTracker testFitnessTracker;


    @BeforeEach
    void runBefore() {
        testFitnessTracker = new FitnessTracker();


    }


    @Test
    void testConstructor() {
        testFitnessTracker.addActivity(new Activity(1000, 1000, 1000,
                "Golf", 1));
        testFitnessTracker.addActivity(new Activity(100, 100, 100, "Run", 100));
        testFitnessTracker.updateGoal(new Goal(5000, 5000, 5000,
                5000));
        assertEquals(5000, testFitnessTracker.getCalorieGoal());
        assertEquals(5000, testFitnessTracker.getStepGoal());
        assertEquals(5000, testFitnessTracker.getWorkoutGoal());
        assertEquals(5000, testFitnessTracker.getRedZoneGoal());
        System.out.println(testFitnessTracker.getActivities());

    }


    @Test
    void testAddMultipleActivities() {
        testFitnessTracker.addActivity(new Activity(100, 100, 100, "swim",
                4));
        testFitnessTracker.addActivity(new Activity(1000, 50, 20, "bike",
                5));
        testFitnessTracker.addActivity(new Activity(2000, 10000, 30, "Golf",
                6));
        assertEquals(-3100, testFitnessTracker.getCalorieProgress());
        testFitnessTracker.updateGoal(new Goal(3100, 0, 0, 0));
        assertEquals(0, testFitnessTracker.getCalorieProgress());
    }


    @Test
    void testGetCalorieProgress() {
        testFitnessTracker.updateGoal(new Goal(6900, 5, 5, 5));
        assertEquals(6900, testFitnessTracker.getCalorieProgress());
        testFitnessTracker.addActivity(new Activity(1000, 100, 20, "Skiing",
                4));
        assertEquals(5900, testFitnessTracker.getCalorieProgress());
    }


    @Test
    void testGetStepProgress() {
        testFitnessTracker.updateGoal(new Goal(39000, 39000, 0, 0));
        assertEquals(39000, testFitnessTracker.getStepProgress());
        testFitnessTracker.addActivity(new Activity(1000, 100, 20,
                "Skiing", 5));
        assertEquals(38900, testFitnessTracker.getStepProgress());
    }


    @Test
    void testGetRedZoneProgress() {
        testFitnessTracker.updateGoal(new Goal(0, 0, 0, 235));
        assertEquals(235, testFitnessTracker.getRedZoneProgress());
        testFitnessTracker.addActivity(new Activity(1000, 100, 20, "Skiing",
                5));
        assertEquals(215, testFitnessTracker.getRedZoneProgress());
    }

    @Test
    void testGetWorkoutProgress() {
        testFitnessTracker.updateGoal(new Goal(0, 0, 6, 0));
        assertEquals(6, testFitnessTracker.getWorkoutProgress());
        testFitnessTracker.addActivity(new Activity(1000, 100, 20, "Skiing",
                5));
        assertEquals(5, testFitnessTracker.getWorkoutProgress());
    }

    @Test
    void testPrintNames() {
        testFitnessTracker.addActivity(new Activity(100, 100, 100,
                "Golf", 100));
        assertTrue("Golf: 100 calories, 100 steps, 100 minutes in red zone, and workout number 100!"
                .equals(testFitnessTracker.printNames()));

    }

    @Test
    void testPrintName() {
        Activity activity1 = new Activity(1000, 1000, 100, "Lift"
                , 10);
        testFitnessTracker.addActivity(activity1);
        assertTrue("Lift: 1000 calories, 1000 steps, 100 minutes in red zone, and workout number 10!"
                .equals(testFitnessTracker.printName(activity1)));
    }

    @Test
    void testPrintGoal() {
        Goal goal1 = new Goal(10, 10, 10, 10);
        testFitnessTracker.updateGoal(goal1);
        assertTrue("Your current goal: 10 calories, 10 steps, 10 minutes in the red zone, and 10 workouts!"
                .equals(testFitnessTracker.printGoal(goal1)));
    }

    @Test
    void testPrintSummary() {
        Activity activity1 = new Activity(100, 100, 100, "golf",
                1);
        Goal goal1 = new Goal(100, 100, 100, 100);
        testFitnessTracker.addActivity(activity1);
        testFitnessTracker.updateGoal(goal1);
        System.out.println(testFitnessTracker.getSummary());
        assertEquals("You have 0 calories remaining in your calorie goal, 0 steps remaining in your " +
                "step goal, 99 workouts remaining in your total workout goal, and 0 minutes remaining in " +
                "your red zone goal!", testFitnessTracker.getSummary());
    }

    @Test
    void testGetGoals() {
        Goal goal1 = new Goal();
        testFitnessTracker.updateGoal(goal1);
        assertEquals(goal1, testFitnessTracker.getGoals());
    }

}