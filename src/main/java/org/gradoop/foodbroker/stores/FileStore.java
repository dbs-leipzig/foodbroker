package org.gradoop.foodbroker.stores;

import org.gradoop.foodbroker.formatter.Formatter;
import org.gradoop.foodbroker.model.DataObject;
import org.gradoop.foodbroker.model.Relationship;
import org.gradoop.foodbroker.model.SimpleRelationship;

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
            for(Map.Entry<String,Object> property : dataObject.getProperties().entrySet()){
                Object value = property.getValue();
                if(value instanceof DataObject){
                    Relationship relationship = new SimpleRelationship(
                            property.getKey(),
                            dataObject,
                            (DataObject) value
                    );
                    store(relationship);
                }
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
        System.out.println("Finito : " +String.valueOf(thread));
    }

    public String getNodeFilePath() {
        return nodeFilePath;
    }

    public String getEdgeFilePath() {
        return edgeFilePath;
    }
}
