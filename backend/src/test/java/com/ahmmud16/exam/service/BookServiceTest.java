package com.ahmmud16.exam.service;

import com.ahmmud16.exam.StubApplication;
import com.ahmmud16.exam.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest extends ServiceTestBase {

    @Autowired
    private BookService bookService;

    @Test
    public void testCreateBook() {

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        boolean isBookCreated = bookService.createBook(title, author, description, course);

        assertTrue(isBookCreated);
    }

    @Test
    public void testCreateBookWithSameTitle() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        boolean isBookCreated = bookService.createBook(title, author, description, course);

        assertTrue(isBookCreated);

        boolean isBookCreatedWithSameId = bookService.createBook(title, author, description, course);

        assertFalse(isBookCreatedWithSameId);
    }

    @Test
    public void testGetBook() {

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        Book book = bookService.getBook(title);

        assertEquals("JavaEE", book.getBookTitle());
        assertEquals("Andrea Arcuri", book.getBookAuthor());
        assertEquals("Java EE is AWESOME!", book.getBookDescription());
        assertEquals("Enterprise Programming 1", book.getCourseWhereBookIsUsed());
    }

    @Test
    public void testGetBookNotExist() {

        Book book = bookService.getBook("nonExsists");
        assertNull(book);
    }

    @Test
    public void testDeleteBook() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        Book book = bookService.getBook(title);

        boolean isDeleted = bookService.deleteBook(book.getBookTitle());

        assertTrue(isDeleted);
    }

    @Test
    public void deleteBookWhichNotExists() {

        boolean isDeleted = bookService.deleteBook("notExists");

        assertFalse(isDeleted);
    }

    @Test
    public void testAddUserToABookThatNotExists() {

        String bookTitle = "notExists";
        String username = "user@mail.com";

        boolean isUserAddedToBook = bookService.addUserToBook(username, bookTitle);

        assertFalse(isUserAddedToBook);
    }

    @Test
    public void testAddTwoSameEmailsToABook() {

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        String userOne = "userOne@mail.com";
        String userTwo = "userOne@mail.com";

        boolean isUserOneAddedToList = bookService.addUserToBook(userOne, title);
        boolean isUserTwoAddedToList = bookService.addUserToBook(userTwo, title);

        assertTrue(isUserOneAddedToList);
        assertFalse(isUserTwoAddedToList);
    }

    @Test
    public void testRemoveUserFromABookThatNotExists() {

        String bookTitle = "notExists";
        String username = "user@mail.com";

        boolean isUserRemovedFromBook = bookService.removeUserFromBook(username, bookTitle);

        assertFalse(isUserRemovedFromBook);
    }

    @Test
    public void testRemoveUserFromABookWhereUserNotIsAdded() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        String user = "notExsists";

        boolean isUserRemovedFromBook = bookService.removeUserFromBook(user, title);

        assertFalse(isUserRemovedFromBook);

    }



    @Test
    public void testGetAllBooks() {

        assertNull(bookService.getAllBook());

        String title1 = "JavaEE";
        String author1 = "Andrea Arcuri";
        String description1 = "Java EE is AWESOME!";
        String course1 = "Enterprise Programming 1";

        bookService.createBook(title1, author1, description1, course1);

        String title2 = "Algorithms And Datastructure";
        String author2 = "Andrea Arcuri";
        String description2 = "Algorithms is AWESOME!";
        String course2 = "Algortihms and Datastructures";

        bookService.createBook(title2, author2, description2, course2);

        List<Book> list = bookService.getAllBook();

        assertEquals(2, list.size());
    }

    @Test
    public void testAddUserToBook() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        String userOne = "userOne";
        String userTwo = "userTwo";

        bookService.createBook(title, author, description, course);

        boolean isUserOneAdded = bookService.addUserToBook(userOne, title);
        boolean isUserTwoAdded = bookService.addUserToBook(userTwo, title);

        assertTrue(isUserOneAdded);
        assertTrue(isUserTwoAdded);

        Book book = bookService.getBook(title);

        assertTrue(book.getUsers().contains("userOne"));
        assertTrue(book.getUsers().contains("userTwo"));
        assertEquals(2, book.getUsers().size());

    }

    @Test
    public void testRemoveUserFromBook() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        String userOne = "userOne";
        String userTwo = "userTwo";

        bookService.createBook(title, author, description, course);

        boolean isUserOneAdded = bookService.addUserToBook(userOne, title);
        boolean isUserTwoAdded = bookService.addUserToBook(userTwo, title);

        assertTrue(isUserOneAdded);
        assertTrue(isUserTwoAdded);

        Book book = bookService.getBook(title);

        assertTrue(book.getUsers().contains(userOne));
        assertTrue(book.getUsers().contains(userTwo));
        assertEquals(2, book.getUsers().size());

        bookService.removeUserFromBook(userOne, title);

        Book book1 = bookService.getBook(title);

        assertFalse(book1.getUsers().contains(userOne));
        assertTrue(book1.getUsers().contains(userTwo));
        assertEquals(1, book1.getUsers().size());
    }

}