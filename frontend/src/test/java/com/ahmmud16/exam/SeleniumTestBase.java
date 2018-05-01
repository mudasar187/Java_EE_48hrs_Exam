package com.ahmmud16.exam;

import com.ahmmud16.exam.po.AdminPO;
import com.ahmmud16.exam.po.IndexPO;
import com.ahmmud16.exam.po.LoginPO;
import com.ahmmud16.exam.po.SignUpPO;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId(){
        return "fooSeleniumLocalIT" + counter.getAndIncrement();
    }

    private IndexPO home;


    @Before
    public void initTest() {

        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue("Failed to start from Home Page", home.isOnPage());
    }

//    @Test
//    public void testCreateAndLogoutUser(){
//
//        assertFalse(home.isLoggedIn());
//
//        String username = getUniqueId();
//        String password = "123456789";
//        String retypePassword = "123456789";
//
//        home.toStartingPage();
//        SignUpPO signUpPO = home.toSignUp();
//        IndexPO indexPO = signUpPO.createUser(username, password, retypePassword);
//
//        assertNotNull(indexPO);
//        assertTrue(home.isLoggedIn());
//        assertTrue(home.getDriver().getPageSource().contains(username));
//
//        home.doLogout();
//
//        assertFalse(home.isLoggedIn());
//        assertFalse(home.getDriver().getPageSource().contains(username));
//    }
//
//    @Test
//    public void testCreateUserWithInvalidUsername() {
//
//        String username = "username__";
//        String password = "123456789";
//        String retypePassword = "123456789";
//
//        SignUpPO signUpPO = home.toSignUp();
//        IndexPO indexPO = signUpPO.createUser(username, password, retypePassword);
//
//        assertNull(indexPO);
//        assertTrue(signUpPO.isOnPage());
//        assertFalse(home.isLoggedIn());
//
//    }
//
//    @Test
//    public void testCreateUserWithNonMatchPassword() {
//
//        String username = "username";
//        String password = "123456789";
//        String retypePassword = "1234";
//
//        SignUpPO signUpPO = home.toSignUp();
//        IndexPO indexPO = signUpPO.createUser(username, password, retypePassword);
//
//        assertNull(indexPO);
//        assertTrue(signUpPO.isOnPage());
//        assertFalse(home.isLoggedIn());
//    }
//
//    @Test
//    public void testLoginWithNonExistingUser() {
//
//        assertFalse(home.isLoggedIn());
//
//        String username = "usernameOne";
//        String password = "1234";
//
//        LoginPO loginPO = home.toLogin();
//
//        assertTrue(loginPO.isOnPage());
//
//        IndexPO indexPO = loginPO.enterCredentials(username, password);
//
//        assertNull(indexPO);
//        assertTrue(loginPO.isOnPage());
//        assertFalse(home.isLoggedIn());
//    }
//
//    @Test
//    public void testCreateAndThenLoginWithWrongPassword() {
//
//        assertFalse(home.isLoggedIn());
//
//        String username = "usernameTwo";
//        String password = "123456789";
//        String retypePassword = "123456789";
//        String wrongPassWord = "123";
//
//        SignUpPO signUpPO = home.toSignUp();
//        IndexPO indexPO = signUpPO.createUser(username, password, retypePassword);
//
//        assertTrue(home.getDriver().getPageSource().contains(username));
//        assertNotNull(indexPO);
//        assertTrue(home.isOnPage());
//
//        indexPO.doLogout();
//
//        assertTrue(home.isOnPage());
//
//        LoginPO loginPO = home.toLogin();
//
//        assertTrue(loginPO.isOnPage());
//
//        loginPO.enterCredentials(username, wrongPassWord);
//
//        assertNotNull(loginPO);
//        assertFalse(home.isLoggedIn());
//
//    }
//
//    @Test
//    public void testCreateTwoUsersWithSameUsername() {
//
//        assertFalse(home.isLoggedIn());
//
//        String username = "usernameThree";
//        String password = "123456789";
//        String retypePassword = "123456789";
//
//        home.toStartingPage();
//
//        SignUpPO signUpPO = home.toSignUp();
//
//        IndexPO indexPO = signUpPO.createUser(username, password, retypePassword);
//
//        assertNotNull(indexPO);
//        assertTrue(home.isLoggedIn());
//        assertTrue(home.getDriver().getPageSource().contains(username));
//
//        home.doLogout();
//        home.toSignUp();
//        IndexPO indexPO1 = signUpPO.createUser(username, password, retypePassword);
//
//        assertTrue(signUpPO.isOnPage());
//        assertNull(indexPO1);
//        assertFalse(home.isLoggedIn());
//    }

    @Test
    public void createUserWithAdminAccess() {

        assertFalse(home.isLoggedIn());

        String username = "usernameFour";
        String password = "123";
        String retypePassword = "123";
        String text = "You are registered as admin, click here to go to admin page";

        home.toStartingPage();
        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUserAsAdmin(username, password, retypePassword);

        assertNotNull(indexPO);
        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));
        assertTrue(home.getDriver().getPageSource().contains(text));

        AdminPO adminPO = home.toAdmin();

        assertNotNull(adminPO);
        assertTrue(adminPO.isOnPage());

        home.doLogout();

        assertTrue(home.isOnPage());
        assertFalse(home.isLoggedIn());
    }

}
