package challenge.parsing;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

public class Parser implements IParser<DataNodeList> {

    private String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    public DataNodeList parse() {
        DataNodeList list = new DataNodeList();
        byte[] buffer = new byte[3];
        try (
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
        ) {
            // the assumption here is that each blob of input is 3 bytes long
            // 2 characters and a number and these blobs are separated by 2 characters
            // (commma and space) its very possible that the distance can be longer 
            // than 9 in which case there will be a problem. also there is at least
            // should be at least 1 blob of input. the safest way to separate the blobs
            // would be to read the file in its entirety and split by ", "
            do {
                fileInputStream.read(buffer);
                char from = (char) buffer[0];
                char to = (char) buffer[1];
                int distance = buffer[2] - 48;
                list.append(from, to, distance);
            } while (fileInputStream.read() != -1 && fileInputStream.read() != -1); // discard ", "
        } catch (IOException e) {
            System.err.println(e);
        }
        return list;
    }
}