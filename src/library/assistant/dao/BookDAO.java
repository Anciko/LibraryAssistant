/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import library.assistant.database.Database;
import library.assistant.model.Book;

public class BookDAO {

    public void saveBook(Book book) throws SQLException {

        Database db = Database.getInstance();
        Connection conn = db.getConnection();
        db.connect();
        String sql = "insert into lbdb.books (id,title,author,publisher) value (?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, book.getId());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setString(4, book.getPublisher());

        stmt.execute();

    }

    //
    public ObservableList<Book> getBooks() throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.books";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        while (result.next()) {
            String id = result.getString("id");
            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");

            Book book = new Book(id, title, author, publisher);
            bookList.add(book);
        }

        return bookList;
    }

    public Book getBook(String id) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.books where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet result = stmt.executeQuery();
        Book book = null;
        while (result.next()) {

            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");
            boolean available = result.getBoolean("available");
            book = new Book(id, title, author, publisher, available);

        }

        return book;
    }
    public void updateAvailabality(String id,boolean available) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "update lbdb.books set available=? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1, available);
        stmt.setString(2, id);
        
        stmt.execute();
    }

    public void deleteBook(String id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from lbdb.books  where id=?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, id);
        
        stmt.execute();
    }

    public void updateBook(Book book) throws SQLException {
        Database db = Database.getInstance();
        Connection conn = db.getConnection();
        db.connect();
        String sql = "update lbdb.books set title=?, author=?,publisher=? where id=?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublisher());
        stmt.setString(4, book.getId());

        stmt.execute();
    }

}
 