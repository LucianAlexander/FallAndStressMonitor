<?php
#qui dovrei prendere tutti i dati del giorno where dati_data = today
#devo inserire devo passargli anche l'id del assistito

require "initmsql.php";
$data=date('Y-m-d');
$assistito = $_POST["assistito"];

$sql = "SELECT dati_ora, dati_bpm, dati_gsr, dati_id   FROM dati where asis_id = '$assistito' AND dati_data = '$data'";
$result = mysqli_query($con,$sql);
$response = array();

while($row = mysqli_fetch_array($result)){

     array_push($response,array(
                                "ora"=>$row[0],
                                "bpm"=>$row[1], 
                                "gsr"=>$row[2]
                                ));

}
echo json_encode(array("response"=>$response)); 
mysqli_close($con);

?>

