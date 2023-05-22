all: run

clean:
	rm -f out/Solver.jar out/Worker.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class
	@rm -f src/Solver.class

out/Square.jar: out/parcs.jar src/Worker.java
	@javac -cp out/parcs.jar src/Worker.java
	@jar cf out/Worker.jar -C src Worker.class
	@rm -f src/Worker.class

build: out/Solver.jar out/Worker.jar

run: out/Solver.jar out/Worker.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Main
