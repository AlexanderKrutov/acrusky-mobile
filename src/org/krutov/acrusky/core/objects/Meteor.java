/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;

/** Meteor shower radiant */
public class Meteor {
  /** Equatorial coordinates of radiant */
  public CrdsEquatorial equatorial = new CrdsEquatorial();
  /** Horizontal coordinates of radiant */
  public CrdsHorizontal horizontal = new CrdsHorizontal();  
  
  public Meteor() {
  } 
}
