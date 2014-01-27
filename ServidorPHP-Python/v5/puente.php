<?php

$act = $_GET["act"];
$cmd = $_GET["cmd"];
$id = $_GET["id"];

$pinLed = 6;
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
      ejecutarCMD($cmd, obtenersidBD());
    }
}

//Funcion que nos ejecuta los cmd que queremos
function ejecutarCMD($cmd, $sidBD){

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
  
  if($cmd == "tvON"){
  mandarIR("SEND_ONCE", "TVLG", "KEY_POWER");
  }
  


 if($cmd == "configurarPines"){ configurarPines(); }
 //Actualizamos la BD sea cual fuere el comando ejecutado
 actualizarBD($sidBD);
}

////////////////////////////INICIO DE FUNCIONES////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////

//Configuramos los pines de salida
function configurarPines(){
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
exec("gpio mode $pin $modo");
}

//Configuramos los pines en salida Alta o salida Baja (High/Low)(corriente/nocorriente) 
function gpioPinState($ping, $estado){
exec("gpio write $pin $estado");
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

///////////////////////////FIN FUNCIONES//////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////

  //Volvemos a abrir la conexion a BD
   $conn = mysql_connect($host, $user, $pass); 
   mysql_select_db($database);
  
  //mostramos resultados en formato XML, para hacer un parseo en android y ver lo ejecutado
  echo '<?xml version="1.0" encoding="UTF-8"?>';
  echo '<CommandList>';
	 
   $result = mysql_query("SELECT * FROM domotica ORDER by id_cmd DESC LIMIT 25", $conn);
   if ($row = mysql_fetch_array($result)){ do { ?>
   	
	  <Command>
	  	<id><? echo $row["id_cmd"]; ?></id>
		  <cmd><? echo $row["cmd"]; ?></cmd>
		  <fecha><? echo $row["fecha"]; ?></fecha>
		  <status><? echo $row["status"]; ?></status>
	  </Command>
	  
   <? } while ($row = mysql_fetch_array($result)); }

  echo '</CommandList>';

?>