/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0102d05Service
 *
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/10/15
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
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d05;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0102d05Dao;

/**
 * コントローラーから呼び出される処理
 *
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0102d05Service extends AbsService {

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  @Autowired
  @Qualifier("com0102d05Dao")
  private Com0102d05Dao com0102d05Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * Daoのgetter.
   * 
   * @return com0102d01Dao Daoクラス
   */
  public Com0102d05Dao getCom0102d05Dao() {
    return com0102d05Dao;
  }

  /**
   * Daoのsetter.
   * 
   * @param com0102d01Dao
   *          Daoクラス
   */
  public void setCom0102d05Dao(Com0102d05Dao com0102d05Dao) {
    this.com0102d05Dao = com0102d05Dao;
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
   * initSearchAction.
   * 
   * @param model:model
   * @param formCom0102d05:formCom0102d05
   */
  public String initSearchAction(FormCom0102d05 formCom0102d05)
      throws JsonProcessingException {
    String jsonData = ""; // 戻り値
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 変数宣言
    Map<String, Object> parms = new HashMap<String, Object>();
    
    // 検索条件セット
    if (formCom0102d05.getUserCodeWk().trim().equals("")) {
      parms.put("userCodeWk", String.valueOf(""));
    } else {
      parms.put("userCodeWk", String.valueOf(formCom0102d05.getUserCodeWk())); // チェーンコード
    }
    if (formCom0102d05.getUserRoleWk().trim().equals("")) {
      parms.put("userRodeWk", String.valueOf(""));// チェーン枝番
    } else {
      parms.put("userRodeWk", String.valueOf(formCom0102d05.getUserRoleWk()));// チェーン枝番
    }
    if (formCom0102d05.getMyOfficeWk().trim().equals("")) {
      parms.put("myOfficeWk", String.valueOf(""));
    } else {
      parms.put("myOfficeWk", formCom0102d05.getMyOfficeWk());
    }
    int searchMax = formCom0102d05.getSearchMax();
    parms.put("searchMax", searchMax + 1);

    List<MstUser> userInfo;
    // SQL実行\
    try {
      userInfo = com0102d05Dao.getCom0102d05Mapper().getMstUserData(parms);

      String userNm = "";
      for (int i = 0; i < userInfo.size(); i++) {
        userNm = Util.convertSanitize(userInfo.get(i).getUserNm());
        userInfo.get(i).setUserNm(userNm);
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(userInfo);
    } catch (JsonProcessingException e) {
      throw e;
    }

    // 変換データを返却
    return jsonData;
  }

  /**
   * searchMSChain.
   * 
   * @param formCom0102d01:formCom0102d01
   * @return jsonData
   * @throws JsonProcessingException e
   */
  public String searchMstUser(FormCom0102d05 formCom0102d05)
      throws JsonProcessingException {

    String userCodeWk = "";// チェーンコード
    String userNameWk = "";// チェーン枝番
    int searchMax = 0;//
    String jsonData = ""; // 戻り値

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter
    // 検索条件判定用
    userCodeWk = formCom0102d05.getUserCodeWk();// 引数のチェーンコードを取得し、親のスクリーニング
    userNameWk = formCom0102d05.getUserNameWk();// 変数に割り当てられた支店コード
    searchMax = formCom0102d05.getSearchMax() + 1;//
    // 検索条件セット
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    try {
      // 引数のチェーンコードがNULLの場合、事務本部の組み合わせのコードを設定
      params.put("userCodeWk", String.valueOf(userCodeWk));

      // 引数の鎖分岐がNULLの場合、//、事務本部の組み合わせのコードを設定
      params.put("searchMax", Integer.valueOf(searchMax));

      // 引数のチェーン名がNULLの場合、//、事務本部の組み合わせのコードを設定
      if (userNameWk.equals("")) {
        userNameWk = "";
      } else {
        userNameWk = "%" + userNameWk + "%";
      }
      params.put("userNameWk", userNameWk);
    } catch (Exception e) {
      List<MstUser> lstCom0102d05 = null; // SQL結果格納用変数
      // 取得データ変換
      try {
        jsonData = ow.writeValueAsString(lstCom0102d05);
      } catch (JsonProcessingException e1) {
        throw e1;
      }
      return jsonData; // 処理終了
    }
    List<MstUser> lstCom0102d05; // SQL結果格納用変数
    try {
      lstCom0102d05 = com0102d05Dao.getCom0102d05Mapper().getSearchResult(
          params);
      // convertSanitize
      String userNm = "";
      for (int i = 0; i < lstCom0102d05.size(); i++) {
        userNm = Util.convertSanitize(lstCom0102d05.get(i).getUserNm());
        lstCom0102d05.get(i).setUserNm(userNm);
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(lstCom0102d05);
    } catch (JsonProcessingException e) {
      throw e;
    }

    // 変換データを返却
    return jsonData;
  }

  /**
   * コード内のリストを選択.
   * 
   * @param model:model
   * @param formCom0102d01:formCom0102d01
   * @return jsonData
   * @throws JsonProcessingException e
   */
  public String selectUser(Model model, FormCom0102d05 formCom0102d05)
      throws JsonProcessingException {

    // 変数宣言
    List<MstUser> lstMstUser; // SQL結果格納用変数
    String jsonData = ""; // 戻り値
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 検索条件セット
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper

    params.put("userCodeWk", formCom0102d05.getUserCodeWk());
    params.put("userNameWk", formCom0102d05.getUserNameWk());
    // SQL実行
    try {
      lstMstUser = com0102d05Dao.getCom0102d05Mapper().getMstUserInfo(params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    // 取得データ変換
    try {
      jsonData = ow.writeValueAsString(lstMstUser);
    } catch (JsonProcessingException e) {
      throw e;
    }

    // 変換データ返却
    return jsonData;
  }
}
