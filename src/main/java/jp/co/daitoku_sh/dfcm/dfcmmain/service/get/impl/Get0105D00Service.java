/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/21
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * 
 *<p>2015/10/21 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.get.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblGet01JigConfirmMapper;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirm;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirmExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.GetConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.FormGet0105d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetCorrectKbGet0105d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetUrikakegatsudo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetZandaka;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.get.impl.Get0105D00Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Get0105D00Mapper;

/**
 * サービスクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Get0105D00Service extends AbsService {

  /** メッセージPropertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  @Qualifier("get0105D00Dao")
  private Get0105D00Dao get0105D00Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  /* ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  @Autowired
  private MstSJigyoMapper getSJigyoMapper;

  @Autowired
  private TblGet01JigConfirmMapper tblGet01JigConfirmMapper;

  public MstSJigyoMapper gettMstSJigyoMapper() {
    return getSJigyoMapper;
  }

  public String getHaitaInspgid() {
    return "DFCM-GET01-01D00";
  }

  public String getHaitaInsymd() {
    return DateUtil.getSysDate();
  }

  public String getHaitaInstime() {
    return DateUtil.getSysTime();
  }

  public String getHaitaUpdpgid() {
    return "DFCM-GET01-01D00";
  }

  public String getHaitaUpdymd() {
    return DateUtil.getSysDate();
  }

  public String getHaitaUpdtime() {
    return DateUtil.getSysTime();
  }

  public Dfcmlogger getLogger() {
    return logger;
  }

  public Get0105D00Dao getGet0105D00Dao() {
    return get0105D00Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  /**
   * get office information from database
   * 
   * @return List
   */
  public List<MstSJigyo> getOfficeInfo() {
    MstSJigyoExample jigyouExample = new MstSJigyoExample();
    jigyouExample.createCriteria().andDelflgEqualTo((short) 0);
    jigyouExample.setOrderByClause("JGYCD ASC");
    return getSJigyoMapper.selectByExample(jigyouExample);
  }

  /**
   * get certificate information from database
   * 
   * @param loginOfficeCode String
   * @return List
   */
  public List<TblGet01JigConfirm> getDeterMonInfo(String loginOfficeCode) {
    TblGet01JigConfirmExample tblGet01JigConfirmExample = new TblGet01JigConfirmExample();

    tblGet01JigConfirmExample.createCriteria().andJigyoCodeEqualTo(
        loginOfficeCode);

    return tblGet01JigConfirmMapper.selectByExample(
        tblGet01JigConfirmExample);
  }

  /**
   * Return error 7-2-1, sheet inittinize
   * 
   */
  public void getErrorStringEventInittialize_721(Model model) {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所マスタ");
    paramMess.add("事業所情報");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    addErrorMessageToModel(errorMessage, model);
  }
  /**
   * Put error String into model
   * 
   * @Param ErrorMessages
   * @Param Model
   */
  private void addErrorMessageToModel(ErrorMessages errorMessage, Model model) {
    List<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    lstErrMess.add(errorMessage);
    model.addAttribute("errorMessages", lstErrMess);
  }

  /**
   * Defaultメッセージの取得
   * 
   * @param model Model
   * 
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    ArrayList<String> paramMess = new ArrayList<String>();
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * CSV Export 発生したCSVファイル.
   * 
   * @param formGet0105d00:フォーム
   * @return 画面表示
   * @throws Exception エラー画面
   */
  public String exportCsvData(FormGet0105d00 formGet0105d00) throws Exception {

    String strResult = "";
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    // ファイル名
    String pathFile = "DFCM-GET01-05D01" + currentDate + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;

    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    SeqProc seqProc = new SeqProc(csvFilePrinter,formGet0105d00);
    try {
      Object[] fileHeader = {"事業所", "請求先","YG", "請求先名称 ",
          "営業担当者","当月売上高  ","締日 ", "入金月","入金日",
          "サイト", "入金方法", "請求期間", "入金予定日", "当月請求額", "入金日",
          "入金金額", "入金日 - 入金予定日","誤入金・再請求等","未回収残高 ", "摘要",};
      csvFilePrinter.printRecord(fileHeader);
      // 売上債権状況情報取得
      // 3-1 以下の条件で売上債権状況情報を取得する。
      Map<String, Object> params = new HashMap<String, Object>();

      String urikakeMonth = formGet0105d00.getUrikakeMonth();
      String businessDate = formGet0105d00.getBusinessDate();
      params.put("urikakeMonthWK", "%" + urikakeMonth + "%");
      if (urikakeMonth == null || urikakeMonth.equalsIgnoreCase("")) {
        params.put("urikakeMonthWK", "");
      }     
      String customerCode = formGet0105d00.getTxtCustCd();
      String offiCode = formGet0105d00.getDdlJigyouSho();
      String tantoshaCode = formGet0105d00.getTxtSaleUserCd();
      params.put("businessDateWK", businessDate);
      params.put("tantoshaCodeWK", tantoshaCode);
      params.put("officeCodeWK", offiCode);
      params.put("seikyusakiCodeWK", customerCode);
      params.put("Shop_CodeWK", CommonConst.SHOP_CD_NONE);
      params.put("delayReason", "Delay_Reason");
      params.put("urikakeMonth",  urikakeMonth);
      Get0105D00Mapper mapperGet0105D00 = getGet0105D00Dao()
          .getGet0105D00Mapper();
      mapperGet0105D00.getUrikake(params, seqProc);
      if (seqProc.isEmpty) {
        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();
        File fileTemp = new File(folderFile);
        if (fileTemp.exists()) {
          fileTemp.delete();
          strResult = GetConst.NO_RECORD;
          return strResult;
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      try {
        if (! seqProc.isEmpty) {
          fileWriter.flush();
          fileWriter.close();
          csvFilePrinter.close();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw e;
      }
    }
    strResult = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD")
        + pathFile;
    return strResult;
  }

  /**
   * 全項目入力チェック処理.
   * 
   * @param formGet0105d00:フォーム
   * @return Listエラーメッセージ
   */
  public GetCorrectKbGet0105d00 checkInput(FormGet0105d00 formGet0105d00) {
    GetCorrectKbGet0105d00 getCorrectKbGet0105d00 = null;
    String strError;
    String strErrorId = "";
    String strErrorMess = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    strError = checkItem(formGet0105d00.getUrikakeMonth(), false,
        InputCheckCom.TYPE_NUM, 6,  CommonConst.DATE_FORMAT_YM);
    if (strError != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("売掛月度");
      strErrorMess += readPropertiesFileService.getMessage(strError, paramMess)
          + MstConst.HTML_NEWLINE;
      strErrorId += "urikakeMonth" + MstConst.DELIMITER_ERROR;
    }
    if (formGet0105d00.getUrikakeMonth().equalsIgnoreCase("")
        || formGet0105d00.getUrikakeMonth() == null) {
      paramMess = new ArrayList<String>();
      paramMess.add("売掛月度");
      strErrorMess += readPropertiesFileService.getMessage("COM006-E",
          paramMess);
      strErrorId += "urikakeMonth" + MstConst.DELIMITER_ERROR;
    }
    if (!strErrorId.equalsIgnoreCase("")) {
      getCorrectKbGet0105d00 = new GetCorrectKbGet0105d00();
      getCorrectKbGet0105d00.setMessage(strErrorMess);
      getCorrectKbGet0105d00.setLstId(strErrorId);
      getCorrectKbGet0105d00.setType("checkInputErr");
    }
    return getCorrectKbGet0105d00;
  }

  /**
   * 入力チェック処理.
   * 
   * @param val:チェックデータ
   * @param emptyFlg:必須チェックフラグ
   * @param type:型
   * @param len:桁数
   * @return null: エラー
   */
  public String checkItem(String val, boolean emptyFlg, int type, int len, String format) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }

    error = InputCheckCom.chkType(val, type);
    if (error != null) {
      return error;
    }
    
    error = InputCheckCom.chkDate(val, format);
    if (error != null) {
      return error;
    }
    
    return error;
  }

  /**
   * SQL実行結果を1レコードずつ処理するための内部クラス
   *
   * @author TSUZUKI DENKI
   * @version 1.00
   * @since BSS 1.0.0
   *
   */
  private class SeqProc implements ResultHandler {

    /** CSVプリンタ. */
    private CSVPrinter printer;

    /** 処理結果が空. */
    private boolean isEmpty;

    private FormGet0105d00 formGet0105d00 ;
    /**
     * コンストラクタ.
     * 
     * @param printer:CSVプリンタ
     */
    public SeqProc(CSVPrinter printer,FormGet0105d00 formGet0105d00) {
      this.printer = printer;
      this.setEmpty(true);
      this.formGet0105d00 = formGet0105d00;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する.
     *
     * @param context リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      this.setEmpty(false);

      try {
        GetUrikakegatsudo getUrikakegatsudo = (GetUrikakegatsudo) context
            .getResultObject();

        // [変数]締日１
        String finishDate1 = "";
        // [変数]締日１
        String finishDate2 = "";
        // [変数]回収月１
        String kenshuMonth1 = "";
        // [変数]回収月2
        String kenshuMonth2 = "";
        // [変数]回収日１
        String kenshuDay1 = "";
        // [変数]回収日2
        String kenshuDay2 = "";
       // 「画面表示」で取得しセットしている[変数]事業所情報リストから
        if (formGet0105d00.getDdlJigyouSho().equalsIgnoreCase(getUrikakegatsudo.getJigyoCode())) {
          getUrikakegatsudo.setJigyoCode(formGet0105d00.getDdlJigyouShoName());
        }
        // 3-4-4 ①(1-1) CST.YG取引区分 = '1'（対象）の場合、
        if (getUrikakegatsudo.getYgkuBun() != null && getUrikakegatsudo
            .getYgkuBun().equalsIgnoreCase("1")) {
          getUrikakegatsudo.setYgkuBun("○");
          // 3-4-4② (1-2) CST.YG取引区分 = '2'（対象外）の場合、
        } else {
          getUrikakegatsudo.setYgkuBun("");
        }
        // (2-1) CST.締日１ = '31' の場合、
        if (getUrikakegatsudo.getTotalDate1() != null) {
          if (getUrikakegatsudo.getTotalDate1().equalsIgnoreCase("31")) {
            finishDate1 = "末日";
            // (2-2) CST.締日１ <> '31' の場合、
          } else {
            finishDate1 = getUrikakegatsudo.getTotalDate1();
          }
        }
        // (2-1) CST.締日2 = '31' の場合、
        if (getUrikakegatsudo.getTotalDate2() != null) {
          if (getUrikakegatsudo.getTotalDate2().equalsIgnoreCase("31")) {
            finishDate2 = "末日";
            // (2-2) CST.締日2 <> '31' の場合、
          } else {
            finishDate2 = getUrikakegatsudo.getTotalDate2();
          }
        }
        String colTermKb1 = getUrikakegatsudo.getColTermKb1();
        if (colTermKb1 != null && !colTermKb1.equalsIgnoreCase("")) {
          if (Util.isNumeric(colTermKb1)) {
            int col1 = Integer.parseInt(colTermKb1);
            switch (col1) {
            // (3-1) CST.回収月区分１ = '1'（当月）の場合、
            case 1:
              kenshuMonth1 = "当月";
              break;
            // (3-2) CST.回収月区分１ = '2'（翌月）の場合、
            case 2:
              kenshuMonth1 = "翌月";
              break;
            // (3-3) CST.回収月区分１ = '3'（翌々月）の場合、
            case 3:
              kenshuMonth1 = "翌々月";
              break;
            // (3-4) CST.回収月区分１ = '4'（翌々々月）の場合、
            case 4:
              kenshuMonth1 = "翌々々月";
              break;
            // (3-5) CST.回収月区分１ = '5'（翌々々々月）の場合、
            case 5:
              kenshuMonth1 = "翌々々々月";
              break;
            default:
              break;
            }
          }
        }
        String colTermKb2 = getUrikakegatsudo.getColTermKb2();
        if (colTermKb2 != null && !colTermKb2.equalsIgnoreCase("")) {
          if (Util.isNumeric(colTermKb2)) {
          int col2 = Integer.parseInt(colTermKb2);
          switch (col2) {
          // (4-1) CST.回収月区分２ = '1'（当月）の場合、
          case 1:
            kenshuMonth2 = "当月";
            break;
          // (4-2) CST.回収月区分２ = '2'（翌月）の場合、
          case 2:
            kenshuMonth2 = "翌月";
            break;
          // (4-3) CST.回収月区分２ = '3'（翌々月）の場合、
          case 3:
            kenshuMonth2 = "翌々月";
            break;
          // (4-4) CST.回収月区分２ = '4'（翌々々月）の場合、
          case 4:
            kenshuMonth2 = "翌々々月";
            break;
          // (4-5) CST.回収月区分２ = '5'（翌々々々月）の場合、
          case 5:
            kenshuMonth2 = "翌々々々月";
            break;
          default:
            break;
          }
          }
        }
        // (5-1) CST.回収日１ = '31' の場合、
        if (getUrikakegatsudo.getColDate1() != null) {
          if (getUrikakegatsudo.getColDate1().equalsIgnoreCase("31")) {
            kenshuDay1 = "末日";
            // (5-2) CST.回収日１ <> '31' の場合、、
          } else {
            kenshuDay1 = getUrikakegatsudo.getColDate1();
          }
        }
        // (6-1) CST.回収日２ = '31' の場合、
        if (getUrikakegatsudo.getColDate2() != null) {
          if (getUrikakegatsudo.getColDate2().equalsIgnoreCase("31")) {
            kenshuDay2 = "末日";
            // (6-2) CST.回収日２ <> '31' の場合、
          } else {
            kenshuDay2 = getUrikakegatsudo.getColDate2();
          }
        }
        String payment = getUrikakegatsudo.getPayment();
        if (payment != null && !payment.equalsIgnoreCase("") && Util.isNumeric(payment)) {
          switch (Integer.parseInt(payment)) {
          // (7-1) CST.入金種別 = '1'（現金）の場合、
          case 1:
            getUrikakegatsudo.setPayment("現金");
            break;
          // (7-2) CST.入金種別 = '2'（当座振込）の場合、
          case 2:
            getUrikakegatsudo.setPayment("当座振込");
            break;
          // (7-3) CST.入金種別 = '3'（普通振込）の場合、
          case 3:
            getUrikakegatsudo.setPayment("普通振込");
            break;
          // (7-4) CST.入金種別 = '4'（小切手）の場合、
          case 4:
            getUrikakegatsudo.setPayment("小切手");
            break;
          // (7-5) CST.入金種別 = '5'（手形）の場合、
          case 5:
            getUrikakegatsudo.setPayment("手形");
            break;
          default:
            break;
          }
        }
        String billingStartDatewk = addPlusDay(getUrikakegatsudo.getPrevBildDate(), 1);
        String billingDatewk = getUrikakegatsudo.getBildDate();
        getUrikakegatsudo.setBillingPeriod(billingStartDatewk.substring(4, 6)
            + "/" + billingStartDatewk.substring(6, 8) + "～"
            + billingDatewk.substring(4, 6) + "/" + billingDatewk.substring(6,
                8));
        getUrikakegatsudo.setSite(subDay(getUrikakegatsudo
            .getPaymentPlanDate(), getUrikakegatsudo.getBildDate()));
        getUrikakegatsudo.setThismonthBillAmount(String.valueOf(
            Integer.parseInt(getUrikakegatsudo.getBildUAmt())
                + Integer.parseInt(getUrikakegatsudo.getBildMAmt())));
        getUrikakegatsudo.setPaymentAmount(String.valueOf(Integer.parseInt(
            getUrikakegatsudo.getNyuKinGaku())
            + Integer.parseInt(getUrikakegatsudo.getSouKingaku())));
       String paymentPlanDate  = getUrikakegatsudo.getPaymentPlanDate().trim();
        getUrikakegatsudo.setPaymentDateLost(subDay(getUrikakegatsudo
            .getPaymentDate(), paymentPlanDate));
        getUrikakegatsudo.setErrorPayment(String.valueOf(Integer.parseInt(
            getUrikakegatsudo.getBildUAmt())
            + Integer.parseInt(getUrikakegatsudo.getBildMAmt())
            - Integer.parseInt(getUrikakegatsudo.getNyuKinGaku())
            - Integer.parseInt(getUrikakegatsudo.getSouKingaku())));
        String paymentDelayReasonwk = getUrikakegatsudo.getTarget1();
        getUrikakegatsudo.setUncollectedBalance("0");
        // (1) [変数]請求締め日WK = 月末 and CST.締日１ = '31' の場合、
        if (findLastDate(billingDatewk) && getUrikakegatsudo
                .getTotalDate1().equalsIgnoreCase("31")) {
          getUrikakegatsudo.setFinishDate(finishDate1);
          getUrikakegatsudo.setKenshuMonth(kenshuMonth1);
          getUrikakegatsudo.setKenshuDate(kenshuDay1);
          // (2) [変数]請求締め日WKの下２桁 = CST.締日１ の場合、
        } else if (billingDatewk.substring(billingDatewk.length() - 2,
            billingDatewk.length()).equalsIgnoreCase(getUrikakegatsudo
                .getTotalDate1())) {
          getUrikakegatsudo.setFinishDate(finishDate1);
          getUrikakegatsudo.setKenshuMonth(kenshuMonth1);
          getUrikakegatsudo.setKenshuDate(kenshuDay1);
          // (3) [変数]請求締め日WK = 月末 and CST.締日２ = '31' の場合、
        } else if (findLastDate(billingDatewk)
            && getUrikakegatsudo .getTotalDate2().equalsIgnoreCase("31")) {
          getUrikakegatsudo.setFinishDate(finishDate2);
          getUrikakegatsudo.setKenshuMonth(kenshuMonth2);
          getUrikakegatsudo.setKenshuDate(kenshuDay2);
          // (4) [変数]請求締め日WKの下２桁 = CST.締日２ の場合、
        } else if (billingDatewk.substring(billingDatewk.length() - 2,
            billingDatewk.length()).equalsIgnoreCase(getUrikakegatsudo
                .getTotalDate2())) {
          getUrikakegatsudo.setFinishDate(finishDate2);
          getUrikakegatsudo.setKenshuMonth(kenshuMonth2);
          getUrikakegatsudo.setKenshuDate(kenshuDay2);
        } else {
          // (5) 上記以外の場合
          getUrikakegatsudo.setFinishDate("");
          getUrikakegatsudo.setKenshuMonth("");
          getUrikakegatsudo.setKenshuDate("");
        }

        String balanceReasonwk = "";
        // 残高理由取得
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nyudenNo", getUrikakegatsudo.getnyuDen());
        ArrayList<GetZandaka> listGetZandaka = get0105D00Dao
            .getGet0105D00Mapper().getZandaka(params);
        // データ存在チェック
        // (1) 該当データが存在しない場合、
        if (listGetZandaka.size() == 0 || listGetZandaka == null) {
          balanceReasonwk = "";
          // (2) 残高理由が存在する場合、取得値を編集（取得件数分、2-6-2を繰返す）
        } else {
          for (int j = 0; j < listGetZandaka.size(); j++) {
            if (listGetZandaka.get(j).getKinGa() != null) {
              balanceReasonwk = balanceReasonwk + listGetZandaka.get(j)
              .getTarget1() + "(" + listGetZandaka.get(j).getKinGa() + ")、";
            } else {
              balanceReasonwk = balanceReasonwk + listGetZandaka.get(j)
              .getTarget1() + "("  + ")、";
            }
       
          }
          balanceReasonwk = balanceReasonwk.substring(0, balanceReasonwk
              .lastIndexOf("、"));
          if (!balanceReasonwk.equalsIgnoreCase("")) {
            getUrikakegatsudo.setSummary(paymentDelayReasonwk + "、"
                + balanceReasonwk);
          } else {
            getUrikakegatsudo.setSummary(paymentDelayReasonwk);
          }
        }
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getJigyoCode()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getCustCode()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getYgkuBun()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getCustNm()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getUserNm()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getMonUriageTtl()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getFinishDate()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getKenshuMonth()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getKenshuDate()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getSite()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getPayment()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getBillingPeriod()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getPaymentPlanDate()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getThismonthBillAmount()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getPaymentDate()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getPaymentAmount()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getPaymentDateLost()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getErrorPayment()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getUncollectedBalance()));
        printer.print(Util.convertUnsanitize(getUrikakegatsudo
            .getSummary()));
        // 次のレコード
        printer.println();

      } catch (IOException ioe) {
        logger.error(ioe.toString());
        throw new RuntimeException(ioe);
      } catch (NullPointerException ioe) {
        logger.error(ioe.toString());
        throw new RuntimeException(ioe);
      }
    }

    public void setEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
    }
  }

  /**
   * フォームの初期化.
   * 
   * @param model ： フォームのModel
   * @param formGet0105d00 ： 画面のフォーム
   * @param txManager ：PlatformTransactionManager
   * @param appContext ：ApplicationContext
   */
  public void init(Model model, FormGet0105d00 formGet0105d00,
      HttpServletRequest request, PlatformTransactionManager txManager,
      ApplicationContext appContext) {
    // store lasted haita
    // hidden value
    model.addAttribute("haitaDate", getHaitaInsymd());
    model.addAttribute("haitaTime", getHaitaInstime());
    // 2-1 共通関数を使用して、業務日付を取得する
    CommonGetSystemCom systemCom = new CommonGetSystemCom(getCommonDao(),
        txManager, appContext,
        readPropertiesFileService);
    String businessDateWk = systemCom.getAplDate();
    // 戻り値チェック
    if (businessDateWk == null) {
      // [変数]業務日付 = Null の場合、 [変数]業務日付 = システム日付
      businessDateWk = DateUtil.getSysDate();
    }
    formGet0105d00.setBusinessDate(businessDateWk);
    // 4-1 [変数]セッション.システム管理者フラグ = '1'（有効）の場合、事業所マスタから事業所情報を取得する。
    Map<String, Object> map = Util.getContentsFromSession(request);
    String sysAdminFlg = String.valueOf(map.get("SysAdminFlg"));
    formGet0105d00.setSysAdminFlag(sysAdminFlg);
    List<ObjCombobox> officeinfo = null;
    if (sysAdminFlg.compareToIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID) == 0) {
      // 4-1-1
      List<MstSJigyo> jigyouExample = getOfficeInfo();

      if (jigyouExample == null || jigyouExample.size() == 0) {
        // error 7-2-1 該当する事業所情報が存在しない場合、エラーとする
        getErrorStringEventInittialize_721(model);
        formGet0105d00.setIsDisableAll("true");
        return;
      } else {
        // 取得した事業所情報を[変数]事業所情報リストへ格納する（取得件数分、3-2を繰返す）
        ObjCombobox office;
        officeinfo = new ArrayList<ObjCombobox>();
        ObjCombobox firstObj = new ObjCombobox();
        firstObj.setCode("");
        firstObj.setName("");
        officeinfo.add(firstObj);
        for (MstSJigyo lstOfficeInfoitem : jigyouExample) {
          office = new ObjCombobox();
          if (lstOfficeInfoitem != null) {
            office.setCode(lstOfficeInfoitem.getJgycd());
            office.setName(lstOfficeInfoitem.getJgycd() + " : "
                + lstOfficeInfoitem.getJgymei());
            officeinfo.add(office);
          }
        }
      }
    }
    String loginJigyouShoName = String.valueOf(map.get("jgymei"));
    formGet0105d00.setDdlJigyouShoName(loginJigyouShoName);
    // 5-1 事業所月次確定情報から月次確定情報を取得する取得
    String loginOfficeCode = String.valueOf(map.get("JigyoshoCode"));
    String loginUserName = String.valueOf(map.get("UserNm"));
    formGet0105d00.setTantoshaName(loginUserName);
    List<TblGet01JigConfirm> tblGet01JigConfirm = getDeterMonInfo(loginOfficeCode);
    String monthlyFixWk;
    // 該当する事業所月次確定情報が存在しない場合、
    if (tblGet01JigConfirm == null || tblGet01JigConfirm.size() == 0) {
      monthlyFixWk = businessDateWk.substring(0, 6);
    } else {
      // 5-2 取得した値を変数へ格納する
      monthlyFixWk = tblGet01JigConfirm.get(0).getDetermMon();
    }
    // 6-1-1 (1) [変数]セッション.システム管理者フラグ = '0'（無効）の場合、[画面]事業所、[画面]事業所名称を非表示とする
    if (sysAdminFlg.compareToIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID) == 0) {
      model.addAttribute("displayCbOfficeInfo", "display_none");
    } else {
      // 6-1-2 (2) [変数]セッション.システム管理者フラグ =
      // '1'（有効）の場合、[画面]事業所をコンボボックス（選択可能）とし、事業所情報リストの値をセットする（事業所情報リストのサイズ分、5-1-2を繰返す）
      model.addAttribute("uiComboBox_DisplayClass", "");
      model.addAttribute("uiOfficeinfo", officeinfo);
      model.addAttribute("displayCbOfficeInfo", "");
      formGet0105d00.setDdlJigyouSho(loginOfficeCode);
    }
    model.addAttribute("uiAccountMonthly", monthlyFixWk);
    model.addAttribute("uiBillingCode", "");
    model.addAttribute("uiBillingSearchBtn_DisplayClass", "");
    String loginUserCode = String.valueOf(map.get("UserCode"));
    model.addAttribute("uiUserId", loginUserCode);
    model.addAttribute("uiUserIdSearchBtn_DisplayClass", "");
    model.addAttribute("uiloginUserName", loginUserName);
  }
  /**
   * Return 日付の時間を返します
   * @param mdatestart String
   * @param mdateend String
   * @return String
   */
  public String subDay(String mdatestart , String mdateend) {
    String yearStart = mdatestart.substring(0, 4);
    String monthStart = mdatestart.substring(4, 6);
    String dateStart = mdatestart.substring(6, 8);
    String yearEnd = mdateend.substring(0, 4);
    String monthEnd = mdateend.substring(4, 6);
    String dateEnd = mdateend.substring(6, 8);
    Calendar calStart = Calendar.getInstance();
    Calendar calEnd = Calendar.getInstance();
    // -1 mean that the value used to set the MONTH calendar field. 
    // Month value is 0-based. e.g., 0 for January.
    calStart.set(Integer.parseInt(yearStart), Integer.parseInt(monthStart)
        - GetConst.ADJAST_CALENDAR_MONTH,Integer.parseInt(dateStart), 0, 0, 0);
    calEnd.set(Integer.parseInt(yearEnd), Integer.parseInt(monthEnd)
        - GetConst.ADJAST_CALENDAR_MONTH,Integer.parseInt(dateEnd), 0, 0, 0);
    long day = (calStart.getTimeInMillis()
        - calEnd.getTimeInMillis()) / (24 * 60 * 60 * 1000);
    return String.valueOf(day);
  }
  /**
   * 日次
   * @param datetime String
   * @param countDay int
   * @return String
   */
  public String addPlusDay(String datetime , int countDay) {
    StringBuilder result = new StringBuilder();
    String yearStart = datetime.substring(0, 4);
    String monthStart = datetime.substring(4, 6);
    String dateStart = datetime.substring(6, 8);
    Calendar calStart = Calendar.getInstance();
    calStart.set(Integer.parseInt(yearStart), Integer.parseInt(monthStart) 
        - GetConst.ADJAST_CALENDAR_MONTH,Integer.parseInt(dateStart), 0, 0, 0);
    calStart.add(Calendar.DAY_OF_YEAR, countDay);
    result.append(calStart.get(Calendar.YEAR));
    int month = calStart.get(Calendar.MONTH) + GetConst.ADJAST_CALENDAR_MONTH;
    if (month < 10) {
      result.append("0");
      result.append(String.valueOf((calStart.get(Calendar.MONTH)
          + GetConst.ADJAST_CALENDAR_MONTH)));
    } else {
      result.append(String.valueOf((calStart.get(Calendar.MONTH)
          + GetConst.ADJAST_CALENDAR_MONTH)));
    }

    String date = "";
    date = String.valueOf(calStart.get(Calendar.DATE));
    if (calStart.get(Calendar.DATE) < 10) {
      date = "0" + calStart.get(Calendar.DATE);
    }
    result.append(date);
    return result.toString();
  }
  /**
   * 月末を取得
   * @param currentdatetime String
   * @return String
   */
  public boolean findLastDate(String currentdatetime) {
    int month = Integer.valueOf(currentdatetime.substring(4, 6));
    int year = Integer.valueOf(currentdatetime.substring(0, 4));
    int datetime = Integer.valueOf(currentdatetime.substring(6, 8));
    Calendar cal = new GregorianCalendar(year, month, 0);
    Date date = cal.getTime();
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String result = sdf.format(date) ;
    int lastOfMonth = Integer.valueOf(result.substring(result.length() - 2,
        result.length()));
    boolean flag = false;
    if (datetime == lastOfMonth) {
      flag = true;
    }
    return flag;
  }
}
