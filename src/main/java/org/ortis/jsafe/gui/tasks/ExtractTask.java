/*******************************************************************************
 * Copyright 2018 Ortis (cao.ortis.org@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.ortis.jsafe.gui.tasks;

import java.io.File;

import javax.swing.SwingWorker;

import org.ortis.jsafe.Safe;
import org.ortis.jsafe.SafeFile;
import org.ortis.jsafe.commands.Extract;
import org.ortis.jsafe.gui.SafeExplorer;
import org.ortis.jsafe.task.MultipartTask;
import org.ortis.jsafe.task.TaskProbeAdapter;

public class ExtractTask extends MultipartTask implements GuiTask
{

	private final SafeExplorer safeExplorer;
	private final Safe safe;
	private final SafeFile safeFile;
	private final File destination;

	public ExtractTask(final SafeExplorer safeExplorer, final SafeFile safeFile, final File destination) throws Exception
	{

		this.safeExplorer = safeExplorer;
		this.safe = this.safeExplorer.getSafe();
		this.safeFile = safeFile;
		this.destination = destination;
		if (!this.destination.isDirectory())
			throw new Exception("Destination must be a directory");
	}

	public void start()
	{

		this.fireMessage("Initalizing transfert...");
		this.fireProgress(0);
		new SwingWorker<Void, String>()
		{

			@Override
			protected Void doInBackground() throws Exception
			{

				ExtractTask.this.fireMessage("Extracting " + safeFile.getName() + "...");
				final TaskProbeAdapter adapter = new TaskProbeAdapter();
				ExtractTask.this.setSubTask(adapter);
				try
				{
					Extract.extract(safe, safeFile, destination, adapter);

				} catch (final Exception e)
				{
					
					if (ExtractTask.this.isCancelRequested())
					{
						ExtractTask.this.fireCanceled();
					}
					return null;

				} finally
				{
					ExtractTask.this.setSubTask(null);
				}

				return null;
			}

			@Override
			protected void done()
			{
				ExtractTask.this.fireTerminated();
			}

		}.execute();

	}

}