/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.core.Math2;

/**
 * Used to solve eclipse details
 */
public class EclipseCalculator
{
  /**
   * Gets next or previous eclipse info nearest to the reference julian day
   * @param jd julian day
   * @param eclipseType type of eclipse: Eclipse.SOLAR or Eclipse.LUNAR
   * @param next true to get next eclipse, false to get previous
   */
  public static Eclipse getEclipse(double jd, int eclipseType, boolean next)
  {
    Eclipse e = new Eclipse();
    
    double year = AstroUtils.julianDayToYear(jd);
    double k, T, TT, TTT, F, S, C, M, M_, P, Tau, n;
    boolean eclipseFound = false;
  
    // AFFC, p. 32, f. 32.2
    k = (year - 1900) * 12.3685;
    
    if (next)
      k = Math.floor(k) + eclipseType * 0.5;
    else
      k = Math.floor(k) - eclipseType * 0.5;
    
    do
    {
      // AFFC, p. 128, f. 32.3
      T = k / 1236.85;
      TT = T * T;
      TTT = T * TT;

      // Moon's argument of latitude 
      // AFFC, p. 129
      F = 21.2964 + 390.67050646 * k
                          - 0.0016528 * TT
                          - 0.00000239 * TTT;

      F = Math.toRadians(Math2.to360(F));
      
      // AFFC, p. 132
      eclipseFound = Math.abs(Math.sin(F)) < 0.36; 

      // no eclipse exactly, examine other lunation
      if (!eclipseFound) 
      {
        if (next) k++;
        else k--;
        continue;
      }
      
      // BOTH ECLIPSE TYPES (SOLAR & LUNAR)
      
      // mean anomaly of the Sun
      // AFFC, p. 129
      M = 359.2242 + 29.10535608 * k
                          - 0.0000333 * TT
                          - 0.00000347 * TTT;
      M = Math.toRadians(Math2.to360(M));

      // mean anomaly of the Moon
      // AFFC, p. 129
      M_ = 306.0253 + 385.81691806 * k
                           + 0.0107306 * TT
                           + 0.00001236 * TTT;
      M_ = Math.toRadians(Math2.to360(M_));

      // time of mean phase
      // AFFC, p. 128, f. 32.1
      e.jd =  2415020.75933 + 29.53058868 * k
                             + 0.0001178 * TT
                             - 0.000000155 * TTT
                             + 0.00033 * 
               Math.sin(Math.toRadians(166.56 + 132.87 * T - 0.009173 * TT));

      // time of maximum eclipse
      // AFFC, p. 132-133, f. 33.1
      e.jd +=
         + (0.1734 - 0.000393 * T) * Math.sin(M)
         + 0.0021 * Math.sin(M + M)
         - 0.4068 * Math.sin(M_)
         + 0.0161 * Math.sin(M_ + M_)
         - 0.0051 * Math.sin(M + M_)
         - 0.0074 * Math.sin(M - M_)
         - 0.0104 * Math.sin(F + F);

      // AFFC, p. 133
      S = 
         5.19595
         - 0.0048 * Math.cos(M)
         + 0.0020 * Math.cos(M + M)
         - 0.3283 * Math.cos(M_)
         - 0.0060 * Math.cos(M + M_)
         + 0.0041 * Math.cos(M - M_);

      C = 
         + 0.2070 * Math.sin(M)
         + 0.0024 * Math.sin(M + M)
         - 0.0390 * Math.sin(M_)
         + 0.0115 * Math.sin(M_ + M_)
         - 0.0073 * Math.sin(M + M_)
         - 0.0067 * Math.sin(M - M_)
         + 0.0117 * Math.sin(F + F);

      e.gamma = 
         S * Math.sin(F) + C * Math.cos(F);

      e.u = 
         0.0059 
         + 0.0046 * Math.cos(M)
         - 0.0182 * Math.cos(M_)
         + 0.0004 * Math.cos(M_ + M_)
         - 0.0005 * Math.cos(M + M_);
      
      // SOLAR ECLIPSE
      if (eclipseType == Eclipse.SOLAR)
      {
        // eclipse is not observable from the Earth
        if (Math.abs(e.gamma) > 1.5432 + e.u)
        {
          eclipseFound = false;
          if (next) k++;
          else k--;
          continue;
        }
       
        // AFFC, p. 134
        // non-central eclipse
        if (Math.abs(e.gamma) > 0.9972 && 
            Math.abs(e.gamma) < 0.9972 + Math.abs(e.u))
        {
          e.type = Eclipse.SOLAR_NONCENTRAL;
          e.phase = 1;
        }
        // central eclipse
        else
        {
          e.phase = 1;
          if (e.u < 0) e.type = Eclipse.SOLAR_CENTRAL_TOTAL;
          if (e.u > 0.0047) e.type = Eclipse.SOLAR_CENTRAL_ANNULAR;
          if (e.u > 0 && e.u < 0.0047)
          {
            C = 0.00464 * Math.sqrt(1 - e.gamma * e.gamma);
            if (e.u < C) e.type = Eclipse.SOLAR_CENTRAL_ANNULAR_TOTAL;
            else e.type = Eclipse.SOLAR_CENTRAL_ANNULAR;
          }
        }
        
        // partial eclipse
        if (Math.abs(e.gamma) > 0.9972 && 
            Math.abs(e.gamma) < 1.5432 + e.u)
        {
          e.type = Eclipse.SOLAR_PARTIAL;
          e.phase = (1.5432 + e.u - Math.abs(e.gamma)) / (0.5461 + e.u + e.u);
        }        
      }
      // LUNAR ECLIPSE
      else
      {
        e.rho = 1.2847 + e.u;
        e.sigma = 0.7494 - e.u;

        // Phase for umbral eclipse
        // AFFC, p. 135, f. 33.4
        e.phase = (1.0129 - e.u - Math.abs(e.gamma)) / 0.5450;
        
        if (e.phase >= 1)
        {
          e.type = Eclipse.LUNAR_UMBRAL_TOTAL;
        }
        if (e.phase > 0 && e.phase < 1)
        {
          e.type = Eclipse.LUNAR_UMBRAL_PARTIAL;
        }
        
        // Check if elipse is penumral only
        if (e.phase < 0)
        {
          // AFC, p. 135, f. 33.3
          e.type = Eclipse.LUNAR_PENUMBRAL;
          e.phase = (1.5572 + e.u - Math.abs(e.gamma)) / 0.5450;
        }

        // no eclipse, if both phases is less than 0,
        // then examine other lunation
        if (e.phase < 0)
        {
          eclipseFound = false;
          if (next) k++;
          else k--;
          continue;
        }
        
        // eclipse was found, calculate remaining details
        // AFFC, p. 135
        P = 1.0129 - e.u;
        Tau = 0.4679 - e.u; 
        n = 0.5458 + 0.0400 * Math.cos(M_);
        
        // semiduration in penumbra
        C = e.u + 1.5573;
        e.sdPenumbra = 
                60.0 / n * Math.sqrt(C * C - e.gamma * e.gamma);             

        // semiduration of partial phase
        e.sdPartial = 
                60.0 / n * Math.sqrt(P * P - e.gamma * e.gamma);        

        // semiduration of total phase
        e.sdTotal = 
                60.0 / n * Math.sqrt(Tau * Tau - e.gamma * e.gamma); 
      }
    }
    while (!eclipseFound);
    return e;
  }
}
