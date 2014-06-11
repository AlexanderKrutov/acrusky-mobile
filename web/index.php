<?php

/*****************************************************************************/
/*    AcruSky Mobile.                                                        */
/*    Web Service for mobile Java planetarium.                               */
/*    http://krutov.org/acrusky/mobile/                                      */
/*    (c) Alexander Krutov                                                   */
/*****************************************************************************/

function parameterValue($name)
{
  if (isset($_POST[$name])) return $_POST[$name];
  else return null;
}

header("Content-Type: text/plain"); 

// predefined types
define('TYPE_ASTEROID', 100);
define('TYPE_COMET',    101);

// obtain parameters
$search = parameterValue("search");

if ($search != null)
{
  searchObjects($search);
  return;
}

//header("HTTP/1.1 404 Not Found");
return;

/*****************************************************************************/
/* Search objects by prefix                                                  */
/*****************************************************************************/
function searchObjects($search)
{
  $path = "./ws/";
  $found = 0;
  $result = "";
  
  // search asteroids
  $data = file($path . "asteroids.dat");
  $count = count($data);

  for($i=0; $i < $count && $found < 10; $i++) 
  { 
    $name = trim(substr($data[$i], 9, 38));
	$id = trim(substr($data[$i], 0, 7));  
	
	if (stristr($name, $search) !== FALSE ||
	    stristr($id, $search) !== FALSE)
    {
      $yy = trim(substr($data[$i], 47, 4));
      $mm = trim(substr($data[$i], 52, 2));
      $dd = trim(substr($data[$i], 55, 4));
      $M = trim(substr($data[$i], 59, 9));
      $a = trim(substr($data[$i], 68, 10));
      $e = trim(substr($data[$i], 78, 9));
      $omega = trim(substr($data[$i], 87, 9));
      $OMEG = trim(substr($data[$i], 96, 9));
      $inc = trim(substr($data[$i], 105, 9));
      $H = trim(substr($data[$i], 114, 7));
      $G = trim(substr($data[$i], 121, 6));	
	
	  $result .= 
	    TYPE_ASTEROID    . ";" . 
		$id . " " .$name . ";" .
		$id              . ";" . 
		$dd              . ";" .
		$mm              . ";" .
		$yy              . ";" .
		$M               . ";" .
		$a               . ";" .	  
		$e               . ";" .	  
		$omega           . ";" .	  
		$OMEG            . ";" .	  
		$inc             . ";" .	  
		$H               . ";" .	  
		$G               . "#";
	  
	  $found++;
	  if ($found == 10) break;
	}
  }
    
  // search comets
  $data = file($path . "comets.dat");
  $count = count($data);

  for($i=0; $i < $count && $found < 10; $i++) 
  {
    $name = trim(substr($data[$i], 0, 47));
	$id = $i;
	if (stristr($name, $search) !== FALSE)
	{
      $yy = trim(substr($data[$i], 47, 4));
      $mm = trim(substr($data[$i], 52, 2));
      $dd = trim(substr($data[$i], 55, 7));
	  $e = trim(substr($data[$i], 78, 9));
	  $q = trim(substr($data[$i], 62, 10));
      $omega = trim(substr($data[$i], 87, 9));
      $OMEG = trim(substr($data[$i], 96, 9));
      $inc = trim(substr($data[$i], 105, 9));
      $g = trim(substr($data[$i], 114, 7));
      $k = trim(substr($data[$i], 121));	
	  
	  $result .= 
	    TYPE_COMET    . ";" . 
		$name         . ";" .
		$id           . ";" . 
		$dd           . ";" .
		$mm           . ";" .
		$yy           . ";" .	  
		$e            . ";" .	  
        $q            . ";" .		
		$omega        . ";" .	  
		$OMEG         . ";" .	  
		$inc          . ";" .	  
		$g            . ";" .	  
		$k            . "#";
	  
	  $found++;
	  if ($found == 10) break;
	}
  }  
  
  $result = trim($result, "#");
  
  print $result;  
}

?>