ALTER USER '2023370_IN5BV'@'localhost' IDENTIFIED WITH mysql_native_password BY 'abc123!!';

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

create table DetalleFactura (
    detalleFacturaId int,
    facturaId int,
    productoId int,
    primary key (detalleFacturaId),
    foreign key (facturaId)
        references Facturas (facturaId),
    foreign key (productoId)
        references Productos (productoId)
);

-- ---------------------------------------------------------------------------------------------- CRUD

-- Clientes
-- Agregar
delimiter $$
create procedure sp_AgregarClientes (
    in codigoCliente int, 
    in NITCliente varchar(10), 
    in nombreCliente varchar(50), 
    in apellidoCliente varchar(50), 
    in direccionCliente varchar(150), 
    in telefonoCliente varchar(15), 
    in correoCliente varchar(50)
)
begin 
    insert into Clientes (
        codigoCliente, NITCliente, nombreCliente, 
        apellidoCliente, direccionCliente, 
        telefonoCliente, correoCliente
    ) values (
        codigoCliente, NITCliente, nombreCliente, 
        apellidoCliente, direccionCliente, 
        telefonoCliente, correoCliente
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarClientes ()
begin 
    select
        codigoCliente,
        NITCliente,
        nombreCliente,
        apellidoCliente,
        direccionCliente,
        telefonoCliente,
        correoCliente
    from Clientes;
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarClientes (
    in codigoCliente_ int, 
    in NITCliente_ varchar(10), 
    in nombreCliente_ varchar(50), 
    in apellidoCliente_ varchar(50), 
    in direccionCliente_ varchar(150), 
    in telefonoCliente_ varchar(15), 
    in correoCliente_ varchar(50)
)
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

-- Eliminar
delimiter $$
create procedure sp_EliminarClientes (
    in codigoCliente_ int
)
begin 
    delete from Clientes 
    where codigoCliente = codigoCliente_;
end $$
delimiter ;

-- Empleados

-- Agregar
delimiter $$
create procedure sp_AgregarEmpleado (
    IN empleadoId_ INT,
    IN nombreEmpleado_ VARCHAR(30),
    IN apellidoEmpleado_ VARCHAR(30),
    IN sueldo_ DECIMAL(10,2),
    IN direccion_ VARCHAR(30),
    IN turno_ VARCHAR(30),
    IN cargoId_ INT,
    IN encargadoId_ INT
)
begin
    insert into Empleados (
        empleadoId, nombreEmpleado, apellidoEmpleado, 
        sueldo, direccion, turno, 
        cargoId, encargadoId
    ) values (
        empleadoId_, nombreEmpleado_, apellidoEmpleado_, 
        sueldo_, direccion_, turno_, 
        cargoId_, encargadoId_
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmpleados ()
begin
    select
        empleadoId,
        nombreEmpleado,
        apellidoEmpleado,
        sueldo,
        direccion,
        turno,
        cargoId,
        encargadoId
    from Empleados;
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarEmpleado (
    IN empleadoId_ INT,
    IN nombreEmpleado_ VARCHAR(30),
    IN apellidoEmpleado_ VARCHAR(30),
    IN sueldo_ DECIMAL(10,2),
    IN direccion_ VARCHAR(30),
    IN turno_ VARCHAR(30),
    IN cargoId_ INT,
    IN encargadoId_ INT
)
begin
    update Empleados set 
        nombreEmpleado = nombreEmpleado_,
        apellidoEmpleado = apellidoEmpleado_,
        sueldo = sueldo_,
        direccion = direccion_,
        turno = turno_,
        cargoId = cargoId_,
        encargadoId = encargadoId_
    where empleadoId = empleadoId_;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmpleado (
    IN empleadoId_ INT
)
begin
    delete from Empleados 
    where empleadoId = empleadoId_;
end $$
delimiter ;

-- Proveedores

-- Agregar
delimiter $$
create procedure sp_AgregarProveedores (
    in codigoProveedor int, 
    in NITproveedor varchar(10), 
    in nombreProveedor varchar (60),
    in apellidoProveedor varchar (60), 
    in direccionProveedor varchar (150), 
    in razonSocial varchar (60), 
    in contactoPrincipal varchar (100),
    in paginaWeb varchar (50)
)
begin 
    insert into Proveedores (
        codigoProveedor, NITproveedor, nombreProveedor, 
        apellidoProveedor, direccionProveedor, razonSocial, 
        contactoPrincipal, paginaWeb
    ) values (
        codigoProveedor, NITproveedor, nombreProveedor, 
        apellidoProveedor, direccionProveedor, razonSocial, 
        contactoPrincipal, paginaWeb
    );
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarProveedor (
    in codigoProveedor int
)
begin
    select
        codigoProveedor,
        NITproveedor,
        nombreProveedor,
        apellidoProveedor,
        direccionProveedor,
        razonSocial,
        contactoPrincipal,
        paginaWeb
    from Proveedores
    where codigoProveedor = codigoProveedor;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarProveedores ()
begin 
    select
        codigoProveedor,
        NITproveedor,
        nombreProveedor,
        apellidoProveedor,
        direccionProveedor,
        razonSocial,
        contactoPrincipal,
        paginaWeb
    from Proveedores;
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarProveedores (
    in codigoProveedor_ int, 
    in NITproveedor_ varchar(10), 
    in nombreProveedor_ varchar (60),
    in apellidoProveedor_ varchar (60), 
    in direccionProveedor_ varchar (150), 
    in razonSocial_ varchar (60), 
    in contactoPrincipal_ varchar (100),
    in paginaWeb_ varchar (50)
)
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

-- Eliminar
delimiter $$
create procedure sp_EliminarProveedores (
    in codigoProveedor_ int
)
begin 
    delete from Proveedores 
    where codigoProveedor = codigoProveedor_;
end $$
delimiter ;

-- EmailProveedor
-- Agregar
delimiter $$
create procedure sp_AgregarEmailProveedor (
    in EmailProveedorid int, 
    in email varchar(50), 
    in descripcion varchar(100), 
    in proveedorId int
)
begin 
    insert into EmailProveedor (
        EmailProveedorid, email, descripcion, proveedorId
    ) values (
        EmailProveedorid, email, descripcion, proveedorId
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmailProveedor ()
begin 
    select
        EmailProveedorid,
        email, 
        descripcion, 
        proveedorId
    from EmailProveedor;
end $$
delimiter ;

-- Buscar
delimiter $$

create procedure sp_BuscarEmailProveedor (
    in EmailProveedorid int
)
begin 
    select 
        EmailProveedorid,
        email, 
        descripcion, 
        proveedorId
    from EmailProveedor
    where EmailProveedorid = EmailProveedorid;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmailProveedor (
    in EmailProveedorid int
)
begin 
    delete from EmailProveedor 
    where EmailProveedorid = EmailProveedorid;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarEmailProveedor (
    in p_EmailProveedorid INT, 
    in p_email VARCHAR(50), 
    in p_descripcion VARCHAR(100), 
    in p_proveedorId INT
)
begin 
    update EmailProveedor
    set 		
        email = p_email,
        descripcion = p_descripcion, 
        proveedorId = p_proveedorId
    where EmailProveedorid = p_EmailProveedorid;
end $$
delimiter ;

-- TelefonoProveedor
-- Agregar
delimiter $$
create procedure sp_AgregarTelefonoProveedor (
    in TelefonoProveedorId int, 
    in numeroP varchar(50), 
    in numeroS varchar(50), 
    in Observaciones varchar(100), 
    in proveedorId int
)
begin 
    insert into TelefonoProveedor (
        TelefonoProveedorId, numeroP, numeroS, Observaciones, proveedorId
    ) values (
        TelefonoProveedorId, numeroP, numeroS, Observaciones, proveedorId
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarTelefonoProveedor ()
begin 
    select
        TelefonoProveedorId,
        numeroP, 
        numeroS, 
        Observaciones, 
        proveedorId
    from TelefonoProveedor;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarTelefonoProveedor (
    in TelefonoProveedorId int
)
begin 
    select 
        TelefonoProveedorId,
        numeroP, 
        numeroS, 
        Observaciones, 
        proveedorId
    from TelefonoProveedor
    where TelefonoProveedorId = TelefonoProveedorId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarTelefonoProveedor (
    in TelefonoProveedorId int
)
begin 
    delete from TelefonoProveedor 
    where TelefonoProveedorId = TelefonoProveedorId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarTelefonoProveedor (
    in p_TelefonoProveedorId INT, 
    in p_numeroP VARCHAR(50), 
    in p_numeroS VARCHAR(50), 
    in p_Observaciones VARCHAR(100), 
    in p_proveedorId INT
)
begin 
    update TelefonoProveedor
    set 		
        numeroP = p_numeroP,
        numeroS = p_numeroS, 
        Observaciones = p_Observaciones, 
        proveedorId = p_proveedorId
    where TelefonoProveedorId = p_TelefonoProveedorId;
end $$
delimiter ;

-- CategoriaProductos
-- Agregar
delimiter $$
create procedure sp_AgregarCategoriaProductos (
    in categoriaProductosId int, 
    in nombreCategoria varchar(65), 
    in descripcionCategoria varchar(100)
)
begin 
    insert into CategoriaProductos (
        categoriaProductosId, nombreCategoria, descripcionCategoria
    ) values (
        categoriaProductosId, nombreCategoria, descripcionCategoria
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCategoriaProductos ()
begin 
    select
        categoriaProductosId,
        nombreCategoria, 
        descripcionCategoria
    from CategoriaProductos;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarCategoriaProductos (
    in categoriaProductosId int
)
begin 
    select 
        categoriaProductosId,
        nombreCategoria, 
        descripcionCategoria
    from CategoriaProductos
    where categoriaProductosId = categoriaProductosId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCategoriaProductos (
    in categoriaProductosId int
)
begin 
    delete from CategoriaProductos 
    where categoriaProductosId = categoriaProductosId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarCategoriaProductos (
    in categoriaProductosId int, 
    in nombreCategoria varchar(65), 
    in descripcionCategoria varchar(100)
)
begin 
    update CategoriaProductos
    set 		
        nombreCategoria = nombreCategoria,
        descripcionCategoria = descripcionCategoria
    where categoriaProductosId = categoriaProductosId;
end $$
delimiter ;

-- Productos
-- Agregar
delimiter $$
create procedure sp_AgregarProductos (
    in productoId INT, 
    in nombreProducto VARCHAR(50), 
    in descripcionProducto VARCHAR(100), 
    in cantidadStock INT,
    in precioVentaMayor DECIMAL(10,2), 
    in precioCompra DECIMAL(10,2), 
    in distribuidorId INT, 
    in categoriaProductosId INT
)
begin
    insert into Productos (
        productoId, nombreProducto, descripcionProducto, 
        cantidadStock, precioVentaMayor, precioCompra, 
        distribuidorId, categoriaProductosId
    ) values (
        productoId, nombreProducto, descripcionProducto, 
        cantidadStock, precioVentaMayor, precioCompra, 
        distribuidorId, categoriaProductosId
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarProductos ()
begin 
    select
        productoId,
        nombreProducto, 
        descripcionProducto, 
        cantidadStock, 
        precioVentaMayor,
        precioCompra,
        distribuidorId,
        categoriaProductosId
    from Productos;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarProductos (
    in productoId int
)
begin 
    select 
        productoId,
        nombreProducto, 
        descripcionProducto, 
        cantidadStock, 
        precioVentaMayor,
        precioCompra,
        distribuidorId,
        categoriaProductosId
    from Productos
    where productoId = productoId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarProductos (
    in productoId int
)
begin 
    delete from Productos 
    where productoId = productoId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarProductos (
    in p_productoId INT, 
    in p_nombreProducto VARCHAR(50), 
    in p_descripcionProducto VARCHAR(100), 
    in p_cantidadStock INT, 
    in p_precioVentaMayor DECIMAL(10,2),
    in p_precioCompra DECIMAL(10,2), 
    in p_distribuidorId INT, 
    in p_categoriaProductosId INT
)
begin
    update Productos 
    set 
        nombreProducto = p_nombreProducto, 
        descripcionProducto = p_descripcionProducto, 
        cantidadStock = p_cantidadStock, 
        precioVentaMayor = p_precioVentaMayor, 
        precioCompra = p_precioCompra, 
        distribuidorId = p_distribuidorId, 
        categoriaProductosId = p_categoriaProductosId
    where productoId = p_productoId;
end $$
delimiter ;

-- Compras
-- Agregar
delimiter $$
create procedure sp_AgregarCompras (
    in p_compraId INT,
    in p_fechaCompra DATE,
    in p_descripcion VARCHAR(100),
    in p_totalCompra DECIMAL(10,2)
)
begin
    insert into Compras (
        compraId, fechaCompra, descripcion, totalCompra
    ) values (
        p_compraId, p_fechaCompra, p_descripcion, p_totalCompra
    );
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarCompras (
    in compraId int
)
begin 
    select 
        compraId,
        fechaCompra,
        descripcion,
        totalCompra
    from Compras
    where compraId = compraId;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCompras ()
begin
    select 
        compraId, fechaCompra, descripcion, totalCompra 
    from Compras;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarCompras (
    in p_compraId INT,
    in p_fechaCompra DATE,
    in p_descripcion VARCHAR(100),
    in p_totalCompra DECIMAL(10,2)
)
begin
    update Compras
    set 
        fechaCompra = p_fechaCompra,
        descripcion = p_descripcion,
        totalCompra = p_totalCompra
    where compraId = p_compraId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCompras (
    in p_compraId INT
)
begin
    delete from Compras 
    where compraId = p_compraId;
end $$
delimiter ;

-- DetalleCompra
-- Agregar
delimiter $$
create procedure sp_AgregarDetalleCompra (
    in detalleCompraId int, 
    in cantidadCompra int, 
    in productoId int, 
    in compraId int
)
begin 
    insert into DetalleCompra (
        detalleCompraId, cantidadCompra, productoId, compraId
    ) values (
        detalleCompraId, cantidadCompra, productoId, compraId
    );
end $$
delimiter ;


-- Listar
delimiter $$
create procedure sp_ListarDetalleCompra ()
begin 
    select
        detalleCompraId,
        cantidadCompra, 
        productoId, 
        compraId
    from DetalleCompra;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarDetalleCompra (
    in detalleCompraId int
)
begin 
    select 
        detalleCompraId,
        cantidadCompra, 
        productoId, 
        compraId
    from DetalleCompra
    where detalleCompraId = detalleCompraId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarDetalleCompra (
    in detalleCompraId int
)
begin 
    delete from DetalleCompra 
    where detalleCompraId = detalleCompraId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleCompra (
    in detalleCompraId int, 
    in cantidadCompra int, 
    in productoId int, 
    in compraId int
)
begin 
    update DetalleCompra
    set 		
        cantidadCompra = cantidadCompra,
        productoId = productoId, 
        compraId = compraId
    where detalleCompraId = detalleCompraId;
end $$
delimiter ;

-- Facturas
-- Agregar
delimiter $$
create procedure sp_AgregarFacturas (
    in facturaId int, 
    in fecha date, 
    in hora time, 
    in clienteId int, 
    in empleadoId int, 
    in total decimal(10,2)
)
begin 
    -- Verificar si el empleado existe
    if exists (select 1 from Empleados where empleadoId = empleadoId) then
        insert into Facturas (
            facturaId, fecha, hora, 
            clienteId, empleadoId, total
        ) values (
            facturaId, fecha, hora, 
            clienteId, empleadoId, total
        );
    else
        -- Lanzar un error si el empleado no existe
        signal sqlstate '45000' set message_text = 'Empleado no existe';
    end if;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarFacturas ()
begin 
    select
        facturaId,
        fecha, 
        hora, 
        clienteId, 
        empleadoId,
        total
    from Facturas;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarFacturas (
    in facturaId int
)
begin 
    select 
        facturaId,
        fecha, 
        hora, 
        clienteId, 
        empleadoId,
        total
    from Facturas
    where facturaId = facturaId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarFacturas (
    in facturaId int
)
begin 
    delete from Facturas 
    where facturaId = facturaId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarFacturas (
    in facturaId int, 
    in fecha date, 
    in hora time, 
    in clienteId int, 
    in empleadoId int, 
    in total decimal(10,2)
)
begin 
    update Facturas
    set 		
        fecha = fecha,
        hora = hora, 
        clienteId = clienteId, 
        empleadoId = empleadoId,
        total = total
    where facturaId = facturaId;
end $$
delimiter ;

-- DetalleFactura
-- Agregar
delimiter $$
create procedure sp_AgregarDetalleFactura (
    in detalleFacturaId int, 
    in facturaId int, 
    in productoId int
)
begin 
    insert into DetalleFactura (
        detalleFacturaId, facturaId, productoId
    ) values (
        detalleFacturaId, facturaId, productoId
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarDetalleFactura ()
begin 
    select
        detalleFacturaId,
        facturaId, 
        productoId
    from DetalleFactura;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarDetalleFactura (
    in detalleFacturaId int
)
begin 
    select 
        detalleFacturaId,
        facturaId, 
        productoId
    from DetalleFactura
    where detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarDetalleFactura (
    in detalleFacturaId int
)
begin 
    delete from DetalleFactura 
    where detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleFactura (
    in detalleFacturaId int, 
    in facturaId int, 
    in productoId int
)
begin 
    update DetalleFactura
    set 		
        facturaId = facturaId,
        productoId = productoId
    where detalleFacturaId = detalleFacturaId;
end $$
delimiter ;

-- Cargo
-- Agregar
delimiter $$
create procedure sp_AgregarCargo (
    in cargoId_ INT,
    in nombreCargo_ VARCHAR(30),
    in descripcionCargo_ VARCHAR(100)
)
begin
    insert into Cargos (
        cargoId, nombreCargo, descripcionCargo
    ) values (
        cargoId_, nombreCargo_, descripcionCargo_
    );
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCargos ()
begin
    select 
        cargoId, nombreCargo, descripcionCargo 
    from Cargos;
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarCargo (
    in cargoId_ INT,
    in nombreCargo_ VARCHAR(30),
    in descripcionCargo_ VARCHAR(100)
)
begin
    update Cargos 
    set 
        nombreCargo = nombreCargo_,
        descripcionCargo = descripcionCargo_
    where cargoId = cargoId_;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCargo (
    in cargoId_ INT
)
begin
    delete from Cargos 
    where cargoId = cargoId_;
end $$
delimiter ;

-- Triggers y Procedimientos adicionales

delimiter $$
create trigger calcular_precios_existencia 
before insert on DetalleCompra
for each row
begin
    declare total_compra decimal(10,2);

    -- Calcular el total de la compra
    set total_compra = (select precioCompra from Productos where productoId = new.productoId) * new.cantidadCompra;

    -- Actualizar precioVentaMayor con un 40% de ganancia en la tabla Productos
    update Productos
    set precioVentaMayor = total_compra * 1.4 / new.cantidadCompra
    where productoId = new.productoId;

    -- Actualizar precioCompra en la tabla Productos
    update Productos
    set precioCompra = total_compra / new.cantidadCompra
    where productoId = new.productoId;

    -- Actualizar existencia en la tabla Productos
    update Productos
    set cantidadStock = cantidadStock + new.cantidadCompra
    where productoId = new.productoId;
end $$
delimiter ;



delimiter $$
create procedure sp_agregarDProducto (
    in p_codigoProducto VARCHAR(15),
    in p_descripcionProducto VARCHAR(15),
    in p_existencia INT,
    in p_codigoTipoProducto INT,
    in p_codigoProveedor INT
)
begin
    -- Insertar el producto con valores predeterminados de precios y existencia
    insert into Productos (
        codigoProducto, descripcionProducto, 
        precioUnitario, precioDocena, precioMayor, 
        existencia, codigoTipoProducto, codigoProveedor
    ) values (
        p_codigoProducto, p_descripcionProducto, 
        0.00, 0.00, 0.00, p_existencia, 
        p_codigoTipoProducto, p_codigoProveedor
    );
end $$
delimiter ;

delimiter $$
create procedure sp_actualizarDProducto (
    in p_codigoProducto VARCHAR(15),
    in p_nuevaDescripcionProducto VARCHAR(15),
    in p_nuevaExistencia INT,
    in p_nuevoCodigoTipoProducto INT,
    in p_nuevoCodigoProveedor INT
)
begin
    -- Actualizar el producto
    update Productos
    set 
        descripcionProducto = p_nuevaDescripcionProducto,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    where codigoProducto = p_codigoProducto;

    -- Recalcular los precios y la existencia del producto
    call calcular_precios_existencia;
end $$
delimiter ;

-- Llamadas a los procedimientos para pruebas
call sp_AgregarClientes (1, '12457878', 'Harol', 'Luna', 'El basurero zona 3', '21215498', 'harolxluna4ever.com');
call sp_AgregarClientes (2, '15423874', 'Oliver', 'Sisimit', 'El basurero km. 22', '45875221', 'xdxsdadasd.com');

call sp_ListarClientes();

call sp_AgregarProveedores(1, '1234567890', 'Proveedor', 'S.A.', 'Calle 123, Ciudad', 'Proveedor de productos', 'Juan Perez', 'www.proveedor.com');
call sp_ListarProveedores();
call sp_ActualizarProveedores(1, '1234567890', 'Proveedor', 'S.A.', 'Calle 123, Ciudad', 'Proveedor de productos', 'Juan Perez', 'www.proveedor.com');

call sp_AgregarEmailProveedor(1, 'proveedor@ejemplo.com', 'Email principal', 1);
call sp_ListarEmailProveedor();
call sp_BuscarEmailProveedor(1);
call sp_EditarEmailProveedor(1, 'proveedor@ejemplo.com', 'Email de contacto', 1);

call sp_AgregarTelefonoProveedor(1, '1234567890', '0987654321', 'Numeros de contacto', 1);
call sp_ListarTelefonoProveedor();
call sp_BuscarTelefonoProveedor(1);
call sp_EditarTelefonoProveedor(1, '1234567890', '0987654321', 'Numeros de contacto actualizados', 1);

call sp_AgregarCategoriaProductos(1, 'Electrónicos', 'Productos electrónicos');
call sp_ListarCategoriaProductos();
call sp_BuscarCategoriaProductos(1);
call sp_EditarCategoriaProductos(1, 'Electrónicos', 'Productos electrónicos como televisores, radios, computadoras, etc.');

call sp_AgregarProductos(1, 'Producto 1', 'Descripción del producto 1', 100, 200.00, 150.00, 1, 1);
call sp_ListarProductos();
call sp_BuscarProductos(1);
call sp_EditarProductos(1, 'Producto 1 actualizado', 'Descripción del producto 1 actualizada', 100, 200.00, 150.00, 1, 1);

call sp_AgregarCompras(1, '2024-05-10', '5 libras de azúcar',  50.00);
call sp_ListarCompras();
call sp_BuscarCompras(1);
call sp_EditarCompras(1, '2024-05-10', 'Nueva descripción de la compra', 60.00);

call sp_AgregarDetalleCompra(1, 10, 1, 1);
call sp_ListarDetalleCompra();
call sp_BuscarDetalleCompra(1);
call sp_EditarDetalleCompra(1, 20, 1, 1);

call sp_AgregarCargo(1, 'Gerente', 'Responsable de la gestión y dirección de un área');
call sp_ListarCargos();
call sp_ActualizarCargo(1, 'Director', 'Encargado de supervisar todas las operaciones');

-- Agregar empleado para evitar error al insertar factura
call sp_AgregarEmpleado(1, 'Juan', 'Perez', 1500.00, 'Calle 123', 'Matutino', 1, 2);

call sp_AgregarFacturas(1, '2024-05-10', '08:00:00', 1, 1, 500.00);
call sp_ListarFacturas();
call sp_BuscarFacturas(1);
call sp_EditarFacturas(1, '2024-05-10', '08:00:00', 1, 1, 600.00);



call sp_AgregarDetalleFactura(1, 1, 1);
call sp_ListarDetalleFactura();
call sp_BuscarDetalleFactura(1);
call sp_EditarDetalleFactura(1, 1, 1);

SELECT * 
FROM DetalleFactura
JOIN Facturas ON DetalleFactura.facturaId = Facturas.facturaId
JOIN Clientes ON Facturas.clienteId = Clientes.codigoCliente
JOIN Productos ON DetalleFactura.productoId = Productos.productoId
WHERE Facturas.facturaId = 1;