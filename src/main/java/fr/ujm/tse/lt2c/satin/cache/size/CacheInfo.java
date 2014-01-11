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

import java.util.Arrays;

import fr.ujm.tse.lt2c.satin.cache.exceptions.CacheNotFoundException;

/**
 * Cache information, manufacturer independent
 * 
 * Information on size are given on a per core basis.
 * 
 * @author Julien Subercaze
 * 
 */
public class CacheInfo {

	final CacheLevelInfo[][] cacheInformation;
	final CacheLevelInfo[] instructionCache;
	final CacheLevelInfo[] dataCache;
	final CacheLevelInfo[] unifiedCache;

	/**
	 * Singleton holder. Thread safe
	 * 
	 * @author Julien
	 * 
	 */
	private static class CacheInfoHolder {
		public static final CacheInfo instance = new CacheSize().getCacheInfo();
	}

	public static CacheInfo getInstance() {
		return CacheInfoHolder.instance;
	}

	CacheInfo() {
		instructionCache = new CacheLevelInfo[3];
		dataCache = new CacheLevelInfo[3];
		unifiedCache = new CacheLevelInfo[3];
		cacheInformation = new CacheLevelInfo[4][];
		cacheInformation[CacheType.INSTRUCTION_CACHE.ordinal()] = instructionCache;
		cacheInformation[CacheType.DATA_CACHE.ordinal()] = dataCache;
		cacheInformation[CacheType.UNIFIED_CACHE.ordinal()] = unifiedCache;

	}

	public void addCache(CacheLevelInfo cacheLevelInfo) {
		if (cacheLevelInfo.getCacheType() == CacheType.INSTRUCTION_CACHE) {
			instructionCache[cacheLevelInfo.getCacheLevel().ordinal()] = cacheLevelInfo;
		} else if (cacheLevelInfo.getCacheType() == CacheType.DATA_CACHE) {
			dataCache[cacheLevelInfo.getCacheLevel().ordinal()] = cacheLevelInfo;
		} else if (cacheLevelInfo.getCacheType() == CacheType.UNIFIED_CACHE) {
			unifiedCache[cacheLevelInfo.getCacheLevel().ordinal()] = cacheLevelInfo;
		}
	}

	/**
	 * 
	 * @param level
	 *            the level of cache from the enum {@link CacheLevel}
	 * @param type
	 *            the type of cache from the enum {@link CacheType}
	 * @return true if the given cache exists
	 */
	public boolean hasCache(CacheLevel level, CacheType type) {
		return cacheInformation[type.ordinal()][level.ordinal()] != null;
	}

	/**
	 * 
	 * @param level
	 *            the level of cache from the enum {@link CacheLevel}
	 * @param type
	 *            the type of cache from the enum {@link CacheType}
	 * @return the {@link CacheLevelInfo} for this given cache, {@code null} if
	 *         not existing
	 */
	public CacheLevelInfo getCacheInformation(CacheLevel level, CacheType type) {
		return cacheInformation[type.ordinal()][level.ordinal()];
	}

	/**
	 * The cache size is given per core. For instance on intel multicore, you
	 * have to multiply the L1 and L2 by the number of cores to get to the total
	 * cache available. However for algorithms design, only the per core
	 * matters.
	 * 
	 * @param level
	 *            the level of cache from the enum {@link CacheLevel}
	 * @param type
	 *            the type of cache from the enum {@link CacheType}
	 * @return the size of the given size in bytes, throw
	 *         {@link CacheNotFoundException} if the cache does not exist
	 * @throws CacheNotFoundException
	 */
	public int getCacheSize(CacheLevel level, CacheType type)
			throws CacheNotFoundException {
		if (hasCache(level, type)) {
			return cacheInformation[type.ordinal()][level.ordinal()]
					.getCacheSize();
		}
		throw new CacheNotFoundException(level, type);
	}

	/**
	 * 
	 * @param level
	 *            the level of cache from the enum {@link CacheLevel}
	 * @param type
	 *            the type of cache from the enum {@link CacheType}
	 * @return the size of the line in bytes
	 * @throws CacheNotFoundException
	 */
	public int getCacheLineSize(CacheLevel level, CacheType type)
			throws CacheNotFoundException {
		if (hasCache(level, type)) {
			return cacheInformation[type.ordinal()][level.ordinal()]
					.getCacheCoherencyLineSize();
		}
		throw new CacheNotFoundException(level, type);
	}

	@Override
	public String toString() {
		return "CacheInfo [instructionCache="
				+ Arrays.toString(instructionCache) + ", dataCache="
				+ Arrays.toString(dataCache) + ", unifiedCache="
				+ Arrays.toString(unifiedCache) + "]";
	}

}
