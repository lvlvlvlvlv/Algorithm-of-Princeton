import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;

public class WordNet {
   private ArrayList<String> synset = new ArrayList<String>();
   private HashMap<String,LinkedList<Integer>> ids = new HashMap<String,LinkedList<Integer>>();
   private HashSet<String> nouns = new HashSet<String>();
   private SAP sap;
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)
   {
       if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
       int V = 0;
       In insy = new In(synsets);
       In inhy = new In(hypernyms);
       
       //read every line of synsets
       while(!insy.isEmpty())
       {
           String sy = insy.readLine();
           String[] fields = sy.split(",");
           Integer id = Integer.parseInt(fields[0]);
           this.synset.add(fields[1]);
           String[] sec_fields = fields[1].split(" ");
           for (String noun : sec_fields)
           {
               if (this.nouns.contains(noun))
               {
                   this.ids.get(noun).add(id);
               }
               else
               {
                   this.nouns.add(noun);
                   LinkedList<Integer> tmp = new LinkedList<Integer>();
                   tmp.add(id);
                   this.ids.put(noun,tmp);
               }
           }
           V++;
       }
       
       Digraph G = new Digraph(V);
       //read every line of hypernyms and construct a digraph
       while(!inhy.isEmpty())
       {
           String hy = inhy.readLine();
           String[] fields = hy.split(","); 
           int v = Integer.parseInt(fields[0]); 
           for (int i = 1; i < fields.length; i++)
           {
               G.addEdge(v,Integer.parseInt(fields[i]));
           }
       }
       //check if this digraph contains a cycle
       DirectedCycle directedCycle = new DirectedCycle(G);  
       if (directedCycle.hasCycle())
       {  
            throw new java.lang.IllegalArgumentException();  
       }  
       
       //check if this digraph only one root
       int rootnum = 0;
       for (int v = 0; v < G.V(); v++)
       {
           if (G.outdegree(v) == 0)
           {
               rootnum++;
           }
           if (rootnum > 1)
           {
               throw new java.lang.IllegalArgumentException();
           }
       }
       this.sap = new SAP(G);
       
       
       
   }

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
       return this.nouns;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word)
   {
       if (word == null)
           throw new java.lang.NullPointerException();
       if (this.nouns.contains(word))
           return true;
       else
           return false;
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB)
   {
       if (nounA == null || nounB == null)
           throw new java.lang.NullPointerException();
       if (!isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();
       
       
       int dis = this.sap.length(this.ids.get(nounA),this.ids.get(nounB));
       return dis;    
   }
   

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
       if (nounA == null || nounB == null)
           throw new java.lang.NullPointerException();
       if (!isNoun(nounA) || !isNoun(nounB))
           throw new java.lang.IllegalArgumentException();
       
       
       return synset.get(sap.ancestor(this.ids.get(nounA),this.ids.get(nounB)));
       
   }

   // do unit testing of this class
   public static void main(String[] args)
   {
       WordNet wt = new WordNet(args[0],args[1]);
       StdOut.println(wt.distance("thrombokinase","Lipo-Hepin"));
       StdOut.println(wt.sap("thrombokinase","Lipo-Hepin"));
       
       
       //StdOut.println(wt.syTree.get("bird"));
       //StdOut.println(G);
       //StdOut.println(wt.sap("worm","bird"));
       //StdOut.println(wt.distance("Brown_Swiss","barrel_roll"));
       //StdOut.println(wt.nouns());
       //StdOut.println(wt.isNoun("x"));
       /*Iterator it = wt.syTree.entrySet().iterator();
       while (it.hasNext())
       {
           Map.Entry entry = (Map.Entry) it.next();
           Object key = entry.getKey();
           Object value = entry.getValue();
           System.out.println(entry);
           System.out.println(key);
           System.out.println(value);
       }*/
       /*for (int i = 0; i < wt.hyList.size(); i++)
       {
           for (String a : wt.hyList.get(i))
               StdOut.println(a);
       }*/
   }
}