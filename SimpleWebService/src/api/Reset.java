package api;

import java.util.Set;
import java.util.Map;

import model.Car;
import model.Customer;

/**
 * Created by swetha on 3/4/18.
 */
public class Reset {

    public static void resetAllCarData(Map<Integer, Car> cars,
                                       Set<Integer> availableCars,
                                       Map<Integer, Customer> carIdToCustomerMap) {
        for (Integer carId : cars.keySet()) {
            Car car = cars.get(carId);
            car.updateCurrentLocation(0,0);
            car.setAvailability(true);
            if (!availableCars.contains(carId)) {
                availableCars.add(carId);
                carIdToCustomerMap.remove(carId);
            }
        }
    }
}
