all: run

clean:
	rm -f out/Main.jar out/BubbleSort.jar

out/Main.jar: out/parcs.jar src/Main.java
	@javac -cp out/parcs.jar src/Main.java
	@jar cf out/Main.jar -C src Main.class
	@rm -f src/Main.class

out/BubbleSort.jar: out/parcs.jar src/BubbleSort.java
	@javac -cp out/parcs.jar src/BubbleSort.java
	@jar cf out/BubbleSort.jar -C src BubbleSort.class
	@rm -f src/BubbleSort.class

build: out/Main.jar out/BubbleSort.jar

run: out/Main.jar out/BubbleSort.jar
	@cd out && java -cp 'BubbleSort.jar:Main.jar' Main
