/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl
 * ファイル名:Sei0106d01Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/08
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fujitsu.systemwalker.outputassist.connector.ConnectorException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.PrintUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl.Sei0106d01Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.PrintSei0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0106d01;

/**
 * サービスクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Sei0106d01Service extends AbsService{
  /** メッセージPropertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  @Autowired
  @Qualifier("sei0106d01Dao")
  private Sei0106d01Dao sei0106d01Dao;
  
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * 事業所情報検索.
   * 
   * @param model A holder for model attributes
   * @param businessDate [HIDDEN]業務日付
   * @return The error message
   */
  public ErrorMessages setSJigyoInfo(Model model, String businessDate) {
    // 事業所情報検索
    List<MstSJigyoInfo> list = getSJigyoInfo(businessDate);
    ArrayList<String> paramMess = new ArrayList<String>();
    String strErrMsg = "";
    ErrorMessages errorMessages = null;
    // データ存在チェック
    if (list == null || list.size() == 0) {
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタ");
      paramMess.add("事業所情報");
      strErrMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errorMessages = new ErrorMessages();
      errorMessages.setMessageContent(strErrMsg);
    } else {
      ObjCombobox element = new ObjCombobox();
      element.setCode("");
      element.setName("");
      ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
      comboboxs.add(element);
      for (MstSJigyoInfo gen : list) {
        element = new ObjCombobox();
        element.setCode(gen.getJgycd());
        element.setName(gen.getJgycd() + " : " + gen.getJgymei());
        comboboxs.add(element);
      }
      model.addAttribute("jigyoCodeList", comboboxs);
    }
    return errorMessages;
  }
  
  /**
   * 締日コンボボックス.
   * 
   * @param model A holder for model attributes
   */
  public void setCloseDateLisCbx(Model model) {
    ObjCombobox element = null;
    ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
    for (int i = 0; i < 32; i++) {
      element = new ObjCombobox();
      if (i == 0) {
        element.setCode("");
        element.setName("");
      } else {
        element.setCode(String.valueOf(i));
        element.setName(String.valueOf(i) + "日");
      }
      comboboxs.add(element);
    }
    model.addAttribute("jgymeiList", comboboxs);
  }
  /**
   * 事業所情報検索.
   * 
   * @param businessDate [HIDDEN]業務日付
   * @return 事業所情報
   */
  private List<MstSJigyoInfo> getSJigyoInfo(String businessDate) {
    Map<String, Object> parms = new HashMap<String, Object>();
    List<MstSJigyoInfo> list = null;
    try {
      parms.put("businessDate", businessDate);
      list = sei0106d01Dao.getSei0106d01Mapper().getSJigyoInfo(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return list;
  }
  
  /**
   * 業務日付取得.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);
    return sysCom.getAplDate();
  }
  
  /**
   * 入力チェックの内容.
   * 
   * @param formSei0106d01 The controller object
   * @return true if the items are valid; false otherwise
   */
  public boolean checkInputContent(FormSei0106d01 formSei0106d01) {
    
    String errorMsg = "";
    ArrayList<String> paramMess = null;
    String errItemIds = "";
    // 請求月
    String txtBildDate = formSei0106d01.getTxtBildDate().replace("/", "");
    String errorCode = Util.checkItem(txtBildDate, true,InputCheckCom.TYPE_NUM, 6);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("請求月");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "txtBildDate" + MstConst.DELIMITER_ERROR;
    }
    
    // 事業所
    String ddlJigyoCode = formSei0106d01.getDdlJigyoCode();
    errorCode = Util.checkItem(ddlJigyoCode, true,InputCheckCom.TYPE_NUM, 2);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("事業所コード");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "ddlJigyoCode" + MstConst.DELIMITER_ERROR;
    }
    
    // 事務担当者
    String txtJgycd = formSei0106d01.getTxtJgycd();
    errorCode = Util.checkItem(txtJgycd, false,InputCheckCom.TYPE_NUM, 8);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("事務担当者");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "txtJgycd" + MstConst.DELIMITER_ERROR;
    }
    
    // 締日
    String ddlJgymei = formSei0106d01.getDdlJgymei();
    errorCode = Util.checkItem(ddlJgymei, false,InputCheckCom.TYPE_NUM, 2);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("締日");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "ddlJgymei" + MstConst.DELIMITER_ERROR;
    }
    
    // 請求先コード
    String txtUserCode = formSei0106d01.getTxtUserCode();
    errorCode = Util.checkItem(txtUserCode, false,InputCheckCom.TYPE_NUM, 7);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("締日");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "txtUserCode" + MstConst.DELIMITER_ERROR;
    }
    // [画面]請求月から「/」を除去し、変数に格納
    errorCode = InputCheckCom.chkDate(txtBildDate, CommonConst.DATE_FORMAT_YM);
    if (errorCode != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("請求月");
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
      errItemIds += "txtBildDate" + MstConst.DELIMITER_ERROR;

    }
    
    // [画面]締日が空でない場合、以下を行う
    ddlJgymei = formSei0106d01.getDdlJgymei();
    if (ddlJgymei != null && !ddlJgymei.equalsIgnoreCase("")) {
      String bildCloseDate = txtBildDate + String.format("%02d", Integer.parseInt(ddlJgymei));
      // 締日
      errorCode = InputCheckCom.chkDate(bildCloseDate, CommonConst.DATE_FORMAT_YMD);
      if (errorCode != null) {
        paramMess = new ArrayList<String>();
        paramMess.add("締日");
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMess) + "<br>";
        errItemIds += "ddlJgymei" + MstConst.DELIMITER_ERROR;
      }
    }
    
    CommonGetData commonGetData = new CommonGetData(commonDao, readPropertiesFileService);
    // 共通部品を使って、担当者氏名を取得する
    txtJgycd = formSei0106d01.getTxtJgycd();
    String sysAdminFlg = formSei0106d01.getSysAdminFlag();
    String jigyoshoCode = sysAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID) 
        ? formSei0106d01.getDdlJigyoCode() : formSei0106d01.getLoginJigyouShoCode();
    UserData userData = commonGetData.getUserData(txtJgycd, jigyoshoCode);
    // 戻り値チェック
    if (userData.getMsgCd() != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタ");
      paramMess.add("担当者氏名");
      errorMsg += readPropertiesFileService.getMessage(userData.getMsgCd(), paramMess) + "<br>";
      errItemIds += "txtJgycd" + MstConst.DELIMITER_ERROR;
    }
    
    // 共通部品を使って、得意先情報を取得する
    txtUserCode = formSei0106d01.getTxtUserCode();
    CustomerData customerData = commonGetData.getCustomerData(txtUserCode, jigyoshoCode, 
        CommonConst.GET_CUSTOMER_DATA_BILLING_TARGET);
    // 戻り値チェック
    if (customerData.getMsgCd() != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("得意先マスタ");
      paramMess.add("請求先情報");
      errorMsg += readPropertiesFileService.getMessage(customerData.getMsgCd(), paramMess) + "<br>";
      errItemIds += "txtUserCode" + MstConst.DELIMITER_ERROR;
    }
    
    if (!errItemIds.equalsIgnoreCase("") || !errorMsg.equalsIgnoreCase("")) {
      formSei0106d01.setErrItemIds(errItemIds);
      formSei0106d01.setErrMessage(errorMsg);
      return false;
    }
    
    return true;
  }
  
  /**
   * 請求先情報取得.
   * 
   * @param formSei0106d01 The controller object
   */
  public void getBillInfo(FormSei0106d01 formSei0106d01) {
    // 入力情報格納
    String closeDateWk = formSei0106d01.getDdlJgymei();
    String bildCloseDateWk = formSei0106d01.getTxtBildDate().replace("/", "");
    if (closeDateWk != null && !closeDateWk.equalsIgnoreCase("")) {
      bildCloseDateWk += String.format("%02d", Integer.parseInt(closeDateWk));
    }
    String jigyoCodeWk = formSei0106d01.getDdlJigyoCode();
    String jgycdWk = formSei0106d01.getTxtJgycd();
    String seiKyusakiCodeWk = formSei0106d01.getTxtUserCode();
    Map<String, Object> parms = new HashMap<String, Object>();
    String searchMax = "TOP " + String.valueOf(formSei0106d01.getSearchMax() + 1);
    parms.put("Search_Max", searchMax);
    parms.put("BIZ_DATE", formSei0106d01.getBusinessDate());
    parms.put("JIGYOSHO_CODE", jigyoCodeWk);
    
    parms.put("closeDateWk", closeDateWk);
    parms.put("SEIKYU_SHIMEBI", bildCloseDateWk);
    parms.put("SEIKYU_SHIMEBI_LIKE", bildCloseDateWk + "%");

    parms.put("SEIKYUSAKI_CODE", seiKyusakiCodeWk);
    parms.put("JIMU_TANTOSHA_CODE", jgycdWk);
    ArrayList<RstSei0106d01> billInfoList = null;
    try {
      billInfoList = sei0106d01Dao.getSei0106d01Mapper().getBillInfo(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    
    if (billInfoList != null && billInfoList.size() > 0) {
      ArrayList<String> paramMess  = null;
      formSei0106d01.setBillInfoList(billInfoList);
      if (billInfoList.size() >= formSei0106d01.getSearchMax()) {
        paramMess = new ArrayList<String>();
        paramMess.add(String.valueOf(formSei0106d01.getSearchMax()));
        formSei0106d01.setErrMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        formSei0106d01.setMessageType("searchMax");
      }
    } else {
      // 警告メッセージを画面に表示する
      String errMessage = readPropertiesFileService.getMessage("COM025-E", null);
      formSei0106d01.setErrMessage(errMessage);
      formSei0106d01.setMessageType("body");
    }
  }
  
  /**
   * 共通部品から検索上限値を取得する.
   * 
   * @return 検索上限値
   */
  public int getSearchMax() {
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        commonDao, null, null, null);
    return commonGetSystemCom.getCodeSearchMax();
  }
  
  /**
   * 帳票管理マスタ取得.
   * 
   * @param outFormatBillCustFlg 出力形式_請求先フラグ
   * @param outFormatCustShopFlg 出力形式（得意先／店舗毎）
   * @param outFormatDenpyoFlg 出力形式（伝票毎）
   * @return 帳票定義マスタ
   */
  public ArrayList<MstListForm> getListForm(boolean outFormatBillCustFlg,
      boolean outFormatCustShopFlg, boolean outFormatDenpyoFlg) {
    
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("outFormatBillCustFlg", outFormatBillCustFlg);
    parms.put("outFormatCustShopFlg", outFormatCustShopFlg);
    parms.put("outFormatDenpyoFlg", outFormatDenpyoFlg);
    ArrayList<MstListForm> mstListForms = null;
    try {
      mstListForms = sei0106d01Dao.getSei0106d01Mapper().getMstListForm(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return mstListForms;
  }
  /**
   * 出力メイン.
   * @param formSei0106d01 The controller object
   * @param billInfoList 請求先情報リスト
   * @param outFormatBillCustFlg 【関数】請求一覧表（請求先）出力
   * @param outFormatCustShopFlg 出力形式_得意先店舗フラグ
   * @param outFormatDenpyoFlg 出力形式_伝票フラグ
   * @param nmWk 帳票名
   * @param dirWk 格納ディレクトリ
   * @param igyoCode 事業所コード
   */
  public void outputMain(FormSei0106d01 formSei0106d01, ArrayList<RstSei0106d01> billInfoList,
      boolean outFormatBillCustFlg, boolean outFormatCustShopFlg,
      boolean outFormatDenpyoFlg, String dirWk, String nmWk, String igyoCode) {
    String errorContent = null;
    if (outFormatBillCustFlg) {
      errorContent = outputBillCust(billInfoList, dirWk, nmWk , igyoCode);
    } else if (outFormatCustShopFlg) {
      errorContent = outputCustShop(billInfoList, dirWk, nmWk , igyoCode);
    } else if (outFormatDenpyoFlg) {
      errorContent = outputDenpyo(billInfoList, dirWk, nmWk ,igyoCode);
    }
    formSei0106d01.setErrMessage(errorContent);
  }
  
  /**
   * 【関数】請求一覧表（伝票毎）出力.
   * .
   * @param billInfoList 請求先情報リスト
   * @param dirWk 格納ディレクトリ
   * @param nmWk 帳票名
   * @param igyoCode 事業所コード
   */
  private String outputDenpyo(ArrayList<RstSei0106d01> billInfoList, String dirWk,
      String nmWk, String igyoCode) {
    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;
    dirWk = readPropertiesFileService.getSetting(
        "LIST_DAT_OUT_TEMP") + "\\" + dirWk;
    // ファイルパス
    String tsvFilePath = dirWk + "\\" + SeiConst.CHOHYO_ID_DENPYO_SEIKYU_ICHIRAN
        + "_"
        + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
        + ".tsv";
    // 納品明細書ハンドラ
    PrintResultHandler handler = new PrintResultHandler();
    try {
      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          tsvFilePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);
      handler.tsvPrinter = tsvPrinter;
      handler.outputFormat  = SeiConst.OUTPUT_FORMAT_DENPYOFLAG;
      RstSei0106d01 row = null;
      Map<String, Object> parms = new HashMap<String, Object>();
      for (int i = 0; i < billInfoList.size(); i++) {
        row = billInfoList.get(i);
        handler.billInfo = row;
        parms.clear();
        parms.put("SEIKYU_ID",row.getBildId()); 
        sei0106d01Dao.getSei0106d01Mapper().getDenpyo(parms, handler);
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }
        if (tsvPrinter != null) {
          tsvPrinter.close();
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (handler.recordCount > 0) {
      PrintUtil printUtil = new PrintUtil(commonDao);
      File dataFile = new File(tsvFilePath);
      // 出力指示
      try {
        String rtnCode = printUtil.exePrint(SeiConst.CHOHYO_ID_DENPYO_SEIKYU_ICHIRAN,
            SeiConst.CHOHYO_HAKKO_SOKUJI,
            igyoCode, nmWk, dataFile);
        if (rtnCode != null && !rtnCode.equalsIgnoreCase("")) {
          return readPropertiesFileService.getMessage(
              rtnCode, null);
        }
      } catch (ConnectorException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
      }
    }
    return null;
  }
  
  /**
   * 【関数】請求一覧表（得意先・店舗毎）出力.
   * .
   * @param billInfoList 請求先情報リスト
   * @param dirWk 格納ディレクトリ
   * @param nmWk 帳票名
   * @param igyoCode 事業所コード
   */
  private String outputCustShop(ArrayList<RstSei0106d01> billInfoList,
      String dirWk, String nmWk, String igyoCode) {
    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;
    dirWk = readPropertiesFileService.getSetting(
        "LIST_DAT_OUT_TEMP") + "\\" + dirWk;
    // ファイルパス
    String tsvFilePath = dirWk + "\\" + SeiConst.CHOHYO_ID_CUST_SHOP_SEIKYU_ICHIRAN
        + "_"
        + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
        + ".tsv";
    // 納品明細書ハンドラ
    PrintResultHandler handler = new PrintResultHandler();
    try {
      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          tsvFilePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);
      handler.tsvPrinter = tsvPrinter;
      handler.outputFormat  = SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG;
      RstSei0106d01 row = null;
      Map<String, Object> parms = new HashMap<String, Object>();
      for (int i = 0; i < billInfoList.size(); i++) {
        row = billInfoList.get(i);
        handler.billInfo = row;
        parms.clear();
        parms.put("SEIKYU_ID",row.getBildId()); 
        sei0106d01Dao.getSei0106d01Mapper().getCustShop(parms, handler);
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }
        if (tsvPrinter != null) {
          tsvPrinter.close();
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (handler.recordCount > 0) {
      PrintUtil printUtil = new PrintUtil(commonDao);
      File dataFile = new File(tsvFilePath);
      try {
        String rtnCode = printUtil.exePrint(SeiConst.CHOHYO_ID_CUST_SHOP_SEIKYU_ICHIRAN,
            SeiConst.CHOHYO_HAKKO_SOKUJI, igyoCode, nmWk, dataFile);
        if (rtnCode != null && !rtnCode.equalsIgnoreCase("")) {
          return readPropertiesFileService.getMessage(
              rtnCode, null);
        }
      } catch (ConnectorException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
      }
    }
    return null;
  }

  /**
   * 【関数】請求一覧表（請求先）出力
   * .
   * @param billInfoList 請求先情報リスト
   * @param dirWk 格納ディレクトリ 
   * @param nmWk 帳票名
   * @param igyoCode 事業所コード
   */
  private String outputBillCust(ArrayList<RstSei0106d01> billInfoList, String dirWk,
      String nmWk, String igyoCode) {
    OutputStreamWriter outputStreamWriter = null;
    CSVPrinter tsvPrinter = null;
    dirWk = readPropertiesFileService.getSetting(
        "LIST_DAT_OUT_TEMP") + "\\" + dirWk;
    // ファイルパス
    String tsvFilePath = dirWk + "\\" + SeiConst.CHOHYO_ID_BILL_SEIKYU_ICHIRAN
        + "_"
        + DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS")
        + ".tsv";
    // 納品明細書ハンドラ
    try {
      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
          tsvFilePath), "UTF-8");
      outputStreamWriter.write("\uFEFF");
      CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
          .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR);
      tsvPrinter = new CSVPrinter(outputStreamWriter, tsvFileFormat);
      RstSei0106d01 row = null;
      for (int i = 0; i < billInfoList.size(); i++) {
        row = billInfoList.get(i);
        // ヘッダー
        tsvPrinter.print(Util.convertUnsanitize(getYearMonth(row.getBildDate())));
        tsvPrinter.print(Util.convertUnsanitize(row.getJgymei()));
        
        // 明細
        tsvPrinter.print(getDay(row.getBildDate()));
        tsvPrinter.print(Util.convertUnsanitize(row.getUserCode()));
        tsvPrinter.print(Util.convertUnsanitize(row.getBildNmR()));
        
        tsvPrinter.print(row.getTaxAmount());
        tsvPrinter.print(row.getSaleTax());
        tsvPrinter.print(row.getIncTaxAmount());
        tsvPrinter.print(row.getBillAmount());
        tsvPrinter.println();
      }

    } catch (IOException ex) {
      logger.error(ex.getMessage());
      ex.printStackTrace();
    } finally {
      try {
        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }
        if (tsvPrinter != null) {
          tsvPrinter.close();
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (billInfoList.size() > 0) {
      PrintUtil printUtil = new PrintUtil(commonDao);
      File dataFile = new File(tsvFilePath);
      // 出力指示
      try {
        String rtnCode = printUtil.exePrint(SeiConst.CHOHYO_ID_BILL_SEIKYU_ICHIRAN,
            SeiConst.CHOHYO_HAKKO_SOKUJI,
            igyoCode, nmWk, dataFile);
        if (rtnCode != null && !rtnCode.equalsIgnoreCase("")) {
          return readPropertiesFileService.getMessage(
              rtnCode, null);
        }
     
      } catch (ConnectorException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
      }
    }
    return null;
  }
  
  /**
   * CSV出力データ作成
   * 
   * @param formSei0106d01 The controller object.
   * @param outFormatBillCustFlg 【関数】請求一覧表（請求先）出力
   * @param outFormatCustShopFlg 出力形式_得意先店舗フラグ
   * @param outFormatDenpyoFlg 出力形式_伝票フラグ
   * @throws Exception エラー画面
   */
  public void exportCsv(FormSei0106d01 formSei0106d01,
      boolean outFormatBillCustFlg, boolean outFormatCustShopFlg,
      boolean outFormatDenpyoFlg) throws Exception {
    String filePath = null;
    if (outFormatBillCustFlg) {
      filePath = exportBillCust(formSei0106d01);
      formSei0106d01.setFilePath(filePath);
    } else if (outFormatCustShopFlg) {
      filePath = exportCustShop(formSei0106d01);
      formSei0106d01.setFilePath(filePath);
    } else if (outFormatDenpyoFlg) {
      filePath = exportDenpyo(formSei0106d01);
      formSei0106d01.setFilePath(filePath);
    }
  }
  
  /**
   * （一覧画面）CSVボタン
   * 
   * @param formSei0106d01 The controller object
   * @return The CSV file's path.
   * @throws Exception エラー画面
   */
  private String exportDenpyo(FormSei0106d01 formSei0106d01) throws Exception {
    String currentTime = DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS");
    // ファイル名
    String pathFile = SeiConst.CHOHYO_ID_DENPYO_SEIKYU_ICHIRAN + "_" + currentTime + ".csv";
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
   
    Object[] fileHeader = {
        "No",
        "[ヘッダ]請求締日",
        "[ヘッダ]請求先",
        "[ヘッダ]得意先",
        "[ヘッダ]店舗",
        "[ヘッダ]事業所",
        "[明細]出荷日",
        "[明細]店名",
        "[明細]請求金額"
    };
    
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    csvFilePrinter.printRecord(fileHeader);
    
    ArrayList<RstSei0106d01> billInfoList = formSei0106d01.getBillInfoList();
    SeqProc seqProc = null;
    try {
      RstSei0106d01 row = null;
      Map<String, Object> parms = new HashMap<String, Object>();;
      for (int i = 0; i < billInfoList.size(); i++) {
        row = billInfoList.get(i);
        parms.clear();
        parms.put("SEIKYU_ID",row.getBildId());
        seqProc = new SeqProc(csvFilePrinter, row ,SeiConst.OUTPUT_FORMAT_DENPYOFLAG);
        sei0106d01Dao.getSei0106d01Mapper().getDenpyo(parms, seqProc);
      }
     
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      fileWriter.flush();
      fileWriter.close();
      csvFilePrinter.close();
    }
    
    if (seqProc.isEmpty) {
      File file = new File(folderFile);
      if (file.exists() && !file.isDirectory()) { 
        file.delete();
      }
      return null;
    }
    
    String urlCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD") + pathFile;
    return urlCsv;
  }

  /**
   * （一覧画面）CSVボタン
   * 
   * @param formSei0106d01 The controller object
   * @return The CSV file's path.
   * @throws Exception No comment
   */
  private String exportBillCust(FormSei0106d01 formSei0106d01) throws Exception {
    String currentTime = DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS");
    // ファイル名
    String pathFile = SeiConst.CHOHYO_ID_BILL_SEIKYU_ICHIRAN + "_" + currentTime + ".csv";
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    Object[] fileHeader = {
        "No",
        "カラム1",
        "カラム2",
        "カラム3",
        "カラム4",
        "カラム5",
        "カラム6",
        "カラム7",
        "カラム8",
        "カラム9"
    };
    ArrayList<RstSei0106d01> billInfoList = formSei0106d01.getBillInfoList();
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    csvFilePrinter.printRecord(fileHeader);
    RstSei0106d01 row = null;
    for (int i = 0; i < billInfoList.size(); i++) {
      row = billInfoList.get(i);
      csvFilePrinter.print(i + 1);
      csvFilePrinter.print(Util.convertUnsanitize(row.getBildDate()));
      csvFilePrinter.print(Util.convertUnsanitize(row.getJgymei()));
      csvFilePrinter.print(Util.convertUnsanitize(row.getUserNm()));
      csvFilePrinter.print(Util.convertUnsanitize(row.getUserCode()));
      csvFilePrinter.print(Util.convertUnsanitize(row.getBildNmR()));
      csvFilePrinter.print(row.getTaxAmount());
      csvFilePrinter.print(row.getSaleTax());
      csvFilePrinter.print(row.getIncTaxAmount());
      csvFilePrinter.print(row.getBillAmount());
      csvFilePrinter.println();
    }

    fileWriter.flush();
    fileWriter.close();
    csvFilePrinter.close();
    String urlCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD")
        + pathFile;
    return urlCsv;
  }
  
  /**
   * （一覧画面）CSVボタン
   * 
   * @param formSei0106d01 The controller object
   * @return The CSV file's path.
   * @throws Exception エラー画面
   */
  private String exportCustShop(FormSei0106d01 formSei0106d01) throws Exception {
    String currentTime = DateUtil.setFormat(new java.util.Date(), "yyyyMMddHHmmssSSS");
    // ファイル名
    String pathFile = SeiConst.CHOHYO_ID_CUST_SHOP_SEIKYU_ICHIRAN + "_" + currentTime + ".csv";
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
   
    Object[] fileHeader = {
        "No",
        "[ヘッダ]請求締日",
        "[ヘッダ]請求先",
        "[ヘッダ]得意先",
        "[ヘッダ]事業所",
        "[明細]店舗コード",
        "[明細]店名",
        "[明細]請求金額",
        "[明細]枚数"
    };
    
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    csvFilePrinter.printRecord(fileHeader);
    
    ArrayList<RstSei0106d01> billInfoList = formSei0106d01.getBillInfoList();
    SeqProc seqProc = null;
    try {
      RstSei0106d01 row = null;
      Map<String, Object> parms = new HashMap<String, Object>();;
      for (int i = 0; i < billInfoList.size(); i++) {
        row = billInfoList.get(i);
        parms.clear();
        parms.put("SEIKYU_ID",row.getBildId());
        seqProc = new SeqProc(csvFilePrinter, row ,SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG);
        sei0106d01Dao.getSei0106d01Mapper().getCustShop(parms, seqProc);
      }
     
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      fileWriter.flush();
      fileWriter.close();
      csvFilePrinter.close();
    }
    
    if (seqProc.isEmpty) {
      File file = new File(folderFile);
      if (file.exists() && !file.isDirectory()) { 
        file.delete();
      }
      return null;
    }
    
    String urlCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD") + pathFile;
    return urlCsv;
  }
  
  /**
   *  「YYYYMMDD」形式を「YYYY」年「MM」月形式に変換
   *  
   * @param yyyyMMdd date
   * @return 「YYYY」年「MM」月
   */
  private String getYearMonth(String yyyyMMdd) {
    return yyyyMMdd.substring(0, 4) + "年" + yyyyMMdd.substring(4, 6) + "月";
  }
  
  /**
   * 「YYYYMMDD」形式を「DD」形式に変換 
   * @param yyyyMMdd date
   * @return 「DD」
   */
  private String getDay(String yyyyMMdd) {
    return yyyyMMdd.substring(6, 8);
  }
  
  /**
   * Defaultメッセージの取得.
   * 
   * @param model A holder for model attributes
   */
  public void getDefaultMess(Model model) {
    ArrayList<DefaultMessages> defaultMsgLst = null;
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsgLst = new ArrayList<DefaultMessages>();;
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", null));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  
  /**
   * SQL実行結果を1レコードずつ処理するための内部クラス
   *
   * @author QuanTran
   * @version 1.00
   * @since BSS 1.0.0
   *
   */
  private class SeqProc implements ResultHandler {
    /** 連番. */
    private int intRenban;

    /** CSVプリンタ */
    private CSVPrinter printer;

    /** 処理結果が空 */
    private boolean isEmpty;
    
    private String outputFormatCustshopflag;
    
    private RstSei0106d01 billInfo;

    /**
     * コンストラクタ
     *
     * @param nengetsudo 年月
     * @param printer CSVプリンタ
     * @param row Model object
     * @param outputFormatCustshopflag The output format flag
     */
    public SeqProc(CSVPrinter printer, RstSei0106d01 billInfo, String outputFormatCustshopflag) {
      this.billInfo = billInfo;
      this.printer = printer;
      this.setEmpty(true);
      this.outputFormatCustshopflag = outputFormatCustshopflag;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する
     *
     * @param context リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      intRenban++;
      this.setEmpty(false);

      PrintSei0106d01 printSei0106d01 = (PrintSei0106d01) context.getResultObject();
      try {
        if (this.outputFormatCustshopflag.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG)) {
          printer.print(intRenban);
          printer.print(Util.convertUnsanitize(billInfo.getBildDate()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getBildNmR()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getCustNmR()));
          printer.print(Util.convertUnsanitize(billInfo.getJgymei()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getShopCode()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getShopNmR()));
          printer.print(printSei0106d01.getTrdPrice());
          printer.print(Util.convertUnsanitize(printSei0106d01.getDenNo()));
        } else {
          printer.print(intRenban);
          printer.print(Util.convertUnsanitize(billInfo.getBildDate()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getBildNmR()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getCustNmR()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getShopNmR()));
          printer.print(Util.convertUnsanitize(billInfo.getJgymei()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getDeliDate()));
          printer.print(Util.convertUnsanitize(printSei0106d01.getDenNo()));
          printer.print(printSei0106d01.getTrdPrice());
        }
        // 次のレコード
        printer.println();
      } catch (IOException ioe) {
        logger.error(ioe.getMessage());
        throw new RuntimeException(ioe);
      }
    }
   
    public void setEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
    }
  }
  
//*************************************************************************
 // **
 // ** INNER CLASS
 // **
 // ************************************************************************/

 /**
  * ResultHandler Class.
  * 
  * @author QuanTran
  * @version 1.0.0
  * @since 1.0.0
  */
 private class PrintResultHandler implements ResultHandler {

   private int recordCount;
   private CSVPrinter tsvPrinter;
   private RstSei0106d01 billInfo;
   private String outputFormat;
   /**
    * 逐次処理.
    */
   @Override
   public void handleResult(ResultContext context) {

     // 納品明細情報
     PrintSei0106d01 printSei0106d01 = (PrintSei0106d01) context.getResultObject();
     try {
       // レコード数カウントアップ
       recordCount++;
       if (outputFormat != null 
           && outputFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG)) {
         tsvPrinter.print(Util.convertUnsanitize(billInfo.getBildDate()));
          if (billInfo.getCustCode().equalsIgnoreCase(printSei0106d01
              .getCustCode())) {
            tsvPrinter.print("");
          } else {
            tsvPrinter.print(Util.convertUnsanitize(printSei0106d01
                .getBildNmR()));
          }
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getCustNmR()));
         tsvPrinter.print(Util.convertUnsanitize(billInfo.getJgymei()));
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getShopCode()));
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getShopNmR()));
         tsvPrinter.print(printSei0106d01.getTrdPrice());
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getDenNo()));
       } else if (outputFormat != null 
           &&  outputFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_DENPYOFLAG)) {
         tsvPrinter.print(Util.convertUnsanitize(billInfo.getBildDate()));
          if (billInfo.getCustCode().equalsIgnoreCase(printSei0106d01
              .getCustCode())) {
            tsvPrinter.print("");
          } else {
            tsvPrinter.print(Util.convertUnsanitize(printSei0106d01
                .getBildNmR()));
          }
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getCustNmR()));
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getShopNmR()));
         tsvPrinter.print(Util.convertUnsanitize(billInfo.getJgymei()));
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getDeliDate()));
         tsvPrinter.print(Util.convertUnsanitize(printSei0106d01.getDenNo()));
         tsvPrinter.print(printSei0106d01.getTrdPrice());
       }
       tsvPrinter.println();

     } catch (IOException ex) {
       ex.printStackTrace();
       logger.error(ex.getMessage());
       throw new RuntimeException(ex);
     }
   }
 }
}
