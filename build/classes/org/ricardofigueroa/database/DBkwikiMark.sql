set global time_zone = '-6:00';

/*
 * Nombre: Ricardo Figueroa 
 * Fecha de creacion: 11/04/2024 
 * Ultima Fecha de edicion : 26/04/2024
 *
 * Esta es la base de datos del proyecto, totalmente funcional, 
 * con la entidad CLIENTE y sus PROCEDIMIENTOS ALMACENADOS, y la entidad PROVEEDORES.
 */
 
 
drop database if exists DBkwikiMark;

create database DBkwikiMark;

use DBkwikiMark;

-- Entidades

create table Clientes(
    codigoCliente int not null,
    NITCliente varchar(10) not null,
    nombreCliente varchar(50) not null,
    apellidoCliente varchar(50) not null,
    direccionCliente varchar(150) not null,
    telefonoCliente varchar(15) not null,
    correoCliente varchar(50) not null,
    primary key (codigoCliente)
);

create table Cargos(
    cargoId int,
    nombreCargo varchar(30),
    descripcionCargo varchar(100),
    primary key (cargoId)
);

create table Empleados(
    empleadoId int,
    nombreEmpleado varchar(30),
    apellidoEmpleado varchar(30),
    sueldo decimal(10,2),
    direccion varchar(30),
    turno varchar(30),
    cargoId int,
    encargadoId int,
    primary key (empleadoId),
    foreign key (cargoId)
        references Cargos (cargoId)
);


 
create table Proveedores(
    codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar (60),
    apellidoProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key (codigoProveedor)
);

CREATE TABLE EmailProveedor( 
    EmailProveedorid INT AUTO_INCREMENT,
    email VARCHAR(50),
    descripcion VARCHAR(100),
    proveedorId int,
    PRIMARY KEY(EmailProveedorid),
    CONSTRAINT fk_Email_Proveedor FOREIGN KEY (proveedorId) REFERENCES Proveedores(codigoProveedor)
);

CREATE TABLE TelefonoProveedor( 
    TelefonoProveedorId INT AUTO_INCREMENT,
    numeroP VARCHAR(50),
    numeroS VARCHAR(50),
    Observaciones VARCHAR(100),
    proveedorId int,
    PRIMARY KEY(TelefonoProveedorId),
    CONSTRAINT fk_Telefono_Proveedor FOREIGN KEY (proveedorId) REFERENCES Proveedores(codigoProveedor)
);

create table CategoriaProductos(
    categoriaProductosId int,
    nombreCategoria varchar(65),
    descripcionCategoria varchar(100),
    primary key (categoriaProductosId)
);

create table Productos (
    productoId int,
    nombreProducto varchar(50),
    descripcionProducto varchar(100),
    cantidadStock int,
    precioVentaMayor decimal(10,2),
    precioCompra decimal(10,2),
    distribuidorId int,
    imagenProducto varchar(50),
    categoriaProductosId int,
    primary key (productoId),
    foreign key (distribuidorId)
        references Proveedores(codigoProveedor),
    foreign key (categoriaProductosId)
        references CategoriaProductos (categoriaProductosId)
);


create table Compras(
    compraId int,
    fechaCompra date,
    descripcion varchar(100),
    totalCompra decimal(10,2),
    primary key (compraId)
);

create table DetalleCompra(
    detalleCompraId int,
    cantidadCompra int,
    productoId int,
    compraId int,
    primary key (detalleCompraId),
    foreign key (productoId)
        references Productos (productoId),
    foreign key (compraId)
        references Compras (compraId)
);



create table Facturas(
    facturaId int,
    fecha date,
    hora time,
    clienteId int,
    empleadoId int,
    total decimal(10,2),
    primary key (facturaId),
    foreign key (clienteId)
        references Clientes (codigoCliente),
    foreign key (empleadoId)
        references Empleados (empleadoId)
);


create table detallefactura (
    detalleFacturaId int(11),
    facturaId int(11),
    productoId int(11),
    Primary key (detalleFacturaId),
    foreign key (facturaId)
        references Facturas (facturaId),
    foreign key (productoId)
        references Productos (productoId)
);


-- ---------------------------------------------------------------------------------------------- CRUD


-- Clientes
-- Agregar
delimiter $$
 
create procedure sp_AgregarClientes (in codigoCliente int, in NITCliente varchar(10), in nombreCliente varchar(50), 
in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(15), in correoCliente varchar(50))
begin 
    insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values 
    (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente);
end $$
 
delimiter ;

delimiter $$
 
-- Listar
create procedure sp_ListarClientes ()
begin 
    select
    C.codigoCliente,
    C.NITCliente,
    C.nombreCliente,
    C.apellidoCliente,
    C.direccionCliente,
    C.telefonoCliente,
    C.correoCliente
    from Clientes C;
end $$
 
delimiter ;

delimiter $$
 
-- Actualizar
create procedure sp_ActualizarClientes (in codigoCliente_ int, in NITCliente_ varchar(10), in nombreCliente_ varchar(50), 
in apellidoCliente_ varchar(50), in direccionCliente_ varchar(150), in telefonoCliente_ varchar(15), in correoCliente_ varchar(50))
begin 
    update Clientes set 
    NITCliente = NITCliente_, 
    nombreCliente = nombreCliente_, 
    apellidoCliente = apellidoCliente_, 
    direccionCliente = direccionCliente_, 
    telefonoCliente = telefonoCliente_, 
    correoCliente = correoCliente_ 
    where codigoCliente = codigoCliente_;
end $$
 
delimiter ;

delimiter $$
 
-- Eliminar
create procedure sp_EliminarClientes (in codigoCliente_ int)
begin 
    delete from Clientes where codigoCliente = codigoCliente_;
end $$
 
delimiter ;

-- Empleados

-- Agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleado (
    IN empleadoId_ INT,
    IN nombreEmpleado_ VARCHAR(30),
    IN apellidoEmpleado_ VARCHAR(30),
    IN sueldo_ DECIMAL(10,2),
    IN direccion_ VARCHAR(30),
    IN turno_ VARCHAR(30),
    IN cargoId_ INT,
    IN encargadoId_ INT
)
BEGIN
    INSERT INTO Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, cargoId, encargadoId) VALUES 
    (empleadoId_, nombreEmpleado_, apellidoEmpleado_, sueldo_, direccion_, turno_, cargoId_, encargadoId_);
END $$
DELIMITER ;

-- Listar
DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados ()
BEGIN
    SELECT
    empleadoId,
    nombreEmpleado,
    apellidoEmpleado,
    sueldo,
    direccion,
    turno,
    cargoId,
    encargadoId
    FROM Empleados;
END $$
DELIMITER ;

-- Actualizar
DELIMITER $$
CREATE PROCEDURE sp_ActualizarEmpleado (
    IN empleadoId_ INT,
    IN nombreEmpleado_ VARCHAR(30),
    IN apellidoEmpleado_ VARCHAR(30),
    IN sueldo_ DECIMAL(10,2),
    IN direccion_ VARCHAR(30),
    IN turno_ VARCHAR(30),
    IN cargoId_ INT,
    IN encargadoId_ INT
)
BEGIN
    UPDATE Empleados SET 
    nombreEmpleado = nombreEmpleado_,
    apellidoEmpleado = apellidoEmpleado_,
    sueldo = sueldo_,
    direccion = direccion_,
    turno = turno_,
    cargoId = cargoId_,
    encargadoId = encargadoId_
    WHERE empleadoId = empleadoId_;
END $$
DELIMITER ;

-- Eliminar
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleado (
    IN empleadoId_ INT
)
BEGIN
    DELETE FROM Empleados WHERE empleadoId = empleadoId_;
END $$
DELIMITER ;



-- Proveedores

-- Agregar
delimiter $$
 
create procedure sp_AgregarProveedores (in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar (60),
in apellidoProveedor varchar (60), in direccionProveedor varchar (150), in razonSocial varchar (60), in contactoPrincipal varchar (100),
in paginaWeb varchar (50))
begin 
    insert into Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, 
    contactoPrincipal, paginaWeb) values 
    (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
end $$
 
delimiter ;

-- Buscar

delimiter $$
create procedure sp_BuscarProveedor (in codigoProveedor int)
begin
    select
        P.codigoProveedor,
        P.NITproveedor,
        P.nombreProveedor,
        P.apellidoProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
    from Proveedores P
    where P.codigoProveedor = codigoProveedor;
end $$
delimiter ;



delimiter $$
-- Listar
create procedure sp_ListarProveedores ()
begin 
    select
    P.codigoProveedor,
    P.NITproveedor,
    P.nombreProveedor,
    P.apellidoProveedor,
    P.direccionProveedor,
    P.razonSocial,
    P.contactoPrincipal,
    P.paginaWeb
    from Proveedores P;
end $$
 
delimiter ;

delimiter $$
 
-- Actualizar
create procedure sp_ActualizarProveedores (in codigoProveedor_ int, in NITproveedor_ varchar(10), in nombreProveedor_ varchar (60),
in apellidoProveedor_ varchar (60), in direccionProveedor_ varchar (150), in razonSocial_ varchar (60), in contactoPrincipal_ varchar (100),
in paginaWeb_ varchar (50))
begin 
    update Proveedores set 
    NITproveedor = NITproveedor_, 
    nombreProveedor = nombreProveedor_, 
    apellidoProveedor = apellidoProveedor_, 
    direccionProveedor = direccionProveedor_, 
    razonSocial = razonSocial_,
    contactoPrincipal = contactoPrincipal_,
    paginaWeb = paginaWeb_ 
    where codigoProveedor = codigoProveedor_;
end $$
 
delimiter ;

delimiter $$
 
-- Eliminar
create procedure sp_EliminarProveedores (in codigoProveedor_ int)
begin 
    delete from Proveedores where codigoProveedor = codigoProveedor_;
end $$
 
delimiter ;

-- EmailProveedor
-- Agregar
delimiter $$

create procedure sp_AgregarEmailProveedor (in EmailProveedorid int, in email varchar(50), in descripcion varchar(100), in proveedorId int)
begin 
	insert into EmailProveedor (EmailProveedorid, email, descripcion, proveedorId) values 
    (EmailProveedorid, email, descripcion, proveedorId);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarEmailProveedor ()
begin 
	select
    E.EmailProveedorid,
    E.email, 
    E.descripcion, 
    E.proveedorId
    from EmailProveedor E;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarEmailProveedor (in EmailProveedorid int)
begin 
	select 
		E.EmailProveedorid,
		E.email, 
		E.descripcion, 
		E.proveedorId
        from EmailProveedor E
        where EmailProveedorid = EmailProveedorid;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarEmailProveedor (in EmailProveedorid int)
begin 
	delete from EmailProveedor 
    where EmailProveedorid = EmailProveedorid;
end $$

Delimiter ;

-- Editar
delimiter $$

create procedure sp_EditarEmailProveedor(IN p_EmailProveedorid INT, IN p_email VARCHAR(50), IN p_descripcion VARCHAR(100), IN p_proveedorId INT)
begin 
	UPDATE EmailProveedor E
    SET 		
        E.email = p_email,
        E.descripcion = p_descripcion, 
        E.proveedorId = p_proveedorId
    WHERE E.EmailProveedorid = p_EmailProveedorid;
end $$

delimiter ;


-- TelefonoProveedor
-- Agregar
delimiter $$

create procedure sp_AgregarTelefonoProveedor (in TelefonoProveedorId int, in numeroP varchar(50), in numeroS varchar(50), in Observaciones varchar(100), in proveedorId int)
begin 
	insert into TelefonoProveedor (TelefonoProveedorId, numeroP, numeroS, Observaciones, proveedorId) values 
    (TelefonoProveedorId, numeroP, numeroS, Observaciones, proveedorId);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarTelefonoProveedor ()
begin 
	select
    T.TelefonoProveedorId,
    T.numeroP, 
    T.numeroS, 
    T.Observaciones, 
    T.proveedorId
    from TelefonoProveedor T;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarTelefonoProveedor (in TelefonoProveedorId int)
begin 
	select 
		T.TelefonoProveedorId,
		T.numeroP, 
		T.numeroS, 
		T.Observaciones, 
		T.proveedorId
        from TelefonoProveedor T
        where TelefonoProveedorId = TelefonoProveedorId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarTelefonoProveedor (in TelefonoProveedorId int)
begin 
	delete from TelefonoProveedor 
    where TelefonoProveedorId = TelefonoProveedorId;
end $$

Delimiter ;

-- Editar
delimiter $$


create procedure sp_EditarTelefonoProveedor(IN p_TelefonoProveedorId INT, IN p_numeroP VARCHAR(50), IN p_numeroS VARCHAR(50), IN p_Observaciones VARCHAR(100), IN p_proveedorId INT)
begin 
	UPDATE TelefonoProveedor T
    SET 		
        T.numeroP = p_numeroP,
        T.numeroS = p_numeroS, 
        T.Observaciones = p_Observaciones, 
        T.proveedorId = p_proveedorId
    WHERE T.TelefonoProveedorId = p_TelefonoProveedorId;
end $$

delimiter ;



delimiter ;



-- CategoriaProductos
-- Agregar
delimiter $$

create procedure sp_AgregarCategoriaProductos (in categoriaProductosId int, in nombreCategoria varchar(65), in descripcionCategoria varchar(100))
begin 
	insert into CategoriaProductos (categoriaProductosId, nombreCategoria, descripcionCategoria) values 
    (categoriaProductosId, nombreCategoria, descripcionCategoria);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarCategoriaProductos ()
begin 
	select
    C.categoriaProductosId,
    C.nombreCategoria, 
    C.descripcionCategoria
    from CategoriaProductos C;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarCategoriaProductos (in categoriaProductosId int)
begin 
	select 
		C.categoriaProductosId,
		C.nombreCategoria, 
		C.descripcionCategoria
        from CategoriaProductos C
        where categoriaProductosId = categoriaProductosId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarCategoriaProductos (in categoriaProductosId int)
begin 
	delete from CategoriaProductos 
    where categoriaProductosId = categoriaProductosId;
end $$

Delimiter ;

-- Editar
delimiter $$

create procedure sp_EditarCategoriaProductos(in categoriaProductosId int, in nombreCategoria varchar(65), in descripcionCategoria varchar(100))
begin 
	Update CategoriaProductos C
    set 		
		C.nombreCategoria = nombreCategoria,
		C.descripcionCategoria = descripcionCategoria
    where categoriaProductosId = categoriaProductosId;
end $$

delimiter ;

-- Productos
-- agregar
DELIMITER $$
CREATE PROCEDURE sp_AgregarProductos (IN productoId INT, IN nombreProducto VARCHAR(50), IN descripcionProducto VARCHAR(100), IN cantidadStock INT,IN precioVentaMayor DECIMAL(10,2), IN precioCompra DECIMAL(10,2), IN distribuidorId INT, IN imagenProducto VARCHAR(50), IN categoriaProductosId INT)
BEGIN
    INSERT INTO Productos (
        productoId, nombreProducto, descripcionProducto, cantidadStock, 
        precioVentaMayor, precioCompra, distribuidorId, imagenProducto, categoriaProductosId
    ) VALUES (
        productoId, nombreProducto, descripcionProducto, cantidadStock, 
        precioVentaMayor, precioCompra, distribuidorId, imagenProducto, categoriaProductosId
    );
END $$
DELIMITER ;


-- Listar
delimiter $$

create procedure sp_ListarProductos ()
begin 
	select
    P.productoId,
    P.nombreProducto, 
    P.descripcionProducto, 
    P.cantidadStock, 
    P.precioVentaMayor,
    P.precioCompra,
    P.distribuidorId,
    P.imagenProducto,
    P.categoriaProductosId
    from Productos P;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarProductos (in productoId int)
begin 
	select 
		P.productoId,
		P.nombreProducto, 
		P.descripcionProducto, 
		P.cantidadStock, 
		P.precioVentaMayor,
		P.precioCompra,
		P.distribuidorId,
		P.imagenProducto,
		P.categoriaProductosId
        from Productos P
        where productoId = productoId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarProductos (in productoId int)
begin 
	delete from Productos 
    where productoId = productoId;
end $$

Delimiter ;

-- Editar
DELIMITER $$
CREATE PROCEDURE sp_EditarProductos (IN p_productoId INT, IN p_nombreProducto VARCHAR(50), IN p_descripcionProducto VARCHAR(100), IN p_cantidadStock INT, IN p_precioVentaMayor DECIMAL(10,2),IN p_precioCompra DECIMAL(10,2), IN p_distribuidorId INT, IN p_imagenProducto VARCHAR(50), IN p_categoriaProductosId INT
)
BEGIN
    UPDATE Productos SET 
        nombreProducto = p_nombreProducto, 
        descripcionProducto = p_descripcionProducto, 
        cantidadStock = p_cantidadStock, 
        precioVentaMayor = p_precioVentaMayor, 
        precioCompra = p_precioCompra, 
        distribuidorId = p_distribuidorId, 
        imagenProducto = p_imagenProducto, 
        categoriaProductosId = p_categoriaProductosId
    WHERE productoId = p_productoId;
END $$
DELIMITER ;






-- Compras
-- Agregar una compra
DELIMITER $$
CREATE PROCEDURE sp_AgregarCompras (
    IN p_compraId INT,
    IN p_fechaCompra DATE,
    IN p_descripcion VARCHAR(100),
    IN p_totalCompra DECIMAL(10,2)
)
BEGIN
    INSERT INTO Compras (compraId, fechaCompra, descripcion, totalCompra)
    VALUES (p_compraId, p_fechaCompra, p_descripcion, p_totalCompra);
END $$
DELIMITER ;

-- Listar todas las compras
DELIMITER $$
CREATE PROCEDURE sp_ListarCompras ()
BEGIN
    SELECT compraId, fechaCompra, descripcion, totalCompra FROM Compras;
END $$
DELIMITER ;

-- Editar una compra
DELIMITER $$
CREATE PROCEDURE sp_EditarCompras (
    IN p_compraId INT,
    IN p_fechaCompra DATE,
    IN p_descripcion VARCHAR(100),
    IN p_totalCompra DECIMAL(10,2)
)
BEGIN
    UPDATE Compras
    SET fechaCompra = p_fechaCompra,
        descripcion = p_descripcion,
        totalCompra = p_totalCompra
    WHERE compraId = p_compraId;
END $$
DELIMITER ;

-- Eliminar una compra por su ID
DELIMITER $$
CREATE PROCEDURE sp_EliminarCompras (
    IN p_compraId INT
)
BEGIN
    DELETE FROM Compras
    WHERE compraId = p_compraId;
END $$
DELIMITER ;




-- DetalleCompra
-- Agregar
delimiter $$

create procedure sp_AgregarDetalleCompra (in detalleCompraId int, in cantidadCompra int, in productoId int, in compraId int)
begin 
	insert into DetalleCompra (detalleCompraId, cantidadCompra, productoId, compraId) values 
    (detalleCompraId, cantidadCompra, productoId, compraId);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarDetalleCompra ()
begin 
	select
    D.detalleCompraId,
    D.cantidadCompra, 
    D.productoId, 
    D.compraId
    from DetalleCompra D;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarDetalleCompra (in detalleCompraId int)
begin 
	select 
		D.detalleCompraId,
		D.cantidadCompra, 
		D.productoId, 
		D.compraId
        from DetalleCompra D
        where detalleCompraId = detalleCompraId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarDetalleCompra (in detalleCompraId int)
begin 
	delete from DetalleCompra 
    where detalleCompraId = detalleCompraId;
end $$

Delimiter ;

-- Editar
delimiter $$

create procedure sp_EditarDetalleCompra(in detalleCompraId int, in cantidadCompra int, in productoId int, in compraId int)
begin 
	Update DetalleCompra D
    set 		
		D.cantidadCompra = cantidadCompra,
		D.productoId = productoId, 
		D.compraId = compraId
    where detalleCompraId = detalleCompraId;
end $$

delimiter ;




-- Facturas
-- Agregar
delimiter $$

create procedure sp_AgregarFacturas (in facturaId int, in fecha date, in hora time, in clienteId int, in empleadoId int, in total decimal(10,2))
begin 
	insert into Facturas (facturaId, fecha, hora, clienteId, empleadoId, total) values 
    (facturaId, fecha, hora, clienteId, empleadoId, total);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarFacturas ()
begin 
	select
    F.facturaId,
    F.fecha, 
    F.hora, 
    F.clienteId, 
    F.empleadoId,
    F.total
    from Facturas F;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarFacturas (in facturaId int)
begin 
	select 
		F.facturaId,
		F.fecha, 
		F.hora, 
		F.clienteId, 
		F.empleadoId,
		F.total
        from Facturas F
        where facturaId = facturaId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarFacturas (in facturaId int)
begin 
	delete from Facturas 
    where facturaId = facturaId;
end $$

Delimiter ;

-- Editar
delimiter $$

create procedure sp_EditarFacturas(in facturaId int, in fecha date, in hora time, in clienteId int, in empleadoId int, in total decimal(10,2))
begin 
	Update Facturas F
    set 		
		F.fecha = fecha,
		F.hora = hora, 
		F.clienteId = clienteId, 
        F.empleadoId = empleadoId,
		F.total = total
    where facturaId = facturaId;
end $$

delimiter ;




-- DetalleFactura
-- Agregar
delimiter $$

create procedure sp_AgregarDetalleFactura (in detalleFacturaId int, in facturaId int, in productoId int)
begin 
	insert into DetalleFactura (detalleFacturaId, facturaId, productoId) values 
    (detalleFacturaId, facturaId, productoId);
end $$

delimiter ;

-- Listar
delimiter $$

create procedure sp_ListarDetalleFactura ()
begin 
	select
    D.detalleFacturaId,
    D.facturaId, 
    D.productoId
    from DetalleFactura D;
end $$

delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarDetalleFactura (in detalleFacturaId int)
begin 
	select 
		D.detalleFacturaId,
		D.facturaId, 
		D.productoId
        from DetalleFactura D
        where detalleFacturaId = detalleFacturaId;
end $$

delimiter ;

-- Eliminar
Delimiter $$

create procedure sp_EliminarDetalleFactura (in detalleFacturaId int)
begin 
	delete from DetalleFactura 
    where detalleFacturaId = detalleFacturaId;
end $$

Delimiter ;

-- Editar
delimiter $$

create procedure sp_EditarDetalleFactura(in detalleFacturaId int, in facturaId int, in productoId int)
begin 
	Update DetalleFactura D
    set 		
		D.facturaId = facturaId,
		D.productoId = productoId
    where detalleFacturaId = detalleFacturaId;
end $$

delimiter ;

-- Agregar un cargo
DELIMITER $$
CREATE PROCEDURE sp_AgregarCargo (
    IN cargoId_ INT,
    IN nombreCargo_ VARCHAR(30),
    IN descripcionCargo_ VARCHAR(100)
)
BEGIN
    INSERT INTO Cargos (cargoId, nombreCargo, descripcionCargo) VALUES 
    (cargoId_, nombreCargo_, descripcionCargo_);
END $$
DELIMITER ;

-- Listar todos los cargos
DELIMITER $$
CREATE PROCEDURE sp_ListarCargos ()
BEGIN
    SELECT cargoId, nombreCargo, descripcionCargo FROM Cargos;
END $$
DELIMITER ;

-- Actualizar un cargo
DELIMITER $$
CREATE PROCEDURE sp_ActualizarCargo (
    IN cargoId_ INT,
    IN nombreCargo_ VARCHAR(30),
    IN descripcionCargo_ VARCHAR(100)
)
BEGIN
    UPDATE Cargos SET 
    nombreCargo = nombreCargo_,
    descripcionCargo = descripcionCargo_
    WHERE cargoId = cargoId_;
END $$
DELIMITER ;

-- Eliminar un cargo
DELIMITER $$
CREATE PROCEDURE sp_EliminarCargo (
    IN cargoId_ INT
)
BEGIN
    DELETE FROM Cargos WHERE cargoId = cargoId_;
END $$
DELIMITER ;

SET SQL_SAFE_UPDATES = 0;



call sp_AgregarClientes (01, '12457878', 'harol', 'luna', 'El basurero zona 3', '21215498', 'harolxluna4ever.com');
call sp_AgregarClientes (02, '15423874', 'oliver', 'sisimit', 'El basurero km. 22', '45875221', 'xdxsdadasd.com');

call sp_ListarClientes ();

-- call sp_BuscarClientes (1);


-- Llamadas a los procedimientos
call sp_AgregarProveedores(1, '1234567890', 'Proveedor', 'S.A.', 'Calle 123, Ciudad', 'Proveedor de productos', 'Juan Perez', 'www.proveedor.com');
call sp_ListarProveedores();
call sp_ActualizarProveedores(1, '1234567890', 'Proveedor', 'S.A.', 'Calle 123, Ciudad', 'Proveedor de productos', 'Juan Perez', 'www.proveedor.com');
-- call sp_EliminarProveedores(1);


-- Llamadas a los procedimientos
call sp_AgregarEmailProveedor(1, 'proveedor@ejemplo.com', 'Email principal', 1);
call sp_ListarEmailProveedor();
call sp_BuscarEmailProveedor(1);
 call sp_EditarEmailProveedor(1, 'proveedor@ejemplo.com', 'Email de contacto', 1);
-- call sp_EliminarEmailProveedor(1);


-- Llamadas a los procedimientos
call sp_AgregarTelefonoProveedor(1, '1234567890', '0987654321', 'Numeros de contacto', 1);
call sp_ListarTelefonoProveedor();
call sp_BuscarTelefonoProveedor(1);
call sp_EditarTelefonoProveedor(1, '1234567890', '0987654321', 'Numeros de contacto actualizados', 1);
-- call sp_EliminarTelefonoProveedor(1);



-- Llamadas a los procedimientos
call sp_AgregarCategoriaProductos(1, 'Electrónicos', 'Productos electrónicos');
call sp_ListarCategoriaProductos();
call sp_BuscarCategoriaProductos(1);
call sp_EditarCategoriaProductos(1, 'Electrónicos', 'Productos electrónicos como televisores, radios, computadoras, etc.');
-- call sp_EliminarCategoriaProductos(1);


-- Llamadas a los procedimientos
call sp_AgregarProductos(1, 'Producto 1', 'Descripción del producto 1', 100, 200.00, 150.00, 1, NULL, 1);
call sp_ListarProductos();
call sp_BuscarProductos(1);
call sp_EditarProductos(1, 'Producto 1 actualizado', 'Descripción del producto 1 actualizada', 100, 200.00, 150.00, 1, NULL, 1);
-- call sp_EliminarProductos(1);


-- Llamadas a los procedimientos
call sp_AgregarCompras(1, '2024-05-10', 500.00);
call sp_ListarCompras();
call sp_BuscarCompras(1);
call sp_EditarCompras(1, '2024-05-10', 600.00);
-- call sp_EliminarCompras(1);



-- Llamadas a los procedimientos
call sp_AgregarDetalleCompra(1, 10, 1, 1);
call sp_ListarDetalleCompra();
call sp_BuscarDetalleCompra(1);
call sp_EditarDetalleCompra(1, 20, 1, 1);
-- call sp_EliminarDetalleCompra(1);


-- Llamadas a los procedimientos
call sp_AgregarFacturas(1, '2024-05-10', '08:00:00', 1, 1, 500.00);
call sp_ListarFacturas();
call sp_BuscarFacturas(1);
call sp_EditarFacturas(1, '2024-05-10', '08:00:00', 1, 1, 600.00);
-- call sp_EliminarFacturas(1);

-- Llamada al procedimiento para agregar un empleado
CALL sp_AgregarEmpleado(1, 'Juan', 'Perez', 1500.00, 'Calle 123', 'Matutino', 1, 2);
CALL sp_ListarEmpleados();
CALL sp_ActualizarEmpleado(1, 'Pedro', 'Gomez', 1700.00, 'Calle 456', 'Vespertino', 2, 3);
-- CALL sp_EliminarEmpleado(1);

-- Llamada al procedimiento para agregar un cargo
CALL sp_AgregarCargo(1, 'Gerente', 'Responsable de la gestión y dirección de un área');
CALL sp_ListarCargos();
CALL sp_ActualizarCargo(1, 'Director', 'Encargado de supervisar todas las operaciones');
-- CALL sp_EliminarCargo(1);

-- Llamado para agregar una compra
CALL sp_AgregarCompras(1, '2024-05-10', 'Descripción de la compra', 100.00);
CALL sp_ListarCompras();
CALL sp_EditarCompras(1, '2024-05-10', 'Nueva descripción de la compra', 150.00);
-- CALL sp_EliminarCompras(1);



-- Llamadas a los procedimientos
call sp_AgregarDetalleFactura(1, 1, 1);
call sp_ListarDetalleFactura();
call sp_BuscarDetalleFactura(1);
call sp_EditarDetalleFactura(1, 1, 1);
-- call sp_EliminarDetalleFactura(1);



