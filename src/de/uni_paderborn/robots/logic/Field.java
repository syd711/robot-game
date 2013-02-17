package de.uni_paderborn.robots.logic;

import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.items.*;

import java.util. *;
/**
 * Class Field'
 */
public class Field
{
    
    private Field left;
    /**
     * Implements write access to the left Field
     * @param The left partner (field)
     */
    public void setLeft (Field elem)
    {
        if (this.left != elem)
        {
            // newPartner
            if (this.left != null)
            {
                // inform old partner
                Field oldLeft = this.left;
                this.left = null;

                oldLeft.setRight (null);
            }  // if

            this.left = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setRight (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements read access for the left Field
     * @return The left field
     */
    public Field getLeft ()
    {
        return this.left;

    }
    
    private Field right;
    /**
     * Implements the write access for the right field
     * @param The right field
     */
    public void setRight (Field elem)
    {
        if (this.right != elem)
        {
            // newPartner
            if (this.right != null)
            {
                // inform old partner
                Field oldRight = this.right;
                this.right = null;

                oldRight.setLeft (null);
            }  // if

            this.right = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setLeft (this);
            }  // if

        }  // if


    }
    /**
     * Implements the read access for the right field 
     * @return The right field
     */
    public Field getRight ()
    {
        return this.right;

    }
    
    private Field up;
    
    /**
     * Implements the write access for the up field
     * @param The up field
     */
    public void setUp (Field elem)
    {
        if (this.up != elem)
        {
            // newPartner
            if (this.up != null)
            {
                // inform old partner
                Field oldUp = this.up;
                this.up = null;

                oldUp.setDown (null);
            }  // if

            this.up = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setDown (this);
            }  // if

        }  // if


    }
    /**
     * Implements the read access for the up field
     * @return The up field
     */
    public Field getUp ()
    {
        return this.up;

    }
    
    private Field down;
    
    /**
     * Implements the write access for the down field
     * @param The down field
     */
    public void setDown (Field elem)
    {
        if (this.down != elem)
        {
            // newPartner
            if (this.down != null)
            {
                // inform old partner
                Field oldDown = this.down;
                this.down = null;

                oldDown.setUp (null);
            }  // if

            this.down = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setUp (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the read access for the down field
     * @return The down field
     */
    public Field getDown ()
    {
        return this.down;

    }
    
    /**
     * Removes these field from the arena
     * 
     */
    public void removeYou ()
    {
        Field tmpRight = getRight();
        if (tmpRight != null)
        {
            setRight (null);
        }  // if

        Field tmpDown = getDown();
        if (tmpDown != null)
        {
            setDown (null);
        }  // if

        Item tmpItem = getItem();
        if (tmpItem != null)
        {
            setItem (null);
            tmpItem.removeYou();
        }  // if

        Arena tmpArena = getArena();
        if (tmpArena != null)
        {
            setArena (null);
        }  // if


    }
    
    private Item item;
    /**
     * Implements the write access for the item of these field
     * @param The item
     */
    public void setItem (Item elem)
    {
        if (this.item != elem)
        {
            // newPartner
            if (this.item != null)
            {
                // inform old partner
                Item oldItem = this.item;
                this.item = null;

                oldItem.setField (null);
            }  // if

            this.item = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setField (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the read access for the item of these field
     * @return The item 
     */
    public Item getItem ()
    {
        return this.item;

    }
    
    private Arena arena;
    
    /**
     * Implements write access for the arena of these field
     * @param The arena
     */
    public void setArena (Arena elem)
    {
        if (this.arena != elem)
        {
            // newPartner
            if (this.arena != null)
            {
                // inform old partner
                Arena oldArena = this.arena;
                this.arena = null;

                oldArena.setField (null);
            }  // if

            this.arena = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setField (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the read access for the arena of these field
     * @return The arena
     */
    public Arena getArena ()
    {
        return this.arena;
    }
    
    private static int actualId = 0;
    
    /**
     * Returns an ID
     * @return The ID
     */
    private static int getUniqueID()
    {
        return actualId++;
    }

    private transient int ID = getUniqueID ();
    
    /**
     * Returns the actual ID of these field
     * @return The actual ID
     */
    public int getId ()
    {
        return ID;
    }
}// Field
