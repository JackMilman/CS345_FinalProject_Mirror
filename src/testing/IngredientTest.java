package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import recipes.Ingredient;
import recipes.Unit;

/**
 * Test cases for the Ingredient class.
 * 
 * @author Jack Milman, KichIntel
 *
 */
class IngredientTest
{
  private final String ingredientName = "NamedIngredient";
  private final String ingredientDetails = "Basic details for an Ingredient";
  private final Unit ingredientUnit = Unit.POUND;
  private final Unit ingredientUnitMetric = Unit.LITER;

  @Test
  public void testGetName()
  {
    String expected = ingredientName;
    Ingredient ingredient = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    String actual = ingredient.getName();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetDetails()
  {
    String expected = ingredientDetails;
    Ingredient ingredient = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    String actual = ingredient.getDetails();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetAmount()
  {
    double expected = 10;
    Ingredient ingredient = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    double actual = ingredient.getAmount();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetUnit()
  {
    Unit expected = ingredientUnit;
    Ingredient ingredient = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    Unit actual = ingredient.getUnit();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetCalories()
  {
    Ingredient ingredient = new Ingredient("Alcohol", "Scotch", 5, Unit.GRAM);
    // Alcohol cal/gram = 2.75. 2.75 * 5 = 13.75
    double expectedPerGram = 13.75;
    // Alcohol cal/gram = 2.75. 2.75 * 5 = 13.75
    // Alcohol gram/ml = 0.79. 0.79 * 13.75 = 10.8625
    double expectedPerMilliliter = 10.8625;

    double actualPerGram = ingredient.getCaloriesPerGram();
    double actualPerMilliliter = ingredient.getCaloriesPerMilliliter();
    assertEquals(expectedPerGram, actualPerGram);
    assertEquals(expectedPerMilliliter, actualPerMilliliter);
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  public void testEquals()
  {
    Ingredient ingredient1 = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    Ingredient ingredient2 = new Ingredient(ingredientName, ingredientDetails, 15,
        ingredientUnitMetric);
    Ingredient ingredient3 = new Ingredient("Not the same Ingredient", ingredientDetails, 10,
        ingredientUnit);
    Ingredient ingredient4 = new Ingredient(ingredientName, "Not the same Details", 10,
        ingredientUnit);
    assertEquals(ingredient1, ingredient1);
    assertFalse(ingredient1.equals(null));
    assertFalse(ingredient1.equals("I am not an Ingredient"));
    assertEquals(ingredient1, ingredient2);
    assertNotEquals(ingredient1, ingredient3);
    assertNotEquals(ingredient1, ingredient4);
  }

  @Test
  public void testHashCode()
  {
    Ingredient ingredient1 = new Ingredient(ingredientName, ingredientDetails, 10, ingredientUnit);
    Ingredient ingredient2 = new Ingredient(ingredientName, ingredientDetails, 1687, Unit.NONE);
    Ingredient differentName = new Ingredient("NewName", ingredientDetails, 1687, Unit.NONE);
    Ingredient differentDetails = new Ingredient(ingredientName, "NewDetails", 1687, Unit.NONE);

    int firstCode = ingredient1.hashCode();
    int secondCode = ingredient2.hashCode();
    int differentNameCode = differentName.hashCode();
    int differentDetailsCode = differentDetails.hashCode();
    assertEquals(firstCode, secondCode);
    assertNotEquals(firstCode, differentNameCode);
    assertNotEquals(firstCode, differentDetailsCode);
    assertNotEquals(differentNameCode, differentDetailsCode);
  }

  @Test
  public void testToString()
  {
    Double amount = 10.0;
    Ingredient individual = new Ingredient(ingredientName, ingredientDetails, amount,
        Unit.INDIVIDUAL);
    Ingredient pinch = new Ingredient(ingredientName, ingredientDetails, amount, Unit.PINCH);
    Ingredient noUnit = new Ingredient(ingredientName, ingredientDetails, amount, Unit.NONE);
    Ingredient anyOtherUnit = new Ingredient(ingredientName, ingredientDetails, amount, Unit.CUP);
    Ingredient nullDetails = new Ingredient(ingredientName, null, amount, Unit.CUP);

    String individualString = String.format("%.2f %s %s %s", amount,
        Unit.INDIVIDUAL.getName().toLowerCase(), ingredientDetails, ingredientName);
    String pinchString = String.format("%.2f %ses of %s %s", amount,
        Unit.PINCH.getName().toLowerCase(), ingredientDetails, ingredientName);
    String noUnitString = String.format("%.2f %s%s %s", amount, Unit.NONE.getName().toLowerCase(),
        ingredientDetails, ingredientName);
    String cupString = String.format("%.2f %ss of %s %s", amount, Unit.CUP.getName().toLowerCase(),
        ingredientDetails, ingredientName);
    String nullDetailsString = String.format("%.2f %ss of %s%s", amount,
        Unit.CUP.getName().toLowerCase(), "", ingredientName);

    assertEquals(individualString, individual.toString());
    assertEquals(pinchString, pinch.toString());
    assertEquals(noUnitString, noUnit.toString());
    assertEquals(cupString, anyOtherUnit.toString());
    assertEquals(nullDetailsString, nullDetails.toString());
  }

  @Test
  public void testCompareTo()
  {
    Ingredient ingredient1 = new Ingredient("ABCD", ingredientDetails, 10, Unit.DRAM);
    Ingredient ingredient2 = new Ingredient("EFGH", ingredientDetails, 1687, Unit.CUP);
    assertFalse(ingredient1.compareTo(ingredient2) > 0);
  }

}
