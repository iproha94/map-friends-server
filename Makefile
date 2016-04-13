all:
		mvn compile assembly:single

clean:
		rm -fr target/*
