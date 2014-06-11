/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

import org.krutov.acrusky.core.coords.CrdsRectangular;
import org.krutov.acrusky.core.Math2;

/**
 * Used for calculating ephemeris for minor bodies (asteroids and comets)
 */
public class MinorBodyCalculator
{
  /**
   * Calculates elliptic orbital elements for minor body for specified date
   * @param oe0 Orbital elements related to specified epoch (oe.epoch)
   * @param jd Julian date
   * @return Returns orbital elements for specified date (jd)
   */
  public static OrbitalElements getEllipticOrbitalElements(final OrbitalElements oe0, final double jd)
  {
    final OrbitalElements oe = oe0;
    
    final double n = 0.9856076686 / (oe.a * Math.sqrt(oe.a));
    final double nd = jd - oe.epoch;
    final double M = oe.M + n * nd;

    oe.E = (float)KeplerEquation.solve(M, oe.e);
    oe.v = trueAnomaly(oe.e, oe.E);
    oe.v = (float)Math2.to360(oe.v);
    oe.r = (float)(oe.a * (1 - oe.e * Math.cos(Math.toRadians(oe.E))));

    return oe;
  }
  
  /**
   * Calculates parabolic orbital elements for minor body for specified date
   * @param oe0 Orbital elements related to specified epoch (oe.epoch)
   * @param jd Julian date
   * @return Returns orbital elements for specified date (jd)
   */
  public static OrbitalElements getParabolicOrbitalElements(final OrbitalElements oe0, final double jd)
  {
    OrbitalElements oe = oe0;
    final double t = (jd - oe.epoch); // time since perihelion   
    final double W = 0.03649116245 / Math2.pow(oe.q, 1.5) * t;
    int count = 0;
    double s = 0;
    double s0 = 1000;
    
    while (count < 50 && Math.abs(s - s0) > 1e-10)
    {
      s0 = s;
      s = (2 * s0*s0*s0 + W) / (3 * (s0*s0 + 1));
      count++;
    }
    oe.v = (float)Math.toDegrees(2 * Math2.atan(s));
    oe.r = (float)(oe.q * (1 + s*s));
    return oe;
  }
  
  /**
   * Calculates hyperbolic orbital elements for minor body for specified date
   * @param oe0 Orbital elements related to specified epoch (oe.epoch)
   * @param jd Julian date
   * @return Returns orbital elements for specified date (jd)
   */  
  public static OrbitalElements getHyperbolicOrbitalElements(final OrbitalElements oe0, final double jd)
  {
    // Method idea is taken from AAPlus class framework (http://www.naughter.com/aa.html)
    OrbitalElements oe = oe0;
    final double k = 0.01720209895;
    final double t = (jd - oe.epoch); // time since perihelion 
    final double third = 1./3.;
    final double a = 0.75 * t * k * Math.sqrt((1 + oe.e) / (oe.q * oe.q * oe.q));
    final double b = Math.sqrt(1 + a*a);
    final double W = Math2.pow(b + a, third) - Math2.pow(b - a, third);
    final double W2 = W*W;
    final double W4 = W2*W2;
    final double f = (1 - oe.e) / (1 + oe.e);
    final double a1 = (2.0/3) + (0.4) * W2;
    final double a2 = (7.0/5) + (33.0/35) * W2 + (37.0/175) * W4;
    final double a3 = W2 * ((432.0/175) + (956.0/1125) * W2 + (84.0/1575) * W4);
    final double C = W2 / (1 + W2);
    final double g = f * C * C;
    final double w = W * (1 + f*C * (a1 + a2*g + a3*g*g));
    final double w2 = w*w;
    
    oe.v = (float)Math.toDegrees(2 * Math2.atan(w));
    oe.r = (float)(oe.q * (1 + w2) / (1 + w2 * f));    
    
    return oe;
  }
  
  /**
   * Calculates rectangular coordinates by known orbital elements
   * @param oe Orbital elements of minor body
   * @param epsilon Earth's orbit obliquity
   * @return Rectangular coordinates 
   */
  public static CrdsRectangular getRectangularCoordinates(final OrbitalElements oe, final double epsilon)
  {
    CrdsRectangular r = new CrdsRectangular();
    
    double F, G, H, P, Q, R;
    double a, b, c, A, B, C;

    F = Math.cos(Math.toRadians(oe.Omega));
    G = Math.sin(Math.toRadians(oe.Omega)) * Math.cos(Math.toRadians(epsilon));
    H = Math.sin(Math.toRadians(oe.Omega)) * Math.sin(Math.toRadians(epsilon));

    P = -Math.sin(Math.toRadians(oe.Omega)) * Math.cos(Math.toRadians(oe.i));
    Q = Math.cos(Math.toRadians(oe.Omega)) * Math.cos(Math.toRadians(oe.i)) * Math.cos(Math.toRadians(epsilon)) - Math.sin(Math.toRadians(oe.i)) * Math.sin(Math.toRadians(epsilon));
    R = Math.cos(Math.toRadians(oe.Omega)) * Math.cos(Math.toRadians(oe.i)) * Math.sin(Math.toRadians(epsilon)) + Math.sin(Math.toRadians(oe.i)) * Math.cos(Math.toRadians(epsilon));

    a = Math.sqrt(F * F + P * P);
    b = Math.sqrt(G * G + Q * Q);
    c = Math.sqrt(H * H + R * R);

    A = Math.toDegrees(Math2.atan2(F, P));
    B = Math.toDegrees(Math2.atan2(G, Q));
    C = Math.toDegrees(Math2.atan2(H, R));

    r.x = oe.r * a * Math.sin(Math.toRadians(A + oe.omega + oe.v));
    r.y = oe.r * b * Math.sin(Math.toRadians(B + oe.omega + oe.v));
    r.z = oe.r * c * Math.sin(Math.toRadians(C + oe.omega + oe.v));
    
    return r;
  }

  /**
   * Calculates magnitude of asteroid
   * @param G Phase coefficient of asteroid
   * @param H Absolute magnitude of asteroid
   * @param beta Phase angle of asteroid
   * @param r Distance between asteroid and Sun (radius-vector), in a.u.
   * @param delta Distance from Earth, in a.u.
   * @return Magnitude of asteroid
   */
  public static float getAsteroidMagnitude(final float G,
                                        final float H,
                                        final double beta,
                                        final double r,
                                        final double delta)
  {
    if (beta > 120)
    {
      // AFFC, p. 104
      return (float)(H + 5 * Math.tan(r * delta) + G * beta);
    }
    
    double F1, F2;
    double m;

    F1 = Math2.exp(-3.33 * Math2.pow(Math.tan(Math.toRadians(beta / 2)), 0.63));
    F2 = Math2.exp(-1.87 * Math2.pow(Math.tan(Math.toRadians(beta / 2)), 1.22));
    m = H + 5 * Math2.log10(r * delta) - 2.5 * Math2.log10((1 - G) * F1 + G * F2);

    return (float)m;
  }
  
  /**
   * Calculates magnitude of comet
   * @param g Absolute magnitude of the comet
   * @param k Phase coefficient of the comet
   * @param r Distance between comet and Sun (radius-vector), in a.u.
   * @param delta Distance from Earth, in a.u.
   * @return Magnitude of comet
   */
  public static float getCometMagnitude(final float g,
                                        final float k,
                                        final double r,
                                        final double delta)
  {
    return (float)(g + 5 * Math2.log10(delta) + k * Math2.log10(r));
  }
  
  /**
   * Calculates true anomaly of minor body
   * @param e Eccentricity
   * @param E Eccentricitical anomaly (solution of Kepler's equation)
   * @return True anomaly value
   */
  private static float trueAnomaly(final double e, final double E)
  {            
    final double tan_v2 = Math.sqrt((1 + e) / (1 - e)) * Math.tan(Math.toRadians(E / 2.0));
    return (float)Math.toDegrees(Math2.atan(tan_v2) * 2);
  }  
  
  /**
   * Calculates appearance of comet
   * @param g Absolute magnitude of the comet
   * @param k Phase coefficient of the comet
   * @param r Distance between comet and Sun (radius-vector), in a.u.
   * @param delta Distance from Earth, in a.u.
   * @return Appearance of comet
   */
  public static CometAppearance getCometAppearance(final float g,
                                                   final float k,
                                                   final double r,
                                                   final double delta)
  {
    CometAppearance ca = new CometAppearance();
    double D, L; 
    final double AU = 149597870.691;
    final double mhelio = g + k * Math2.log10(r);
    final double log10Lo = -0.0075 * mhelio * mhelio - 0.19 * mhelio + 2.10;
    final double log10Do = -0.0033 * mhelio * mhelio - 0.07 * mhelio + 3.25;
    final double Lo = Math2.pow(10, log10Lo);
    final double Do = Math2.pow(10, log10Do);
    // tail length, in millions of kilometers
    L = Lo * (1 - Math2.pow(10, -4 * r)) * (1 - Math2.pow(10, -2 * r));
    // coma diameter, in thousands of kilometers
    D = Do * (1 - Math2.pow(10, -2 * r)) * (1 - Math2.pow(10, -r));
    // translate sizes to a.u.
    D = (D * 1e3) / AU;
    L = (L * 1e6) / AU;
    // coma diameter, in degrees
    ca.coma = (float)Math.toDegrees(2 * Math2.atan(D / (2 * delta)));
    // tail length, in a.u.
    ca.tail = (float)L;
    return ca;
  }
}
