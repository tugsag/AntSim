import java.util.ArrayList;
import java.util.Random;
import java.util.Random;

public class Soldier extends AntGeneral{

	Soldier() {
		
	}
	
	Soldier(Node node) {
		location = node;
		currentTurn = -1;
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
		if(location.antCount(new Bala()) > 0) {
			this.attack();
		}
		
		else {
			Node moveTo = new Node();
			ArrayList<Node> adjacents = new ArrayList<>();
			adjacents = location.adjacentNodes();
			ArrayList<Node> exploreds = new ArrayList<>();
			for(Node n: adjacents) {
				if(n.getExplored()) {
					exploreds.add(n);
				}
			}
			Random random = new Random();
			int moveToChance = random.nextInt(exploreds.size());
			moveTo = exploreds.get(moveToChance);
			for(Node node: adjacents) {
				if(node.balaAnts().size() > 0) {
					moveTo = node;
					break;
				}
			}
			movement(moveTo);
		}
	}
	
	public void attack() {
		ArrayList<AntGeneral> balaList = location.balaAnts();
		Random random = new Random();
		int killChance = random.nextInt(2);
		if(killChance < 1)
			balaList.get(0).death();
	}
}
