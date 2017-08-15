/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Sei0107d01Controller.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/12/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0107d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0107d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.Sei0107d01Service;

/**
 * Controller layer.
 * 
 * @author NghiaNguyen
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/sei/SEI01-07D00/")
public class Sei0107d01Controller  extends AbsController {

  @Autowired
  @Qualifier("sei0107d01Service")
  private Sei0107d01Service sei0107d01Service;
  
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  /**
   * 画面表示初期処理。.
   * 
   * @param model A holder for model attributes
   * @param formSei0107d01 The controller object via modelAttribute.
   * @param request The request object.
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormSei0107d01 formSei0107d01,
      HttpServletRequest request) {
    // 1.【初期処理】
    // セッション情報取得
    String loginPage = Util.checkSession(model, request, CommonConst.SEI0107D00_SCREEN_NAME);
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    if (!loginPage.equalsIgnoreCase(CommonConst.EMPTY)) {
      return loginPage;
    }
    
    // 共通部品を使用して、業務日付を取得する
    String businessDate = sei0107d01Service.getBusinessDate();
    if (businessDate == null) {
      businessDate = DateUtil.getSysDate();
      formSei0107d01.setBusinessDate(businessDate);
    } else {
      formSei0107d01.setBusinessDate(businessDate);
    }
    sei0107d01Service.init(model, formSei0107d01);
    String screen = "sei/sei0107d01";
    return screen;
  }
  
  /**
   * changeDivision 請求集計表区分.
   * @param model model 
   * @param formSei0107d01 formSei0107d01 エンティティ
   * @return list
   */
  @RequestMapping(value = "/changeDivision") 
  @ResponseBody
  public List<RstSei0107d01> changeDivision (Model model, FormSei0107d01 formSei0107d01 ) {
      
    List<RstSei0107d01> lst = sei0107d01Service.changeDivision(model, formSei0107d01);
    
    return lst;
  }
  
}
