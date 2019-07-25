import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Node{
	int food;
	int pher;
	boolean explored;
	int antCount;
	int x;
	int y;
	boolean queenNode;
	boolean start;
	ArrayList<AntGeneral> antList;
	ArrayList<AntGeneral> toAdd;
	ArrayList<AntGeneral> toRemove;
	ColonyNodeView cnv;
	Environment environ;
	
	Node() {
		
	}
	
	Node(ColonyNodeView cnv, int x, int y) {
		Random food = new Random();
		if(food.nextInt(4) > 0) {
			setFood(0);
		}
		else {
			setFood(food.nextInt(500) + 500);
		}
		this.cnv = cnv;
		this.x = x;
		this.y = y;
		pher = 0;
		queenNode = false;
		antCount = 0;
		explored = false;
		antList = new ArrayList<AntGeneral>();
		toAdd = new ArrayList<AntGeneral>();
		toRemove = new ArrayList<AntGeneral>();
	}
	
	//Was getting concurrent modification error when trying to add ants directly
	//so had to make separate lists for 
	//adding and removing ants to wait so no concurrent modification occurs
	public void addAnt(AntGeneral ant) {
		if(start) {
			toAdd.add(ant);
		}
		else {
			antList.add(ant);
		}
		updateView();
	}
	
	public void removeAnt(AntGeneral ant){
		if(start)
			toRemove.add(ant);
		else
			antList.remove(ant);
		updateView();
	}
	
	public void setExplored(boolean visible) {
		explored = visible;
		if(visible) {
			cnv.showNode();
		}
		if(!visible) {
			cnv.hideNode();
		}
	}
	
	public boolean getExplored() {
		return explored;
	}
	
	public void setFood(int newFood) {
		food = newFood;
	}
	
	public int getFood() {
		return food;
	}
	
	public void setPher(int phero) {
		pher = phero;
	}
	
	public void setEnvironment(Environment environment) {
		environ = environment;
	}
	
	public int getPher() {
		return pher;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean queenNode() {
		return queenNode;
	}
	
	public void nextTurn(int currentTurn) {
		if(currentTurn != 0 && currentTurn % 10 == 0) {
			this.setPher(getPher() / 2);
		}
		start = true;
		for (AntGeneral ant: antList) {
			ant.nextTurn(currentTurn);
		}
		start = false;
		antList.removeAll(toRemove);
		antList.addAll(toAdd);
		toRemove.clear();
		toAdd.clear();
		this.updateView();
	}
	
	public void updateView() {
		int queen = antCount(new Queen());
		if(queen > 0) {
			queenNode = true;
			cnv.setQueen(true);
			cnv.showQueenIcon();
		}
		
		int scout = antCount(new Scout());
		cnv.setScoutCount(scout);
		if(scout > 0) {
			cnv.showScoutIcon();
		}
		else {
			cnv.hideScoutIcon();
		}
		
		int forager = antCount(new Forager());
		cnv.setForagerCount(forager);
		if(forager > 0) {
			cnv.showForagerIcon();
		}
		else {
			cnv.hideForagerIcon();
		}
		
		int soldier = antCount(new Soldier());
		cnv.setSoldierCount(soldier);
		if(soldier > 0) {
			cnv.showSoldierIcon();
		}
		else {
			cnv.hideSoldierIcon();
		}
		
		int bala = antCount(new  Bala());
		cnv.setBalaCount(bala);
		if(bala > 0) {
			cnv.showBalaIcon();
		}
		else {
			cnv.hideBalaIcon();
		}
		
		cnv.setFoodAmount(food);
		cnv.setPheromoneLevel(pher);
	}
	
	//Could not use instanceof here, so had to use getCLass()
	public int antCount(AntGeneral type) {
		int count = 0;
		for(int i = 0; i < antList.size(); i++) {
			if(antList.get(i).getClass() == type.getClass()) {
				count++;
			}
		}
		
		return count;
	}
	
	public ArrayList<AntGeneral> friendlyAnts(){
		ArrayList<AntGeneral> list = new ArrayList<>();
		for(int i = 0; i < antList.size(); i++) {
			if(!(antList.get(i) instanceof Bala)) {
				list.add(antList.get(i));
			}
		}
		
		return list;
	}
	
	public ArrayList<AntGeneral> balaAnts(){
		ArrayList<AntGeneral> list = new ArrayList<>();
		for(int i = 0; i < antList.size(); i ++) {
			if(antList.get(i) instanceof Bala) {
				list.add(antList.get(i));
			}
		}
		
		return list;
	}
	
	public ArrayList<Node> adjacentNodes(){
		return environ.adjacentNodes(this);
	}
	
	
}
