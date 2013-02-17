package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.robot.*;
import de.uni_paderborn.robots.robot.group3.*;
import de.uni_paderborn.robots.logic.Robot;

/**
 * Contains of two panel: one for fix and one for moving items<br>
 * - fix items are painted in class GridField<br>
 * - moveable items are painted in class ArenaField
 */

class ArenaPane extends JPanel
{
	/**
	 * Creates an ArenaPane object and contains the initialization
	 * of all images who should represent an item.
	 */

    ArenaPane ()
    {
        // set the backkground color
        this.setBackground(Color.black);

        //robots
        img_robot_up_green = new ImageIcon(imagePath + "green/robot_up.gif");
        img_robot_down_green = new ImageIcon(imagePath + "green/robot_down.gif");
        img_robot_left_green = new ImageIcon(imagePath + "green/robot_left.gif");
        img_robot_right_green = new ImageIcon(imagePath + "green/robot_right.gif");

        img_robot_up_yellow = new ImageIcon(imagePath + "yellow/robot_up.gif");
        img_robot_down_yellow = new ImageIcon(imagePath + "yellow/robot_down.gif");
        img_robot_left_yellow = new ImageIcon(imagePath + "yellow/robot_left.gif");
        img_robot_right_yellow = new ImageIcon(imagePath + "yellow/robot_right.gif");

        img_robot_up_red = new ImageIcon(imagePath + "red/robot_up.gif");
        img_robot_down_red = new ImageIcon(imagePath + "red/robot_down.gif");
        img_robot_left_red = new ImageIcon(imagePath + "red/robot_left.gif");
        img_robot_right_red = new ImageIcon(imagePath + "red/robot_right.gif");

        img_robot_up_darkred = new ImageIcon(imagePath + "darkred/robot_up.gif");
        img_robot_down_darkred = new ImageIcon(imagePath + "darkred/robot_down.gif");
        img_robot_left_darkred = new ImageIcon(imagePath + "darkred/robot_left.gif");
        img_robot_right_darkred = new ImageIcon(imagePath + "darkred/robot_right.gif");

        img_robot_up_orange = new ImageIcon(imagePath + "orange/robot_up.gif");
        img_robot_down_orange = new ImageIcon(imagePath + "orange/robot_down.gif");
        img_robot_left_orange = new ImageIcon(imagePath + "orange/robot_left.gif");
        img_robot_right_orange = new ImageIcon(imagePath + "orange/robot_right.gif");

        img_robot_up_cyan = new ImageIcon(imagePath + "cyan/robot_up.gif");
        img_robot_down_cyan = new ImageIcon(imagePath + "cyan/robot_down.gif");
        img_robot_left_cyan = new ImageIcon(imagePath + "cyan/robot_left.gif");
        img_robot_right_cyan = new ImageIcon(imagePath + "cyan/robot_right.gif");

        img_robot_up_gray = new ImageIcon(imagePath + "gray/robot_up.gif");
        img_robot_down_gray = new ImageIcon(imagePath + "gray/robot_down.gif");
        img_robot_left_gray = new ImageIcon(imagePath + "gray/robot_left.gif");
        img_robot_right_gray = new ImageIcon(imagePath + "gray/robot_right.gif");

        img_robot_up_pink = new ImageIcon(imagePath + "pink/robot_up.gif");
        img_robot_down_pink = new ImageIcon(imagePath + "pink/robot_down.gif");
        img_robot_left_pink = new ImageIcon(imagePath + "pink/robot_left.gif");
        img_robot_right_pink = new ImageIcon(imagePath + "pink/robot_right.gif");

        img_robot_up_magenta = new ImageIcon(imagePath + "magenta/robot_up.gif");
        img_robot_down_magenta = new ImageIcon(imagePath + "magenta/robot_down.gif");
        img_robot_left_magenta = new ImageIcon(imagePath + "magenta/robot_left.gif");
        img_robot_right_magenta = new ImageIcon(imagePath + "magenta/robot_right.gif");

        img_robot_up_blue = new ImageIcon(imagePath + "blue/robot_up.gif");
        img_robot_down_blue = new ImageIcon(imagePath + "blue/robot_down.gif");
        img_robot_left_blue = new ImageIcon(imagePath + "blue/robot_left.gif");
        img_robot_right_blue = new ImageIcon(imagePath + "blue/robot_right.gif");

        //...and the rest
        img_taxman_up = new ImageIcon(imagePath + "tax_up.gif");
        img_taxman_down = new ImageIcon(imagePath + "tax_down.gif");
        img_taxman_left = new ImageIcon(imagePath + "tax_left.gif");
        img_taxman_right = new ImageIcon(imagePath + "tax_right.gif");

        img_card_one_blue = new ImageIcon(imagePath + "one_blue.gif");
        img_card_two_blue = new ImageIcon(imagePath + "two_blue.gif");
        img_card_three_blue = new ImageIcon(imagePath + "three_blue.gif");
        img_card_four_blue = new ImageIcon(imagePath + "four_blue.gif");

        img_card_one_green = new ImageIcon(imagePath + "one_green.gif");
        img_card_two_green = new ImageIcon(imagePath + "two_green.gif");
        img_card_three_green = new ImageIcon(imagePath + "three_green.gif");
        img_card_four_green = new ImageIcon(imagePath + "four_green.gif");

        img_card_one_lightgreen = new ImageIcon(imagePath + "one_lightgreen.gif");
        img_card_two_lightgreen = new ImageIcon(imagePath + "two_lightgreen.gif");
        img_card_three_lightgreen = new ImageIcon(imagePath + "three_lightgreen.gif");
        img_card_four_lightgreen = new ImageIcon(imagePath + "four_lightgreen.gif");

        img_card_one_pink = new ImageIcon(imagePath + "one_pink.gif");
        img_card_two_pink = new ImageIcon(imagePath + "two_pink.gif");
        img_card_three_pink = new ImageIcon(imagePath + "three_pink.gif");
        img_card_four_pink = new ImageIcon(imagePath + "four_pink.gif");

        img_card_one_darkpink = new ImageIcon(imagePath + "one_darkpink.gif");
        img_card_two_darkpink = new ImageIcon(imagePath + "two_darkpink.gif");
        img_card_three_darkpink = new ImageIcon(imagePath + "three_darkpink.gif");
        img_card_four_darkpink = new ImageIcon(imagePath + "four_darkpink.gif");

        img_card_one_brown = new ImageIcon(imagePath + "one_brown.gif");
        img_card_two_brown = new ImageIcon(imagePath + "two_brown.gif");
        img_card_three_brown = new ImageIcon(imagePath + "three_brown.gif");
        img_card_four_brown = new ImageIcon(imagePath + "four_brown.gif");

        img_card_one_bluew = new ImageIcon(imagePath + "one_blew.gif");
        img_card_two_bluew = new ImageIcon(imagePath + "two_blew.gif");
        img_card_three_bluew = new ImageIcon(imagePath + "three_blew.gif");
        img_card_four_bluew = new ImageIcon(imagePath + "four_blew.gif");

        img_card_one_lightyellow = new ImageIcon(imagePath + "one_lightyellow.gif");
        img_card_two_lightyellow = new ImageIcon(imagePath + "two_lightyellow.gif");
        img_card_three_lightyellow = new ImageIcon(imagePath + "three_lightyellow.gif");
        img_card_four_lightyellow = new ImageIcon(imagePath + "four_lightyellow.gif");

        img_card_one_red = new ImageIcon(imagePath + "one_red.gif");
        img_card_two_red = new ImageIcon(imagePath + "two_red.gif");
        img_card_three_red = new ImageIcon(imagePath + "three_red.gif");
        img_card_four_red = new ImageIcon(imagePath + "four_red.gif");

        img_card_one_orange = new ImageIcon(imagePath + "one_orange.gif");
        img_card_two_orange = new ImageIcon(imagePath + "two_orange.gif");
        img_card_three_orange = new ImageIcon(imagePath + "three_orange.gif");
        img_card_four_orange = new ImageIcon(imagePath + "four_orange.gif");

        img_card_one_yellow = new ImageIcon(imagePath + "one_yellow.gif");
        img_card_two_yellow = new ImageIcon(imagePath + "two_yellow.gif");
        img_card_three_yellow = new ImageIcon(imagePath + "three_yellow.gif");
        img_card_four_yellow = new ImageIcon(imagePath + "four_yellow.gif");

        setDoubleBuffered(true);
        setLayout(null);

		// add JPanel with the moving items
        arenaField = new ArenaField();
        arenaField.setBounds(10, 10, 1200, 1200);
        add(arenaField);

		// add JPanel with the fix items
        grid = new GridField();
        grid.setBounds(10, 10, 1200, 1200);
        add(grid);
    }

   /**
    * Computes the right walls and saves them with other fix items in an array.
	*
	* @param gameState the gameState-object of the first round
    */

    public void paintFixElements(GameState gameState)
    {
        wall_rechts_oben = new ImageIcon(imagePathWalls + "wall_ro.gif");
        wall_rechts_unten = new ImageIcon(imagePathWalls + "wall_ru.gif");
        wall_links_oben = new ImageIcon(imagePathWalls + "wall_lo.gif");
        wall_links_unten = new ImageIcon(imagePathWalls + "wall_lu.gif");
        wall_mid = new ImageIcon(imagePathWalls + "wall_mid.gif");

        wall_vert = new ImageIcon(imagePathWalls + "wall_vert.gif");
        wall_vert_links = new ImageIcon(imagePathWalls + "wall_vert_links.gif");
        wall_vert_rechts = new ImageIcon(imagePathWalls + "wall_vert_rechts.gif");
        wall_vert_links_bound = new ImageIcon(imagePathWalls + "wall_vert_links_bound.gif");
        wall_vert_rechts_bound = new ImageIcon(imagePathWalls + "wall_vert_rechts_bound.gif");

        wall_hor = new ImageIcon(imagePathWalls + "wall_hor.gif");
        wall_hor_oben = new ImageIcon(imagePathWalls + "wall_hor_oben.gif");
        wall_hor_unten = new ImageIcon(imagePathWalls + "wall_hor_unten.gif");
        wall_hor_oben_bound = new ImageIcon(imagePathWalls + "wall_hor_oben_bound.gif");
        wall_hor_unten_bound = new ImageIcon(imagePathWalls + "wall_hor_unten_bound.gif");

        wall_edge_top = new ImageIcon(imagePathWalls + "wall_edge_top.gif");
        wall_edge_bottom = new ImageIcon(imagePathWalls + "wall_edge_bottom.gif");
        wall_edge_left = new ImageIcon(imagePathWalls + "wall_edge_left.gif");
        wall_edge_right = new ImageIcon(imagePathWalls + "wall_edge_right.gif");

        img_feld = new ImageIcon(imagePathWalls + "feld.gif");

        img_trader = new ImageIcon(imagePath + "trader.gif");
        img_well = new ImageIcon(imagePathWalls + "well.gif");
        img_teleporter = new ImageIcon(imagePathWalls + "teleporter.gif");
        img_exit = new ImageIcon(imagePathWalls + "exit.gif");
        img_fatamorgana = new ImageIcon(imagePath + "fata.gif");

        xSize = gameState.arena.length;
        ySize = gameState.arena[1].length;

		// create a matrix of imageIcons ...
        fixMatrix = new ImageIcon [xSize][ySize];

		// ...and fill it
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                //edges
                if ((gameState.arena[x][y].getItem() instanceof Wall) && x == 0 && y == 0) fixMatrix[x][y] = wall_links_oben;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == xSize - 1 && y == 0) fixMatrix[x][y] = wall_rechts_oben;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == xSize - 1 && y == ySize - 1) fixMatrix[x][y] = wall_rechts_unten;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == 0 && y == ySize - 1) fixMatrix[x][y] = wall_links_unten;

                //top and bottom lines

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x > 0 && y == 0 && x < xSize - 1 && !(gameState.arena[x][y + 1].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_hor_oben;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x > 0 && y == ySize - 1 && x < xSize - 1 && !(gameState.arena[x][y - 1].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_hor_unten;

                // left and right lines

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == 0 && y > 0 && y < ySize - 1 && !(gameState.arena[x + 1][y].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_vert_links;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == xSize - 1 && y > 0 && y < ySize - 1 && !(gameState.arena[x - 1][y].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_vert_rechts;

				// bounds on top and bottom

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x > 0 && y == 0 && x < xSize - 1 && (gameState.arena[x][y + 1].getItem() instanceof Wall)) fixMatrix[x][y] = wall_hor_oben_bound;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x > 0 && y == ySize - 1 && x < xSize - 1 && (gameState.arena[x][y - 1].getItem() instanceof Wall)) fixMatrix[x][y] = wall_hor_unten_bound;

                // bounds left and right

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == 0 && y > 0 && y < ySize - 1 && (gameState.arena[x + 1][y].getItem() instanceof Wall)) fixMatrix[x][y] = wall_vert_links_bound;
                else if ((gameState.arena[x][y].getItem() instanceof Wall) && x == xSize - 1 && y < ySize - 1 && y > 0 && (gameState.arena[x - 1][y].getItem() instanceof Wall)) fixMatrix[x][y] = wall_vert_rechts_bound;

                // edges
                else if ((gameState.arena[x][y].getItem() instanceof Wall ) && !(gameState.arena[x - 1][y].getItem() instanceof Wall ) && !(gameState.arena[x][y + 1].getItem() instanceof Wall ) && !(gameState.arena[x][y - 1].getItem() instanceof Wall )) fixMatrix[x][y] = wall_edge_left;
                else if ((gameState.arena[x][y].getItem() instanceof Wall ) && !(gameState.arena[x - 1][y].getItem() instanceof Wall ) && !(gameState.arena[x + 1][y].getItem() instanceof Wall ) && !(gameState.arena[x][y - 1].getItem() instanceof Wall )) fixMatrix[x][y] = wall_edge_top;


                else if ((gameState.arena[x][y].getItem() instanceof Wall ) && (gameState.arena[x - 1][y].getItem() instanceof Wall ) && (gameState.arena[x + 1][y].getItem() instanceof Wall ) && (gameState.arena[x][y - 1].getItem() instanceof Wall ) && (gameState.arena[x][y + 1].getItem() instanceof Wall )) fixMatrix[x][y] = wall_mid;
                // vertical

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && (gameState.arena[x][y + 1].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_vert;

                // horizontal

                else if ((gameState.arena[x][y].getItem() instanceof Wall) && (gameState.arena[x + 1][y].getItem() instanceof Wall) ) fixMatrix[x][y] = wall_hor;

                // edge down, up, left, right

                else if ((gameState.arena[x][y].getItem() instanceof Wall ) && !(gameState.arena[x - 1][y].getItem() instanceof Wall ) && !(gameState.arena[x + 1][y].getItem() instanceof Wall ) && !(gameState.arena[x][y + 1].getItem() instanceof Wall )) fixMatrix[x][y] = wall_edge_bottom;
                else if ((gameState.arena[x][y].getItem() instanceof Wall ) && !(gameState.arena[x][y - 1].getItem() instanceof Wall ) && !(gameState.arena[x + 1][y].getItem() instanceof Wall ) && !(gameState.arena[x][y + 1].getItem() instanceof Wall )) fixMatrix[x][y] = wall_edge_right;

                // security wall
                else if (gameState.arena[x][y].getItem() instanceof Wall ) fixMatrix[x][y] = wall_mid;

                // ...and the rest
                else if (gameState.arena[x][y].getItem() instanceof Well) fixMatrix[x][y] = img_well;
                else if (gameState.arena[x][y].getItem() instanceof Teleport) fixMatrix[x][y] = img_teleporter;
                else if (gameState.arena[x][y].getItem() instanceof Exit) fixMatrix[x][y] = img_exit;


                else fixMatrix[x][y] = img_feld;

            } //for
        } //for

		// set scrollable area
        setPreferredSize(new Dimension(xSize*MainWindow.H_SIZE + 20, ySize*MainWindow.V_SIZE + 20));

		// make sure that this method is only called once a game
        grid.dontPaintMeAgain = true;
        grid.updateFixMatrix(fixMatrix);
        grid.paintComponent(grid.getGraphics());
        grid.dontPaintMeAgain = false;
        fixElementsWerePainted = true;

    }

	/**
     * Creates a matrix of ImageIcons every round and calls the updateState method in
	 * in ArenaField to paint the new round.
     *
     * @param gameState the GameState-Object contains the actual state of the game
     */
    public void updateState(GameState gameState)
    {
        this.gameState = gameState;
        grid.dontPaintMeAgain = false;

		// check if the background was painted
        if (!fixElementsWerePainted)
        {
            paintFixElements(gameState);
        };

        movingMatrix = new ImageIcon[xSize][ySize];

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {

			// check all items of the gameState-object

			if (gameState.arena[x][y].getItem() instanceof Taxman)
                {
                    Robot actualTaxman = (Robot) gameState.arena[x][y].getItem();
                    int direction = actualTaxman.getDirection();
                    if (direction == 1) movingMatrix[x][y] = img_taxman_up;
                    else if (direction == 4) movingMatrix[x][y] = img_taxman_right;
                    else if (direction == 3) movingMatrix[x][y] = img_taxman_down;
                    else if (direction == 2) movingMatrix[x][y] = img_taxman_left;
                }

                else if (gameState.arena[x][y].getItem() instanceof Trader) movingMatrix[x][y] = img_trader;
                else if (gameState.arena[x][y].getItem() instanceof Fatamorgana) movingMatrix[x][y] = img_fatamorgana;

                else if (gameState.arena[x][y].getItem() instanceof Card)
                {
                    Card actualCard = (Card) gameState.arena[x][y].getItem();
                    int farbe = (actualCard.getColor() % 11) + 1;
                    int wert = actualCard.getValue();


                    if (farbe == 1 && wert == 1)	movingMatrix[x][y] = img_card_one_green;
                    else if (farbe == 1 && wert == 2)	movingMatrix[x][y] = img_card_two_green;
                    else if (farbe == 1 && wert == 3)	movingMatrix[x][y] = img_card_three_green;
                    else if (farbe == 1 && wert == 4)	movingMatrix[x][y] = img_card_four_green;

                    else if (farbe == 2 && wert == 1)	movingMatrix[x][y] = img_card_one_darkpink;
                    else if (farbe == 2 && wert == 2)	movingMatrix[x][y] = img_card_two_darkpink;
                    else if (farbe == 2 && wert == 3)	movingMatrix[x][y] = img_card_three_darkpink;
                    else if (farbe == 2 && wert == 4)	movingMatrix[x][y] = img_card_four_darkpink;

                    else if (farbe == 3 && wert == 1)	movingMatrix[x][y] = img_card_one_blue;
                    else if (farbe == 3 && wert == 2)	movingMatrix[x][y] = img_card_two_blue;
                    else if (farbe == 3 && wert == 3)	movingMatrix[x][y] = img_card_three_blue;
                    else if (farbe == 3 && wert == 4)	movingMatrix[x][y] = img_card_four_blue;

                    else if (farbe == 4 && wert == 1)	movingMatrix[x][y] = img_card_one_yellow;
                    else if (farbe == 4 && wert == 2)	movingMatrix[x][y] = img_card_two_yellow;
                    else if (farbe == 4 && wert == 3)	movingMatrix[x][y] = img_card_three_yellow;
                    else if (farbe == 4 && wert == 4)	movingMatrix[x][y] = img_card_four_yellow;

                    else if (farbe == 5 && wert == 1)	movingMatrix[x][y] = img_card_one_red;
                    else if (farbe == 5 && wert == 2)	movingMatrix[x][y] = img_card_two_red;
                    else if (farbe == 5 && wert == 3)	movingMatrix[x][y] = img_card_three_red;
                    else if (farbe == 5 && wert == 4)	movingMatrix[x][y] = img_card_four_red;

                    else if (farbe == 6 && wert == 1)	movingMatrix[x][y] = img_card_one_orange;
                    else if (farbe == 6 && wert == 2)	movingMatrix[x][y] = img_card_two_orange;
                    else if (farbe == 6 && wert == 3)	movingMatrix[x][y] = img_card_three_orange;
                    else if (farbe == 6 && wert == 4)	movingMatrix[x][y] = img_card_four_orange;

                    else if (farbe == 7 && wert == 1)	movingMatrix[x][y] = img_card_one_brown;
                    else if (farbe == 7 && wert == 2)	movingMatrix[x][y] = img_card_two_brown;
                    else if (farbe == 7 && wert == 3)	movingMatrix[x][y] = img_card_three_brown;
                    else if (farbe == 7 && wert == 4)	movingMatrix[x][y] = img_card_four_brown;

                    else if (farbe == 8 && wert == 1)	movingMatrix[x][y] = img_card_one_bluew;
                    else if (farbe == 8 && wert == 2)	movingMatrix[x][y] = img_card_two_bluew;
                    else if (farbe == 8 && wert == 3)	movingMatrix[x][y] = img_card_three_bluew;
                    else if (farbe == 8 && wert == 4)	movingMatrix[x][y] = img_card_four_bluew;

                    else if (farbe == 9 && wert == 1)	movingMatrix[x][y] = img_card_one_pink;
                    else if (farbe == 9 && wert == 2)	movingMatrix[x][y] = img_card_two_pink;
                    else if (farbe == 9 && wert == 3)	movingMatrix[x][y] = img_card_three_pink;
                    else if (farbe == 9 && wert == 4)	movingMatrix[x][y] = img_card_four_pink;

                    else if (farbe == 10 && wert == 1)	movingMatrix[x][y] = img_card_one_lightyellow;
                    else if (farbe == 10 && wert == 2)	movingMatrix[x][y] = img_card_two_lightyellow;
                    else if (farbe == 10 && wert == 3)	movingMatrix[x][y] = img_card_three_lightyellow;
                    else if (farbe == 10 && wert == 4)	movingMatrix[x][y] = img_card_four_lightyellow;

                    else if (farbe == 11 && wert == 1)	movingMatrix[x][y] = img_card_one_lightgreen;
                    else if (farbe == 11 && wert == 2)	movingMatrix[x][y] = img_card_two_lightgreen;
                    else if (farbe == 11 && wert == 3)	movingMatrix[x][y] = img_card_three_lightgreen;
                    else if (farbe == 11 && wert == 4)	movingMatrix[x][y] = img_card_four_lightgreen;
                }

                else if (gameState.arena[x][y].getItem() instanceof Robot)
                {
                    Robot actualRobot = (Robot) gameState.arena[x][y].getItem();

                    switch (actualRobot.getId() % 9)
                    {
                            case 0:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_blue;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_blue;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_blue;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_blue;
                            break;
                            case 1:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_cyan;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_cyan;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_cyan;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_cyan;
                            break;
                            case 2:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_green;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_green;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_green;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_green;
                            break;
                            case 3:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_magenta;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_magenta;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_magenta;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_magenta;
                            break;
                            case 4:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_orange;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_orange;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_orange;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_orange;
                            break;
                            case 5:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_red;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_red;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_red;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_red;
                            break;
                            case 6:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_yellow;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_yellow;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_yellow;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_yellow;
                            break;
                            case 7:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_pink;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_pink;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_pink;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_pink;
                            break;
                            case 8:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_darkred;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_darkred;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_darkred;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_darkred;
                            break;
                            default:
                            if ( actualRobot.getDirection() == Robot.NORTH )
                                movingMatrix[x][y] = img_robot_up_gray;
                            else if ( actualRobot.getDirection() == Robot.WEST )
                                movingMatrix[x][y] = img_robot_left_gray;
                            else if ( actualRobot.getDirection() == Robot.SOUTH )
                                movingMatrix[x][y] = img_robot_down_gray;
                            else if ( actualRobot.getDirection() == Robot.EAST )
                                movingMatrix[x][y] = img_robot_right_gray;
                            break;
                    }
                }


            }
        }
        arenaField.updateMovingMatrix(movingMatrix);
    }

    /**
    * Destinates the skinNumber which was chosen in the maintoolbar
	*
	* @param int Number of the skin
    */

    public void setSkin(int skin)
    {
        this.skin = skin;
    }


    /**
    *   resets the gameState object
    */

    public void clearDisplay()
    {
        this.gameState = null;
        grid.dontPaintMeAgain = true;
        this.fixElementsWerePainted = false;
        if (grid.offImage != null) grid.offImage.flush();
    }

 /**
  * @serial When true, the paint-method in GridField was called and the
  * fix elements were painted.
  */
    private boolean fixElementsWerePainted = false;

 /**
  * @serial Declaration of the fixMatrix array, which contains the fix items
  */
    private ImageIcon fixMatrix[][];

 /**
  * @serial Declaration of the movingMatrix array, which contains the moving items and
  * is updated every round
  */
    private ImageIcon movingMatrix[][];

/**
 * @serial path declaration
 */
    public String imagePath = "src/de/uni_paderborn/robots/gui/images/";
/**
 * @serial path declaration
 */
    public String imagePathWalls = "src/de/uni_paderborn/robots/gui/images/skins/redhat/";

/**
 * @serial skin declaration
 */
    public int skin;

/**
 * @serial imageSize declaration
 */
    public int imageSize = 20;

/**
 * @serial contains the x-size of the ArenaPane
 */

    private int xSize;

/**
 * @serial contains the y-size of the ArenaPane
 */
    private int ySize;

/**
 * @serial GridField declaration
 */
    private GridField grid;

/**
 * @serial ArenaField declaration
 */
    private ArenaField arenaField;
/**
 * @serial ImageIcon declaration
 */
    private ImageIcon wall_rechts_oben;
/**
 * @serial ImageIcon declaration
 */
    private ImageIcon wall_links_oben;
/**
 * @serial ImageIcon declaration
 */
    private ImageIcon wall_links_unten;
/**
 * @serial ImageIcon declaration
 */
    private ImageIcon wall_rechts_unten;
/**
 * @serial ImageIcon declaration
 */

    private ImageIcon wall_mid;
/**
 * @serial ImageIcon declaration
 */

    private ImageIcon wall_edge_right;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_edge_left;

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_edge_top;

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_edge_bottom;

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_vert;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_vert_links;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_vert_rechts;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_vert_links_bound;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_vert_rechts_bound;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon wall_hor;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_hor_oben;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_hor_unten;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_hor_oben_bound;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_hor_unten_bound;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon wall_left_bound;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_right_bound;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_top_bound;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon wall_bottom_bound;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_well;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_teleporter;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_gray;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_gray;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_gray;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_gray;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_red;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_magenta;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_magenta;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_magenta;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_magenta;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_darkred;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_darkred;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_darkred;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_darkred;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_orange;

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_up_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_blue;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_pink;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_cyan;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_cyan;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_cyan;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_cyan;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_robot_up_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_down_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_left_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_robot_right_green;
	/**
 	* @serial ImageIcon declaration
 	*/



    private ImageIcon img_card_one_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_blue;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_blue;

	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_one_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_green;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_green;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_lightgreen;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_lightgreen;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_lightgreen;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_lightgreen;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_pink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_pink;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_darkpink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_darkpink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_darkpink;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_darkpink;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_brown;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_brown;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_brown;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_brown;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_bluew;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_bluew;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_bluew;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_bluew;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_lightyellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_lightyellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_lightyellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_lightyellow;

	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_red;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_red;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_orange;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_orange;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_card_one_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_two_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_three_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_card_four_yellow;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_feld;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_exit;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_taxman_up;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_taxman_down;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_taxman_left;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_taxman_right;
	/**
 	* @serial ImageIcon declaration
 	*/

    private ImageIcon img_fatamorgana;
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon img_trader;
	/**
 	* @serial GameState declaration
 	*/

    private GameState gameState;


}
