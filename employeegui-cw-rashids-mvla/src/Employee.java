
public class Employee {
	private String lastName;
	private String firstName;
	private double salary;
	private int years;
	private String SSN;
	private int age;
	private String pronouns;
	private String dept;
	private static int EMPID = 1;
	private int empID;

public Employee (String fName, String lName, String SSN, int age, String pronouns, double salary, int years, String dept) {
	this.lastName = lName;
	this.firstName = fName;
	this.SSN = SSN;
	this.age = age;
	this.pronouns = pronouns;
	this.salary = salary;
	this.years = years;
	this.dept = dept;
	empID = EMPID;
	EMPID++;
}


public String getLName() {
	return lastName;
}


public void setLName(String name) {
	this.lastName = name;
}

public String getFName() {
	return firstName;
}

public void setFName(String name) {
	this.firstName = name;
}

public double getSalary() {
	return salary;
}


public void setSalary(double salary) {
	this.salary = salary;
}


public int getYears() {
	return years;
}


public void setYears(int years) {
	this.years = years;
}


public String getSSN() {
	return SSN;
}


public void setSSN(String sSN) {
	SSN = sSN;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public String getPronouns() {
	return pronouns;
}

public void setPronouns(String pronouns) {
	this.pronouns = pronouns;
}

public String getDept() {
	return dept;
}

public void setDept(String dept) {
	this.dept = dept;
}


public int getEmpID() {
	return empID;
}

public void setEmpID(int empID) {
	this.empID = empID;
}

protected static void resetStaticID() {
	EMPID = 1;
}

public String toString( ) {
	String str = String.format("%-8s\t","Name:")+firstName+" "+lastName;
	str += (!pronouns.isEmpty()) ? " ("+pronouns+")\n" : "\n";
	str = str +    String.format("%-8s\t", "SSN")+String.format("%-16s\t",SSN );
	str = str +    String.format("%-16s\t","Employee ID:")+empID+"\n";
	str = str +    String.format("%-8s\t","Age:")+String.format("%-16d\t\t", age)+String.format("%-20s\t","Years Exp:")+years+"\n";
	if(dept.equals("Finance")) {
		str = str +    String.format("%-8s\t","Dept:")+String.format("%-16s\t\t",dept);
	} else {
		str = str +    String.format("%-8s\t","Dept:")+String.format("%-16s\t",dept);		
	}
	str = str +    String.format("%-20s\t\t","Salary:")+ String.format("%.2f",((long) (salary*100)/100.0));
	return(str);
	}


}
