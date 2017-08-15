/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0199d01Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran　新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0199d00Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.CsvMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d01;

@Service
public class Mst0199d01Service extends AbsService {
  
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("mst0199d00Dao")
  private Mst0199d00Dao mst0199d00Dao;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 画面表示.
   * @param formMst0199d01 The class whose elements are to be place 
   * @param model A holder for model attributes
   * @param searchCondMst0199d01 検索条件保持クラス
   * @return 画面表示
   */
  public String init(FormMst0199d01 formMst0199d01, Model model,
      SearchCondMst0199d01 searchCondMst0199d01) {
    ArrayList<MstGeneral> categoryList = null;
    try {
      // 汎用マスタのカテゴリから汎用分類を取得し、分類コンボの作成を行う
      categoryList = mst0199d00Dao.getMst0199d00Mapper().getCategoryList();
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    
    ArrayList<String> paramMess = null;
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    String errMsg = "";
    // 件数チェック
    if (categoryList == null || categoryList.size() <= 0) {
      // エラーメッセージを画面に表示する
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("カテゴリ");
      errMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      return errMsg;
    } else {
      ObjCombobox element = new ObjCombobox();
      element.setCode("");
      element.setName("");
      ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
      comboboxs.add(element);
      for (MstGeneral gen: categoryList) {
        element = new ObjCombobox();
        element.setCode(gen.getCategory());
        element.setName(gen.getCategory() + " : " + gen.getTarget1());
        comboboxs.add(element);
      }
      // 取得した[変数]カテゴリ情報格納クラスを、[画面]分類へセットする
      model.addAttribute("categoryList", comboboxs);
      // 【画面初期化】
      if (searchCondMst0199d01 != null) {
        formMst0199d01.setCategory(searchCondMst0199d01.getCategory());
        formMst0199d01.setKbNm(searchCondMst0199d01.getKbNm());
      } else {
        formMst0199d01.setCategory(null);
        formMst0199d01.setKbNm(null);
      }
    }
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        commonDao, null, null, null);
    int searchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0199d01.setSearchMax(searchMax);
    return errMsg;
  }

  /**
   * 検索ボタンをクリックされた時の処理。検索条件に該当するマスタデータを一覧に表示する.
   * @param formMst0199d01 The class whose elements are to be place 
   */
  public void search(FormMst0199d01 formMst0199d01) {
    ArrayList<String> paramMess = null;
    String errMessage = "";
    Map<String, Object> parms = new HashMap<String, Object>();
    String searchMax = "TOP " + String.valueOf(formMst0199d01.getSearchMax() + 1);
    parms.put("Search_Max", searchMax);
    parms.put("Category", formMst0199d01.getCategory());
    parms.put("Kb_Nm", "%" + formMst0199d01.getKbNm() + "%");
    // 管理区分一覧データを取得する
    ArrayList<RstMst0199d01> rstMst0199d01s = null;
    try {
      rstMst0199d01s = mst0199d00Dao.getMst0199d00Mapper().getDivisionList(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    // （1）[変数]管理区分一覧情報格納クラス ≠ NULL の場合、正常とする
    if (rstMst0199d01s != null && rstMst0199d01s.size() > 0) {
      formMst0199d01.setRstMst0199d01List(rstMst0199d01s);
      setHaitaDate(formMst0199d01);
      if (rstMst0199d01s.size() >= formMst0199d01.getSearchMax()) {
        paramMess = new ArrayList<String>();
        paramMess.add(String.valueOf(formMst0199d01.getSearchMax()));
        formMst0199d01.setErrMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        formMst0199d01.setMessageType("searchMax");
      }
      // （2）[変数]管理区分一覧情報格納クラス ＝ NULL の場合、警告メッセージを表示する
    } else {
      // 警告メッセージを画面に表示する
      errMessage = readPropertiesFileService.getMessage("COM025-E", null);
      formMst0199d01.setErrMessage(errMessage);
      formMst0199d01.setMessageType("body");
    }
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
   * (一覧画面)CSVボタン
   * @param formMst0199d01 The class whose elements are to be place 
   * @return The URL of CSV file
   * @throws Exception No comment
   */
  public String exportCvs(FormMst0199d01 formMst0199d01) throws Exception {
    Date date = new Date();
    String currentTime = DateUtil.setFormat(date, CommonConst.DATE_FORMAT_YMDHMS);
    // ファイル名
    String pathFile = "Mst01-99d011_" + currentTime + ".csv";
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Category", formMst0199d01.getCategory());
    parms.put("Kb_Nm", "%" + formMst0199d01.getKbNm() + "%");
    Object[] fileHeader = { "区分 ", "コード  ", "コード属性  ", "コード桁数  ", "区分名  ",
        "コード名 ", "項目１  ", "項目属性１  ",
        "項目桁数１  ", "項目説明１  ", "項目２  ", "項目属性２  ", "項目桁数２  ", "項目説明２  ",
        "項目３  ", "項目属性３  ", "項目桁数３  ",
        "項目説明３  ", "項目４  ", "項目属性４  ", "項目桁数４  ", "項目説明４  ", "項目５  ",
        "項目属性５  ", "項目桁数５  ", "項目説明５  ", "項目６  ",
        "項目属性６  ", "項目桁数６  ", "項目説明６  ", "項目７  ", "項目属性７  ", "項目桁数７  ",
        "項目説明７  ", "項目８  ", "項目属性８  ", "項目桁数８  ",
        "項目説明８  ", "項目９  ", "項目属性９  ", "項目桁数９  ", "項目説明９  ", "項目１０ ",
        "項目属性１０ ", "項目桁数１０ ", "項目説明１０ ", "カテゴリ ",
        "分類名称 ", "表示順  ", "更新可能フラグ  ", "追加可能フラグ  ", "削除可能フラグ  ", "登録ユーザID  ",
        "登録プログラムID  ", "登録年月日  ", "登録時刻 ", "修正ユーザID  ", "最終登録者氏名  ",
        "修正プログラムID  ", "修正年月日  ", "修正時刻 " };
    csvFilePrinter.printRecord(fileHeader);
    SeqProc seqProc = new SeqProc(csvFilePrinter);
    mst0199d00Dao.getMst0199d00Mapper().getCsvMst0199d01Data(parms, seqProc);
    if (seqProc.isEmpty) {
      return null;
    }
    fileWriter.flush();
    fileWriter.close();
    csvFilePrinter.close();
    String urlCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD")
        + pathFile;
    return urlCsv;
  }
  
  /**
   * No comment
   * 
   * @param formMst0199d01 フォーム(jspの modelAttribute="FormMst0199d01" とリンク）
   */
  public void setHaitaDate(FormMst0199d01 formMst0199d01) {
    formMst0199d01.setHaitaDate(DateUtil.getSysDate());
    formMst0199d01.setHaitaTime(DateUtil.getSysTime());
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
      this.setEmpty(false);

      CsvMst0199d01 row = (CsvMst0199d01) context.getResultObject();

      try {
        printer.print(Util.convertUnsanitize(row.getGlKb()));
        printer.print(Util.convertUnsanitize(row.getGlCode()));
        printer.print(Util.convertUnsanitize(row.getCodeAttr()));
        printer.print(row.getCodeDigit());
        printer.print(Util.convertUnsanitize(row.getKbNm()));
        printer.print(Util.convertUnsanitize(row.getCodeNm()));
        
        printer.print(Util.convertUnsanitize(row.getTarget1()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr1()));
        printer.print(row.getTargetDigit1());
        printer.print(Util.convertUnsanitize(row.getTargetExp1()));
        
        printer.print(Util.convertUnsanitize(row.getTarget2()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr2()));
        printer.print(row.getTargetDigit2());
        printer.print(Util.convertUnsanitize(row.getTargetExp2()));
        
        printer.print(Util.convertUnsanitize(row.getTarget3()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr3()));
        printer.print(row.getTargetDigit3());
        printer.print(Util.convertUnsanitize(row.getTargetExp3()));
        
        printer.print(Util.convertUnsanitize(row.getTarget4()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr4()));
        printer.print(row.getTargetDigit4());
        printer.print(Util.convertUnsanitize(row.getTargetExp4()));
        
        printer.print(Util.convertUnsanitize(row.getTarget5()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr5()));
        printer.print(row.getTargetDigit5());
        printer.print(Util.convertUnsanitize(row.getTargetExp5()));
        
        printer.print(Util.convertUnsanitize(row.getTarget6()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr6()));
        printer.print(row.getTargetDigit6());
        printer.print(Util.convertUnsanitize(row.getTargetExp6()));
        
        printer.print(Util.convertUnsanitize(row.getTarget7()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr7()));
        printer.print(row.getTargetDigit7());
        printer.print(Util.convertUnsanitize(row.getTargetExp7()));
        
        printer.print(Util.convertUnsanitize(row.getTarget8()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr8()));
        printer.print(row.getTargetDigit8());
        printer.print(Util.convertUnsanitize(row.getTargetExp8()));
        
        printer.print(Util.convertUnsanitize(row.getTarget9()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr9()));
        printer.print(row.getTargetDigit9());
        printer.print(Util.convertUnsanitize(row.getTargetExp9()));
        
        printer.print(Util.convertUnsanitize(row.getTarget10()));
        printer.print(Util.convertUnsanitize(row.getTargetAttr10()));
        printer.print(row.getTargetDigit10());
        printer.print(Util.convertUnsanitize(row.getTargetExp10()));
        
        printer.print(Util.convertUnsanitize(row.getCategory()));
        printer.print(Util.convertUnsanitize(row.getClassificationName()));
        
        printer.print(row.getDispNumeric());
        printer.print(Util.convertUnsanitize(row.getInsPermitFlg()));
        printer.print(Util.convertUnsanitize(row.getUpdPermitFlg()));
        printer.print(Util.convertUnsanitize(row.getDelPermitFlg()));
        printer.print(Util.convertUnsanitize(row.getInsUserid()));
        printer.print(Util.convertUnsanitize(row.getInsPgid()));
        printer.print(Util.convertUnsanitize(row.getInsYmd()));
        printer.print(Util.convertUnsanitize(row.getInsTime()));
        printer.print(Util.convertUnsanitize(row.getUpdUserid()));
        printer.print(Util.convertUnsanitize(row.getUserNm()));
        printer.print(Util.convertUnsanitize(row.getUpdPgid()));
        printer.print(Util.convertUnsanitize(row.getUpdYmd()));
        printer.print(Util.convertUnsanitize(row.getUpdTime()));
        // 次のレコード
        printer.println();

      } catch (IOException ioe) {
        throw new RuntimeException(ioe);
      }
    }
   
    public void setEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
    }
  }
}
