/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.List;

public class FormTools extends List {
  
  public static final Command cmdCancel = new Command(Language.Back, Command.BACK, 0);
  public static final Command cmdOK = new Command("OK", Command.OK, 0); 
  
  public static final int TOOL_SOLAR_SYSTEM    = 0;
  public static final int TOOL_VENUS_PHASES    = 1;    
  public static final int TOOL_MARS_APPEARANCE = 2;  
  public static final int TOOL_JUPITER_MOONS   = 3;
  public static final int TOOL_SATURN_RINGS    = 4;
  public static final int TOOL_SOLAR_ECLIPSES  = 5;
  public static final int TOOL_LUNAR_ECLIPSES  = 6;
  public static final int TOOL_DAILY_EVENTS    = 7;
  public static final int TOOL_NOW_OBSERVABLE  = 8;  
  
  public FormTools(CommandListener cl) {
    super(Language.Tools, List.IMPLICIT);
    this.addCommand(cmdCancel);
    this.addCommand(cmdOK);
    this.append(Language.ToolSolarSystem, null);
    this.append(Language.ToolVenusPhases, null);
    this.append(Language.ToolMarsAppearance, null);
    this.append(Language.ToolJupiterMoons, null);
    this.append(Language.ToolSaturnRings, null);
    this.append(Language.SolarEclipses, null);
    this.append(Language.LunarEclipses, null);
    this.append(Language.ToolDailyEvents, null);
    this.append(Language.ToolNowObservable, null);
    this.setCommandListener(cl);
    this.setSelectCommand(cmdOK);
  }   
}
