package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Transactional -> Rollback -> In Memory DB
class CarServiceH2Test {

    @Autowired
    CarRepository carRepository;
    CarService carService;

    // We don't add the 'isInitialized' flag, because??????????
    Car c1, c2; //

    @BeforeEach
    void setUp(){
        c1 = carRepository.save(new Car("Ford","Focus", 100, 5 ));
        c2 = carRepository.save(new Car("Toyota", "Aygo", 150, 1));
        carService = new CarService(carRepository); //Set up carService with the mock (H2) DB
    }

    @Test
    void testEditCarWithSameId() {
        ResponseEntity<Boolean> response = carService.editCar(CarRequest.builder()
                .id(c1.getId())
                .brand("Ford")
                .model("Focus")
                .rentalPriceDay(200)
                .maxDiscount(10)
                .build(), c1.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode(), "Expects status code OK");
        assertEquals(200, c1.getRentalPriceDay(), "Expects c1 rental price to have changed to 200");
    }

   @Test
    void testEditCarNON_ExistingIdThrows() {
        //This should test that a ResponseStatus exception is thrown with status= 404 (NOT_FOUND)
        CarRequest newInfo = new CarRequest(new Car("Toyota", "Aygo", 120, 20));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                carService.editCar(newInfo, -99)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }

    @Test
    void testEditCarChangePrimaryKeyThrows() {
        //This should test that a ResponseStatus exception is thrown with status = BAD_REQUEST
        CarRequest newInfo = new CarRequest(new Car("Toyota", "Aygo", 120, 20));
        newInfo.setId(-99);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                carService.editCar(newInfo, c2.getId())
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Expects status code BAD_REQUEST");
    }

    @Test
    void testGetCarsAllDetails() {
        List<CarResponse> carResponses = carService.getCars(true);
        assertEquals(2, carResponses.size());
        LocalDateTime time = carResponses.get(0).getCreated();
        assertNotNull(time, "Expects date to be set, when true is passed");
    }

    @Test
    void testGetCarsNoDetails() {
        List<CarResponse> carResponses = carService.getCars(false);
        assertEquals(2, carResponses.size());
        LocalDateTime time = carResponses.get(0).getCreated();
        assertNull(time, "Expects date to not be set, when false is passed");
    }

    @Test
    void testFindByIdFound() {
        CarResponse response = carService.findById(c1.getId());
        assertEquals("Ford", response.getBrand(), "Expects response brand to be ford");
    }

    @Test
    void testFindByIdNotFound() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> carService.findById(-99));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }

    /* Remember CarRequest comes from the API layer, and CarResponse is returned to the API layer
     * Internally addCar save a Car entity to the database*/
    @Test
    void testAddCar() {
        //Add @AllArgsConstructor to CarRequest and @Builder to CarRequest for this to work
        CarRequest request = CarRequest.builder()
                .brand("Audi")
                .model("Y8")
                .rentalPriceDay(300)
                .maxDiscount(8)
                .build();
        CarResponse response = carService.addCar(request);
        List<CarResponse> carResponses = carService.getCars(false);

        assertEquals(3, carResponses.size(), "Expects size of responses to be 3 after insertion");
        assertEquals("Audi", response.getBrand(), "Expects response brand to be Audi");
    }
    @Test
    void testDeleteCarById() {
        carService.deleteCarById(c1.getId());
        List<CarResponse> carResponses = carService.getCars(false);
        assertEquals(1, carResponses.size(), "Expects size of responses to be 1 after deletion");
    }

    @Test
    void testDeleteCar_ThatDontExist() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> carService.deleteCarById(-99));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }
}