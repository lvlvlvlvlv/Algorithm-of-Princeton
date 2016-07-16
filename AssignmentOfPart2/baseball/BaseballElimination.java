import java.util.HashMap;
import edu.princeton.cs.algs4.*;
public class BaseballElimination
{
    private HashMap<String,Integer> teams;
    private int N;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] against;
    private boolean trivial = false;
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename)
    {
        
        In in = new In(filename);
        this.N = Integer.parseInt(in.readLine());
        
        teams = new HashMap<String,Integer>(this.N);
        String[] tmp = new String[4 + this.N];
        wins = new int[this.N];
        losses = new int[this.N];
        remaining = new int[this.N];
        against = new int[this.N][this.N];
        int count = 0;
        while (!in.isEmpty())
        {
           tmp = in.readLine().trim().split("\\s+");
           teams.put(tmp[0],count);
           wins[count] = Integer.parseInt(tmp[1]);
           losses[count] = Integer.parseInt(tmp[2]);
           remaining[count] = Integer.parseInt(tmp[3]);
           for (int i = 0; i < this.N; i++)
           {
               against[count][i] = Integer.parseInt(tmp[4 + i]);
           }
           count++;
          
        }
        
        
    }
    // number of teams
    public int numberOfTeams()
    {
        return this.N;
    }
    // all teams
    public Iterable<String> teams()
    {
        return this.teams.keySet();
    }
    // number of wins for given team
    public int wins(String team)
    {
        if (team == null || this.teams.get(team) == null)
            throw new java.lang.IllegalArgumentException();
        int value = this.teams.get(team);
        return this.wins[value];
        
    }
    // number of losses for given team
    public int losses(String team)
    {
        if (team == null || this.teams.get(team) == null)
            throw new java.lang.IllegalArgumentException();
        int value = this.teams.get(team);
        return this.losses[value];
    }
    // number of remaining games for given team
    public int remaining(String team)
    {
        if (team == null || this.teams.get(team) == null)
            throw new java.lang.IllegalArgumentException();
        int value = this.teams.get(team);
        return this.remaining[value];
    }
    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        if (team1 == null || this.teams.get(team1) == null || team2 == null || this.teams.get(team2) == null)
            throw new java.lang.IllegalArgumentException();
        int value1 = this.teams.get(team1);
        int value2 = this.teams.get(team2);
        return this.against[value1][value2];
    }
    // is given team eliminated?
    public boolean isEliminated(String team)
    {
        // exception situation
        if (team == null || this.teams.get(team) == null)
            throw new java.lang.IllegalArgumentException();
        
        
        // trivial situation. No need to build FlowNetwork
        int maxWins = wins(team) + remaining(team);
        for (String s : teams())
        {
            if (!s.equals(team) && wins(s) > maxWins)
            {
                this.trivial = true;
                return true;
                
            }
        }
        FlowNetwork G = drawFlowNet(team);
        FordFulkerson ffk = new FordFulkerson(G,0,G.V()-1);
        for (FlowEdge e : G.adj(0))
        {
            if (e.flow() < e.capacity())
            {
                this.trivial = false;
                return true;
                
                
            }
        }
        return false;
    }
    // draw a flow net except the argument team
    private FlowNetwork drawFlowNet(String team)
    {
        String[] a = new String[this.N - 1];
        int k = 0;
        for (String s : teams())
        {
            // new point need to be learned
            if (s.equals(team))
            {
                continue;
            }
            else
            {
                a[k] = s;
                k++;
            }
        }
//        StdOut.println(a[0]);
//        StdOut.println(a[1]);
//        StdOut.println(a[2]);    
//        StdOut.println(a[3]);    

        

        int numberOfVertexAgainst = numberOfVertexAgainst();
        int V = 2 + numberOfVertexAgainst + this.N - 1;
        FlowNetwork fnw = new FlowNetwork(V);
        int vertex = 1;
        for (int m = 0; m < a.length - 1; m++)
        {
            for (int n = m + 1; n < a.length; n++)
            {
                FlowEdge e = new FlowEdge(0,vertex,against(a[m],a[n]),0);
                fnw.addEdge(e);
                vertex++;
            }
            
        }
        
        vertex = 1;
        for (int m = numberOfVertexAgainst + 1; m < V - 1; m++)
        {
            for (int n = m + 1; n < V - 1; n++)
            {
                FlowEdge e = new FlowEdge(vertex,m,Double.POSITIVE_INFINITY,0);
                FlowEdge f = new FlowEdge(vertex,n,Double.POSITIVE_INFINITY,0);
                fnw.addEdge(e);
                fnw.addEdge(f);
                vertex++;
            }
        }
        
        for (int m = 0; m < a.length; m++)
        {
            if (wins(team)+remaining(team)-wins(a[m]) < 0)
            {
                FlowEdge e = new FlowEdge(numberOfVertexAgainst + 1 + m,V-1,0,0);
                fnw.addEdge(e);
            }
            else
            {
                FlowEdge e = new FlowEdge(numberOfVertexAgainst + 1 + m,V-1,wins(team)+remaining(team)-wins(a[m]),0);
                fnw.addEdge(e);
            }
            
            
        }
            
    return fnw;   
        
    }
    private int numberOfVertexAgainst()
    {
        int sum = 0;
        for (int j = this.N - 2; j > 0; j--)
        {
            sum += j;
        }
        return sum;
    }
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)
    {
        Queue<String> q = new Queue<String>(); 
        if (isEliminated(team))
        {
            // trivial situation
            if (this.trivial == true)
            {
                int maxWins = wins(team) + remaining(team);
                for (String s : teams())
                {
                    if (!s.equals(team) && wins(s) > maxWins)
                    {
                
                        q.enqueue(s);
                
                    }
                }
            }
            else
            {
                String[] a = new String[this.N - 1];
                int k = 0;
                for (String s : teams())
                {
                    // new point need to be learned
                    if (s.equals(team))
                    {
                        continue;
                    }
                    else
                    {
                        a[k] = s;
                        k++;
                    }
                }
                int numberOfVertexAgainst = numberOfVertexAgainst();
                FlowNetwork G = drawFlowNet(team);
                FordFulkerson ffk = new FordFulkerson(G,0,G.V()-1);
                int count = -1;
                for (int i = numberOfVertexAgainst + 1; i < G.V()-1; i++)
                {
                    count++;
                    if (ffk.inCut(i))
                    {
                        
                        q.enqueue(a[count]);
                    }
                }
            }
            
            
        }
        
        else
        {
            return null;
        }
        return q;
        
    }
    
    public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    //StdOut.println(division.numberOfTeams());
    //StdOut.println(division.teams());
    //StdOut.println(division.wins("Atlanta"));
    //StdOut.println(division.losses("Philadelphia"));
    //StdOut.println(division.remainings("Montreal"));
    //StdOut.println(division.numberOfVertexAgainst());
    //division.drawFlowNet("Atlanta");
    //StdOut.println(division.drawFlowNet("Team23").toString());
    //StdOut.println(division.isEliminated("Atlanta"));
    
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team)) {
                StdOut.print(t + " ");
            }
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
    }
    
}