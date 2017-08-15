/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0101d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

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
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstUserInfoMst0101d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0101d01Dao;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0101d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0101d01Dao")
  private Mst0101d01Dao mst0101d01Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  /** メッセージPropertiesファイルを読む. */
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

  /** DAOの定義. */
  public Mst0101d01Dao getMst0101d01Dao() {
    return mst0101d01Dao;
  }

  public void setMst0101d01Dao(Mst0101d01Dao mst0101d01Dao) {
    this.mst0101d01Dao = mst0101d01Dao;
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
   * @param formMst0101d01:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo_Dll(FormMst0101d01 formMst0101d01, Model model) {
    try {
      ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
      ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = null;
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", formMst0101d01.getBusinessDate().toString());
      lstMstSJigyoInfo = mst0101d01Dao.getMst0101d01Mapper().getSJigyoInfo(
          params);
      if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstSJigyoInfoReturn.add(firstObject);
        for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstSJigyoInfo.getJgycd());
          tempObj.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
              .getJgymei());
          lstMstSJigyoInfoReturn.add(tempObj);
        }
        model.addAttribute("ShozokuClassList", lstMstSJigyoInfoReturn);
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
   * 利用権限のデータを取得して、ドロップダウンリストに表示する.
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setRiyoKengen_Dll(Model model) {
    ArrayList<ObjCombobox> lstMstRiyoKengenInfoReturn = new ArrayList<ObjCombobox>();
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 得意先種別名称を取得する
      List<MstGeneralData> generalDataAuthType = systemCom.getMstGeneralConf(
          "Auth_Code", null);
      if (generalDataAuthType != null && generalDataAuthType.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstRiyoKengenInfoReturn.add(firstObject);
        for (MstGeneralData mstGeneralData : generalDataAuthType) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstGeneralData.getGlCode());
          tempObj.setName(mstGeneralData.getGlCode() + " : " + mstGeneralData
              .getTarget1());
          lstMstRiyoKengenInfoReturn.add(tempObj);
        }
        model.addAttribute("RiyoKengenClassList", lstMstRiyoKengenInfoReturn);
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
   * @param formMst0101d01:フォーム
   */
  public void initScreenNoConditional(FormMst0101d01 formMst0101d01) {
    formMst0101d01.setTxtUserCode("");
    formMst0101d01.setTxtUserName("");
    // [画面]利用権限 ＝先頭行選択状態 default
    formMst0101d01.setDdlShozoku(formMst0101d01.getLoginJigyouShoCode());
    formMst0101d01.setChkTorikeshiData(false);
    formMst0101d01.setChkLock(false);
  }

  /**
   * 検索条件入力エリアを初期化する 条件：検索条件保持クラス !＝ NULL.
   * @param SearchCondMst0101d01:一覧画面の情報
   * @param formMst0101d01:フォーム
   */
  public void initScreenWithConditional(
      SearchCondMst0101d01 searchCondMst0101d01,
      FormMst0101d01 formMst0101d01) {
    // [セッション]システム管理者フラグ ＝ '1'（システム管理者） の場合
    if (formMst0101d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      formMst0101d01.setDdlShozoku(searchCondMst0101d01.getJigyoshoCode());
    }
    formMst0101d01.setTxtUserCode(searchCondMst0101d01.getUserCode());
    formMst0101d01.setTxtUserName(searchCondMst0101d01.getUserNm());
    formMst0101d01.setDdlRiyoKengen(searchCondMst0101d01.getAuthCode());

    // [入力]検索条件保持クラス.取消データを対象とする ＝ '0'（チェックあり） の場合
    if (searchCondMst0101d01.getCancelData().equalsIgnoreCase(
        MstConst.CHECK_OFF)) {
      formMst0101d01.setChkTorikeshiData(true);
    } else {
      // [入力]検索条件保持クラス.取消データを対象とする ＝ '1'（チェックなし） の場合
      formMst0101d01.setChkTorikeshiData(false);
    }

    // [入力]状態（ロック中） ＝ '0'（チェックあり） の場合
    if (searchCondMst0101d01.getUserStatus().equalsIgnoreCase(
        MstConst.CHECK_OFF)) {
      formMst0101d01.setChkLock(true);
    } else {
      formMst0101d01.setChkLock(false);
    }
  }

  /**
   * 検索条件の通りに、検索結果を取得する.
   * @param formMst0101d01:フォーム
   * @return テーブルのデータを戻る
   */
  public List<MstUserInfoMst0101d02> getSearchResult(
      FormMst0101d01 formMst0101d01) {
    List<MstUserInfoMst0101d02> lstMstUserInfoMst0101d2 = new ArrayList<MstUserInfoMst0101d02>();

    try {
      String strJigyoshoCode;
      if (formMst0101d01.getSysAdminFlag().equalsIgnoreCase(
          MstConst.SYS_ADMIN_FLG_VALID)) {
        strJigyoshoCode = formMst0101d01.getDdlShozoku();
      } else {
        strJigyoshoCode = formMst0101d01.getLoginJigyouShoCode();
      }

      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("YMD", formMst0101d01.getBusinessDate());
      params.put("Jigyosho_Code", strJigyoshoCode);
      params.put("User_Code", formMst0101d01.getTxtUserCode());
      String strUserName = "";
      if (!formMst0101d01.getTxtUserName().equalsIgnoreCase("")) {
        strUserName = "%" + formMst0101d01.getTxtUserName() + "%";
      }
      params.put("User_Nm", strUserName);
      params.put("Auth_Code", formMst0101d01.getDdlRiyoKengen());
      params.put("STS_Code", formMst0101d01.isChkTorikeshiData());
      params.put("User_Status", formMst0101d01.isChkLock());
      String searchMax = "TOP " + String.valueOf(formMst0101d01.getSearchMax()
          + 1);
      params.put("Search_Max", searchMax);

      lstMstUserInfoMst0101d2 = mst0101d01Dao.getMst0101d01Mapper()
          .getSearchResult(params);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstUserInfoMst0101d2;
  }

  /**
   * 担当者マスタから担当者情報を取得する.
   * @param strUserCode:担当者コード
   * @param strJigyoCode:事業所コード
   * @return UserData 担当者情報
   */
  public UserData getUserName(String strUserCode, String strJigyoCode) {
    UserData userData = null;
    try {
      CommonGetData commonGetData = new CommonGetData(commonDao, null);
      userData = commonGetData.getUserData(strUserCode, strJigyoCode);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return userData;
  }

  /**
   * 排他の設定.
   * @param formMst0101d01:フォーム
   */
  public void setHaitaDate(FormMst0101d01 formMst0101d01) {
    formMst0101d01.setHaitaDate(DateUtil.getSysDate());
    formMst0101d01.setHaitaTime(DateUtil.getSysTime());
  }
  
  /**
   * CSV Export 発生したCSVファイル.
   * @param formMst0101d01:フォーム
   * @return　画面表示
   * @throws Exception　エラー画面
   */
  public String exportCsvData(FormMst0101d01 formMst0101d01) throws Exception {

    String strResult = "";
    DateFormat dateFormat = new SimpleDateFormat(
        CommonConst.DATE_FORMAT_YMDHMS);
    Date date = new Date();
    String currentTime = dateFormat.format(date);
    // ファイル名
    String pathFile = "Mst01-01d011_" + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;

    String strJigyoshoCode;
    if (formMst0101d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      strJigyoshoCode = formMst0101d01.getDdlShozoku();
    } else {
      strJigyoshoCode = formMst0101d01.getLoginJigyouShoCode();
    }

    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");

    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("YMD", formMst0101d01.getBusinessDate());
      params.put("Jigyosho_Code", strJigyoshoCode);
      params.put("User_Code", formMst0101d01.getTxtUserCode());
      String strUserName = "";
      if (!formMst0101d01.getTxtUserName().equalsIgnoreCase("")) {
        strUserName = "%" + formMst0101d01.getTxtUserName() + "%";
      }
      params.put("User_Nm", strUserName);
      params.put("Auth_Code", formMst0101d01.getDdlRiyoKengen());
      params.put("STS_Code", formMst0101d01.isChkTorikeshiData());
      params.put("User_Status", formMst0101d01.isChkLock());
      params.put("Search_Max", "");

      Object[] fileHeader = {"No", "担当者コード", "担当者氏名", "利用権限", "所属事業所", "部署名",
          "電話番号", "FAX番号", "状態"};
      csvFilePrinter.printRecord(fileHeader);

      SeqProc seqProc = new SeqProc(csvFilePrinter);

      mst0101d01Dao.getMst0101d01Mapper().getSearchResult(params, seqProc);
      if (seqProc.isEmpty) {
        return null;
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      try {
        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();
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

      MstUserInfoMst0101d02 userInfo = (MstUserInfoMst0101d02) context
          .getResultObject();

      try {
        printer.print(intRenban);
        printer.print(Util.convertUnsanitize(userInfo.getUserCode()));
        printer.print(Util.convertUnsanitize(userInfo.getUserNm()));
        printer.print(Util.convertUnsanitize(userInfo.getTargetAuth()));
        printer.print(Util.convertUnsanitize(userInfo.getJgymei()));
        printer.print(Util.convertUnsanitize(userInfo.getUserPost()));
        printer.print(Util.convertUnsanitize(userInfo.getTelNo()));
        printer.print(Util.convertUnsanitize(userInfo.getFaxNo()));
        printer.print(Util.convertUnsanitize(userInfo.getTargetUserStatus()));

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
