package cs355.solution;

import cs355.GUIFunctions;
import cs355.controller.Controller;
import cs355.view.View;
import cs355.controller.SharedViewInfo;
import cs355.model.Model;
import java.awt.Color;
/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {
                
                Model model = new Model();
                SharedViewInfo sharedInfo = new SharedViewInfo();
                View view = new View(model,sharedInfo);
                Controller controller = new Controller(model,sharedInfo);
                model.addObserver(view);
// Fill in the parameters below with your controller and view.
		GUIFunctions.createCS355Frame(controller, view);
                
                GUIFunctions.setHScrollBarMax(2048);
                GUIFunctions.setHScrollBarMin(0);
                GUIFunctions.setVScrollBarMax(2048);
                GUIFunctions.setVScrollBarMin(0);
                GUIFunctions.setHScrollBarKnob(512);
                GUIFunctions.setVScrollBarKnob(512);
                GUIFunctions.setVScrollBarPosit(0);
                GUIFunctions.setHScrollBarPosit(0);
                
                
                controller.colorButtonHit(Color.white);

		GUIFunctions.refresh();
	}
}
