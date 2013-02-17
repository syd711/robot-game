package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;

import java.util.*;
/**
 * Class Card
 */
public class Card extends Item
{
    /**
     * Constructor Card
     * @param Representing the color and value of the card
     */
    public Card(int color, int value)
    {
        this.setColor(color);
        this.setValue(value);
    }
    
    private int value;

    /**
     * Implements the read access for the value
     * @return The value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Implements the write access for the value
     * @param The value
     */
    private void setValue(int value)
    {
        this.value = value;
    }
    
    private int color = 1;
    
    /**
     * Implements the read access for the color of these card
     * @param The color
     */
    public int getColor ()
    {
        return color;
    }
    
    /**
     * Implements the write access for the color of these card
     * @param The color
     * @return The acual color set
     */
    private int setColor (int color)
    {
        if (this.color != color)
        {
            this.color = color;
        }  // if

        return this.color;

    }
    
    private Robot robot;
    
    /**
     * Implements the write access for the roboter who holds these card
     * @param The roboter holding these card
     */
    public void setRobot (Robot elem)
    {
        if (this.robot != elem)
        {
            // newPartner
            if (this.robot != null)
            {
                // inform old partner
                Robot oldRobot = this.robot;
                this.robot = null;

                oldRobot.removeFromPortable (this);
            }  // if

            this.robot = elem;
            if (elem != null)
            {
                // inform new partner
                elem.addToPortable (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the read access for the roboter who holds these card
     * @return The roboter
     */
    public Robot getRobot ()
    {
        return this.robot;

    }
    
    /**
     * Removes these card from the game
     * 
     */
    public void removeYou ()
    {
        super.removeYou ();

        Robot tmpRobot = getRobot();
        if (tmpRobot != null)
        {
            setRobot (null);
        }  // if


    }



}// Class Card

