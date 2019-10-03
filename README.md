Welcome to TriviaVet
====================

Este es un proyecto utilizado para enseñar/aprender algunas herramientas en el marco de la asignatura "Análisis y Diseño de Algoritmos" del Departamento de Computación de la UNRC.

#### Integrantes
* Agustin Borda.
* Valentin Tobares.
* Nicolas D'Alessandro.

#### Run
```Bash
	cd triviavet
	mvn install
	mvn db-migrator:reset
	./run.sh
```
En otra terminal
```Bash
	cd triviavet/client
	npm install
	expo-cli r
```

#### Database configuration

	Create an archive named "database.properties" in ~/src/main/resources/ with the following content;

```Java Properties
	development.driver=com.mysql.jdbc.Driver
	development.username=<username>
	development.password=<password>
	development.url=jdbc:mysql://localhost/trivia_dev

	development.test.driver=com.mysql.jdbc.Driver
	development.test.username=<username>
	development.test.password=<password>
	development.test.url=jdbc:mysql://localhost/trivia_test
```


# Licence

This project is licensed under the MIT License - see the LICENSE.md file for details
