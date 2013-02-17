package de.uni_paderborn.robots.items;

import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;

/**
 * Class Fatamorgana implements a virtual well
 */
public class Fatamorgana extends Reactable
{
    public Fatamorgana ()
    {
        this.energyDiff = 0;
    }  // Fatamorgana



    /**
     * This method transports the fatamorgana to a new place
     */
    public void doAction (Robot robot)
    {
        if (robot != null)
        {
            Field tmpField = null;
            int direction = robot.getDirection();

            if (direction == Robot.NORTH)
            {
                if (this.getField().getUp().getItem() == null)
                {
                    tmpField = this.getField();
                    this.getField().getUp().setItem(this);
                    robot.getField().setItem(null);
                    tmpField.setItem(robot);
                    robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                    return ;
                }// if
                else
                {
                    if (this.getField().getLeft().getItem() == null)
                    {
                        tmpField = this.getField();
                        this.getField().getLeft().setItem(this);
                        robot.getField().setItem(null);
                        tmpField.setItem(robot);
                        robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                        return ;
                    }// if
                    else
                    {
                        if (this.getField().getDown().getItem() == null)
                        {
                            tmpField = this.getField();
                            this.getField().getDown().setItem(this);
                            robot.getField().setItem(null);
                            tmpField.setItem(robot);
                            robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                            return ;
                        }// if
                        else
                        {
                            if (this.getField().getRight().getItem() == null)
                            {
                                tmpField = this.getField();
                                this.getField().getRight().setItem(this);
                                robot.getField().setItem(null);
                                tmpField.setItem(robot);
                                robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                return ;
                            }// if
                            else robot.getArena().setRandom(this);


                        }// else
                    }// else
                }// else
            }// if


            else
            {
                if (direction == Robot.WEST)
                {
                    if (this.getField().getLeft().getItem() == null)
                    {
                        tmpField = this.getField();
                        this.getField().getLeft().setItem(this);
                        robot.getField().setItem(null);
                        tmpField.setItem(robot);
                        robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                        return ;
                    }// if
                    else
                    {
                        if (this.getField().getDown().getItem() == null)
                        {
                            tmpField = this.getField();
                            this.getField().getDown().setItem(this);
                            robot.getField().setItem(null);
                            tmpField.setItem(robot);
                            robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                            return ;
                        }// if
                        else
                        {
                            if (this.getField().getRight().getItem() == null)
                            {
                                tmpField = this.getField();
                                this.getField().getRight().setItem(this);
                                robot.getField().setItem(null);
                                tmpField.setItem(robot);
                                robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                return ;
                            }// if
                            else
                            {
                                if (this.getField().getUp().getItem() == null)
                                {
                                    tmpField = this.getField();
                                    this.getField().getUp().setItem(this);
                                    robot.getField().setItem(null);
                                    tmpField.setItem(robot);
                                    robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                    return ;
                                }// if
                                else robot.getArena().setRandom(this);
                            }// else
                        }// else
                    }// else
                }// if


                else
                {
                    if (direction == Robot.SOUTH)
                    {
                        if (this.getField().getDown().getItem() == null)
                        {
                            tmpField = this.getField();
                            this.getField().getDown().setItem(this);
                            robot.getField().setItem(null);
                            tmpField.setItem(robot);
                            robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                            return ;
                        }// if
                        else
                        {
                            if (this.getField().getRight().getItem() == null)
                            {
                                tmpField = this.getField();
                                this.getField().getRight().setItem(this);
                                robot.getField().setItem(null);
                                tmpField.setItem(robot);
                                robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                return ;
                            }// if
                            else
                            {
                                if (this.getField().getUp().getItem() == null)
                                {
                                    tmpField = this.getField();
                                    this.getField().getUp().setItem(this);
                                    robot.getField().setItem(null);
                                    tmpField.setItem(robot);
                                    robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                    return ;
                                }// if
                                else
                                {
                                    if (this.getField().getLeft().getItem() == null)
                                    {
                                        tmpField = this.getField();
                                        this.getField().getLeft().setItem(this);
                                        robot.getField().setItem(null);
                                        tmpField.setItem(robot);
                                        robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                        return ;
                                    }// if
                                    else robot.getArena().setRandom(this);
                                }// else
                            }// else
                        }// else
                    }// if


                    else
                    {
                        if (direction == Robot.EAST)
                        {
                            if (this.getField().getRight().getItem() == null)
                            {
                                tmpField = this.getField();
                                this.getField().getRight().setItem(this);
                                robot.getField().setItem(null);
                                tmpField.setItem(robot);
                                robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                return ;
                            }// if
                            else
                            {
                                if (this.getField().getUp().getItem() == null)
                                {
                                    tmpField = this.getField();
                                    this.getField().getUp().setItem(this);
                                    robot.getField().setItem(null);
                                    tmpField.setItem(robot);
                                    robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                    return ;
                                }// if
                                else
                                {
                                    if (this.getField().getLeft().getItem() == null)
                                    {
                                        tmpField = this.getField();
                                        this.getField().getLeft().setItem(this);
                                        robot.getField().setItem(null);
                                        tmpField.setItem(robot);
                                        robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                        return ;
                                    }// if
                                    else
                                    {
                                        if (this.getField().getDown().getItem() == null)
                                        {
                                            tmpField = this.getField();
                                            this.getField().getDown().setItem(this);
                                            robot.getField().setItem(null);
                                            tmpField.setItem(robot);
                                            robot.getArena().drawLogMessage(robot.getName() + " tried to use " + this.getName() + "\n");
                                            return ;
                                        }// if
                                        else robot.getArena().setRandom(this);
                                    }// else
                                }// else
                            }// else
                        }// if
                    }// else
                }// else
            }// else
        }// if



        return ;
    } // doAction
}
