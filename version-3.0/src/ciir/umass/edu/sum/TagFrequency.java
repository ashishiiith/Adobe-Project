package ciir.umass.edu.sum;
import java.io.*;
import java.util.List;
import ciir.umass.edu.retrieval.utils.IndriSearchEngine;
/**
 * Created by ashishjain on 5/21/14.
 */
public class TagFrequency{

    public String getTagFrequency(String query) throws Exception{

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(new String[]{"bash","-c", "/bolt/ashishjain/zwang/indri-bolt/dumpindex/dumpindex /usr/dan/users4/ashishjain/tagIndexes/DecIndex xcount "+ query});
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line=null;
        if ((line=input.readLine()) != null) {
            System.out.println(line);
        }
        String result=null;
        try {
        //result=line.split(":")[1];
        result=line;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return result;
    }
    public static void main(String[] args) throws  Exception {

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("/bolt/ashishjain/zwang/indri-bolt/dumpindex/dumpindex /usr/dan/users4/ashishjain/indexes/DecIndex xcount obama");
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line=null;

        if ((line=input.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println(line.split(":")[1]);
    }

}
