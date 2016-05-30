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
package com.cognifide.slice.core.internal.monitoring;

import org.junit.Assert
import org.mockito.Mockito

import com.cognifide.slice.api.provider.ModelProvider
import com.cognifide.slice.core.internal.injector.InjectorHierarchy
import com.cognifide.slice.core.internal.provider.SliceModelProvider
import com.cognifide.slice.test.module.CompositeModel
import com.cognifide.slice.test.module.SimpleModel;
import com.cognifide.slice.test.setup.BaseSetup

/**
 * @author Jacek Fohs
 */
class StatisticsGatheringTest extends BaseSetup {

	def setupSpec() {
		setup:
		nodeBuilder.content {
			foo(prop1: "abc123")
			bar(prop2: "cba321")
		}
	}

	def "Get models with disabled statistics"() {
		setup:
		InjectorStatisticsRepository statsRepo = injector.getInstance(InjectorStatisticsRepository.class);
		ModelUsageData root = statsRepo.modelUsageDataRoot;
		Assert.assertNotNull(root);
		Assert.assertNotNull(root.subModels);
		Assert.assertTrue(root.subModels.isEmpty());
		def compositeModel = modelProvider.get(CompositeModel.class, "/content");
		Assert.assertNotNull(compositeModel);
		Assert.assertNotNull(compositeModel.foo);
		Assert.assertNotNull(compositeModel.foo.prop1);
		Assert.assertTrue(root.subModels.isEmpty());
	}

	def "Get models with enabled statistics"() {
		setup:
		InjectorStatisticsRepository statsRepo = injector.getInstance(InjectorStatisticsRepository.class);
		statsRepo.setEnabled(true);
		SliceModelProvider sliceProvider = modelProvider;
		// Simulate new context start
		def enabledField = ExecutionStatisticsStack.class.getDeclaredField("monitoringEnabled")
		enabledField.setAccessible(true);
		enabledField.setBoolean(sliceProvider.executionStatisticsStack, statsRepo.isEnabled());
		// Simulate new context end
		ModelUsageData root = statsRepo.modelUsageDataRoot;
		Assert.assertNotNull(root);
		Assert.assertNotNull(root.subModels);
		Assert.assertTrue(root.subModels.isEmpty());
		def c1 = modelProvider.get(CompositeModel.class, "/content");

		Assert.assertNotNull(c1);
		Assert.assertNotNull(c1.foo);
		Assert.assertNotNull(c1.foo.prop1);
		Assert.assertFalse(root.subModels.isEmpty());
		ModelUsageData modelRoot = statsRepo.getModelUsageDataRoot();
		Assert.assertTrue(modelRoot.getSubModels().containsKey(CompositeModel.class));
		ModelUsageData composite = modelRoot.getSubModels().get(CompositeModel.class);
		Assert.assertTrue(composite.getCount() == 1);
		Assert.assertTrue(composite.getSubModels().containsKey(SimpleModel.class));
		ModelUsageData simple = composite.getSubModels().get(SimpleModel.class);
		Assert.assertTrue(simple.getCount() == 2);
		Assert.assertTrue(simple.getTotalTime() <= composite.getTotalTime());
		
		statsRepo.clear();
		Assert.assertTrue(root.subModels.isEmpty());
	}
}
