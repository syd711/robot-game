package de.uni_paderborn.robots.actions;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class MoveAction
 */
public class MoveAction extends AbstractAction
{
    /**
     * This method implements the action move
     * @param Stores the roboter who wants move
     */
    public void doAction (Robot robot)
    {
        Field downField = null;
        Field field = null;
        Field leftField = null;
        Field rightField = null;
        Field upField = null;
        Object item = null;

        robot.setEnergy(robot.getEnergy() - 1);

        if (robot.getDirection() == Robot.NORTH)
        {
            // bind field
            field = robot.getField();
            // bind upField
            upField = field.getUp();
            // link item
            item = upField.getItem();
            // if upField empty roboter moves on upField
            if (upField != null && item == null)
            {
                field.setItem(null);
                upField.setItem(robot);
                if (robot.getArena() != null)
                    robot.getArena().drawLogMessage(robot.getName() + " decided to move up\n");
            }
            // if upField not empty, roboter hold position, call item.doAction()
            else
                if (upField != null && item instanceof Reactable)
                    ((Reactable) upField.getItem()).doAction(robot);
                else
                {
                    if (upField != null && item instanceof Robot)
                    {
                        try
                        {
                            int tmpEnergy = robot.getEnergy() - 10;
                            if (tmpEnergy < 0)
                                robot.setEnergy(0);
                            else robot.setEnergy(robot.getEnergy() - 10);

                            robot.getArena().drawLogMessage( robot.getName() + " bumped " + ((Robot) item).getName() + "\n");
                        }
                        catch (ClassCastException e)
                        {}




                    }
                }

            return ;
        }

        else
        {
            if (robot.getDirection() == Robot.EAST)
            {
                // bind field
                field = robot.getField();
                // bind rightField
                rightField = field.getRight();
                // link item
                item = rightField.getItem();
                // if rightField empty roboter moves on rightField
                if (rightField != null && item == null)
                {
                    field.setItem(null);
                    rightField.setItem(robot);
                    if (robot.getArena() != null)
                        robot.getArena().drawLogMessage(robot.getName() + " decided to move right\n");
                }
                // if rightField not empty, roboter hold position, call item.doAction()
                else
                    if (rightField != null && item instanceof Reactable)
                        ((Reactable)rightField.getItem()).doAction(robot);
                    else
                    {
                        if (rightField != null && item instanceof Robot)
                        {
                            try
                            {
                                int tmpEnergy = robot.getEnergy() - 10;
                                if (tmpEnergy < 0)
                                    robot.setEnergy(0);
                                else robot.setEnergy(robot.getEnergy() - 10);

                                robot.getArena().drawLogMessage( robot.getName() + " bumped " + ((Robot) item).getName() + "\n");
                            }
                            catch (ClassCastException e)
                            {}




                        }
                    }

                return ;
            }

            else
            {
                if (robot.getDirection() == Robot.SOUTH)
                {
                    // bind field
                    field = robot.getField();
                    // bind downField
                    downField = field.getDown();
                    // link item
                    item = downField.getItem();
                    // if downField empty roboter moves on downField
                    if (downField != null && item == null)
                    {
                        field.setItem(null);
                        downField.setItem(robot);
                        if (robot.getArena() != null)
                            robot.getArena().drawLogMessage(robot.getName() + " decided to move down\n");
                    }
                    // if downField not empty, roboter hold position, call item.doAction()
                    else
                        if (downField != null && item instanceof Reactable)
                            ((Reactable) downField.getItem()).doAction(robot);
                        else
                        {
                            if (downField != null && item instanceof Robot)
                            {
                                try
                                {
                                    int tmpEnergy = robot.getEnergy() - 10;
                                    if (tmpEnergy < 0)
                                        robot.setEnergy(0);
                                    else robot.setEnergy(robot.getEnergy() - 10);

                                    robot.getArena().drawLogMessage( robot.getName() + " bumped " + ((Robot) item).getName() + "\n");
                                }
                                catch (ClassCastException e)
                                {}




                            }
                        }

                    return ;

                }

                else
                {
                    if (robot.getDirection() == Robot.WEST)
                    {
                        // bind field
                        field = robot.getField();
                        // bind leftField
                        leftField = field.getLeft();
                        //link item
                        item = leftField.getItem();
                        // if leftField empty roboter moves on leftField
                        if (leftField != null && item == null)
                        {
                            field.setItem(null);
                            leftField.setItem(robot);
                            if (robot.getArena() != null)
                                robot.getArena().drawLogMessage(robot.getName() + " decided to move left\n");
                        }
                        // if leftField not empty, roboter hold position, call item.doAction()
                        else
                            if (leftField != null && item instanceof Reactable)
                                ((Reactable) leftField.getItem()).doAction(robot);
                            else
                            {
                                if (leftField != null && item instanceof Robot)
                                {
                                    try
                                    {
                                        int tmpEnergy = robot.getEnergy() - 10;
                                        if (tmpEnergy < 0)
                                            robot.setEnergy(0);
                                        else robot.setEnergy(robot.getEnergy() - 10);

                                        robot.getArena().drawLogMessage( robot.getName() + " bumped " + ((Robot) item).getName() + "\n");
                                    }
                                    catch (ClassCastException e)
                                    {}



                                }
                            }

                        return ;

                    }

                    else
                    {
                        return ;
                    }

                }

            }

        }

    }
}// MoveAction
