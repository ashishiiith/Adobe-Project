package ciir.umass.edu.sum.feature;

/**
 * Created by ashishjain on 4/5/14.
 */
import ciir.umass.edu.qproc.Stopper;
import ciir.umass.edu.retrieval.utils.IndriSearchEngine;
import ciir.umass.edu.retrieval.utils.QueryProcessor;
import lemurproject.indri.DocumentVector;

import java.util.*;

public class PMI {
    //local variables
    private IndriSearchEngine se = null;
    public PMI(IndriSearchEngine se)
    {
        this.se = se;
    }
    public double getValue(String Phrase)
    {
        String[] s = Phrase.split("\\s+");
        double final_pmi = 0.0;
        double pmi = 0.0;
        int i=1; //start from index i=1. For unigram, pmi=0.0
        try {
            while(i < s.length) {
                if(!Stopper.getInstance().isStop(s[i])) {
                    String gram = s[i-1] + " " + s[i];
                    gram = QueryProcessor.makeIndriFriendly(gram);
                    double c1 = se.getTermCount(s[i-1], false);
                    double c2 = se.getTermCount(s[i], false);
                    double c = 0;
                    if(gram.compareTo("") != 0)
                        c = se.getGramCount(gram, true);
                    if(c1 > 0 && c2 > 0)
                    {
                        pmi = c / c1;
                        pmi *= se.getCollectionTermCount();
                        pmi /= c2;
                    }
                }
                final_pmi += pmi;
                i++;
            }
            final_pmi = final_pmi/(s.length-1);
            System.out.println("final_pmi: " + final_pmi);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return final_pmi;
    }
}
