package com.company;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * This is the main class of my program. It is also the java file that allows
 * manipulation of GUI.form
 * and the main GUI
 *
 * @author Shenal Herath
 * @date November 2020
 */
public class GUI {

    // Creating arrays that will store the room and collectable objects
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Collectable> collectables = new ArrayList<>();

    // Initialising the global variables for the collectables and the rooms
    private Collectable bedroomKey;
    private Collectable parentKey;
    private Collectable kitchenKey;
    private Collectable livingRoomKey;
    private Collectable bathroomKey;
    private Collectable exitKey;
    private Collectable secretRoomKey;

    private Room bedroom;
    private Room closet;
    private Room bedroomBathroom;
    private Room hallway;
    private Room bathroom;
    private Room parentRoom;
    private Room entranceWay;
    private Room kitchen;
    private Room livingRoom;
    private Room secretRoom;
    private Room exit;

    // That are updated throughout the time of the game
    private Room currentRoom;
    private int collectableFound = 0;
    private String playerName;
    private boolean gameStart = true;
    private int elapsedTime = 0;
    private Timer timer = new Timer(1000, e -> countElapsedTime());

    /*
     * Initialising the all the global variable names to be able to manipulate the
     * Swing GUI elements
     */
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel dialogPanel;
    private JLabel northConnectionLabel;
    private JLabel southConnectionLabel;
    private JLabel eastConnectionLabel;
    private JLabel westConnectionLabel;
    private JLabel currentRoomLabel;
    private JButton northButton;
    private JButton southButton;
    private JButton westButton;
    private JButton eastButton;
    private JButton searchButton;
    private JLabel collectableFoundLabel;
    private JLabel collectableCountLabel;
    private JButton dialogConfirmButton;
    private JLabel dialogLabel;
    private JPanel navigationPanel;
    private JPanel collectablePanel;
    private JPanel connectionPanel;

    /**
     * This is the main method of this class that creates the main GUI and sets its
     * options
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Map Game");
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * This is the constructor for the GUI class. After the main method is run, this
     * function runs which then calls other functions which initialise the game
     * which are inputName which asks for the user's name and setupGame which sets
     * up
     * the map, its connections and the collectables. This is also where the
     * listeners
     * are for the GUI buttons
     */
    public GUI() {
        inputName();
        setupGame();
        timer.start();

        northButton.addActionListener(e -> goNorth());
        southButton.addActionListener(e -> goSouth());
        eastButton.addActionListener(e -> goEast());
        westButton.addActionListener(e -> goWest());

        dialogPanel.setVisible(false);

        dialogConfirmButton.addActionListener(e -> {
            dialogPanel.setVisible(false);
            gamePanel.setVisible(true);
            gameStart = false;
        });
        searchButton.addActionListener(e -> updateCollectables());

        updateUI();
    }

    /**
     * This functions sets up the game itself. It creates the room and collectable
     * objects, adds them to their respective arrays, sets up the connections
     * between
     * rooms, sets which rooms the collectables open and sets where each collectable
     * is
     * located. The starting room (bedroom) is also chosen at the end of the
     * function
     */
    private void setupGame() {
        // Creating the room objects
        bedroom = new Room("Bedroom");
        closet = new Room("Closet");
        bedroomBathroom = new Room("Bedroom Bathroom");
        hallway = new Room("Hallway");
        parentRoom = new Room("Parent's Room");
        bathroom = new Room("Bathroom");
        entranceWay = new Room("Entrance Way");
        livingRoom = new Room("Living Room");
        kitchen = new Room("Kitchen");
        secretRoom = new Room("Secret Room");
        exit = new Room("Exit");

        // Adding the rooms to the rooms array
        rooms.add(closet);
        rooms.add(bedroom);
        rooms.add(bedroomBathroom);
        rooms.add(bathroom);
        rooms.add(hallway);
        rooms.add(parentRoom);
        rooms.add(livingRoom);
        rooms.add(entranceWay);
        rooms.add(kitchen);
        rooms.add(secretRoom);

        // Assigning the connections that are available at the start of the the game
        bedroom.setEast(bedroomBathroom);
        bedroom.setWest(closet);

        bedroomBathroom.setWest(bedroom);

        closet.setEast(bedroom);

        hallway.setNorth(entranceWay);

        entranceWay.setSouth(hallway);

        /*
         * Creating the room objects with what connections they unlock and the direction
         * of
         * those connections relative to the first room named
         */
        bedroomKey = new Collectable("Bedroom Key", bedroom, hallway, "N",
                "You found a key engraved with the word 'Bedroom' sounds like you can get out of this room");
        parentKey = new Collectable("Parent's Room Key", hallway, parentRoom, "E",
                "You found a key engraved with the words 'Parent's room' sounds like you can get into that room east of the hallway");
        kitchenKey = new Collectable("Kitchen Key", entranceWay, kitchen, "E",
                "You found a key engraved with the word 'Kitchen' sounds like you can get into that room east of the entrance way");
        livingRoomKey = new Collectable("Living Room Key", entranceWay, livingRoom, "W",
                "You found a key engraved with the words 'Living Room' sounds like you can get into that room west of the hallway");
        bathroomKey = new Collectable("Bathroom Key", hallway, bathroom, "W",
                "You found a key engraved with the word 'Bathroom' sounds like you can get into that room west of the hallway");
        exitKey = new Collectable("Exit Key", entranceWay, exit, "N",
                "You found a key engraved with the words 'Exit'. You can finally leave! Well done, you've played my game well");
        secretRoomKey = new Collectable("Secret Room Key", bedroomBathroom, secretRoom, "S",
                "You found a key engraved with the words 'Secret Room' There was an odd key-shaped hole in the bedroom bathroom. Looks like you've unlocked a secret room");

        // Adding the collectables to the collectables array
        collectables.add(bedroomKey);
        collectables.add(parentKey);
        collectables.add(kitchenKey);
        collectables.add(livingRoomKey);
        collectables.add(bathroomKey);
        collectables.add(exitKey);
        collectables.add(secretRoomKey);

        // Putting the collectables in the rooms
        closet.setCollectable(bedroomKey);
        bedroomBathroom.setCollectable(kitchenKey);
        kitchen.setCollectable(bathroomKey);
        bathroom.setCollectable(parentKey);
        secretRoom.setCollectable(exitKey);
        parentRoom.setCollectable(livingRoomKey);
        livingRoom.setCollectable(secretRoomKey);

        // Setting the starting location of the room
        currentRoom = bedroom;

        /*
         * The collectables counter is set to show the total number of collectables
         * in the game
         */
        collectableCountLabel.setText(Integer.toString(collectables.size()));
    }

    /**
     * This updates the main UI elements. The connections, current room, and number
     * of collectables found labels
     */
    private void updateUI() {
        /*
         * Tells the user what their current room is and how many collectables they
         * have found
         */
        currentRoomLabel.setText(currentRoom.getName());
        collectableFoundLabel.setText(Integer.toString(collectableFound));

        // If the game is just starting, the intro will show
        if (gameStart) {
            changeDialog("Oh... I see you've woken up. I am the Game Master and " +
                    "let's play a little game shall we? Find the keys to unlock " +
                    "all the rooms and find the exit key to escape! If you don't, " +
                    "you'll be spending say... the rest of your life here? " +
                    "So good luck and entertain me! (The collectables are " +
                    "random based so you might need to search a few times " +
                    "in a room to get a collectable)");
        }

        // Checking for the win status
        checkForWin();
        // If not, update the connections labels
        updateConnections();
    }

    /**
     * Updates the connections available from the current room if there is one
     * it will be displayed, if not the label is set to Nothing There! and the
     * button
     * to that direction is disabled to avoid an error
     */
    private void updateConnections() {
        if (currentRoom.getNorth() == null) {
            northConnectionLabel.setText("Nothing There!");
            northButton.setEnabled(false);
        } else {
            northConnectionLabel.setText(currentRoom.getNorth().getName());
            northButton.setEnabled(true);
        }

        if (currentRoom.getSouth() == null) {
            southConnectionLabel.setText("Nothing There!");
            southButton.setEnabled(false);
        } else {
            southConnectionLabel.setText(currentRoom.getSouth().getName());
            southButton.setEnabled(true);
        }

        if (currentRoom.getEast() == null) {
            eastConnectionLabel.setText("Nothing There!");
            eastButton.setEnabled(false);
        } else {
            eastConnectionLabel.setText(currentRoom.getEast().getName());
            eastButton.setEnabled(true);
        }

        if (currentRoom.getWest() == null) {
            westConnectionLabel.setText("Nothing There!");
            westButton.setEnabled(false);
        } else {
            westConnectionLabel.setText(currentRoom.getWest().getName());
            westButton.setEnabled(true);
        }
    }

    /*
     * Checks if the player is in the exit which is the win status if so
     * then the timer will stop and the end message will show
     */
    private void checkForWin() {
        if (currentRoom == exit) {
            changeDialog("You win! Thanks for playing " + playerName + "       You got a time of "
                    + elapsedTime
                    + " seconds");
            gamePanel.setVisible(false);
            timer.stop();
            dialogConfirmButton.setVisible(false);
        }
    }

    /**
     * The following Four methods are for when the player is moving
     * from one room to another in that respective direction. It will update
     * the currentRoom to that room and update the UI
     */
    private void goNorth() {
        currentRoom = currentRoom.getNorth();
        updateUI();
    }

    private void goSouth() {
        currentRoom = currentRoom.getSouth();
        updateUI();
    }

    private void goEast() {
        currentRoom = currentRoom.getEast();
        updateUI();
    }

    private void goWest() {
        currentRoom = currentRoom.getWest();
        updateUI();
    }

    /**
     * Used for asking the player's name at the start of the game
     */
    private void inputName() {
        nameInput nameInput = new nameInput();
        nameInput.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        nameInput.pack();
        nameInput.setResizable(false);
        nameInput.setLocationRelativeTo(null);
        nameInput.setVisible(true);

        playerName = nameInput.showDialog();
    }

    /**
     * This function runs every time the player presses the search button
     * Since the collectables are luck based, a random number is selected which
     * if it is under a certain value, will trigger a another function if their is
     * a collectable in the current room.
     */
    private void updateCollectables() {
        // Local collectable variable for the current room
        Collectable collectable = currentRoom.getCollectable();
        // random number selected for a luck based experience
        int searchChance = new Random().nextInt(100);

        if (searchChance <= 100) { // Yes, the number is beneath the threshold
            if (collectable != null && !collectable.isFound()) {// Their is a collectable and it has not
                                                                // been found yet
                Room keyRoom = collectable.getRoom();
                Room linkedRoom = collectable.getLinked();
                String direction = collectable.getDirection();

                /*
                 * Depending on the direction of the connection relative to the first room
                 * entered when creating the collectable. The connection between
                 * the two rooms is created, the collectable has been found
                 * the user is shown that by incrementing a counter, the description
                 * of that collectable is shown and the UI is updated
                 */
                switch (direction) {
                    case "N":
                        keyRoom.setNorth(linkedRoom);
                        linkedRoom.setSouth(keyRoom);
                        changeDialog(collectable.getDescription());
                        collectable.setFound(true);
                        collectableFound++;
                        updateUI();
                        break;
                    case "S":
                        keyRoom.setSouth(linkedRoom);
                        linkedRoom.setNorth(keyRoom);
                        changeDialog(collectable.getDescription());
                        collectable.setFound(true);
                        collectableFound++;
                        updateUI();
                        break;
                    case "E":
                        keyRoom.setEast(linkedRoom);
                        linkedRoom.setWest(keyRoom);
                        changeDialog(collectable.getDescription());
                        collectable.setFound(true);
                        collectableFound++;
                        updateUI();
                        break;
                    case "W":
                        keyRoom.setWest(linkedRoom);
                        linkedRoom.setEast(keyRoom);
                        changeDialog(collectable.getDescription());
                        collectable.setFound(true);
                        collectableFound++;
                        updateUI();
                        break;
                }
            }
        } else { // the number is below the threshold so the search has failed
            changeDialog("You searched the " + currentRoom.getName()
                    + " and found nothing. Keep searching or come back to it");
        }
    }

    /**
     * Used for changing the text in the dialogLabel. To make sure the text is
     * wrapped
     * correctly, it is wrapped in html tags and to make is easier, this function
     * does
     * that automatically so it doesn't have to be done manually for each bit of
     * text
     *
     * @param dialog
     */
    private void changeDialog(String dialog) {
        dialogLabel.setText("<html>" + dialog + "</html>");
        dialogPanel.setVisible(true);
        gamePanel.setVisible(false);
    }

    /**
     * Just used to count the elapsed time between starting and finishing the game
     */
    private void countElapsedTime() {
        elapsedTime++;
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(
                new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(20, 20, 20, 20), -1,
                        -1));
        mainPanel.setMinimumSize(new Dimension(400, 450));
        mainPanel.setPreferredSize(new Dimension(500, 450));
        gamePanel = new JPanel();
        gamePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1,
                -1));
        mainPanel.add(gamePanel,
                new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null, 0, false));
        connectionPanel = new JPanel();
        connectionPanel
                .setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2,
                        new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(connectionPanel,
                new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 22, label1.getFont());
        if (label1Font != null)
            label1.setFont(label1Font);
        label1.setText("North:");
        connectionPanel.add(label1,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 22, label2.getFont());
        if (label2Font != null)
            label2.setFont(label2Font);
        label2.setText("South:");
        connectionPanel.add(label2,
                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, -1, 22, label3.getFont());
        if (label3Font != null)
            label3.setFont(label3Font);
        label3.setText("East:");
        connectionPanel.add(label3,
                new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 22, label4.getFont());
        if (label4Font != null)
            label4.setFont(label4Font);
        label4.setText("West:");
        connectionPanel.add(label4,
                new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        northConnectionLabel = new JLabel();
        Font northConnectionLabelFont = this.$$$getFont$$$(null, -1, 22, northConnectionLabel.getFont());
        if (northConnectionLabelFont != null)
            northConnectionLabel.setFont(northConnectionLabelFont);
        northConnectionLabel.setText(" ");
        connectionPanel.add(northConnectionLabel,
                new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        southConnectionLabel = new JLabel();
        Font southConnectionLabelFont = this.$$$getFont$$$(null, -1, 22, southConnectionLabel.getFont());
        if (southConnectionLabelFont != null)
            southConnectionLabel.setFont(southConnectionLabelFont);
        southConnectionLabel.setText(" ");
        connectionPanel.add(southConnectionLabel,
                new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        eastConnectionLabel = new JLabel();
        Font eastConnectionLabelFont = this.$$$getFont$$$(null, -1, 22, eastConnectionLabel.getFont());
        if (eastConnectionLabelFont != null)
            eastConnectionLabel.setFont(eastConnectionLabelFont);
        eastConnectionLabel.setText(" ");
        connectionPanel.add(eastConnectionLabel,
                new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        westConnectionLabel = new JLabel();
        Font westConnectionLabelFont = this.$$$getFont$$$(null, -1, 22, westConnectionLabel.getFont());
        if (westConnectionLabelFont != null)
            westConnectionLabel.setFont(westConnectionLabelFont);
        westConnectionLabel.setText(" ");
        connectionPanel.add(westConnectionLabel,
                new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, 22, label5.getFont());
        if (label5Font != null)
            label5.setFont(label5Font);
        label5.setText("Current Room:");
        gamePanel.add(label5,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        currentRoomLabel = new JLabel();
        Font currentRoomLabelFont = this.$$$getFont$$$(null, -1, 22, currentRoomLabel.getFont());
        if (currentRoomLabelFont != null)
            currentRoomLabel.setFont(currentRoomLabelFont);
        currentRoomLabel.setText(" ");
        gamePanel.add(currentRoomLabel,
                new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        navigationPanel = new JPanel();
        navigationPanel
                .setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3,
                        new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(navigationPanel,
                new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null, 0, false));
        northButton = new JButton();
        northButton.setText("North");
        navigationPanel.add(northButton,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(150, 25), null,
                        new Dimension(150, 25), 0, false));
        southButton = new JButton();
        southButton.setText("South");
        navigationPanel.add(southButton,
                new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(150, 25), null,
                        new Dimension(150, 25), 0, false));
        westButton = new JButton();
        westButton.setText("West");
        navigationPanel.add(westButton,
                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(150, 25), null,
                        new Dimension(150, 25), 0, false));
        eastButton = new JButton();
        eastButton.setText("East");
        navigationPanel.add(eastButton,
                new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(150, 25), null,
                        new Dimension(150, 25), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        navigationPanel.add(spacer1,
                new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1,
                        new Dimension(25, -1),
                        null, new Dimension(25, -1), 0, false));
        searchButton = new JButton();
        searchButton.setText("Search");
        navigationPanel.add(searchButton,
                new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        navigationPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
                0, false));
        collectablePanel = new JPanel();
        collectablePanel
                .setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4,
                        new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(collectablePanel,
                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 24, label6.getFont());
        if (label6Font != null)
            label6.setFont(label6Font);
        label6.setText("Keys:");
        collectablePanel.add(label6,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        collectableFoundLabel = new JLabel();
        Font collectableFoundLabelFont = this.$$$getFont$$$(null, -1, 24, collectableFoundLabel.getFont());
        if (collectableFoundLabelFont != null)
            collectableFoundLabel.setFont(collectableFoundLabelFont);
        collectableFoundLabel.setHorizontalAlignment(4);
        collectableFoundLabel.setText("0");
        collectablePanel.add(collectableFoundLabel,
                new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, 24, label7.getFont());
        if (label7Font != null)
            label7.setFont(label7Font);
        label7.setHorizontalAlignment(0);
        label7.setText("/");
        collectablePanel.add(label7,
                new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        collectableCountLabel = new JLabel();
        Font collectableCountLabelFont = this.$$$getFont$$$(null, -1, 24, collectableCountLabel.getFont());
        if (collectableCountLabelFont != null)
            collectableCountLabel.setFont(collectableCountLabelFont);
        collectableCountLabel.setHorizontalAlignment(2);
        collectableCountLabel.setText("0");
        collectablePanel.add(collectableCountLabel,
                new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, -1, 30, label8.getFont());
        if (label8Font != null)
            label8.setFont(label8Font);
        label8.setHorizontalAlignment(0);
        label8.setText("Kidnapper's Little Game");
        mainPanel.add(label8,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        mainPanel.add(spacer3,
                new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null,
                        new Dimension(-1, 10),
                        new Dimension(-1, 10), 0, false));
        dialogPanel = new JPanel();
        dialogPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0),
                -1, -1));
        mainPanel.add(dialogPanel,
                new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1, 350), new Dimension(-1, 350), null, 0, false));
        dialogConfirmButton = new JButton();
        dialogConfirmButton.setText("OK");
        dialogPanel.add(dialogConfirmButton,
                new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
                                | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        dialogPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
                0, false));
        dialogLabel = new JLabel();
        Font dialogLabelFont = this.$$$getFont$$$(null, -1, 22, dialogLabel.getFont());
        if (dialogLabelFont != null)
            dialogLabel.setFont(dialogLabelFont);
        dialogLabel.setText("Label");
        dialogPanel.add(dialogLabel,
                new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2,
                        com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                        com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                        com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(400, -1),
                        new Dimension(400, -1), 0, false));

    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null)
            return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
                size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize())
                : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback
                : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
