/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0104d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)NhungMa 新規開発
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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCSVMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInfoMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0104d01Dao;

/**
 * サービスクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0104d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0104d01Dao")
  private Mst0104d01Dao mst0104d01Dao;

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

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  // DAOの定義
  public Mst0104d01Dao getMst0401d01Dao() {
    return mst0104d01Dao;
  }

  public void setMst0104d01Dao(Mst0104d01Dao mst0104d01Dao) {
    this.mst0104d01Dao = mst0104d01Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }
  
  /**
   * Defaultメッセージの取得.
   * @param model:モデル
   * @throws Exception エラー画面
   */
  public void getDefaultMess(Model model) throws Exception {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    
    DefaultMessages defaultMsg = new DefaultMessages();
    // 確認メッセージを画面に表示する
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM001-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    paramMess = new ArrayList<String>();
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // MST013-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E");
    paramMess = new ArrayList<String>();
    paramMess.add("配送区分は１または２で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));

    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  
  /**
   * list data of MstSJigyo.
   * @param formMst0104d01:フォーム
   * @return list MstSJigyo
   */
  private List<MstSJigyo> listSJigyo(FormMst0104d01 formMst0104d01) {
    List<MstSJigyo> lstMstJigy = null;
    try {
      MstSJigyoExample mstSJigyoExample = new MstSJigyoExample();
      MstSJigyoExample.Criteria criteria = mstSJigyoExample.createCriteria();
      criteria.andDelflgEqualTo(MstConst.DEL_FLAG);
      criteria.andStrymdLessThanOrEqualTo(Integer.parseInt(formMst0104d01
          .getBusinessDate().toString()));
      criteria.andEndymdGreaterThanOrEqualTo(Integer.parseInt(formMst0104d01
          .getBusinessDate().toString()));
      mstSJigyoExample.setOrderByClause("JGYCD");
      lstMstJigy = mst0104d01Dao.getMstSJgyoMapper().selectByExample(
          mstSJigyoExample);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstJigy;
  }

  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する。
   * @param formMst0104d01:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo(FormMst0104d01 formMst0104d01, Model model) {
    ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
    List<MstSJigyo> lstMstSJigyoInfo = new ArrayList<MstSJigyo>();
    lstMstSJigyoInfo = listSJigyo(formMst0104d01);
    if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objectCombobox = new ObjCombobox();
      objectCombobox.setCode("");
      objectCombobox.setName("");
      lstMstSJigyoInfoReturn.add(objectCombobox);
      for (MstSJigyo mstSJigyoInfo : lstMstSJigyoInfo) {
        objectCombobox = new ObjCombobox();
        objectCombobox.setCode(mstSJigyoInfo.getJgycd());
        objectCombobox.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
            .getJgymei());
        lstMstSJigyoInfoReturn.add(objectCombobox);
      }
      model.addAttribute("JigyoCodeClassList", lstMstSJigyoInfoReturn);
      return true;
    }
    return false;
  }

  /**
   * 排他の設定.
   * @param formMst0104d01:フォーム
   */
  public void setHaitaDate(FormMst0104d01 formMst0104d01) {
    formMst0104d01.setHaitaDate(DateUtil.getSysDate());
    formMst0104d01.setHaitaTime(DateUtil.getSysTime());
  }
  
  /**
   * コース一覧作成.
   * @param formMst0104d01:フォーム
   * @return テーブルのデータを戻る
   */
  public List<MstCourseInfoMst0104d01> getSearchResult(
      FormMst0104d01 formMst0104d01) {
    List<MstCourseInfoMst0104d01> lstMstCourse0104d01 = new ArrayList<MstCourseInfoMst0104d01>();
    // [セッション]システム管理者フラグ
    String systemAdminFlag = formMst0104d01.getSysAdminFlag();
    // 一般ユーザ
    String loginJigyoCode = formMst0104d01.getLoginJigyouShoCode();
    try {
      String jigyoCode;
      if (systemAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
        jigyoCode = formMst0104d01.getDdlJigyoCode();
      } else {
        jigyoCode = loginJigyoCode;
      }

      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      String searchMax = "TOP " + String.valueOf(formMst0104d01.getSearchMax()
          + 1);
      params.put("searchMax", searchMax);
      params.put("businessDate", formMst0104d01.getBusinessDate());
      params.put("jigyoCode", jigyoCode);
      params.put("courseCode", formMst0104d01.getTxtCourseCode());
      if (!formMst0104d01.getTxtCourseName().equalsIgnoreCase("")) {
        params.put("courseName", "%" + formMst0104d01.getTxtCourseName() + "%");
      }
      params.put("haisoKb", formMst0104d01.getTxtHaisoKb());
      params.put("chkCancellationData", formMst0104d01.isChkCancellationData());
      lstMstCourse0104d01 = mst0104d01Dao.getMst0104d01Mapper()
          .searchCourseList(params);

    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstCourse0104d01;
  }

  /**
   * 検索条件入力エリアを初期化する 条件：検索条件保持クラス == NULL
   * @param loginJigyoshoCode:システム管理者フラグ
   * @param formMst0104d01:フォーム
   */
  public void initScreenNoConditional(FormMst0104d01 formMst0104d01) {
    formMst0104d01.setTxtCourseCode(null);
    formMst0104d01.setTxtCourseName(null);
    formMst0104d01.setTxtHaisoKb(null);
    if (formMst0104d01.getSysAdminFlag().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      // [セッション]ログイン所属事業所コード
      formMst0104d01.setDdlJigyoCode(formMst0104d01.getLoginJigyouShoCode());
    }
    formMst0104d01.setChkCancellationData(false);
  }

  /**
   * 検索条件入力エリアを初期化する 条件：検索条件保持クラス !＝ NULL
   * @param searchCondMst0104d01:検索条件
   * @param formMst0104d01:フォーム
   */
  public void initScreenWithConditional(SearchCondMst0104d01 searchCondMst0104d01,
      FormMst0104d01 formMst0104d01) {
    formMst0104d01.setDdlJigyoCode(searchCondMst0104d01.getJigyoCode());
    formMst0104d01.setTxtCourseCode(searchCondMst0104d01.getCourseCode());
    formMst0104d01.setTxtCourseName(searchCondMst0104d01.getCourseName());
    formMst0104d01.setTxtHaisoKb(searchCondMst0104d01.getHaisoKb());
    // [入力]検索条件保持クラス.取消データを対象とする ＝ '0'（チェックあり） の場合
    if (searchCondMst0104d01.getCancelData().equalsIgnoreCase(
        MstConst.CHECK_OFF)) {
      formMst0104d01.setChkCancellationData(true);
    } else {
      // [入力]検索条件保持クラス.取消データを対象とする ＝ '1'（チェックなし） の場合
      formMst0104d01.setChkCancellationData(false);
    }
  }
  
  /**
   * CSV Export 発生したCSVファイル.
   * @param formMst0104d01:フォーム
   * @return 画面表示
   * @throws Exception:エラー画面
   */
  public String exportDataCsv(FormMst0104d01 formMst0104d01) throws Exception {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    // ファイル名
    String pathFile = "Mst01-04d011_" + currentDate + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;

    String strJigyoCode;
    if (formMst0104d01.getSysAdminFlag().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      strJigyoCode = formMst0104d01.getDdlJigyoCode();
    } else {
      strJigyoCode = formMst0104d01.getLoginJigyouShoCode();
    }

    // CSVファイルを作成する
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);

    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(folderFile),
        "Windows-31J");
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    Object[] fileHeader = {"No", "事業所コード", "事業所名", "コースコード", "コース名称",
        "コース略称", "配送時間", "出荷更新時間", "配送区分", "配送区分名称", "状況コード",
        "得意先コード", "得意先名称", "店舗コード", "店舗名称", "便区分", "納品区分", "納品区分名称"};
    csvFilePrinter.printRecord(fileHeader);
    SeqProc seqProc = new SeqProc(csvFilePrinter);
    
    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", String.valueOf(formMst0104d01.getBusinessDate()));
      params.put("jigyoCode", strJigyoCode);
      params.put("courseCode", formMst0104d01.getTxtCourseCode());
      if (!formMst0104d01.getTxtCourseName().equalsIgnoreCase("")) {
        params.put("courseName", "%" + formMst0104d01.getTxtCourseName() + "%");
      }
      params.put("haisoKb", formMst0104d01.getTxtHaisoKb());
      params.put("chkCancellationData", formMst0104d01.isChkCancellationData());
      
      mst0104d01Dao.getMst0104d01Mapper().getSearchResultCsv(params, seqProc);
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
    String resultCsv = "";
    resultCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD")
        + pathFile;
    return resultCsv;
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

    /** CSVプリンタ */
    private CSVPrinter printer;

    /** 処理結果が空 */
    private boolean isEmpty;

    /** 連番 */
    private int renban;

    /**
     * コンストラクタ
     *
     * @param nengetsudo 年月
     * @param printer CSVプリンタ
     */
    public SeqProc(CSVPrinter printer) {
      this.printer = printer;
      this.setEmpty(true);
      renban = 0;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する
     *
     * @param context リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      renban++;
      this.setEmpty(false);

      MstCSVMst0104d01 courseInfo = (MstCSVMst0104d01) context
          .getResultObject();

      try {
        printer.print(renban);
        printer.print(Util.convertUnsanitize(courseInfo.getJigyoCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getJgyMei()));
        printer.print(Util.convertUnsanitize(courseInfo.getCosCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getCosNm()));
        printer.print(Util.convertUnsanitize(courseInfo.getCosNmR()));
        printer.print(Util.convertUnsanitize(courseInfo.getHaisoTime()));
        printer.print(Util.convertUnsanitize(courseInfo.getShipUpdtTime()));
        printer.print(Util.convertUnsanitize(courseInfo.getHaisoKb()));
        printer.print(Util.convertUnsanitize(courseInfo.getShippingTypeName()));
        printer.print(Util.convertUnsanitize(courseInfo.getStsCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getCustCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getCustNm()));
        printer.print(Util.convertUnsanitize(courseInfo.getShopCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getShopNm()));
        printer.print(Util.convertUnsanitize(courseInfo.getBinKb()));
        printer.print(Util.convertUnsanitize(courseInfo.getDeliKb()));
        printer.print(Util.convertUnsanitize(courseInfo
            .getDeliveryDivisionName()));

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