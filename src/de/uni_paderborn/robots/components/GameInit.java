package de.uni_paderborn.robots.components;
import java.util.*;
import de.uni_paderborn.robots.gui.*;

/**
 * A class containing all the information the arena needs
 * on startup.
 */
public class GameInit
{
    public GameInit(MainWindow mainWindow, HashSet robots, int maxEnergy, int noOfWells, int noOfTeleporters, int noOfFatamorganas, int noOfTraders, int noOfTaxmans, int noOfFieldsX, int noOfFieldsY)
    {
        this.mainWindow = mainWindow;
        this.robots = robots;
        this.maxEnergy = maxEnergy;
        this.noOfWells = noOfWells;
        this.noOfTeleporters = noOfTeleporters;
        this.noOfFatamorganas = noOfFatamorganas;
        this.noOfTraders = noOfTraders;
        this.noOfTaxmans = noOfTaxmans;
        this.noOfFieldsX = noOfFieldsX;
        this.noOfFieldsY = noOfFieldsY;
    }
    public MainWindow mainWindow;
    public HashSet robots;
    public int maxEnergy;
    public int noOfWells;
    public int noOfTeleporters;
    public int noOfFatamorganas;
    public int noOfTraders;
    public int noOfTaxmans;
    public int noOfFieldsX;
    public int noOfFieldsY;
    public boolean wasModified;
}
