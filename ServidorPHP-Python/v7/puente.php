<?php

$act = $_GET["act"];
$cmd = $_GET["cmd"];
$id = $_GET["id"];

$pinLed = 0;
$pinIRsend = 17;
$pinIRreceiver = 18;

include("config.php");
	
//si el comando es distinto de nada, ejecutamos el codigo
if($act != ""){
    if($act == "newReg"){
      //insertamos en BD +id+cmd+timestamp+status
      $insertar = "INSERT INTO domotica (cmd, status) VALUES ('$cmd', 'NotExec')";
      mysql_query($insertar, $conn);
      mysql_close($conn);
      
      //Llamamos a la funcion que nos haga lo que queremos segun el cmd llegado
      ejecutarCMD($cmd, obtenersidBD(), $pinLed);
    }
}

//Funcion que nos ejecuta los cmd que queremos
function ejecutarCMD($cmd, $sidBD, $pinLed){

/////COMANDOS LED////////////////////////////

  if($cmd == "ledON"){
  gpioPinState($pinLed, 1);
  }

  if($cmd == "ledOFF"){
  gpioPinState($pinLed, 0);
  }

  if($cmd == "ledBlinking"){
    for ($i =0; $i <10 ; $i++) {
     gpioPinState($pinLed, 1);
     sleep(1);
     gpioPinState($pinLed, 0);
    }
  }
  
//////COMANDOS TVLG///////////////////////////
  if($cmd == "tvON"){
  mandarIR("SEND_ONCE", "TVLG", "power");
  }

  if($cmd == "tvLGvolUP"){
  mandarIR("SEND_ONCE", "TVLG", "vol_up");
  }

  if($cmd == "tvLGvolDOWN"){
  mandarIR("SEND_ONCE", "TVLG", "vol_down");
  }
  
  if($cmd == "tvLGchUP"){
  mandarIR("SEND_ONCE", "TVLG", "page_up");
  }

  if($cmd == "tvLGchDOWN"){
  mandarIR("SEND_ONCE", "TVLG", "page_down");
  }

  if($cmd == "tvLGvolMUTE"){
  mandarIR("SEND_ONCE", "TVLG", "mute");
  }
/////////////COMANDOS PLUS//////////////////////////////////
    if($cmd == "canalPlusON"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_POWER");
  }

  if($cmd == "canalPlusUP"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_VOLUMEUP");
  }

  if($cmd == "canalPlusDOWN"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_VOLUMEDOWN");
  }
  
  if($cmd == "canalPlusChUP"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_CHANNELUP");
  }

  if($cmd == "canalPlusChDOWN"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_CHANNELDOWN");
  }

  if($cmd == "canalPlusMUTE"){
  mandarIR("SEND_ONCE", "CANALPLUS", "KEY_MUTE");
  }
/////////////COMANDOS WOL y SHUTDOWN ////////////////////////////

  if($cmd == "wolMARDUK"){
  	for($i=0;$i<5;$i++){
     exec("wakeonlan BC:AE:C5:83:E7:F2");
     usleep(500000);
    }
  }

  if($cmd == "wolPARCELA"){
  	for($i=0;$i<5;$i++){
     exec("wakeonlan 90:2B:34:92:68:9A");
     usleep(500000);
    }
  }
  
 if($cmd == "shutdownMarduk"){
  	for($i=0;$i<5;$i++){
     exec(" net rpc shutdown -I ".getMachineIP("Marduk")." -U GlobalMarduk%none");
     usleep(500000);
    }
  }
  
    if($cmd == "shutdownPARCELA"){
  	for($i=0;$i<5;$i++){
     exec(" net rpc shutdown -I ".getMachineIP("globalparcelapc")." -U GlobalParcela%none");
     usleep(500000);
    }
  }

/////////////COMANDOS EXTRAS//////////////////////////////////

  if($cmd == "MORIR"){
  exec("/sbin/shutdown -h 0");
  }


 if($cmd == "configurarPINES"){ configurarPines($pinLed); }
 //Actualizamos la BD sea cual fuere el comando ejecutado
 actualizarBD($sidBD);
}

////////////////////////////INICIO DE FUNCIONES////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////

//Configuramos los pines de salida
function configurarPines($pinLed){
	//Ponemos en modo salida el pin del led
	gpioMode($pinLed, "out");
	//Configuramos los pines entrada/salida para el recevidor/emisor led infrarrojo
	exec("lirc_rpi gpio_out_pin=$pinIRsend gpio_in_pin=$pinIRreceiver");	
	
	return true;
}

//Funcion para mandar un comando por infrarrojo por LIRC
function mandarIR($veces,$dispositivo,$tecla){
	exec("irsend $veces $dispositivo $tecla");
}

//Configuramos los pines en modo salida o entrada según convenga 
function gpioMode($ping, $modo){
exec("gpio mode $ping '$modo'");
}

//Configuramos los pines en salida Alta o salida Baja (High/Low)(corriente/nocorriente) 
function gpioPinState($ping, $estado){
exec("gpio write $ping $estado");
}

function obtenersidBD(){
  include("config.php");
  $conn = mysql_connect($host, $user, $pass); 
  mysql_select_db($database);
  $result = mysql_query("SELECT id_cmd FROM domotica ORDER by id_cmd DESC LIMIT 1", $conn);
  if ($row = mysql_fetch_array($result)){ do {
  $identificador = $row["id_cmd"];
  } while ($row = mysql_fetch_array($result)); }
return $identificador;
}

function actualizarBD ($sidBD){
   //actualizamos en BD
   include("config.php");
   $update = "UPDATE domotica Set status='Exec' Where id_cmd='$sidBD'";
   mysql_query($update, $conn);
   mysql_close($conn); 
}

//Obtener IP a traves dle nombre de la maquina
function getMachineIP($machineName){
	for($i=0;$i<5;$i++){
	$output= exec("nmblookup ".$machineName);
	$outputExploded = explode(" ", $output);
	  if($outputExploded[0] == "name_query"){
	   $outputExploded[0] = "dispositivo no encontrado";	
	  }else{
	   return $outputExploded[0];
	   exit();
	  }
	  usleep(200000);
	}
	return $outputExploded[0]; 
}


function obtenerTempCPU(){
	$output = exec("cat /sys/class/thermal/thermal_zone0/temp");
	$outputToDegress = $output/1000;
	return $outputToDegress;
}

function obtenerTempGPU(){
	$output = exec("/opt/vc/bin/vcgencmd measure_temp");
	$outputExploded = explode("=", $output);
        $outputReplaced = str_replace("'C", "", $outputExploded[1]);
	return $outputReplaced;
}

function getRunningUser(){
return exec("whoami");
}

function getUptime(){
	$output =  shell_exec("uptime");
   	$outputExploded = explode(",", $output);      
        return $outputExploded[0];
}

///////////////////////////FIN FUNCIONES//////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////

  //Volvemos a abrir la conexion a BD
   $conn = mysql_connect($host, $user, $pass); 
   mysql_select_db($database);
  
  //mostramos resultados en formato XML, para hacer un parseo en android y ver lo ejecutado
  echo '<?xml version="1.0" encoding="UTF-8"?>';
  echo '<CommandList>';
	 
   $result = mysql_query("SELECT id_cmd, cmd, status, DATE_FORMAT(fecha, '%h:%i:%s - %d') as fecha2 FROM domotica ORDER by id_cmd DESC LIMIT 10", $conn);
   if ($row = mysql_fetch_array($result)){ do { ?>
   	
	  <Command>
	  	<id><? echo $row["id_cmd"]; ?></id>
		  <cmd><? echo $row["cmd"]; ?></cmd>
		  <fecha><? echo $row["fecha2"]; ?></fecha>
		  <status><? echo $row["status"]; ?></status>
	  </Command>
	  
   <? } while ($row = mysql_fetch_array($result)); } ?>

	  <CommandInfo>
	  	<id>1</id>
		  <cmd>Marduk</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo getMachineIP("Marduk"); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>2</id>
		  <cmd>Parcela</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo getMachineIP("globalparcelapc"); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>3</id>
		  <cmd>Gutentag</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo getMachineIP("Gutentag"); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>4</id>
		  <cmd>TempCPU</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo obtenerTempCPU(); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>5</id>
		  <cmd>TempGPU</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo obtenerTempGPU(); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>6</id>
		  <cmd>Usuario</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo getRunningUser(); ?></status>
	  </CommandInfo>

	  <CommandInfo>
	  	<id>7</id>
		  <cmd>Uptime</cmd>
		  <fecha>0000-00-00</fecha>
		  <status><? echo getUptime(); ?></status>
	  </CommandInfo>

  <? echo '</CommandList>';

?>