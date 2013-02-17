package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

/**
 * Shows a window with information about the game.
 */
class AboutWindow extends JDialog
{
    /**
     * Create a new AboutWindow-Object and display it
     *
     * @param owner The owner of AboutWindow
     */
    AboutWindow (Frame owner)
    {
        super(owner, "About Robots", true);

        // north, cold & icy
        JPanel onTop = new JPanel();
        onTop.setBackground(Color.black);
        onTop.add(new JLabel(new ImageIcon("src/de/uni_paderborn/robots/gui/images/about/robotgame.gif")));
        this.getContentPane().add(onTop, BorderLayout.NORTH);

        // center, somewhere in between
        JEditorPane display = new JEditorPane();
        display.setEditable(false);

        try
        {
            URL url = new URL("file:src/de/uni_paderborn/robots/gui/images/about/text.html");
            display.setPage(url);
        }
        catch (IOException e)
        {
            // hmmm, somethings wrong; don't know what to do
            dispose();
        }
        this.getContentPane().add(new JScrollPane(display), BorderLayout.CENTER);

        // gone south for the winter
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener()
                                 {
                                     public
                                     void
                                     actionPerformed (ActionEvent e)
                                     {
                                         dispose();
                                     }
                                 }
                                );
        this.getContentPane().add(button, BorderLayout.SOUTH);

        // ok, done. Now show it to the world
        this.setSize(500, 650);
        this.show();
    }
}
