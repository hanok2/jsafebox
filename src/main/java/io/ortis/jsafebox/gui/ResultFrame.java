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


import io.ortis.jsafebox.Utils;
import io.ortis.jsafebox.gui.tasks.GUITask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * @author Ortis
 */
public class ResultFrame extends JDialog implements WindowListener, KeyListener
{
	private final static long SUCCESS_DISPOSE_DELAY = 1500;
	private final Window parent;
	private final GUITask task;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JLabel headerLabel;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	private JLabel loadingIconLabel;
	private JPanel logPanel;
	private JPanel mainPanel;
	private JLabel resultLabel;
	// End of variables declaration//GEN-END:variables

	/**
	 * Creates new form ResultFrame
	 */
	public ResultFrame(final Window parent, final GUITask task)
	{
		super(parent);
		this.parent = parent;
		this.task = task;

		initComponents();
		final Settings settings = GUI.getSettings();
		mainPanel.setBackground(settings.getUITheme().getBackgroundColor());
		jPanel1.setBackground(settings.getUITheme().getLeftPanelBackgroundColor());
		this.jTextArea1.setEditable(false);

		final Exception exception = this.task == null ? null : this.task.getException();
		if(this.task == null)
		{
			loadingIconLabel.setIcon(new ImageIcon(settings.getSuccessIcon()));
			mainPanel.remove(logPanel);
		}
		else if(exception == null)
		{
			if(this.task.skipResultOnSuccess())
				return;

			headerLabel.setText(this.task.getSuccessHeader());
			loadingIconLabel.setIcon(new ImageIcon(settings.getSuccessIcon()));
			resultLabel.setText(this.task.getSuccessMessage());


			mainPanel.remove(logPanel);
			this.setSize(getWidth(), jPanel1.getHeight() + resultLabel.getHeight() + 40);

			final Thread thread = new Thread()
			{

				public void run()
				{

					try
					{
						Thread.sleep(SUCCESS_DISPOSE_DELAY);
					} catch(final InterruptedException e)
					{

					}

					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							dispose();
						}
					});
				}

			};
			thread.setName("result frame success delay");
			thread.start();

		}
		else
		{
			headerLabel.setText("Error");
			loadingIconLabel.setIcon(new ImageIcon(getClass().getResource("/img/error-50.png")));
			// loadingIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mochimomascot/mochimomascot10.png")));
			resultLabel.setText("<html>" + exception.getMessage() + "</html>");
			final String exceptionStackTrace = Utils.formatException(exception);
			final StringBuilder sb = new StringBuilder();

			sb.append("\n");
			sb.append(exceptionStackTrace);
			sb.append("\n");
			sb.append("-----------------------------------------------------");
			sb.append("\n");
			sb.append("\n");

			for(final LogRecord record : this.task.getLogs(new ArrayList<>()))
				sb.append(record.getMessage() + "\n");

			sb.append("\n");

			jTextArea1.setText(sb.toString());
		}

		addWindowListener(this);

		settings.applyHeaderLabelStyle(this.headerLabel);
		headerLabel.addKeyListener(this);

		settings.applyTextAreaStyle(this.jTextArea1);
		jTextArea1.addKeyListener(this);
		addKeyListener(this);

		if(parent != null)
		{
			setLocationRelativeTo(parent);
			setModal(true);
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		mainPanel = new JPanel();
		jPanel1 = new JPanel();
		loadingIconLabel = new JLabel();
		headerLabel = new JLabel();
		resultLabel = new JLabel();
		logPanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(425, 461));

		mainPanel.setBackground(new Color(45, 58, 79));

		jPanel1.setBackground(new Color(22, 28, 38));

		loadingIconLabel.setIcon(new ImageIcon(getClass().getResource("/img/ok-24.png"))); // NOI18N

		headerLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
		headerLabel.setForeground(new Color(115, 122, 133));
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setText("Processing Wallet");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(
						loadingIconLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(headerLabel,
						GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE).addGap(57, 57, 57)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(loadingIconLabel, GroupLayout.Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE).addGroup(
				jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE).addContainerGap()));

		resultLabel.setFont(new Font("Segoe UI", 0, 15)); // NOI18N
		resultLabel.setForeground(new Color(255, 255, 255));
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setText("<html>Invalid mnemonic - Mnemonic phrase must be a at least 32 characters long</html>");

		logPanel.setPreferredSize(new Dimension(263, 50));

		jTextArea1.setColumns(20);
		jTextArea1.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
		jTextArea1.setRows(5);
		jTextArea1.setText(
				"java.lang.Exception: Invalid mnemonic - Mnemonic phrase must be a at least 32 characters long\nio.ortis.mochimo.mojo.gui.swing.download_task.CreateWalletTask.task(CreateWalletTask.java:120)\nio.ortis.mochimo.mojo.gui.swing.download_task.AbstractGUITask.run(AbstractGUITask.java:49)\njava.lang.Thread.run(Thread.java:745)\n\n-----------------------------------------------------\n\nChecking wallet path\nC:\\Users\\ortis\\workspaces\\mochimo\\mojo-app\\wallet-Sun 31 Mar 2019 222830.mcm\nChecking password\nParsing PBKDF2\n\njava.lang.Exception: Invalid mnemonic - Mnemonic phrase must be a at least 32 characters long\nio.ortis.mochimo.mojo.gui.swing.download_task.CreateWalletTask.task(CreateWalletTask.java:120)\nio.ortis.mochimo.mojo.gui.swing.download_task.AbstractGUITask.run(AbstractGUITask.java:49)\njava.lang.Thread.run(Thread.java:745)\n\n-----------------------------------------------------\n\nChecking wallet path\nC:\\Users\\ortis\\workspaces\\mochimo\\mojo-app\\wallet-Sun 31 Mar 2019 222830.mcm\nChecking password\nParsing PBKDF2");
		jScrollPane1.setViewportView(jTextArea1);

		GroupLayout logPanelLayout = new GroupLayout(logPanel);
		logPanel.setLayout(logPanelLayout);
		logPanelLayout.setHorizontalGroup(logPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1));
		logPanelLayout.setVerticalGroup(
				logPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
						337, Short.MAX_VALUE));

		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(mainPanelLayout.createSequentialGroup().addGap(10, 10, 10).addComponent(resultLabel,
				GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE).addContainerGap()).addComponent(logPanel, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mainPanelLayout.createSequentialGroup().addComponent(
				jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED).addComponent(resultLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED).addComponent(logPanel, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)));

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	@Override
	public void windowOpened(WindowEvent e)
	{
		jTextArea1.setCaretPosition(0);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{

	}

	@Override
	public void windowClosed(final WindowEvent e)
	{

		dispose();
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

	@Override
	public void keyTyped(final KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e == null)
			return;

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dispose();
	}

	@Override
	public void keyReleased(KeyEvent e)
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
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(ResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(ResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(ResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(ResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				ResultFrame dialog = new ResultFrame(null, null);
				dialog.addWindowListener(new java.awt.event.WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}
}
