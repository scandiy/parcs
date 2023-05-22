all: run

clean:
	rm -rf out/Solver.jar out/Worker.jar out/parcs.jar

out/parcs.jar: src/Solver.java 
	@mkdir -p out/parcs
	@javac -d out/parcs src/Solver.java 
	@jar cf $@ -C out/parcs .

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf $@ -C src Solver.class
	@rm -f src/Solver.class

build: out/Solver.jar 

run: out/Solver.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver
