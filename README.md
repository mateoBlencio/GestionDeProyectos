# Gestión de Proyectos

## Descripción

Esta API REST busca la ayuda en el seguimiento proyectos para una empresa, para así, utilizando entidades
como "Proyectos", "Tareas", "Empleado", "Rol", entre otras lograr la correcta asignación de empleados a proyectos y también tener un seguimiento de los cambios de estados que llevan estos proyectos y manteniendo un registro sobre en qué está trabajando cada empleado.

## Características

- Principales características y funcionalidades de la API:
  1. Autenticación utilizando Jwt
  2. Autorización para el acceso a endpoints a través de roles.
  3. CRUD de Proyectos, Tareas, Empleados, GruposTrabajo (EmpleadoXProyecto) y Roles.
  4. Utilización de base de datos MySQL.

## Tecnologías Utilizadas

1. Java
2. Spring boot
3. Spring boot Security
4. Json Web Token
5. MySQL
6. Lombok
7. Junit
8. Mockito

## Estructura del Proyecto

- Application: Dentro de esta carpeta de encuentran los controllers responsables de los endpoints y también las clases para sus respectivos request y response.
  - Clientes
  - Empleados
  - EmpleadoXProyecto (también llamados grupos de trabajo en este mismo documento)
  - Estados de Proyectos
  - Proyectos
  - RegistroCambios
  - RolEmpleados
  - Tareas
- Auth: Dentro de la misma se encuentran las clases con sus respectivos métodos para la autenticación de usuarios así como también para su configuración y enpoints de login y register.
- Model: Contiene todas las entidades responsables del funcionamiento de la api.
- Repositories: Contiene los repositorios de las entidades de la api.
- Service: Posee los servicios utilizados para llevar a cabo el funcionamiento y validaciones necesarias.

## Endpoints

| Entidad             | Método | Endpoint                                 | Descripción                                                                                                                                                                            |
|---------------------|--------|------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Clientes            | GET    | `/clientes`                              | Obtener información de todos los clientes                                                                                                                                              |
|                     | GET    | `/clientes/{id}`                         | Obtener información de un cliente a través de su número identificador                                                                                                                  |
|                     | POST   | `/clientes`                              | Crear un nuevo cliente                                                                                                                                                                 |
|                     | PATCH  | `/clientes/{id}`                         | Actualizar un cliente (se puede actualizar completo o parcialmente)                                                                                                                    |
|                     | DELETE | `/clientes/{id}`                         | Eliminar cliente                                                                                                                                                                       |
| Empleados           | GET    | `/empleados`                             | Obtener información sobre empleados                                                                                                                                                    |
|                     | GET    | `/empleados/{id}`                        | Obtener información de un empleado a través de su número identificador                                                                                                                 |
|                     | POST   | `/empleados`                             | Crear un nuevo empleado                                                                                                                                                                |
|                     | PATCH  | `/empleados/{id}`                        | Actualizar un empleado                                                                                                                                                                 |
|                     | DELETE | `/empleados/{id}`                        | Eliminar empleado                                                                                                                                                                      |
| EmpleadoXProyecto   | GET    | `/empleados/proyectos`                   | Obtener sobre todos los registros                                                                                                                                                      |
|                     | GET    | `/empleados/proyectos`                   | Obtener sobre registro pasando un id de proyecto e id de empleado como parámetro                                                                                                       |
|                     | POST   | `/empleados/proyectos`                   | Crear un nuevo registro asignando un empleado a un proyecto siempre y cuando el proyecto no se encuentre en estado finalizado y el empleado no esté trabajando en más de dos proyectos |
|                     | DELETE | `/empleados/proyectos/`                  | Eliminar recurso con parámetros, también puede ser visto como eliminar a un integrante del grupo                                                                                       |
| Estados             | GET    | `/estados`                               | Obtener información sobre los estados creados                                                                                                                                          |
|                     | POST   | `/estados`                               | Crear un nuevo estado                                                                                                                                                                  |
|                     | PATCH  | `/estados/{id}`                          | Actualizar información de un estado                                                                                                                                                    |
| Proyectos           | GET    | `/proyectos`                             | Obtener información de proyectos                                                                                                                                                       |
|                     | GET    | `/proyectos/{id}`                        | Obtener información de un solo proyecto                                                                                                                                                |
|                     | POST   | `/proyectos`                             | Crear un nuevo proyecto                                                                                                                                                                |
|                     | PATCH  | `/proyectos/{id}`                        | Actualizar un proyecto                                                                                                                                                                 |
|                     | DELETE | `/proyectos/{id}`                        | Eliminar un proyecto                                                                                                                                                                   |
|                     | PATCH  | `/proyectos/{id}`                        | Actualizar el estado de un proyecto                                                                                                                                                    |
| Registro de Cambios | GET    | `/registrosCambiosEstados`               | Obtener información sobre todos los cambios de todos los proyectos activos                                                                                                             |
|                     | GET    | `/registrosCambiosEstados/{nroProyecto}` | Obtener información sobre los cambios de estado de un proyecto                                                                                                                         |
| Roles               | GET    | `/roles`                                 | Obtener información de los roles actuales                                                                                                                                              |
|                     | POST   | `/roles`                                 | Crear un nuevo rol                                                                                                                                                                     |
|                     | PATCH  | `/roles/{id}`                            | Actualizar un rol                                                                                                                                                                      |
|                     | DELETE | `/roles/{id}`                            | Eliminar un rol                                                                                                                                                                        |
| Tareas              | GET    | `/tareas`                                | Obtener información sobre todas las tareas                                                                                                                                             |
|                     | GET    | `/tareas/{nroProyecto}`                  | Obtener información sobre las tareas de un proyecto determinado                                                                                                                        |
|                     | GET    | `/tareas/tareasXProyectos`               | Obtener información de una tarea, el id de la tarea y el ide de del proyecto deben ser pasados como parámetros                                                                         |
|                     | POST   | `/tareas`                                | Crear una nueva tarea                                                                                                                                                                  |
|                     | PATCH  | `/tareas/{id}`                           | Actualizar una tarea pasando id de tarea e id del proyecto por parámetro                                                                                                               |
|                     | DELETE | `/tareas/{id}`                           | Eliminar una tarea                                                                                                                                                                     |
