/*-
 * #%L
 * Slice - Mapper Tests
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
package com.cognifide.slice.mapper.annotation;

import java.util.List;

/**
 * @author Mariusz Kubiś Date: 14.04.15
 */
@SliceResource
public class ChildrenFollowModel {

	@JcrProperty
	private String text;

	@Children(JcrPropertyModel.class)
	@JcrProperty(value = "children")
	@Follow
	private List<JcrPropertyModel> childrenList;

	public String getText() {
		return text;
	}

	public List<JcrPropertyModel> getChildrenList() {
		return childrenList;
	}
}
