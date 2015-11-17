package com.team1ofus.apollo;

import java.awt.Point;
import java.util.HashMap;

public class PaintTool {

	private int brushSelection; //brush index
	private Brush[] brushes; // available brushes
	private TILE_TYPE tileToPaint; //tiletype selected to paint with
	private HashMap<Integer, TILE_TYPE> tileMap = new HashMap<Integer, TILE_TYPE>();
	
	public PaintTool() {
		tileToPaint = TILE_TYPE.WALL;
		brushSelection = 0;
		initializeTileMap();
		initializeBrushes();
	}
	
	/*
	 * Generates the array of brushes
	 * seems primitive
	 * sizes?
	 */
	private void initializeBrushes() {
		brushes = new Brush[1];
		Point[] brush1points = {new Point(0,0)}; //single tile
		brushes[0] = new Brush(brush1points);
		
	}
	
	/*
	 * Fills out the hashmap with the indices linked to the tiletypes 
	 */
	private void initializeTileMap() {
		int c = 0;
		for(TILE_TYPE t : TILE_TYPE.values()) {
			tileMap.put(c, t);
			c++;
		}
	}
	
	/*
	 * Selects the brush based on index
	 */
	public void selectBrush(int brushIndex) {
		brushSelection = brushIndex;
	}
	
	/*
	 * Selects the tiletype based on index
	 */
	public void selectTileType(int typeIndex) {
		tileToPaint = tileMap.get(typeIndex);
	}
	
	/*
	 * Selects the tiletype based on enum
	 */
	public void selectTileType(TILE_TYPE type) {
			tileToPaint = type;
	}
	//Need to be able to query the currently selected brush; set with methods you created.
	public int getBrushSelection() {
		return brushSelection;
	}

	public Brush[] getBrushes() {
		return brushes;
	}

	public TILE_TYPE getTileToPaint() {
		return tileToPaint;
	}
}
