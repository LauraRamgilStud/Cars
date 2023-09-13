package dat3.cars.entity;

import dat3.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//------------
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class Member extends UserWithRoles {

    @Column(name = "member_fname")
    private String firstName;
    @Column(name = "member_lname")
    private String lastName;
    @Column(name = "street")
    private String street;
    @Column(name = "member_city")
    private String city;
    @Column(name = "member_zip")
    private String zip;
    @Column(name = "member_approved")
    private boolean approved;
    @Column(name = "member_ranking")
    private int ranking;

    // Mapped by name of the attribute in the class at the other end -> in this case the Reservation class
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public void addReservation(Reservation reservation){
        if(reservations == null){
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
    }

    public Member(String user, String password, String email, String firstName, String lastName, String street, String city, String zip){
        super(user, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
    }
}
