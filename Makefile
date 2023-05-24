JAR_FILES := out/BubbleSort.jar out/Solver.jar

# Default target
all: run

# Clean target to remove generated files
clean:
	rm -rf out

# Build target to compile the source files and generate JAR files
build: $(JAR_FILES)

# Run target to execute the program
run: build
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver

# Compile the BubbleSort.java source file and generate BubbleSort.jar
out/BubbleSort.jar: src/BubbleSort.java | out
	@javac -d out $<
	@jar cfe $@ BubbleSort -C out .

# Compile the Solver.java source file and generate Solver.jar
out/Solver.jar: src/Solver.java out/BubbleSort.jar | out
	@javac -cp out/BubbleSort.jar -d out $<
	@jar cfe $@ Solver -C out .

# Create the 'out' directory if it doesn't exist
out:
	@mkdir -p out
