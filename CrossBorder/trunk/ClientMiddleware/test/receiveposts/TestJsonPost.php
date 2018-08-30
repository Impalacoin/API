 <?php
    /*
      Listens to HTTP POST that contains Json Object and prints to file

      @author Eugene  Chimita
      20th September 2014
    */

    // This is the file that the HTTP POST information will be written to.
    // Adjust accordingly.
    $logFile = "/tmp/sessionLog.txt";

    $jsonData= file_get_contents('php://input');

    $phpArray = json_decode($jsonData);


    //*******************************************
    // No need to change what is below this line
    //*******************************************

    //fetch each key value pair posted per request
    foreach ($phpArray as $key => $value) {

      $final=$key.'='.$value."\r\n".$final;
      $ip=$_SERVER['REMOTE_ADDR'];
      $time= gmdate("l jS \of F Y h:i:s A",$_SERVER['REQUEST_TIME']);
      $url=$_SERVER['REQUEST_URI'];
      $method=$_SERVER['REQUEST_METHOD'];
      $remoteport=$_SERVER['REMOTE_PORT'];
      $protocol=$_SERVER['SERVER_PROTOCOL'];
      $software=$_SERVER['SERVER_SOFTWARE'];
      $port=$_SERVER['SERVER_PORT'];
    }


    $fileHandle = fopen($logFile, 'a') or die("Unable to open the listenerLog.txt.");
    fwrite($fileHandle, "Remote Ip Address:"."\t".$ip."\r\n" );
    fwrite($fileHandle, "Time:"."\t"."\t".$time."\r\n" );
    fwrite($fileHandle, "Method:"."\t"."\t".$method."\r\n" );
    fwrite($fileHandle, "Remote Port:"."\t".$remoteport."\r\n" );
    fwrite($fileHandle, "Protocol:"."\t".$protocol."\r\n" );
    fwrite($fileHandle, "Local Port:"."\t".$port."\r\n" );
    fwrite($fileHandle,'***********end***********'."\r\n"."\r\n"."\r\n");
    fwrite($fileHandle, $final);
    fclose($fileHandle);

    $output = "Thank you";
    echo $output;
?>
