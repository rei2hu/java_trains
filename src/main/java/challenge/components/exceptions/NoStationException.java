package challenge.components.exceptions;

import challenge.components.Station;

/**
 * A custom exception class for when a station
 * that doesn't exist in the town is accessed
 */
public class NoStationException extends Exception {
    public NoStationException() {
    }

    public NoStationException(Station s) {
        super(String.format("Station %s doesn't exist", s.getName()));

    }

    public NoStationException(char c) {
        super(String.format("Station %s doesn't exist", c));
    }
}