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
package fr.ujm.tse.lt2c.satin.cache.exceptions;

public class NativeCPUInfoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3902273889059017680L;

	public NativeCPUInfoException() {
		super(
				"Error while parsing the CPU information. Make sure your CPU manufacturer is supported by this library");
	}
}
