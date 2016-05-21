all: clean compile jar

compile:
	mkdir build
	javac *.java -d ./build

clean:
	rm -rf cipher.jar
	rm -rf build
	find -name "*~" | xargs rm -rf

jar:
	echo "Main-Class: FileCipher" > manifest.txt
	jar cvfm cipher.jar manifest.txt doc -C ./build .
	@echo "Created jar file: cipher.jar"
