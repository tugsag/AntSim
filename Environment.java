import java.util.ArrayList;
import java.util.Random;

public class Environment {
	Node [][] nodeGrid;
	ColonyView cv;
	Node node;
	ColonyNodeView cnv;
	int ID = 100000;
	Simulation sim;
	
	Environment() {
	
	}
	
	Environment(ColonyView colonyView) {
		this.cv = colonyView;
		nodeGrid = new Node[27][27]; 
	}
	
	public void setSimulation(Simulation sim) {
		this.sim = sim;
	}
	
	public ColonyView getView() {
		return cv;
	}
	
	public void addNode(Node newNode, int x, int y) {
		nodeGrid[x][y] = newNode;
	}	
	
	public void initializeEnvironment() {
		for(int x = 0; x < 27; x++) {
			for(int y = 0; y < 27; y++) {
				cnv = new ColonyNodeView();
				node = new Node(cnv, x, y);
				node.setEnvironment(this);
				cv.addColonyNodeView(cnv, x, y);
				addNode(node, x, y);
				cnv.setID(x + ", " + y);
				if(x == 13 && y == 13) {
					node.setExplored(true);
					node.setFood(1000);
				}
				}
			}
		}
	
	public void nextTurn(int currentTurn) {
		balaEntry(currentTurn);
		for(int i = 0; i < 27; i++) {
			for (int j = 0; j < 27; j++) {
				nodeGrid[i][j].nextTurn(currentTurn);
			}
		}
	}
	
	public ArrayList<Node> adjacentNodes(Node location){
		int x = location.getX();
		int y = location.getY();
		ArrayList<Node> adjacentNodesList;
		adjacentNodesList = new ArrayList<Node>();
		for(int i = -1; i < 2; i ++) {
			for(int j = -1; j < 2; j ++) {
				if((i != 0 || j != 0) && (x+i >= 0 && x+i < 27) && (y+j>=0 && y+j<27)) {
					adjacentNodesList.add(nodeGrid[x + i][y + j]);
			}
		}

	}
	
	return adjacentNodesList;
}
	//Put bala spawn class here because bala class would not generate new balas
	//unless there was at least one on the environment grid. ID counts back to not interfere with friendly IDs
	public void balaEntry(int currentTurn) {
		Random entryChance = new Random();
		Node entryLocation = nodeGrid[0][0];
		if(entryChance.nextInt(100) < 3) {
			Bala newBala = new Bala(entryLocation);
			entryLocation.addAnt(newBala);
			newBala.setAgeOfAnt(currentTurn);
			newBala.setID(this.ID);
			ID--;
		}
	}
}

