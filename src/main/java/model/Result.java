package model;

import java.util.HashMap;

public class Result {
    private HashMap<Integer, Integer> coreMap;
    private Double takenTime;
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


    public Result(HashMap<Integer, Integer> coreMap, Double takenTime, String algorithmName, String type) {
        this.coreMap = coreMap;
        this.takenTime = takenTime;
        this.algorithmName = algorithmName;
        this.type = type;
    }

    public Result(HashMap<Integer, Integer> coreMap, Double takenTime, String algorithmName, String datasetName, String type) {
        this.coreMap = coreMap;
        this.takenTime = takenTime;
        this.algorithmName = algorithmName;
        this.datasetName = datasetName;
        this.type = type;
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
}
