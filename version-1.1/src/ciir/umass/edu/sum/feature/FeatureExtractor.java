package ciir.umass.edu.sum.feature;

import ciir.umass.edu.retrieval.utils.IndriSearchEngine;
import java.util.*;
import lemurproject.indri.DocumentVector;
import lemurproject.indri.ScoredExtentResult;

public class FeatureExtractor {
private IndriSearchEngine se = null;
	
	protected long maxResultsFetch = 1000;
	
 	public FeatureExtractor(String col) throws Exception
	{
		se = new IndriSearchEngine(col);
	}
	public FeatureExtractor(IndriSearchEngine se)
	{
		this.se = se;
	}
	
	/**
	 * 
	 * @param query An input entity (noun phrase in the context of tweets).
	 * @param topD
	 */
	public double[] run(String query, int topD, List<String> L)
	{
		double[] features = new double[4];
		try {
			ScoredExtentResult[] r = se.runQuery(query, topD);
		
			DiversityFeature df = new DiversityFeature(); //Diversity feature
            PMI pm = new PMI(se); //PMI feature
            Relevance re = new Relevance();


            //TopicalityFeature tf = new TopicalityFeature(se); //Topicality feature

            double diversity = df.getValue(getDocumentVectors(r), getScores(r));
            double pmi = pm.getValue(query);
            double rel = re.getValue(getDocumentVectors(r), getScores(r), query);
            //double topicality = tf.getValue(L, getDocumentIDs(r), getScores(r));

            features[0] = diversity;
            features[1] = pmi;
            features[2] = (double)se.getGramCount(query, true)/(double)se.getCollectionTermCount(); //uniqueness
            features[3] = rel;
            //features[3] = topicality;


 			//int[] docIDs = getDocumentIDs(r);
			//double uniqueness = ((double)se.runQuery(query, docIDs, docIDs.length).length) / se.runQuery(query, docIDs, docIDs.length).length);
			//System.out.println(query + "\t" + diversity);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return features;
	}
	
	protected int[] getDocumentIDs(ScoredExtentResult[] r) throws Exception
	{
		int[] docIDs = new int[r.length];
		for(int j=0;j<r.length;j++)
			docIDs[j] = r[j].document;		
		return docIDs;
	}
	protected DocumentVector[] getDocumentVectors(ScoredExtentResult[] r) throws Exception
	{
		int[] docIDs = new int[r.length];
		for(int j=0;j<r.length;j++)
			docIDs[j] = r[j].document;		
		return se.getDocumentVectors(docIDs);
	}
	protected double[] getScores(ScoredExtentResult[] r) throws Exception
	{
		double[] scores = new double[r.length];
		for(int j=0;j<r.length;j++)
			scores[j] = r[j].document;		
		return scores;
	}
}
