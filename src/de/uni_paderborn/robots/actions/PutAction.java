package de.uni_paderborn.robots.actions;

import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;

/**
 * Class PutAction implements the put down of cards
 */
public class PutAction extends AbstractAction
{
    /**
     * Constructor for Object Putaction
     * @param Represents the card to put down
     */
    public PutAction(Card card)
    {
        this.pCard = card;
    }
    
    /**
     * Implements the action for put down a card
     * @param Represents the roboter who wants to put down a card
     */
    public void doAction(Robot robot)
    {
        robot.setEnergy(robot.getEnergy() - 1);

        if (robot.getDirection() == Robot.NORTH)
        {
            field = robot.getField();
            upField = field.getUp();
            if (upField.getItem() == null && pCard != null)
            {
                upField.setItem(this.pCard);
                robot.removeFromPortable(this.pCard);
                robot.getArena().drawLogMessage(robot.getName() + " decided to put down " + pCard.getName() + "\n");
            }
            else
            {
                if (upField.getItem() != null)
                    robot.getArena().drawLogMessage(robot.getName() + " tried to put a card on a non empty field\n");
            }
            return ;
        }
        else
        {
            if (robot.getDirection() == Robot.WEST)
            {
                field = robot.getField();
                leftField = field.getLeft();
                if (leftField.getItem() == null && pCard != null)
                {
                    leftField.setItem(this.pCard);
                    robot.removeFromPortable(this.pCard);
                    robot.getArena().drawLogMessage(robot.getName() + " decided to put down " + pCard.getName() + "\n");
                }
                else
                {
                    if (leftField.getItem() != null)
                        robot.getArena().drawLogMessage(robot.getName() + " tried to put a card on a non empty field\n");
                }
                return ;
            }

            else
            {
                if (robot.getDirection() == Robot.SOUTH)
                {
                    field = robot.getField();
                    downField = field.getDown();
                    if (downField.getItem() == null && pCard != null)
                    {
                        downField.setItem(this.pCard);
                        robot.removeFromPortable(this.pCard);
                        robot.getArena().drawLogMessage(robot.getName() + " decided to put down " + pCard.getName() + "\n");
                    }
                    else
                    {
                        if (downField.getItem() != null)
                            robot.getArena().drawLogMessage(robot.getName() + " tried to put a card on a non empty field\n");
                    }
                    return ;
                }
                else
                {
                    if (robot.getDirection() == Robot.EAST)
                    {
                        field = robot.getField();
                        rightField = field.getRight();
                        if (rightField.getItem() == null && pCard != null)
                        {
                            rightField.setItem(this.pCard);
                            robot.removeFromPortable(this.pCard);
                            robot.getArena().drawLogMessage(robot.getName() + " decided to put down " + pCard.getName() + "\n");
                        }  // if
                        else
                        {
                            if (rightField.getItem() != null)
                                robot.getArena().drawLogMessage(robot.getName() + " tried to put a card on a non empty field\n");
                        }// else
                        return ;
                    }  // if
                }// else
            }// else
        }// else

        return ;
    } // doAction

    /**
     * Returns the Card stored in these PutAction
     * @return The Card 
     */
    public Card getCard()
    {
        return this.pCard;
    }

    /**
     * Manipulates the card which will be put down
     * @param Represents the card wich will be put down 
     */
    public void setCard(Card card)
    {
        if ( this.pCard != card && card != null)
            this.pCard = card;
    }

    Card pCard = null;
    Field field = null;
    Field upField = null;
    Field downField = null;
    Field leftField = null;
    Field rightField = null;

}// PutAction

