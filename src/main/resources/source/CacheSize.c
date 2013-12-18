#include <jni.h>
#include <stdio.h>
#include <stdint.h>
#include "CacheSize.h"
/* Adapted from the answer by Sergey L. to :
http://stackoverflow.com/questions/12594208/c-program-to-determine-levels-size-of-cache
*/
JNIEXPORT jintArray JNICALL Java_fr_ujm_tse_lt2c_satin_cache_size_CacheSize_getCPUCacheInfo
  (JNIEnv *env, jobject thisobject) {
    int i;
	int size=320;
    jintArray content=(jintArray)(*env)->NewIntArray(env,size);
    jint f[size];
 
    for (i = 0; i < 32; i++) {

        // Variables to hold the contents of the 4 i386 legacy registers
        uint32_t eax, ebx, ecx, edx; 

        eax = 4; // get cache info
        ecx = i; // cache id

        __asm__ (
            "cpuid" // call i386 cpuid instruction
            : "+a" (eax) // contains the cpuid command code, 4 for cache query
            , "=b" (ebx)
            , "+c" (ecx) // contains the cache id
            , "=d" (edx)
        ); // generates output in 4 registers eax, ebx, ecx and edx 

        // taken from http://download.intel.com/products/processor/manual/325462.pdf Vol. 2A 3-149
        int cache_type = eax & 0x1F; 

        if (cache_type == 0) // end of valid cache identifiers
            break;

        char * cache_type_string;
        switch (cache_type) {
            case 1: cache_type_string = "Data Cache"; break;
            case 2: cache_type_string = "Instruction Cache"; break;
            case 3: cache_type_string = "Unified Cache"; break;
            default: cache_type_string = "Unknown Type Cache"; break;
        }

        int cache_level = (eax >>= 5) & 0x7;

        int cache_is_self_initializing = (eax >>= 3) & 0x1; // does not need SW initialization
        int cache_is_fully_associative = (eax >>= 1) & 0x1;


        // taken from http://download.intel.com/products/processor/manual/325462.pdf 3-166 Vol. 2A
        // ebx contains 3 integers of 10, 10 and 12 bits respectively
        unsigned int cache_sets = ecx + 1;
        unsigned int cache_coherency_line_size = (ebx & 0xFFF) + 1;
        unsigned int cache_physical_line_partitions = ((ebx >>= 12) & 0x3FF) + 1;
        unsigned int cache_ways_of_associativity = ((ebx >>= 10) & 0x3FF) + 1;

        // Total cache size is the product
        size_t cache_total_size = cache_ways_of_associativity * cache_physical_line_partitions * cache_coherency_line_size * cache_sets;
		
		int z = 10*i;
		f[z] = i;
		f[z+1] = cache_level;
		f[z+2] = cache_type;
		f[z+3] = cache_sets;
		f[z+4] = cache_coherency_line_size;
		f[z+5] = cache_physical_line_partitions;
		f[z+6] = cache_ways_of_associativity;
		f[z+7] = cache_is_fully_associative;
		f[z+8] = cache_is_self_initializing;
		f[z+9] = -1;
    }
	 (*env)->SetIntArrayRegion(env,content,0,size,(jint*)f);   
    return content;
}
