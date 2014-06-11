/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.coords.CrdsGeographical;
import javax.microedition.rms.*;

public class Settings {
  
    private static RecordStore rms = null;
    private static final String RMS_NAME = "AcruSky25Settings";
    private static final int RECORDS_COUNT = 15;

    public static boolean Stars = true;
    public static boolean StarColors = true;
    public static boolean StarLabels = true;
    public static boolean DeepSkies = true;
    public static boolean DeepSkyLabels = true;
    public static boolean SolarSystem = true;
    public static boolean SolarSystemLabels = true;
    public static boolean ConstelLines = true;
    public static boolean ConstelLabels = true;
    public static boolean HorisontalGrid = true;
    public static boolean Ecliptic = true;
    public static CrdsGeographical Location = new CrdsGeographical(Language.DefaultLocationName, Language.DefaultLocationLongitude, Language.DefaultLocationLatitiude, Language.DefaultTimeZone);
        
    public static boolean load()
    {
      try 
      {
        rms = RecordStore.openRecordStore(RMS_NAME, true);
        int n = rms.getNumRecords();
        
        // Delete old or damaged record store if necessary
        if (n != 0 &&
            n != RECORDS_COUNT)
        {
          rms.closeRecordStore();
          RecordStore.deleteRecordStore(RMS_NAME);
          rms = RecordStore.openRecordStore(RMS_NAME, true);
          n = 0;
        }
       
        // Create records
        if (n == 0)
        {            
            // Program settings (0 ... 10)
            addBoolean(Stars);
            addBoolean(StarColors);
            addBoolean(StarLabels);
            addBoolean(DeepSkies);
            addBoolean(DeepSkyLabels);
            addBoolean(SolarSystem);
            addBoolean(SolarSystemLabels);
            addBoolean(ConstelLines);
            addBoolean(ConstelLabels);
            addBoolean(HorisontalGrid);
            addBoolean(Ecliptic);
            
            // Location settings (11 ... 14)
            addString(Location.name);
            addString(String.valueOf(Location.latitude));
            addString(String.valueOf(Location.longitude));
            addString(String.valueOf(Location.timeZone));
        
            n = RECORDS_COUNT;
        }
        
        // Load records
        if (n == RECORDS_COUNT)
        {            
            // Program settings (0 ... 10)
            Stars =  getBoolean(0, Stars);
            StarColors = getBoolean(1, StarColors);
            StarLabels = getBoolean(2, StarLabels);
            DeepSkies = getBoolean(3, DeepSkies);
            DeepSkyLabels = getBoolean(4, DeepSkyLabels);
            SolarSystem = getBoolean(5, SolarSystem);
            SolarSystemLabels = getBoolean(6, SolarSystemLabels);
            ConstelLines = getBoolean(7, ConstelLines);
            ConstelLabels = getBoolean(8, ConstelLabels);
            HorisontalGrid = getBoolean(9, HorisontalGrid);
            Ecliptic = getBoolean(10, Ecliptic);
            
            // Location settings (11 ... 14)
            Location.name = getString(11, Location.name);
            Location.latitude = Double.parseDouble(getString(12, String.valueOf(Location.latitude)));
            Location.longitude = Double.parseDouble(getString(13, String.valueOf(Location.longitude)));
            Location.timeZone = Double.parseDouble(getString(14, String.valueOf(Location.timeZone)));
        }

        return true;
      }
      catch (Exception ex) { }
      return false;
    }
    
    public static boolean saveSettings()
    {
      try 
      {
          setBoolean(0, Stars);
          setBoolean(1, StarColors);
          setBoolean(2, StarLabels);
          setBoolean(3, DeepSkies);
          setBoolean(4, DeepSkyLabels);
          setBoolean(5, SolarSystem);
          setBoolean(6, SolarSystemLabels);
          setBoolean(7, ConstelLines);
          setBoolean(8, ConstelLabels);
          setBoolean(9, HorisontalGrid);
          setBoolean(10, Ecliptic);   
          return true;
      }
      catch (Exception ex) { }
      return false;
    }
    
    public static boolean saveLocation()
    {
      try 
      {
          setString(11, Location.name);
          setString(12, String.valueOf(Location.latitude));
          setString(13, String.valueOf(Location.longitude));
          setString(14, String.valueOf(Location.timeZone));
          return true;
      }
      catch (Exception ex) { }
      return false;
    }    
           
    private static boolean getBoolean(int settingNumber, boolean settingValue) {
      byte buf[] = new byte[1];
      try {
        buf = rms.getRecord(settingNumber);
        return (buf[0] != (byte)0); 
      }
      catch (Exception ex) { }
      return settingValue;
    }    
        
    private static void addBoolean(boolean settingValue)
    {
      try {
        byte buf[] = new byte[1];
        buf[0] = (byte)(settingValue ? 1 : 0);
        rms.addRecord(buf, 0, 1);
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }
    
    private static void setBoolean(int settingNumber, boolean settingValue)
    {
      try {
        byte buf[] = new byte[1];
        buf[0] = (byte)(settingValue ? 1 : 0);
        rms.setRecord(settingNumber, buf, 0, 1);
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }
    
    private static String getString(int settingNumber, String settingValue) {
      try {
        byte buf[] = new byte[rms.getRecordSize(settingNumber)];
        buf = rms.getRecord(settingNumber);
        return new String(buf); 
      }
      catch (Exception ex) { }
      return settingValue;
    }     
    
    private static void addString(String settingValue)
    {
      try {
        rms.addRecord(settingValue.getBytes(), 0, settingValue.length());
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }    
    
    private static void setString(int settingNumber, String settingValue)
    {
      try {
        rms.setRecord(settingNumber, settingValue.getBytes(), 0, settingValue.length());
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }    
    
     private static byte[] getByteArray(int settingNumber) {
      try {
        byte buf[] = new byte[rms.getRecordSize(settingNumber)];
        buf = rms.getRecord(settingNumber);
        return buf; 
      }
      catch (Exception ex) { }
      return null;
    }     
    
    private static void addByteArray(byte[] settingValue)
    {
      try 
      {
        rms.addRecord(settingValue, 0, settingValue.length);
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }   
    
    private static void setByteArray(int settingNumber, byte[] settingValue)
    {
      try {
        rms.setRecord(settingNumber, settingValue, 0, settingValue.length);
      }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
      }      
    }       
}
