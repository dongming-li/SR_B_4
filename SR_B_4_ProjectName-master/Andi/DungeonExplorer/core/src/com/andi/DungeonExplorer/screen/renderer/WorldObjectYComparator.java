package com.andi.DungeonExplorer.screen.renderer;

import com.andi.DungeonExplorer.map.YSortable;

import java.util.Comparator;

/**
 * compares objects
 */
public class WorldObjectYComparator implements Comparator<YSortable> {

	/**
	 * compares what to render first based on when it was queued
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(YSortable o1, YSortable o2) {
		if (o1.getWorldY() < o2.getWorldY()) {
			return -1;
		} else if (o1.getWorldY() > o2.getWorldY()) {
			return 1;
		}
		return 0;
	}
}
