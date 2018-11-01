<?php
    
require "initmsql.php";

$stmt = $con->prepare("INSERT INTO assistito (asis_nome, asis_cognome, asis_eta, asis_tel, car_id) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("ssiii", $nome, $cognome, $eta, $tel, $carid);

    $nome = $_POST["nome"];
    $cognome = $_POST["cognome"];
    $eta = intval($POST["eta"]);
    $tel = intval($_POST["telefono"]);
    $carid = intval($_POST["carid"]);

$response["success"] = false; 
$result = $stmt->execute();
$response["success"]= $result;
echo json_encode($response);
$stmt->close(); 
$con->close();

?>
