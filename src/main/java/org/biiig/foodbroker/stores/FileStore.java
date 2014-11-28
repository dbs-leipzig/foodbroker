package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;

import java.io.*;

/**
 * Created by peet on 25.11.14.
 */
public class FileStore extends AbstractStore{

    private String nodeFilePath;
    private FileWriter nodeFileWriter;
    private String edgeFilePath;
    private FileWriter edgeFileWriter;
    private int thread;

    public FileStore(Formatter formatter){
        this.formatter = formatter;
        this.thread = 0;
    }

    public FileStore(Formatter formatter, int thread){
        this.formatter = formatter;
        this.thread = thread;
    }

    @Override
    public void open() {
        nodeFilePath = this.formatter.getNodeFilePath(thread);
        edgeFilePath = this.formatter.getEdgeFilePath(thread);

        try {
            nodeFileWriter = new FileWriter(nodeFilePath);
            edgeFileWriter = new FileWriter(edgeFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void store(DataObject dataObject) {
        try {
            nodeFileWriter.write(formatter.format(dataObject) + "\n");

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
            edgeFileWriter.write(formatter.format(relationship) + "\n");
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
