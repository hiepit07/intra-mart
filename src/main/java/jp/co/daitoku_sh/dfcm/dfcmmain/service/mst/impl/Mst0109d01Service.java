/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0109d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/20
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCustName0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCorrectKbMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0109d01Dao;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0109d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0109d01Dao")
  private Mst0109d01Dao mst0109d01Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  /** Propertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
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

  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する.
   * 
   * @param formMst0109d01:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo_Dll(FormMst0109d01 formMst0109d01, Model model) {
    try {
      ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
      ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = null;
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", String.valueOf(formMst0109d01.getBusinessDate()));
      lstMstSJigyoInfo = mst0109d01Dao.getMst0109d01Mapper().getSJigyoInfo(
          params);
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
   * 検索条件入力エリアを初期化する 条件：検索条件保持クラス == NULL.
   * 
   * @param formMst0109d01:フォーム
   */
  public void initScreenNoConditional(FormMst0109d01 formMst0109d01) {
    // [画面]事業所 ＝ [セッション]ログイン所属事業所コード
    if (formMst0109d01.getSysAdminFlag().equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_VALID)) {
      formMst0109d01.setDdlOyaShozoku(formMst0109d01.getLoginJigyouShoCode());
    }
    // [画面]得意先コード ＝ NULL
    formMst0109d01.setTxtCustCode("");
    // [画面]得意先名称（表示用） ＝ NULL
    formMst0109d01.setLblCustNmRHidden("");
    // [画面]得意先名称（検索用） ＝ NULL
    formMst0109d01.setTxtCustNmR("");
    // [画面]訂正区分 ＝ NULL
    formMst0109d01.setTxtCorrectKb("");
    // [画面]数量ゼロ納品データ連携 ＝ NULL
    formMst0109d01.setTxtZeroDlvdatFlg("");
    // [画面]取消データを対象とする ＝ チェックなし
    formMst0109d01.setChkStKb(false);
    // [画面]共通設定を検索対象とする ＝ チェックあり
    formMst0109d01.setChkStKb(true);
  }
  
  /**
   * 検索条件入力エリアを初期化する 条件：検索条件保持クラス !＝ NULL.
   * 
   * @param SearchCondMst0109d01:一覧画面の条件情報
   * @param FormMst0109d01:フォーム
   */
  public void initScreenWithConditional(
      SearchCondMst0109d01 searchCondMst0109d01,
      FormMst0109d01 formMst0109d01) {
    formMst0109d01.setDdlOyaShozoku(searchCondMst0109d01.getDdlShozoku());
    formMst0109d01.setTxtCustCode(searchCondMst0109d01.getTxtCustCode());
    formMst0109d01.setTxtCustNmR(searchCondMst0109d01.getTxtCustNmR());
    formMst0109d01.setTxtCorrectKb(searchCondMst0109d01.getTxtCorrectKb());
    formMst0109d01.setTxtZeroDlvdatFlg(searchCondMst0109d01
        .getTxtZeroDlvdatFlg());
    if (searchCondMst0109d01.getChkStKb().equalsIgnoreCase(
        MstConst.CHECK_OFF)) {
      formMst0109d01.setChkStKb(true);
    } else {
      formMst0109d01.setChkStKb(false);
    }

    if (searchCondMst0109d01.getChkStsCode().equalsIgnoreCase(
        MstConst.CHECK_OFF)) {
      formMst0109d01.setChkStsCode(true);
    } else {
      formMst0109d01.setChkStsCode(false);
    }

  }

  /**
   * 検索条件の通りに、検索結果を取得する.
   * 
   * @param formMst0109d01:フォーム
   * @return テーブルのデータを戻る
   */
  public List<MstCorrectKbMst0109d01> getSearchResult(
      FormMst0109d01 formMst0109d01) {
    List<MstCorrectKbMst0109d01> lstMstCorrectKbMst0109d01 = 
        new ArrayList<MstCorrectKbMst0109d01>();

    String strJigyoshoCode;
    if (formMst0109d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      strJigyoshoCode = formMst0109d01.getDdlOyaShozoku();
    } else {
      strJigyoshoCode = formMst0109d01.getLoginJigyouShoCode();
    }
    
    String strCustNmR = "";
    if (!formMst0109d01.getTxtCustNmR().equalsIgnoreCase("")) {
      strCustNmR = "%" + formMst0109d01.getTxtCustNmR() + "%";
    }

    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      String searchMax = "TOP " + String.valueOf(formMst0109d01.getSearchMax() + 1);
      params.put("Search_Max", searchMax);
      params.put("JigyoshoCode", strJigyoshoCode);
      params.put("CustCode", formMst0109d01.getTxtCustCode());  
      params.put("CustNmR", strCustNmR);      
      params.put("CorrectKb", formMst0109d01.getTxtCorrectKb());
      params.put("ZeroDlvdatFlg", formMst0109d01.getTxtZeroDlvdatFlg());
      params.put("STS_Code", formMst0109d01.isChkStsCode());
      params.put("St_Kb", formMst0109d01.isChkStKb());

      lstMstCorrectKbMst0109d01 = mst0109d01Dao.getMst0109d01Mapper().getSearchResult(params);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstCorrectKbMst0109d01;
  }

  /**
   * 排他の設定.
   * 
   * @param formMst0109d01:フォーム
   */
  public void setHaitaDate(FormMst0109d01 formMst0109d01) {
    formMst0109d01.setHaitaDate(DateUtil.getSysDate());
    formMst0109d01.setHaitaTime(DateUtil.getSysTime());
  }

  /**
   * 全項目入力チェック処理.
   * 
   * @param formMst0109d01:フォーム
   * @return Listエラーメッセージ
   */
  public MstCorrectKbMst0109d01 checkInput(FormMst0109d01 formMst0109d01) {

    MstCorrectKbMst0109d01 mstCorrectKbMst0109d01 = null;

    String strError;
    String strErrorId = "";
    String strErrorMess = "";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 得意先コード
    strError = checkItem(formMst0109d01.getTxtCustCode(), false, InputCheckCom.TYPE_NUM, 7);
    if (strError != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("得意先コード");
      strErrorMess += readPropertiesFileService.getMessage(strError, paramMess)
          + MstConst.HTML_NEWLINE;
      strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR;
    }

    // 得意先名称
    strError = checkItem(formMst0109d01.getTxtCustNmR(), false,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("得意先名称");
      strErrorMess += readPropertiesFileService.getMessage(strError, paramMess)
          + MstConst.HTML_NEWLINE;
      strErrorId += "txtCustNmR" + MstConst.DELIMITER_ERROR;
    }

    // 訂正区分
    strError = checkItem(formMst0109d01.getTxtCorrectKb(), false,
        InputCheckCom.TYPE_NUM, 4);
    if (strError != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("訂正区分");
      strErrorMess += readPropertiesFileService.getMessage(strError, paramMess)
          + MstConst.HTML_NEWLINE;
      strErrorId += "txtCorrectKb" + MstConst.DELIMITER_ERROR;
    }

    // 数量ゼロ納品データ連携
    strError = checkItem(formMst0109d01.getTxtZeroDlvdatFlg(), false,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("数量ゼロ納品データ連携 ");
      strErrorMess += readPropertiesFileService.getMessage(strError, paramMess)
          + MstConst.HTML_NEWLINE;
      strErrorId += "txtZeroDlvdatFlg" + MstConst.DELIMITER_ERROR;
    } else {
      // (4) [画面]数量ゼロ納品データ連携 ≠ NULL and ([画面]数量ゼロ納品データ連携 ≠ '1' or [画面]数量ゼロ納品データ連携 ≠ '2')
      // の場合エラーとし、次の通り処理を行う。
      if (!formMst0109d01.getTxtZeroDlvdatFlg().equalsIgnoreCase("") && !formMst0109d01
          .getTxtZeroDlvdatFlg().equalsIgnoreCase(MstConst.RENKEI_TAISHO) && !formMst0109d01
              .getTxtZeroDlvdatFlg().equalsIgnoreCase(MstConst.RENKEI_TAISHOGAI)) {
        paramMess = new ArrayList<String>();
        paramMess.add("数量ゼロ納品データ連携は１または２で入力してください");
        strErrorMess += readPropertiesFileService.getMessage("MST013-E", paramMess)
            + MstConst.HTML_NEWLINE;
        strErrorId += "txtZeroDlvdatFlg" + MstConst.DELIMITER_ERROR;
      }
    }

    if (!strErrorId.equalsIgnoreCase("")) {
      mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
      mstCorrectKbMst0109d01.setMessage(strErrorMess);
      mstCorrectKbMst0109d01.setLstId(strErrorId);
      mstCorrectKbMst0109d01.setType("checkInputErr");
    }

    return mstCorrectKbMst0109d01;
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
  public String checkItem(String val, boolean emptyFlg, int type, int len) {
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
    error = InputCheckCom.chkLength(val, len);
    if (error != null) {
      return error;
    }

    return error;
  }

  /**
   * 存在チェック.
   * 
   * @param formMst0109d01:フォーム
   * @return MstCorrectKbMst0109d01
   */
  public MstCorrectKbMst0109d01 checkExist(FormMst0109d01 formMst0109d01) {

    MstCorrectKbMst0109d01 mstCorrectKbMst0109d01 = null;
    String strCustCd = formMst0109d01.getTxtCustCode();
    String strJigyoCd = "";

    // （1）[画面]得意先コード ≠ NULL の場合、
    if (strCustCd != null && !strCustCd.equalsIgnoreCase("")) {
      // （1）-1 [画面]得意先コードをゼロ編集する
      strCustCd = Util.addLeadingZeros(strCustCd, 7);

      // （1）-2 得意先マスタから得意先名称を取得する
      // [セッション]システム管理者フラグ ＝ '1'（システム管理者） の場合
      if (formMst0109d01.getSysAdminFlag().equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_VALID)) {
        strJigyoCd = formMst0109d01.getDdlOyaShozoku();
      } else {
        strJigyoCd = formMst0109d01.getLoginJigyouShoCode();
      }

      // （1）-2-b 得意先略称取得
      List<MstCustName0109d01> lstMst0109d01CustName = null;
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("CustCode", strCustCd);
      params.put("JigyoCode", strJigyoCd);

      lstMst0109d01CustName = mst0109d01Dao.getMst0109d01Mapper().getCustName(params);
      if (lstMst0109d01CustName == null || lstMst0109d01CustName.size() <= 0) {
        // メッセージのParam
        ArrayList<String> paramMess = new ArrayList<String>();
        mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
        paramMess.add("得意先マスタ");
        paramMess.add("入力された得意先");
        // 該当レコードが存在しない場合、[変数]入力チェックエラーフラグにTrueをセットし、エラーメッセージ表示
        mstCorrectKbMst0109d01.setMessage(readPropertiesFileService.getMessage("COM009-E",
            paramMess));
        mstCorrectKbMst0109d01.setLstId("txtCustCode");
        mstCorrectKbMst0109d01.setType("checkExistErr");
      } else {
        formMst0109d01.setLblCustNmRHidden(lstMst0109d01CustName.get(0).getCustNm());
      }
    }
    return mstCorrectKbMst0109d01;
  }

  /**
   * CSV Export 発生したCSVファイル.
   * 
   * @param formMst0109d01:フォーム
   * @return 画面表示
   * @throws Exception エラー画面
   */
  public String exportCsvData(FormMst0109d01 formMst0109d01) throws Exception {

    String strResult = "";
    DateFormat dateFormat = new SimpleDateFormat(
        CommonConst.DATE_FORMAT_YMDHMS);
    Date date = new Date();
    String currentTime = dateFormat.format(date);
    // ファイル名
    String pathFile = "Mst01-09d011_" + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;

    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");

    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    Object[] fileHeader = { "No", "設定区分", "事業所コード", "得意先コード", "得意先略称", "訂正区分",
        "訂正理由", "数量ゼロ納品データ連携", "備考", "状況コード" };
    csvFilePrinter.printRecord(fileHeader);

    SeqProc seqProc = new SeqProc(csvFilePrinter);
    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("Search_Max", "");
      params.put("JigyoshoCode", formMst0109d01.getDdlOyaShozoku());
      params.put("CustCode", formMst0109d01.getTxtCustCode());
      params.put("CustNmR", formMst0109d01.getTxtCustNmR());
      params.put("CorrectKb", formMst0109d01.getTxtCorrectKb());
      params.put("ZeroDlvdatFlg", formMst0109d01.getTxtZeroDlvdatFlg());
      params.put("STS_Code", formMst0109d01.isChkStsCode());
      params.put("St_Kb", formMst0109d01.isChkStKb());

      mst0109d01Dao.getMst0109d01Mapper().getSearchResult(params, seqProc);
      if (seqProc.isEmpty) {
        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();
        File fileTemp = new File(folderFile);
        if (fileTemp.exists() && !fileTemp.isDirectory()) {
          fileTemp.delete();
          return null;
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      try {
        if (!seqProc.isEmpty) {
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

    /** 連番. */
    private int intRenban;

    /**
     * コンストラクタ.
     * 
     * @param printer:CSVプリンタ
     */
    public SeqProc(CSVPrinter printer) {
      this.printer = printer;
      this.setEmpty(true);
      intRenban = 0;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する.
     *
     * @param context リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      intRenban++;
      this.setEmpty(false);

      MstCorrectKbMst0109d01 mstCorrectKb = (MstCorrectKbMst0109d01) context
          .getResultObject();

      try {
        printer.print(intRenban);        
        printer.print(Util.convertUnsanitize(mstCorrectKb.getStKb()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getJigyoCode()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getCustCode()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getCustNmR()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getCorrectKb()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getCorrectCause()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getZeroDlvdatFlg()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getBiKou()));
        printer.print(Util.convertUnsanitize(mstCorrectKb.getStsCode()));
        
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
