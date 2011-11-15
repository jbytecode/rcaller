package rcaller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamConsumer implements Runnable {
  
  private InputStream is;
  private BufferedReader reader;
  private Thread consumerThread;
  private boolean closeSignal = false;
  private RCaller rcaller = null;
  private String name = null;
  
  public boolean isCloseSignal() {
    return closeSignal;
  }
  
  public void setCloseSignal(boolean closeSignal) {
    this.closeSignal = closeSignal;
  }
  
  public void start(){
    closeSignal = false;
    this.consumerThread = new Thread(this);
    this.consumerThread.setName(this.name);
    this.consumerThread.start();
  }
  
  public void stop(){
    closeSignal = true;
  }
  
  public boolean isAlive(){
    return this.consumerThread.isAlive();
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
  
  public InputStreamConsumer(InputStream is, RCaller rcaller, String name) {
    this.is = is;
    this.rcaller = rcaller;
    this.name = name;
    reader = new BufferedReader(new InputStreamReader(is));
    consumerThread = new Thread(this, name);
  }
  
  private void sendMessage(String threadName, String s) {
    for (int i = 0; i < this.rcaller.getEventHandlers().size(); i++) {
      this.rcaller.getEventHandlers().get(i).MessageReceived(threadName, s);
    }
  }
  
  @Override
  public void run() {
    String s;
    while (!closeSignal) {
      try {
        s = this.reader.readLine();
        if(s!=null){
          sendMessage(Thread.currentThread().getName(), s);
        }
      } catch (Exception e) {
        //...
      }
    }
  }
}
