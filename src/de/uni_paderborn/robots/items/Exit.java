package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util.*;

/**
 * Class Exit 
 */
public class Exit extends Reactable
{
    /**
     * Implements the action to do, if robot take the exit
     *@param The roboter who takes the exit
     */
    public void doAction (Robot robot)
    {
        robot.getArena().setExit();
        robot.getArena().drawLogMessage( robot.getName() + " took the exit!!\n");
        return ;
    }
}// Exit
