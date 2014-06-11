/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.ephem;

public class AstroEvent {
    public double jd;
    public String text;

    public AstroEvent(double jd, String text) {
      this.jd = jd;
      this.text = text;
    }
}