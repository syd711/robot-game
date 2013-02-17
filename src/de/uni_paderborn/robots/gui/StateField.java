package de.uni_paderborn.robots.gui;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.Robot;

/**
 * Paints the fix elements of the arena on a JPanel which is added to the StateWindow
 */

class StateField extends JPanel
{
    StateField ()
    {
        background = new ImageIcon(imagePath + "stateBack.gif");
        setOpaque(false);
    }

	/**
     * Initializes the gameState-object of StateField with the gameState-object
	 * of the first round
     *
     * @param gameState The gameState-object
     */
    public void updateStatePane(GameState gameState)
    {
        this.gameState = gameState;
        this.numberOfRobots = gameState.robots.size();
    }

    public void paintComponent(Graphics g)
    {
        if (numberOfRobots != 0)
        {
            if (stateFieldWasPainted)
            {
                if ((offImage == null ) || ( this.myRobots == null) )
                {
                    if (offImage != null) offImage.flush();
                    if (offGraphics != null) offGraphics.dispose();
                    offImage = this.createImage (280, numberOfRobots * 38);
                    offGraphics = offImage.getGraphics();
                }

                // initialize an array with robots
                myRobots = new Robot[numberOfRobots];
                Iterator myIterator = gameState.robots.iterator();


                // set font appearance
                Font font = new Font("SansSerif", Font.BOLD, 11);
                offGraphics.setFont(font);
                offGraphics.setColor(Color.white);

                for (int i = 0; myIterator.hasNext(); i++)
                {
                    Robot robot = (Robot)myIterator.next();
                    int id = robot.getId();

                    background.paintIcon(this, offGraphics, 0, (id - 1)*38);

                    // draw the image of the robot depending on its ID
                    int robotColor = ((robot.getId()) % 9);
                    if (robotColor == 0)
                        img_robot_right_blue.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 1)
                        img_robot_right_cyan.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 2)
                        img_robot_right_green.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 3)
                        img_robot_right_magenta.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 4)
                        img_robot_right_orange.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 5)
                        img_robot_right_red.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 6)
                        img_robot_right_yellow.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 7)
                        img_robot_right_pink.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else if (robotColor == 8)
                        img_robot_right_darkred.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);
                    else
                        img_robot_right_gray.paintIcon(this, offGraphics, 10, (id - 1)*38 + 8);

                    // draw the name of the robot
                    offGraphics.drawString(robot.getName(), 45, (id - 1)*38 + 20);
                }

            }
			// make sure that this method is only called once a game
            stateFieldWasPainted = false;

			// offImage was painted? ok, then show it
            g.drawImage (offImage, 0, 0, Color.white, null);
        }
    }

	/**
 	* @serial myRobots[] declaration
 	*/
    public Robot myRobots[];

	/**
 	* @serial number declaration
 	*/
	private int numberOfRobots;

	/**
 	* @serial x-size declaration
 	*/
	private int xSize;

	/**
 	* @serial y-size declaration
 	*/
    private int ySize;

	/**
 	* @serial If false, the fix elements of the StatePane were painted
 	*/
	public boolean stateFieldWasPainted = true;

	/**
 	* @serial path declaration
 	*/
    private String imagePath = "src/de/uni_paderborn/robots/gui/images/";

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_green	= new ImageIcon(imagePath + "green/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_yellow = new ImageIcon(imagePath + "yellow/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_red	= new ImageIcon(imagePath + "red/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_darkred = new ImageIcon(imagePath + "darkred/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_orange = new ImageIcon(imagePath + "orange/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_cyan	= new ImageIcon(imagePath + "cyan/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_gray	= new ImageIcon(imagePath + "gray/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_pink	= new ImageIcon(imagePath + "pink/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_magenta = new ImageIcon(imagePath + "magenta/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_blue	= new ImageIcon(imagePath + "blue/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon background;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon [][] imageField;

	/**
 	* @serial Image declaration
 	*/
	public Image offImage;

	/**
 	* @serial Graphics declaration
 	*/
    public Graphics offGraphics;

	/**
 	* @serial GameState declaration
 	*/
    private GameState gameState;
}
