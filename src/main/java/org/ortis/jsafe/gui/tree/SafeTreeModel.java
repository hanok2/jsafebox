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
package org.ortis.jsafe.gui.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.ortis.jsafe.Safe;
import org.ortis.jsafe.gui.SafeExplorer;

public class SafeTreeModel extends DefaultTreeModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SafeFileTreeNode root;
	private Safe safe;
	private final SafeExplorer safeExplorer;

	public SafeTreeModel(final SafeExplorer safeExplorer)
	{

		super(safeExplorer.getSafe() == null ? new DefaultMutableTreeNode() : new SafeFileTreeNode(safeExplorer.getSafe().getRootFolder()));
		
		this.safeExplorer = safeExplorer;
		if (getRoot() instanceof SafeFileTreeNode)
			this.root = (SafeFileTreeNode) getRoot();
		else
			this.root = null;

		this.safe = this.safeExplorer.getSafe();
		

	}

	public SafeFileTreeNode getRootMode()
	{
		return this.root;
	}

	public Safe getSafe()
	{
		return this.safe;
	}
	
	public SafeExplorer getSafeExplorer()
	{
		return safeExplorer;
	}

}
