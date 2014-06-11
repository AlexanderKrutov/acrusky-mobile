/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;

/**
 * Represents constellation label point
 */
public class Constellation {
  /** Name of constallation */
  public String name;
  /** Equatorial coordinates of label point */
  public CrdsEquatorial equatorial = new CrdsEquatorial();
  /** Horizontal coordinates of label point */
  public CrdsHorizontal horizontal = new CrdsHorizontal();
  
  public Constellation() {
  }
}
