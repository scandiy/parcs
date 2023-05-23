all: run

clean:
	rm -f out/Solver.jar

out/Main.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class
	@rm -f src/Solver.class


build: out/Solver.jar

run: out/Solver.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver
