<?php

$act = $_GET["act"];
$cmd = $_GET["cmd"];
$id = $_GET["id"];

include("config.php");
	
//si el comando es distinto de nada, ejecutamos el codigo
if($act != ""){
	if($act == "newReg"){
	//insertamos en BD +id+cmd+timestamp+status
  $insertar = "INSERT INTO domotica (cmd, status) VALUES ('$cmd', 'NotExecuted')";
  mysql_query($insertar, $conn);
  mysql_close($conn);
  }
  
  if($act == "updateReg"){
  //actualizamos en BD
  $update = "UPDATE domotica Set status='Executed' Where id_cmd='$id'";
  mysql_query($update, $conn);
  mysql_close($conn);  
  }	
	
}
   //Volvemos a abrir la conexion a BD
   $conn = mysql_connect($host, $user, $pass); 
   mysql_select_db($database);
  
  //mostramos resultados en formato XML, para hacer un parseo en android y ver lo ejecutado
  echo '<?xml version="1.0" encoding="UTF-8"?>';
  echo '<CommandList>';
	 
   $result = mysql_query("SELECT * FROM domotica ORDER by id_cmd DESC", $conn);
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