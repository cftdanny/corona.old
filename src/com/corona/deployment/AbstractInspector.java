/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This handler is used to test whether an java class file is annotated by specified annotation </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractInspector implements Inspector {

	/**
	 * The logger
	 */
	private Log logger = LogFactory.getLog(Inspector.class);
	
	/**
	 * The scanner that uses this handler
	 */
	private Scanner scanner;
	
	/**
	 * {@inheritDoc}
	 * @see com.talgroup.deployment.IHandler#setScanner(com.talgroup.deployment.IScanner)
	 */
	public void setScanner(final Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * @return The scanner that uses this handler
	 */
	protected Scanner getScanner() {
		return this.scanner;
	}

	/**
	 * @param classFile the class file
	 * @return the annotation attribute
	 */
	private AnnotationsAttribute getAnnotationsAttribute(final ClassFile classFile) {
		return (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
	}
	
	/**
	 * <p>Load class from resource if this class has any of specified annotations. </p>
	 *
	 * @param name The class resource name
	 * @param types The specified annotations
	 * @return The class or <code>null</code> if does not have annotation
	 */
	protected Class<?> getAnnotationClass(final String name, final Class<? extends Annotation>... types) {
		
		// load class file as data input stream, if fail to load, just return null
		InputStream inputStream = null;
		DataInputStream dataStream = null;
		try {
			inputStream = this.scanner.getClassLoader().getResourceAsStream(name);
			dataStream = new DataInputStream(this.scanner.getClassLoader().getResourceAsStream(name));
		} catch (Exception e) {
			
			this.logger.error("Fail to load class file [{0}] as input stream, skip this class file", e, name);
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ex) {
				this.logger.error("Fail to close opened input stream for file [{0}], skip this error", e, name);
			}
		}
		if (dataStream == null) {
			return null;
		}
		
		// load data input stream to class file, if fail to load, just return null
		ClassFile classFile = null;
		try {
			classFile = new ClassFile(dataStream);
		} catch (Exception e) {
			if (this.logger.isErrorEnabled()) {
				this.logger.error("Fail to load class by file [{0}], skip this class file", e, name);
			}
		} finally {
			try {
				dataStream.close();
				inputStream.close();
			} catch (IOException e) {
				if (this.logger.isErrorEnabled()) {
					this.logger.error("Fail to close file [{0}], discard this error", e, name);
				}
			}
		}
		if (classFile == null) {
			return null;
		}
		
		// get annotations attribute from loaded class file, if it is null, just return null
		AnnotationsAttribute attribute = this.getAnnotationsAttribute(classFile);
		if (attribute == null) {
			return null;
		}
		
		// find whether specified annotations exist or not, if exist, return it, if not, return null
		for (Class<? extends Annotation> type : types) {
			
			if (attribute.getAnnotation(type.getName()) != null) {
				try {
					return this.getScanner().getClassLoader().loadClass(this.getHandledClassName(name));
				} catch (Exception e) {
					this.logger.error("Fail to load file [{0}] as class", e, name);
					return null;
				}
			}
		}
		return null;
	}
	
	/**
	 * <p>Get the class name from class path or jar resource name </p>
	 *
	 * @param name The resource name
	 * @return The class name
	 */
	public String getHandledClassName(final String name) {
		return name.replace('/', '.').substring(0, name.length() - 6);
	}
	
	/**
	 * <p>Get all annotated public methods in a class with specified annotation </p>
	 *
	 * @param clazz The class
	 * @param type The annotation type
	 * @return All the methods annotated with an annotation
	 */
	protected List<Method> getAnnotationMethods(Class< ? > clazz, Class< ? extends Annotation> type) {
		List<Method> methods = new ArrayList<Method>();
		
		try {
			for (Method method : clazz.getMethods()) {
				if (method.isAnnotationPresent(type) && Modifier.isPublic(method.getModifiers())) {
					methods.add(method);
				}
			}
		} catch (Exception e) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(Constants.LOG_HDL_FAIL_GET_CLASS_METHOD, clazz.getName());
			}
		} catch (NoClassDefFoundError e) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(Constants.LOG_HDL_FAIL_GET_CLASS_METHOD, clazz.getName());
			}
		}
		
		return methods; 
	}
	
	/**
	 * <p>Try to check whether specified class has been annotated with an annotation </p>
	 *
	 * @param name The class file name
	 * @param type The annotation type
	 * @return Whether class has been annotated with specified annotation
	 */
	protected boolean isAnnotationPresent(String name, Class< ? extends Annotation> type) {
		
		InputStream is = this.scanner.getClassLoader().getResourceAsStream(name);
		DataInputStream dis = new DataInputStream(is);
		
		ClassFile classFile = null;
		try {
			classFile = new ClassFile(dis);
		} catch (Exception e) {
			if (this.logger.isErrorEnabled()) {
				this.logger.error(Constants.LOG_HDL_FAIL_LOAD_CLASS_STREAM, e, name);
			}
		} finally {
			try {
				dis.close();
				is.close();
			} catch (IOException e) {
				if (this.logger.isErrorEnabled()) {
					this.logger.error(Constants.LOG_HDL_FAIL_CLOSE_CLASS_STREAM, e, name);
				}
			}
		}
		
		if (classFile != null) {
			AnnotationsAttribute attr = (AnnotationsAttribute) classFile.getAttribute(
					AnnotationsAttribute.visibleTag
			);
			
			return (attr != null) ? (attr.getAnnotation(type.getName()) != null) : false;
		} else {
			return false;
		}
	}}
