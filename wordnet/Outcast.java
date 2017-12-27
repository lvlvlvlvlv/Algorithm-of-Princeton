import edu.princeton.cs.algs4.*;
public class Outcast
{
    private WordNet wordnet;
    public Outcast(WordNet wordnet)
    {
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns)
    {
        
        int dis_max = 0;
        int dis = 0;
        String noun = "";
        for (int i = 0; i < nouns.length; i++)
        {
           dis = 0;
           for (int j = 0; j < nouns.length; j++)
           {
                dis += this.wordnet.distance(nouns[i],nouns[j]);
           } 
           StdOut.println(dis);
           if (dis >= dis_max)
           {
               dis_max = dis;
               noun = nouns[i];
           }
        }
        
        return noun;
        
    }
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
    }
}