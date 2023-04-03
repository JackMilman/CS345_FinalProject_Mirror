package recipes;

import utilities.UnitConversion;

/**
 * Object class describing an Ingredient in a Recipe. An Ingredient contains a name, details on
 * itself (i.e. what it looks like or how it smells), the amount of that Ingredient required, and
 * the unit that amount is being measured in.
 * 
 * @version 3/28/2023 Version 1
 * @author Jack Milman, KichIntel
 *
 */
public class Ingredient
{

  private final String name;

  private final String details;
  
  private final NutritionInfo nutritionInfo;

  private final double amount;

  private final String unit;

  /**
   * Constructs a new Ingredient.
   * 
   * @param name
   * @param details
   * @param amount
   * @param unit
   */
  public Ingredient(final String name, final String details, final double amount, final String unit)
  {
    this.name = name;
    this.details = details;
    this.nutritionInfo = NutritionInfo.fromCode(name);
    this.amount = amount;
    this.unit = unit;
  }

  /**
   * Gets the name of the Ingredient.
   * 
   * @return the name of the Ingredient
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets the details of the Ingredient.
   * 
   * @return the details of the Ingredient
   */
  public String getDetails()
  {
    return details;
  }

  /**
   * Gets the amount of the Ingredient.
   * 
   * @return the amount of the Ingredient
   */
  public double getAmount()
  {
    return amount;
  }

  /**
   * Gets the unit of the Ingredient.
   * 
   * @return the unit of the Ingredient
   */
  public String getUnit()
  {
    return unit;
  }
  
  /*
   * Gets the calories in the Ingredient per gram.
   * 
   * @return the calories of the Ingredient as a double
   */
//  public double getCaloriesPerGram() {
//    return UnitConversion.convert(unit, "GRAM", amount) * nutritionInfo.getCalPerGram();
//  }
  
  /*
   * Gets the calories in the Ingredient per milliliter.
   * 
   * @return the calories of the Ingredient as a double
   */
  public double getCaloriesPerMilliliter() {
    return getCaloriesPerGram() * nutritionInfo.getGramPerML();
  }

  /**
   * Compares two Ingredients to see if they are equal. Two Ingredients are considered equal when
   * they have the same name and details.
   * 
   * @param other
   *          the object being compared to this Ingredient
   * @return true if the two Ingredients have the same name, or false if they do not
   */
  public boolean equals(final Object other)
  {
    if (other == this)
      return true;
    if (other == null)
      return false;
    if (other.getClass() != this.getClass())
      return false;
    
    Ingredient that = (Ingredient) other;
    if (this.name.equals(that.name))
      if (this.details.equals(that.details))
        return true;
    
    return false;
  }

  /**
   * This operation is not supported because Ingredients are mutable.
   * 
   * @return does not return a value
   * @throws UnsupportedOperationException
   *           if called
   */
  public int hashCode()
  {
    throw new UnsupportedOperationException(
        "hashCode() is not supported because Ingredients are mutable");
  }

}
