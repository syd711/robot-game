package de.uni_paderborn.robots.gui;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.logic.Robot;

/**
 * DisqualifyWindow is responsible for showing a dialog used to
 * remove robots from the game
 */
class DisqualifyWindow extends JDialog
{
    /**
     * Create a new DisqualifyWindow
     *
     * @param owner The owning Frame myMainWindow The MainWindow of the game
     */
    public DisqualifyWindow(Frame owner, MainWindow myMainWindow)
    {
        super(owner, "Disqualify Robots", true);
        this.mainWindow = myMainWindow;
        arena = this.mainWindow.arena;
        this.setSize(250, 200);
        this.setLocation(150, 150);
        this.oldPauseState = arena.getPause();
        arena.setPause(true);

        this.addWindowListener(new WindowAdapter()
                               {
                                   public void windowClosing(WindowEvent e)
                                   {
                                       dispose();
                                       mainWindow.arena.setPause(oldPauseState);
                                   }
                               }
                              );

        JScrollPane listScrollPane = new JScrollPane(createListPane(createList()));

        Container contentPane = getContentPane();
        contentPane.add(listScrollPane, BorderLayout.CENTER);
        contentPane.add(makeButtons(), BorderLayout.SOUTH);

        this.show();
    }

    /**
     * Create a List of robots in the game from which
     * the user may chose a robot to remove
     *
     * @return The list of robots.
     */
    private JList createListPane(DefaultListModel listModel)
    {
        robotList = new JList(listModel);
        robotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        robotList.setCellRenderer(
            new DefaultListCellRenderer()
            {
                public Component getListCellRendererComponent(
                    JList list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus)
                {
                    Robot robot = (Robot)value;
                    if (isSelected)
                    {
                        this.setBackground(list.getSelectionBackground());
                        this.setForeground(list.getSelectionForeground());
                    }
                    else
                    {
                        this.setBackground(list.getBackground());
                        this.setForeground(list.getForeground());
                    }
                    this.setText(robot.getName());
                    return this;
                }
            }
        );
        robotList.setSelectedIndex(0);
        return (robotList);
    }

    /**
     * Create the lists model.
     *
     * @return The lists model.
     */
    private DefaultListModel createList()
    {
        listModel = new DefaultListModel();

        Iterator myIterator = arena.iteratorOfRobot();
        for (int i = 0; myIterator.hasNext(); i++)
        {
            Robot robot = (Robot) myIterator.next();

            Object name = robot;
            listModel.addElement(name);
        }
        return listModel;
    }

    /**
     * Creates a ButtonPane
     *
     * @return The Pane of buttons
     */
    private JPanel makeButtons()
    {
        disqualifyButton = new JButton("Disqualify");
        disqualifyButton.addActionListener(new ActionListener()
                                           {
                                               public void actionPerformed (ActionEvent e)
                                               {
                                                   Robot name = (Robot)robotList.getSelectedValue();
                                                   mainWindow.statePane.setDisqualifiedID(name.getId());
						   int index = robotList.getSelectedIndex();
                                                   listModel.remove(index);

                                                   int size = listModel.getSize();

                                                   if (size == 0)
                                                   {
                                                       //Nobody's left, disable disqualify.
                                                       disqualifyButton.setEnabled(false);
                                                   }
                                                   else
                                                   {
                                                       //Adjust the selection.
                                                       if (index == listModel.getSize())//removed item in last position
                                                           index--;
                                                       robotList.setSelectedIndex(index);              //otherwise select same index
                                                   }


                                                   mainWindow.arena.disqualify(name);
                                                   mainWindow.logMessage("Disqualify " + name.getName() + "\n");
                                                   dispose();
                                                   mainWindow.arena.setPause(oldPauseState);
                                               }
                                           }
                                          );

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener()
                                       {
                                           public void actionPerformed (ActionEvent e)
                                           {
                                               dispose();
                                               mainWindow.arena.setPause(oldPauseState);
                                           }
                                       }
                                      );
        JPanel buttonPane = new JPanel();
        buttonPane.add(disqualifyButton);
        buttonPane.add(cancelButton);
        return buttonPane;
    }
	/**
 	* @serial Arena declaration
 	*/
    private Arena arena;
	/**
 	* @serial DefaultListModel declaration
 	*/
    private DefaultListModel listModel;

	/**
 	* @serial MainWindow declaration
 	*/
	private MainWindow mainWindow;

	/**
 	* @serial JComponent declaration
 	*/
    private JList robotList;

	/**
 	* @serial JComponent declaration
 	*/
    private JButton disqualifyButton;

	/**
 	* @serial If true, the game was paused before the DisqualifyWindow was called
 	*/
    private boolean oldPauseState;

} // DisqualifyWindow
