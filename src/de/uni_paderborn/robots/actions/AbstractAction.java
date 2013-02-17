package de.uni_paderborn.robots.actions;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;
/**
 * abstract class AbstractAction 
 */
public abstract class AbstractAction
{
    /**
     * Executes the specified action
     * @param Stores the roboter whose action will be executed
     */
    public abstract void doAction (Robot robot);
}// AbstractAction
