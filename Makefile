all: run

clean:
	rm -f out/BubbleSort.jar out/Solver.jar

out/BubbleSort.jar: out/parcs.jar src/BubbleSort.java
	@javac -cp out/parcs.jar src/BubbleSort.java
	@jar cf out/BubbleSort.jar -C src BubbleSort.class
	@rm -f src/BubbleSort.class

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class
	@rm -f src/Solver.class

build: out/BubbleSort.jar out/Solver.jar

run: out/BubbleSort.jar out/Solver.jar
	@cd out && java -cp 'parcs.jar:BubbleSort.jar' Solver
