package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;


import java.util. *;
import javax.swing.*;
/**
 * Class Item
 */
public class Item
{
    public String name = "";
    
    /**
     * Implements the read access for the icon of these item (not use)
     * @return The icon
     */
    public Icon getIcon ()
    {
        return null;
    }
    
    /**
     * Implements the read access for the name of these item 
     * @return The name
     */
    public String getName ()
    {
        return name;

    }
    
    /**
     * Implements the action write access for the name of these item
     * @param The desired name
     */
    public String setName (String name)
    {
        if ((this.name == null) || (this.name != null && !this.name.equals (name)))
        {
            this.name = name;
        }  // if

        return this.name;

    }
    
    /**
     * Removes these item from the corresponding field
     * 
     */
    public void removeYou ()
    {
        Field tmpField = getField();
        if (tmpField != null)
        {
            setField (null);
        }  // if


    }
    
    private Field field;
    
    /**
     * Implements the write access for the corresponding field of these item
     * @param The field
     */
    public void setField (Field elem)
    {
        if (this.field != elem)
        {
            // newPartner
            if (this.field != null)
            {
                // inform old partner
                Field oldField = this.field;
                this.field = null;

                oldField.setItem (null);
            }  // if

            this.field = elem;
            if (elem != null)
            {
                // inform new partner
                elem.setItem (this);
            }  // if

        }  // if


    }
    
    /**
     * Implements the write access for the corresponding field
     * @return The field
     */
    public Field getField ()
    {
        return this.field;

    }
}// Class Item
