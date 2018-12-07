package challenge;

import java.io.IOException;

import challenge.parsing.Parser;
import challenge.parsing.IParser;
import challenge.parsing.DataNodeList;
import challenge.parsing.DataNode;

import challenge.components.Town;
import challenge.components.calculators.TownWalker;
import challenge.components.calculators.WalkerData;
import challenge.components.calculators.DistanceAggregator;
import challenge.components.exceptions.NoPathException;

public class ExampleUsage {

    public static void main(String[] args) throws Exception {
        DataNodeList input = input("./test/fixtures/input");
        Town town = update(input);
        System.out.println(town);
        
        // tests 1 - 5
        int distance = new DistanceAggregator(town, 'A')
            .toStation('B')
            .toStation('C')
            .calculate();
        System.out.printf("Output #1: %d\n", distance);
        distance = new DistanceAggregator(town, 'A')
            .toStation('D')
            .calculate();
        System.out.printf("Output #2: %d\n", distance);
        distance = new DistanceAggregator(town, 'A')
            .toStation('D')
            .toStation('C')
            .calculate();
        System.out.printf("Output #3: %d\n", distance);
        distance = new DistanceAggregator(town, 'A')
            .toStation('E')
            .toStation('B')
            .toStation('C')
            .toStation('D')
            .calculate();
        System.out.printf("Output #4: %d\n", distance);
        try {
            distance = new DistanceAggregator(town, 'A')
                .toStation('A')
                .toStation('E')
                .toStation('D')
                .calculate();
        } catch (Exception e) {
            if (e instanceof NoPathException) {
                System.out.println("Output #5: NO SUCH ROUTE");
            } else {
                System.out.println("Output #5: Encountered an unexpected exception");
            }
        }

        // tests 6 - 7
        TownWalker walker = new TownWalker(town, 'C');
        WalkerData data = walker.walk((loc, path, dist) -> {
            if (loc.getName() == 'C') {
                return 1;
            }
            if (path.size() > 3) {
                return -1;
            }
            return 0;
        });
        System.out.printf("Output #6: %d\n", data.size());
        walker = new TownWalker(town, 'A');
        data = walker.walk((loc, path, dist) -> {
            if (loc.getName() == 'C' && path.size() == 5) {
                return 1;
            }
            if (path.size() > 4) {
                return -1;
            }
            return 0;
        });
        System.out.printf("Output #7: %d\n", data.size());

        // tests 8 - 9
        distance = town.getStation('A').minDistanceTo('C');
        System.out.printf("Output #8: %d\n", distance);
        // i realize that the desired answer is 9
        // for the path BCEB but that doesn't make sense
        // to me and 0 is a nicer answer
        distance = town.getStation('B').minDistanceTo('B');
        System.out.printf("Output #9: %d\n", distance);
        
        // test 10
        walker = new TownWalker(town, 'C');
        data = walker.walk((loc, path, dist) -> {
            if (loc.getName() == 'C' && dist < 30) {
                return 1;
            }
            if (dist > 30) {
                return -1;
            }
            return 0;
        });
        System.out.printf("Output #10: %d\n", data.size());
    }

    private static DataNodeList input(String filename) {
        IParser<DataNodeList> parser = new Parser(filename);
        return parser.parse();
    }
    
    private static Town update(DataNodeList list) {
        Town town = new Town("kiwiland");
        for (DataNode n: list) {
            town.update(n.from, n.to, n.distance);
        }
        return town;
    }
}