/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0105d00Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.OlCustConvMasterData;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0105d00Dao;

/**
 * Service layer.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0105d01Service extends AbsService {
  
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("mst0105d00Dao")
  private Mst0105d00Dao mst0105d00Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;
  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param formMst0105d01 The controller object via modelAttribute.
   */
  public void getOlCustConvMasterData(FormMst0105d01 formMst0105d01) {
    ArrayList<String> paramMess = null;
    String errMessage = "";
    // オンライン得意先変換マスタデータを取得する。
    ArrayList<OlCustConvMasterData> olCustConvMasterDatas = getOlCustConvMasterDataList(
        formMst0105d01, MstConst.PROCESS_TYPE_LIST);
    if (olCustConvMasterDatas != null && olCustConvMasterDatas.size() > 0) {
      formMst0105d01.setConvMasterInfos(olCustConvMasterDatas);
      setHaitaDate(formMst0105d01);
      if (olCustConvMasterDatas.size() >= formMst0105d01.getSearchMax()) {
        paramMess = new ArrayList<String>();
        paramMess.add(String.valueOf(formMst0105d01.getSearchMax()));
        formMst0105d01.setErrMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        formMst0105d01.setMessageType("searchMax");
      }
    } else {
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("有効な管理区分");
      errMessage = readPropertiesFileService.getMessage("COM025-E", paramMess);
      formMst0105d01.setErrMessage(errMessage);
      formMst0105d01.setMessageType("body");
    }
  }

  /**
   * オンラインセンターコード
   * 
   * @param formMst0105d01 The controller object
   * @return 得意先名称を
   */
  private boolean getCustNmR(FormMst0105d01 formMst0105d01 , boolean isCsv) {
    // [変数]関連マスタエラー発生フラグ
    boolean relationMasterErrorFlag = false;
    if (!formMst0105d01.getTxtOnlineCenterCode().equalsIgnoreCase(CommonConst.EMPTY)) {
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("Close_Date", formMst0105d01.getBusinessDate());
      parms.put("OL_Center_Code", formMst0105d01.getTxtOnlineCenterCode());
      ArrayList<MstCustomer> custNmRs = null;
      try {
        if (isCsv) {
          custNmRs = mst0105d00Dao.getMst0105d00Mapper().getCustNmRForCsv(parms);
        } else {
          custNmRs = mst0105d00Dao.getMst0105d00Mapper().getCustNmR(parms);
        }
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
      
      MstCustomer customer = null;
      // 取得結果に応じて後続の処理を行う。
      if (custNmRs != null && custNmRs.size() == 1) {
        customer = custNmRs.get(0);
        if (customer.getCustNmR() == null) {
          formMst0105d01.setLblCustomerNameR01(customer.getCustCode());
        } else {
          formMst0105d01.setLblCustomerNameR01(customer.getCustNmR());
        }
      } else if (custNmRs != null && custNmRs.size() > 1) {
        customer = custNmRs.get(0);
        if (customer.getCustNmR() == null) {
          formMst0105d01.setLblCustomerNameR01(customer.getCustCode() + " " + "他");
        } else {
          formMst0105d01.setLblCustomerNameR01(customer.getCustNmR() + " " + "他");
        }
      } else {
        formMst0105d01.setLblCustomerNameR01("");
        relationMasterErrorFlag = true;
      }
    }
    return relationMasterErrorFlag;
  }
  
  /**
   * オンライン得意先変換マスタ一覧データ取得処理.
   * 
   * @param formMst0105d01 The controller object
   * @return オンライン得意先変換マスタ一覧表示
   */
  private ArrayList<OlCustConvMasterData> getOlCustConvMasterDataList(
      FormMst0105d01 formMst0105d01, int processDivision) {
    Map<String, Object> parms = new HashMap<String, Object>();
    String searchMax = "TOP " + String.valueOf(formMst0105d01.getSearchMax() + 1);
    parms.put("Search_Max", searchMax);
    parms.put("Close_Date", formMst0105d01.getBusinessDate());
    parms.put("OL_Center_Code", formMst0105d01.getTxtOnlineCenterCode());
    parms.put("OL_Torihiki_Code", formMst0105d01.getTxtOnlineTorihikiCode());
    parms.put("AT_Torihiki_Code", formMst0105d01.getTxtAtTorihikiCode());
    parms.put("chkCancellationData", formMst0105d01.isChkCancellationData());
    ArrayList<OlCustConvMasterData> list = null;
    // 処理区分 = 1 （一覧表示の場合）
    if (processDivision == MstConst.PROCESS_TYPE_LIST) {
      try {
        list = mst0105d00Dao.getMst0105d00Mapper().getOlCustConvMasterDataList(parms);
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
    }
    return list;
  }
  
  /**
   * Get the business date.
   * 
   * @param formMst0105d01 The controller object via modelAttribute
   * @return The error message on the screen
   */
  public ErrorMessages getBusinessDate(FormMst0105d01 formMst0105d01) {
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, txManager, appContext,
        readPropertiesFileService);
    ErrorMessages errMess = null;
    // 業務日付取得
    String businessDate = systemCom.getAplDate();
    // 結果判定
    if (businessDate == null) {
      ArrayList<String> paramMess = new ArrayList<String>();
      errMess = new ErrorMessages();
      // 画面にエラーメッセージを表示する。
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E", paramMess));
    } else {
      formMst0105d01.setBusinessDate(businessDate);
    }
    return errMess;
  }
  
  /**
   *  画面項目初期化
   * @param formMst0105d01 The controller object
   * @param model A holder for model attributes.
   */
  public void initializeScreenItems(FormMst0105d01 formMst0105d01, Model model) {
    // 1-1.以下の内容で初期化する。
    // ヘッダ部①
    formMst0105d01.setTxtOnlineCenterCode(CommonConst.EMPTY);
    model.addAttribute("lblCustomerNameR01", CommonConst.EMPTY);
    formMst0105d01.setTxtOnlineTorihikiCode(CommonConst.EMPTY);
    formMst0105d01.setTxtAtTorihikiCode(CommonConst.EMPTY);
    model.addAttribute("lblCustomerNameR02", CommonConst.EMPTY);
    formMst0105d01.setChkCancellationData(false);
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        commonDao, null, null, null);
    int searchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0105d01.setSearchMax(searchMax);
  }
  
  /**
   * （一覧画面）検索ボタン 
   * 
   * @param formMst0105d01 The controller object
   */
  public void search(FormMst0105d01 formMst0105d01) {
    String errorItemId = "";
    // オンラインセンターコード
    boolean relationMasterErrorFlag = getCustNmR(formMst0105d01, false);
    if (relationMasterErrorFlag) {
      errorItemId += "txtOnlineCenterCode" + MstConst.DELIMITER_ERROR;
    }
    
    //  相手取引先コード
    relationMasterErrorFlag = getPartnerCustCode(formMst0105d01 , false);
    // フォーカスセット
    if (relationMasterErrorFlag) {
      errorItemId += "txtOnlineTorihikiCode" + MstConst.DELIMITER_ERROR;
      errorItemId += "txtAtTorihikiCode" + MstConst.DELIMITER_ERROR;
    } else {
      // オンライン得意先変換マスタ一覧取得
      getOlCustConvMasterData(formMst0105d01);
    }
    formMst0105d01.setErrorItemId(errorItemId);
  }
  
  /**
   * Get 相手取引先コード.
   * 
   * @param formMst0105d01 The controller object
   * @return The controller object
   */
  private boolean getPartnerCustCode(FormMst0105d01 formMst0105d01 , boolean isCsv) {
    boolean relationMasterErrorFlag = false;
    if (!formMst0105d01.getTxtOnlineCenterCode().equalsIgnoreCase(CommonConst.EMPTY)
        && !formMst0105d01.getTxtAtTorihikiCode().equalsIgnoreCase(CommonConst.EMPTY)) {
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("Close_Date", formMst0105d01.getBusinessDate());
      parms.put("OL_Center_Code", formMst0105d01.getTxtOnlineCenterCode());
      parms.put("AT_Torihiki_Code", formMst0105d01.getTxtAtTorihikiCode());
      parms.put("OL_Torihiki_Code", formMst0105d01.getTxtOnlineTorihikiCode());
      ArrayList<MstCustomer> custNmRs = null;
      try {
        if (isCsv) {
          custNmRs = mst0105d00Dao.getMst0105d00Mapper().getPartnerCustCodeForCsv(parms);
        } else {
          custNmRs = mst0105d00Dao.getMst0105d00Mapper().getPartnerCustCode(parms);
        }
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
      
      MstCustomer customer = null;
      // 取得結果に応じて後続の処理を行う。
      if (custNmRs != null && custNmRs.size() == 1) {
        customer = custNmRs.get(0);
        if (customer.getCustNmR() == null) {
          formMst0105d01.setLblCustomerNameR02(customer.getCustCode());
        } else {
          formMst0105d01.setLblCustomerNameR02(customer.getCustNmR());
        }
      } else if (custNmRs != null && custNmRs.size() > 1) {
        customer = custNmRs.get(0);
        if (customer.getCustNmR() == null) {
          formMst0105d01.setLblCustomerNameR02(customer.getCustCode() + " " + "他");
        } else {
          formMst0105d01.setLblCustomerNameR02(customer.getCustNmR() + " " + "他");
        }
      } else {
        formMst0105d01.setLblCustomerNameR02("");
        relationMasterErrorFlag = true;
      }
    }
    return relationMasterErrorFlag;
  }
  
  /**
   * Defaultメッセージの取得.
   * 
   * @param model A holder for model attributes
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = null;
    ArrayList<DefaultMessages> defaultMsgLst = null;
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsgLst = new ArrayList<DefaultMessages>();
    paramMess = new ArrayList<String>();
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
   
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM001-E", null));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM016-E", null));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM006-E", null));
    defaultMsgLst.add(defaultMsg);
    
    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  
  /**
   * No comment
   * 
   * @param formMst0105d01 The controller object.
   */
  public void setHaitaDate(FormMst0105d01 formMst0105d01) {
    formMst0105d01.setHaitaDate(DateUtil.getSysDate());
    formMst0105d01.setHaitaTime(DateUtil.getSysTime());
  }
  
  /**
   * （一覧画面）CSVボタン
   * 
   * @param formMst0105d01 The controller object
   * @return The CSV file's path.
   * @throws Exception No comment
   */
  public String exportCvs(FormMst0105d01 formMst0105d01) throws Exception {
    String errorItemId = "";
    boolean relationMasterErrorFlag = getCustNmR(formMst0105d01, true);
    if (relationMasterErrorFlag) {
      errorItemId += "txtOnlineCenterCode" + MstConst.DELIMITER_ERROR;
    }
    relationMasterErrorFlag = getPartnerCustCode(formMst0105d01, true);
    // フォーカスセット
    if (relationMasterErrorFlag) {
      errorItemId += "txtOnlineTorihikiCode" + MstConst.DELIMITER_ERROR;
      errorItemId += "txtAtTorihikiCode" + MstConst.DELIMITER_ERROR;
    }
    formMst0105d01.setErrorItemId(errorItemId);
    if (relationMasterErrorFlag) {
      return null;
    }
    String currentTime = DateUtil.getSysDate() + DateUtil.getSysTime();
    // ファイル名
    String pathFile = "Mst01-05d011_" + currentTime + ".csv";
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Close_Date", formMst0105d01.getBusinessDate());
    parms.put("OL_Center_Code", formMst0105d01.getTxtOnlineCenterCode());
    parms.put("OL_Torihiki_Code", formMst0105d01.getTxtOnlineTorihikiCode());
    parms.put("AT_Torihiki_Code", formMst0105d01.getTxtAtTorihikiCode());
    parms.put("chkCancellationData", formMst0105d01.isChkCancellationData());
    Object[] fileHeader = {
        "No",
        "オンラインセンターコード",
        "オンライン取引先コード",
        "相手取引先コード",
        "相手店コード",
        "自社得意先コード",
        "得意先略称",
        "自社店コード",
        "店舗略称",
        "納品区分",
        "納品区分名称",
        "状況コード",
        "登録ユーザID",
        "登録プログラムID",
        "登録年月日",
        "登録時刻",
        "修正ユーザID",
        "修正プログラムID",
        "修正年月日",
        "修正時刻"
    };
    
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    csvFilePrinter.printRecord(fileHeader);
    SeqProc seqProc = new SeqProc(csvFilePrinter);
    try {
      mst0105d00Dao.getMst0105d00Mapper().getOlCustConvMasterInfoListForCsv(parms, seqProc);
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

    /**
     * コンストラクタ
     *
     * @param nengetsudo 年月
     * @param printer CSVプリンタ
     */
    public SeqProc(CSVPrinter printer) {
      this.printer = printer;
      this.setEmpty(true);
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

      OlCustConvMasterData row = (OlCustConvMasterData) context.getResultObject();
      try {
        printer.print(intRenban);
        printer.print(Util.convertUnsanitize(row.getOlCenterCode()));
        printer.print(Util.convertUnsanitize(row.getOlTorihikiCode()));
        printer.print(Util.convertUnsanitize(row.getAtTorihikiCode()));
        printer.print(Util.convertUnsanitize(row.getAtTenCode()));
        printer.print(Util.convertUnsanitize(row.getCustCode()));
        printer.print(Util.convertUnsanitize(row.getCustNmR()));
        printer.print(Util.convertUnsanitize(row.getShopCode()));
        printer.print(Util.convertUnsanitize(row.getShopNmR()));
        printer.print(Util.convertUnsanitize(row.getDeliKb()));
        printer.print(Util.convertUnsanitize(row.getTarget1()));
        printer.print(Util.convertUnsanitize(row.getStsCode()));
        printer.print(Util.convertUnsanitize(row.getInsUserid()));
        printer.print(Util.convertUnsanitize(row.getInsPgid()));
        printer.print(Util.convertUnsanitize(row.getInsYmd()));
        printer.print(Util.convertUnsanitize(row.getInsTime()));
        printer.print(Util.convertUnsanitize(row.getUpdUserid()));
        printer.print(Util.convertUnsanitize(row.getUpdPgid()));
        printer.print(Util.convertUnsanitize(row.getUpdYmd()));
        printer.print(Util.convertUnsanitize(row.getUpdTime()));
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
}
