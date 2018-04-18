
import java.util.*;

public class Queen extends Ant 
{
  int present;
  int lastID;
  int ID;

    public Queen(ColonyNode node) 
    {
      location = node;
      lastID = 0;
      ID = 0;
    }

    Queen() 
    {
    }

    public void hatch(Ant ant) //radnomly hatch new Ants
    {
        Random r = new Random();
        int randomAnt = r.nextInt(100) + 1;
        Ant newAnt;

        if (randomAnt <= 50) //50% Forager
        {
         newAnt = new Forager(location);
        } 
        else if ((randomAnt > 50) && (randomAnt <= 75)) //25% Scout
        {
         newAnt = new Scout(location);
        } 
        else 
        {
         newAnt = new Soldier(location); //25% Soldier
        }

        if (ant != null) newAnt = ant;

        newAnt.trackAge(present);
        lastID ++;
        newAnt.setID(lastID);
        location.addAnt(newAnt);
    }

    public void nextTurn(int present) 
    {
        this.present = present;
        inviteBala();
        this.eat();
        if ((present > (20 * 10 * 365)) || (location.getFood() < 1)) //queen dies after 20 yrs or 0 food
        {
          death();
          return;
        }

        if ((present % 10) == 0) //hatch newAnt after 10 turns
        {
         this.hatch(null);
        }
    }

    public void inviteBala() 
    {
        Random b = new Random();
        int newBalaAnt = b.nextInt(100) + 1;
        ColonyNode balaLocation = location.colony.colonyEnvironment[0][0];

        if (newBalaAnt < 30) 
        {
         Bala newBala = new Bala(balaLocation);
         balaLocation.addAnt(newBala);
         newBala.trackAge(present);
         lastID++;
         newBala.setID(lastID);
        }
    }
    
    public void eat() 
    {
        int foodCount = location.getFood();
        if (foodCount <= 0) 
        {
         this.death();
        }
        foodCount = foodCount - 1;
        location.setFood(foodCount);
    }

    public void death() 
    {
        System.out.println("The iron throne is empty and the 7 kingdoms are in jeopardy. I vote Daenerys Targaryen to be our new Queen.");
        location.nodeView.hideQueenIcon();
        location.colony.sim.gameOver();
    }
}
