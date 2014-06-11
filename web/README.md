Server-side part of AcruSky Mobile application
==============================================

1. index.php - web-service script for AcruSkyMobile. 
Needs to be located on http://krutov.org/ws/ path.

2. Supplementary data files.
Should be located in the same folder as web-service script. 
  
  - asteroids.dat
     
    Asteroids database in SkyMap format, used by index.php script. 
    Can be obtained from:
    http://www.minorplanetcenter.net/iau/Ephemerides/Bright/2011/Soft01Bright.txt

  - comets.dat

    Comets database in SkyMap format, used by index.php script. 
    Can be obtained from:
    http://www.minorplanetcenter.net/iau/Ephemerides/Comets/Soft01Cmt.txt
