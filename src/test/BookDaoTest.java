package test;

import dao.BookDao;
import dto.Book;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookDaoTest {
    BookDao bookDao = new BookDao();

    @Test
    public void testInsertBook(){
        Book book = new Book(6, "java", "蒋子旭", 50, 20);
        int i = bookDao.insertBook(book);
        assertEquals(1,i);
    }

    @Test
    public void testDeleteBook(){
        int i = bookDao.deleteBook(1);
        assertEquals(1,i);
    }

    @Test
    public void testUpdataBook(){
        Book book = new Book(5, "c语言", "蒋子旭", 5, 100);
        int i = bookDao.updataBook(book);
        assertEquals(1,i);
    }

    @Test
    public void testQueryBook(){
        Book book = bookDao.queryBook(2);
        System.out.println(book);
    }

    @Test
    public void testListBook(){
        List<Book> bookList = bookDao.listBooks();
        for (Book book:bookList){
            System.out.println(book);
        }
    }

    @Test
    public void testGetCount(){
        long count = bookDao.getCount();
        System.out.println(count);
    }
}
