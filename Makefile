JAVAC_FLAGS = -cp .:./out/parcs.jar
JAR_FLAGS = cf
JAVA_FLAGS = -cp .:./out/parcs.jar:./out/Solver.jar

all: build run

clean:
	rm -rf out/*.class out/*.jar

out/BubbleSort.class: src/BubbleSort.java
	@javac $(JAVAC_FLAGS) -d out src/BubbleSort.java

out/Solver.class: src/Solver.java out/BubbleSort.class
	@javac $(JAVAC_FLAGS) -d out src/Solver.java

out/Solver.jar: out/Solver.class
	@jar $(JAR_FLAGS) out/Solver.jar -C out Solver.class

build: out/Solver.jar

run: out/Solver.jar
	@cd out && java $(JAVA_FLAGS) Solver

.PHONY: all clean build run
