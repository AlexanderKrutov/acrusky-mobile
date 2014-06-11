/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsGeographical;
import org.krutov.acrusky.core.coords.CrdsRectangular;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.coords.CrdsEcliptical;
import org.krutov.acrusky.core.objects.Comet;
import org.krutov.acrusky.core.objects.Constellation;
import org.krutov.acrusky.core.objects.Sun;
import org.krutov.acrusky.core.objects.Moon;
import org.krutov.acrusky.core.objects.Asteroid;
import org.krutov.acrusky.core.objects.Star;
import org.krutov.acrusky.core.objects.DeepSky;
import org.krutov.acrusky.core.objects.Planet;
import org.krutov.acrusky.core.ephem.AstroEvent;
import org.krutov.acrusky.core.ephem.Eclipse;
import org.krutov.acrusky.core.ephem.MinorBodyCalculator;
import org.krutov.acrusky.core.ephem.MoonCalculator;
import org.krutov.acrusky.core.ephem.OrbitalElements;
import org.krutov.acrusky.core.ephem.PlanetAppearance;
import org.krutov.acrusky.core.ephem.PlanetCalculator;
import org.krutov.acrusky.core.ephem.RiseTransitSet;
import org.krutov.acrusky.core.ephem.SolarCalculator;
import java.io.*;
import java.util.Date;
import java.util.Vector;
import org.krutov.acrusky.ui.FormSearchResults;
import org.krutov.acrusky.ui.WaitScreen;

/** Model of the sky */
public class Sky {
  
  // Objects
  public static Star[] stars;
  public static short starsFrom[];
  public static short starsTo[];
  public static CrdsHorizontal[] ecliptic;
  public static Constellation[] constells;
  public static DeepSky[] deepSkies;
  public static CrdsRectangular[] jupiterMoons;
  public static CrdsRectangular saturnRings;
  public static double jupiterCM2;
  public static double jupiterGRS;
  public static PlanetAppearance marsAppearance;
  public static Planet[] planets;
  public static Sun sun;
  public static Moon moon;
  public static Vector asteroids = new Vector();
  public static Vector comets = new Vector();
  public static CrdsGeographical observerLocation;  
  
  // object counts
  public static final int STARS_COUNT = 1631;
  public static final int CONLINES_COUNT = 466; 
  public static final int NAMES_COUNT = 304;
  public static final int CONSTELS_COUNT = 89;
  public static final int MESSIER_COUNT = 110;
  public static final int CALDWELL_COUNT = 109;  
  public static final int DEEPSKY_COUNT = 219;
  public static final int PLANETS_COUNT = 9;
  
  // Earth index (all planet indices are zero-based), so "2"
  public static final int EARTH = 2;
  
  // Earth orbit obliquity
  public static final double epsilon1980 = 23.441884;  

  // Object types 
  public static final int TYPE_SUN      = 0;
  public static final int TYPE_MOON     = 1;  
  public static final int TYPE_PLANET   = 2;  
  public static final int TYPE_STAR     = 3;
  public static final int TYPE_DEEPSKY  = 4;
  public static final int TYPE_ASTEROID = 100;
  public static final int TYPE_COMET    = 101;
  
  // current time instant variables
  public static double julianDay;
  public static double siderealTime;  
 
  public static void Initialize() 
  {  
    stars = new Star[STARS_COUNT];
    constells = new Constellation[CONSTELS_COUNT];
    deepSkies = new DeepSky[DEEPSKY_COUNT];
    planets = new Planet[PLANETS_COUNT];
    ecliptic = new CrdsHorizontal[49];    
    starsFrom = new short[CONLINES_COUNT];
    starsTo = new short[CONLINES_COUNT];
    jupiterMoons = new CrdsRectangular[4];
    saturnRings = new CrdsRectangular();
    
    for (int i = 0; i < PLANETS_COUNT; i++) {
      planets[i] = new Planet();
      planets[i].name = Language.PlanetNames[i];
    }
    
    for (int i = 0; i < 4; i++) {
      jupiterMoons[i] = new CrdsRectangular();
    }    
 
    sun = new Sun();
    sun.name = Language.SunName;
    moon = new Moon();
    moon.name = Language.MoonName;
  }
  
  public static void calculateEphemerids(WaitScreen ws) 
  {
      ws.setProgress(Language.CalcEphemerides, 0);
    calculateStarEphemerides();
      ws.setProgress(Language.CalcEphemerides, 15);
    calculateDeepSkyEphemerides();    
      ws.setProgress(Language.CalcEphemerides, 30);
    calculatePlanetEphemerides();
      ws.setProgress(Language.CalcEphemerides, 45);
    calculateSolarEphemerides();
      ws.setProgress(Language.CalcEphemerides, 60);
    calculateMoonEphemerides();
      ws.setProgress(Language.CalcEphemerides, 75);
    calculateAsteroidsEphemerides();
      ws.setProgress(Language.CalcEphemerides, 80);
    calculateCometsEphemerides();
      ws.setProgress(Language.CalcEphemerides, 85);
    calculateConstellEphemerides();
      ws.setProgress(Language.CalcEphemerides, 90);
    calculateGridEphemerides();
      ws.setProgress(Language.CalcEphemerides, 95);
    System.gc();
  } 
   
  private static void calculateStarEphemerides()
  {
    for (int i=0; i<STARS_COUNT; i++)
    {
      stars[i].horizontal = AstroUtils.localHorizontal(siderealTime, stars[i].equatorial, observerLocation);
    }
  }
  
  private static void calculateConstellEphemerides()
  {
    for (int i=0; i<CONSTELS_COUNT; i++)
    {
      constells[i].horizontal = AstroUtils.localHorizontal(siderealTime, constells[i].equatorial, observerLocation);
    }
  }
  
  private static void calculateDeepSkyEphemerides()
  {
    for (int i=0; i<DEEPSKY_COUNT; i++)
    {
      deepSkies[i].horizontal = AstroUtils.localHorizontal(siderealTime, deepSkies[i].equatorial, observerLocation);
    }
  } 
  
  private static void calculateGridEphemerides()
  {
    CrdsEcliptical ecl = new CrdsEcliptical();
    CrdsEquatorial eq;
    ecl.beta = 0;
    
    for (int i=0; i<=48; i++)
    {
      ecl.lambda = i*7.5;
      eq = AstroUtils.eclipticalToEquatorial(ecl, epsilon1980);
      ecliptic[i] = AstroUtils.localHorizontal(siderealTime, eq, observerLocation);
    }
  }  
  
  private static void calculateMoonEphemerides() 
  {
    moon.moonElements = MoonCalculator.getMoonElements(julianDay,
                    sun.ecliptical.lambda, sun.solarElements.M);

    moon.ecliptical = MoonCalculator
                    .getEclipticCoordinates(moon.moonElements);

    moon.equatorial = AstroUtils.eclipticalToEquatorial(moon.ecliptical,
                    epsilon1980);

    moon.distanceFromEarth = MoonCalculator.getDistanceFromEarth(
                    moon.moonElements.M_, moon.moonElements.Ec);

    moon.angleDiameter = MoonCalculator.getAngleDiameter(moon.distanceFromEarth);
    
    moon.age = Math2.to360(moon.moonElements.l__ - sun.ecliptical.lambda);
    
    moon.phase = MoonCalculator.getPhase(moon.age);

    moon.equatorial = MoonCalculator.getTopocentricCoordinates(
                    moon.equatorial, observerLocation, moon.distanceFromEarth,
                    siderealTime);

    moon.horizontal = AstroUtils.localHorizontal(siderealTime, moon.equatorial, observerLocation);
    
    moon.positionAngle = AstroUtils.positionAngle(sun.equatorial, moon.equatorial);
    
    moon.parallacticAngle = AstroUtils.parallacticAngle(siderealTime, observerLocation, moon.equatorial);
    
    if (moon.phase > 0.999)
    {
       double u = MoonCalculator.getEarthShadowSize(julianDay);
       moon.umbra = 0.7432 - u;
       moon.penumbra = 1.2985 + u;
       moon.shadowEquatorial.RA = (float)Math2.to360(sun.equatorial.RA - 180);
       moon.shadowEquatorial.Dec = -sun.equatorial.Dec;
       
       moon.shadowEquatorial = MoonCalculator.getTopocentricCoordinates(
                    moon.shadowEquatorial, observerLocation, moon.distanceFromEarth,
                    siderealTime);
       
       moon.shadowHorisontal = AstroUtils.localHorizontal(siderealTime, moon.shadowEquatorial, observerLocation);
    }
  }

    private static void calculatePlanetEphemerides(int i)
    {
      planets[i].orbit = PlanetCalculator
                      .getOrbitalElements(julianDay, i);

      planets[i].ecliptical = PlanetCalculator
                      .getEclipticalCoordinates(
                                      planets[EARTH].orbit,
                                      planets[i].orbit, i > EARTH);

      planets[i].equatorial = AstroUtils.eclipticalToEquatorial(
                      planets[i].ecliptical, epsilon1980);

      planets[i].phase = PlanetCalculator.getPlanetPhase(
                      planets[i].ecliptical.lambda,
                      planets[i].orbit.l);

      planets[i].distanceFromEarth = PlanetCalculator
                      .getDistanceFromEarth(planets[i].orbit.r,
                                      planets[EARTH].orbit.r,
                                      planets[i].orbit.l,
                                      planets[EARTH].orbit.l);

      planets[i].phaseAngle = AstroUtils.phaseAngle(
                      planets[i].orbit.r,
                      planets[EARTH].orbit.r,
                      planets[i].distanceFromEarth);

      planets[i].magnitude = PlanetCalculator.getMagnitude(i,
                      planets[i].distanceFromEarth,
                      planets[i].orbit.r, planets[i].phaseAngle);

      planets[i].angleDiameter = PlanetCalculator.getAngleDiameter(i,
                      planets[i].distanceFromEarth);

      planets[i].horizontal = AstroUtils.localHorizontal(siderealTime, planets[i].equatorial, observerLocation);
    }
    
    private static void calculateEarthEphemerides()
    {
      // Calculate orbital elements for the Earth:
      planets[EARTH].orbit = PlanetCalculator.getOrbitalElements(
                      julianDay, EARTH);
    }
  
    private static void calculatePlanetEphemerides() 
    {
      calculateEarthEphemerides();
      // Calculate ephemerids for other planets (except Earth):
      for (int i = 0; i < PLANETS_COUNT; i++) {
        if (i != EARTH) {
          calculatePlanetEphemerides(i);
        }
      }
    }

  private static void calculateSolarEphemerides() 
  {
    sun.solarElements = SolarCalculator.getSolarElements(julianDay);
    sun.ecliptical = SolarCalculator
                    .getEclipticalCoordinates(sun.solarElements);

    sun.equatorial = AstroUtils.eclipticalToEquatorial(sun.ecliptical,
                    epsilon1980);

    sun.distanceFromEarth = sun.solarElements.r;

    sun.angleDiameter = SolarCalculator
                    .getAngleDiameter(sun.distanceFromEarth);

    sun.horizontal = AstroUtils.localHorizontal(siderealTime, sun.equatorial, observerLocation);
    
    sun.rectangular = SolarCalculator.getRectangularCoordinates(sun.ecliptical.lambda, epsilon1980, sun.distanceFromEarth);
  }  
  
  public static int addAsteroid(Asteroid a)
  {
    // search asteroid
    for (int i=0; i<asteroids.size(); i++)
    {
      if (((Asteroid)asteroids.elementAt(i)).id == a.id) return i;
    }
    // new asteroid
    asteroids.addElement(a);
    int index = asteroids.size()-1;
    Sky.calculateAsteroidEphemerides(index);
    return index;
  }
  
  public static void calculateAsteroidsEphemerides()
  {
    for (int i=0; i<asteroids.size(); i++)
    {
      calculateAsteroidEphemerides(i);
    }
  }
  
  private static void calculateAsteroidEphemerides(int i)
  {
    Asteroid a = (Asteroid)asteroids.elementAt(i);
    OrbitalElements oe = MinorBodyCalculator.getEllipticOrbitalElements(a.orbit, julianDay);    
    CrdsRectangular rect = MinorBodyCalculator.getRectangularCoordinates(oe, epsilon1980);
    a.equatorial = AstroUtils.rectangularToEquatorial(sun.rectangular, rect); 
    a.horizontal = AstroUtils.localHorizontal(siderealTime, a.equatorial, observerLocation);
    a.distanceFromEarth = AstroUtils.distanceFromEarth(sun.rectangular, rect);
    a.phaseAngle = AstroUtils.phaseAngle(oe.r, planets[EARTH].orbit.r, a.distanceFromEarth);
    a.magnitude = MinorBodyCalculator.getAsteroidMagnitude(a.G, a.H, a.phaseAngle, oe.r, a.distanceFromEarth);
  }  
  
  public static int addComet(Comet c)
  {
    // search comet
    for (int i=0; i<comets.size(); i++)
    {
      if (((Comet)comets.elementAt(i)).id == c.id) return i;
    }
    // new comet
    comets.addElement(c);
    int index = comets.size()-1;
    Sky.calculateCometEphemerides(index);
    return index;
  }  
  
  private static void calculateCometEphemerides(int i)
  {
    Comet c = (Comet)comets.elementAt(i);
    OrbitalElements oe = c.orbit;
    if (oe.e < 1)
    {
      // Elliptic orbit
      oe = MinorBodyCalculator.getEllipticOrbitalElements(oe, julianDay); 
    }
    else if (oe.e > 1)
    {
      // Hyperbolic orbit
      oe = MinorBodyCalculator.getHyperbolicOrbitalElements(oe, julianDay); 
    }
    else if (oe.e == 1)
    {
      // Parabolic orbit
      oe = MinorBodyCalculator.getParabolicOrbitalElements(oe, julianDay);
    }
        
    CrdsRectangular rect = MinorBodyCalculator.getRectangularCoordinates(oe, epsilon1980);
    c.equatorial = AstroUtils.rectangularToEquatorial(sun.rectangular, rect); 
    c.horizontal = AstroUtils.localHorizontal(siderealTime, c.equatorial, observerLocation);
    c.distanceFromEarth = AstroUtils.distanceFromEarth(sun.rectangular, rect);
    c.magnitude = MinorBodyCalculator.getCometMagnitude(c.g, c.k, oe.r, c.distanceFromEarth);
    c.appearance = MinorBodyCalculator.getCometAppearance(c.g, c.k, oe.r, c.distanceFromEarth);
    final float r = oe.r;
    oe.r += c.appearance.tail;
    rect = MinorBodyCalculator.getRectangularCoordinates(oe, epsilon1980);
    c.tailEquatorial = AstroUtils.rectangularToEquatorial(sun.rectangular, rect); 
    c.tailHorizontal = AstroUtils.localHorizontal(siderealTime, c.tailEquatorial, observerLocation);  
    oe.r = r;
  }   
  
  public static void calculateCometsEphemerides()
  {
    for (int i=0; i<comets.size(); i++)
    {
      calculateCometEphemerides(i);
    }
  }  
  
  public static void calculateJupiterMoons()
  {
    PlanetCalculator.getJupiterMoons(jupiterMoons, julianDay);
    jupiterCM2 = PlanetCalculator.getJupiterCentralMeridian(julianDay);
    jupiterGRS = PlanetCalculator.getJupiterGRSLongitude(julianDay);
  }  
  
  public static void calculateSaturnRings()
  {
    PlanetCalculator.getSaturnRings(saturnRings, julianDay, planets[5].ecliptical, planets[6].distanceFromEarth);
  }
  
  public static void calculateMarsAppearance()
  {
    marsAppearance = PlanetCalculator.getMarsAppearance(Sky.julianDay, Sky.planets[3].equatorial, Sky.planets[3].distanceFromEarth);
  }
          
  public static void setDate(Date d) {
    julianDay = AstroUtils.julianDay(d);
    siderealTime = AstroUtils.siderealTime(julianDay);
  }
  
  public static void setDate(double jd) {
    julianDay = jd;
    siderealTime = AstroUtils.siderealTime(julianDay);
  }
  
  public static boolean loadStars(WaitScreen waitScreen) {
    //
    // Record Format:
    //  
    // ID            [2]  [short] Star Id
    // Name          [10] [string] Bayer/Flamesteed Name
    // RA            [4]  [float] Right Ascension
    // Dec           [4]  [float] Declination
    // Mag           [4]  [float] Magnitude
    // SpectralClass [2]  [float] Spectral Class
    //      
    //

    InputStream is;
    DataInputStream dis;
    byte[] bufName = new byte[27];
    byte[] bufSpClass = new byte[2];
    int progress = 0;
    int progressOld = -11;  
    int namelen = 0;
  
    try {
      is = Sky.class.getResourceAsStream("/files/Stars.bin");
      dis = new DataInputStream(is);
      for (int k = 0; k < STARS_COUNT; k++) {
        stars[k] = new Star();
        namelen = (int)dis.readByte();
        dis.read(bufName, 0, namelen);
        stars[k].name = new String(bufName, 0, namelen);
        stars[k].equatorial.RA = dis.readFloat();
        stars[k].equatorial.Dec = dis.readFloat();
        stars[k].mag = dis.readFloat();
        dis.read(bufSpClass, 0, 2);        
        stars[k].spectralClass = new String(bufSpClass);
    
        progress = (int)((double)k / STARS_COUNT * 100);
        if (progress > progressOld + 10)
        {
          progressOld = progress;
          waitScreen.setProgress(Language.LoadStars, progress);
        }
      }
    }
    catch (Exception e) 
    {
      //System.out.println("*** LOG: error on reading stars file");
      return false;
    }
    
    try
    {
      is.close();
    }
    catch (Exception e)
    {
      //System.out.println("*** LOG: error on closing file stars.bin");
    }
    
    return true;
  } 
  
  public static boolean loadStarNames(WaitScreen waitScreen)
  {
    //
    // File Format:
    //  		 
    // Id   [2]   [short]  BSC star number
    // Len  [1]   [byte]   Name length
    // Name [Len] [String] Proper star name
    //    

    InputStream is = null;
    DataInputStream dis = null;
    int id;
    int len = 0;
    int progress = 0;
    int progressOld = -11;  
    byte[] bufName = new byte[64];
    
    try {
      is = Sky.class.getResourceAsStream(Language.FileStarNames);
      dis = new DataInputStream(is);
      for (int k = 0; k < NAMES_COUNT; k++) {
        id = (int)(dis.readShort());
        len = (int)(dis.readByte());
        dis.read(bufName, 0, len);
        
//#ifdef Russian            
//#         stars[id].properName = Language.convertEncoding(bufName, len);
//#else
        stars[id].properName = new String(bufName, 0, len); 
//#endif   
               
        progress = (int)((double)k / NAMES_COUNT * 100);
        if (progress > progressOld + 10)
        {
          progressOld = progress;
          waitScreen.setProgress(Language.LoadStarNames, progress);
        }        
      }
    }     
    catch (Exception e) 
    {
      //System.out.println("*** LOG: error on reading stars names");
      return false;
    }
    
    try
    {
      is.close();
    }
    catch (Exception e)
    {
      //System.out.println("*** LOG: error on closing file conlines.bin");
    } 
    return true;
  }
  
  public static boolean loadConLines(WaitScreen waitScreen)
  {
    //
    // File Format:
    //  		 
    // From [2] [short] Star number from
    // To   [2] [short] Star number to
    //    

    InputStream is = null;
    DataInputStream dis = null;
    int progress = 0;
    int progressOld = -11;    
    try {
      is = Sky.class.getResourceAsStream("/files/Conlines.bin");
      dis = new DataInputStream(is);
      for (int k = 0; k < CONLINES_COUNT; k++) {
        starsFrom[k] = dis.readShort();
        starsTo[k] = dis.readShort();
        progress = (int)((double)k / CONLINES_COUNT * 100);
        if (progress > progressOld + 10)
        {
          progressOld = progress;
          waitScreen.setProgress(Language.LoadConstellLines, progress);
        }        
      }
    }     
    catch (Exception e) 
    {
      //System.out.println("*** LOG: error on reading constellation lines");
      return false;
    }
    
    try
    {
      is.close();
    }
    catch (Exception e)
    {
      //System.out.println("*** LOG: error on closing file conlines.bin");
    } 
    return true;
  }
  
  public static boolean loadConstells(WaitScreen waitScreen)
  {
    //
    // File Format:
    //  		 
    // Ra   [4]   [float]  Right ascention
    // Dec  [4]   [float]  Declination
    // Len  [1]   [byte]   Name length
    // Name [Len] [String] Name
    //    

    InputStream is = null;
    DataInputStream dis = null;
    int progress = 0;
    int progressOld = -11;   
    int len = 0;
    byte[] bufName = new byte[64];
    try {
      is = Sky.class.getResourceAsStream(Language.FileConstells);
      dis = new DataInputStream(is);
      for (int k = 0; k < CONSTELS_COUNT; k++) {
        constells[k] = new Constellation();
        constells[k].equatorial.RA =  dis.readFloat();
        constells[k].equatorial.Dec = dis.readFloat();
        len = (int)(dis.readByte());
        dis.read(bufName, 0, len);
        
//#ifdef Russian       
//#         constells[k].name = Language.convertEncoding(bufName, len);
//#else
        constells[k].name = new String(bufName, 0, len);
//#endif  
       
        progress = (int)((double)k / CONSTELS_COUNT * 100);
        if (progress > progressOld + 10)
        {
          progressOld = progress;
          waitScreen.setProgress(Language.LoadConstellations, progress);
        }        
      }
    }     
    catch (Exception e) 
    {
      //System.out.println("*** LOG: error on reading constellation lines");
      return false;
    }
    
    try
    {
      is.close();
    }
    catch (Exception e)
    {
      //System.out.println("*** LOG: error on closing file conlines.bin");
    } 
    return true;
  }  
  
  public static boolean loadDeepSkies(WaitScreen waitScreen)
  {
    //
    // File Format:
    //  		 
    // Ra    [4] [float] Right Ascention;
    // Dec   [4] [float] Declination;
    // Type  [1] [byte]  Deep sky object type;
    // Mag   [4] [float] Magnitude;  
    //
      
    InputStream is = null;
    DataInputStream dis = null;
    int progress = 0;
    int progressOld = -11;    
    try {
      is = Sky.class.getResourceAsStream("/files/DeepSky.bin");
      dis = new DataInputStream(is);
      for (int k = 0; k < DEEPSKY_COUNT; k++) {
        deepSkies[k] = new DeepSky();
        deepSkies[k].equatorial.RA = dis.readFloat();
        deepSkies[k].equatorial.Dec = dis.readFloat(); 
        deepSkies[k].type = dis.readByte();
        deepSkies[k].magnitude = dis.readFloat();
        deepSkies[k].name = (k < MESSIER_COUNT ? "M " + String.valueOf(k+1) : "C " + String.valueOf(k-MESSIER_COUNT+1));
        progress = (int)((double)k / DEEPSKY_COUNT * 100);
        if (progress > progressOld + 10)
        {
          progressOld = progress;
          waitScreen.setProgress(Language.LoadDeepSkyCatalog, progress);
        }        
      }
    }     
    catch (Exception e) 
    {
      //System.out.println("*** LOG: error on reading Messier catalog");
      return false;
    }
    
    try
    {
      is.close();
    }
    catch (Exception e)
    {
      //System.out.println("*** LOG: error on closing file messier.bin");
    } 
    return true;
  }
  
  public static void findObjects(String search, FormSearchResults lst)
  {
    lst.deleteAll();
    search = search.trim();
    int len = search.length();
   
    // Sun
    if (sun.name.indexOf(search) != -1)
    {
      lst.addItem(TYPE_SUN, 0, sun.name);
    }
    // Moon
    if (moon.name.indexOf(search) != -1)
    {
      lst.addItem(TYPE_MOON, 0, moon.name);
    }
    
    // Planets
    for (int i=0; i<PLANETS_COUNT; i++)
    {
        if (i==EARTH) continue;
        if (planets[i].name.indexOf(search) != -1)
        {
            lst.addItem(TYPE_PLANET, i, planets[i].name);
            if (lst.size() == 10) return;
        }
    }
    
    // Asteroids
    for (int i=0; i<asteroids.size(); i++)
    {
      Asteroid a = (Asteroid)asteroids.elementAt(i);
      if (a.name.indexOf(search) != -1)
      {
        lst.addItem(TYPE_ASTEROID, i, a.name);
        if (lst.size() == 10) return;
      }
    }
    
    // Comets
    for (int i=0; i<comets.size(); i++)
    {
      Comet c = (Comet)comets.elementAt(i);
      if (c.name.indexOf(search) != -1)
      {
        lst.addItem(TYPE_COMET, i, c.name);
        if (lst.size() == 10) return;
      }
    }    
    
    // Stars
    for (int i=0; i<STARS_COUNT; i++)
    {
      if (stars[i].properName != null &&
          stars[i].properName.indexOf(search) != -1)
      {
        lst.addItem(TYPE_STAR, i, stars[i].properName + ", " + stars[i].name);
        if (lst.size() == 10) return;
        continue;
      }        
        
      if (stars[i].name.indexOf(search) != -1)
      {
        lst.addItem(TYPE_STAR, i, (stars[i].properName != null ? stars[i].properName + ", " : "") + stars[i].name);
        if (lst.size() == 10) return;
        continue;
      }
    }
    
    // Deep skies
    for (int i=0; i<DEEPSKY_COUNT; i++)
    {
        if (deepSkies[i].name.indexOf(search) != -1)
        {
            lst.addItem(TYPE_DEEPSKY, i, deepSkies[i].name);
            if (lst.size() == 10) return;
        }
    }
  }
  
  public static void findObservableObjects(FormSearchResults lst)
  {
    lst.deleteAll();
    // Sun
    if (sun.horizontal.altitude > 0)
    {
      lst.addItem(TYPE_SUN, 0, sun.name);
    }
    
    // Moon
    if (moon.horizontal.altitude > 0)
    {
      lst.addItem(TYPE_MOON, 0, moon.name);
    }
    
    // If no twilight
    if (sun.horizontal.altitude < -19)
    {
      // Planets
      for (int i=0; i<PLANETS_COUNT; i++)
      {
        if (i==EARTH) continue;
        if (planets[i].horizontal.altitude > 0)
        {
          lst.addItem(TYPE_PLANET, i, planets[i].name);
          if (lst.size() == 10) return;
        }
      }
      
      // Deep skies
      // Deep Sky numbers, sorted by magnitude
      /*
      byte[] dsNumbers = {45, 7, 44, 6, 31, 47, 25, 8, 20, 41, 42, 35, 39, 48, 
                          23, 34, 37, 16, 22, 36, 46, 93, 3, 5, 11, 13, 17, 21, 
                          33, 38, 43, 50, 55, 103, 2, 4, 10, 15, 27, 67, 92, 12,
                          18, 51, 52, 62, 78, 19, 28, 30, 53, 54, 63, 71, 79, 
                          80, 81, 83, 101, 9, 1, 29, 40, 64, 68, 69, 70, 73, 14, 
                          26, 56, 57, 75, 82, 94, 104, 106, 32, 49, 66, 72, 107,
                          110, 60, 61, 65, 74, 77, 85, 96, 99, 100, 102, 58, 84,
                          86, 87, 88, 90, 95, 98, 105, 108, 109, 24, 59, 89, 91, 
                          76, 97};
      */
      
      // Messier marathon
      byte[] dsNumbers = {74, 77, 31, 32, 110, 33, 52, 103, 76, 34, 
                          79, 42, 43, 78, 45, 1, 38, 36, 37, 35, 41, 
                          50, 93, 46, 47, 48, 67, 44, 95, 96, 105, 
                          65, 66, 81, 82, 108, 97, 51, 109, 40, 101, 
                          102, 104, 63, 94, 106, 3, 53, 64, 85, 60, 
                          59, 58, 89, 90, 91, 88, 87, 86, 84, 99, 98, 
                          100, 49, 61, 68, 83, 13, 92, 5, 57, 12, 10, 
                          107, 80, 56, 4, 29, 14, 9, 71, 27, 62, 19, 
                          11, 39, 26, 16, 17, 18, 24, 23, 25, 8, 20, 
                          21, 7, 6, 22, 28, 69, 54, 15, 70, 72, 75, 
                          73, 2, 55, 30};
      
      for (int i=0; i<dsNumbers.length; i++)
      {
        if (deepSkies[dsNumbers[i]-1].horizontal.altitude > 0)
        {
          lst.addItem(TYPE_DEEPSKY, dsNumbers[i]-1, deepSkies[dsNumbers[i]-1].name);
          if (lst.size() == 10) return;
        }
      } 
    }
  }
  
  private static RiseTransitSet getRiseSetAsteroid(int i)
  {
      Asteroid a = (Asteroid)asteroids.elementAt(i);
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      // TODO: take in account asteroid motion?
      return AstroUtils.getRiseTransitSet(jd, a.equatorial, a.equatorial, a.equatorial, observerLocation, -0.5667);
  }  
  
  private static RiseTransitSet getRiseSetComet(int i)
  {
      Comet c = (Comet)comets.elementAt(i);
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      return AstroUtils.getRiseTransitSet(jd, c.equatorial, c.equatorial, c.equatorial, observerLocation, -0.5667);
  }  
  
  private static RiseTransitSet getRiseSetStar(int i)
  {
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      return AstroUtils.getRiseTransitSet(jd, stars[i].equatorial, stars[i].equatorial, stars[i].equatorial, observerLocation, -0.5667);
  }
  
  private static RiseTransitSet getRiseSetDeepSky(int i)
  {
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      return AstroUtils.getRiseTransitSet(jd, deepSkies[i].equatorial, deepSkies[i].equatorial, deepSkies[i].equatorial, observerLocation, -0.5667);
  }  
  
  private static RiseTransitSet getRiseSetMoon()
  {
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      CrdsEquatorial[] eq = new CrdsEquatorial[3];
   
      double jdSave = julianDay;
      for (int i = 0; i < 3; i++)
      {
        setDate(jd + i * 0.5);
        calculateSolarEphemerides();
        calculateMoonEphemerides();
        eq[i] = new CrdsEquatorial();
        eq[i].RA = moon.equatorial.RA;
        eq[i].Dec = moon.equatorial.Dec;
      }
      
      setDate(jdSave);
      calculateSolarEphemerides();
      calculateMoonEphemerides();      
      
      return AstroUtils.getRiseTransitSet(jd, eq[0], eq[1], eq[2], observerLocation, 0.7);
  }
  
  private static RiseTransitSet getRiseSetSun()
  {
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      CrdsEquatorial[] eq = new CrdsEquatorial[3];
   
      double jdSave = julianDay;
      for (int i = 0; i < 3; i++)
      {
        setDate(jd + i * 0.5);
        calculateSolarEphemerides();
        eq[i] = new CrdsEquatorial();
        eq[i].RA = sun.equatorial.RA;
        eq[i].Dec = sun.equatorial.Dec;
      }
      
      setDate(jdSave);
      calculateSolarEphemerides();
         
      return AstroUtils.getRiseTransitSet(jd, eq[0], eq[1], eq[2], observerLocation, 0.8333);
  }
  
  private static RiseTransitSet getRiseSetPlanet(int p)
  {
      double jd = AstroUtils.localMidnight(julianDay, observerLocation.timeZone);
      CrdsEquatorial[] eq = new CrdsEquatorial[3];
   
      double jdSave = julianDay;
      for (int i = 0; i < 3; i++)
      {
        setDate(jd + i * 0.5);
        calculateEarthEphemerides();
        calculatePlanetEphemerides(p);
        eq[i] = new CrdsEquatorial();
        eq[i].RA = planets[p].equatorial.RA;
        eq[i].Dec = planets[p].equatorial.Dec;
      }
      
      setDate(jdSave);
      calculateEarthEphemerides();
      calculatePlanetEphemerides(p);     
      
      return AstroUtils.getRiseTransitSet(jd, eq[0], eq[1], eq[2], observerLocation, -0.5667);
  }  
  
  private static String getRiseSetString(RiseTransitSet rts)
  {
      boolean noRise = Double.isNaN(rts.rise);
      boolean noSet = Double.isNaN(rts.set);
      boolean noTrans = Double.isNaN(rts.transit);
      
      String rise = Language.Rise + AstroUtils.toStringTime(rts.rise + observerLocation.timeZone / 24.0);
      String trans = Language.Transit + AstroUtils.toStringTime(rts.transit + observerLocation.timeZone / 24.0);
      String set =  Language.Setting + AstroUtils.toStringTime(rts.set + observerLocation.timeZone / 24.0);
      
      if (noRise && noSet && noTrans) return Language.NoRises;
      if (noRise && noSet && !noTrans) return 
              Language.NoSets + "\n" +
              trans;
     
      return rise + "\n" +
             trans + "\n" + 
             set;
  }
  
  private static String getEquatorialString(CrdsEquatorial eq)
  {
      return
        Language.RA + AstroUtils.toStringRA(eq.RA) + "\n" +    
        Language.Dec + AstroUtils.toStringSignedAngle(eq.Dec);
  }
  
  private static String getMoonPhasesString()
  {
    double jdNM = MoonCalculator.getNearestPhase(julianDay, MoonCalculator.PHASE_NM);
    double jdFQ = MoonCalculator.getNearestPhase(julianDay, MoonCalculator.PHASE_FQ);
    double jdFM = MoonCalculator.getNearestPhase(julianDay, MoonCalculator.PHASE_FM);
    double jdLQ = MoonCalculator.getNearestPhase(julianDay, MoonCalculator.PHASE_LQ);
    
    return 
      Language.NewMoon + AstroUtils.toStringDateTime(jdNM + observerLocation.timeZone / 24.0) + "\n" +
      Language.FirstQuarter + AstroUtils.toStringDateTime(jdFQ + observerLocation.timeZone / 24.0) + "\n" + 
      Language.FullMoon + AstroUtils.toStringDateTime(jdFM + observerLocation.timeZone / 24.0) + "\n" +
      Language.LastQuarter + AstroUtils.toStringDateTime(jdLQ + observerLocation.timeZone / 24.0);
  }

  private static String getSeasonString()
  {
    double jdSpring = SolarCalculator.getSeason(julianDay, SolarCalculator.SPRING);
    double jdSummer = SolarCalculator.getSeason(julianDay, SolarCalculator.SUMMER);
    double jdAutumn = SolarCalculator.getSeason(julianDay, SolarCalculator.AUTUMN);
    double jdWinter = SolarCalculator.getSeason(julianDay, SolarCalculator.WINTER);
    
    return 
      Language.VernalEquinox + AstroUtils.toStringDateTime(jdSpring + observerLocation.timeZone / 24.0) + "\n" +
      Language.SummerSolstice + AstroUtils.toStringDateTime(jdSummer + observerLocation.timeZone / 24.0) + "\n" + 
      Language.AutumnalEquinox + AstroUtils.toStringDateTime(jdAutumn + observerLocation.timeZone / 24.0) + "\n" +
      Language.WinterSolstice + AstroUtils.toStringDateTime(jdWinter + observerLocation.timeZone / 24.0);
  }  
  
  private static String getHorizontalString(CrdsHorizontal hor)
  {
      return
        Language.Alt + AstroUtils.toStringSignedAngle(hor.altitude) + "\n" +
        Language.Azi + AstroUtils.toStringUnsignedAngle(hor.azimuth);
  }  
    
  private static String getDeepSkyType(int type)
  {
     return Language.DeepSkyTypes[type];
  }
  
  public static String getObjectInfo(int type, int index)
  {
    switch (type)
    {
      case TYPE_SUN:
        return getSunInfo();
      case TYPE_MOON:
        return getMoonInfo();
      case TYPE_PLANET:
        return getPlanetInfo(index);
      case TYPE_STAR:
        return getStarInfo(index);
      case TYPE_DEEPSKY:
        return getDeepSkyInfo(index);
      case TYPE_ASTEROID:
        return getAsteroidInfo(index);
      case TYPE_COMET:
        return getCometInfo(index);        
      default:
        break;
    }
    return "";
  }
  
  public static CrdsHorizontal getObjectCrdsHorisontal(int type, int index)
  {
    switch (type)
    {
      case TYPE_SUN:
        return sun.horizontal;
      case TYPE_MOON:
        return moon.horizontal;
      case TYPE_PLANET:
        return planets[index].horizontal;
      case TYPE_STAR:
        return stars[index].horizontal;
      case TYPE_DEEPSKY:
        return deepSkies[index].horizontal;
      case TYPE_ASTEROID:
        return ((Asteroid)asteroids.elementAt(index)).horizontal;
      case TYPE_COMET:
        return ((Comet)comets.elementAt(index)).horizontal;        
      default:
        break;
    }
    return null;
  }  
  
  public static CrdsEquatorial getObjectCrdsEquatorial(int type, int index)
  {
    switch (type)
    {
      case TYPE_SUN:
        return sun.equatorial;
      case TYPE_MOON:
        return moon.equatorial;
      case TYPE_PLANET:
        return planets[index].equatorial;
      case TYPE_STAR:
        return stars[index].equatorial;
      case TYPE_DEEPSKY:
        return deepSkies[index].equatorial;
      case TYPE_ASTEROID:
        return ((Asteroid)asteroids.elementAt(index)).equatorial;
      case TYPE_COMET:
        return ((Comet)comets.elementAt(index)).equatorial;                
      default:
        break;
    }
    return null;
  }    
  
  public static String getStarInfo(int i)
  {
    String info = "";
    
    if (stars[i].properName != null)
    {
        info += stars[i].properName + ", ";
    }
    
    info += stars[i].name + "\n";
 
    info += Language.Star + "\n";
    
    info += getEquatorialString(stars[i].equatorial) + "\n";
    info += getHorizontalString(stars[i].horizontal) + "\n";
        
    info += Language.Magnitude;
    info += AstroUtils.toStringMagnitude(stars[i].mag) + "\n";

    info += Language.SpectralClass;
    info += stars[i].spectralClass + "\n";    
    
    info += getRiseSetString(getRiseSetStar(i));
       
    return info;
  }
  
  public static String getPlanetInfo(int i)
  {
    String info = "";
    
    info += planets[i].name + "\n";
    info += Language.Planet + "\n";
    
    info += getEquatorialString(planets[i].equatorial) + "\n";
    info += getHorizontalString(planets[i].horizontal) + "\n";
        
    info += Language.Magnitude;
    info += AstroUtils.toStringMagnitude(planets[i].magnitude) + "\n";

    info += Language.Phase;
    info += AstroUtils.toStringPhase(planets[i].phase) + "\n";
    
    info += Language.PhaseAngle;
    info += AstroUtils.toStringShortAngle(planets[i].phaseAngle) + "\n";

    info += Language.AngularDiameter;
    info += AstroUtils.toStringAngleDiameter(planets[i].angleDiameter / 3600.0) + "\n";
    
    info += Language.DistanceEarth;
    info += AstroUtils.toStringDistance(planets[i].distanceFromEarth) + "\n";

    info += Language.DistanceSun;
    info += AstroUtils.toStringDistance(planets[i].orbit.r) + "\n"; 
    
    info += getRiseSetString(getRiseSetPlanet(i)) + "\n";

    return info;
  }
  
  public static String getAsteroidInfo(int i)
  {
    Asteroid a = (Asteroid)asteroids.elementAt(i);
    
    String info = "";
    
    info += a.name + "\n";
    info += Language.Asteroid + "\n";
    
    info += getEquatorialString(a.equatorial) + "\n";
    info += getHorizontalString(a.horizontal) + "\n";
        
    info += Language.Magnitude;
    info += AstroUtils.toStringMagnitude(a.magnitude) + "\n";
    
    info += Language.PhaseAngle;
    info += AstroUtils.toStringShortAngle(a.phaseAngle) + "\n";
    
    info += Language.DistanceEarth;
    info += AstroUtils.toStringDistance(a.distanceFromEarth) + "\n";

    info += Language.DistanceSun;
    info += AstroUtils.toStringDistance(a.orbit.r) + "\n"; 
    
    info += getRiseSetString(getRiseSetAsteroid(i)) + "\n";

    return info;
  }
  
  public static String getCometInfo(int i)
  {
    Comet c = (Comet)comets.elementAt(i);
    
    String info = "";
    
    info += c.name + "\n";
    info += Language.Comet + "\n";
    
    info += getEquatorialString(c.equatorial) + "\n";
    info += getHorizontalString(c.horizontal) + "\n";
        
    info += Language.Magnitude;
    info += AstroUtils.toStringMagnitude(c.magnitude) + "\n";
        
    info += Language.DistanceEarth;
    info += AstroUtils.toStringDistance(c.distanceFromEarth) + "\n";

    info += Language.DistanceSun;
    info += AstroUtils.toStringDistance(c.orbit.r) + "\n"; 
    
    info += Language.Epoch;
    info += AstroUtils.toStringDateTime(c.orbit.epoch) + "\n";      
    
    info += getRiseSetString(getRiseSetComet(i)) + "\n";

    return info;
  }  
  
  public static String getDeepSkyInfo(int i)
  {
     String info = "";
    
     info += deepSkies[i].name + "\n";

     info += getDeepSkyType(deepSkies[i].type) + "\n";
     
     info += getEquatorialString(deepSkies[i].equatorial) + "\n";
     info += getHorizontalString(deepSkies[i].horizontal) + "\n";
        
     info += Language.Magnitude;
     info += AstroUtils.toStringMagnitude(deepSkies[i].magnitude) + "\n";
     
     info += getRiseSetString(getRiseSetDeepSky(i)) + "\n";
     
     return info;
  }
  
  public static String getMoonInfo()
  {
    String info = "";
    
    info += moon.name + "\n"; 
    
    info += getEquatorialString(moon.equatorial) + "\n";
    info += getHorizontalString(moon.horizontal) + "\n";
    
    info += Language.Phase;
    info += AstroUtils.toStringPhase(moon.phase) + "\n";
    
    // check: - not correct
    info += Language.PhaseAngle;
    info += AstroUtils.toStringShortAngle(moon.age) + "\n";
    
    info += Language.Age;
    info += AstroUtils.toStringMoonAge(moon.age) + "\n";
    
    info += Language.AngularDiameter;
    info += AstroUtils.toStringAngleDiameter(moon.angleDiameter) + "\n";
    
    info += Language.Magnitude;
    info += AstroUtils.toStringMagnitude(MoonCalculator.getMagnitude(moon.age)) + "\n";

    info += Language.PositionAngle;
    info += AstroUtils.toStringShortAngle(moon.positionAngle) + "\n";    

    info += Language.ParallacticAngle;
    info += AstroUtils.toStringShortAngle(moon.parallacticAngle) + "\n";    
    
    info += Language.Distance;
    info += String.valueOf((int)moon.distanceFromEarth) + Language.KM + "\n";
    
    CrdsEcliptical lib = MoonCalculator.getLibration(julianDay, moon.ecliptical);
    info += Language.LibrationLongitude;
    info += AstroUtils.toStringLibrationAngle(lib.lambda) + " " + (lib.lambda < 0 ?  Language.Directions[2] /*W*/ : Language.Directions[6] /*E*/) + "\n";
 
    info += Language.LibrationLatitude;
    info += AstroUtils.toStringLibrationAngle(lib.beta) + " " + (lib.beta < 0 ?  Language.Directions[4] /*S*/ : Language.Directions[0] /*N*/) + "\n";
       
    info += getRiseSetString(getRiseSetMoon()) + "\n";
    
    info += getMoonPhasesString();
    
    return info;
  }
  
  public static String getSunInfo()
  {
    String info = "";
    
    info += sun.name + "\n"; 
    
    info += getEquatorialString(sun.equatorial) + "\n";
    info += getHorizontalString(sun.horizontal) + "\n";
        
    info += Language.EclipticLongitude;
    info += AstroUtils.toStringUnsignedAngle(sun.ecliptical.lambda) + "\n";
    
    info += Language.AngularDiameter;
    info += AstroUtils.toStringAngleDiameter(sun.angleDiameter) + "\n";
        
    info += Language.Distance;
    info += AstroUtils.toStringDistance(sun.distanceFromEarth) + "\n";
    
    info += Language.CarringtonNumber;
    info += String.valueOf(SolarCalculator.carrington(julianDay)) + "\n";    
    
    RiseTransitSet rts = getRiseSetSun();
    info += getRiseSetString(rts) + "\n";
    
    info += Language.DayLength;
    info += AstroUtils.toStringTime(SolarCalculator.getDayLength(rts.rise, rts.set)) + "\n";
    
    info += getSeasonString() + "\n";
    
    return info;
  }
  
  public static String getJupiterMoonsInfo()
  {
    String info = "";
    for (int i=0; i<4; i++)
    {
      info += Language.JupiterMoons[i] + ":\n";
      info += "X = " + AstroUtils.toStringDecimal3(jupiterMoons[i].x) + "; ";
      info += "Y = " + AstroUtils.toStringDecimal3(jupiterMoons[i].y) + "; ";
      if (jupiterMoons[i].x * jupiterMoons[i].x +
          jupiterMoons[i].y * jupiterMoons[i].y * 1.14784225 < 1)
      {
        info += (jupiterMoons[i].z > 0) ? Language.JupiterMoonTransit : Language.JupiterMoonOccultation;
      }
      info += "\n";
    }
    
    info += Language.GreatRedSpot + ":\n";
    info += Language.GRSLongitude + AstroUtils.toStringShortAngle(jupiterGRS) + "\n";
    info += Language.CMLongitude + AstroUtils.toStringShortAngle(jupiterCM2);
    return info;
  }
  
  public static String getSaturnRingsInfo()
  {
    String info = Language.ToolSaturnRings + "\n";
    info += "a = " + AstroUtils.toStringAngleDiameter(saturnRings.x / 3600.0) + "\n";
    info += "b = " + AstroUtils.toStringAngleDiameter(saturnRings.y / 3600.0) + "\n";
    return info;
  }
  
  public static String getMarsAppearanceInfo()
  {
    String info = "";
    info += Language.Magnitude + AstroUtils.toStringMagnitude(Sky.planets[3].magnitude) + "\n";
    info += Language.Phase + AstroUtils.toStringPhase(Sky.planets[3].phase) + "\n";
    info += Language.AngularDiameter + AstroUtils.toStringAngleDiameter(planets[3].angleDiameter / 3600.0) + "\n";
    info += Language.DeclinationEarth + AstroUtils.toStringShortAngle(marsAppearance.D) + "\n";
    info += Language.CMLongitude + AstroUtils.toStringShortAngle(marsAppearance.lambda) + "\n";    
    info += Language.PositionAngle + AstroUtils.toStringShortAngle(marsAppearance.P) + "\n";  
    return info;
  }
  
  public static String getVenusPhaseInfo()
  {
    final double positionAngle = AstroUtils.positionAngle(Sky.sun.equatorial, Sky.planets[1].equatorial);    
    String info = "";
    info += Language.Magnitude + AstroUtils.toStringMagnitude(Sky.planets[1].magnitude) + "\n";
    info += Language.Phase + AstroUtils.toStringPhase(Sky.planets[1].phase) + "\n";
    info += Language.AngularDiameter + AstroUtils.toStringAngleDiameter(planets[1].angleDiameter / 3600.0) + "\n";  
    info += Language.PositionAngle + AstroUtils.toStringShortAngle(positionAngle) + "\n";  
    return info;
  }
  
  public static String getDayEvents(WaitScreen ws)
  {
    RiseTransitSet rts; 
    Vector events = new Vector();
    AstroEvent ev;
    int i;
    
    ws.setProgress(Language.CalcEphemerides, 10);
    
    // Sun events
    rts = getRiseSetSun();
    events.addElement(new AstroEvent(rts.rise, Language.EventRise + " " + Language.SunNameGen));
    events.addElement(new AstroEvent(rts.set, Language.EventSet + " " + Language.SunNameGen));
    ws.setProgress(Language.CalcEphemerides, 20);
    
    // Moon events
    rts = getRiseSetMoon();
    events.addElement(new AstroEvent(rts.rise, Language.EventRise + " " + Language.MoonNameGen));
    events.addElement(new AstroEvent(rts.set, Language.EventSet + " " + Language.MoonNameGen));
    ws.setProgress(Language.CalcEphemerides, 40);
    
    // Planet events
    for (i=0; i<6; i++)
    {
      if (i != EARTH)
      {
        rts = getRiseSetPlanet(i);
        events.addElement(new AstroEvent(rts.rise, Language.EventRise + " " + Language.PlanetNamesGen[i]));
        events.addElement(new AstroEvent(rts.set, Language.EventSet + " " + Language.PlanetNamesGen[i]));        
        ws.setProgress(Language.CalcEphemerides, 40 + i*10);
      }
    }
    
    AstroUtils.sortAstroEvents(events);
  
    String info = "";
    for (i=0; i<events.size(); i++)
    {
      ev = (AstroEvent)(events.elementAt(i));
      info += AstroUtils.toStringTime(ev.jd + observerLocation.timeZone / 24.0) +
              ": " + ev.text + "\n";
    }    
    
    return info;
  }
  
        
  /**
   * Calculates local circumstances of lunar eclipse.
   * @param e Eclipse global data.
   */
  public static void calculateLocalLunarEclipse(Eclipse e)
  {
    // main moments of eclipse
    double[] times = new double[7];
    final double minutesInDay = 1440.0;
    times[0] = e.jd - e.sdPenumbra / minutesInDay;
    times[1] = e.jd - e.sdPartial / minutesInDay;
    times[2] = e.jd - e.sdTotal / minutesInDay;
    times[3] = e.jd;
    times[4] = e.jd + e.sdTotal / minutesInDay;
    times[5] = e.jd + e.sdPartial / minutesInDay;    
    times[6] = e.jd + e.sdPenumbra / minutesInDay;  
                 
    CrdsEquatorial eqMoon = new CrdsEquatorial();    
    CrdsHorizontal horMoon;    
    double theta0;
    double jdSave = julianDay;
    
    // calculate moon equatorial coordinates for eclipse maximum 
    setDate(e.jd);
    calculateSolarEphemerides();
    calculateMoonEphemerides();
    eqMoon.RA = moon.equatorial.RA;
    eqMoon.Dec = moon.equatorial.Dec;
    
    // back to saved date & time  
    setDate(jdSave);
    calculateSolarEphemerides();
    calculateMoonEphemerides();      
    
    // calculate local horizontal coordinates 
    // of the Moon for main moments of the eclipse
    double[] alt = new double[7];
    for (int i=0; i<7; i++)
    {
      theta0 = AstroUtils.siderealTime(times[i]);
      alt[i] = AstroUtils.localHorizontal(theta0, eqMoon, observerLocation).altitude;
    }
    
    // determine local visibility of the eclipse
    if (alt[0] <= 0 && alt[6] <= 0)
    {
      e.visibility = Eclipse.VISIBILITY_NONE;
      e.jdBestVisible = -1;
    }  
    if (alt[3] > 0)
    {
      e.visibility = Eclipse.VISIBILITY_FULL;
      e.jdBestVisible = times[3];
    }    
    if (alt[0] > 0 && alt[3] <= 0)
    {
      e.visibility = Eclipse.VISIBILITY_START_PENUMBRA;
      e.jdBestVisible = times[0];
      if (alt[1] > 0) 
      {
        e.visibility = Eclipse.VISIBILITY_START_PARTIAL;
        e.jdBestVisible = times[1];
      }
      if (alt[2] > 0) 
      {
        e.visibility = Eclipse.VISIBILITY_START_FULL;
        e.jdBestVisible = times[2];
      }
    }
    if (alt[3] <= 0 && alt[6] > 0)
    {
      e.visibility = Eclipse.VISIBILITY_END_PENUMBRA;
      e.jdBestVisible = times[6];
      if (alt[5] > 0) 
      {
        e.visibility = Eclipse.VISIBILITY_END_PARTIAL;
        e.jdBestVisible = times[5];
      }
      if (alt[4] > 0) 
      {
        e.visibility = Eclipse.VISIBILITY_END_FULL;
        e.jdBestVisible = times[4];
      }
    }
  }
  
  /**
   * Calculates local circumstances of solar eclipse.
   * @param e Eclipse global data.
   */
  private static void calculateLocalSolarEclipse(Eclipse e)
  {
    CrdsEcliptical eclMoon; 
    double theta0;
    final double jdSave = julianDay;    
    boolean visible = false;
    double maxPhase = 0;
    
    // speed of the Moon relative to the Sun (degrees/day)
    final double speedMoon = (0.607 - 0.041) * 24.0;
    
    double[] longitudeMoon = new double[3];
    double[] longitudeSun = new double[3]; 
    
    CrdsEquatorial[] eqMoon = new CrdsEquatorial[3];
    CrdsEquatorial[] eqSun = new CrdsEquatorial[3];
    CrdsEquatorial eqMoon0 = new CrdsEquatorial();
    CrdsEquatorial eqSun0 = new CrdsEquatorial();
    double[] deltaCenter = new double[3];

    final double interval = 3./24.;    
    final double minute = 1.0 / 60.0 / 24.0;
    final double epsilon = 1e-6;
    
    double[] times = new double[3];  
    double f[] = new double[3];
    double x;        
    double delta;
    
    setDate(e.jd);
    calculateSolarEphemerides();
    calculateMoonEphemerides();
    eclMoon = AstroUtils.equatorialToEcliptical(moon.equatorial, epsilon1980);
    
    // time of eclipse maximum (for observer location), first approximation
    final double jd = e.jd - AstroUtils.subtractAngles360(eclMoon.lambda, sun.ecliptical.lambda) / speedMoon;
    
    for (int i=0; i<3; i++)
    {
      setDate(jd + (i-1) * interval);
      calculateSolarEphemerides();
      calculateMoonEphemerides();
      eclMoon = AstroUtils.equatorialToEcliptical(moon.equatorial, epsilon1980);
      longitudeMoon[i] = eclMoon.lambda;
      longitudeSun[i] = sun.ecliptical.lambda;   
      eqMoon[i] = new CrdsEquatorial();
      eqMoon[i].RA = moon.equatorial.RA;
      eqMoon[i].Dec = moon.equatorial.Dec;
      eqSun[i] = new CrdsEquatorial();
      eqSun[i].RA = sun.equatorial.RA;
      eqSun[i].Dec = sun.equatorial.Dec;
    }
    
    // MAXIMAL PHASE
    
    times[0] = jd - interval;
    times[1] = jd;
    times[2] = jd + interval;
    do
    {
      for (int i=0; i<3; i++)
      {
        x = (times[i] - (jd - interval)) / (interval * 2);
        f[i] = AstroUtils.subtractAngles360(
          AstroUtils.interpolate(x, longitudeMoon[0], longitudeMoon[1], longitudeMoon[2]),
          AstroUtils.interpolate(x, longitudeSun[0], longitudeSun[1], longitudeSun[2]));
      }
      
      if (f[0] * f[1] <= 0)
      {
        times[2] = times[1];
        times[1] = times[0] + (times[2]-times[0]) / 2.0;
      }
      else
      {
        times[0] = times[1];
        times[1] = times[0] + (times[2]-times[0]) / 2.0;
      }
    }
    while (Math.abs(f[1]) > epsilon && (times[2]-times[0]) > minute);
    e.jdLocal = times[1];
      
    // check visibility
    
    setDate(e.jdLocal);
    calculateSolarEphemerides();
    calculateMoonEphemerides();         
    // distance between solar & lunar centers
    delta = AstroUtils.angularDistance(moon.equatorial, sun.equatorial);
    // local visibility
    visible = delta < (moon.angleDiameter + sun.angleDiameter) / 2.0;
    
    if (visible)
    {
      // local maximal phase
      e.phaseLocal = ((sun.angleDiameter + moon.angleDiameter) / 2.0 - delta) / sun.angleDiameter;
      
      // FIRST CONTACT

      times[0] = jd - interval;
      times[1] = jd;
      times[2] = jd + interval;
      do
      {
        for (int i=0; i<3; i++)
        {        
          x = (times[i] - (jd - interval)) / (interval * 2);

          eqSun0.RA = (float)AstroUtils.interpolate(x, eqSun[0].RA, eqSun[1].RA, eqSun[2].RA);
          eqSun0.Dec = (float)AstroUtils.interpolate(x, eqSun[0].Dec, eqSun[1].Dec, eqSun[2].Dec);       
          eqMoon0.RA = (float)AstroUtils.interpolate(x, eqMoon[0].RA, eqMoon[1].RA, eqMoon[2].RA);
          eqMoon0.Dec = (float)AstroUtils.interpolate(x, eqMoon[0].Dec, eqMoon[1].Dec, eqMoon[2].Dec);          

          deltaCenter[i] = AstroUtils.angularDistance(eqMoon0, eqSun0)
                            - (moon.angleDiameter + sun.angleDiameter) / 2.0;

        }

        if (deltaCenter[0] * deltaCenter[1] <= 0)
        {
          times[2] = times[1];
          times[1] = times[0] + (times[2]-times[0]) / 2.0;
        }
        else
        {
          times[0] = times[1];
          times[1] = times[0] + (times[2]-times[0]) / 2.0;
        }      
      }
      while (Math.abs(deltaCenter[1]) > epsilon && (times[2]-times[0]) > minute);
      e.jdLocalPartialStart = times[1];

      // LAST CONTACT

      times[0] = jd - interval;
      times[1] = jd;
      times[2] = jd + interval; 
      do
      {
        for (int i=0; i<3; i++)
        {        
          x = (times[i] - (jd - interval)) / (interval * 2);

          eqSun0.RA = (float)AstroUtils.interpolate(x, eqSun[0].RA, eqSun[1].RA, eqSun[2].RA);
          eqSun0.Dec = (float)AstroUtils.interpolate(x, eqSun[0].Dec, eqSun[1].Dec, eqSun[2].Dec);       
          eqMoon0.RA = (float)AstroUtils.interpolate(x, eqMoon[0].RA, eqMoon[1].RA, eqMoon[2].RA);
          eqMoon0.Dec = (float)AstroUtils.interpolate(x, eqMoon[0].Dec, eqMoon[1].Dec, eqMoon[2].Dec);          

          deltaCenter[i] = AstroUtils.angularDistance(eqMoon0, eqSun0)
                            - (moon.angleDiameter + sun.angleDiameter) / 2.0;

        }

        if (deltaCenter[1] * deltaCenter[2] <= 0)
        {
          times[0] = times[1];
          times[1] = times[0] + (times[2]-times[0]) / 2.0;
        }
        else
        {
          times[2] = times[1];
          times[1] = times[0] + (times[2]-times[0]) / 2.0;
        }      
      }
      while (Math.abs(deltaCenter[1]) > epsilon && (times[2]-times[0]) > minute);
      e.jdLocalPartialEnd = times[1];
    }
    
    times[0] = e.jdLocalPartialStart;
    times[1] = e.jdLocal;
    times[2] = e.jdLocalPartialEnd;
    
    // solar altitudes for moments
    double[] alt = new double[3];
    for (int i=0; i<3; i++)
    {
      theta0 = AstroUtils.siderealTime(times[i]);
      alt[i] = AstroUtils.localHorizontal(theta0, sun.equatorial, observerLocation).altitude;    
    }
    
    // determine local visibility of the eclipse
    if (!visible || (alt[0] <= 0 && alt[2] <= 0))
    {
      e.visibility = Eclipse.VISIBILITY_NONE;
      e.jdBestVisible = -1;
    }  
    
    if (visible)
    {
      if (alt[1] > 0)
      {
        if (e.phaseLocal > 1)
          e.visibility = Eclipse.VISIBILITY_FULL;
        else
          e.visibility = Eclipse.VISIBILITY_PARTIAL;
        e.jdBestVisible = times[1];
      }
      if (alt[0] > 0 && alt[1] <= 0)
      {
        e.visibility = Eclipse.VISIBILITY_START_PARTIAL;
        e.jdBestVisible = times[0];
      }
      if (alt[1] <= 0 && alt[2] > 0)
      {
        e.visibility = Eclipse.VISIBILITY_END_PARTIAL; 
        e.jdBestVisible = times[2];
      }
    }
    
    // back to saved date & time  
    setDate(jdSave);
    calculateSolarEphemerides();
    calculateMoonEphemerides();          
  }
  
  /**
   * Provides detailed info about circumstances of lunar eclipse.
   * @param e Eclipse data.
   * @returns Returns String contains detailed info
   */  
  private static String getLunarEclipseInfo(Eclipse e)
  {
    String info = "";
    double jd; 
    final double minutesInDay = 1440.0;

    info += Language.EclipseType[e.type] + " ";
    jd = e.jd + observerLocation.timeZone / 24.0;
    info += AstroUtils.toStringDate(jd) + "\n";
    info += Language.EclipseMaximalPhase + ": " + AstroUtils.toStringDecimal3(e.phase) + "\n";
    info += Language.EclipseFromCurrentPoint + Language.EclipseVisibility[e.visibility] + "\n";   
    jd = e.jd - e.sdPenumbra / minutesInDay + observerLocation.timeZone / 24.0;
    info += Language.EclipseStartPenumbra + ": " + AstroUtils.toStringTime(jd) + "\n";   
    if (e.sdPartial > 0)
    {
      jd = e.jd - e.sdPartial / minutesInDay + observerLocation.timeZone / 24.0;
      info += Language.EclipseStartPartial + ": " + AstroUtils.toStringTime(jd) + "\n"; 
    }
    if (e.sdTotal > 0) 
    {
      jd = e.jd - e.sdTotal / minutesInDay + observerLocation.timeZone / 24.0;
      info += Language.EclipseStartTotal + ": " + AstroUtils.toStringTime(jd) + "\n";      
    }
    jd = e.jd + observerLocation.timeZone / 24.0;
    info  += Language.EclipseMaximalPhase + ": " + AstroUtils.toStringTime(jd) + "\n"; 
    if (e.sdTotal > 0) 
    {
      jd = e.jd + e.sdTotal / minutesInDay + observerLocation.timeZone / 24.0;
      info += Language.EclipseEndTotal + ": " + AstroUtils.toStringTime(jd) + "\n";       
    }
    if (e.sdPartial > 0) 
    {
      jd = e.jd + e.sdPartial / minutesInDay + observerLocation.timeZone / 24.0;
      info += Language.EclipseEndPartial + ": " + AstroUtils.toStringTime(jd) + "\n";      
    }
    jd = e.jd + e.sdPenumbra / minutesInDay + observerLocation.timeZone / 24.0;
    info += Language.EclipseEndPenumbra + ": " + AstroUtils.toStringTime(jd); 
    
    return info;
  }
  
  /**
   * Provides detailed info about circumstances of solar eclipse.
   * @param e Eclipse data.
   * @returns Returns String contains detailed info
   */   
  private static String getSolarEclipseInfo(Eclipse e)
  {
    String info = "";
    double jd;    

    info += Language.EclipseType[e.type] + " ";
    jd = e.jd + observerLocation.timeZone / 24.0;
    info += AstroUtils.toStringDateTime(jd) + "\n";
    info += Language.EclipseMaximalPhase + ": " + AstroUtils.toStringDecimal3(e.phase) + "\n";    
    info += Language.EclipseFromCurrentPoint + Language.EclipseVisibility[e.visibility] + "\n"; 
    
    if (e.visibility > 0)
    {
      info += Language.EclipseLocalCircumstances + ":\n";
      info += Language.EclipseMaximalPhase + ": " + AstroUtils.toStringDecimal3(e.phaseLocal) + "\n";
      jd = e.jdLocalPartialStart + observerLocation.timeZone / 24.0;
      info += Language.EclipseStartPartial + ": " + AstroUtils.toStringTime(jd) + "\n";
      jd = e.jdLocal + observerLocation.timeZone / 24.0;
      info += Language.EclipseMaximalPhase + ": " + AstroUtils.toStringTime(jd) + "\n";
      jd = e.jdLocalPartialEnd + observerLocation.timeZone / 24.0;
      info += Language.EclipseEndPartial + ": " + AstroUtils.toStringTime(jd) + "\n"; 
    }
    
    return info;
  }
  
  public static void calculateLocalEclipse(Eclipse e, int eclipseType)
  {
    if (eclipseType == Eclipse.SOLAR)
    {
      calculateLocalSolarEclipse(e);
    }
    else
    {
      calculateLocalLunarEclipse(e);
    }
  }
  
  public static String getEclipseInfo(Eclipse e, int eclipseType)
  {
    if (eclipseType == Eclipse.SOLAR)
    {
      return getSolarEclipseInfo(e);
    }
    else
    {
      return getLunarEclipseInfo(e);
    }
  }
}
