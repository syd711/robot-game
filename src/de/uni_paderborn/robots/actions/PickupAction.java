package de.uni_paderborn.robots.actions;

import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.logic.*;
import java.util. *;
/**
 * Class PickupAction implements the pickup of cards
 */
public class PickupAction extends AbstractAction
{
    /**
     * Implements the action to do
     * @param Represents the roboter who wants to pickup a card
     */
    public void doAction (Robot robot)
    {
        Field downField = null;
        Field field = null;
        Field leftField = null;
        Card tmpCard = null;
        Field rightField = null;
        Field upField = null;
        Object item = null;

        robot.setEnergy(robot.getEnergy() - 1);

        if (robot.getDirection() == Robot.EAST)
        {
            // bind field : Field
            field = robot.getField();

            // bind rightField : Field
            rightField = field.getRight();

            // bind tmpCard : Card
            item = rightField.getItem();

            if (item != null && item instanceof Card)
            {
                if (robot.sizeOfPortable() < 5)
		{
			tmpCard = (Card) rightField.getItem();
                	// delete link
                	rightField.setItem (null);

                	// create link
               		System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
			robot.addToPortable (tmpCard);
                	robot.getArena().drawLogMessage(robot.getName() + " decided to pickup " + tmpCard.getName() + "\n");
			System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
		}
		else robot.getArena().drawLogMessage( " Sorry, " + robot.getName() + "\nwe don't allow more than 5 cards!\n");
		
            }
            return ;
        }

        else
        {
            if (robot.getDirection() == Robot.SOUTH)
            {
                // bind field : Field
                field = robot.getField();

                // bind rightField : Field
                downField = field.getDown();

                // bind item : Card
                item = downField.getItem();

                if (item != null && item instanceof Card)
                {
                    if (robot.sizeOfPortable() < 5)
		    {
		    	// bind tmpCard card
                    	tmpCard = (Card) downField.getItem();
                    	// delete link
                    	downField.setItem (null);

                    	// create link
                    	System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
			robot.addToPortable (tmpCard);
                    	robot.getArena().drawLogMessage(robot.getName() + " decided to pickup " + tmpCard.getName() + "\n");
			System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
		   }
		   else robot.getArena().drawLogMessage( " Sorry, " + robot.getName() + "\nwe don't allow more than 5 cards!\n");
                }
                return ;

            }

            else
            {
                if (robot.getDirection() == Robot.WEST)
                {
                    // bind field : Field
                    field = robot.getField();

                    // bind rightField : Field
                    leftField = field.getLeft();

                    // bind tmpCard : Card
                    item = leftField.getItem();

                    if (item != null && item instanceof Card)
                    {
                        if (robot.sizeOfPortable() < 5)
			{
				tmpCard = (Card) leftField.getItem();
                        	// delete link
                        	leftField.setItem (null);

                        	// create link
                        	System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
				robot.addToPortable (tmpCard);
                        	robot.getArena().drawLogMessage(robot.getName() + " decided to pickup " + tmpCard.getName() + "\n");
				System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
			}
			else robot.getArena().drawLogMessage( " Sorry, " + robot.getName() + "\nwe don't allow more than 5 cards!\n");
                    }
                    return ;
                }

                else
                {
                    if (robot.getDirection() == Robot.NORTH)
                    {

                        // bind field : Field
                        field = robot.getField();

                        // bind rightField : Field
                        upField = field.getUp();

                        // bind tmpCard : Card
                        item = upField.getItem();

                        if (item != null && item instanceof Card)
                        {
                            if (robot.sizeOfPortable() < 5)
			    {
			    	tmpCard = (Card) upField.getItem();
                            	// delete link
                            	upField.setItem (null);

                            	// create link
                            	System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
				robot.addToPortable (tmpCard);
                            	robot.getArena().drawLogMessage(robot.getName() + " decided to pickup " + tmpCard.getName() + "\n");
				System.out.println ( robot.getName() + " Portable:" + robot.sizeOfPortable());
			    }
			    else robot.getArena().drawLogMessage( " Sorry, " + robot.getName() + "\nwe don't allow more than 5 cards!\n");
                        }
                        return ;
                    }

                }

            }

        }

        return ;
    }

}// PickupAction
