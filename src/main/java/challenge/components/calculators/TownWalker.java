package challenge.components.calculators;

import java.util.LinkedList;

import challenge.components.exceptions.*;
import challenge.components.Station;
import challenge.components.Town;

/**
 * A helper class that can gather paths that fall
 * under user-specified criteria
 */
public class TownWalker {

    private int distance = 0;
    private Station location;
    private Town town;

    /**
     * Creates an instance of TownWalker
     * @param t the town this walker belongs to
     * @param c the station this walker starts at
     * @throws NoStationException if the town does not contain a station
     *         with the provided name
     */
    public TownWalker(Town t, char c) throws NoStationException {
        town = t;
        location = t.getStation(c);
    }

    /**
     * Creates an instance of TownWalker
     * @param start the station this walker starts at
     */
    public TownWalker(Station start) {
        town = start.getTown();
        location = start;
    }

    /**
     * Traverses all paths starting from the walker's origin until
     * all final paths are rejected by the test function
     * @param testFunction the test function
     * @return a list of paths and their distances that satisfied the
     *         test function
     */
    public WalkerData walk(ICondFunc testFunction) {
        WalkerData data = new WalkerData();
        LinkedList<Character> paths = new LinkedList();
        for (Station s: location.getAdjacencies()) {
            if (s == null) continue;
            LinkedList<Character> list = new LinkedList<Character>();
            list.add(location.getName());
            try {
                walkRecursive(testFunction, data, list, location.distanceTo(s), s);
            } catch (NoPathException e) {
                // shouldn't throw because of null checks
            }
        }
        return data;
    }

    /**
     * Recursive walk; this is private so I'll refrain from going
     * in detail
     * @param testFunction the test function
     * @param data accumulator
     * @param path path so far
     * @param distance distance so far
     * @param location current location
     * @throws NoPathException shouldn't be able to throw it
     */
    private void walkRecursive(ICondFunc testFunction,
        WalkerData data,
        LinkedList<Character> path,
        int distance,
        Station location) throws NoPathException {
        path.add(location.getName());
        // here the lambda is used to test to determine
        // if the path should continue to be traversed
        // more deeply
        int result = testFunction.op(location, path, distance);
        if (result < 0) {
            return;
        } else if (result > 0) {
            data.append(path, distance);
        }
        for (Station s: location.getAdjacencies()) {
            if (s == null || s.getName() == location.getName()) continue;
            LinkedList<Character> pathClone = (LinkedList<Character>) path.clone();
            walkRecursive(testFunction, data, pathClone, distance + location.distanceTo(s), s);
        }
    }
}

