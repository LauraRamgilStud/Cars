package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars(boolean includeAll){
        List<CarResponse> response = new ArrayList<>();

        List<Car> cars =carRepository.findAll();
        for (Car car : cars){
            CarResponse cr = new CarResponse(car, includeAll);
            response.add(cr);
        }
        return response;
    }

    public CarResponse addCar(CarRequest body){
        if(carRepository.existsById(body.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This car does not exist");
        }
        Car newCar = CarRequest.getCarEntity(body);
        newCar = carRepository.save(newCar);

        return new CarResponse(newCar, true);
    }

    public ResponseEntity<Boolean> editCar(CarRequest body, int id){
        Car car = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with this id does not exist"));
        if(!(body.getId() == id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change id");
        }
        car.setBrand(body.getBrand());
        car.setModel(body.getModel());
        car.setMaxDiscount(body.getMaxDiscount());
        car.setRentalPriceDay(body.getRentalPriceDay());
        return ResponseEntity.ok(true);
    }

    public CarResponse findById(int id){
        Car car = carRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with this id does not exist"));
        return new CarResponse(car, true);
    }
}
