package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Controller implements Initializable{

    @FXML
    private TextField fp_ans;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_procced;

    @FXML
    private TextField fp_username;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changebtn;

    @FXML
    private PasswordField np_confrimPass;

    @FXML
    private AnchorPane np_newPassform;

    @FXML
    private PasswordField np_newpass;

    @FXML
    private AnchorPane question_Form;

    @FXML
    private Hyperlink si_forotpass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginform;

    @FXML
    private PasswordField si_pass;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_alreadybtn;

    @FXML
    private Button side_createbtn1;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_ans;

    @FXML
    private PasswordField su_pass;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;
    
    @FXML
    private ComboBox<String> fp_question;

    @FXML 
    private ComboBox<String> su_questions;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private String[] questionList = {"What is Your favorite color?","what is Your favorite food?","What is Your birthdate?", };
    
    private Alert alert;
    
    public void loginBtn() {
    	if(si_username.getText().isEmpty() || si_pass.getText().isEmpty()) {
    		alert = new Alert (AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Incorrect Username/Password");
    		alert.showAndWait();
    	}
    	else {
    		String selectdata = "SELECT username,password From employee where username =? and password =?";
    		
    		connect = database.connectDB();
    		
    		try {
    			prepare = connect.prepareStatement(selectdata);
    			prepare.setString(1, si_username.getText());
    			prepare.setString(2, si_pass.getText());
    			
    			result = prepare.executeQuery();
    			if(result.next()) {
    				alert = new Alert (AlertType.INFORMATION);
    	    		alert.setTitle("Information Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Successfully Logged in!");
    	    		alert.showAndWait();
    			}
    			else {
    				alert = new Alert (AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Incorrect Username/Password");
    	    		alert.showAndWait();
    			}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    public void regBtn() {
    	if(su_username.getText().isEmpty() || su_pass.getText().isEmpty() 
    			|| su_questions.getSelectionModel().getSelectedItem() ==null
    			|| su_ans.getText().isEmpty()) {
    		
    		alert = new Alert (AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all Blank fields");
    		alert.showAndWait();
    		
    	}
    	else {
    		
    		String regData = "INSERT INTO employee(username,password,question,answer,date)"
    				+ "VALUES(?,?,?,?,?)";
    		connect = database.connectDB();
    		
    		try {
    			
    			String checkUsername = "SELECT username From employee WHERE username ='" + su_username.getText() + "'";
    			
    			prepare = connect.prepareStatement(checkUsername);
    			result = prepare.executeQuery();
    			
    			if(result.next()) {
    				alert = new Alert (AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText(su_username.getText()  + " is already taken");
    	    		alert.showAndWait();
    				
    			}else if(su_pass.getText().length()<6) {
    				alert = new Alert (AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Password Length Must be More than 6");
    	    		alert.showAndWait();
    				
    			}
    			
    			else {
    				prepare = connect.prepareStatement(regData);
        			prepare.setString(1, su_username.getText());
        			prepare.setString(2, su_pass.getText());
        			prepare.setString(3, (String)su_questions.getSelectionModel().getSelectedItem());
        			prepare.setString(4, su_ans.getText());
        			
        			Date date = new Date(0);
        			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        			prepare.setString(5, String.valueOf(sqlDate));
        			
        			prepare.executeUpdate();
        			
        			alert = new Alert (AlertType.INFORMATION);
            		alert.setTitle("Information Message");
            		alert.setHeaderText(null);
            		alert.setContentText("Successfully Registered Account!");
            		alert.showAndWait();
            		
            		su_username.setText("");
            		su_pass.setText("");
            		su_questions.getSelectionModel().clearSelection();
            		su_ans.setText("");
            		
            	 	TranslateTransition slider = new TranslateTransition();
            		
            		slider.setNode(side_form);
            		slider.setToX(0);
            		slider.setDuration(Duration.seconds(.5));
            		System.out.println("Transition is working");
            		
            		slider.setOnFinished((ActionEvent e)-> {
            			side_alreadybtn.setVisible(false);
            			side_createbtn1.setVisible(true);
            		});
            		slider.play();
    				
    			}
    			
    			
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public void regLquestionList() {
    	List<String> listQ = new ArrayList<>();
    	
    	for(String data: questionList) {
    		listQ.add(data);
    	}
    	ObservableList<String> listData = FXCollections.observableArrayList(listQ);
    	
    	su_questions.setItems(listData); 
    }
    
    public void ForgotpassQuestionList() {
    	List<String> listQ = new ArrayList<>();
    	
    	for (String data : questionList) {
    		listQ.add(data);
    	}
    	
    	ObservableList<String> listData = FXCollections.observableArrayList(listQ);
    	fp_question.setItems(listData);
    }

    public void procceedBtn () {
    	if(fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null || fp_ans.getText().isEmpty() ) {
    		
    		alert = new Alert (AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all Blank fields");
    		alert.showAndWait();
    		
    	}
    	else {
    		String selectData  = "SELECT username,question FROM employee WHERE username =? AND question = ? AND answer = ?";
    		connect  = database.connectDB();
    		
    		try {
    				prepare = connect.prepareStatement(selectData);
    				prepare.setString(1, fp_username.getText());
    				prepare.setString(2,(String)fp_question.getSelectionModel().getSelectedItem());
    				prepare.setString(3, fp_ans.getText());
    				
    				result = prepare.executeQuery();
    				
    				if(result.next()) {
    					np_newPassform.setVisible(true);
    					question_Form.setVisible(false);
    				}
    				else {
    					alert = new Alert (AlertType.ERROR);
    		    		alert.setTitle("Error Message");
    		    		alert.setHeaderText(null);
    		    		alert.setContentText("Incorrect Information!");
    		    		alert.showAndWait();
    				}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public void changePass() {
        if (np_newpass.getText().isEmpty() || np_confrimPass.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Fill all blanks!");
            alert.showAndWait();
        }
        else if(!np_newpass.getText().equals(np_confrimPass.getText())) {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Confirm Password Doesnt Match");
            alert.showAndWait();
        	
        }
        else if(np_newpass.getText().length()<6) {
			alert = new Alert (AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Password Length Must be More than 6");
    		alert.showAndWait();
			
		}
        else {
            Connection connect = null;
            PreparedStatement prepare = null;
            
            try {
                connect = database.connectDB();
                
                String username = fp_username.getText(); 
                String updatePass = "UPDATE employee SET password=?, question=?, answer=? WHERE username=?";
                prepare = connect.prepareStatement(updatePass);
                prepare.setString(1, np_newpass.getText());
                prepare.setString(2, fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_ans.getText());
                prepare.setString(4, username); 
                
                int rowsUpdated = prepare.executeUpdate();
                
                prepare.close();
                connect.close();
                
                if (rowsUpdated > 0) {

                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Password updated successfully.");
                    successAlert.showAndWait();
                    
                    
                	si_loginform.setVisible(true);
        			np_newPassform.setVisible(false);
                	
                    np_newpass.setText("");
                    fp_username.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_ans.setText("");
                    np_confrimPass.setText("");
                } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Error Message");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Username not found. Password not updated.");
                    errorAlert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                
                // Handle any exceptions here, e.g., display an error message
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error Message");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while updating the password.");
                errorAlert.showAndWait();
            } finally {
                try {
                    if (prepare != null) prepare.close();
                    if (connect != null) connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    
    
    public void switchForgotPass (ActionEvent event) {
    	question_Form.setVisible(true);
    	si_loginform.setVisible(false);
    	
    	ForgotpassQuestionList();
    	
    }
    public void Backsi(ActionEvent event) { 
    	question_Form.setVisible(false);
    	si_loginform.setVisible(true);
    }
    public void Backfp(ActionEvent event) { 
    	question_Form.setVisible(true);

		np_newPassform.setVisible(false);
    }
    


    public void switchForm(ActionEvent event) {
    	
    	System.out.println("Button clicked");
    	TranslateTransition slider = new TranslateTransition();
    	
    	if(event.getSource() == side_createbtn1) {
    		slider.setNode(side_form);
    		slider.setToX(300);
    		slider.setDuration(Duration.seconds(.5));
    		slider.setOnFinished((ActionEvent e)-> {
    			side_alreadybtn.setVisible(true);
    			side_createbtn1.setVisible(false);
    			
    			np_newPassform.setVisible(false);
    	    	question_Form.setVisible(false);
    	    	si_loginform.setVisible(true);
    			
    			regLquestionList();
    			
    		});
    		slider.play();
    	}
    	else if(event.getSource() == side_alreadybtn) {
    		slider.setNode(side_form);
    		slider.setToX(0);
    		slider.setDuration(Duration.seconds(.5));
    		System.out.println("Transition is working");
    		
    		slider.setOnFinished((ActionEvent e)-> {
    			side_alreadybtn.setVisible(false);
    			side_createbtn1.setVisible(true);
    			
    			np_newPassform.setVisible(false);
    	    	question_Form.setVisible(false);
    	    	si_loginform.setVisible(true);
    			
    		});
    		slider.play();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}