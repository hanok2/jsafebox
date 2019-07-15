
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

package io.ortis.jsafebox.hash;

import java.security.MessageDigest;

public class SHA256 implements Hasher
{

	private final MessageDigest md;

	public SHA256()
	{
		try
		{
			this.md = MessageDigest.getInstance("SHA-256");
		} catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte [] hash(byte [] data)
	{
		return md.digest(data);
	}

	

	@Override
	public int getHashLength()
	{
		return 32;
	}

}
