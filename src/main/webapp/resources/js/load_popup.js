//****************************************************
// Load popup
//****************************************************

$(function(){
	
	//Handle click on button Organization
	$("#loadSupplierSearchButton").click(function(e){
		$("#dialog").load("suppliersSearch.html #supplier",function() {
			ShowDialog(700,500);
			$("#dialog").draggable({
				containment: 'body',
				handle: '.web_dialog_title'
			});
			e.preventDefault();
		});
	});
	//Handle click on button Close Organization
	$(document).on('click', '#closeSupplierSearch', function(e) {
		$("#dialog").empty();
		HideDialog();
		e.preventDefault();
	});
	
	//Handle click on button Organization
	$("#loadpartNumberSearchButton").click(function(e){
		$("#dialog").load("partNumberSearch.html #partNumber",function() {
			ShowDialog(700,500);
			$("#dialog").draggable({
				containment: 'body',
				handle: '.web_dialog_title'
			});
			e.preventDefault();
		});
	});
	//Handle click on button Close Organization
	$(document).on('click', '#closepartNumberSearch', function(e) {
		$("#dialog").empty();
		HideDialog();
		e.preventDefault();
	});
	
});
function ShowDialog(box_width, box_height)
{
	$("#overlay").show();
	$("#dialog").fadeIn(300);
	
	//Set width, height for dialog
	document.getElementById('dialog').style.width = box_width+"px";
	document.getElementById('dialog').style.height= box_height+"px";
	
	//Get the window height and width
	var winH = $(window).height(); //$("#wrapper").height();
	var winW = $(window).width();
	
	//Set position center for dialog
	var h = (winH-$("#dialog").height())/2;
	var w = (winW-$("#dialog").width())/2;
	$("#dialog").css('top', h);
	$("#dialog").css('left', w);
	
	$("#overlay").unbind("click");            
}
function HideDialog()
{
	$("#overlay").hide();
	$("#dialog").fadeOut(300);
}
