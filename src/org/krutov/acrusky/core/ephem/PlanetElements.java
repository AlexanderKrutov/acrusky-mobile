/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

/**
 * Orbital elements of major planet
 */
public class PlanetElements {

    // For ALL planets:

    /** Mean anomaly */
    public double M;

    /** True anomaly */
    public double v;

    /** Heliocentric longitude */
    public double l;

    /** Radius-vector (distance from Sun), in a.u.*/
    public double r;

    // For planets except Earth:

    /** Heliocentric latitude of planet */
    public double psi;

    /** Projection of heliocentric longitude of planet */
    public double l_;

    /** Projection of radius-vector */
    public double r_;
}
