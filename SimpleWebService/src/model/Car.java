package model;

/**
 * Created by swetha on 3/4/18.
 */
public class Car {
    /**
     * model.Car - a unique id,
     * the current location of the car
     * could be available or not for the customers
     */
    private Integer id;
    private LocationMap currentLocation;
    private boolean isAvailable;

    /**
     * Initialize a car with id
     * and start location as 0,0 in the Location Map Grid
     * and set the availability to true
     * @param id unique identifier
     */
    public Car(Integer id) {
        this.id = id;
        currentLocation = new LocationMap(0, 0);
        isAvailable = true;
    }

    /**
     * Return the identifier of the car
     * @return
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Return true/false based on its availability
     * @return
     */
    public boolean isAvailable() {
        return this.isAvailable;
    }

    /**
     * Set the availability of the car
     * @param isAvailable
     */
    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * Update the current location of the car
     * @param x along x direction
     * @param y along y direction
     */
    public void updateCurrentLocation(int x, int y) {
        LocationMap newLocation = new LocationMap(x, y);
        this.currentLocation = newLocation;
    }

    /**
     * Get the current location of the car
     * @return current location (x, y)
     */
    public LocationMap getCurrentLocation() {
        return this.currentLocation;
    }

    /**
     * Obtain the car's distance from a given location in terms of time units
     *
     * @param location a given (destination) location
     * @return time unit(s)
     */
    public int getDistanceFrom(LocationMap location) {
        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        int endX = location.getX();
        int endY = location.getY();

        int distX = Math.abs(endX - currentX);
        int distY = Math.abs(endY - currentY);
        int timeUnitsToLocation = distX + distY;
        return timeUnitsToLocation;
    }

    /**
     * Move the car along X axis
     * if a negative timeunit is provided, it is moved in the negative direction
     * @param timeUnit integer
     */
    public void moveCarAlongX(int timeUnit) {
        int newX = getCurrentLocation().getX() + timeUnit;
        int y = getCurrentLocation().getY();
        updateCurrentLocation(newX, y);
    }

    /**
     * Move the car along Y axis
     * if a negative timeunit is provided, it is moved in the negative direction
     * @param timeUnit integer
     */
    public void moveCarAlongY(int timeUnit) {
        int newY = getCurrentLocation().getY() + timeUnit;
        int x = getCurrentLocation().getX();
        updateCurrentLocation(x, newY);
    }
}
