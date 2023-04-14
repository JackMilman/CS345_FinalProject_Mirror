package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import branding.Logo;
import config.Language;
import config.Translator;

/**
 * Main Window of the GUI for the KiLowBites application.
 * 
 * @version 3/29/2023 Version 1
 * @author Shelsey Vega
 *
 */
public class MainWindow extends JFrame implements Runnable
{
  private static final long serialVersionUID = 1L;

  /**
   * 
   * @param args
   * @throws InvocationTargetException
   * @throws InterruptedException
   */
  public static void main(final String[] args)
      throws InvocationTargetException, InterruptedException
  {
    SwingUtilities.invokeAndWait(new MainWindow());
  }

  @Override
  public void run()
  {
    // set the the frame
    getContentPane().setBackground(Color.WHITE);
    setTitle(Translator.translate("KiLowBites Main Window"));
    getContentPane().setLayout(new BorderLayout());
    setSize(700, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Construct the controller
    KiLowBitesController controller = new KiLowBitesController(this);

    // create a menu bar and add the items
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu file = new JMenu(Translator.translate("File"));
    file.setActionCommand("File");
    menuBar.add(file);
    // Exit: All windows are closed
    JMenuItem exit = new JMenuItem(Translator.translate("Exit"));
    exit.addActionListener(controller);
    exit.setActionCommand("Exit");
    file.add(exit);

    JMenu edit = new JMenu(Translator.translate("Edit"));
    menuBar.add(edit);
    // Recipe: A RecipeEditor is opened
    JMenuItem recipe = new JMenuItem(Translator.translate("Recipe"));
    recipe.addActionListener(controller);
    recipe.setActionCommand("Recipe");
    edit.add(recipe);
    // Meal: A MealEditor is opened
    JMenuItem meal = new JMenuItem(Translator.translate("Meal"));
    meal.addActionListener(controller);
    meal.setActionCommand("Meal");
    edit.add(meal);

    // not using search in the 1st sprint.
    // JMenu search = new JMenu(Translator.translate("Search"));
    // menuBar.add(search);
    // // Recipes: The user is prompted for the ingredients of interest
    // JMenuItem recipes = new JMenuItem(Translator.translate("Recipes"));
    // recipes.addActionCommand("Recipes");
    // search.add(recipes);
    // // Meals: The user is prompted for the ingredients of interest
    // JMenuItem meals = new JMenuItem("Meals");
    // meals.setActionCommand("Meals");
    // search.add(meals);

    JMenu view = new JMenu(Translator.translate("View"));
    menuBar.add(view);
    // Shopping List: A ShoppingListViewer is opened
    JMenuItem shoppingList = new JMenuItem(Translator.translate("Shopping List"));
    shoppingList.addActionListener(controller);
    shoppingList.setActionCommand("Shopping List");
    view.add(shoppingList);
    // Process: A ProcessViewer is opened
    JMenuItem process = new JMenuItem(Translator.translate("Process"));
    process.addActionListener(controller);
    process.setActionCommand("Process");
    view.add(process);

    JMenu tools = new JMenu(Translator.translate("Tools"));
    menuBar.add(tools);
    // Calorie Calculator: Calorie Calculator is opened
    JMenuItem calorieCalculator = new JMenuItem(Translator.translate("Calorie Calculator"));
    calorieCalculator.addActionListener(controller);
    calorieCalculator.setActionCommand("Calorie Calculator");
    tools.add(calorieCalculator);
    // Units Converter: UnitConversionWindow is opened
    JMenuItem unitsConverter = new JMenuItem(Translator.translate("Units Converter"));
    unitsConverter.addActionListener(controller);
    unitsConverter.setActionCommand("Units Converter");
    tools.add(unitsConverter);

    JMenu configure = new JMenu(Translator.translate("Configure"));
    menuBar.add(configure);
    JMenuItem preferences = new JMenuItem(Translator.translate("Preferences"));
    configure.add(preferences);
    JMenuItem shortcuts = new JMenuItem(Translator.translate("Shortcuts"));
    configure.add(shortcuts);
    JMenuItem nutrition = new JMenuItem(Translator.translate("Nutrition"));
    configure.add(nutrition);
    
    // Help items
    JMenu help = new JMenu(Translator.translate("Help"));
    menuBar.add(help);
    //JMenuItem about = new JMenuItem(Translator.translate("About"));
    //help.add(about);
    JMenuItem userGuide = new JMenuItem(Translator.translate("User Guide"));
    help.add(userGuide);

    // add the company logo to the window
    // Josiah's changes:
    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource(Logo.path()));
    JLabel logoLabel = new JLabel(logo);
    getContentPane().add(logoLabel, BorderLayout.CENTER);
    // ImageIcon logo = new ImageIcon(PATH);
    // JLabel logoLabel = new JLabel(logo);
    getContentPane().add(logoLabel, BorderLayout.CENTER);
    setVisible(true);
  }
}
