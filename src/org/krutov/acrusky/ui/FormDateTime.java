/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.lang.Language;
import java.util.Date;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public class FormDateTime extends Form implements CommandListener, ItemCommandListener {

    private final TextField txtDate = new TextField(Language.txtDate, "", 10, TextField.ANY);
    private final TextField txtTime = new TextField(Language.txtTime, "", 5, TextField.ANY);
    private final static StringItem strNow = new StringItem(null, Language.txtSetCurrent, Item.HYPERLINK);
     
    public final Command cmdOK = new Command("OK", Command.OK, 0);  
    public final Command cmdBACK = new Command(Language.Cancel, Command.BACK, 0);   
    private final Command cmdNOW = new Command(Language.Set, Command.ITEM, 0);
    
    private double julianDay; 
    private double timeZone;
    
    public FormDateTime(CommandListener cl) {
        super(Language.DateTimeTitle);
        this.addCommand(cmdOK);
        this.addCommand(cmdBACK);
        this.append(txtDate);
        this.append(txtTime);
        this.append(strNow);
        this.strNow.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        this.strNow.setDefaultCommand(cmdNOW);
        this.strNow.setItemCommandListener(this);
        this.setCommandListener(cl);
    }
    
    public void commandAction(Command c, Displayable d) {

    }
    
    public void commandAction(Command c, Item item) {
      if (c == cmdNOW) {
        julianDay = AstroUtils.julianDay(new Date());
        setDate(julianDay, timeZone);
      }
    }   
    
    public void setDate(double jd, double tz)
    {
      txtDate.setString(AstroUtils.toStringDate(jd + tz / 24.0));
      txtTime.setString(AstroUtils.toStringTime(jd + tz / 24.0));
      julianDay = jd;
      timeZone = tz;
    }
    
    public boolean isCorrectDate(Display d)
    {      
      int Y, M, D, h, m;
      try
      {
        Y = Integer.parseInt(txtDate.getString().substring(6, 10));
        M = Integer.parseInt(txtDate.getString().substring(3, 5));
        D = Integer.parseInt(txtDate.getString().substring(0, 2));
        if (txtDate.getString().charAt(2) != '.' ||
            txtDate.getString().charAt(5) != '.') throw new Exception();
        if (Y < 1900 || Y > 2100)
            throw new Exception();
        if (M < 0 || M > 12)
            throw new Exception();
        if (D < 0 || D > 31)
            throw new Exception();        
      }
      catch (Exception e)
      {
        Alert alert = new Alert(Language.WrongDate, Language.EnterDate, null, AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        d.setCurrent(alert);
        return false;
      }
    
      try
      {
        h = Integer.parseInt(txtTime.getString().substring(0, 2));
        m = Integer.parseInt(txtTime.getString().substring(3, 5));
        if (txtTime.getString().charAt(2) != ':') throw new Exception();
        if (h < 0 || h > 23) throw new Exception();
        if (m < 0 || m > 59) throw new Exception();
      }
      catch (Exception e)
      {
        Alert alert = new Alert(Language.WrongTime, Language.EnterTime, null, AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        d.setCurrent(alert);
        return false;
      }       
      
      julianDay = AstroUtils.julianDay(Y, M, D, h, m);
      
      return true;
    }
    
    
    public double getJulianDay()
    {
      return julianDay - timeZone / 24.0;
    }
    
}
