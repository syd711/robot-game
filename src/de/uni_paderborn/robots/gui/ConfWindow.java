package de.uni_paderborn.robots.gui;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.gui.*;

/**
 * Creates a new configurationdialog and displays it. The dialog
 * asks the user for all data needed to run the game. These data
 * are stored in a GameInit-object which is used and modified by the
 * ConfWindow.
 */

class ConfWindow extends JDialog
{
    /**
     * Creates a new ConfWindow-object and sets its default parameters.
     */
    ConfWindow (Frame owner, GameInit gameInit)
    {
        super(owner, "Configuration", true);

        try
        {
            this.mainWindow = (MainWindow)owner;
        }
        catch (ClassCastException e)
        {}


        this.gameInit = gameInit;

        this.setSize(400, 400);
        this.getContentPane().setLayout(new BorderLayout());

        this.getContentPane().add(makeTabPane(), BorderLayout.CENTER);
        this.getContentPane().add(makeButtons(), BorderLayout.SOUTH);

        this.setLocation(170, 150);
        this.show();
    }

    /**
     * Creates a tabbed pane which is used to get the input from the
     * user.
     */
    private JTabbedPane makeTabPane()
    {
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);

        // create the robots-panel
        JPanel robots = new JPanel();
        JButton button;
        robots.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // build the source list
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 1.0;
        sourceList = new JList();
        sourceList.setModel(new DefaultListModel());
        sourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel tmpModel = (DefaultListModel)sourceList.getModel();
        tmpModel.addElement("Survivor");
        tmpModel.addElement("Yeti");
        tmpModel.addElement("Explorer");
        tmpModel.addElement("Softies");
        tmpModel.addElement("Number5");
        tmpModel.addElement("SmartBot");
        tmpModel.addElement("Robi");
        tmpModel.addElement("PapstBot");
        tmpModel.addElement("LAR");
        robots.add(new JScrollPane(sourceList), c);

        // build the buttons to manipulate the lists of robots
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        button = new JButton(">>");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         Object tmp = sourceList.getSelectedValue();
                                         DefaultListModel destModel = (DefaultListModel)destList.getModel();
                                         if (tmp != null)
                                         {
                                             destModel.addElement(tmp);
                                         }
                                     }
                                 }
                                );
        robots.add(button, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        button = new JButton("Add all");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         DefaultListModel sourceModel = (DefaultListModel)sourceList.getModel();
                                         DefaultListModel destModel = (DefaultListModel)destList.getModel();

                                         Object[] sourceContents = sourceModel.toArray();

                                         for (int i = 0; i < sourceContents.length; i++)
                                         {
                                             destModel.addElement(sourceContents[i]);
                                         }
                                     }
                                 }
                                );
        robots.add(button, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        button = new JButton("Remove all");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         DefaultListModel destModel = (DefaultListModel)destList.getModel();
                                         while (!destModel.isEmpty())
                                         {
                                             destModel.remove(0);
                                         }
                                     }
                                 }
                                );
        robots.add(button, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        button = new JButton("<<");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         Object tmp = destList.getSelectedValue();
                                         DefaultListModel destModel = (DefaultListModel)destList.getModel();
                                         if (tmp != null)
                                         {
                                             destModel.removeElement(tmp);
                                         }
                                     }
                                 }
                                );
        robots.add(button, c);

        // build the destination list
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 1.0;
        destList = new JList();
        destList.setModel(new DefaultListModel());
        destList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        robots.add(new JScrollPane(destList), c);


        // create the options-panel
        JPanel options = new JPanel();
        options.setLayout(new GridLayout(5, 2));

        options.add(new JLabel("No. of Wells"));
        wells = new JTextField();
        wells.setText(Integer.toString(gameInit.noOfWells));
        options.add(wells);
        options.add(new JLabel("No. of Teleporterpairs"));
        teleporters = new JTextField();
        teleporters.setText(Integer.toString(gameInit.noOfTeleporters));
        options.add(teleporters);
        options.add(new JLabel("Width of Gamefield"));
        noOfFieldsX = new JTextField();
        noOfFieldsX.setText(Integer.toString(gameInit.noOfFieldsX));
        options.add(noOfFieldsX);
        options.add(new JLabel("Height of Gamefield"));
        noOfFieldsY = new JTextField();
        noOfFieldsY.setText(Integer.toString(gameInit.noOfFieldsY));
        options.add(noOfFieldsY);
        options.add(new JLabel("Energy of Robots"));
        noOfEnergy = new JTextField();
        noOfEnergy.setText(Integer.toString(gameInit.maxEnergy));
        options.add(noOfEnergy);

        // create robot-names-panel
        JPanel robotName = new JPanel();
        robotName.setLayout(new GridLayout(9, 2));
        for (int i = 0; i < robotNamesInput.length; i++)
        {
            robotName.add(new JLabel("Name of Robot " + (i + 1)));
            robotNamesInput[i] = new JTextField(robotNames[i]);
            robotName.add(robotNamesInput[i]);
        }



        JPanel addOns = new JPanel();
        addOns.setLayout(new GridLayout(3, 2));

        addOns.add(new JLabel("No. of Fata Morgana's"));
        fatamorgana = new JTextField();
        fatamorgana.setText(Integer.toString(gameInit.noOfFatamorganas));
        addOns.add(fatamorgana);
        addOns.add(new JLabel("No. of Taxmen"));
        taxmen = new JTextField();
        taxmen.setText(Integer.toString(gameInit.noOfTaxmans));
        addOns.add(taxmen);
        addOns.add(new JLabel("No. of Traders"));
        trader = new JTextField();
        trader.setText(Integer.toString(gameInit.noOfTraders));
        addOns.add(trader);



        tabPane.add("Robots", robots);
        tabPane.add("Options", options);
        tabPane.add("Names of Robots", robotName);
        tabPane.add("Add-On's", addOns);

        return tabPane;
    }

    /**
     * Creates the buttons to control the dialog. The user may
     * commit the changes ("OK") or abort. This method also
     * checks to input for consistency (within the ActionListeners).
     */
    private JPanel makeButtons()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton button;

        button = new JButton("OK");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         boolean inputOK = true;

                                         // check, if user has entered non-numbers
                                         try
                                         {
                                             String tmpString;

                                             tmpString = wells.getText();
                                             gameInit.noOfWells = Integer.parseInt(tmpString);
                                             tmpString = teleporters.getText();
                                             gameInit.noOfTeleporters = Integer.parseInt(tmpString);
                                             tmpString = noOfFieldsX.getText();
                                             gameInit.noOfFieldsX = Integer.parseInt(tmpString);
                                             tmpString = noOfFieldsY.getText();
                                             gameInit.noOfFieldsY = Integer.parseInt(tmpString);

                                             tmpString = noOfEnergy.getText();
                                             gameInit.maxEnergy = Integer.parseInt(tmpString);

                                             tmpString = trader.getText();
                                             gameInit.noOfTraders = Integer.parseInt(tmpString);

                                             tmpString = taxmen.getText();
                                             gameInit.noOfTaxmans = Integer.parseInt(tmpString);

                                             tmpString = fatamorgana.getText();
                                             gameInit.noOfFatamorganas = Integer.parseInt(tmpString);

                                         }
                                         catch (NumberFormatException exception)
                                         {
                                             inputOK = false;
                                             displayWarning("Please enter only numbers in the OPTIONS-panel");
                                         }

                                         // make sure, there are not _too_ make Items
                                         if (gameInit.noOfWells >
                                                 (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2)
                                         {
                                             inputOK = false;
                                             displayWarning("There must not be more than " +
                                                            (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2 +
                                                            " Wells in the Arena.");
                                         }


                                         if (gameInit.maxEnergy < 10)
                                         {
                                             inputOK = false;
                                             displayWarning("Please choose more than 10 energy points.");
                                         }


                                         if (gameInit.noOfTeleporters >
                                                 (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2)
                                         {
                                             inputOK = false;
                                             displayWarning("There must not be more than " +
                                                            (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2 +
                                                            " Teleporters in the Arena.");
                                         }

                                         // not too many and not too few robots, please
                                         if (destList.getModel().getSize() == 0)
                                         {
                                             inputOK = false;
                                             displayWarning("Please select at least one robot.");
                                         }
                                         if (destList.getModel().getSize() >
                                                 (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2)
                                         {
                                             inputOK = false;
                                             displayWarning("There must not be more than " +
                                                            (gameInit.noOfFieldsX*gameInit.noOfFieldsY)*0.2 +
                                                            " Roboters in the Arena.");
                                         }

                                         // read data of the robots names
                                         for (int i = 0; i < robotNamesInput.length; i++)
                                         {
                                             robotNames[i] = robotNamesInput[i].getText();
                                         }

                                         // create the robots
                                         HashSet myRobots = new HashSet();
                                         DefaultListModel destModel = (DefaultListModel)destList.getModel();
                                         Object[] destRobots = destModel.toArray();

                                         try
                                         {
                                             for (int i = 0; i < destRobots.length; i++)
                                             {
                                                 String tmp = (String)destRobots[i];
                                                 if (tmp == "Survivor")
                                                     myRobots.add(Class.forName(robotNames[0]).newInstance());
                                                 else if (tmp == "Yeti")
                                                     myRobots.add(Class.forName(robotNames[1]).newInstance());
                                                 else if (tmp == "Explorer")
                                                     myRobots.add(Class.forName(robotNames[2]).newInstance());
                                                 else if (tmp == "Softies")
                                                     myRobots.add(Class.forName(robotNames[3]).newInstance());
                                                 else if (tmp == "Number5")
                                                     myRobots.add(Class.forName(robotNames[4]).newInstance());
                                                 else if (tmp == "SmartBot")
                                                     myRobots.add(Class.forName(robotNames[5]).newInstance());
                                                 else if (tmp == "Robi")
                                                     myRobots.add(Class.forName(robotNames[6]).newInstance());
                                                 else if (tmp == "PapstBot")
                                                     myRobots.add(Class.forName(robotNames[7]).newInstance());
                                                 else
                                                     myRobots.add(Class.forName(robotNames[8]).newInstance());
                                             }
                                         }
                                         catch (Exception exception)
                                         {
//                                             inputOK = false;
//                                             displayWarning("Please use only valid names for the robots.");
                                         }


                                         gameInit.robots = myRobots;

                                         // if all is okay now, we may
                                         // leave (puhh, finally)
                                         if (inputOK)
                                         {
                                             gameInit.wasModified = true;

                                             mainWindow.mainMenuBar.zweid.setEnabled(false);
                                             mainWindow.mainMenuBar.dreid.setEnabled(false);
                                             mainWindow.mainMenuBar.blue.setEnabled(false);

                                             dispose();
                                         }
                                     }
                                 }
                                );
        buttonPanel.add(button, BorderLayout.WEST);

        button = new JButton("Cancel");
        button.addActionListener(new ActionListener()
                                 {
                                     public void actionPerformed (ActionEvent e)
                                     {
                                         gameInit.wasModified = false;

                                         mainWindow.toolBar.newButton.setEnabled(true);
                                         mainWindow.mainMenuBar.newGame.setEnabled(true);

                                         dispose();
                                     }
                                 }
                                );
        buttonPanel.add(button, BorderLayout.EAST);

        return buttonPanel;
    }

    /**
     * Shows a dialog which displays a given text. Used to display
     * warnings about wrong input-data
     */
    private void displayWarning(String warn)
    {
        final JDialog warning = new JDialog(this, "Warning", true);
        JButton ok = new JButton("OK");

        warning.getContentPane().add(new JLabel(warn), BorderLayout.CENTER);
        warning.getContentPane().add(ok, BorderLayout.SOUTH);
        ok.addActionListener(new ActionListener()
                             {
                                 public void actionPerformed (ActionEvent e)
                                 {
                                     warning.dispose();
                                 }
                             }
                            );
        warning.setSize(310, 100);
        warning.setLocation(250, 250);
        warning.show();
    }
	/**
 	* @serial GameInit declaration
 	*/ 	
    private GameInit gameInit;
	
	/**
 	* @serial MainWindow declaration
 	*/ 	
    private MainWindow mainWindow;
	
	/**
 	* @serial JComponent declaration
 	*/ 	
    private JList sourceList;
	/**
 	* @serial JComponent declaration
 	*/
    private JList destList;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField taxmen;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField fatamorgana;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField trader;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField wells;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField teleporters;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField noOfFieldsX;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField noOfFieldsY;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField noOfEnergy;
	/**
 	* @serial JComponent declaration
 	*/
    private JTextField[] robotNamesInput = new JTextField[9];
	/**
 	* @serial robot-names declaration
 	*/
    private String[] robotNames =
        { "de.uni_paderborn.robots.robot.group1.Survivor",
          "de.uni_paderborn.robots.robot.group2.Yeti",
          "de.uni_paderborn.robots.robot.group3.Explorer",
          "de.uni_paderborn.robots.robot.group4.Softies",
          "de.uni_paderborn.robots.robot.group5.Number5",
          "de.uni_paderborn.robots.robot.group6.SmartBot",
          "de.uni_paderborn.robots.robot.group7.Robi",
          "de.uni_paderborn.robots.robot.group8.PapstBot",
          "de.uni_paderborn.robots.robot.group9.LAR"
        };
}
