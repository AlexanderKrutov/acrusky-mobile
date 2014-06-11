/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky;

import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Settings;
import org.krutov.acrusky.core.Sky;
import org.krutov.acrusky.core.WebClient;
import org.krutov.acrusky.core.WebClientListener;
import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.ephem.Eclipse;
import org.krutov.acrusky.ui.FormAbout;
import org.krutov.acrusky.ui.FormDateTime;
import org.krutov.acrusky.ui.FormEclipses;
import org.krutov.acrusky.ui.FormLocation;
import org.krutov.acrusky.ui.FormMenu;
import org.krutov.acrusky.ui.FormSolarSystem;
import org.krutov.acrusky.ui.FormSearch;
import org.krutov.acrusky.ui.FormSearchResults;
import org.krutov.acrusky.ui.FormSettings;
import org.krutov.acrusky.ui.FormTools;
import org.krutov.acrusky.ui.KeyCodeAdapter;
import org.krutov.acrusky.ui.SkyCanvas;
import org.krutov.acrusky.ui.ToolsScreen;
import org.krutov.acrusky.ui.WaitScreen;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import java.util.*; 

public class AcruSkyMobile extends MIDlet implements CommandListener, WebClientListener {
  private boolean first;
  
  private final Display display;  
  private final SkyCanvas cnvSky;
  private final ToolsScreen toolsScreen;
  private final WaitScreen waitScreen;
  private final KeyCodeAdapter adapter;
  
  private final FormDateTime frmDateTime = new FormDateTime(this);
  private final FormLocation frmLocation = new FormLocation(this);
  private final FormSearch frmSearch = new FormSearch(this);
  private final FormSearchResults frmSearchResults = new FormSearchResults(this);
  private final FormAbout frmAbout = new FormAbout(this);
  private final FormSolarSystem frmSolarSystem = new FormSolarSystem(this);
  private final FormTools frmTools = new FormTools(this);
  private final FormEclipses frmEclipses = new FormEclipses(this);
  private final FormSettings frmSettings = new FormSettings(this);
  
  public void commandAction(Command c, Displayable d) {
    if (c == cnvSky.frmMenu.cmdCancel)
    {
      display.setCurrent(cnvSky);
    }
    if (c == cnvSky.frmMenu.cmdOK)
    {
      int menuItem = cnvSky.frmMenu.getSelectedIndex();
      switch (menuItem)
      {
        case FormMenu.MENU_EXIT:
          destroyApp(true);
          notifyDestroyed();          
          break;
        case FormMenu.MENU_DATETIME:
          frmDateTime.setDate(Sky.julianDay, Sky.observerLocation.timeZone);
          display.setCurrent(frmDateTime);            
          break;
        case FormMenu.MENU_LOCATION:
          frmLocation.setLocation(Sky.observerLocation);
          display.setCurrent(frmLocation);
          break;
        case FormMenu.MENU_TOOLS:
          display.setCurrent(frmTools);
          break;
        case FormMenu.MENU_SEARCH:
          display.setCurrent(frmSearch); 
          break;
        case FormMenu.MENU_SETTINGS:
          frmSettings.loadSettings();
          display.setCurrent(frmSettings);
          break;
        case FormMenu.MENU_ABOUT:
          display.setCurrent(frmAbout);
          break;
        default:
      }
    }          
    if (c == frmDateTime.cmdOK)
    {
      if (frmDateTime.isCorrectDate(display))
      {
        final double jd = frmDateTime.getJulianDay();
        if (Math.abs(Sky.julianDay - jd) > 1.0 / 1440.0)
        {
          initWaitScreen(Language.CalcEphemerides, 10);
          display.callSerially(new Runnable() {
            public void run() {
              Sky.setDate(jd);
              Sky.calculateEphemerids(waitScreen);
              display.setCurrent(cnvSky);
            }
          });
        }
        else
        {
          display.setCurrent(cnvSky);
        }
      }
    }
    if (c == frmDateTime.cmdBACK)
    {
      display.setCurrent(cnvSky);
    }    
    if (c == frmLocation.cmdBACK)
    {
        display.setCurrent(cnvSky);
    }
    if (c == frmLocation.cmdOK)
    {
      if (frmLocation.isCorrectLocation(display))
      {
        initWaitScreen(Language.CalcEphemerides, 10);
        display.callSerially(new Runnable() {
          public void run() {
            Sky.observerLocation.name = frmLocation.locationName;
            Sky.observerLocation.longitude = frmLocation.longitude; 
            Sky.observerLocation.latitude = frmLocation.latitude;
            Sky.observerLocation.timeZone = frmLocation.timeZone;
            Settings.saveLocation();
            Sky.calculateEphemerids(waitScreen); 
            display.setCurrent(cnvSky);
          }
        });
      }
    }
    if (c == frmSolarSystem.cmdCancel)
    {
      display.setCurrent(frmTools);
    }
    if (c == frmSolarSystem.cmdOK)
    {
      cnvSky.frmInfo.previousForm = frmSolarSystem;
      cnvSky.frmInfo.setTitle(Language.InformationTitle);
      cnvSky.frmInfo.setInfo(
        Sky.getObjectInfo(
          frmSolarSystem.getSelectedObjectType(), 
          frmSolarSystem.getSelectedObjectIndex()
        )
      );
      display.setCurrent(cnvSky.frmInfo); 
    }
   
    if (c == frmTools.cmdCancel)
    {
      display.setCurrent(cnvSky);
    }

    if (c == frmTools.cmdOK)
    {
      final int tool = frmTools.getSelectedIndex();
      switch (tool)
      {
        case FormTools.TOOL_SOLAR_SYSTEM:
          display.setCurrent(frmSolarSystem);
          break;
        case FormTools.TOOL_VENUS_PHASES:
          toolsScreen.drawMode = toolsScreen.VENUS_PHASES;
          display.setCurrent(toolsScreen);
          break;
        case FormTools.TOOL_MARS_APPEARANCE:
          initWaitScreen(Language.ImageCreating, 0);
          Sky.calculateMarsAppearance();
          toolsScreen.drawMode = toolsScreen.MARS_APPEARANCE;    
          display.callSerially(new Runnable() {
            public void run() {
              toolsScreen.createMarsImage(waitScreen);
              display.setCurrent(toolsScreen);
            }
          }); 
          break;          
        case FormTools.TOOL_JUPITER_MOONS:
          Sky.calculateJupiterMoons();
          toolsScreen.drawMode = toolsScreen.JUPITER_MOONS;
          display.setCurrent(toolsScreen);
          break;
        case FormTools.TOOL_SATURN_RINGS:
          Sky.calculateSaturnRings();
          toolsScreen.drawMode = toolsScreen.SATURN_RINGS;
          display.setCurrent(toolsScreen);
          break;
        case FormTools.TOOL_SOLAR_ECLIPSES:
        case FormTools.TOOL_LUNAR_ECLIPSES:
          initWaitScreen(Language.CalcEphemerides, 50);
          display.callSerially(new Runnable() {
            public void run() {
              if (tool == FormTools.TOOL_SOLAR_ECLIPSES)
              {
                frmEclipses.setTitle(Language.SolarEclipses);
                frmEclipses.fillList(Eclipse.SOLAR);
              }
              else if (tool == FormTools.TOOL_LUNAR_ECLIPSES)
              {
                frmEclipses.setTitle(Language.LunarEclipses);
                frmEclipses.fillList(Eclipse.LUNAR);
              }
              display.setCurrent(frmEclipses);
            }
          });
          break;          
        case FormTools.TOOL_DAILY_EVENTS:
          initWaitScreen(Language.CalcEphemerides, 0);
          display.callSerially(new Runnable() {
            public void run() {
              cnvSky.frmInfo.setTitle(Language.ToolDailyEvents);
              cnvSky.frmInfo.setInfo(Sky.getDayEvents(waitScreen));
              cnvSky.frmInfo.previousForm = frmTools;
              display.setCurrent(cnvSky.frmInfo);
            }
          });
          break;
        case FormTools.TOOL_NOW_OBSERVABLE:
          Sky.findObservableObjects(frmSearchResults);         
          if (frmSearchResults.size() == 0)
          {
            Alert alert = new Alert(Language.ToolNowObservable, Language.DayLightTime, null, AlertType.WARNING);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
          }
          else
          {
            frmSearchResults.setTitle(Language.ToolNowObservable);
            display.setCurrent(frmSearchResults);
          }
          break;
      }
    }
    if (c == toolsScreen.cmdBack)
    {
      display.setCurrent(frmTools);
    }
    if (c == toolsScreen.cmdInfo)
    {      
      cnvSky.frmInfo.previousForm = toolsScreen;
      cnvSky.frmInfo.setTitle(Language.InformationTitle);
      if (toolsScreen.drawMode == toolsScreen.JUPITER_MOONS)
      {
        cnvSky.frmInfo.setInfo(Sky.getJupiterMoonsInfo());
      }
      if (toolsScreen.drawMode == toolsScreen.SATURN_RINGS)
      {
        cnvSky.frmInfo.setInfo(Sky.getSaturnRingsInfo());
      }    
      if (toolsScreen.drawMode == toolsScreen.MARS_APPEARANCE)
      {
        cnvSky.frmInfo.setInfo(Sky.getMarsAppearanceInfo());
      }       
      if (toolsScreen.drawMode == toolsScreen.VENUS_PHASES)
      {
        cnvSky.frmInfo.setInfo(Sky.getVenusPhaseInfo());
      }      
      display.setCurrent(cnvSky.frmInfo);
    } 
    if (c == frmEclipses.cmdBack)
    {
      display.setCurrent(frmTools);
    }
    if (c == frmEclipses.cmdGetInfo)
    {
      initWaitScreen(Language.CalcEphemerides, 50);
      display.callSerially(new Runnable() {
        public void run() {
          Eclipse e = frmEclipses.getEclipse();
          Sky.calculateLocalEclipse(e, frmEclipses.eclipseType);
          cnvSky.frmInfo.previousForm = frmEclipses;
          cnvSky.frmInfo.setTitle(Language.InformationTitle);
          cnvSky.frmInfo.setInfo(Sky.getEclipseInfo(e, frmEclipses.eclipseType));
          display.setCurrent(cnvSky.frmInfo);
        }
      });
    }
    if (c == frmEclipses.cmdShow)
    {
      final Eclipse e = frmEclipses.getEclipse();
      Sky.calculateLocalEclipse(e, frmEclipses.eclipseType);
      if (e.jdBestVisible > 0)
      {
        initWaitScreen(Language.CalcEphemerides, 0);
        display.callSerially(new Runnable() {
          public void run() {            
            Sky.setDate(e.jdBestVisible);
            Sky.calculateEphemerids(waitScreen);
            if (frmEclipses.eclipseType == Eclipse.SOLAR)
              cnvSky.centerObject(Sky.sun.horizontal);
            else
              cnvSky.centerObject(Sky.moon.horizontal);       
            display.setCurrent(cnvSky);
          }
        });
      }
      else
      {           
        Alert alert = new Alert(Language.Warning, Language.EclipseNotVisible, null, AlertType.WARNING);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);            
      }
    }
    if (c == frmEclipses.cmdNextEclipses)
    {
      initWaitScreen(Language.CalcEphemerides, 50);
      display.callSerially(new Runnable() {
        public void run() {
          frmEclipses.nextEclipses();
          display.setCurrent(frmEclipses);
        }
      });
    }
    if (c == frmEclipses.cmdPrevEclipses)
    {
      initWaitScreen(Language.CalcEphemerides, 50);
      display.callSerially(new Runnable() {
        public void run() {
          frmEclipses.prevEclipses();
          display.setCurrent(frmEclipses);
        }
      });
    }    
    
    if (c == cnvSky.frmInfo.cmdOK)
    {
      if (cnvSky.frmInfo.previousForm != null)
        display.setCurrent(cnvSky.frmInfo.previousForm);
      else
        display.setCurrent(cnvSky);
    }

    if (c == frmSearch.cmdCancel)
    {
      display.setCurrent(cnvSky);  
    }
    if (c == frmSearch.cmdOK)
    {
      if (frmSearch.isCorrectString(display))
      {
        if (frmSearch.getSearchMode() == FormSearch.SEARCH_LOCAL)
        {
          Sky.findObjects(frmSearch.getSearchString(), frmSearchResults);
          searchCompleted();
        }
        else
        {
          initWaitScreen(Language.ConnectionProgress, WaitScreen.CONTINUOUS);
          WebClient.findObjects(frmSearch.getSearchString(), frmSearchResults);
        }
      }
    }     
    if (c == frmSearchResults.cmdGetInfo)
    {
      cnvSky.frmInfo.setTitle(Language.InformationTitle);
      cnvSky.frmInfo.setInfo(Sky.getObjectInfo(
                frmSearchResults.getSelectedObjectType(),  
                frmSearchResults.getSelectedObjectIndex()));
      cnvSky.frmInfo.previousForm = null;
      display.setCurrent(cnvSky.frmInfo);  
    }    

    if (c == frmSearchResults.cmdShow)
    {    
      CrdsEquatorial eq = Sky.getObjectCrdsEquatorial(
        frmSearchResults.getSelectedObjectType(), 
        frmSearchResults.getSelectedObjectIndex()
      );
      if (AstroUtils.isVisibleOnLatitude(Sky.observerLocation.latitude, eq.Dec))
      {
        CrdsHorizontal hor = Sky.getObjectCrdsHorisontal(
          frmSearchResults.getSelectedObjectType(), 
          frmSearchResults.getSelectedObjectIndex()
        );
        if (hor.altitude > 0)
        {
          cnvSky.centerObject(hor);
          display.setCurrent(cnvSky);
        }
        else
        {
          Alert alert = new Alert(Language.Warning, Language.ObjectInvisibleAtMoment, null, AlertType.INFO);
          alert.setTimeout(Alert.FOREVER);
          display.setCurrent(alert);
        }
      }
      else
      {
        Alert alert = new Alert(Language.Warning, Language.ObjectInvisibleOnLatitude, null, AlertType.INFO);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);
      }
    } 
    
    if (c == frmSettings.cmdOK)
    {
      frmSettings.saveSettings();
      display.setCurrent(cnvSky);
      cnvSky.repaint();
    }    
    if (c == frmSettings.cmdCancel)
    {
      display.setCurrent(cnvSky);
    }
    if (c == frmAbout.cmdOK)
    {
      display.setCurrent(cnvSky);
    }
    if (c == waitScreen.cmdCancel)
    {
      display.setCurrent(cnvSky);
      WebClient.cancel();
    }
  }  
    
  private void initWaitScreen(String text, int progress)
  {
    waitScreen.setProgress(text, progress);
    display.setCurrent(waitScreen);
  }
  
  public void webClientCallback(int result)
  {
    waitScreen.stopProgress();
    if (result < 0)
    {
      String msg = null;
      if (result == WebClient.ERR_CONNECTION)
        msg = Language.ConnectionError;
      if (result == WebClient.ERR_PROHIBITED)
        msg = Language.ConnectionProhibited; 
      Alert alert = new Alert(Language.Warning, msg, null, AlertType.WARNING);
      alert.setTimeout(Alert.FOREVER);
      display.setCurrent(alert, cnvSky);
    }
    else if (result == WebClient.CANCELED)
    {
      display.setCurrent(frmSearch);
    }    
    else if (result == WebClient.NO_ERROR)
    {
      searchCompleted();
    }
  }
    
  private void searchCompleted()
  {
    if (frmSearchResults.size() == 0)
    {
      Alert alert = new Alert(Language.SearchResultsTitle, Language.SearchNoResults, null, AlertType.WARNING);
      alert.setTimeout(Alert.FOREVER);
      display.setCurrent(alert, frmSearch);
    }
    else
    {
      frmSearchResults.setTitle(Language.SearchResultsTitle);
      display.setCurrent(frmSearchResults);
    }
  }
      
  public AcruSkyMobile() 
  {    
    first = true;  
    Settings.load();       
    adapter = new KeyCodeAdapter();
    cnvSky = new SkyCanvas(this, Display.getDisplay(this), adapter);
    toolsScreen = new ToolsScreen(this);
    waitScreen = new WaitScreen(this, adapter);
    display = Display.getDisplay(this);
    WebClient.setWebClientListener(this);
  }
  
  protected void startApp() {
    if (first) 
    {
        display.setCurrent(waitScreen);

        Sky.Initialize();
        
        Sky.loadConstells(waitScreen);
        System.gc();

        Sky.loadStars(waitScreen);
        System.gc();

        Sky.loadStarNames(waitScreen);
        System.gc();

        Sky.loadConLines(waitScreen);   
        System.gc();   

        Sky.loadDeepSkies(waitScreen);
        System.gc();

        Sky.observerLocation = Settings.Location;
        
        Sky.setDate(new Date());         
        Sky.calculateEphemerids(waitScreen);        

        display.setCurrent(cnvSky);
        
        first = false;        
    }
  }
        
  public void pauseApp() {
  }
  public void destroyApp(boolean unconditional) {
  }    
}
