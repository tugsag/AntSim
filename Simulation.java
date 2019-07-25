public class Simulation implements SimulationEventListener{
	
	boolean queenStatus;
	int currentTurn = 0;
	AntSimGUI gui;
	Environment environ;
	Queen queen;
	
	Simulation(){
		
	}
	
	Simulation(AntSimGUI gui){
		queenStatus = true;
		environ = new Environment(new ColonyView(27, 27));
		this.gui = gui;
		gui.initGUI(environ.getView());
	}
	
	//Both nextTurn functions call the nextTurn function in 
	//Environment class, which calls nextTurn functions in each Node in the nodeGrid
	//which call each nextTurn function for each ant in the antList for each node
	
	public void nextTurnLoop() {
		while(queenStatus == true) {
			environ.nextTurn(currentTurn);
			String time = Integer.toString(currentTurn);
			String days;
			if(currentTurn % 10 == 0) {
				days = Integer.toString(currentTurn / 10);
				gui.setTime(time + "turns, " + days + "days");
			}
			else {
				int remainder = currentTurn % 10;
				days = Integer.toString((currentTurn - remainder) / 10);
				gui.setTime(time + "turns, " + days + "days");
			}
			currentTurn++;
		if(queenStatus == false) {
			endSim();
		}
		}
		
	}
	
	public void nextTurnStep() {
			environ.nextTurn(currentTurn);
			String time = Integer.toString(currentTurn);
			String days;
			if(currentTurn % 10 == 0) {
				days = Integer.toString(currentTurn / 10);
				gui.setTime(time + "turns, " + days + "days");
			}
			else {
				int remainder = currentTurn % 10;
				days = Integer.toString((currentTurn - remainder) / 10);
				gui.setTime(time + "turns, " + days + "days");
			}
			currentTurn++;
			if(queenStatus == false) {
				endSim();
			}
	}
	
	
	public void endSim() {
		System.out.println("Queen has died. Simulation ended on turn " + currentTurn);
		return;
		
	}
	
	
	public void simulationEventOccurred(SimulationEvent event) {
		if(event.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
			environ.setSimulation(this);
			environ.initializeEnvironment();
			
			//Put this bit here since I could not get the adjacents of the queen node to work in environment class
			//So, initializing environment is halved between this setup method and original initialize method
			Node node = environ.nodeGrid[13][13];
			Queen queen = new Queen(node, this);
			node.addAnt(queen);
			for(int i = 0; i < 10; i++) {
				queen.initialHatch(new Soldier(node));
			}
			for(int i = 0; i < 50; i++) {
				queen.initialHatch(new Forager(node));
			}
			for(int i = 0; i < 4; i++) {
				queen.initialHatch(new Scout(node));
			}
			for(Node n: node.adjacentNodes()) {
				n.setExplored(true);
			}
			}
		
		//Was unable to make the Swing solution work for smooth updates,
		//so found another solution online using a separate thread for the nextTurn loop
		//but still unable to make the updates slower. Thread.sleep method was not working, so left it as is
		else if(event.getEventType() == SimulationEvent.RUN_EVENT) {
			Thread thread = null;
			thread = new Thread(){
				public void run() {
					nextTurnLoop();
				}
			};
			thread.start();
			
		}
		else if(event.getEventType() == SimulationEvent.STEP_EVENT) {
			this.nextTurnStep();
		}
		
	}
}
