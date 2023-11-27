/**
 * 
 */


import java.awt.Color;
import java.beans.EventHandler;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Scott
 *
 */
public class EmpDataGUI extends Application {
    private BorderPane realMain;
	private GridPane main = new GridPane();	
    private ListController controller = new ListController();
    private BorderPane view;
    private ScrollPane scroll;
    GridPane buttonGrid = new GridPane();
    // TODO #1:
    // create private TextField variables for Name, SSN, Salary and Years
    // so that they can be accessed directly by methods inside this class.
	private TextField LastName;
	private TextField FirstName;
	private TextField SSN;
	private TextField Age;
	private TextField Pronouns;
	private TextField Salary;
	private TextField Years;
	private ToggleGroup tg;
	private RadioButton eng;
    @Override
    public void start(Stage primaryStage) {
    	realMain  = new BorderPane();
    	Scene scene = new Scene(realMain,400,500);
    	realMain.setCenter(main);
    	view = new BorderPane();
    	scroll = new ScrollPane();
    	Scene scene2 = new Scene(view, 400, 500);
    	
    	
    	Label nameOfRealMain = new Label("EMPLOYEE DATA ENTRY:");
    	realMain.setTop(nameOfRealMain);
	// TODO #2:
    	// create Labels for Name, SSN, Salary and Years
    	Label lNameLabel = new Label("Last Name: ");
    	Label fNameLabel = new Label("First Name: ");
    	Label ssnLabel = new Label("SSN: ");
    	Label ageLabel = new Label("Age: ");
    	Label pronLabel = new Label("Pronouns: ");
    	Label salaryLabel = new Label("Salary: ");
    	Label yearsLabel = new Label("Years: ");
    	Label radioLabel = new Label("Dept: ");
    	
    	eng = new RadioButton("Engineering");
		RadioButton markSales = new RadioButton("Marketing/Sales");
		RadioButton manuf = new RadioButton("Manufacturing");
		RadioButton fin = new RadioButton("Finance");
		RadioButton hR = new RadioButton("Human Resources");
		RadioButton custS = new RadioButton("Customer Suppoort");
		RadioButton manag = new RadioButton("Management");
		tg = new ToggleGroup();
		eng.setToggleGroup(tg);
		markSales.setToggleGroup(tg);
		manuf.setToggleGroup(tg);
		fin.setToggleGroup(tg);
		hR.setToggleGroup(tg);
		custS.setToggleGroup(tg);
		manag.setToggleGroup(tg);
	
		
		
		VBox vBox = new VBox();
		
		eng.setSelected(true);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(eng, markSales, manuf, fin, hR, custS, manag);
		HBox hBox = new HBox();
		hBox.getChildren().addAll(radioLabel);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setSpacing(50);
		main.add(hBox, 0, 9);
		main.add(vBox, 1, 9);
		
    	Button viewEmplo = new Button("View Employees");
    	main.add(viewEmplo, 0, 5);
    	viewEmplo.setOnAction(e -> {viewEmployeeDB(); primaryStage.setScene(scene2);});
    	//scene 2:
    	Label employeeDataViewLabel = new Label("Employee Data View");
    	Button back = new Button("Back");
    	Button sortByName = new Button("Sort By Name");
    	Button sortByID = new Button("Sort By ID");
    	Button sortBySalary = new Button("Sort By Salary");
    	HBox viewHBox = new HBox();
    	viewHBox.getChildren().addAll(back, sortByName, sortByID, sortBySalary);
    	viewHBox.setSpacing(10);
    	view.setTop(employeeDataViewLabel);
    	view.setBottom(viewHBox);
    	
    	view.setCenter(scroll);
    	back.setOnAction(e -> primaryStage.setScene(scene));
    	sortByName.setOnAction(e -> {controller.sortByName(); viewEmployeeDB();});
    	sortByID.setOnAction(e -> {controller.sortByID(); viewEmployeeDB();});
    	sortBySalary.setOnAction(e -> {controller.sortBySalary(); viewEmployeeDB();});
    	
	// TODO #4
    	// instantiate (new) TextFields (already declared above) for Name, SSN, Salary and Years
    	LastName = new TextField();
    	FirstName = new TextField();
    	SSN = new TextField();
    	Age = new TextField();
    	Pronouns = new TextField();
    	Salary = new TextField();
    	Years = new TextField();
	// TODO #5
        // Create Add Employee Button, and write the setOnAction handler to call the controller
    	// to add the new Employee data
    	Button addEmplo = new Button("Add Employee");
    	
    	addEmplo.setOnAction(e -> giveAlert());
    	Button saveDB = new Button("Save DB");
    	saveDB.setOnAction(e -> controller.saveEmployeeData());
	// TODO #6
    	// add all the labels, textfields and button to gridpane main. refer to the slide
    	// for ordering...
    	main.add(lNameLabel, 0, 0);
    	main.add(fNameLabel, 0, 1);
    	main.add(ssnLabel, 0, 2);
    	main.add(ageLabel, 0, 3);
    	main.add(pronLabel, 0, 4);
    	main.add(salaryLabel, 0, 5);
    	main.add(yearsLabel, 0, 6);
    	
    	main.add(LastName, 1, 0);
    	main.add(FirstName, 1, 1);
    	main.add(SSN, 1, 2);
    	main.add(Age, 1, 3);
    	main.add(Pronouns, 1, 4);
    	main.add(Salary, 1, 5);
    	main.add(Years, 1, 6);
    	realMain.setBottom(buttonGrid);
    	buttonGrid.add(addEmplo,0,0);
    	buttonGrid.add(viewEmplo,1,0);
    	buttonGrid.add(saveDB,2,0);
    	buttonGrid.setHgap(20);
    	primaryStage.setTitle("Employees");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // don't worry about this yet - part of part2
    private void viewEmployeeDB() {
    	String[] empDataStr = controller.getEmployeeDataStr();
    	ListView<String> lv = new ListView<>(FXCollections.observableArrayList(empDataStr));
    	lv.setPrefWidth(400);
    	scroll.setContent(lv);
    }
    
  /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
	
	public void giveAlert() {
		String alert = controller.addEmployee(FirstName.getText(), LastName.getText(), SSN.getText(), 
    			Age.getText(), Pronouns.getText(), Salary.getText(), Years.getText(), (RadioButton)tg.getSelectedToggle());
		if(!(alert.equals(""))) {
			Alert alertToUser = new Alert(AlertType.WARNING);
			alertToUser.setHeaderText("Add Employee Failed");
			alertToUser.setContentText(alert);
			alertToUser.showAndWait();
		}
		else {
			LastName.clear();
			FirstName.clear();
			SSN.clear();
			Age.clear();
			Pronouns.clear();
			Salary.clear();
			Years.clear();
			eng.setSelected(true);
		}
	}

} ;
