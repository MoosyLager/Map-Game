package com.company;

/**
 * This is the class for each collectable object. It stores a name, a description, two
 * room objects that it unlocks a connection between, the connection direction from the
 * first room stated to the second room stated, and whether it has been found or not
 */
public class Collectable {

    private String name;
    private String description;
    private Room room;
    private Room linked;
    private String direction;
    private boolean found;


    /**
     * Each variable is required  besides the found as all collectables by default have
     * not been found
     *
     * @param name
     * @param room
     * @param linked
     * @param direction
     * @param description
     */
    public Collectable( String name, Room room, Room linked, String direction, String description ) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.linked = linked;
        this.direction = direction;
        this.found = false;
    }

    // getters and setters for each variable of a collectable objects
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = "<html>" + description + "</html>";
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom( Room room1 ) {
        this.room = room1;
    }

    public Room getLinked() {
        return linked;
    }

    public void setLinked( Room linked ) {
        this.linked = linked;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection( String direction ) {
        this.direction = direction.toLowerCase();
    }

    public boolean isFound() {
        return found;
    }

    public void setFound( boolean found ) {
        this.found = found;
    }
}
