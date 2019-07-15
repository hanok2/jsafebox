/*
 *  Copyright 2019 Ortis (ortis@ortis.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.ortis.jsafebox.gui;

import io.ortis.jsafebox.Block;
import io.ortis.jsafebox.Folder;
import io.ortis.jsafebox.Safe;
import io.ortis.jsafebox.SafeFile;
import io.ortis.jsafebox.gui.old.SafeExplorer;
import io.ortis.jsafebox.gui.old.previews.ImagePreview;
import io.ortis.jsafebox.gui.old.previews.TextPreview;
import io.ortis.jsafebox.gui.tasks.ExceptionTask;
import io.ortis.jsafebox.gui.tasks.LoadTreeTask;
import io.ortis.jsafebox.gui.tree.SafeFileNodePopupMenu;
import io.ortis.jsafebox.gui.tree.SafeFileTreeNode;
import io.ortis.jsafebox.gui.tree.SafeTreeCellEditor;
import io.ortis.jsafebox.gui.viewers.ImageViewer;
import io.ortis.jsafebox.gui.viewers.TextViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ortis
 */
public class SafeboxFrame extends javax.swing.JFrame implements MouseListener, KeyListener, ActionListener, WindowListener, TreeSelectionListener
{
	public final static String TITLE = "JSafebox";
	private final Window parent;
	private final AtomicBoolean modificationPending = new AtomicBoolean(false);
	private Safe safe;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JMenu ViewMenu;
	private javax.swing.JMenuItem aboutMenuItem;
	private javax.swing.JCheckBoxMenuItem autoHashCheckBoxMenuItem;
	private javax.swing.JCheckBoxMenuItem autoSaveCheckBoxMenuItem;
	private javax.swing.JPanel belowRightPanel;
	private javax.swing.JPanel bottomPanel;
	private javax.swing.JPanel explorerPanel;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JPanel fileTreePanel;
	private javax.swing.JMenuItem hashMenuItem;
	private javax.swing.JMenu helpMenu;
	private javax.swing.JMenuItem helpMenuItem;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JSplitPane jSplitPane2;
	private javax.swing.JTree jTree1;
	private javax.swing.JPanel rightPanel;
	private javax.swing.JMenuItem saveMenuItem;
	private javax.swing.JMenu securityMenu;
	private javax.swing.JCheckBoxMenuItem showPreviewCheckBoxMenuItem;
	private javax.swing.JPanel statusPanel;
	private javax.swing.JPanel topRightPanel;

	// End of variables declaration//GEN-END:variables


	/**
	 * Creates new form SafeboxFrame
	 */
	public SafeboxFrame(final Window parent, final Safe safe)
	{
		this.parent = parent;
		this.safe = safe;

		initComponents();

		final Settings settings = Settings.getSettings();

		this.jSplitPane1.setDividerLocation(.25);
		this.jSplitPane2.setDividerLocation(.7);

		fileTreePanel.setBackground(settings.getUITheme().getLeftPanelBackgroundColor());
		rightPanel.setBackground(settings.getUITheme().getBackgroundColor());
		topRightPanel.setBackground(settings.getUITheme().getBackgroundColor());
		belowRightPanel.setBackground(settings.getUITheme().getBackgroundColor());

		bottomPanel.setBackground(settings.getUITheme().getBackgroundColor());
		final LineBorder border = (LineBorder) bottomPanel.getBorder();
		bottomPanel.setBorder(new LineBorder(jSplitPane1.getBackground(), border.getThickness(), false));

		//File menu
		KeyStroke keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		saveMenuItem.setAccelerator(keyStrokeAccelerator);
		saveMenuItem.addActionListener(this);

		keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK);
		autoSaveCheckBoxMenuItem.setAccelerator(keyStrokeAccelerator);
		autoSaveCheckBoxMenuItem.setSelected(settings.isAutoSave());
		autoSaveCheckBoxMenuItem.setToolTipText("Automatically save after a modification");
		autoSaveCheckBoxMenuItem.addActionListener(this);

		//View menu
		keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		showPreviewCheckBoxMenuItem.setAccelerator(keyStrokeAccelerator);
		showPreviewCheckBoxMenuItem.setSelected(settings.isPreview());
		showPreviewCheckBoxMenuItem.addActionListener(this);

		//Security menu
		keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
		hashMenuItem.setAccelerator(keyStrokeAccelerator);
		hashMenuItem.addActionListener(this);

		keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.SHIFT_DOWN_MASK);
		autoHashCheckBoxMenuItem.setAccelerator(keyStrokeAccelerator);
		autoHashCheckBoxMenuItem.setSelected(settings.isAutoHashCheck());
		autoHashCheckBoxMenuItem.setToolTipText("Check hash when opening");
		autoHashCheckBoxMenuItem.addActionListener(this);

		//Help menu
		keyStrokeAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
		helpMenuItem.setAccelerator(keyStrokeAccelerator);
		helpMenuItem.addActionListener(this);

		aboutMenuItem.addActionListener(this);

		//panels
		this.jScrollPane1.setOpaque(false);
		this.jScrollPane1.setBackground(fileTreePanel.getBackground());

		this.jScrollPane1.getViewport().setOpaque(false);
		this.jScrollPane1.getViewport().setBackground(fileTreePanel.getBackground());

		this.jTree1.setBackground(fileTreePanel.getBackground());
		this.jTree1.setOpaque(false);
		this.jTree1.getRootPane().setOpaque(false);
		this.jTree1.getRootPane().setBackground(fileTreePanel.getBackground());

		final SafeTreeCellEditor safeTreeCellEditor = new SafeTreeCellEditor(jTree1, (DefaultTreeCellRenderer) jTree1.getCellRenderer());
		jTree1.setCellEditor(safeTreeCellEditor);

		addWindowListener(this);

		loadSafe();

		setIconImages(settings.getFrameIcons());
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width * 2 / 3, Toolkit.getDefaultToolkit().getScreenSize().height * 2 / 3);
		setLocationRelativeTo(this);

	}


	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		explorerPanel = new javax.swing.JPanel();
		jSplitPane1 = new javax.swing.JSplitPane();
		fileTreePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		rightPanel = new javax.swing.JPanel();
		jSplitPane2 = new javax.swing.JSplitPane();
		topRightPanel = new javax.swing.JPanel();
		belowRightPanel = new javax.swing.JPanel();
		bottomPanel = new javax.swing.JPanel();
		statusPanel = new javax.swing.JPanel();
		jMenuBar1 = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		saveMenuItem = new javax.swing.JMenuItem();
		autoSaveCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		ViewMenu = new javax.swing.JMenu();
		showPreviewCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		securityMenu = new javax.swing.JMenu();
		hashMenuItem = new javax.swing.JMenuItem();
		autoHashCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
		helpMenu = new javax.swing.JMenu();
		helpMenuItem = new javax.swing.JMenuItem();
		aboutMenuItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		explorerPanel.setBackground(new java.awt.Color(255, 255, 255));

		jSplitPane1.setDividerLocation(150);
		jSplitPane1.setOneTouchExpandable(true);

		fileTreePanel.setBackground(new java.awt.Color(28, 29, 103));
		fileTreePanel.setBorder(null);
		fileTreePanel.setMinimumSize(new java.awt.Dimension(100, 0));
		fileTreePanel.setPreferredSize(new java.awt.Dimension(100, 567));
		fileTreePanel.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setOpaque(false);

		jTree1.setOpaque(false);
		jScrollPane1.setViewportView(jTree1);

		fileTreePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jSplitPane1.setLeftComponent(fileTreePanel);

		jSplitPane2.setDividerLocation(350);
		jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		jSplitPane2.setOneTouchExpandable(true);

		topRightPanel.setBackground(new java.awt.Color(177, 157, 24));
		topRightPanel.setBorder(null);

		javax.swing.GroupLayout topRightPanelLayout = new javax.swing.GroupLayout(topRightPanel);
		topRightPanel.setLayout(topRightPanelLayout);
		topRightPanelLayout.setHorizontalGroup(topRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 975, Short.MAX_VALUE));
		topRightPanelLayout.setVerticalGroup(topRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

		jSplitPane2.setTopComponent(topRightPanel);

		belowRightPanel.setBackground(new java.awt.Color(151, 246, 151));
		belowRightPanel.setBorder(null);

		javax.swing.GroupLayout belowRightPanelLayout = new javax.swing.GroupLayout(belowRightPanel);
		belowRightPanel.setLayout(belowRightPanelLayout);
		belowRightPanelLayout.setHorizontalGroup(belowRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 975, Short.MAX_VALUE));
		belowRightPanelLayout.setVerticalGroup(belowRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 566, Short.MAX_VALUE));

		jSplitPane2.setRightComponent(belowRightPanel);

		javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
		rightPanel.setLayout(rightPanelLayout);
		rightPanelLayout.setHorizontalGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane2));
		rightPanelLayout.setVerticalGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane2));

		jSplitPane1.setRightComponent(rightPanel);

		javax.swing.GroupLayout explorerPanelLayout = new javax.swing.GroupLayout(explorerPanel);
		explorerPanel.setLayout(explorerPanelLayout);
		explorerPanelLayout.setHorizontalGroup(
				explorerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING));
		explorerPanelLayout.setVerticalGroup(explorerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitPane1));

		getContentPane().add(explorerPanel, java.awt.BorderLayout.CENTER);

		bottomPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
		bottomPanel.setPreferredSize(new java.awt.Dimension(1081, 35));

		statusPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout.setHorizontalGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1067, Short.MAX_VALUE));
		statusPanelLayout.setVerticalGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 21, Short.MAX_VALUE));

		javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
		bottomPanel.setLayout(bottomPanelLayout);
		bottomPanelLayout.setHorizontalGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1071, Short.MAX_VALUE).addGroup(
				bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		bottomPanelLayout.setVerticalGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 25, Short.MAX_VALUE).addGroup(
				bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);

		fileMenu.setText("File");

		saveMenuItem.setText("Save");
		fileMenu.add(saveMenuItem);

		autoSaveCheckBoxMenuItem.setSelected(true);
		autoSaveCheckBoxMenuItem.setText("Auto save");
		fileMenu.add(autoSaveCheckBoxMenuItem);

		jMenuBar1.add(fileMenu);

		ViewMenu.setText("View");

		showPreviewCheckBoxMenuItem.setSelected(true);
		showPreviewCheckBoxMenuItem.setText("Show preview");
		ViewMenu.add(showPreviewCheckBoxMenuItem);

		jMenuBar1.add(ViewMenu);

		securityMenu.setText("Security");

		hashMenuItem.setText("Compute hash");
		securityMenu.add(hashMenuItem);

		autoHashCheckBoxMenuItem.setSelected(true);
		autoHashCheckBoxMenuItem.setText("Check hash on opening");
		securityMenu.add(autoHashCheckBoxMenuItem);

		jMenuBar1.add(securityMenu);

		helpMenu.setText("Help");

		helpMenuItem.setText("Help frame");
		helpMenu.add(helpMenuItem);

		aboutMenuItem.setText("About");
		helpMenu.add(aboutMenuItem);

		jMenuBar1.add(helpMenu);

		setJMenuBar(jMenuBar1);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	public void loadSafe()
	{
		this.modificationPending.set(false);

		final LoadTreeTask task = new LoadTreeTask(this.safe, this.jTree1, this, Settings.getSettings().isTreeLazyLoading(), GUI.getLogger());
		final ProgressFrame progressFrame = new ProgressFrame(this);
		progressFrame.execute(task);

		if(task.getException() == null)
			jTree1.expandRow(0);
		else
			jTree1.collapseRow(0);

		jTree1.updateUI();
		jTree1.repaint();
	}

	private void openNode(final SafeFileTreeNode node)
	{
		SafeFile file = (SafeFile) node.getUserObject();
		if(file.isBlock())
		{
			final Block block = (Block) file;

			final String mime = block.getProperties().get(Block.MIME_LABEL);

			final Settings settings = Settings.getSettings();
			final FileType fileType = settings.getFileType(mime);

			switch(fileType)
			{
				case Image:
					try
					{
						final ImageViewer viewer = new ImageViewer(safe, block, getTitle() + " - ");
						viewer.addWindowListener(SafeboxFrame.this);
						viewer.setVisible(true);

					} catch(final Exception exception)
					{
						new ResultFrame(this, new ExceptionTask(exception, GUI.getLogger()));
					}

					break;

				default:
					if(block.getDataLength() > settings.maxUnknownFileTypeLengthDisplay())
						break;

				case Text:
					try
					{
						final ByteArrayOutputStream baos = new ByteArrayOutputStream();
						safe.extract(block, baos);
						final String text = new String(baos.toByteArray());// Use local Charset
						final TextViewer viewer = new TextViewer(text);
						viewer.setTitle(getTitle() + " - " + block.getPath());
						viewer.addWindowListener(this);
						viewer.setVisible(true);

					} catch(final Exception exception)
					{
						new ResultFrame(this, new ExceptionTask(exception, GUI.getLogger()));
					}

					break;
			}
		}

	}

	private void loadNode(final SafeFileTreeNode node)
	{
		// clear preview
		topRightPanel.removeAll();
		SafeFile file = (SafeFile) node.getUserObject();
		if(file.isFolder())
		{
			if(node.isLeaf() && node.getSafeFile().isFolder())
				for(final SafeFile safeFile : ((Folder) node.getSafeFile()).listFiles())
					node.add(new SafeFileTreeNode(safeFile));
		}
		else
		{
			final Settings settings = Settings.getSettings();

			// preview
			final Block block = (Block) file;

			final List<String> displayed = new ArrayList<>();
/*
			propertyModel.addRow(new Object[]{Block.NAME_LABEL, block.getProperties().get(Block.NAME_LABEL)});
			propertyModel.addRow(new Object[]{Block.PATH_LABEL, block.getProperties().get(Block.PATH_LABEL)});
			propertyModel.addRow(new Object[]{"size", block.getLength() > 1000 ? MEMORY_FORMAT.format(
					block.getLength() / 1000) + " Kb" : block.getLength() + " bytes"});

			for(int i = 0; i < propertyModel.getRowCount(); i++)
				displayed.add(propertyModel.getValueAt(i, 0).toString());

			for(final Map.Entry<String, String> metadata : block.getProperties().entrySet())
				if(!displayed.contains(metadata.getKey()))
					propertyModel.addRow(new Object[]{metadata.getKey(), metadata.getValue()});
*/

			final String mime = block.getProperties().get(Block.MIME_LABEL);
			final FileType type = settings.getFileType(mime);
			if(Settings.getSettings().isPreview())
				switch(type)
				{
					case Image:
						try
						{
							final ByteArrayOutputStream baos = new ByteArrayOutputStream();
							safe.extract(block, baos);
							final ImagePreview imagePreview = new ImagePreview(ImageIO.read(new ByteArrayInputStream(baos.toByteArray())));
							topRightPanel.setBackground(Color.pink);
							//topRightPanel.add(imagePreview, BorderLayout.CENTER);
							topRightPanel.add(imagePreview);
						} catch(final Exception e)
						{
							topRightPanel.removeAll();
							new ResultFrame(this, new ExceptionTask(e, GUI.getLogger()));
						}
						break;

					case Text:
						try
						{
							final ByteArrayOutputStream baos = new ByteArrayOutputStream();
							safe.extract(block, baos);
							final String text = new String(baos.toByteArray());// use local charset
							final TextPreview textPreview = new TextPreview(text);
							topRightPanel.add(textPreview, BorderLayout.CENTER);
						} catch(final Exception e)
						{
							topRightPanel.removeAll();
							new ResultFrame(this, new ExceptionTask(e, GUI.getLogger()));
						}
						break;

					case Audio:
						try
						{
							final ImagePreview imagePreview = new ImagePreview(ImageIO.read(SafeExplorer.class.getResourceAsStream("/img/audio-file-100.png")));
							topRightPanel.add(imagePreview, BorderLayout.CENTER);
						} catch(final Exception e)
						{
							topRightPanel.removeAll();
							new ResultFrame(this, new ExceptionTask(e, GUI.getLogger()));
						}
						break;

					case Video:
						try
						{
							final ImagePreview imagePreview = new ImagePreview(ImageIO.read(SafeExplorer.class.getResourceAsStream("/img/video-file-100.png")));
							topRightPanel.add(imagePreview, BorderLayout.CENTER);
						} catch(final Exception e)
						{
							topRightPanel.removeAll();
							new ResultFrame(this, new ExceptionTask(e, GUI.getLogger()));
						}
						break;

					default:
						try
						{
							final ImagePreview imagePreview = new ImagePreview(ImageIO.read(SafeExplorer.class.getResourceAsStream("/img/binary-file-100.png")));
							topRightPanel.add(imagePreview, BorderLayout.CENTER);
						} catch(final Exception e)
						{
							topRightPanel.removeAll();
							new ResultFrame(this, new ExceptionTask(e, GUI.getLogger()));
						}
						break;
				}
		}
	}

	public void notifyModificationPending()
	{
		modificationPending.set(true);
		((DefaultTreeModel) jTree1.getModel()).reload();
		jTree1.expandRow(0);
	}


	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if(e.getSource() == saveMenuItem)
		{


		}
		else if(e.getSource() == autoSaveCheckBoxMenuItem)
		{

		}
		else if(e.getSource() == autoSaveCheckBoxMenuItem)
		{

		}
		else if(e.getSource() == showPreviewCheckBoxMenuItem)
		{

		}
		else if(e.getSource() == hashMenuItem)
		{

		}
		else if(e.getSource() == helpMenuItem)
		{

		}
		else if(e.getSource() == aboutMenuItem)
		{

		}
	}

	@Override
	public void keyTyped(final KeyEvent e)
	{
		if(e.isConsumed())
			return;

		if(e.getKeyChar() == '\n')
		{
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
			if(node.getParent() != null)
			{
				final SafeFileTreeNode sfNode = (SafeFileTreeNode) node;
				openNode(sfNode);
			}

			e.consume();
		}
		else if(e.getKeyChar() == KeyEvent.VK_DELETE)
		{
			final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.jTree1.getLastSelectedPathComponent();
			if(selectedNode != null && selectedNode instanceof SafeFileTreeNode)
			{
				final SafeFileNodePopupMenu menu = new SafeFileNodePopupMenu(jTree1, (SafeFileTreeNode) (selectedNode));
				menu.actionPerformed(new ActionEvent(selectedNode, 0, SafeFileNodePopupMenu.DELETE));
			}

			e.consume();
		}
	}

	@Override
	public void keyPressed(final KeyEvent keyEvent)
	{

	}

	@Override
	public void keyReleased(final KeyEvent keyEvent)
	{

	}

	@Override
	public void mouseClicked(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void mousePressed(final MouseEvent e)
	{
		final int selRow = jTree1.getRowForLocation(e.getX(), e.getY());
		final TreePath selPath = jTree1.getPathForLocation(e.getX(), e.getY());
		if(selRow != -1)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();

			if(node.getParent() != null)
			{
				final SafeFileTreeNode sfNode = (SafeFileTreeNode) node;

				//				loadNode(sfNode);

				if(e.getClickCount() == 2)
				{
					openNode(sfNode);
				}
				else if(SwingUtilities.isRightMouseButton(e))
				{
					jTree1.setSelectionRow(selRow);
					final SafeFileNodePopupMenu menu = new SafeFileNodePopupMenu(jTree1, sfNode);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void mouseEntered(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void mouseExited(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void valueChanged(final TreeSelectionEvent treeSelectionEvent)
	{
		final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.jTree1.getLastSelectedPathComponent();
		if(selectedNode != null && selectedNode instanceof SafeFileTreeNode)
			loadNode((SafeFileTreeNode) selectedNode);
	}

	@Override
	public void windowOpened(final WindowEvent windowEvent)
	{

	}

	@Override
	public void windowClosing(final WindowEvent event)
	{
		if(event.getSource() == this)
		{
			if(this.safe != null)
			{

				if(modificationPending.get())
				{
					final WarningOptionFrame warningOptionFrame = new WarningOptionFrame(this, "Discard changes ?", null, "You have pending changes.\n\n Exit anyway ?");

					warningOptionFrame.setVisible(true);
					if(warningOptionFrame.getChoice() != Boolean.TRUE)
						return;
				}

			}
			//	statusLabel.setText("Closing safe...");
			dispose();
			GUI.exit(this.safe, 0);
		}
	}

	@Override
	public void windowClosed(final WindowEvent event)
	{

	}

	@Override
	public void windowIconified(final WindowEvent windowEvent)
	{

	}

	@Override
	public void windowDeiconified(final WindowEvent windowEvent)
	{

	}

	@Override
	public void windowActivated(final WindowEvent windowEvent)
	{

	}

	@Override
	public void windowDeactivated(final WindowEvent windowEvent)
	{

	}

	public boolean isModificationPending()
	{
		return this.modificationPending.get();
	}

	public Safe getSafe()
	{
		return safe;
	}

	public void setSafe(final Safe safe)
	{
		this.safe = safe;
		loadSafe();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(SafeboxFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(SafeboxFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(SafeboxFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(SafeboxFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new SafeboxFrame(null, null).setVisible(true);
			}
		});
	}
}