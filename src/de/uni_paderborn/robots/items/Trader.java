package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class Trader takes a Card from Robot and translates it to Energy
 */
public class Trader extends Reactable
{

    /**
     * Constructor Trader
     */
    public Trader ()
    {
        this.energyDiff = 0;
    } // Trader

    Card tmpCard = null;
    int energy = 0;

    /**
     * Implements the action for a trader
     * @param The roboter who deals with these trader
     */
    public void doAction (Robot robot)
    {
        try
        {
            if (robot != null)
            {
                tmpCard = robot.exchangeProposal();
                energy = tmpCard.getColor() + tmpCard.getValue() + 10;  // colorvalue + cardvalue + 10 bonuspoints
                if (energy > robot.getArena().getMaxEnergy())
                    robot.getArena().drawLogMessage(robot.getName() + " meet Trader\n");
                else

                    robot.removeFromPortable(tmpCard);  // remove Card from Robot
                robot.setEnergy(robot.getEnergy() + energy);  // set new Energy of Robot
                robot.getArena().setRandom(tmpCard);  // adds Card randomly to Arena

                robot.getArena().drawLogMessage(robot.getName() + " meet Trader\n and exchanged Card to Energy\n");
            }// if
            return ;
        }// try
        catch (NoSuchElementException e)
        {
            robot.getArena().drawLogMessage(robot.getName() + " meet Trader\n but he hasn't Card to exchange\n");
            return ;
        }// catch

        catch (NullPointerException e)

        {

            robot.getArena().drawLogMessage(robot.getName() + " meet Trader\n but he hasn't Card to exchange\n");

            return ;

        }// catch
    }// doAction

}// Class Trader
