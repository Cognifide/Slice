package com.cognifide.slice.cq.module;

/*
 * #%L
 * Slice - CQ Add-on
 * $Id:$
 * $HeadURL:$
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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

import com.cognifide.slice.api.injector.InjectorServiceRunner;
import com.cognifide.slice.api.module.SliceModulesInstaller;
import com.cognifide.slice.cq.api.CQModulesInstaller;

// @formatter:off
/**
 * @author Rafał Malinowski
 * @class CQModulesInstallerImpl
 */
@Component(immediate = true)
@Service
@Properties({ //
		@Property(name = Constants.SERVICE_DESCRIPTION, value = "Factory of CQ modules."), //
		@Property(name = Constants.SERVICE_VENDOR, value = "Cognifide") })
// @formatter:on
public class CQModulesInstallerImpl implements CQModulesInstaller {

	@Override
	public void installCQModules(
			final InjectorServiceRunner injectorServiceRunner) {
		final ServiceReference sliceModulesInstallerService = injectorServiceRunner
				.getBundleContext().getServiceReference(
						SliceModulesInstaller.class.getName());
		final SliceModulesInstaller sliceModulesInstaller = (SliceModulesInstaller) injectorServiceRunner
				.getBundleContext().getService(sliceModulesInstallerService);
		sliceModulesInstaller.installSliceModules(injectorServiceRunner);

		injectorServiceRunner.installModule(new CQModule());
		injectorServiceRunner.installModule(new CQMapperModule());
		injectorServiceRunner.installModule(new DamModule());
		injectorServiceRunner.installModule(new CurrentPageModule());
		injectorServiceRunner.installModule(new RequestedPageModule());
		injectorServiceRunner.installModule(new LinkModule());
		injectorServiceRunner.installModule(new TemplateModule());
	}

}
