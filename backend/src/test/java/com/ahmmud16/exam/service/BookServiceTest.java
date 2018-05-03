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

        Long id = bookService.createBook(title, author, description, course);

        assertNotNull(id);
    }

    @Test
    public void testCreateBookWithSameTitle() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        Long a = bookService.createBook(title, author, description, course);
        assertNotNull(a);

        Long b = bookService.createBook(title, author, description, course);
        assertNull(b);
    }

    @Test
    public void testGetBook() {

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        Book book = bookService.getBook(title);

        assertEquals(title, book.getBookTitle());
        assertEquals(author, book.getBookAuthor());
        assertEquals(description, book.getBookDescription());
        assertEquals(course, book.getCourseWhereBookIsUsed());
    }

    @Test
    public void testGetBookNotExist() {

        try {
            assertNull(bookService.getBook("nonExsists"));
            fail();
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void testDeleteBook() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        assertTrue(bookService.deleteBook(title));
    }

    @Test
    public void deleteBookWhichNotExists() {

        try {
            assertFalse(bookService.deleteBook("notExists"));
            fail();
        } catch (Exception e){
            // excepted
        }
    }

    @Test
    public void testAddUserToABookThatNotExists() {

        try {
            assertFalse(bookService.addOrRemoveUserFromBookList("notExists", "user@mail.com", true));
            fail();
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void testAddTwoSameEmailsToABook() {

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        String user = "userOne@mail.com";

        assertTrue(bookService.addOrRemoveUserFromBookList(user, title, true));
        assertFalse(bookService.addOrRemoveUserFromBookList(user, title, true));
    }

    @Test
    public void testRemoveUserFromABookThatNotExists() {

        String bookTitle = "notExists";
        String username = "user@mail.com";

         try {
             assertFalse(bookService.addOrRemoveUserFromBookList(username, bookTitle, false));
         } catch (Exception e) {
             // excepted
         }
    }

    @Test
    public void testRemoveUserFromABookWhereUserNotIsAdded() {
        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        bookService.createBook(title, author, description, course);

        String user = "notExsists";

        assertFalse(bookService.addOrRemoveUserFromBookList(user, title, false));

    }



    @Test
    public void testGetAllBooks() {

        assertNull(bookService.getAllBooks());

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

        List<Book> list = bookService.getAllBooks();

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

        assertTrue(bookService.addOrRemoveUserFromBookList(userOne, title, true));
        assertTrue(bookService.addOrRemoveUserFromBookList(userTwo, title, true));

        Book book = bookService.getBook(title);

        assertTrue(book.getUsers().contains(userOne));
        assertTrue(book.getUsers().contains(userTwo));
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


        assertTrue(bookService.addOrRemoveUserFromBookList(userOne, title, true));
        assertTrue(bookService.addOrRemoveUserFromBookList(userTwo, title, true));

        Book book = bookService.getBook(title);

        assertTrue(book.getUsers().contains(userOne));
        assertTrue(book.getUsers().contains(userTwo));
        assertEquals(2, book.getUsers().size());

        bookService.addOrRemoveUserFromBookList(userOne, title, false);

        Book book1 = bookService.getBook(title);

        assertFalse(book1.getUsers().contains(userOne));
        assertTrue(book1.getUsers().contains(userTwo));
        assertEquals(1, book1.getUsers().size());
    }

    @Test
    public void testGetAllBooksForSale() {

        assertNull(bookService.getAllBooksThatAreForSale());

        String title = "JavaEE";
        String author = "Andrea Arcuri";
        String description = "Java EE is AWESOME!";
        String course = "Enterprise Programming 1";

        String user = "user";

        Long id = bookService.createBook(title, author, description, course);
        assertNotNull(id);

        assertTrue(bookService.addOrRemoveUserFromBookList(user, title, true));

        List<Book> book = bookService.getAllBooksThatAreForSale();
        assertEquals(1, book.size());

    }

}