/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.core.Math2;
import org.krutov.acrusky.core.Settings;
import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Sky;
import org.krutov.acrusky.core.coords.CrdsHorizontal;
import org.krutov.acrusky.core.objects.Asteroid;
import org.krutov.acrusky.core.objects.Comet;
import javax.microedition.lcdui.*;

public class SkyCanvas extends Canvas { 

    public int width;
    public int height;
    public double radius;
    public int centerX;
    public int centerY;
    
    private Display display;
    private KeyCodeAdapter adapter;
    
    private double scale;
    private double rotation;
    private double sinRotation;
    private double cosRotation;
    private double magLimit;
    private int starLimit;
        
    private int pointX;
    private int pointY;
    
    private int cursorX;
    private int cursorY;
    
    private boolean cursorVisible;
    private boolean touchScreen;
        
    private CrdsHorizontal horTemp = new CrdsHorizontal();
    
    public FormInfo frmInfo;
    public FormMenu frmMenu;
            
    private static final double SCALE_MAX = 57.6650390625;
    private static final int HORIZONT_OFFSET = 20;    
    
    public SkyCanvas(CommandListener cl, Display d, KeyCodeAdapter a)
    {
      super();
      this.setFullScreenMode(true);
      
      this.width = this.getWidth();
      this.height = this.getHeight();    
      this.radius = this.height - HORIZONT_OFFSET;
      setRotation(-90);
      this.centerX = this.width / 2;
      this.centerY = 0;
      this.setScale(1);       
      
      this.display = d;
      this.adapter = a;
      this.setCommandListener(cl);
      this.frmInfo = new FormInfo(cl);
      this.frmMenu = new FormMenu(cl);      
      this.touchScreen = this.hasPointerEvents();      
    }
            
    public void paint(Graphics g)
    {
      // fill ground
      if (scale < 3)
      {
        g.setColor(0x002200);
      }
      else
      {
        g.setColor(0x000000);
      }
      
      g.translate(0, 0); 
      g.fillRect(0, 0, width, height);
      g.translate(centerX, centerY);
            
      drawGrid(g);
      drawConstells(g);
      drawDeepSkies(g);
      drawConLines(g);
      drawStars(g);
      for (int i = Sky.PLANETS_COUNT - 1; i > 2; i--)
      {
        drawPlanet(g, i);
      }
      drawAsteroids(g);
      drawComets(g);
      drawSun(g);
      drawPlanet(g, 1);
      drawPlanet(g, 0);
      drawMoon(g);  
      drawEarthShadow(g);
      drawCursor(g);
      drawMenu(g);
      drawButtons(g);
    }
     
    private void drawConLines(Graphics g)
    {
        if (!Settings.ConstelLines) return;
        g.setColor(100, 100, 100);
        g.setStrokeStyle(Graphics.DOTTED);
        int from, to, x1, y1;
        for (int i=0; i < Sky.CONLINES_COUNT; i++)
        {
          from = Sky.starsFrom[i];
          to = Sky.starsTo[i];
          if (Sky.stars[from].horizontal.altitude < 0 ||
              Sky.stars[to].horizontal.altitude < 0) continue;
          
          Projection(Sky.stars[from].horizontal);        
          x1 = pointX;
          y1 = pointY;
          Projection(Sky.stars[to].horizontal);
          g.drawLine(x1, y1, pointX, pointY);
        }        
    }
    
    private void drawStars(Graphics g)
    {
      if (!Settings.Stars) return;
      g.setStrokeStyle(Graphics.SOLID);
      
      int diam = 1;
      for (int i=0; i < starLimit; i++)
      {        
        if (Sky.stars[i].horizontal.altitude < 0) continue;
        if (!Projection(Sky.stars[i].horizontal)) continue;         
        if (Settings.StarColors)
        {
          g.setColor(getStarColor(Sky.stars[i].spectralClass));
        }
        else
        {
          g.setColor(0xFFFFFF);
        }

        diam = getDrawDiameter(Sky.stars[i].mag);
        if (diam > 1)
        {
          g.fillArc(pointX-diam/2, pointY-diam/2, diam, diam, 0, 360);
        }
        else
        {
          g.drawLine(pointX, pointY, pointX, pointY);
        }
        
        if (Settings.StarLabels && Sky.stars[i].properName != null && isLabelVisible(Sky.stars[i].mag))
        {
          g.setColor(0x505050);
          g.drawString(Sky.stars[i].properName, (int)(pointX), (int)(pointY), Graphics.LEFT | Graphics.TOP);
        }
      }
    }
    
    private void drawAsteroids(Graphics g)
    {
      if (!Settings.SolarSystem) return; 
      for (int i=0; i<Sky.asteroids.size(); i++)
      {
        Asteroid a = (Asteroid)Sky.asteroids.elementAt(i);
        
        if (a.magnitude > magLimit) continue;
        if (a.horizontal.altitude < 0) continue;
        if (!Projection(a.horizontal)) continue;         

        g.setStrokeStyle(Graphics.SOLID);
        g.setColor(0xFFFFFF);
        g.drawLine(pointX, pointY, pointX, pointY);

        if (Settings.SolarSystemLabels && a.name != null)
        {
          g.setColor(0x303030);
          g.drawString(a.name, (int)(pointX), (int)(pointY), Graphics.LEFT | Graphics.TOP);
        }
      }
    }
    
    private void drawComets(Graphics g)
    {
      if (!Settings.SolarSystem) return; 
      int pX, pY; 

      for (int i=0; i<Sky.comets.size(); i++)
      {
        Comet c = (Comet)Sky.comets.elementAt(i);
        
        if (c.magnitude > magLimit) continue;
        if (c.horizontal.altitude < 0) continue;
        if (!Projection(c.horizontal)) continue;         
        pX = pointX;
        pY = pointY;          
        
        g.setStrokeStyle(Graphics.SOLID);        
        float size = (float)(c.appearance.coma / 90.0 * scale * radius);        
        
        // draw tail
        g.setColor(0x3e515e);
        Projection(c.tailHorizontal);
        if (size < 3 || c.appearance.tail < 0.0001)
        {
          g.drawLine(pX, pY, pointX, pointY); 
        }
        else
        {
          double rot = Math2.atan2(pY - pointY, pX - pointX) + Math.PI / 2;
          double sinRot = Math.sin(rot);
          double cosRot = Math.cos(rot);
          int pX1, pY1, pX2, pY2;
          pX1 = (int)(pX+size/2.*cosRot);
          pY1 = (int)(pY+size/2.*sinRot);
          pX2 = (int)(pX-size/2.*cosRot);
          pY2 = (int)(pY-size/2.*sinRot);           
          g.fillTriangle(pX1, pY1, pointX, pointY, pX2, pY2);
        }
        
        // draw coma/nuclear
        if (size < 3)
        {
          g.setColor(0xFFFFFF);
          g.drawLine(pX, pY, pX, pY); 
        }
        else
        {
          g.fillArc((int)(pX-size/2.),(int)(pY-size/2.),(int)size,(int)size,0,360);
        }
        
        // draw label
        if (Settings.SolarSystemLabels && c.name != null)
        {
          g.setColor(0x303030);
          g.drawString(c.name, (int)(pX+size*0.36), (int)(pY+size*0.36), Graphics.LEFT | Graphics.TOP);
        }
      }
    }    
    
    private void drawConstells(Graphics g)
    {
      if (!Settings.ConstelLabels) return;
      if (scale == 1) return;
      g.setColor(0x703030);
      for (int i=0; i < Sky.CONSTELS_COUNT; i++)
      {
        if (Sky.constells[i].horizontal.altitude < 0) continue;
        if (!Projection(Sky.constells[i].horizontal)) continue;         
        g.drawString(Sky.constells[i].name, pointX, pointY, Graphics.BASELINE | Graphics.HCENTER);
      }
    }    
    
    private void drawDeepSkies(Graphics g)
    {
      if (!Settings.DeepSkies) return;  
      g.setStrokeStyle(Graphics.SOLID);
      for (int i=0; i < Sky.DEEPSKY_COUNT; i++)
      {
        if (Sky.deepSkies[i].magnitude > magLimit) continue;
        if (Sky.deepSkies[i].horizontal.altitude < 0) continue;
        if (!Projection(Sky.deepSkies[i].horizontal)) continue;
       
        g.setColor(0x606060);
    
        switch (Sky.deepSkies[i].type)
        {
          case 0: // Galaxy:
            g.drawArc(pointX-3,pointY-1, 6,3,0,360);
            break;
          case 1: // Open Cluster: 
          case 5: // Cluster associated with nebulosity:  
          default:
            g.drawArc(pointX-1,pointY-1, 3,3,0,360);
            break;
          case 2: // Globular cluster:
            g.drawArc(pointX-3,pointY-3,5,5,0,360);
            g.drawLine(pointX-2, pointY, pointX+2, pointY);
            g.drawLine(pointX, pointY-2, pointX, pointY+2);
            break;
          case 3: // Bright emission or reflection nebula:
            g.drawRect(pointX-2,pointY-2,4,4);  
            break;
          case 4: // Planetary nebula:
            g.drawArc(pointX-2,pointY-2, 4,4,0,360);
            g.drawLine(pointX-3,pointY, pointX-4, pointY);
            g.drawLine(pointX+3,pointY, pointX+4, pointY);
            g.drawLine(pointX,pointY-3, pointX, pointY-4);
            g.drawLine(pointX,pointY+3, pointX, pointY+4);  
            break;              
        }
        
        if (Settings.DeepSkyLabels && scale > 1)
        {
          g.setColor(0x205E80);
          g.drawString(Sky.deepSkies[i].name, pointX+2, pointY+2, Graphics.LEFT | Graphics.TOP);
        }
      }
    } 
    
    
    private void drawSun(Graphics g)
    {
      if (!Settings.SolarSystem) return;
      if (Sky.sun.horizontal.altitude < 0) return;     
      int size = getMoonOrSunDiameter(Sky.sun.angleDiameter, Sky.sun.horizontal);
      if (size < 7) size = 7;
      Projection(Sky.sun.horizontal);
      g.setColor(0xFFFF00);  
      g.fillArc(pointX-size/2 , pointY-size/2, size, size, 0, 360);
      
      if (Settings.SolarSystemLabels)
      {
        g.setColor(0x808080);
        g.drawString(Sky.sun.name, (int)(pointX+size*0.36), (int)(pointY+size*0.36), Graphics.LEFT | Graphics.TOP);
      }
    }
    
    private int getMoonOrSunDiameter(double angleDiameter, CrdsHorizontal hor)
    {      
      // return (int)(angleDiameter / 90.0 * scale * radius);
      
      double alt, x1, x2, y1, y2;
      
      alt = Math.toRadians(hor.altitude - angleDiameter / 2.0);
      x1 = (radius * scale * hor.cosAzi * Math.cos(alt)) / (Math.sin(alt) + 1);
      y1 = (radius * scale * hor.sinAzi * Math.cos(alt)) / (Math.sin(alt) + 1); 
      alt = Math.toRadians(hor.altitude + angleDiameter / 2.0);
      x2 = (radius * scale * hor.cosAzi * Math.cos(alt)) / (Math.sin(alt) + 1);
      y2 = (radius * scale * hor.sinAzi * Math.cos(alt)) / (Math.sin(alt) + 1);      
      
      return (int)Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));      
    }
    
    private void drawMoon(Graphics g)
    {
      if (!Settings.SolarSystem) return;
      if (Sky.moon.horizontal.altitude < 0) return;     
      int size = getMoonOrSunDiameter(Sky.moon.angleDiameter, Sky.moon.horizontal);
      if (size < 7) size = 7;
      Projection(Sky.moon.horizontal);
      if (pointX < -centerX-size/2) return;
      if (pointX > -centerX + width + size/2) return;
      if (pointY < -centerY-size/2) return;
      if (pointY > -centerY + height+size/2) return;      
      
      int clrDark = 0x808080;
      int clrLight = 0xFFFFFF;
        
      if (Settings.SolarSystemLabels)
      {
        g.setColor(0x808080);
        g.drawString(Sky.moon.name, (int)(pointX+size*0.36), (int)(pointY+size*0.36), Graphics.LEFT | Graphics.TOP);
      }
      
      double rot = -Sky.moon.horizontal.azimuth + rotation - (Sky.moon.positionAngle) - Sky.moon.parallacticAngle;            
      rot = Math2.to360(rot);

      drawPhase(g, pointX, pointY, rot, size, Sky.moon.phase, Sky.moon.age, clrDark, clrLight);
      
    }
    
    public static void drawPhase(Graphics g,
                                 int x,
                                 int y,
                                 double rot,
                                 double size,
                                 double phase,
                                 double age,
                                 int clrDark,
                                 int clrLight)
    {            
      boolean inv = (age < 90 || age > 270);
      if (!inv)
      {
        rot += 180;
      }
            
      int pX0 = 0, pY0 = 0, pX = 0, pY = 0;
           
      double a = size/2.0;
      double b = Math.abs((0.5 - phase))/0.5*a;
      double e = Math.sqrt(1 - (b*b)/(a*a));
      
      double p = (b*b)/a;
      double rho; 
      double sinPhi;
      double cosPhi;      
      double angle = 0;
      
      g.setColor(inv ? clrLight : clrDark);
      for (int i=0; i<16; i++)
      {
        if (i>0)
        {
          pX0 = pX;
          pY0 = pY;          
        }
        
        angle = ((double)i / 15.0) * 180;
          
        cosPhi = Math.cos(Math.toRadians(rot + angle));
        sinPhi = Math.sin(Math.toRadians(rot + angle));
             
        pX = (int)(x + a * cosPhi);
        pY = (int)(y + a * sinPhi);        
       
        if (i>0)
        {  
          g.fillTriangle(x, y, pX0, pY0, pX, pY);
        }     
      }
      
      g.setColor(inv ? clrDark : clrLight); 
      for (int i=0; i<16; i++)
      {
        if (i>0)
        {
          pX0 = pX;
          pY0 = pY;          
        }
        
        angle = ((double)i / 15.0) * 180;
          
        cosPhi = Math.cos(Math.toRadians(angle));
        sinPhi = Math.sin(Math.toRadians(angle));
       
        rho = (a*b)/(Math.sqrt(b*b*cosPhi*cosPhi + a*a*sinPhi*sinPhi)); 
        
        cosPhi = Math.cos(Math.toRadians(rot + angle));
        sinPhi = Math.sin(Math.toRadians(rot + angle));
              
        pX = (int)(x + rho * cosPhi);
        pY = (int)(y + rho * sinPhi);
        
        if (i>0)
        {
          g.fillTriangle(x, y, pX0, pY0, pX, pY);
        }
      }
      
      for (int i=0; i<16; i++)
      {
        if (i>0)
        {
          pX0 = pX;
          pY0 = pY;          
        }
        
        angle = ((double)i / 15.0) * 180 + 180;
          
        cosPhi = Math.cos(Math.toRadians(rot + angle));
        sinPhi = Math.sin(Math.toRadians(rot + angle));
             
        pX = (int)(x + a * cosPhi);
        pY = (int)(y + a * sinPhi);        
       
        if (i>0)
        {  
          g.fillTriangle(x, y, pX0, pY0, pX, pY);
        }     
      } 
    }                            
    
    private void drawEarthShadow(Graphics g)
    {
      if (!Settings.SolarSystem) return;
      if (Sky.moon.horizontal.altitude < 0) return;     
      int size = getMoonOrSunDiameter(Sky.moon.angleDiameter, Sky.moon.horizontal);
      if (size < 7) return;
      Projection(Sky.moon.shadowHorisontal);
      if (pointX < -centerX-size/2) return;
      if (pointX > -centerX + width + size/2) return;
      if (pointY < -centerY-size/2) return;
      if (pointY > -centerY + height+size/2) return;
      
      g.setColor(0x800000);
      g.setStrokeStyle(Graphics.DOTTED);
      
      int umbra = (int)(2 * Sky.moon.umbra * size * 6378.0 / 3476.0);
      int penumbra = (int)(2 * Sky.moon.penumbra * size * 6378.0 / 3476.0);
      
      g.drawArc(pointX-umbra/2 , pointY-umbra/2, umbra, umbra, 0, 360);
      g.drawArc(pointX-penumbra/2 , pointY-penumbra/2, penumbra, penumbra, 0, 360);
      
      if (Settings.SolarSystemLabels)
      {
        g.setColor(0x800000);
        g.drawString(Language.EarthShadow, (int)(pointX+penumbra*0.36), (int)(pointY+penumbra*0.36), Graphics.LEFT | Graphics.TOP);
      }    
    }
    
    private void drawPlanet(Graphics g, int i)
    {
      if (!Settings.SolarSystem) return;
      if (Sky.planets[i].horizontal.altitude < 0) return;     
      if (Sky.planets[i].magnitude > magLimit) return;
      if (!Projection(Sky.planets[i].horizontal)) return;
      int diam = getDrawDiameter(Sky.planets[i].magnitude); 
      g.setColor(getPlanetColor(i));  
      
      if (diam > 1)
      {
        g.fillArc(pointX-diam/2 , pointY-diam/2, diam, diam, 0, 360);
      }
      else
      {
        g.drawLine(pointX, pointY, pointX, pointY);
      }
      
      if (Settings.SolarSystemLabels)
      {
        g.setColor(0x808080);
        g.drawString(Sky.planets[i].name, pointX+1, pointY+1, Graphics.LEFT | Graphics.TOP);
      }
    }
    
    private void drawGrid(Graphics g)
    {
        // fill sky background
        g.setColor(0x000000);
        int r = (int)(radius * scale);
        g.fillArc(-r, -r, 2 * r, 2 * r, 0, 360);
        
        // draw horizon
        if (scale >= 3)
        {
            if (r + centerY < height) 
            {
              g.setColor(0x002200);
              g.fillRect(-width/2, r, width, height - (r + centerY));
              g.setStrokeStyle(Graphics.SOLID);
              g.setColor(0, 100, 100);
              g.drawLine(-width/2, r, width/2, r);
            }
        }
        
        // set line colors
        g.setColor(0, 100, 100);
        g.setStrokeStyle(Graphics.DOTTED);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        
        // circles
        double alt;
         
        for (int i = 0; i<90; i+=10)
        {
            if (!Settings.HorisontalGrid && i > 0) break;
            alt = Math.toRadians(i);
            r = (int)(radius * scale * (Math.cos(alt) / (Math.sin(alt) + 1)));

            if (r < Math.abs(centerY) || 
                r > Math.abs(centerY) + radius + HORIZONT_OFFSET ||
                r > 2.5 * radius) continue; 
            
            if (i > 0) g.setStrokeStyle(Graphics.DOTTED);
            else g.setStrokeStyle(Graphics.SOLID);
            g.drawArc(-r, -r, 2 * r, 2 * r, 0, 360);
        }
        
        
        // lines
        int x1, y1;
        for (int i = 0; i < 360; i+=15)
        {
          horTemp.altitude = 0;
          horTemp.azimuth = i;
          horTemp.sinAzi = (float)Math.sin(Math.toRadians(i));
          horTemp.cosAzi = (float)Math.cos(Math.toRadians(i));
          horTemp.coefAlt = 1;
          Projection(horTemp);
          x1 = pointX;
          y1 = pointY;
          horTemp.altitude = 80;
          horTemp.coefAlt = (float)0.087488666; // for 80 degrees; 
          Projection(horTemp);
          
          if (Settings.HorisontalGrid)
          {
            g.drawLine(x1, y1, pointX, pointY);
          } 
          // directions
          if (i % 45 == 0)
          {
            g.drawString(Language.Directions[i / 45], x1, y1 + 3, Graphics.TOP | Graphics.HCENTER);
          }
       }
        
       // ecliptic
       if (Settings.Ecliptic)
       {
         g.setColor(0x505000);
         int pointX0, pointY0;
         boolean vis1, vis2;

         for (int i=0; i<48; i++)
         {
           if (Sky.ecliptic[i].altitude > 0 &&
               Sky.ecliptic[i+1].altitude > 0)
           {
             vis1 = Projection(Sky.ecliptic[i]);
             pointX0 = pointX;
             pointY0 = pointY;
             vis2 = Projection(Sky.ecliptic[i+1]);
             if (vis1 || vis2)
             {
               g.drawLine(pointX0, pointY0, pointX, pointY);
             }
           }
         }
       }
    }
    
    private void drawCursor(Graphics g)
    {
        if (!cursorVisible) return;
        
        if (touchScreen)
        {
          int size = width / 10; 
          g.setColor(0xFF0000);
          g.drawArc(cursorX-size/2, cursorY-size/2, size, size, 0, 360);
        }
        else
        {
          g.setColor(0xFF0000);
          g.drawRect(cursorX-2, cursorY-2,5,5);
          g.drawLine(cursorX-5, cursorY, cursorX-2, cursorY);
          g.drawLine(cursorX+3, cursorY, cursorX+6, cursorY);
          g.drawLine(cursorX, cursorY-5, cursorX, cursorY-2);
          g.drawLine(cursorX, cursorY+3, cursorX, cursorY+6);     
        }
    }
    
    private void drawButtons(Graphics g)
    {
      if (!touchScreen) return;
                
      // size of button
      int size = width / 10;
     
      g.setStrokeStyle(Graphics.SOLID);
      
      // buttons
           
      /* (-) */ 
      g.setColor(0x000000);
      g.fillArc(0, 0, size, size, 0, 360);      
      g.setColor(0x660000);
      g.drawArc(0, 0, size, size, 0, 360);
      g.setColor(0xaa0000);      
      g.drawLine(size/4, size/2, size/4+size/2, size/2);

      /* (+) */ 
      g.setColor(0x000000);
      g.fillArc(width-size-1, 1, size, size, 0, 360);
      g.setColor(0x660000);
      g.drawArc(width-size-1, 0, size, size, 0, 360);
      g.setColor(0xaa0000);
      g.drawLine(width-size/4-size/2-1, size/2,width-size/4-1, size/2);
      g.drawLine(width-size/2-1, size/4,width-size/2-1, size/4+size/2);
      
      /* < */
      g.setColor(0x000000);
      g.fillArc(0, (height-size)/2, size, size, 0, 360);
      g.setColor(0x660000);
      g.drawArc(0, (height-size)/2, size, size, 0, 360);
      g.setColor(0xaa0000);
      g.drawLine(size/4, height/2, size/4+size/3, height/2-size/3);
      g.drawLine(size/4, height/2, size/4+size/3, height/2+size/3);
       
      /* > */
      g.setColor(0x000000);
      g.fillArc(width-size-1, (height-size)/2, size, size, 0, 360);
      g.setColor(0x660000);
      g.drawArc(width-size-1, (height-size)/2, size, size, 0, 360);
      g.setColor(0xaa0000);
      g.drawLine(width-size/4-1, height/2, width-size/4-1-size/3, height/2-size/3);
      g.drawLine(width-size/4-1, height/2, width-size/4-1-size/3, height/2+size/3);      
 
      if (scale > 1)
      {
        /* ^ */
        g.setColor(0x000000);
        g.fillArc((width-size)/2, 0, size, size, 0, 360);
        g.setColor(0x660000);
        g.drawArc((width-size)/2, 0, size, size, 0, 360);
        g.setColor(0xaa0000);
        g.drawLine(width/2, size/4, width/2+size/3, size/4+size/3);
        g.drawLine(width/2, size/4, width/2-size/3, size/4+size/3);

        /* v */
        g.setColor(0x000000);
        g.fillArc((width-size)/2, height-size-1, size, size, 0, 360);
        g.setColor(0x660000);
        g.drawArc((width-size)/2, height-size-1, size, size, 0, 360);      
        g.setColor(0xaa0000);
        g.drawLine(width/2, height-1-size/4, width/2-size/3, height-1-size/4-size/3);
        g.drawLine(width/2, height-1-size/4, width/2+size/3, height-1-size/4-size/3);
      }
    }
    
    private void drawMenu(Graphics g)
    {
      g.translate(-g.getTranslateX(), -g.getTranslateY());
      g.setColor(0x808080);
      g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
      g.drawString(Language.Menu, (int)(width), (int)(height), Graphics.BOTTOM|Graphics.RIGHT);
    }

    protected void sizeChanged(int w, int h)
    {
      this.width = w;
      this.height = h;
      this.radius = this.height - HORIZONT_OFFSET;
      repaint();
    }         
    
    public void centerObject(CrdsHorizontal hor)
    {
      setScale(38.443359375);
      setRotation(hor.azimuth - 90);
      Projection(hor);
      centerX = pointX + width / 2;
      centerY = -pointY + height / 2;
      checkCenter();
      repaint();
    }
    
    private void whatObject()
    {
      frmInfo.previousForm = this;
      frmInfo.setTitle(Language.InformationTitle);
      
      // minimal distance
      int minDist = (touchScreen ? width / 10 / 2 : 3);
      
      // Moon:
      Projection(Sky.moon.horizontal);
      int dist = getMoonOrSunDiameter(Sky.moon.angleDiameter / 2, Sky.moon.horizontal);      
      if (dist < minDist) dist = minDist;
      if (isNearPoint(dist))
      {     
        frmInfo.setInfo(Sky.getMoonInfo());
        display.setCurrent(frmInfo);
        return;
      }        
        
      // Sun:
      Projection(Sky.sun.horizontal);
      dist = getMoonOrSunDiameter(Sky.sun.angleDiameter / 2, Sky.sun.horizontal);
      if (dist < minDist) dist = minDist;
      if (isNearPoint(dist))
      {          
        frmInfo.setInfo(Sky.getSunInfo());
        display.setCurrent(frmInfo);
        return;
      }          
      
      // Planets:
      for (int i=0; i<Sky.PLANETS_COUNT; i++)
      {
        if (i==Sky.EARTH) continue;
        if (Sky.planets[i].magnitude > magLimit) continue;
        Projection(Sky.planets[i].horizontal);
        if (isNearPoint(minDist))
        {          
          frmInfo.setInfo(Sky.getPlanetInfo(i));
          display.setCurrent(frmInfo);
          return;
        }
      }
      
      // Asteroids:
      for (int i=0; i<Sky.asteroids.size(); i++)
      {      
        Projection(((Asteroid)(Sky.asteroids.elementAt(i))).horizontal);
        if (isNearPoint(minDist))
        {          
          frmInfo.setInfo(Sky.getAsteroidInfo(i));
          display.setCurrent(frmInfo);
          return;
        }
      }
      
      // Comets:
      for (int i=0; i<Sky.comets.size(); i++)
      {      
        Projection(((Comet)(Sky.comets.elementAt(i))).horizontal);
        if (isNearPoint(minDist))
        {          
          frmInfo.setInfo(Sky.getCometInfo(i));
          display.setCurrent(frmInfo);
          return;
        }
      }      
      
      // Stars:
      for (int i=0; i<starLimit; i++)
      {
        Projection(Sky.stars[i].horizontal);
        if (isNearPoint(minDist))
        {          
          frmInfo.setInfo(Sky.getStarInfo(i));
          display.setCurrent(frmInfo);
          return;
        }
      }
   
      // Deep Skies:
      for (int i=0; i<Sky.DEEPSKY_COUNT; i++)
      {
        if (Sky.deepSkies[i].magnitude > magLimit) continue;
        Projection(Sky.deepSkies[i].horizontal);
        if (isNearPoint(minDist))
        {          
          frmInfo.setInfo(Sky.getDeepSkyInfo(i));
          display.setCurrent(frmInfo);
          return;
        }
      } 
      
      if (touchScreen)
        cursorVisible = false;
      
    }
    
    private boolean isNearPoint(int dist)
    {
      boolean result = Math.sqrt((pointX-cursorX)*(pointX-cursorX) + 
                        (pointY-cursorY)*(pointY-cursorY)) < dist;
      
      if (result)
      {
        cursorX = pointX;
        cursorY = pointY;
      }
      
      return result;
      
    }
    
    private int getStarColor(final String spClass)
    {      
      switch (spClass.charAt(0)) 
      {
        case 'O': // blue
          return 0x00FFFF; 
        case 'B': // white-blue
          return 0xA4FFFF;         
        case 'A': // white
          return 0xFFFFFF;               
        case 'F': // white-yellow
          return 0xFFFFA4;              
        case 'G': // yellow
          return 0xFFFF00;               
        case 'K': // orange
          return 0xFF8000;              
        case 'M': // red
          return 0xFF0000; 
        default:
          break;
      }
      return 0xFFFFFF;
    }
    
    private int getPlanetColor(final int i)
    {
      switch (i)
      {   
        case 0:
          return 0x808080;
        case 1:
          return 0xFFFFFF;
        case 3:
          return 0xFF0000;
        case 4:
          return 0xFFFF80;
        case 5:
          return 0xFFC880;
        case 6: 
          return 0x7BA540;
        case 7:
          return 0x0000FF;     
        case 8:
        default:
          break;   
      }
      return 0x505050;
    }
        
    private boolean Projection(CrdsHorizontal hor)
    {
      /*
      double alt = Math.toRadians(hor.Altitude);
      double azi = Math.toRadians(hor.Azimuth + 90 + rotation);
     
      double cose = Math.cos(alt);
      double sine = Math.sin(alt);
      double cosf = Math.cos(azi);
      double sinf = Math.sin(azi);

      double k = (radius * scale) / (sine + 1);

      pointX = (int)(k * cose * cosf);
      pointY = (int)(k * cose * sinf);      
      */
      
      //double r = (90 - hor.Altitude) / 90.0 * radius * scale;
      //pointX = (int)(r * (hor.cosAzi * cosRotation - hor.sinAzi * sinRotation));
      //pointY = (int)(r * (hor.sinAzi * cosRotation + hor.cosAzi * sinRotation));
      
      double k = radius * scale * hor.coefAlt;
      pointX = (int)(k * (hor.cosAzi * cosRotation - hor.sinAzi * sinRotation));
      pointY = (int)(k * (hor.sinAzi * cosRotation + hor.cosAzi * sinRotation));
      
      if (pointX < -centerX) return false;
      if (pointX > -centerX + width) return false;
      if (pointY < -centerY) return false;
      if (pointY > -centerY + height) return false;

      return true;
    }   
    
    public void setRotation(double r)
    {
      rotation = r;
      sinRotation = Math.sin(Math.toRadians(rotation));
      cosRotation = Math.cos(Math.toRadians(rotation));
    }
    
    public void setScale(double s)
    {
        scale = s;
        if (scale < 1) scale = 1;
        if (scale > SCALE_MAX) scale = SCALE_MAX; 
        magLimit = 100;
        starLimit = Sky.STARS_COUNT;
        if (scale < 5.0625) magLimit = 5;
        if (scale < 3.375) magLimit = 4.5;
        if (scale < 2.25) magLimit = 4;
        if (scale < 1.5) magLimit = 3.5;
        
        if (scale < 5.0625) starLimit = Sky.STARS_COUNT; // 5m
        if (scale < 3.375) starLimit = 905; // 4.5m
        if (scale < 2.25) starLimit = 518; // 4m
        if (scale < 1.5) starLimit = 287; // 2.5m   
    }
    
    public int getDrawDiameter(float mag)
    {
      if (mag <= 0) return 5;
      if (mag <= 1) return 4;
      if (mag <= 2) return 3;
      if (mag <= 3) return 2;
      if (mag <= 4) return 1;
      return 1;
    }    
    
    public boolean isLabelVisible(float mag)
    {
      if (mag <= 0) return true; 
      if (scale / mag > 1.5) return true;
      return false;
    }
    
    protected void keyPressed(int keyCode) {
      int adaptKeyCode = adapter.adoptKeyCode(keyCode);  
      // Menu:      
      if (adaptKeyCode == KeyCodeAdapter.SOFT_KEY_RIGHT)
      {
        display.setCurrent(frmMenu);
        return;
      }
      // Moving map:
      if (adaptKeyCode == KeyCodeAdapter.UP_KEY) {
        if (cursorVisible) cursorY -= 10;
        else centerY += 10;
      } 
      else if (adaptKeyCode == KeyCodeAdapter.KEY_2)
      {
        if (cursorVisible) cursorY -= 1;
        else centerY += 1;
      }
      else if (adaptKeyCode == KeyCodeAdapter.DOWN_KEY) {
        if (cursorVisible) cursorY += 10;
        else centerY -= 10;
      }
      else if (adaptKeyCode == KeyCodeAdapter.KEY_8) {
        if (cursorVisible) cursorY += 1;
        else centerY -= 1;
      }      
      else if (adaptKeyCode == KeyCodeAdapter.LEFT_KEY) {     
        if (cursorVisible) cursorX -= 10;
        else  setRotation(rotation - 10.0 / scale);
      }
      else if (adaptKeyCode == KeyCodeAdapter.KEY_4) {     
        if (cursorVisible) cursorX -= 1;
        else  setRotation(rotation - 1.0 / scale);
      }      
      else if (adaptKeyCode == KeyCodeAdapter.RIGHT_KEY) {
        if (cursorVisible) cursorX += 10;
        else setRotation(rotation + 10.0 / scale);
      }
      else if (adaptKeyCode == KeyCodeAdapter.KEY_6) {
        if (cursorVisible) cursorX += 1;
        else setRotation(rotation + 1.0 / scale);
      }      
      else if (adaptKeyCode == KeyCodeAdapter.KEY_1) {
        zoomOut();
      } 
      else if (adaptKeyCode == KeyCodeAdapter.KEY_3) {
        zoomIn();
      }      
      else if (adaptKeyCode == KeyCodeAdapter.KEY_0) {
        cursorVisible = !cursorVisible;
        if (cursorVisible)
        {
          cursorX = -centerX + width / 2;
          cursorY = -centerY + height / 2;
        }
      }      
      else if (adaptKeyCode == KeyCodeAdapter.CENTER_KEY || 
               adaptKeyCode == KeyCodeAdapter.KEY_5) {
        if (cursorVisible) {
          whatObject();
        }  
      }     
      
      if (cursorVisible) checkCursor();
      else checkCenter();
      repaint();
    }
    
    protected void keyRepeated(int keyCode)
    {      
      if (this.hasRepeatEvents())
      {
        int adaptKeyCode = adapter.adoptKeyCode(keyCode);
        if (adaptKeyCode == KeyCodeAdapter.DOWN_KEY  ||
            adaptKeyCode == KeyCodeAdapter.UP_KEY    ||
            adaptKeyCode == KeyCodeAdapter.LEFT_KEY  ||
            adaptKeyCode == KeyCodeAdapter.RIGHT_KEY ||
            adaptKeyCode == KeyCodeAdapter.KEY_2     ||
            adaptKeyCode == KeyCodeAdapter.KEY_4     ||
            adaptKeyCode == KeyCodeAdapter.KEY_6     ||
            adaptKeyCode == KeyCodeAdapter.KEY_8)
        {
          keyPressed(keyCode);
        }
      }
    }
    
    private void zoomIn()
    {
        if (scale < SCALE_MAX)
        {
          cursorVisible = false;  
          setScale(scale * 1.5);
          double sy = (centerY-radius / 2)*1.5;
          centerY = (int)(radius / 2 + sy);
        }
    }
    
    private void zoomOut()
    {
        cursorVisible = false;
        setScale(scale / 1.5);
        double sy = (centerY-radius / 2) / 1.5;
        centerY = (int)(radius / 2 + sy); 
    }
    
    protected void pointerPressed(int x, int y)
    {      
      double dx; 
      double dy; 
      int BUTTON_SIZE = width / 10;
      cursorVisible = true;
      
      // menu
      if (x > width - BUTTON_SIZE &&
          y > height - BUTTON_SIZE)
      {
        display.setCurrent(frmMenu);
      }
      
      // left
      dx = BUTTON_SIZE/2-x;
      dy = height/2-y;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        setRotation(rotation - 10.0 / scale);
        cursorVisible = false;
      }
      
      // right
      dx = width-BUTTON_SIZE/2-x;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        setRotation(rotation + 10.0 / scale);
        cursorVisible = false;
      }
      
      // up
      dx = width/2-x;
      dy = BUTTON_SIZE/2-y;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        centerY += 10;
        cursorVisible = false;
      }
      
      // down
      dy = height-BUTTON_SIZE/2-y;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        centerY -= 10;
        cursorVisible = false;
      }     
      
      // zoom out
      dx = BUTTON_SIZE/2-x;
      dy = BUTTON_SIZE/2-y;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        zoomOut();
        cursorVisible = false;
      }         
      
      // zoom in
      dx = width-BUTTON_SIZE/2-x;
      if (Math.sqrt(dx*dx + dy*dy) <= BUTTON_SIZE / 2)
      {
        zoomIn();
        cursorVisible = false;
      }   
      
      // what object
      if (cursorVisible)
      {
        cursorX = -centerX+x;
        cursorY = -centerY+y;
        whatObject();
      }
      
      checkCenter();
      repaint();
    }
        
    private void checkCenter()
    {
      if (centerY > 0) centerY = 0;
      if (centerY < radius - radius * scale) 
      {
        centerY = (int)(radius - radius * scale);
      }
    }
    
    private void checkCursor()
    {
      if (cursorX < -centerX) cursorX = -centerX;
      if (cursorX > -centerX + width) cursorX = -centerX + width;
      if (cursorY < -centerY) cursorY = -centerY;
      if (cursorY > -centerY + height) cursorY = -centerY + height;      
    }

  protected void keyReleased(int keyCode)
  {
  }
}
