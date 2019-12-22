package form.addBooksForm;

import dataBase.DBConnector;
import getJSON.Send_HTTP_Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BooksFormController implements Initializable {

    @FXML
    private TextField isbn;
    @FXML
    private Button getData;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private ImageView photo;
    @FXML
    private Button cancel;
    @FXML
    private Spinner<Integer> spinner = new Spinner<Integer>();
    @FXML
    private DatePicker publishedYear;
    @FXML
    private TextField subject;
    @FXML
    private TextField authorName;

    private Send_HTTP_Request sendHttpRequest = new Send_HTTP_Request();
    private DBConnector connector = new DBConnector().getConnector();

    PreparedStatement preparedStatement;
    Connection connection;


    public BooksFormController() throws SQLException {
        connection = connector.getConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Value factory.
        initSpinner();
    }
    @FXML
    private void initSpinner() {
       spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,1));
        }

    private void saveDateOnce() throws SQLException {
        for (int i=0;i<spinner.getValue();i++){
            saveData();
        }
    }

    @FXML
    private void setAttach(){

        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jpeg files","*.jpg") );
        File selectedFile= fileChooser.showOpenDialog(null);

        //create and set image
        Image image= new Image(selectedFile.toURI().toString());
        photo.setImage(image);

        try {
            ImageIO.write(ImageIO.read(
                    new File(selectedFile.toString())),
                    "jpg",
                    new File("./src/images/bookPhoto/"+isbn.getText()+".jpg"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    @FXML
    private void saveData() throws SQLException {
        String insertBook_group ="INSERT INTO book_group (isbn, category, descrip, title, published_date) VALUES (?,?,?,?,?)";
        String insertAuthors ="INSERT INTO authors (author_name,isbn) VALUES (?,?)";
        String insertBook ="INSERT INTO book (isbn) VALUES (?)";
        preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthors);

        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook_group);
            preparedStatement.setString(1, isbn.getText());
            preparedStatement.setString(2, subject.getText());
            preparedStatement.setString(3, description.getText());
            preparedStatement.setString(4, title.getText());
            preparedStatement.setString(5, String.valueOf(publishedYear.getValue()));
            preparedStatement.executeUpdate();

            preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthors);
            preparedStatement.setString(1, authorName.getText());
            preparedStatement.setString(2, isbn.getText());
            preparedStatement.executeUpdate();

            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook);
            preparedStatement.setString(1, isbn.getText());
            preparedStatement.executeUpdate();


        }catch (Exception e) {
            System.out.println(e);
        }

        clearFields();
    }

    @FXML
    private void handleEventSave() throws SQLException {
        if (isbn.getText().isEmpty()||title.getText().isEmpty()||
                authorName.getText().isEmpty()||
                description.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Please, fill all necessary fields!");
            alert.showAndWait();
        }
        else{
            saveDateOnce();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information!");
            alert.setHeaderText("Successfully inserted");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        isbn.clear();
        title.clear();
        description.clear();
        authorName.clear();
        subject.clear();
        photo.setImage(new Image("images/demoBook.png"));
    }

    public void cancelButtonHandler()
    {
        ((Stage)cancel.getScene().getWindow()).close();
    }


    public void getData() throws Exception {
        sendHttpRequest.call_me(isbn.getText(), this);
    }
    public void setPhoto(String url) {
        Image image= new Image(url);
        photo.setImage(image);
    }
    public void setTitle(String txt) {
        title.setText(txt);
    }
    public void setPublishedYear(String txt) {
        publishedYear.setValue(LocalDate.parse(txt));
    }
    public void setDescription(String txt) {
        description.setText(txt);
    }
    public void setIsbn(String txt) {
        isbn.setText(txt);
    }
    public void setSubject(String txt) {
        subject.setText(txt);
    }
    public void addAuthor(String txt) {
        authorName.setText(txt);
    }
}
