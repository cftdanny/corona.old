/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This scanner is used to scan java resources in class path. Most often, it can be used to find
 * all classes that are annotated by some specified annotation when application starts. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Scanner {

	/**
	 * the file indicator to scan resource if file exits  
	 */
	private static final String DEPLOYMENT_FLAG = "META-INF/corona/INSTALL";
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(Scanner.class);
	
	/**
	 * The class loader
	 */
	private ClassLoader classLoader;

	/**
	 * all inspectors for this scanner
	 */
	private List<Inspector> inspectors = new ArrayList<Inspector>();
	
	/**
	 * The package pattern to scan resource in it
	 */
	private Pattern pattern = null;
	
	/**
	 * create default scanner
	 */
	public Scanner() {
		this(Scanner.class.getClassLoader());
	}

	/**
	 * @param classLoader the class loader
	 */
	public Scanner(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * @return the class loader
	 */
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	
	/**
	 * @param pattern the package pattern to scan resource name in it
	 */
	public void setPattern(final String pattern) {
		this.pattern = (pattern == null) ? null : Pattern.compile(pattern);
	}
	
	/**
	 * @return All loaded jar resources from class loader
	 */
	private URL[] getURLsFromClassLoader() {
		return ((URLClassLoader) this.classLoader).getURLs();
	}
	
	/**
	 * @param inspector the inspector to add
	 */
	public void add(final Inspector inspector) {
		this.inspectors.add(inspector);
	}
	
	/**
	 * @param inspector the inspector to remove
	 */
	public void remove(final Inspector inspector) {
		this.inspectors.remove(inspector);
	}

	/**
	 * @param name the resource to be inspected
	 */
	private void inspect(final String name) {
		
		for (Inspector inspector : this.inspectors) {
			inspector.inspect(name);
		}
	}
	
	/**
	 * @param root The root directory to scan resources
	 * @param current the current path
	 */
	private void scanDirectory(final File root, final String current) {

		boolean scanning = (this.pattern == null) || this.pattern.matcher(current).matches();
		this.logger.debug("Start to scan resources from class directory [{0}]", root);
		for (File child : root.listFiles()) {
			
			if (child.isDirectory()) {
				this.scanDirectory(child, current + (current.length() == 0 ? "" : "/") + child.getName());
			} else if (scanning) {
				this.inspect(current + "/" + child.getName());
			}
		}
	}
	
	/**
	 * @param file The jar file
	 * @throws IOException Fail to open or operate jar file
	 */
	private void scanLibrary(final File file) throws IOException {

		// test whether library is defined for scanning. if yes, scan it
		ZipFile library = new ZipFile(file);
		if (library.getEntry(DEPLOYMENT_FLAG) != null) {

			Enumeration<? extends ZipEntry> entries = library.entries();
			this.logger.debug("Start to scan resources from class library [{0}]", file);
			while (entries.hasMoreElements()) {
				
				ZipEntry entry = entries.nextElement();
				if (!entry.isDirectory()) {
					this.inspect(entry.getName());
				}
			}
		}
	}
	
	/**
	 * <p>Scan all resources in class path and pass them to registered handlers. </p>
	 * 
	 * @throws ScanningException if fail to scan resources in class path
	 */
	public void scan() throws ScanningException {
		
		for (URL url : getURLsFromClassLoader()) {

			File root = new File(url.getFile());
			try {
				if (root.isDirectory()) {
					this.scanDirectory(root, "");
				} else {
					this.scanLibrary(root);
				}
			} catch (Exception e) {
				this.logger.error("Fail to scan resources from URL [{0}], just skip it", e, url);
			}
		}
	}
}
