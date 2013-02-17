package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This Window explains all symbols in the arena
 */
class Legende extends JDialog
{
    /**
     * Creates a new Legende which shows all items of the arena and their names
     *
     * @return owner The owning Frame
     */
    Legende (Frame owner)
    {
        super(owner, "Info", true);
        this.setSize(200, 300);
        this.setLocation(200, 200);
        this.setResizable(false);
        this.toFront();
        this.getContentPane().setLayout(new BorderLayout());

        JPanel legende = new JPanel();
        legende.setLayout(new GridLayout(9, 2));

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener()
                             {
                                 public void actionPerformed (ActionEvent e)
                                 {
                                     dispose();
                                 }
                             }
                            );

        legende.add(new JLabel(robot));
        legende.add(new JLabel("Robot"));

        legende.add(new JLabel(card));
        legende.add(new JLabel("Card"));

        legende.add(new JLabel(exit));
        legende.add(new JLabel("Exit"));

        legende.add(new JLabel(trader));
        legende.add(new JLabel("Trader"));

        legende.add(new JLabel(fatamorgana));
        legende.add(new JLabel("Fatamorgana"));

        legende.add(new JLabel(taxman));
        legende.add(new JLabel("Taxman"));

        legende.add(new JLabel(well));
        legende.add(new JLabel("Well"));

        legende.add(new JLabel(wall));
        legende.add(new JLabel("Wall"));

        legende.add(new JLabel(teleporter));
        legende.add(new JLabel("Teleporter"));

        this.getContentPane().add(legende, BorderLayout.CENTER);
        this.getContentPane().add(ok, BorderLayout.SOUTH);

        this.show();
    }
	
	/**
 	* @serial path declaration
 	*/	
	private String imagePath = "src/de/uni_paderborn/robots/gui/images/";
	/**
 	* @serial path declaration
 	*/
    private String imagePathWalls = "src/de/uni_paderborn/robots/gui/images/skins/redhat/";
	
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon taxman = new ImageIcon(imagePath + "tax_up.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon card = new ImageIcon(imagePathWalls + "card.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon feld = new ImageIcon(imagePathWalls + "feld.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon exit = new ImageIcon(imagePathWalls + "exit.gif");
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
    private ImageIcon wall = new ImageIcon(imagePathWalls + "wall_mid.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon well = new ImageIcon(imagePathWalls + "well.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon robot = new ImageIcon(imagePath + "robot_up.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon teleporter = new ImageIcon(imagePathWalls + "teleporter.gif");

}
