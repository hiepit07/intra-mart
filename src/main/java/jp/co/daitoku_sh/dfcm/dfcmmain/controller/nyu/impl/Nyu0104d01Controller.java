/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl
 * ファイル名:Nyu0104d01Controller.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/12/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/16 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl.Nyu0104d01Service;

/**
 * コントローラクラス 入金登録
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/nyu/NYU01-04D00/")
public class Nyu0104d01Controller extends AbsController {

  @Autowired
  private Nyu0104d01Service nyu0104d01Service;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 画面表示.
   * 
   * @param model モデル
   * @param request リクエスト
   * @return 遷移先
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request,
      FormNyu0104d01 formNyu0104d01) {
    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0104D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    ArrayList<ErrorMessages> listErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    // 共通部品初期化
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    // 業務日付の取得
    String businessDate = commonGetSysCom.getAplDate();
    if (businessDate == null) {
      businessDate = DateUtil.getSysDate();
    }
    formNyu0104d01.setBusinessDate(Integer.parseInt(businessDate));
    
    // set message default
    nyu0104d01Service.getDefaultMess(model);

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    formNyu0104d01.setSysAdminFlag(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)));

    // ログイン事業所コード値を取得する
    formNyu0104d01.setLoginJigyoshoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
    // ログイン事業所名値を取得する
    formNyu0104d01.setJigyoshoCodeWk(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));

    String strScreen = "/nyu/nyu0104d01";
    
    // [セッション]システム管理者フラグ ＝ '1' （システム管理者）の場合、事業所コンボの作成を行う
    if (formNyu0104d01.getSysAdminFlag().equalsIgnoreCase(
        NyuConst.SYS_ADMIN_FLG_VALID)) {
      if (!nyu0104d01Service.setJigyo(formNyu0104d01, model)) {
        listErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタ");
        paramMess.add("事業所情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        listErrMess.add(errMess);
        formNyu0104d01.setErrorControl("errorControl");
      }
    }
    // 初期値セット
    nyu0104d01Service.setViewItem(formNyu0104d01, model);

    return strScreen;

  }

}
