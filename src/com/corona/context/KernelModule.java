/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>This module is used to install module automatically by {@link ServiceLoader}. It can be
 * used as plug-in pattern to extend application by a jar. </p>
 * 
 * <p>The kernel module is used by framework to install first priority modules. If application wants
 * load modules automatically, please use {@link StartModule} instead. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class KernelModule extends AbstractModule {

}
