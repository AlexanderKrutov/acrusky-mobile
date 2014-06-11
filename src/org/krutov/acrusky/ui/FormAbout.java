/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.StringItem;

public class FormAbout extends Form {
    
    public static final Command cmdOK = new Command("OK", Command.OK, 0);  
    private final StringItem strAbout = new StringItem(null, null);
    
    /** Creates a new instance of FormAbout */
    public FormAbout(CommandListener cl) {
      super(Language.AboutTitle);
      this.addCommand(cmdOK);
      this.append(strAbout);
      strAbout.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
      strAbout.setLayout(Item.LAYOUT_CENTER);
      strAbout.setText(Language.AboutText);
      this.setCommandListener(cl);  
    }
    
}
