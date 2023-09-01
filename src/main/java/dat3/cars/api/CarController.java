package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    //Security --> Admin only/Anonymous
    @GetMapping
    List<CarResponse> getCars(){
        return carService.getCars(false);
    }

    //Security --> Admin Only
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable int id) throws Exception {
        return carService.findById(id);
    }

    //Security --> Admin Only
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body){
        return carService.addCar(body);
    }

    //Security --> Admin Only
    @PutMapping("/{id}")
    ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id){
        return carService.editCar(body, id);
    }

    //Security --> Admin Only
    @DeleteMapping("/{id}")
    void deleteCarById(@PathVariable int id){
        carService.deleteCarById(id);
    }
}
