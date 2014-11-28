package org.biiig.foodbroker.formatter;

/**
 * Created by peet on 28.11.14.
 */
public abstract  class AbstractFormatter implements Formatter{

    @Override
    public String getNodeFilePath() {
        return getDirectory() + "nodes" + getFileExtension();
    }

    @Override
    public String getEdgeFilePath() {
        return getDirectory() + "edges" + getFileExtension();
    }

    @Override
    public String getDataFilePath() {
        return getDirectory() + "data" + getFileExtension();
    }

    @Override
    public String getNodeFilePath(int thread) {
        return getNodeFilePath()+String.valueOf(thread);
    }

    @Override
    public String getEdgeFilePath(int thread) {
        return getEdgeFilePath()+String.valueOf(thread);
    }

    protected String getDirectory(){
        return System.getProperty("user.home")+"/";
    }
}
