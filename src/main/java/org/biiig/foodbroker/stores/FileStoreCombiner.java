package org.biiig.foodbroker.stores;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.biiig.foodbroker.formatter.Formatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peet on 28.11.14.
 */
public class FileStoreCombiner extends AbstractStoreCombiner {

    private final List<FileStore> fileStores = new ArrayList();
    private final Formatter formatter;

    public FileStoreCombiner(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void add(Store store) {
        fileStores.add((FileStore) store);
    }

    @Override
    public void combine() {
        try {
            FileWriter nodeFileWriter;
            FileWriter edgeFileWriter;

            if(formatter.hasSeparateRelationshipHandling()){
                nodeFileWriter = new FileWriter(formatter.getNodeFilePath());
                edgeFileWriter = new FileWriter(formatter.getEdgeFilePath());
            }
            else {
                nodeFileWriter = new FileWriter(formatter.getDataFilePath());
                edgeFileWriter = nodeFileWriter;
            }

            for (FileStore fileStore : this.fileStores){
                File nodeFile = new File(fileStore.getNodeFilePath());
                LineIterator nodeIterator = FileUtils.lineIterator(nodeFile);

                while(nodeIterator.hasNext()){
                    nodeFileWriter.write(nodeIterator.nextLine()+"\n");
                }

                nodeIterator.close();
                nodeFile.delete();

                File edgeFile = new File(fileStore.getEdgeFilePath());
                LineIterator edgeIterator = FileUtils.lineIterator(edgeFile);

                while(edgeIterator.hasNext()){
                    edgeFileWriter.write(edgeIterator.nextLine()+"\n");
                }

                edgeIterator.close();
                edgeFile.delete();
            }

            nodeFileWriter.close();
            edgeFileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
