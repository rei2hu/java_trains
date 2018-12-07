package challenge.components.calculators;

import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.Iterator;

public class WalkerData {
    // this part specifically is causing the warnings
    // because of the use of nested generic types
    // it could be simply avoided by implementing
    // my own classes to handle the data
    private LinkedList<LinkedList<Character>> paths = new LinkedList<LinkedList<Character>>();
    private LinkedList<Integer> distances = new LinkedList<Integer>();

    public void append(LinkedList<Character> path, int distance) {
        paths.add(path);
        distances.add(distance);
    }

    public int size() {
        return distances.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = distances.iterator();

        for (LinkedList<Character> path: paths) {
            int distance = it.next();
            sb.append("Path (distance: ");
            sb.append(distance);
            sb.append("): ");
            for (char c: path) {
                sb.append(c);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}