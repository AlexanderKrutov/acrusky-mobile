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
import org.krutov.acrusky.core.coords.CrdsGeographical;

/**
 * Used for calculation Moon ephemerides
 */
public class MoonCalculator {
    // constants describing lunar orbit
    public static double l0 = 64.975464;
    public static double P0 = 349.383063;
    public static double N0 = 151.950429;
    public static double i = 5.145396;
    public static double e = 0.054900;
    public static double theta0 = 0.5181;
    public static double a = 384401;
    public static double pi = 0.9507;

    // lunar phases 
    public static final int PHASE_NM = 0;
    public static final int PHASE_FQ = 25;
    public static final int PHASE_FM = 50;
    public static final int PHASE_LQ = 75;

    /**
     * Calculates Moon's angle diameter
     * @param rho Distance from Earth
     * @return Angle diameter
     */
    public static double getAngleDiameter(final double rho) {
        final double rho_ = rho / a;
        return theta0 / rho_;
    }

    /**
     * Calculates Moon's distance from Earth
     * @param M_
     * @param Ec
     * @return Distance from Earth
     */
    public static double getDistanceFromEarth(final double M_, final double Ec) {
        return (a * (1 - e * e)) / (1 + e * Math.cos(Math.toRadians(M_ + Ec)));
    }

    /**
     * Calculates ecliptical coordinates of the Moon
     * @param me Moon elements
     * @return Ecliptical coordinates of the Moon
     */
    public static CrdsEcliptical getEclipticCoordinates(final MoonElements me) {
        final CrdsEcliptical ecl = new CrdsEcliptical();

        final double l__N_ = Math.toRadians(me.l__ - me.N_);

        final double y = Math.sin(l__N_) * Math.cos(Math.toRadians(i));

        final double x = Math.cos(l__N_);

        final double lambda = Math2.to360(Math.toDegrees(Math2.atan2(y, x))
                        + me.N_);

        final double beta = Math.toDegrees(Math2.asin(Math.sin(l__N_)
                        * Math.sin(Math.toRadians(i))));

        ecl.lambda = lambda;
        ecl.beta = beta;

        return ecl;
    }

    /**
     * Calculates Moon elements for specified date
     * @param jd Julian day
     * @param sunLambda
     * @param sunM
     * @return 
     */
    public static MoonElements getMoonElements(final double jd,
                                               final double sunLambda, 
                                               final double sunM) {
        final MoonElements me = new MoonElements();

        final double D = jd - AstroUtils.epoch1980;

        double l = 13.176396 * D + l0;
        l = Math2.to360(l);

        double M = l - 0.1114041 * D - P0;
        M = Math2.to360(M);

        double N = N0 - 0.0529539 * D;
        N = Math2.to360(N);

        final double C = l - sunLambda;

        final double Ev = 1.2739 * Math.sin(Math.toRadians(2 * C - M));

        final double sinSunM = Math.sin(Math.toRadians(sunM));

        final double Ae = 0.1858 * sinSunM;

        final double A3 = 0.37 * sinSunM;

        final double M_ = M + Ev - Ae - A3;

        final double Ec = 6.2886 * Math.sin(Math.toRadians(M_));

        final double A4 = 0.214 * Math.sin(Math.toRadians(2 * M_));

        final double l_ = l + Ev + Ec - Ae + A4;

        final double V = 0.6583 * Math
                        .sin(Math.toRadians(2 * (l_ - sunLambda)));

        final double l__ = l_ + V;

        final double N_ = N - 0.16 * Math.sin(Math.toRadians(sunM));

        me.M_ = M_;
        me.l__ = l__;
        me.N_ = N_;
        me.Ec = Ec;

        return me;
    }

    /**
     * Calculates Moon phase
     * @param age Moon age in days
     */
    public static double getPhase(double age) {
        return 0.5 * (1 - Math.cos(Math.toRadians(age)));
    }

    /**
     * Calculates Moon magnitude
     * @param phaseAngle Moon's phase angle
     * @return Magnitude of Moon
     */
    public static float getMagnitude(double phaseAngle)
    {
        // Method from http://en.wikipedia.org/wiki/Absolute_magnitude
        // Phase integral:
        if (phaseAngle > 180) phaseAngle -= 180;

        double p = 2.0 / 3.0 * ((1 - Math.toRadians(phaseAngle) / Math.PI) * Math.cos(Math.toRadians(phaseAngle)) + 1.0 / Math.PI * Math.sin(Math.toRadians(phaseAngle)));

        return (float)(0.25 + 2.5 * Math2.log10(0.00257 * 0.00257 / p));
    }

    /**
     * Calculates equatorial topocentric (related to place of observation) coordinates
     * @param geocentric Geocentric equatorial coordinates
     * @param location Place of observation
     * @param distance Distance from Earth
     * @param siderealTime Siderial time at the place of observation
     * @return Equatorial coordinates related to place of observation
     */
    public static CrdsEquatorial getTopocentricCoordinates(
                                            final CrdsEquatorial geocentric, 
                                            final CrdsGeographical location,
                                            final double distance, 
                                            final double siderealTime) {
        final CrdsEquatorial eq = new CrdsEquatorial();

        final double u = Math2.atan(0.99664719 * Math.tan(Math
                        .toRadians((location.latitude))));
        final double rhoSinPhi = 0.99664719 * Math.sin(u)
                        + (location.elevation / 6378140.0)
                        * Math.sin(Math.toRadians(location.latitude));
        final double rhoCosPhi = Math.cos(u) + (location.elevation / 6378140.0)
                        * Math.cos(Math.toRadians(location.latitude));

        final double H = AstroUtils.hourAngle(siderealTime, location.longitude,
                        geocentric.RA);

        final double r = distance / 6378.14;

        final double D = Math.toDegrees(Math2.atan((rhoCosPhi * Math.sin(Math
                        .toRadians(H)))
                        / (r * Math.cos(Math.toRadians(geocentric.Dec)) - rhoCosPhi
                                        * Math.cos(Math.toRadians(H)))));

        final double H_ = H + D;

        eq.RA = (float)(geocentric.RA - D);

        eq.Dec = (float)Math.toDegrees(Math2.atan(Math.cos(Math.toRadians(H_))
                        * (r * Math.sin(Math.toRadians(geocentric.Dec)) - rhoSinPhi)
                        / (r * Math.cos(Math.toRadians(geocentric.Dec))
                                        * Math.cos(Math.toRadians(H)) - rhoCosPhi)));

        eq.RA = (float)Math2.to360(eq.RA);

        return eq;
    }

    /**
     * Calculates nearest instant lunar phase
     * @param jd Julian day
     * @param phase Phase to find (MoonCalculator.PHASE_NM, ...FQ, ...FM, ...LQ)
     * @return Julian day of nearest phase
     */
    public static double getNearestPhase(double jd, int phase)
    {
        // AA, p. 320:
        double k = Math.floor((jd - 2451545.0) / 365.25 * 12.3685);

        k += ((double)(phase)) / 100.0;

        double T = k / (1236.85);

        double TT = T * T;
        double TTT = TT * T;
        double TTTT = TTT * T;

        double JDEphase = 2451550.09765 + 29.530588853 * k
                                        + 0.0001337 * TT
                                        - 0.000000150 * TTT
                                        + 0.00000000073 * TTTT;

        double M = 2.5534 + 29.10535669 * k
                          - 0.0000218 * TT
                          - 0.00000011 * TTT;

        double M_ = 201.5643 + 385.81693528 * k
                             + 0.0107438 * TT
                             + 0.00001239 * TTT
                             - 0.000000058 * TTTT;

        double F = 160.7108 + 390.67050274 * k
                            - 0.0016341 * TT
                            - 0.00000227 * TTT
                            + 0.000000011 * TTTT;

        double Omega = 124.7746 - 1.56375580 * k
                                + 0.0020691 * TT
                                + 0.00000215 * TTT;

        M = Math.toRadians(M);
        M_ = Math.toRadians(M_);
        F = Math.toRadians(F);
        Omega = Math.toRadians(Omega);

        double A1 = Math.toRadians(299.77 + 0.107408 * k - 0.009173 * TT);
        double A2 = Math.toRadians(251.88 + 0.016321 * k);
        double A3 = Math.toRadians(251.83 + 26.651866 * k);
        double A4 = Math.toRadians(349.42 + 36.412478 *k);
        double A5 = Math.toRadians(84.66 + 18.206239 * k);
        double A6 = Math.toRadians(141.74 + 53.303771 * k);
        double A7 = Math.toRadians(207.14 + 2.453732 * k);
        double A8 = Math.toRadians(154.84 + 7.306860 * k);
        double A9 = Math.toRadians(34.52 + 27.261239 * k);
        double A10 = Math.toRadians(207.19 + 0.121824 * k);
        double A11 = Math.toRadians(291.34 + 1.844379 * k);
        double A12 = Math.toRadians(161.72 + 24.198154 * k);
        double A13 = Math.toRadians(239.56 + 25.513099 * k);
        double A14 = Math.toRadians(331.55 + 3.592518 * k);

        double E = 1 - 0.002516 * T - 0.0000074 * TT;

        double addition = 0;

        if (phase == PHASE_NM)
        {
            addition =
                -0.40720 * Math.sin(M_)
                + 0.17241 * E * Math.sin(M)
                + 0.01608 * Math.sin(2 * M_)
                + 0.01039 * Math.sin(2 * F)
                + 0.00739 * E * Math.sin(M_ - M)
                - 0.00514 * E * Math.sin(M_ + M)
                + 0.00208 * E * E * Math.sin(2 * M)
                - 0.00111 * Math.sin(M_ - 2 * F)
                - 0.00057 * Math.sin(M_ + 2 * F)
                + 0.00056 * E * Math.sin(2 * M_ + M)
                - 0.00042 * Math.sin(3 * M_)
                + 0.00042 * E * Math.sin(M + 2 * F)
                + 0.00038 * E * Math.sin(M - 2 * F)
                - 0.00024 * E * Math.sin(2 * M_ - M)
                - 0.00017 * Math.sin(Omega)
                - 0.00007 * Math.sin(M_ + 2 * M)
                + 0.00004 * Math.sin(2 * M_ - 2 * F)
                + 0.00004 * Math.sin(3 * M)
                + 0.00003 * Math.sin(M_ + M - 2 * F)
                + 0.00003 * Math.sin(2 * M_ + 2 * F)
                - 0.00003 * Math.sin(M_ + M + 2 * F)
                + 0.00003 * Math.sin(M_ - M + 2 * F)
                - 0.00002 * Math.sin(M_ - M - 2 * F)
                - 0.00002 * Math.sin(3 * M_ + M)
                + 0.00002 * Math.sin(4 * M_);
        }

        if (phase == PHASE_FM)
        {
            addition =
                -0.40614 * Math.sin(M_)
                + 0.17302 * E * Math.sin(M)
                + 0.01614 * Math.sin(2 * M_)
                + 0.01043 * Math.sin(2 * F)
                + 0.00734 * E * Math.sin(M_ - M)
                - 0.00515 * E * Math.sin(M_ + M)
                + 0.00209 * E * E * Math.sin(2 * M)
                - 0.00111 * Math.sin(M_ - 2 * F)
                - 0.00057 * Math.sin(M_ + 2 * F)
                + 0.00056 * E * Math.sin(2 * M_ + M)
                - 0.00042 * Math.sin(3 * M_)
                + 0.00042 * E * Math.sin(M + 2 * F)
                + 0.00038 * E * Math.sin(M - 2 * F)
                - 0.00024 * E * Math.sin(2 * M_ - M)
                - 0.00017 * Math.sin(Omega)
                - 0.00007 * Math.sin(M_ + 2 * M)
                + 0.00004 * Math.sin(2 * M_ - 2 * F)
                + 0.00004 * Math.sin(3 * M)
                + 0.00003 * Math.sin(M_ + M - 2 * F)
                + 0.00003 * Math.sin(2 * M_ + 2 * F)
                - 0.00003 * Math.sin(M_ + M + 2 * F)
                + 0.00003 * Math.sin(M_ - M + 2 * F)
                - 0.00002 * Math.sin(M_ - M - 2 * F)
                - 0.00002 * Math.sin(3 * M_ + M)
                + 0.00002 * Math.sin(4 * M_);
        }

        if (phase == PHASE_FQ || phase == PHASE_LQ)
        {
            addition =
                -0.62801 * Math.sin(M_)
                + 0.17172 * E * Math.sin(M)
                - 0.01183 * E * Math.sin(M_ + M)
                + 0.00862 * Math.sin(2 * M_)
                + 0.00804 * Math.sin(2 * F)
                + 0.00454 * E * Math.sin(M_ - M)
                + 0.00204 * E * E * Math.sin(2 * M)
                - 0.00180 * Math.sin(M_ - 2 * F)
                - 0.00070 * Math.sin(M_ + 2 * F)
                - 0.00040 * Math.sin(3 * M_)
                - 0.00034 * E * Math.sin(2 * M_ - M)
                + 0.00032 * E * Math.sin(M + 2 * F)
                + 0.00032 * E * Math.sin(M - 2 * F)
                - 0.00028 * E * E * Math.sin(M_ + 2 * M)
                + 0.00027 * E * Math.sin(2 * M_ + M)
                - 0.00017 * Math.sin(Omega)
                - 0.00005 * Math.sin(M_ - M - 2 * F)
                + 0.00004 * Math.sin(2 * M_ + 2 * F)
                - 0.00004 * Math.sin(M_ + M + 2 * F)
                + 0.00004 * Math.sin(M_ - 2 * M)
                + 0.00003 * Math.sin(M_ + M - 2 * F)
                + 0.00003 * Math.sin(3 * M)
                + 0.00002 * Math.sin(2 * M_ - 2 * F)
                + 0.00002 * Math.sin(M_ - M + 2 * F)
                - 0.00002 * Math.sin(3 * M_ + M);

            double W = 0.00306 - 0.00038 * E * Math.cos(M) + 0.00026 * Math.cos(M_)
                - 0.00002 * Math.cos(M_ - M) + 0.00002 * Math.cos(M_ + M) + 0.00002 * Math.cos(2 * F);

            if (phase == PHASE_FQ) addition += W;
            if (phase == PHASE_LQ) addition -= W;
        }

        double correction =
              0.000325 * Math.sin(A1)
            + 0.000165 * Math.sin(A2)
            + 0.000164 * Math.sin(A3)
            + 0.000126 * Math.sin(A4)
            + 0.000110 * Math.sin(A5)
            + 0.000062 * Math.sin(A6)
            + 0.000060 * Math.sin(A7)
            + 0.000056 * Math.sin(A8)
            + 0.000047 * Math.sin(A9)
            + 0.000042 * Math.sin(A10)
            + 0.000040 * Math.sin(A11)
            + 0.000037 * Math.sin(A12)
            + 0.000035 * Math.sin(A13)
            + 0.000023 * Math.sin(A14);

        JDEphase += addition + correction;

        return JDEphase;
    }

    /**
     * Calulcates libration of Moon
     * @param jd Julian day
     * @param ecl Ecliptical coordinates of Moon
     * @return Ecliptical coordinates pair representing libration values in longitude and latitude
     */
    public static CrdsEcliptical getLibration(double jd, CrdsEcliptical ecl)
    {
        // AA, p. 342

        // l > 0: easterly libration in longitude
        // l < 0: westerely libration in longitude
        // b > 0: northerly libration in latitude
        // b < 0: southerly libration in latitude

        double T = (jd - 2451545.0) / 36525.0;
        double TT = T * T;
        double TTT = TT * T;
        double TTTT = TTT * T;

        double F = 93.2720993 + 483202.0175273 * T - 0.0034029 * TT - TTT / 3526000.0 + TTTT / 863310000.0;
        F = Math2.to360(F);

        double Omega = 125.0445550 - 1934.1361849 * T + 0.0020762 * TT + TTT / 467410.0 - TTTT / 60616000.0;
        Omega = Math2.to360(Omega);

        double I = 1.54242;
        double W = ecl.lambda - 0.0048 - Omega;
        W = Math2.to360(W);

        double A1 = Math.sin(Math.toRadians(W)) * Math.cos(Math.toRadians(ecl.beta)) * Math.cos(Math.toRadians(I)) - Math.sin(Math.toRadians(ecl.beta)) * Math.sin(Math.toRadians(I));
        double A2 = Math.cos(Math.toRadians(W)) * Math.cos(Math.toRadians(ecl.beta));

        double A = Math.toDegrees(Math2.atan2(A1, A2));
        CrdsEcliptical le = new CrdsEcliptical();
        le.lambda = Math2.to360(A - F);
        if (le.lambda > 180) le.lambda -= 360;            
        le.beta = Math.toDegrees(Math2.asin(-Math.sin(Math.toRadians(W)) * Math.cos(Math.toRadians(ecl.beta)) * Math.sin(Math.toRadians(I)) - Math.sin(Math.toRadians(ecl.beta)) * Math.cos(Math.toRadians(I))));

        return le;        
    }

    /**
     * Calculates visible size of Earth umbra on the sky
     * @param jd Julain day
     * @return Visible angular size of Earth umbra on the sky 
     */
    public static double getEarthShadowSize(double jd)
    {
        double T = (jd - 2451545.0) / 36525.0;
        double TT = T*T;
        double TTT = TT*T;
        double TTTT = TTT*T;

        double M = 357.5291092 + 35999.0502909 * T - 0.0001536 * TT + TTT / 24490000.0;
        double M_ = 134.9634114 + 477198.8676313 * T + 0.0089970 * TT + TTT / 69699.0 - TTTT / 14712000.0;
        double E = 1 - 0.002516 * T - 0.0000074 * TT;

        M = Math2.to360(M);
        M_ = Math2.to360(M_);
        M = Math.toRadians(M);
        M_ = Math.toRadians(M_);

        double u = 0.0059
            + 0.0046 * E * Math.cos(M)
            - 0.0182 * Math.cos(M_)
            + 0.0004 * Math.cos(2 * M_)
            - 0.0005 * Math.cos(M + M_);

        return u;
    }
}
