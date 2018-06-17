/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.addMember;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import library.assistant.dao.MemberDAO;
import library.assistant.model.Member;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AddmemberController implements Initializable {

    @FXML
    private JFXTextField memberIDField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton savebtn;
    
    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
    }    

    @FXML
    private void memberAction(ActionEvent event) {
        String id = memberIDField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        System.out.println(id + ":" + name + ":" + email + ":" + mobile + ":" + address);
        
        Member member = new Member(id, name, email,mobile, address);
        try {
            memberDAO.saveMember(member);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Member adding success");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Member adding failed");
            alert.show();
            Logger.getLogger(AddmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
