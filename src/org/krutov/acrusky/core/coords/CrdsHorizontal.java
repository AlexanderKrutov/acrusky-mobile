/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.coords;

/** Represents pair of horizontal coordinates */
public class CrdsHorizontal {
    /** Altitude */
    public float altitude;
    /** Azimuth */
    public float azimuth;
    /** Sine of azimuth (needed for fast rendering) */
    public float sinAzi;
    /** Cosine of azimuth (needed for fast rendering) */
    public float cosAzi;
    /** Coefficient of altitude (needed for fast rendering) */
    public float coefAlt;
}
