/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

/**
 * Contains information about rise, transit and set of a body
 */
public class RiseTransitSet {
    /** Rise instant (julian day) */
    public double rise;
    /** Transit instant (julian day) */
    public double transit;
    /** Set instant (julian day) */
    public double set;
    /** Altitude at transit */
    public double transitAltitude;
    /** Azimuth at rise */
    public double riseAzimuth;
    /** Azimuth at set */
    public double setAzimuth;
    public RiseTransitSet() {
    }
}
