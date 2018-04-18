
import java.util.*;
public class Bala extends Ant 
{
    Bala(ColonyNode node) 
    {
      location = node;
      finalTurn = -1;
    }

    Bala() 
    {
    }
    
    public void nextTurn(int present) 
    {
        if (finalTurn == present)
          return;

        if ((present - firstTurn) > 10 * 365) //lifespan of 1 yr
        {
          death();
          return;
        }

        finalTurn = present;
        ArrayList<Ant> notBala = location.getColonyAnts();

        if (notBala.size() > 0) //attack colony
        {
         fight();
        }
        else 
        {
          Random m = new Random(); //random node
          ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
          ColonyNode destination;
          destination = adjacentList.get(m.nextInt(adjacentList.size()));
          move(destination);
        }
    }

    public void move(ColonyNode node) 
    {
      location.removeAnt(this);
      location = node;
      location.addAnt(this);
    }

    public void fight() 
    {
      ArrayList<Ant> notBala = location.getColonyAnts();
      Random k = new Random();
      int chance = k.nextInt(2);
        if (chance == 0) 
        {
         notBala.get(0).death();
        }
    }
}
