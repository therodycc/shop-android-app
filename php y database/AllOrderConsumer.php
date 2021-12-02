<?php
$hostname='localhost';
$database='id15395402_startbuying';
$username='id15395402_start';
$password='m\l7}3BceH4/K)!i';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "Lo sentimos, el sitio web está experimentado problemas";
}

$nt_id_user=$_POST['id_user'];

$consulta = "SELECT o.id_orders as id_order, c.name_company as name_company, o.order_subTotal as subtotal, o.order_status as status, o.order_date as date, c.phone_company as phone FROM orderUser as o LEFT JOIN companyUser as c ON o.fk_id_company = c.id_company WHERE o.fk_id_user = '$nt_id_user' ";

$stmt = $conexion->prepare($consulta);

$stmt->execute();
$result = $stmt->get_result();
$outp = $result->fetch_all(MYSQLI_ASSOC);
if(json_encode($outp)!="[]"){echo json_encode($outp);}


$stmt->close();
$conexion->close();

?>
