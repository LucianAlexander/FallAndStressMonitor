<?php
    require "initmsql.php";
    
    $email = $_POST["email"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT car_id, car_nome FROM caregiver WHERE car_email = ? AND car_password = ?");
    
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $car_id, $car_nome);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
         $response["success"] = true; 
         $response["idCaregiver"] = $car_id; 
         $response["nome"] = $car_nome;
       
    }
    
    echo json_encode($response);
?>
