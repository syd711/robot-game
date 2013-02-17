package de.uni_paderborn.robots.robot.group3;
import de.uni_paderborn.robots.items.*;

class Node
{
    /**
     * neighbor node in north of this node
     */
    public Node north = null;

    /**
     * neighbor node in south of this node
     */
    public Node south = null;

    /**
     * neighbor node in east of this node
     */
    public Node east = null;

    /**
     * neighbor node in west of this node
     */
    public Node west = null;

    /**
     * neighbor node connected by teleporter
     */
    public Node jumpGate = null;

    /**
     * item allocated to this node
     */
    public Item item = null;

    /**
     * x-value of the virtual array
     * needed for recognition of known fields
     */
    public int x;

    /**
     * y-value of the virtual array
     * needed for recognition of known fields
     */
    public int y;

    /**
     * distance to source node of BFS
     */
    public int distanceToSource;

    /**
     * needed for BFS (0=white,1=gray,2=black)
     */
    public int color;

    /**
     * arrow pointing to source node of BFS
     *  bot will take shortest way by using the arrows
     */
    public int preNode;
    /**
     * id-number of node
     */
    public int id;
	/**
	 * allocates node to partial-graph
	 */
    public int graph;
} // end of class node


