/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.addBook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.assistant.dao.BookDAO;
import library.assistant.model.Book;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddBookController implements Initializable {

    @FXML
    private JFXButton savebtn;
    @FXML
    private JFXTextField bookField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    
    private BookDAO bookDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
    }    

    @FXML
    private void saveAction(ActionEvent event) {
        String id = bookField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        
        System.out.println("ID:" + id + "Title" + title + "Author:"+author + "Publisher" + publisher);
        
        Book book = new Book(id, title, author, publisher);
        
        try {
            bookDAO.saveBook(book);
            System.out.println("Book Saving success");
        } catch (SQLException ex) {
            System.out.println("Failed");
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
