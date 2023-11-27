import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import myfileio.MyFileIO;

public class ListController {
	private ArrayList<Employee> employees;
	private static final boolean DEBUG = true;
	private static final String FILENAME = "empDB.dat";
	private static final int MINAGE = 16;
	private static final int MAXAGE = 90;
	private static final int MINSALARY = 30000;
	private static final int MAXSALARY = 300000000;
	public ListController () {
		Employee.resetStaticID();
		employees = new ArrayList<Employee>();
		
		restore();
		
	}
	

	// adds a new employee
	String addEmployee(String fName, String lName, String SSN, String age, String pronouns, String salary, String years, RadioButton dept) {
		// TODO #7
		// controller needs to convert the numeric string data -
		// use Integer.parseInt() or Double.parseDouble() for ints and doubles
		// years should be int, salary should be a double....
		// Then, add the new employee to the employees list!
		// for initial demo and debugging, set DEBUG to true;
		
		String deptStr = dept.getText();
		
		
		return addEmployee(fName, lName, SSN, age, pronouns, salary, years, deptStr);
		
		
	}
	
	String addEmployee(String fName, String lName, String SSN, String age, String pronouns, String salary, String years, String dept) {
		
		int yearsInt;
		double salaryDouble;
		int ageInt;
		try {
			yearsInt = Integer.parseInt(years);
			salaryDouble = Double.parseDouble(salary);
			ageInt = Integer.parseInt(age);
		} catch(Exception e) {
			System.out.println("invalid year, salary, or age input");
			return "invalid year, salaary, or age input";
		}
		
		//error checking
		if(!(SSN.matches("^[0-9]{3}-[0-9]{2}-[0-9]{4}$"))) {
			System.out.println("Invalid SSN format");
			return "Invalid SSN format";
		}
		for(int i = 0; i < employees.size(); i++) {
			System.out.println("Duplicate SSN");
			if(SSN.matches(employees.get(i).getSSN())) {
				return "Duplicate SSN";
			}
		}
		if(fName.equals("") || lName.equals("") || SSN.equals("") 
				|| age.equals("") || salary.equals("") || years.equals("")) {
			System.out.println("One or more required fields are empty");
			return "One or more required fields are empty";
		}
		if(ageInt < MINAGE) {
			System.out.println("Age is too young to be hired");
			return("Age is too young to be hired");
		}
		
		if((ageInt - MINAGE) < yearsInt) {
			System.out.println("age and years experience don't make sense");
			return("age and years experience don't make sense");
		}
		if(!(salaryDouble >= MINSALARY && salaryDouble < MAXSALARY)) {
			System.out.println("salary is not within range");
			return("salary is not within range");
		}
		if(ageInt > MAXAGE) {
			System.out.println("Wow, that person is very old");
		}
		
		employees.add(new Employee(fName, lName, SSN, ageInt, pronouns, salaryDouble, yearsInt, dept));
		if (DEBUG) System.out.println(employees);
		
		return "";
		
		
		
	}
	
	// returns a string array of the employee information to be viewed
	public String[] getEmployeeDataStr() {
		// temporary placeholder for compilation reasons - will remove later...
		String[] toStringArray = new String[employees.size()];
		for(int i = 0; i < employees.size(); i++) {
			toStringArray[i] = "" + employees.get(i);
		}
		
		return(toStringArray);
		
	}
	
	//saves database
	public void saveEmployeeData() {
		MyFileIO fileIO = new MyFileIO();
		FileWriter fw;
		sortByID();
		boolean canWrite = false;
		try {
			fw = new FileWriter(fileIO.getFileHandle(FILENAME));
			int textFileCanWrite = fileIO.checkTextFile(fileIO.getFileHandle("empDB.dat"), false);
			if(textFileCanWrite == 7) {
				canWrite = true;
			}
			if(canWrite == true) {
				BufferedWriter bw = new BufferedWriter(fw);
				for(int i = 0; i < employees.size(); i++) {
					bw.write(employees.get(i).getEmpID() + "|,|" + employees.get(i).getFName() + "|,|" + employees.get(i).getLName() + "|,|" + employees.get(i).getSSN() + "|,|" + 
				employees.get(i).getAge() + "|,|" + employees.get(i).getPronouns() + "|,|" + String.format("%.2f", ((long)(employees.get(i).getSalary()*100)/100.0)) + "|,|" + 
							employees.get(i).getYears() + "|,|" + employees.get(i).getDept()  + "\n");
				}
				
				bw.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	public void restore() {
		MyFileIO fileIO = new MyFileIO();
		FileReader fr;
		boolean canRead = false;
		String[] line;
		try {
			fr = new FileReader(fileIO.getFileHandle(FILENAME));
			int textFileCanRead = fileIO.checkTextFile(fileIO.getFileHandle(FILENAME), true);
			if(textFileCanRead == 0) {
				canRead = true;
			}
			if(canRead == true) {
				BufferedReader br = new BufferedReader(fr);
				String brLine = br.readLine();
				while(brLine != null) {
					line = brLine.split("\\|,\\|");
					
					addEmployee(line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8]);					
					employees.get(employees.size()-1).setEmpID(Integer.parseInt(line[0]));
					brLine = br.readLine();
				}
				br.close();
			}
		} catch (IOException e) {
			return;
		}
			
	}
	
	
	
	private class ByID implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return Integer.compare(o1.getEmpID(), o2.getEmpID());
		}
	}
	
	private class ByName implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			int lNameComp = o1.getLName().compareTo(o2.getLName());
			
			if(lNameComp == 0) {
				int fNameComp = o1.getFName().compareTo(o2.getFName());
				return fNameComp;
			}
			
			return lNameComp;
		}
	}
	
	private class BySalary implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return Double.compare(o1.getSalary(), o2.getSalary());
		}
	}
	
	
	
	public int getNumEmployees() {
		return employees.size();
	}
	
	void sortByID() {
		Collections.sort(employees, new ByID());
	}
	
	void sortByName() {
		Collections.sort(employees, new ByName());
	}
	
	void sortBySalary() {
		Collections.sort(employees, new BySalary());
	}

}

 
