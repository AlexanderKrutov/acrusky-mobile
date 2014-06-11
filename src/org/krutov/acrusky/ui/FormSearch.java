/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.TextField;

public class FormSearch extends Form {
 
  public static final Command cmdOK = new Command("OK", Command.OK, 0);  
  public static final Command cmdCancel = new Command(Language.Cancel, Command.CANCEL, 0); 
  private static final TextField txtSearch = new TextField(Language.EnterSearchString, "", 16, TextField.ANY);
  private static final ChoiceGroup rbtSearch = new ChoiceGroup(Language.SearchType, Choice.EXCLUSIVE);
  
  public static final int SEARCH_LOCAL = 0;
  public static final int SEARCH_WEB   = 1; 
  
  public FormSearch(CommandListener cl) {
    super(Language.SearchTitle);
    this.addCommand(cmdOK);
    this.addCommand(cmdCancel);
    this.append(txtSearch);
    rbtSearch.append(Language.SearchLocal, null);
    rbtSearch.append(Language.SearchWeb, null);
    this.append(rbtSearch);
    this.setCommandListener(cl);
  }
  
  public int getSearchMode()
  {
    return rbtSearch.getSelectedIndex();
  }
  
  public boolean isCorrectString(Display display)
  {
    if (getSearchString().length() == 0)
    {
      Alert alert = new Alert(Language.Warning, Language.EnterSearchString, null, AlertType.WARNING);
      alert.setTimeout(Alert.FOREVER);
      display.setCurrent(alert);
      return false;
    } 
    return true;
  }
  
  public String getSearchString()
  {
    return txtSearch.getString().trim();
  }
}
