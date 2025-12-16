# 💳 Sistema de Cobros de Cartera **CrediYa**

Proyecto académico desarrollado en **Java (consola)** que permite gestionar empleados, clientes, préstamos y pagos para la empresa ficticia **CrediYa S.A.S.**, utilizando **POO**, **archivos de texto** y **MySQL (JDBC)**.

---

## 📌 Descripción General

CrediYa S.A.S. otorga créditos personales y actualmente maneja su información de manera manual. Este sistema digitaliza el control de:

* Empleados
* Clientes
* Préstamos
* Pagos
* Reportes

El sistema está diseñado bajo una **arquitectura modular**, siguiendo buenas prácticas de programación y permitiendo la persistencia tanto en **base de datos MySQL** como en **archivos `.txt`**.

---

## 🎯 Objetivo General

Desarrollar un sistema de consola en Java que permita gestionar préstamos y cobros de cartera aplicando:

* Programación Orientada a Objetos (POO)
* Conexión a base de datos MySQL mediante JDBC
* Manejo de archivos
* Principios SOLID
* Manejo adecuado de excepciones
* Organización por capas y paquetes

---

## 🛠️ Tecnologías Utilizadas

* **Java SE**
* **MySQL**
* **JDBC**
* **VS Code / IntelliJ IDEA**
* **Git & GitHub**

---

## 📁 Estructura del Proyecto

```
CREDIYA
│
├── src
│   └── main
│       └── java
│           └── com
│               └── cristian
│                   ├── conector
│                   │   ├── baseDatosMysql.sql
│                   │   └── conexionMysql.java
│                   │
│                   ├── data
│                   │   ├── clientes.txt
│                   │   ├── empleados.txt
│                   │   ├── pagos.txt
│                   │   └── prestamos.txt
│                   │
│                   ├── servies
│                   │   ├── clienteService.java
│                   │   ├── empleadoService.java
│                   │   ├── pagoService.java
│                   │   ├── prestamoService.java
│                   │   └── reportesService.java
│                   │
│                   ├── view
│                   │   ├── ClienteMenu.java
│                   │   ├── EmpleadoMenu.java
│                   │   ├── PagoMenu.java
│                   │   ├── PrestamosMenu.java
│                   │   └── ReportesMenu.java
│                   │
│                   └── Main.java
│
├── test
├── target
├── pom.xml
└── README.md
```

---

## 🧩 Módulos del Sistema

### 👨‍💼 Módulo de Empleados

Funciones:

* Registrar empleados
* Consultar empleados
* Persistencia en MySQL y archivo `empleados.txt`

Atributos:

* id
* nombre
* documento
* rol
* correo
* salario

---

### 👥 Módulo de Clientes

Funciones:

* Registrar clientes
* Listar clientes
* Consultar préstamos asociados

Atributos:

* id
* nombre
* documento
* correo
* teléfono

Persistencia en:

* MySQL
* `clientes.txt`

---

### 💰 Módulo de Préstamos

Funciones:

* Crear préstamos
* Calcular automáticamente:

  * Monto total con interés
  * Valor de la cuota mensual
* Cambiar estado del préstamo
* Simulación de préstamos
* Consultar préstamos por cliente o empleado

Estados:

* ACTIVO
* VENCIDO
* PAGADO

Persistencia en:

* MySQL
* `prestamos.txt`

---

### 💵 Módulo de Pagos

Funciones:

* Registrar abonos
* Actualizar saldo pendiente
* Mostrar histórico de pagos

Persistencia en:

* MySQL
* `pagos.txt`

---

### 📊 Módulo de Reportes

Permite:

* Consultar préstamos activos
* Consultar préstamos vencidos
* Listar clientes morosos
* Uso de **Lambda Expressions** y **Stream API** para filtrado de datos

---

## 🗄️ Base de Datos (MySQL)

### Script Base

```sql

docker run --name crediya_container -e MYSQL_ROOT_PASSWORD=credienmin -p 3309:3306 -d mysql:8.4.3

docker start crediya_container    
docker exec -it crediya_container mysql -h localhost -u root -P 3306 -p                                                                                                                                             

CREATE DATABASE crediya_data;
USE crediya_data;

CREATE TABLE empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) NOT NULL UNIQUE,
    rol ENUM('Administrador', 'Asesor', 'Contador', 'Supervisor') NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    salario DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) NOT NULL UNIQUE,
    correo VARCHAR(80) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empleado_id INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuotas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (empleado_id) REFERENCES empleado(id)
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id)
);

INSERT INTO empleado (nombre, documento, rol, correo, salario) VALUES
('Diana Lopez', '1002003005', 'Asesor', 'dianalopez@crediya.com', 2400000),
('Jorge Medina', '1002003006', 'Asesor', 'jorgemedina@crediya.com', 2600000),
('Paola Castro', '1002003007', 'Contador', 'paolacastro@crediya.com', 3100000),
('Andres Leon', '1002003008', 'Supervisor', 'andresleon@crediya.com', 3300000);



INSERT INTO clientes (nombre, documento, correo, telefono) VALUES
('Camilo Torres', '900100204', 'camilotorres@gmailcom', '3011111111'),
('Natalia Rios', '900100205', 'nataliarios@gmailcom', '3012222222'),
('Felipe Herrera', '900100206', 'felipeherrera@gmailcom', '3013333333'),
('Andrea Munoz', '900100207', 'andreamunoz@gmailcom', '3014444444'),
('Sebastian Cruz', '900100208', 'sebastiancruz@gmailcom', '3015555555'),
('Valentina Pardo', '900100209', 'valentinapardo@gmailcom', '3016666666'),
('Ricardo Molina', '900100210', 'ricardomolina@gmailcom', '3017777777'),
('Juliana Velez', '900100211', 'julianavelez@gmailcom', '3018888888'),
('Sharik Mayorga', '900100203', 'sharikmayorga@gmailcom', '3004445566');


INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado) VALUES
(1, 1, 6000000, 4.2, 6, '2024-10-10', 'VENCIDO'),
(2, 2, 9000000, 5.1, 9, '2025-01-20', 'ACTIVO'),
(3, 3, 7000000, 4.8, 7, '2024-08-15', 'VENCIDO'),
(4, 2, 11000000, 6, 11, '2025-02-01', 'ACTIVO'),
(5, 1, 5000000, 3.9, 5, '2024-05-05', 'VENCIDO'),
(6, 4, 15000000, 6.5, 15, '2025-03-10', 'ACTIVO'),
(9, 3, 4000000, 3.5, 4, '2024-06-01', 'PAZ_Y_SALVO'),
(8, 3, 8500000, 4.7, 8, '2024-07-20', 'VENCIDO');


INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES
(1, '2024-11-10', 1000000),
(2, '2025-02-20', 1000000),
(2, '2025-03-20', 1000000),
(3, '2024-09-15', 1000000),
(3, '2024-10-15', 1000000),
(5, '2024-06-05', 1000000),
(7, '2024-07-01', 1000000),
(7, '2024-08-01', 1000000),
(7, '2024-09-01', 1000000),
(7, '2024-10-01', 1000000);
```

---

## ▶️ Ejecución del Proyecto

1. Crear la base de datos en MySQL usando el script SQL.
2. Configurar credenciales en `conexionMysql.java`.
3. Ejecutar la clase `Main.java`.
4. Navegar por los menús interactivos en consola.

---

## 📄 Archivos Generados

El sistema genera automáticamente archivos de respaldo:

* `empleados.txt`
* `clientes.txt`
* `prestamos.txt`
* `pagos.txt`

Estos archivos se encuentran en:

```
src/main/java/com/cristian/data/
```

---

## 📐 UML (Sugerido)

Clases principales:

* Empleado
* Cliente
* Prestamo
* Pago
* Servicios asociados

(Relaciones: composición y asociación)

---

## 🚀 Mejoras Futuras

* Interfaz gráfica (JavaFX)
* Control de roles
* Autenticación
* Exportación a PDF / Excel
* Pruebas unitarias (JUnit)

---

## 👨‍💻 Autor

**Cristian Mayorga**
Proyecto académico – Java & MySQL

---

## 📌 Repositorio

Este proyecto puede ser versionado y publicado en GitHub como evidencia del aprendizaje en:

* Java
* JDBC
* POO
* Persistencia de datos

---

✨ *Sistema desarrollado con fines educativos* ✨
