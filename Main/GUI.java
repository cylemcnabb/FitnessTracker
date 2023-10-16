package ui;

import model.Activity;
import model.Event;
import model.EventLog;
import model.FitnessTracker;
import model.Goal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private FitnessTracker fitnessTracker;
    private JLabel welcome;
    private int number = 1;
    private DefaultListModel<String> activityModel = new DefaultListModel<>();
    private JList<String> activityList = new JList<>(activityModel);
    private JScrollPane scrollActivities = new JScrollPane(activityList);
    private String currentGoal = "You haven't entered a goal yet!";
    private String currentProgress = "No progress to report. Keep working!";
    private String saveMessage = null;
    private String loadMessage = null;
    private JLabel loadState = new JLabel(loadMessage);
    private JLabel saveState = new JLabel(saveMessage);
    private JLabel label = new JLabel(currentGoal);
    private JLabel summary = new JLabel(currentProgress);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/GoalProgressTracker.json";
    private EventLog log;


    // EFFECTS: constructs a new GUI with a splash screen, before loading into main GUI with functionality
    public GUI() {
        super("Fitness Tracker");
        SplashScreen splash = new SplashScreen();

        setSize(1000, 1000);
        setBackground(Color.blue);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 50, 20, 50));
        fitnessTracker = new FitnessTracker();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        log = EventLog.getInstance();
        windowClose(log);
        welcomeGreeting();
        addButtons();
        add(scrollActivities, BorderLayout.SOUTH);
        scrollActivities.setBackground(Color.green);
        add(label);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: prints log when the GUI is closed
    private void windowClose(EventLog log) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : log) {
                    System.out.println(event);
                }
                System.exit(0);
            }
        });
    }


    // MODIFIES: this
    // EFFECTS: Creates a simple welcome greeting for the GUI
    private void welcomeGreeting() {
        welcome = new JLabel("Welcome to your Fitness Tracker", JLabel.NORTH_EAST);
        welcome.setSize(WIDTH, HEIGHT / 3);
        this.add(welcome);
    }

    // MODIFIES: this
    // EFFECTS: adds a user goal to the interface
    private void doAddGoal() {
        JTextField calorieGoal = new JTextField(1);
        JTextField stepGoal = new JTextField(1);
        JTextField workoutGoal = new JTextField(1);
        JTextField redZoneGoal = new JTextField(1);

        JPanel goalPanel = new JPanel(new GridLayout(0, 1));
        addPromptToPanel(goalPanel, calorieGoal, "What is your calorie goal? :");
        addPromptToPanel(goalPanel, stepGoal, "What is your step goal? :");
        addPromptToPanel(goalPanel, workoutGoal, "What is your total workout goal? :");
        addPromptToPanel(goalPanel, redZoneGoal, "What is your total red zone goal? :");

        JButton confirmGoal = new JButton("Confirm Goal");
        confirmGoal.setActionCommand("confirm");
        confirmGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("confirm")) {
                    confirmGoal(calorieGoal, stepGoal, workoutGoal, redZoneGoal);
                }
            }
        });
        goalPanel.add(confirmGoal);

        addPanel(goalPanel, "Add Goal");
    }

    // MODIFIES: this
    // EFFECTS: helper, creates/updates a goal to be used in action event handling for doAddGoal()
    private void confirmGoal(JTextField calories, JTextField steps, JTextField workouts, JTextField redZone) {
        int calorieGoal = Integer.parseInt(calories.getText());
        int stepGoal = Integer.parseInt(steps.getText());
        int workoutGoal = Integer.parseInt(workouts.getText());
        int redZoneGoal = Integer.parseInt(redZone.getText());

        Goal addedGoal = new Goal(calorieGoal, stepGoal, workoutGoal, redZoneGoal);
        fitnessTracker.updateGoal(addedGoal);
        currentGoal = fitnessTracker.printGoal(addedGoal);
        label.setText(currentGoal);
        JOptionPane.showMessageDialog(null, "You have added your goal!"
                + " You may close the goal window.");

    }


    // MODIFIES: this
    // EFFECTS: creates a new interactive button that matches the GUI theme
    private JButton newButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setBackground(Color.green);
        button.setActionCommand(text);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: Adds all buttons to be used to GUI
    private void addButtons() {
        JButton saveButton = newButton("save");
        JButton loadButton = newButton("load");
        JButton summaryButton = newButton("view summary");
        JButton goalButton = newButton("add goal");
        JButton activityButton = newButton("add activity");
        add(summaryButton);
        add(goalButton);
        add(activityButton);
        add(saveButton);
        add(loadButton);
    }

    // EFFECTS: helper method to add a prompt to panel, getting user input
    private void addPromptToPanel(JPanel panel, JTextField field, String question) {
        panel.add(new JLabel(question));
        panel.add(field);

    }

    // MODIFIES: this
    // EFFECTS: adds a users activities
    private void doAddActivity() {
        JTextField calories = new JTextField(1);
        JTextField steps = new JTextField(1);
        JTextField redZone = new JTextField(1);
        JTextField activityName = new JTextField(1);
        JPanel activityPanel = new JPanel(new GridLayout(0, 1));
        addPromptToPanel(activityPanel, calories, "How many calories did you burn;");
        addPromptToPanel(activityPanel, steps, "How many steps did you take:");
        addPromptToPanel(activityPanel, redZone, "How much time did you spend in the red zone:");
        addPromptToPanel(activityPanel, activityName, "What was the name of your activity");
        JButton confirmActivity = new JButton("Confirm Activity");
        confirmActivity.setActionCommand("confirm activity");
        confirmActivity.addActionListener(new ActionListener() {
            //private class ActivityActionListener implements ActionListener {
            //  …
            //} no fields, but need local variables
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("confirm activity")) {
                    confirmActivity(calories, steps, redZone, activityName);

                }
            }
        });
        activityPanel.add(confirmActivity);

        addPanel(activityPanel, "Add Activity");
    }

    // MODIFIES: this
    // EFFECTS: helper, creates an activity to be used in action event handling for doAddActivity()
    private void confirmActivity(JTextField calories, JTextField steps, JTextField redZone, JTextField activityName) {
        int caloriesBurned = Integer.parseInt(calories.getText());
        int stepsTaken = Integer.parseInt(steps.getText());
        int redZoneTime = Integer.parseInt(redZone.getText());
        String workoutName = activityName.getText();

        Activity addedActivity = new Activity(caloriesBurned, stepsTaken, redZoneTime, workoutName, number++);
        fitnessTracker.addActivity(addedActivity);
        activityModel.addElement(fitnessTracker.printName(addedActivity));
        JOptionPane.showMessageDialog(null, "You have added your activity!"
                + " You may close the add activity window.");
    }

    // MODIFIES: this
    // EFFECTS: helper method to add new panel to frame
    private void addPanel(JPanel panel, String title) {
        JFrame addPanel = new JFrame(title);
        addPanel.add(panel);
        addPanel.pack();
        addPanel.setLocationRelativeTo(this);
        addPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Presents a basic summary of goal and activity progress in a new frame
    private void presentSummary() {
        JPanel activitySummary = new JPanel();
        activitySummary.add(summary);
        if (fitnessTracker.getActivities().isEmpty()) {
            {
            }
        } else if (fitnessTracker.getCalorieGoal() == 0 && fitnessTracker.getStepGoal() == 0
                && fitnessTracker.getWorkoutGoal() == 0 && fitnessTracker.getRedZoneGoal() == 0) {
            summary.setText("Please add a goal now!");

        } else {
            currentProgress = fitnessTracker.getSummary();
            summary.setText(currentProgress);
        }
        JFrame activityFrame = new JFrame();
        activityFrame.add(activitySummary);
        activityFrame.pack();
        activityFrame.setLocationRelativeTo(this);
        activityFrame.setVisible(true);

    }


    // MODIFIES: this
    // EFFECTS: Save fitness tracker progress
    private void saveGui() {
        JPanel saveSummary = new JPanel();
        saveSummary.add(saveState);
        try {
            jsonWriter.open();
            jsonWriter.write(fitnessTracker);
            jsonWriter.close();
            saveMessage = "Fitness Tracker successfully saved to " + JSON_STORE;
            saveState.setText(saveMessage);
        } catch (FileNotFoundException e) {
            saveMessage = "Unable to write to file";
            saveState.setText(saveMessage);
        }
        JFrame saveFrame = new JFrame();
        saveFrame.add(saveSummary);
        saveFrame.pack();
        saveFrame.setLocationRelativeTo(this);
        saveFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: load fitness tracker progress to GUI
    private void loadGUI() {
        JPanel loadSummary = new JPanel();
        loadSummary.add(loadState);

        try {
            fitnessTracker = jsonReader.read();
            loadMessage = "Successfully loaded your Fitness Tracker from " + JSON_STORE;
            loadState.setText(loadMessage);
        } catch (IOException e) {
            loadMessage = "Unable to read file from: " + JSON_STORE;
            loadState.setText(loadMessage);
        }
        JFrame loadFrame = new JFrame();
        loadFrame.add(loadSummary);
        loadFrame.pack();
        loadFrame.setLocationRelativeTo(this);
        loadFrame.setVisible(true);
    }

    // EFFECTS: Updates the gui display of the current goal when a goal is added
    public void refreshGUI() {
        Goal savedGoal = fitnessTracker.getGoals();
        if (savedGoal.getStepGoal() == 0 && savedGoal.getTotalWorkoutGoal() == 0 && savedGoal.getCalorieGoal() == 0
                && savedGoal.getRedZoneGoal() == 0) {
            currentGoal = "You haven't entered a goal yet!";
            label.setText(currentGoal);
        } else {
            currentGoal = fitnessTracker.printGoal(savedGoal);
            label.setText(currentGoal);
        }

        // clears all previous activities, and loads each saved activity
        List<Activity> savedActivities = fitnessTracker.getActivities();
        activityModel.clear();
        for (Activity activity : savedActivities) {
            activityModel.addElement(fitnessTracker.printName(activity));
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add goal")) {
            doAddGoal();
        } else if (e.getActionCommand().equals("add activity")) {
            doAddActivity();
        } else if (e.getActionCommand().equals("view summary")) {
            presentSummary();
        } else if (e.getActionCommand().equals("save")) {
            saveGui();
        } else if (e.getActionCommand().equals("load")) {
            loadGUI();
            refreshGUI();

        }

    }

}

