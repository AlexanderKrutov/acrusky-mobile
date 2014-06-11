/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

/** 
  Provides detailed info about eclipse (lunar or solar).
 */
public class Eclipse
{
  // Types of eclipse
  public static final int SOLAR = 0;
  public static final int LUNAR = 1;    
  
  // Details of eclipses
  public static final int SOLAR_NONCENTRAL            = 0;
  public static final int SOLAR_PARTIAL               = 1;
  public static final int SOLAR_CENTRAL_TOTAL         = 2;
  public static final int SOLAR_CENTRAL_ANNULAR       = 3;
  public static final int SOLAR_CENTRAL_ANNULAR_TOTAL = 4;
  public static final int LUNAR_UMBRAL_TOTAL          = 5;
  public static final int LUNAR_UMBRAL_PARTIAL        = 6;
  public static final int LUNAR_PENUMBRAL             = 7;  
 
  // Local visibility circumstances (lunar & solar both)
  public static final int VISIBILITY_NONE             = 0;
  public static final int VISIBILITY_PARTIAL          = 1;
  public static final int VISIBILITY_FULL             = 2;
  public static final int VISIBILITY_START_PENUMBRA   = 3;
  public static final int VISIBILITY_START_PARTIAL    = 4;
  public static final int VISIBILITY_START_FULL       = 5;  
  public static final int VISIBILITY_END_FULL         = 6;    
  public static final int VISIBILITY_END_PARTIAL      = 7;
  public static final int VISIBILITY_END_PENUMBRA     = 8;
  
  /** UTC date & time of maximal phase of eclipse (for Earth center) */
  public double jd;  
  
  /** Maximal phase of eclipse */
  public double phase;
  
  /** Type of eclipse */
  public int type;  
  
  /** Minimal distance between:
   a) solar eclipse: center of Moon shadow axis and Earth center;
   b) lunar eclipse: Moon center and Earth shadow axis. */
  public double gamma;
  
  /** Radius of ...  */
  public double u;  
  
  /** Eclipse visibiliy for local point */
  public int visibility;
  
  /** Julian date for observable phase */
  public double jdBestVisible;
  
  // LUNAR ECLIPSE
      
  /** Penumra radius (in Earth equatorial radii) */
  public double rho;
  
  /** Umbra radius (in Earth equatorial radii) */
  public double sigma; 
  
  /** Semiduration of partial phase in penumbra, in minutes */
  public double sdPenumbra;  
  
  /** Semiduration of partial phase, in minutes */
  public double sdPartial;
  
  /** Semiduration of total phase, in minutes */
  public double sdTotal;  
  
  // SOLAR ECLIPSE
  
  /** Maximal local phase of eclipse */
  public double phaseLocal;  
  
  /** UTC date & time eclipse maximum for local point */
  public double jdLocal;
  
  /** Time of partial phase beginning for local point */
  public double jdLocalPartialStart;
  
  /** Time of partial phase ending for local point */  
  public double jdLocalPartialEnd;
}
