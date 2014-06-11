/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core;

import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.ephem.AstroEvent;
import org.krutov.acrusky.core.coords.CrdsEcliptical;
import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsGeographical;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.coords.CrdsRectangular;
import org.krutov.acrusky.core.ephem.RiseTransitSet;
import java.util.Date;
import java.util.Vector;

/**
 * AstroUtils implements a set of routines needed for astronomical calculations, 
 * like coordinates transformation, string formatting and etc.
 */
public class AstroUtils 
{
  /** Earth orbit obliquity for epoch 1980.0 */
  public static double epoch1980 = 2444238.5;
  public static final int DATEPART_YEAR = 0;
  public static final int DATEPART_MONTH = 1;
  public static final int DATEPART_DAY = 2;
  public static final int DATEPART_HOUR = 3;
  public static final int DATEPART_MINUTE = 4;
  public static final int DATEPART_SECOND = 5;
  
  /**
   * Converts angle value to Right Ascension (RA) string.
   *
   * @param angle angle value in degrees (0...360).
   * @return Returns string representation of angle in RA form 
   * (like 00h00m00s).
   */
  public static String toStringRA(double angle)
  {
    angle = angle / 15.0;
    int h = (int)angle;
    angle = (angle - h) * 60;
    int m = (int)(angle + .01);
    angle = (angle - m) * 60;
    int s = (int)(angle + .01);

    return ((h < 10 ? "0" : "") + h + Language.Hour + 
            (m < 10 ? "0" : "") + m + Language.Minute + 
            (s < 10 ? "0" : "") + s + Language.Second);
  }
        
 /**
  * Converts angle to its string representation with +/- sign.
  *
  * @param angle angle value in degrees
  * @return Returns string representation of angle in form ±00°00'00"
  */
  public static String toStringSignedAngle(double angle)
  {
    boolean neg = (angle < 0);
    angle = Math.abs(angle);
    return (neg ? "-" : "+") + toStringUnsignedAngle(angle);
  }  
        
  /**
  * Converts angle to its string representation without sign.
  *
  * @param angle angle value in degrees
  * @return Returns string representation of angle in form 00°00'00"
  */
  public static String toStringUnsignedAngle(double angle)
  {
    int d = (int)angle;
    angle = (angle - d) * 60;
    int m = (int)(angle + .01);
    angle = (angle - m) * 60;
    int s = (int)(angle + .01);

    return  ((d < 10 ? "0" : "") + d + Language.DegreeSign + 
             (m < 10 ? "0" : "") + m + "'" + 
             (s < 10 ? "0" : "") + s + "\"");
  }
        
  /**
  * Converts angle to its string representation.
  *
  * @param angle angle value in degrees
  * @return Returns string representation of angle in form 0.0°
  */
  public static String toStringShortAngle(double angle)
  {
    return String.valueOf((double)((int)(angle * 10)) / 10) + Language.DegreeSign;
  }

  /**
  * Converts magnitude value to its string representation.
  *
  * @param mag magnitude value
  * @return Returns string representation of magnitude in form 0.00m
  */        
  public static String toStringMagnitude(float mag)
  {
    mag = (float)((int)(mag * 100)) / 100.0f;
    return (mag > 0 ? "+" : "") + String.valueOf(mag) + "m";
  }
        
  /**
  * Converts decimal value to its string representation.
  *
  * @param number decimal value. 
  * @return Returns string representation in form 0.000.
  */
  public static String toStringDecimal3(double number)
  {
    number = (double)((int)(number * 1000)) / 1000.0f;
    return String.valueOf(number);
  }  
  
  /**
  * Converts distance value to its string representation.
  *
  * @param au distance value in a.u. (astronomical units).
  * @return Returns distance string representation in form 0.000 a.u.
  */
  public static String toStringDistance(double au)
  {
    return toStringDecimal3(au) + Language.AU;
  }   
  
  /**
  * Converts libration angle to its string representation.
  *
  * @param angle libration angle value in degrees.
  * @return Returns string representation of libration angle in form 0.0°
  */
  public static String toStringLibrationAngle(double angle)
  {
    angle = Math.abs(angle);
    angle = (double)((int)(angle * 10)) / 10.0;
    return String.valueOf(angle) + Language.DegreeSign;
  }
        
  /**
  * Converts Julian date value to its string representation.
  *
  * @param jd Julian date (with decimal).
  * @return Returns string representation of Julian date in form 
  * DD.MM.YYYY
  */
  public static String toStringDate(double jd)
  {
    int Y = getDatePart(jd, AstroUtils.DATEPART_YEAR);
    int M = getDatePart(jd, AstroUtils.DATEPART_MONTH);
    int D = getDatePart(jd, AstroUtils.DATEPART_DAY);
    return ((D < 10 ? "0" : "") + D  + "." + 
           (M < 10 ? "0" : "") + M + "." + Y);
  }
         
  /**
  * Extracts time value from Julian date and converts it to string 
  * representation.
  *
  * @param jd Julian date (with decimal).
  * @return Returns string representation of time in form hh:mm
  */
  public static String toStringTime(double jd)
  {
    if (Double.isNaN(jd)) return "-";

    int h = getDatePart(jd, AstroUtils.DATEPART_HOUR);
    int m = getDatePart(jd, AstroUtils.DATEPART_MINUTE);
    return ((h < 10 ? "0" : "") + h  + ":" + 
            (m < 10 ? "0" : "") + m);  
  }    
        
  /**
  * Converts Julian date value to date & time string representation.
  *
  * @param jd Julian date (with decimal).
  * @return Returns string representation of date and time in form 
  * DD.MM.YYYY hh:mm
  */
  public static String toStringDateTime(double jd)
  {
    return toStringDate(jd) + " " + toStringTime(jd);
  }

  /**
  * Converts numeric phase value to its string representation.
  *
  * @param phase Numeric phase value.
  * @return Returns string representation of phase value in form
  * 0.00
  */
  public static String toStringPhase(double phase)
  { 
    phase = Math.abs(phase);
    if (phase > 0.99) phase = 1;
    phase = (double)((int)(phase * 100)) / 100.0;
    String p = String.valueOf(phase); 
    return p + (p.length() < 4 ? "0" : "");
  }

  /**
  * Converts numeric Moon's age value to its string representation.
  *
  * @param age Numeric value of Moon age (in days).
  * @return Returns string representation of age value in form
  * 0.00d
  */
  public static String toStringMoonAge(double age)
  {
    age = age / 360.0 * 29.530589;
    age = (double)((int)(age * 100)) / 100.0;
    return String.valueOf(age) + Language.Days; 
  }

  /**
  * Converts angular diameter value to its string representation.
  *
  * @param angle Angular diameter (in degrees).
  * @return Returns string representation of angular diameter
  * in form 00°00'00"
  */
  public static String toStringAngleDiameter(double angle)
  {
    int d = (int)angle;
    angle = (angle - d) * 60;
    int m = (int)(angle + .01);
    angle = (angle - m) * 60;
    int s = (int)(angle + .01);

    return  ((m > 0 ? ((m < 10 ? "0" : "") + m + "'") : "") + 
            (m > 0 ? (s < 10 ? "0" : "") : "") + s + "\"");
  }          
        
  /**
   * Converts ecliptical coordinates to equatorial.
   *
   * @param ecliptical Ecliptical coordinates of the body
   * @param epsilon Earth orbit inclination for the current epoch
   * @return Returns pair of equatorial coordinates
   */
  public static CrdsEquatorial eclipticalToEquatorial(
          final CrdsEcliptical ecliptical, 
          final double epsilon) 
  {
    final CrdsEquatorial equatorial = new CrdsEquatorial();
    final double y = Math.sin(Math.toRadians(ecliptical.lambda))
                  * Math.cos(Math.toRadians(epsilon))
                  - Math.tan(Math.toRadians(ecliptical.beta))
                  * Math.sin(Math.toRadians(epsilon));
    final double x = Math.cos(Math.toRadians(ecliptical.lambda));
    equatorial.RA = (float)Math.toDegrees(Math2.atan2(y, x));
    equatorial.RA = (float)Math2.to360(equatorial.RA);
    equatorial.Dec = (float)Math.toDegrees(Math2.asin(Math.sin(Math
                  .toRadians(ecliptical.beta))
                  * Math.cos(Math.toRadians(epsilon))
                  + Math.cos(Math.toRadians(ecliptical.beta))
                  * Math.sin(Math.toRadians(epsilon))
                  * Math.sin(Math.toRadians(ecliptical.lambda))));
    return equatorial;
  }
  
  /**
   * Converts equatorial coordinates to ecliptical.
   *
   * @param equatorial CrdsEquatorial coordinates of the body
   * @param epsilon Earth orbit inclination for the current epoch
   * @return Returns pair of ecliptical coordinates
   */  
  public static CrdsEcliptical equatorialToEcliptical(
          final CrdsEquatorial equatorial, 
          final double epsilon)
  {
    final CrdsEcliptical ecliptical = new CrdsEcliptical();
    final double y = Math.sin(Math.toRadians(equatorial.RA)) * 
                     Math.cos(Math.toRadians(epsilon)) + 
                     Math.tan(Math.toRadians(equatorial.Dec)) * 
                     Math.sin(Math.toRadians(epsilon));
    final double x = Math.cos(Math.toRadians(equatorial.RA));
    ecliptical.lambda = Math2.to360(Math.toDegrees(Math2.atan2(y, x)));
    ecliptical.beta = Math.toDegrees(
                     Math2.asin(Math.sin(Math.toRadians(equatorial.Dec)) * 
                                  Math.cos(Math.toRadians(epsilon)) - 
                                  Math.cos(Math.toRadians(equatorial.Dec)) * 
                                  Math.sin(Math.toRadians(epsilon)) * 
                                  Math.sin(Math.toRadians(equatorial.RA))));
    return ecliptical;    
  }
  
  /**
   * Converts rectangular coordinates to equatorial.
   * 
   * @param sun Rectangular coordinates of the Sun
   * @param planet Rectangular coordinates of a planet
   * @return Returns pair of equatorial coordinates
   */
  public static CrdsEquatorial rectangularToEquatorial(CrdsRectangular sun, CrdsRectangular planet)
  {
    CrdsEquatorial eq = new CrdsEquatorial();
    eq.RA = (float)Math.toDegrees(Math2.atan2(sun.y + planet.y, sun.x + planet.x));
    eq.RA = (float)Math2.to360(eq.RA);
    final double delta = Math.sqrt(Math2.pow(sun.x + planet.x, 2) + Math2.pow(sun.y + planet.y, 2) + Math2.pow(sun.z + planet.z, 2));
    eq.Dec = (float)Math.toDegrees(Math2.asin((sun.z + planet.z) / delta));
    return eq;
  }  
  
  /**
   * Calculates local (referred to the observation point) horizontal coordinates
   * 
   * @param H Hour angle of a body
   * @param phi Geographical latitude of the observation point
   * @param delta Declination of a body
   * @return horizontal coordinates referred to the observation point
   */
  private static CrdsHorizontal getLocalHorizontal(
          final double H,
          final double phi, 
          final double delta) 
  {
    final CrdsHorizontal horizontal = new CrdsHorizontal();
    final double Y = Math.sin(Math.toRadians(H));
    final double X = Math.cos(Math.toRadians(H)) * Math.sin(Math.toRadians(phi))
                    - Math.tan(Math.toRadians(delta)) * Math.cos(Math.toRadians(phi));

    final double azi = Math2.atan2(Y, X);
    final double alt = Math2.asin(Math.sin(Math.toRadians(phi))
                    * Math.sin(Math.toRadians(delta)) + Math.cos(Math.toRadians(phi))
                    * Math.cos(Math.toRadians(delta)) * Math.cos(Math.toRadians(H)));

    horizontal.azimuth = (float)Math2.to360(Math.toDegrees(azi));

    horizontal.sinAzi = -(float)Math.sin(azi + Math.PI);
    horizontal.cosAzi = (float)Math.cos(azi + Math.PI);
    horizontal.coefAlt = (float)(Math.cos(alt) / (Math.sin(alt) + 1));

    horizontal.altitude = (float)Math.toDegrees(alt);
    return horizontal;
  }

  /**
   * Converts horizontal coordinates to equatorial
   * 
   * @param horizontal Horizontal coordinates
   * @param geographical Geographical coordinates of the observation point
   * @param theta0 Sidereal time
   * @return Equatorial coordinates
   */
  public static CrdsEquatorial horizontalToEquatorial(
          final CrdsHorizontal horizontal,
          final CrdsGeographical geographical, 
          final double theta0) 
  {
    final double phi = geographical.latitude;
    final double L = geographical.longitude;

    final CrdsEquatorial eq = new CrdsEquatorial();
    final double H = Math.toDegrees(Math2.atan2(Math.sin(Math
                    .toRadians(horizontal.azimuth)), (Math.cos(Math
                    .toRadians(horizontal.azimuth))
                    * Math.sin(Math.toRadians(phi)) + Math.tan(Math
                    .toRadians(horizontal.altitude))
                    * Math.cos(Math.toRadians(phi)))));
    eq.RA = (float)(theta0 - L - H);
    eq.RA = (float)Math2.to360(eq.RA);
    eq.Dec = (float)Math.toDegrees(Math2.asin(Math.sin(Math.toRadians(phi))
                    * Math.sin(Math.toRadians(horizontal.altitude))
                    - Math.cos(Math.toRadians(phi))
                    * Math.cos(Math.toRadians(horizontal.altitude))
                    * Math.cos(Math.toRadians(horizontal.azimuth))));
    return eq;
  }

  /**
   * Calculates hour angle
   * 
   * @param theta0 Sidereal time
   * @param L  Geographical longitude of the observation point
   * @param alpha Right ascention
   * @return Hour angle
   */
  public static double hourAngle(
          final double theta0, 
          final double L,
          final double alpha) 
  {
    return theta0 - L - alpha;
  }

  /** 
   * Calculates current julian day corresponding to specified Date object 
   * 
   * @param d Date object to be converted to julian day
   * @return julian day value
   */
  public static double julianDay(final Date d) 
  {
    double JD = 2440587.5; // 01 January 1970
    return JD + d.getTime() / (1000.0 * 60 * 60 * 24);
  }
  
  /** 
   * Calculates current julian day corresponding to specified date
   * 
   * @param Y year
   * @param M month
   * @param D day
   * @param h hours
   * @param m minutes
   * @return julian day value
   */
  public static double julianDay(int Y, int M, int D, int h, int m) 
  {
    if (M <= 2)
    {
      M += 12;
      Y--;
    }

    int A = (int)((int)(Y) / 100.0);
    int B = 2 - A + (int)((int)(A) / 4.0);

    return (int)(365.25 * (Y + 4716)) + 
           (int)(30.600001 * (M + 1)) + D + 
           ((double)h / 24.0) + ((double)m / 1440.0) + B - 1524.5;   
  }        

  /**
   * Returns date part corresponding to the specified julian day
   * 
   * @param jd Julian day
   * @param part date part (one of DATEPART_YEAR, DATEPART_MONTH, DATEPART_DAY, 
   * DATEPART_HOUR, DATEPART_MINUTE)
   * @return Date part corresponding to the specified julian day
   */
  public static int getDatePart(final double jd, int part)
  {
    int YY = 0, MM = 0;
    double DD;
    double JD = jd + 0.5;
    int Z = (int)JD;
    double F = JD - Z;
    int A = 0;
    if (Z < 2299161)
    {
        A = Z;
    }
    else
    {
        int a = (int)((double)(Z - 1867216.25) / 36524.25);
        A = Z + 1 + a - (int)((double)(a) / 4.0);  
    }
    int B = A + 1524;
    int C = (int)((double)(B - 122.1) / 365.25);
    int D = (int)(365.25 * C);
    int E = (int)((double)(B - D) / (30.6001));
    DD = B - D - (int)(30.6001 * E) + F;
    if (E < 14) MM = E - 1;
    if (E == 14 || E == 15) MM = E - 13;
    if (MM > 2) YY = C - 4716;
    if (MM == 1 || MM == 2) YY = C - 4715;

    double value = DD;
    int dd = (int)value;
    value = (value - (int)value) * 24.0;
    int hh = (int)value;
    value = (value - (int)value) * 60.0;
    int mm = (int)(value);

    switch (part)
    {
        case DATEPART_YEAR:
          return YY;
        case DATEPART_MONTH:
          return MM;
        case DATEPART_DAY:
          return dd;
        case DATEPART_HOUR:
          return hh;
        case DATEPART_MINUTE:
          return mm;
        default:
          break;
    }
    return 0;
  }
  
  /** 
   * Calculates whether an object is visible on current latitude or not
   * 
   * @param latitude Geographical latitude of observation point
   * @param dec Object declination
   * @return true if visiblem false otherwise
   */
  public static boolean isVisibleOnLatitude(double latitude, double dec)
  {
    if ((latitude >= 0 && dec < latitude - 90) || 
        (latitude < 0 && dec > 90 + latitude))
    {
      // Object is invisible:
      return false;
    }
    return true;
  }

  /**
   * Calculates local horizontal coordinates of an object 
   * @param siderealTime Sidereal time
   * @param e Equatorial coordinates of an object 
   * @param g Geographical coordinates of observation point 
   * @return Local horizontal coordinates of an object 
   */
  public static CrdsHorizontal localHorizontal(
          final double siderealTime,
          final CrdsEquatorial e, 
          final CrdsGeographical g) 
  {
    final double H = hourAngle(siderealTime, g.longitude, e.RA);
    return getLocalHorizontal(H, g.latitude, e.Dec);
  }

  /**
   * Calculates positional angle of an object
   * 
   * @param eqSun Equatorial coordinates of the Sun
   * @param eqBody Equatorial coordinates of an object
   * @return Positional angle of an object
   */
  public static double positionAngle(CrdsEquatorial eqSun, CrdsEquatorial eqBody)
  {
    double deltaRA = Math.toRadians(eqSun.RA - eqBody.RA);
    double y = Math.cos(Math.toRadians(eqSun.Dec)) * Math.sin(deltaRA);
    double x =  Math.cos(Math.toRadians(eqBody.Dec)) *  Math.sin(Math.toRadians(eqSun.Dec))
                -  Math.sin(Math.toRadians(eqBody.Dec)) *  Math.cos(Math.toRadians(eqSun.Dec))
                * Math.cos(deltaRA);

    return Math2.to360(Math.toDegrees(Math2.atan2(y, x)));
  }       
  
  /**
   * Calculates distance from Earth for the desired object
   * 
   * @param sun Rectangular coordinates of the Sun
   * @param body Rectangular coordinates of the object
   * @return distance from Earth for the desired object, in a.u.
   */
  public static double distanceFromEarth(CrdsRectangular sun, CrdsRectangular body)
  {
    return Math.sqrt((sun.x + body.x) * (sun.x + body.x) + 
                     (sun.y + body.y) * (sun.y + body.y) + 
                     (sun.z + body.z) * (sun.z + body.z));
  }
  
  /**
   * Calculates phase angle for the body
   * 
   * @param r Radius-vector of the body (distance from the Sun)
   * @param R Radius-vector of the Earth (distance from the Sun)
   * @param rho Distance from the Earth
   * @return 
   */
  public static double phaseAngle(final double r, final double R,
                  final double rho) {
          return Math.toDegrees(Math2.acos((r * r + rho * rho - R * R)
                          / (2.0 * r * rho)));
  }  
  
  /**
   * Calculates parallactic angle for the body
   * 
   * @param siderealTime Sidereal time
   * @param geo Geographical coordinates of observation point
   * @param eq Equatorial coordinates of the body
   * @return Parallactic angle of the body
   */
  public static double parallacticAngle(double siderealTime, CrdsGeographical geo, CrdsEquatorial eq)
  {
    double H = Math.toRadians(hourAngle(siderealTime, geo.longitude, eq.RA));

    double x = Math.sin(H);
    double y = Math.tan(Math.toRadians(geo.latitude)) * Math.cos(Math.toRadians(eq.Dec)) 
        - Math.sin(Math.toRadians(eq.Dec)) * Math.cos(H); 

    return Math2.to360(Math.toDegrees(Math2.atan2(y, x)));
  }
  
  /**
   * Adjusts angle values to 0...360 range 
   * @param Y1 angle value 1
   * @param Y2 angle value 2
   * @return Distance between 2 angles adjusted between 0...360 degrees.
   */
  public static double subtractAngles360(double Y1, double Y2)
  {
    if (Math.abs(Y1 - Y2) > 180)
    {
        if (Y1 < Y2) Y1 += 360;
        else Y2 += 360;
    }
    return Y1 - Y2;
  }
  
  /**
   * Does simple quadratic interpolation by 3 points for function Y(X), X in [0,1]
   * 
   * @param n X point to find interploated value Y
   * @param Y1 Y point 1
   * @param Y2 Y point 2
   * @param Y3 Y point 3
   * @return Interpolated function value Y at point X
   */
  public static double interpolate(double n, double Y1, double Y2, double Y3)
  {
    if (Math.abs(Y1 - Y2) > 180)
    {
        if (Y1 < Y2) Y1 += 360;
        else Y2 += 360;
    }
    if (Math.abs(Y2 - Y3) > 180)
    {
        if (Y2 < Y3) Y2 += 360;
        else Y3 += 360;
    }

    double a = 2 * Y1 - 4 * Y2 + 2 * Y3;
    double b = -3 * Y1 + 4 * Y2 - Y3;
    double c = Y1;

    return Math2.to360(a * n * n + b * n + c);
  }
  
  /**
   * Solves parabola equation by 3 points
   * 
   * @param y1 Y point 1
   * @param y2 Y point 2
   * @param y3 Y point 3
   * @return Root of parabola equation (X), NaN if no solution
   */
  private static double solveParabola(double y1, double y2, double y3)
  {
    double a = 2 * y1 - 4 * y2 + 2 * y3;
    double b = -3 * y1 + 4 * y2 - y3;
    double c = y1;

    double D = Math.sqrt(b * b - 4 * a * c);

    double x1 = (- b - D) / (2 * a);
    double x2 = (- b + D) / (2 * a);

    if (x1 >= 0 && x1 <= 1) return x1;
    if (x2 >= 0 && x2 <= 1) return x2;

    return Double.NaN;
  }
  
  /**
   * Calculates local midnight instant
   * 
   * @param julianDay Julian day to calculate for
   * @param timeZone Current time zone
   * @return  local midnight instant (jd)
   */
  public static double localMidnight(double julianDay, double timeZone)
  {
    double jd = julianDay + timeZone / 24.0;
    int h = getDatePart(jd, AstroUtils.DATEPART_HOUR);
    int m = getDatePart(jd, AstroUtils.DATEPART_MINUTE);
    return jd - (double)h / 24.0 - (double)m/1440.0 - timeZone / 24.0;
  }
        
    /**
     * Calculates rise, transit and set details for specified julian day for particular object
     * 
     * @param jde Julian day
     * @param eq1 Equatorial coordinates of an object at midnight
     * @param eq2 Equatorial coordinates of an object at mid of the day
     * @param eq3 Equatorial coordinates at next midnight
     * @param location Observation point
     * @param h0 Correction for refraction (depends on type of object)
     * @return RiseTransitSet instance describing RTS details
     */
  public static RiseTransitSet getRiseTransitSet(double jde,
                                           CrdsEquatorial eq1,
                                           CrdsEquatorial eq2,
                                           CrdsEquatorial eq3,
                                           CrdsGeographical location,
                                           double h0)
  {
      final RiseTransitSet result = new RiseTransitSet(); 

      CrdsHorizontal[] hor = new CrdsHorizontal[24 + 1];

      for (int i = 0; i <= 24; i++)
      {
          double n = (double)i / 24.0;
          CrdsEquatorial eq = new CrdsEquatorial();
          eq.RA = (float)interpolate(n, eq1.RA, eq2.RA, eq3.RA);
          eq.Dec = (float)interpolate(n, eq1.Dec, eq2.Dec, eq3.Dec);
          double theta0 = siderealTime(jde + n);
          hor[i] = localHorizontal(theta0, eq, location);
          hor[i].altitude += h0;
      }

      int rise = -1;
      int set = -1;
      for (int i = 0; i < 24; i++)
      {
          // rise:
          if (hor[i].altitude <= 0 && hor[i + 1].altitude >= 0)
          {
              rise = i;
          }
          // set:
          else if (hor[i].altitude >= 0 && hor[i + 1].altitude <= 0)
          {
              set = i;
          }
      }

      // If rising occurs:
      if (rise != -1)
      {
          // eq: at the middle of hour
          CrdsEquatorial eq = new CrdsEquatorial();
          eq.RA = (float)interpolate((rise + 0.5) / 24.0, eq1.RA, eq2.RA, eq3.RA);
          eq.Dec = (float)interpolate((rise + 0.5) / 24.0, eq1.Dec, eq2.Dec, eq3.Dec);

          double theta0 = siderealTime(jde + (rise + 0.5) / 24.0);
          CrdsHorizontal hor0 = localHorizontal(theta0, eq, location);
          hor0.altitude += h0;

          double n = solveParabola(hor[rise].altitude, hor0.altitude, hor[rise + 1].altitude);

          result.rise = jde + (rise + n) / 24.0;
          result.riseAzimuth = interpolate(n, hor[rise].azimuth, hor0.azimuth, hor[rise + 1].azimuth);
      }
      else
      {
          result.rise = Double.NaN;
          result.riseAzimuth = Double.NaN;
      }

      // if setting occurs:
      if (set != -1)
      {
          // eq: at the middle of hour
          CrdsEquatorial eq = new CrdsEquatorial();
          eq.RA = (float)interpolate((set + 0.5) / 24.0, eq1.RA, eq2.RA, eq3.RA);
          eq.Dec = (float)interpolate((set + 0.5) / 24.0, eq1.Dec, eq2.Dec, eq3.Dec);

          double theta0 = siderealTime(jde + (set + 0.5) / 24.0);
          CrdsHorizontal hor0 = localHorizontal(theta0, eq, location);
          hor0.altitude += h0;

          double n = solveParabola(hor[set].altitude, hor0.altitude, hor[set + 1].altitude);

          result.set = jde + (set + n) / 24.0;
          result.setAzimuth = interpolate(n, hor[set].azimuth, hor0.azimuth, hor[set + 1].azimuth);
      }
      else
      {
          result.set = Double.NaN;
          result.setAzimuth = Double.NaN;
      }

      // transit:
      result.transit = Double.NaN;
      result.transitAltitude = Double.NaN;
      for (int i = 0; i < 24; i++)
      {
          // eq: at the middle of hour
          CrdsEquatorial eq = new CrdsEquatorial();
          eq.RA = (float)interpolate((i + 0.5) / 24.0, eq1.RA, eq2.RA, eq3.RA);
          eq.Dec = (float)interpolate((i + 0.5) / 24.0, eq1.Dec, eq2.Dec, eq3.Dec);

          double theta0 = siderealTime(jde + (i + 0.5) / 24.0);
          CrdsHorizontal hor0 = localHorizontal(theta0, eq, location);

          if (hor0.altitude > 0)
          {
              double n = solveParabola(Math.sin(Math.toRadians(hor[i].azimuth)), Math.sin(Math.toRadians(hor0.azimuth)), Math.sin(Math.toRadians(hor[i + 1].azimuth)));
              if (!Double.isNaN(n))
              {
                  result.transit = jde + (i + n) / 24.0;
                  result.transitAltitude = interpolate(n, hor[i].altitude - h0, hor0.altitude, hor[i + 1].altitude - h0);
                  break;
              }
          }
      }

      return result;            
  }
  
  /**
   * Calculates sidereal time
   * 
   * @param jd Julian day to calculate for
   * @return Sidereal time
   */
  static public double siderealTime(final double jd)
  {
    final double T = (jd - 2451545.0) / 36525.0;
    double theta0 = 280.46061837 + 360.98564736629 * (jd - 2451545.0)
                    + 0.000387933 * T * T - T * T * T / 38710000.0;
    theta0 = Math2.to360(theta0);
    return theta0;
  }
  
  /**
   * Sorts astronomical events by julian day
   * 
   * @param events Vector of astronomical events
   */
  public static void sortAstroEvents(Vector events)
  {
    int i, j;
    AstroEvent temp = new AstroEvent(0, "");
    AstroEvent e1, e2;
    
    for(i=0; i<events.size()-1; i++)
    {
      for(j=i+1; j < events.size(); j++)
      {
        e1 = ((AstroEvent)(events.elementAt(i)));
        e2 = ((AstroEvent)(events.elementAt(j)));
        if(e1.jd > e2.jd)
        {
          temp.jd = e1.jd;
          temp.text = e1.text;
          e1.jd = e2.jd;
          e1.text = e2.text;
          e2.jd = temp.jd;
          e2.text = temp.text;
        }
      }
    }
  }
  
  /**
   * Cheks is leap year or not
   * 
   * @param year Year value
   * @return True if leap year, false otherwise
   */
  public static boolean isLeapYear(int year)
  {
    if (year < 1582)
    {
      if (year % 4 == 0) return true;
    }
    if (year % 100 == 0 && (year / 100) % 4 != 0) return false;
    if (year % 4 == 0) return true;
    return false;
  }  
  
  /**
   * Gets day number in year
   * 
   * @param year Year
   * @param month Month
   * @param day Day
   * @return Day number in year
   */
  public static int dayOfYear(int year, int month, int day)
  {
      int K = (isLeapYear(year)) ? 1 : 2;
      int N = (int)((int)((275 * month) / 9) - K * (int)((month + 9) / 12) + day - 30);
      return N;
  }  
  
  /**
   * Converts julian day to year with fractions
   * 
   * @param jd Julian day
   * @return year with fractions, for ex. 2008.543524
   */
  public static double julianDayToYear(double jd)
  {
    int year = AstroUtils.getDatePart(jd, AstroUtils.DATEPART_YEAR);
    int month = AstroUtils.getDatePart(jd, AstroUtils.DATEPART_MONTH);
    int day = AstroUtils.getDatePart(jd, AstroUtils.DATEPART_DAY);
    double dayOfYear = dayOfYear(year, month, day);
    return year + dayOfYear / 365.2425;
  }
 
  /**
   * Calculates angular distance between two objects with equatorial coordinates
   * 
   * @param eq1 Equatorial coorsinates of first object
   * @param eq2 Equatorial coorsinates of second object
   * @return Angular distance between objects
   */
  public static double angularDistance(CrdsEquatorial eq1, CrdsEquatorial eq2)
  {
      double decl1 = eq1.Dec;
      double decl2 = eq2.Dec;
      double RA1 = eq1.RA;
      double RA2 = eq2.RA;

      double a = Math2.acos(
          Math.sin(decl1 * Math.PI / 180) * Math.sin(decl2 * Math.PI / 180) +
          Math.cos(decl1 * Math.PI / 180) * Math.cos(decl2 * Math.PI / 180) * Math.cos((RA1 - RA2) * Math.PI / 180)) * 180 / Math.PI;

      return Double.isNaN(a) ? 0 : a;
  }  
  
  /**
   * Calculates rotation matrix for Mars
   * 
   * @param psi Opposite-side value for planetographic longitude of the central meridian, 
   * or declination of Earth: psi = (180-lambda)
   * @param theta Inverted position angle of the axis: theta = -P
   * @param phi Inverted absolute value for planetographic latitude of the Earth:
   * phi = -|D|
   * @return Rotation matrix as two-dimensional array
   */
  public static double[][] rotationMatrix(double psi, double theta, double phi)
  {
    double[][] R = new double[3][3];
    
    psi = Math.toRadians(psi);
    theta = Math.toRadians(theta);
    phi = Math.toRadians(phi);

    R[0][0] = Math.cos(phi) * Math.cos(psi) - Math.sin(phi) * Math.cos(theta) * Math.sin(psi);
    R[0][1] = Math.cos(phi) * Math.sin(psi) + Math.sin(phi) * Math.cos(theta) * Math.cos(psi);
    R[0][2] = Math.sin(phi) * Math.sin(theta);

    R[1][0] = -Math.sin(phi) * Math.cos(psi) - Math.cos(phi) * Math.cos(theta) * Math.sin(psi);
    R[1][1] = -Math.sin(phi) * Math.sin(psi) + Math.cos(phi) * Math.cos(theta) * Math.cos(psi);
    R[1][2] = Math.cos(phi) * Math.sin(theta);

    R[2][0] = Math.sin(theta) * Math.sin(psi);
    R[2][1] = -Math.sin(theta) * Math.cos(psi);
    R[2][2] = Math.cos(theta);

    return R;
  }
}
