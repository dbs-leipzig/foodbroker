package org.biiig.foodbroker.formatter;

/**
 * Created by peet on 28.11.14.
 */
public abstract class AbstractFormatter implements Formatter {

  private final String directory;

  private final String pathSeparator;

  protected AbstractFormatter(String directory) {
    this.directory = directory;
    this.pathSeparator = System.getProperty("file.separator");
  }

  @Override
  public String getNodeFilePath() {
    return getDirectory() + pathSeparator + "nodes" + getFileExtension();
  }

  @Override
  public String getEdgeFilePath() {
    return getDirectory() + pathSeparator + "edges" + getFileExtension();
  }

  @Override
  public String getDataFilePath() {
    return getDirectory() + pathSeparator + "data" + getFileExtension();
  }

  @Override
  public String getNodeFilePath(int thread) {
    return getNodeFilePath() + String.valueOf(thread);
  }

  @Override
  public String getEdgeFilePath(int thread) {
    return getEdgeFilePath() + String.valueOf(thread);
  }

  protected String getDirectory() {
    return directory;

  }
}
