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
 * Paints the moving items of the arena on a JPanel which is added to the StateWindow
 */

class StateDraw extends JPanel
{
    StateDraw ()
    {
        setOpaque(false);
    }

    /**
     * Updates the moving items of the arena
     *
     * @param gameState The gameState-object
     */

    public void updateStatePane(GameState gameState)
    {
        this.gameState = gameState;
        repaint();
    }

    public void paintComponent( Graphics g)
    {
        if (gameState != null)
        {
            if (disqualifiedRobots == null)
                disqualifiedRobots = new int[gameState.robots.size()];

            for (int i = 0; i < disqualifiedRobots.length; i++)
            {
                if (disqualifiedRobots[i] != 0)
                    disqualify.paintIcon(this, g, 0, (disqualifiedRobots[i] - 1)*38);
            }

            Font font = new Font("SansSerif", Font.BOLD, 16);
            g.setFont(font);

            Iterator myIterator = gameState.robots.iterator();

            while (myIterator.hasNext())
            {
                Robot robot = (Robot)myIterator.next();
                int id = robot.getId();

                //draw energy
                double energy = robot.getEnergy();
                if (fullEnergy == 0)
                    this.fullEnergy = energy;

                int widthOfEnergy = (int)(69 * (energy / fullEnergy));

                if (widthOfEnergy > 25)
                    g.setColor(Color.green);
                else if (widthOfEnergy <= 25 && widthOfEnergy > 13)
                    g.setColor(Color.yellow);
                else if (widthOfEnergy <= 13 && widthOfEnergy > 0)
                    g.setColor(Color.red);
                else if (widthOfEnergy == 0)
                    sleep_gray.paintIcon(this, g, 0, (id - 1)*38);
                else g.setColor(Color.green);
                g.fillRect(166, 38*(id - 1) + 4, widthOfEnergy, 8);

                // draw score

                // make copy of HashSet and evaluate it
                try
                {
                    HashSet tmpHash = new HashSet();
                    Iterator robotIterator = robot.iteratorOfPortable();
                    while (robotIterator.hasNext())
                        tmpHash.add(robotIterator.next());
                    int scoreNo = robot.getArena().valuateCards(robot.iteratorOfPortable());

                    String score = Integer.toString(scoreNo);
                    g.setColor(Color.black);
                    g.drawString(score, 242, 38*(id - 1) + 25);
                }
                catch (NullPointerException e)
                {}


                Iterator cardIterator = robot.iteratorOfPortable();
                // drawCards
                for (int k = 0; cardIterator.hasNext(); k++)
                {
                    Card actualCard = (Card) cardIterator.next();
                    int farbe = (actualCard.getColor() % 11) + 1;
                    int wert = actualCard.getValue();

                    if (farbe == 1 && wert == 1)	s_one_green.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 1 && wert == 2)	s_two_green.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 1 && wert == 3)	s_three_green.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 1 && wert == 4)	s_four_green.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 2 && wert == 1)	s_one_darkpink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 2 && wert == 2)	s_two_darkpink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 2 && wert == 3)	s_three_darkpink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else	if (farbe == 2 && wert == 4)	s_four_darkpink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 3 && wert == 1)	s_one_blue.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 3 && wert == 2)	s_two_blue.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 3 && wert == 3)	s_three_blue.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 3 && wert == 4)	s_four_blue.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 4 && wert == 1)	s_one_yellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 4 && wert == 2)	s_two_yellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 4 && wert == 3)	s_three_yellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 4 && wert == 4)	s_four_yellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 5 && wert == 1)	s_one_red.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 5 && wert == 2)	s_two_red.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 5 && wert == 3)	s_three_red.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 5 && wert == 4)	s_four_red.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 6 && wert == 1)	s_one_orange.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 6 && wert == 2)	s_two_orange.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 6 && wert == 3)	s_three_orange.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 6 && wert == 4)	s_four_orange.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 7 && wert == 1)	s_one_brown.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 7 && wert == 2)	s_two_brown.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 7 && wert == 3)	s_three_brown.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 7 && wert == 4)	s_four_brown.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 8 && wert == 1)	s_one_blew.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 8 && wert == 2)	s_two_blew.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 8 && wert == 3)	s_three_blew.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 8 && wert == 4)	s_four_blew.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 9 && wert == 1)	s_one_pink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 9 && wert == 2)	s_two_pink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 9 && wert == 3)	s_three_pink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 9 && wert == 4)	s_four_pink.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 10 && wert == 1)	s_one_lightyellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 10 && wert == 2)	s_two_lightyellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 10 && wert == 3)	s_three_lightyellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 10 && wert == 4)	s_four_lightyellow.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);

                    else if (farbe == 11 && wert == 1)	s_one_lightgreen.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 11 && wert == 2)	s_two_lightgreen.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 11 && wert == 3)	s_three_lightgreen.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17);
                    else if (farbe == 11 && wert == 4)	s_four_lightgreen.paintIcon(this, g, 165 + k*15, 38*(id - 1) + 17); ;
                }

            }

        }

    }

	/**
 	* @serial fullEnergy declaration
 	*/
    double fullEnergy;

	/**
 	* @serial int[][] of disqualified robots declaration
 	*/
    int[] disqualifiedRobots;

	/**
 	* @serial GameState declaration
 	*/
    private GameState gameState;

	/**
 	* @serial paht declaration
 	*/
    private String imagePath = "src/de/uni_paderborn/robots/gui/images/";

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon disqualify = new ImageIcon(imagePath + "statedis.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon sleep_gray = new ImageIcon(imagePath + "gray/sleep.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_green = new ImageIcon(imagePath + "green/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_yellow = new ImageIcon(imagePath + "yellow/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_red = new ImageIcon(imagePath + "red/robot_right.gif");
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
    private ImageIcon img_robot_right_cyan = new ImageIcon(imagePath + "cyan/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_gray = new ImageIcon(imagePath + "gray/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_pink = new ImageIcon(imagePath + "pink/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_magenta = new ImageIcon(imagePath + "magenta/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_blue = new ImageIcon(imagePath + "blue/robot_right.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_red = new ImageIcon(imagePath + "statecards/s_one_red.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_red = new ImageIcon(imagePath + "statecards/s_two_red.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_red = new ImageIcon(imagePath + "statecards/s_three_red.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_red = new ImageIcon(imagePath + "statecards/s_four_red.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_green = new ImageIcon(imagePath + "statecards/s_one_green.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_green = new ImageIcon(imagePath + "statecards/s_two_green.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_green = new ImageIcon(imagePath + "statecards/s_three_green.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_green = new ImageIcon(imagePath + "statecards/s_four_green.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_yellow = new ImageIcon(imagePath + "statecards/s_one_yellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_yellow = new ImageIcon(imagePath + "statecards/s_two_yellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_yellow = new ImageIcon(imagePath + "statecards/s_three_yellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_yellow = new ImageIcon(imagePath + "statecards/s_four_yellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_darkpink = new ImageIcon(imagePath + "statecards/s_one_darkpink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_darkpink = new ImageIcon(imagePath + "statecards/s_two_darkpink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_darkpink = new ImageIcon(imagePath + "statecards/s_three_darkpink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_darkpink = new ImageIcon(imagePath + "statecards/s_four_darkpink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/


    private ImageIcon s_one_lightyellow = new ImageIcon(imagePath + "statecards/s_one_lightyellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_lightyellow = new ImageIcon(imagePath + "statecards/s_two_lightyellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_lightyellow = new ImageIcon(imagePath + "statecards/s_three_lightyellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_lightyellow = new ImageIcon(imagePath + "statecards/s_four_lightyellow.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_pink = new ImageIcon(imagePath + "statecards/s_one_pink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_pink = new ImageIcon(imagePath + "statecards/s_two_pink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_pink = new ImageIcon(imagePath + "statecards/s_three_pink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_pink = new ImageIcon(imagePath + "statecards/s_four_pink.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_lightgreen = new ImageIcon(imagePath + "statecards/s_one_lightgreen.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_lightgreen = new ImageIcon(imagePath + "statecards/s_two_lightgreen.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_lightgreen = new ImageIcon(imagePath + "statecards/s_three_lightgreen.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_lightgreen = new ImageIcon(imagePath + "statecards/s_four_lightgreen.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_blue = new ImageIcon(imagePath + "statecards/s_one_blue.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_blue = new ImageIcon(imagePath + "statecards/s_two_blue.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_blue = new ImageIcon(imagePath + "statecards/s_three_blue.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_blue = new ImageIcon(imagePath + "statecards/s_four_blue.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_blew = new ImageIcon(imagePath + "statecards/s_one_blew.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_blew = new ImageIcon(imagePath + "statecards/s_two_blew.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_blew = new ImageIcon(imagePath + "statecards/s_three_blew.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_blew = new ImageIcon(imagePath + "statecards/s_four_blew.gif");
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon s_one_orange = new ImageIcon(imagePath + "statecards/s_one_orange.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_orange = new ImageIcon(imagePath + "statecards/s_two_orange.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_orange = new ImageIcon(imagePath + "statecards/s_three_orange.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_orange = new ImageIcon(imagePath + "statecards/s_four_orange.gif");
	/**
 	* @serial ImageIcon declaration
 	*/


    private ImageIcon s_one_brown = new ImageIcon(imagePath + "statecards/s_one_brown.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_two_brown = new ImageIcon(imagePath + "statecards/s_two_brown.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_three_brown = new ImageIcon(imagePath + "statecards/s_three_brown.gif");
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon s_four_brown = new ImageIcon(imagePath + "statecards/s_four_brown.gif");

}
