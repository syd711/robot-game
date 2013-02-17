package de.uni_paderborn.robots.gui;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.robot.group3.*;
import de.uni_paderborn.robots.logic.Robot;

/**
 * The main window of robots which displays the other components,
 * creates, starts and controls the arena.
 */
public class MainWindow extends JFrame
{
    /**
     * Creates a new MainWindow. Only to be called from main.
     */
    public MainWindow ()
    {
        this.addWindowListener (new WindowAdapter ()
                                {
                                    public void windowClosing (WindowEvent e)
                                    {
                                        System.exit (0);
                                    }
                                }
                               );

        // build basic layout
        this.getContentPane().setLayout (new BorderLayout());

        // build right part of window
        statePane = new StatePane();
        logScrollPane = new JScrollPane(logPane);
        logPane = new LogPane(logScrollPane);
        logScrollPane.setViewportView(logPane);
        vSplitPane = new JSplitPane (JSplitPane.VERTICAL_SPLIT, true);
        vSplitPane.setOneTouchExpandable(true);
        vSplitPane.setTopComponent(new JScrollPane(statePane));
        vSplitPane.setBottomComponent(logScrollPane);

        // build left part of window
        arenaPane = new ArenaPane();
        arenaScrollPane = new JScrollPane(arenaPane);
        hSplitPane = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, true);
        hSplitPane.setOneTouchExpandable(true);
        hSplitPane.setRightComponent(vSplitPane);
        hSplitPane.setLeftComponent(arenaScrollPane);
        this.getContentPane().add(hSplitPane, BorderLayout.CENTER);

        // adding the menubar
        mainMenuBar = new MainMenuBar();
        this.setJMenuBar(mainMenuBar);

        // adding the toolbar
        this.toolBar = (new MainToolBar(this));
        this.getContentPane().add(this.toolBar, BorderLayout.NORTH);

        // various settings
        this.setTitle ("Robots");
        this.setSize(this.getToolkit().getScreenSize());
        this.setLocation(0, 0);
        this.setVisible (true);
        vSplitPane.setDividerLocation(342 + 5) ;
        hSplitPane.setDividerLocation((this.getSize().width) - 280 - 27);
    }

    /**
     * Does everthing necessary to start a new game such as invocing
     * the configuration window and creating a new arena.
     */
    void runGame()
    {
        // claculate the size of the Arena from the MainWindow current
        // size (and leave some spare space)
        Dimension sizeOfArenaPane = arenaScrollPane.getSize();
        int sizeX = (sizeOfArenaPane.width - 30) / MainWindow.H_SIZE;
        int sizeY = (sizeOfArenaPane.height - 30) / MainWindow.V_SIZE;

        // run the configuration window
        GameInit gameInit = new GameInit(this, new HashSet(), 250, 10, 10, 0, 3, 0, sizeX, sizeY);
        ConfWindow confWindow = new ConfWindow(this, gameInit);

        // start the Arena (unless abort was selected in ConfWindow)
        if (gameInit.wasModified)
        {
            // clean up remains from the old game
            arenaPane.clearDisplay();
            statePane.clearDisplay();
            logPane.clearDisplay();

            // actually start a new game
            this.logMessage("Creating Arena\n");
            this.arena = new Arena(gameInit);
            this.logMessage("Starting Arena\n");

            arenaThread = new Thread(this.arena);

            arenaThread.start();
        }
        else
        {
            this.logMessage("Sorry, configuration was aborted.\nNo conf, no game.\n");
            this.mainMenuBar.newGame.setEnabled(true);
            this.mainMenuBar.start.setEnabled(false);
            this.mainMenuBar.stop.setEnabled(false);
            this.mainMenuBar.pause.setEnabled(false);
            this.mainMenuBar.disqualify.setEnabled(false);
            this.toolBar.newButton.setEnabled(true);
            this.toolBar.startButton.setEnabled(false);
            this.toolBar.stopButton.setEnabled(false);
            this.toolBar.pauseButton.setEnabled(false);
            this.toolBar.disqualifyButton.setEnabled(false);
        }

        return ;
    }

    /**
     * Updates the displayed information about the current
     * state of the game.
     *
     * @param gameState An object containing the current state
     */
    public void updateState(GameState gameState)
    {
        arenaPane.updateState(gameState);

        // what a terrible hacking !!
        HashSet tmpHash = new HashSet();
        Iterator tmpIterator = gameState.robots.iterator();
        while (tmpIterator.hasNext())
        {
            Robot tmpRobot = (Robot) tmpIterator.next();
            if (!(tmpRobot instanceof Taxman))
                tmpHash.add(tmpRobot);
        }

        gameState.robots = tmpHash;
        statePane.updateState(gameState);

    }

    /**
     * Log a message to the mainwindow´s log
     *
     * @param message the message
     */
    public void logMessage(String message)
    {
        logPane.logMessage(message);
    }

    /**
     * When called it displays the results of the game.
     *
     * @param gameEnd Object containing the results of the game
     */
    public void displayWinner(GameEnd gameEnd)
    {
        HighScoreWindow scoreWindow = new HighScoreWindow(this, gameEnd);
        this.mainMenuBar.zweid.setEnabled(true);
        this.mainMenuBar.dreid.setEnabled(true);
        this.mainMenuBar.blue.setEnabled(true);

        this.mainMenuBar.newGame.setEnabled(true);
        this.mainMenuBar.start.setEnabled(false);
        this.mainMenuBar.stop.setEnabled(false);
        this.mainMenuBar.pause.setEnabled(false);
        this.mainMenuBar.disqualify.setEnabled(false);
        this.toolBar.newButton.setEnabled(true);
        this.toolBar.startButton.setEnabled(false);
        this.toolBar.stopButton.setEnabled(false);
        this.toolBar.pauseButton.setEnabled(false);
        this.toolBar.disqualifyButton.setEnabled(false);
    }

    /**
     * Display a timer for a robot
     *
     * @param robot the robot
     */
    public void startTimer(Robot robot)
    {
        // start the timer-thread
        this.robotTimer = new RobotTimer(robot, this);
        this.timerThread = new Thread(this.robotTimer);
        this.timerThread.start();
    }

    /**
     * Set the new skin for the gamefield
     *
     * @param skin The new Skin (a number from one to three).
     */
    public void setAppearance(int skin)
    {
        if (skin == 1)
            arenaPane.imagePathWalls = "src/de/uni_paderborn/robots/gui/images/skins/stone/";
        else if (skin == 2)
            arenaPane.imagePathWalls = "src/de/uni_paderborn/robots/gui/images/skins/redhat/";
        else
            arenaPane.imagePathWalls = "src/de/uni_paderborn/robots/gui/images/skins/bluesmile/";
    }

    /**
     * Stop a currently running timer
     */
    public void stopTimer()
    {
        // remove the timer-thread (and wait for it to terminate)
        if (this.robotTimer != null)
        {
            this.robotTimer.stopThread();
            try
            {
                this.timerThread.join();
            }
            catch (InterruptedException e)
            {}

        }

        // remove display
        if (timerDisplay != null)
        {
            this.toolBar.remove(timerDisplay);
            timerDisplay = null;
        }
	this.toolBar.revalidate();
	this.toolBar.repaint();
    }

    /**
    * Update the display of the timer within the MainWindow
    *
    * @param time the time to display (in seconds)
    */
    void displayTimer(String time)
    {
        // setup the display in the toolbar if necessary
        if (timerDisplay == null)
        {
            this.timerDisplay = new JButton();
            this.timerDisplay.setFont(new Font("SansSerif", Font.BOLD, 14));
            this.toolBar.add(timerDisplay);
        }

        if (robotTimer.getMin() >= 1 || robotTimer.getSec() >= 20)
            this.timerDisplay.setForeground(Color.red);
        else
            this.timerDisplay.setForeground(Color.black);

        this.timerDisplay.setText(time);
	this.toolBar.revalidate();
	this.toolBar.repaint();
    }

    /**
     * in the beginning there was main
     *
     * @param who knows?
     * @return who cares?
     */
    public static void main (String[] args)
    {
        WelcomeWindow welcome = new WelcomeWindow();
        // wait for the WelcomeWindow to close

        while (welcome.isVisible())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {}

        }

        welcome.dispose();
        MainWindow mainWindow = new MainWindow ();
    }
	/**
	 * @serial declaration
	 */
    MainMenuBar mainMenuBar;
	/**
	 * @serial declaration
	 */
    private JSplitPane hSplitPane;
	/**
	 * @serial declaration
	 */
    private JSplitPane vSplitPane;
	/**
	 * @serial declaration
	 */
    private JScrollPane arenaScrollPane;
	/**
	 * @serial declaration
	 */
    private JScrollPane logScrollPane;
	/**
	 * @serial declaration
	 */
    MainToolBar toolBar;
	/**
	 * @serial declaration
	 */
    private LogPane logPane;
	/**
	 * @serial declaration
	 */
    StatePane statePane;
	/**
	 * @serial declaration
	 */
    private ArenaPane arenaPane;
	/**
	 * @serial declaration
	 */
    Arena arena;
	/**
	 * @serial declaration
	 */
    private Thread arenaThread;
	/**
	 * @serial declaration
	 */
    private Thread timerThread;
	/**
	 * @serial declaration
	 */
    private RobotTimer robotTimer;
	/**
	 * @serial declaration
	 */
    private JButton timerDisplay;
	/**
	 * @serial declaration
	 */
    public static int H_SIZE = 20;
	/**
	 * @serial declaration
	 */
    public static int V_SIZE = 20;
}
