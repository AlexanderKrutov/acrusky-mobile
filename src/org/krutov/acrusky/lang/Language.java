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
//#     public static final String AboutText = "AcruSky Mobile 2.5\nПланетарий для мобильных телефонов\n\u00A9 Александр Крутов, 2008-2014\nwww.krutov.org/mobile";
//#     public static final String AboutTitle = "О программе";
//#     public static final String txtDate = "Дата";
//#     public static final String txtTime = "Время";
//#     public static final String txtSetCurrent = "Установить текущее";
//#     public static final String Cancel = "Отмена";
//#     public static final String Back = "Назад";
//#     public static final String Set = "Установить";
//#     public static final String Show = "Показать";
//#     public static final String GetInfo = "Инфо";
//#     public static final String DateTimeTitle = "Дата и время";
//#     public static final String WrongDate = "Неверная дата";
//#     public static final String EnterDate = "Введите дату в формате 'ДД.ММ.ГГГГ'";
//#     public static final String WrongTime = "Неверное время";
//#     public static final String EnterTime = "Введите время в формате 'чч:мм'";
//#     public static final String InformationTitle = "Информация";
//#     public static final String Tools = "Инструменты";
//#     public static final String ToolSolarSystem = "Солнечная система";
//#     public static final String ToolVenusPhases = "Фазы Венеры";
//#     public static final String ToolMarsAppearance = "Фазы Марса";    
//#     public static final String ToolJupiterMoons = "Спутники Юпитера";
//#     public static final String ToolSaturnRings = "Кольца Сатурна";
//#     public static final String ToolDailyEvents = "События дня";
//#     public static final String ToolNowObservable = "Сейчас на небе";
//#     public static final String DayLightTime = "Светлое время суток, нет объектов для наблюдения";    
//#     public static final String GreatRedSpot = "Большое Красное Пятно";
//#     public static final String GRSLongitude = "Долгота БКП: ";
//#     public static final String CMLongitude = "Долгота ЦМ: ";
//#     public static final String JupiterMoonTransit = "Прохождение";
//#     public static final String JupiterMoonOccultation = "Покрытие";
//#     public static final String DeclinationEarth = "Склонение Земли: ";
//#     public static final String LocationName = "Название";
//#     public static final String Latitude = "Широта";
//#     public static final String Longitude = "Долгота";
//#     public static final String TimeZone = "Часовой пояс";
//#     public static final String LocationTitle = "Место";
//#     public static final String WrongLocationName = "Неверное название";
//#     public static final String EnterLocationName = "Введите название";   
//#     public static final String WrongLatitude = "Неверная широта";
//#     public static final String EnterLatitude = "Введите широту в формате '+/-dd.mm.ss'";    
//#     public static final String WrongLongitude = "Неверная долгота";
//#     public static final String EnterLongitude = "Введите долготу в формате '+/-ddd.mm.ss'";  
//#     public static final String WrongTimeZone = "Введите часовой пояс";
//#     public static final String EnterTimeZone = "Введите часовой пояс в формате '+/-hh:mm'";
//#     public static final String EnterSearchString = "Введите строку поиска";
//#     public static final String SearchTitle = "Поиск";    
//#     public static final String SearchType = "Тип поиска";
//#     public static final String SearchLocal = "Внутренний";
//#     public static final String SearchWeb = "На сервере";
//#     public static final String SearchResultsTitle = "Результат поиска";
//#     public static final String SettingsTitle = "Настройки";
//#     public static final String SettingsStars = "Звезды";
//#     public static final String SettingsStarColors = "Цвета звезд";
//#     public static final String SettingsStarLabels = "Названия звезд";
//#     public static final String SettingsDeepSky = "Дальний космос";
//#     public static final String SettingsDeepSkyLabels = "Названия объектов дальнего космоса";
//#     public static final String SettingsSolarSystem = "Солнечная система";
//#     public static final String SettingsSolarSystemLabels = "Названия объектов Солнечной системы";
//#     public static final String SettingsConstellLines = "Линии созвездий";
//#     public static final String SettingsConstellLabels = "Названия созвездий";
//#     public static final String SettingsHorizontalGrid = "Горизонтальная сетка";
//#     public static final String SettingsEcliptic = "Эклиптика";
//#     public static final String Menu = "Меню";
//#     public static final String MenuDateTime = "Дата и время";
//#     public static final String MenuLocation = "Место";
//#     public static final String MenuSearch = "Поиск";
//#     public static final String MenuSettings = "Настройки";
//#     public static final String MenuRegistration = "Регистрация";
//#     public static final String MenuExit = "Выход";
//#     public static final String MenuAbout = "О программе";   
//#     public static final String[] Directions = {"С", "СЗ", "З", "ЮЗ", "Ю", "ЮВ", "В", "СВ"}; 
//#     public static final String[] JupiterMoons = {"Ио", "Европа", "Ганимед", "Каллисто"};
//#     public static final String CalcEphemerides = "Рассчет эфемерид...";
//#     public static final String LoadStars = "Загрузка звезд...";
//#     public static final String LoadStarNames = "Названия звезд...";
//#     public static final String LoadConstellLines = "Линии созвездий...";
//#     public static final String LoadConstellations =  "Загрузка созвездий...";
//#     public static final String LoadDeepSkyCatalog =  "Дальний космос...";
//#     public static final String Warning = "Внимание";
//#     public static final String SearchNoResults = "Объекты не найдены.";
//#     public static final String ObjectInvisibleAtMoment = "Объект не виден в данный момент.";
//#     public static final String ObjectInvisibleOnLatitude =  "Объект не виден на данной широте.";
//#     public static final String Hour = "ч";
//#     public static final String Minute = "м";
//#     public static final String Second = "с";
//#     public static final String Days = "дн";
//#     public static final String AU = " а.е.";
//#     public static final String KM = " км";
//#     public static final String DegreeSign = "\u00B0";
//#     public static final String Rise = "Восход: ";
//#     public static final String Transit = "Кульминация: ";
//#     public static final String Setting = "Заход: ";
//#     public static final String NoRises = "Не восходит";
//#     public static final String NoSets = "Не заходит";
//#     public static final String RA = "П.В.: ";
//#     public static final String Dec = "Скл.: ";
//#     public static final String Alt = "Высота: ";
//#     public static final String Azi = "Азимут: ";
//#     public static final String NewMoon = "Новолуние: ";
//#     public static final String FirstQuarter = "Перв. четверть: ";
//#     public static final String FullMoon = "Полнолуние: ";
//#     public static final String LastQuarter = "Посл. четверть: ";
//#     public static final String VernalEquinox = "Весен. равноденств.: "; 
//#     public static final String SummerSolstice = "Летн. солнцестоян.: ";
//#     public static final String AutumnalEquinox = "Осен. равноденств.: "; 
//#     public static final String WinterSolstice = "Зимн. солнцестоян.: ";
//#     public static final String DefaultLocationName = "N.Novgorod, Russia";
//#     public static final double DefaultLocationLongitude = -44.0;
//#     public static final double DefaultLocationLatitiude = 56.3333;
//#     public static final double DefaultTimeZone = 4;    
//#     public static final String[] PlanetNames = {"Меркурий", "Венера", "", "Марс", "Юпитер", "Сатурн", "Уран", "Нептун", "Плутон"};
//#     public static final String[] PlanetNamesGen = {"Меркурия", "Венеры", "", "Марса", "Юпитера", "Сатурна", "Урана", "Нептуна", "Плутона"};   
//#     public static final String MoonNameGen = "Луны";
//#     public static final String SunNameGen = "Солнца"; 
//#     public static final String EventRise = "Восход";
//#     public static final String EventSet = "Заход";
//#     public static final String SunName = "Солнце";
//#     public static final String MoonName = "Луна";
//#     public static final String EarthShadow = "Тень Земли";
//#     public static final String[] DeepSkyTypes = {"Галактика", "Рассеянное скопление", "Шаровое скопление", "Туманность", "Планетарная туманность", "Скопление с туманностью"};
//#     public static final String Star = "Звезда";
//#     public static final String Planet = "Планета";  
//#     public static final String Asteroid = "Астероид";
//#     public static final String Comet = "Комета"; 
//#     public static final String Magnitude = "Блеск: ";
//#     public static final String SpectralClass =  "Спектр. класс: ";
//#     public static final String Phase = "Фаза: ";
//#     public static final String Age = "Возраст: ";
//#     public static final String AngularDiameter = "Угловой диаметр: ";
//#     public static final String PositionAngle = "Позиционный угол: ";
//#     public static final String ParallacticAngle = "Параллактич. угол: ";
//#     public static final String PhaseAngle = "Фазовый угол: ";
//#     public static final String Distance = "Расстояние: ";
//#     public static final String DistanceEarth = "Расстояние от Земли: ";
//#     public static final String DistanceSun =  "Расстояние от Солнца: ";
//#     public static final String LibrationLongitude = "Либрация по долготе: ";
//#     public static final String LibrationLatitude= "Либрация по широте: ";
//#     public static final String EclipticLongitude = "Эклиптич. долгота: ";
//#     public static final String CarringtonNumber = "Оборот по Кэррингтону: ";
//#     public static final String Epoch = "Эпоха: ";
//#     public static final String DayLength = "Долгота дня: ";
//#     public static final String FileStarNames = "/files/Names-ru.bin";
//#     public static final String FileConstells = "/files/Consts-ru.bin";
//#     public static final String ImageCreating = "Создание изображения...";
//#     public static final String Eclipses = "Затмения";
//#     public static final String SolarEclipses = "Солнечные затмения";
//#     public static final String LunarEclipses = "Лунные затмения";
//#     public static final String PrevEclipses = "Предыдущие затмения";
//#     public static final String NextEclipses = "Следующие затмения";
//#     public static final String EclipseNotVisible = "Из текущей точки затмение не видно";        
//#     public static final String[] EclipseType = {
//#       "Нецентральное солнечное затмение",
//#       "Частное солнечное затмение",
//#       "Полное солнечное затмение",
//#       "Кольцеобразное солнечное затмение",
//#       "Кольцобразно-полное солнечное затмение",
//#       "Полное лунное затмение",
//#       "Частное лунное затмение",
//#       "Полутеневое лунное затмение"
//#     };    
//#     public static final String EclipseLocalCircumstances = "Местные обстоятельства";
//#     public static final String EclipseStartPenumbra = "Начало полутеневой фазы";
//#     public static final String EclipseStartPartial = "Начало частной фазы";    
//#     public static final String EclipseStartTotal = "Начало полной фазы";    
//#     public static final String EclipseMaximalPhase = "Максимальная фаза";   
//#     public static final String EclipseEndTotal = "Конец полной фазы";
//#     public static final String EclipseEndPartial = "Конец частной фазы"; 
//#     public static final String EclipseEndPenumbra = "Конец полутеневой фазы";
//#     public static final String EclipseFromCurrentPoint = "Из текущей точки "; 
//#     public static final String[] EclipseVisibility = {
//#       "не видно",
//#       "видно как частное",
//#       "видно полностью",
//#       "видно начало полутеневого затмения",
//#       "видно начало частной фазы затмения",
//#       "видно начало полной фазы затмения",
//#       "виден конец полной фазы затмения",
//#       "виден конец частной фазы затмения",
//#       "виден конец полутеневого затмения"
//#     };
//#     public static final String ConnectionError = "Не удалось подключиться к серверу. Проверьте настройки интернет.";
//#     public static final String ConnectionProhibited = "Сетевые соединения запрещены.";
//#     public static final String ConnectionProgress = "Соединение с сервером...";
//#     
//#endif  
  
  //#ifdef Russian 
//#   // Encoding chars for hacky conversion
//#   private static final int[] intPhoneCharDiv = {(int)'А' - 192, (int)'Ё' - 168, (int)'ё' - 184};
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
