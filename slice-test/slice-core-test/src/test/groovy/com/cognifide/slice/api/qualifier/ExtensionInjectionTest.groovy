/*-
 * #%L
 * Slice - Core Tests
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
package com.cognifide.slice.api.qualifier

import com.google.inject.Key
import junit.framework.Assert

/**
 * Created by Jaromir Celejewski on 2015-04-22.
 */
class ExtensionInjectionTest extends SlingUrlTestBase {

	def "Get Extension"() {
		setup: "Initialize context with a filled request"
		richRequestContext()
		and: "Get extension from injector"
		def extension = injector.getInstance(Key.get(String.class, Extension.class))

		expect: "There is a html extension"
		Assert.assertEquals("html", extension)
	}

	def "Get Empty Extension for no request"() {
		setup: "Initialize context with an empty request"
		emptyRequestContext()
		and: "Get extension from injector"
		def extension = injector.getInstance(Key.get(String.class, Extension.class))

		expect: "Extension is an empty string"
		Assert.assertEquals("", extension)
	}

	def "Get Empty Extension"() {
		setup: "Initialize context with an simple request (no extension)"
		simpleRequestContext()
		and: "Get extension from injector"
		def extension = injector.getInstance(Key.get(String.class, Extension.class))

		expect: "Extension is an empty string"
		Assert.assertEquals("", extension)
	}
}
