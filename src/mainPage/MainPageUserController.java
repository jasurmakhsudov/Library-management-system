package mainPage;

import windowLoader.WindowLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import loginPage.UserSession;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainPageUserController {
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();
    private MainController mainController = new MainController().getMainController();
    @FXML
    private Label topLabel;
    @FXML
    private Button homeMenuItem;
    @FXML
    private Button librariansMenuItem;
    @FXML
    private Button studentsMenuItem;
    @FXML
    private Button newBookBtn;
    @FXML
    private Button deleteBookBtn;
    @FXML
    private Button issueBookBtn;
    @FXML
    private Button booksMenuItem;
    @FXML
    private Button statisticsMenuItem;
    private ArrayList<Button> buttons = new ArrayList<>();
    @FXML
    private GridPane homePane;
    @FXML
    private GridPane librariansPane;
    @FXML
    private GridPane studentsPane;
    @FXML
    private GridPane booksPane;
    @FXML
    private GridPane statisticsPane;
    @FXML
    private BorderPane booksBorderPane;
    @FXML
    private BorderPane studentsBorderPane;
    @FXML
    private BorderPane homeBorderPane;
    @FXML
    private Circle avatar;
    @FXML
    private Label labelUserName;

    UserSession userSession=UserSession.getInstance();

    public void initialize() throws MalformedURLException {

        mainController.loadUserDetails(labelUserName, avatar);
        mainController.initializeStudentBookTable(booksBorderPane);
        mainController.initializeStudentHome(homeBorderPane);
        windowLoader.loadLineChart(statisticsPane);
        buttons.add(homeMenuItem);
        buttons.add(librariansMenuItem);
        buttons.add(studentsMenuItem);
        buttons.add(booksMenuItem);
        buttons.add(statisticsMenuItem);

        //Set Home Page as default
        homePane.toFront();
        topLabel.setText(mainController.letterSpace(homeMenuItem.getText().toUpperCase(), 4));

        //Setting letter spaced menu item names
        for(Button button:buttons){
            button.setText(mainController.letterSpace(button.getText(), 1));
        }
        librariansMenuItem.setManaged(false);
        librariansMenuItem.setVisible(false);
        librariansPane.setVisible(false);
        studentsMenuItem.setManaged(false);
        studentsMenuItem.setVisible(false);
        studentsPane.setVisible(false);
    }
    @FXML
    public void logOut(){
        ((Stage)homeMenuItem.getScene().getWindow()).close();
        mainController.logOut();
    }
    @FXML
    public void editProfile(){
        mainController.loadEditProfileWindow();
    }
    @FXML
    public void setHomePane(MouseEvent event){
        mainController.setPane(event, homePane, buttons, topLabel);
    }
    @FXML
    public void setBooksPane(MouseEvent event){
        mainController.setPane(event, booksPane, buttons, topLabel);
    }
    @FXML
    public void setStatisticsPane(MouseEvent event){
        mainController.setPane(event, statisticsPane, buttons, topLabel);
    }
    @FXML
    public void setLibrariansPane(MouseEvent event) {
        mainController.setPane(event, librariansPane, buttons, topLabel);
    }

    @FXML
    public void setStudentsPane(MouseEvent event) {
        mainController.setPane(event, studentsPane, buttons, topLabel);
    }
    @FXML
    public void initStudentsTable(){
        mainController.initializeTotalStudentsTable(studentsBorderPane);
    }
    @FXML
    public void initFinedStudentsTable(){
        mainController.initializeFinedStudentsTable(studentsBorderPane);
    }
    @FXML
    public void initBlockedStudentsTable(){
        mainController.initializeBlockedStudentsTable(studentsBorderPane);
    }
    @FXML
    public void initReservedStudentsTable(){
        mainController.initializeReservedStudentsTable(studentsBorderPane);
    }
    @FXML
    public void initBookTable(){
        mainController.initializeBookTable(booksBorderPane);
    }
    @FXML
    public void initIssuedBookTable(){
        mainController.initializeIssuedBookTable(booksBorderPane);
    }
    @FXML
    public void initLostBookTable(){
        mainController.initializeLostBookTable(booksBorderPane);
    }
    @FXML
    public void setAvatar(){
        try{
            mainController.setAvatar(avatar);
        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
    }
}
