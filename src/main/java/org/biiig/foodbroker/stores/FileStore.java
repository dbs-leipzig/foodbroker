package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by peet on 25.11.14.
 */
public class FileStore extends AbstractStore{

    private String nodeFilePath;
    private BufferedWriter nodeFileWriter;
    private String edgeFilePath;
    private BufferedWriter edgeFileWriter;
    private int thread;
    private String lineSeparator;

    public FileStore(Formatter formatter){
        this(formatter, 0);
    }

    public FileStore(Formatter formatter, int thread){
        this.formatter = formatter;
        this.thread = thread;
        this.lineSeparator = System.getProperty("line.separator");
    }

    @Override
    public void open() {
        nodeFilePath = this.formatter.getNodeFilePath(thread);
        edgeFilePath = this.formatter.getEdgeFilePath(thread);

        try {
            nodeFileWriter = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(nodeFilePath),
              Charset.forName("UTF-8")));

            edgeFileWriter = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(edgeFilePath),
              Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void store(DataObject dataObject) {
        try {
            nodeFileWriter.write(formatter.format(dataObject) + lineSeparator);

            if (formatter.hasSeparateRelationshipHandling()) {
                for (String key : dataObject.getNestedRelationshipKeys()) {
                    store(dataObject.getNestedRelationship(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store(Relationship relationship) {
        try {
            edgeFileWriter.write(formatter.format(relationship) + lineSeparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            nodeFileWriter.close();
            edgeFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNodeFilePath() {
        return nodeFilePath;
    }

    public String getEdgeFilePath() {
        return edgeFilePath;
    }
}
