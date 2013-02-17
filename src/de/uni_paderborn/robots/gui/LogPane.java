package de.uni_paderborn.robots.gui;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Logs any message to a JTextArea.
 */
class LogPane extends JTextArea
{
    LogPane (JScrollPane logScrollPane)
    {
        super(1, 1);
        this.logScrollPane = logScrollPane;
        this.setEditable(false);
        this.setAutoscrolls(true);
        this.logMessage("Log created\n");
    }

    /**
     * Adds message to the log. Whenever the log gets longer than
     * 200 lines, the first lines are removed.
     *
     * @param message String to be appended to the log.
     */
    void logMessage(String message)
    {
        this.append(message);
        this.cleanUpLog();

        //make sure, our scrollbar is at the log's end
        JScrollBar tmpBar = this.logScrollPane.getVerticalScrollBar();
        tmpBar.setValue(tmpBar.getMaximum());

        return ;
    }

    /**
     * Remove the first lines from the log when it
     * contains more than 200.
     */
    private void cleanUpLog()
    {
        while (this.getLineCount() > 500)
        {
            // delete the first line
            this.replaceRange("", 0, 1);
        }
        return ;
    }

    /**
     * Clear whatever data is displayed
     */
    public void clearDisplay()
    {
        this.setText(null);
        this.repaint();
    }
	/**
 	* @serial JComponent declaration
 	*/
    private JScrollPane logScrollPane;
}
