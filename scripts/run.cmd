javac -d ..\apputilities\target\classes ..\apputilities\src\main\java\apputilities\*.java
javac -cp .;..\apputilities\target\classes -d .\target\classes .\src\main\java\conwaysgameoflife\*.java
java -cp .;..\apputilities\target\classes;.\target\classes conwaysgameoflife.ConwaysGameOfLifeInstance