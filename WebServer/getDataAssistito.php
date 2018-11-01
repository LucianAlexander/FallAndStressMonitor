
<?php
    require "initmsql.php";
    
    $carid = $_POST["carid"];
    
    $statement = mysqli_prepare($con,"SELECT assistito.asis_id, 
                                             assistito.asis_nome, 
                                             assistito.asis_cognome, 
                                             assistito.asis_eta, 
                                             assistito.asis_tel, 
                                             dati.dati_bpm, 
                                             dati.dati_gsr 
                                             FROM dati, assistito
                                             WHERE assistito.asis_id=dati.asis_id AND assistito.car_id = ?
                                           
                                ");
    
    mysqli_stmt_bind_param($statement, "s", $carid);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $asis_id, $asis_nome, $asis_cognome, $asis_eta, $asis_tel, $dati_bpm, $dati_gsr);
    
    $response = array();
   
    $response1["success"] = false;  
   
    while(mysqli_stmt_fetch($statement)){
         $response["success"] = true; 
         $response["idasis"] = $asis_id; 
         $response["nome"] = $asis_nome;
         $response["cognome"] = $asis_cognome;
         $response["eta"] = $asis_eta;           
         $response["tel"] = $asis_tel;
         $response["bpm"] = $dati_bpm;
         $response["gsr"] = $dati_gsr;
           
    }
    $dead = $asis_id;
    if($dead == NULL){
       echo json_encode($response1);
    }else{
       echo json_encode($response);
         }
    
    
?>

