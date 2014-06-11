/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

import org.krutov.acrusky.core.Math2;

/**
 * Used to solve Kepler's equation
 */
public class KeplerEquation {  
    /**
     * Solves Kepler's equation for specified parameters
     * @param M Mena anomaly
     * @param e Eccentricity
     * @return Eccentric anomaly
     */
    public static double solve(final double M, final double e) {
            final double angleM = Math.toRadians(M);

            double E0 = angleM;
            double E1 = angleM;
            final double M_ = angleM;
            do {
                    E0 = E1;
                    E1 = M_ + e * Math.sin(E0);
            } while (Math.abs(E1 - E0) >= 1e-9);
            return Math2.to360(Math.toDegrees(E1));
    }
}
