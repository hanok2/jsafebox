/*******************************************************************************
 * Copyright 2018 Ortis (cao.ortis.org@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 ******************************************************************************/

package org.ortis.jsafe.gui.tasks;

import java.io.File;

import javax.swing.SwingWorker;

import org.ortis.jsafe.commands.Init;
import org.ortis.jsafe.gui.LoginFrame;
import org.ortis.jsafe.task.MultipartTask;

public class InitTask extends MultipartTask implements GuiTask
{

	private final File safe;
	private char [] password;
	private final LoginFrame loginFrame;

	public InitTask(final File safe, final char [] password, final LoginFrame loginFrame)
	{
		this.safe = safe;
		this.password = password;
		this.loginFrame = loginFrame;

	}

	public void start()
	{

		this.fireMessage("Creating safe...");
		this.fireProgress(Double.NaN);
		new SwingWorker<Void, String>()
		{

			@Override
			protected Void doInBackground() throws Exception
			{

				InitTask.this.fireProgress(Double.NaN);

				try
				{
					Init.init(InitTask.this.safe, InitTask.this.password, null, null, 1024 * 8);

				} catch (final Exception e)
				{
					InitTask.this.fireException(e);

				} finally
				{

				}

				return null;
			}

			@Override
			protected void done()
			{
				if (InitTask.this.getException() == null && !InitTask.this.isCancelled())
				{
					InitTask.this.loginFrame.setText(InitTask.this.safe.getAbsolutePath());
				}

				InitTask.this.fireTerminated();
			}

		}.execute();

	}

}
