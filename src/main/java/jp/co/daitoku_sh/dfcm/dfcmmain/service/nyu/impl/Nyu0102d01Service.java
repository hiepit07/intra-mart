/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl
 * ファイル名:Nyu0102d01Service.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/04
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/04 1.00 ACT)ANZAI 新規開発
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

import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyukinGamenMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl.Nyu0102d01Dao;

/**
 * 入金登録用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Nyu0102d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private Nyu0102d01Dao nyu0102d01Dao;

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
   * 権限区分の取得
   * 
   * @param isSysAdminFlag
   * @param authCode
   * @return
   */
  public String getAuthKbn(boolean isSysAdminFlag, String authCode) {
    String authKbn = NyuConst.AUTH_KBN_TANTOSHA;

    if (isSysAdminFlag) {
      // システム管理者
      authKbn = NyuConst.AUTH_KBN_SYS_ADMIN;
    }
    if (authKbn.equals(NyuConst.AUTH_KBN_TANTOSHA)) {

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("GL_CODE", NyuConst.AUTH_KBN_KEIRI_ADMIN);
      params.put("AUTH_CODE", authCode);
      String tmpAuthKbn = nyu0102d01Dao.getNyu0102d01Mapper().getAuthKbn(
          params);
      if (tmpAuthKbn != null && !tmpAuthKbn.equals("")) {
        // 経理管理者
        authKbn = NyuConst.AUTH_KBN_KEIRI_ADMIN;
      }
    }
    if (authKbn.equals(NyuConst.AUTH_KBN_TANTOSHA)) {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("GL_CODE", NyuConst.AUTH_KBN_JIGYOSHO_ADMIN);
      params.put("AUTH_CODE", authCode);
      String tmpAuthKbn = nyu0102d01Dao.getNyu0102d01Mapper().getAuthKbn(
          params);
      if (tmpAuthKbn != null && !tmpAuthKbn.equals("")) {
        // 事業所管理者
        authKbn = NyuConst.AUTH_KBN_JIGYOSHO_ADMIN;
      }
    }
    logger.debug("権限区分=[" + authKbn + "]");

    return authKbn;
  }

  /**
   * 遅れ理由リストの取得
   * 
   * @param commonGetSysCom 共通部品
   * @param model モデル
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getOkureRiyuList(CommonGetSystemCom commonGetSysCom,
      Model model, ArrayList<ErrorMessages> errorList) {

    boolean isOk = false;
    List<MstGeneralData> genList = commonGetSysCom.getMstGeneralConf(
        "Delay_Reason", null);

    if (genList == null || genList.size() == 0) {

      // 遅れ理由なし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("汎用マスタ");
      params.add("遅れ理由");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);

    } else {

      ObjCombobox firstObject = new ObjCombobox();
      firstObject.setCode("");
      firstObject.setName("");

      List<ObjCombobox> okureList = new ArrayList<ObjCombobox>();
      okureList.add(firstObject);

      for (MstGeneralData item : genList) {
        String code = item.getGlCode();
        String name = item.getTarget1();

        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(code);
        tempObj.setName(code + " : " + name);
        okureList.add(tempObj);
      }
      model.addAttribute("okureRiyuList", okureList);
      isOk = true;
    }
    return isOk;
  }

  /**
   * 選択科目区分リストの取得
   * 
   * @param commonGetSysCom 共通部品
   * @param model モデル
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getSelectKamokuKbnList(CommonGetSystemCom commonGetSysCom,
      Model model, ArrayList<ErrorMessages> errorList) {

    boolean isOk = false;
    List<MstGeneralData> genList = commonGetSysCom.getMstGeneralConf(
        "Sel_Sub_Code_Kb", null);

    if (genList == null || genList.size() == 0) {

      // 選択科目区分なし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("汎用マスタ");
      params.add("選択科目区分");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);

    } else {

      ObjCombobox firstObject = new ObjCombobox();
      firstObject.setCode("");
      firstObject.setName("");

      List<ObjCombobox> selectKamokuList = new ArrayList<ObjCombobox>();
      selectKamokuList.add(firstObject);

      for (MstGeneralData item : genList) {
        String code = item.getGlCode();
        String name = item.getTarget1();

        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(code);
        tempObj.setName(code + " : " + name);
        selectKamokuList.add(tempObj);
      }
      model.addAttribute("selectKamokuList", selectKamokuList);
      isOk = true;
    }
    return isOk;
  }

  /**
   * 残高理由リストの取得
   * 
   * @param commonGetSysCom 共通部品
   * @param model モデル
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getZandakaRiyuList(CommonGetSystemCom commonGetSysCom,
      Model model, ArrayList<ErrorMessages> errorList) {

    boolean isOk = false;
    List<MstGeneralData> genList = commonGetSysCom.getMstGeneralConf(
        "Balance_Reason", null);

    if (genList == null || genList.size() == 0) {

      // 残高理由なし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("汎用マスタ");
      params.add("残高理由");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);

    } else {

      ObjCombobox firstObject = new ObjCombobox();
      firstObject.setCode("");
      firstObject.setName("");

      List<ObjCombobox> zandakaRiyuList = new ArrayList<ObjCombobox>();
      zandakaRiyuList.add(firstObject);

      for (MstGeneralData item : genList) {
        String code = item.getGlCode();
        String name = item.getTarget1();

        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(code);
        tempObj.setName(code + " : " + name);
        zandakaRiyuList.add(tempObj);
      }
      model.addAttribute("zandakaRiyuList", zandakaRiyuList);
      isOk = true;
    }
    return isOk;
  }
  
  /**
   * 発生場所リストの取得
   * 
   * @param model モデル
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getHasseiBashoList(Model model, ArrayList<ErrorMessages> errorList) {
    boolean isOk = false;
    
    List<Map<String, Object>> result = nyu0102d01Dao.getNyu0102d01Mapper().getHasseiBashoList();
    
    if (result == null || result.size() == 0) {
      // 発生場所なし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("勘定科目マスタ（コード）");
      params.add("発生場所");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);
    } else {
      
      ObjCombobox firstObject = new ObjCombobox();
      firstObject.setCode("");
      firstObject.setName("");

      List<ObjCombobox> hasseiBashoList = new ArrayList<ObjCombobox>();
      hasseiBashoList.add(firstObject);

      for (Map<String, Object> item : result) {
        String code = String.valueOf(item.get("CODE"));
        String name = String.valueOf(item.get("NAME"));

        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(code);
        tempObj.setName(code + " : " + name);
        
        hasseiBashoList.add(tempObj);
      }
      model.addAttribute("hasseiBashoList", hasseiBashoList);
      
      isOk = true;
    }
    
    return isOk;
  }

  /**
   * 追加科目リストの取得
   * 
   * @param model モデル
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getAddKamokuList(Model model, ArrayList<ErrorMessages> errorList) {
    boolean isOk = false;
    
    List<Map<String, Object>> result = nyu0102d01Dao.getNyu0102d01Mapper().getAddKamokuList();
    
    if (result == null || result.size() == 0) {
      // 追加科目情報なし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("勘定科目マスタ（機能）");
      params.add("科目コード");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);
    } else {
      
      ObjCombobox firstObject = new ObjCombobox();
      firstObject.setCode("");
      firstObject.setName("");

      List<ObjCombobox> addKamokuList = new ArrayList<ObjCombobox>();
      addKamokuList.add(firstObject);

      for (Map<String, Object> item : result) {
        String code = String.valueOf(item.get("CODE"));
        String name = String.valueOf(item.get("NAME"));

        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(code);
        tempObj.setName(code + " : " + name);
        
        addKamokuList.add(tempObj);
      }
      model.addAttribute("addKamokuList", addKamokuList);
      
      isOk = true;
    }
    
    return isOk;
  }
  
  /**
   * 入金調整額の取得
   * 
   * @param seikyusakiCd 請求先コード
   * @param seikyuShimebi 請求締日
   * 
   * @return 調整額
   */
  public int getNyukinChoseiGaku(String seikyusakiCd, String seikyuShimebi) {
    
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("SEIKYUSAKI_CD", seikyusakiCd);
    params.put("SEIKYU_SHIMEBI", seikyuShimebi);
    
    int choseiGaku = nyu0102d01Dao.getNyu0102d01Mapper().getNyukinChoseiGaku(params);
    
    return choseiGaku;
    
  }
  
  /**
   * 初期表示科目リストの取得
   * 
   * @param form フォーム
   * @param errorList エラーリスト
   * 
   * @return 処理結果
   */
  public boolean getDefaultKamokuList(FormNyu0102d01 form,
      ArrayList<ErrorMessages> errorList) {

    List<Map<String, Object>> kamokuList = nyu0102d01Dao.getNyu0102d01Mapper()
        .getKamokuInfoList(null);

    if (kamokuList == null || kamokuList.size() == 0) {
      // 初期表示科目リスト取得エラー
      
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> msgParams = new ArrayList<String>();

      msgParams.add("勘定科目マスタ（機能）");
      msgParams.add("勘定科目");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", msgParams));
      errorList.add(errorMsg);

      return false;
    }

    List<NyukinGamenMeisaiInfo> gamenList = new ArrayList<NyukinGamenMeisaiInfo>();
    form.setMeisaiList(null);
    form.setMeisaiList(gamenList);

    int idx = 1;
    for (Map<String, Object> rec : kamokuList) {

      NyukinGamenMeisaiInfo info = new NyukinGamenMeisaiInfo();

      // No
      info.setNo(String.valueOf(idx));
      // 機能詳細区分
      info.setKinoShosaiKbn(String.valueOf(rec.get("FUNC_SHOSAI_KBN")));
      // 科目コード
      info.setKamokuCd(String.valueOf(rec.get("KAMOKU_CD")));
      // 貸借区分
      info.setTaishakuCd(String.valueOf(rec.get("TAISHAKU_KBN")));
      // 発生場所
      info.setSelectedHasseiBasho(String.valueOf(rec.get("HASSEI_BASHO")));
      // 税区分
      info.setTaxKbn(String.valueOf(rec.get("TAX_KBN")));
      // 補助科目設定区分
      info.setHojoKamokuSetteiKbn(String.valueOf(rec.get("HOJO_KAMOKU_SETTEI_KBN")));
      // 補助科目固定値
      info.setHojoKamokuKoteichi(String.valueOf(rec.get("HOJO_KAMOKU_KOTEICHI")));
      // 画面タイトル
      info.setGamenTitle(String.valueOf(rec.get("GAMEN_TITLE")));
      // 科目選択可否
      info.setKamokuSentakuKahi(String.valueOf(rec.get("KAMOKU_SENTAKU_KAHI")));
      // 正式科目名称
      info.setSeishikiKamokuName(String.valueOf(rec.get("KAMOKU_MST_KAMOKU_CD")));
      // 科目マスタ（機能）科目コード
      info.setKamokuMstKamokuCd(String.valueOf(rec.get("SEISHIKI_KAMOKU_NAME")));
      
      gamenList.add(info);
      idx++;
    }

    return true;

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
