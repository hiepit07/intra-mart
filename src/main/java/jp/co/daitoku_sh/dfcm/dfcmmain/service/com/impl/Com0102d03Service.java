/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl
 * ファイル名:Com0102d0３Service
 *
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/０９/２５
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/02 1.00 ABV)グエン　リユオン　ギア　 新規開発
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
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d03;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl.Com0102d03Dao;

/**
 * コントローラーから呼び出される処理
 *
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0102d03Service extends AbsService {

  private Dfcmlogger logger = new Dfcmlogger();
  @Autowired
  @Qualifier("com0102d03Dao")
  private Com0102d03Dao com0102d03Dao;

  // メッセージPropertiesファイルを読む
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
   * initialize 
   * 
   * @param model:model
   * @param request:request
   * @param formCom0102d03:formCom0102d03
   */
  public void initialize(Model model, String custCode, FormCom0102d03 formCom0102d03) {
    try {
      List<MstCustomer> lstCustomer = null;     
      // MST_CUSTOMER
      Map<String, Object> parmCust = new HashMap<String, Object>();
      String date = DateUtil.getSysDate();
      parmCust.put("customerCode", custCode);
      parmCust.put("date", date);
      lstCustomer = com0102d03Dao.getCom0102d03Mapper().getMstCustomerData(
          parmCust);

      model.addAttribute("custList", lstCustomer);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
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
   * 得意先情報を取得する.
   * 
   * @param request:request
   * @return lst
   */
  public List<MstCustomer> getListCustomer(WebRequest request) {
    String paramCustomerCode = request.getParameter("customer_code");
    List<MstCustomer> lst = null;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customerCode", paramCustomerCode);
    try {
      lst = com0102d03Dao.getCom0102d03Mapper().getMstCustomerData(params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    return lst;
  }

  /**
   * 店舗の検索処理.
   * 
   * @param formCom0102d03:formCom0102d03
   * @return jsonData
   * @throws JsonProcessingException:e
   */
  public String searchMstShop(FormCom0102d03 formCom0102d03)
      throws JsonProcessingException {

    String strCustCode = "";
    String jsonData = "";
    String date = "";    
    date = DateUtil.getSysDate();
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter
    String strShopName = "";
  
    // 変数に値を代入
    strCustCode = formCom0102d03.getCustCode();
    String strShopCode = "";
    strShopCode = formCom0102d03.getShopCode();
    strShopName = formCom0102d03.getShopNm();
    int searchMax = 0;
    searchMax = formCom0102d03.getSearchMax();
    if (strShopName.equals("")) {
      strShopName = "";
    } else {
      strShopName = "%" + strShopName + "%";
    }
    
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    
    params.put("searchMax", searchMax + 1);
    params.put("customerCode", (strCustCode));
    params.put("shopCode", (strShopCode));
    params.put("shopName", strShopName);
    params.put("date", date);
    List<MstShop> lstCom0102d03; // 1中空宣言リスト
    ArrayList<MstShop> lstShop = new ArrayList<MstShop>();
    try {
      lstCom0102d03 = com0102d03Dao.getCom0102d03Mapper().getSearchResult(
          params);
     
      // convertSanitize
      String shopCode = "";
      String shopNmR = "";
      for (int i = 0; i < lstCom0102d03.size(); i++) {
        MstShop mstShop = new MstShop();
        shopCode = String.valueOf(lstCom0102d03.get(i).getShopCode());
        mstShop.setShopCode(shopCode);
        shopNmR = String.valueOf(lstCom0102d03.get(i).getShopNmR());
        shopNmR = Util.convertSanitize(shopNmR);
        mstShop.setShopNmR(shopNmR);
        lstShop.add(mstShop);
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    try {
      jsonData = ow.writeValueAsString(lstShop);
    } catch (JsonProcessingException e) {
      throw e;
    }

    //jsondataためのテレビリスト
    return jsonData;
  }

  /**
   * 店舗情報の取得の処理.
   * 
   * @param formCom0102d03:formCom0102d03
   * @return jsonData
   * @throws JsonProcessingException:e
   */
  public String getMstShopInfo(FormCom0102d03 formCom0102d03)
      throws JsonProcessingException {
    //変数を初期化します
    String strCustCode = "";
    String strShopCode = "";
    String jsonData = ""; 
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // ObjectWriter

    // 変数に値を代入
    strCustCode = formCom0102d03.getCustCode();//得意先コードの取得
    strShopCode = formCom0102d03.getShopCode();//店舗コードの取得

    // 宣言リスト
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    params.put("customerCode", (strCustCode));
    params.put("shopCode", (strShopCode));
    List<MstShop> lstCom0102d03; // 1中空宣言リスト
    try {
      lstCom0102d03 = com0102d03Dao.getCom0102d03Mapper().getMstShopInfo(
          params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    //jsondataためのテレビリスト
    try {
      jsonData = ow.writeValueAsString(lstCom0102d03);
    } catch (JsonProcessingException e) {
      throw e;
    }

    // 
    return jsonData;
  }
}
