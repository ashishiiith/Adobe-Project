package ciir.umass.edu.sum;
import java.util.*;
/**
 *  * Created by ashishjain on 6/3/14.
 *   */


class ValueComparator implements Comparator {

    Map map;

    public ValueComparator(Map map){
        this.map = map;
    }
    public int compare(Object keyA, Object keyB){

        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);

        //System.out.println(valueA +" - "+valueB);

        return valueA.compareTo(valueB);

    }
}
public class MapSort {


    public static Map sortByValue(Map unsortedMap){
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }
    public static Map sortByKey(Map unsortedMap){
        Map sortedMap = new TreeMap();
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }
}

