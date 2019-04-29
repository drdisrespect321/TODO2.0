import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

public class TodoList {

	int currentDay, currentMonth, currentYear;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();

	TodoFrame todoFrame = new TodoFrame();
	JPanel mainPanel;
	JFrame frame;
	int birthDay = 8;
	int birthMonth = 8;
	ArrayList<Birthday> birthdays;

	public TodoList() {
		frame = new JFrame();
		frame.setTitle("Calendar ");
		getBirthdays();
		mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		currentDay = localDate.getDayOfMonth();
		currentMonth = localDate.getMonthValue();
		currentYear = localDate.getYear();

		UtilDateModel model = new UtilDateModel();
		// set Birthdays to calendar
		for (int i = 0; i < birthdays.size(); i++) {
			model.setMonth(birthdays.get(i).month - 1);
			model.setDay(birthdays.get(i).day);
			System.out.println("m " + birthdays.get(i).day + "/ " + birthdays.get(i).month);
			// model.setDate(2019 , 03, 20);
			model.setSelected(true);

		}
		JDatePanel datePanel = new JDatePanel(model);
		 
		
		// Calendar click listener
		datePanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String date = datePanel.getModel().getValue().toString();

				Date selectedDate = (Date) datePanel.getModel().getValue();
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String reportDate = df.format(selectedDate);
				LocalDate localDate = LocalDate.parse(reportDate, dtf);
				todoFrame.todoFrame(localDate.getDayOfMonth(), (localDate.getMonthValue()), localDate.getYear());

			}
		});

		todoFrame.todoFrame(currentDay, currentMonth, currentYear);

		
		

		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		mainPanel.add(datePanel);
		mainPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.setSize(300, 350);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// pack();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TodoList();
			}
		});

	}

	
	// method to get adde birthdays
	public ArrayList<Birthday> getBirthdays() {
		BufferedReader reader;

		birthdays = new ArrayList<>();
		try {
			File file = new File("src/res/birthdays");
			if (!file.exists()) {
				file.createNewFile();
			}

			reader = new BufferedReader(new FileReader("src/res/birthdays"));
			String line = reader.readLine();
			int i = 1;
			while (line != null) {
				System.out.println(line);
				String[] array = line.split(",");
				birthdays.add(new Birthday(Integer.parseInt(array[0]), Integer.parseInt(array[1])));
				line = reader.readLine();
				i = i + 1;

			}
			reader.close();
		} catch (IOException e) {
			System.out.println("error getting birthdays");
			e.printStackTrace();
		}
		return birthdays;
	}

}
