/**
 * パッケージ名: ファイル名: menu.js
 * 
 * 作成者:アクトブレーンベトナム 作成日:2015/09/17
 * 
 * 履歴 -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

jQuery(document).ready(function($) {
	var liWidth = $(".drop-menu ul li").width();
	$(".drop-menu ul li ul li").width(liWidth);
	// Get retrieves the menu html from local storage
	var menu = $.jStorage.get('menu');
	// Inserts the menu content
	$('#menu').html('');
	$('#menu').append(menu);
	//Handle show sub-menu
	for (var i=0; i < $(".par_menu p").length; i++) {
		if( $.cookie("par_menu" + i) == "open" ) {
			$(".par_menu > li").eq(i).children("ul").show();
		}
	}
	
	$(document).on("click", ".par_menu p", function(e) {
		$(this).next("ul").slideToggle();
		$(this).children("span").toggleClass("open");
		n = $(".par_menu p").index(this);
		if ($.cookie("par_menu" + n) == "open") {
			$.cookie("par_menu" + n, "close", {path:"/"}); 
		} else {
			$.cookie("par_menu" + n, "open", {path:"/"});
		}
	});
	
	
	//
	var isAnimating = false;
	/****************************************************************************
	 *** HOVER MENU HANDLE STARTS
	 ****************************************************************************/
	$(".show_hide").bind("click", function() {
		processHover();
	});

	$(document).on('click', '#btn_close_menu', function(e) {
		e.preventDefault();
		HideOnMenu();
	});

	$(document).on('click', '#goMenuButton', function(e) {
		if (!window.location.origin) {
			window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
		}
		//window.location.href = window.location.origin + ROOT_PATH + "index.html";
	});

	// detect click outside of hover menu
	$(document).bind("click", function(e) {
		var container = $("#dialogonmenu");

		if (!container.is(e.target) // if the target of the click isn't the container
			&& container.has(e.target).length === 0) { // nor a descendant of the container
			HideOnMenu();
		}
	});

	function processHover() {
		if (!isAnimating) {
			isAnimating = true;
			showMenu();
		}
	}

	function showMenu() {
		$("#btn_show").animate({
			left: "-30px"
		}, 500, function() {
			// Animation complete
			ShowOnMenu(300, 625);
		});
	}

	function ShowOnMenu(box_width, box_height) {
		// set dialog's original left
		$("#dialogonmenu").css("left", -box_width + "px");

		// set width, height for dialog
		document.getElementById('dialogonmenu').style.width = box_width + "px";
		document.getElementById('dialogonmenu').style.height = box_height + "px";

		$("#dialogonmenu").css("display", "block").animate({
			opacity: 1,
			left: "0"
		}, 500, function() {
			isAnimating = false;
		});
	}

	function HideOnMenu() {
		if (!isAnimating) {
			isAnimating = true;
			var box_width = $("#dialogonmenu").width();
			$("#dialogonmenu").animate({
				opacity: 0,
				left: -box_width
			}, 1000, function() {
				// animation complete.
				$("#dialogonmenu").css("display", "none");
				// show arrow buttons
				$("#btn_show").animate({
					left: "0"
				}, 500, function() {
					isAnimating = false;
				});
			});
		}
	}
});