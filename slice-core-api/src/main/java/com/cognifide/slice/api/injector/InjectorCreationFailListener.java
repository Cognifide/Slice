/*-
 * #%L
 * Slice - Core API
 * %%
 * Copyright (C) 2012 Wunderman Thompson Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.slice.api.injector;

import aQute.bnd.annotation.ProviderType;

/**
 * Listener which is called when an injector fails during creation. It's implemented by {@link InjectorRunner}
 * .
 * 
 */
@ProviderType
public interface InjectorCreationFailListener {
	/**
	 * Called when an injector fails during creation.
	 */
	void creationFailed();
}
