package helper;

/**
 * Created by swetha on 3/4/18.
 */
public class CarIdAndTotalTime {
    private String car_id;
    private Long total_time;

    public CarIdAndTotalTime() {
    }

    public CarIdAndTotalTime(String car_id, Long total_time) {
        this.car_id = car_id;
        this.total_time = total_time;
    }

    public String getCarId() {
        return this.car_id;
    }

    public Long getTotalTime() {
        return this.total_time;
    }
}
