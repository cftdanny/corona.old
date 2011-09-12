/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;

/**
 * <p>This descriptor is used to load java class file and verify its metadata, for example, 
 * methods, annotations, etc. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ClassDescriptor {

	/**
	 * The logger
	 */
	private Log logger = LogFactory.getLog(ClassDescriptor.class);

	/**
	 * the class loader
	 */
	private ClassLoader classLoader;
	
	/**
	 * the class name
	 */
	private String className;
	
	/**
	 * the class file
	 */
	private ClassFile classFile;
	
	/**
	 * @param classLoader the class loader to load class
	 * @param name the class file name in class path 
	 * @throws SourceException if fail to load resource as descriptor
	 */
	private ClassDescriptor(final ClassLoader classLoader, final String name) throws SourceException {
		
		// load class file as data input stream, if fail to load or don't exist, will throw exception
		InputStream inputStream = null;
		try {
			inputStream = classLoader.getResourceAsStream(name);
		} catch (Exception e) {
			this.logger.error("Fail to load resource [{0}] as stream, skip this class file", e, name);
			throw new SourceException("Fail to load resource [{0}] as stream, skip this class file", e, name);
		}
		if (inputStream == null) {
			throw new SourceException("Resource [{0}] does not exist in class path", name);
		}
		
		// load data input stream to class file, if fail to load, just return null
		DataInputStream dataStream = new DataInputStream(inputStream);
		try {
			this.classFile = new ClassFile(dataStream);
		} catch (Exception e) {
			this.logger.error("Fail to load class descriptor by resource [{0}] file", e, name);
			throw new SourceException("Fail to load class descriptor by resource [{0}] file", e, name);
		} finally {
			
			try {
				dataStream.close();
			} catch (IOException e) {
				this.logger.error("Fail to close stream for resource [{0}], discard this error", e, name);
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				this.logger.error("Fail to close stream for resource [{0}], discard this error", e, name);
			}
		}
		
		// store class loader and resource name of class file
		this.classLoader = classLoader;
		this.className = name.replace('/', '.').substring(0, name.length() - 6);
	}
	
	/**
	 * @param classLoader the class loader to load class
	 * @param name the class file name in class path 
	 * @return the descriptor of class file in class path
	 * @throws SourceException if fail to load resource as descriptor
	 */
	public static ClassDescriptor forName(final ClassLoader classLoader, final String name) throws SourceException {
		return new ClassDescriptor(classLoader, name);
	}
	
	/**
	 * @return the attribute of annotations
	 */
	private AnnotationsAttribute getAnnotationsAttribute() {
		return (AnnotationsAttribute) this.classFile.getAttribute(AnnotationsAttribute.visibleTag);
	}

	/**
	 * @param types the expected annotations that is annotated in class
	 * @return whether class is annotated by this annotation
	 */
	public boolean isAnnotationsPresent(final Class<?>... types) {
		
		AnnotationsAttribute annotations = this.getAnnotationsAttribute();
		if (annotations != null) {
			for (Class<?> type : types) {
				if (annotations.getAnnotation(type.getName()) != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the class or null if fail to load
	 */
	public Class<?> loadClass() {
		
		try {
			return this.classLoader.loadClass(this.className);
		} catch (Exception e) {
			this.logger.error("Fail to load class with name [{0}] by class loader", e, className);
		}
		return null;
	}
	
	/**
	 * @param types all annotation types that class may be annotated
	 * @return the class if it is annotated by any annotations or null if fail to load
	 */
	public Class<?> loadAnnotatedClass(final Class<?>... types) {
		return this.isAnnotationsPresent(types) ? this.loadClass() : null;
	}
}
