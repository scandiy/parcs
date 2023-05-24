JAVAC_FLAGS = -cp .:./out/parcs.jar

build: out/BubbleSort.class out/Solver.class

out/BubbleSort.class: src/BubbleSort.java
	javac $(JAVAC_FLAGS) -d out src/BubbleSort.java

out/Solver.class: src/Solver.java
	javac $(JAVAC_FLAGS) -d out src/Solver.java

clean:
	rm -rf out/*.class

run:
	cd out && java -cp .:../out/parcs.jar Solver

.PHONY: build clean run
