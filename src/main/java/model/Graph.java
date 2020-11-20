package model;

import com.sun.applet2.AppletParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private ArrayList<Integer> nodeList;
    private ArrayList<Edge> edgeList;
    private HashMap<Integer, ArrayList<Integer>> adjMap;

    public Graph(ArrayList<Integer> nodeList, ArrayList<Edge> edgeList, HashMap<Integer, ArrayList<Integer>> adjMap) {
        this.nodeList = nodeList;
        this.edgeList = edgeList;
        this.adjMap = adjMap;
    }

    /*
    method
     */

    public HashMap<Integer,Integer> getDegMap() {
        HashMap<Integer, Integer> degMap = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Integer>> map : adjMap.entrySet()) {
            degMap.put(map.getKey(), map.getValue().size());
        }
        return degMap;
    }



    /*
    getter and setter
     */

    public ArrayList<Integer> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Integer> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public HashMap<Integer, ArrayList<Integer>> getAdjMap() {
        return adjMap;
    }

    public void setAdjMap(HashMap<Integer, ArrayList<Integer>> adjMap) {
        this.adjMap = adjMap;
    }
}
