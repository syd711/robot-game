package de.uni_paderborn.robots.gui;
import de.uni_paderborn.robots.logic.*;

/**
 * A Timer to measure how long the robot is thinking
 */
class RobotTimer implements Runnable
{
    /**
     * Create a new timer
     *
     * @param robot The thinking Robot mainWindow The MainWindow-instance
     */
    RobotTimer(Robot robot, MainWindow mainWindow)
    {
        sec = 0;
        min = 0;
        mil = 0;
        this.mainWin = mainWindow;
	this.robot = robot;
    }

    /**
     * Control the timer, call MainWindow to display timer
     */
    public void run()
    {
        while (running)
        {
            if (min > 1 || sec > 1)
            {
	        String name = new String("");
	        if (this.robot.getName() != null)
		    name = this.robot.getName() + ": ";
                this.mainWin.displayTimer(name + min + ":" + sec + "." + mil);
            }
            mil++;
            if (mil >= 10)
            {
                mil = 0;
                sec++;
            }
            if (sec >= 60)
            {
                min++;
                sec = 0;
            }
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {}

        }
    }

    /**
     * @return Seconds of timer.
     */
    public int getSec()
    {
        return sec;
    }

    /**
     * @return Minutes of timer.
     */
    public int getMin()
    {
        return min;
    }

    /**
     * Stop the RobotTimer
     */
    public void stopThread()
    {
        this.running = false;
    }
	
	/**
 	* @serial If true, the timer runs 
	*/
    private boolean running = true;
	
	/**
 	* @serial seconds declaration
 	*/
    private int sec;
	
	/**
 	* @serial min. declaration
 	*/
    private int min;
	
	/**
 	* @serial milli declaration
 	*/
    private int mil;
	/**
 	* @serial MainWindow declaration
 	*/
    private MainWindow mainWin;
	
	/**
 	* @serial Roboter declaration
 	*/
    private Robot robot;
}
