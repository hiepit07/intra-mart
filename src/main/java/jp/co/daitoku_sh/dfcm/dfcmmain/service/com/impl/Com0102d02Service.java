/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0102d02Service
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/03
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomerExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.Com0102d02Const;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0102d02Dao;



/**
 * コントローラーから呼び出される処理
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0102d02Service extends AbsService  {

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  @Autowired
  @Qualifier("com0102d02Dao")
  private Com0102d02Dao com0102d02Dao;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 画面表示処理
   *
   * @param formCom0102d02 フォームクラス
   * @param model モデル
   */
  public void initAction(FormCom0102d02 formCom0102d02, Model model) {

    // 変数宣言
    ArrayList<String> paramMess = new ArrayList<String>(); // エラーメッセージのParam

    // 共通部品取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao, null, null, readPropertiesFileService);

    int intSearchMax = commonGetSystemCom.getCodeSearchMax(); // レコード数上限取得
    String strbusinessDate = commonGetSystemCom.getAplDate(); // 業務日付取得

 // 最大表示件数をセット
    formCom0102d02.setSearchMax(intSearchMax);

    // メッセージ取得
    getDefaultMess(model); // 検索結果０件メッセージの取得

    // 事業所情報取得
    if (strbusinessDate.equals("")) {
      // 業務日付の取得に失敗した場合は処理終了
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      formCom0102d02.setTxtMessage(readPropertiesFileService.getMessage(
          "COM015-E", paramMess)); // 取得したエラーメッセージをセット
      return; // 処理終了
    } else {

      // [入力]事業所コード＝NULLの場合のみ、事業所コンボを作成する
      if (formCom0102d02.getJigyoshoCode().equals("")) {

        // 変数宣言
        ArrayList<MstSJigyoInfo> lstMstSJigyoInfoReturn = new ArrayList<MstSJigyoInfo>();
        List<MstSJigyoInfo> lstMstSJigyoInfo;
        Map<String, Object> params = new HashMap<String, Object>();

        // 検索条件セット
        params.put("businessDate", strbusinessDate);

        // SQL実行
        lstMstSJigyoInfo = com0102d02Dao.getCom0102d02Mapper().getSJigyoInfo(
            params);

        // 取得データが０件の場合、エラーメッセージをセット
        if (lstMstSJigyoInfo.size() == 0) {
          paramMess = new ArrayList<String>();
          paramMess.add("事業所マスタの取得");
          formCom0102d02.setTxtMessage(readPropertiesFileService.getMessage(
              "COM015-E", paramMess)); // 取得したエラーメッセージをセット
          return; // 処理終了
        }

        // 取得データ編集（コード：名称）
        for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
          mstSJigyoInfo.setJgymei(mstSJigyoInfo.getJgycd() + " : "
              + mstSJigyoInfo.getJgymei());
          lstMstSJigyoInfoReturn.add(mstSJigyoInfo);
        }

        // 取得データセット
        model.addAttribute("ShozokuClassList", lstMstSJigyoInfoReturn);

      }
    }

  }

  /**
   * 一覧データ取得処理
   *
   * @param formCom0102d02 フォームクラス
   * @return String（JSON）
   * @throws JsonProcessingException JSON変換エラー
   */
  public String searchCustomer(FormCom0102d02 formCom0102d02)
      throws JsonProcessingException {

    // 変数宣言
    List<MstCustomer> lstMstCustomer; // SQL結果格納用変数
    String strJigyoshoCode = ""; // 事業所コード判定用変数
    String strCustCode = ""; // 得意先コード判定用
    String strCustNm = ""; // 得意先名称判定用
    String strSearchKb = ""; // 検索対象区分判定用変数
    String jsonData = ""; // 戻り値
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 検索条件判定用
    strJigyoshoCode = formCom0102d02.getJigyoshoCode(); // 親画面引数の事業所コードを取得
    strCustCode = formCom0102d02.getCustCode(); // 画面検索条件を取得
    strCustNm = formCom0102d02.getCustNm(); // 画面検索条件を取得
    strSearchKb = formCom0102d02.getSearchKb(); // 親画面引数の検索対象区分を取得

    // 仕様変更：業務日付にて使用中止日を比較する
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null, null,readPropertiesFileService);
    String strBusinessDate = systemCom.getAplDate(); // 業務日付取得

    // 検索条件セット
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    params.put("Search_Max", String.valueOf(formCom0102d02.getSearchMax() + 1)); // 最大件数+1

    // 仕様変更：業務日付追加
    params.put("Business_Date", strBusinessDate);

    if (strJigyoshoCode.equals("")) {

      // 引数の事業所コードがNULLの場合、事業所コンボの事業所コードをセット
      params.put("Jigyosho_Code", formCom0102d02.getDdlShozoku());
    } else {

      // 引数の事業所コードがNULLではない場合、親画面引数の事業所コードをセット
      params.put("Jigyosho_Code", strJigyoshoCode);
    }
    if (strCustCode.equals("")) {
      params.put("Cust_Code", Com0102d02Const.NOT_SEARCH);
    } else {

      strCustCode = "%" + strCustCode + "%"; // LIKE用編集
      params.put("Cust_Code", strCustCode);
    }

    if (strCustNm.equals("")) {
      params.put("Cust_Nm", Com0102d02Const.NOT_SEARCH);
    } else {

      strCustNm = "%" + strCustNm + "%"; // LIKE用編集
      params.put("Cust_Nm", strCustNm);
    }
    params.put("Shop_Kb", formCom0102d02.getShopKb());
    if (strSearchKb.equals("")) {
      params.put("Cust_Flg", Com0102d02Const.NOT_SEARCH);
      params.put("Bild_Flg", Com0102d02Const.NOT_SEARCH);
    } else if (strSearchKb.equals(Com0102d02Const.CUST_FLG)) {
      params.put("Cust_Flg", Com0102d02Const.SEARCH_FLG);
      params.put("Bild_Flg", Com0102d02Const.NOT_SEARCH);
    } else if (strSearchKb.equals(Com0102d02Const.BILD_FLG)) {
      params.put("Cust_Flg", Com0102d02Const.NOT_SEARCH);
      params.put("Bild_Flg", Com0102d02Const.SEARCH_FLG);
    }

    // SQL実行
    lstMstCustomer = com0102d02Dao.getCom0102d02Mapper().getSearchResult(params);

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(lstMstCustomer);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    // 変換データを返却
    return jsonData;

  }

  /**
   * 一覧選択処理
   *
   * @param strCustCode 得意先コード
   * @return String（JSON）
   * @throws JsonProcessingException JSON変換エラー
   */
  public String selectCustomer(String strCustCode)
      throws JsonProcessingException {

    // 変数宣言
    List<MstCustomer> lstMstCustomer; // SQL結果格納用変数
    MstCustomerExample mstCustomerExample = new MstCustomerExample(); // SQL検索条件格納クラス
    String jsonData = ""; // 戻り値
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 検索条件セット
    mstCustomerExample.createCriteria().andCustCodeEqualTo(strCustCode);

    // SQL実行
    lstMstCustomer = com0102d02Dao.getMstCustomerMapper().selectByExample(
        mstCustomerExample);

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(lstMstCustomer);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    // 変換データ返却
    return jsonData;
  }

  /**
   * Defaultメッセージの取得
   *
   * @param Model:model
   */
  public void getDefaultMess(Model model) {

    DefaultMessages defaultMsg;
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();

    // COM025-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM025-EDia"); // ダイアログ用にメッセージコード編集
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM025-E", null));
    defaultMsgLst.add(defaultMsg);

    // COM005-W
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM005-WDia"); // ダイアログ用にメッセージコード編集
    defaultMsg.setMessageTitle("ワーニングメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM005-W", null));
    defaultMsgLst.add(defaultMsg);

    // メッセージセット
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

}
