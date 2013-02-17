package de.uni_paderborn.robots.actions;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class RotateAction
 */
public class RotateAction extends AbstractAction
{
    /**
     * Constructor RotateAction
     * @param Stores the desired direction 
     */
    public RotateAction (int direct)
    {
        if (direct >= 1 && direct <= 4)
        {
            // is fool, but why should I store the return value
            this.direction = direct;
        }
    }
    /**
     * Attribute: ' direction : Integer'
     */
    public int direction;
    /**
     * Turns the  robot in the desired direction
     + @param The roboter who wants change his direction 
     */
    public void doAction (Robot robot)
    {
        if (robot != null)
        {
            robot.setEnergy(robot.getEnergy() - 1);
            String look = "bad";
            if (this.direction > 0 && this.direction < 5)
            {
                robot.setDirection(this.direction);

                switch ( this.direction)
                {
                        case Robot.NORTH:
                        look = "up.";
                        break;
                        case Robot.WEST:
                        look = "left.";
                        break;
                        case Robot.EAST:
                        look = "right.";
                        break;
                        case Robot.SOUTH:
                        look = "down.";
                        break;
                        default:
                        look = "";
                        break;
                }
            }
            robot.getArena().drawLogMessage( robot.getName() + " decided to rotate.\nNow he looks " + look + "\n");
            return ;
        }// if
    }
    /**
     * Read access method for attribute  direction
     *@return The actual direction
     */
    public int getDirection ()
    {
        return direction;

    }
    /**
     * Write access method for attribute  direction 
     *@param Stores the desired direction
     *@return Returns the actual manipulated direction
     */
    public int setDirection (int direction)
    {
        if (this.direction != direction)
        {
            this.direction = direction;
        }  // if
        return this.direction;
    }
}// RotateAction
