<?php
//qui prendo tutti gli enventi successi all'assistito da sempre
//devo passargli anche l'id dell'assistito
$data=date('Y-m-d');
$assistito = $_POST["assistito"];
require "initmsql.php";  
$blood = "stabile";
$sql = "SELECT dati_data, dati_ora, dati_bpm, dati_gsr, dati_evento FROM dati WHERE asis_id = '$assistito' AND dati_evento != '$blood'ORDER BY dati_id DESC";
$result = mysqli_query($con,$sql);
$response = array();

while($row = mysqli_fetch_array($result)){

     array_push($response,array( 
                                "data"=>$row[0], 
                                "ora"=>$row[1], 
                                "bpm"=>$row[2], 
                                "gsr"=>$row[3], 
                                "evento"=>$row[4]));

}
echo json_encode(array("server_response"=>$response)); 
mysqli_close($con);

?>
