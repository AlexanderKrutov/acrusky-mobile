/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

/** Contains orbital elements of minor body */
public class OrbitalElements
{
  /** Mean longitude */
  public float L;
  /** Semimajor axis */
  public float a;
  /** Eccentricity */
  public float e;
  /** Inclination */
  public float i;
  /** Perihelion distance, in a.u. */
  public float q;
  /** Longitude of the ascending node */
  public float Omega;
  /** Longitude of the perihelion (pi = Omega + omega) */
  public float pi;
  /** Argument of the perihelion */
  public float omega;
  /** Mean anomaly */
  public float M;
  /** Eccentricitical anomaly (solution of Kepler's equation) */
  public float E;
  /** True anomaly */
  public float v;
  /** Radius-vector */
  public float r;
  /** Orbital elements epoch (in julian days) */
  public float epoch;  
}
