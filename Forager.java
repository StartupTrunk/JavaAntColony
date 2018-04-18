import java.util.*;

public class Forager extends Ant 
{
    int retraceSteps;
    boolean returnToNestMode;
    ArrayList<ColonyNode> moveHistory;

    public Forager(ColonyNode node) 
    {
        location = node;
        finalTurn = -1;
        returnToNestMode = false;
        moveHistory = new ArrayList<ColonyNode>();
        moveHistory.add(location);
    }

    Forager() 
    {
    }

    public ColonyNode forageMode() 
    {
        ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
        ArrayList<ColonyNode> randomNode = new ArrayList<ColonyNode>();
        Random chooseNode = new Random();
        for (Iterator<ColonyNode> iterator = adjacentList.iterator(); iterator.hasNext();) //eliminate open nodes
        {
            ColonyNode x = iterator.next();
            if (moveHistory.contains(x) || !x.isSeen()) 
            {
              iterator.remove();
            }
        }
        if (adjacentList.size() == 0)  //all nodes open
        {
            adjacentList = location.getAdjacentNodes();
        }

        ColonyNode maxP = adjacentList.get(0);

        for (int i = 1; i < adjacentList.size(); i++) //check for larger pheromone levels
        {
            if (maxP.isSeen() && maxP.getPheromones() < adjacentList.get(i).getPheromones()) 
            {
              maxP = adjacentList.get(i);
            }
        }

        for (int j = 0; j < adjacentList.size(); j++) 
        {
            if ((adjacentList.get(j).getPheromones() == maxP.getPheromones()) && adjacentList.get(j).isSeen()) {
              randomNode.add(adjacentList.get(j));
            }
        }
        maxP = randomNode.get(chooseNode.nextInt(randomNode.size()));
        return maxP;
    }

    public void move(ColonyNode node) 
    {
        location.removeAnt(this);
        location = node;
        location.addAnt(this);

        if (location.hasQueen() && returnToNestMode) //drop food for queen
        {
          location.setFood(location.getFood() + 1);
          returnToNestMode = false; //end mode
          moveHistory.clear(); //clear steps
        }
        moveHistory.add(location); //log steps
    }

    public boolean isFood() 
    {
        if (location.getFood() > 0 && !(location.hasQueen())) 
        {
          location.setFood(location.getFood() - 1); //pickup 1 unit of food
          returnToNestMode = true; //enable mode
          retraceSteps = moveHistory.size() - 1; 
          return true;
        } 
        else return false;
    }

    public void nextTurn(int present) 
    {
        ColonyNode destination;
        if (finalTurn == present)
            return;

        finalTurn = present;
        if (returnToNestMode) //return to Queen
        {
         retraceSteps --;
         destination = moveHistory.get(retraceSteps);
         depositPheromones();
        } 
        else 
        {
            destination = forageMode(); //continue looking
        }

        move(destination);
        if (!(returnToNestMode)) 
        {
            isFood();
        }

        if ((present - firstTurn) > 10 * 365) //lifespan
        {
         death();
        }
    }

    public void depositPheromones() 
    {
        if (!(location.hasQueen())) //not with Queen
        {
            if (location.getPheromones() < 1000) //nodes < 1000
            {
            location.setPheromones(location.getPheromones() + 10); //deposit 10 units
            }
        }
    }

    public void death() 
    {
        if (returnToNestMode == true) 
        {
        location.setFood(location.getFood() + 1); //drop units in node of death
        }
        location.removeAnt(this);
    }
}
