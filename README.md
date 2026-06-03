Case Study Overview

This project implements graph traversal and Minimum Spanning Tree (MST) algorithms for planning the Bangalore Metro Phase-3 expansion network.

The metro system is modeled as a weighted undirected graph where:

Metro stations are represented as vertices.
Metro routes are represented as weighted edges.
Edge weights indicate estimated construction costs.

The objective is to minimize overall construction cost while maintaining complete connectivity and ensuring redundancy between critical stations.

Problem Statement

The Bangalore Metro Authority requires a network design that:

Connects all stations.
Minimizes construction cost.
Provides fault tolerance through alternative paths.
Supports efficient network analysis.

The project evaluates connectivity using graph traversal algorithms and determines the minimum-cost network using MST algorithms.

Algorithms Implemented
1. Breadth First Search (BFS)

Used to:

Verify network connectivity.
Traverse stations level-by-level.
Check whether all stations are reachable.

Time Complexity:

O(V + E)

where:

V = Number of stations
E = Number of routes
2. Depth First Search (DFS)

Used to:

Explore all connected stations.
Verify connectivity.
Analyze network structure.

Time Complexity:

O(V + E)
3. Prim's Algorithm

Used to construct a Minimum Spanning Tree by:

Starting from an initial station.
Continuously selecting the lowest-cost route.
Connecting all stations with minimum total cost.

Time Complexity:

O(E log V)
4. Kruskal's Algorithm

Used to construct a Minimum Spanning Tree by:

Sorting routes by construction cost.
Selecting edges that do not form cycles.
Connecting all stations with minimum total cost.

Time Complexity:

O(E log E)
5. Multiple Path Analysis

Used to verify redundancy requirements.

The project checks whether at least two independent paths exist between:

Majestic
Whitefield

This ensures continued operation even if a route becomes unavailable.

Metro Network Representation
Stations
Vertex	Station
0	Majestic
1	MG Road
2	Indiranagar
3	Whitefield
4	KR Puram
5	Silk Board
Routes
Source	Destination	Cost
Majestic	MG Road	5
MG Road	Indiranagar	4
Indiranagar	Whitefield	7
Majestic	KR Puram	8
KR Puram	Whitefield	6
MG Road	Silk Board	3
Silk Board	Whitefield	5
Indiranagar	KR Puram	2
Project Structure
BangaloreMetroMST/
│
├── BangaloreMetroMST.java
├── README.md
Compilation

Compile the program using:

javac BangaloreMetroMST.java
Execution

Run the program using:

java BangaloreMetroMST
Sample Output
Bangalore Metro Phase-3

BFS Traversal:
0 1 4 2 5 3

DFS Traversal:
0 1 2 3 4 5

Prim's MST:
0 - 1 : 5
1 - 5 : 3
1 - 2 : 4
2 - 4 : 2
5 - 3 : 5
Total Construction Cost = 19

Kruskal MST:
2 - 4 : 2
1 - 5 : 3
1 - 2 : 4
0 - 1 : 5
5 - 3 : 5
Total Construction Cost = 19

Paths between Majestic and Whitefield = 4

Redundancy Requirement Satisfied
Results
BFS confirmed that all stations are reachable.
DFS verified graph connectivity.
Prim's algorithm generated a minimum-cost metro network.
Kruskal's algorithm produced the same optimal construction cost.
Multiple path analysis verified redundancy between Majestic and Whitefield.
The network satisfies both connectivity and fault-tolerance requirements.
Conclusion

The project demonstrates that graph algorithms are highly effective for metro infrastructure planning.
