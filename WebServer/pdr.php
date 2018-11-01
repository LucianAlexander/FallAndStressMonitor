<?php
    require "initmsql.php";

    $data=date('Y-m-d');
    $ora=date('H:i:s');

    $gsr = $_POST['g'];
    $bpm =$_POST['b'];
    $asisid =intval($_POST['i']); //nuovo prova
  

    #API access key from Google API's Console
    define( 'API_ACCESS_KEY', 'yourapikey' );
 
    //$asisid =intval($_GET['a']); la usavo prima
    $messaggio = $_POST['m'];  // era GET
    $problema = "";
   
  
    //determina il messaggio da inviare al caregiver
    if(strcmp($messaggio,"A")==0){
    $title = "Allerta";
    $message = "Il tuo assistito è caduto";
    $img_url = "http://192.168.0.4/server/images/cadere.jpg";
    $problema = "caduta";
    
    }else if(strcmp($messaggio,"B")==0){
         $title = "Attenzione";
         $message = "Il tuo assistito è stressato";
         $img_url = "http://192.168.0.4/server/images/stresat.jpeg";
         $problema = "stress";
         }else if(strcmp($messaggio,"C")==0){
              $title = "Attenzione";
              $message = "Ritmo cardiaco irregolare";
              $img_url = "http://192.168.0.4/server/images/inima.jpg";
              $problema = "cardio";
              }else if(strcmp($messaggio,"D")==0){
                   $problema = "caldo";
                   }else if(strcmp($messaggio,"E")==0){
                        $problema = "fredo";
                         }else{
                               $problema = "stabile";
                              }
    //individua il caregiver prendendo il suo token dalla basi di dati
    $sql = "select caregiver.car_token from caregiver, assistito WHERE caregiver.car_id = assistito.car_id AND assistito.asis_id = $asisid";
     
   

    $result1 = mysqli_query($con,$sql);
    $row = mysqli_fetch_row($result1);
    $registrationIds = $row[0];

    //invio la notifica  

    #prep the bundle
     $msg = array
          (
		'title'	=> $title,
                'message' => $message,
                'img_url' => $img_url,
             	'icon'	=> 'myicon',/*Default Icon*/
              	'sound' => 'mySound'/*Default sound*/
          );
	$fields = array
			(
				'to'		=> $registrationIds,
				'data'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
   #Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );
    #Echo Result Of FireBase Server

    echo $result;

    $statement = mysqli_prepare($con, "INSERT INTO dati (dati_data, dati_ora, dati_bpm, dati_gsr, asis_id, dati_evento) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssisis", $data, $ora, $bpm , $gsr, $asisid, $problema);
    
 
    $response1 = mysqli_stmt_execute($statement);
    
    
    $response["success"] = $response1;  
    echo json_encode($response);
?>
