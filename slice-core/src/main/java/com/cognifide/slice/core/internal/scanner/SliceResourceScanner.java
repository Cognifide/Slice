package com.cognifide.slice.core.internal.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.cognifide.slice.core.internal.module.AnnotationReader;
import com.cognifide.slice.core.internal.scanner.BundleClassesFinder.ClassFilter;
import com.cognifide.slice.mapper.annotation.SliceResource;

public class SliceResourceScanner {
	private final BundleContext bundleContext;

	public SliceResourceScanner(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public Collection<Class<?>> findSliceResources(String bundleNameFilter, String basePackage) {
		List<Bundle> bundles = findBundles(bundleNameFilter);
		BundleClassesFinder classFinder = new BundleClassesFinder(bundles, basePackage);
		classFinder.addFilter(new ClassFilter() {
			@Override
			public boolean accepts(ClassReader classReader) {
				AnnotationReader annotationReader = new AnnotationReader();
				classReader.accept(annotationReader, ClassReader.SKIP_DEBUG);
				return annotationReader.isAnnotationPresent(SliceResource.class);
			}
		});
		return classFinder.getClasses();
	}

	private List<Bundle> findBundles(String bundleNameFilter) {
		Pattern bundleNamePattern = Pattern.compile(bundleNameFilter);
		List<Bundle> bundles = new ArrayList<Bundle>();
		for (Bundle bundle : bundleContext.getBundles()) {
			if (bundleNamePattern.matcher(bundle.getSymbolicName()).matches()) {
				bundles.add(bundle);
			}
		}
		return bundles;
	}
}