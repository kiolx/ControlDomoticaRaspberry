<?php

$output = exec("upnpc -a 192.168.0.23 8990 8990 TCP");
echo $output;

?>