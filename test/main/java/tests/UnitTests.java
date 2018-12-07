package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

import challenge.parsing.Parser;
import challenge.parsing.IParser;
import challenge.parsing.DataNodeList;
import challenge.parsing.DataNode;
import challenge.components.Town;
import challenge.components.calculators.DistanceAggregator;
import challenge.components.calculators.TownWalker;
import challenge.components.calculators.WalkerData;
import challenge.components.exceptions.NoStationException;
import challenge.components.exceptions.NoPathException;

public class UnitTests {

    static IParser<DataNodeList> parser = new Parser("./test/fixtures/input");
    static Town t = new Town("TestTown");
    static {
        DataNodeList list = parser.parse();
        for (DataNode n: list) {
            t.update(n.from, n.to, n.distance);
        }
    }

    @Test
    public void parsesInputDataProperly() {
        DataNodeList list = parser.parse();
        Iterator<DataNode> it = list.iterator();

        int i = 0;
        // AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
        char[] froms = new char[]{'A', 'B', 'C', 'D', 'D', 'A', 'C', 'E', 'A'};
        char[] tos = new char[]{'B', 'C', 'D', 'C', 'E', 'D', 'E', 'B', 'E'};
        int[] distances = new int[]{5, 4, 8, 8, 6, 5, 2, 3, 7};
        while (it.hasNext()) {
            DataNode node = it.next();
            assertEquals("from vertex is " + froms[i], node.from, froms[i]);
            assertEquals("to vertex is " + tos[i], node.to, tos[i]);
            assertEquals("distance is " + distances[i], node.distance, distances[i]);
            i++;
        }
    }

    @Test
    public void calculatesDistancesCorrectly() throws NoStationException, NoPathException {
        char[][] tests = new char[][]{
            new char[]{'A', 'B', 'C'},
            new char[]{'A', 'D'},
            new char[]{'A', 'D', 'C'},
            new char[]{'A', 'E', 'B', 'C', 'D'}
        };
        int[] distances = new int[]{9, 5, 13, 22};
        for (int i = 0; i < tests.length; i++) {
            char[] test = tests[i];
            DistanceAggregator d = new DistanceAggregator(t, test[0]);
            for (int j = 1; j < test.length; j++) {
                d.toStation(test[j]);
            }
            int distance = d.calculate();
            assertEquals("distance between " + String.valueOf(test) + " is " + distances[i],
                distance,
                distances[i]);
        }
    }

    @Test
    public void townWalkerGathersPathsCorrectly() throws NoStationException, NoPathException {
        TownWalker walker = new TownWalker(t, 'C');
        WalkerData data = walker.walk((loc, path, dist) -> {
            if (loc.getName() == 'C' && dist < 30) {
                return 1;
            }
            if (dist > 30) {
                return -1;
            }
            return 0;
        });
        assertEquals("there are 7 paths from C to itself thare less than length 30", data.size(), 7);
    }

    @Test
    public void cachesMinimumDistanceCorrectly() throws NoPathException, NoStationException {
        int distance = t.getStation('A').minDistanceTo('C');
        assertEquals("the minimum distance between A and C is 9", distance, 9);
    }

    @Test
    public void testNoPathException() throws NoStationException, NoPathException {
        DistanceAggregator d = new DistanceAggregator(t, 'A')
            .toStation('E');
        try {
            d.toStation('D');
            fail("did not throw NoPathException exception");
        } catch (NoPathException e) {
            // worked
        }
    }

    @Test
    public void testNoStationException() throws NoStationException, NoPathException {
        try {
            t.getStation('F');
            fail("did not throw NoStationException exception");
        } catch(NoStationException e) {
            // worked
        }
    }
}
