import java.util.*;

public class ColonyNode 
{

    int food;
    int pheromones;
    int antCount;
    int x, y;
    
    boolean isQueen;
    boolean isEntrance;
    boolean isBusy;
    boolean isSeen;
    
    ArrayList<Ant> antList;
    ArrayList<Ant> removeList;
    ArrayList<Ant> addList;
    ColonyNodeView nodeView;
    Colony colony;

    ColonyNode(ColonyNodeView nodeView, int x, int y) 
    {
        Random f = new Random();
        if (f.nextInt(4) == 0) //25% chance
        {
         food = f.nextInt(500) + 500; //random amount of food available
        } 
        else food = 0; //75% chance
        this.nodeView = nodeView;
        this.x = x;
        this.y = y;
        pheromones = 0;
        isSeen = false;
        antCount = 0;
        isQueen = false;
        antList = new ArrayList<Ant>();
        removeList = new ArrayList<Ant>();
        addList = new ArrayList<Ant>();
        isEntrance = false;
    }

    public void setSeen(boolean val) //set show & hide values for isSeen
    {
        isSeen = val;
        if (val == true) 
        {
         nodeView.showNode();
        }
        if (val == false) 
        {
         nodeView.hideNode();
        }
    }

    public void addAnt(Ant newAnt) 
    {
        if (isBusy) // Wait till list completes
        {
          addList.add(newAnt);
        } 
        else 
        {
         antList.add(newAnt);
        }
        updateView();
    }

    public void removeAnt(Ant newAnt) 
    {
        if (isBusy) // Wait till list is complete
        {
         removeList.add(newAnt);
        } 
        else 
        {
         antList.remove(newAnt);
        }
        updateView();
    }

    public int countAnts(Ant getAntType) 
    {
        int antCount = 0;
        for (int i = 0; i < antList.size(); i++) 
        {
            if (antList.get(i).getClass() == getAntType.getClass()) 
            {
             antCount++;
            }
        }
        return antCount;
    }

    public void updateNode() 
    {
        this.nodeView.showNode();
    }

    public void updateView() //show ant images for > 0
    {
        int queenCount = countAnts(new Queen());
        nodeView.setFoodAmount(food);
        nodeView.setPheromoneLevel(pheromones);
        if (queenCount == 1) 
        {
         isQueen = true;
         nodeView.showQueenIcon();
         nodeView.setQueen(isQueen);
        }

        nodeView.setForagerCount(countAnts(new Forager()));
        if (countAnts(new Forager()) > 0)
          nodeView.showForagerIcon();
        else nodeView.hideForagerIcon();
        
        nodeView.setSoldierCount(countAnts(new Soldier()));
        if (countAnts(new Soldier()) > 0)
           nodeView.showSoldierIcon();
        else nodeView.hideSoldierIcon();
        
        nodeView.setScoutCount(countAnts(new Scout()));
        if (countAnts(new Scout()) > 0)
         nodeView.showScoutIcon();
        else nodeView.hideScoutIcon();

        nodeView.setBalaCount(countAnts(new Bala()));
        if (countAnts(new Bala()) > 0)
         nodeView.showBalaIcon();
        else nodeView.hideBalaIcon();
    }

    public void updateList() 
    {
        for (Ant x:removeList) 
        {
         antList.remove(x);
        }
        removeList.clear();
        for (Ant x:addList) 
        {
         antList.add(x);
        }
        addList.clear();
    }

    public void nextTurn(int present) 
    {
        if ((present !=0) && (present %10==0)) //decrease pheromones by half each day
        {
         this.setPheromones(getPheromones()/2);
        }
        isBusy = true;
        for (Ant nextAnt:antList) 
        {
         nextAnt.nextTurn(present);
        }
        isBusy = false;
        updateList();
        updateView();
    }

    public void setPheromones(int pheromonesCount) 
    {
      pheromones = pheromonesCount;
    }

    public int getPheromones() 
    {
     return pheromones;
    }
    
    public void setFood(int foodCount) 
    {
     food = foodCount;
    }

    public int getFood() 
    {
     return food;
    }

    public boolean hasQueen() 
    {
     return isQueen;
    }

    public boolean isSeen() 
    {
     return isSeen;
    }

    public int getX() 
    {
     return x;
    }

    public int getY() 
    {
     return y;
    }

    public void setColony(Colony colony) 
    {
     this.colony = colony;
    }

    public ArrayList<ColonyNode> getAdjacentNodes() 
    {
     return colony.getAdjacentNodes(this);
    }

    public ArrayList<Ant> getAntType(Ant type) 
    {
      ArrayList<Ant> antType = new ArrayList<Ant>();
      for (int i = 0; i < antList.size(); i++) 
        {
            if (antList.get(i).getClass() == type.getClass()) 
            {
             antType.add(antList.get(i));
            }
        }
        return antType;
    }

    public ArrayList<Ant> getColonyAnts() 
    {
        ArrayList<Ant> notBala = new ArrayList<Ant>();
        for (int i = 0; i < antList.size(); i++) 
        {
            if (antList.get(i).getClass() != new Bala().getClass()) 
            {
             notBala.add(antList.get(i));
            }
        }
       return notBala;
    }
}