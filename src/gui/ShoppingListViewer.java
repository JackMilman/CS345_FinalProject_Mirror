package gui;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import recipes.Ingredient;
import recipes.Recipe;

/**
 * Creates the GUI to view a shopping list.
 * 
 * @author Meara Patterson
 * @version 3/29/2023, Version 1
 */
public class ShoppingListViewer
{

  /**
   * Creates a ShoppingListViewer panel that displays the ingredients of the given recipe.
   * @param recipe
   */
  public ShoppingListViewer(final Recipe recipe)
  {
    
    /**
     * UNFINISHED:
     * Text field shows up, doesn't save information
     * List of ingredients does not work
     */
    
    JFrame frame = new JFrame("KiLowBites Shopping List Viewer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel contentPane = (JPanel) frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    
    // A text field so the user can input the number of people
    JPanel numPeople = new JPanel();
    numPeople.add(new JLabel("Number of People:"));
    JTextField textField = new JTextField("\t\t");
    numPeople.add(textField);
    contentPane.add(numPeople);
    
    // A scrollable, alphabetized list of ingredients in a recipe with prices
    List<Ingredient> ingredients = recipe.getIngredients();
    JScrollPane scroll = new JScrollPane();
    scroll.createVerticalScrollBar();
    for (Ingredient ing : ingredients)
    {
      scroll.add(new JLabel(ing.getName()));
    }
    contentPane.add(scroll);
    
    frame.setSize(600, 400);
    frame.setVisible(true);

  }
  
}