/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Java planetarium for mobile phones.                                    */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

package org.krutov.acrusky.ui;

import org.krutov.acrusky.lang.Language;
import javax.microedition.lcdui.*;

public class WaitScreen extends Canvas 
{  
    /** Continuous mode of progress bar */
    public static final int CONTINUOUS = -1;
  
    private int progress = 0;
    private int step = 0;
    private String text = ""; 
    private int width;
    private int height; 
    private Image imgLogo;   
    private KeyCodeAdapter adapter;
    private CommandListener listener;  
    
    public static final Command cmdCancel = new Command(Language.Cancel, Command.CANCEL, 0);  
    
    public WaitScreen(CommandListener cl, KeyCodeAdapter a)
    {
      super();
      this.setFullScreenMode(true);
      width = getWidth();
      height = getHeight();
      adapter = a;
      listener = cl;
    }
       
    public void stopProgress()
    {  
      progress = 0;
    }    
    
    public void setProgress(String text, int progress)
    {     
      progress = Math.min(progress, 99);
      this.progress = progress;
      this.text = text;
      
      if (progress == CONTINUOUS)
      {
        Thread t = new Thread(new Runnable()
        {
          public void run()
          {
            while(isContinuous())
            {
              try
              {
                Thread.sleep(100);
              }
              catch (Exception e) { }
              repaint();
              serviceRepaints();
            }
          }
        });
        t.start();
      }
      else
      {
        repaint();
        serviceRepaints();
      }
    }
    
    private boolean isContinuous()
    {
      return (this.progress == CONTINUOUS);
    }
       
    protected void paint(Graphics g)
    {
      // Draw logo 
      g.translate(0, 0);
      g.setColor(0x000000);
      g.fillRect(0, 0, width, height);
      if (imgLogo == null)
      {
        try
        {
          imgLogo = Image.createImage("/files/logo.png");
        }
        catch(Exception ex){}         
      }
      g.drawImage(imgLogo, width / 2, height / 2 - 40, Graphics.HCENTER|Graphics.VCENTER);
      g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));      
      g.setColor(0x505050);
      g.drawString("AcruSky Mobile 2.5", width / 2, height / 2 + 10,  Graphics.TOP|Graphics.HCENTER);
      
      // Draw progress bar:
      g.setColor(0xFFFFFF);
      g.drawString(text, width / 2, height / 2 - 10, Graphics.HCENTER | Graphics.BOTTOM);
      g.setColor(0xBDA002);
      g.drawRect(10, height / 2 - 5, width - 20, 10);
      g.setColor(0x528597);
      
      if (isContinuous())
      {
        // continuous progress bar
        g.fillRect(12 + (int)((width - 23 - width / 10) * (double)step / 100), height / 2 - 3, width / 10, 7);
        step += 10;
        if (step > 100) step = 0;
        
        // "cancel" button
        g.translate(-g.getTranslateX(), -g.getTranslateY());
        g.setColor(0x808080);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        g.drawString(Language.Cancel, (int)(width), (int)(height), Graphics.BOTTOM|Graphics.RIGHT);
      }
      else
      {
        g.fillRect(12, height / 2 - 3, (int)((width - 21) * (double)progress / 100), 7);
      }
    }
    
    protected void sizeChanged(int w, int h)
    {
      this.width = w;
      this.height = h;
      repaint();
    }   
    
    protected void keyPressed(int keyCode) 
    {
      if (!isContinuous()) return;
      int adaptKeyCode = adapter.adoptKeyCode(keyCode);      
      if (adaptKeyCode == KeyCodeAdapter.SOFT_KEY_RIGHT)
      {
        progress = 0;
        listener.commandAction(cmdCancel, this);
      }
    }
}
