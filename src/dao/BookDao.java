package dao;

import dto.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class BookDao {

    public int insertBook(Book book){
        int i=0;
        try {
            //1.编写SQL
            String sql = "insert into books(book_id,book_name,book_author,book_price,book_stock) values(?,?,?,?,?)";
            //2.准备参数
            Object[] params = {book.getBookId(),book.getBookName(),book.getBookAuthor(),book.getBookPrice(),book.getBookStock()};

            //3.调用commons-dbutils中的QueryRunner执行SQL
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            i = queryRunner.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int deleteBook(int bookId){
        int i=0;
        try {
            String sql = "delete from books where book_id=?";
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            i = queryRunner.update(sql, bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int updataBook(Book book){
        int i =0;
        try {
            String sql = "update books set book_name=?,book_author=?,book_price=?,book_stock=? where book_id=?";
            Object[] params = {book.getBookName(),book.getBookAuthor(),book.getBookPrice(),book.getBookStock(),book.getBookId()};
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            i = queryRunner.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public Book queryBook(int bookId){
        Book book = null;
        try {
            String sql ="select book_id bookId,book_name bookName,book_author bookAuthor,book_price bookPrice,book_stock bookStock from books where book_id=?";
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            //对于查询操作，我们需要通过QueryRunner对象调用query方法执行
            //所有的query方法都需要一个ResultSetHandler的参数，通过此参数指定query的返回类型
            //如果SQL指令执行之后返回的是一条记录，我们通过BeanHandler指定查询结果封装的实体类类型
            //要求：查询结果集的字段名必须与指定 的实体类中的属性名一致
            //方案一：创建实体类时，实体类中的属性名跟数据表中的列名一致
            //方案二：查询语句取别名，让字段名与实体类中属性名一致
            BeanHandler<Book> bookBeanHandler = new BeanHandler<>(Book.class);
            book = queryRunner.query(sql, bookBeanHandler, bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> listBooks(){
        List<Book> bookList = null;
        try {
            String sql ="select book_id bookId,book_name bookName,book_author bookAuthor,book_price bookPrice,book_stock bookStock from books";
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            //如果SQL指令返回的是多条记录，我们通过BeanListHandler指定查询结果封装的实体类的集合类型
            BeanListHandler<Book> bookBeanListHandler = new BeanListHandler<>(Book.class);
            bookList = queryRunner.query(sql, bookBeanListHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public long getCount(){
        long l = 0;
        try {
            String sql = "select count(1) from books";
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            //如果SQL指令返回的是一个值时，我们通过ScalarHandler指定返回类型
            //QueryRunner在处理统计操作时，是以long类型进行操作的
            //如果我们确定这个值在int范围内，我们可以得到long类型后进行强转，但建议使用long类型
            ScalarHandler<Long> integerScalarHandler = new ScalarHandler<>();
            l=queryRunner.query(sql,integerScalarHandler);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }
}
