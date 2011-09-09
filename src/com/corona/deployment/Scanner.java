/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This scanner is used to scan java resources in class path </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Scanner {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(Scanner.class);
	
	/**
	 * The class loader
	 */
	private ClassLoader classLoader;

	/**
	 * all handlers for this scanner
	 */
	private List<Inspector> handlers = new ArrayList<Inspector>();
	
	/**
	 * The REGEX pattern to scan jar file
	 */
	private Pattern libraryPattern = null;
	
	/**
	 * The REGEX pattern to scan resource
	 */
	private Pattern resourcePattern = null;
	
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

	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	
	/**
	 * @param pattern the REGEX pattern for resource name
	 */
	public void setResourcePattern(final String pattern) {
		this.resourcePattern = (pattern == null) ? null : Pattern.compile(pattern);
	}
	
	/**
	 * @param pattern the REGEX pattern for jar library
	 */
	public void setLibraryPattern(final String pattern) {
		this.libraryPattern = (pattern == null) ? null : Pattern.compile(pattern);
	}
	
	/**
	 * @return All loaded jar resources from class loader
	 */
	private URL[] getURLsFromClassLoader() {
		return ((URLClassLoader) this.classLoader).getURLs();
	}
	
	/**
	 * @param root The root directory to scan resources
	 */
	private void scanDirectory(final File root, final String current) {

		this.logger.debug("Scan resources under directory [{0}]", file.getAbsolutePath());
		for (File child : root.listFiles()) {
			
			String newPath = (path == null ? child.getName() : path + '/' + child.getName());
			if (!child.isDirectory()) {
				if ((this.scanResourcePattern == null) || (this.scanResourcePattern.matcher(newPath).matches())) {
					if (this.logger.isDebugEnabled()) {
						this.logger.debug(Constants.LOG_ASN_SCNA_DIR_FOUND, newPath, file);
					}
					
					this.handle(file.getAbsolutePath(), newPath);
				}
			} else {
				handleDirectory(child, newPath);
			}
		}
	}
	
	/**
	 * @param file The jar file
	 * @throws IOException Fail to open or operate jar file
	 */
	private void scanLibrary(File file) throws IOException {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug(Constants.LOG_ASN_SCAN_JAR, file.getAbsolutePath());
		}
		
		Enumeration< ? extends ZipEntry> entries = new ZipFile(file).entries();

		while (entries.hasMoreElements()) {
			String name = entries.nextElement().getName();
			
			if ((this.scanResourcePattern == null) || (this.scanResourcePattern.matcher(name).matches())) {
				if (this.logger.isDebugEnabled()) {
					this.logger.debug(Constants.LOG_ASN_SCNA_JAR_FOUND, name, file);
				}
				
				this.handle(file.getAbsolutePath(), name);
			}
		}
	}
	
	/**
	 * <p>Scan all resources in class path and pass them to registered handlers. </p>
	 * 
	 * @throws ScanningException if fail to scan resources in class path
	 */
	public void scan() throws ScanningException {
		
		Set<String> resources = new HashSet<String>();
		for (URL url : getURLsFromClassLoader()) {
			
			String resource = url.getFile();
			if (resource.endsWith("/")) {
				resource = resource.substring(0, resource.length() - 1);
			}
			this.logger.info("Load resource [{0}]", resource);
			resources.add(resource);
		}
		
	}
}
