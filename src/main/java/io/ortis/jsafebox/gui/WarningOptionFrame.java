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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @author Ortis
 */
public class WarningOptionFrame extends JDialog implements WindowListener, MouseListener, KeyListener
{

	private Boolean choice;


	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JLabel continueLabel;
	private JLabel headerMessageLabel;
	private JPanel headerPanel;
	private JScrollPane jScrollPane1;
	private JLabel loadingIconLabel;
	private JPanel logPanel;
	private JPanel mainPanel;
	private JLabel noLabel;
	private JTextArea textArea;
	private JLabel yesLabel;

	// End of variables declaration//GEN-END:variables

	/**
	 * Creates new form ProgressFrame
	 */
	public WarningOptionFrame(final Window parent, final String header, final String message, final String question)
	{
		super(parent);

		initComponents();
		final Settings settings = GUI.getSettings();

		headerPanel.setBackground(settings.getUITheme().getLeftPanelBackgroundColor());
		logPanel.setBackground(settings.getUITheme().getBackgroundColor());
		mainPanel.setBackground(settings.getUITheme().getBackgroundColor());


		settings.applyTextAreaStyle(this.textArea);
		textArea.setForeground(settings.getUITheme().getTextLabelColor());
		textArea.setBackground(settings.getUITheme().getBackgroundColor());
		textArea.setBorder(null);


		jScrollPane1.setOpaque(false);
		jScrollPane1.setBackground(textArea.getBackground());
		jScrollPane1.setBorder(null);

		jScrollPane1.getViewport().setOpaque(false);
		jScrollPane1.getViewport().setBackground(textArea.getBackground());
		jScrollPane1.setViewportBorder(null);

		settings.applyFirstButtonStyle(yesLabel);
		settings.applySecondButtonStyle(noLabel);

		settings.applyHeaderLabelStyle(this.headerMessageLabel);
		headerMessageLabel.setText(header);

		textArea.setText(message);
		textArea.setWrapStyleWord(true);
		if(question == null)
		{
			yesLabel.setVisible(false);
			noLabel.setVisible(false);
			continueLabel.setText(null);
		}
		else
		{
			continueLabel.setText("<html>" + question + "</html>");
			yesLabel.addMouseListener(this);
			noLabel.addMouseListener(this);
		}

		Settings.addKeyListener(this, this);

		addWindowListener(this);
		pack();
		if(parent != null)
		{
			setLocationRelativeTo(parent);
			setModal(true);
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		mainPanel = new JPanel();
		headerPanel = new JPanel();
		loadingIconLabel = new JLabel();
		headerMessageLabel = new JLabel();
		logPanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		textArea = new JTextArea();
		continueLabel = new JLabel();
		yesLabel = new JLabel();
		noLabel = new JLabel();

		mainPanel.setBackground(new Color(45, 58, 79));

		headerPanel.setBackground(new Color(22, 28, 38));

		loadingIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingIconLabel.setIcon(new ImageIcon(getClass().getResource("/img/warning-50.png"))); // NOI18N

		headerMessageLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
		headerMessageLabel.setForeground(new Color(115, 122, 133));
		headerMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerMessageLabel.setText("Processing wallet");

		GroupLayout headerPanelLayout = new GroupLayout(headerPanel);
		headerPanel.setLayout(headerPanelLayout);
		headerPanelLayout.setHorizontalGroup(
				headerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(headerPanelLayout.createSequentialGroup().addContainerGap().addComponent(
						loadingIconLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(headerMessageLabel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(44, 44, 44)));
		headerPanelLayout.setVerticalGroup(headerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				headerPanelLayout.createSequentialGroup().addContainerGap().addComponent(headerMessageLabel, GroupLayout.PREFERRED_SIZE, 28,
						GroupLayout.PREFERRED_SIZE).addContainerGap(11, Short.MAX_VALUE)).addComponent(loadingIconLabel, GroupLayout.Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		logPanel.setBackground(new Color(45, 58, 79));

		jScrollPane1.setBorder(null);

		textArea.setEditable(false);
		textArea.setBackground(new Color(45, 58, 79));
		textArea.setColumns(20);
		textArea.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setText("<html>Exporting the secret is dangerous. Anyone having acces to the export file will control the address</html>");
		textArea.setBorder(null);
		jScrollPane1.setViewportView(textArea);

		GroupLayout logPanelLayout = new GroupLayout(logPanel);
		logPanel.setLayout(logPanelLayout);
		logPanelLayout.setHorizontalGroup(logPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING,
				logPanelLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE).addContainerGap()));
		logPanelLayout.setVerticalGroup(
				logPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE));

		continueLabel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
		continueLabel.setForeground(new Color(255, 255, 255));
		continueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		continueLabel.setText(
				"<html>Do you want to continue and export the secret ?Do you want to continue and export the secret ?Do you want to continue and export the secret ?</html>");

		yesLabel.setBackground(new Color(55, 120, 106));
		yesLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
		yesLabel.setForeground(new Color(255, 255, 255));
		yesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yesLabel.setText("Yes");
		yesLabel.setOpaque(true);

		noLabel.setBackground(new Color(99, 105, 112));
		noLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
		noLabel.setForeground(new Color(255, 255, 255));
		noLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noLabel.setText("No");
		noLabel.setOpaque(true);

		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(headerPanel, GroupLayout.Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(logPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE).addGroup(mainPanelLayout.createSequentialGroup().addContainerGap().addGroup(mainPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(continueLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE).addGroup(
				mainPanelLayout.createSequentialGroup().addComponent(noLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(yesLabel, GroupLayout.PREFERRED_SIZE, 93,
						GroupLayout.PREFERRED_SIZE))).addContainerGap()));

		mainPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[]{noLabel, yesLabel});

		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addComponent(
				headerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
				logPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED).addComponent(continueLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.UNRELATED).addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(noLabel,
				GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addComponent(yesLabel, GroupLayout.PREFERRED_SIZE, 43,
				GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		mainPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[]{noLabel, yesLabel});

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	public Boolean getChoice()
	{
		return choice;
	}

	@Override
	public void mouseClicked(final MouseEvent e)
	{

		final Component component = (Component) e.getSource();

		if(component == null)
			return;

		if(component == yesLabel)
		{
			this.choice = true;
			dispose();

		}
		else if(component == noLabel)
		{
			this.choice = false;
			dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(final KeyEvent e)
	{

	}

	@Override
	public void keyPressed(final KeyEvent e)
	{
		if(e.isConsumed())
			return;

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			this.choice = null;
			e.consume();
			dispose();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e)
	{
	}

	@Override
	public void windowOpened(final WindowEvent e)
	{
		textArea.setCaretPosition(0);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{

	}

	@Override
	public void windowClosed(final WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(WarningOptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(WarningOptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(WarningOptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(WarningOptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new WarningOptionFrame(null, "Header", "some text", "Continue ?").setVisible(true);
			}
		});
	}
}
