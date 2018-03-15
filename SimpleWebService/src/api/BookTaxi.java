package api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import model.Car;
import model.Customer;
import model.LocationMap;

/**
 * Created by swetha on 3/4/18.
 */
public class BookTaxi {
    private final static Logger logger = Logger.getLogger(BookTaxi.class.getName());

    private Set<Integer> availableCars;
    private Map<Integer, Customer> carIdToCustomerMap;
    private Map<Integer, Car> cars;

    public BookTaxi(Map<Integer, Car> cars,
                    Set<Integer> availableCars,
                    Map<Integer, Customer> carIdToCustomerMap) {
        this.cars = cars;
        this.availableCars = availableCars;
        this.carIdToCustomerMap = carIdToCustomerMap;
    }

    /**
     * Get a car for the given customer
     * @param customer
     * @return null or an available car
     */
    public Car getCar(Customer customer) {
        LocationMap sourceLocation = customer.getSourceLocation();
        Car carToBook = getAvailableCar(sourceLocation);

        //If "No" cars available, return null
        if (carToBook == null) {
            return null;
        }
        /* If there is an available car
         * 1. Mark the car as booked
         * 2. Remove it from list of available cars
         * 3. Add to customer/car relationship map
         */
        carToBook.setAvailability(false);
        for(Integer carId : availableCars) {
            if (carId == carToBook.getId().intValue()) {
                availableCars.remove(carId);
                break;
            }
        }
        carIdToCustomerMap.put(carToBook.getId(), customer);
        return carToBook;
    }

    /**
     * Given a car and customer, calculate the total time it would take for the trip
     * Total Time = time to pick up customer from the car's current location + time to drop off the customer
     * @param car
     * @param customer
     * @return time units
     */
    public long calculateTotalTime(Car car, Customer customer) {
        LocationMap sourceLocation = customer.getSourceLocation();
        LocationMap destLocation = customer.getDestinationLocation();
        long timeToPickUp = car.getDistanceFrom(sourceLocation);
        long timeToDrop = calculateDiffBetweenDistances(sourceLocation, destLocation);
        return timeToPickUp + timeToDrop;
    }

    /**
     * Criteria to book a car:
     * model.Car is available - Not booked by another customer
     * model.Car is closest to the customer
     * @param sourceLocation source location
     * @return car
     */
    private Car getAvailableCar(LocationMap sourceLocation) {
        if (availableCars.isEmpty()) {
            logger.info("No available cars");
            return null;
        }
        //If only one car is available, return it
        if (availableCars.size() == 1) {
            return cars.get(availableCars.iterator().next());
        }

        //If more than one car available,
        //calculate the distance (in terms of time units) to the customer's source location
        Map<Car, Integer> carAndDistanceFromSourceMap = calculateTimeUnitsFrom(sourceLocation);
        return getTheNearestCar(carAndDistanceFromSourceMap);
    }

    /**
     * Return the nearest (in terms of time units) car from the source location
     * If two or more cars are at the same distance, then return the car with the smallest id
     *
     * @param carAndDistanceFromSourceMap
     * @return car that's nearest to the customer's location
     */
    private Car getTheNearestCar(Map<Car, Integer> carAndDistanceFromSourceMap) {
        Integer smallestDistance = Integer.MAX_VALUE;
        Car nearestCar = null;
        for (Map.Entry<Car, Integer> entry : carAndDistanceFromSourceMap.entrySet()) {
            //If two cars are at the same distance from the customer, then return the car with the smallest id
            if (entry.getValue().intValue() == smallestDistance) {
                if ((nearestCar == null) || (entry.getKey().getId() < nearestCar.getId())) {
                    nearestCar = entry.getKey();
                }
            } else if (entry.getValue() < smallestDistance) {
                smallestDistance = entry.getValue();
                nearestCar = entry.getKey();
            }
        }
        return nearestCar;
    }

    /**
     * calculate the distances in terms of time units for only the available cars
     * @param sourceLocation given a location
     * @return map of car and its distance from the given location
     */
    private Map<Car, Integer> calculateTimeUnitsFrom(LocationMap sourceLocation) {
        Map<Car, Integer> timeUnitsFromSourceLocation = new HashMap<>();
        for (Integer carId : availableCars) {
            Car car = cars.get(carId);
            timeUnitsFromSourceLocation.put(car, car.getDistanceFrom(sourceLocation));
        }
        return timeUnitsFromSourceLocation;
    }

    /**
     * Given two distances, calculate the time it takes to travel from one to another
     * @param source source location
     * @param dest destination location
     * @return time
     */
    private long calculateDiffBetweenDistances(LocationMap source, LocationMap dest) {
        int sourceX = source.getX();
        int sourceY = source.getY();

        int destX = dest.getX();
        int destY = dest.getY();

        return Math.abs(destX - sourceX) + Math.abs(destY - sourceY);
    }
}
