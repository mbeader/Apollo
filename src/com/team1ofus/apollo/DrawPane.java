package com.team1ofus.apollo;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class DrawPane extends JPanel {
	CellRenderer render;
	public DrawPane() {
		//test data
		DataTile[][] dummyData;
		dummyData = new DataTile[50][50];
		
		for(int i=0; i<50; i++) {
			for(int j=0; j<50; j++) {
				dummyData[i][j] = new DataTile(TILE_TYPE.WALL);
			}
		}
		dummyData[1][2] = new DataTile(TILE_TYPE.HALLWAY);
		render = new CellRenderer(dummyData);
	}
	public void paintComponent(Graphics g) {
		render.renderTiles(g);
	}
	public Point pickTile(int mouseX, int mouseY) {
		return render.pickTile(mouseX, mouseY);
	}
	
}
