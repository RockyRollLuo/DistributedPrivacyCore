package model;

import java.util.HashMap;

public class Result {
    private HashMap<Integer, Integer> coreMap;
    private Double takenTime;
    private int totalRound;
    private String algorithmName;
    private String datasetName;
    private String type;


    /**
     * constructor
     */
    public Result(HashMap<Integer, Integer> coreMap, double takenTime, String algorithmName) {
        this.coreMap = coreMap;
        this.takenTime = takenTime;
        this.algorithmName = algorithmName;
    }

    public Result(HashMap<Integer, Integer> coreMap, Double takenTime, int totalRound, String algorithmName) {
        this.coreMap = coreMap;
        this.takenTime = takenTime;
        this.totalRound = totalRound;
        this.algorithmName = algorithmName;
    }


    /**
     * Getter() and Setter()
     */
    public HashMap<Integer, Integer> getCoreMap() {
        return coreMap;
    }

    public void setCoreMap(HashMap<Integer, Integer> coreMap) {
        this.coreMap = coreMap;
    }

    public Double getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Double takenTime) {
        this.takenTime = takenTime;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalRound() {
        return totalRound;
    }

    public void setTotalRound(int totalRound) {
        this.totalRound = totalRound;
    }
}
