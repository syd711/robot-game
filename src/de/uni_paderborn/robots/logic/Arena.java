package de.uni_paderborn.robots.logic;

import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;
import java.util.*;
import java.util.Random;
import de.uni_paderborn.robots.gui.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.robot.group3.*;

/**
 * Arena is the main class of the game. 
 */
public class Arena implements Runnable
{
    /**
     * The Constructor initializes the Arena.
     *
     * @param gamInit Stores several Objects set in the Configuration-Window
     * 
     */
    public Arena(GameInit gameInit)
    {
        // checks gamInit and robots on valid values
        if (gameInit != null)
            this.init = gameInit;
        else
            this.drawLogMessage("Error in gameInit object");
        if (this.init.robots != null)
            this.robot = this.init.robots;
        else
            this.drawLogMessage("Init error in HashSet robot");
        // free stores a checksum over all fields
        this.free = this.init.noOfFieldsX * this.init.noOfFieldsY;
        this.setMaxEnergy(this.init.maxEnergy);

        // setting color ID`s to the robots
        int i = 1;
        Iterator tmpIter = this.iteratorOfRobot();
        while (tmpIter.hasNext())
        {
            Robot tmpBot = (Robot)tmpIter.next();
            tmpBot.setID(i);
            i++;
        }

        initGameField();
    }

    /**
     * Controls several conditions (Start, Stop, Pause etc.)
     */
    private void arenaControl()
    {
        // Stops the arena while pauseGame is set
        while (getPause())
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                this.drawLogMessage("Sleep error");
            }

        }
        // deletes all disqualified robots
        this.deleteDisqualifiedRobots();

        // checks if a roboter took the exit
        if (getExit())
        {
            this.calculateWinner();
            this.stop();
            return ;
        }

        // checks the stop-button and stops the arena
        if (getStop())
        {
            this.drawLogMessage("Game stopped!!\n");
            this.stop();
            return ;
        }
        // checks the HashSet robot for an active robot
        if (!this.checkActivityBots())
        {
            if (this.sizeOfRobot() == 0)
            {
                //there is no roboter in the HashSet robot. Game stopped.
                this.drawLogMessage("No roboter in the game.\nGame stopped!\n");
                this.stop();
                return ;
            }
            else
            {
                // there is no active robot. Game stopped.
                this.drawLogMessage("There is no active roboter.\nGame stopped!!\n");
                this.calculateWinner();
                this.stop();
                return ;
            }


        }
        return ;
    }


    /**
     * Prints a message on the log.
     *
     * @param text Stores the message
     */
    public void drawLogMessage(String text)
    {
        // calls method logMessage in Mainwindow and write text on the log
        this.init.mainWindow.logMessage(text);
    }


    /**
     * Starts the Arena-Thread
     */
    public void run ()
    {
        this.oneTurn();
    }



    /**
     * Adds the robot to the HashSet robot
     *
     * @param robot The roboter who will be added to HashSet robot
     */
    public void addToRobot (Robot elem)
    {
        if ((elem != null) && !this.hasInRobot (elem))
        {
            if (this.robot == null)
            {
                // Create a new container for the robot association.
                this.robot = new HashSet ();

            }  // if

            this.robot.add (elem);
            elem.setArena (this);

        }  // if

    }


    /**
     * Returns true if robot member of HashSet robot
     *
     * @param elem Holds the Roboter-Object 
     * @return boolean
     */
    public boolean hasInRobot (Robot elem)
    {
        return ((this.robot == null)
                ? false
                : this.robot.contains (elem));
    }


    /**
     * Returns an Iterator for HashSet robot
     *
     * @return An Iterator of HashSet robot
     */
    public Iterator iteratorOfRobot ()
    {
        if (this.robot != null)
            return this.robot.iterator ();
        else
        {
            this.robot = new HashSet();
            return this.robot.iterator();
        }
    }

    /**
     * Removes elem from HashSet robot
     *
     * @param elem 
     */
    public void removeFromRobot (Robot elem)
    {
        if (this.hasInRobot (elem))
        {
            this.robot.remove (elem);
            elem.setArena (null);
        }  // if
    }


    /**
     * Returns the number of roboter in the game
     *
     * @return Number of robots running
     */
    public int sizeOfRobot ()
    {
        return ((this.robot == null)
                ? 0
                : this.robot.size ());
    }


    /**
     * Removes the disqualified robots from HashSet robot
     *
     */
    public void removeAllFromRobot ()
    {
        Robot tmpRobot;
        Iterator iter = iteratorOfRobot ();

        while (iter.hasNext())
        {
            try
            {
                tmpRobot = (Robot) iter.next ();
            }

            catch (ClassCastException e)
            {
                this.drawLogMessage("Remove error in HashSet robot\n");
                return ;
            }

            iter.remove ();
            tmpRobot.setArena (null);
        }  // while


    }


    /**
     * Deletes the arena-object
     *
     */
    public void removeYou ()
    {
        removeAllFromRobot();
        this.stop();
    }

    /**
      * Valuates the cards the roboter owns
      *
      * @param iterator Iterator of the robots portable
      * @return The value  
      */
    public int valuateCards (Iterator iterator)
    {
        int currentColor = 0;
        int currentValue = 0;
        int currentValueTmp = 0;
        int colorCounter = 0;
        int sumValue = 0;
        int points_of_robot = 0;
        int [][] arrayOfCards = new int [5][2];


        if (iterator != null)
        {
            // fills the array [default color][null] up
            for (int i = 0; i < arrayOfCards.length; i++)
            {
                arrayOfCards[i][0] = 0;
            }

            // fills the array [x = current color][y = value of current color x] up
            int m = 0;
            while (iterator.hasNext() && m < 5)
            {
                try
                {
                    Card tmpCard = (Card) iterator.next();
                    arrayOfCards[m][0] = tmpCard.getColor();
                    arrayOfCards[m][1] = tmpCard.getValue();
                    m++;
                }
                catch (ClassCastException e)
                {
                    this.drawLogMessage("Cast error in valutateCards");
                }


            }// while

            // find out the points of the cards the roboter owns
            for (int j = 0; j < arrayOfCards.length; j++)
            {
                currentColor = arrayOfCards[j][0];
                currentValue = arrayOfCards[j][1];
                colorCounter = 1;
                sumValue = 0;

                if (currentColor != 0)
                {
                    // counts the cards of one color
                    for (int i = j + 1; i < arrayOfCards.length; i++)
                    {

                        //sumValue = currentValue;
                        currentValueTmp = arrayOfCards[i][1];

                        if (currentColor == arrayOfCards[i][0])
                        {
                            colorCounter = colorCounter + 1;
                            sumValue = sumValue + currentValueTmp;
                            arrayOfCards[i][0] = 0;
                        }//if
                    }// for


                    if (colorCounter == 4)
                    {
                        // for four cards of one color 500 points + cardvalue (1,...,4)
                        points_of_robot = 500 + points_of_robot + sumValue + currentValue;

                    }
                    if (colorCounter == 3)
                    {
                        // for three cards of one color 300 points + cardvalue (1,...,4)
                        points_of_robot = 300 + points_of_robot + sumValue + currentValue;

                    }
                    if (colorCounter == 2)
                    {
                        // for two cards of one color 100 points + cardvalue (1,...,4)
                        points_of_robot = 100 + points_of_robot + sumValue + currentValue;

                    }
                    if (colorCounter == 1)
                    {
                        // for one card 10 points + cardvalue (1,...,4)
                        points_of_robot = points_of_robot + currentValue;
                    }// if
                }// if
            }// for


            return points_of_robot;
        }// if
        else
        {
            this.drawLogMessage("Error in valuateCards, iterator = null\n");
            return points_of_robot;
        }

    }// valuateCards



    /**
     * Returns the Item in front of the robot
     *
     * @param The robot who wants to know the item in front
     * @return The item in front
     */
    public Item getItemInFrontOfRobot(Robot robot)
    {
        // return the item in front of the robot
        // depending on its direction
        if (robot.getDirection() == Robot.EAST)
            return robot.getField().getRight().getItem();
        else if (robot.getDirection() == Robot.WEST)
            return robot.getField().getLeft().getItem();
        else if (robot.getDirection() == Robot.NORTH)
            return robot.getField().getUp().getItem();
        else
            return robot.getField().getDown().getItem();
    }// getItemInFrontOfRobot


    /**
    * Calls the HighScoreWindow and shows the winner.
    *
    */
    private void calculateWinner()
    {
        // Removes the taxman - robots from robot
        HashSet tmpHash = new HashSet();
        Iterator tmpIterator = this.iteratorOfRobot();

        while (tmpIterator.hasNext())
        {
            Robot tmpBot = (Robot) tmpIterator.next();
            if (!(tmpBot instanceof Taxman))
                tmpHash.add(tmpBot);
        }

        Iterator iterator = tmpHash.iterator();
        int count = tmpHash.size();
        String [] name = new String [count];
        int [][] score = new int [count][2];
        String [][] highScore = new String [count] [3];
        Robot tmpRobot = null;

        // fills the temp arrays with [name of robot][reached points]
        for (int i = 0; iterator.hasNext() && i < name.length; i++)
        {
            try
            {
                tmpRobot = (Robot) iterator.next();
                name [i] = tmpRobot.getName();
                score[i][0] = this.valuateCards(tmpRobot.iteratorOfPortable());
                score[i][1] = i;
            }
            catch (ClassCastException e)
            {
                this.drawLogMessage("Cast error in calculateWinner()");
            }
        }

        // sorts the robots by ranking
        for (int i = 0; i < score.length; i++)
        {
            int current = score [i][0];
            int currentI = score [i][1];

            for (int y = i + 1; y < score.length; y++)
            {
                if (current < score [y][0])
                {
                    int tmpScore = current;
                    int tmpI = currentI;
                    current = score[y][0];
                    currentI = score[y][1];
                    score[y][0] = tmpScore;
                    score[y][1] = tmpI;
                }

            }

            highScore [i] [2] = "" + current;
            if (i > 0 && (highScore[i] [2]).equals(highScore [i - 1] [2]))
                highScore [i] [0] = highScore [i - 1][0];
            else
                highScore [i] [0] = "" + (i + 1);
            highScore [i] [1] = name [currentI];

        }
        // calls the HighScore-Window
        this.init.mainWindow.displayWinner( new GameEnd(highScore));
    }// calculateWinner



    /**
     * Initializes the game-array 
     *
     */
    private void initGameField()
    {
        gameField = new Field[init.noOfFieldsX][init.noOfFieldsY];


        for (int x = 0; x < gameField.length; x++)
        {
            for (int y = 0; y < gameField [x].length; y++)
            {
                gameField [x] [y] = new Field();
                ((Field) gameField [x] [y]).setArena(this);
            }
        }

        //linking the gamefields

        for (int i = 0; i < gameField.length; i++)
        {
            for (int j = 0; j < gameField[i].length; j++)
            {
                try
                {
                    gameField[i][j].setLeft(gameField[i - 1][j]);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {}
                ;

                try
                {
                    gameField[i][j].setRight(gameField[i + 1][j]);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {}
                ;

                try
                {
                    gameField[i][j].setUp(gameField[i][j - 1]);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {}
                ;

                try
                {
                    gameField[i][j].setDown(gameField[i][j + 1]);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {}
                ;


            }
        }


        //wall-settings at the edge of arena

        for (int i = 0; i < init.noOfFieldsX; i++)
        {
            gameField[i][0].setItem(new Wall());
            gameField[i][0].getItem().setName("Wall");
            gameField[i][init.noOfFieldsY - 1].setItem(new Wall());
            gameField[i][init.noOfFieldsY - 1].getItem().setName("Wall");
            this.free = this.free - 2;
        }

        for (int i = 1; i < init.noOfFieldsY - 1; i++)
        {
            gameField[0][i].setItem(new Wall());
            gameField[0][i].getItem().setName("Wall");
            gameField[init.noOfFieldsX - 1][i].setItem(new Wall());
            gameField[init.noOfFieldsX - 1][i].getItem().setName("Wall");
            this.free = this.free - 2;
        }

        //wall-settings in the arena

        try
        {
            int xTwo = (int) Math.floor((init.noOfFieldsX) / 4);
            int yTwo = (int) Math.floor((init.noOfFieldsY) / 4);
            int xOne = (init.noOfFieldsX - 1) - xTwo;
            int yOne = (init.noOfFieldsY - 1) - yTwo;

            for (int i = 1; i < (xTwo - 1); i++)
            {
                gameField[i][yTwo].setItem(new Wall());
                gameField[i][yTwo].getItem().setName("Wall");
                gameField[i][yOne].setItem(new Wall());
                gameField[i][yOne].getItem().setName("Wall");
                gameField[(init.noOfFieldsX - 1)-i][yOne].setItem(new Wall());
                gameField[(init.noOfFieldsX - 1)-i][yOne].getItem().setName("Wall");
                gameField[(init.noOfFieldsX - 1)-i][yTwo].setItem(new Wall());
                gameField[(init.noOfFieldsX - 1)-i][yTwo].getItem().setName("Wall");
                this.free = this.free - 4;
            }

            for (int i = 1; i < (yTwo - 1); i++)
	    {
	    	gameField[xTwo][i].setItem(new Wall());
                gameField[xTwo][i].getItem().setName("Wall");
                gameField[xOne][i].setItem(new Wall());
                gameField[xOne][i].getItem().setName("Wall");
                gameField[xTwo][(init.noOfFieldsY - 1)-i].setItem(new Wall());
                gameField[xTwo][(init.noOfFieldsY - 1)-i].getItem().setName("Wall");
                gameField[xOne][(init.noOfFieldsY - 1)-i].setItem(new Wall());
                gameField[xOne][(init.noOfFieldsY - 1)-i].getItem().setName("Wall");
                this.free = this.free - 4;            
	    }
	    
	    
	    int xHalf = (int) Math.floor((init.noOfFieldsX) / 2);
            int yHalf = (int) Math.floor((init.noOfFieldsY) / 2);
            int counterHalf = (int) Math.floor((init.noOfFieldsX) / 5);

            gameField[xHalf][yHalf].setItem(new Wall());
            gameField[xHalf][yHalf].getItem().setName("Wall");
            this.free = this.free - 1;

            for (int i = 1; i < counterHalf; i++)
            {
                gameField[xHalf - i][yHalf].setItem(new Wall());
                gameField[xHalf - i][yHalf].getItem().setName("Wall");
                gameField[xHalf + i][yHalf].setItem(new Wall());
                gameField[xHalf + i][yHalf].getItem().setName("Wall");
                gameField[xHalf][yHalf - i].setItem(new Wall());
                gameField[xHalf][yHalf - i].getItem().setName("Wall");
                gameField[xHalf][yHalf + i].setItem(new Wall());
                gameField[xHalf][yHalf + i].getItem().setName("Wall");
                this.free = this.free - 4;
            }

            int tmpXplus = xHalf + counterHalf + 1;
            int tmpXminus = xHalf - counterHalf - 1;
            int tmpYplus = yHalf + counterHalf + 1;
            int tmpYminus = yHalf - counterHalf - 1;

            while (tmpXplus < init.noOfFieldsX)
            {
                if (tmpXplus < init.noOfFieldsX)
                {
                    gameField[tmpXplus][yHalf].setItem(new Wall());
                    gameField[tmpXplus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXplus++;
                }
                if (tmpXplus < init.noOfFieldsX)
                {
                    gameField[tmpXplus][yHalf].setItem(new Wall());
                    gameField[tmpXplus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXplus++;
                }
                if (tmpXplus < init.noOfFieldsX)
                {
                    gameField[tmpXplus][yHalf].setItem(new Wall());
                    gameField[tmpXplus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXplus = tmpXplus + 2;
                }
            }

            while (tmpXminus > 0)
            {
                if (tmpXminus > 0)
                {
                    gameField[tmpXminus][yHalf].setItem(new Wall());
                    gameField[tmpXminus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXminus--;
                }
                if (tmpXminus > 0)
                {
                    gameField[tmpXminus][yHalf].setItem(new Wall());
                    gameField[tmpXminus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXminus--;
                }
                if (tmpXminus > 0)
                {
                    gameField[tmpXminus][yHalf].setItem(new Wall());
                    gameField[tmpXminus][yHalf].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpXminus = tmpXminus - 2;
                }
            }

            while (tmpYplus < init.noOfFieldsY)
            {
                if (tmpYplus < init.noOfFieldsY)
                {
                    gameField[xHalf][tmpYplus].setItem(new Wall());
                    gameField[xHalf][tmpYplus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYplus++;
                }
                if (tmpYplus < init.noOfFieldsY)
                {
                    gameField[xHalf][tmpYplus].setItem(new Wall());
                    gameField[xHalf][tmpYplus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYplus++;
                }
                if (tmpYplus < init.noOfFieldsY)
                {
                    gameField[xHalf][tmpYplus].setItem(new Wall());
                    gameField[xHalf][tmpYplus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYplus = tmpYplus + 2;
                }
            }

            while (tmpYminus > 0)
            {
                if (tmpYminus > 0)
                {
                    gameField[xHalf][tmpYminus].setItem(new Wall());
                    gameField[xHalf][tmpYminus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYminus--;
                }
                if (tmpYminus > 0)
                {
                    gameField[xHalf][tmpYminus].setItem(new Wall());
                    gameField[xHalf][tmpYminus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYminus--;
                }
                if (tmpYminus > 0)
                {
                    gameField[xHalf][tmpYminus].setItem(new Wall());
                    gameField[xHalf][tmpYminus].getItem().setName("Wall");
                    this.free = this.free - 1;
		    tmpYminus = tmpYminus - 2;
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {}


        //random well-settings
        if (this.free-init.noOfWells > 0 && init.noOfWells > 0)
        {
            for (int i = 1; i <= init.noOfWells; i++)
            {
                Well well = new Well();
                well.setName("Well " + i);
                this.setRandom (well);
                this.free = this.free - 1;
	    }
        }// if


        //random fatamorgana-setting
        if (this.free-init.noOfFatamorganas > 0 && init.noOfFatamorganas > 0)
        {
            for (int i = 1; i <= init.noOfFatamorganas; i++)
            {
                Fatamorgana fatamorgana = new Fatamorgana();
                fatamorgana.setName("Fatamorgana " + i);
                this.setRandom(fatamorgana);
                this.free = this.free - 1;
	    }
        }// if

        //random trader-setting
        if (this.free-init.noOfTraders > 0 && init.noOfTraders > 0)
        {
            for (int i = 1; i <= init.noOfTraders; i++)
            {
                Trader trader = new Trader();
                trader.setName("Trader " + i);
                this.setRandom(trader);
                this.free = this.free - 1;
	    }
        }// if


        //random Teleport-settings
        if (this.free-init.noOfTeleporters > 0 && init.noOfTeleporters > 0)
        {
            for (int i = 1; i <= init.noOfTeleporters; i++)
            {
                Teleport eins = new Teleport();
                eins.setName("Set " + i);

                Teleport zwei = new Teleport();
                zwei.setName("Set " + i);

                eins.setTeleport(zwei);
                zwei.setTeleport(eins);

                this.setRandom(eins);
                this.setRandom(zwei);
                this.free = this.free - 1;
            }// for
        }// if


        //random Exit-settings


        Exit exit = new Exit();
        exit.setName("Exit");
        this.setRandom(exit);




        //random Card-settings

        if (this.free > 0 && this.sizeOfRobot() > 0)
        {
            int colors = this.sizeOfRobot();
            colors = (int) Math.floor((colors / 4) + colors);

            for (int i = 1; i <= colors; i++)
            {
                for (int x = 1; x <= 4; x++)
                {
                    Card tmpCard = new Card(i, x);
                    tmpCard.setName("Card c:" + i + " v:" + x);
                    this.setRandom(tmpCard);
                    this.free = this.free - 1;
		}// for
            } // for


            //random taxman-setting
            if (this.free-init.noOfTaxmans > 0 && init.noOfTaxmans > 0)
            {
                for (int i = 1; i <= init.noOfTaxmans; i++)
                {
                    Taxman taxman = new Taxman();
                    taxman.setName("Taxman " + i);
                    this.setRandom(taxman);
                    this.addToRobot(taxman);
                    this.free = this.free - 1;
		}
            }// if


            //random Robot-settings
            int unKnown = 1;
            Iterator robotIterator = this.iteratorOfRobot();
            while (this.free > 0 && robotIterator.hasNext())
            {
                try
                {
                    Robot tmpBot = (Robot) robotIterator.next();
                    // gives all unknown roboters a name
                    if (tmpBot.getName() == "")
                    {
                        tmpBot.setName("The Unknown Nr. " + unKnown);
                        unKnown++;
                    }

                    tmpBot.setArena(this);
                    tmpBot.setDirection(1);
                    tmpBot.setEnergy(this.getMaxEnergy());
                    this.setRandom(tmpBot);
                    this.free = this.free - 1;                 
		}

                catch (ClassCastException e)
                {
                    this.drawLogMessage("Cast error in initGameField()");
                }

            }//  while

        }//  if

    }// initGameField





    /**
     * Pauses/Starts the game.
     * @param True pauses the game, false restarts the game
     */
    public synchronized void setPause(boolean pause)
    {
        this.pauseGame = pause;
    }


    /**
     * Returns the value of pauseGame
     *
     * @return Pause - Status of the game (false running, true stopped)
     */
    public synchronized boolean getPause()
    {
        return this.pauseGame;
    }


    /**
     * Sets exitGame. Finally stops the game.
     */
    public void setExit()
    {
        this.exitGame = true;
    }


    /**
     * Returns the value of exitGame
     *
     * @return True game stopped, false game still running
     */
    public boolean getExit()
    {
        return this.exitGame;
    }


    /**
     * Stops the game
     *
     */
    public synchronized void setStop()
    {
        this.stopGame = true;
    }


    /**
     * Returns the value of stopGame
     *
     * @return A boolean
     */
    public synchronized boolean getStop()
    {
        return this.stopGame;
    }


    /**
     * Controls the energy of all robots. Return true if energy of one robot > 0
     *
     * @return True if one roboter active
     */
    private boolean checkActivityBots()
    {
        boolean oneRobotActive = false;

        if (this.sizeOfRobot() > 0)
        {
            Iterator iterator = null;
            // Link iterator to robot
            iterator = this.iteratorOfRobot();
            while (iterator.hasNext())
            {

                Robot tmpBot = (Robot) iterator.next();

                // Check all robots for Energy > 0
                if (!(tmpBot instanceof Taxman) && tmpBot.getEnergy() > 0)
                {
                    oneRobotActive = true;
                    return oneRobotActive;
                }
            }

        }
        return oneRobotActive;
    }// checkActivityBots

    /**
     * Initializes the trading of cards
     * @throws NullPointerException, ClassCastException, NoSuchElementException (throwed by the trading robots)
     */
    private void trade()
    {
        Iterator robotIter = null;
        Robot currentBot = null;
        Robot currentPartner = null;
        Field currentBotField = null;
        Card one = null;
        Card two = null;
        Card tmpCard = null;
        HashSet tBots = null;
        boolean throwed = false;
        robotIter = this.iteratorOfRobot();
        tBots = new HashSet();



        while (robotIter.hasNext())
        {
            try
            {
                currentBot = (Robot)robotIter.next();
                currentBotField = currentBot.getField();
                // Avoid taxmen from trading
                if (!(currentBot instanceof Taxman) && currentBot.sizeOfPortable() > 0)
                {
                    if (currentBotField.getUp() != null && currentBotField.getUp().getItem() instanceof Robot)
                    {
                        currentPartner = (Robot) currentBotField.getUp().getItem();
                        // if Partner is a taxman, currentBot has to pay the tax. Otherwise the two robots trade.
                        if (!(currentPartner instanceof Taxman))
                        {
                            if (currentPartner.sizeOfPortable() > 0 && (!tBots.contains(currentBot) || !tBots.contains(currentPartner)))
                            {
                                try
                                {
                                    one = currentBot.exchangeProposal();

                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentBot.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned CCE");
                                    throwed = true;
                                }

                                try
                                {
                                    two = currentPartner.exchangeProposal();
                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentPartner.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned CCE");
                                    throwed = true;
                                }

                                if (!tBots.contains(currentBot))
                                        tBots.add(currentBot);
                                    if (!tBots.contains(currentPartner))
                                        tBots.add(currentPartner);
								
                                if ( !throwed && currentBot.exchangeOK(one, two) && currentPartner.exchangeOK(two, one))
                                {
                                    currentBot.removeFromPortable(one);
                                    currentPartner.removeFromPortable(two);
                                    currentBot.addToPortable(two);
                                    currentPartner.addToPortable(one);
                                    
                                    this.drawLogMessage( currentBot.getName() + " traded with " + currentPartner.getName() + "\n");
                                    this.drawLogMessage( currentBot.getName() + " got " + two.getName() + "\n");
                                    this.drawLogMessage( currentPartner.getName() + " got " + one.getName() + "\n");
                                }  // if
                                else this.drawLogMessage(currentBot.getName() + " can not agree with " + currentPartner.getName() + "\n");
				throwed = false;
                            } // if
                        }// if


                        else
                        {
                            try
                            {
                                tmpCard = currentBot.exchangeProposal();

                            }
                            catch (NullPointerException e)
                            {
                                this.drawLogMessage( currentBot.getName() + " returned NPE");
                                throwed = true;
                            }
                            catch (NoSuchElementException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned NSE");
                                throwed = true;
                            }
                            catch (ClassCastException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned CCE");
                                throwed = true;
                            }

                            if (!throwed && tmpCard != null)
                            {
                                currentBot.removeFromPortable(tmpCard);
                                this.setRandom(tmpCard);
                                this.drawLogMessage(currentBot.getName() + " paid his tax (" + tmpCard.getName() + ")\n");
                            }// if
                            throwed = false;
                        }// else

                    }  // if




                    if (currentBotField.getLeft() != null && currentBotField.getLeft().getItem() instanceof Robot)
                    {
                        currentPartner = (Robot) currentBotField.getLeft().getItem();
                        // if Partner is a taxman, currentBot has to pay the tax. Otherwise the two robots trade.
                        if (!(currentPartner instanceof Taxman))
                        {
                            if (currentPartner.sizeOfPortable() > 0 && (!tBots.contains(currentBot) || !tBots.contains(currentPartner)))
                            {
                                try
                                {
                                    one = currentBot.exchangeProposal();

                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentBot.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned CCE");
                                    throwed = true;
                                }

                                try
                                {
                                    two = currentPartner.exchangeProposal();
                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentPartner.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned CCE");
                                    throwed = true;
                                }

                                if (!tBots.contains(currentBot))
                                        tBots.add(currentBot);
                                    if (!tBots.contains(currentPartner))
                                        tBots.add(currentPartner);
				
				if ( !throwed && currentBot.exchangeOK(one, two) && currentPartner.exchangeOK(two, one))
                                {
                                    currentBot.removeFromPortable(one);
                                    currentPartner.removeFromPortable(two);
                                    currentBot.addToPortable(two);
                                    currentPartner.addToPortable(one);

                                    
                                    this.drawLogMessage( currentBot.getName() + " traded with " + currentPartner.getName() + "\n");
                                    this.drawLogMessage( currentBot.getName() + " got " + two.getName() + "\n");
                                    this.drawLogMessage( currentPartner.getName() + " got " + one.getName() + "\n");

                                }  // if
                                
				else this.drawLogMessage(currentBot.getName() + " can not agree with " + currentPartner.getName() + "\n");
				throwed = false;
                            }  // if
                        }// if


                        else
                        {

                            try
                            {
                                tmpCard = currentBot.exchangeProposal();

                            }
                            catch (NullPointerException e)
                            {
                                this.drawLogMessage( currentBot.getName() + " returned NPE");
                                throwed = true;
                            }
                            catch (NoSuchElementException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned NSE");
                                throwed = true;
                            }
                            catch (ClassCastException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned CCE");
                                throwed = true;
                            }

                            if (!throwed && tmpCard != null)
                            {
                                currentBot.removeFromPortable(tmpCard);
                                this.setRandom(tmpCard);
                                this.drawLogMessage(currentBot.getName() + " paid his tax (" + tmpCard.getName() + ")\n");
                            }// if
                            throwed = false;
			}



                    }  // if

                    if (currentBotField.getDown() != null && currentBotField.getDown().getItem() instanceof Robot)
                    {
                        currentPartner = (Robot) currentBotField.getDown().getItem();
                        // if Partner is a taxman, currentBot has to pay the tax. Otherwise the two robots trade.
                        if (!(currentPartner instanceof Taxman))
                        {
                            if (currentPartner.sizeOfPortable() > 0 && (!tBots.contains(currentBot) || !tBots.contains(currentPartner)))
                            {
                                try
                                {
                                    one = currentBot.exchangeProposal();

                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentBot.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned CCE");
                                    throwed = true;
                                }

                                try
                                {
                                    two = currentPartner.exchangeProposal();
                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentPartner.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned CCE");
                                    throwed = true;
                                }

                                if (!tBots.contains(currentBot))
                                        tBots.add(currentBot);
                                if (!tBots.contains(currentPartner))
                                        tBots.add(currentPartner);
								
                                if ( !throwed && currentBot.exchangeOK(one, two) && currentPartner.exchangeOK(two, one))
                                {
                                    currentBot.removeFromPortable(one);
                                    currentPartner.removeFromPortable(two);
                                    currentBot.addToPortable(two);
                                    currentPartner.addToPortable(one);

                                    
                                    this.drawLogMessage( currentBot.getName() + " traded with " + currentPartner.getName() + "\n");
                                    this.drawLogMessage( currentBot.getName() + " got " + two.getName() + "\n");
                                    this.drawLogMessage( currentPartner.getName() + " got " + one.getName() + "\n");

                                }  // if
                                else this.drawLogMessage(currentBot.getName() + " can not agree with " + currentPartner.getName() + "\n");
				throwed = false;
			    }  // if
                        }  // if


                        else
                        {
                            try
                            {
                                tmpCard = currentBot.exchangeProposal();

                            }
                            catch (NullPointerException e)
                            {
                                this.drawLogMessage( currentBot.getName() + " returned NPE");
                                throwed = true;
                            }
                            catch (NoSuchElementException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned NSE");
                                throwed = true;
                            }
                            catch (ClassCastException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned CCE");
                                throwed = true;
                            }

                            if (!throwed && tmpCard != null)
                            {
                                currentBot.removeFromPortable(tmpCard);
                                this.setRandom(tmpCard);
                                this.drawLogMessage(currentBot.getName() + " paid his tax (" + tmpCard.getName() + ")\n");
                            }// if
                            throwed = false;
                        }
                    }  // if

                    if (currentBotField.getRight() != null && currentBotField.getRight().getItem() instanceof Robot)
                    {
                        currentPartner = (Robot) currentBotField.getRight().getItem();
                        // if Partner is a taxman, currentBot has to pay the tax. Otherwise the two robots trade.
                        if (!(currentPartner instanceof Taxman))
                        {
                            if (currentPartner.sizeOfPortable() > 0 && (!tBots.contains(currentBot) || !tBots.contains(currentPartner)))
                            {
                                try
                                {
                                    one = currentBot.exchangeProposal();

                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentBot.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentBot.getName() + " returned CCE");
                                    throwed = true;
                                }

                                try
                                {
                                    two = currentPartner.exchangeProposal();
                                }
                                catch (NullPointerException e)
                                {
                                    this.drawLogMessage( currentPartner.getName() + " returned NPE");
                                    throwed = true;
                                }
                                catch (NoSuchElementException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned NSE");
                                    throwed = true;
                                }
                                catch (ClassCastException e)
                                {
                                    this.drawLogMessage(currentPartner.getName() + " returned CCE");
                                    throwed = true;
                                }

                                if (!tBots.contains(currentBot))
                                        tBots.add(currentBot);
                                if (!tBots.contains(currentPartner))
                                        tBots.add(currentPartner);
				
                                if ( !throwed && currentBot.exchangeOK(one, two) && currentPartner.exchangeOK(two, one))
                                {
                                    currentBot.removeFromPortable(one);
                                    currentPartner.removeFromPortable(two);
                                    currentBot.addToPortable(two);
                                    currentPartner.addToPortable(one);

                                    
                                    this.drawLogMessage( currentBot.getName() + " traded with " + currentPartner.getName() + "\n");
                                    this.drawLogMessage( currentBot.getName() + " got " + two.getName() + "\n");
                                    this.drawLogMessage( currentPartner.getName() + " got " + one.getName() + "\n");

                                }  // if
                                else this.drawLogMessage(currentBot.getName() + " can not agree with " + currentPartner.getName() + "\n");
				throwed = false;
			    }  // if
                       }// if


                        else
                        {
                            try
                            {
                                tmpCard = currentBot.exchangeProposal();

                            }
                            catch (NullPointerException e)
                            {
                                this.drawLogMessage( currentBot.getName() + " returned NPE");
                                throwed = true;
                            }
                            catch (NoSuchElementException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned NSE");
                                throwed = true;
                            }
                            catch (ClassCastException e)
                            {
                                this.drawLogMessage(currentBot.getName() + " returned CCE");
                                throwed = true;
                            }

                            if (!throwed && tmpCard != null)
                            {
                                currentBot.removeFromPortable(tmpCard);
                                this.setRandom(tmpCard);
                                this.drawLogMessage(currentBot.getName() + " paid his tax (" + tmpCard.getName() + ")\n");
                            }// if
                            throwed = false;
                        }// else
                    } // if
                }//if
            }//try

            catch (Exception e)
            {}



        }// while
        return ;


    } // trade


    /**
     * Stops the arena - thread
     */
    public void stop()
    {
        this.stop = true;
    }


    /**
     * Returns the ID of the current field
     *
     * @param Get the ID for this robots current field
     * @return An ID
     */
    public int getIdOfCurrentField(Robot robot)
    {
        return robot.getField().getId();
    }


    /**
     * Returns the ID of the field in front of the robot
     *
     * @param robot Get the ID of the field in front of this robot
     * @return int An ID
     */
    public int getIdOfFieldInFront(Robot robot)
    {
        if (robot.getDirection() == robot.NORTH)
            return robot.getField().getUp().getId();
        else if (robot.getDirection() == robot.SOUTH)
            return robot.getField().getDown().getId();
        else if (robot.getDirection() == robot.EAST)
            return robot.getField().getRight().getId();
        else
            return robot.getField().getLeft().getId();
    }

    /**
     * Updates the state- and game-panel
     */
    private void drawState()
    {
        this.init.mainWindow.updateState(new GameState((HashSet) robot, gameField));
    }


    /**
     * Starts one round of the game
     * @throws NullPointerException, ClassCastException (throwed by the actual robot)
     */
    private void oneTurn()
    {
        boolean throwed = false;

        while (!this.stop)
        {
            // Active waiting while pauseGame is true
            while (getPause())
            {
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    this.drawLogMessage("Sleep error");
                }
            }// while

            if (!getStop())
            {
                // Initializes the trading of cards
                this.trade();

                Robot current = null;
                Iterator robotIterator = null;
                AbstractAction robotAction = null;

                counter ++;
                this.drawLogMessage("\nRound " + counter + "\n");

                robotIterator = this.iteratorOfRobot();

                // This loop asks all robots for an action
                while (robotIterator.hasNext())
                {
                    try
                    {
                        // Next robot
                        current = (Robot) robotIterator.next();
                    }

                    catch (ClassCastException e)
                    {
                        // Draw an error message on the log and stop the game
                        this.drawLogMessage(" Wrong element  in HashSet robot \n");
                        return ;
                    }

                    if (!this.hasInDisqualifiedRobots(current) && current.getEnergy() > 0)
                    {
                        // draw Message on the log
                        this.drawLogMessage ("\nQuiet please!\n" + current.getName() + " is thinking !!\n");
                        // start timer
                        this.init.mainWindow.startTimer(current);
                        // ask current robot for command
                        try
                        {
                            robotAction = current.getCommand();
                        }// try
                        catch (Exception e)
                        {
                            this.drawLogMessage(current.getName() + " was not able to return an executable command!!\n");
			    current.setEnergy(current.getEnergy()-1);
                            throwed = true;
                        }// catch

                        // stop timer
                        this.init.mainWindow.stopTimer();

                        // if robot not disqualified do command
                        if (!throwed && !hasInDisqualifiedRobots(current))
                        {
                            try
                            {
                                robotAction.doAction(current);
                            }
                            catch (ClassCastException e)
                            {
                                this.drawLogMessage(current.getName() + ": I can't execute this command\n");
				current.setEnergy(current.getEnergy()-1);
                            }
                            // update state- and game- panel
                            this.drawState();

                        }// if
                        throwed = false;
                    }// if

                }// while
            }// if


            this.arenaControl();
        }// while

        // stop arena - thread
        return ;

    }

    /**
     * Removes all disqualified robots from HashSet robot
     * @throws ConcurrentModificationsException
     */
    private synchronized void deleteDisqualifiedRobots()
    {
      try
      {
	// removes all disqualified Robots from robot
        if (this.sizeOfDisqualifiedRobots() > 0)
        {
            Iterator iteratorDR = null;
            Robot toDelete = null;
            iteratorDR = this.iteratorOfDisqualifiedRobots();
            while (iteratorDR.hasNext())
            {
                try
                {
                    // cast the next element of disqualifiedRobots to Robot
                    toDelete = (Robot) iteratorDR.next();
                }

                catch (ClassCastException e)
                {
                    // draw an error message on the log if the next element is not a Robot
                    this.drawLogMessage("Error in HashSet disqualifiedRobots\n");
                    return ;
                }



                if (this.hasInRobot(toDelete))
                {
                    this.removeFromRobot(toDelete);
                    toDelete.removeYou();
                }

                // removes disqualified robot from HashSet robot
                this.disqualifiedRobots.remove(toDelete);

            }// while
        }// if
      }// try
      catch (ConcurrentModificationException e) {}
    }


    /**
     * Returns an Iterator of HashSet disqualifiedRobots
     *
     * @return Iterator 
     */
    private Iterator iteratorOfDisqualifiedRobots()
    {
        if (this.disqualifiedRobots != null)
            return this.disqualifiedRobots.iterator ();
        else
        {
            this.disqualifiedRobots = new HashSet();
            return this.disqualifiedRobots.iterator();
        }
    }


    /**
     * Adds the robot to the diqualified list
     *
     * @param robot The actual disqualified robot
     */
    public synchronized void disqualify(Robot robot)
    {
        if (!this.hasInDisqualifiedRobots(robot))
        {
            if (this.disqualifiedRobots == null)
            {
                // Create a new container for the disqualifiedRobots association.
                this.disqualifiedRobots = new HashSet ();
            }  // if
            this.disqualifiedRobots.add (robot);
        }  // if
    }


    /**
     * Returns true if the robot is element of HashSet disqualifiedRobots
     *
     * @param robot 
     * @return True if robot member of disqualifiedRobots,else false
     */
    private boolean hasInDisqualifiedRobots(Robot robot)
    {
        return ((this.disqualifiedRobots == null)
                ? false
                : this.disqualifiedRobots.contains (robot));
    }


    /**
     * Returns the size of the HashSet disqualifiedRobots
     *
     * @return Number of actual diqualified robots
     */
    public int sizeOfDisqualifiedRobots ()
    {
        return ((this.disqualifiedRobots == null)
                ? 0
                : this.disqualifiedRobots.size ());
    }


    /**
     * Sets the start field of the arena (not used)
     *
     *
     */
    public void setField (Field elem)
    {
        if (this.field != elem)
        {
            //newPartner
            if (this.field != null)
            {
                //inform old partner
                Field oldField = this.field;
                this.field = null;

                oldField.setArena (null);
            }//if

            this.field = elem;
            if (elem != null)
            {
                //inform new partner
                elem.setArena(this);
            }
        }
    }

    /**
     * Returns the start-field of the arena (not used)
     *
     * @return The actual start-field
     */
    public Field getField()
    {
        return this.field;
    }

    /**
     * Sets the maximum energy of each robot
     *
     * @param The max. amount of energy
     */
    
    private void setMaxEnergy (int energy)
    {
        this.maxEnergy = energy;
    }

    /**
     * Returns the allowed (maximum) energy
     *
     * @return max.Energy
     */
    public int getMaxEnergy ()
    {
        return this.maxEnergy;
    }

   
    /**
     * Places the item randomly in the arena
     *
     * @param Item
     */
    public void setRandom(Item item)
    {
        int row;
        int column;
        Random random = new Random();

        if (this.free > 0)
        {
            do
            {
                row = random.nextInt(init.noOfFieldsX - 1);
                column = random.nextInt(init.noOfFieldsY - 1);
            }
            while (gameField[row][column].getItem() != null);

            gameField [row] [column].setItem(item);
            this.free = this.free - 1;
        }// if
    }





    
    private int free;
    private Field[] [] gameField;
    private int counter = 0;
    private HashSet robot = null;
    private Set disqualifiedRobots = null;
    private boolean exitGame = false;
    private boolean pauseGame = false;
    private boolean stopGame = false;
    private Field field;
    private boolean stop = false;
    private GameInit init;
    private int maxEnergy;
}// Class Arena
