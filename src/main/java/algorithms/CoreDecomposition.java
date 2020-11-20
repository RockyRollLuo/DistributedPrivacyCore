package algorithms;

import model.Graph;
import model.Result;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CoreDecomposition {
    private static Logger LOGGER = Logger.getLogger(CoreDecomposition.class);

    private final Graph graph;

    public CoreDecomposition(Graph graph) {
        this.graph = graph;
    }

    public Result run() {
        long startTime = System.nanoTime();

        HashMap<Integer, ArrayList<Integer>> adjMap = graph.getAdjMap();
        HashMap<Integer, Integer> degMap = graph.getDegMap();
        ArrayList<Integer> tempNodeList = new ArrayList<>(graph.getNodeList());

        //coreMap
        HashMap<Integer, Integer> coreMap = new HashMap<>();

        for (int k = 1; ; k++) {

            if (tempNodeList.isEmpty()) {
                break;
            }

            LinkedList<Integer> delQueue = new LinkedList<>();
            for (Integer node : degMap.keySet()) {
                if (degMap.get(node) <= k) {
                    delQueue.offer(node);
                }
            }

            while (!delQueue.isEmpty()) {
                LinkedList<Integer> newDelQueue = new LinkedList<>();

                Integer node = delQueue.poll();

                ArrayList<Integer> nodeNeigbors = adjMap.get(node);
                for (Integer nodeNei : nodeNeigbors) {
                    int degNodeNei = degMap.get(nodeNei) - 1;
                    degMap.put(nodeNei, degNodeNei);

                    if (degNodeNei == k) {
                        newDelQueue.offer(nodeNei);
                    }
                }
                coreMap.put(node, k);

                delQueue = newDelQueue;
            }
        }

        long endTime = System.nanoTime();
        LOGGER.info("TakenTime:" + (double) (endTime - startTime) / 1.0E9D);
        return new Result(coreMap, System.nanoTime() - startTime, "CoreDecomposition");
    }

}
