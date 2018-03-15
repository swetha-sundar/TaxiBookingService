package api;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import model.Car;
import model.Customer;

/**
 * Created by swetha on 3/4/18.
 */
public class TickTime {
    private final static int TIME_UNIT = 1;
    private final static Logger logger = Logger.getLogger(TickTime.class.getName());

    /**
     * Advance every booked car by one time unit
     *
     * if destination reached, make car available
     */

    public static void updateLocations(Car car,
                                       Customer customer,
                                       Set<Integer> availableCars,
                                       Iterator<Map.Entry<Integer, Customer>> iterator) {
        //If customer is picked up, then we are on our way to destination
        if (customer.isPickedUp() && !customer.isDroppedOff()) {
            logger.info("Customer is picked up. " + customer.getId());
            updateLocationAfterCustomerIsPickedUp(car, customer);
        } else {
            logger.info("Customer is not picked up. " + customer.getId());
            //model.Customer is not picked up yet. So we need to go to the source location
            updateLocationBeforeCustomerIsPickedUp(car, customer);
        }
        int customerLocationX = customer.getCurrentLocation().getX();
        int customerLocationY = customer.getCurrentLocation().getY();
        int destLocationX = customer.getDestinationLocation().getX();
        int destLocationY = customer.getDestinationLocation().getY();
        int carLocationX = car.getCurrentLocation().getX();
        int carLocationY = car.getCurrentLocation().getY();

        if (!customer.isPickedUp() && carLocationX == customerLocationX && carLocationY == customerLocationY) {
            //We have reached the source location to pick up the customer
            //Mark the customer as picked up
            customer.setPickedUp(true);
        }
        if (customerLocationX == destLocationX && customerLocationY == destLocationY) {
            //We have reached the destination
                /*
                 * 1. Mark the customer as dropped off
                 * 2. Mark the car as available and add to the availability list
                 * 3. Remove entry from the car-customer map
                 */
            customer.setDroppedOff(true);
            car.setAvailability(true);
            iterator.remove();
            availableCars.add(car.getId());
        }
    }

    /**
     * If the customer has not been picked up, the car needs to travel towards the source location
     *
     * If the car location is ahead of the customer's location,
     * then the car's location needs to be updated along the negative axes to reach customer
     * Otherwise, along the positive axes to reach customer
     *
     * @param car
     * @param customer
     */
    private static void updateLocationBeforeCustomerIsPickedUp(Car car, Customer customer) {
        int carLocationX = car.getCurrentLocation().getX();
        int carLocationY = car.getCurrentLocation().getY();
        int customerLocationX = customer.getCurrentLocation().getX();
        int customerLocationY = customer.getCurrentLocation().getY();

        int timeunit;
        if (carLocationX != customerLocationX) {
            if (carLocationX < customerLocationX) {
                timeunit = TIME_UNIT;
            } else {
                timeunit = -TIME_UNIT;
            }
            car.moveCarAlongX(timeunit);
        } else if (carLocationY != customerLocationY) {
            if (carLocationY < customerLocationY) {
                timeunit = TIME_UNIT;
            } else {
                timeunit = -TIME_UNIT;
            }
            car.moveCarAlongY(timeunit);
        } else {
            //car is already at customer's location
            customer.setPickedUp(true);
            updateLocationAfterCustomerIsPickedUp(car, customer);
        }
    }

    /**
     * If the customer has been picked up, the car needs to travel towards the destination location to drop off
     *
     * If the car is ahead of the destination,
     * then the car needs to move along the negative axes to reach destination
     * Otherwise, along the positive axes to drop off the customer
     *
     * @param car
     * @param customer
     */
    private static void updateLocationAfterCustomerIsPickedUp(Car car, Customer customer) {
        int timeunit;
        int customerLocationX = customer.getCurrentLocation().getX();
        int customerLocationY = customer.getCurrentLocation().getY();
        int destLocationX = customer.getDestinationLocation().getX();
        int destLocationY = customer.getDestinationLocation().getY();

        //First advance along X if not there yet
        if (customerLocationX != destLocationX) {
            if (customerLocationX > destLocationX) {
                timeunit = -TIME_UNIT;
            } else {
                timeunit = TIME_UNIT;
            }
            car.moveCarAlongX(timeunit);
            customer.updateCurrentLocation(customerLocationX + timeunit, customerLocationY);
        } else if (customerLocationY != destLocationY) {
            //Advance along Y if not there yet
            if (customerLocationY > destLocationY) {
                timeunit = -TIME_UNIT;
            } else {
                timeunit = TIME_UNIT;
            }
            car.moveCarAlongY(timeunit);
            customer.updateCurrentLocation(customerLocationX, customerLocationY + timeunit);
        }
    }
}
