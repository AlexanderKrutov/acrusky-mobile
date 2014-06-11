/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsEcliptical;
import org.krutov.acrusky.core.coords.CrdsEquatorial;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.coords.CrdsRectangular;
import org.krutov.acrusky.core.ephem.SolarElements;

/** Sun */
public class Sun {
    /** Elements describing current Earth position */
    public SolarElements solarElements = new SolarElements();
    /** Equatorial coordinates */
    public CrdsEquatorial equatorial = new CrdsEquatorial();
    /** Ecliptical coordinates */
    public CrdsEcliptical ecliptical = new CrdsEcliptical();    
    /** Horizontal coordinates */
    public CrdsHorizontal horizontal = new CrdsHorizontal();
    /** Rectangular coordinates */
    public CrdsRectangular rectangular = new CrdsRectangular();
    /** Distance from Earth, in a.u. */
    public double distanceFromEarth;
    /** Visible angular diameter */
    public double angleDiameter;
    /** Displayable name */
    public String name;
}
