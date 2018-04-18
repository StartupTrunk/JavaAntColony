public class Ant 
{
   int ID;
   int firstTurn;
   int finalTurn;
   int lifespan;
   
   boolean alive;
   
   ColonyNode location;

    public Ant(ColonyNode node) //initial values
    {
      ID = 0;
      lifespan = 0;
      alive = true;
      location = node;
      finalTurn = 0;
    }

    public Ant() 
    {
    }

    public void setID(int ID) //provide Ant IDs
    {
      this.ID = ID;
    }
    
    public void nextTurn(int present) 
    {
    }

    public void move(ColonyNode newLocation) 
    {
       location.removeAnt(this);
       location = newLocation;
       location.addAnt(this);
    }
    
    public void trackAge (int present) 
    {
      firstTurn = present;
    }

    public void death() 
    {
      location.removeAnt(this);
    }   
}
