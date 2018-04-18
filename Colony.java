import java.util.*;
public class Colony {
    ColonyNode [][] colonyEnvironment;
    ColonyView colonyView;
    ColonyNodeView cnv;
    Simulation sim;
    ColonyNode cn;

    Colony(ColonyView view, Simulation sim) 
    {
       this.sim = sim;
       colonyEnvironment = new ColonyNode [27][27];
       this.colonyView = view;
    }

    public void addColonyNode(ColonyNode nodeNum, int x, int y) 
    {
      colonyEnvironment[x][y] = nodeNum;
    }

    public ColonyView getView() 
    {
      return colonyView;
    }

    public void nextTurn(int curTurn) 
    {
        for (int a = 0; a < 27; a++)
        {
            for (int b = 0; b < 27; b++)
            {
              colonyEnvironment[a][b].nextTurn(curTurn);
            }
        }
    }

    public void startColony() 
    {
        for (int c = 0; c < 27; c++) 
        {
            for (int d = 0; d < 27; d++) 
            {
              cnv = new ColonyNodeView();
              cn = new ColonyNode(cnv, c, d);
              cn.setColony(this);
              colonyView.addColonyNodeView(cnv, c, d);
              addColonyNode(cn, c, d);
              cnv.setID(c + "," + d);

                if (c == 13 && d == 13) 
                {
                   Queen q = new Queen(cn);
                   cn.setFood(1000);
                   cn.addAnt(q);

                    for (int a = 0; a < 10; a++) 
                    {
                     q.hatch(new Soldier(cn));
                    }
                    for (int e = 0; e < 50; e++) 
                    {
                     q.hatch(new Forager(cn));
                    }
                    for (int b = 0; b < 4; b++) {
                     q.hatch(new Scout(cn));
                    }
                }
                if ((c == 12 && d == 12) || (c == 12 && d == 13) || (c == 12 && d == 14) || (c == 13 && d == 12) || (c == 13 && d == 13) || (c == 13 && d == 14) || (c == 14 && d == 12) || (c == 14 && d == 13) || (c == 14 && d == 14)) 
                {
                  cn.setSeen(true);
                }
            }
        }
    }

    public ArrayList<ColonyNode> getAdjacentNodes(ColonyNode node) 
    {
        int x = node.getX();
        int y = node.getY();
        ArrayList<ColonyNode> adjacentNodes;
        adjacentNodes = new ArrayList<ColonyNode>();

        for (int f = -1; f <= 1; ++f) 
        {
            for (int g = -1; g <= 1; ++g) 
            {
                if (f != 0 || g != 0) 
                {
                    try 
                    {
                     adjacentNodes.add(colonyEnvironment[x + f][y + g]);
                    } catch (Exception ex) {}
                }
            }
        }
        return adjacentNodes;
    }
}
