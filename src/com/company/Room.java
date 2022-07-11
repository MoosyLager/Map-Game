package com.company;

/**
 * This is the class for each room object. Each room has a north, sound, east,
 * and west
 * as well as a collectable if it has one
 */
public class Room {

    private String name;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private Collectable collectable;

    /**
     * At the start only the name is inputted as not all rooms have connections in
     * each direction
     *
     * @param name
     */
    public Room(String name) {
        this.name = name;
        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
        this.collectable = null;
    }

    // The getters and setters for each variable of a room object
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Collectable getCollectable() {
        return collectable;
    }

    public void setCollectable(Collectable collectable) {
        this.collectable = collectable;
    }
}
