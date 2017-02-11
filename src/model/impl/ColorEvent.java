package model.impl;

import java.awt.Color;
import java.util.EventObject;

public class ColorEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color;

	public ColorEvent(Object source, Color c) {
		super(source);
		color = c;
	}
	
	public Color getColor() {
		return color;
	}

}
