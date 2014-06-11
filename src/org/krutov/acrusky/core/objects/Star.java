/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;

/** Star */
public class Star {
  /** Equatorial coordinates */
  public CrdsEquatorial equatorial = new CrdsEquatorial();
  /** Horizontal coordinates */
  public CrdsHorizontal horizontal = new CrdsHorizontal();
  /** Visible magnitude */
  public float mag;
  /** Displayable name */
  public String name;
  /** Proper name */
  public String properName;
  /** Spectral class */
  public String spectralClass;
}
