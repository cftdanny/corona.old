/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.expression;

import java.util.Map;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * <p>This FreeMaker template model is used to directly export properties, method to template. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DataModel extends BeanModel {

	/**
	 * the map context
	 */
	private Map<String, ?> context;
	
	/**
	 * @param root the root object
	 * @param context the map context
	 */
	DataModel(final Object root, final Map<String, ?> context) {
		super(root, new BeansWrapper());
		this.context = context;
	}
	
	/**
	 * {@inheritDoc}
	 * @see freemarker.ext.beans.BeanModel#get(java.lang.String)
	 */
	@Override
	public TemplateModel get(final String name) throws TemplateModelException {
		
		if (this.getWrappedObject() != null) {
			
			TemplateModel model = super.get(name);
			if (model != null) {
				return model;
			}
		}

		return this.wrap(this.context.get(name));
	}
}
