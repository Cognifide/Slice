/*-
 * #%L
 * Slice - Persistence
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
package com.cognifide.slice.persistence.impl.serializer;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;

import com.cognifide.slice.mapper.annotation.Children;
import com.cognifide.slice.persistence.api.SerializerContext;
import com.cognifide.slice.persistence.api.serializer.FieldSerializer;

public class ChildrenCollectionSerializer extends ChildrenSerializer implements FieldSerializer {

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public boolean accepts(Field field) {
		return field.isAnnotationPresent(Children.class)
				&& Collection.class.isAssignableFrom(field.getType());
	}

	@Override
	protected void createChildren(String childName, Object fieldValue, SerializerContext ctx, Resource child)
			throws PersistenceException {
		int i = 1;
		for (Object o : (Collection<?>) fieldValue) {
			ctx.getFacade().serializeObject(generateChildName(childName, i++), o, child, ctx);
		}
	}
}
