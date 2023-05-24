JFLAGS = -cp .:./out/parcs.jar
SRC_DIR = src
OUT_DIR = out

all: run

clean:
	rm -rf $(OUT_DIR)

$(OUT_DIR):
	mkdir -p $(OUT_DIR)

$(OUT_DIR)/Solver.jar: $(OUT_DIR)/parcs.jar $(SRC_DIR)/BubbleSort.java $(SRC_DIR)/Solver.java
	javac $(JFLAGS) $(SRC_DIR)/BubbleSort.java $(SRC_DIR)/Solver.java
	jar cf $(OUT_DIR)/Solver.jar -C $(SRC_DIR) BubbleSort.class Solver.class

build: $(OUT_DIR) $(OUT_DIR)/Solver.jar

run: build
	cd $(OUT_DIR) && java $(JFLAGS) Solver

.PHONY: all clean build run
