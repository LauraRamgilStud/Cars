package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    MemberRepository memberRepository;

    boolean isInitialized = false;
    Car car;
    Member member;

    @BeforeEach
    void setUp(){
        if(!isInitialized){
            car = new Car("Toyota", "Camry", 100, 5);
            carRepository.save(car);
            member = new Member("user1", "password1", "user1@example.com", "John", "Doe", "123 Main St", "Cityville", "12345");
            memberRepository.save(member);
            reservationRepository.save(new Reservation(LocalDate.of(2023, 12, 12), car, member));
            reservationRepository.save(new Reservation(LocalDate.of(2023, 12, 13), car, member));
            isInitialized = true;
        }
    }

    @Test
    void reservationForCarAndDateExist(){
        boolean reserved = reservationRepository.existsByCarIdAndRentalDate(car.getId(), LocalDate.of(2023, 12,12));
        assertTrue(reserved);
    }

    @Test
    void reservationForCarAndDateNOT_Exist(){
        boolean reserved = reservationRepository.existsByCarIdAndRentalDate(car.getId(), LocalDate.of(2023, 12,11));
        assertFalse(reserved);
    }

    @Test
    void findAllReservationsForSpecificUser(){
        List<Reservation> reservations = reservationRepository.findAllByMemberUsername(member.getUsername());
        assertEquals(2, reservations.size());
    }

}