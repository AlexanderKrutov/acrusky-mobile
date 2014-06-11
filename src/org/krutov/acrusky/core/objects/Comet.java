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
import org.krutov.acrusky.core.ephem.CometAppearance;

/** Comet */
public class Comet
{
  /** Numeric id */
  public int id;
  /** Comet name */
  public String name;
  /** Orbital elements */
  public OrbitalElements orbit;
  /** Equatorial coordinates */
  public CrdsEquatorial equatorial;
  /** Horizontal corrdinates */
  public CrdsHorizontal horizontal;
  /** Absolute magnitude */
  public float g;
  /** Phase coefficient */
  public float k;
  /** Visible magnitude */
  public float magnitude;  
  /** Phase angle, in degrees */
  //public double phaseAngle;
  /** Distance from Earth, in a.u.*/
  public double distanceFromEarth;

  public CometAppearance appearance; 
  public CrdsEquatorial tailEquatorial;
  public CrdsHorizontal tailHorizontal;  
  
  public Comet()
  {
    orbit = new OrbitalElements();
    equatorial = new CrdsEquatorial();
    horizontal = new CrdsHorizontal();
    tailEquatorial = new CrdsEquatorial();
    tailHorizontal = new CrdsHorizontal();    
  }
}
