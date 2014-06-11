/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.ephem.OrbitalElements;

/**
 * Asteroid
 */
public class Asteroid
{
  /** Numeric id */
  public int id;
  /** Asteroid name */
  public String name;
  /** Orbital elements */
  public OrbitalElements orbit;
  /** Equatorial coordinates */
  public CrdsEquatorial equatorial;
  /** Horiaontal coordinates */
  public CrdsHorizontal horizontal;
  /** Absolute magnitude */
  public float H;
  /** Phase coefficient */
  public float G;
  /** Visible magnitude */
  public float magnitude;  
  /** Phase angle, in degrees */
  public double phaseAngle;
  /** Distance from Earth, in a.u.*/
  public double distanceFromEarth;
         
  public Asteroid()
  {
    orbit = new OrbitalElements();
    equatorial = new CrdsEquatorial();
  }
}
