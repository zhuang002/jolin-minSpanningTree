import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph graph = Graph.load("graph3.dat");
		graph = graph.getMinSpanningTree();
		graph.print();
	}

}

class Graph {
	List<Path> paths=new ArrayList<>();
	int noNodes;

	public static Graph load(String fname) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		Scanner sc = new Scanner(new File(fname));
		g.noNodes = sc.nextInt();
		int n = sc.nextInt();
		
		for (int i=0;i<n;i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int len = sc.nextInt();
			
			g.paths.add(new Path(a,b,len));
		}
		return g;
	}

	public void print() {
		// TODO Auto-generated method stub
		System.out.println(noNodes);
		System.out.println(this.paths.size());
		for (Path p:this.paths) {
			p.print();
		}
	}

	public Graph getMinSpanningTree() {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		g.noNodes = this.noNodes;
		int pathAdded = 0;
		
		int[] sets = new int[noNodes];
		for (int i=0;i<noNodes;i++)
			sets[i] = -1;
		
		Collections.sort(this.paths, (x,y)->x.length-y.length);
		
		for (Path p:this.paths) {
			int set1 = getSet(sets, p.p1);
			int set2 = getSet(sets, p.p2);
			
			if (set1==-1) {
				if (set2==-1) {
					sets[p.p2] = p.p1;
					sets[p.p1] = p.p1;
					pathAdded++;
					g.addPath(p);
				} else {
					sets[p.p1] = set2;
					g.addPath(p);
					pathAdded++;
				}
			} else {
				if (set2==-1) {
					sets[p.p2] = set1;
					g.addPath(p);
					pathAdded++;
				} else {
					if (set1!=set2) {
						merge(sets,set1,set2);
						g.addPath(p);
						pathAdded++;
					}
				}
			}
			
			if (pathAdded>=noNodes-1) {
				break;
			}
		}
		
		return g;
	}

	private void merge(int[] sets, int set1, int set2) {
		// TODO Auto-generated method stub
		for (int i=0;i<noNodes;i++) {
			if (sets[i]==set2) {
				sets[i]=set1;
			}
		} 
		sets[set2] = set1;
	}

	private void addPath(Path p) {
		// TODO Auto-generated method stub
		this.paths.add(p);
	}

	private int getSet(int[] sets, int p) {
		// TODO Auto-generated method stub
		return sets[p];
	}

	
}

class Path {
	int p1;
	int p2;
	int length;
	
	public Path(int a, int b, int len) {
		this.p1 = a;
		this.p2 = b;
		this.length = len;
	}

	public void print() {
		// TODO Auto-generated method stub
		System.out.println(p1+" "+p2+" "+length);
	}
}