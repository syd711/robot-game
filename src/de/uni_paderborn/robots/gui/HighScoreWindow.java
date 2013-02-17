package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import de.uni_paderborn.robots.components.*;

/**
 * Displays a window that shows the games results
 */
class HighScoreWindow extends JDialog
{
    /*
     * Creates a new HighScoreWindow
     */
    HighScoreWindow (Frame owner, GameEnd gameEnd)
    {
        super(owner, "Highscore", true);

        this.winner = gameEnd.winner;

        this.setSize(400, 400);
        this.getContentPane().setLayout(new BorderLayout());

        // add image on top
	JPanel onTop = new JPanel();
	onTop.setBackground(Color.black);
	onTop.add(new JLabel(new ImageIcon("src/de/uni_paderborn/robots/gui/images/highscore/highscore.gif")));
        this.getContentPane().add(onTop, BorderLayout.NORTH);

        // create the JTable to display our results
        JTable myTable = new JTable();
        this.makeModel(myTable);
        this.getContentPane().add(new JScrollPane(myTable), BorderLayout.CENTER);

        // add the button
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed(ActionEvent e)
                                     {

                                         dispose();
                                     }
                                 }
                                );
        this.getContentPane().add(button, BorderLayout.SOUTH);

        myTable.setForeground(Color.white);
        myTable.setBackground(Color.black);
        this.show();
    }

    /**
     * Makes the TableModel which contains the results
     */
    private void makeModel(JTable myTable)
    {
        // create a model for our table
        TableModel dataModel = new AbstractTableModel()
                               {
                                   public int getColumnCount()
                                   {
                                       return titels.length;
                                   }

                                   public int getRowCount()
                                   {
                                       return winner.length;
                                   }

                                   public Object getValueAt(int row, int col)
                                   {
                                       return winner[row][col];
                                   }

                                   public String getColumnName(int col)
                                   {
                                       return titels[col];
                                   }
                               };

        myTable.setModel(dataModel);
    }
	/**
 	* @serial winner-string declaration
 	*/	
    private String[][] winner;
	/**
 	* @serial titel-string declaration
 	*/	
    private String[] titels = {"Rank", "Name", "Score"};
}
