import static api.Reset.resetAllCarData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.BookTaxi;
import api.TickTime;
import model.Car;
import helper.CarIdAndTotalTime;
import model.Customer;
import model.LocationMap;

/**
 * Created by swetha on 3/4/18.
 */
@Path("/TaxiBookingService")
public class TaxiBookingService {
    private final static Logger logger = Logger.getLogger(TaxiBookingService.class.getName());

    //Map of car id to customer whom it was assigned to for a trip
    //If customer is dropped off, the entry is removed from this map
    private static Map<Integer, Customer> carIdToCustomerMap = new HashMap<>();

    //Map of cars sorted by car id
    private static Map<Integer, Car> cars = new TreeMap<>();

    //Set of available cars (only the car's identifier are stored)
    private static Set<Integer> availableCars = new TreeSet<>();

    //List of all customers who have booked a car
    //For simplicity, every booking is assumed to be done by a new customer
    private static List<Customer> customers = new ArrayList<>();

    private final static int NUM_CARS = 3;

    public TaxiBookingService() {
    }

    /**
     * Build an inventory of cars,
     * Also add the car to an available cars set
     */
    public static void buildInventory() {
        for (int i = 0; i < NUM_CARS; i++) {
            int carId = i+1;
            Car car = new Car(carId);
            cars.put(carId, car);
            availableCars.add(carId);
        }
    }

    @Path("/api/book")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response book(@QueryParam("source") String source,
                         @QueryParam("destination") String destination) {

        //Null Inputs - Not Valid
        if (source == null || destination == null || source.isEmpty() || destination.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String[] sourceCoordinates = source.split(",");
        String[] destCoordinates = destination.split(",");
        if (sourceCoordinates.length != 2 || destCoordinates.length != 2) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        LocationMap sourceLocation, destLocation;
        try {
            sourceLocation = new LocationMap(Integer.parseInt(sourceCoordinates[0]),
                    Integer.parseInt(sourceCoordinates[1]));
            destLocation = new LocationMap(Integer.parseInt(destCoordinates[0]),
                    Integer.parseInt(destCoordinates[1]));
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String id = Integer.toString(customers.size() + 1);
        Customer customer = new Customer(id, sourceLocation, destLocation);
        customers.add(customer);
        try {
            BookTaxi bookTaxi = new BookTaxi(cars, availableCars, carIdToCustomerMap);
            Car carToBook = bookTaxi.getCar(customer);

            //If no car available to book, return empty response
            if (carToBook == null) {
                return Response.noContent().build();
            }
            //Car available, so calculate total time and return a valid response
            long totalTime = bookTaxi.calculateTotalTime(carToBook, customer);
            return Response.accepted(new CarIdAndTotalTime(carToBook.getId().toString(), totalTime)).build();
        } catch (Exception e) {
            logger.severe("Could not book a car for the customer " + customer);
            return Response.serverError().build();
        }
    }

    @Path("/api/tick")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response tick() {
        try {
            Iterator<Map.Entry<Integer, Customer>> iterator = carIdToCustomerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Customer> entry = iterator.next();
                Integer carId = entry.getKey();
                Car car = cars.get(carId);
                Customer customer = entry.getValue();
                TickTime.updateLocations(car, customer, availableCars, iterator);
            }
            return Response.ok().build();
        } catch (Exception e) {
            logger.severe("Could not advance server time: " + e.getMessage());
            return Response.serverError().build();
        }
    }

    @Path("/api/reset")
    @POST
    public Response reset() {
        try {
            resetAllCarData(cars, availableCars, carIdToCustomerMap);
            logger.info("Service was Reset");
            return Response.ok().build();
        } catch (Exception e) {
            logger.severe("Service Reset Failed: " + e.getMessage());
            return Response.serverError().build();
        }
    }
}

