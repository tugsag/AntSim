import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Forager extends AntGeneral{
	boolean carryingFood;
	ArrayList<Node> movementHistory;
	int returnToNest;
	
	
	Forager() {
		
	}
	
	Forager(Node node) {
		location = node;
		currentTurn = -1;
		carryingFood = false;
		movementHistory = new ArrayList<Node>();
		movementHistory.add(node);
	}
	
	public void movement(Node newNode) {
		location.removeAnt(this);
		location = newNode;
		location.addAnt(this);
		
		if(location.queenNode() && carryingFood) {
			location.setFood(location.getFood() + 1);
			carryingFood = false;
			movementHistory.clear();
		}
		movementHistory.add(location);
	}
	
	public Node mostPher() {
		ArrayList<Node> adjacents = location.adjacentNodes();
		Iterator<Node> iterator = adjacents.iterator();
		
		Random random = new Random();
		
		//Also had concurrent modification error here, so used iterator
		while(iterator.hasNext()) {
			Node n = iterator.next();
			if(n.getExplored() == false || movementHistory.contains(n)) {
				iterator.remove();
			}
		}
		if(adjacents.size() == 0) {
			adjacents = location.adjacentNodes();
		}
		
		Node maxPher = new Node();
		for(Node n: adjacents) {
			if(n.getPher() > maxPher.getPher() && n.getExplored()) {
				maxPher = n;
			}
		}
		
		ArrayList<Node> maxPherNodes = new ArrayList<>();
		for(Node n: adjacents) {
			if(maxPher.getPher() == n.getPher() && n.getExplored()) {
				maxPherNodes.add(n);
			}
		}
		
		maxPher = maxPherNodes.get(random.nextInt(maxPherNodes.size()));
		return maxPher;
	}
	
	public void depositPher() {
		if(location.queenNode() == false) {
			if(location.getPher() < 1000) {
				location.setPher(location.getPher() + 10);
			}
		}
	}
	
	public void pickUpFood() {
		if(location.getFood() > 0 && !location.queenNode()) {
			location.setFood(location.getFood() - 1);
			carryingFood = true;
			returnToNest = movementHistory.size() - 1;
		}
		
		else
			carryingFood = false;
	}
	
	public void nextTurn(int currentTurn) {
		if(this.currentTurn == currentTurn) {
			return;
		}
		Node moveTo;
		if(currentTurn - ageOfAnt > 3650) {
			death();
			return;
		}
		this.currentTurn = currentTurn;
		if(carryingFood) {
			moveTo = movementHistory.get(returnToNest);
			movement(moveTo);
			returnToNest--;
			depositPher();
		}
		else {
			moveTo = mostPher();
			movement(moveTo);
			pickUpFood();
			
			
		}
	}
	
	public void death() {
		if(carryingFood) {
			location.setFood(location.getFood() + 1);
		}
		location.removeAnt(this);
	}
}
