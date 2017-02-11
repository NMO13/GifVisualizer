package main;

import gui.VisualizerView;
import model.VisualizerModel;
import model.impl.VisualizerModelImpl;

public class Main {

	public static void main(String[] args)
	{
		VisualizerModel model = new VisualizerModelImpl();
		VisualizerView view = new VisualizerView(model);
		view.showGui();
	}
}
