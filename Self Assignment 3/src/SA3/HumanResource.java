package SA3;

public class HumanResource extends Resource{
    public String name;
    public String position;
    private double hours_worked;
    public double salary;

    public HumanResource(String name, String position, double salary){
	this.name = name;
	this.position = position;
	this.salary = salary;
    }
    public void work(double hours){
	hours_worked += hours;
    }
    public double payable_salary(){
	return hours_worked * salary;
    }
}
