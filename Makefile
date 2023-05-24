JFLAGS = -cp .:./out/parcs.jar
SRC_DIR = src
OUT_DIR = out

all: run

clean:
	rm -rf $(OUT_DIR)

$(OUT_DIR):
	mkdir -p $(OUT_DIR)

$(OUT_DIR)/BubbleSort.class: $(OUT_DIR) $(SRC_DIR)/BubbleSort.java
	javac $(JFLAGS) -d $(OUT_DIR) $(SRC_DIR)/BubbleSort.java

$(OUT_DIR)/Solver.class: $(OUT_DIR) $(SRC_DIR)/Solver.java $(OUT_DIR)/BubbleSort.class
	javac $(JFLAGS) -d $(OUT_DIR) $(SRC_DIR)/Solver.java

$(OUT_DIR)/Solver.jar: $(OUT_DIR)/Solver.class
	jar cf $(OUT_DIR)/Solver.jar -C $(OUT_DIR) Solver.class

build: $(OUT_DIR)/Solver.jar

run: build
	cd $(OUT_DIR) && java $(JFLAGS) -cp ../parcs.jar:Solver.jar Solver

.PHONY: all clean build run
