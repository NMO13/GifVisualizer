package model.impl;

import java.util.EventObject;

import model.VisualizerModel;

public class DecodeEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DecodeEvent(VisualizerModel source) {
		super(source);
	}
}
