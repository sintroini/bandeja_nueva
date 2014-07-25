(function($) {
	$.fn.upload = function() {
		
		
//		$(function () {
		    'use strict';
		    // Change this to the location of your server-side upload handler:
		    var url = ctx+"/rest/file/upload";
		    console.log("ejecutando function de upload, luego presionar boton");
		    $('#fileupload').fileupload({
		    	type: "POST",
		    	url: url,
		        dataType: 'multipart/form-data',
//		        acceptFileTypes: /(\.|\/)(bpmn2)$/i,
//		        maxFileSize: 5000000,//5 mb
		        //no está entrando aca ???¿¿¿¿
		        add: function (e, data) {
		            data.context = $('<button/>').text('Upload')
		                .appendTo(document.body)
		                .click(function () {
		                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
		                    data.submit();
		                });
		        },
		        done: function (e, data) {
		            $.each(data.result.files, function (index, file) {
		                $('<p/>').text(file.name).appendTo('#files');
		            });
		        },
		        progressall: function (e, data) {
		            var progress = parseInt(data.loaded / data.total * 100, 10);
		            $('#progress .progress-bar').css(
		                'width',
		                progress + '%'
		            );
		        }
		    })
		    .prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
		    
	};
});

		