package de.uni_paderborn.robots.items;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.logic.*;


import java.util. *;
/**
 * Class Reactable
 */
public abstract class Reactable extends Item
{
    /**
     * The abstract action for all Reactables
     * @param The roboter who use these reactable
     */
    public abstract void doAction (Robot robot);

    public int energyDiff = 0;
    
    /**
     * Implements the read access of the EnergyDiff of the reactable
     * @return The amount of energy the reactable removes/adds
     */
    public int getEnergyDiff ()
    {
        return energyDiff;

    }
    
    /**
     * Implements the write access for EnergyDiff
     * @param The desired amount of energy
     */
    public int setEnergyDiff (int energyDiff)
    {
        if (this.energyDiff != energyDiff)
        {
            this.energyDiff = energyDiff;
        }  // if

        return this.energyDiff;

    }
}// Class Reactable
