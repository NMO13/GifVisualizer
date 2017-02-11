package model.impl;

import java.util.EventObject;

import model.VisualizerModel;

public class ScalingEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int factor;
	public ScalingEvent(VisualizerModel source, int factor) {
		super(source);
		this.factor = factor;
	}
	
	public int getScalingFactor() {
		return factor;
	}
}
