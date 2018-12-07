package challenge.parsing;

/**
 * This is a class that represents one blob of info
 * from the input
 */
public class DataNode {
    public char from;
    public char to;
    public int distance;
    public DataNode next;

    public DataNode(char from, char to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
}