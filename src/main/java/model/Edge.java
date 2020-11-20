package model;

import java.util.Objects;

public class Edge {
    private Integer first;
    private Integer second;

    public Edge(Integer first, Integer second) {
        if (first > second) {
            this.first = second;
            this.second = first;
        } else {
            this.first = first;
            this.second = second;
        }
    }

    @Override
    public String toString() {
        return "Edge{" + first + ", " + second + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return first.equals(edge.first) && second.equals(edge.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
