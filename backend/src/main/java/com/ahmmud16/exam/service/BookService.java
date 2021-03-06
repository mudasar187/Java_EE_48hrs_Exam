package com.ahmmud16.exam.service;

import com.ahmmud16.exam.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private EntityManager em;

    public Long createBook(String title, String author, String description, String course) {

        TypedQuery<Book> query = em.createQuery("select b from Book b where b.bookTitle=?1", Book.class);
        query.setParameter(1, title);
        List<Book> books = query.getResultList();

        if(books.size() > 0) {
            return null;
        }

        Book book = new Book();
        book.setBookTitle(title);
        book.setBookAuthor(author);
        book.setBookDescription(description);
        book.setCourseWhereBookIsUsed(course);

        em.persist(book);

        return book.getBookId();
    }

    public Book getBook(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.bookTitle=?1", Book.class);
        query.setParameter(1, title);
        List<Book> books = query.getResultList();

        if(books.size() == 0) {
            throw new IllegalArgumentException("Book " + title + " not exists!");
        }

        return books.get(0);
    }

    public boolean deleteBook(String title) {
        Book book = getBook(title);
        em.remove(book);
        return true;
    }

    public List<Book> getAllBooks() {

        TypedQuery<Book> query = em.createQuery("select c from Book c", Book.class);
        List<Book> books = query.getResultList();

        if(books.size() == 0) {
            return null;
        }

        return books;
    }

    public List<Book> getAllBooksThatAreForSale() {

        TypedQuery<Book> query = em.createQuery("select b from Book b where size(b.users) > 0", Book.class);
        List<Book> books = query.getResultList();

        if(books.size() == 0) {
            return null;
        }

        return books;
    }

    public boolean addOrRemoveUserFromBookList(String username, String bookTitle, boolean add) {

        Book book = getBook(bookTitle);

        if(add) {
            if(checkIfUserExistsInTheBookList(username, bookTitle)) {
                return false;
            }
            book.getUsers().add(username);
        } else {
            if(!checkIfUserExistsInTheBookList(username, bookTitle)) {
                return false;
            }
            book.getUsers().remove(username);
        }

        return true;
    }

    public boolean checkIfUserExistsInTheBookList(String username, String bookTitle) {

        Book book = getBook(bookTitle);

        return book.getUsers().contains(username);

    }
}
