package dat3.cars.config;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import dat3.cars.repository.CarRepository;
import dat3.cars.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeveloperData implements ApplicationRunner {
    CarRepository carRepository;
    MemberRepository memberRepository;

    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository){
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Toyota", "Camry", 100, 5));
        cars.add(new Car("Honda", "Civic", 90, 3));
        cars.add(new Car("Ford", "F-150", 110, 6));
        cars.add(new Car("Chevrolet", "Silverado", 105, 4));
        cars.add(new Car("Nissan", "Altima", 95, 2));
        cars.add(new Car("Toyota", "Corolla", 85, 4));
        cars.add(new Car("Honda", "Accord", 95, 3));
        cars.add(new Car("Ford", "Mustang", 120, 7));
        cars.add(new Car("Chevrolet", "Camaro", 115, 6));
        cars.add(new Car("Nissan", "Maxima", 100, 4));
        cars.add(new Car("Toyota", "Rav4", 95, 3));
        cars.add(new Car("Honda", "CR-V", 90, 2));
        cars.add(new Car("Ford", "Escape", 85, 2));
        cars.add(new Car("Chevrolet", "Equinox", 92, 3));
        cars.add(new Car("Nissan", "Rogue", 88, 2));
        cars.add(new Car("Toyota", "Highlander", 110, 4));
        cars.add(new Car("Honda", "Pilot", 115, 5));
        cars.add(new Car("Ford", "Explorer", 120, 6));
        cars.add(new Car("Chevrolet", "Traverse", 125, 7));
        cars.add(new Car("Nissan", "Pathfinder", 105, 4));
        cars.add(new Car("Toyota", "Sienna", 95, 3));
        cars.add(new Car("Honda", "Odyssey", 100, 4));
        cars.add(new Car("Ford", "Fusion", 80, 2));
        cars.add(new Car("Chevrolet", "Malibu", 85, 2));
        cars.add(new Car("Nissan", "Sentra", 75, 1));
        cars.add(new Car("Toyota", "Avalon", 105, 5));
        cars.add(new Car("Honda", "Fit", 70, 1));
        cars.add(new Car("Ford", "Focus", 75, 1));
        cars.add(new Car("Chevrolet", "Cruze", 80, 2));
        cars.add(new Car("Nissan", "Versa", 70, 1));
        cars.add(new Car("Toyota", "Tacoma", 110, 5));
        cars.add(new Car("Honda", "Ridgeline", 120, 6));
        cars.add(new Car("Ford", "Ranger", 115, 5));
        cars.add(new Car("Chevrolet", "Colorado", 105, 4));
        cars.add(new Car("Nissan", "Frontier", 100, 4));
        cars.add(new Car("Toyota", "Tundra", 125, 7));
        cars.add(new Car("Honda", "Civic Type R", 130, 8));
        cars.add(new Car("Ford", "Mustang Shelby GT500", 140, 9));
        cars.add(new Car("Chevrolet", "Corvette", 135, 8));
        cars.add(new Car("Nissan", "GT-R", 150, 10));
        cars.add(new Car("Toyota", "Supra", 140, 9));
        cars.add(new Car("Honda", "Insight", 80, 2));
        cars.add(new Car("Ford", "Edge", 95, 3));
        cars.add(new Car("Chevrolet", "Blazer", 100, 4));
        cars.add(new Car("Nissan", "Murano", 105, 5));
        cars.add(new Car("Toyota", "4Runner", 120, 6));
        cars.add(new Car("Honda", "HR-V", 85, 3));
        cars.add(new Car("Ford", "Bronco", 110, 5));
        cars.add(new Car("Chevrolet", "Trailblazer", 90, 2));
        cars.add(new Car("Nissan", "Kicks", 80, 1));
        carRepository.saveAll(cars);
        List<Member> members = new ArrayList<>();
        members.add(new Member("user1", "password1", "user1@example.com", "John", "Doe", "123 Main St", "Cityville", "12345"));
        members.add(new Member("user2", "password2", "user2@example.com", "Jane", "Smith", "456 Elm St", "Towndale", "67890"));
        members.add(new Member("user3", "password3", "user3@example.com", "Michael", "Johnson", "789 Oak St", "Villagetown", "13579"));
        members.add(new Member("user4", "password4", "user4@example.com", "Emily", "Williams", "567 Pine St", "Suburbia", "24680"));
        members.add(new Member("user5", "password5", "user5@example.com", "David", "Brown", "890 Maple St", "Hometown", "97531"));
        members.add(new Member("user6", "password6", "user6@example.com", "Olivia", "Jones", "234 Cedar St", "Hamlet", "86420"));
        members.add(new Member("user7", "password7", "user7@example.com", "William", "Garcia", "876 Birch St", "Meadowville", "25789"));
        members.add(new Member("user8", "password8", "user8@example.com", "Sophia", "Martinez", "543 Walnut St", "Countryside", "10475"));
        members.add(new Member("user9", "password9", "user9@example.com", "James", "Anderson", "678 Spruce St", "Ruraltown", "80246"));
        members.add(new Member("user10", "password10", "user10@example.com", "Emma", "Lee", "901 Oak St", "Villageville", "63127"));
        members.add(new Member("user11", "password11", "user11@example.com", "Daniel", "Rodriguez", "123 Pine St", "Townsville", "48562"));
        members.add(new Member("user12", "password12", "user12@example.com", "Ava", "Lopez", "456 Cedar St", "Urbanville", "29601"));
        members.add(new Member("user13", "password13", "user13@example.com", "Liam", "Hernandez", "789 Birch St", "Metropolis", "71385"));
        members.add(new Member("user14", "password14", "user14@example.com", "Mia", "Gonzalez", "567 Maple St", "Citytown", "96024"));
        members.add(new Member("user15", "password15", "user15@example.com", "Benjamin", "Perez", "890 Elm St", "Suburbania", "54231"));
        memberRepository.saveAll(members);
    }
}
