/*
 * Karina Fernandez
 */
/*****---------plugin para footer -----******/
(function($) {
	$.fn.fixposition = function() {
		if(this.is("footer")){
			var docHeight = $(window).height(); //dyn
			var footerHeight = this.height(); //60
			var footerTop = this.position().top + footerHeight;
			
			console.log("docHeight:",docHeight);
			console.log("footerHeight:",footerHeight);
			console.log("footerPosition:",this.position());
			console.log("footerTop:",footerTop);
			
			if (footerTop < docHeight) {
				console.log("Fixing footer position...");
				$('#footer').css('margin-top', (docHeight - footerTop) - 2 + 'px');
			}
		}else{
			console.warn("No footer was found when executing fixposition()");
		}
		return this;
	};
}(jQuery));