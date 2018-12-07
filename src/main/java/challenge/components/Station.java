package challenge.components;

import java.lang.StringBuilder;

import challenge.components.exceptions.*;

/**
 * This class represents a single train station
 * in the town
 */
public class Station {

    private Town town;
    private char name;
    private Station[] adjacencies = new Station[26];
    private int[] distances = new int[26];

    // because it's likey that what people will care about
    // is minimum distances and their paths, it's better to
    // cache them here instead of calculating it each time
    // although a nicer way would utilize a class that stored
    // path + distance instead of two separate arrays
    private int[] minDistances = new int[26];
    private String[] minDistancePaths = new String[26];

    /**
     * Creates an instance of Station
     * @param t the town this station belongs to
     * @param c the name of this station
     */
    public Station(Town t, char c) {
        town = t;
        name = c;
    }

    /**
     * Adds or updates a the distance of a station adjacent to this one
     * @param to the station that to update
     * @param distance the distance to the station that is being updated
     */
    public void addOrUpdateAdjacentStation(Station to, int distance) {
        int toIndex = to.getName() - 'A';
        // note that this does not throw an exception when a station
        // is added when it already exists because this is can also
        // be used for updating the distance between stations
        distances[toIndex] = distance;
        adjacencies[toIndex] = to;
        // if the station already existed, that means
        // a path to it was updated. in this case we have to
        // update the minimum distances for each station
        // because maybe a newer, shorter path opened up.
        // if this station did not exist before, then
        // this implies there are no paths leading out of
        // that station so there is no need to update
        if (adjacencies[toIndex] != null) {
            town.updateMinDistances();
        } else {
            minDistances[toIndex] = distance;
        }
    }

    /**
     * Adds or updates a the distance of a station adjacent to this one
     * @param c the name of the station to update
     * @param distance the distance to the station that is being updated
     */
    public void addOrUpdateAdjacentStation(char c, int distance) {
        addOrUpdateAdjacentStation(new Station(town, c), distance);
    }

    /**
     * Checks if a station is adjacent to this one
     * @param s the station to check
     * @return true if the station is adjacent to this one, false otherwise
     */
    public boolean isAdjacentTo(Station s) {
        return isAdjacentTo(s.getName());
    }

    /**
     * Checks if a station is adjacent to this one
     * @param c the name of the station to check
     * @return true if the station is adjacent to this one, false otherwise
     */
    public boolean isAdjacentTo(char c) {
        int index = c - 'A';
        return adjacencies[index] != null;
    }

    /**
     * Gets a station that is adjacent to this one
     * @param s the station to get
     * @return the station to get
     * @throws NoPathException if no path exists between this station and
     *         the station to get
     */
    public Station getAdjacentStation(Station s) throws NoPathException{
        return getAdjacentStation(s.getName());
    }

    /**
     * Gets a station that is adjacent to this one
     * @param c the name of the station to get
     * @return the station to get
     * @throws NoPathException if no path exists between this station and
     *         the station to get
     */
    public Station getAdjacentStation(char c) throws NoPathException {
        int index = c - 'A';
        if (adjacencies[index] == null) {
            throw new NoPathException(this, c);
        }
        return adjacencies[index];
    }

    /**
     * Updates the minimum distance of this station to another one
     * @param s the destination of the path to update
     * @param distance the distance of the shortest path
     * @param path the labels of the stations on the shortest path
     */
    protected void setMinDistanceTo(Station s, int distance, String path) {
        setMinDistanceTo(s.getName(), distance, path);
    }

    /**
     * Updates the minimum distance of this station to another one
     * @param c the name of the destination of the path to update
     * @param distance the distance of the shortest path
     * @param path the labels of the stations on the shortest path
     */
    protected void setMinDistanceTo(char c, int distance, String path) {
        int index = c - 'A';
        minDistances[index] =  distance;
        minDistancePaths[index] = path;
    }

    /**
     * Gets the minimum distance between this station and a target station
     * @param s the target station
     * @return this minimum distance
     * @throws NoPathException if no path exists between these stations
     */
    public int minDistanceTo(Station s) throws NoPathException {
        return minDistanceTo(s.getName());
    }

    /**
     * Gets the minimum distance between this station and a target station
     * @param c the name of the target station
     * @return this minimum distance
     * @throws NoPathException if no path exists between these stations
     */
    public int minDistanceTo(char c) throws NoPathException {
        int index = c - 'A';
        if (minDistances[index] == 0 && c != name) {
            throw new NoPathException(this, c);
        }
        return minDistances[index];
    }

    /**
     * Gets the distance of the direct path between this station and another
     * @param s the target station
     * @return the distance of the path
     * @throws NoPathException if no path exists between the stations
     */
    public int distanceTo(Station s) throws NoPathException {
        return distanceTo(s.getName());
    }

    /**
     * Gets the distance of the direct path between this station and another
     * @param c the name of the target station
     * @return the distance of the path
     * @throws NoPathException if no path exists between the stations
     */
    public int distanceTo(char c) throws NoPathException {
        int index = c - 'A';
        if (adjacencies[index] == null && c != name) {
            throw new NoPathException(this, c);
        }
        return distances[index];
    }

    /**
     * Gets the town this station belongs to
     * @return the town
     */
    public Town getTown() {
        return town;
    }

    /**
     * Gets the town this station belongs to
     * @return the town
     */
    public char getName() {
        return name;
    }

    /**
     * Gets the adjacency array of this station
     * @return the adjacency array
     */
    public Station[] getAdjacencies() {
        return adjacencies;
    }

    /**
     * Returns a string representation of this station
     * @return the string representation of this station
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(name);
        sb.append("\n");
        sb.append("Has Paths to: ");
        for (Station s: adjacencies) {
            if (s == null) continue;
            sb.append(s.getName());
            sb.append("(");
            try {
                sb.append(distanceTo(s));
            } catch (NoPathException e) {
                // shouldnt error because nulls are ignored
                // above
            }
            sb.append(") ");
        }
        return sb.toString();
    }
}