/**
 * 
 */
$(document).ready(function(){
	$("#btn_search_customer_id05").on("click", function(){
		//getStoreFinder(customerCode, storeCode);
		
		var userCodeWk = $("#userCodeWk").val().trim();
		userCodeWk = replaceText(userCodeWk);
		var userRodeWk = $("#userRodeWk").val().trim();
		userRodeWk = replaceText(userRodeWk);
		var myOfficeWk = $("#myOfficeWk").val().trim();
		myOfficeWk = replaceText(myOfficeWk);
		getStoreFinderCom0102d05(userCodeWk,userRodeWk,myOfficeWk);
	});
});
function getCom0102d05(data) {
	
	var showDataCnt = data.length;
	$("#dialogStoreFinder").fadeOut(100);
	$("#overlay").fadeOut(100);
	for (var i = 0; i < showDataCnt; i++) {
	$("#userCodeWk").val(data[i].userCode);
	$("#userNm").html(data[i].userNm);
	}
	
	$("#txtEigyouTantoushaCode").focus().select();
	reTurnCheckTab();
	
}
function checkTabIndex() {
	$("#wrapper").find("input").attr("tabindex",-1);
	$("#wrapper").find("button").attr("tabindex",-1);
	$("#wrapper").find("select").attr("tabindex",-1);
	$("#formCom0102d05").find("input").attr("tabindex",0);
}
function reTurnCheckTab() {
	$("#wrapper").find("input").attr("tabindex",0);
	$("#wrapper").find("button").attr("tabindex",0);
	$("#wrapper").find("select").attr("tabindex",0);
}