package com.andi.DungeonExplorer.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerTool {

	public static void main(String[] args) {

		TexturePacker.process(
				"res/graphics_unpacked/tiles/",
				"res/graphics_packed/tiles/",
				"tilepack");

	}

}
