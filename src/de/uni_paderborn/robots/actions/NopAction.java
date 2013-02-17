package de.uni_paderborn.robots.actions;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.*;
import java.util.*;

/**
 * Class NopAction implements a do nothing action
 *
 */
public class NopAction extends AbstractAction
{
    /**
     * Does the do nothing action
     *@param The roboter who want to do nothing
     */
    public void doAction (Robot robot)
    {
        robot.setEnergy(robot.getEnergy() - 1);
        robot.getArena().drawLogMessage( robot.getName() + " decided to do nothing\n");
        return ;
    }
}// NopAction
