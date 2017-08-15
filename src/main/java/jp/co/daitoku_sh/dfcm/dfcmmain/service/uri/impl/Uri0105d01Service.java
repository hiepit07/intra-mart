/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl
 * ファイル名:Uri0105d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/12/09
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl.Uri0105d01Dao;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Uri0105d01Service extends AbsService {
  
  @Autowired
  @Qualifier("uri0105d01Dao")
  private Uri0105d01Dao uri0105d01Dao;
  
  /** Propertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する.
   * 
   * @param formMst0109d01:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo_Dll(FormUri0105d01 formUri0105d01, Model model) {
    try {
      ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
      ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = null;
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", String.valueOf(formUri0105d01.getBusinessDate()));
      lstMstSJigyoInfo = uri0105d01Dao.getUri0105d01Mapper().getSJigyoInfo(params);
      if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
        ObjCombobox tempObj = new ObjCombobox();
        //最初の項目
        tempObj.setCode("");
        tempObj.setName("");
        lstMstSJigyoInfoReturn.add(tempObj);
        //項目の追加
        for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
          tempObj = new ObjCombobox();
          tempObj.setCode(mstSJigyoInfo.getJgycd());
          tempObj.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
              .getJgymei());
          lstMstSJigyoInfoReturn.add(tempObj);
        }
        model.addAttribute("OyaShozokuClassList", lstMstSJigyoInfoReturn);
        return true;
      } else {
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }
  
  /**
   * 
   * @param request
   * @param formUri0105d01
   * @param model
   */
  public void init(HttpServletRequest request, FormUri0105d01 formUri0105d01, Model model) {

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    formUri0105d01.setSysAdminFlag(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)));
    // ログイン事業所コード値を取得する
    formUri0105d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
    
    // 共通部品を使って、業務日付を取得する
    String strBussinessDate = null;
    strBussinessDate = this.getBusinessDate();
    
    // 戻り値チェック
    // (1)[画面_hidden]業務日付 ≠ Nullの場合、正常とする
    // (2)[画面_hidden]業務日付 ＝ Nullの場合、エラーとする

    if (strBussinessDate == null || strBussinessDate.equalsIgnoreCase("")) {
      // 6-3 エラー処理へ
      // メッセージコード（COM015-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formUri0105d01.setErrorControl("errorControl");
      return;
    } else {
      // hidden設定
      formUri0105d01.setBusinessDate(Integer.parseInt(strBussinessDate));
    }    
    
    // [変数]セッション.システム管理者フラグ = '1'（有効）の場合、事業所マスタから事業所情報を取得する。
    if (formUri0105d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      if (!this.setJigyo_Dll(formUri0105d01, model)) {
        // エラー（COM009-E)
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタ");
        paramMess.add("事業所情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通部品【システム共通.エラー時のコントロール制御】
        formUri0105d01.setErrorControl("errorControl");
        return;
      }
    }
    this.setInit(request, formUri0105d01, model);
    
  }
  
  /**
   * 初期値セット.
   * @param formUri0105d01:フォーム
   */
  public void setInit(HttpServletRequest request, FormUri0105d01 formUri0105d01, Model model) {
    // ヘッダ部を表示し、以下の初期値をセットする
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // セッション.業務日付 値を取得する
    String strLoginBussinessDate = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_BUSSINES_DATE));
    if (strLoginBussinessDate == null || strLoginBussinessDate.equalsIgnoreCase("")) {
      formUri0105d01.setLoginBussinessDate(DateUtil.getSysDate());
    } else {
      formUri0105d01.setLoginBussinessDate(strLoginBussinessDate);
    }

    // [画面]データ受信日From = [変数]セッション.業務日付
    formUri0105d01.setTxtJuDateFrom(formUri0105d01.getLoginBussinessDate());
    // [画面]データTo = [変数]セッション.業務日付
    formUri0105d01.setTxtJuDateTo(formUri0105d01.getLoginBussinessDate());

    // [画面]得意先コード = ''
    formUri0105d01.setTxtCustCode("");
    // [画面]店舗コード = ''（非活性化）
    // [画面]店舗検索ボタン = 非活性化
    formUri0105d01.setTxtShopCode("");
    model.addAttribute("shopDisable", "1");
    // [画面]伝票No =''
    formUri0105d01.setTxtDenCode("");
    // [画面]処理済データを対象とする = OFF
    formUri0105d01.setChkSyori(false);
    // [画面]データ種別（受領データ） = ON
    formUri0105d01.setChkReceiptData(true);
    // [画面]データ種別（返品データ） = ON
    formUri0105d01.setChkReturnedData(true);
    // [画面]データ種別（欠品データ） = ON
    formUri0105d01.setChkLackOfData(true);
    // [画面]データ種別（修正データ） = ON
    formUri0105d01.setChkModifyData(true);

    // [画面]事業所 ＝ [セッション]ログイン所属事業所コード
    if (formUri0105d01.getSysAdminFlag().equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_VALID)) {
      formUri0105d01.setDdlOyaShozoku(formUri0105d01.getLoginJigyouShoCode());
    }
  }
  
  /**
   * 業務日付を取得する.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    String strResult = null;
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 共通部品を呼ぶ
      strResult = systemCom.getAplDate();

      
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return strResult;
  }
}
