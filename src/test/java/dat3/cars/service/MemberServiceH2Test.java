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

@DataJpaTest
class MemberServiceH2Test {

    @Autowired
    MemberRepository memberRepository;

    MemberService memberService;

    Member m1, m2;

    @BeforeEach
    void setUp() {
        m1 = memberRepository.save(new Member("user1", "pw1", "email1", "fn1", "ln1",  "street1", "city1", "zip1"));
        m2 = memberRepository.save(new Member("user2", "pw2", "email2", "fn2", "ln2", "street2", "city2", "zip2"));
        memberService = new MemberService(memberRepository);
    }

    @Test
    void testGetMembersAllDetails() {
        List<MemberResponse> responses = memberService.getMembers(true);
        assertEquals(2, responses.size(), "Expected 2 members");
        LocalDateTime created = responses.get(0).getCreated();
        assertNotNull(created, "Dates must be set since TRUE was passed to getMembers");
    }

    @Test
    void testGetMembersNoDetails() {
        List<MemberResponse> responses = memberService.getMembers(false);
        assertEquals(2, responses.size(), "Expected 2 members");
        LocalDateTime created = responses.get(0).getCreated();
        assertNull(created, "Dates must NOT be set since FALSE was passed to getMembers");
    }

    @Test
    void testFindByIdFound() {
        MemberResponse response = memberService.findById("user1");
        assertEquals("user1", response.getUsername());
    }

    @Test
    void testFindByIdNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.findById("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
        /* Remember MemberRequest comes from the API layer, and MemberResponse is returned to the API layer
         * Internally addMember save a Member entity to the database*/
    void testAddMember_UserDoesNotExist() {
        //Add @AllArgsConstructor to MemberRequest and @Builder to MemberRequest for this to work
        //Make sure to include at least all fields which are not nullable
        MemberRequest request = MemberRequest.builder().
                username("user3").
                password("pw3").
                email("aa@bb.dk").
                firstName("fn3").
                lastName("ln3").
                build();
        MemberResponse res = memberService.addMember(request);
        assertEquals("user3", res.getUsername());
        //Here we use the repository to verify that the member was actually saved
        assertTrue(memberRepository.existsById("user3"));
    }

    @Test
    void testAddMember_UserDoesExist() {
        MemberRequest request = new MemberRequest();
        request.setUsername("user1");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.addMember(request));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void testEditMemberWithExistingUsername() {
        //Create a MemberRequest from an existing member we can edit
        MemberRequest request = new MemberRequest(m1);
        request.setFirstName("New First Name");
        request.setLastName("New Last Name");

        memberService.editMember(request, "user1");

        memberRepository.flush();
        MemberResponse response = memberService.findById("user1");

        assertEquals("user1", response.getUsername());
        assertEquals("New First Name", response.getFirstName());
        assertEquals("New Last Name", response.getLastName());
        assertEquals("email1", response.getEmail());
        assertEquals("street1", response.getStreet());
        assertEquals("city1", response.getCity());
        assertEquals("zip1", response.getZip());
    }

    @Test
    void testEditMemberNON_ExistingUsername() {
        MemberRequest memberRequest = new MemberRequest();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.editMember(memberRequest, "I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testEditMemberChangePrimaryKey() {
        //Create a MemberRequest from an existing member we can edit
        MemberRequest request = new MemberRequest(m1);
        request.setUsername("New Username");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.editMember(request, "user1"));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Cannot change username", ex.getReason());
    }

    @Test
    void testSetRankingForUser() {
        memberService.setRankingForUser("user1", 5);
        MemberResponse response = memberService.findById("user1");
        assertEquals(5, response.getRanking());
    }

    @Test
    void testSetRankingForNoExistingUser() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.setRankingForUser("I dont exist", 5));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
    @Test
    void testDeleteMemberByUsername() {
        memberService.deleteMemberByUsername("user1");
        assertFalse(memberRepository.existsById("user1"));
    }

    @Test
    void testDeleteMember_ThatDontExist() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> memberService.deleteMemberByUsername("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
/*@DataJpaTest // Transactional -> Rollback -> In Memory DB
class MemberServiceH2Test {

    @Autowired
    MemberRepository memberRepository;
    MemberService memberService;

    // We don't add the 'isInitialized' flag, because??????????
    Member m1, m2;

    @BeforeEach
    void setUp() {
        m1 = memberRepository.save(new Member("user1", "pw1", "email1", "fn1", "ln1",  "street1", "city1", "zip1"));
        m2 = memberRepository.save(new Member("user2", "pw2", "email1", "fn2", "ln2", "street2", "city2", "zip2"));
        memberService = new MemberService(memberRepository); //Set up memberService with the mock (H2) database
    }

    @Test
    void testEditMemberWithExistingUsername() {
        ResponseEntity<Boolean> response = memberService.editMember(MemberRequest.builder()
                .username("user1")
                .password("pw1")
                .email("newEmail")
                .firstName("fn1")
                .lastName("ln1")
                .street("street1")
                .city("city1")
                .zip("zip1")
                .build(),"user1");

        assertEquals(HttpStatus.OK,response.getStatusCode(), "Expects status code to be OK");
        assertEquals("newEmail", m1.getEmail(), "Expects email to be the same");
    }

    @Test
    void testEditMemberNON_ExistingUsernameThrows() {
        //This should test that a ResponseStatus exception is thrown with status= 404 (NOT_FOUND)
        MemberRequest newInfo = new MemberRequest("user3", "pw3", "email3", "fn3", "ln3", "street3", "city3", "zip3");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                memberService.editMember(newInfo, "user3")
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code to be NOT_FOUND");
    }

    @Test
    void testEditMemberChangePrimaryKeyThrows() {
        MemberRequest newInfo = new MemberRequest("user3", "pw1", "emailNew", "fn1", "ln1", "street1", "city1", "zip1");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()->
                memberService.editMember(newInfo, "user1")
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Expects status code BAD_REQUEST");
    }

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
        assertEquals("user1", response.getUsername(), "Expects response username to be user1");
    }

    @Test
    void testFindByIdNotFound() {
        //This should test that a ResponseStatus exception is thrown with status= 404 (NOT_FOUND)
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberService.findById("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }

    *//* Remember MemberRequest comes from the API layer, and MemberResponse is returned to the API layer
     * Internally addMember save a Member entity to the database*//*
    @Test
    void testAddMember_UserDoesNotExist() {
        //Add @AllArgsConstructor to MemberRequest and @Builder to MemberRequest for this to work
        MemberRequest request = MemberRequest.builder().username("user3")
                .password("pw3")
                .email("email3")
                .firstName("fn3")
                .lastName("ln3")
                .street("street3")
                .city("city3")
                .zip("zip3").build();
        MemberResponse response = memberService.addMember(request);
        List<MemberResponse> memberResponses = memberService.getMembers(false);

        assertEquals(3, memberResponses.size(), "Expects size of responses to be 3");
        assertEquals("email3", response.getEmail(), "Expects response email to have changed to email3");
    }

    @Test
    void testAddMember_UserDoesExistThrows() {
        //This should test that a ResponseStatus exception is thrown with status= 409 (BAD_REQUEST)
        MemberRequest request = MemberRequest.builder().username("user1")
                .password("pw1")
                .email("email1")
                .firstName("fn1")
                .lastName("ln1")
                .street("street1")
                .city("city1")
                .zip("zip1").build();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberService.addMember(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Expects status code BAD_REQUEST");
    }

    @Test
    void testSetRankingForUser() {
        memberService.setRankingForUser("user1", 120);
        assertEquals(120, m1.getRanking(), "Expects ranking of m1 to be changed to 120");
    }

    @Test
    void testSetRankingForNoExistingUser() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberService.setRankingForUser("I dont exist", 120));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }
    @Test
    void testDeleteMemberByUsername() {
        memberService.deleteMemberByUsername("user1");
        List<MemberResponse> memberResponses = memberService.getMembers(false);
        assertEquals(1, memberResponses.size(), "Expects size of responses to be 1 after deletion");
    }

    @Test
    void testDeleteMember_ThatDontExist() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> memberService.deleteMemberByUsername("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expects status code NOT_FOUND");
    }
}*/

