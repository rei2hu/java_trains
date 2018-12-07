package challenge.components.calculators;

import challenge.components.exceptions.*;
import challenge.components.Station;
import challenge.components.Town;

/**
 * A helper class that allows easy calculation of
 * distances for the specified path
 */
public class DistanceAggregator {

    private int distance = 0;
    private Station location;
    private Town town;

    /**
     * Creates an instance of DistanceAggregator
     * @param t the town that this aggregator belongs to
     * @param c the name of the station this aggregator will start at
     * @throws NoStationException if the town does not contain a station
     *         the provided name
     */
    public DistanceAggregator(Town t, char c) throws NoStationException {
        town = t;
        location = t.getStation(c);
    }

    /**
     * Creates an instance of DistanceAggregator
     * @param start the station where this aggregator will start at
     */
    public DistanceAggregator(Station start) {
        town = start.getTown();
        location = start;
    }

    /**
     * Moves this aggregator from its current station to the specified station
     * @param c the station to move to
     * @return itself
     * @throws NoStationException if the station to move to doesn't exist
     * @throws NoPathException if there is no path between the current station and
     *         the target station
     */
    public DistanceAggregator toStation(char c) throws NoStationException, NoPathException {
        Station s = town.getStation(c);
        distance += location.distanceTo(c);
        location = s;
        return this;
    }

    /**
     * Calculates the distance of the path traversed
     * @return the distance
     */
    public int calculate() {
        return distance;
    }
}