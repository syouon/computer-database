jQuery(document).ready(function() {
	jQuery("#editForm").validate({
		rules : {
			name : {
				required : true
			},
			introduced : {
				dateISO : true
			},
			discontinued : {
				dateISO : true
			}
		},
		messages : {
			introduced : "Should be a date in ISO format (YYYY-MM-DD)",
			discontinued : "Should be a date in ISO format (YYYY-MM-DD)"
		}
	});
});