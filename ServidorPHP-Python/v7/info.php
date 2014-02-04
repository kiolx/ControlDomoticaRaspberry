<?php

echo "Marduk resuelve la direccion: ".getMachineIP("Marduk")."<br>";
echo "RaspberryPi resuelve la direccion: ".getMachineIP("RaspberryPi")."<br>";
echo "Gutentag resuelve la direccion: ".getMachineIP("gutentag")."<br>";
echo "Parcela resuelve la direccion: ".getMachineIP("globalparcelapc")."<br>";
echo "<br>";
echo "Temperatura CPU: ".obtenerTempCPU()." ºC<br>";
echo "Temperatura GPU: ".obtenerTempGPU()."<br>";
echo "<br>";
echo getPinStatus();
echo "<br>";
echo "Tiempo de encendido: ".getUptime()."<br>";
echo "EL usuario ejecutor del script es: ".getRunningUser();

function getRunningUser(){
return exec("whoami");
}

function getUptime(){
	return shell_exec("uptime");
}

function getPinStatus(){
	for($i=0;$i<20;$i++){
		$output = exec("gpio read ".$i);
		echo "El pin ".$i." está en modo ".$output."<br>";
	}
}

function obtenerTempCPU(){
	$output = exec("cat /sys/class/thermal/thermal_zone0/temp");
	$outputToDegress = $output/1000;
	return $outputToDegress;
}

function obtenerTempGPU(){
	$output = exec("/opt/vc/bin/vcgencmd measure_temp");
	$outputExploded = explode("=", $output);
        $outputReplaced = str_replace("'", " º", $outputExploded[1]);
	return $outputReplaced;
}

function getMachineIP($machineName){
	for($i=0;$i<5;$i++){
	$output= exec("nmblookup ".$machineName);
	$outputExploded = explode(" ", $output);
	  if($outputExploded[0] == "name_query"){
	   $outputExploded[0] = "dispositivo no encendido o no accesible";	
	  }else{
	   return $outputExploded[0];
	   exit();
	  }
	  usleep(300000);
	}
	return $outputExploded[0]; 
}

?>