import java.util.*;

public class Soldier extends Ant 
{
    Soldier(ColonyNode node) 
    {
      location = node;
      finalTurn = -1;
    }

    Soldier() 
    {
    }

     public void move(ColonyNode node) 
    {
     location.removeAnt(this);
     location = node;
     location.addAnt(this);
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

        if (location.getAntType(new Bala()).size() > 0) //bala in adjacent node
        {
          fight();
        } 
        else 
        {
          Random m = new Random();
          ArrayList<ColonyNode> adjacentList = location.getAdjacentNodes();
          ArrayList<ColonyNode> visibleList = new ArrayList<ColonyNode>();
          ColonyNode destination;
            for (int i = 0; i < adjacentList.size(); i++) 
            {
                if (adjacentList.get(i).isSeen()) 
                {
                 visibleList.add(adjacentList.get(i));
                }
            }
            
          destination = visibleList.get(m.nextInt(visibleList.size()));  //random open node
       
            for (ColonyNode x:visibleList) //Bala node priority 
            {
                if (x.getAntType(new Bala()).size() > 0) 
                {
                 destination = x;
                }
            }
          move(destination);
        }
    }

    public void fight() 
    {
      ArrayList<Ant> balaList = location.getAntType(new Bala());
      Random d = new Random();
      int chance = d.nextInt(2);
        if (chance == 0) 
        {
          balaList.get(0).death();
        }
    }
}
