/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.StringItem;

public class FormInfo extends Form {
 
  public Displayable previousForm = null;
  public static final Command cmdOK = new Command("OK", Command.OK, 0);  
  private final StringItem strInfo = new StringItem(null, null);
  
  public FormInfo(CommandListener cl) {
    super(Language.InformationTitle);
    this.addCommand(cmdOK);
    this.append(strInfo);
    strInfo.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
    this.setCommandListener(cl); 
  }
  
  public void setInfo(String info)
  {
    strInfo.setText(info);
  }
  
}
