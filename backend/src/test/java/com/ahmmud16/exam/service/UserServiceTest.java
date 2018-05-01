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

        String userId = "a";
        String userPassword = "123";

        boolean isCreated = createUser(userId, userPassword, false);
        assertTrue(isCreated);

    }

    @Test
    public void testCreateUserWithNull() {

        String userId = null;
        String userPassword = null;

        try {
            createUser(userId, userPassword, false);
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void testCanCreateUserWithInvalidRegEx() {

        String userId = "a@";
        String userPassword = "123";

        try{
            createUser(userId, userPassword, false);
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testCanCreateWithWhiteSpaces() {

        String userId = "     ";
        String userPassword = "123";

        try {
            createUser(userId, userPassword, false);
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testCreateTwoUserWithSameId() {

        String userOne = "a";
        String userOnePassword = "123";

        boolean isUserOneCreated = createUser(userOne, userOnePassword, false);
        assertTrue(isUserOneCreated);

        String userTwo = "a";
        String userTwoPassword = "123";

        boolean isUserTwoCreated = createUser(userTwo, userTwoPassword, false);
        assertFalse(isUserTwoCreated);

    }

    @Test
    public void testWhichRoleUserHave() {

        String userOne = "a";
        String userOnePassword = "123";

        createUser(userOne, userOnePassword, false);
        User getUserOne = getUser(userOne);

        assertEquals("a", getUserOne.getUsername());

        String userTwo = "b";
        String userTwoPassword = "123";

        createUser(userTwo, userTwoPassword, true);
        User getUserTwo = getUser(userTwo);

        assertEquals("b", getUserTwo.getUsername());
    }
}