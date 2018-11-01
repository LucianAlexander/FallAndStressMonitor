<?php
    
require "initmsql.php";

$stmt = $con->prepare("INSERT INTO caregiver (car_nome, car_cognome, car_tel, car_email, car_password, car_token) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssisss", $nome, $cognome, $tel, $email, $password, $token);

    $nome = $_POST["nome"];
    $cognome = $_POST["cognome"];
    $tel = intval($_POST["tel"]);
    $email = $_POST["email"];  
    $password = $_POST["password"];
    $token = $_POST["token"];

$response["success"] = false; 
$result = $stmt->execute();
$response["success"]= $result;
echo json_encode($response);
$stmt->close(); 
$con->close();

?> 


