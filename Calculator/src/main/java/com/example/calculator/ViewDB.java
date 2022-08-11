package com.example.calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewDB implements Initializable {
    @FXML
    private TableView<user> table;

    @FXML
    private TableColumn<user, Integer> col_id;

    @FXML
    private TableColumn<user, String> col_exp;

    @FXML
    private TableColumn<user, Integer> col_res;

    @FXML
    private TextField txt_search;

    public void loadDatatoTableAndSearch(){
        ObservableList<user> oblist = FXCollections.observableArrayList();
        try {
            Connection con = ConnectionDB.conDB();
            ResultSet rs = con.createStatement().executeQuery("select * from expressions;");
            while(rs.next()){
                oblist.add(new user(Integer.parseInt(rs.getString("id")), rs.getString("exp"),
                        Integer.parseInt(rs.getString("result"))));
            }
            System.out.println("Success");
        } catch (SQLException e) {
            System.out.println("No Success");
            e.getMessage();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<user, Integer>("id"));
        col_exp.setCellValueFactory(new PropertyValueFactory<user, String>("exp"));
        col_res.setCellValueFactory(new PropertyValueFactory<user, Integer>("result"));
        table.setItems(oblist);

        //search expressions in data
        FilteredList<user> filteredList = new FilteredList<>(oblist, b -> true);
        txt_search.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(search -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(search.getExp().toLowerCase().indexOf(lowerCaseFilter) !=-1){
                    return true;
                } else if (String.valueOf(search.getResult()).toLowerCase().indexOf(lowerCaseFilter) !=-1) {
                    return true;
                } else return false; //don't find
            });
        });
        SortedList<user> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatatoTableAndSearch();
    }
}
