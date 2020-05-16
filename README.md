# UNI-WHEELS

## Estudiantes:
+ Carlos Manuel Murillo Ibañez
+ Orlando Antonio Gelves Kerguelen
+ Andres Guillermo Rocha Mendez


## Descripción del Problema
La movilidad en la ciudad es una situación muy agoviante para toda la comunidad, gran parte de los universitarios de la ciudad se transportan a sus universidades usando los medios de trasnportes públicos existentes. Estos son inseguros, incomodos y no generan un nivel de satisfacción alto con sus usuarios. Por lo cual, muchos estudiantes deciden llegar a la universidad en un automovil particular. Varios de ellos, prestan el servicio de transporte a otros compañeros que viven cerca a ellos o pasan por un ruta similar a la que ellos usualmente cogen en su recorrido. Este servicio es prestado a través de grupos de WhatsApp de estudiantes llamados "Wheels", pero por su poca privacidad de información personal y falta de comodidad a la hora de solicitar el servicio, muchos estudiantes no deciden tomarlo.

Nosotros como grupo de trabajo identificamos esta problemática y decidimos emprender una idea de construir una aplicación que le dará la posibilidad a cualquier miembro de alguna comunidad universitaria de Bogotá, solicitar un servicio de transporte en un ambiente seguro, cómodo y confiable. En donde, el conductor que le brindará el servicio, será otro miembro de alguna  comunidad universitaria que se moviliza en su carro particular.


## Resumen

Un usuario PASAJERO [miembro activo de la Universidad] quiere llegar a su universidad por lo cual entra a la aplicación y selecciona su lugar de destino. Inmediatamente, la app buscará que conductores activos van a pasar por la zona donde está ubicado el PASAJERO y tienen como destino el mismo lugar. Terminada la búsqueda, el PASAJERO podrá ver en pantalla cuantas posbiles opciones de rutas tiene para llegar a su destino, cada opción presentará sus características principales como costo,distancia al punto de encuentro,tiempo de llegada del conductor al punto de encuentro. Con base en esta información, el PASAJERO elegirá la ruta que más se acomode a su gusto.

Después de seleccionar la ruta a tomar,si el CONDUCTOR seleccionado decide llevar al PASAJERO, este verá la información principal del conductor que lo va a recoger(nombre,universidad a la que pertenece,placas de carro,modelo del carro). Además, verá en un mapa la ubicación en tiempo real del CONDUCTOR. También, el CONDUCTOR podrá ver la información básica del PASAJERO sin comprometer informaicón personal de alguno de los dos. 

Cuando el PASAJERO se sube al carro, el CONDUCTOR debe registrarlo en la aplicación y al momento de finalizar el viaje también deberá hacerlo. Por último, al final del recorrido, el usuario paga y se le mostrará una encuesta donde calificará la calidad del servicio prestado por el CONDUCTOR.  

Nuestra aplicación le dará una sensación de seguridad a los dos usuarios con respecto a los demás transportes ya que solo esta tratando con individuos pertenecientes a la comunidad universitaria también una forma fácil y cómoda de transportarse a la universidad y de poder obtener dinero extra.	
 
## Modelo Entidad - Relación

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Diagrama%20%20Entidad_relacion.PNG)

## Diagrama de Clases

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Diagrama%20de%20clases.PNG)

## Diagrama de Componentes

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Diagrama%20de%20Componentes.PNG)


## Caso de uso

### Usuario

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Casos%20de%20Uso-usuario.PNG)


### Conductor

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Casos%20de%20Uso-conductor.PNG)

### Pasajero

![](https://github.com/ARSW-2020-1-UNIWheels/UNIWheels/blob/master/resources/Casos%20de%20Uso-pasajero.PNG)

## Mockups
[Aplicación en NinjaMock](https://ninjamock.com/s/49239Fx)


## Historias de usuario
+ COMO usuario QUIERO poder logearme PARA PODER recibir los servicios que ofrece la aplicación web.  
+ COMO pasajero QUIERO solicitar un Wheels PARA PODER coger un servicio de transporte barato y de calidad hacia mi universidad.  
+ COMO conductor QUIERO ofrecer mi carro como Wheels PARA PODER sustentar el alto costo del parqueadero de la universidad.   
+ COMO pasajero QUIERO poder ver el tiempo real donde esta mi wheels PARA PODER salir a tiempo de mi casa y llegar cerca de su ruta a tiempo.  

## Manual de Usuario
Bienvenido a UNI-WHEELS, para que tengas una buena experiencia te recomendamos crear tu propia cuenta en la opción de registrar. Los datos que debes ingresar son los siguientes:
+ Nombre de Usuario
+ Correo (debe ser tu correo institucional de la Universidad)
+ Universidad
+ Dirección 
+ Contraseña

Cuando ya estes adentro de la aplicación, tendrás la opción de elegir que servicio quieres tomar: PASAJERO o CONDUCTOR. 

Si eliges PASAJERO, se te mostrará en pantalla la información de todos los conductores activos de la aplicación; cada uno te dirá su punto de origen, destino, universidad a la que pertenece y el costo del viaje. Ten en cuenta, que el costo de cada viaje lo establece el conductor,por eso te podrás encontrar con tarifas de diferente valor. Elige el CONDUCTOR que se ajuste a tus necesidades y enviale una solicitud. Cuando tu solicitud sea aceptada, podrás ver la ubicació en tiempo real de tu CONDUCTOR. 
No olvides al terminar el viaje, calificarlo.

Si eliges CONDUCTOR, te dirigirás a una nueva pantalla donde vas a ingresar la información básica de tu viaje: origen, destino, carro que vas a usar(recuerda que puedes registrar varios carros en la aplicación)y por último el costo; será a tu criterio. 
Cuando inicie tu viaje, verás la información de Tus Solicitudes(PASAJEROS que soliciten unirse a tu viaje) y Tus Pasajeros.
Siempre tendrás la opción de rechazar o aceptar una solicitud, solo ten en cuenta que máximo podrás llevar cuatro usuarios contigo.

Cada vez que aceptes un PASAJERO, verás la información de su ubicación en un mapa, así podrás llegar facíl al punto de encuentro. Cuando termines tu recorrido, pulsa el botón de finalizar viaje y califica a tus PASAJEROS.

Además de ofrecer un serivicio, como CONDUCTOR, tendrás la opción de revisar los carros que tienes inscritos en la aplicación. Solo tienes que ingresar en la opción que se encuentra en la parte superior derecha que dice "Mis Carros".



## Estado del proyecto
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e193b7b32c3342ccadb0c7f681896b06)](https://www.codacy.com/gh/ARSW-2020-1-UNIWheels/UNIWheels?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ARSW-2020-1-UNIWheels/UNIWheels&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/ARSW-2020-1-UNIWheels/UNIWheels.svg?style=svg)](https://circleci.com/gh/ARSW-2020-1-UNIWheels/UNIWheels)
+ [Despliegue en Heroku](https://uniwheels.herokuapp.com/)
