# IPC1-B-Proyecto2_-202405103-_1S26
Sancarlista Academy
Descripción

Sancarlista Academy es una aplicación desarrollada en Java que permite la gestión académica de cursos, instructores y estudiantes mediante una interfaz gráfica basada en Swing. El sistema está diseñado siguiendo el patrón de arquitectura Modelo-Vista-Controlador (MVC) e implementa principios de Programación Orientada a Objetos como herencia, polimorfismo e interfaces.

El proyecto permite la administración de información académica, incluyendo la creación de usuarios, gestión de cursos y generación de reportes, utilizando estructuras de datos estáticas y sin el uso de librerías dinámicas como ArrayList o HashMap.

Objetivo

Desarrollar una aplicación funcional que integre conceptos fundamentales de programación, permitiendo la gestión académica de manera organizada, segura y estructurada.

Tecnologías utilizadas
Java
Java Swing (Interfaz gráfica)
Serialización de objetos (.ser)
JTable para visualización de datos
Arquitectura del sistema

El sistema está basado en el patrón MVC:

Modelo: Manejo de datos (SistemaAcademy, Usuario, Curso, etc.)
Vista: Interfaces gráficas (LoginFrameTop, DashboardAdminTop, etc.)
Controlador: Lógica del sistema (AppController)
Funcionalidades principales
Autenticación
Inicio de sesión por usuario y contraseña
Redirección según rol
Administración
Crear instructores
Crear estudiantes
Crear cursos
Visualizar datos en tablas
Eliminación de registros
Visualización de datos
Uso de JTable para mostrar:
Usuarios
Cursos
Bitácora
Reportes
Generación de reportes en formato PDF
Persistencia
Almacenamiento de datos mediante serialización en archivos .ser
Usuario inicial

Para acceder al sistema:

Usuario: admin
Contraseña: IPC1A
Estructura del proyecto
app/
controlador/
modelo/
vista/
util/
interfaces/
hilos/
Programación Orientada a Objetos

El proyecto implementa:

Herencia: Usuario → Administrador, Instructor, Estudiante
Polimorfismo: Método getRol()
Interfaces: Crud<T>
Restricciones del proyecto

El sistema cumple con las siguientes restricciones:

No se utilizan estructuras dinámicas como:
ArrayList
LinkedList
HashMap
List
No se utilizan métodos de ordenamiento de librerías
Se utilizan arreglos estáticos y ciclos for
Requisitos para ejecutar
Java JDK 8 o superior
IDE (NetBeans, IntelliJ o Eclipse)
Ejecución
Abrir el proyecto en el IDE
Ejecutar la clase:
Main.java
Iniciar sesión con las credenciales indicadas
Notas importantes
Si el sistema presenta errores al iniciar sesión, eliminar el archivo .ser para reiniciar los datos
El sistema guarda automáticamente los cambios realizados
Autor

Proyecto desarrollado como práctica académica para el curso de Introducción a la Programación y Computación.
