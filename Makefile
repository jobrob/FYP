CMP=javac
FLAGS=-d ./bin

### PHONY

.PHONY: all clean

all: ForceDirected Graph

clean:
	rm -rf ./bin
	mkdir ./bin

### PACKAGES

ForceDirected: ForceDirected.FDD

Graph: Graph.Edge Graph.Graph Graph.SVG Graph.Vector Graph.Vertex

### CLASSES

Graph.Vector: ./src/Graph/Vector.java
	$(CMP) $(FLAGS) ./src/Graph/Vector.java

Graph.Vertex: ./src/Graph/Vertex.java Graph.Vector
	$(CMP) $(FLAGS) ./src/Graph/Vertex.java

Graph.Edge: ./src/Graph/Edge.java Graph.Vertex
	$(CMP) $(FLAGS) ./src/Graph/Edge.java

Graph.Graph: ./src/Graph/Graph.java Graph.Vertex Graph.Edge
	$(CMP) $(FLAGS) ./src/Graph/Graph.java

Graph.SVG: ./src/Graph/SVG.java Graph.Graph 
	$(CMP) $(FLAGS) ./src/Graph/SVG.java

ForceDirected.FDD: ./src/ForceDirected/FDD.java Graph
	$(CMP) $(FLAGS) ./src/ForceDirected/FDD.java


