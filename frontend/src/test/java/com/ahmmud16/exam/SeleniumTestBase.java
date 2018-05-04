package com.ahmmud16.exam;

import com.ahmmud16.exam.po.*;
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

    private String getUniqueId() {
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

    @Test
    public void testCreateAndLogoutUser(){

        //
        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String firstname = "test1";
        String lastname = "test1";
        String password = "123456789";
        String retypePassword = "123456789";

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();
        IndexPO indexPO = signUpPO.createUser(username, firstname, lastname, password, retypePassword);

        assertNotNull(indexPO);
        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(username));
    }

    @Test
    public void testCreateUserWithNonMatchPassword() {

        String username = "username@mail.com";
        String firstname = "test2";
        String lastname = "test2";
        String password = "123456789";
        String retypePassword = "1234";

        SignUpPO signUpPO = home.toSignUp();
        IndexPO indexPO = signUpPO.createUser(username, firstname, lastname, password, retypePassword);

        assertNull(indexPO);
        assertTrue(signUpPO.isOnPage());
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testLoginWithNonExistingUser() {

        assertFalse(home.isLoggedIn());

        String username = "usernameOne@notexists.com";
        String password = "1234";

        LoginPO loginPO = home.toLogin();

        assertTrue(loginPO.isOnPage());

        IndexPO indexPO = loginPO.enterCredentials(username, password);

        assertNull(indexPO);
        assertTrue(loginPO.isOnPage());
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testCreateAndThenLoginWithWrongPassword() {

        assertFalse(home.isLoggedIn());

        String username = "test3@mail.com";
        String firstname = "test3";
        String lastname = "test3";
        String password = "123456789";
        String retypePassword = "123456789";
        String wrongPassWord = "123";

        SignUpPO signUpPO = home.toSignUp();
        IndexPO indexPO = signUpPO.createUser(username, firstname, lastname, password, retypePassword);

        assertTrue(home.getDriver().getPageSource().contains(username));
        assertNotNull(indexPO);
        assertTrue(home.isOnPage());

        indexPO.doLogout();

        assertTrue(home.isOnPage());

        LoginPO loginPO = home.toLogin();

        assertTrue(loginPO.isOnPage());

        loginPO.enterCredentials(username, wrongPassWord);

        assertNotNull(loginPO);
        assertFalse(home.isLoggedIn());

    }

    @Test
    public void testCreateTwoUsersWithSameUsername() {

        assertFalse(home.isLoggedIn());

        String username = "test4@mail.com";
        String firstname = "test4";
        String lastname = "test4";
        String password = "123456789";
        String retypePassword = "123456789";

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(username, firstname, lastname, password, retypePassword);

        assertNotNull(indexPO);
        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));

        home.doLogout();
        home.toSignUp();

        IndexPO indexPO1 = signUpPO.createUser(username, firstname, lastname, password, retypePassword);

        assertTrue(signUpPO.isOnPage());
        assertNull(indexPO1);
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testDefaultBooks() {
        assertFalse(home.isLoggedIn());

        // Go to homepage
        home.toStartingPage();

        // We have three books initialized as default, we want to get three rows
        assertEquals(3, home.getNumberOfDisplayedRows("bookTable"));

    }

    @Test
    public void testRegisterSelling() {

        assertFalse(home.isLoggedIn());

        String username = "foo@mail.com";
        String password = "123";

        // Go to homepage
        home.toStartingPage();

        // We are not logged in, except to see 3 rows without logging in
        assertEquals(3, home.getNumberOfDisplayedColumns("bookTable"));

        // Now logging in and should see four rows where user can mark their book selling or not
        LoginPO loginPO = home.toLogin();

        assertNotNull(loginPO);
        assertTrue(loginPO.isOnPage());

        IndexPO indexPO = loginPO.enterCredentials(username, password);

        assertNotNull(indexPO.isOnPage());
        assertTrue(home.isLoggedIn());

        // We should now see 4 columns which means there is an extra column for mark selling book or not
        assertEquals(4, home.getNumberOfDisplayedColumns("bookTable"));

        // Check how many count there is in seller for first book and second book
        int getValueOfSellersCurrentFirstBook = Integer.valueOf(home.checkCountOfSellers("0"));
        int getValueOfSellersCurrentSecondbook = Integer.valueOf(home.checkCountOfSellers("1"));


        // Now user want to sell a book first book
        home.clickAddUserToListButton("0");

        // Check again for how many seller there is in seller for first book should increment by one
        // Check also sellers for second book, should stay same
        int getValueOfSellersAfterAddUserFirstbook = Integer.valueOf(home.checkCountOfSellers("0"));
        int getValueOfSellersAfterAddUserFirstbookForSecondbook = Integer.valueOf(home.checkCountOfSellers("1"));

        assertEquals(getValueOfSellersCurrentFirstBook+1, getValueOfSellersAfterAddUserFirstbook);
        assertEquals(getValueOfSellersCurrentSecondbook, getValueOfSellersAfterAddUserFirstbookForSecondbook);

        // Click on remove to remove user from list
        home.clickRemoveUserFromListButton("0");

        // Check how many count there is in seller
        int getValueOfSellerAfterRemoveUserFirstbook = Integer.valueOf(home.checkCountOfSellers("0"));
        assertEquals(getValueOfSellersCurrentFirstBook,getValueOfSellerAfterRemoveUserFirstbook);

        // Click selling again for firstbook
        home.clickAddUserToListButton("0");
        int a = Integer.valueOf(home.checkCountOfSellers("0"));
        assertEquals(2, a);

        // Log out
        home.doLogout();

        // Now check if the sellers has same value when logout
        int b = Integer.valueOf(home.checkCountOfSellers("0"));
        assertEquals(a, b);

    }

    @Test
    public void testBookDetails() {

        assertFalse(home.isLoggedIn());

        String userOne = "foo@mail.com";
        String userOnePassword = "123";

        String userTwo = "bar@mail.com";
        String userTwoPassword = "123";

        // Go to homepage
        home.toStartingPage();

        // Go to login
        LoginPO loginPO = home.toLogin();

        assertNotNull(loginPO);
        assertTrue(loginPO.isOnPage());

        IndexPO indexPO = loginPO.enterCredentials(userOne, userOnePassword);

        assertNotNull(indexPO);
        assertTrue(indexPO.isOnPage());

        // Click for mark selling
        home.clickAddUserToListButton("2");

        // Go to book details and check if user is listed on sellers table
        BookDetailsPO bookDetailsPO = home.chooseBook("2");

        assertNotNull(bookDetailsPO);
        assertTrue(bookDetailsPO.isOnPage());

        // Check if user is listed in table sellers
        String getEmailListedInTable = bookDetailsPO.getEmailTextFromTable(userOne);

        // Assert that user is listed in table
        assertEquals(userOne, getEmailListedInTable);

        // Log out
        home.doLogout();

        // Login with another user
        LoginPO loginPO1 = home.toLogin();
        assertNotNull(loginPO1);
        assertTrue(loginPO1.isOnPage());

        IndexPO indexPO1 = loginPO1.enterCredentials(userTwo, userTwoPassword);

        assertNotNull(indexPO1);
        assertTrue(indexPO1.isOnPage());

        // Go to book details
        home.chooseBook("2");

        // Check if sellers foo@mail.com is listed
        String veriFyEmailIsListed = bookDetailsPO.getEmailTextFromTable(userOne);

        // Verify that email is same as above
        assertEquals(userOne, veriFyEmailIsListed);

        // Log out
        home.doLogout();

        assertFalse(home.isLoggedIn());

    }

    @Test
    public void testMessages() {

        assertFalse(home.isLoggedIn());

        String userOne = "foo@mail.com";
        String userOnePassword = "123";

        String userTwo = "bar@mail.com";
        String userTwoPassword = "123";

        // Go to homepage
        home.toStartingPage();

        // userOne log in
        LoginPO loginPO = home.toLogin();

        assertNotNull(loginPO);
        assertTrue(loginPO.isOnPage());

        IndexPO indexPO = loginPO.enterCredentials(userOne, userOnePassword);

        assertNotNull(indexPO);
        assertTrue(indexPO.isOnPage());

        // Click "add me to list" button
        home.clickAddUserToListButton("2");

        // Log out userOne
        home.doLogout();

        // Login with userTwo
        LoginPO loginPO1 = home.toLogin();

        IndexPO indexPO1 = loginPO1.enterCredentials(userTwo, userTwoPassword);

        assertNotNull(indexPO1);
        assertTrue(indexPO1.isOnPage());

        // Go to book details page
        BookDetailsPO bookDetailsPO = indexPO1.chooseBook("2");

        assertNotNull(bookDetailsPO);
        assertTrue(bookDetailsPO.isOnPage());

        // Click send message button
        SendMessagePO sendMessagePO = bookDetailsPO.clickSendMessageButton("0");

        // Text to send
        String text = "Hello!";
        String moreText = "Hello again!!";

        // Create message and send
        sendMessagePO.createMessage(text);
        sendMessagePO.createMessage(moreText);

        // Log out userTwo
        home.doLogout();

        home.toLogin();

        // Log in userOne again
        IndexPO indexPO2 = loginPO1.enterCredentials(userOne, userOnePassword);

        // Go to inbox page
        InboxPO inboxPO = indexPO2.toInbox();

        assertNotNull(inboxPO);
        assertTrue(inboxPO.isOnPage());

        // Check if there is any reply from userTwo
        String getEmail = inboxPO.getTextFromTable(userTwo);
        String getMessage = inboxPO.getTextFromTable(text);

        // Assert that email is userTwo and text is same as what userOne sent
        assertEquals(userTwo, getEmail);
        assertEquals(text, getMessage);



        // userOne want to reply
        SendMessagePO sendMessagePO1 = inboxPO.clickReplyButton("0");

        assertNotNull(sendMessagePO1);
        assertTrue(sendMessagePO1.isOnPage());

        String replyMessage = "Hello back at ya!";

        // Reply message
        sendMessagePO1.createMessage(replyMessage);

        // Log out userOne
        home.doLogout();

        // userTwo log in
        home.toLogin();

        IndexPO indexPO3 = loginPO1.enterCredentials(userTwo, userTwoPassword);

        assertNotNull(indexPO3);
        assertTrue(indexPO3.isOnPage());

        // userTwo go to inbox
        InboxPO inboxPO1 = home.toInbox();

        assertNotNull(inboxPO1);
        assertTrue(inboxPO1.isOnPage());

        String checkEmailFromUser = inboxPO1.getTextFromTable(userOne);
        String checkTextFromUserOne = inboxPO1.getTextFromTable(replyMessage);

        assertEquals(userOne, checkEmailFromUser);
        assertEquals(replyMessage, checkTextFromUserOne);

        home.doLogout();

        assertFalse(home.isLoggedIn());
    }


    @Test
    public void testAdmin() {

        assertFalse(home.isLoggedIn());

        String user = "bar@mail.com";
        String password = "123";

        home.toStartingPage();

        // Go to login page
        LoginPO loginPO = home.toLogin();

        assertNotNull(loginPO);
        assertTrue(loginPO.isOnPage());

        // Log in
        IndexPO indexPO = loginPO.enterCredentials(user, password);

        assertNotNull(indexPO);
        assertTrue(indexPO.isOnPage());

        // Check how many rows
        int rows = indexPO.getNumberOfDisplayedRows("bookTable");
        assertEquals(3, rows);

        // delete a row, only admin can do it
        indexPO.clickDeleteButton("0");

        int newRows = indexPO.getNumberOfDisplayedRows("bookTable");
        assertEquals(2, newRows);

        CreateBookPO createBookPO = indexPO.clickCreateABook();

        assertNotNull(createBookPO);
        assertTrue(createBookPO.isOnPage());

        String bookTitle = "test";
        String bookAuthor = "test";
        String bookCourse = "test";
        String bookDesc = "test";

        createBookPO.createBook(bookTitle, bookAuthor, bookCourse, bookDesc);

        createBookPO.clickCreateBook();

        home.toHomePage();

        int newRowsAgain = indexPO.getNumberOfDisplayedRows("bookTable");


        assertEquals(3, newRowsAgain);

        indexPO.doLogout();

    }

}
