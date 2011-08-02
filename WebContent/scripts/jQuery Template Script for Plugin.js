/**
 * JavaScript template for HTML web page
 * 
 * @author $Author$
 * @version $Id$
 */

/**
 * the description about plugin
 */
(function($) {
	
	// define jQuery plugin
	$.fn.plugin = function(options) {
		
		// merge default options with argument
		var settings = $.extend({}, $.fn.plugin.defaults, options);
		return this.each(function() {
			
		});
	};

	// set default settings for plugin
	$.fn.plugin.defaults = {
			
	};
})(jQuery);