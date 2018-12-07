package challenge.components;

import java.util.Arrays;
import java.lang.StringBuilder;

import challenge.components.exceptions.*;

/**
 * This class represents a town
 */
public class Town {

    private static final int FAKE_INFINITY = 9999;

    private String name;
    private Station[] stations = new Station[26];

    /**
     * Creates a town with a certain name
     * @param name the name of the town
     */
    public Town(String name) {
        this.name = name;
    }

    /**
     * Gets a station in the town
     * @param c the name of the station
     * @return the station
     * @throws NoStationException if the station doesn't exist
     */
    public Station getStation(char c) throws NoStationException {
        int index = c - 'A';
        if (stations[index] == null) {
            throw new NoStationException(c);
        }
        return stations[index]; 
    }

    /**
     * Gets the name of the town
     * @return the name of the town
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the minimum distances of all stations
     * in this town
     */
    protected void updateMinDistances() {
        int [][] distances = new int[26][26];
        String[][] paths = new String[26][26];

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (stations[i] == null || stations[j] == null || !stations[i].isAdjacentTo(stations[j])) {
                    // using 9999 here is risky because if a path is over that length, the shortest
                    // distance will be marked as 9999. however, due to how the algorithm works,
                    // using Integer.MAX_VALUE will wrap around to the negatives when adding and 
                    // cause even weirder results.
                    distances[i][j] = FAKE_INFINITY;
                } else {
                    try {
                        distances[i][j] = stations[i].distanceTo(stations[j]);
                        paths[i][j] = Character.toString((char) ('A' + i)) + Character.toString((char) ('A' + j));
                    } catch(NoPathException e) {
                        // swallow this exception because it shouldn't occur
                        // with the checks above
                    }
                }
            }
        }

        for (int i = 0; i < 26; i++) {
            if (stations[i] == null) continue;
            for (int j = 0; j < 26; j++) {
                if (stations[j] == null) continue;
                for (int k = 0; k < 26; k++) {
                    if (j == k || stations[k] == null) continue;
                    int dist = distances[j][i] + distances[i][k];
                    if (distances[j][k] <= dist) continue;
                    distances[j][k] = dist;
                    paths[j][k] = paths[j][i].substring(0, paths[j][i].length() - 1) + paths[i][k];
                }
            }
        }

        for(int i = 0; i < 26; i++) {
            if (stations[i] == null) continue;
            for (int j = 0; j < 26; j++) {
                if (stations[j] == null || distances[i][j] == FAKE_INFINITY) continue;
                // update the minimum distance for the nodes and update the paths
                stations[i].setMinDistanceTo(stations[j], distances[i][j], paths[i][j]);
            }
        }
    }

    /**
     * Adds a path to the town
     * @param from the path's origin
     * @param to the path's destination
     * @param distance the distance of the path
     */
    public void update(char from, char to, int distance) {
        // an assumption here is that stations are represented by
        // upper case characters so we can take advantage of that
        // to calculate the index for an array instead of using
        // something heavier like a map.
        int fromIndex = from - 'A';
        int toIndex = to - 'A';
        Station fromStation, toStation;
        if (stations[fromIndex] == null) {
            fromStation = stations[fromIndex] = new Station(this, from);
        } else {
            fromStation = stations[fromIndex];
        }
        if (stations[toIndex] == null) {
            toStation = stations[toIndex] = new Station(this, to);
        } else {
            toStation = stations[toIndex];
        }
        fromStation.addOrUpdateAdjacentStation(toStation, distance);
    }

    /**
     * Returns a string representation of the town
     * @return the string representation of the town
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Town: ");
        sb.append(name);
        sb.append("\n");
        for (Station s: stations) {
            if (s == null) continue;
            sb.append(s.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}