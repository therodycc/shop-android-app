
--APP STARTBUYING

--Consulta ALL PRODUCT
SELECT Producto.id_product as id,Producto.name_product as name,Producto.image_product as image, Producto.descripcion_product as description, 
Producto.price_product as price, categoryProduct.name_category as categoria
FROM `Producto` INNER JOIN categoryProduct 
ON Producto.fk_id_category = categoryProduct.id_category 
INNER JOIN companyUser ON categoryProduct.id_company = companyUser.id_company 
WHERE categoryProduct.id_company = '6'
GO
--Consulta all category
SELECT c.id_category as id, c.name_category, c.description_category, COUNT(p.fk_id_category) as totalProduct 
FROM categoryProduct as c LEFT JOIN Producto as p 
ON c.id_category = p.fk_id_category 
INNER JOIN companyUser as cu 
ON cu.id_company = c.id_company 
WHERE c.id_company = '5' GROUP BY c.id_category
--Consulta all product
SELECT p.id_product as id, p.name_product as name, p.image_product as image, p.price_product as price, p.descripcion_product as description, c.name_category as category 
FROM categoryProduct as c 
INNER JOIN Producto as p 
ON c.id_category = p.fk_id_category 
LEFT JOIN companyUser as cu 
ON cu.id_company = c.id_company 
WHERE c.id_company = '5' 
GROUP BY p.id_product ORDER BY c.id_category
--Consulta store
SELECT cu.id_company as id, cu.name_company as name, cu.social_company as social, cu.phone_company as phono, cu.image_company as image_company, COUNT(p.id_product) AS product FROM categoryProduct as c LEFT JOIN Producto as p ON c.id_category = p.fk_id_category 
INNER JOIN companyUser as cu 
ON cu.id_company = c.id_company 
WHERE cu.status_company = 'true' 
GROUP BY cu.id_company
--Insert Order:
INSERT INTO `orderUser` (`id_orders`, `order_date`, `order_status`, `order_comment`, `order_subTotal`, `fk_id_user`, `fk_id_company`) VALUES ('shwb1isdn', '2020-11-22', 'pending', 'me gustaria probar tus productos algún dia', '12000', '16', '5');

--Insert Detalle Order:
INSERT INTO `detalleOrder_consumer` (`fk_id_product`, `fk_id_order`, `price`, `quantify`) VALUES ('3', 'shwb1isdn', '230', '1');

--List Orders:
SELECT o.id_orders as id_order, c.name_company as name_company, o.order_subTotal as subtotal, o.order_status as status, o.order_date as date FROM orderUser as o LEFT JOIN companyUser as c ON o.fk_id_company = c.id_company WHERE o.fk_id_user = 16

--list order count:



--Orders Suplier
SELECT l.id_orders as idorder, z.id_user as idcliente, concat_ws(' ', z.firtName_user, z.lastName_user) as fullname, l.order_status as status, l.order_subTotal as total, z.phone_user as phone, l.order_date as date
FROM companyUser AS c
INNER JOIN orderUser AS l
ON c.id_company = l.fk_id_company
INNER JOIN user as z
ON l.fk_id_user = z.id_user WHERE c.id_company = '5'

--Contador Product Company
SELECT COUNT(pt.id_product) as product FROM Producto AS pt 
LEFT JOIN categoryProduct as cp 
ON pt.fk_id_category = cp.id_category
LEFT JOIN companyUser as dc 
ON dc.id_company = cp.id_company WHERE cp.id_company = '5'
