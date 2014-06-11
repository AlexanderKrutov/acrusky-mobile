/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.coords.CrdsGeographical;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.TextField;

public class FormLocation extends Form {
    
    public static final Command cmdOK = new Command("OK", Command.OK, 0);  
    public static final Command cmdBACK = new Command(Language.Cancel, Command.BACK, 0);   
        
    private TextField txtLocation = new TextField(Language.LocationName, "", 30, TextField.ANY);
    private TextField txtLatitude = new TextField(Language.Latitude, "", 9, TextField.ANY);
    private TextField txtLongitude = new TextField(Language.Longitude, "", 10, TextField.ANY);
    private TextField txtTimeZone = new TextField(Language.TimeZone, "", 6, TextField.ANY);
    
    public String locationName;
    public double latitude;
    public double longitude;
    public double timeZone;
    
    public FormLocation(CommandListener cl) {
        super(Language.LocationTitle);
        this.addCommand(cmdOK);
        this.addCommand(cmdBACK);
        this.append(txtLocation);
        this.append(txtLatitude);
        this.append(txtLongitude);
        this.append(txtTimeZone);
        this.setCommandListener(cl);
    } 
    
    public void setLocation(CrdsGeographical g)
    {
       txtLocation.setString(g.name);
        
       double value = g.latitude;
       boolean neg = value < 0;
       value = Math.abs(value);
       int d = (int)value;
       value = (value - d) * 60;
       int m = (int)(value + .01);
       value = (value - m) * 60;
       int s = (int)(value + .01);
       
       txtLatitude.setString((neg ? "-" : "+") +
                             (d < 10 ? "0" : "") + d  + "." + 
                             (m < 10 ? "0" : "") + m + "." + 
                             (s < 10 ? "0" : "") + s);
       
       value = g.longitude;
       neg = value < 0;
       value = Math.abs(value);
       d = (int)value;
       value = (value - d) * 60;
       m = (int)(value + .01);
       value = (value - m) * 60;
       s = (int)(value + .01);
       
       txtLongitude.setString((neg ? "+" : "-") +
                             (d < 10 ? "0" : "") +
                             (d < 100 ? "0" : "") + d + "." + 
                             (m < 10 ? "0" : "") + m + "." + 
                             (s < 10 ? "0" : "") + s);
       
       value = g.timeZone;
       neg = value < 0;
       value = Math.abs(value);
       d = (int)value;
       value = (value - d) * 60;
       m = (int)(value + .01);
       
       txtTimeZone.setString((neg ? "-" : "+") +
                             (d < 10 ? "0" : "") + d + ":" +
                             (m < 10 ? "0" : "") + m);
    }
    
    public boolean isCorrectLocation(Display display)
    {
        locationName = txtLocation.getString().trim();
        
        if (locationName.length() == 0)
        {
          showAlert(display, Language.WrongLocationName, Language.EnterLocationName);
          return false;
        }
        
      int d, m, s;
      boolean neg;
      try
      {
        neg = (txtLatitude.getString().charAt(0) == '-');
        d = Integer.parseInt(txtLatitude.getString().substring(1, 3));
        m = Integer.parseInt(txtLatitude.getString().substring(4, 6));
        s = Integer.parseInt(txtLatitude.getString().substring(7, 9));
        if (txtLatitude.getString().charAt(3) != '.' ||
            txtLatitude.getString().charAt(6) != '.') throw new Exception(); 
        if (txtLatitude.getString().charAt(0) != '+' &&
            txtLatitude.getString().charAt(0) != '-') throw new Exception();
        
        latitude = (d + (double)m / 60.0 + (double)s / 3600.0) * (neg ? -1 : 1);
      }
      catch (Exception e)
      {
        showAlert(display, Language.WrongLatitude, Language.EnterLatitude);
        return false;
      }        
       
      try
      {
        neg = (txtLongitude.getString().charAt(0) == '+');
        d = Integer.parseInt(txtLongitude.getString().substring(1, 4));
        m = Integer.parseInt(txtLongitude.getString().substring(5, 7));
        s = Integer.parseInt(txtLongitude.getString().substring(9, 10));
        if (txtLongitude.getString().charAt(4) != '.' ||
            txtLongitude.getString().charAt(7) != '.') throw new Exception(); 
        if (txtLongitude.getString().charAt(0) != '+' &&
            txtLongitude.getString().charAt(0) != '-') throw new Exception();
      
        longitude = (d + (double)m / 60.0 + (double)s / 3600.0) * (neg ? -1 : 1);
      }
      catch (Exception e)
      {
        showAlert(display, Language.WrongLongitude, Language.EnterLongitude);
        return false;
      }        
      
      try
      {
        neg = (txtTimeZone.getString().charAt(0) == '-');
        d = Integer.parseInt(txtTimeZone.getString().substring(1, 3));
        m = Integer.parseInt(txtTimeZone.getString().substring(4, 6));
        if (txtTimeZone.getString().charAt(3) != ':') throw new Exception(); 
        if (txtTimeZone.getString().charAt(0) != '+' &&
            txtTimeZone.getString().charAt(0) != '-') throw new Exception();
      
        timeZone = (d + (double)m / 60.0) * (neg ? -1 : 1);
      }
      catch (Exception e)
      {
        showAlert(display, Language.WrongTimeZone, Language.EnterTimeZone);
        return false;
      }    
        
        return true;
    }
    
    private void showAlert(Display display, String title, String text)
    {
        Alert alert = new Alert(title, text, null, AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);
    }
    
}
