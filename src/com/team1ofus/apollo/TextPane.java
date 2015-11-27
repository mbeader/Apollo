package com.team1ofus.apollo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Intended to be efficient at drawing text in a standardized fashion over the screen.
 * Add support for offset needed as a future task.
 */
public class TextPane extends JPanel {
	ArrayList<TextLocation> locations = new ArrayList<TextLocation>();
	public TextPane() {
		setOpaque(true);

	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		this.paintComponents(g);
		for(TextLocation l : locations) {
			//we now need to center the text.
			//double yShift = g.getFont().getSize()/2;
			for(int i=0; i<l.lines.size(); i++) {
				
				//double xShift = 0;
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int stringLength = (int) metrics.getStringBounds(l.lines.get(i), g).getWidth();
				int start = stringLength/2;
				g.drawString(l.lines.get(i), l.location.x*BootstrapperConstants.TILE_WIDTH-(int)start+BootstrapperConstants.TILE_WIDTH/2, l.location.y*BootstrapperConstants.TILE_HEIGHT+g.getFont().getSize()*i);
				
			}
		}
		showConsole(g);
	}

	public void addLocation(TextLocation input) {
		DebugManagement.writeNotificationToLog("Created new location at " + input.location.toString());
		locations.add(input);
	}
	//remove locations if they already exist.
	public void removeLocation(Point input) {
		locations.removeIf(isEqual(input));
	}
	//appends line to the input if it exists
	public void updateLocation(String input, Point location) {
	
		for(int i=0; i < locations.size(); i++) {
			if(locations.get(i).location.equals(location)) {
				//matching location! append the text.
				DebugManagement.writeNotificationToLog("Appended the text " + input + " at location " + location.toString());
				locations.get(i).addString(input);
				return;
			}
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.WARNING, "Attempted to append when there was nothing to append.");
		//can't have reached here if the point already existed.
		locations.add(new TextLocation(input, (new Point(BootstrapperConstants.TILE_WIDTH*location.x, BootstrapperConstants.TILE_HEIGHT*location.y))));
	}
	public static Predicate<TextLocation> isEqual(Point filter) {
	    return p -> p.location.equals(filter);
	}
	private void showConsole(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 8));
		if(DebugConsole.getEntries().size() > 1000) {
			DebugConsole.getEntries().clear();
		}
		if(DebugConsole.getEntries().size() <= 0) {
			return;
		}
		int count = 0; //number of lines drawn.
		if(DebugConsole.getEntries().size() < BootstrapperConstants.LINES_TO_SCREEN) {
			//There are fewer lines on the list, so just start at the end.
			
			for(int i=DebugConsole.getEntries().size()-1; i>0;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		} else {
			//more, start at the end up to LINES_TO_SCREEN lower
			for(int i=DebugConsole.getEntries().size()-1; i>DebugConsole.getEntries().size()-1-BootstrapperConstants.LINES_TO_SCREEN;i--) {
				g.drawString(DebugConsole.getEntries().get(i), 0, g.getFont().getSize()*count);
				count++;
			}
		}
	
		//draw the number of lines onscreen that are specified, but don't remove them
	}
}