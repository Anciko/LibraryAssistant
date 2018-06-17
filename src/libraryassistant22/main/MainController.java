/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant22.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.assistant.dao.BookDAO;
import library.assistant.dao.IssueInfoDAO;
import library.assistant.dao.MemberDAO;
import library.assistant.model.Book;
import library.assistant.model.IssueInfo;
import library.assistant.model.Member;
import library.assistant.util.MessageBox;

/**
 *
 * @author DELL
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton addBooksBtn;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homePane;
    @FXML
    private JFXButton addMemberID;
    @FXML
    private JFXButton bookListbtn;
    @FXML
    private JFXButton memberListID;
    @FXML
    private JFXTextField bookIDField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;

    //DAOs   
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueInfoDAO issueInfoDAO;
    @FXML
    private JFXTextField memberIDField;
    @FXML
    private Text nameText;
    @FXML
    private Text emailText;
    @FXML
    private Text moblieText;
    @FXML
    private Text addressText;
    @FXML
    private Text availableText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField issueBookIDField;
    @FXML
    private Text mIDText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mEmailText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text pPublisherText;
    @FXML
    private Text issuedDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private MenuItem configitem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueInfoDAO = new IssueInfoDAO();
    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        // Stage stage = new Stage();
        centerPane.getChildren().clear();

        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/addBook/addBook.fxml"));
        centerPane.getChildren().add(root);
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//       
//        stage.show();
    }

    @FXML
    private void homeAction(ActionEvent event) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homePane);
    }

    @FXML
    private void loadAddMember(ActionEvent event) throws IOException {
        centerPane.getChildren().clear();

        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/addMember/addmember.fxml"));
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        centerPane.getChildren().clear();

        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/bookList/bookList.fxml"));
        centerPane.getChildren().add(root);
    }

    @FXML
    private void memberListAction(ActionEvent event) throws IOException {
        centerPane.getChildren().clear();

        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/memberList/memberList.fxml"));
        centerPane.getChildren().add(root);
    }

    @FXML
    private void searchBookInfo(ActionEvent event) {
        clearBookCache();
        
        
        String id = bookIDField.getText();

        if (id.isEmpty()) {
            System.out.println("Please enter book id first");
            return;
        }

        try {
            Book book = bookDAO.getBook(id);
            if (book != null) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                publisherText.setText(book.getPublisher());
                boolean available = book.isAvailable();
                if(available){
                    availableText.setText("Available");
                }else{
                    availableText.setText("Not available");
                }
            } else {
                MessageBox.showAndWaitErrorMessage("Cannot find any Book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {
         clearMemberCache();
         String id = memberIDField.getText();

        if (id.isEmpty()) {
            System.out.println("Please enter member id first");
            return;
        }

        try {
            Member member = memberDAO.getMember(id);
            if (member != null) {
                nameText.setText(member.getName());
                emailText.setText(member.getEmail());
                moblieText.setText(member.getMobile());
                addressText.setText(member.getAddress());
                
            } else {
                MessageBox.showAndWaitErrorMessage("Cannot find any member");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
    }

    private void clearBookCache() {
        titleText.setText("_");
        authorText.setText("_");
        publisherText.setText("_");
        availableText.setText("_");
    }

    private void clearMemberCache() {
        nameText.setText("_");
        moblieText.setText("_");
        emailText.setText("_");
        addressText.setText("_");
        
    }

    @FXML
    private void issueBook(ActionEvent event) {
        String bookId = bookIDField.getText();
        String memberId = memberIDField.getText();
        
        if(bookId.isEmpty() || memberId.isEmpty()){
            System.out.println("Enter Book Id and Member Id first.");
            return;
        }
        try {
            Book book = bookDAO.getBook(bookId);
            if(book.isAvailable()){
                issueInfoDAO.saveIssueInfo(new IssueInfo(memberId, bookId));
                bookDAO.updateAvailabality(bookId, false);
                System.out.println("Book issue success");
            }else{
                System.out.println("This book is already issued.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchIssueedBookInfo(ActionEvent event) {
        clearIssueInfo();
        String bookId = issueBookIDField.getText();
        
        //issue Table
        //book id, member id, date, renew count
        //book table
        //member table
        
        if(bookId.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter book id first...");
            alert.show();
            
            return;
        }
        
        try {
            IssueInfo issueInfo = issueInfoDAO.getIssueInfo(bookId);
            if(issueInfo != null){
                Book book = bookDAO.getBook(bookId);
                
                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                pPublisherText.setText(book.getPublisher());
                
                Member member = memberDAO.getMember(issueInfo.getMemberId());
                mNameText.setText(member.getName());
                mEmailText.setText(member.getEmail());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());
                
                mIDText.setText(issueInfo.getMemberId());
                issuedDateText.setText("Issued Date: "+issueInfo.getIssueDate().toString());
                renewCountText.setText("Renew Count: "+issueInfo.getRenewCount());
            }else{
                System.out.println("Cannot find any issue info for this book id.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void renewBook(ActionEvent event) {
        String bookId = issueBookIDField.getText();
        if(bookId.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter book id first");
            alert.show();
            return;
        }
        
        try {
            IssueInfo issueInfo = issueInfoDAO.getIssueInfo(bookId);
            
            if(issueInfo != null){
                issueInfoDAO.updateRenewCount(bookId);
                
            }else{
                System.out.println("Cannot find any book.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void submitBook(ActionEvent event) {
        String bookId = issueBookIDField.getText();
        if(bookId.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter book id first");
            alert.show();
            return;
        }
        
        try {
            IssueInfo issueInfo = issueInfoDAO.getIssueInfo(bookId);
            
            if(issueInfo != null){
                issueInfoDAO.deleteIssueInfo(bookId);
                bookDAO.updateAvailabality(bookId, true);
            }else{
                System.out.println("Cannot find any book.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearIssueInfo(){
        mIDText.setText("_");
        mNameText.setText("_");
        mEmailText.setText("_");
        mMobileText.setText("_");
        mAddressText.setText("_");
        
        bTitleText.setText("_");
        bAuthorText.setText("_");
        pPublisherText.setText("_");
        
        issuedDateText.setText("_");
        renewCountText.setText("_");
    }

    @FXML
    private void loadDatabaseConfigView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/dbConfig/databaseConfig.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Database Configuration");
        
        stage.initOwner(centerPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        
        MessageBox.showAndWaitErrorMessage("Please restart your app.");
        
        Stage currentStage = (Stage)centerPane.getScene().getWindow();
        currentStage.close();
    }

}
