/*-
 * #%L
 * Slice - Core
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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Key;

public class MethodMonitoringInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object invocationObject = invocation.getThis();
		if (!(invocationObject instanceof StatisticsStackProvider)) {
			throw new IllegalArgumentException(
					"Monitored instance has to implement " + StatisticsStackProvider.class.getName());
		}
		ExecutionStatisticsStack stack = ((StatisticsStackProvider) invocation.getThis()).getExecutionStatisticsStack();
		Key<?> key = findKey(invocation);
		stack.startMeasurement(key);
		try {
			return invocation.proceed();
		} finally {
			stack.endAndStoreMeasurement();
		}
	}

	private Key<?> findKey(MethodInvocation invocation) {
		Key<?> result = null;
		for (Object arg : invocation.getArguments()) {
			if (arg instanceof Key<?>) {
				result = (Key<?>) arg;
				break;
			}
		}
		if (result == null) {
			String exceptionMsg = String.format("Method %s from class %s is not eligible for @%s annotation.",
					invocation.getMethod().getName(), invocation.getThis().getClass(), Monitored.class.getName());
			throw new IllegalArgumentException(exceptionMsg);
		}
		return result;
	}
}
