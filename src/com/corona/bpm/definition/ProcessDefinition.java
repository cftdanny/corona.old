/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProcessDefinition {

	private StartNode startNode;
	
	private Map<String, Node> nodes = new HashMap<String, Node>();
}
