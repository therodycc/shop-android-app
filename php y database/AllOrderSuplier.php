<?php
$hostname='localhost';
$database='id15395402_startbuying';
$username='id15395402_start';
$password='m\l7}3BceH4/K)!i';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "Lo sentimos, el sitio web está experimentado problemas";
}

$nt_id_company=$_POST['id'];

$stmt = $conexion->prepare("SELECT l.id_orders as idorder, z.id_user as idcliente, concat_ws(' ', z.firtName_user, z.lastName_user) as fullname, l.order_status as status, l.order_subTotal as total, z.phone_user as phone, l.order_date as date, l.order_comment as commentc FROM companyUser AS c INNER JOIN orderUser AS l ON c.id_company = l.fk_id_company INNER JOIN user as z ON l.fk_id_user = z.id_user WHERE c.id_company = '$nt_id_company' ORDER BY l.order_status;");

$stmt->execute();
$result = $stmt->get_result();
$outp = $result->fetch_all(MYSQLI_ASSOC);
if(json_encode($outp)!="[]"){echo json_encode($outp);}


$stmt->close();
$conexion->close();

?>