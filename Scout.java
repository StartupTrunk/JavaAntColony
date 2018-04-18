import java.util.*;

public class Scout extends Ant 
{
    Scout(ColonyNode node) 
    {
      location = node;
      finalTurn = -1;
    }

    Scout() 
    {
    }

    public void nextTurn(int present) 
    {
        if (finalTurn == present)
          return;
        if ((present - firstTurn) > 10 * 365) //lifespan of 1 year
        {
          death();
          return;
        }
        finalTurn = present;
        Random m = new Random();
        ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
        ColonyNode destination;
        destination = adjacentList.get(m.nextInt(adjacentList.size()));
        move(destination);
    }

    public void move(ColonyNode node) //reveal node contents
    {
      location.removeAnt(this);
      location = node;
      location.addAnt(this);

        if (!(location.isSeen())) 
        {
         location.setSeen(true);
        }
    }
}
