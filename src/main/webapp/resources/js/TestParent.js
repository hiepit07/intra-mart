jQuery(document).ready(
		function($) {
			$(document).on(
					"click",
					"#btn_search_customer_id",
					function(e) {

						//得意先コードを退避する
						$("#hdCustomerCodeIni").val($("#txtCustomerCode").val());

						// 検索子画面呼び出し用引数セット
						var jigyoshoCode = ""; // [変数]事業所コードWK
						var custCode = $("#customer_id").val(); // [画面]得意先コード
						var shopKb = ""; // Null
						var searchKb = "1"; // '1'（得意先）

						// 検索子画面呼び出し用関数を呼び出す
						showCom0102d02(jigyoshoCode, custCode, shopKb, searchKb);

					});

			$(document).on("click", "#btn_search_store_code", function(e) {

				var test1 = 5;
				var test2 = 221195735;
				var test3 = 1;
				var test4 = 1;
				var test5 = 3;
				$.ajax({
					type : "POST",
					url : "testJava",
					async : false,
					data : {
						test1 : test1,
						test2 : test2,
						test3 : test3,
						test4 : test4,
						test5 : test5
					},
					success : function(returnData) {

					},
					error : function(e) {
						alert(e.message);
					},
					complete : function(jqXHR, textStatus) {
					}
				});

			});

		});


// 得意先子画面データ受け取り用関数（仮）
function getCom0102d02(data) {

	// 親画面側処理（仮）
	$("#customer_id").val(data[0].custCode);
	$("#txt_customer_id").text(data[0].custNmR);

}
