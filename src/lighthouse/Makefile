JAVA=~/java/jre1.8.0_73/bin/java

all: Parser.java Lexer.java
	
.PHONY: clean

clean:
	rm Lexer.java Parser.java

Parser.java: lh.cup
	$(JAVA) -jar java-cup-11b.jar -package lighthouse -expect 6000 -parser Parser lh.cup

Lexer.java: lh.flex
	$(JAVA) -jar jflex-1.6.1.jar lh.flex

