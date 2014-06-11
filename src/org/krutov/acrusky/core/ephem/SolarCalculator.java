/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

import org.krutov.acrusky.core.Math2;
import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.core.coords.CrdsEcliptical;
import org.krutov.acrusky.core.coords.CrdsRectangular;

/**
 * Contains routines for calculation solar ephemerides.
 */
public class SolarCalculator {

    /** Angular diameter at 1 a.u. */
    private static double theta0 = 0.533128;

    /** Ecliptical longitude for the epoch 1980.0 */
    private static double epsilonG = 278.833540;

    /** Ecliptical longitude at perigee */
    public static double omegaG = 282.596403;

    /** Eccentricity of Earth orbit */
    public static double e = 0.016718;

    // seasons constants
    public final static int SPRING = 0;
    public final static int SUMMER = 1; 
    public final static int AUTUMN = 2;
    public final static int WINTER = 3; 

    /**
     * Calculates angular diameter of Sun
     * @param rho Distance between Earth and Sun, in a.u.
     * @return Visible angular diameter of Sun
     */
    public static double getAngleDiameter(final double rho) {
        return theta0 / rho;
    }

    /**
     * Calculates ecliptical coordinates of the Sun
     * @param se Solar elements
     * @return Ecliptical coordinates of the Sun
     */
    public static CrdsEcliptical getEclipticalCoordinates(final SolarElements se) {
        final CrdsEcliptical ecl = new CrdsEcliptical();
        ecl.lambda = se.v + omegaG;
        ecl.lambda = Math2.to360(ecl.lambda);
        ecl.beta = 0;
        return ecl;
    }

    /**
     * Calculates rectangular coordinates of the Sun
     * @param theta Ecliptical longitude
     * @param epsilon Earth's orbit obliquity
     * @param R Distance between Sun and Earth, in a.u.
     * @return Rectangular coordinates of the Sun
     */
    public static CrdsRectangular getRectangularCoordinates(
            final double theta, 
            final double epsilon,
            final double R)
    {
        CrdsRectangular r = new CrdsRectangular();
        r.x = R * Math.cos(Math.toRadians(theta));
        r.y = R * Math.sin(Math.toRadians(theta)) * Math.cos(Math.toRadians(epsilon));
        r.z = R * Math.sin(Math.toRadians(theta)) * Math.sin(Math.toRadians(epsilon));
        return r;
    }

    /**
     * Calculates solar elements for specified date
     * @param jd Julian day
     * @return Solar elements for specified date
     */
    public static SolarElements getSolarElements(final double jd) 
    {
        final SolarElements se = new SolarElements();
        final double D = jd - AstroUtils.epoch1980;

        se.M = 360.0 / 365.2422 * D + epsilonG - omegaG;
        se.M = Math2.to360(se.M);

        se.E = KeplerEquation.solve(se.M, e);

        se.v = 2 * Math.toDegrees(Math2.atan(Math.sqrt((1 + e) / (1 - e))
                        * Math.tan(Math.toRadians(se.E / 2.0))));

        se.r = (1 - e * e) / (1 + e * Math.cos(Math.toRadians(se.v)));

        return se;
    }

    /**
     * Calculates current Carrington rotation number of the Sun
     * @param jd JUlian day
     * @return Carrington rotation number for specified date
     */
    public static long carrington(double jd)
    {
        return (long)(1690 + (jd - 2444235.43) / 27.2753);
    }

    /**
     * Calcaulates day length by Sun rise and set values
     * @param rise Sun ruse, julian day instant
     * @param set Sun set, julian day instant
     * @return day length as decimal value (1.0 = 24h) 
     */
    public static double getDayLength(double rise, double set)
    {
      return 0.5 + (set - rise);
    }

    /**
     * Calculates instant of beginning of astronomical season nearest to specified date.
     * @param jd Julian day
     * @param season Season constant (SolarCalculator.SPRING, ...SUMMER, ...AUTUMN, ...WINTER)
     * @return Julian day value as an instant of beginning of astronomical season
     */
    public static double getSeason(double jd, int season)
    {

      int year = AstroUtils.getDatePart(jd, AstroUtils.DATEPART_YEAR);
      // AA, p. 165-166
      double Y = 0;
      double JDE0 = 0;

      //Y = (double)((int)((jd - 2451545.0) / 365.25) - 2000) / 1000.0;
      Y = (double)(year - 2000) / 1000.0;
      //March equinox  (beginning of astronomical spring) :
      if (season == SPRING) JDE0 = 2451623.80984 + 365242.37404 * Y + 0.05169 * Y * Y - 0.00411 * Y * Y * Y - 0.00057 * Y * Y * Y * Y;
      //June solstice  (beginning of astronomical summer) :
      if (season == SUMMER) JDE0 = 2451716.56767 + 365241.62603 * Y + 0.00325 * Y * Y + 0.00888 * Y * Y * Y - 0.00030 * Y * Y * Y * Y;
      //September equinox (beginning of astronomical autumn) :
      if (season == AUTUMN) JDE0 = 2451810.21715 + 365242.01767 * Y - 0.11575 * Y * Y + 0.00337 * Y * Y * Y + 0.00078 * Y * Y * Y * Y;
      //December solstice (beginning of astronomical winter) :
      if (season == WINTER) JDE0 = 2451900.05952 + 365242.74049 * Y - 0.06223 * Y * Y - 0.00823 * Y * Y * Y + 0.00032 * Y * Y * Y * Y;

      double T = (JDE0 - 2451545.0) / 36525.0;
      double W = 35999.373 * T - 2.47;
      double delta_lambda = 1 + 0.0334 * Math.cos(Math.toRadians(W)) + 0.0007 * Math.cos(Math.toRadians(2 * W));

      double S =
            485 * Math.cos(Math.toRadians(324.96 + 1934.136 * T)) +
            203 * Math.cos(Math.toRadians(337.23 + 32964.467 * T)) +
            199 * Math.cos(Math.toRadians(342.08 + 20.186 * T)) +
            182 * Math.cos(Math.toRadians(27.85 + 445267.112 * T)) +
            156 * Math.cos(Math.toRadians(73.14 + 45036.886 * T)) +
            136 * Math.cos(Math.toRadians(171.52 + 22518.443 * T)) +
            77 * Math.cos(Math.toRadians(222.54 + 65928.934 * T)) +
            74 * Math.cos(Math.toRadians(296.72 + 3034.906 * T)) +
            70 * Math.cos(Math.toRadians(243.58 + 9037.513 * T)) +
            58 * Math.cos(Math.toRadians(119.81 + 33718.147 * T)) +
            52 * Math.cos(Math.toRadians(297.17 + 150.678 * T)) +
            50 * Math.cos(Math.toRadians(21.02 + 2281.226 * T)) +
            45 * Math.cos(Math.toRadians(247.54 + 29929.562 * T)) +
            44 * Math.cos(Math.toRadians(325.15 + 31555.956 * T)) +
            29 * Math.cos(Math.toRadians(60.93 + 4443.417 * T)) +
            18 * Math.cos(Math.toRadians(155.12 + 67555.328 * T)) +
            17 * Math.cos(Math.toRadians(288.79 + 4562.452 * T)) +
            16 * Math.cos(Math.toRadians(198.04 + 62894.029 * T)) +
            14 * Math.cos(Math.toRadians(199.76 + 31436.921 * T)) +
            12 * Math.cos(Math.toRadians(95.39 + 14577.848 * T)) +
            12 * Math.cos(Math.toRadians(287.11 + 31931.756 * T)) +
            12 * Math.cos(Math.toRadians(320.81 + 34777.259 * T)) +
            9 * Math.cos(Math.toRadians(227.73 + 1222.114 * T)) +
            8 * Math.cos(Math.toRadians(15.45 + 16859.074 * T));

        double JDE = JDE0 + (0.00001 * S) / delta_lambda;
        return JDE;
    }
}
