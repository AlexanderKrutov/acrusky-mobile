/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Sky;
import org.krutov.acrusky.core.ephem.Eclipse;
import org.krutov.acrusky.core.ephem.EclipseCalculator;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.List;

public class FormEclipses extends List 
{
  public static final Command cmdBack = new Command(Language.Back, Command.BACK, 0);  
  public static final Command cmdShow = new Command(Language.Show, Command.OK, 0);
  public static final Command cmdGetInfo = new Command(Language.GetInfo, Command.OK, 0);
  public static final Command cmdPrevEclipses = new Command(Language.PrevEclipses, Command.OK, 0);
  public static final Command cmdNextEclipses = new Command(Language.NextEclipses, Command.OK, 0);
  
  private Eclipse[] eclipses = new Eclipse[10];
  public int eclipseType;
  
  public FormEclipses(CommandListener cl) 
  {    
    super(Language.Eclipses, List.IMPLICIT);
    this.addCommand(cmdBack);
    this.addCommand(cmdGetInfo);
    this.addCommand(cmdShow);
    this.addCommand(cmdPrevEclipses);
    this.addCommand(cmdNextEclipses);
    this.setCommandListener(cl);  
    this.setSelectCommand(cmdGetInfo);
  }
    
  private void fillList(double startDate, int eclipseType, boolean next)
  {
    this.deleteAll();
    this.eclipseType = eclipseType;
    double jd = startDate;
    
    int i;
    
    if (next)
    {
      for (i=0; i<10; i++)
      {
        eclipses[i] = EclipseCalculator.getEclipse(jd, eclipseType, true);
        this.append(AstroUtils.toStringDate(eclipses[i].jd + Sky.observerLocation.timeZone / 24.0), null);
        jd = eclipses[i].jd + 30;
      }
    }
    else
    {
      for (i=9; i>=0; i--)
      {
        eclipses[i] = EclipseCalculator.getEclipse(jd, eclipseType, false);
        this.insert(0, AstroUtils.toStringDate(eclipses[i].jd + Sky.observerLocation.timeZone / 24.0), null);
        jd = eclipses[i].jd - 30;
      }
    }
  }
  
  public void fillList(int eclipseType)
  {
    fillList(Sky.julianDay, eclipseType, true);
  }  
  
  public void nextEclipses()
  {
    fillList(eclipses[9].jd + 30, this.eclipseType, true);
  }
  
  public void prevEclipses()
  {
    fillList(eclipses[0].jd - 30, this.eclipseType, false);
  }
  
  public Eclipse getEclipse()
  {
    return eclipses[this.getSelectedIndex()]; 
  }
  
}
