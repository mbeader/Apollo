package com.team1ofus.apollo;

import java.util.ArrayList;

public class UIManagement implements IDataUpdateListener, IHumanInteractionListener, ICellUpdateListener{
	ApolloUI window;

	ArrayList<Cell> cells;
	public UIManagementInteractionEventObject events; //later, add getters and setters to prevent direct access.
	
	public UIManagement(ArrayList<Cell> allCells) {
		events = new UIManagementInteractionEventObject();
		
		cells = allCells; //loaded from DataManagement
		//Changes are made to Cells until a Save operation is made.
	
	}
	/*
	 * Launch the application once event handling and stitching is complete.
	 */
	public void begin() {
		window = new ApolloUI();
		window.events.addSaveListener(this);
		window.initialize(cells.get(0));
		
	}
	
	public void onCellUpdate() {
		
	}
	
	public	void onDataUpdate() {
		
	}
	@Override
	public void onSaveTriggered(Cell cellToSave) {
		cells.set(0, cellToSave);
		events.triggerSave(cells);
		
	}
}
