<?php
$hostname='localhost';
$database='id15395402_startbuying';
$username='id15395402_start';
$password='m\l7}3BceH4/K)!i';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "Lo sentimos, el sitio web está experimentado problemas";
}

$id = $_POST['id'];
$sql = "DELETE FROM `categoryProduct` WHERE `categoryProduct`.`id_category` = '$id'";


$stmt = $conexion->prepare($sql);


$stmt->execute();
$result = $stmt->get_result();
$outp = $result->fetch_all(MYSQLI_ASSOC);
if(json_encode($outp)!="[]"){echo json_encode($outp);}

$stmt->close();
$conexion->close();

?>