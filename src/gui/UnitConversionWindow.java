package gui;

import java.awt.*;
import java.awt.event.*;
import utilities.UnitConversion;
import javax.swing.*;

import branding.KitchIntelIconButton;
import branding.KitchIntelJFrame;
import config.Translator;
import recipes.NutritionInfo;

/**
 * 
 * @author
 *
 */
public class UnitConversionWindow extends KitchIntelJFrame
{
  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_TEXT_FIELD_WIDTH = 8;
  private static final String CALCULATION_COMMAND = "calc";
  private static final String RESET = "reset";

  private static UnitConversionWindow unitWindow = null;

  private String[] units = {"", "DRAM", "OUNCE", "GRAM", "POUND", "PINCH", "TEASPOON", "TABLESPOON",
      "FLUID OUNCE", "CUP", "PINT", "QUART", "GALLON", "MILLILITER"};

  private JComboBox<String> fromunitBox = new JComboBox<String>();
  private JComboBox<String> tounitBox = new JComboBox<String>();
  private JComboBox<String> ingredientBox = new JComboBox<String>();
  private JTextField amount;
  private JLabel resultLabel;

  private String fromUnit;
  private String toUnit;
  private String ingredient;
  private int amountvalue;

  /**
   * 
   * @param main
   */
  private UnitConversionWindow(final Window main)
  {
    super(Translator.translate("KiLowBites Unit Converter"));
    setUp();
    setDefaultCloseOperation(HIDE_ON_CLOSE);
  }

  public static UnitConversionWindow getUnitConversionWindow()
  {
    if (unitWindow == null)
    {
      unitWindow = new UnitConversionWindow(null);

    }
    unitWindow.setVisible(true);

    return unitWindow;
  }

  private Container createIcons()
  {
    Container icons = new Container();
    icons.setLayout(new FlowLayout(FlowLayout.LEFT));
    JButton calcButton = new KitchIntelIconButton(KitchIntelIconButton.CALCULATE_IMAGE);
    JButton resetButton = new KitchIntelIconButton(KitchIntelIconButton.RESET_IMAGE);

    calcButton.setActionCommand(CALCULATION_COMMAND);
    resetButton.setActionCommand(RESET);
    UnitConversionListener calcListener = new UnitConversionListener(this);
    UnitConversionListener resetListener = new UnitConversionListener(this);
    calcButton.addActionListener(calcListener);
    resetButton.addActionListener(resetListener);
    icons.add(calcButton);
    icons.add(resetButton);

    return icons;
  }

  private Container createUnitMenu()
  {
    Container unitMenu = new Container();
    unitMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel fromunitLabel = new JLabel(Translator.translate("From units") + ":");
    fromunitBox = new JComboBox<String>();
    JLabel tounitLabel = new JLabel(Translator.translate("To units") + ":");
    tounitBox = new JComboBox<String>();
    for (int i = 0; i < units.length; i++)
    {
      fromunitBox.addItem(units[i]);
      tounitBox.addItem(units[i]);
    }

    fromunitBox.addItemListener(new FromComboBoxHandler());
    tounitBox.addItemListener(new ToComboBoxHandler());
    Object[] ingredients = NutritionInfo.getIngredientsInMap().toArray();
    JLabel ingredientLabel = new JLabel(Translator.translate("Ingredient") + ":");
    ingredientBox = new JComboBox<String>();

    ingredientBox.addItem("");
    for (int i = 0; i < ingredients.length; i++)
    {
      ingredientBox.addItem((String) ingredients[i]);
    }

    unitMenu.add(fromunitLabel);
    unitMenu.add(fromunitBox);
    unitMenu.add(tounitLabel);
    unitMenu.add(tounitBox);
    unitMenu.add(ingredientLabel);
    unitMenu.add(ingredientBox);
    ingredientBox.addItemListener(new IngredientComboBoxHandler());
    return unitMenu;
  }

  private JPanel createInputPanel()
  {
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromAmount = new JLabel(Translator.translate("From amount") + ":");
    amount = new JTextField(DEFAULT_TEXT_FIELD_WIDTH);
    resultLabel = new JLabel(Translator.translate("Result") + ":  ___________");

    inputPanel.add(fromAmount);
    inputPanel.add(amount);
    inputPanel.add(resultLabel);
    
    inputPanel.setOpaque(false);
    
    return inputPanel;
  }

  private void setUp()
  {
    Container c = getContentPane();
    ;
    Container icons = createIcons();
    c.add(icons, BorderLayout.NORTH);
    Container unitMenu = createUnitMenu();
    c.add(unitMenu, BorderLayout.AFTER_LINE_ENDS);
    JPanel inputPanel = createInputPanel();
    c.add(inputPanel, BorderLayout.SOUTH);
    // Result
    setVisible(true);
    setResizable(false);
    pack();
  }

  private class FromComboBoxHandler implements ItemListener
  {

    public void itemStateChanged(ItemEvent e)
    {
      fromUnit = (String) e.getItem();
    }
  }

  private class ToComboBoxHandler implements ItemListener
  {

    public void itemStateChanged(ItemEvent e)
    {
      toUnit = (String) e.getItem();
    }
  }

  private class IngredientComboBoxHandler implements ItemListener
  {

    public void itemStateChanged(ItemEvent e)
    {
      ingredient = (String) e.getItem();
    }
  }

  private class UnitConversionListener implements ActionListener
  {
    private final UnitConversionWindow subject;

    public UnitConversionListener(final UnitConversionWindow subject)
    {
      super();
      this.subject = subject;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      String command = e.getActionCommand();

      if (command.equals(RESET))
      {
        fromunitBox.setSelectedItem("");
        tounitBox.setSelectedItem("");
        ingredientBox.setSelectedItem("");
        amount.setText("");
        resultLabel.setText(Translator.translate("Result") + ": ");
      }
      else if (command.equals(CALCULATION_COMMAND))
      {
        amountvalue = Integer.parseInt(amount.getText());
        double value = UnitConversion.convert(ingredient, fromUnit, toUnit, amountvalue);
        resultLabel.setText(Translator.translate("Result") + ":   " + Double.toString(value));
      }

    }
  }
}
