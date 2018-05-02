package com.ahmmud16.exam.service;

import com.ahmmud16.exam.StubApplication;
import com.ahmmud16.exam.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase {

    @Test
    public void testCanCreateUser() {

        String username = "a@mail.com";
        String firstname = "a";
        String lastname = "b";
        String password = "123";

        boolean isCreated = createUser(username, firstname, lastname, password, false);
        boolean isEnabled = getUser(username).getEnabled();

        assertTrue(isCreated);
        assertTrue(isEnabled);
    }

    @Test
    public void testCreateUserWithNull() {

        String username = null;
        String firstname = null;
        String lastname = null;
        String password = null;

        try {
            createUser(username, firstname, lastname, password, false);
            fail();
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void testCanCreateWithWhiteSpaces() {

        String username = "     ";
        String firstname = "a";
        String lastname = "a";
        String password = "123";

        try {
            createUser(username, firstname, lastname, password, false);
            fail();
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testCreateTwoUserWithSameId() {

        String userOneUsername = "a@mail.com";
        String userOneFirstname = "a";
        String userOneLastname = "b";
        String userOnePassword = "123";

        boolean isUserOneCreated = createUser(userOneUsername, userOneFirstname, userOneLastname, userOnePassword, false);
        assertTrue(isUserOneCreated);

        String userTwoUsername = "a@mail.com";
        String userTwoFirstname = "a";
        String userTwoLastname = "b";
        String userTwoPassword = "123";

        boolean isUserTwoCreated = createUser(userTwoUsername, userTwoFirstname, userTwoLastname, userTwoPassword, false);
        assertFalse(isUserTwoCreated);

    }

    @Test
    public void testWhichRoleUserHave() {

        String userOneUsername = "a@mail.com";
        String userOneFirstname = "a";
        String userOneLastname = "b";
        String userOnePassword = "123";

        createUser(userOneUsername, userOneFirstname, userOneLastname, userOnePassword, false);
        User getUserOne = getUser(userOneUsername);

        assertTrue(getUserOne.getRoles().contains("ROLE_USER"));

        String userTwoUsername = "aa@mail.com";
        String userTwoFirstname = "a";
        String userTwoLastname = "b";
        String userTwoPassword = "123";

        createUser(userTwoUsername, userTwoFirstname, userTwoLastname, userTwoPassword, true);
        User getUserTwo = getUser(userTwoUsername);

        assertTrue(getUserTwo.getRoles().contains("ROLE_ADMIN"));
    }

    @Test
    public void testUserCredentials() {

        String username = "a@mail.com";
        String firstname = "a";
        String lastname = "b";
        String password = "123";

        createUser(username, firstname, lastname, password, false);
        User user = getUser(username);

        assertEquals("a@mail.com", user.getUsername());
        assertEquals("a", user.getFirstname());
        assertEquals("b", user.getLastname());
    }
}