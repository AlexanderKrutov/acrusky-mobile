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

public class FormMenu extends List 
{
  public static final Command cmdCancel = new Command(Language.Cancel, Command.CANCEL, 0);
  public static final Command cmdOK = new Command("OK", Command.OK, 0); 
  
  public final static int MENU_DATETIME      =  0;
  public final static int MENU_LOCATION      =  1;
  public final static int MENU_SEARCH        =  2;
  public final static int MENU_TOOLS         =  3;
  public final static int MENU_SETTINGS      =  4;
  public final static int MENU_ABOUT         =  5;
  public final static int MENU_EXIT          =  6;
  
  public FormMenu(CommandListener cl)
  {
    super(Language.Menu, List.IMPLICIT);
    this.addCommand(cmdCancel);
    this.addCommand(cmdOK);

    this.append(Language.MenuDateTime, null);
    this.append(Language.MenuLocation, null);
    this.append(Language.MenuSearch, null);
    this.append(Language.Tools, null);
    this.append(Language.MenuSettings, null);
    this.append(Language.MenuAbout, null);
    this.append(Language.MenuExit, null);
    this.setCommandListener(cl);
    this.setSelectCommand(cmdOK);
  }
}
