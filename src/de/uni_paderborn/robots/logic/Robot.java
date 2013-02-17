package de.uni_paderborn.robots.logic;
import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.items.*;
import java.util. *;

/**
 * Class Robot
 */
public class Robot extends Item
{
    /**
     * energylevel of Robot
     */
    public int energy;
    /**
     * direction the Robot looks at
     */
    public int direction;
    /**
     * constant for North
     */
    public static final int NORTH = 1;
    /**
     * constant for West
     */
    public static final int WEST = 2;
    /**
     * constant for East
     */
    public static final int EAST = 4;
    /**
     * constant for South
     */
    public static final int SOUTH = 3;
    /**
     * "hand" of the robot
     */
    private HashSet portable;
    /**
     * arena, the robot is in
     */
    private Arena arena;
    /**
     * ID of Robot
     */
    private int id = 0;
    /**
     * returns next action of robot.
     *
     * @return Action, the robot wants to do. 
     */
    public AbstractAction getCommand ()
    {
        return null;
    }
    /**
     * select card for exchange.
     *
     * @return most redundant card 
     */
    public Card exchangeProposal ()
    {
        return null;
    }
    /**
     * returns current direction.
     *
     * @return constant for current direction 
     */
    final public int getDirection ()
    {
        return direction;
    }
    /**
     * sets direction
     *
     * @param direction direction
     * @return constant for direction 
     */
    final public int setDirection (int direction)
    {
        if (this.direction != direction)
        {
            this.direction = direction;
        }  // if

        return this.direction;
    }
    /**
     * returns current energylevel
     *
     * @return int for current energylevel 
     */
    final public int getEnergy ()
    {
        return energy;
    }
    /**
     * sets energy of robot
     *
     * @param energy energy
     * @return int for energy 
     */
    final public int setEnergy (int energy)
    {
        if (this.energy != energy)
        {
            this.energy = energy;
        }  // if

        return this.energy;
    }
    /**
     * returns true if robot accepts exchange.
     *
     * @param myCard our card, otherCard card of other robot 
     * @return true=accepts card, false=do not accept 
     */
    public boolean exchangeOK (Card myCard, Card otherCard)
    {
        return false;
    }
    /**
     * picks up a card
     *
     * @param elem card to add
     */
    final public void addToPortable(Card elem)
    {
        if ((elem != null) && !this.hasInPortable(elem) && (this.sizeOfPortable() < 5))
        {
            if (this.portable == null)
            {
                // Create a new container for the portable association.
                this.portable = new HashSet();
            }  // if

            this.portable.add(elem);
            elem.setRobot(this);
        }  // if


    }
    /**
     * checks if card is owned
     *
     * @param elem card to look for
     * @return is the card there ? 
     */
    final public boolean hasInPortable (Card elem)
    {
        return ((this.portable == null)
                ? false
                : this.portable.contains(elem));
    }
    /**
     * a shity pointer
     *
     * @return iterator
     */
    final public Iterator iteratorOfPortable ()
    {
        if (this.portable != null)
            return this.portable.iterator();
        else
        {
            this.portable = new HashSet();
            return this.portable.iterator();
        }
    }
    /**
     * removes card
     *
     * @param elem card to remove
     */
    final public void removeFromPortable (Card elem)
    {
        if (this.hasInPortable(elem))
        {
            this.portable.remove(elem);
            elem.setRobot(null);
        }  // if
    }
    /**
     * number of cards owned
     *
     * @return int for number of cards
     */
    final public int sizeOfPortable()
    {
        return ((this.portable == null)
                ? 0
                : this.portable.size());
    }
    /**
     * throws all cards away
     */
    final public void removeAllFromPortable()
    {
        Card tmpCard;
        Iterator iter = iteratorOfPortable();

        while (iter.hasNext())
        {
            tmpCard = (Card)iter.next();
            iter.remove();
            tmpCard.setRobot(null);
        }  // while


    }
    /**
     * fuck off
     */
    final public void removeYou()
    {
        super.removeYou();

        removeAllFromPortable();

        Arena tmpArena = getArena();
        if (tmpArena != null)
        {
            setArena (null);
        }  // if
    }
    /**
     * allocates an arena to the robot
     *
     * @param elem arena
     */
    final public void setArena (Arena elem)
    {
        if (this.arena != elem)
        {
            // newPartner
            if (this.arena != null)
            {
                // inform old partner
                Arena oldArena = this.arena;
                this.arena = null;

                oldArena.removeFromRobot (this);
            }  // if

            this.arena = elem;
            if (elem != null)
            {
                // inform new partner
                elem.addToRobot (this);
            }  // if

        }  // if


    }
    /**
     * return current arena
     *
     * @return arena the robot is in
     */
    final public Arena getArena ()
    {
        return this.arena;

    }

    /**
     *  sets the id of a Robot
     *
     * @param x ID to set
     */
    public void setID (int x)
    {
        this.id = x;
    }

    /**
     * returns id of a Robot
     *
     * @return ID of Robot
     */
    public int getId()
    {
        return this.id;
    }
}
