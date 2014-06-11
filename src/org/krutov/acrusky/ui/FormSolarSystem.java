/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Sky;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.List;

public class FormSolarSystem extends List {
  
  public static final Command cmdCancel = new Command(Language.Back, Command.BACK, 0);
  public static final Command cmdOK = new Command("OK", Command.OK, 0); 
  
  public FormSolarSystem(CommandListener cl) {
    super(Language.ToolSolarSystem, List.IMPLICIT);
    this.addCommand(cmdCancel);
    this.addCommand(cmdOK);
    
    this.append(Language.SunName, null);
    this.append(Language.PlanetNames[0], null);
    this.append(Language.PlanetNames[1], null);
    this.append(Language.MoonName, null);
    this.append(Language.PlanetNames[3], null);
    this.append(Language.PlanetNames[4], null);
    this.append(Language.PlanetNames[5], null);
    this.append(Language.PlanetNames[6], null);
    this.append(Language.PlanetNames[7], null);
    this.append(Language.PlanetNames[8], null);
    this.setCommandListener(cl);
    this.setSelectCommand(cmdOK);
  }
  
  public int getSelectedObjectType()
  {
    switch (this.getSelectedIndex())
    {
      case 0: return Sky.TYPE_SUN;
      case 3: return Sky.TYPE_MOON;
      default: break;
    }
    return Sky.TYPE_PLANET;
  }
  
  public int getSelectedObjectIndex()
  {
    int ind = this.getSelectedIndex();
    if (ind > 0) return ind - 1;
    else return 0;
  }
    
}
