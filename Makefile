JAVAC = javac
JAVA = java
JFLAGS = -cp .:parcs.jar
SOURCE_DIR = src
SOURCE_FILES = $(SOURCE_DIR)/Solver.java $(SOURCE_DIR)/WorkerTask.java $(SOURCE_DIR)/Worker.java

all: compile run

compile:
	$(JAVAC) $(JFLAGS) $(SOURCE_FILES)

run:
	$(JAVA) $(JFLAGS) Solver

clean:
	rm -rf $(SOURCE_DIR)/*.class output.txt

.PHONY: all compile run clean
