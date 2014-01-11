package fr.ujm.tse.lt2c.satin.cache.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import fr.ujm.tse.lt2c.satin.cache.size.CacheInfo;
import fr.ujm.tse.lt2c.satin.cache.size.CacheSize;

/**
 * Since it is machine dependent, ensure that some value is returned.
 * Unforntunately can't do better testing yet
 * 
 * @author Julien Subercaze
 * 
 */
public class TestCacheSize {

	@Test
	public void test() {
		try {
			CacheInfo cache = CacheInfo.getInstance();
			assertNotNull(cache);
		} catch (Exception e) {
			fail("Error processing the CPU information " + e.getMessage());
		}
	}

}
