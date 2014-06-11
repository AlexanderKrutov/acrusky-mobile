/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.coords;

/** Represents a pair of geographical coordinates */
public class CrdsGeographical {
  public String name;
  public double timeZone;
  public double latitude;
  public double longitude;
  public double elevation;

  public CrdsGeographical(String name, double longitude, double latitude, double timeZone)
  {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.timeZone = timeZone;
  }
}
