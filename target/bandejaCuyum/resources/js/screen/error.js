$(document).ready(function() {
	console.log("Ejecutando Javascript de pantalla de ERROR...");
	
	$("#menu").addClass("mainnav-menu");
	
	//barra de notificaciones
	$("#notification_bar").addClass("nav navbar-nav noticebar navbar-right");
	$("#icon_bell").addClass("fa fa-bell");
	$("#icon_envelope").addClass("fa fa-envelope");
	$("#icon_triangle").addClass("fa fa-exclamation-triangle");
	$("#notification_bar_notifications").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	$("#notification_bar_messages").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	$("#notification_bar_alerts").addClass("dropdown-menu noticebar-menu noticebar-hoverable");
	//avatar user
	$("#image_user").addClass("navbar-profile-avatar");
	$("#label_user").addClass("navbar-profile-label");
	$("#icon_user").addClass("fa fa-caret-down");	
	$("#header_user_icon").addClass("glyphicon glyphicon-user");

	$("#error_content_title").addClass("portlet-title");
	$("footer").fixposition();
});

console.log("Javascript de pantalla ERROR cargado.");
