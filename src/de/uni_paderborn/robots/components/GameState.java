package de.uni_paderborn.robots.components;
import java.util.*;
import de.uni_paderborn.robots.logic.*;

/**
 * A class containing all the information that is passed between
 * the GUI and the arena during a running game.
 */
public class GameState
{
    public GameState(HashSet robots, Field[][] arena)
    {
        this.robots = robots;
        this.arena = arena;
    }
    public HashSet robots;
    public Field[][] arena;
}
