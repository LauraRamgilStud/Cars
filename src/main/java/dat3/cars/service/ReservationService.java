package dat3.cars.service;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import dat3.cars.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    CarRepository carRepository;
    MemberRepository memberRepository;
    ReservationRepository reservationRepository;

    public ReservationService(CarRepository carRepository, MemberRepository memberRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations(){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map((reservation -> new ReservationResponse(reservation))).toList();
    }

    public ReservationResponse reserveCar(ReservationRequest body){
        // Check reservation is not in the past
        if (body.getDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot rent in the past");
        }

        // Check that the member exists
        Member member = memberRepository.findById(body.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No member with this username found"));

        // Check that the car exists
        Car car = carRepository.findById(body.getCarId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No car with this id found"));

        // Check that car is not already reserved on this date
        if (reservationRepository.existsByCarIdAndRentalDate(body.getCarId(), body.getDate())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This car is already reserved on this date");
        }

        Reservation res = reservationRepository.save(new Reservation(body.getDate(), car, member));
        return new ReservationResponse(res);
    }

    public List<ReservationResponse> getReservationsByUsername(String username){
        List<Reservation> reservations = reservationRepository.findAllByMemberUsername(username);
        return reservations.stream().map((reservation -> new ReservationResponse(reservation))).toList();
    }
}
