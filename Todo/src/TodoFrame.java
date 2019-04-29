import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDatePicker;

public class TodoFrame {
	String fileName;
	JButton delete, edit, add, export;
	String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	JTextField task;
	int taskPosition, taskId;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	boolean isEdit = false;
	JList<Task> myTasks;
	DefaultListModel<Task> listModel;
	JFrame frame;
	JLabel label;
	JPanel panel;
	JPanel featurePanel;

	public TodoFrame() {
		frame = new JFrame();
		frame.setTitle("Todo List");
		// frame.pack();
		frame.setVisible(true);
		frame.setSize(400, 390);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new CardLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();

	}

	DefaultListModel<Task> tasksModel;

	public DefaultListModel<Task> getTasks() {
		BufferedReader reader;
		tasksModel = new DefaultListModel();
		try {
			reader = new BufferedReader(new FileReader("src/res/tasks"));
			String line = reader.readLine();
			int i = 1;
			while (line != null) {
				// System.out.println(line);
				String[] taskArray = line.split(",");
				tasksModel.addElement(new Task( Integer.parseInt(taskArray[0]), Integer.parseInt(taskArray[1]), Integer.parseInt(taskArray[2]),
						Integer.parseInt(taskArray[3]), taskArray[4]));
				line = reader.readLine();
				i = i + 1;

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tasksModel;
	}

	public void saveTask(Task task) {
		try {
			FileWriter fileWriter = new FileWriter("src/res/tasks", true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			// printWriter.println();
			printWriter.println(task.id + "," + task.date + "," + task.month + "," + task.year + "," + task.task); // New
			// line
			printWriter.close();
		} catch (Exception e) {

		}
	}

	public void editTask(DefaultListModel<Task> tasksModel) {
		try {
			File file = new File("src/res/tasks");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			System.out.println("update starts ");

			for (int i = 0; i < tasksModel.size(); i++) {
				Task task = tasksModel.get(i);
				// delete file and create afresh
				FileWriter fileWriter = new FileWriter("src/res/tasks", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println(task.id + "," + task.date + "," + task.month + "," + task.year + "," + task.task); // New
				// line
				printWriter.close();
			}

		} catch (Exception e) {
			System.out.println("Error updating " + e.getMessage());
		}
	}

	public void todoFrame(int day, int month, int year) {

		getTasks();
		final Container content = frame.getContentPane();
		// remove all the views and then repaint again to avoid overlapping of
		// items when a date is selected
		panel.removeAll();
		panel.repaint();
		label = new JLabel();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String monthStr = "";
			if (month < 10)
				monthStr = "0" + month;
			else
				monthStr = String.valueOf(month);
			String date = year + "-" + monthStr + "-" + day;

			label.setText("Today " + format.parse(date));
			panel.add(label);
		} catch (Exception e) {
			System.out.println("...." + e.getMessage());
		}

		fileName = months[month - 1] + "_" + year;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// get all tasks and then sort the tasks for the selected day
		 
		listModel = new DefaultListModel<>();
		// get tasks for selected date
		for (int i = 0; i < tasksModel.size(); i++) {
			if (tasksModel.get(i).date == day && tasksModel.get(i).month == month && tasksModel.get(i).year == year)
				listModel.addElement(tasksModel.get(i));
		}

		myTasks = new JList<>(listModel);
		JScrollPane pane = new JScrollPane(myTasks);
		panel.add(pane);

		JPanel bottonPanel = new JPanel();
		bottonPanel.setSize(480, 150);
		bottonPanel.setBounds(50, 350, 400, 50);
		bottonPanel.setLayout(new FlowLayout());

		JPanel mainPanel = new JPanel();
		task = new JTextField();
		task.setBounds(50, 350, 100, 100);
		mainPanel.add(task);

		delete = new JButton("Delete");
		edit = new JButton("Edit Task");
		add = new JButton("Add Task");
		export = new JButton("Export");

		delete.setBounds(50, 450, 100, 30);
		edit.setBounds(155, 450, 100, 30);
		add.setBounds(260, 450, 100, 30);
		export.setBounds(365, 450, 100, 30);

		bottonPanel.add(delete);
		bottonPanel.add(edit);
		bottonPanel.add(add);
		bottonPanel.add(export);

		BoxLayout layout2 = new BoxLayout(bottonPanel, BoxLayout.X_AXIS);
		bottonPanel.setLayout(layout2);
		BoxLayout layout3 = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(layout3);
		mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 1)));
		
		
		//feature panel
		// bottom layout
		featurePanel = new JPanel(); 
				JDatePicker datePicker = new JDatePicker();
				JLabel birthday = new JLabel("Add birthday");
				JButton addBirthday = new JButton("Add");
				featurePanel.setLayout(new BorderLayout());
				
				featurePanel.add(birthday, BorderLayout.NORTH);
				featurePanel.add(Box.createRigidArea(new Dimension(5, 100)), BorderLayout.CENTER);
				featurePanel.add(datePicker, BorderLayout.CENTER);
				

				featurePanel.add(addBirthday, BorderLayout.SOUTH);
				addBirthday.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						// add birthday here

						if (datePicker.getFormattedTextField().getText() == ""
								|| datePicker.getFormattedTextField().getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "Please birthday date");

							return;
						}
						System.out.println("date " + datePicker.getFormattedTextField().getText());
						Date selectedDate = new Date(datePicker.getFormattedTextField().getText());
						DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
						String reportDate = df.format(selectedDate);
						LocalDate localDate = LocalDate.parse(reportDate, dtf);
						addBirthday(localDate.getDayOfMonth(), localDate.getMonthValue());
					}
				});

		panel.add(mainPanel);
		panel.add(bottonPanel);
		panel.add(Box.createRigidArea(new Dimension(5, 50)), BorderLayout.CENTER);

		panel.add(featurePanel);

		// Implement action listeners for all the buttons
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				taskPosition = myTasks.getSelectedIndex();
				taskId = listModel.get(taskPosition).id;
				if (myTasks.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please task to edit");
					return;
				}
				task.setText(listModel.get(taskPosition).task);
				isEdit = true;
			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (myTasks.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please task to delete");
					return;
				}
				listModel.remove(myTasks.getSelectedIndex());
				myTasks.setModel(listModel);
			}
		});

		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (task.getText().toString().length() == 0) {
					JOptionPane.showMessageDialog(null, "Please type your task");
					return;
				}
				Task newTask = new Task(tasksModel.getSize() + 1, day, month, year, task.getText().toString());
				listModel.addElement(newTask);
				myTasks.setModel(listModel);
				task.setText("");
				saveTask(newTask);
			}
		});

		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tasksModel.size() > 0) {
					exportTasks(tasksModel, month, year);
				}

			}
		});

		// Key listener for task field when editing
		task.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {

				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (isEdit) {

						listModel.set(taskPosition, new Task(taskId, day, month, year, task.getText().toString()));
						myTasks.setModel(listModel);
						for (int i = 0; i < tasksModel.size(); i++) {
							System.out.println("Update item "+tasksModel.get(i).id+"  "+taskId);
							if (tasksModel.get(i).id == taskId) {
								System.out.println("Update item ");
								tasksModel.set(i, new Task(taskId, day, month, year, task.getText().toString()));
								break;
							}						}
						editTask(tasksModel);
						taskId = -1;
						isEdit = false;
						task.setText("");

					}
				}
			}
		});
		// add the panel to the content pane
		content.add(panel, BorderLayout.PAGE_START);

	}

	// Method for exporting the tasks into a file
	public void exportTasks(DefaultListModel<Task> listModel, int month, int year) {
		try {
			// get all tasks for the month
			DefaultListModel<Task> tasks = new DefaultListModel<>();
			for (int i = 0; i < listModel.size(); i++) {
				if (listModel.get(i).month == month && listModel.get(i).year == year)
					tasks.addElement(listModel.get(i));
			}
			// Write tasks line by line on the file
			for (int i = 0; i < tasks.size(); i++) {
				FileWriter fileWriter = new FileWriter(fileName, true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println(tasks.get(i).year + "-" + tasks.get(i).month + "-" + tasks.get(i).date + "  "
						+ tasks.get(i).task); // New line
				printWriter.close();
			}
			// Show a dialog when its successful
			JOptionPane.showMessageDialog(null, "Tasks exported successfully to " + fileName);
		} catch (Exception e) {
			System.out.println("error " + e.getLocalizedMessage());
		}

	}
	
	// Method to add birthday
		public void addBirthday(int day, int month) {
			try {
				FileWriter fileWriter = new FileWriter("src/res/birthdays", true);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println(day + "," + month);
				printWriter.close();
				JOptionPane.showMessageDialog(null, "Birthday added successfully ");
//				getBirthdays();
			} catch (Exception e) {

			}
		}

}
