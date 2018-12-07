package challenge.components.calculators;

import java.util.LinkedList;

import challenge.components.Station;

/*
 * This is a class that is used to allow lambdas to be passed
 * to the TownWalker to allow highly specific conditions on
 * which a path should be accepted or not
*/
public interface ICondFunc {
    /**
     * The comparison operation
     * @param station the current station the walker is located at
     * @param path the path of stations that has been walked so far
     * @param distance the distance of the path so far
     * @return a number less than 0 if the path cannot be valid
     *         a number greater than 0 if the path is accepted
     *         0 if the path should continue being walked
     */
    public int op(Station station, LinkedList<Character> path, int distance);
}