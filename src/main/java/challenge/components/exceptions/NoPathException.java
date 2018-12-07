package challenge.components.exceptions;

import challenge.components.Station;

/**
 * A custom exception class for when a path that
 * doesn't exist between two stations is accessed
 */
public class NoPathException extends Exception {
    public NoPathException() {
    }

    public NoPathException(Station from, Station to) {
        super(String.format("There is no path directly between Station %s and Station %s", from.getName(), to.getName()));
    }

    public NoPathException(char from, char to) {
        super(String.format("There is no path directly between Station %s and Station %s", from, to));
    }

    public NoPathException(char from, Station to) {
        super(String.format("There is no path directly between Station %s and Station %s", from, to.getName()));
    }

    public NoPathException(Station from, char to) {
        super(String.format("There is no path directly between Station %s and Station %s", from.getName(), to));
    }

    public NoPathException(String message) {
        super(message);
    }
}