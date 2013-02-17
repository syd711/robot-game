package de.uni_paderborn.robots.gui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * The games menubar to control the game
 */
class MainMenuBar extends JMenuBar
{
    /**
     * Creates a new menubar
     */
    MainMenuBar()
    {
        this.add(createGameMenu());
        this.add(createLookMenu());
        this.add(createSkinMenu());
        this.add(createAboutMenu());

        // adjust buttons
        this.newGame.setEnabled(true);
        this.start.setEnabled(false);
        this.stop.setEnabled(false);
        this.pause.setEnabled(false);
        this.disqualify.setEnabled(false);
        this.dreid.setSelected(true);
    }

    /**
     * Creates a submenu with controls to start, pause, stop a game and
     * to disqualify robots
     *
     * @return The Submenu
     */
    private JMenu createGameMenu()
    {

        JMenu file = new JMenu("Game");

        newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener()
                                  {
                                      public void actionPerformed (ActionEvent e)
                                      {
                                          MainWindow mainWindow = (MainWindow)getTopLevelAncestor();

                                          newGame.setEnabled(false);
                                          start.setEnabled(false);
                                          stop.setEnabled(true);
                                          pause.setEnabled(true);
                                          disqualify.setEnabled(true);
                                          mainWindow.toolBar.newButton.setEnabled(false);
                                          mainWindow.toolBar.startButton.setEnabled(false);
                                          mainWindow.toolBar.stopButton.setEnabled(true);
                                          mainWindow.toolBar.pauseButton.setEnabled(true);
                                          mainWindow.toolBar.disqualifyButton.setEnabled(true);

                                          mainWindow.runGame();
                                      }
                                  }
                                 );
        file.add(newGame);


        start = new JMenuItem("Start");
        start.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {
                                        MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                        if (mainWindow.arena != null)
                                            mainWindow.arena.setPause(false);

                                        // adjust buttons
                                        mainWindow.toolBar.startButton.setEnabled(false);
                                        mainWindow.toolBar.stopButton.setEnabled(true);
                                        mainWindow.toolBar.pauseButton.setEnabled(true);

                                        start.setEnabled(false);
                                        stop.setEnabled(true);
                                        pause.setEnabled(true);
                                    }
                                }
                               );
        file.add(start);


        pause = new JMenuItem("Pause");
        pause.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {
                                        MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                        if (mainWindow.arena != null)
                                            mainWindow.arena.setPause(true);

                                        // adjust buttons
                                        mainWindow.toolBar.startButton.setEnabled(true);
                                        mainWindow.toolBar.stopButton.setEnabled(true);
                                        mainWindow.toolBar.pauseButton.setEnabled(false);

                                        start.setEnabled(true);
                                        stop.setEnabled(true);
                                        pause.setEnabled(false);
                                    }
                                }
                               );
        file.add(pause);


        stop = new JMenuItem("Stop");
        stop.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                       MainWindow mainWindow = (MainWindow)getTopLevelAncestor();

                                       // kill the Arena if one exists
                                       if (mainWindow.arena != null)
                                           mainWindow.arena.setStop();

                                       //make sure there is no timer
                                       //left over after the arena crashed
                                       mainWindow.stopTimer();

                                       mainWindow.toolBar.newButton.setEnabled(true);
                                       mainWindow.toolBar.startButton.setEnabled(false);
                                       mainWindow.toolBar.stopButton.setEnabled(false);
                                       mainWindow.toolBar.pauseButton.setEnabled(false);
                                       mainWindow.toolBar.disqualifyButton.setEnabled(false);
                                       newGame.setEnabled(false);
                                       start.setEnabled(false);
                                       stop.setEnabled(false);
                                       pause.setEnabled(false);
                                       disqualify.setEnabled(false);
                                       zweid.setEnabled(true);
                                       dreid.setEnabled(true);
                                       blue.setEnabled(true);
                                   }
                               }
                              );
        file.add(stop);

        file.addSeparator();

        disqualify = new JMenuItem("Disqualify");
        disqualify.addActionListener(new ActionListener()
                                     {
                                         public void actionPerformed (ActionEvent e)
                                         {
                                             MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                             DisqualifyWindow disqwindow = new DisqualifyWindow(mainWindow, mainWindow);
                                         }
                                     }
                                    );
        file.add(disqualify);

        file.addSeparator();



        quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                       System.exit(0);
                                   }
                               }
                              );
        file.add(quit);
        return file;
    }

    /**
     * Returns the submenu the change the games L&F
     *
     * @return The submenu.
     */
    private JMenu createLookMenu()
    {


        JMenu look_feel = new JMenu("Look&Feel");

        metal = new JRadioButtonMenuItem("Metal");
        windows = new JRadioButtonMenuItem("Windows");
        motif = new JRadioButtonMenuItem("Motif");

        metal.setSelected(true);
        updateRadioButtons();

        windows.addActionListener(new ActionListener()
                                  {
                                      public void actionPerformed (ActionEvent e)
                                      {
                                          try
                                          {
                                              UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                                              SwingUtilities.updateComponentTreeUI((Frame)getTopLevelAncestor());
                                              getTopLevelAncestor().validate();
                                              updateRadioButtons();
                                          }
                                          catch (Exception exc)
                                          {
                                              updateRadioButtons();
                                          }

                                      }
                                  }
                                 );


        metal.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {

                                        try
                                        {
                                            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                                            SwingUtilities.updateComponentTreeUI((Frame)getTopLevelAncestor());
                                            getTopLevelAncestor().validate();
                                            updateRadioButtons();
                                        }
                                        catch (Exception exc)
                                        {
                                            updateRadioButtons();
                                        }


                                    }
                                }
                               );


        motif.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {
                                        try
                                        {
                                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                                            SwingUtilities.updateComponentTreeUI((Frame)getTopLevelAncestor());
                                            getTopLevelAncestor().validate();
                                            updateRadioButtons();
                                        }
                                        catch (Exception exc)
                                        {
                                            updateRadioButtons();
                                        }


                                    }
                                }
                               );



        look_feel.add(windows);
        look_feel.add(metal);
        look_feel.add(motif );

        return look_feel;
    }

    /**
     * Updates the selections in the L&F-menu upon change
     */
    public void updateRadioButtons()
    {
        String lnfName = UIManager.getLookAndFeel().getClass().getName();

        metal.setSelected(false);
        windows.setSelected(false);
        motif.setSelected(false);

        if (lnfName.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
        {
            metal.setSelected(false);
            windows.setSelected(true);
            motif.setSelected(false);
        }

        if (lnfName.equals("javax.swing.plaf.metal.MetalLookAndFeel"))
        {
            metal.setSelected(true);
            windows.setSelected(false);
            motif.setSelected(false);
        }

        if (lnfName.equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel"))
        {
            metal.setSelected(false);
            windows.setSelected(false);
            motif.setSelected(true);
        }


    }

    /**
     * Submenu to change the gamefields skin
     *
     * @return The submenu.
     */
    private JMenu createSkinMenu()
    {

        JMenu appearance = new JMenu("Skin");

        zweid = new JRadioButtonMenuItem("Rolling Stone");
        zweid.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {
                                        MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                        mainWindow.setAppearance(1);
                                        updateSkinButtons(1);
                                    }
                                }
                               );

        dreid = new JRadioButtonMenuItem("Red Hat");
        dreid.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed (ActionEvent e)
                                    {
                                        MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                        mainWindow.setAppearance(2);
                                        updateSkinButtons(2);
                                    }
                                }
                               );

        blue = new JRadioButtonMenuItem("Blue Smiling Planet");
        blue.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                       MainWindow mainWindow = (MainWindow)getTopLevelAncestor();
                                       mainWindow.setAppearance(3);
                                       updateSkinButtons(3);
                                   }

                               }
                              );

        appearance.add(zweid);
        appearance.add(dreid);
        appearance.add(blue);

        return appearance;
    }

    /**
     * Updates the skin-buttons upon change.
     */
    private void updateSkinButtons(int buttonNumber)
    {
        zweid.setSelected(false);
        dreid.setSelected(false);
        blue.setSelected(false);

        if (buttonNumber == 1)
        {
            zweid.setSelected(true);
        }

        if (buttonNumber == 2)
        {
            dreid.setSelected(true);
        }

        if (buttonNumber == 3)
        {
            blue.setSelected(true);
        }
    }

    /**
     * Creates the submenu to display the help- and about-windows.
     *
     * @return The submenu.
     */
    private JMenu createAboutMenu()
    {
        JMenuItem aboutItem;
        JMenuItem help;
        JMenu about = new JMenu("Help");

        aboutItem = new JMenuItem("About Robots");
        aboutItem.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed (ActionEvent e)
                                        {
                                            AboutWindow aboutWindow = new AboutWindow((Frame)getTopLevelAncestor());
                                        }
                                    }
                                   );

        help = new JMenuItem("Icons");
        help.addActionListener(new ActionListener()
                               {
                                   public void actionPerformed (ActionEvent e)
                                   {
                                       HelpWindow helpWindow = new HelpWindow((Frame)getTopLevelAncestor());
                                   }
                               }
                              );


        about.add(help);
        about.add(aboutItem);

        return about;
    }
	
	/**
 	* @serial JComponent declaration
 	*/
    private JRadioButtonMenuItem windows;
	/**
 	* @serial JComponent declaration
 	*/
    private JRadioButtonMenuItem metal;
	/**
 	* @serial JComponent declaration
 	*/
    private JRadioButtonMenuItem motif ;
	
	/**
 	* @serial JComponent declaration
 	*/
    JRadioButtonMenuItem zweid;
	
	/**
 	* @serial ImageIcon declaration
 	*/
    JRadioButtonMenuItem dreid;
	
	/**
 	* @serial ImageIcon declaration
 	*/
    JRadioButtonMenuItem blue;
	
	/**
 	* @serial ImageIcon declaration
 	*/

    JMenuItem newGame;
	/**
 	* @serial ImageIcon declaration
 	*/
    JMenuItem start;
	
	/**
 	* @serial ImageIcon declaration
 	*/
	JMenuItem stop;
	/**
 	* @serial ImageIcon declaration
 	*/
    JMenuItem pause;
	/**
 	* @serial ImageIcon declaration
 	*/
    JMenuItem disqualify;
	
	/**
 	* @serial ImageIcon declaration
 	*/

    private JMenuItem quit;
}
