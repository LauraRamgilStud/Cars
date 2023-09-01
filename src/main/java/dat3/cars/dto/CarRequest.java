package dat3.cars.dto;

import dat3.cars.entity.Car;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarRequest {
    int id;
    String brand;
    String model;
    double rentalPriceDay;
    int maxDiscount;

    public static Car getCarEntity(CarRequest c){
        return new Car(c.getBrand(), c.getModel(), c.getRentalPriceDay(), c.getMaxDiscount());
    }

    public CarRequest(Car c){
        this.id = c.getId();
        this.brand = c.getBrand();
        this.model = c.getModel();
        this.rentalPriceDay = c.getRentalPriceDay();
        this.maxDiscount = c.getMaxDiscount();
    }
}
