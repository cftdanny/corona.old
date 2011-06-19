/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.corona.context.annotation.InjectType;
import com.corona.context.annotation.ScopeType;

/**
 * <p>The utility class for package <b>com.corona.context</b>. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ContextUtil {

	/**
	 * utility class
	 */
	protected ContextUtil() {
		// do nothing
	}
	
	/**
	 * <p>This method is used to find an annotation that is annotated with specified annotation type from a set 
	 * of annotations. If there are more than one annotations matches condition, just return first matched one.
	 * </p>
	 * 
	 * @param annotations a set of annotations to be checked
	 * @param annotationType the annotation type whether it is annotated in annotation
	 * @return the found annotation or <code>null</code> if it does not exists 
	 */
	public static Annotation findAnnotatedAnnotation(
			final Annotation[] annotations, final Class<? extends Annotation> annotationType
	) {
		
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAnnotationPresent(annotationType)) {
				return annotation;
			}
		}
		
		return null;
	}
	
	/**
	 * <p>Check all annotations that annotated to component type and return first annotation that is annotated
	 * with {@link com.corona.context.annotation.ScopeType}. If it does not exists, return <code>null</code>. 
	 * </p>
	 * 
	 * @param componentType the component type
	 * @return the annotation annotated with {@link ScopeType} or <code>null</code> if does not exists 
	 */
	public static Annotation findScopeAnnotation(final Class<?> componentType) {
		return findAnnotatedAnnotation(componentType.getAnnotations(), ScopeType.class);
	}
	
	/**
	 * <p>This method is used to find an annotation that is annotated with {@link InjectType} from a set of
	 * annotations. Usually, it is used to check whether constructor, field, method or method needs inject
	 * value from context manager. </p>
	 * 
	 * @param annotations a set of annotation to be checked
	 * @return the annotation annotated with {@link InjectType} or <code>null</code> if does not exists 
	 */
	public static Annotation findInjectAnnotation(final Annotation[] annotations) {
		return findAnnotatedAnnotation(annotations, InjectType.class);
	}
	
	/**
	 * <p>Find an annotation that is annotated with {@link InjectType} from all annotations present in 
	 * component constructor. 
	 * </p>
	 * 
	 * @param constructor the constructor
	 * @return the annotation annotated with {@link InjectType} or <code>null</code> 
	 */
	@SuppressWarnings("rawtypes")
	public static Annotation findInjectAnnotation(final Constructor constructor) {
		return findInjectAnnotation(constructor.getDeclaredAnnotations());
	}
	
	/**
	 * <p>Find an annotation that is annotated with {@link InjectType} from all annotations present in method. 
	 * </p>
	 * 
	 * @param method the method
	 * @return the annotation of method and is annotated with {@link InjectType} or <code>null</code> 
	 */
	public static Annotation findInjectAnnotation(final Method method) {
		return findInjectAnnotation(method.getDeclaredAnnotations());
	}

	/**
	 * @param method the method
	 * @return <code>true</code> if method is setter method
	 */
	public static boolean isSetterMethod(final Method method) {
		
		if (method.getParameterTypes().length != 1) {
			return false;
		}
		return method.getName().startsWith("set");
	}
	
	/**
	 * <p>Find an annotation that is annotated with {@link InjectType} from all annotations present in field. 
	 * </p>
	 * 
	 * @param field the field
	 * @return the annotation annotated with {@link InjectType} or <code>null</code> 
	 */
	public static Annotation findInjectAnnotation(final Field field) {
		return findInjectAnnotation(field.getDeclaredAnnotations());
	}
	
	/**
	 * <p>Find an annotation that is annotated with parent annotation from all annotations present in method. </p>
	 * 
	 * @param method the method
	 * @param parent the parent annotated annotation
	 * @return the annotation annotated with parent annotation
	 */
	public static Annotation findChainedAnnotation(final Method method, final Class<? extends Annotation> parent) {
		return findAnnotatedAnnotation(method.getAnnotations(), parent);
	}
}
