package dat3.cars.repository;

import dat3.cars.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
            isInitialized = true;
        }
    }

    @Test
    public void deleteAll(){
        carRepository.deleteAll();
        assertEquals(0, carRepository.count());
    }

    @Test
    public void testAll(){
        Long count = carRepository.count();
        assertEquals(2, count);
    }
}