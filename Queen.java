import java.util.Random;

public class Queen extends AntGeneral{
	int currentTurn;
	Simulation sim;
	
	Queen() {
		
	}
	
	Queen(Node node, Simulation sim) {
		location = node;
		ID = 0;
		this.sim = sim;
	}
	
	public void initialHatch(AntGeneral ant) {
		location.addAnt(ant);
		ant.setID(ID + 1);
		ID++;
	}
	
	public void hatch() {
		Random random = new Random();
		int hatchProb = random.nextInt(4);
		AntGeneral newAnt;
		if(hatchProb < 2) {
			newAnt = new Forager(location);
		}
		else if(hatchProb == 2) {
			newAnt = new Scout(location);
		}
		else {
			newAnt = new Soldier(location);
		}
		newAnt.setAgeOfAnt(0);
		newAnt.setID(ID);
		ID++;
		location.addAnt(newAnt);
	}
	
	public void consume() {
		int food = location.getFood();
		if(food < 1) {
			this.death();
		}
		food--;
		location.setFood(food);
	}
	
	public void death() {
		location.cnv.hideQueenIcon();
		sim.queenStatus = false;
	}
	
	public void nextTurn(int currentTurn) {
		this.currentTurn = currentTurn;
		if(currentTurn> (20*3650) || location.getFood() == 0) {
			death();
			return;
		}
		if((currentTurn % 10) == 0) {
			this.hatch();
		}
		this.consume();
	}
	
	
}
