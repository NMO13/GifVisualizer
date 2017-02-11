package model.impl;

import java.util.EventListener;

public interface DecodeListener extends EventListener {

	void update(DecodeEvent event);
	void updateScaling(ScalingEvent event);
	void updateColor(ColorEvent event);
}
