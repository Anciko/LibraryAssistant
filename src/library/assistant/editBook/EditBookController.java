
package library.assistant.editBook;

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
import javafx.stage.Stage;
import library.assistant.dao.BookDAO;
import library.assistant.model.Book;



public class EditBookController implements Initializable {

    @FXML
    private JFXTextField bookField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton updatebtn;
    @FXML
    private JFXButton cancelbtn;
    
    BookDAO bookDAO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookField.setDisable(true);
        bookDAO = new BookDAO();
    }    

    @FXML
    private void updateAction(ActionEvent event) {
        String id = bookField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        
        if(id.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()){
            System.out.println("Please fill in all fields..");
            return;
        }
        Book book = new Book(id, title, author, publisher);
        try {
            bookDAO.updateBook(book);
        } catch (SQLException ex) {
            Logger.getLogger(EditBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage oldStage = (Stage)updatebtn.getScene().getWindow();
        oldStage.close();
    }                                                                                          
    public void setBookInfo(Book selectedBook){
        bookField.setText(selectedBook.getId());
        titleField.setText(selectedBook.getTitle());
        authorField.setText(selectedBook.getAuthor());
        publisherField.setText(selectedBook.getPublisher());
        
    }
}
