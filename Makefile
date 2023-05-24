JFLAGS = -cp .:./parcs.jar

compile:
	javac $(JFLAGS) src/BubbleSort.java src/Solver.java

run:
	java $(JFLAGS) Solver
