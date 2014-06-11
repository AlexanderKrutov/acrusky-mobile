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
 * Represents deep sky object (galaxy, nebula, cluster and etc.)
 * @author krutov
 */
public class DeepSky {
    /** Equatorial coordinates */
    public CrdsEquatorial equatorial = new CrdsEquatorial();
    /** Horizontal coordinates */
    public CrdsHorizontal horizontal = new CrdsHorizontal();
    /** Visible magnitude */
    public float magnitude;
    /** Deep sky object type (@see Sky.getDeepSkyType()) */
    public byte type;
    /** Name of deep sky object */
    public String name;
    
    public DeepSky() {
    }
}
