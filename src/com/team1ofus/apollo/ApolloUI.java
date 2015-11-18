package com.team1ofus.apollo;

package com.team1ofus.apollo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ApolloUI {
	private JFrame frame;
	private JPanel windowUI;
	private Point mousePosition;
	private PaintTool painter;
	private JComboBox tiles;
	private JComboBox brushes;
	private Box verticalBox;
	private Box verticalBox_1;
	private JLabel lblOffset;
	private JLabel lblMousePos;
	DrawPane panel;
	int scrollSpeed = 1;
	public ApolloUI(Cell cellToEdit) {
		painter = new PaintTool();
		initialize(cellToEdit);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Cell cellToEdit) {
		buildControls(cellToEdit)
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});


		
		
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				 
				if(SwingUtilities.isLeftMouseButton(e)) {
					//left is pressed!
					doPaint(panel, e);
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					//left is pressed!
					doPaint(panel, e);
				}
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				doPaint(panel, e);
			}
		});
		tiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//selection box changed for tiles
				painter.selectTileType((TILE_TYPE) tiles.getSelectedItem());
			}
		});
		brushes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//selection box changed for brushes
				painter.selectBrush(brushes.getSelectedIndex());
				
				
			}
		});
		windowUI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
			}
		});
		verticalBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e, lblOffset);
				
			}
		});
		frame.getContentPane().add(verticalBox_1, BorderLayout.SOUTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		panel.grabFocus();
	}
	public Point getMouseLocation() {
		return mousePosition;
	}
	private void doPaint(DrawPane panel, MouseEvent e) {
		//PICK TILES HERE
		Point picked = panel.render.pickTile(e.getX(), e.getY());
		painter.applyBrush(panel.render, picked.x, picked.y);
		
		//panel.render.editTile(picked.x, picked.y, new DataTile(painter.getTileToPaint()));
		
		panel.paint(panel.getGraphics());
	}
	private void doOffsetCalc(KeyEvent e, JLabel offsetLbl) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			panel.render.incrementOffset(-1*scrollSpeed, 0);
			break;
		case KeyEvent.VK_RIGHT:
			panel.render.incrementOffset(scrollSpeed, 0);
			break;
		case KeyEvent.VK_DOWN:
			panel.render.incrementOffset(0, scrollSpeed);
			break;
		case KeyEvent.VK_UP:
			panel.render.incrementOffset(0, -1*scrollSpeed);
			break;
		default:
			break;
		}
		
		panel.paint(panel.getGraphics());
		lblOffset.setText(panel.render.offset.getX() + "," + panel.render.offset.getY());
	}
	private void buildControls(Cell cellToEdit) {
		frame = new JFrame();
		panel  = new DrawPane(cellToEdit);
		frame.setBounds(100, 100, 969, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		windowUI = new JPanel();

		windowUI.setBackground(Color.RED);
		windowUI.setForeground(Color.RED);
		frame.getContentPane().add(windowUI, BorderLayout.EAST);
		windowUI.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		verticalBox = Box.createVerticalBox();


		
		brushes = new JComboBox();

		verticalBox.add(brushes);
		brushes.setModel(new DefaultComboBoxModel(new String[] {"Single Tile"}));
		brushes.setAlignmentY(Component.TOP_ALIGNMENT);
		lblOffset = new JLabel("0,0");
		lblOffset.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(lblOffset);
		windowUI.add(verticalBox);
		tiles = new JComboBox();
		lblMousePos = new JLabel("#mouse#");
		lblMousePos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		verticalBox.add(lblMousePos);
		verticalBox.add(tiles);
		tiles.setAlignmentY(5.0f);
		tiles.setModel(new DefaultComboBoxModel(TILE_TYPE.values()));
		verticalBox_1 = Box.createVerticalBox();
	}
}
