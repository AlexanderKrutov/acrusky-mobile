/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.core.AstroUtils;
import org.krutov.acrusky.lang.Language;
import org.krutov.acrusky.core.Math2;
import org.krutov.acrusky.core.Sky;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ToolsScreen extends Canvas {   
  private boolean touchScreen;      
  private int width;
  private int height;
  private int centerX;
  private int centerY;   
  private double scale;
  private Image imgMars;
  
  private static final double jupiterFlateness = 0.9351256;
  private static final double saturnFlateness = 0.90203756;
  private static final double SCALE_MAX = 25;
   
  public static final int JUPITER_MOONS   = 0;
  public static final int SATURN_RINGS    = 1;
  public static final int MARS_APPEARANCE = 2;
  public static final int VENUS_PHASES    = 3;
  
  public int drawMode;
  
  public static final Command cmdBack = new Command(Language.Back, Command.BACK, 0);
  public static final Command cmdInfo = new Command(Language.GetInfo, Command.OK, 0);
  
  public ToolsScreen(CommandListener cl)
  {
    super();

    this.setCommandListener(cl);
    this.addCommand(cmdBack);  
    this.addCommand(cmdInfo);  
        
    touchScreen = this.hasPointerEvents();
    
    this.width = this.getWidth();
    this.height = this.getHeight(); 
    this.centerX = this.width / 2;
    this.centerY = this.height / 2;
    this.scale = 3;
  }   
  
  protected void pointerPressed(int x, int y)
  {      
    double dx; 
    double dy; 
    int size = width / 10;

    // zoom out
    dx = size/2-x;
    dy = size/2-y;
    if (Math.sqrt(dx*dx + dy*dy) <= size / 2)
    {
      zoomOut();
    }         

    // zoom in
    dx = width-size/2-x;
    if (Math.sqrt(dx*dx + dy*dy) <= size / 2)
    {
      zoomIn();
    }   

    repaint();
  }  
      
  public void paint(Graphics g)
  {
    // Fill background
    g.setColor(0x000000);
    g.translate(0, 0); 
    g.fillRect(0, 0, width, height);
  
    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
    
    switch (drawMode)
    {
      case JUPITER_MOONS:
        drawJupiterMoons(g);
        break;
      case SATURN_RINGS:
        drawSaturnRings(g);
        break;
      case MARS_APPEARANCE:
        drawMars(g);
        break;
      case VENUS_PHASES:
        drawVenus(g);
        break;
      default:
        break;
    }
    
    drawDirections(g);
    
    if (drawMode == JUPITER_MOONS ||
        drawMode == SATURN_RINGS)
      drawButtons(g);
  }
   
  private void drawJupiterMoons(Graphics g)
  {
    double radiusX = (width / 100.0) * scale;
    double radiusY = (jupiterFlateness * radiusX);
     
    int pointX = 0;
    int pointY = 0;
    int i, j;
    
    // Draw satellites
    for (j=0; j<2; j++)
    {
      for (i=0; i<4; i++)
      {
        if ((j == 0 && Sky.jupiterMoons[i].z < 0) /* inferior */ ||
            (j == 1 && Sky.jupiterMoons[i].z > 0) /* superior */)
        {
          pointX = (int)(Sky.jupiterMoons[i].x * radiusX + centerX);
          pointY = (int)(-Sky.jupiterMoons[i].y * radiusX + centerY);
          g.setColor(0xffffff);
          g.fillArc(pointX, pointY, 3, 3, 0, 360);
          g.setColor(0x505050);
          g.drawString(Language.JupiterMoons[i], pointX + 2, pointY + 2, Graphics.LEFT | Graphics.TOP);
        }
      }
      
      // Draw Jupiter
      if (j == 0)
      {
        g.setColor(0xeac76b);
        g.fillArc((int)(centerX-radiusX), (int)(centerY-radiusY), (int)(radiusX*2), (int)(radiusY*2), 0, 360);
                
        // NTB belt
        g.setColor(0xc0ae80);
        g.fillRoundRect((int)(centerX-radiusX*0.9), // x
                        (int)(centerY-radiusY*0.5),      // y
                        (int)(radiusX*2*0.9),   // width
                        (int)(radiusY*0.1), // height
                        (int)(radiusX * 0.1),
                        (int)(radiusX * 0.1));        
        
        // NEB belt
        g.setColor(0xb28262);
        g.fillRoundRect((int)(centerX-radiusX*0.99), // x
                        (int)(centerY-radiusY*0.3),      // y
                        (int)(radiusX*2*0.99),   // width
                        (int)(radiusY*0.2), // height
                        (int)(radiusX * 0.15),
                        (int)(radiusX * 0.15));
        
        
        // SEB belt
        g.setColor(0xc8b681);
        g.fillRoundRect((int)(centerX-radiusX*0.98), // x
                        (int)(centerY+radiusY*0.15),      // y
                        (int)(radiusX*2*0.98),   // width
                        (int)(radiusY*0.2), // height
                        (int)(radiusX * 0.15),
                        (int)(radiusX * 0.15));        
            
        // STB belt
        g.fillRoundRect((int)(centerX-radiusX*0.85), // x
                        (int)(centerY+radiusY*0.45),      // y
                        (int)(radiusX*2*0.85),   // width
                        (int)(radiusY*0.05), // height
                        (int)(radiusX * 0.1),
                        (int)(radiusX * 0.1));  
        
        // GRS
        // grsY > 0 => visible hemisphere
        double longitude = Math.toRadians(Sky.jupiterCM2 - Sky.jupiterGRS);
        double grsY = Math.cos(longitude);
        // grsX > 0 => right hemisphere 
        double grsX = Math.sin(longitude);
        
        if (grsY > 0 && Math.abs(grsX) < 0.8)
        {      
          g.setColor(0xb78b6d);
          g.fillArc((int)(centerX-radiusX*0.3/2.0 + radiusX*grsX),   // x
                     (int)(centerY+radiusY*0.25), // y
                     (int)(radiusX*0.3), // width
                      (int)(radiusX*0.2), // height
                    0,
                    360); 
        }
        
        g.setColor(0x000000);
        g.drawArc((int)(centerX-radiusX-1), (int)(centerY-radiusY-1), (int)(radiusX*2+2), (int)(radiusY*2+2), 0, 360);

      }
    }
  }
    
  private void drawSaturnRings(Graphics g)
  {
    double radiusX = (width / 100.0) * scale;
    double radiusY = (saturnFlateness * radiusX); 
    double sizeX;
    double sizeY;    
    int startAngle;
    int i;
    
    // Draw Rings
    double[] rings = new double[] {1, 0.8801, 0.8599, 0.6650, 0.5486};
    
    for (i=0; i<5; i++)
    {
      // Determine size of ring
      sizeX = rings[i] * radiusX * (Sky.saturnRings.x / Sky.planets[6].angleDiameter / 2);
      sizeY = rings[i] * radiusX * (Sky.saturnRings.y / Sky.planets[6].angleDiameter / 2);

      // Select color to draw the ring
      switch(i)
      {
        case 0:
        case 2:
          g.setColor(0xeac76b); // light
          break;
        case 1:
        case 4:
          g.setColor(0x000000); // black
          break;
        case 3:
          g.setColor(0x7c6529); // dust
          break;
         default:
          break;
      }
      g.fillArc((int)(centerX-sizeX), (int)(centerY-sizeY), (int)(sizeX*2), (int)(sizeY*2), 0, 360);
    }
           
    // Draw Saturn
    g.setColor(0xeac76b);
    g.fillArc((int)(centerX-radiusX), (int)(centerY-radiusY), (int)(radiusX*2), (int)(radiusY*2), 0, 360);

    // Draw rings' shadows
    if (scale > 5)
    {
      g.setColor(0x000000);   
      startAngle = (Sky.saturnRings.z < 0) ? 0 : 180;
      for (i=0; i<5; i++)
      {
        sizeX = rings[i] * radiusX * (Sky.saturnRings.x / Sky.planets[6].angleDiameter / 2);
        sizeY = rings[i] * radiusX * (Sky.saturnRings.y / Sky.planets[6].angleDiameter / 2);
        g.drawArc((int)(centerX-sizeX), (int)(centerY-sizeY), (int)(sizeX*2+1), (int)(sizeY*2+1), startAngle, 180);
      }
      g.drawArc((int)(centerX-radiusX-1), (int)(centerY-radiusY-1), (int)(radiusX*2+2), (int)(radiusY*2+2), (int)Math2.to360(startAngle+180), 180);
    }
  }  
  
  private void drawMars(Graphics g)
  {
    this.scale = SCALE_MAX;
    g.drawImage(imgMars, centerX, centerY, Graphics.HCENTER|Graphics.VCENTER);
  } 
  
  private void drawVenus(Graphics g)
  {
    this.scale = SCALE_MAX;
    final int radius = (int)((width / 100.0) * scale) * 2;
    final double positionAngle = AstroUtils.positionAngle(Sky.sun.equatorial, Sky.planets[1].equatorial);
    final double parallacticAngle = AstroUtils.parallacticAngle(Sky.siderealTime, Sky.observerLocation, Sky.planets[1].equatorial);    
    final double age = (Math.abs(Sky.planets[1].phase) < 0.5 ? 0 : 180);
    final double rot = Math2.to360(-90 - positionAngle - parallacticAngle);
    SkyCanvas.drawPhase(g, centerX, centerY, rot, radius, Math.abs(Sky.planets[1].phase), age, 0x756523, 0xebd477);
  }
  
  public void createMarsImage(WaitScreen ws)
  {
    this.scale = SCALE_MAX;
    int radius = (int)((width / 100.0) * scale);
    int sizeX = 0;
    int sizeY = 0;
    int[] pixels = null;
    Image img = null;
    imgMars = null;
    
    System.gc();

    try
    {
      img = Image.createImage("/files/mars.png");
      sizeX = img.getWidth();
      sizeY = img.getHeight(); 
      pixels = new int[1];
    }
    catch(Exception ex) { }
    
    int[][] points = new int[sizeY][2];
    int[] points2 = new int[2];
    
    imgMars = Image.createImage(radius*2, radius*2);

    double[][] R = Sky.marsAppearance.R;
    double lon;
    double lat;
    double delta;
    double alpha;
    double cosBcosL;
    double cosBsinL;
    double sinB;
    double B;
    double L;
    double cosL;
    double sinL;
    int x, y;
    int i, j;
    boolean black = false;
    
    Graphics g = imgMars.getGraphics();

    g.setColor(0x000000);
    g.translate(0, 0);
    g.fillRect(0, 0, radius*2, radius*2);     
   
    for (i = 0; i <= sizeX; i++)
    {
        if (i%8 == 0) 
          ws.setProgress(Language.ImageCreating, (int)(((double)i)/sizeX*100));
        
        for (j = 0; j < sizeY; j++)
        {
            lon = (double)i / sizeX * 360 - 180;
            lat = 90 - (double)j / sizeY * 180;

            black = Math.cos(Math.toRadians(lon + Sky.marsAppearance.lambda - Math2.sign(Sky.planets[3].phase) * Sky.planets[3].phaseAngle /*(-Sky.planets[3].phaseAngle + Sky.marsAppearance.lambda0)*/ )) >= 0; 
            
            delta = Math.toRadians(lat);
            alpha = Math.toRadians(lon);

            cosBcosL = R[0][0] * Math.cos(delta) * Math.cos(alpha) +
                       R[0][1] * Math.cos(delta) * Math.sin(alpha) +
                       R[0][2] * Math.sin(delta);

            cosBsinL = R[1][0] * Math.cos(delta) * Math.cos(alpha) +
                       R[1][1] * Math.cos(delta) * Math.sin(alpha) +
                       R[1][2] * Math.sin(delta);

            sinB =     R[2][0] * Math.cos(delta) * Math.cos(alpha) +
                       R[2][1] * Math.cos(delta) * Math.sin(alpha) +
                       R[2][2] * Math.sin(delta);

            B = Math2.asin(sinB);
            cosL = cosBcosL / Math.cos(B);
            sinL = cosBsinL / Math.cos(B);
            L = Math.toDegrees(Math2.atan2(sinL, cosL));
            B = Math.toDegrees(B);
            lon = L;
            lat = B;
  
            x = (int)(radius + radius * Math.sin(Math.toRadians(lon)) * Math.cos(Math.toRadians(lat)));
            y = (int)(radius - radius * Math.sin(Math.toRadians(lat)));
                                
            if (i == 0)
            {
              points[j][0] = x;
              points[j][1] = y;
            }
            if (j == 0)
            {
              points2[0] = x;
              points2[1] = y;
            }
                
            if (i > 0 && j > 0)
            {
              if (lon >= - 90 && lon <= 90)
              {
                // draw
                if (i==sizeX)
                {
                  img.getRGB(pixels, 0, 1, 0, j, 1, 1);
                  g.setColor(pixels[0]);
                }
                else
                {
                  img.getRGB(pixels, 0, 1, i, j, 1, 1);
                  g.setColor(pixels[0]);                 
                }
                              
                if (black)
                {
                  g.setColor(0x000000);
                }
                
                g.fillTriangle(points[j-1][0],
                               points[j-1][1],
                               points[j][0],
                               points[j][1],
                               x,
                               y);

                g.fillTriangle(points2[0],
                               points2[1],
                               points[j-1][0],
                               points[j-1][1],
                               x,
                               y);
              }  

              points[j-1][0] = points2[0];
              points[j-1][1] = points2[1];
              points2[0] = x;
              points2[1] = y;   
            }
        }
    }
    
    img = null;
    pixels = null;
    points2 = null;
    points = null;
    System.gc();
    
  }
  
  private void drawDirections(Graphics g)
  {
    g.setColor(0x505050);
    g.drawString(Language.Directions[0], width / 2, 0, Graphics.TOP | Graphics.HCENTER);
    g.drawString(Language.Directions[4], width / 2, height, Graphics.BOTTOM | Graphics.HCENTER);
  
    double radiusX = (width / 100.0) * scale;
    
    int p = 1;
    if (drawMode == MARS_APPEARANCE) p = 3;
    if (drawMode == JUPITER_MOONS) p = 4;
    if (drawMode == SATURN_RINGS) p = 5;
    
    // current scale, arcsec/pixel
    double scale = Sky.planets[p].angleDiameter / (radiusX * 2);
    
    int len = 5;
    int angle = 0;
    while (len < width / 4)
    {
      angle += 5;
      len = (int)(angle / scale);
    }
    
    int x1 = (width - len) / 2;
    int x2 = (width + len) / 2;
    
    g.drawString(String.valueOf(angle) + "\"", width / 2, height - 20, Graphics.BOTTOM | Graphics.HCENTER);
    g.drawLine(x1, height - 20, x2, height - 20);
    g.drawLine(x1, height - 19, x1, height - 21);
    g.drawLine(x2, height - 19, x2, height - 21);
  }
   
  private void drawButtons(Graphics g)
  {
      if (!touchScreen) return;
           
      // size of button
      int size = width / 10;
      
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
  }  
    
  private void setScale(double s)
  {
    scale = s;
    if (scale < 1) scale = 1;
    if (scale > SCALE_MAX) scale = SCALE_MAX; 
  }    
    
  private void zoomIn()
  {
    setScale(scale * 1.5);
  }
    
  private void zoomOut()
  {
    setScale(scale / 1.5);
  }    
    
  protected void keyPressed(int keyCode) 
  {
    if (keyCode == Canvas.KEY_NUM1) 
    {
      zoomOut();
    } 
    else if (keyCode == Canvas.KEY_NUM3) 
    {
      zoomIn();
    }      
    repaint();
  }    
}