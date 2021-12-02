<?php
$hostname='localhost';
$database='id15395402_startbuying';
$username='id15395402_start';
$password='m\l7}3BceH4/K)!i';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "<h1>Lo sentimos, el sitio web está experimentado problemas</h1>";
}

    $nt_idorder= $_POST['idorder'];
    $nt_idproduct= $_POST['idproduct'];
    $nt_price= $_POST['price'];
    $nt_quantify= $_POST['quantify'];

    $stmt = "INSERT INTO `detalleOrder_consumer` (`fk_id_product`, `fk_id_order`, `price`, `quantify`) VALUES ('$nt_idproduct', '$nt_idorder', '$nt_price', '$nt_quantify')";
    
    $response = mysqli_query($conexion,$stmt);
    
    if($response){
        echo "image upload";
        mysqli_close($conexion);
    }else{
        echo "Failed";   
    }

    
?>




