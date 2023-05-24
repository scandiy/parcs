all: run

clean:
	rm -f out/Sovler.jar out/BubbleSort.jar

out/Sovler.jar: out/parcs.jar src/Sovler.java
	@javac -cp out/parcs.jar src/Sovler.java
	@jar cf out/Sovler.jar -C src Sovler.class
	@rm -f src/Sovler.class

out/SquBubbleSortare.jar: out/parcs.jar src/BubbleSort.java
	@javac -cp out/parcs.jar src/BubbleSort.java
	@jar cf out/BubbleSort.jar -C src BubbleSort.class
	@rm -f src/BubbleSort.class

build: out/Sovler.jar out/BubbleSort.jar

run: out/Sovler.jar out/BubbleSort.jar
	@cd out && java -cp 'parcs.jar:Sovler.jar' Sovler
