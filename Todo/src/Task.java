import javax.swing.DefaultListModel;

//This class is a task model. Includes the date month, year and the task.
public class Task {
	public String task;
	int id;
	int month;
	int date;
	int year;
	static DefaultListModel<Task> tasks=new DefaultListModel();

	public Task(int id,int date, int month,int year, String task) {
		this.id=id;
		this.date = date;
		this.month = month;
		this.task = task;
		this.year=year;
	}
	@Override
	public String toString() {
	    return task;
	} 
	
}
