package challenge.parsing;

import java.util.Iterator;

/**
 * This is a class that will represent the input as
 * a linked list type of structure
 */
public class DataNodeList implements Iterable<DataNode> {

    private int size;
    private DataNode head;
    private DataNode tail;

    public DataNodeList() {
        head = tail = null;
    }

    public DataNode append(char from, char to, int distance) {
        DataNode d = new DataNode(from, to, distance);
        return append(d);        
    }

    public DataNode append(DataNode d) {
        if (size == 0) {
            head = tail = d;
        } else if (size == 1) {
            head.next = tail = d;
        }  else {
            tail.next = d;
            tail = tail.next;
        }
        size++;
        return d;
    }

    @Override
    public Iterator<DataNode> iterator() {
        Iterator<DataNode> it = new Iterator<DataNode>() {
            
            private DataNode node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public DataNode next() {
                DataNode temp = node;
                node = node.next;
                return temp;
            }
        };
        return it;
    }
}