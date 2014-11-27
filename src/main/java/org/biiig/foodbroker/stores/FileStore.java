package org.biiig.foodbroker.stores;

import org.biiig.foodbroker.formatter.Formatter;
import org.biiig.foodbroker.model.DataObject;
import org.biiig.foodbroker.model.Relationship;
import org.biiig.foodbroker.model.SimpleRelationship;

import java.io.*;
import java.util.Map;

/**
 * Created by peet on 25.11.14.
 */
public class FileStore extends AbstractStore{

    private String nodeFilePath;
    private FileWriter nodeFile;
    private String edgeFilePath;
    private FileWriter edgeFile;
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
        String filePath = System.getProperty("user.home")+"/";
        nodeFilePath = filePath + "nodefile_"+String.valueOf(thread) + formatter.getFileExtension();
        edgeFilePath = filePath + "edgefile_"+String.valueOf(thread) + formatter.getFileExtension();

        try {
            nodeFile = new FileWriter(nodeFilePath);
            edgeFile = new FileWriter(edgeFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void store(DataObject dataObject) {
        try {
            nodeFile.write(formatter.format(dataObject)+"\n");

            for(String key : dataObject.getNestedRelationshipKeys() ){
                store(dataObject.getNestedRelationship(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store(Relationship relationship) {
        try {
            edgeFile.write(formatter.format(relationship)+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            nodeFile.close();
            edgeFile.close();
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
