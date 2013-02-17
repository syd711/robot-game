package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class Wall implements the walls
 */
public class Wall extends Reactable
{

    /**
     * Constructor Wall
     */
    public Wall ()
    {
        this.energyDiff = 10;
    } // Wall



    /**
     * Implements the action nessecary if a roboter hits a wall
     * @param The roboter who hits the wall
     */
    public void doAction (Robot robot)
    {
        if (robot != null)
        {
            int tmpEnergy = robot.getEnergy() - this.getEnergyDiff();
            if (tmpEnergy < 0)
                robot.setEnergy(0);
            else
                robot.setEnergy (tmpEnergy);
            robot.setDirection (((robot.direction + 2) % 4) + 1);
            robot.getArena().drawLogMessage(robot.getName() + " hits a wall\n");
        } // if
        return ;
    }  // doAction
}// Class Wall
