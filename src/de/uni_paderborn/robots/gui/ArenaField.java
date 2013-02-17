package de.uni_paderborn.robots.gui;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;

/**
 * Paints the fix elements of the arena on a JPanel.
 */
 
class ArenaField extends JPanel
{
	/**
	* creates an ArenaField object
	*/ 

    ArenaField ()
    {
        setOpaque(false);
    }
		
    /**
	 * Is called every round to update the position of moveable items.
	 *
     * @param imageField The Array contains the matrix of the fix items
     */
    public void updateMovingMatrix(ImageIcon [][] imageField)
    {
        this.imageField = imageField;
        repaint();
    }
	
	/**
	 * Displays the content of the array imageField which is initialized with
	 * ImageIcons.
	 */

    public void paintComponent( Graphics g)
    {
        if (imageField != null)
        {
            super.paintComponent(g);

            int xSize = imageField.length;
            int ySize = imageField[1].length;

            for (int x = 0; x < xSize; x++)
            {
                for (int y = 0; y < ySize; y++)
                {
                    if (imageField[x][y] != null)
                    {
                        imageField[x][y].paintIcon(this, g, x*20, y*20);
                    }
                } //for
            } //
        }

    }
 /**
  * @serial ImageIcon[][] declaration
  */
  private ImageIcon [][] imageField;   
}
