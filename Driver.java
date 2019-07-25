
public class Driver {

	
	public static void main(String[] args) {
		AntSimGUI gui = new AntSimGUI();
		Simulation sim = new Simulation(gui);
		gui.addSimulationEventListener(sim);

	}

}
