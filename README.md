Explicación de un pool de conexiones en Java
Un pool de conexiones (conocido como connection pool en inglés) en Java es una técnica utilizada para mejorar la eficiencia en la gestión de conexiones a bases de datos.

Cuando una aplicación Java necesita conectarse a una base de datos, generalmente se crea una conexión entre la aplicación y la base de datos. Sin embargo, crear y cerrar conexiones de forma repetitiva puede ser costoso en términos de rendimiento y recursos, por lo que el uso de un pool de conexiones se vuelve una solución más efectiva.

Un pool de conexiones es un conjunto de conexiones a la base de datos que se mantiene en espera y disponible para su reutilización en caso de que la aplicación necesite una conexión. En lugar de crear una nueva conexión cada vez que se necesita acceder a la base de datos, la aplicación solicita una conexión disponible en el pool. Una vez que se completa la tarea, la conexión se devuelve al pool en lugar de cerrarse, para que pueda ser reutilizada más adelante.

El uso de un pool de conexiones en Java puede mejorar significativamente el rendimiento y la eficiencia de una aplicación que requiere acceso a una base de datos, ya que reduce la sobrecarga de crear y cerrar conexiones de forma repetitiva. Además, el pool de conexiones puede configurarse para limitar el número máximo de conexiones que se pueden abrir, lo que ayuda a evitar problemas de saturación y sobrecarga del servidor de base de datos.



-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Ejemplo de un pool de conexiones en Java con Apache Commons DBCP

Primero, se debe agregar la dependencia de `**Apache Commons DBCP**` en el archivo **`pom.xml`** o `**build.gradle**`, según el sistema de construcción utilizado. A continuación, se crea una instancia de `BasicDataSource`, que es una implementación de **`DataSource`** que utiliza un pool de conexiones:


    `BasicDataSource ds = new BasicDataSource();
    ds.setUrl("jdbc:mysql://localhost:3306/mydb");
    ds.setUsername("username");
    ds.setPassword("password");
    ds.setInitialSize(5);
    ds.setMaxTotal(10);` 

En este ejemplo, se configura el número inicial de conexiones en el pool en 5 y el número máximo de conexiones en 10. Luego, se puede utilizar el **`DataSource`** para obtener una conexión a la base de datos:





     `try (Connection connection = ds.getConnection()) {
            // Realizar consultas utilizando la conexión
        } catch (SQLException e) {
            // Manejar excepciones
        }` 

Una vez que se ha utilizado la conexión, esta se devuelve automáticamente al pool y está disponible para su reutilización en el futuro.

De esta manera, el uso de un pool de conexiones con **`Apache Commons DBCP`** permite mejorar la eficiencia en la gestión de conexiones a la base de datos y reducir la sobrecarga de crear y cerrar conexiones de forma repetitiva.