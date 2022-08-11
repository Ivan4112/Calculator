package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller{
    @FXML
    private TextField tf2, tf;
    @FXML
    private Button bt_equal;
    private String gobject = "";
    double FirstDouble,SecondDouble,result ;

    String prevStr="";

    @FXML
    void handletDigitAction(ActionEvent event) {
        String str = ((Button)event.getSource()).getText();
        tf.setText(tf.getText()+str);
        tf2.setText(prevStr+str);
        prevStr = tf2.getText();
    }

    @FXML
    void handlerGenrealAction(ActionEvent event) {
        gobject = ((Button) event.getSource()).getText();
        switch (gobject) {
            case "Cl":
                tf.setText("");
                tf2.setText("");
                prevStr = "";
                break;
            case "+/-":
                double plusminus = Double.parseDouble(String.valueOf(tf.getText()));
                plusminus *= -1;
                tf.setText(String.valueOf(plusminus));
                tf2.setText(String.valueOf(plusminus));
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case ")":
            case "(":
                FirstDouble = Double.parseDouble(tf.getText());
                tf2.setText(prevStr + gobject);
                prevStr = tf2.getText();
                tf.setText("");
                break;

            default:
        }
    }

    @FXML
    void handlerEqualAction(ActionEvent event) {

        SecondDouble = Double.parseDouble(tf.getText());
        switch(gobject){

            case "+": result = FirstDouble+SecondDouble; tf.setText("" + result);
                break;
            case "*": result = FirstDouble*SecondDouble; tf.setText("" + result);
                break;
            case "-": result = FirstDouble-SecondDouble; tf.setText("" + result);
                break;
            case "/":
                try {
                    result = FirstDouble / SecondDouble;
                    tf.setText("" + result);
                }catch (Exception ex){
                    tf.setText("Infinity");
                }
                break;
            default:
        }
    }
    @FXML
    void openDbAction(MouseEvent event) throws IOException {
        Parent dataDb = FXMLLoader.load(getClass().getResource("showDB.fxml"));
        Stage secondStage = new Stage();
        secondStage.setTitle("Data");
        secondStage.setScene(new Scene(dataDb));
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.setResizable(false);
        secondStage.show();
    }

    ResultSet rs = null;
    PreparedStatement ps = null;
    String SQL = "insert into expressions (exp, result) values (?,?);";

    @FXML
    void SaveDataTable(ActionEvent event) {
        try {
            Connection con = ConnectionDB.conDB();
            ps = con.prepareStatement(SQL);
            ps.setString(1,tf2.getText());
            ps.setString(2,tf.getText());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("No Success");
            e.getErrorCode();
        }
    }
}