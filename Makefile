all: run

clean:
	rm -rf out/Solver.jar out/Worker.jar out/parcs.jar

out/parcs.jar: src/parcs/*.java
	@mkdir -p out/parcs
	@javac -d out/parcs src/parcs/*.java
	@jar cf $@ -C out/parcs .

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf $@ -C src Solver.class
	@rm -f src/Solver.class

out/Worker.jar: out/parcs.jar src/Worker.java
	@javac -cp out/parcs.jar src/Worker.java
	@jar cf $@ -C src Worker.class
	@rm -f src/Worker.class

build: out/Solver.jar out/Worker.jar

run: out/Solver.jar out/Worker.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver
