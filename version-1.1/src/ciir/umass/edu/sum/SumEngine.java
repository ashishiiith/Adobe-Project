package ciir.umass.edu.sum;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.lemurproject.kstem.KrovetzStemmer;

import lemurproject.indri.DocumentVector;
import lemurproject.indri.ScoredExtentResult;
import ciir.umass.edu.retrieval.utils.IndriSearchEngine;
import ciir.umass.edu.retrieval.utils.QueryProcessor;

public class SumEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			
			SumEngine se = new SumEngine(args[0]);
			se.extractNPs(args[1], args[2]);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	

	protected int topD = 100;  

	protected IndriSearchEngine se = null;
	protected KrovetzStemmer stemmer = new KrovetzStemmer();
	public SumEngine(){}
	public SumEngine(String col)
	{		
		try {
			se = new IndriSearchEngine(col);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void extractNPs(String queryFile, String outputFile)
	{
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
			
			List<String> qids = new ArrayList<String>();
			List<String> qtexts = new ArrayList<String>();
			QueryProcessor.readIndriQueryFile(queryFile, qids, qtexts);
			for(int i=0;i<qids.size();i++)
			{
				String query = QueryProcessor.removeStopWords(qtexts.get(i), false);
				List<String> nps = extractNPs(query);
				for(int j=0;j<nps.size();j++)
				{
					out.write(qids.get(i) + "\t" + nps.get(j));
					out.newLine();					
				}
			}
			out.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public List<String> extractNPs(String query) 
	{
		List<String> nps = new ArrayList<String>();
		try {
			ScoredExtentResult[] r = se.runQuery(query, topD);
			//get docid and score
			int size = Math.min(topD, r.length);
			int[] docIDs = new int[size];
			double[] scores = new double[size];
			for(int j=0;j<r.length;j++)
			{
				docIDs[j] = r[j].document;
				double score = r[j].score;
				if(r[j].score < 0)
					score = Math.exp(score);
				else
					score = Double.MIN_VALUE;
				scores[j] = score;
			}
			//Pull out the parsed document vector
			DocumentVector[] dvs = se.getDocumentVectors(docIDs);
			for(int v=0;v<dvs.length;v++)
			{
				DocumentVector dv = dvs[v];
				String content = "";
				int[] pos = dv.positions;
				for(int i=0;i<pos.length;i++)
					content += dv.stems[pos[i]] + ((i==pos.length-1)?"":" ");
	
				//#content is the content of the tweet
				//run it through a tagger to get the noun phrases			
				String[] ws = content.split("\\s+");
				for(int i=0;i<ws.length;i++)
					nps.add(ws[i]);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return nps;
	}		

	protected String stem(String text)
	{
		String[] s = text.split("\\s+");
		String output = "";
		for(int i=0;i<s.length;i++)
			output += stemmer.stem(s[i]) + ((i<s.length-1)?" ":"");
		return output;
	}
}
