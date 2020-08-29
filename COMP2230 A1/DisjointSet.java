/* 
 * File:   		   DisjointSet.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	DisjointSet is used to store all the rankings and parents
 *	of the graph to be used when making and finding the sets.
 *	It also has the methods of Merging two sets
*/

public class DisjointSet {

    private int parent[];
    private int rank[];

    // Constructor
    public DisjointSet(int nodes) {
        parent = new int[nodes];
        rank = new int[nodes];
        for (int i = 0; i < nodes; i++)
            makeSet(i);
    }

    // Initialise each node to be in their own set of rank 0
    private void makeSet(int i) {
        parent[i] = i;
        rank[i] = 0;
    }

    // Find the Set that 'i' is in
    public int findSet(int i) {
        int root = i;
        // Get the root by going up the tree until the parent of the root equals the root
        while (root != parent[root])
            root = parent[root];

        // Make every node along the way a child of the root
        int j = parent[i];
        while (j != root) {
            parent[i] = root;
            i = j;
            j = parent[i];
        }
        return root;
    }

    // Merge the Trees of 'i' and 'j', but not necessarily at the root.
    private void mergeTrees(int i, int j) {
        if (rank[i] < rank[j]) {
            parent[i] = j;
        } else if (rank[j] < rank[i]) {
            parent[j] = i;
        } else {
            parent[i] = j;
            rank[j]++;
        }
    }

    // Union of the two Sets, but merging the roots of the trees
    public void union(int i, int j) {
        mergeTrees(findSet(i), findSet(j));
    }
}
