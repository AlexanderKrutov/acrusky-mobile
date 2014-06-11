/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

/** Describes planet appearance variables 
   (positional angle of axis, central meridian and etc.) 
 */
public class PlanetAppearance
{  
  /** Position of the null meridian */
  public double W;
  
  /** Planetographic longitude of the central meridian, or declination of Earth */
  public double lambda;
  
  /** Planetographic latitude of the Earth */
  public double D;
  
  /** Position angle of the axis */
  public double P;
 
  /** Rotation matrix (3x3) */
  public double[][] R;
  
}
