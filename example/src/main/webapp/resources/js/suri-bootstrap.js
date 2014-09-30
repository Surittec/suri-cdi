$(function() {
	
	/*
	 * CALENDAR
	 */
	
	$('.text-danger').parents('.form-group').addClass('has-error');
	
	
	/*
	 * DATA TABLE
	 */
	
	$('.ui-icon.ui-icon-seek-first').addClass('glyphicon glyphicon-fast-backward').empty();
	$('.ui-icon.ui-icon-seek-prev').addClass('glyphicon glyphicon-step-backward').empty();
	$('.ui-icon.ui-icon-seek-next').addClass('glyphicon glyphicon-step-forward').empty();
	$('.ui-icon.ui-icon-seek-end').addClass('glyphicon glyphicon-fast-forward').empty();
	$('.ui-icon.ui-icon-carat-2-n-s').addClass('glyphicon glyphicon-sort');
	$('.ui-datatable-tablewrapper').addClass('table-responsive');
	$('.ui-column-filter.ui-inputfield').addClass('form-control input-sm pull-left').css("width", "90%");
	
});