package de.uni_paderborn.robots.gui;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.items.*;

    /**
     * Paints the fix items of the arena
     */
class GridField extends JPanel
{
	/**
	 * Creates a new GridField object
	 */
    GridField ()
    {
        setOpaque(false);
    }
	
	/**
	 * Is called once at the beginning of the game to paint all items, which do not
	 * change their position during a game.
	 */
    public void updateFixMatrix(ImageIcon [][] imageField)
    {
        this.imageField = imageField;
    }
	
	/**
	 * If called and a new game was started, this paint method creates an offImage,
	 *  paints all fix items on it and displays it.
	 */
    public void paintComponent(Graphics g)
    {
        if (this.imageField != null )
        {
            if (dontPaintMeAgain)
            {
                // check if a new game was started
				
				if ((offImage == null) || (xSize != imageField.length || ySize != imageField[1].length) )
                {
                    if (offImage != null) offImage.flush();
                    if (offGraphics != null) offGraphics.dispose();

                    xSize = imageField.length;
                    ySize = imageField[1].length;

                    offImage = this.createImage (xSize * 20 + 15, ySize * 20 + 15);

                    // make sure the background is black
                    offGraphics = offImage.getGraphics();
                    offGraphics.setColor(Color.black);
                    offGraphics.fillRect(0, 0, xSize * 20 + 15, ySize * 20 + 15);
                }
                for (int x = 0; x < xSize; x++)
                {
                    for (int y = 0; y < ySize; y++)
                    {
                        if (imageField[x][y] != null)
                        {
                            imageField[x][y].paintIcon(this, offGraphics, x*20, y*20);
                        }
                    } //for
                } // for
            }
			
			// offImage was painted? ok, then draw it
            g.drawImage (offImage, 0, 0, Color.black, null);
        }
    }
	/**
 	* @serial x-size declaration
 	*/
    private int xSize;
	
	/**
 	* @serial y-size declaration
 	*/
    private int ySize;
	
	/**
 	* @serial boolena declaration
 	*/
    public boolean dontPaintMeAgain = true;
	
	/**
 	* @serial ImageIcon declaration
 	*/
    private ImageIcon [][] imageField;
	
	/**
 	* @serial Image declaration
 	*/
    public Image offImage;
	
	/**
 	* @serial Graphics declaration
 	*/
    public Graphics offGraphics;

}
