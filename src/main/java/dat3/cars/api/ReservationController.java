package dat3.cars.api;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // Security --> Admin Only
    @GetMapping
    List<ReservationResponse> getReservations(){
        return service.getReservations();
    }

    //Security --> Admin Only
    @GetMapping("/{username}")
    List<ReservationResponse> getReservationsByUsername(@PathVariable String username) throws Exception{
        return service.getReservationsByUsername(username);
    }


    //Security --> Anonymous
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse makeReservation(@RequestBody ReservationRequest res){
        return service.reserveCar(res);
    }


}
