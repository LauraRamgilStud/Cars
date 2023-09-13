package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findAllByBrandAndModel(String brand, String model);

    List<Car> findAllByReservationsIsEmpty();

    List<Car> findByOrderByMaxDiscountDesc();

    // Got this @Query from ChatGPT
    @Query("SELECT CAST(AVG(c.rentalPriceDay) AS int) FROM Car c")
    int findAverageRentalPriceDay();
}
