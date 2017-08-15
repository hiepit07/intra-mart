/**
 * 
 */
jQuery(document).ready(function($) {
	
	loadDialogCom0102d04();
	
	//検索ボタンが表示されポップアップをクリック
	$("#btn_search_customer_id04").bind("click", function() {
		//getStoreFinder(customerCode, storeCode);
		var changeCodeWk = $("#changeCodeWk").val().trim();
		changeCodeWk = replaceText(changeCodeWk);
		var changeBranchWk = $("#changeBranchWk").val().trim();
		changeBranchWk = replaceText(changeBranchWk);
		var ourCompanyItemCodeWk = $("#ourCompanyItemCodeWk").val().trim();
		ourCompanyItemCodeWk = replaceText(ourCompanyItemCodeWk);
		var customerCodeWk = $("#customerCodeWk").val().trim();
		customerCodeWk = replaceText(customerCodeWk);
		var saleTypeWk = $("#saleTypeWk").val().trim();
		saleTypeWk = replaceText(saleTypeWk);
		
		var deadlineWk = $("#deadlineWk").val().trim();
		deadlineWk = replaceText(deadlineWk);
		var flightWk = $("#flightWk").val().trim();
		flightWk = replaceText(flightWk);
		showCom0102d04(changeCodeWk,changeBranchWk,ourCompanyItemCodeWk,
				customerCodeWk,saleTypeWk,deadlineWk,flightWk);
	});
});





		


// function getDataForTextbox
function getCom0102d04(ItemCodeWk,ItemNameWk) {		
	$("#hinMeiCode").val(ItemCodeWk);
	$("#hinMeiName").val(ItemNameWk);
	reTurnCheckTab();
}
	
	