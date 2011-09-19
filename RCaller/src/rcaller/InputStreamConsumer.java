
package rcaller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class InputStreamConsumer implements Runnable {
  
  private InputStream is;
  private BufferedReader reader;
  private Thread consumerThread;
  private boolean closeSignal = false;

  public boolean isCloseSignal() {
    return closeSignal;
  }

  public void setCloseSignal(boolean closeSignal) {
    this.closeSignal = closeSignal;
  }
  
  
  public Thread getConsumerThread() {
    return consumerThread;
  }

  public void setConsumerThread(Thread consumerThread) {
    this.consumerThread = consumerThread;
  }

  public InputStream getIs() {
    return is;
  }

  public void setIs(InputStream is) {
    this.is = is;
  }

  public BufferedReader getReader() {
    return reader;
  }

  public void setReader(BufferedReader reader) {
    this.reader = reader;
  }
  
  
  public InputStreamConsumer(InputStream is){
    this.is = is;
    reader = new BufferedReader ( new InputStreamReader(is));
    consumerThread = new Thread(this);
  }

  @Override
  public void run() {
    String s;
    while(!closeSignal){
      try{
        s = this.reader.readLine();
      }catch (Exception e){
        //...
      }
    }
  }
  
  
}
