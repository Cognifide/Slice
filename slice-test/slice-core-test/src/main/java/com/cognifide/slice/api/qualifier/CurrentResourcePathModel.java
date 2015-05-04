/*-
 * #%L
 * Slice - Core Tests
 * %%
 * Copyright (C) 2012 Cognifide Limited
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
package com.cognifide.slice.api.qualifier;

import com.cognifide.slice.mapper.annotation.SliceResource;
import com.google.inject.Inject;

/**
 * @author Mariusz Kubiś Date: 10.04.15
 */
@SliceResource
public class CurrentResourcePathModel {

	private String currentResourcePath;

	@Inject
	public CurrentResourcePathModel(@CurrentResourcePath String currentResourcePath) {
		this.currentResourcePath = currentResourcePath;
	}

	public String getCurrentResourcePath() {
		return currentResourcePath;
	}
}
