/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl
 * ファイル名:Nyu0102d03Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/03
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/03 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d03;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyuSeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl.Nyu0102d03Dao;

/**
 * 都度請求未回収設定用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Nyu0102d03Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private Nyu0102d03Dao nyu0102d03Dao;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * DEFAULT MESSAGE の取得.
   * 
   * @return メッセージリスト
   */
  public List<DefaultMessages> getDefaultMess() {

    // TODO: 要修正
    DefaultMessages defaultMsg = new DefaultMessages();

    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", null));

    List<DefaultMessages> defaultMsgList = new ArrayList<DefaultMessages>();
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM008-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM008-E", null));
    defaultMsgList.add(defaultMsg);

    return defaultMsgList;
  }

  /**
   * 入力データチェック（検索）.
   * 
   * @param model モデル
   * @param form フォーム
   * @return チェック結果
   */
  public boolean checkInputParamsForSearch(Model model, FormNyu0102d03 form) {

    String errorCode;
    String errorId = "";
    List<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    // ---------------------------------------------------------------------
    // 事業所コード（必須）
    // ---------------------------------------------------------------------

    String jigyoshoCd = form.isSysAdminFlag() ? form.getSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();

    if (form.isSysAdminFlag()) {

      // 単項目チェック
      errorCode = checkItem(jigyoshoCd, true, InputCheckCom.TYPE_NUM, 8);
      if (errorCode != null && !errorCode.equals("")) {
        logger.debug("事業所コード:単項目チェックエラー");
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("所属事業所");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            errorCode, params));

        errorList.add(errorMsg);
        errorId += "selectedJigyoshoCd" + MstConst.DELIMITER_ERROR;

      }

    }

    // ---------------------------------------------------------------------
    // 請求先コード（必須）
    // ---------------------------------------------------------------------

    String seikyusakiCd = form.getSeikyusakiCd();

    // 単項目チェック
    errorCode = checkItem(seikyusakiCd, true, InputCheckCom.TYPE_NUM, 7);
    if (errorCode != null && !errorCode.equals("")) {
      logger.debug("請求先コード:単項目チェックエラー");
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("請求先コード");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(errorCode,
          params));

      errorList.add(errorMsg);
      errorId += "seikyusakiCd" + MstConst.DELIMITER_ERROR;

    }

    // チェック結果判定
    if (errorId.equals("")) {
      return true;
    } else {
      model.addAttribute("errorMessages", errorList);
      model.addAttribute("lstErrorID", errorId);
      return false;
    }
  }

  /**
   * 請求先情報リストの取得.
   * 
   * @param form フォーム
   * @return 請求先情報リスト
   */
  public List<NyuSeikyusakiInfo> getTsudoSeikyusakiInfoList(
      FormNyu0102d03 form) {

    // パラメータ取得
    String jigyoshoCd = form.isSysAdminFlag() ? form.getCondSelectedJigyoshoCd()
        : form.getLoginJigyoshoCd();
    String businessDate = form.getBusinessDate();
    String seikyusakiCd = form.getCondSeikyusakiCd();

    // パラメータ設定
    Map<String, Object> params = new HashMap<String, Object>();
    // 業務日付
    params.put("BIZ_DATE", businessDate);
    // 事業所コード
    params.put("JIGYOSHO_CD", jigyoshoCd);
    // 請求先コード
    params.put("SEIKYUSAKI_CD", seikyusakiCd);

    // 検索実行
    List<NyuSeikyusakiInfo> list = nyu0102d03Dao.getNyu0102d03Mapper()
        .getTsudoSeikyusakiList(params);

    return list;
  }

  // *************************************************************************
  // **
  // ** PRIVATE METHOD
  // **
  // ************************************************************************/

  /**
   * 単項目チェック.
   * 
   * @param val チェック項目
   * @param required 必須チェックフラグ
   * @param type 型
   * @param len LENGTH
   * @return 必須エラー :COM006-E 型エラー :COM001-E LENGTHエラー :COM001-E
   */
  private String checkItem(String val, boolean required, int type, int len) {

    String errorCode = null;

    // 必須チェック
    if (required) {
      errorCode = InputCheckCom.chkEmpty(val);
      if (errorCode != null) {
        return errorCode;
      }
    }
    // 型チェック
    errorCode = InputCheckCom.chkType(val, type);
    if (errorCode != null) {
      return errorCode;
    }
    // LENGTH チェック
    errorCode = InputCheckCom.chkLength(val, len);
    if (errorCode != null) {
      return errorCode;
    }

    return errorCode;
  }
}
