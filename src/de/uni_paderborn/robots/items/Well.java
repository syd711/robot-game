package de.uni_paderborn.robots.items;

import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class Well implements the well`s
 */
public class Well extends Reactable
{
    
    /**
     * Constructor Well
     */
    public Well ()
    {
        this.energyDiff = 10;
    }  // Well



    /**
     * Implements the action for using a well
     * @param The roboter who use these well
     */
    public void doAction (Robot robot)
    {
        if (robot != null)
        {
            int tmpEnergy = robot.getEnergy() + this.getEnergyDiff();
            if (tmpEnergy > robot.getArena().getMaxEnergy())
                robot.setEnergy(robot.getArena().getMaxEnergy());
            else
                robot.setEnergy (tmpEnergy);
            robot.getArena().drawLogMessage(robot.getName() + " used " + this.getName() + "\n");
        }// if

        return ;
    } // doAction
}// Class Well
