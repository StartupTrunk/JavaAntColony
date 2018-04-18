import javax.swing.*;

public class Simulation implements SimulationEventListener 
{
  int present = 0;
  boolean isQueenAlive;
  boolean step;
  Colony colonySimulator;
  AntSimGUI gui;
  Thread thread;

    Simulation(AntSimGUI gui) 
    {
       isQueenAlive = true;
        step = true;
        this.gui = gui;
        colonySimulator = new Colony(new ColonyView(27, 27), this);
        gui.initGUI(colonySimulator.getView());
        thread = null;
    }

    public void nextTurn() //track turns
    {
        do 
        {
          colonySimulator.nextTurn(present);
          present++;
            try 
            {
              Thread.sleep(200);
            } catch (InterruptedException ex) 
            {
              Thread.currentThread().interrupt();
            }
        } while (!step && isQueenAlive);
    }
    
    public void simulationEventOccurred(SimulationEvent simEvent) //Normal Play
    {
        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) 
        {
          colonySimulator.startColony();
        }
        if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) 
        {
           step = false;
           thread = new Thread() 
            {
                public void run() 
                {
                    nextTurn();
                }
            };
            thread.start();
        }
        if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) 
        {
          step = true;
          nextTurn();
        }
    }
    
    public void gameOver() //off with her head message
    {
        isQueenAlive = false;
        if (thread != null) 
        {
          thread.interrupt();
        }
        String[] options = {"Ok."};
        int pane = JOptionPane.showOptionDialog(gui,
        "The iron throne is empty and the 7 kingdoms are in jeopardy. I vote Daenerys Targaryen to be our new Queen.",
        "Simulation Complete", 0, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (pane == 0) 
        {
          System.exit(0);
        }
    }
}
