all : play

classes:
	javac -sourcepath src -d classes src/*/*.java

start: classes
	java -classpath classes main.Main 

jar :  classes
	jar cvfe vlille.jar main.Main -C classes .

compileTests: classes
	javac -cp classes:junit-platform-console-standalone-1.9.1.jar -d classes  test/*/*.java

runTests: compileTests classes
	java -jar junit-platform-console-standalone-1.9.1.jar -cp classes --scan-class-path

doc:
	javadoc -sourcepath src -d docs -subpackages constants controlCenters exceptions main persons redistibuationStrategy stations vehicleDecorators vehiclesState vehicles

runJar: 
	java -jar vlille.jar

clean :
	rm -rf classes
	rm -rf docs
	rm -f vlille.jar

