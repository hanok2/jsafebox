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

package org.ortis.jsafebox.gui.tasks;

import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;

import org.ortis.jsafebox.Safe;
import org.ortis.jsafebox.task.MultipartTask;
import org.ortis.jsafebox.task.TaskProbeAdapter;

public class HashTask extends MultipartTask implements GuiTask
{

	private final Safe safe;
	private String hash;

	public HashTask(final Safe safe)
	{
		this.safe = safe;
	}

	public void start()
	{
		final TaskProbeAdapter adapter = new TaskProbeAdapter();
		this.fireMessage("Computing integrity hash...");
		this.fireProgress(Double.NaN);
		new SwingWorker<String, String>()
		{

			@Override
			protected String doInBackground() throws Exception
			{

				System.gc();// Somehow, it looks like the GC is having trouble detecting large heap when using ByteBuffer. So we just call it before and after hash computation
				HashTask.this.fireProgress(Double.NaN);
				HashTask.this.setSubTask(adapter);

				try
				{
					final byte [] hash = safe.computeHash(adapter);
					return DatatypeConverter.printHexBinary(hash);

				} catch (final Exception e)
				{

				}
				return null;
			}

			@Override
			protected void done()
			{
				System.gc();
				if (HashTask.this.getException() == null && !HashTask.this.isCancelled())
				{
					try
					{
						HashTask.this.hash = get();
					} catch (final Exception e)
					{
						HashTask.this.fireException(e);
					}
				}

				HashTask.this.fireTerminated();
			}

		}.execute();

	}

	public String getHash()
	{
		return hash;
	}

}
