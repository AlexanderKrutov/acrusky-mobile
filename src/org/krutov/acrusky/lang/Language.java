/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.lang;

/**
 * Contains internationalization constants
 */
public class Language {
    
  //#ifndef Russian
    public static final String AboutText = "AcruSky Mobile 2.5\nPlanetarium for mobile phones\n\u00A9 Alexander Krutov, 2008-2014\nwww.krutov.org/mobile";
    public static final String AboutTitle = "About";
    public static final String txtDate = "Date";
    public static final String txtTime = "Time";
    public static final String txtSetCurrent = "Set current";
    public static final String Cancel = "Cancel";
    public static final String Back = "Back";
    public static final String Set = "Set";
    public static final String Show = "Show";
    public static final String GetInfo = "Get info";
    public static final String DateTimeTitle = "Date&Time";
    public static final String WrongDate = "Wrong Date";
    public static final String EnterDate = "Enter date in form 'DD.MM.YYYY'";
    public static final String WrongTime = "Wrong time";
    public static final String EnterTime = "Enter time in form 'hh:mm'";
    public static final String InformationTitle = "Information";
    public static final String ToolsTitle = "Tools";
    public static final String LocationName = "Name";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";
    public static final String TimeZone = "Time Zone";
    public static final String LocationTitle = "Location";
    public static final String WrongLocationName = "Wrong name";
    public static final String EnterLocationName = "Enter name of location.";   
    public static final String WrongLatitude = "Wrong latitude";
    public static final String EnterLatitude = "Enter latitude in form '+/-dd.mm.ss'";    
    public static final String WrongLongitude = "Wrong longitude";
    public static final String EnterLongitude = "Enter longitude in form '+/-ddd.mm.ss'";  
    public static final String WrongTimeZone = "Wrong time zone";
    public static final String EnterTimeZone = "Enter time zone in form '+/-hh:mm'";
    public static final String EnterSearchString = "Enter search string";
    public static final String Tools = "Tools";
    public static final String ToolSolarSystem = "Solar system";
    public static final String ToolVenusPhases = "Venus phases";
    public static final String ToolMarsAppearance = "Mars appearance";
    public static final String ToolJupiterMoons = "Jupiter moons";
    public static final String ToolSaturnRings = "Saturn rings";
    public static final String ToolDailyEvents = "Day events";
    public static final String ToolNowObservable = "Now at the sky";
    public static final String DayLightTime = "Daylight time. There are no objects to observe";
    public static final String DeclinationEarth = "Earth's declination: ";
    public static final String JupiterMoonTransit = "Transit";
    public static final String JupiterMoonOccultation = "Occultation";
    public static final String GreatRedSpot = "Great Red Spot";
    public static final String GRSLongitude = "GRS Longitude";
    public static final String CMLongitude = "CM Longitude: ";    
    public static final String SearchTitle = "Search";    
    public static final String SearchResultsTitle = "Search Results";
    public static final String SearchType = "Search type";
    public static final String SearchLocal = "Local";
    public static final String SearchWeb = "On server";    
    public static final String SettingsTitle = "Settings";
    public static final String SettingsStars = "Stars";
    public static final String SettingsStarColors = "Stars colors";
    public static final String SettingsStarLabels = "Star labels";
    public static final String SettingsDeepSky = "Deep sky objects";
    public static final String SettingsDeepSkyLabels = "Deep sky labels";
    public static final String SettingsSolarSystem = "Solar system";
    public static final String SettingsSolarSystemLabels = "Solar system labels";
    public static final String SettingsConstellLines = "Constell. lines";
    public static final String SettingsConstellLabels = "Constell. labels";
    public static final String SettingsHorizontalGrid = "Horisontal grid";
    public static final String SettingsEcliptic = "Ecliptic";
    public static final String Menu = "Menu";
    public static final String MenuDateTime = "Date&Time";
    public static final String MenuLocation = "Location";
    public static final String MenuSearch = "Search";
    public static final String MenuInformation = "Information";
    public static final String MenuTools = "Tools";
    public static final String MenuSettings = "Settings";
    public static final String MenuRegistration = "Registration";
    public static final String MenuExit = "Exit";
    public static final String MenuAbout = "About";
    public static final String[] Directions = {"N", "NW", "W", "SW", "S", "SE", "E", "NE"}; 
    public static final String[] JupiterMoons = {"Io", "Europa", "Ganymede", "Callisto"};
    public static final String CalcEphemerides = "Ephemerides calculation...";
    public static final String LoadStars = "Stars...";
    public static final String LoadStarNames = "Stars names...";
    public static final String LoadConstellLines = "Constell. lines...";
    public static final String LoadConstellations =  "Constellations...";
    public static final String LoadDeepSkyCatalog =  "Deep Sky catalog...";
    public static final String Warning = "Warning";
    public static final String SearchNoResults = "No objects found.";
    public static final String ObjectInvisibleAtMoment = "Object is invisible at the moment.";
    public static final String ObjectInvisibleOnLatitude =  "Object is invisible on current latitude.";
    public static final String Hour = "h";
    public static final String Minute = "m";
    public static final String Second = "s";
    public static final String Days = "d";
    public static final String AU = " au";
    public static final String KM = " km";
    public static final String DegreeSign = "\u00B0";
    public static final String Rise = "Rise: ";
    public static final String Transit = "Transit: ";
    public static final String Setting = "Set: ";
    public static final String NoRises = "No rises";
    public static final String NoSets = "No sets";
    public static final String RA = "R.A.: ";
    public static final String Dec = "Dec.: ";
    public static final String Alt = "Alt.: ";
    public static final String Azi = "Az.: ";
    public static final String NewMoon = "New Moon: ";
    public static final String FirstQuarter = "First Quarter: ";
    public static final String FullMoon = "Full Moon: ";
    public static final String LastQuarter = "Last Quarter: ";
    public static final String VernalEquinox = "Vernal Equinox: "; 
    public static final String SummerSolstice = "Summer Solstice: ";
    public static final String AutumnalEquinox = "Autumnal Equinox: "; 
    public static final String WinterSolstice = "Winter Solstice: ";
    public static final String DefaultLocationName = "N.Novgorod, Russia";
    public static final double DefaultLocationLongitude = -44.0;
    public static final double DefaultLocationLatitiude = 56.3333;
    public static final double DefaultTimeZone = 4;    
    public static final String[] PlanetNames = {"Mercury", "Venus", "", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};
    public static final String[] PlanetNamesGen = {"of Mercury", "of Venus", "", "of Mars", "of Jupiter", "of Saturn", "of Uranus", "of Neptune", "of Pluto"};
    public static final String SunName = "Sun";
    public static final String MoonName = "Moon";
    public static final String Asteroid = "Asteroid";
    public static final String Comet = "Comet";
    public static final String SunNameGen = "of Sun";
    public static final String MoonNameGen = "of Moon"; 
    public static final String EventRise = "Rise";
    public static final String EventSet = "Set";   
    public static final String EarthShadow = "Earth Shadow";
    public static final String[] DeepSkyTypes = {"Galaxy", "Open Cluster", "Globular cluster", "Reflection nebula", "Planetary nebula", "Cluster with nebulosity"};
    public static final String Star = "Star";
    public static final String Planet = "Planet";
    public static final String Magnitude = "Magnitude: ";
    public static final String SpectralClass =  "Sp. Class: ";
    public static final String Phase = "Phase: ";
    public static final String Age = "Age: ";
    public static final String Epoch = "Epoch: ";
    public static final String AngularDiameter = "Angular diam.: ";
    public static final String PositionAngle = "Position angle: ";
    public static final String ParallacticAngle = "Parallactic angle: ";
    public static final String PhaseAngle = "Phase Angle: ";
    public static final String Distance = "Distance: ";
    public static final String DistanceEarth = "Distance from Earth: ";
    public static final String DistanceSun =  "Distance from Sun: ";
    public static final String LibrationLongitude = "Libration in longitude: ";
    public static final String LibrationLatitude= "Libration in latitude: ";
    public static final String EclipticLongitude = "Ecl. Longitude: ";
    public static final String CarringtonNumber = "Carrington no: ";
    public static final String DayLength = "Day Length: ";
    public static final String FileStarNames = "/files/Names-en.bin";
    public static final String FileConstells = "/files/Consts-en.bin";
    public static final String ImageCreating = "Creating image...";
    public static final String Eclipses = "Eclipses";
    public static final String SolarEclipses = "Solar eclipses";
    public static final String LunarEclipses = "Lunar eclipses";
    public static final String PrevEclipses = "Previous eclipses";
    public static final String NextEclipses = "Next eclipses";
    public static final String EclipseNotVisible = "Eclipse is not visible from current point";        
    public static final String[] EclipseType = {
      "Non-central solar eclipse",
      "Partial solar eclipse",
      "Total solar eclipse",
      "Annular solar eclipse",
      "Annular-total solar eclipse",
      "Total lunar eclipse",
      "Partial lunar eclipse",
      "Penumbral lunar eclipse"
    };    
    public static final String EclipseLocalCircumstances = "Local circumstances";
    public static final String EclipseStartPenumbra = "First contact with penumbra";
    public static final String EclipseStartPartial = "First contact with umbra";    
    public static final String EclipseStartTotal = "Beginning of total phase";    
    public static final String EclipseMaximalPhase = "Maximal phase";   
    public static final String EclipseEndTotal = "End of total phase";
    public static final String EclipseEndPartial = "Last contact with umbra"; 
    public static final String EclipseEndPenumbra = "Las contact with penumbra";
    public static final String EclipseFromCurrentPoint = "Local visibility: "; 
    public static final String[] EclipseVisibility = {
      "unvisible",
      "visible as partial eclipse",
      "visible",
      "visible beginning of penumbral eclipse",
      "visible beginning of partial eclipse",
      "visible beginning of total eclipse",
      "visible end of total eclipse",
      "visible end of partial eclipse",
      "visible end of penumbral eclipse"
    };
    public static final String ConnectionError = "Couldn't connect to server. Check internet settings.";
    public static final String ConnectionProhibited = "Network connections are prohibited.";
    public static final String ConnectionProgress = "Connecting to server...";
    
  //#endif  
    
  //#if Russian 
//#     public static final String AboutText = "AcruSky Mobile 2.5\n���������� ��� ��������� ���������\n\u00A9 ��������� ������, 2008-2014\nwww.krutov.org/mobile";
//#     public static final String AboutTitle = "� ���������";
//#     public static final String txtDate = "����";
//#     public static final String txtTime = "�����";
//#     public static final String txtSetCurrent = "���������� �������";
//#     public static final String Cancel = "������";
//#     public static final String Back = "�����";
//#     public static final String Set = "����������";
//#     public static final String Show = "��������";
//#     public static final String GetInfo = "����";
//#     public static final String DateTimeTitle = "���� � �����";
//#     public static final String WrongDate = "�������� ����";
//#     public static final String EnterDate = "������� ���� � ������� '��.��.����'";
//#     public static final String WrongTime = "�������� �����";
//#     public static final String EnterTime = "������� ����� � ������� '��:��'";
//#     public static final String InformationTitle = "����������";
//#     public static final String Tools = "�����������";
//#     public static final String ToolSolarSystem = "��������� �������";
//#     public static final String ToolVenusPhases = "���� ������";
//#     public static final String ToolMarsAppearance = "���� �����";    
//#     public static final String ToolJupiterMoons = "�������� �������";
//#     public static final String ToolSaturnRings = "������ �������";
//#     public static final String ToolDailyEvents = "������� ���";
//#     public static final String ToolNowObservable = "������ �� ����";
//#     public static final String DayLightTime = "������� ����� �����, ��� �������� ��� ����������";    
//#     public static final String GreatRedSpot = "������� ������� �����";
//#     public static final String GRSLongitude = "������� ���: ";
//#     public static final String CMLongitude = "������� ��: ";
//#     public static final String JupiterMoonTransit = "�����������";
//#     public static final String JupiterMoonOccultation = "��������";
//#     public static final String DeclinationEarth = "��������� �����: ";
//#     public static final String LocationName = "��������";
//#     public static final String Latitude = "������";
//#     public static final String Longitude = "�������";
//#     public static final String TimeZone = "������� ����";
//#     public static final String LocationTitle = "�����";
//#     public static final String WrongLocationName = "�������� ��������";
//#     public static final String EnterLocationName = "������� ��������";   
//#     public static final String WrongLatitude = "�������� ������";
//#     public static final String EnterLatitude = "������� ������ � ������� '+/-dd.mm.ss'";    
//#     public static final String WrongLongitude = "�������� �������";
//#     public static final String EnterLongitude = "������� ������� � ������� '+/-ddd.mm.ss'";  
//#     public static final String WrongTimeZone = "������� ������� ����";
//#     public static final String EnterTimeZone = "������� ������� ���� � ������� '+/-hh:mm'";
//#     public static final String EnterSearchString = "������� ������ ������";
//#     public static final String SearchTitle = "�����";    
//#     public static final String SearchType = "��� ������";
//#     public static final String SearchLocal = "����������";
//#     public static final String SearchWeb = "�� �������";
//#     public static final String SearchResultsTitle = "��������� ������";
//#     public static final String SettingsTitle = "���������";
//#     public static final String SettingsStars = "������";
//#     public static final String SettingsStarColors = "����� �����";
//#     public static final String SettingsStarLabels = "�������� �����";
//#     public static final String SettingsDeepSky = "������� ������";
//#     public static final String SettingsDeepSkyLabels = "�������� �������� �������� �������";
//#     public static final String SettingsSolarSystem = "��������� �������";
//#     public static final String SettingsSolarSystemLabels = "�������� �������� ��������� �������";
//#     public static final String SettingsConstellLines = "����� ���������";
//#     public static final String SettingsConstellLabels = "�������� ���������";
//#     public static final String SettingsHorizontalGrid = "�������������� �����";
//#     public static final String SettingsEcliptic = "���������";
//#     public static final String Menu = "����";
//#     public static final String MenuDateTime = "���� � �����";
//#     public static final String MenuLocation = "�����";
//#     public static final String MenuSearch = "�����";
//#     public static final String MenuSettings = "���������";
//#     public static final String MenuRegistration = "�����������";
//#     public static final String MenuExit = "�����";
//#     public static final String MenuAbout = "� ���������";   
//#     public static final String[] Directions = {"�", "��", "�", "��", "�", "��", "�", "��"}; 
//#     public static final String[] JupiterMoons = {"��", "������", "�������", "��������"};
//#     public static final String CalcEphemerides = "������� ��������...";
//#     public static final String LoadStars = "�������� �����...";
//#     public static final String LoadStarNames = "�������� �����...";
//#     public static final String LoadConstellLines = "����� ���������...";
//#     public static final String LoadConstellations =  "�������� ���������...";
//#     public static final String LoadDeepSkyCatalog =  "������� ������...";
//#     public static final String Warning = "��������";
//#     public static final String SearchNoResults = "������� �� �������.";
//#     public static final String ObjectInvisibleAtMoment = "������ �� ����� � ������ ������.";
//#     public static final String ObjectInvisibleOnLatitude =  "������ �� ����� �� ������ ������.";
//#     public static final String Hour = "�";
//#     public static final String Minute = "�";
//#     public static final String Second = "�";
//#     public static final String Days = "��";
//#     public static final String AU = " �.�.";
//#     public static final String KM = " ��";
//#     public static final String DegreeSign = "\u00B0";
//#     public static final String Rise = "������: ";
//#     public static final String Transit = "�����������: ";
//#     public static final String Setting = "�����: ";
//#     public static final String NoRises = "�� ��������";
//#     public static final String NoSets = "�� �������";
//#     public static final String RA = "�.�.: ";
//#     public static final String Dec = "���.: ";
//#     public static final String Alt = "������: ";
//#     public static final String Azi = "������: ";
//#     public static final String NewMoon = "���������: ";
//#     public static final String FirstQuarter = "����. ��������: ";
//#     public static final String FullMoon = "����������: ";
//#     public static final String LastQuarter = "����. ��������: ";
//#     public static final String VernalEquinox = "�����. �����������.: "; 
//#     public static final String SummerSolstice = "����. �����������.: ";
//#     public static final String AutumnalEquinox = "����. �����������.: "; 
//#     public static final String WinterSolstice = "����. �����������.: ";
//#     public static final String DefaultLocationName = "N.Novgorod, Russia";
//#     public static final double DefaultLocationLongitude = -44.0;
//#     public static final double DefaultLocationLatitiude = 56.3333;
//#     public static final double DefaultTimeZone = 4;    
//#     public static final String[] PlanetNames = {"��������", "������", "", "����", "������", "������", "����", "������", "������"};
//#     public static final String[] PlanetNamesGen = {"��������", "������", "", "�����", "�������", "�������", "�����", "�������", "�������"};   
//#     public static final String MoonNameGen = "����";
//#     public static final String SunNameGen = "������"; 
//#     public static final String EventRise = "������";
//#     public static final String EventSet = "�����";
//#     public static final String SunName = "������";
//#     public static final String MoonName = "����";
//#     public static final String EarthShadow = "���� �����";
//#     public static final String[] DeepSkyTypes = {"���������", "���������� ���������", "������� ���������", "����������", "����������� ����������", "��������� � �����������"};
//#     public static final String Star = "������";
//#     public static final String Planet = "�������";  
//#     public static final String Asteroid = "��������";
//#     public static final String Comet = "������"; 
//#     public static final String Magnitude = "�����: ";
//#     public static final String SpectralClass =  "������. �����: ";
//#     public static final String Phase = "����: ";
//#     public static final String Age = "�������: ";
//#     public static final String AngularDiameter = "������� �������: ";
//#     public static final String PositionAngle = "����������� ����: ";
//#     public static final String ParallacticAngle = "�����������. ����: ";
//#     public static final String PhaseAngle = "������� ����: ";
//#     public static final String Distance = "����������: ";
//#     public static final String DistanceEarth = "���������� �� �����: ";
//#     public static final String DistanceSun =  "���������� �� ������: ";
//#     public static final String LibrationLongitude = "�������� �� �������: ";
//#     public static final String LibrationLatitude= "�������� �� ������: ";
//#     public static final String EclipticLongitude = "��������. �������: ";
//#     public static final String CarringtonNumber = "������ �� �����������: ";
//#     public static final String Epoch = "�����: ";
//#     public static final String DayLength = "������� ���: ";
//#     public static final String FileStarNames = "/files/Names-ru.bin";
//#     public static final String FileConstells = "/files/Consts-ru.bin";
//#     public static final String ImageCreating = "�������� �����������...";
//#     public static final String Eclipses = "��������";
//#     public static final String SolarEclipses = "��������� ��������";
//#     public static final String LunarEclipses = "������ ��������";
//#     public static final String PrevEclipses = "���������� ��������";
//#     public static final String NextEclipses = "��������� ��������";
//#     public static final String EclipseNotVisible = "�� ������� ����� �������� �� �����";        
//#     public static final String[] EclipseType = {
//#       "������������� ��������� ��������",
//#       "������� ��������� ��������",
//#       "������ ��������� ��������",
//#       "�������������� ��������� ��������",
//#       "������������-������ ��������� ��������",
//#       "������ ������ ��������",
//#       "������� ������ ��������",
//#       "����������� ������ ��������"
//#     };    
//#     public static final String EclipseLocalCircumstances = "������� ��������������";
//#     public static final String EclipseStartPenumbra = "������ ����������� ����";
//#     public static final String EclipseStartPartial = "������ ������� ����";    
//#     public static final String EclipseStartTotal = "������ ������ ����";    
//#     public static final String EclipseMaximalPhase = "������������ ����";   
//#     public static final String EclipseEndTotal = "����� ������ ����";
//#     public static final String EclipseEndPartial = "����� ������� ����"; 
//#     public static final String EclipseEndPenumbra = "����� ����������� ����";
//#     public static final String EclipseFromCurrentPoint = "�� ������� ����� "; 
//#     public static final String[] EclipseVisibility = {
//#       "�� �����",
//#       "����� ��� �������",
//#       "����� ���������",
//#       "����� ������ ������������ ��������",
//#       "����� ������ ������� ���� ��������",
//#       "����� ������ ������ ���� ��������",
//#       "����� ����� ������ ���� ��������",
//#       "����� ����� ������� ���� ��������",
//#       "����� ����� ������������ ��������"
//#     };
//#     public static final String ConnectionError = "�� ������� ������������ � �������. ��������� ��������� ��������.";
//#     public static final String ConnectionProhibited = "������� ���������� ���������.";
//#     public static final String ConnectionProgress = "���������� � ��������...";
//#     
//#endif  
  
  //#ifdef Russian 
//#   // Encoding chars for hacky conversion
//#   private static final int[] intPhoneCharDiv = {(int)'�' - 192, (int)'�' - 168, (int)'�' - 184};
//#   
//#   /**
//#    * Hacky method to convert encoding
//#    * @param bufName Buffer to convert
//#    * @param len Length of buffer
//#    * @return Converted sting value
//#    */
//#   public static String convertEncoding(byte[] bufName, int len)
//#   {
//#       int c = 0;
//#       StringBuffer name = new StringBuffer();
//#       for (int i=0; i<len; i++) {
//#         c = bufName[i];
//#         if (c < 0) {
//#           c += 256;
//#         }
//#         if (c > 191 && c < 256) {
//#           c += intPhoneCharDiv[0];
//#         } 
//#         else if (c == 168) {
//#           c += intPhoneCharDiv[1];
//#         } 
//#         else if (c == 184) {
//#           c += intPhoneCharDiv[2];
//#         }
//#         name.append((char)c);
//#       }
//#       return name.toString(); 
//#   }
//#   
  //#endif 
}
