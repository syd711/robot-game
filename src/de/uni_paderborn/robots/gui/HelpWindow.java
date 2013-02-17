package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays a help-window which describes the arenas addons
 */
class HelpWindow extends JDialog
{
    /**
     * Creates a new HelpWindow and displays it.
     *
     * @param owner The owning Frame.
     */
    HelpWindow (Frame owner)
    {
        super(owner, "Help", true);
        this.setSize(720, 230);
        this.getContentPane().setLayout(new BorderLayout());

        JButton fatamorganaButton = new JButton("Fatamorgana", fatamorgana);
        JButton traderButton = new JButton("Trader", trader);
        JButton taxmanButton = new JButton("Taxman", taxman);

        fatamorganaButton.addActionListener(new ActionListener()
                                            {
                                                public void actionPerformed (ActionEvent e)
                                                {
                                                    OffGraphics = textPanel.getGraphics();
                                                    int maxX = textPanel.getSize().width - getInsets().left - getInsets().right;
                                                    int maxY = textPanel.getSize().height - getInsets().top - getInsets().bottom;
                                                    OffGraphics.clearRect(0, 0, maxX + 50, maxY + 50);
                                                    Font font = new Font("SansSerif", Font.BOLD, 11);
                                                    OffGraphics.setFont(font);
                                                    OffGraphics.drawString("The Fatamorgana shall confuse our", 20, 60);
                                                    OffGraphics.drawString("citizen. If a roboter tries to ", 20, 75);
                                                    OffGraphics.drawString("use this pseudo well the fatamorgana", 20, 90);
                                                    OffGraphics.drawString("changes its location in ", 20, 105);
                                                    OffGraphics.drawString("direction of the roboter", 20, 120);
                                                }
                                            }
                                           );


        traderButton.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed (ActionEvent e)
                                           {
                                               OffGraphics = textPanel.getGraphics();
                                               int maxX = textPanel.getSize().width - getInsets().left - getInsets().right;
                                               int maxY = textPanel.getSize().height - getInsets().top - getInsets().bottom;
                                               OffGraphics.clearRect(0, 0, maxX + 50, maxY + 50);
                                               Font font = new Font("SansSerif", Font.BOLD, 11);
                                               OffGraphics.setFont(font);
                                               OffGraphics.drawString("'The Trader' gives a roboter (those ", 20, 60);
                                               OffGraphics.drawString("who lost their way) a last chance.", 20, 75);
                                               OffGraphics.drawString("The roboter can buy energy and", 20, 105);
                                               OffGraphics.drawString("pays these energy with one card ", 20, 120);
                                               OffGraphics.drawString("from his portable.", 20, 135);

                                           }
                                       }
                                      );

        taxmanButton.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed (ActionEvent e)
                                           {
                                               OffGraphics = textPanel.getGraphics();
                                               int maxX = textPanel.getSize().width - getInsets().left - getInsets().right;
                                               int maxY = textPanel.getSize().height - getInsets().top - getInsets().bottom;
                                               OffGraphics.clearRect(0, 0, maxX + 50, maxY + 50);
                                               Font font = new Font("SansSerif", Font.BOLD, 11);
                                               OffGraphics.setFont(font);
                                               OffGraphics.drawString("'The Taxman' is on the staff of ", 20, 60);
                                               OffGraphics.drawString("our arena. So he has to collect  ", 20, 75);
                                               OffGraphics.drawString("the tax from all our citizen. ", 20, 90);
                                               OffGraphics.drawString("A roboter who meets our tax-", 20, 120);
                                               OffGraphics.drawString("man has to pay a tax (one card).", 20, 135);
                                           }
                                       }
                                      );



        logoPanel.add(new JLabel(logo));

        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(taxmanButton);
        buttonPanel.add(fatamorganaButton);
        buttonPanel.add(traderButton);

        JPanel both = new JPanel();
        both.setLayout(new GridLayout(1, 2));

        both.add(logoPanel);
        both.add(buttonPanel);

        this.getContentPane().add("West", both);
        this.getContentPane().add("Center", textPanel);

        this.show();

    }
	
	/**
 	* @serial path declaration
 	*/	
	private String imagePath = "src/de/uni_paderborn/robots/gui/images/";

	/**
 	* @serial ImageIcon declaration
 	*/	
    private ImageIcon taxman = new ImageIcon(imagePath + "tax_up.gif");
	/**
 	* @serial ImageIcon declaration
 	*/	
    private ImageIcon fatamorgana = new ImageIcon(imagePath + "fata.gif");
	/**
 	* @serial ImageIcon declaration
 	*/	
    private ImageIcon trader = new ImageIcon(imagePath + "trader.gif");
	/**
 	* @serial ImageIcon declaration
 	*/	
    
	private ImageIcon logo = new ImageIcon(imagePath + "logo.gif");
	
	/**
 	* @serial JComponent declaration
 	*/	
	private JPanel logoPanel = new JPanel();
	
	/**
 	* @serial JComponent declaration
 	*/	
    private JPanel buttonPanel = new JPanel();
	
	/**
 	* @serial JComponent declaration
 	*/	
    private JPanel textPanel = new JPanel();
	
	/**
 	* @serial Graphics declaration
 	*/	
    private Graphics OffGraphics;
}
