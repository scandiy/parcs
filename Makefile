JFLAGS = -cp .:./parcs.jar

all: run

clean:
	rm -rf out/

out:
	mkdir -p out

out/Solver.jar: out/parcs.jar src/BubbleSort.java src/Solver.java
	javac $(JFLAGS) src/BubbleSort.java src/Solver.java
	jar cf out/Solver.jar -C src BubbleSort.class Solver.class

build: out out/Solver.jar

run: build
	java $(JFLAGS) Solver
