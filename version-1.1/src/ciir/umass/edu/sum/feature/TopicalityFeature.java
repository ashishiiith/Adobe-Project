package ciir.umass.edu.sum.feature;

import ciir.umass.edu.sum.Hierarchy;
import ciir.umass.edu.retrieval.utils.IndriSearchEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashishjain on 4/5/14.
 */
public class TopicalityFeature {
    private IndriSearchEngine se = null;

    public TopicalityFeature(IndriSearchEngine se)
    {
        this.se = se;
    }

    public double getValue(List<String> Phrase)
    {
        //se.getDocumentVectors(se.docInternalIDs());
        Hierarchy h = new Hierarchy(se);
       // h.estimate(Phrase, 1000);
        return 0.0;
    }
}
