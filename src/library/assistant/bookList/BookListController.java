
package library.assistant.bookList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.assistant.dao.BookDAO;
import library.assistant.editBook.EditBookController;
import library.assistant.model.Book;
import library.assistant.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class BookListController implements Initializable {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    
    private BookDAO bookDAO;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private MenuItem editItem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       bookDAO = new BookDAO();
        initColumn();
        
        loadTableData();
       
    }    

    private void initColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    private void loadTableData()  {
       
       // List<Book> bookList = new ArrayList<>();
        
        try {
            ObservableList<Book> bookList = bookDAO.getBooks();
            bookTable.getItems().setAll(bookList);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
        
        
    }

    @FXML
    private void deleteAction(ActionEvent event)  {
        
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
//            Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setHeaderText(null);
//            alert.setContentText("are you sure you want to delete this book");
            Optional<ButtonType> selectedOption = MessageBox.showConfirmMessage("Are you sure you want to delete this book");
            
            if(selectedOption.get() == ButtonType.OK){
                try {
                bookDAO.deleteBook(selectedBook.getId());
                bookTable.getItems().remove(selectedBook);
               // loadTableData();
            } catch (SQLException ex) {
                Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            
            
           
        }
    }

    @FXML
    private void editAction(ActionEvent event) throws IOException {
        
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        
        if(selectedBook != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/editBook/editBook.fxml"));
            Parent root = loader.load();
            EditBookController controller = (EditBookController)loader.getController();
            controller.setBookInfo(selectedBook);
            Stage stage = new Stage();
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.initOwner(bookTable.getScene().getWindow());
         stage.initModality(Modality.WINDOW_MODAL);
         stage.setTitle("Edit Book");
         stage.showAndWait();
         System.out.println("Edit Book...");
         loadTableData();
        
        }
        
        
        
         
         
         
        
    }
    
}
