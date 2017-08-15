/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0103d01Service.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu 新規開発
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstStoreInfoMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0103d01Dao;

/**
 * サービスクラス
 * 
 * @author TramChu
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0103d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0103d01Dao")
  private Mst0103d01Dao mst0103d01Dao;
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  // DAOの定義
  public Mst0103d01Dao getMst0103d01Dao() {
    return mst0103d01Dao;
  }

  public void setMst0103d01Dao(Mst0103d01Dao mst0103d01Dao) {
    this.mst0103d01Dao = mst0103d01Dao;
  }

  /**
   * Defaultメッセージの取得
   * 
   * @param model モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<DefaultMessages> defaultMsgLst;
    defaultMsgLst = new ArrayList<DefaultMessages>();
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * 検索条件入力エリアを初期化する 条件
   * 
   * @param formMst0103d01Back form back
   * @param formMst0103d01 form
   */
  public void initScreenWithConditional(FormMst0103d01 formMst0103d01Back,
      FormMst0103d01 formMst0103d01) {
    formMst0103d01.setDdlOffice(formMst0103d01Back.getDdlOffice());
    formMst0103d01.setTxtCustomerCode(formMst0103d01Back.getTxtCustomerCode());
    formMst0103d01.setTxtCustomerName(formMst0103d01Back.getTxtCustomerName());
    formMst0103d01.setTxtStoreCode(formMst0103d01Back.getTxtStoreCode());
    formMst0103d01.setTxtStoreName(formMst0103d01Back.getTxtStoreName());
    formMst0103d01.setChkCancelData(formMst0103d01Back.getChkCancelData());
    formMst0103d01.setViewMode(formMst0103d01Back.getViewMode());
  }

  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する.
   * 
   * @param formMst0103d01 form
   * @param model model
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setDataOffice(FormMst0103d01 formMst0103d01, Model model) {
    ArrayList<ObjCombobox> lstMstSJigyo = new ArrayList<ObjCombobox>();
    ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = new ArrayList<MstSJigyoInfo>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("businessDate", formMst0103d01.getBusinessDate());
    lstMstSJigyoInfo = mst0103d01Dao.getMst0103d01Mapper().getSJigyoInfo(
        params);
    if (lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objCombobox = new ObjCombobox();
      objCombobox.setCode("");
      objCombobox.setName("");
      lstMstSJigyo.add(objCombobox);
      for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
        objCombobox = new ObjCombobox();
        objCombobox.setCode(mstSJigyoInfo.getJgycd());
        objCombobox.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
            .getJgymei());
        lstMstSJigyo.add(objCombobox);
      }
      model.addAttribute("lstMstSJigyo", lstMstSJigyo);

      return true;
    }
    return false;
  }

  /**
   * get list search
   * 
   * @param formMst0103d01 form
   * @return list search
   */
  public ArrayList<MstStoreInfoMst0103d01> getSearchResult(
      FormMst0103d01 formMst0103d01) {
    ArrayList<MstStoreInfoMst0103d01> lstMstStoreInfoMst0103d01 = null;
    String strSystemAdminFlg = formMst0103d01.getSysAdminFlag();
    String loginJigyoshoCode = formMst0103d01.getLoginJigyouShoCode();
    // 店舗マスタデータ取得
    try {
      String jigyoshoCode = "";
      if (strSystemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
        jigyoshoCode = loginJigyoshoCode;
      } else if (strSystemAdminFlg.equalsIgnoreCase(
          CommonConst.SYS_ADMIN_FLG_VALID)) {
        jigyoshoCode = formMst0103d01.getDdlOffice();
      }
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", formMst0103d01.getBusinessDate().toString());
      String searchMax = "";
      searchMax = "TOP " + String.valueOf(formMst0103d01.getSearchMax() + 1);
      params.put("searchMax", searchMax);
      if (!jigyoshoCode.equalsIgnoreCase("")) {
        params.put("ddlOffice", jigyoshoCode);
      }
      if (!formMst0103d01.getTxtCustomerCode().equalsIgnoreCase("")) {
        params.put("txtCustomerCode", formMst0103d01.getTxtCustomerCode());
      }
      if (!formMst0103d01.getTxtStoreCode().equalsIgnoreCase("")) {
        params.put("txtStoreCode", formMst0103d01.getTxtStoreCode());
      }
      if (!formMst0103d01.getTxtCustomerName().equalsIgnoreCase("")) {
        params.put("txtCustomerName", "%" + formMst0103d01.getTxtCustomerName()
            + "%");
      }
      if (!formMst0103d01.getTxtStoreName().equalsIgnoreCase("")) {
        params.put("txtStoreName", "%" + formMst0103d01.getTxtStoreName()
            + "%");
      }
      if (!formMst0103d01.getChkCancelData()) {
        params.put("chkCancelData", formMst0103d01.getChkCancelData());

      }
      lstMstStoreInfoMst0103d01 = mst0103d01Dao.getMst0103d01Mapper()
          .getMstStoreInfoMst0103d01(params);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstMstStoreInfoMst0103d01;
  }

  /**
   * CSV Export 発生したCSVファイル
   * 
   * @param formMst0103d01 form
   * @return 画面表示
   * @throws Exception エラー画面
   */
  public String exportCsvData(FormMst0103d01 formMst0103d01)
      throws Exception {
    String strResult = "";
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    // ファイル名
    String pathFile = "Mst01-03d011_" + currentDate + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // CSVファイルを作成する
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");

    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    Object[] fileHeadear = {"No", "得意先コード", "得意先略称", "店舗コード", "店舗名称",
        "店舗名称カナ", "店舗略称", "郵便番号", "住所１", "住所２", "電話番号", "FAX番号", "事業所コード",
        "事業所名", "集約先店舗区分", "店舗集約条件", "納品センターコード", "サンクス区分", "集約店舗コード",
        "社店コード（定番・店直）", "社店コード（定番・センター）", "社店コード（特売・店直）", "社店コード（特売・センター）",
        "取引中止日", "状況コード", "登録ユーザID", "登録プログラムID", "登録年月日", "登録時刻", "修正ユーザID",
        "修正プログラムID", "修正年月日", "修正時刻"};
    csvFilePrinter.printRecord(fileHeadear);
    SeqProc seqProc = new SeqProc(csvFilePrinter);
    // 店舗マスタデータ取得
    try {
      String jigyoshoCode = "";
      String strSystemAdminFlg = formMst0103d01.getSysAdminFlag();
      String loginJigyoshoCode = formMst0103d01.getLoginJigyouShoCode();
      if (strSystemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
        jigyoshoCode = loginJigyoshoCode;
      } else if (strSystemAdminFlg.equalsIgnoreCase(
          CommonConst.SYS_ADMIN_FLG_VALID)) {
        jigyoshoCode = formMst0103d01.getDdlOffice();
      }
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("searchMax", "");
      params.put("businessDate", formMst0103d01.getBusinessDate().toString());
      if (!jigyoshoCode.equalsIgnoreCase("")) {
        params.put("ddlOffice", jigyoshoCode);
      }
      if (!formMst0103d01.getTxtCustomerCode().equalsIgnoreCase("")) {
        params.put("txtCustomerCode", formMst0103d01.getTxtCustomerCode());
      }
      if (!formMst0103d01.getTxtStoreCode().equalsIgnoreCase("")) {
        params.put("txtStoreCode", formMst0103d01.getTxtStoreCode());
      }
      if (!formMst0103d01.getTxtCustomerName().equalsIgnoreCase("")) {
        params.put("txtCustomerName", "%" + formMst0103d01.getTxtCustomerName()
            + "%");
      }
      if (!formMst0103d01.getTxtStoreName().equalsIgnoreCase("")) {
        params.put("txtStoreName", "%" + formMst0103d01.getTxtStoreName()
            + "%");
      }
      if (!formMst0103d01.getChkCancelData()) {
        params.put("chkCancelData", formMst0103d01.getChkCancelData());

      }     
      mst0103d01Dao.getMst0103d01Mapper().getMstStoreInfoMst0103d01(params,
          seqProc);
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
   * convert list to Json
   * 
   * @param mstStoreInfoMst0103d01Lst list convert
   * @return Json
   * @throws JsonProcessingException エラー画面
   */
  public String convertJson(
      ArrayList<MstStoreInfoMst0103d01> mstStoreInfoMst0103d01Lst)
          throws JsonProcessingException {
    String jsonData = "";
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    jsonData = ow.writeValueAsString(mstStoreInfoMst0103d01Lst);
    return jsonData;
  }

  /**
   * 排他の設定.
   * 
   * @param formMst0103d01 form
   */
  public void setHaitaDate(FormMst0103d01 formMst0103d01) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    formMst0103d01.setHaitaDate(currentDate);
    formMst0103d01.setHaitaTime(currentTime);
  }

  /**
   * SQL実行結果を1レコードずつ処理するための内部クラス
   *
   * @author TramChu
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

      MstStoreInfoMst0103d01 storeInfo = (MstStoreInfoMst0103d01) context
          .getResultObject();

      try {
        printer.print(renban);
        printer.print(storeInfo.getCustCode());
        printer.print(storeInfo.getCustNmR());
        printer.print(storeInfo.getShopCode());
        printer.print(storeInfo.getShopNm());
        printer.print(storeInfo.getShopNmKana());
        printer.print(storeInfo.getShopNmR());
        printer.print(storeInfo.getZipCode());
        printer.print(storeInfo.getAdr1());
        printer.print(storeInfo.getAdr2());
        printer.print(storeInfo.getTelNo());
        printer.print(storeInfo.getFaxNo());
        printer.print(storeInfo.getJigyoCode());
        printer.print(storeInfo.getjgymei());
        printer.print(storeInfo.getSumShopKb());
        printer.print(storeInfo.getSumShopJkn());
        printer.print(storeInfo.getDeliCenterCode());
        printer.print(storeInfo.getSunksKb());
        printer.print(storeInfo.getSumShopCode());
        printer.print(storeInfo.getstCodeSts());
        printer.print(storeInfo.getstCodeStc());
        printer.print(storeInfo.getstCodeSps());
        printer.print(storeInfo.getstCodeSpc());
        printer.print(storeInfo.getCloseDate());
        printer.print(storeInfo.getstsCode());
        printer.print(storeInfo.getinsuserId());
        printer.print(storeInfo.getinspgId());
        printer.print(storeInfo.getinsymd());
        printer.print(storeInfo.getinstime());
        printer.print(storeInfo.getupduserId());
        printer.print(storeInfo.getupdpgId());
        printer.print(storeInfo.getupdymd());
        printer.print(storeInfo.getupdtime());
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