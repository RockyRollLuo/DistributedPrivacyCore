package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ComputeUtils {

    /**
     * compute a < k.num > map from a < k,v> map
     *
     * @param map < k,v>
     * @return < k,num>
     */
    public static HashMap<Integer, Integer> computeDistribution(HashMap<Integer, Integer> map) {
        HashMap<Integer, Integer> numMap = new HashMap<>();
        for (int value : map.values()) {
            int num = numMap.get(value) == null ? 1 : numMap.get(value) + 1;
            numMap.put(value, num);
        }
        return numMap;
    }

    /**
     * compute a accumulative proportion list from a < k,v> map
     *
     * @param map < k, v>
     * @return proportion double list
     */
    public static ArrayList<Double> computeAccumulativeProportion(HashMap<Integer, Integer> map) {
        HashMap<Integer, Integer> numMap = computeDistribution(map);

        int allSum = 0;
        for (Integer key : numMap.keySet()) {
            allSum += numMap.get(key);
        }

        int curSum = 0;
        HashMap<Integer, Double> proprotionMap = new HashMap<>();
        for (Integer key : numMap.keySet()) {
            curSum += numMap.get(key);
            double precent = 1.0 * curSum / allSum;
            proprotionMap.put(key, (new BigDecimal(precent * 100).setScale(3, RoundingMode.FLOOR)).doubleValue());
        }

        return (ArrayList<Double>) proprotionMap.values();
    }

    /**
     * choose dynamic edges by cardinality distribution
     *
     * @param edgeMap     edgeMap
     * @param dynamicNums the number of dynamic edges
     * @param cardi       cardinality distribution
     * @return id list of dynamic edges
     */
    public static ArrayList<Integer> computeDynamicEdges(HashMap<Integer, ArrayList<Integer>> edgeMap, int dynamicNums, int cardi) {
        ArrayList<Integer> dynamicEdges = new ArrayList<>();

        HashMap<Integer, Integer> edgeCardiMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : edgeMap.entrySet()) {
            Integer eId = entry.getKey();
            ArrayList<Integer> edgeNodes = entry.getValue();

            edgeCardiMap.put(eId, edgeNodes.size());
        }
        edgeCardiMap = (HashMap<Integer, Integer>) GetUtils.getSortMapByValue(edgeCardiMap, 0);

        ArrayList<Integer> sortedNodesList = new ArrayList<>(edgeCardiMap.keySet());

        int length = sortedNodesList.size();
        int index1 = length * 3 / 10;
        int index2 = length * 7 / 10;

        ArrayList<Integer> choosenSubList = null;

        if (cardi == 0) {//low
            choosenSubList = new ArrayList<>(sortedNodesList.subList(0, index1));
        } else if (cardi == 2) {//high
            choosenSubList = new ArrayList<>(sortedNodesList.subList(index2, length));
        }
        Collections.shuffle(choosenSubList);
        dynamicEdges = new ArrayList<>(choosenSubList.subList(0, dynamicNums));

        return dynamicEdges;
    }


}

