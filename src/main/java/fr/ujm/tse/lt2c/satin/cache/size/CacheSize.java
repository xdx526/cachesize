/*
 * Copyright (C) 2013 Julien Subercaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package fr.ujm.tse.lt2c.satin.cache.size;

import ru.concerteza.util.jni.CtzJniUtils;
import fr.ujm.tse.lt2c.satin.cache.exceptions.NativeCPUInfoException;

/**
 * Class that retrieves the information directly from the CPU by making native
 * calls.
 * 
 * @author Julien Subercaze
 * 
 */
public class CacheSize {
	static {
		CtzJniUtils.loadJniLibsFromStandardPath(CacheSize.class, "CacheSize");
	}

	private native int[] getCPUCacheInfo();

	/**
	 * Execute the native call to get cache information
	 * 
	 * @return information regarding the CPU cache
	 * @throws NativeCPUInfoException
	 */
	protected CacheInfo getCacheInfo() {
		int[] result = null;
		try {
			result = getCPUCacheInfo();
		} catch (Exception e) {
			return null;
		}
		CacheInfo cacheInfo = new CacheInfo();
		if (result == null)
			return null;
		boolean parse = true;
		// int[] is structured as 10 values per cache level, documentation is to
		// be found in the original C source
		int i = 0;
		int parsed = 0;
		while (parse) {
			// Ensure this part is filled
			if (result[(i * 10) + 1] > 3 || result[(i * 10) + 1] <= 0) {
				parse = false;
				break;
			}
			int k = i * 10;
			CacheLevelInfo currentLevel = new CacheLevelInfo(
					CacheLevel.values()[result[k + 1] - 1],
					CacheType.values()[result[k + 2]], result[k + 3],
					result[k + 4], result[k + 5], result[k + 6],
					result[k + 7] == 1, result[k + 8] == 1);
			cacheInfo.addCache(currentLevel);
			parsed++;
			i++;
		}
		if (parsed == 0) {
			return null;
		}
		return cacheInfo;
	}
}
