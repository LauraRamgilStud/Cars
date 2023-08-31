package dat3.cars.service;

import dat3.cars.dto.MemberRequest;
import dat3.cars.dto.MemberResponse;
import dat3.cars.entity.Member;
import dat3.cars.repository.MemberRepository;
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

@DataJpaTest //transaktionelle (rollback)
class MemberServiceH2Test {

    @Autowired
    MemberRepository memberRepository;
    MemberService memberService;

    Member m1, m2;  //Talk about references in Java for why we don't add the "isInitialize flag"

    @BeforeEach
    void setUp() {
        m1 = memberRepository.save(new Member("user1", "pw1", "email1", "fn1", "ln1",  "street1", "city1", "zip1"));
        m2 = memberRepository.save(new Member("user2", "pw2", "email1", "fn2", "ln2", "street2", "city2", "zip2"));
        memberService = new MemberService(memberRepository); //Set up memberService with the mock (H2) database
    }

    //---------------------------------------------------------------
    @Test
    void testEditMemberWithExistingUsername() {
        ResponseEntity<Boolean> response = memberService.editMember(MemberRequest.builder()
                .username("user1")
                .password("pw1")
                .email("emailx")
                .firstName("fn1")
                .lastName("ln1")
                .street("street1")
                .city("city1")
                .zip("zip1")
                .build(),"user1");

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("emailx", m1.getEmail());
    }

    @Test
    void testEditMemberNON_ExistingUsernameThrows() {
        //This should test that a ResponseStatus exception is thrown with status= 404 (NOT_FOUND)
        MemberRequest newInfo = new MemberRequest("user3", "pw1", "emailx", "fn1", "ln1", "street1", "city1", "zip1");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                memberService.editMember(newInfo, "user3")
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testEditMemberChangePrimaryKeyThrows() {
        MemberRequest newInfo = new MemberRequest("user3", "pw1", "emailx", "fn1", "ln1", "street1", "city1", "zip1");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                memberService.editMember(newInfo, "user1")
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
    //---------------------------------------------------------------

    @Test
    void testGetMembersAllDetails() {
        List<MemberResponse> memberResponses = memberService.getMembers(true);
        assertEquals(2, memberResponses.size());
        LocalDateTime time = memberResponses.get(0).getCreated();
        assertNotNull(time, "Expects date to be set, when true is passed");
    }

    @Test
    void testGetMembersNoDetails() {
        List<MemberResponse> memberResponses = memberService.getMembers(false);
        assertEquals(2, memberResponses.size());
        LocalDateTime time = memberResponses.get(0).getCreated();
        assertNull(time, "Expects date to not be set, when false is passed");
    }

    @Test
    void testFindByIdFound() {
        MemberResponse response = memberService.findById("user1");
        assertEquals("user1", response.getUsername());
    }

    @Test
    void testFindByIdNotFound() {
        //This should test that a ResponseStatus exception is thrown with status= 404 (NOT_FOUND)
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberService.findById("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
        /* Remember MemberRequest comes from the API layer, and MemberResponse is returned to the API layer
         * Internally addMember save a Member entity to the database*/
    void testAddMember_UserDoesNotExist() {
        //Add @AllArgsConstructor to MemberRequest and @Builder to MemberRequest for this to work
        MemberRequest request = MemberRequest.builder().username("user1")
                .password("pw1")
                .email("emailx")
                .firstName("fn1")
                .lastName("ln1")
                .street("street1")
                .city("city1")
                .zip("zip1").build();
    }

    @Test
    void testAddMember_UserDoesExistThrows() {
        //This should test that a ResponseStatus exception is thrown with status= 409 (BAD_REQUEST)
        //TODO
    }

    @Test
    void testSetRankingForUser() {
        //TODO
    }

    @Test
    void testSetRankingForNoExistingUser() {
        //TODO
    }
    @Test
    void testDeleteMemberByUsername() {
        //TODO
    }

    @Test
    void testDeleteMember_ThatDontExist() {
        //TODO
    }
}

