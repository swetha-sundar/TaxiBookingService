package model;

/**
 * Created by swetha on 3/4/18.
 */
public class Customer {
    private String id;
    private LocationMap sourceLocation;
    private LocationMap destinationLocation;
    private LocationMap currentLocation;
    private boolean isPickedUp;
    private boolean isDroppedOff;

    /**
     * Initializes/Creates a customer with a unique id and the source & dest location
     * @param id unique identifier
     * @param sourceLocation source location to pick up the customer
     * @param destinationLocation dest location to drop off the customer
     */
    public Customer(String id, LocationMap sourceLocation, LocationMap destinationLocation) {
        this.id = id;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.currentLocation = sourceLocation;
        this.isPickedUp = false;
        this.isDroppedOff = false;
    }

    /**
     * Return the customer's id
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Return the customer's start location (source)
     * @return start location
     */
    public LocationMap getSourceLocation() {
        return this.sourceLocation;
    }

    /**
     * Return the customer's destination location
     * @return destination location
     */
    public LocationMap getDestinationLocation() {
        return this.destinationLocation;
    }

    /**
     * Return the current location of the customer
     * @return current location
     */
    public LocationMap getCurrentLocation() {
        return this.currentLocation;
    }

    /**
     * Return true/false if the customer is picked up
     * @return true/false
     */
    public boolean isPickedUp() {
        return this.isPickedUp;
    }

    /**
     * Set true/false to indicate the customer has been picked up/not
     * @param pickedUp true/false
     */
    public void setPickedUp(boolean pickedUp) {
        this.isPickedUp = pickedUp;
    }

    /**
     * Return true/false if the customer is dropped off
     * @return true/false
     */
    public boolean isDroppedOff() {
        return this.isDroppedOff;
    }

    /**
     * Set true/false to indicate the customer has been dropped off/not
     * @param droppedOff true/false
     */
    public void setDroppedOff(boolean droppedOff) {
        this.isDroppedOff = droppedOff;
    }

    /**
     * Update the customer's current location to the given coordinates
     * @param x coordinate in the location map
     * @param y coordinate in the location map
     */
    public void updateCurrentLocation(int x, int y) {
        this.currentLocation = new LocationMap(x, y);
    }
}
