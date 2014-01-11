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

/**
 * Bean describing the values for a given level of cache. Fields name are self
 * explanatory
 * <p>
 * Field unit is the bit, if not specified otherwise
 * </p>
 * 
 * @author Julien Subercaze
 * 
 */
public class CacheLevelInfo {

	private CacheLevel cacheLevel;

	private CacheType cacheType;

	private int cacheSets;

	private int cacheCoherencyLineSize;

	private int cachePhysicalLinePartitions;

	private int cacheWaysOfAssociativity;

	private boolean isFullyAssociative;

	private boolean isSelfInitializing;

	private int totalSizeInBytes;

	public CacheLevelInfo(CacheLevel cacheLevel, CacheType cacheType,
			int cacheSets, int cacheCoherencyLineSize,
			int cachePhysicalLinePartitions, int cacheWaysOfAssociativity,
			boolean isFullyAssociative, boolean isSelfInitializing) {
		super();
		this.cacheLevel = cacheLevel;
		this.cacheType = cacheType;
		this.cacheSets = cacheSets;
		this.cacheCoherencyLineSize = cacheCoherencyLineSize;
		this.cachePhysicalLinePartitions = cachePhysicalLinePartitions;
		this.cacheWaysOfAssociativity = cacheWaysOfAssociativity;
		this.isFullyAssociative = isFullyAssociative;
		this.isSelfInitializing = isSelfInitializing;
		totalSizeInBytes = cacheWaysOfAssociativity
				* cachePhysicalLinePartitions * cacheCoherencyLineSize
				* cacheSets;
	}

	public CacheLevel getCacheLevel() {
		return cacheLevel;
	}

	public CacheType getCacheType() {
		return cacheType;
	}

	public int getCacheSets() {
		return cacheSets;
	}

	public int getCacheCoherencyLineSize() {
		return cacheCoherencyLineSize;
	}

	public int getCachePhysicalLinePartitions() {
		return cachePhysicalLinePartitions;
	}

	public int getCacheWaysOfAssociativity() {
		return cacheWaysOfAssociativity;
	}

	public boolean isFullyAssociative() {
		return isFullyAssociative;
	}

	public boolean isSelfInitializing() {
		return isSelfInitializing;
	}

	/**
	 * 
	 * @return the size of the cache in bytes
	 */
	public int getCacheSize() {
		return totalSizeInBytes;
	}

	@Override
	public String toString() {
		return "CacheLevelInfo [cacheLevel=" + cacheLevel + ", cacheType="
				+ cacheType + ", cacheSets=" + cacheSets
				+ ", cacheCoherencyLineSize=" + cacheCoherencyLineSize
				+ ", cachePhysicalLinePartitions="
				+ cachePhysicalLinePartitions + ", cacheWaysOfAssociativity="
				+ cacheWaysOfAssociativity + ", isFullyAssociative="
				+ isFullyAssociative + ", isSelfInitializing="
				+ isSelfInitializing + ", totalSizeInBytes=" + totalSizeInBytes
				+ "]";
	}

}
