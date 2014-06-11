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
import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsRectangular;

/**
 * Contains routines for calculation planet ephemerides.
 */
public class PlanetCalculator {

    /** Period, tropical years */
    private static double T[] = { 0.24085, 0.61521, 1.00004, 1.88089, 11.86224,
                    29.45771, 84.01247, 164.79558, 250.9 };

    /** Longitude in epoch, degrees */
    private static double epsilon[] = { 231.2973, 355.73352, 98.833540,
                    126.30783, 146.966365, 165.322242, 228.0708551, 260.3578998,
                    209.439 };

    /** Longitude of perihelion, degrees */
    private static double omega[] = { 77.1442128, 131.2895792, 102.596403,
                    335.6908166, 14.0095493, 92.6653974, 172.7363288, 47.8672148,
                    222.972 };

    /** Eccentricity of orbit */
    private static double e[] = { 0.2056306, 0.0067826, 0.016718, 0.0933865,
                    0.0484658, 0.0556155, 0.0463232, 0.0090021, 0.25387 };

    /** Semimajor axis, a.u. */
    private static double a[] = { 0.3870986, 0.7233316, 1.000000, 1.5236883,
                    5.202561, 9.554747, 19.21814, 30.10957, 39.78459 };

    /** Inclination, degrees */
    private static double i[] = { 7.0043579, 3.394435, 0, 1.8498011, 1.3041819,
                    2.4893741, 0.7729895, 1.7716017, 17.137 };

    /** Longitude of ascending node, degrees */
    private static double OMEGA[] = { 48.0941733, 76.4997524, 0, 49.4032001,
                    100.2520175, 113.4888341, 73.8768642, 131.5606494, 109.941 };

    /** Angle diameter on 1 a.u., seconds of arc */
    private static double theta0[] = { 6.74, 16.92, 0, 9.36, 196.74, 165.60,
                    65.80, 62.20, 8.20 };

    /**
     * Calculates angular diameter of the planet
     * @param planet planet index (zero based)
     * @param rho Distance from Earth, in a.u.
     * @return Returns angular diameter of the planet
     */
    public static double getAngleDiameter(final int planet, final double rho) {
            return theta0[planet] / rho;
    }

    /**
     * Calculates distance from Earth
     * @param r Radius-vector of the planet (distance from Sun), in a.u.
     * @param R Radius-vector of Earth (distance from Sun), in a.u.
     * @param l Heliocentric longitude of the planet
     * @param L Heliocentric longitude of Earth
     * @return Distance from Earth, in a.u.
     */
    public static double getDistanceFromEarth(final double r, final double R,
                    final double l, final double L) {
            return Math.sqrt(R * R + r * r - 2 * R * r
                            * Math.cos(Math.toRadians(l - L)));
    }

    /**
     * Calculates ecliptical coordinates of the planet
     * @param earth Orbital elements of Earth for specified date
     * @param planet Orbital elements of the planet for specified date
     * @param isExternal true if planet is outer, flse for inner
     * @return Ecliptical coordinates of the planet
     */
    public static CrdsEcliptical getEclipticalCoordinates(
                    final PlanetElements earth, 
                    final PlanetElements planet,
                    final boolean isExternal) {

        double lambda = 0;
        double beta = 0;

        final double l_L = Math.toRadians(planet.l_ - earth.l);

        if (isExternal) {

                lambda = Math.toDegrees(Math2.atan((earth.r * Math.sin(l_L))
                                / (planet.r_ - earth.r * Math.cos(l_L))))
                                + planet.l_;

        } else {
                final double L_l_ = Math.toRadians(earth.l - planet.l_);

                lambda = 180
                                + earth.l
                                + Math.toDegrees(Math2.atan((planet.r_ * Math.sin(L_l_))
                                                / (earth.r - planet.r_ * Math.cos(L_l_))));

        }

        lambda = Math2.to360(lambda);

        final double psi = Math.toRadians(planet.psi);
        final double lambda_l_ = Math.toRadians(lambda - planet.l_);

        beta = Math.toDegrees(Math2.atan((planet.r_ * Math.tan(psi)
                        * Math.sin(lambda_l_) / (earth.r * Math.sin(l_L)))));

        final CrdsEcliptical ecl = new CrdsEcliptical();
        ecl.lambda = lambda;
        ecl.beta = beta;

        return ecl;
    }

    /**
     * Calculates magnitude of the planet
     * @param planet Planet index (zero-based)
     * @param rho distance from earth, in a.u.
     * @param r Radius-vector of the planet
     * @param il Phase angle of the planet
     * @return Magnitude of the planet
     */
    public static float getMagnitude(final int planet, final double rho,
                    final double r, final double il) {
        double mag = 0;

        final double f = 5 * Math2.log10(r * rho);

        switch (planet) {
            case 0:
                    mag = -0.42 + f + 0.0380 * il - 0.000273 * il * il + 0.000002 * il
                                    * il * il;
                    break;
            case 1:
                    mag = -4.40 + f + 0.0009 * il + 0.000239 * il * il - 0.00000065
                                    * il * il * il;
                    break;
            case 3:
                    mag = -1.52 + f + 0.016 * il;
                    break;
            case 4:
                    mag = -9.40 + f + 0.005 * il;
                    break;
            case 5:
                    mag = -8.88 + f;
                    break;
            case 6:
                    mag = -7.19 + f;
                    break;
            case 7:
                    mag = -6.87 + f;
                    break;
            case 8:
                    mag = -1.00 + f;
                    break;
            }
        mag = ((int) (mag * 100)) / 100.0;

        return (float)mag;
    }

    /**
     * Calculates planet's orbital elements for specified date
     * @param jd Julian day
     * @param planet planet index (zero-based)
     * @return Planet's orbital elements
     */
    public static PlanetElements getOrbitalElements(final double jd,
                    final int planet) {
            final PlanetElements oe = new PlanetElements();

            final double D = jd - AstroUtils.epoch1980;

            oe.M = 360.0 / 365.2422 * D / T[planet] + epsilon[planet]
                            - omega[planet];
            oe.M = Math2.to360(oe.M);

            oe.v = oe.M + 360.0 / Math.PI * e[planet]
                            * Math.sin(Math.toRadians(oe.M));
            oe.v = Math2.to360(oe.v);

            oe.l = oe.v + omega[planet];
            oe.l = Math2.to360(oe.l);

            oe.r = (a[planet] * (1 - e[planet] * e[planet]))
                            / (1 + e[planet] * Math.cos(Math.toRadians(oe.v)));

            // if planet is not Earth:
            if (planet != 2) {

                    final double angle_l_OMEGA = Math.toRadians(oe.l - OMEGA[planet]);
                    final double angle_i = Math.toRadians(i[planet]);

                    oe.psi = Math.toDegrees(Math2.asin(Math.sin(angle_l_OMEGA)
                                    * Math.sin(angle_i)));

                    final double y = Math.sin(angle_l_OMEGA) * Math.cos(angle_i);
                    final double x = Math.cos(angle_l_OMEGA);

                    oe.l_ = Math.toDegrees(Math2.atan2(y, x)) + OMEGA[planet];

                    oe.r_ = oe.r * Math.cos(Math.toRadians(oe.psi));
            }

            return oe;
    }

    /**
     * Calculates phase of the planset
     * @param lambda
     * @param l Planet's heliocentric longitude
     * @return planet's phase
     */
    public static double getPlanetPhase(double lambda, double l) 
    {
      final double d = Math.toRadians(lambda - l);
      if (Math.abs(l - lambda) > 180)
      {
        if (l < lambda)
          l += 360;
        else
          lambda += 360;
      }              
      return ((l > lambda) ? 1 : -1) * 0.5 * (1 + Math.cos(d));
    }

    /**
     * Calulaltes coordinates of 4 major Jupiter moons 
     * @param moons [out] Array of rectangular coordinates of Jupiter moons,
     * in coordinate system related to center of Jupiter
     * @param jd Julian day to calculate
     */
    public static void getJupiterMoons(CrdsRectangular[] moons, final double jd)
    {
        double d = jd - 2451545.0;
        double V = 172.74 + 0.00111588 * d;
        double M = 357.529 + 0.9856003 * d;
        double N = 20.020 + 0.0830853 * d + 0.329 * Math.sin(Math.toRadians(V));
        double J = 66.115 + 0.9025179 * d - 0.329 * Math.sin(Math.toRadians(V));
        double A = 1.915 * Math.sin(Math.toRadians(M)) + 0.020 * Math.sin(Math.toRadians(2 * M));
        double B = 5.555 * Math.sin(Math.toRadians(N)) + 0.168 * Math.sin(Math.toRadians(2 * N));
        double K = J + A - B;
        double R = 1.00014 - 0.01671 * Math.cos(Math.toRadians(M)) - 0.00014 * Math.cos(Math.toRadians(2 * M));
        double r = 5.20872 - 0.25208 * Math.cos(Math.toRadians(N)) - 0.00611 * Math.cos(Math.toRadians(2 * N));
        double Delta = Math.sqrt(r * r + R * R - 2 * r * R * Math.cos(Math.toRadians(K)));
        double psi = Math.toDegrees(Math2.asin((R / Delta) * Math.sin(Math.toRadians(K))));
        double lambda = 34.35 + 0.083091 * d + 0.329 * Math.sin(Math.toRadians(V)) + B;
        double Ds = 3.12 * Math.sin(Math.toRadians(lambda + 42.8));
        double De = Ds - 2.22 * Math.sin(Math.toRadians(psi)) * Math.cos(Math.toRadians(lambda + 22)) - 1.30 * ((r - Delta) / Delta) * Math.sin(Math.toRadians(lambda - 100.5));
        // AA, pp. 286-287
        double[] u = new double[4];
        u[0] = 163.8067 + 203.4058643 * (d - Delta / 173.0) + psi - B;
        u[1] = 358.4108 + 101.2916334 * (d - Delta / 173.0) + psi - B;
        u[2] = 5.7129 + 50.2345179 * (d - Delta / 173.0) + psi - B;
        u[3] = 224.8151 + 21.4879801 * (d - Delta / 173.0) + psi - B;
        double G = 331.18 + 50.310482 * (d - Delta / 173.0);
        double H = 87.40 + 21.569231 * (d - Delta / 173.0);
        double[] rs = new double[4];
        rs[0] = 5.9073 - 0.0244 * Math.cos(Math.toRadians(2 * (u[0] - u[1])));
        rs[1] = 9.3991 - 0.0882 * Math.cos(Math.toRadians(2 * (u[1] - u[2])));
        rs[2] = 14.9924 - 0.0216 * Math.cos(Math.toRadians(G));
        rs[3] = 26.3699 - 0.1935 * Math.cos(Math.toRadians(H));
        u[0] += 0.473 * Math.sin(Math.toRadians(2 * (u[0] - u[1])));
        u[1] += 1.065 * Math.sin(Math.toRadians(2 * (u[1] - u[2])));
        u[2] += 0.165 * Math.sin(Math.toRadians(G));
        u[3] += 0.841 * Math.sin(Math.toRadians(H));

        for (int i = 0; i < 4; i++)
        {
            u[i] = Math2.to360(u[i]);
            moons[i].x = rs[i] * Math.sin(Math.toRadians(u[i]));
            moons[i].y = -rs[i] * Math.cos(Math.toRadians(u[i])) * Math.sin(Math.toRadians(De));
            moons[i].z = Math.cos(Math.toRadians(u[i])) < 0 ? -1 : 1;
        }
    }

    /**
     * Calculates position of central meridian of Juputer
     * @param jd Julian day to calulate
     * @return Position of central meridian of Juputer
     */
    public static double getJupiterCentralMeridian(final double jd)
    {
        double jup_mean = (jd - 2455636.938) * 360.0 / 4332.89709;
        double eqn_center = 5.55 * Math.sin(Math.toRadians(jup_mean));
        double angle = (jd - 2451870.628) * 360.0 / 398.884 - eqn_center;
        double correction = 11 * Math.sin(Math.toRadians(angle)) + 
                            5 * Math.cos(Math.toRadians(angle)) - 
                            1.25 * Math.cos(Math.toRadians(jup_mean)) - 
                            eqn_center;
        return Math2.to360(181.62 + 870.1869147 * jd + correction);
    }

    /**
     * Calculate longitude of Great Rrd Spot of Jupiter
     * @param jd Julian day to calculate
     * @return Longitude of Great Rrd Spot of Jupiter
     */
    public static double getJupiterGRSLongitude(final double jd)
    {
       // NOTE: this code is empiric and based on observations. 
       // No high accuracy guaranteed.

       // GRS drift, degrees per day:
       double drift = 0.03836;
       // Longitude for 30 jun 2008 epoch:
       double longitude0 = 128;
       // Current longitude:
       double longitude = Math2.to360(longitude0 + drift * (jd - 2454647.0));
       return longitude;
    }

    /**
     * Calculates appearance of Saturn rings
     * @param rings [out] Rectangular coordinates representing apprarance of the rings 
     * @param jd Julian day to calculate
     * @param saturnEcl Ecliptical coordinates of Saturn
     * @param distance Distance from Earth
     */
    public static void getSaturnRings(CrdsRectangular rings, final double jd, CrdsEcliptical saturnEcl, double distance)
    {
        double T = (jd - 2451545.0) / 36525.0;
        double i = 28.075216 - 0.012998 * T + 0.000004 * T * T;
        double Omega = 169.508470 + 1.394681 * T + 0.000412 * T * T;
        double B = Math.toDegrees(Math2.asin(Math.sin(Math.toRadians(i)) * Math.cos(Math.toRadians(saturnEcl.beta)) * Math.sin(Math.toRadians(saturnEcl.lambda - Omega)) - Math.cos(Math.toRadians(i)) * Math.sin(Math.toRadians(saturnEcl.beta))));
        rings.x = 375.35 / distance;
        rings.y = rings.x * Math.sin(Math.abs(Math.toRadians(B)));
        rings.z = B;
    }

    /**
     * Calculates Mars appearance
     * @param jd Julian day to calculate
     * @param eqMars Equatorial coordinates of Mars
     * @param distance Distance from Mars
     * @return Appearance details
     */
    public static PlanetAppearance getMarsAppearance(final double jd, 
                                                     final CrdsEquatorial eqMars, 
                                                     final double distance)
    {
        // PEC, pp. 88-92

        PlanetAppearance a = new PlanetAppearance();

        double d = jd - 2451545.0;
        double T = d / 36525.0;

        // coordinates of the rotation axis 
        CrdsEquatorial eq0 = new CrdsEquatorial();
        eq0.RA = (float)Math2.to360(317.681 - 0.108 * T + 0.786 * T);
        eq0.Dec = (float)Math2.to360(52.886 - 0.061 * T + 0.413 * T);

        // take light time effect into account
        d -= 0.0057755183 * distance; 
        T = d / 36525.0;

        // position of the null meridian
        a.W = Math2.to360(176.655 + 350.8919830 * d + 0.620 * T);

        double sinD = - Math.sin(Math.toRadians(eq0.Dec)) *
                        Math.sin(Math.toRadians(eqMars.Dec)) 
                      - Math.cos(Math.toRadians(eq0.Dec)) * 
                        Math.cos(Math.toRadians(eqMars.Dec)) *
                        Math.cos(Math.toRadians(eq0.RA - eqMars.RA)); 

        // planetographic latitude of the Earth
        a.D = Math.toDegrees(Math2.asin(sinD));

        double cosD = Math.cos(Math.toRadians(a.D));

        double sinP = Math.cos(Math.toRadians(eq0.Dec)) *
                      Math.sin(Math.toRadians(eq0.RA - eqMars.RA)) / cosD; 

        double cosP = (  Math.sin(Math.toRadians(eq0.Dec)) *
                         Math.cos(Math.toRadians(eqMars.Dec))
                       - Math.cos(Math.toRadians(eq0.Dec)) *
                         Math.sin(Math.toRadians(eqMars.Dec)) *
                         Math.cos(Math.toRadians(eq0.RA - eqMars.RA))) / cosD;    

        // position angle of the axis
        a.P = Math2.to360(Math.toDegrees(Math2.atan2(sinP, cosP)));

        double sinK = ( - Math.cos(Math.toRadians(eq0.Dec)) *
                          Math.sin(Math.toRadians(eqMars.Dec)) 
                        + Math.sin(Math.toRadians(eq0.Dec)) *
                          Math.cos(Math.toRadians(eqMars.Dec)) *
                          Math.cos(Math.toRadians(eq0.RA - eqMars.RA))) / cosD;

        double cosK =  (Math.cos(Math.toRadians(eqMars.Dec)) *
                        Math.sin(Math.toRadians(eq0.RA - eqMars.RA))) / cosD;

        double K = Math.toDegrees(Math2.atan2(sinK, cosK));

        // planetographic longitude of the central meridian
        a.lambda = Math2.to360(Math2.sign(a.W) * (a.W - K));

        // rotation matrix
        a.R = AstroUtils.rotationMatrix(180-a.lambda, -a.P, -Math.abs(a.D));

        return a;
    }
}
