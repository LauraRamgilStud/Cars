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
        List<Car> cars = carRepository.findAll();
        return cars.stream().map((car) -> new CarResponse(car, includeAll)).toList();
    }

    public CarResponse addCar(CarRequest body){
        /*if(carRepository.existsById(body.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This car already exist");
        }*/
        Car newCar = CarRequest.getCarEntity(body);
        newCar = carRepository.save(newCar);

        return new CarResponse(newCar, true);
    }

    public ResponseEntity<Boolean> editCar(CarRequest body, int id){
        Car car = getCarById(id);
        if(!(body.getId() == id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change id");
        }
        car.setBrand(body.getBrand());
        car.setModel(body.getModel());
        car.setMaxDiscount(body.getMaxDiscount());
        car.setRentalPriceDay(body.getRentalPriceDay());
        carRepository.save(car);
        return ResponseEntity.ok(true);
    }

    public CarResponse findById(int id){
        Car car = getCarById(id);
        return new CarResponse(car, true);
    }

    public void deleteCarById(int id){
        Car car = getCarById(id);
        carRepository.delete(car);
    }

    private Car getCarById(int id){
        return carRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with this id does not exist"));
    }
}
