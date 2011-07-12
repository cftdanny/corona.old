/**
 * 
 * @author $Author$
 * @version $Id$
 */

/**
 * the Corona JavaScript Framework
 */
var Corona = function() {
	
	/**
	 * the product name
	 */
	var product = "Corona";
	
	/**
	 * the framework version
	 */
	var version = "1.0";
	
	/**
	 * the properties and functions for Corona
	 */
	return {
		
		/**
		 * the product name
		 */
		getProduct : function () {
			return product;
		},
	
		/**
		 * the framework version
		 */
		getVersion : function () {
			return version;
		},
	};
}();

/**
 * add namespace support to Corona JavaScript Framework
 */
Corona.namespace = function () {
	
	var result = null;
	for (var i = 0; i < arguments.length; i = i + 1) {
		
		module = arguments[i].split(".");
		result = Corona;
		for (var j = ((module[0] == "Corona") ? 1 : 0); j < module.length; j = j + 1) {
			result[module[j]] = result[module[j]] || { };
			result = result[module[j]];
        }		
	}
	return result;
};

