package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
// ---------------
@Entity
public class Car extends AdminDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "car_brand", nullable = false, length = 50)
    private String brand;
    @Column(name = "car_model", nullable = false, length = 60)
    private String model;
    @Column(name = "rental_price_day")
    private double rentalPriceDay;
    @Column(name = "max_discount")
    private int maxDiscount;

    public Car(String brand, String model, double rentalPriceDay, int maxDiscount) {
        this.brand = brand;
        this.model = model;
        this.rentalPriceDay = rentalPriceDay;
        this.maxDiscount = maxDiscount;
    }
}
