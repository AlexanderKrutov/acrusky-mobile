/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Settings;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.List;

public class FormSettings extends List 
{
  public static final Command cmdOK = new Command("OK", Command.OK, 0);
  public static final Command cmdCancel = new Command(Language.Cancel, Command.CANCEL, 0);  
  
  /** Creates a new instance of FormSettings */
  public FormSettings(CommandListener cl) {
    super(Language.SettingsTitle, List.MULTIPLE);
    this.addCommand(cmdOK);
    this.addCommand(cmdCancel);
    
    this.append(Language.SettingsStars, null);
    this.append(Language.SettingsStarColors, null);
    this.append(Language.SettingsStarLabels, null);
    this.append(Language.SettingsDeepSky, null);
    this.append(Language.SettingsDeepSkyLabels, null);
    this.append(Language.SettingsSolarSystem, null);
    this.append(Language.SettingsSolarSystemLabels, null);
    this.append(Language.SettingsConstellLines, null); 
    this.append(Language.SettingsConstellLabels, null);
    this.append(Language.SettingsHorizontalGrid, null);
    this.append(Language.SettingsEcliptic, null);
    
    this.setCommandListener(cl);
  }
  
  public void loadSettings()
  {
    this.setSelectedIndex(0, Settings.Stars);
    this.setSelectedIndex(1, Settings.StarColors);
    this.setSelectedIndex(2, Settings.StarLabels);
    this.setSelectedIndex(3, Settings.DeepSkies);
    this.setSelectedIndex(4, Settings.DeepSkyLabels);
    this.setSelectedIndex(5, Settings.SolarSystem);
    this.setSelectedIndex(6, Settings.SolarSystemLabels);
    this.setSelectedIndex(7, Settings.ConstelLines);
    this.setSelectedIndex(8, Settings.ConstelLabels);
    this.setSelectedIndex(9, Settings.HorisontalGrid);
    this.setSelectedIndex(10, Settings.Ecliptic);
  }
  public void saveSettings()
  {
    Settings.Stars = this.isSelected(0);
    Settings.StarColors = this.isSelected(1);
    Settings.StarLabels = this.isSelected(2);
    Settings.DeepSkies = this.isSelected(3);
    Settings.DeepSkyLabels = this.isSelected(4);
    Settings.SolarSystem = this.isSelected(5);
    Settings.SolarSystemLabels = this.isSelected(6);
    Settings.ConstelLines = this.isSelected(7);
    Settings.ConstelLabels = this.isSelected(8);
    Settings.HorisontalGrid = this.isSelected(9);    
    Settings.Ecliptic = this.isSelected(10);
    Settings.saveSettings();
  }

}
