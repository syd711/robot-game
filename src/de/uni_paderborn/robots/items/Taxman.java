package de.uni_paderborn.robots.items;

import java.util.*;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.actions.*;

public class Taxman extends Robot
{

    /**
     * Constructor Taxman
     */
    public Taxman()
    {
        setName ("Taxman");
    }

    /**
     *  Returns the next action of the robot
     *  only step forward, rotate to right/left
     *
     * @return AbstractAction next action
     */
    public AbstractAction getCommand()
    {
        Random r = new Random();
        int i;
        Item itemFront = this.getArena().getItemInFrontOfRobot(this);

        if (itemFront == null)
        {
            i = r.nextInt();
            if (( i % 6) == 3) return new RotateAction((this.getDirection() % 4) + 1);
            else if (( i % 6) == 4) return new RotateAction(((this.getDirection() + 2 ) % 4) + 1);
            else return new MoveAction();
        }

        else if (itemFront instanceof Well)
        {
            i = r.nextInt();
            if (( i % 4) == 1) return new RotateAction((this.getDirection() % 4) + 1);
            else if (( i % 4) == 2) return new RotateAction(((this.getDirection() + 2 ) % 4) + 1);
            else return new MoveAction();
        }

        else if (itemFront instanceof Wall) return new RotateAction(((this.getDirection() + 1) % 4) + 1);

        else if (itemFront instanceof Fatamorgana) return new RotateAction(((this.direction + 2) % 4) + 1);

        else if (itemFront instanceof Teleport) return new MoveAction();

        else if (itemFront instanceof Exit) return new RotateAction(((this.direction + 2) % 4) + 1);

        else if (itemFront instanceof Card) return new RotateAction(((this.direction + 2) % 4) + 1);

        else if (itemFront instanceof Robot) return new RotateAction(((this.getDirection() + 1) % 4) +
                    1);

        return new RotateAction(((this.getDirection() + 1) % 4) + 1);
    }


}// Class Taxman
