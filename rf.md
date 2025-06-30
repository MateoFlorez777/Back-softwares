# Requisitos Funcionales del Sistema de comercio electronico (Software - S)

Este documento redacta la informacion a detalle del sistema de comercio para tener
un gran análisis y entendimiento del avance del desarrollo del proyecto formativo.

## Índice

1. Introducción
2. Funciones
3. Requisitos funcionales
4. Tecnologías propuestas
5. Anexos

    5.1. Documento historias de usuario
    5.2. Documento Mvp
    5.3. Mockups o prototipo de interfaz

6. Descripcion de avances
7. Próximos avances


## 1. Introducción

La solución para "Mantenimiento de Computadores" consiste en la 
implementación de un software integral que optimizará la venta de 
componentes y periféricos y la gestión de su inventario. Este sistema integrará una 
plataforma de tienda en línea (comercio electrónico) para ampliar su alcance 
de mercado y permitir ventas digitales, junto con un robusto módulo de gestión de 
inventarios que permitirá organizar, controlar y visualizar el stock de productos en 
tiempo real. Esta herramienta transformará los procesos manuales y propensos a 
errores en un sistema digitalizado, mejorando significativamente la eficiencia 
operativa y la rapidez del negocio.


## 2. Funciones

1.	Gestión de productos

•	Obtener lista de productos: Los usuarios pueden explorar los productos disponibles.

•	Filtrar y ordenar: Filtrar productos por categorías, precio, etc., y ordenarlos por precio.

•	Paginación: Mostrar productos en varias páginas para mejorar el rendimiento y la experiencia del    usuario.

•	Detalles del producto: Ver información detallada sobre un producto específico.



2.	Gestión del carrito

•	Añadir artículo al carrito: Añadir productos al carrito de compras.

•	Actualizar artículo del carrito: Modificar la cantidad de artículos o eliminarlos del carrito.



3.	Proceso de pago

•	Aplicar cupón: Los usuarios pueden aplicar cupones de descuento a su carrito.

•	Añadir nueva dirección: Añadir y gestionar direcciones de envío durante el pago.

•	Pasarelas de pago: Pagar con opciones de pago como MercadoPago.




4.	Historial de pedidos

•	Ver pedidos anteriores: Los usuarios pueden ver una lista de sus compras anteriores y los detalles de los pedidos.

•	Cancelar pedido


5.	Gestión de cuentas de usuario

•	Gestionar datos personales, ver el historial de pedidos y realizar un seguimiento de la configuración de la cuenta.


6.	Reseñas y valoraciones

•	Escribir una reseña


7.	Lista de deseos

•	Añadir y eliminar productos de la lista de deseos



8.	Cuentas de Usuario

•	Un usuario tiene permitido la creación de una cuenta de usuario para cliente con la función de poder comprar, tener registros de pedidos y listas de deseo.


## 3. Requisitos funcionales

RF01- Generar informes: Se requiere un módulo para que el rol empresa pueda generar informes de manera automática a través de estadísticas y diagramas

RF02- Métodos de Pago: Se requiere un módulo para que puedan tener métodos de pago que son una parte crucial en cualquier plataforma de comercio electrónico
	
RF03 - Manejo de inventario: Se requiere un módulo para poder realizar el manejo del inventario de la empresa en cuestión. El sistema deberá poder realizar el control de inventarios, el usuario deberá ingresar los productos que entran para poder tener el registro y poder llevar un orden en el inventario. El sistema deberá poder realizar los siguientes requerimientos en el rol de empresa.	
	
RF04 - Filtros y medios facilitadores de búsqueda: Se requiere un módulo para que los usuarios puedan tener varias opciones para hacer sus búsquedas.
	
RF05 - Gestión y comunicación con clientes: Se requiere un módulo para que el rol empresa pueda gestionar y comunicarse de manera sencilla con los clientes.
	
RF06 – Acceso: Se requiere un módulo para que los usuarios puedan ingresar al sistema.

RF07 - Capacidad de realizar un seguimiento detallado de las existencias: La habilidad de registrar y monitorear con precisión la cantidad, ubicación, estado y movimiento de los productos en inventario.
	
RF08 - Capacidad de registrar y dar seguimiento a las consultas de los clientes: La funcionalidad del software para capturar, almacenar y gestionar de manera efectiva las interacciones y solicitudes de los clientes.
	
RF09 - Herramientas de análisis y generación de informes para evaluar la satisfacción del cliente: Implica que el software cuente con funcionalidades que permitan recopilar datos relevantes sobre la satisfacción del cliente y generar informes que faciliten la evaluación de dicha satisfacción.
	
RF10 - Interfaz de usuario intuitiva y amigable para la navegación y búsqueda de productos: Implicaría que los usuarios puedan acceder de manera sencilla y eficiente a la información relacionada con el inventario de productos provenientes de los distintos proveedores.

RF11 - Capacidad de ver detalles y especificaciones de los productos: Implica la posibilidad de acceder a información detallada sobre cada artículo proporcionado por distintos proveedores.
	
RF12 - Capacidad de ver reseñas y calificaciones de otros clientes: Implicaría la posibilidad de acceder a opiniones y valoraciones sobre productos provenientes de distintos proveedores por parte de otros usuarios o clientes.
	
RF13 - Proceso de compra sencillo y seguro: Implica la implementación de pasos claros y fáciles para que los usuarios puedan adquirir los productos de los proveedores de manera eficiente y confiable.
	
RF14 - Posibilidad de crear listas de deseos: Implicar permitir a los usuarios guardar y organizar los productos provenientes de distintos proveedores que les interesan para una posible compra futura.
	
RF15 - Recibir notificaciones sobre productos de interés: Implica la posibilidad de configurar alertas personalizadas para estar informado sobre cambios en la disponibilidad, precios u otras actualizaciones relevantes de los productos provenientes de distintos proveedores.
	
RF16 - Abarcar la gestión eficiente del catálogo de productos: Implica la capacidad de agregar, organizar, actualizar y presentar de manera clara y estructurada la información detallada de los productos provenientes de distintos proveedores.
	
RF17 - La capacidad de agregar, editar o eliminar productos: Implica la posibilidad de realizar cambios en el catálogo de productos de manera ágil y precisa.
	
RF18 - Herramientas para el seguimiento de inventario, la gestión de pedidos y envíos: mantener un control preciso de la disponibilidad de productos y gestionar eficientemente el proceso de recepción, preparación y envío de pedidos.

RF19 - La generación de informes sobre ventas, clientes y desempeño del negocio: Implica la capacidad de recopilar, analizar y presentar datos relevantes para evaluar el rendimiento comercial.

RF20 - Autenticación segura: Implica las implementaciones de mecanismos robustos para verificar la identidad de los usuarios y proteger el acceso a la plataforma.


## 4. Tecnologías propuestas

Herramientas: 
●	intellij idea
●	Vs code
●	node js
●	MySQL
Backend:
●	Spring Boot
●	Spring Security
●	Java Mail Sender
●	JWT
●	MercadoPago
Frontend:
●	React
●	JavaScript
●	Tailwind CSS
●	React Router DOM
●	Axios
Base de Datos:
●	MySQL


## 5. Anexos

        Documento historias de usuario(https://drive.google.com/drive/folders/1Ev5NzI4228Rdg0LwGog9lriN-bIfekhJ)
        
        Documento Mvp(https://drive.google.com/drive/folders/1Ev5NzI4228Rdg0LwGog9lriN-bIfekhJ)

        Mockups o prototipo de interfaz(https://www.figma.com/proto/7oNZ00k0xWR2UEsTA3K4ll/Untitled?node-id=5-14&starting-point-node-id=5%3A14&t=vqdJrfTruwu63gpU-1)


## 6. Descripcion de avances

Esta carpeta de proyecto contiene el avance del desarrollo de comercio electronico en el lenguaje java con spring boot, las que contiene:

    o- Auntenticacion por jwt para registro de usuarios e inicio de sesion

    0- Base de datos completa creada desde hibernate 

    0- Controladores y servicios para los productos

    0- correos para entregar claves secretas al usuario

## 7. Próximos avances

    Como proximo avance a mas tardar el 1 de junio, se actualizará y se subiran
    los controladores, servicios, repositorios, implementaciones y proteccion de rutas
    para el carro de compras, lista de deseos, productos, pagos, mercado pago para así
    dar como finalizado el desarrollo del back del proyecto formativo.




