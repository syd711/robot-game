package de.uni_paderborn.robots.gui;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.table.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.robot.group3.*;

/**
 * StatePane displays the current state of the game
 * (such as robots name, its score or its energy)
 */
class StatePane extends JPanel
{
	/**
	* Creates a new StatePane which contains two JPanels, one for the moving and one 
	* for the fix elements of the StatePane
	*/
    StatePane ()
    {
        this.setBackground(Color.black);
        setLayout(null);

        stateDraw = new StateDraw();
        stateDraw.setBounds(0, 0, 1200, 1200);
        add(stateDraw);

        stateField = new StateField();
        stateField.setBounds(0, 0, 1200, 1200);
        stateField.setPreferredSize(new Dimension(280, 600));
        add(stateField);
    }

    /**
     * Updates the displayed data via the given GameState-object
     */
    void updateState(GameState gameState)
    {
        if (!statePaneWasInitialized)
            initializeStatePane(gameState);
        stateDraw.updateStatePane(gameState);
        repaint();
    }
	
	/**
	 * Initializes the class StateField with the gameState-object of the first round
	 */
	
    private void initializeStatePane(GameState gameState)
    {
        this.gameState = gameState;
        stateField.stateFieldWasPainted = true;
        int dim = gameState.robots.size();
        setPreferredSize(new Dimension(280, 38*dim));
        stateField.updateStatePane(gameState);
        this.statePaneWasInitialized = true;
        revalidate();
        repaint();
    }

	/**
	 * Clears the display at the beginning of a game
	 */
	
    public void clearDisplay()
    {
        this.statePaneWasInitialized = false;
        stateField.myRobots = null;
        stateDraw.fullEnergy = 0;
        stateDraw.disqualifiedRobots = null;
    }

	/**
	 * Deletes a robot from the class StateDraw
	 *
	 * @param number The id of the disqualified robot which was chosen in the
	 * disqualify window
	 */
	 
    void setDisqualifiedID(int number)
    {
        stateDraw.disqualifiedRobots[number - 1] = number;
    }
	
	/**
 	* @serial StateDraw declaration
 	*/ 	
	private StateDraw stateDraw;
	
	/**
 	* @serial StateField declaration
 	*/ 	
    private StateField stateField;

	/**
 	* @serial If true, the array which contains the fix items was initialized
 	*/ 	
    private boolean statePaneWasInitialized = false;
	
	/**
 	* @serial path declaration
 	*/ 	
    private String imagePath = "src/de/uni_paderborn/robots/gui/images/";
	
	/**
 	* @serial GameState declaration
 	*/ 	
    private GameState gameState;
}
