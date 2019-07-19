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

import io.ortis.jsafebox.gui.tasks.OpenSafeboxTask;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Ortis
 */
public class LoginFrame extends javax.swing.JFrame implements MouseListener, ActionListener
{

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel browseLabel;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel newLabel;
    private javax.swing.JLabel openLabel;
    private javax.swing.JComboBox<String> safePathComboBox;
    // End of variables declaration//GEN-END:variables


	/**
	 * Creates new form LoginFrame
	 */
	public LoginFrame()
	{
		initComponents();

		final Settings settings = Settings.getSettings();

		mainPanel.setBackground(settings.getUITheme().getBackgroundColor());

		final JTextField field = (JTextField) safePathComboBox.getEditor().getEditorComponent();
		field.setText("Select a safe or create a new one");

                
                logoLabel.setText("<html>Jsafebox</html>");
                logoLabel.setForeground(settings.getUITheme().getButtonFirstColor());
                logoLabel.setFont(settings.getFontTheme().getLoginOpenFont());
                
		safePathComboBox.setRenderer(new ComboBoxRenderer());
		safePathComboBox.removeAllItems();
				for(final String path : settings.getSafeFilePaths())
			safePathComboBox.addItem(path);
		safePathComboBox.setFont(settings.getFontTheme().getFieldFont());

		field.requestFocus();
		field.selectAll();

		this.jPasswordField1.setText("");
		this.jPasswordField1.addActionListener(this);

		this.browseLabel.addMouseListener(this);
		this.newLabel.addMouseListener(this);

		settings.applyFirstButtonStyle(this.openLabel);
		openLabel.setFont(settings.getFontTheme().getLoginOpenFont());
		this.openLabel.addMouseListener(this);


		setIconImages(settings.getFrameIcons());


		setSize(Toolkit.getDefaultToolkit().getScreenSize().width * 1 / 3, Toolkit.getDefaultToolkit().getScreenSize().height * 1 / 3);
		setResizable(false);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getSize().width / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getSize().height / 2);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        safePathComboBox = new javax.swing.JComboBox<>();
        browseLabel = new javax.swing.JLabel();
        newLabel = new javax.swing.JLabel();
        openLabel = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        logoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        safePathComboBox.setEditable(true);
        safePathComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        safePathComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        safePathComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                safePathComboBoxActionPerformed(evt);
            }
        });

        browseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/browse-folder-32.png"))); // NOI18N

        newLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/new-32.png"))); // NOI18N

        openLabel.setBackground(new java.awt.Color(255, 102, 0));
        openLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        openLabel.setForeground(new java.awt.Color(255, 255, 255));
        openLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        openLabel.setText("Open");
        openLabel.setOpaque(true);

        jPasswordField1.setText("jPasswordField1");

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/frame-icons/safe-filled-100.png"))); // NOI18N
        logoLabel.setText("JSafebox");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(safePathComboBox, 0, 328, Short.MAX_VALUE)
                            .addComponent(jPasswordField1)
                            .addComponent(openLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(newLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(safePathComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(openLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


	public void setSafePath(final String path)
	{
		final JTextField field = (JTextField) safePathComboBox.getEditor().getEditorComponent();
		field.setText(path);
	}

	@Override
	public void mouseClicked(final MouseEvent mouseEvent)
	{
		final Object source = mouseEvent.getSource();
		if(source == null)
			return;

		if(source == this.openLabel)
		{
			final JTextField field = (JTextField) safePathComboBox.getEditor().getEditorComponent();
			final String path = field.getText();

			final OpenSafeboxTask task = new OpenSafeboxTask(path, this.jPasswordField1.getPassword(), GUI.getLogger());

			final ProgressFrame progressFrame = new ProgressFrame(this);
			progressFrame.execute(task);

			if(task.getException() == null)
			{
				GUI.getSettings().addSafeFilePath(path);

				final SafeboxFrame safeboxFrame = new SafeboxFrame(this, task.getSafe());
				SwingUtilities.invokeLater(() -> safeboxFrame.setVisible(true));

				dispose();
			}
		}
		else if(source == browseLabel)
		{
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

			fileChooser.setFileFilter(new FileNameExtensionFilter("JSafebox file", "jsb"));
			fileChooser.setCurrentDirectory(Settings.getDefaultDirectory());

			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				final String filename = fileChooser.getSelectedFile().toString();
				Settings.setDefaultDirectory(filename);

				setSafePath(filename);
			}
		}
		else if(source == newLabel)
		{
			final NewSafeFrame newSafeFrame = new NewSafeFrame(this);
			newSafeFrame.setVisible(true);
			if(newSafeFrame.getCreated() !=null)
			{
				setSafePath(newSafeFrame.getCreated().getAbsolutePath());
			}
		}
	}

	@Override
	public void mousePressed(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void mouseReleased(final MouseEvent mouseEvent)
	{

	}

	@Override
	public void mouseEntered(final MouseEvent e)
	{
		if(e.getSource() == null)
			return;

		if(e.getSource() == this.openLabel)
		{
			GUI.getSettings().applyFirstButtonMouseOverStyle(this.openLabel);
			this.openLabel.setFont(GUI.getSettings().getFontTheme().getLoginOpenFont());
		}
	}

	@Override
	public void mouseExited(final MouseEvent e)
	{
		if(e.getSource() == null)
			return;

		if(e.getSource() == openLabel)
		{
			GUI.getSettings().applyFirstButtonStyle(this.openLabel);
			this.openLabel.setFont(GUI.getSettings().getFontTheme().getLoginOpenFont());
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final Component component = (Component) e.getSource();

		if(component == null)
			return;

		if(component == jPasswordField1)
		{
			final MouseEvent me = new MouseEvent(openLabel, 0, 0, 0, 0, 0, 0, 0, 0, false, 0);
			mouseClicked(me);// simulate click
		}
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
			java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch(javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new LoginFrame().setVisible(true);
			}
		});
	}
}
