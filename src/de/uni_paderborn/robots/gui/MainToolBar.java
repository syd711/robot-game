package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The games main toolbar.
 */
class MainToolBar extends JToolBar
{
    /**
     * Creates a new toolbar to display in the MainWindow
     *
     * @param myMainWindow The games MainWindow-instance.
     */
    MainToolBar (MainWindow myMainWindow)
    {
        // where is our main window?
        this.mainWindow = myMainWindow;

        newButton = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/new.gif"));
        newButton.setToolTipText("New Game");
        newButton.addActionListener(new ActionListener()
                                    {
                                        public void actionPerformed (ActionEvent e)
                                        {
                                            mainWindow.mainMenuBar.newGame.setEnabled(false);
                                            mainWindow.mainMenuBar.start.setEnabled(false);
                                            mainWindow.mainMenuBar.stop.setEnabled(true);
                                            mainWindow.mainMenuBar.pause.setEnabled(true);
                                            mainWindow.mainMenuBar.disqualify.setEnabled(true);
                                            newButton.setEnabled(false);
                                            startButton.setEnabled(false);
                                            stopButton.setEnabled(true);
                                            pauseButton.setEnabled(true);
                                            disqualifyButton.setEnabled(true);

                                            mainWindow.runGame();
                                        }
                                    }
                                   );
        this.add(newButton);

        startButton = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/start.gif"));
        startButton.setToolTipText("Start");
        startButton.addActionListener(new ActionListener()
                                      {
                                          public void actionPerformed (ActionEvent e)
                                          {
                                              if (mainWindow.arena != null)
                                                  mainWindow.arena.setPause(false);

                                              // adjust buttons
                                              startButton.setEnabled(false);
                                              stopButton.setEnabled(true);
                                              pauseButton.setEnabled(true);

                                              mainWindow.mainMenuBar.start.setEnabled(false);
                                              mainWindow.mainMenuBar.stop.setEnabled(true);
                                              mainWindow.mainMenuBar.pause.setEnabled(true);
                                          }
                                      }
                                     );
        this.add(startButton);

        pauseButton = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/pause.gif"));
        pauseButton.setToolTipText("Pause");
        pauseButton.addActionListener(new ActionListener()
                                      {
                                          public void actionPerformed (ActionEvent e)
                                          {
                                              if (mainWindow.arena != null)
                                                  mainWindow.arena.setPause(true);

                                              // adjust buttons
                                              startButton.setEnabled(true);
                                              stopButton.setEnabled(true);
                                              pauseButton.setEnabled(false);

                                              mainWindow.mainMenuBar.start.setEnabled(true);
                                              mainWindow.mainMenuBar.stop.setEnabled(true);
                                              mainWindow.mainMenuBar.pause.setEnabled(false);
                                          }
                                      }
                                     );
        this.add(pauseButton);

        stopButton = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/stop.gif"));
        stopButton.setToolTipText("Stop");
        stopButton.addActionListener(new ActionListener()
                                     {
                                         public void actionPerformed (ActionEvent e)
                                         {

                                             // kill the Arena if one exists
                                             if (mainWindow.arena != null)
                                                 mainWindow.arena.setStop();

                                             //make sure there is no timer
                                             //left over after the arena crashed
                                             mainWindow.stopTimer();

                                             mainWindow.mainMenuBar.newGame.setEnabled(true);
                                             mainWindow.mainMenuBar.start.setEnabled(false);
                                             mainWindow.mainMenuBar.stop.setEnabled(false);
                                             mainWindow.mainMenuBar.pause.setEnabled(false);
                                             mainWindow.mainMenuBar.disqualify.setEnabled(false);
                                             newButton.setEnabled(true);
                                             startButton.setEnabled(false);
                                             stopButton.setEnabled(false);
                                             pauseButton.setEnabled(false);
                                             disqualifyButton.setEnabled(false);
                                             mainWindow.mainMenuBar.zweid.setEnabled(true);
                                             mainWindow.mainMenuBar.dreid.setEnabled(true);
                                             mainWindow.mainMenuBar.blue.setEnabled(true);
                                         }
                                     }
                                    );
        this.add(stopButton);
        this.addSeparator();

        disqualifyButton = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/remove.gif"));
        disqualifyButton.setToolTipText("Disqualify");
        disqualifyButton.addActionListener(new ActionListener()
                                           {
                                               public void actionPerformed (ActionEvent e)
                                               {
                                                   if (mainWindow.arena != null)
                                                   {
                                                       DisqualifyWindow disqwindow = new DisqualifyWindow(mainWindow, mainWindow);

                                                   }
                                               }
                                           }
                                          );
        this.add(disqualifyButton);
        this.addSeparator();

        JButton b;
        b = new JButton (new ImageIcon("src/de/uni_paderborn/robots/gui/images/toolbar/info.gif"));
        b.setToolTipText("About Robots");
        b.addActionListener(new ActionListener()
                            {
                                public void actionPerformed (ActionEvent e)
                                {
                                    Legende legendWindow = new Legende(mainWindow);
                                }
                            }
                           );
        this.add(b);
        this.addSeparator();

        // adjust buttons
        this.newButton.setEnabled(true);
        this.startButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.disqualifyButton.setEnabled(false);
    }
	
	/**
 	* @serial JComponent declaration
 	*/
    private MainWindow mainWindow;
	/**
 	* @serial ImageIcon declaration
 	*/
    JButton newButton;
	/**
 	* @serial ImageIcon declaration
 	*/
    JButton startButton;
	/**
 	* @serial ImageIcon declaration
 	*/
    JButton stopButton;
	/**
 	* @serial ImageIcon declaration
 	*/
    JButton pauseButton;
	/**
 	* @serial ImageIcon declaration
 	*/
    JButton disqualifyButton;
}
