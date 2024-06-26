package branding;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A JButton with one of the logos from the Google Material Library.
 * 
 * @author Josiah Leach, KitchIntel
 * @version 03.29.2023
 */
public class KitchIntelIconButton extends JButton
{

  public static final String CALCULATE_IMAGE = "calculate.png";
  public static final String CLOSE_IMAGE = "close.png";
  public static final String NEW_IMAGE = "new.png";
  public static final String OPEN_IMAGE = "open.png";
  public static final String PRINT_IMAGE = "print.png";
  public static final String RESET_IMAGE = "reset.png";
  public static final String SAVE_IMAGE = "save.png";
  public static final String SAVE_AS_IMAGE = "save_as.png";

  private static final long serialVersionUID = 1L;

  private static final String PATH = "images/";
  private static final String PRESSED_MODIFIER = "white_";

  /**
   * Create a KitchIntel button.
   * 
   * @param buttonImage
   */
  public KitchIntelIconButton(final String buttonImage)
  {
    // Josiah's changes:
    // super();
    super(new ImageIcon(PATH + buttonImage));
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setOpaque(false);

    // Josiah's changes:
    setIcon(new ImageIcon(getClass().getClassLoader().getResource(buttonImage)));

    setPressedIcon(
        new ImageIcon(getClass().getClassLoader().getResource(PRESSED_MODIFIER + buttonImage)));
    setDisabledIcon(
        new ImageIcon(getClass().getClassLoader().getResource(PRESSED_MODIFIER + buttonImage)));

    // setPressedIcon(new ImageIcon(PATH + PRESSED_MODIFIER + buttonImage));
    // setDisabledIcon(new ImageIcon(PATH + PRESSED_MODIFIER + buttonImage));
  }
}
