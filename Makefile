all: run

clean:
	rm -f out/Solver.jar out/BubbleSort.jar
	rm -f src/*.class

out/Main.jar: out/parcs.jar src/Solver.java src/BubbleSort.java
	@javac -cp out/parcs.jar src/Solver.java src/BubbleSort.java
	@jar cf out/Solver.jar -C src Solver.class -C src BubbleSort.class
	@rm -f src/Solver.class src/BubbleSort.class

out/Invert.jar: out/parcs.jar src/BubbleSort.java
	@javac -cp out/parcs.jar src/BubbleSort.java
	@jar cf out/BubbleSort.jar -C src BubbleSort.class
	@rm -f src/BubbleSort.class

build: out/Solver.jar out/BubbleSort.jar

run: build
	@cd out && java -cp 'parcs.jar:Solver.jar:Invert.jar' Solver
