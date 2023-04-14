package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import config.Translator;
import recipes.Meal;
import recipes.Recipe;
import recipes.Step;
import recipes.Utensil;
import utilities.SortLists;

/**
 * GUI for the process viewer. This allows the user to view the list of utensils
 * and steps in a recipe.
 * 
 * @version 3/29/23
 * @author Allie O'Keeffe, KichIntel
 *
 */
public class ProcessViewer extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	//private static final String RECIPEEXT = "rcp";
	//private static final String MEALEXT = "mel";
	private JTable table;
	private List<Step> steps;

	/**
	 * Recipe constructor.
	 * 
	 * @param recipe
	 */
	public ProcessViewer(final Recipe recipe) {
		super(String.format("%s	%s", Translator.translate("KiLowBites Process Viewer"), recipe.getName()));
		setUp(recipe);
	}

	/**
	 * Meal constructor.
	 * 
	 * @param meal
	 */
	public ProcessViewer(final Meal meal) {
		super(String.format("%s %s", Translator.translate("KiLowBites Process Viewer"), meal.getName()));
		setUp(meal);
	}

	/**
	 * Sets up to panel for the utensils.
	 * 
	 * @param utensils The list of utensils used in a recipe
	 * @return A scrollable panel with a border and list of utensils
	 */
	private JScrollPane setUpUtensils(final List<Utensil> utensils) {
		SortLists.sortUtensils(utensils); // Added since change to Recipe's get() methods do not return
											// an automatically sorted list anymore - Jack, 3/30
		
		//Creates table model and sets editable to false
		DefaultTableModel tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		//Creates JTable
		String[] data = new String[utensils.size()];
		int r = 0;
		for (Utensil item : utensils) {
			data[r] = item.toString() + "";
			r++;
		}
		tableModel.addColumn("", data);
		JTable table = new JTable(tableModel);

		// Set up scroll pane
		JScrollPane p = new JScrollPane(table);
		p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p.setBorder(BorderFactory.createTitledBorder(Translator.translate("Utensils")));
		p.setPreferredSize(new Dimension(575, 100));

		return p;
	}

	/**
	 * Sets up to panel for the steps.
	 * 
	 * @param steps The list of steps used in a recipe
	 * @return A scrollable panel with a border and list of steps
	 */
	private JScrollPane setUpSteps() {
		// Sets table model editable to false
		DefaultTableModel tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}

		};
		
		//Creates JTable with steps and their corresponding times
		String[] stepData = new String[steps.size()];
		String[] timeData = new String[steps.size()];
		int r = 0;
		for (Step item : steps) {
			stepData[r] = item.toString(false) + "";
			r++;
		}
		tableModel.addColumn("Steps", stepData);
		tableModel.addColumn("Time", timeData);
		table = new JTable(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		
		// Sets up scroll panel
		JScrollPane p = new JScrollPane(table);
		p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p.setBorder(BorderFactory.createTitledBorder(Translator.translate("Steps")));
		p.setPreferredSize(new Dimension(575, 300));
		return p;
	}

	/**
	 * Converts the time  back to standard time. 
	 * 
	 * @param total The time in minutes and military time
	 * @return A string representation of the time
	 */
	private String convertMinsToTime(int total) {
		String indicator;
		int hour;
		System.out.print(total);

		// Finds if the time crosses from AM to PM or vice versa
		if (total > 719) {
			indicator = "PM";
			hour = total / 60 - 12;
		} else {
			indicator = "AM";
			hour = total / 60;
		}
		if (hour == 0)
			hour = 12;
		int mins = total % 60;
		return String.format("%d:%02d %s", hour, mins, indicator);
	}

	/**
	 * Updates the time to start tests when the user inputs a plating time.
	 * 
	 * @param hour The hour the user wants to start eating.
	 * @param min  The time the user wants to start eating
	 * @param amPm The evening or morning
	 */
	private void setTimes(int hour, int min, String amPm) {
		if (amPm.equals("PM") && hour != 12) {
			hour = hour + 12;
		}
		int totalTimeInMins = hour * 60 + min;
		DefaultTableModel model = ((DefaultTableModel) table.getModel());
		for (int i = steps.size() - 1; i >= 0; i--) {
			int duration = steps.get(i).getTime();
			totalTimeInMins = totalTimeInMins - duration;
			model.setValueAt(convertMinsToTime(totalTimeInMins), i, 1);
		}
	}

	/**
	 * Sets up the remove ingredients button. This removes the ingredients from the
	 * inventory that are in the recipe.
	 * 
	 * @return A button to click and remove the ingredients
	 */
	private JButton setUpRemoveIngredients() {
		JButton removeIngredients = new JButton("Recipe Complete");
		removeIngredients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Remove ingredients from inventory
				JOptionPane.showMessageDialog(null, "Successful removal of ingredients.", "Ingredient Removal",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(getClass().getClassLoader().getResource("KILowBites_Logo.png")));
			}
		});
		return removeIngredients;
	}

	/**
	 * Sets up the plating time inputs. This includes the some JTextFields and the two JComboBoxes. 
	 * 
	 * @return A panel with the plating time inputs. 
	 */
	private JPanel setUpPlatingTime() {
		JPanel p = new JPanel();
		String[] h = new String[13];
		for (int i = 0; i < h.length; i++) {
			h[i] = String.format("%02d", i);
		}
		JComboBox<String> hours = new JComboBox<>(h);

		String[] m = new String[60];
		for (int i = 0; i < m.length; i++) {
			m[i] = String.format("%02d", i);
		}
		JComboBox<String> minutes = new JComboBox<>(m);
		String[] amPm = { "", "AM", "PM" };
		JComboBox<String> mornNight = new JComboBox<>(amPm);

		// Creates an action listener for the plating time
		ActionListener time = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer selectedHour = Integer.parseInt((String) hours.getSelectedItem());
				Integer selectedMin = Integer.parseInt((String) minutes.getSelectedItem());
				String morningEvening = (String) mornNight.getSelectedItem();
				if (!(selectedHour == 0) && !(selectedMin == 0) && !morningEvening.equals("")) {
					setTimes(selectedHour, selectedMin, morningEvening);
				}
			}

		};

		// Sets up the panel
		mornNight.addActionListener(time);
		minutes.addActionListener(time);
		hours.addActionListener(time);
		p.add(new JTextField("Enter Plating Time: "));
		p.add(hours);
		p.add(new JTextField(":"));
		p.add(minutes);
		p.add(mornNight);
		return p;
	}

	/**
	 * Sets up the calorie calculations, plating time, and ingredient removal from
	 * the inventory.
	 * 
	 * @param calories The number of calories in a recipe/meal
	 * @return A JPanel with the calories, inventory, and plating time
	 */
	private JPanel setUpCaloriesAndInventory(double calories) {
		JPanel p = new JPanel();
		p.add(new JTextField("Calories: " + Math.round(calories * 10) / 10.0));
		p.add(setUpRemoveIngredients());
		p.add(setUpPlatingTime());
		return p;
	}

	/**
	 * Sets up the main frame for the process viewer. This adds utensils and steps
	 * to the main frame.
	 * 
	 * @param recipe The recipe the process viewer is looking at
	 */
	private void setUp(final Recipe recipe) {
		JScrollPane p;
		Container c;

		c = getContentPane();
		p = setUpUtensils(recipe.getUtensils());
		c.setLayout(new BorderLayout());
		c.add(p, BorderLayout.NORTH);

		steps = recipe.getSteps();
		p = setUpSteps();
		c.add(p, BorderLayout.CENTER);

		c.add(setUpCaloriesAndInventory(recipe.calculateCalories()), BorderLayout.SOUTH);

		setSize(700, 450);
		pack();
		setVisible(true);
	}

	/**
	 * Sets up the main frame for the process viewer. This adds utensils and steps
	 * to the main frame.
	 * 
	 * @param recipe The recipe the process viewer is looking at
	 */
	private void setUp(final Meal meal) {
		JScrollPane p;
		Container c;

		c = getContentPane();

		// Gets each utensil and step in the meals
		ArrayList<Utensil> utensils = new ArrayList<>();
		ArrayList<Step> steps = new ArrayList<>();
		for (Recipe recipe : meal.getRecipes()) {
			for (Utensil utensil : recipe.getUtensils()) {
				if (!utensils.contains(utensil))
					utensils.add(utensil);
			}
			for (Step step : recipe.getSteps()) {
				steps.add(step);
			}
		}

		p = setUpUtensils(utensils);
		c.setLayout(new FlowLayout());
		c.add(p);

		p = setUpSteps();
		c.add(p);
		setSize(600, 450);
		setVisible(true);
	}

}
