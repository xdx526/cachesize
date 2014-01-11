package fr.ujm.tse.lt2c.satin.cache.test;

import fr.ujm.tse.lt2c.satin.cache.exceptions.CacheNotFoundException;
import fr.ujm.tse.lt2c.satin.cache.exceptions.NativeCPUInfoException;
import fr.ujm.tse.lt2c.satin.cache.size.CacheInfo;
import fr.ujm.tse.lt2c.satin.cache.size.CacheLevel;
import fr.ujm.tse.lt2c.satin.cache.size.CacheSize;
import fr.ujm.tse.lt2c.satin.cache.size.CacheType;

/**
 * Not a test
 * 
 * @author Julien Subercaze
 * 
 */
public class Helper {

	public static void main(String[] args) throws NativeCPUInfoException,
			CacheNotFoundException {
		CacheInfo info = CacheInfo.getInstance();
		System.out.println(info);
		System.out.println(info.getCacheSize(CacheLevel.L1,
				CacheType.INSTRUCTION_CACHE));
		System.out.println(info.getCacheLineSize(CacheLevel.L1,
				CacheType.INSTRUCTION_CACHE));
		System.out.println(info.getCacheSize(CacheLevel.L2,
				CacheType.UNIFIED_CACHE));
		System.out.println(info.getCacheSize(CacheLevel.L3,
				CacheType.UNIFIED_CACHE));
	}
}
