package config;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class CustomAction extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private String name;
  private KeyStroke keyStroke;
  private JFrame frame;

  public CustomAction(String name, JFrame frame)
  {
    this.name = name;
    this.frame = frame;
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // Add the code that you want to execute when the button is clicked
  }

  /**
   * 
   * @param keyStroke
   */
  public void setAcceleratorKey(KeyStroke keyStroke)
  {
    this.keyStroke = keyStroke;
  }

  /**
   * 
   * @return keyStroke
   */
  public KeyStroke getAcceleratorKey()
  {
    return keyStroke;
  }

  @Override
  public String toString()
  {
    return name;
  }

  /**
   * 
   */
  public void addToMenu()
  {
    frame.getRootPane().getActionMap().put(name, this);
    frame.getRootPane().getInputMap().put(keyStroke, name);
  }
}
