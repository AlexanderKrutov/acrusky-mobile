/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Sky;
import org.krutov.acrusky.core.WebClient;
import org.krutov.acrusky.core.objects.Asteroid;
import org.krutov.acrusky.core.objects.Comet;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.List;

public class FormSearchResults extends List {
  
  public static final Command cmdShow = new Command(Language.Show, Command.OK, 0);
  public static final Command cmdGetInfo = new Command(Language.GetInfo, Command.BACK, 0);   
  
  private int objIndexes[] = new int[10];
  private int objTypes[] = new int[10];
  
  public FormSearchResults(CommandListener cl) {    
    super(Language.SearchResultsTitle, List.IMPLICIT);
    this.addCommand(cmdShow);
    this.addCommand(cmdGetInfo);
    this.setCommandListener(cl);
  }
  
  public int getSelectedObjectIndex()
  {
    int index = -1;
    int ind = this.getSelectedIndex();
    if (ind >= 0 && ind < 10) index = objIndexes[ind];
 
    // object was downloaded from web service
    if (index < 0)
    {
      int type = this.getSelectedObjectType();
      switch (type)
      {
        case Sky.TYPE_ASTEROID:
          return Sky.addAsteroid((Asteroid)WebClient.getObject(ind));
        case Sky.TYPE_COMET:
          return Sky.addComet((Comet)WebClient.getObject(ind));
        default:
      }
    }
    return index;
  }
  
  public int getSelectedObjectType()
  {
    int ind = this.getSelectedIndex();
    if (ind >= 0 && ind < 10) return objTypes[ind];
    else return -1; 
  }  
    
  public void addItem(int type, int index, String text)
  {
    objTypes[this.size()] = type;
    objIndexes[this.size()] = index;
    this.append(text, null);
  }
}
