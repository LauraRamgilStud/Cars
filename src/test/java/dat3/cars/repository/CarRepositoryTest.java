package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;
    boolean isInitialized = false;

    @BeforeEach
    void setUp(){
        if(!isInitialized){
            carRepository.save(new Car("Toyota", "Camry", 100, 5));
            carRepository.save(new Car("Honda", "Civic", 90, 3));
            carRepository.save(new Car("Ford", "Fiesta", 90, 10));
            isInitialized = true;
        }
    }

    @Test
    void findAverageRentalPriceDay(){
        int expected = (100+90+90)/3;
        int actual = carRepository.findAverageRentalPriceDay();

        assertEquals(expected, actual);
    }

    @Test
    void findByBrandAndModel(){
        List<Car> cars = carRepository.findAllByBrandAndModel("Toyota", "Camry");

        assertEquals(1, cars.size());
        assertEquals(5, cars.get(0).getMaxDiscount());
    }

    @Test
    void findAllCarsWithoutReservations(){
        List<Car> cars = carRepository.findAllByReservationsIsEmpty();

        assertEquals(3, cars.size());
    }

    @Test
    void findCarsWithBestDiscountDescending(){
        List<Car> cars = carRepository.findByOrderByMaxDiscountDesc();
        assertEquals(10, cars.get(0).getMaxDiscount());
        assertEquals(5, cars.get(1).getMaxDiscount());
        assertEquals(3, cars.get(2).getMaxDiscount());
    }

    @Test
    public void deleteAll(){
        carRepository.deleteAll();
        assertEquals(0, carRepository.count());
    }

    @Test
    public void testAll(){
        Long count = carRepository.count();
        assertEquals(3, count);
    }
}