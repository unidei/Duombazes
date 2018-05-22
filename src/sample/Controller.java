package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller  implements Initializable {
    @FXML
    private TableView<student> studentTableView;
    @FXML
    private TableColumn<student, Integer> idTableColumn;
    @FXML
    private TableColumn<student, String> nameTableColumn;
    @FXML
    private TableColumn<student, String> surnameTableColumn;
    @FXML
    private TableColumn<student, String> phoneTableColumn;
    @FXML
    private TableColumn<student, String> emailTableColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = MyDbUtils.createConnection();
        if(connection != null) {
            List<student> students = getStudents(connection);
            idTableColumn.setCellValueFactory(new PropertyValueFactory<student, Integer>("id"));
            nameTableColumn.setCellValueFactory(new PropertyValueFactory<student, String>("name"));
            surnameTableColumn.setCellValueFactory(new PropertyValueFactory<student, String>("surname"));
            phoneTableColumn.setCellValueFactory(new PropertyValueFactory<student, String>("phone"));
            emailTableColumn.setCellValueFactory(new PropertyValueFactory<student, String>("email"));

            studentTableView.setItems(FXCollections.observableList(students));

            /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Prisijungeme" + students.size());
            alert.show(); */

        }
    }

    private List<student> getStudents(Connection connection) {
        List<student> students = new ArrayList<>();


        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()){
                student student = new student(resultSet.getInt("ID"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("phone"), resultSet.getString("email"));
                students.add(student);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return students;

    }
}
