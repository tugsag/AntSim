
public class AntGeneral {
	int ID;
	int lifeSpan;
	boolean aliveStatus;
	int currentTurn;
	Node location;
	int ageOfAnt;
	
	AntGeneral() {
		
	}
	
	AntGeneral(Node node) {
		ID = 0;
		lifeSpan = 0;
		aliveStatus = true;
		location = node;
		currentTurn = 0;
	}
	
	public void setID(int newID) {
		this.ID = newID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void movement(Node newNode) {
		location.removeAnt(this);
		location = newNode;
		location.addAnt(this);
	}
	
	public void death() {
		location.removeAnt(this);
	}
	
	public void nextTurn(int currentTurn) {
		
	}
	
	public void setAgeOfAnt(int currentTurn) {
		ageOfAnt = currentTurn;
	}
	
	public int getAgeOfAnt() {
		return ageOfAnt;
	}
	
}

