/*-
 * #%L
 * Slice - Persistence API
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
package com.cognifide.slice.persistence.api.serializer;

import java.lang.reflect.Field;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;

import com.cognifide.slice.persistence.api.SerializerContext;

public class FieldSerializerAdapter implements FieldSerializer {

	private final ObjectSerializer objectSerializer;

	public FieldSerializerAdapter(ObjectSerializer fieldSerializer) {
		this.objectSerializer = fieldSerializer;
	}

	@Override
	public int getPriority() {
		return objectSerializer.getPriority();
	}

	@Override
	public boolean accepts(Field field) {
		return objectSerializer.accepts(field.getType());
	}

	@Override
	public void serialize(Field field, String propertyName, Object fieldValue, Resource parent,
			SerializerContext ctx) throws PersistenceException {
		objectSerializer.serialize(propertyName, fieldValue, parent, ctx);
	}

}
