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
import org.krutov.acrusky.core.ephem.PlanetElements;

/** Major planet */
public class Planet {
    /** Equatorial coordinates */
    public CrdsEquatorial equatorial = new CrdsEquatorial();
    /** Ecliptical coordinates */
    public CrdsEcliptical ecliptical = new CrdsEcliptical();
    /** Horizontal coordinates */
    public CrdsHorizontal horizontal = new CrdsHorizontal();
    /** Orbital elements */
    public PlanetElements orbit = new PlanetElements();
    /** Current phase */
    public double phase;
    /** Visible magintude */
    public float magnitude;
    /** Phase angle */
    public double phaseAngle;
    /** Distance from Earth, in a.u.*/
    public double distanceFromEarth;
    /** Visible angular diameter */
    public double angleDiameter;
    /** Displayable name */
    public String name;
}
