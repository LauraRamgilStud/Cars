package dat3.cars.repository;

import dat3.cars.entity.Car;
import dat3.cars.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    boolean isInitialized = false;

    @BeforeEach
    void setUp(){
        if(!isInitialized){
            memberRepository.save(new Member("user1", "password1", "user1@example.com", "John", "Doe", "123 Main St", "Cityville", "12345"));
            memberRepository.save(new Member("user2", "password2", "user2@example.com", "Jane", "Smith", "456 Elm St", "Towndale", "67890"));
            isInitialized = true;
        }
    }

    @Test
    public void deleteAll(){
        memberRepository.deleteAll();
        assertEquals(0, memberRepository.count());
    }

    @Test
    public void testAll(){
        Long count = memberRepository.count();
        assertEquals(2, count);
    }
}