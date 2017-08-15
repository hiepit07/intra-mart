/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0102d04Service
 *
 * <p>作成者:グエン リユオン ギア
 * 作成日:2015/10/12
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
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.Com0102d04Model;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d04;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0102d04Dao;

/**
 * コントローラーから呼び出される処理
 *
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Com0102d04Service extends AbsService {

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  @Autowired
  @Qualifier("com0102d04Dao")
  private Com0102d04Dao com0102d04Dao;

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
   * Dao.
   * 
   * @return com0102d04Dao
   */
  public Com0102d04Dao getCom0102d04Dao() {
    return com0102d04Dao;
  }

  /**
   * setCom0102d04Dao setter.
   * 
   * @param com0102d04Dao:com0102d04Dao
   */
  public void setCom0102d04Dao(Com0102d04Dao com0102d04Dao) {
    this.com0102d04Dao = com0102d04Dao;
  }

  /**
   * ReadPropertiesFileService getter.
   * 
   * @return readPropertiesFileService
   */
  public ReadPropertiesFileService getReadPropertiesFileService() {
    return readPropertiesFileService;
  }

  /**
   * setReadPropertiesFileService setter.
   * 
   * @param readPropertiesFileService:readPropertiesFileService
   */
  public void setReadPropertiesFileService(
      ReadPropertiesFileService readPropertiesFileService) {
    this.readPropertiesFileService = readPropertiesFileService;
  }

  /**
   * Defaultメッセージの取得.
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
   * 初期処理.
   * @param formCom0102d04:フォーム
   */
  public void init(FormCom0102d04 formCom0102d04) {
    String changeCodeWk = formCom0102d04.getChangeCodeWk();
    String changeBranchWk = formCom0102d04.getChangeBranchWk();
    String customerCodeWk = formCom0102d04.getCustomerCodeWk();
    String deadlineWk = formCom0102d04.getDeadlineWk();
    int searchMax = formCom0102d04.getSearchMax();
    
    // チェーンコード取得
    // ケース changeCodeWk = "" と // changeBranchWk = ""
    if (changeCodeWk.equals("") || changeBranchWk.equals("") || changeCodeWk == null
        || changeBranchWk == null) {
      // 変数宣言
      Map<String, Object> parms = new HashMap<String, Object>();
      // 検索条件セット

      try {
        parms.put("customerCodeWk", String.valueOf(customerCodeWk)); // チェーンコード
        parms.put("deadlineWk", String.valueOf(deadlineWk));// チェーン枝番
        parms.put("searchMax", Integer.valueOf(searchMax + 1));
      } catch (Exception ex) {
        //Error
        formCom0102d04.setTxtDiaMessage("#COM025-EDia");
      }
      try {
        List<Com0102d04Model> custList = com0102d04Dao.getCom0102d04Mapper()
            .getMstCustomerData(parms);
        if (custList.size() == 0) {
          //Error
          formCom0102d04.setTxtDiaMessage("#COM025-EDia");
         
        } else {
          formCom0102d04.setChangeCodeWk(custList.get(0).getCainCode());
          formCom0102d04.setChangeBranchWk(custList.get(0).getCainIdx());
        }

      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
    }
  }
  
  /**
   * initAction.
   * 
   * @param formCom0102d04:formCom0102d04
   * @param model:model
   * @throws JsonProcessingException e
   */
  public String initSearchAction(FormCom0102d04 formCom0102d04) throws JsonProcessingException {

    // 変数を初期化します
    String changeCodeWk = "";
    String changeBranchWk = "";
    String ourCompanyItemCodeWk = "";
    // 検索条件判定用
    changeCodeWk = formCom0102d04.getChangeCodeWk();
    changeBranchWk = formCom0102d04.getChangeBranchWk();
    ourCompanyItemCodeWk = formCom0102d04.getOurCompanyItemCodeWk();

    // 変数を初期化します
    String customerCodeWk = "";
    String saleTypeWk = "";
    String deadlineWk = "";
    int searchMax = formCom0102d04.getSearchMax();

    List<Com0102d04Model> lstResult = null;
    String jsonData = ""; // 戻り値
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 検索条件判定用
    customerCodeWk = formCom0102d04.getCustomerCodeWk();
    saleTypeWk = formCom0102d04.getSaleTypeWk();
    deadlineWk = formCom0102d04.getDeadlineWk();
    String flightWk = "";
    flightWk = formCom0102d04.getFlightWk();
    
    // 品目情報取得
    // ケース customerCodeWk= null とsaleTypeWk = null
    if (customerCodeWk == null || saleTypeWk == null
        || customerCodeWk.equals("") || saleTypeWk.equals("")) {
      // 変数宣言
      Map<String, Object> parms = new HashMap<String, Object>();
      // 検索条件セット
      try {
        parms.put("ourCompanyItemCodeWk", ourCompanyItemCodeWk);
        parms.put("changeCodeWk", Integer.valueOf(changeCodeWk)); // チェーンコード
        parms.put("changeBranchWk", Integer.valueOf(changeBranchWk));// チェーン枝番
        parms.put("searchMax", Integer.valueOf(searchMax + 1));
        if (deadlineWk == null || deadlineWk.equalsIgnoreCase("")) {
          parms.put("deadlineWk", 0);
        } else {
          parms.put("deadlineWk", Integer.valueOf(deadlineWk));
        }
        if (flightWk == null || flightWk.equalsIgnoreCase("")) {
          parms.put("flightWk", 0);
        } else {
          parms.put("flightWk", Integer.valueOf(flightWk));
        }
        
        
      } catch (Exception ex) {
        // 取得データ変換
        try {
          jsonData = ow.writeValueAsString(lstResult);
        } catch (JsonProcessingException e) {
          throw e;
        }
        // 変換データを返却
        return jsonData;
      }

      try {
        lstResult = com0102d04Dao.getCom0102d04Mapper()
            .getMstSSeihn(parms);
        try {
          jsonData = ow.writeValueAsString(lstResult);
        } catch (JsonProcessingException e1) {
          throw e1;
        }
        return jsonData; // 処理終了
        
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
    } else {
      // 変数宣言
      Map<String, Object> parms = new HashMap<String, Object>();
      // 検索条件セット
      try {
        parms.put("changeCodeWk", Integer.valueOf(changeCodeWk)); // チェーンコード
        parms.put("changeBranchWk", Integer.valueOf(changeBranchWk));// チェーン枝番
        parms.put("customerCodeWk", String.valueOf(customerCodeWk));
        parms.put("ourCompanyItemCodeWk", String.valueOf(ourCompanyItemCodeWk));
        if (deadlineWk == null || deadlineWk.equalsIgnoreCase("")) {
          parms.put("deadlineWk", 0);
        } else {
          parms.put("deadlineWk", Integer.valueOf(deadlineWk));
        }
        if (flightWk == null || flightWk.equalsIgnoreCase("")) {
          parms.put("flightWk", 0);
        } else {
          parms.put("flightWk", Integer.valueOf(flightWk));
        }
        parms.put("saleTypeWk", String.valueOf(saleTypeWk));
        parms.put("searchMax", Integer.valueOf(searchMax + 1));
      } catch (Exception ex) {
        // 取得データ変換
        try {
          jsonData = ow.writeValueAsString(lstResult);
        } catch (JsonProcessingException e) {
          throw e;
        }
        // 変換データを返却
        return jsonData;
      }
      try {
        lstResult = com0102d04Dao.getCom0102d04Mapper().getMstCustTanKaSeihn(
            parms);
        try {
          jsonData = ow.writeValueAsString(lstResult);
        } catch (JsonProcessingException e1) {
          throw e1;
        }
        return jsonData; // 処理終了
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
    }

  }

  /**
   * function getSearchResult.
   * 
   * @param model:model
   * @param formCom0102d04:formCom0102d04
   * @return jsonData
   * @throws JsonProcessingException e
   */
  public String getSearchResult(Model model, FormCom0102d04 formCom0102d04)
      throws JsonProcessingException {
    // 変数を初期化します
    String jsonData = "";
    String changeCodeWk = "";
    String changeBranchWk = "";
    String ourCompanyItemCodeWk = "";
    // ObjectWriter
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter
    // フォームからデータを取得 fromCOm0102d04
    changeCodeWk = formCom0102d04.getChangeCodeWk();
    changeBranchWk = formCom0102d04.getChangeBranchWk();
    ourCompanyItemCodeWk = formCom0102d04.getOurCompanyItemCodeWk();
    // 変数を初期化します
    String ourCompanyItemNameWk = "";
    String customerCodeWk = "";

    // 連結
    ourCompanyItemNameWk = formCom0102d04.getOurCompanyItemNameWk();

    if (ourCompanyItemNameWk.equals("")) {
      ourCompanyItemNameWk = "";
    } else {
      ourCompanyItemNameWk = "%" + ourCompanyItemNameWk + "%";
    }
    customerCodeWk = formCom0102d04.getCustomerCodeWk();
    // 変数を初期化します
    String saleTypeWk = "";
    String deadlineWk = "";
    String flightWk = "";
    // 連結
    saleTypeWk = formCom0102d04.getSaleTypeWk();
    deadlineWk = formCom0102d04.getDeadlineWk();
    flightWk = formCom0102d04.getFlightWk();   

    // ストリームプロセッサ
    if (customerCodeWk == null || saleTypeWk == null) {
      try {
        // 変数宣言
        Map<String, Object> parms = new HashMap<String, Object>();
        // 検索条件セット
        parms.put("ourCompanyItemCodeWk", String.valueOf(ourCompanyItemCodeWk));
        parms.put("ourCompanyItemNameWk", String.valueOf(ourCompanyItemNameWk));
        parms.put("changeCodeWk", Integer.valueOf(changeCodeWk)); // チェーンコード
        parms.put("changeBranchWk", Integer.valueOf(changeBranchWk));// チェーン枝番
        if (deadlineWk == null || deadlineWk.equalsIgnoreCase("")) {
          parms.put("deadlineWk", 0);
        } else {
          parms.put("deadlineWk", Integer.valueOf(deadlineWk));
        }
        if (flightWk == null || flightWk.equalsIgnoreCase("")) {
          parms.put("flightWk", 0);
        } else {
          parms.put("flightWk", Integer.valueOf(flightWk));
        }
        int searchMax = formCom0102d04.getSearchMax();
        parms.put("searchMax", Integer.valueOf(searchMax + 1));
        // リストを初期化します
        try {
          List<Com0102d04Model> sheinList = com0102d04Dao.getCom0102d04Mapper()
              .getSearchMstSSeihn(parms);

          try {
            jsonData = ow.writeValueAsString(sheinList);
          } catch (JsonProcessingException e) {
            throw e;
          }
        } catch (MyBatisSystemException e) {
          logger.error(e.getMessage());
          throw e;
        }
      } catch (Exception e) {
        List<Com0102d04Model> lstCom0102d04 = null; // SQL結果格納用変数
        // 取得データ変換
        try {
          jsonData = ow.writeValueAsString(lstCom0102d04);
        } catch (JsonProcessingException e1) {
          throw e1;
        }
        return jsonData; // 処理終了
      }
      return jsonData;
    } else if (customerCodeWk.equals("") || saleTypeWk.equals("")) {
      try {
        // 変数宣言
        Map<String, Object> parms = new HashMap<String, Object>();
        // 検索条件セット
        parms.put("ourCompanyItemCodeWk", String.valueOf(ourCompanyItemCodeWk));
        parms.put("ourCompanyItemNameWk", String.valueOf(ourCompanyItemNameWk));
        parms.put("changeCodeWk", Integer.valueOf(changeCodeWk)); // チェーンコード
        parms.put("changeBranchWk", Integer.valueOf(changeBranchWk));// チェーン枝番
        if (deadlineWk == null || deadlineWk.equalsIgnoreCase("")) {
          parms.put("deadlineWk", 0);
        } else {
          parms.put("deadlineWk", Integer.valueOf(deadlineWk));
        }
        if (flightWk == null || flightWk.equalsIgnoreCase("")) {
          parms.put("flightWk", 0);
        } else {
          parms.put("flightWk", Integer.valueOf(flightWk));
        }
        int searchMax = formCom0102d04.getSearchMax();
        parms.put("searchMax", Integer.valueOf(searchMax + 1));

        try {
          List<Com0102d04Model> sheinList = com0102d04Dao.getCom0102d04Mapper()
              .getSearchMstSSeihn(parms);

          try {
            jsonData = ow.writeValueAsString(sheinList);
          } catch (JsonProcessingException e) {
            throw e;
          }
        } catch (MyBatisSystemException e) {
          logger.error(e.getMessage());
          throw e;
        }
      } catch (Exception e) {
        List<Com0102d04Model> lstCom0102d04 = null; // SQL結果格納用変数
        // 取得データ変換
        try {
          jsonData = ow.writeValueAsString(lstCom0102d04);
        } catch (JsonProcessingException e1) {
          throw e1;
        }
        return jsonData; // 処理終了
      }
    } else if (saleTypeWk != "") {
      try {
        // 変数宣言
        Map<String, Object> parms = new HashMap<String, Object>();
        // 検索条件セット
        parms.put("ourCompanyItemCodeWk", String.valueOf(ourCompanyItemCodeWk));
        parms.put("ourCompanyItemNameWk", String.valueOf(ourCompanyItemNameWk));
        parms.put("changeCodeWk", changeCodeWk); // チェーンコード
        parms.put("changeBranchWk", changeBranchWk);// チェーン枝番
        parms.put("customerCodeWk", customerCodeWk);
        if (deadlineWk == null || deadlineWk.equalsIgnoreCase("")) {
          parms.put("deadlineWk", 0);
        } else {
          parms.put("deadlineWk", Integer.valueOf(deadlineWk));
        }
        if (flightWk == null || flightWk.equalsIgnoreCase("")) {
          parms.put("flightWk", 0);
        } else {
          parms.put("flightWk", Integer.valueOf(flightWk));
        }
        parms.put("saleTypeWk", Integer.parseInt(saleTypeWk));// チェーン枝番
        int searchMax = formCom0102d04.getSearchMax();
        parms.put("searchMax", Integer.valueOf(searchMax + 1));
        try {
          List<Com0102d04Model> tcuList = com0102d04Dao.getCom0102d04Mapper()
              .getSearchMstCustTanKaSeihn(parms);
          try {
            jsonData = ow.writeValueAsString(tcuList);
          } catch (JsonProcessingException e) {
            throw e;
          }
        } catch (MyBatisSystemException e) {
          throw e;
        }
      } catch (Exception e) {
        List<Com0102d04Model> lstCom0102d04 = null; // SQL結果格納用変数
        // 取得データ変換
        try {
          jsonData = ow.writeValueAsString(lstCom0102d04);
        } catch (JsonProcessingException e1) {
          throw e1;
        }
        return jsonData; // 処理終了
      }
    }
    return jsonData;
  }

  /**
   * getDataInfo.
   * 
   * @param formCom0102d04 formCom0102d04
   * 
   * @return jsonData
   * @throws JsonProcessingException e
   */
  public String getDataInfo(FormCom0102d04 formCom0102d04)
      throws JsonProcessingException {

    String jsonData = ""; // 戻り値

    return jsonData;
  }
}
