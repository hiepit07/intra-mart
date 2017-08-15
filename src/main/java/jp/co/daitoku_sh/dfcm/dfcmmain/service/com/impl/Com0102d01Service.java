/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0102d01Service
 *
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/10/02
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/02 1.00 ABV)グエン リユオン ギア 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSChain;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0102d01Dao;

/**
 * コントローラーから呼び出される処理
 *
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0102d01Service extends AbsService {

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  @Autowired
  @Qualifier("com0102d01Dao")
  private Com0102d01Dao com0102d01Dao;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * Daoのgetter.
   * 
   * @return com0102d01Dao Daoクラス
   */
  public Com0102d01Dao getCom0102d01Dao() {
    return com0102d01Dao;
  }

  /**
   * Daoのsetter.
   * 
   * @param com0102d01Dao
   *          Daoクラス
   */
  public void setCom0102d01Dao(Com0102d01Dao com0102d01Dao) {
    this.com0102d01Dao = com0102d01Dao;
  }

  /**
   * Defaultメッセージの取得
   *
   * @param Model:model
   */
  public void getDefaultMess(Model model) {

    DefaultMessages defaultMsg;

    // COM025-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM025-EDia");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM025-E", null));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsgLst.add(defaultMsg);

    // COM005-W
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM005-WDia");
    defaultMsg.setMessageTitle("ワーニングメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM005-W", null));
    defaultMsgLst.add(defaultMsg);

    // メッセージセット
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * チェーン検索.
   * 
   * @param formCom0102d01：フォーム
   * @param model：モデル
   * @return jsonData
   * @throws JsonProcessingException e
   */
  public String searchMsChain(FormCom0102d01 formCom0102d01, Model model)
      throws JsonProcessingException {

    String strChainCode = "";// チェーンコード
    String strChainBranch = "";// チェーン枝番
    String strChainName = "";// チェーン名
    String jsonData = ""; // 戻り値

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter
    // 検索条件判定用
    strChainCode = formCom0102d01.gettxtChainCode();// 引数のチェーンコードを取得し、親のスクリーニング
    strChainBranch = formCom0102d01.gettxtChainBranch();// 変数に割り当てられた支店コード
    strChainName = formCom0102d01.gettxtChainName();// 可変鎖に名前を割り当てます
    String date = ""; // 戻り値    
    date = DateUtil.getSysDate();
    int searchMax = 0;
    searchMax = formCom0102d01.getSearchMax();
    // 検索条件セット
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    try {

      params.put("date", date);

      // 引数のチェーンコードがNULLの場合、事務本部の組み合わせのコードを設定
      if (strChainCode.equals("") || strChainCode.equals(" ")) {
        params.put("chainCd", strChainCode);
      } else {
        params.put("chainCd", Short.valueOf(strChainCode));
      }
      if (strChainBranch.equals("") || strChainBranch.equals(" ")) {
        // 引数の鎖分岐がNULLの場合、//、事務本部の組み合わせのコードを設定
        params.put("chainEda", String.valueOf(strChainBranch));
      } else {
        // 引数の鎖分岐がNULLの場合、//、事務本部の組み合わせのコードを設定
        params.put("chainEda", strChainBranch);
      }
    } catch (Exception e) {
      List<MstSChain> lstCom0102d01 = null; // SQL結果格納用変数
      // 取得データ変換
      try {
        jsonData = ow.writeValueAsString(lstCom0102d01);
      } catch (JsonProcessingException e1) {

        throw e1;
      }
      return jsonData; // 処理終了
    }
    searchMax = searchMax + 1;
    params.put("searchMax", searchMax);
    // 引数のチェーン名がNULLの場合、//、事務本部の組み合わせのコードを設定
    if (strChainName.trim().equals("")) {
      params.put("chainNm", strChainName);
    } else {
      strChainName = "%" + strChainName + "%";
      params.put("chainNm", strChainName);
    }

    List<MstSChain> lstCom0102d01; // SQL結果格納用変数
    try {
      lstCom0102d01 = com0102d01Dao.getCom0102d01Mapper().getSearchResult(
          params);
      Short chainCode = 0;
      Short chainBranch = 0;
      String chainNm = "";
      // convertSanitize
      for (int i = 0; i < lstCom0102d01.size(); i++) {
        MstSChain chain = new MstSChain();
        chainCode = Short.valueOf(lstCom0102d01.get(i).getChncd());
        chain.setChncd(chainCode);
        chainBranch = Short.valueOf(lstCom0102d01.get(i).getChneda());
        chain.setChneda(chainBranch);
        chainNm = Util.convertSanitize(lstCom0102d01.get(i).getChnmei());
        chain.setChnmei(chainNm);
        lstCom0102d01.get(i).setChncd(chainCode);
        lstCom0102d01.get(i).setChneda(chainBranch);
        lstCom0102d01.get(i).setChnmei(chainNm);
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(lstCom0102d01);
    } catch (JsonProcessingException e) {
      throw e;
    }

    // 変換データを返却
    return jsonData;
  }
}
