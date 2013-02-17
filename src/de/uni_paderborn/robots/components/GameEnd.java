package de.uni_paderborn.robots.components;
import java.util.*;
import de.uni_paderborn.robots.logic.*;

/**
 * A class containing all the information that is needed
 * by the GUI to display the games final result.
 */
public class GameEnd
{
    public GameEnd(String[][] winner)
    {
        this.winner = winner;
    }
    public String[][] winner;
}
