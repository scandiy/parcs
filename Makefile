all: run

clean:
	rm -f out/Solver.jar out/BubbleSort.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class
	@rm -f src/Solver.class

out/BubbleSort.jar: out/parcs.jar src/BubbleSort.java
	@javac -cp out/parcs.jar src/BubbleSort.java
	@jar cf out/BubbleSort.jar -C src BubbleSort.class
	@rm -f src/BubbleSort.class

build: out/Solver.jar out/BubbleSort.jar

run: out/Solver.jar out/BubbleSort.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver
