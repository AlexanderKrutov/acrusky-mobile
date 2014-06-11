/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.core.objects;

import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.ephem.MoonElements;
import org.krutov.acrusky.core.coords.CrdsEcliptical;
import org.krutov.acrusky.core.coords.CrdsEquatorial;

/** Moon */
public class Moon {
    /** Equatorial coordinates */
    public CrdsEquatorial equatorial = new CrdsEquatorial();
    /** Ecliptical coordinates */
    public CrdsEcliptical ecliptical = new CrdsEcliptical();
    /** Horizontal coordinates */
    public CrdsHorizontal horizontal = new CrdsHorizontal();
    /** Equatorial coordinates of Earth umbra (used for lunar eclipses) */
    public CrdsEquatorial shadowEquatorial = new CrdsEquatorial();
    /** Horizontal coordinates of Earth umbra (used for lunar eclipses) */
    public CrdsHorizontal shadowHorisontal = new CrdsHorizontal();
    /** Orbital elements for lunar orbit */
    public MoonElements moonElements = new MoonElements();
    /** Distance from Earth */
    public double distanceFromEarth;
    /** Visible angular diameter */
    public double angleDiameter;
    /** Current phase */
    public double phase;
    /** Current age, in days */
    public double age;
    /** Position angle */
    public double positionAngle;
    /** Parallactica angle */
    public double parallacticAngle;
    /** Visible Earth umbra size, in fractions of Moon diameter */
    public double umbra;
    /** Visible Earth penumbra size, in fractions of Moon diameter */
    public double penumbra;
    /** Displayable name */
    public String name;
}
