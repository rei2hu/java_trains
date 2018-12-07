SRCDIR=./src/main/java
TESTDIR=./test/main/java
HAMCREST=./lib/hamcrest-core.jar
JUNIT=./lib/junit.jar

compile:
	javac -g -cp $(SRCDIR) \
		$(SRCDIR)/challenge/parsing/*.java \
		$(SRCDIR)/challenge/components/*.java \
		$(SRCDIR)/challenge/ExampleUsage.java

run:
	java -cp $(SRCDIR) challenge.ExampleUsage

tests:
	javac -cp $(SRCDIR):$(TESTDIR):$(HAMCREST):$(JUNIT) $(TESTDIR)/tests/*.java
	java -cp $(SRCDIR):$(TESTDIR):$(HAMCREST):$(JUNIT) org.junit.runner.JUnitCore tests.UnitTests

clean:
	rm -f $(SRCDIR)/challenge/*.class
	rm -f $(SRCDIR)/challenge/*/*.class
	rm -f $(SRCDIR)/challenge/*/*/*.class
	rm -f $(TESTDIR)/tests/*.class
