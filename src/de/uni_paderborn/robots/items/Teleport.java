package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util.*;

/**
 * Class Teleport
 */
public class Teleport extends Reactable
{
    /**
     * Implements the action for teleporting a robot
     * @param The roboter
     */
    public void doAction (Robot robot)
    {
        Field newField = null;
        Teleport nextTeleport = null;
        Field oldField = null;

        boolean success = false;
        Object fujaba__TmpObject = null;

        while (!success)
        {
            if (teleport != null && this != teleport)
            {
                nextTeleport = this.getTeleport();
                oldField = robot.getField();
            }
            newField = nextTeleport.getField().getUp();

            if (oldField != null && oldField.getItem() == robot && newField.getItem() == null && oldField != newField)
            {

                oldField.setItem (null);
                newField.setItem (robot);
                success = true;
            }
            else
            {
                newField = nextTeleport.getField().getLeft();

                if (oldField != null && oldField.getItem() == robot && newField.getItem() == null && oldField != newField)
                {

                    oldField.setItem (null);
                    newField.setItem (robot);
                    success = true;
                }

                else
                {
                    newField = nextTeleport.getField().getDown();

                    if (oldField != null && oldField.getItem() == robot && newField.getItem() == null && oldField != newField)
                    {

                        oldField.setItem (null);
                        newField.setItem (robot);
                        success = true;
                    }

                    else
                    {
                        newField = nextTeleport.getField().getUp();

                        if (oldField != null && oldField.getItem() == robot && newField.getItem() == null && oldField != newField)
                        {

                            oldField.setItem (null);
                            newField.setItem (robot);
                            success = true;
                        }
                    } // else
                }// else
            }// else



            if (success)
                robot.getArena().drawLogMessage( robot.getName() + " used teleporter " + this.getName() + "\n");
            else
            {
                robot.getArena().drawLogMessage( " Teleporting " + robot.getName() + "failed" + "\n");
                break;
            }

        }
        return ;
    }



    /**
     * Removes these Teleporter from the arena
     * 
     */
    public void removeYou ()
    {
        Teleport tmpTeleport = getTeleport();
        if (tmpTeleport != null)
        {
            setTeleport (null);
        }  // if


    }
    private Teleport teleport;
    
    /**
     * Implements the write access for the corresponding teleporter
     * @param The corrsponding teleporter
     */
    public void setTeleport (Teleport elem)
    {
        if (this.teleport != elem)
        {
            // newPartner
            if (this.teleport != null)
            {
                // inform old partner
                Teleport oldTeleport = this.teleport;
                this.teleport = null;

                oldTeleport.setTeleport (null);
            }  // if

            this.teleport = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setTeleport (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the read access for the corresponding teleporter
     * @return The corresponding teleporter
     */
    public Teleport getTeleport ()
    {
        return this.teleport;

    }
}// Class Teleporter
