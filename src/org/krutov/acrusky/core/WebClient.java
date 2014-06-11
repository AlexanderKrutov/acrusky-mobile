/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core;

import org.krutov.acrusky.core.objects.Asteroid;
import org.krutov.acrusky.core.objects.Comet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import org.krutov.acrusky.ui.FormSearchResults;

/**
 * Performs interactions between remote service and AcruSky Mobile midlet.
 */
public class WebClient
{ 
  private static HttpConnection connection = null;
  private static InputStream inputStream = null;  
  private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();         
  private static Thread thread;
  private static WebClientListener listener;
  private static FormSearchResults lst;
  private static String query;
  private static int result;
  private static String[] objects;
  
  private static final String HOST_NAME = "http://krutov.org/ws/";
  
  public static final int CANCELED           =  0;    
  public static final int NO_ERROR           =  1;
  public static final int ERR_CONNECTION     = -1;
  public static final int ERR_PROHIBITED     = -2;
  
  private static final String SEPARATOR_DATA = ";";
  private static final String SEPARATOR_OBJECT = "#";  
  
  public static void setWebClientListener(WebClientListener wcl)
  {
    listener = wcl;
  }
  
  public static void findObjects(String search, FormSearchResults frm)
  {
    lst = frm;
    result = NO_ERROR;
    query = "search=" + search;

    thread = new Thread(new Runnable()
    {
      public void run()
      {
        requestData();
      }
    });
    thread.start();
  }
  
  public static void cancel()
  {
    if (thread != null && thread.isAlive())
    {
      result = CANCELED;
      thread.interrupt();
    }
  }  
    
  private static InputStream openInputStream(HttpConnection connection) throws IOException
  {
    try
    {
      return connection.openInputStream();
    }
    catch (Exception ex) { }       
    return connection.openInputStream();            
  }  
  
  private static void closeConnections()
  {
    try
    {
      if(inputStream != null)
      {
        inputStream.close();
        inputStream = null;
      }
    }
    catch(Exception ex) { }
    try
    {
      if(connection != null)
      {
        connection.close();
        connection = null;
      }
    }
    catch(Exception ex) { }
  }  
  
  private static String[] split(String original, String separator) 
  {
    Vector nodes = new Vector();
    int index = original.indexOf(separator);
    while(index >= 0) 
    {
      nodes.addElement( original.substring(0, index) );
      original = original.substring(index+separator.length());
      index = original.indexOf(separator);
    }
    nodes.addElement(original);
    String[] result = new String[nodes.size()];
    nodes.copyInto(result);
    return result;
  }
  
  private static void parseData()
  {  
    lst.deleteAll();
    objects = null; 
    String dataString = outputStream.toString();
    if (dataString.length() == 0) return;
    objects = split(dataString, SEPARATOR_OBJECT);
    for (int i=0; i<objects.length; i++)
    {
      String[] data = split(objects[i], SEPARATOR_DATA); 
      lst.addItem(Integer.parseInt(data[0]), -1, data[1]);
    }
  }
  
  private static double parseEpoch(String d, String m, String y)
  {
    double day = Double.parseDouble(d);
    int month = Integer.parseInt(m);
    int year = Integer.parseInt(y);
    double hour = ((day - (int)day) * 24.0);
    int minute = (int)((hour - (int)hour) * 60.0);
    return AstroUtils.julianDay(year, month, (int)day, (int)hour, minute);
  }
  
  public static Object getObject(int index)
  {
    String[] data = split(objects[index], SEPARATOR_DATA); 
    final int type = Integer.parseInt(data[0]);
    
    if (type == Sky.TYPE_ASTEROID)
    {
      Asteroid a = new Asteroid();      
      a.name = data[1];
      a.id = Integer.parseInt(data[2]);
      a.orbit.epoch = (float)parseEpoch(data[3], data[4], data[5]);
      a.orbit.M = Float.parseFloat(data[6]);
      a.orbit.a = Float.parseFloat(data[7]);
      a.orbit.e = Float.parseFloat(data[8]);
      a.orbit.omega = Float.parseFloat(data[9]);
      a.orbit.Omega = Float.parseFloat(data[10]);
      a.orbit.i = Float.parseFloat(data[11]);
      a.H = Float.parseFloat(data[12]);
      a.G = Float.parseFloat(data[13]);
      return a;
    }
    if (type == Sky.TYPE_COMET)
    {
      Comet c = new Comet();
      c.name = data[1];
      c.id = Integer.parseInt(data[2]);
      c.orbit.epoch = (float)parseEpoch(data[3], data[4], data[5]);
      c.orbit.e = Float.parseFloat(data[6]);
      c.orbit.q = Float.parseFloat(data[7]);
      c.orbit.M = 0;
      if (c.orbit.e < 1) c.orbit.a = c.orbit.q / (1 - c.orbit.e);
      c.orbit.omega = Float.parseFloat(data[8]);
      c.orbit.Omega = Float.parseFloat(data[9]);
      c.orbit.i = Float.parseFloat(data[10]);
      c.g = Float.parseFloat(data[11]);
      c.k = Float.parseFloat(data[12]);
      return c;
    }
    return null;
  }
   
  private static void requestData() 
  {
    outputStream.reset(); 
    try
    {
      connection = (HttpConnection)Connector.open(HOST_NAME, Connector.READ_WRITE, true);
      connection.setRequestMethod(HttpConnection.POST);
      connection.setRequestProperty("Connection", "close");
      connection.setRequestProperty("User-Agent", "AcruSky-Mobile-2.5");  
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Accept", "text/plain");  

      OutputStream os = connection.openOutputStream();
      byte[] bt = query.getBytes();
      for (int i=0; i<bt.length; i++)
      os.write(bt[i]);
      os.flush();
      os.close();  

      inputStream = openInputStream(connection);

      if (inputStream == null)
      {
        throw new Exception();
      }

      int response = connection.getResponseCode();

      if (response != HttpConnection.HTTP_OK)
      {
        throw new Exception();
      }

      int count = 0;
      int currentByte = -1;
      while (true) 
      {
        currentByte = inputStream.read();
        if (currentByte > 0)
        {
          outputStream.write((byte)currentByte);
          count++;
        }
        else
        {
          break;
        }
      }
    }
    catch (ConnectionNotFoundException e) 
    { 
      result = ERR_CONNECTION;    
    } 
    catch (ClassCastException e) 
    {  
      result = ERR_CONNECTION; 
    } 
    catch (InterruptedIOException e) 
    {
      if (result == NO_ERROR) 
      {
        // timeout expired, connection problem
        result = ERR_CONNECTION;     
      }
    } 
    catch (IOException e) 
    {  
      // connection problem
      result = ERR_CONNECTION;
    } 
    catch (SecurityException e) 
    { 
      // network operations are prohibited
      result = ERR_PROHIBITED;
    } 
    catch (Exception e) 
    {  
      // undefined error
      result = ERR_CONNECTION;    
    }
    finally 
    {
      closeConnections();
      if (result == NO_ERROR) parseData();
      listener.webClientCallback(result);
    }
  }
}
