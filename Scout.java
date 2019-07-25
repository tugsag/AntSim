import java.util.ArrayList;
import java.util.Random;

public class Scout extends AntGeneral{

	Scout() {
		
	}
	
	Scout(Node node) {
		location = node;
		currentTurn = -1;
	}
	
	public void movement(Node newNode) {
		location.removeAnt(this);
		location = newNode;
		location.addAnt(this);
		if(!location.getExplored()) {
			location.setExplored(true);
		}
	}
	
	public void nextTurn(int currentTurn) {
		if(this.currentTurn == currentTurn) {
			return;
		}
		if(currentTurn - ageOfAnt > 3650) {
			death();
			return;
		}
		this.currentTurn = currentTurn;
		Random random = new Random();
		ArrayList<Node> adjacents = location.adjacentNodes();
		movement(adjacents.get(random.nextInt(adjacents.size())));
	}
}
