package de.uni_paderborn.robots.gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The WelcomeWindow is displayes before the real game starts
 */
class WelcomeWindow extends JWindow
{
	/**
 	* @serial Toolkit declaration
 	*/
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
	/**
 	* @serial JLabel declaration
 	*/
    private JLabel label = new JLabel(new ImageIcon("src/de/uni_paderborn/robots/gui/images/welcome.gif"));

    /**
     * Create the Welcome Window
     */
    public WelcomeWindow()
    {
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        this.getContentPane().add(label, BorderLayout.CENTER);
        centerWindow();
        pack();
        this.show();

        this.addMouseListener(new MouseAdapter()
                              {
                                  public void mousePressed(MouseEvent e)
                                  {
                                      if (e.getClickCount() == 2)
                                      {
                                          setVisible(false);
                                      }
                                  }
                              }
                             );
    }

    /**
     * Center the window.
     */
    private void centerWindow()
    {
        Dimension scrnSize = toolkit.getScreenSize();
        Dimension labelSize = label.getPreferredSize();
        int labelWidth = labelSize.width,
                         labelHeight = labelSize.height;

        this.setLocation(scrnSize.width / 2 - (labelWidth / 2),
                         scrnSize.height / 2 - (labelHeight / 2));
        this.pack();
    }
}
