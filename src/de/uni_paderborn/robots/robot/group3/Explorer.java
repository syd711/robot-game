package de.uni_paderborn.robots.robot.group3;

import de.uni_paderborn.robots.actions.*;
import de.uni_paderborn.robots.items.*;
import de.uni_paderborn.robots.components.*;
import de.uni_paderborn.robots.logic.*;
import de.uni_paderborn.robots.*;

import java.util.*;


/**
 * class Explorer
 */
public class Explorer extends Robot
{

    /**
     * Constructor
     */
    public Explorer()
    {
        setName ("Explorer IV");
    }

    // start of definition

    /**
     * Field the robot is on
     */
    private Node whereIAm = new Node();
    /**
     * node containing field in front of robot
     */
    private Node inFrontOfMe = new Node();
    /**
     * vector containing found teleporters 
     */
    Vector listOfTeleport = new Vector(0, 1);
    /**
     * vector containing found exits
     */
    Vector listOfExit = new Vector(0, 1);
    /**
     * vector containig found wells 
     */
    Vector listOfWell = new Vector(0, 1);
    /**
     * vector for found unknown items
     */
    Vector listOfUnknownItems = new Vector(0, 1);
    // more vectors containing additional items created by our group

    /**
     * did bot use a teleporter during last turn
     */
    boolean tookTeleport = false;
    /**
     * did bot use a well during last turn
     */
    boolean tookWell = false;
    /**
     * true if at least one teleporter is known	 
     */
    boolean foundTeleporter = false;
    /**
     * true if at least one exit is known
     */
    boolean foundExit = false;
    /**
     * flag set during put card to side and turn back again
     */
    boolean awayCard = false;
    /**
     * approximated maximum of reachable points
     */
    int maxPoints;
    /**
     * current points of robot
     */
    int currentPoints;
    /**
     * 0 = local mode / 1 = global mode / 2 = exit mode / 3 = look for teleporter 
	 * / 4 = crawling along the wall
     */
    int mode = 0;
    /**
     * vector containing next actions of the robot
     */
    Vector way = new Vector(0, 1);
    /**
     *  vector recording the last 20 steps
     */
    Vector metWall = new Vector(0, 1);
    /**
     * counts rounds since big bang
     */
    int timer;
    /**
     * stores value of maximal energy
     */
    private int maxEnergy;
    /**
     * value representing white 
     * needed for bfs
     */
    private int zero = 0;
    /**
     * true if the robot has been initialized
     */
    private boolean init = false;
    /**
     * bot doesn't take cards during its magicmove
     */
    boolean cardBlocked = false;
    /**
     * current Node during BFS
     */
    Node tempNode = new Node();
    /**
     * node containing nearest exit
     */
    Node nearestExit = new Node();
    /**
     * node containing nearest well
     */
    Node nearestWell = new Node();
    /**
     * node containing teleporter the robot took last time
     */
    Node lastTeleport = new Node();
    /**
     * second teleporter used to verify teleportation
     */
    Teleport secondTeleport = new Teleport();
    /**
     * id-number of current graph (used for reunion of graphs)
     */
    int currentGraph = 0;
    /**
     * don't now any more
     */
    int robotInFront = 0;
    /**
	 * maximum numbers of steps the robot stays in maze-mode 
	 */
    int maxValue;
    /**
     * calculates the next action of the robot.
     *
     * @return Next action
     */
    public AbstractAction getCommand()
    {
        int amount = 0;
        timer++;

        if (!init)
        {
            maxPoints = getMaximumPoints();
            maxEnergy = this.getEnergy();
            whereIAm = new Node();
            whereIAm.x = 0;
            whereIAm.y = 0;
            whereIAm.item = null;
            whereIAm.id = this.getArena().getIdOfCurrentField(this);
            whereIAm.graph = 0;
            makePattern(1);
            way.add(0, new MoveAction());
            mode = 0;
            init = true;
        }


        if (tookTeleport)
        {
            // landed in known area
            currentGraph++;
            { 
                // new graph is created & switching to mode 3
                whereIAm = new Node();
                whereIAm.x = 0;
                whereIAm.y = 0;
                whereIAm.item = null;
                whereIAm.id = this.getArena().getIdOfCurrentField(this);
                whereIAm.graph = currentGraph;
                whereIAm.preNode = 2;
                mode = 3;
            }
            tookTeleport = false;
        }
        else
        {
            // update metWall
            if (this.getArena().getItemInFrontOfRobot(this) instanceof Wall)
                metWall.addElement( new Boolean(true));
            else
                metWall.addElement( new Boolean(false));
            if (metWall.capacity() > 20)
        	  { 	    
                metWall.removeElementAt(0);
                metWall.trimToSize();
        	  }    
            if ((this.getArena().getIdOfCurrentField(this)) == inFrontOfMe.id)       
           {
                whereIAm = inFrontOfMe;
            }
            if (cardBlocked == true)
                cardBlocked = false;
            if (!way.isEmpty() && (AbstractAction)way.firstElement() instanceof PutAction)
                cardBlocked = true;
        }

        way.trimToSize();
        if (way.capacity() <= 1)
        {
            if (mode == 0)
            {
                mode = 1;
                makePattern(9);
            }
            else if (mode == 1)
            {
                mode = 0;
                Random randomTemp = new Random();
                if ((randomTemp.nextInt(100) % 2) == 0)
                    makePattern(1);
                else
                    makePattern(2);
            }
        }
        else
        {
            way.removeElementAt(0);
            way.trimToSize();
        }


        // create new node in front of robot
        // always do this even in mode 2
        inFrontOfMe = new Node();
        inFrontOfMe.id = this.getArena().getIdOfFieldInFront(this);
        inFrontOfMe.color = zero + 1;
        inFrontOfMe.preNode = 1 ;
        inFrontOfMe.graph = currentGraph;
        this.setCoordinates(inFrontOfMe, whereIAm, this.getDirection());
		
        inFrontOfMe.item = (Item)(this.getArena().getItemInFrontOfRobot(this));

        // looking for teleporter after using it
        if ((mode == 3) && (this.getArena().getItemInFrontOfRobot(this) instanceof Teleport))
        {
            if ( this.getArena().getItemInFrontOfRobot(this) == (Teleport)secondTeleport)  //teleportation ok
            {

                inFrontOfMe.jumpGate = lastTeleport;
                lastTeleport.jumpGate = inFrontOfMe;
         		fixArray(whereIAm);
                modifyVector(14);
                mode = 0;
            }
            else if (this.getArena().getIdOfFieldInFront(this) == lastTeleport.id)    // teleportation failed
            {
                inFrontOfMe = lastTeleport;
                mode = 0;
            }

            else
            { // new Teleporter found
                listOfTeleport.add (inFrontOfMe);
            }
        }


        

        // calculating wall-density
        for (int counter = 0; counter < metWall.capacity(); counter++)
        {
            if (((Boolean)metWall.elementAt(counter)).booleanValue())
                amount++;
        }
        if (amount >= 5)
        {
            mode = 4;
            maxValue = timer + 50;
        }


        if (mode != 2)
        {
            // calculating nearest exit
            nearestExit = null;
            if (!listOfExit.isEmpty())
            {
                nearestExit = (Node)listOfExit.elementAt(0);             // searching nearest known exit
                for (int i = 0; i < listOfExit.capacity(); i++)
                {
                    if (((Node)listOfExit.elementAt(i)).distanceToSource < nearestExit.distanceToSource)
                        nearestExit = ((Node)listOfExit.elementAt(i));
                }
            }


            // calculating nearest well
            nearestWell = null;
            if (!listOfWell.isEmpty())
            {
                nearestWell = (Node)listOfWell.elementAt(0);             // searching nearest known well
                for (int i = 0; i < listOfWell.capacity(); i++)
                {
                    if (((Node)listOfWell.elementAt(i)).distanceToSource <= nearestWell.distanceToSource)
                        nearestWell = ((Node)listOfWell.elementAt(i));

                }
            }
        }

        // points over 75% of maximum and  just enough energy to reach nearest well
        if ( currentPoints > (0.75 * maxPoints) && (mode != 3) )
        {
            if ((nearestExit != null ) && (nearestExit.distanceToSource < (this.getEnergy() + 10 )) )
            {
                mode = 2;
                BFS(nearestExit);
            }
            else
            {
                mode = 0;
                makePattern(1);
            }
        }


        // energylevel below 40% or just enough energy to reach nearest well


        if ( (mode != 3) && ( (this.getEnergy() < 0.40 * maxEnergy) ||
                              ( (nearestWell != null) && (nearestWell.distanceToSource > (this.getEnergy() + 10)))))
        {
            if (nearestWell != null)
            {
                mode = 2;
                BFS(nearestWell);
            }
            else
            {
                mode = 0;
                makePattern(1);
            }
        }



        if ( (mode != 2) )
            BFS(whereIAm);
        way.trimToSize();

        switch (mode)
        {
                case 0 : return localMode();
                case 1 : return globalMode();
                case 2 : return exitMode();
                case 3 : return new RotateAction((this.getDirection() % 4) + 1);
                case 4 : return mazeMode();
                default : return new MoveAction();
        }

    } // getCommand


    /**
     * binding node "InFrontOfMe" (and other smart things)
     *
     * @param sourceNode startingpoint
     */
    private void BFS(Node sourceNode)
    {
        boolean errorFoundInVirtualArray = false;
        boolean surroundTeleport = false;
        Vector queue = new Vector(0, 1);
        zero = zero + 2;
        int s = 0;
        sourceNode.color = zero + 1;
        sourceNode.distanceToSource = -1;
        sourceNode.preNode = -1;
        enqueue(queue, sourceNode);
        while (!queue.isEmpty())
        {

            tempNode = (Node) queue.remove(0);   // read out first element

            // surrounding teleport
            if (tempNode.item instanceof Teleport)
            {
                if ( (tempNode.north != null) && (tempNode.north.color > zero) )
                    surroundTeleport = true;
                if ( (tempNode.west != null) && (tempNode.west.color > zero) )
                    surroundTeleport = true;
                if ( (tempNode.south != null) && (tempNode.south.color > zero) )
                    surroundTeleport = true;
                if ( (tempNode.east != null) && (tempNode.east.color > zero) )
                    surroundTeleport = true;
                if ( (tempNode.jumpGate != null) && (tempNode.jumpGate.color > zero) )
                    surroundTeleport = false;
            }


            if ( ( (tempNode.north != null) && (tempNode.north.color == (zero + 2)) && (tempNode.north.preNode != tempNode.preNode) ) ||
                    ( (tempNode.south != null) && (tempNode.south.color == (zero + 2)) && (tempNode.south.preNode != tempNode.preNode) ) ||
                    ( (tempNode.west != null) && (tempNode.west.color == (zero + 2)) && (tempNode.west.preNode != tempNode.preNode) ) ||
                    ( (tempNode.east != null) && (tempNode.east.color == (zero + 2)) && (tempNode.east.preNode != tempNode.preNode) ) )
                tempNode.distanceToSource++; 	 // corrects calculation of energy consumption caused by rotation



            // northern neighbour
            if ( (tempNode.north != null) && (tempNode.north.color <= zero) &&
                    ( (tempNode == sourceNode) || ( tempNode.item == null) ||
                      ( ((Item)(tempNode.item) instanceof Teleport ) && (!surroundTeleport) ) ))
            {
                tempNode.north.color = zero + 1;                                       // marks node for open
                tempNode.north.distanceToSource = tempNode.distanceToSource + 1;       // increments distance to source by one
                tempNode.north.preNode = 3;                                            // points to south
                enqueue(queue, tempNode.north);                                        // adds node to queue
            }

            // southern neighbour

            if ( (tempNode.south != null) && (tempNode.south.color <= zero) && ((tempNode == sourceNode) ||
                    ( tempNode.item == null) || (((Item)(tempNode.item) instanceof Teleport )) && (!surroundTeleport) ) )
            {
                tempNode.south.color = zero + 1;
                tempNode.south.distanceToSource = tempNode.distanceToSource + 1;
                tempNode.south.preNode = 1;     // points to north
                enqueue(queue, tempNode.south);
            }

            // eastern neighbour
            if ( (tempNode.east != null) && (tempNode.east.color <= zero) &&
                    ((tempNode == sourceNode) || ( tempNode.item == null) ||
                     ( ((Item)(tempNode.item) instanceof Teleport )) && (!surroundTeleport) ) )
            {
                tempNode.east.color = zero + 1;
                tempNode.east.distanceToSource = tempNode.distanceToSource + 1;
                tempNode.east.preNode = 2;     // points to west
                enqueue(queue, tempNode.east);
            }

            // western neighbour
            if ( (tempNode.west != null) && (tempNode.west.color <= zero) &&
                    ((tempNode == sourceNode) || ( tempNode.item == null) ||
                     ( ((Item)(tempNode.item) instanceof Teleport )) && (!surroundTeleport) ) )
            {
                tempNode.west.color = zero + 1;
                tempNode.west.distanceToSource = tempNode.distanceToSource + 1;
                tempNode.west.preNode = 4;     // points to east
                enqueue(queue, tempNode.west);
            }


            if ( (tempNode.jumpGate != null) && (tempNode.jumpGate.color <= zero) &&
                    ((tempNode == sourceNode) || ( tempNode.item == null) || ((Item)(tempNode.item) instanceof Teleport ) ) )
            {
                tempNode.jumpGate.color = zero + 1;
                tempNode.jumpGate.distanceToSource = tempNode.distanceToSource + 1;
                enqueue(queue, tempNode.jumpGate);
            }



            surroundTeleport = false;       // very important line of code
            tempNode.color = zero + 2;  // marks current node for closed

            if ( tempNode.id == inFrontOfMe.id)
            {
            	  if (tempNode.graph != inFrontOfMe.graph)
                {
                    inFrontOfMe = tempNode;
                    inFrontOfMe.graph = whereIAm.graph;
					
                    //infrontofme <-> whereiam                    
                    switch(this.getDirection())
                	  {
                	       case 1 :{ 
                	       	        whereIAm.north = inFrontOfMe; 	
                	       	        inFrontOfMe.south = whereIAm;
                	       	        break;
                	               } 
                	       case 2 :{ 
                	       	        whereIAm.west = inFrontOfMe; 	
                	       	        inFrontOfMe.east = whereIAm;
                	       	        break;
                	               } 
                	       case 3 :{ 
                	       	        whereIAm.south = inFrontOfMe; 	
                	       	        inFrontOfMe.north = whereIAm;
                	       	        break;
                	               } 
                	       case 4 :{ 
                	       	        whereIAm.east = inFrontOfMe; 	
                	       	        inFrontOfMe.west = whereIAm;
                	       	        break;
                	               } 
                	  }		
                    	
                    fixArray(whereIAm);
                }

            }

            // tempNode in north
            if ( (tempNode.x == inFrontOfMe.x) && (tempNode.y == inFrontOfMe.y + 1 ) && (tempNode.graph == inFrontOfMe.graph) )
            {
                if ( (tempNode.south != null) && (tempNode.south.jumpGate != null) )
                {
                    inFrontOfMe.jumpGate = tempNode.south.jumpGate;    // binding node linked by teleporter
                    tempNode.south.jumpGate.jumpGate = inFrontOfMe;
                }
                if (tempNode.south != null)
                {
                    inFrontOfMe.color = tempNode.south.color;
                    inFrontOfMe.preNode = tempNode.south.preNode;
                }
                inFrontOfMe.north = tempNode;
                tempNode.south = inFrontOfMe;
            }

            if ( (tempNode.x == inFrontOfMe.x + 1 ) && (tempNode.y == inFrontOfMe.y) && (tempNode.graph == inFrontOfMe.graph) )
            {
                if ( (tempNode.west != null) && (tempNode.west.jumpGate != null) )
                {
                    inFrontOfMe.jumpGate = tempNode.west.jumpGate;
                    tempNode.west.jumpGate.jumpGate = inFrontOfMe;
                }
                if (tempNode.west != null)
                {
                    inFrontOfMe.color = tempNode.west.color;
                    inFrontOfMe.preNode = tempNode.west.preNode;
                }
                inFrontOfMe.east = tempNode;
                tempNode.west = inFrontOfMe;
            }
            if ( (tempNode.x == inFrontOfMe.x) && (tempNode.y == inFrontOfMe.y - 1 ) && (tempNode.graph == inFrontOfMe.graph) )
            {
                if ( (tempNode.north != null) && (tempNode.north.jumpGate != null) )
                {
                    inFrontOfMe.jumpGate = tempNode.north.jumpGate;
                    tempNode.north.jumpGate.jumpGate = inFrontOfMe;
                }
                if (tempNode.north != null)
                {
                    inFrontOfMe.color = tempNode.north.color;
                    inFrontOfMe.preNode = tempNode.north.preNode;
                }
                inFrontOfMe.south = tempNode;
                tempNode.north = inFrontOfMe;
            }
            if ( (tempNode.x == inFrontOfMe.x - 1 ) && (tempNode.y == inFrontOfMe.y) && (tempNode.graph == inFrontOfMe.graph) )
            {
                if ( (tempNode.east != null) && (tempNode.east.jumpGate != null) )
                {
                    inFrontOfMe.jumpGate = tempNode.east.jumpGate;
                    tempNode.east.jumpGate.jumpGate = inFrontOfMe;
                }
                if (tempNode.east != null)
                {
                    inFrontOfMe.color = tempNode.east.color;
                    inFrontOfMe.preNode = tempNode.east.preNode;
                }
                inFrontOfMe.west = tempNode;
                tempNode.east = inFrontOfMe;
            }

            s++;

        } // END OF WHile


        if (errorFoundInVirtualArray)
        {
            fixArray(whereIAm);
            errorFoundInVirtualArray = false;
        }
    } // end of BFS


    /**
     * adds node to queue
     *
     * @ param snake Queue vertex Node
     */
    private void enqueue(Vector snake, Node vertex)
    {
        snake.addElement(vertex);
    }
    /**
     * removes node from queue
     * @param snake Queue
     */
    private void dequeue(Vector snake)
    {
        snake.removeElementAt(0);              // remove first element
        snake.trimToSize();                    // delete spaces from queue
    }

    /**
     * sets coordinates of first node by using coordinates of the second
     * @param node to work on, node which gives the coordinates
     */
    private void setCoordinates(Node workNode, Node setNode, int tmpDirection)
    {
        switch (tmpDirection)
        {
                case 4:
                {
                    workNode.x = setNode.x + 1;
                    workNode.y = setNode.y;
                    break;
                }
                case 3:
                {
                    workNode.x = setNode.x;
                    workNode.y = setNode.y - 1;
                    break;
                }
                case 2:
                {
                    workNode.x = setNode.x - 1;
                    workNode.y = setNode.y;
                    break;
                }
                case 1:
                {
                    workNode.x = setNode.x;
                    workNode.y = setNode.y + 1;
                    break;
                }
                default :
                break;
        }
    }

    /**
      * graph is covered with a new virtual array
      * @param starting node
      */
    private void fixArray(Node sourceNode)
    {
        Vector queue = new Vector(0, 1);
        zero = zero + 2;
        int s = 0;
        sourceNode.color = zero + 1;
        currentGraph = sourceNode.graph;
        enqueue(queue, sourceNode);

        while (!queue.isEmpty())
        {
            tempNode = (Node) queue.remove(0);   // read out first node of queue

            if ( (tempNode.north != null) && (tempNode.north.color <= zero) )
            {
                setCoordinates(tempNode.north, tempNode, NORTH);
                tempNode.north.graph = currentGraph;
                tempNode.north.color = zero + 1;                                       // paints the node gray
                enqueue(queue, tempNode.north);                                        // adds node to queue
            }

            if ( (tempNode.south != null) && (tempNode.south.color <= zero) )
            {
                setCoordinates(tempNode.south, tempNode, SOUTH);
                tempNode.south.graph = currentGraph;
                tempNode.south.color = zero + 1;
                enqueue(queue, tempNode.south);
            }
            if ( (tempNode.east != null) && (tempNode.east.color <= zero) )
            {
                setCoordinates(tempNode.east, tempNode, EAST);
                tempNode.east.graph = currentGraph;
                tempNode.east.color = zero + 1;
                enqueue(queue, tempNode.east);
            }
            if ( (tempNode.west != null) && (tempNode.west.color <= zero) )
            {
                setCoordinates(tempNode.west, tempNode, WEST);
                tempNode.west.graph = currentGraph;
                tempNode.west.color = zero + 1;
                enqueue(queue, tempNode.west);
            }

            tempNode.color = zero + 2;   // mark node for finished
            s++;
        }
    } // end of fixArray


    /**
     * fills the "way"-vector witch actions
     *
     * @param kind what pattern
     */
    private void makePattern( int kind )
    {
        /*
        Werte fr MakePattern :
         
        1: SpinLeft
        2: SpinRight
        3: WaveLeft
        4: WaveRight
        5: globalTopLeftBottomRight
        6: globalTopRightBottomLeft
        7: globalBottomRightTopLeft
        8: globalBottomLeftTopRight
        9: straight ahead
         
        Number: Wie oft soll ein global pattern in den Vector geschrieben werden
         
        */


        //initialisierung der Arrays

        int tmpDirection = (this.getDirection());

        //  Spirale von innen nach aussen  leftspin
        //  bewegungen

        AbstractAction SpinLeft [] = new AbstractAction[32] ;

        SpinLeft [0] = new MoveAction ();
        SpinLeft [1] = new RotateAction ((tmpDirection % 4) + 1);   // left
        tmpDirection++;
        SpinLeft [2] = new MoveAction ();
        SpinLeft [3] = new RotateAction ((tmpDirection % 4) + 1);  //left
        tmpDirection++;
        SpinLeft [4] = new MoveAction ();
        SpinLeft [5] = new MoveAction ();
        SpinLeft [6] = new RotateAction ((tmpDirection % 4) + 1);   //left
        tmpDirection++;
        SpinLeft [7] = new MoveAction ();
        SpinLeft [8] = new MoveAction ();
        SpinLeft [9] = new RotateAction ((tmpDirection % 4) + 1);  // left
        tmpDirection++;
        SpinLeft [10] = new MoveAction ();
        SpinLeft [11] = new MoveAction ();
        SpinLeft [12] = new MoveAction ();
        SpinLeft [13] = new RotateAction ((tmpDirection % 4) + 1);  //left
        tmpDirection++;
        SpinLeft [14] = new MoveAction ();
        SpinLeft [15] = new MoveAction ();
        SpinLeft [16] = new MoveAction ();
        SpinLeft [17] = new RotateAction ((tmpDirection % 4) + 1);  //left
        tmpDirection++;
        SpinLeft [18] = new MoveAction ();
        SpinLeft [19] = new MoveAction ();
        SpinLeft [20] = new MoveAction ();
        SpinLeft [21] = new MoveAction ();
        SpinLeft [22] = new RotateAction ((tmpDirection % 4) + 1);   //left
        tmpDirection++;
        SpinLeft [23] = new MoveAction ();
        SpinLeft [24] = new MoveAction ();
        SpinLeft [25] = new MoveAction ();
        SpinLeft [26] = new MoveAction ();
        SpinLeft [27] = new RotateAction ((tmpDirection % 4) + 1);  //left
        tmpDirection++;
        SpinLeft [28] = new MoveAction ();
        SpinLeft [29] = new MoveAction ();
        SpinLeft [30] = new MoveAction ();
        SpinLeft [31] = new MoveAction ();

        //next

        //  Spirale von innen nach aussen  rightspin
        //  bewegungen


        AbstractAction SpinRight [] = new AbstractAction [32];

        tmpDirection = (this.getDirection());

        SpinRight[0] = new MoveAction ();
        SpinRight[1] = new RotateAction (((tmpDirection + 2) % 4) + 1);       //right
        tmpDirection = tmpDirection + 3;
        SpinRight[2] = new MoveAction ();
        SpinRight[3] = new RotateAction (((tmpDirection + 2) % 4) + 1);      //right
        tmpDirection = tmpDirection + 3;
        SpinRight[4] = new MoveAction ();
        SpinRight[5] = new MoveAction ();
        SpinRight[6] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right
        tmpDirection = tmpDirection + 3;
        SpinRight[7] = new MoveAction ();
        SpinRight[8] = new MoveAction ();
        SpinRight[9] = new RotateAction (((tmpDirection + 2) % 4) + 1);    //right
        tmpDirection = tmpDirection + 3;
        SpinRight[10] = new MoveAction ();
        SpinRight[11] = new MoveAction ();
        SpinRight[12] = new MoveAction ();
        SpinRight[13] = new RotateAction (((tmpDirection + 2) % 4) + 1);     //right
        tmpDirection = tmpDirection + 3;
        SpinRight[14] = new MoveAction ();
        SpinRight[15] = new MoveAction ();
        SpinRight[16] = new MoveAction ();
        SpinRight[17] = new RotateAction (((tmpDirection + 2) % 4) + 1);    // right
        tmpDirection = tmpDirection + 3;
        SpinRight[18] = new MoveAction ();
        SpinRight[19] = new MoveAction ();
        SpinRight[20] = new MoveAction ();
        SpinRight[21] = new MoveAction ();
        SpinRight[22] = new RotateAction (((tmpDirection + 2) % 4) + 1);     // right
        tmpDirection = tmpDirection + 3;
        SpinRight[23] = new MoveAction ();
        SpinRight[24] = new MoveAction ();
        SpinRight[25] = new MoveAction ();
        SpinRight[26] = new MoveAction ();
        SpinRight[27] = new RotateAction (((tmpDirection + 2) % 4) + 1);    // right
        tmpDirection = tmpDirection + 3;
        SpinRight[28] = new MoveAction ();
        SpinRight[29] = new MoveAction ();
        SpinRight[30] = new MoveAction ();
        SpinRight[31] = new MoveAction ();

        //next

        //  Wellenmuster left   beginne untere rechte ecke bis obere linke ecke
        //  bewegungen

        AbstractAction WaveLeft [] = new AbstractAction [34];

        tmpDirection = (this.getDirection());

        WaveLeft [0] = new RotateAction ((tmpDirection % 4) + 1);  // left  //nach links drehen  //west
        tmpDirection++;
        WaveLeft [1] = new MoveAction ();
        WaveLeft [2] = new MoveAction ();
        WaveLeft [3] = new MoveAction ();
        WaveLeft [4] = new MoveAction ();
        WaveLeft [5] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right  //north
        tmpDirection = tmpDirection + 3;
        WaveLeft [6] = new MoveAction ();  // v?rw?rts
        WaveLeft [7] = new RotateAction (((tmpDirection + 2) % 4) + 1);   //right  //east
        tmpDirection = tmpDirection + 3;
        WaveLeft [8] = new MoveAction ();
        WaveLeft [9] = new MoveAction ();
        WaveLeft [10] = new MoveAction ();
        WaveLeft [11] = new MoveAction ();
        WaveLeft [12] = new RotateAction ((tmpDirection % 4) + 1);  // left   //north
        tmpDirection++;
        WaveLeft [13] = new MoveAction ();
        WaveLeft [14] = new RotateAction ((tmpDirection % 4) + 1);   //left   //west
        tmpDirection++;
        WaveLeft [15] = new MoveAction ();
        WaveLeft [16] = new MoveAction ();
        WaveLeft [17] = new MoveAction ();
        WaveLeft [18] = new MoveAction ();
        WaveLeft [19] = new RotateAction (((tmpDirection + 2) % 4) + 1);   //right   //north
        tmpDirection = tmpDirection + 3;
        WaveLeft [20] = new MoveAction ();
        WaveLeft [21] = new RotateAction (((tmpDirection + 2) % 4) + 1);   //right   //east
        tmpDirection = tmpDirection + 3;
        WaveLeft [22] = new MoveAction ();
        WaveLeft [23] = new MoveAction ();
        WaveLeft [24] = new MoveAction ();
        WaveLeft [25] = new MoveAction ();
        WaveLeft [26] = new RotateAction ((tmpDirection % 4) + 1);   //left    //north
        tmpDirection++;
        WaveLeft [27] = new MoveAction ();
        WaveLeft [28] = new RotateAction ((tmpDirection % 4) + 1);  //left    //west
        tmpDirection++;
        WaveLeft [29] = new MoveAction ();
        WaveLeft [30] = new MoveAction ();
        WaveLeft [31] = new MoveAction ();
        WaveLeft [32] = new MoveAction ();
        WaveLeft [33] = new RotateAction ((tmpDirection % 4) + 1);  //left // nach vorne drehen //north

        //next

        //  Wellenmuster right  beginne untere linke ecke bis obere rechte ecke
        //  bewegungen


        AbstractAction WaveRight [] = new AbstractAction [34];

        tmpDirection = (this.getDirection());

        WaveRight [0] = new RotateAction (((tmpDirection + 2) % 4) + 1);   // right //nach rechts drehen //east
        tmpDirection = tmpDirection + 3;
        WaveRight [1] = new MoveAction ();
        WaveRight [2] = new MoveAction ();
        WaveRight [3] = new MoveAction ();
        WaveRight [4] = new MoveAction ();
        WaveRight [5] = new RotateAction ((tmpDirection % 4) + 1);  //left //north
        tmpDirection++;
        WaveRight [6] = new MoveAction ();
        WaveRight [7] = new RotateAction ((tmpDirection % 4) + 1);  //left  //west
        tmpDirection++;
        WaveRight [8] = new MoveAction ();
        WaveRight [9] = new MoveAction ();
        WaveRight [10] = new MoveAction ();
        WaveRight [11] = new MoveAction ();
        WaveRight [12] = new RotateAction (((tmpDirection + 2) % 4) + 1);   //right  //north
        tmpDirection = tmpDirection + 3;
        WaveRight [13] = new MoveAction ();
        WaveRight [14] = new RotateAction (((tmpDirection + 2) % 4) + 1);    //right  //east
        tmpDirection = tmpDirection + 3;
        WaveRight [15] = new MoveAction ();
        WaveRight [16] = new MoveAction ();
        WaveRight [17] = new MoveAction ();
        WaveRight [18] = new MoveAction ();
        WaveRight [19] = new RotateAction ((tmpDirection % 4) + 1);   //left   //north
        tmpDirection++;
        WaveRight [20] = new MoveAction ();
        WaveRight [21] = new RotateAction ((tmpDirection % 4) + 1);    //left   //west
        tmpDirection++;
        WaveRight [22] = new MoveAction ();
        WaveRight [23] = new MoveAction ();
        WaveRight [24] = new MoveAction ();
        WaveRight [25] = new MoveAction ();
        WaveRight [26] = new RotateAction (((tmpDirection + 2) % 4) + 1);    // right   //north
        tmpDirection = tmpDirection + 3;
        WaveRight [27] = new MoveAction ();
        WaveRight [28] = new RotateAction (((tmpDirection + 2) % 4) + 1);     //right   //east
        tmpDirection = tmpDirection + 3;
        WaveRight [29] = new MoveAction ();
        WaveRight [30] = new MoveAction ();
        WaveRight [31] = new MoveAction ();
        WaveRight [32] = new MoveAction ();
        WaveRight [33] = new RotateAction ((tmpDirection % 4) + 1);   //left  //nach vorne drehen //north

        //next

        //global topleft bottom right if called various times

        AbstractAction globalTopLeftBottomRight [] = new AbstractAction [36];

        tmpDirection = (this.getDirection());

        globalTopLeftBottomRight [0] = new RotateAction ((tmpDirection % 4) + 1);  // left  //east
        tmpDirection++;
        globalTopLeftBottomRight [1] = new MoveAction ();
        globalTopLeftBottomRight [2] = new MoveAction ();
        globalTopLeftBottomRight [3] = new MoveAction ();
        globalTopLeftBottomRight [4] = new MoveAction ();
        globalTopLeftBottomRight [5] = new MoveAction ();
        globalTopLeftBottomRight [6] = new MoveAction ();
        globalTopLeftBottomRight [7] = new MoveAction ();
        globalTopLeftBottomRight [8] = new MoveAction ();
        globalTopLeftBottomRight [9] = new MoveAction ();
        globalTopLeftBottomRight [10] = new MoveAction ();
        globalTopLeftBottomRight [11] = new MoveAction ();
        globalTopLeftBottomRight [12] = new RotateAction (((tmpDirection + 2) % 4) + 1);   //right //south
        tmpDirection = tmpDirection + 3;
        globalTopLeftBottomRight [13] = new MoveAction ();
        globalTopLeftBottomRight [14] = new MoveAction ();
        globalTopLeftBottomRight [15] = new MoveAction ();
        globalTopLeftBottomRight [16] = new MoveAction ();
        globalTopLeftBottomRight [17] = new MoveAction ();
        globalTopLeftBottomRight [18] = new MoveAction ();
        globalTopLeftBottomRight [19] = new MoveAction ();
        globalTopLeftBottomRight [20] = new MoveAction ();
        globalTopLeftBottomRight [21] = new MoveAction ();
        globalTopLeftBottomRight [22] = new MoveAction ();
        globalTopLeftBottomRight [23] = new MoveAction ();
        globalTopLeftBottomRight [24] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right //west
        tmpDirection = tmpDirection + 3;
        globalTopLeftBottomRight [25] = new MoveAction ();
        globalTopLeftBottomRight [26] = new MoveAction ();
        globalTopLeftBottomRight [27] = new MoveAction ();
        globalTopLeftBottomRight [28] = new MoveAction ();
        globalTopLeftBottomRight [29] = new MoveAction ();
        globalTopLeftBottomRight [30] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right //north
        tmpDirection = tmpDirection + 3;
        globalTopLeftBottomRight [31] = new MoveAction ();
        globalTopLeftBottomRight [32] = new MoveAction ();
        globalTopLeftBottomRight [33] = new MoveAction ();
        globalTopLeftBottomRight [34] = new MoveAction ();
        globalTopLeftBottomRight [35] = new MoveAction ();


        //next


        //     global : top right -> bottom left

        AbstractAction globalTopRightBottomLeft [] = new AbstractAction [36];

        tmpDirection = (this.getDirection());

        globalTopRightBottomLeft [0] = new RotateAction ((tmpDirection % 4) + 1);  // left  //west
        tmpDirection++;
        globalTopRightBottomLeft [1] = new MoveAction ();
        globalTopRightBottomLeft [2] = new MoveAction ();
        globalTopRightBottomLeft [3] = new MoveAction ();
        globalTopRightBottomLeft [4] = new MoveAction ();
        globalTopRightBottomLeft [5] = new MoveAction ();
        globalTopRightBottomLeft [6] = new MoveAction ();
        globalTopRightBottomLeft [7] = new MoveAction ();
        globalTopRightBottomLeft [8] = new MoveAction ();
        globalTopRightBottomLeft [9] = new MoveAction ();
        globalTopRightBottomLeft [10] = new MoveAction ();
        globalTopRightBottomLeft [11] = new MoveAction ();
        globalTopRightBottomLeft [12] = new RotateAction ((tmpDirection % 4) + 1);  // left  //south
        tmpDirection++;
        globalTopRightBottomLeft [13] = new MoveAction ();
        globalTopRightBottomLeft [14] = new MoveAction ();
        globalTopRightBottomLeft [15] = new MoveAction ();
        globalTopRightBottomLeft [16] = new MoveAction ();
        globalTopRightBottomLeft [17] = new MoveAction ();
        globalTopRightBottomLeft [18] = new MoveAction ();
        globalTopRightBottomLeft [19] = new MoveAction ();
        globalTopRightBottomLeft [20] = new MoveAction ();
        globalTopRightBottomLeft [21] = new MoveAction ();
        globalTopRightBottomLeft [22] = new MoveAction ();
        globalTopRightBottomLeft [23] = new MoveAction ();
        globalTopRightBottomLeft [24] = new RotateAction ((tmpDirection % 4) + 1);  //left  //east
        tmpDirection++;
        globalTopRightBottomLeft [25] = new MoveAction ();
        globalTopRightBottomLeft [26] = new MoveAction ();
        globalTopRightBottomLeft [27] = new MoveAction ();
        globalTopRightBottomLeft [28] = new MoveAction ();
        globalTopRightBottomLeft [29] = new MoveAction ();
        globalTopRightBottomLeft [30] = new RotateAction ((tmpDirection % 4) + 1);   //left //north
        tmpDirection++;
        globalTopRightBottomLeft [31] = new MoveAction ();
        globalTopRightBottomLeft [32] = new MoveAction ();
        globalTopRightBottomLeft [33] = new MoveAction ();
        globalTopRightBottomLeft [34] = new MoveAction ();
        globalTopRightBottomLeft [35] = new MoveAction ();

        //next

        //global bottom right top left

        AbstractAction globalBottomRightTopLeft [] = new AbstractAction [36];

        tmpDirection = (this.getDirection());

        globalBottomRightTopLeft [0] = new RotateAction ((tmpDirection % 4) + 1);   //left  //west
        tmpDirection++;
        globalBottomRightTopLeft [1] = new MoveAction ();
        globalBottomRightTopLeft [2] = new MoveAction ();
        globalBottomRightTopLeft [3] = new MoveAction ();
        globalBottomRightTopLeft [4] = new MoveAction ();
        globalBottomRightTopLeft [5] = new MoveAction ();
        globalBottomRightTopLeft [6] = new MoveAction ();
        globalBottomRightTopLeft [7] = new MoveAction ();
        globalBottomRightTopLeft [8] = new MoveAction ();
        globalBottomRightTopLeft [9] = new MoveAction ();
        globalBottomRightTopLeft [10] = new MoveAction ();
        globalBottomRightTopLeft [11] = new MoveAction ();
        globalBottomRightTopLeft [12] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right  //north
        tmpDirection = tmpDirection + 3;
        globalBottomRightTopLeft [13] = new MoveAction ();
        globalBottomRightTopLeft [14] = new MoveAction ();
        globalBottomRightTopLeft [15] = new MoveAction ();
        globalBottomRightTopLeft [16] = new MoveAction ();
        globalBottomRightTopLeft [17] = new MoveAction ();
        globalBottomRightTopLeft [18] = new MoveAction ();
        globalBottomRightTopLeft [19] = new MoveAction ();
        globalBottomRightTopLeft [20] = new MoveAction ();
        globalBottomRightTopLeft [21] = new MoveAction ();
        globalBottomRightTopLeft [22] = new MoveAction ();
        globalBottomRightTopLeft [23] = new MoveAction ();
        globalBottomRightTopLeft [24] = new RotateAction (((tmpDirection + 2) % 4) + 1);  // right  //east
        tmpDirection = tmpDirection + 3;
        globalBottomRightTopLeft [25] = new MoveAction ();
        globalBottomRightTopLeft [26] = new MoveAction ();
        globalBottomRightTopLeft [27] = new MoveAction ();
        globalBottomRightTopLeft [28] = new MoveAction ();
        globalBottomRightTopLeft [29] = new MoveAction ();
        globalBottomRightTopLeft [30] = new RotateAction (((tmpDirection + 2) % 4) + 1);   // right //south
        tmpDirection = tmpDirection + 3;
        globalBottomRightTopLeft [31] = new MoveAction ();
        globalBottomRightTopLeft [32] = new MoveAction ();
        globalBottomRightTopLeft [33] = new MoveAction ();
        globalBottomRightTopLeft [34] = new MoveAction ();
        globalBottomRightTopLeft [35] = new MoveAction ();

        //next

        //global bottom left  top right

        AbstractAction globalBottomLeftTopRight [] = new AbstractAction [36];

        tmpDirection = (this.getDirection());

        globalBottomLeftTopRight [0] = new RotateAction (((tmpDirection + 2) % 4) + 1);    //right //east
        tmpDirection = tmpDirection + 3;
        globalBottomLeftTopRight [1] = new MoveAction ();
        globalBottomLeftTopRight [2] = new MoveAction ();
        globalBottomLeftTopRight [3] = new MoveAction ();
        globalBottomLeftTopRight [4] = new MoveAction ();
        globalBottomLeftTopRight [5] = new MoveAction ();
        globalBottomLeftTopRight [6] = new MoveAction ();
        globalBottomLeftTopRight [7] = new MoveAction ();
        globalBottomLeftTopRight [8] = new MoveAction ();
        globalBottomLeftTopRight [9] = new MoveAction ();
        globalBottomLeftTopRight [10] = new MoveAction ();
        globalBottomLeftTopRight [11] = new MoveAction ();
        globalBottomLeftTopRight [12] = new RotateAction ((tmpDirection % 4) + 1);  // left   //north
        tmpDirection++;
        globalBottomLeftTopRight [13] = new MoveAction ();
        globalBottomLeftTopRight [14] = new MoveAction ();
        globalBottomLeftTopRight [15] = new MoveAction ();
        globalBottomLeftTopRight [16] = new MoveAction ();
        globalBottomLeftTopRight [17] = new MoveAction ();
        globalBottomLeftTopRight [18] = new MoveAction ();
        globalBottomLeftTopRight [19] = new MoveAction ();
        globalBottomLeftTopRight [20] = new MoveAction ();
        globalBottomLeftTopRight [21] = new MoveAction ();
        globalBottomLeftTopRight [22] = new MoveAction ();
        globalBottomLeftTopRight [23] = new MoveAction ();
        globalBottomLeftTopRight [24] = new RotateAction ((tmpDirection % 4) + 1);  // left   //west
        tmpDirection++;
        globalBottomLeftTopRight [25] = new MoveAction ();
        globalBottomLeftTopRight [26] = new MoveAction ();
        globalBottomLeftTopRight [27] = new MoveAction ();
        globalBottomLeftTopRight [28] = new MoveAction ();
        globalBottomLeftTopRight [29] = new MoveAction ();
        globalBottomLeftTopRight [30] = new RotateAction ((tmpDirection % 4) + 1);  //left  //south
        tmpDirection++;
        globalBottomLeftTopRight [31] = new MoveAction ();
        globalBottomLeftTopRight [32] = new MoveAction ();
        globalBottomLeftTopRight [33] = new MoveAction ();
        globalBottomLeftTopRight [34] = new MoveAction ();
        globalBottomLeftTopRight [35] = new MoveAction ();



        Random randomTemp = new Random();
        int tempInt = randomTemp.nextInt(3);
        AbstractAction forw [] = new AbstractAction [2 + tempInt];



        for (int j = 0; j < tempInt + 2; j++)
        {
            forw [j] = new MoveAction ();
        }

        //Anzahl der Durchl„äufe fr global

        int number = 5;
        way.clear();   //Vector immer vorher löschen !!!

        //Ende Initialisierung

        //switch um zu Prfen welcher Typ gefragt ist ...



        switch (kind)
        {

                case 1 :
                {
                    //spinleft
                    for (int i = 0; i < SpinLeft.length; i++)
                    {
                        way.addElement(SpinLeft[i]);
                    }
                    break;
                }

                case 2 :
                {
                    //spinright
                    for (int i = 0; i < SpinRight.length; i++)
                    {
                        way.addElement(SpinLeft[i]);
                    }
                    break;
                }

                case 3 :
                {    //waveleft
                    for (int i = 0; i < WaveLeft.length; i++)
                    {
                        way.addElement(WaveLeft[i]);
                    }
                    break;
                }

                case 4 :
                {
                    //waveright
                    for (int i = 0; i < WaveRight.length; i++)
                    {
                        way.addElement(WaveRight[i]);
                    }
                    break;
                }

                case 5 :
                { //globalTopLeftBottomRight
                    //Da Nur Grundstruktur muss das Array ”fters durchlaufen werden und in vector gespeichert werden

                    for (int x = 0; x <= number; x++)
                    {
                        for (int i = 0; i < globalTopLeftBottomRight.length; i++)
                        {
                            way.addElement(globalTopLeftBottomRight[i]);
                        }
                    }
                    break;
                }

                case 6 :
                { //globalTopRightBottomLeft
                    //Da Nur Grundstruktur muss das Array ”fters sprich 4-5 mal durchlaufen werden und in vector gespeichert werden
                    for (int x = 0; x <= number; x++)
                    {
                        for (int i = 0; i < globalTopRightBottomLeft.length; i++)
                        {
                            way.addElement(globalTopRightBottomLeft[i]);
                        }

                    }
                    break;
                }

                case 7 :
                { //globalBottomRightTopLeft
                    //			  System.out.println("Welle");
                    //Da Nur Grundstruktur muss das Array ”fters sprich 4-5 mal durchlaufen werden und in vector gespeichert werden
                    for (int x = 0; x <= number; x++)
                    {
                        for (int i = 0; i < globalBottomRightTopLeft.length; i++)
                        {
                            way.addElement(globalBottomRightTopLeft[i]);
                        }
                    }

                    break;
                }


                case 8 :
                { //globalBottomLeftTopRight

                    //Da Nur Grundstruktur muss das Array ”fters sprich 4-5 mal durchlaufen werden und in vector gespeichert werden
                    for (int x = 0; x <= number; x++)
                    {
                        for (int i = 0; i < globalBottomLeftTopRight.length; i++)
                        {
                            way.addElement(globalBottomLeftTopRight[i]);
                        }
                    }

                    break;
                }

                case 9 :
                {

                    for (int i = 0; i < forw.length; i++)
                    {
                        way.addElement(forw[i]);
                    }
                    break;
                }

        }  //ende switch


    } //ende makepattern

    /**
     * approximates maximum points
     * 
     * @return int
     */
    private int getMaximumPoints()
    {
        int max = 0;
        HashSet testSet = new HashSet();

        Card[] colorOne = new Card[4];
        Card[] colorTwo = new Card[4];
        Card[] colorThree = new Card[4];
        Card[] colorFour = new Card[4];
        Card[] colorFive = new Card[4];

        for (int i = 1; i <= 5; i++)       //Fill Arrays
        {
            for (int j = 1; j <= 4; j++)
            {
                Card insCard = new Card(j, i);

                switch (i)
                {
                        case 1:
                        colorOne[j - 1] = insCard;
                        case 2:
                        colorTwo[j - 1] = insCard;
                        case 3:
                        colorThree[j - 1] = insCard;
                        case 4:
                        colorFour[j - 1] = insCard;
                        case 5:
                        colorFive[j - 1] = insCard;
                }
            }
        }

        for (int i = 1; i <= 5; i++)    // Test with all single Cards
        {
            for (int j = 1; j <= 4; j++)
            {
                switch (i)
                {
                        case 1:
                        testSet.add(colorOne[j - 1]);
                        case 2:
                        testSet.add(colorTwo[j - 1]);
                        case 3:
                        testSet.add(colorThree[j - 1]);
                        case 4:
                        testSet.add(colorFour[j - 1]);
                        case 5:
                        testSet.add(colorFive[j - 1]);
                }

                if (this.getArena().valuateCards(testSet.iterator()) > max)
                    max = this.getArena().valuateCards(testSet.iterator());
                testSet.clear();
            }
        }

        for (int j = 1; j <= 4; j++)     //Test with cards of same number
        {

            testSet.add(colorOne[j - 1]);
            testSet.add(colorTwo[j - 1]);
            testSet.add(colorThree[j - 1]);
            testSet.add(colorFour[j - 1]);
            testSet.add(colorFive[j - 1]);

            if (this.getArena().valuateCards(testSet.iterator()) > max)
                max = this.getArena().valuateCards(testSet.iterator());


            testSet.clear();
        }


        for (int i = 2; i <= 4; i++)            //Test with all combinations between 2 and 4
        {
            for (int j = 1; j <= i; j++)
            {
                testSet.add(colorThree[j - 1]);
            }

            if (this.getArena().valuateCards(testSet.iterator()) > max)
                max = this.getArena().valuateCards(testSet.iterator());

            testSet.clear();
        }


        for (int i = 1; i <= 2; i++)   //Test of two pairs
        {
            testSet.add(colorThree[i]);
            testSet.add(colorFive[i]);
        }

        testSet.add(colorOne[1]);

        if (this.getArena().valuateCards(testSet.iterator()) > max)
            max = this.getArena().valuateCards(testSet.iterator());
        testSet.clear();

        testSet.add(colorTwo[0]);     //Test of Full House
        testSet.add(colorTwo[1]);
        testSet.add(colorTwo[2]);
        testSet.add(colorFour[0]);
        testSet.add(colorFour[1]);

        if (this.getArena().valuateCards(testSet.iterator()) > max)
            max = this.getArena().valuateCards(testSet.iterator());
        testSet.clear();


        return max;

    }

 
    /**
     * maze mode
     *
     * @return AbstractAction
     */
    public AbstractAction mazeMode()
	  {  
	  	 	    	       // rechts unbekannt -> rechts drehen
	  	 	    	       // vorne & rechts frei -> gehen
	  	 	    	       // vorne frei & rechts nicht -> gehen
	  		    	       // vorne nicht frei -> links drehen
	  		    	       

	     if (timer == maxValue)
	     	mode = 0;
	     if (inFrontOfMe.item instanceof Well)
	     {
                this.modifyVector(14);    // umlaufen
                if (!listOfWell.isEmpty())
                {
         		     boolean inserted =false;
                     for(int i =0;i<listOfWell.capacity(); i++)
                	   {
                	   	    if ( ((Node)listOfWell.elementAt(i)).id == inFrontOfMe.id )
                	   	    { 	
                	   	     listOfWell.removeElementAt(i);
                	   	     listOfWell.add(i, inFrontOfMe);
                	   	     listOfWell.trimToSize();
				     inserted = true;
                	   	    }
                  	   }
			   if (!inserted)
                 listOfWell.add(inFrontOfMe);			       
                }
                else	   
                listOfWell.add(inFrontOfMe);
	     }
	     if ((this.getArena().getItemInFrontOfRobot(this) instanceof Card) && !cardBlocked)
             {
                 if (this.sizeOfPortable() <= 4)
                 {
                     inFrontOfMe.item = null;
		     return new PickupAction();
                 }
                 else if (this.exchangeOK(lowestPoints(iteratorOfPortable()), (Card)this.getArena().getItemInFrontOfRobot(this)) & (this.sizeOfPortable() == 5))
		 {
                     this.modifyVector(20);    //put away Card
		     mode = 0;
		 }
             }

	     
	     switch(this.getDirection())
	  	 {
	  	 	    case 1: {
    	  	 	    	       if (whereIAm.east == null)
	      	 	    	       	   return new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // right	  	 	    	       	
	  	 	        	       else if (inFrontOfMe.item == null)
					            {  
	  	 	        	       	     if ( (whereIAm.east.item == null) && (whereIAm.south != null) &&
						        ( (whereIAm.south.east == null) || (whereIAm.south.east.item != null) ) )
						         return  new RotateAction(((this.getDirection() + 2)% 4)+ 1); // left 
						     else
						       	 return new MoveAction();
						    }	
	  	 	    	           	    else 
	  	 	    	           	        return new RotateAction((this.getDirection()% 4)+ 1); // left
		  	 	    	      }
	  	 	    case 2: {
    	  	 	    	       if (whereIAm.north == null)
	      	 	    	       	   return new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // right	  	 	    	       	
	  	 	        	       else if (inFrontOfMe.item == null)
					            {  
	  	 	        	       	     if ( (whereIAm.north.item == null) && (whereIAm.east != null) &&
						        ( (whereIAm.east.north == null) || (whereIAm.east.north.item != null) ) )
						         return  new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // left 
						     else
						       	 return new MoveAction();
						    }	
	  	 	    	           	    else 
	  	 	    	           	        return new RotateAction((this.getDirection()% 4)+ 1); // left
    	 	    	      }
	  	 	    case 3: {
    	  	 	    	       if (whereIAm.west == null)
	      	 	    	       	   return new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // right	  	 	    	       	
	  	 	        	       else if (inFrontOfMe.item == null)
					            {  
	  	 	        	       	     if ( (whereIAm.west.item == null) && (whereIAm.north != null) &&
						        ( (whereIAm.north.west == null) || (whereIAm.north.west.item != null) ) )
						         return  new RotateAction(((this.getDirection()+2 )% 4)+ 1); // left 
						     else
						       	 return new MoveAction();
						    }	
	  	 	    	           	    else 
	  	 	    	           	        return new RotateAction((this.getDirection()% 4)+ 1); // left
	  	 	    	      }
	  	 	    case 4: {
    	  	 	    	       if (whereIAm.south == null)
	      	 	    	       	   return new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // right	  	 	    	       	
	  	 	        	       else if (inFrontOfMe.item == null)
					            {  
	  	 	        	       	     if ( (whereIAm.south.item == null) && (whereIAm.west != null) &&
						        ( (whereIAm.west.south == null) || (whereIAm.west.south.item != null) ) )
						         return  new RotateAction(((this.getDirection()+ 2)% 4)+ 1); // left 
						     else
						       	 return new MoveAction();
						    }	
	  	 	    	           	    else 
	  	 	    	           	        return new RotateAction((this.getDirection()% 4)+ 1); // left
	  	 	    	      }
	  	 	    	      

	  	 }		

	  	 	
	  	 	return new RotateAction(1);	  	 	
	  	 	
	  }

    /**
     * local searchmode
     *
     * @return AbstractAction
     */

    public AbstractAction localMode()
    {
        if (this.getArena().getItemInFrontOfRobot(this) == null)
        {
		
        }
        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Well)
        {

            if (this.getEnergy() < (0.95 * maxEnergy))
            {
                way.add (0, new MoveAction());  //nehmen
            }
            else
            {
                this.modifyVector(14);    // umlaufen
                if (!listOfWell.isEmpty())
                {
        		     boolean inserted = false;	
                     for(int i =0;i<listOfWell.capacity(); i++)
                	   {
                	   	    if ( ((Node)listOfWell.elementAt(i)).id == inFrontOfMe.id )
                	   	    {
                	   	     listOfWell.removeElementAt(i);
                	   	     listOfWell.add(i, inFrontOfMe);
                	   	     listOfWell.trimToSize();
				     inserted = true;
        
                	   	    } 
                	   }
			   if (!inserted)
                               listOfWell.add(inFrontOfMe);		    
                }
                else	   
                 listOfWell.add(inFrontOfMe);
            }
        }

        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Wall)

        {
            if ( !way.isEmpty() && !((AbstractAction)(way.firstElement()) instanceof RotateAction) )
             this.modifyVector(14);  //Wegdrehen !!!
            	 	
        }


        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Teleport)
        {
            listOfTeleport.add (inFrontOfMe);
            modifyVector(14);    //Umlaufen !!!
        }


        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Exit)
        {

                this.modifyVector(14);    // umlaufen
                if (!listOfExit.isEmpty())
                {
                	
                     for(int i =0;i<listOfExit.capacity(); i++)
                	   {
                	   	    if ( ((Node)listOfExit.elementAt(i)).id == inFrontOfMe.id )
                	   	    {
                	   	     listOfExit.removeElementAt(i);
                	   	     listOfExit.add(i, inFrontOfMe);
                	   	     listOfExit.trimToSize();
                	   	    } 
                	   }
                }
                else	   
                 listOfExit.add(inFrontOfMe);
        }


        else if ((this.getArena().getItemInFrontOfRobot(this) instanceof Card) && !cardBlocked)
        {
            if (this.sizeOfPortable() <= 4)
            {
                way.add(0, new PickupAction());
                inFrontOfMe.item = null;
            }
            else if (this.exchangeOK(lowestPoints(iteratorOfPortable()), (Card)this.getArena().getItemInFrontOfRobot(this)) & (this.sizeOfPortable() == 5))
                this.modifyVector(20);    //put away Card

            else this.modifyVector(14);
        }

        else/* if (this.getArena().getItemInFrontOfRobot(this) instanceof Robot || (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6) &&
                 ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("axman")))
        {
           if (robotInFront == 0)
            {
                robotInFront++;
                inFrontOfMe.item = null;
                // einmal nop dann in als solchen graph eintragen
                return new NopAction();
            }
            else
            {
                robotInFront = 0;
                inFrontOfMe.item = this.getArena().getItemInFrontOfRobot(this);
                modifyVector(14);
            }
        }


        else if ( (this.getArena().getItemInFrontOfRobot(this) != null ) && (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6) &&
                  (this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("atamo") )
        {


            modifyVector(0);
        }
        else if
        (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6 &&
                ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("rader")) && (this.getEnergy() < 0.2 * maxEnergy) &&
                (this.sizeOfPortable() > 1))
        {
            way.add (0, new MoveAction());
        }
        else */
        {
            listOfUnknownItems.add (inFrontOfMe);
            this.modifyVector(14);  //Umlaufen !!!
        }



        if (!way.isEmpty()) return (AbstractAction)way.firstElement();
        return null;
    }



    /**
     * global searchmode
     *
     * @return AbstractAction
     */
    private AbstractAction globalMode()
    {


        if (this.getArena().getItemInFrontOfRobot(this) == null)
        {
            this.modifyVector(0);
        }


        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Well)
        {
            if (this.getEnergy() < (0.95 * maxEnergy))
            {
                way.add (0, new MoveAction());  //nehmen

            }


            else

            {
                this.modifyVector(14);    // umlaufen
                if (!listOfWell.isEmpty())
                {
             	     boolean inserted = false; 
                     for(int i =0;i<listOfWell.capacity(); i++)
                	   {
                	   	    if ( ((Node)listOfWell.elementAt(i)).id == inFrontOfMe.id )
                	   	    { 	
                	   	     listOfWell.removeElementAt(i);
                	   	     listOfWell.add(i, inFrontOfMe);
                	   	     listOfWell.trimToSize();
				     inserted = true;
                	   	    }
                	   }
			   if (!inserted)
			       listOfWell.add(inFrontOfMe);

                }
                else	   
                listOfWell.add(inFrontOfMe);
            }

        }

        else if ((this.getArena().getItemInFrontOfRobot(this) instanceof Card) && !cardBlocked)
        {
            if (this.sizeOfPortable() <= 4)
            {
                way.add(0, new PickupAction());
                inFrontOfMe.item = null;
            }
            else if (this.exchangeOK(lowestPoints(iteratorOfPortable()), (Card)this.getArena().getItemInFrontOfRobot(this)) & (this.sizeOfPortable() == 5))
                this.modifyVector(20);    //put away Card
            else this.modifyVector(14);
        }

        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Teleport)          // take teleport
        {
            listOfTeleport.add (inFrontOfMe);
            lastTeleport = inFrontOfMe;
            secondTeleport = ((Teleport)this.getArena().getItemInFrontOfRobot(this)).getTeleport();
            way.add(0, new MoveAction());
            tookTeleport = true;
        }

        else /*if (this.getArena().getItemInFrontOfRobot(this) instanceof Robot || (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6) &&
                 ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("axman")))
        {
        	           if (robotInFront == 0)
            {
                robotInFront++;
                inFrontOfMe.item = null;
                // einmal nop dann in als solchen graph eintragen
                //System.out.println("robot in front -> 2nd chance");
                return new NopAction();
            }
            else
            {
                robotInFront = 0;
                inFrontOfMe.item = this.getArena().getItemInFrontOfRobot(this);
                modifyVector(14);
            }

        }
        else*/ if (this.getArena().getItemInFrontOfRobot(this) instanceof Exit)
        {
                this.modifyVector(14);    // umlaufen
                if (!listOfExit.isEmpty())
                {
                	
                     for(int i =0;i<listOfExit.capacity(); i++)
                	   {
                	   	    if ( ((Node)listOfExit.elementAt(i)).id == inFrontOfMe.id )
                	   	    {
                	   	     listOfExit.removeElementAt(i);
                	   	     listOfExit.add(i, inFrontOfMe);
                	   	     listOfExit.trimToSize();
                	   	    } 
                	   }
                }
                else	   
                 listOfExit.add(inFrontOfMe);
        }

        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Wall)
        {
            if ( !way.isEmpty() && !((AbstractAction)(way.firstElement()) instanceof RotateAction) )
             this.modifyVector(14);  //Wegdrehen !!!
        }


        else /*if (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6 && (this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("atamo"))
        {
            modifyVector(0);
        }
        else if (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6 &&
                 ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("rader")) &&
                 (this.getEnergy() < (0.2 * maxEnergy)) && (this.sizeOfPortable() > 1))
        {
            way.add (0, new MoveAction());
        }

        else */
        {
            listOfUnknownItems.add (inFrontOfMe);
            this.modifyVector(14);   //umlaufen
        }

        if (!way.isEmpty()) return (AbstractAction)way.firstElement();
        return null;

    }


    /**
     * tracking mode
     *
     * @return next robot-movement
     */
    private AbstractAction exitMode()
    {

        if ( (this.getArena().getItemInFrontOfRobot(this) instanceof Teleport) &&
                (this.getDirection() == whereIAm.preNode) )
        {
            tookTeleport = true;
            lastTeleport = inFrontOfMe;
            secondTeleport = ((Teleport)this.getArena().getItemInFrontOfRobot(this)).getTeleport();
        }
        if (this.getArena().getItemInFrontOfRobot(this) instanceof Well)
        {
            mode = 0;
            return new MoveAction();
        }
        else if (this.getArena().getItemInFrontOfRobot(this) instanceof Robot)
        {

            if (robotInFront == 0)
            {
                robotInFront++;
                inFrontOfMe.item = null;
                // einmal nop dann in als solchen graph eintragen
                //System.out.println("robot in front -> 2nd chance");
                return new NopAction();
            }
            else
            {
                robotInFront = 0;
                inFrontOfMe.item = this.getArena().getItemInFrontOfRobot(this);
            }

        }

        else /*if ( (this.getEnergy() < (0.2 * maxEnergy)) &&
                  (this.sizeOfPortable() > 1) &&
                  (this.getArena().getItemInFrontOfRobot(this) != null) &&
                  (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6) &&
                  ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("rader")) )

        {
            way.add (0, new MoveAction());
        }
        else */ if ((this.getArena().getItemInFrontOfRobot(this) instanceof Card) && !cardBlocked)
        {
            if (this.sizeOfPortable() <= 4)
            {
                inFrontOfMe.item = null;
                return new PickupAction();
            }
            else
            {
                inFrontOfMe.item = null;
                return new RotateAction(whereIAm.preNode);
            }
        }
        else if ( whereIAm.preNode == this.getDirection() )
        {

            return new MoveAction();

        }
        else
        {

            return new RotateAction(whereIAm.preNode);
        }
        return new MoveAction();
    }




    /**
     * method modifies the vector way
     *
     * @param kind of modification
     */
    private void modifyVector(int kind)
    {
        Random r = new Random();
        int tmpDirection = this.getDirection();
        way.trimToSize();
        switch (kind)
        {
                case 0 :
                {
                    break;  // no changes
                }

                case 1 :
                { // surround right
                    switch ( analyseVector(way) )
                    {
                            case 0 :
                            break;
                            case 11 :
                            { // at obst right
                                if (way.capacity() >= 3)
                                {
                                    way.removeElementAt(0);
                                    way.removeElementAt(1);
                                    way.removeElementAt(2);
                                }
                                else
                                    way.clear();
                                way.trimToSize();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);               // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                   // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                     // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                   // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);               // right
                                way.add(4, new RotateAction(tmpDirection));
                                break;
                            }
                            case 12 :
                            {
                                if (way.capacity() >= 3)
                                {
                                    way.removeElementAt(0);
                                    way.removeElementAt(1);
                                    way.removeElementAt(2);
                                    way.trimToSize();
                                }
                                else
                                    way.clear();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(8, new RotateAction(tmpDirection));
                                way.add(9, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(10, new RotateAction(tmpDirection));
                                break;
                            } // at obst left
                            case 21 :
                            {
                                if (way.capacity() >= 4)
                                {
                                    way.removeElementAt(0);
                                    way.removeElementAt(1);
                                    way.removeElementAt(2);
                                    way.removeElementAt(3);
                                    way.trimToSize();
                                }
                                else
                                    way.clear();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                break;
                            } // behind obst right
                            case 22 :
                            {
                                if (way.capacity() >= 4)
                                {
                                    way.removeElementAt(0);
                                    way.removeElementAt(1);
                                    way.removeElementAt(2);
                                    way.removeElementAt(3);
                                    way.trimToSize();
                                }
                                else
                                    way.clear();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                break;
                            } // behind obst left
                            case 3 :
                            {
                                if (way.capacity() >= 2)
                                {
                                    way.removeElementAt(0);
                                    way.removeElementAt(1);
                                    way.trimToSize();
                                }
                                else
                                    way.clear();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(7, new RotateAction(tmpDirection));
                                break;
                            }
                            case 4 :
                            {
                                modifyVector(18);  // S-curve right
                                break;
                            }
                            default :
                            break;
                    }
                    break;
                }
                case 2 :
                {  // surround left
                    switch ( analyseVector(way) )
                    {
                            case 0 :
                            break;
                            case 11 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(8, new RotateAction(tmpDirection));
                                way.add(9, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(10, new RotateAction(tmpDirection));
                                break;
                            } // at obst right
                            case 12 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(4, new RotateAction(tmpDirection));
                                break;
                            } // at obst left
                            case 21 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                break;
                            } // behind obst right
                            case 22 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(5, new RotateAction(tmpDirection));
                                break;
                            } // behind obst left
                            case 3 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);   // remove element 0 & 1
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(7, new RotateAction(tmpDirection));
                                break;
                            }
                            case 4 :
                            {
                                modifyVector(19);  // S-curve left
                                break;
                            }
                            default :
                            break;
                    }
                    break;
                }
                case 3 :
                {  // shortest way
                    switch ( analyseVector(way) )
                    {
                            case 0 :
                            break;
                            case 11 :
                            { // at obst right
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);               // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                   // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                     // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                   // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);               // right
                                way.add(4, new RotateAction(tmpDirection));
                                break;
                            }
                            case 12 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(4, new RotateAction(tmpDirection));
                                break;
                            } // at obst left
                            case 21 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                break;
                            } // behind obst right
                            case 22 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(5, new RotateAction(tmpDirection));
                                break;
                            } // behind obst left
                            // 3 & 4 could be decided by random
                            case 3 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);   // remove element 0 & 1
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(7, new RotateAction(tmpDirection));
                                break;
                            }
                            case 4 :
                            {
                                modifyVector(19);  // S-curve left
                                break;
                            }
                    }
                    break;
                }
                case 4 :
                {
                    modifyVector(3);
                    break;
                }
                case 5 :
                {  // longest way
                    switch ( analyseVector(way) )
                    {
                            case 0 :
                            break;
                            case 11 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(8, new RotateAction(tmpDirection));
                                way.add(9, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(10, new RotateAction(tmpDirection));
                                break;
                            } // at obst right
                            case 12 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.trimToSize();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(8, new RotateAction(tmpDirection));
                                way.add(9, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(10, new RotateAction(tmpDirection));
                                break;
                            } // at obst left surrounding right
                            case 21 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                        // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                break;
                            } // behind obst right surrounding left
                            case 22 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);
                                way.removeElementAt(2);
                                way.removeElementAt(3);
                                way.trimToSize();
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                way.add(7, new MoveAction());                                  // forward
                                break;
                            } // behind obst left surrounding right
                            // 3 & 4 could be decided by random
                            case 3 :
                            {
                                way.removeElementAt(0);
                                way.removeElementAt(1);   // remove element 0 & 1
                                way.trimToSize();
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(0, new RotateAction(tmpDirection));
                                way.add(1, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(2, new RotateAction(tmpDirection));
                                way.add(3, new MoveAction());                                  // forward
                                way.add(4, new MoveAction());                                  // forward
                                tmpDirection = (((tmpDirection + 2) % 4) + 1);              // right
                                way.add(5, new RotateAction(tmpDirection));
                                way.add(6, new MoveAction());                                  // forward
                                tmpDirection = ((tmpDirection % 4) + 1);                    // left
                                way.add(7, new RotateAction(tmpDirection));
                                break;
                            }
                            case 4 :
                            {
                                modifyVector(19);  // S-curve left
                                break;
                            }
                    }
                    break;
                }
                case 6 :
                {
                    modifyVector(5);
                    break;
                }
                case 7 :
                { // known field

                    int randomTemp = r.nextInt(2) + 1;
                    switch ( whereIsAKnownField() )
                    {
                            case 0 :
                            modifyVector( randomTemp );
                            break;  // no known field
                            case 1 :
                            modifyVector(1);
                            break;             // right
                            case 2 :
                            modifyVector(2);
                            break;             // left
                            case 3 :
                            modifyVector( randomTemp );
                            break;  // known field behind
                    }

                    break;
                }
                case 8 :
                {
                    modifyVector(7);
                    break;
                }
                case 9 :
                {  // unknown field
                    int randomTemp = r.nextInt(2) + 1;
                    switch ( whereIsAnUnknownField() )
                    {
                            case 0 :
                            modifyVector( randomTemp );
                            break;  // no unknown field
                            case 1 :
                            modifyVector(1);
                            break;             // right
                            case 2 :
                            modifyVector(2);
                            break;             // left
                            case 3 :
                            modifyVector( randomTemp );
                            break;  // unknown field behind
                    }

                    break;
                }
                case 10 :
                {
                    modifyVector(9);
                    break;
                }
                case 11 :
                {    // rotate right
                    way.add(0, new RotateAction( ((this.getDirection() + 2) % 4) + 1));  // rotate right
                    break;
                }
                case 12 :
                {    // rotate left
                    way.add(0, new RotateAction( (this.getDirection() % 4) + 1));  // rotate left
                    break;
                }

                case 13 :
                {       // rotate to known field
                    int randomTemp = r.nextInt(100);
                    switch ( whereIsAKnownField() )
                    {
                            case 0 :
                            {
                                if ((randomTemp % 2) == 0 )
                                    way.add(0, new RotateAction( ((this.getDirection() + 2) % 4) + 1));
                                else
                                    way.add(0, new RotateAction( (this.getDirection() % 4) + 1 ));
                                break;
                            }
                            case 1 :
                            {
                                makePattern(9);
                                way.add(0,new RotateAction( ((this.getDirection() +2)%4)+1));
                                break; // rotate right
                            }

                            case 2 :
                            {
                                makePattern(9);

                                way.add(0,new RotateAction( (this.getDirection() % 4) + 1 ));
                                break; // rotate left
                            }

                            case 3 :
                            way.add(0, new RotateAction(((this.getDirection() + 1) % 4) + 1)); break;    // backward
                            default : break;
                    }
                    break;
                }

                case 14 :
                {                                // rotate to unknown field
                    int randomTemp = r.nextInt(100);
                    switch ( whereIsAnUnknownField() )
                    {
                            case 0 :
                            {
                                modifyVector(13);
                                break;
                            }
                            case 1 :
                            {
                                makePattern(9);
                                way.add(0, new RotateAction( ((this.getDirection() + 2) % 4) + 1));
                                break; // rotate right
                            }

                            case 2 :
                            {
                                makePattern(9);
                                way.add(0, new RotateAction( (this.getDirection() % 4) + 1));
                                break; // rotate left
                            }

                            case 3 :
                            {
                                makePattern(9);
                                way.add(0, new RotateAction(((this.getDirection() + 1) % 4) + 1));
                                break;    // backward
                            }

                            default :
                            break;
                    }
                    break;
                }
                case 15 :
                { // 180 Grad
                    way.clear();
                    tmpDirection = (((this.getDirection() % 4) + 2) % 4);          // backward
                    way.addElement(new RotateAction(tmpDirection));
                    break;
                }
                case 16 :
                {  // Kehre recht
                    way.clear();                                      // delete vector
                    way.addElement(new RotateAction(((this.getDirection() + 2) % 4) + 1));   // right
                    way.addElement(new MoveAction());                                  // forward
                    way.addElement(new RotateAction(((this.getDirection() + 2) % 4) + 1));   // right
                    way.addElement(new MoveAction());                                  // forward
                    break;
                }
                case 17 :
                {  // Kehre links
                    way.clear();                                       // delete vector
                    way.addElement(new RotateAction((this.getDirection() % 4) + 1));       // left
                    way.addElement(new MoveAction());                                  // forward
                    way.addElement(new RotateAction((this.getDirection() % 4) + 1));       // left
                    way.addElement(new MoveAction());                                  // forward
                    break;
                }
                case 18 :
                { //s-curve right
                    way.clear();                                       // delete vector
                    way.addElement(new RotateAction(((this.getDirection() + 2) % 4) + 1));   // right
                    way.addElement(new MoveAction());                                  // forward
                    way.addElement(new RotateAction((this.getDirection() % 4) + 1));       // left
                    way.addElement(new MoveAction());                                  // forward
                    break;
                }
                case 19 :
                {  // s-curve left
                    way.clear();                                  // delete vector
                    way.addElement(new RotateAction((this.getDirection() % 4) + 1));       // left
                    way.addElement(new MoveAction());                                  // forward
                    way.addElement(new RotateAction(((this.getDirection() + 2) % 4) + 1));   // right
                    way.addElement(new MoveAction());                                  // forward
                    break;
                }
                case 20 :
                {
                    // magic move
                    way.trimToSize();
                    int randomTemp = r.nextInt(100);
                    switch ( whereIsAKnownField() )
                    {
                            case 0 :
                            {
                                if ( (randomTemp % 2) == 0 )
                                    way.add(0, new RotateAction( ((this.getDirection() + 2) % 4) + 1));
                                else
                                    way.add(0, new RotateAction( (this.getDirection() % 4) + 1 ));
                                break;
                            }
                            case 1 :
                            way.add(0, new RotateAction(((this.getDirection() + 2) % 4) + 1)); break;  // rotate right
                            case 2 : way.add(0, new RotateAction((this.getDirection() % 4) + 1 )); break;  // rotate left
                            case 3 : way.add(0, new RotateAction(((this.getDirection() + 1) % 4) + 1)); break;    // backward
                            default : break;
                    }
                    way.add(1, new PutAction(lowestPoints(iteratorOfPortable())));
                    way.add(2, new RotateAction(this.getDirection()));
                    break;
                }
                default :
                {
                  way.add(0, new NopAction());
                }

        }

    } // modifyVector


    /**
     * analyses the first three entries of the vector
     *
     * @param vector, to be anlysed
     * @return result of analysis as int
     */
    private int analyseVector(Vector oldWay)
    {
        oldWay.trimToSize();
        if ( oldWay.capacity() < 4 )
        {
            return 4;                   // vector too short for being analysed
        }

        else
        {
            if ( ((AbstractAction)oldWay.elementAt(0)) instanceof RotateAction )
            {
                return 0;                   // obsticle will never be touched

            }

            else
            {
                if ( ((AbstractAction)oldWay.elementAt(0)) instanceof MoveAction )
                {
                    if ( oldWay.elementAt(1) instanceof RotateAction )
                    {
                        if ( ((RotateAction)oldWay.elementAt(1)).getDirection() == ((this.getDirection() + 2) % 4) + 1)
                        {
                            return 11;     // turn at obsticle to right
                        }


                        if ( ((RotateAction)oldWay.elementAt(1)).getDirection() == ((this.getDirection() % 4) + 1))
                        {
                            return 12;     // turn at obsticle to left
                        }



                    }
                    else
                    {
                        if ( (AbstractAction)oldWay.elementAt(1) instanceof MoveAction )
                        {
                            if ((AbstractAction)oldWay.elementAt(2) instanceof RotateAction)
                            {
                                if ( ((RotateAction)oldWay.elementAt(2)).getDirection() == ((this.getDirection() + 2) % 4) + 1)
                                {
                                    return 21;     // turn behind obsticle to right
                                }


                                if ( ((RotateAction)oldWay.elementAt(2)).getDirection() == ((this.getDirection() % 4) + 1) )
                                {
                                    return 22;     // turn behind obsticle to left
                                }


                            }
                            else
                            {
                                if ( oldWay.elementAt(2) instanceof MoveAction )
                                {
                                    return 3;  // forward
                                }

                                else
                                {
                                    // System.out.println("Fehler bei Analyse des Vektors in 'analyseVector'.");
                                    // this should never be executed.
                                }

                            }
                        }
                    }
                }
            }
        }



        return 0;

    } // analyseVector



    /**
     * looks for an unknown field on the right and left of bot
     *
     * @return 1=unkown field on the right 2=unkown field on the left 3=unknown field behind bot
     */
    private int whereIsAnUnknownField()
    {
        int result = 0;
        switch ( this.getDirection() )
        {
                case 1 :
                {
                    if (whereIAm.west == null)
                    {
                        result = 2;
                    }
                    else if (whereIAm.east == null)
                    {
                        result = 1;
                    }
                    else if (whereIAm.south == null)
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 2 :
                {
                    if (whereIAm.north == null)
                    {
                        result = 2;
                    }
                    else if (whereIAm.south == null)
                    {
                        result = 1;
                    }
                    else if (whereIAm.east == null)
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 3 :
                {
                    if (whereIAm.east == null)
                    {
                        result = 2;
                    }
                    else if (whereIAm.west == null)
                    {
                        result = 1;
                    }
                    else if (whereIAm.north == null)
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 4 :
                {
                    if (whereIAm.north == null)
                    {
                        result = 2;
                    }
                    else if (whereIAm.south == null)
                    {
                        result = 1;
                    }
                    else if (whereIAm.west == null)
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
        }
        return result;
    } // whereIsAnUnknownField



    /**
     * looks for an known field on the right and left of bot
     *
     * @return direction
     */
    private int whereIsAKnownField()
    {
        int result = 0;
        Random randomTemp = new Random();
        switch ( this.getDirection() )
        {
                case 1 :
                {
                                    	  
                    if ( ((whereIAm.west != null) && (whereIAm.west.item == null)) && 
                         ((whereIAm.east != null) && (whereIAm.east.item == null)) )
                    {
                        result = randomTemp.nextInt(2)+1;  	   
                    }
                    if ((whereIAm.west != null) && (whereIAm.west.item == null))
                    {
                        result = 2;
                    }
                    else if ((whereIAm.east != null) && (whereIAm.east.item == null))
                    {
                        result = 1;
                    }
                    else if ((whereIAm.south != null) && (whereIAm.south.item == null))
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 2 :
                {
                    if ( ((whereIAm.north != null) && (whereIAm.north.item == null)) && 
                         ((whereIAm.south != null) && (whereIAm.south.item == null)) )
                    {
                        result = randomTemp.nextInt(2)+1;  	   
                    }
                    if ((whereIAm.north != null) && (whereIAm.north.item == null))
                    {
                        result = 2;
                    }
                    else if ((whereIAm.south != null) && (whereIAm.south.item == null))
                    {
                        result = 1;
                    }
                    else if ((whereIAm.east != null) && (whereIAm.east.item == null))
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 3 :
                {
                    if ( ((whereIAm.west != null) && (whereIAm.west.item == null)) && 
                         ((whereIAm.east != null) && (whereIAm.east.item == null)) )
                    {
                        result = randomTemp.nextInt(2)+1;  	   
                    }
                    if ((whereIAm.east != null) && (whereIAm.east.item == null))
                    {
                        result = 2;
                    }
                    else if ((whereIAm.west != null) && (whereIAm.west.item == null))
                    {
                        result = 1;
                    }
                    else if ((whereIAm.north != null) && (whereIAm.north.item == null))
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
                case 4 :
                {
                    if ( ((whereIAm.north != null) && (whereIAm.north.item == null)) && 
                         ((whereIAm.south != null) && (whereIAm.south.item == null)) )
                    {
                        result = randomTemp.nextInt(2)+1;  	   
                    }
                    if ((whereIAm.north != null) && (whereIAm.north.item == null))
                    {
                        result = 2;
                    }
                    else if ((whereIAm.south != null) && (whereIAm.south.item == null))
                    {
                        result = 1;
                    }
                    else if ((whereIAm.west != null) && (whereIAm.west.item == null))
                    {
                        result = 3;
                    }
                    else
                    {
                        result = 0;
                    }
                    break;
                }
        }
        return result;
    } // whereIsAKnownField





    /**
     * calculates most useless card
     *
     * @param hand cards of robot
     * @return Card
     */
    private Card lowestPoints (Iterator hand)
    {

        Iterator tmpIterator = this.iteratorOfPortable();
        HashSet testSet = new HashSet();

        int testCard = 0;

        int useless = 0;

        int points = 0;

        Card tmpCards[] = new Card[5];

        int i = 0;
        while (tmpIterator.hasNext())
        {
            tmpCards[i] = (Card)tmpIterator.next();
            i++;
        }


        for ( int k = 0; k < i; k++)
        {
            for ( int j = 0; j < i; j++)
            {
                testSet.add(tmpCards[j]);
            }
            testSet.remove(tmpCards[testCard]);
            if (this.getArena().valuateCards(testSet.iterator()) > points)
            {
                points = this.getArena().valuateCards(testSet.iterator());
                useless = testCard;
            }

            testSet.clear();
            testCard++;
        }

        return tmpCards[useless];

    }



    /**
     * select card for exchange.
     *
     * @return most redundant card
     */
    public Card exchangeProposal ()
    {
        if ((this.getArena().getItemInFrontOfRobot(this) != null) && (((this.getArena().getItemInFrontOfRobot(this).getName()).length()) >= 6) &&
                ((this.getArena().getItemInFrontOfRobot(this).getName()).substring(1, 6).equals("axman")))
        {
            return null;
        }
        if (this.sizeOfPortable() == 0)
        {
            return null;
        }

        else if (this.sizeOfPortable() == 1)
        {
            return (Card) this.iteratorOfPortable().next();
        }
        else
        {
            return lowestPoints(iteratorOfPortable());
        }
    }


    /**
     * returns true if robot accepts exchange.
     *
     * @param myCard our card, otherCard card of other robot
     * @return true=accepts card, false=do not accept
     */

    public boolean exchangeOK (Card myCard, Card otherCard)
    {
        Iterator tmpIterator = this.iteratorOfPortable();
        HashSet testSet = new HashSet();

        while (tmpIterator.hasNext())
        {
            testSet.add(tmpIterator.next());
        }

        try
        {

            testSet.remove(myCard);
            testSet.add(otherCard);
        }
        catch (NullPointerException e)
        {
		}


        if (this.getArena().valuateCards(iteratorOfPortable()) < this.getArena().valuateCards(testSet.iterator()))
        {
               return true;
        }
        else
        {

            return false;
        }


    }



}
