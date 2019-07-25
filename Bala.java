import java.util.ArrayList;
import java.util.Random;

public class Bala extends AntGeneral{
	

	Bala() {
		
	}
	
	Bala(Node node) {
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
		ArrayList<AntGeneral> list = location.friendlyAnts();
		if(list.size() > 0) {
			attack();
		}
			
		else {
			Random random = new Random();
			ArrayList<Node> adjacentList = location.adjacentNodes();
			int moveTo = random.nextInt(adjacentList.size());
			Node node = adjacentList.get(moveTo);
			movement(node);
			}
		}
	
	public void movement(Node newNode) {
		location.removeAnt(this);
		location = newNode;
		location.addAnt(this);
	}
	
	public void attack() {
		ArrayList<AntGeneral> notBala = location.friendlyAnts();
		Random random = new Random();
		int antToAttack = random.nextInt(notBala.size());
		int killChance = random.nextInt(2);
		if (killChance < 1) {
			notBala.get(antToAttack).death();
		}
	}

}
