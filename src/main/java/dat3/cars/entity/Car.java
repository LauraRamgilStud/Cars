package dat3.cars.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Mapped by name of the attribute in the class at the other end -> in this case the Reservation class
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public void addReservation(Reservation reservation){
        if(reservations == null){
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
    }

    public Car(String brand, String model, double rentalPriceDay, int maxDiscount) {
        this.brand = brand;
        this.model = model;
        this.rentalPriceDay = rentalPriceDay;
        this.maxDiscount = maxDiscount;
    }
}
