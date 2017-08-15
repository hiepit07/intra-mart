/**
 * ファイル名:com0103d01.js
 * 概要:ジョブ実行状況照会画面
 * 
 * 作成者:ABV）TramChu
 * 作成日:2015/10/06
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/10/06 1.00                  ABV）TramChu        新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

jQuery(document).ready(function($) {
	
	//Dialogのadd_clickイベントの作成
	loadDialogCom0102d05();
	
	var strSysAdminFlag = $('#sysAdminFlag').val();
	var isClickButton = false;
	var txtDiaUserCodeExec = "";
	// フォーカスセット
	setFocus(strSysAdminFlag);
	// 事業所が変更された場合の処理
	$("#ddlDiaJigyo").change(function(){
		var officeCode = $("#ddlDiaJigyo").find('option:selected').val();
		$("#officeCodeWk").val(officeCode);
		$("#txtDiaUserCodeExec").val("");
		$("#lblDiaUserNmExec").html("");
		$("#btn_search_customer_id05").removeAttr('disabled');
		$("#btn_search_customer_id05").removeClass("cursor_default").addClass("cursor_pointer");
		$('#txtDiaDateFromExec').focus();
	});

	$(document).on('focus', '#txtDiaUserCodeExec', function(){		
		txtDiaUserCodeExec = $("#txtDiaUserCodeExec").val();
	});
	
	$(document).on('focusout', '#txtDiaUserCodeExec', function(){
		if (txtDiaUserCodeExec != $("#txtDiaUserCodeExec").val()) {
			$("#lblDiaUserNmExec").html("");
		}				
	});
	// 日付編集ルール
	$(document).on('focus', '#txtDiaDateFromExec', function(){
		var txtDiaDateFromExec = $('#txtDiaDateFromExec').val().replace(/\//g, '');
		$("#txtDiaDateFromExec").val(txtDiaDateFromExec);
		if ($("#txtDiaDateFromExec").hasClass("form-error-field")) {
			$("#txtDiaDateFromExec").select();
		}
	});
	
	$(document).on('focusout', '#txtDiaDateFromExec', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var txtDiaDateFromExec = $('#txtDiaDateFromExec').val().replace(/\//g, '');
				var error = chkItem(txtDiaDateFromExec, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "処理日付（From）"));		
					$("#txtDiaDateFromExec").addClass("form-error-field");
				} else {
					$("#txtMess").html("");
					$("#txtDiaDateFromExec").removeClass("form-error-field");
				}
				if (txtDiaDateFromExec != "") {					
					var yearStart = txtDiaDateFromExec.substring(0,4);
					var monthStart = txtDiaDateFromExec.substring(4,6);
					var dayStart = txtDiaDateFromExec.substring(6,8);
					var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
					$("#txtDiaDateFromExec").val(formatDateStart);
				}		
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focus', '#txtDiaDateToExec', function(){
		var txtDiaDateToExec = $('#txtDiaDateToExec').val().replace(/\//g, '');
		$("#txtDiaDateToExec").val(txtDiaDateToExec);
		if ($("#txtDiaDateToExec").hasClass("form-error-field")) {
			$("#txtDiaDateToExec").select();
		}
	});
	
	$(document).on('focusout', '#txtDiaDateToExec', function(){
		setTimeout(function(){
			if (!isClickButton) {
				var txtDiaDateToExec = $('#txtDiaDateToExec').val().replace(/\//g, '');
				var error = chkItem(txtDiaDateToExec, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMD);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "処理日付（To）"));		
					$("#txtDiaDateToExec").addClass("form-error-field");
				}
				else {
					$("#txtMess").html("");
					$("#txtDiaDateToExec").removeClass("form-error-field");
				}
				if (txtDiaDateToExec != "") {					
					var yearStart = txtDiaDateToExec.substring(0,4);
					var monthStart = txtDiaDateToExec.substring(4,6);
					var dayStart = txtDiaDateToExec.substring(6,8);
					var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;
					$("#txtDiaDateToExec").val(formatDateStart);
				}		
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtDiaTimeFromExec', function(){
		setTimeout(function(){
			if (!isClickButton) {				
				var txtDiaTimeFromExec = $('#txtDiaTimeFromExec').val().replace(/\//g, '');
				var error = chkEmpty(txtDiaTimeFromExec);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "処理日付（From）"));		
					$("#txtDiaTimeFromExec").addClass("form-error-field");
					return;
				} else {
					$("#txtMess").html("");		
					$("#txtDiaTimeFromExec").removeClass("form-error-field");
				}				
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	$(document).on('focusout', '#txtDiaTimeToExec', function(){
		setTimeout(function(){
			if (!isClickButton) {				
				var txtDiaTimeToExec = $('#txtDiaTimeToExec').val().replace(/\//g, '');
				var error = chkEmpty(txtDiaTimeToExec);
				if (error != null) {
					$("#txtMess").html($("#" + error).val().replace("{1}", "処理日付（To）"));		
					$("#txtDiaTimeToExec").addClass("form-error-field");
					return;
				} else {
					$("#txtMess").html("");		
					$("#txtDiaTimeToExec").removeClass("form-error-field");
				}				
			} else {
				isClickButton = false;
			}
		}, FOCUSOUT_TIMEOUT);
	});
	
	 //  カレンダーを表示する
	$("#txtDiaDateFromExec,#txtDiaDateToExec").datepicker({
		showOn: "button",
	    buttonImage: rootPath + "/resources/css/images/calendar.gif",
	    buttonImageOnly: true
	});
	
	// Enter key
	$(document).on("keydown", "input[type=text]", function(e) {
		if (e.keyCode == 13) {
	        e.preventDefault();
			EnterKey(e, this, ".data_conditions_area_cont");
		}
	});
	
	$("#btnReproces").bind("click",function() {
		isClickButton = true;
		disableButton("btnReproces");
		setTimeout(function(){
			var jsonData = getReprocessJobLst();
			$("#txtMess").html("");
			if (jsonData.length <= 0) {
				$("#txtMess").html($("#COM024-E").val());
				enableButton("btnReproces");
				isClickButton = false;
				return;
			}			
			jQuestion_confirm($("#COM001-I").val(), $("#COM001-I").attr("title"), function(retVal) {
				if (retVal) {					
					disableControl();
					$.ajax({
						type : "POST",
						url : "reprocessJob",
						contentType: 'application/json; charset=utf-8',
						data : JSON.stringify(jsonData),
						async : false,
						success : function(returnData) {
							if (returnData != "") {
								if (returnData[0].type == "msgWaring") {
									$("#txtMess").html(returnData[0].messageEr);
									$("#haitaDate").val(returnData[1].startDate);
									$("#haitaTime").val(returnData[1].startTime);
								}
							}
						},
						error : function(e) {
							// TODO エラーメッセージ
							$("#txtMess").html("例外エラー");
						},
						complete : function(jqXHR, textStatus) {
							enableControl();
							enableButton("btnReproces");
						}
					});
				} else {
					enableButton("btnReproces");
				}
			});
			isClickButton = false;
		}, FOCUSOUT_TIMEOUT);
	});
	
	// データ呼出条件の初期化
	$("#btnConditionClear").bind("click", function() {
		isClickButton = true;
		$("#txtMess").html("");
		$('input').removeClass('form-error-field');		
		$("#ddlDiaJigyo").removeClass("form-error-field");
		// 画面項目に初期値を以下の初期値をセットする
		$("#ddlDiaJigyo").val($('#loginJigyouShoCode').val());
		// 処理日付From
		$('#txtDiaDateFromExec').val($('#systemDate').val());
	    // 処理時間From
	    $('#txtDiaTimeFromExec').val("0000");
	    // 処理日付To
	    $('#txtDiaDateToExec').val($('#systemDate').val());
	    // 処理時間To
	    $('#txtDiaTimeToExec').val("2359");
	    // 処理担当者
	    $('#txtDiaUserCodeExec').val($('#loginUserID').val());
	    $('#officeCodeWk').val($('#loginJigyouShoCode').val());
	    // 処理種別
	    $('#ddlDiaTypExec').val("0");
	    // 処理種別
	    $('#ddlDiaJobExec').val("");
	    // 担当者検索ボタン 
	    $('#btn_search_customer_id05').removeClass("cursor_default").addClass("cursor_pointer");
		$('#btn_search_customer_id05').attr("src",$('#btn_search_customer_id05').attr("src").replace("search_dis.png", "search.png"));
	    // 処理担当者名称
	    $('#lblDiaUserNmExec').html("");
	    // 「配信ジョブを対象とする」チェックボックス
	    $('#chkDiaRsJob').prop("checked",true);
		// 「集信ジョブを対象とする」チェックボックス
	    $('#chkDiaSsJob').prop("checked",true);
	    // 「異常ジョブのみ表示」チェックボックス
	    $('#chkDiaIjJob').prop("checked",false);
	    enableButton("btnSearch");
	    enableButton("btnConditionClear");
		// フォーカスセット
		$('#txtDiaDateFromExec').focus();
		isClickButton = false;
	});
	
	// 担当者検索画面を表示する
	$("#btn_search_customer_id05").on("click",function() {
		var userCode = $("#txtDiaUserCodeExec").val().trim();
		var userRole = "";
		var myOffice = $("#officeCodeWk").val().trim();
		showCom0102d05(userCode,userRole,myOffice);
	});

	// ジョブを一覧表示
	$("#btnSearch").bind("click", function() {
		event.stopImmediatePropagation();
		isClickButton = true;
		disableButton("btnSearch");
		setTimeout(function(){
			$("#txtMess").html("");
			$("#tbl_tbody").html("");
			$("#txtDiaDateFromExec").removeClass("form-error-field");
			$("#txtDiaTimeFromExec").removeClass("form-error-field");
			$("#txtDiaDateToExec").removeClass("form-error-field");
			$("#txtDiaTimeToExec").removeClass("form-error-field");
			$("#ddlDiaJigyo").removeClass("form-error-field");
			var processStartDateWk = $('#txtDiaDateFromExec').val().replace(/\//g, '');
			var processStartTimeWk = $('#txtDiaTimeFromExec').val() + "00";
			var processEndDateWk = $('#txtDiaDateToExec').val().replace(/\//g, '');
			var processEndTimeWk = $('#txtDiaTimeToExec').val() + "59";
			var error = "0" ;
			// 以下の項目の単項目チェックを実施する
			var officeCodeWk = $("#officeCodeWk").val();
			var errorOffice =chkEmpty(officeCodeWk);
			var txtMsg = "";
			if (errorOffice != null) {	
				txtMsg = $("#" + errorOffice).val().replace("{1}", "事業所");
				$("#ddlDiaJigyo").addClass("form-error-field");
				error = "1";
			} else {
				$("#txtMess").html("");		
				$("#officeCodeWk").removeClass("form-error-field");
			}
			var errorStart = chkItem(processStartDateWk + processStartTimeWk, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMDHMS);
			if (errorStart != null) {
				if (txtMsg != ""){
					txtMsg = txtMsg +"<br>" + $("#" + errorStart).val().replace("{1}", "処理日付（From）");
				} else {
					txtMsg = $("#" + errorStart).val().replace("{1}", "処理日付（From）");
				}
				$("#txtDiaDateFromExec").addClass("form-error-field");
				$("#txtDiaTimeFromExec").addClass("form-error-field");
				error = "2";
			} else {
				$("#txtMess").html("");		
				$("#txtDiaDateFromExec").removeClass("form-error-field");
				$("#txtDiaTimeFromExec").removeClass("form-error-field");
			}
			var errorStartEnd = chkItem(processEndDateWk + processEndTimeWk, true, TYP_DATE_FORMAT, false, DATE_FORMAT_YMDHMS);
			if (errorStartEnd != null) {
				if (txtMsg != ""){
					txtMsg = txtMsg +"<br>" + $("#" + errorStartEnd).val().replace("{1}", "処理日付（To）");
				} else {
					txtMsg = $("#" + errorStartEnd).val().replace("{1}", "処理日付（To）");
				}
				$("#txtDiaDateToExec").addClass("form-error-field");
				$("#txtDiaTimeToExec").addClass("form-error-field");
				error = "3";
			} else {
				$("#txtMess").html("");
				$("#txtDiaDateToExec").removeClass("form-error-field");
				$("#txtDiaTimeToExec").removeClass("form-error-field");
			}
			if (error != "0") {
				$("#txtMess").html(txtMsg);
				var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
				$("#"+idError).select();
				$("#"+idError).focus();
				enableButton("btnSearch");
				isClickButton = false;
				return;
			}
			// 処理日時生成
			var processStartDateTimeWk = processStartDateWk + processStartTimeWk;
			var processEndDateTimeWk = processEndDateWk + processEndTimeWk;
			var messageCode = chkFromTo(processStartDateTimeWk,processEndDateTimeWk,DATE_FORMAT_YMDHMS);		
			if (messageCode == null) {
				// 選択チェック
				var sendFlag = $("#chkDiaRsJob").is(':checked');
				var concentrateFlag = $("#chkDiaSsJob").is(':checked');
				if ((!sendFlag) && (!concentrateFlag)) {
					$("#txtMess").html($("#COM033-E").val());
					$("#chkDiaRsJob").focus();
					enableButton("btnSearch");
					isClickButton = false;
					return;
				}
				$.ajax({
					type : "POST",
					url : "getSearchResult",
					async : false,
					data : $("form").serialize(),
					contentType : "application/x-www-form-urlencoded; charset=UTF-8",
					success : function(returnData) {
						var data = JSON.parse(returnData);
						if (data[0].messageEr != null) {
							if (data[0].type == "body") {
								$("#txtMess").html("");
								$("#tbl_tbody").html(data[0].messageEr);
								$("#btnReproces").attr("disabled",true);
								$("#btnReproces").removeClass("btn_button").addClass("btn_button_disabled");
							}
							for (var i = 0; i < data.length; i++) {
								if (data[i].type == "userNameWk") {
									$("#lblDiaUserNmExec").html(data[i].userNmExec);	
								}	
							}
						} else {
							$("#txtMess").html("");
							showtable(data);							
							$("#btnReproces").removeAttr('disabled');
							$("#btnReproces").removeClass("btn_button_disabled").addClass("btn_button");
						}
					},
					error : function(e) {
						// TODO エラーメッセージ
						$("#txtMess").html("例外エラー");
						
					},
					complete : function(jqXHR, textStatus) {
						enableButton("btnSearch");
					}
				});
			} else {
				enableButton("btnSearch");
				$("#txtMess").html($("#" + messageCode).val().replace("{1}", "処理日時To").replace("{2}", "処理日時From"));		
				$("#txtDiaDateFromExec").addClass("form-error-field");
				$("#txtDiaTimeFromExec").addClass("form-error-field");
				$("#txtDiaDateToExec").addClass("form-error-field");
				$("#txtDiaTimeToExec").addClass("form-error-field");
			}
			var idError =  $(".form-error-field:enabled:not([readonly]):first").attr("id");
			$("#"+idError).select();
			$("#"+idError).focus();
			isClickButton = false;
		}, FOCUSOUT_TIMEOUT);
	});

	/**
	 * 項目非活性化
	 */
	function disableControl() {
		$('#txtDiaDateFromExec').prop("disabled",true);
		$('.ui-datepicker-trigger').prop("disabled",true);
		$('#txtDiaTimeFromExec').prop("disabled",true);
		$('#txtDiaDateToExec').prop("disabled",true);
		$('#txtDiaTimeToExec').prop("disabled",true);
		$('#txtDiaUserCodeExec').prop("disabled",true);
		$('#btn_search_customer_id05').removeClass("cursor_pointer").addClass("cursor_default");
		$('#btn_search_customer_id05').attr("src",$('#btn_search_customer_id05').attr("src").replace("search.png", "search_dis.png"));
		$('#ddlDiaTypExec').prop("disabled",true);
		$('#ddlDiaJobExec').prop("disabled",true);
		$('#ddlDiaJigyo').prop("disabled",true);
		$('#chkDiaRsJob').prop("disabled",true);
		$('#chkDiaSsJob').prop("disabled",true);
		$('#chkDiaIjJob').prop("disabled",true);
		disableButton("btnSearch");
		disableButton("btnConditionClear");
	}
	
	/**
	 * enableControl
	 */
	function enableControl() {
		$('#txtDiaDateFromExec').prop("disabled",false);
		$('.ui-datepicker-trigger').prop("disabled",false);
		$('#txtDiaTimeFromExec').prop("disabled",false);
		$('#txtDiaDateToExec').prop("disabled",false);
		$('#txtDiaTimeToExec').prop("disabled",false);
		$('#txtDiaUserCodeExec').prop("disabled",false);
		$('#btn_search_customer_id05').removeClass("cursor_default").addClass("cursor_pointer");
		$('#btn_search_customer_id05').attr("src",$('#btn_search_customer_id05').attr("src").replace("search_dis.png", "search.png"));
		$('#ddlDiaTypExec').prop("disabled",false);
		$('#ddlDiaJobExec').prop("disabled",false);
		$('#ddlDiaJigyo').prop("disabled",false);
		$('#chkDiaRsJob').prop("disabled",false);
		$('#chkDiaSsJob').prop("disabled",false);
		$('#chkDiaIjJob').prop("disabled",false);
		enableButton("btnSearch");
		enableButton("btnConditionClear");		
	}
	
	/**
	 * 
	 */
	function getReprocessJobLst() {
		var reprocessJobLst = [];
		var reprocessJob;
		var startDate = $("#haitaDate").val();
		var startTime = $("#haitaTime").val();
		var type = $("#loginUserID").val();
		var id = $("input.tblSearch[type^=checkbox]:not([disabled]):first").attr("id");
		$('input.tblSearch[type^=checkbox]:not([disabled])').each(function() {
			if(this.checked){
				var fileName = $(this).parent().parent().find("td").eq(13).text();
				var parameter = $(this).parent().parent().find("td").eq(14).text();
				var jobNo = $(this).parent().parent().find("td").eq(15).text();
				var jobNm = $(this).parent().parent().find("td").eq(4).text();
				var tempObj = {
					fileNmExec : fileName,
					parameters : parameter,
					jobNo : jobNo,
					startDate : startDate,
					startTime : startTime,
					type : type,
					jobNm : jobNm
				};
				reprocessJobLst.push(tempObj);
			}
        });
		return reprocessJobLst;
	}
	
	/**
	 * テーブルの表示
	 * 
	 * @param data
	 *            テーブルのデータ
	 * @return
	 * @exception
	 */
	function showtable(data) {
		var htmlData = [];
		var searchMax = parseInt($("#searchMax").val()) + 1 ;
		// テーブルの表示
		for (var i = 0; i < data.length; i++) {
			if (data[i].jobNo != "" && data[i].jobNo != null) {
				htmlData.push("<tr>");
				if ((i + 1) < searchMax) {
					htmlData.push(drawRow(i + 1, data[i]));
				}
				htmlData.push("</tr>");
			} else if (data[i].type == "searchMax") {
				$("#txtMess").html(data[i].messageEr);
			} else if (data[i].type == "haita") {
				$("#haitaDate").val(data[i].startDate);
				$("#haitaTime").val(data[i].startTime);				
			}  else if (data[i].type == "userNameWk") {
				$("#lblDiaUserNmExec").html(data[i].userNmExec);	
			}		
		}
		document.getElementById('tbl_tbody').innerHTML = htmlData.join('');
		// table scroll
		var tblBodyHeight = $("#tblBody").height();
		var divBodyHeight = $("#divBody").height();
		if (tblBodyHeight > divBodyHeight) {
			$("#divBody").css("overflow-y", "scroll");
			$("#divHead").width($("#divBody").width() - 17);
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width() - 17);
			});
		} else {
			$("#divBody").css("overflow-y", "hidden");
			$("#divHead").width($("#divBody").width());
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width());
			});
		}
		// フォーカスセット
		var id = $("input.tblSearch[type^=checkbox]:not([disabled]):first").attr("id");
		$("#" + id).focus();
	}

	/**
	 * テーブルを描く
	 * 
	 * @param renban
	 *            連番
	 * @param 行のデータ
	 * @return
	 * @exception
	 */
	function drawRow(renban, dataRow) {
		var htmlRow = [];
		if (dataRow.reFlg == WHETHER || dataRow.reFlg == RE_PROCESSED) {
			htmlRow.push("<td class='align_center'>" + "<input class='tblSearch' id='"+ renban +"' type='checkbox' disabled>"+ "</td>");
		} else if (dataRow.reFlg == YES){
			htmlRow.push("<td class='align_center'>" + " <input class='tblSearch' id='"+ renban +"' type='checkbox'>"+ "</td>");
		}
		htmlRow.push("<td class='align_center'>" + renban + "</td>");
		htmlRow.push("<td class='align_center'>" + dataRow.jobTyp+ "</td>");
		htmlRow.push("<td class='align_left'>" + dataRow.jobId + "</td>");
		htmlRow.push("<td class='align_left'>" + dataRow.jobNm + "</td>");
		htmlRow.push("<td class='align_left'>" + dataRow.userCodeExec + "</td>");
		htmlRow.push("<td class='align_left'>" + dataRow.userNmExec + "</td>");
		var yearStart = dataRow.startDate.substring(0,4);
		var monthStart = dataRow.startDate.substring(4,6);
		var dayStart = dataRow.startDate.substring(6,8);
		var formatDateStart = yearStart + "/" + monthStart + "/" + dayStart;		
		htmlRow.push("<td class='align_left'>" + formatDateStart + "</td>");
		var hStart = dataRow.startTime.substring(0,2);
		var mStart = dataRow.startTime.substring(2,4);
		var sStart = dataRow.startTime.substring(4,6);
		var formatTimeStart = hStart + ":" + mStart + ":" + sStart;
		htmlRow.push("<td class='align_center'>" + formatTimeStart + "</td>");
		var yearEnd = dataRow.endDate.substring(0,4);
		var monthEnd = dataRow.endDate.substring(4,6);
		var dayEnd = dataRow.endDate.substring(6,8);
		var formatDateEnd = yearEnd + "/" + monthEnd + "/" + dayEnd;
		htmlRow.push("<td class='align_left'>" + formatDateEnd + "</td>");
		var hEnd = dataRow.endTime.substring(0,2);
		var mEnd = dataRow.endTime.substring(2,4);
		var sEnd = dataRow.endTime.substring(4,6);
		var formatTimeEnd = hEnd + ":" + mEnd + ":" + sEnd;
		htmlRow.push("<td class='align_center'>" + formatTimeEnd + "</td>");
		var text = "";
		var color = "";
		if (dataRow.result == NORMAL) {
			text = "正常";
		} else if (dataRow.result == ABNORMALITY) {
			text = "異常";
			color = "bg_red_FF3300";
		} else if (dataRow.result == CAVEAT) {
			text = "警告";
			color = "bg_yellow";
		}
		htmlRow.push("<td class='align_center "+ color +"'>" + text + "</td>");
		htmlRow.push("<td class='align_left crop' title='"+ dataRow.message +"'>" + dataRow.message + "</td>");
		htmlRow.push("<td class='align_left display_none'>" + dataRow.fileNmExec + "</td>");
		htmlRow.push("<td class='align_left display_none'>" + dataRow.parameters + "</td>");
		htmlRow.push("<td class='align_left display_none'>" + dataRow.jobNo + "</td>");
		return htmlRow.join('');
	}

	/**
	 * フォーカス処理
	 * @param [セッション]システム管理者フラグ
	 * @return 
	 * @exception
	 */
	function setFocus(strSysAdminFlag){
		if (strSysAdminFlag == SYS_ADMIN_FLAG_ADMIN) {
			// 事業所コードへフォーカスをセットする
			$('#ddlDiaJigyo').focus();
		} else if (strSysAdminFlag == SYS_ADMIN_FLAG_USER){
			// 処理日付Fromへフォーカスをセットする
			$('#txtDiaDateFromExec').focus();
		}
	}
	
});

/**
 * 担当者コード検索のCallback
 * 
 * @param data ： 取得されたデータ
 */
function getCom0102d05(data) {	
	var showDataCnt = data.length;
	for (var i = 0; i < showDataCnt; i++) {
		$("#txtDiaUserCodeExec").val(data[i].userCode);
		$("#lblDiaUserNmExec").html(data[i].userNm);
	}
	reTurnCheckTab();
	$("#chkDiaRsJob").focus();
	
}

