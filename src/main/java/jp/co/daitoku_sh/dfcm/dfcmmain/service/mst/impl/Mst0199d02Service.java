/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0199d02Service.java
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

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.CastData0199d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0199d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0199d00Dao;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.Gen0199d02Data;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d02;

/**
 * コントローラクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0199d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0199d00Dao")
  private Mst0199d00Dao mst0199d00Dao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;
  
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * 管理レコード情報を取得する.
   * 
   * @param formMst0199d02 A model attribute
   */
  public void search(FormMst0199d02 formMst0199d02) {
    List<Gen0199d02Data> list = getGeneralData(formMst0199d02.getGlCode());
    if (list != null && list.size() > 0) {
      formMst0199d02.setDataList(list);
    }
  }

  /**
   * [関数]管理レコードデータ取得（[入力]区分）.
   * 
   * @param glCode [入力]区分
   * @return 管理レコードデータ
   */
  public List<Gen0199d02Data> getGeneralData(String glCode) {
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Gl_Code", glCode);
    List<Gen0199d02Data> list = mst0199d00Dao.getMst0199d00Mapper().getDataList(parms);
    return list;
  }

  /**
   * 汎用区分設定一覧情報を取得する.
   * 
   * @param glCode [入力]区分
   * @return 汎用区分設定一覧情報を
   */
  public ArrayList<RstMst0199d02> getRecordData(String glCode) {
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Gl_Code", glCode);
    ArrayList<RstMst0199d02> recordList = mst0199d00Dao.getMst0199d00Mapper()
        .getRstMst0199d02Data(parms);
    return recordList;
  }
  
  /**
   * ＜ヘッダー＞.
   * 
   * @param formMst0199d02 A model attribute
   * @param model A holder for model attributes
   * @param record 汎用マスタ
   */
  public void initScreenHeader(FormMst0199d02 formMst0199d02, Model model,
      RstMst0199d02 record) {
    // [入力]画面表示モード ＝ 1（照会） の場合
    // ＜編集エリア＞
    model.addAttribute("glKb", formMst0199d02.getGlCode()); // [画面]区分
    model.addAttribute("glKbNm", record.getKbNm()); // [画面]区分名称
    formMst0199d02.setCodeDigit(record.getCodeDigit());
    if (record.getCodeDigit() != null && record.getCodeDigit().compareTo(BigDecimal.ZERO) != 0) {
      model.addAttribute("codeNm", record.getCodeNm()); // [画面]コード名称
      if (record.getCodeAttr().equalsIgnoreCase(MstConst.CHARACTER)) {
        model.addAttribute("codeAttr", "文字"); // [画面]コード属性
      } else if (record.getCodeAttr().equalsIgnoreCase(MstConst.NUMERICAL_VALUE)) {
        model.addAttribute("codeAttr", "数値"); // [画面]コード属性
      }
      model.addAttribute("codeDigit", record.getCodeDigit()); // [画面]コード桁数
    } else if (record.getCodeDigit() != null && record.getCodeDigit().compareTo(
        BigDecimal.ZERO) == 0) {
      model.addAttribute("codeNm", "コード無し"); // [画面]コード名称
    }
    if (formMst0199d02.getViewMode().equalsIgnoreCase(MstConst.SHOUKAI_MODE)) {
      model.addAttribute("category", record.getCategoryName()); // [画面]汎用分類
    } else {
      model.addAttribute("category", record.getCategory()); // [画面]汎用分類
    }
    
    model.addAttribute("dispNumeric", record.getDispNumeric()); // [画面]表示順
    // '[変数]管理レコード情報格納クラス.更新可能フラグ ＝ '1'（更新可能）の場合
    if (record.getUpdPermitFlg().equalsIgnoreCase(MstConst.CHECK_ON)) {
      formMst0199d02.setUpdPermitFlg(true);
    }

    // '[変数]管理レコード情報格納クラス.更新可能フラグ ＝ '0'（更新不可）の場合
    if (record.getUpdPermitFlg().equalsIgnoreCase(MstConst.CHECK_OFF)) {
      formMst0199d02.setUpdPermitFlg(false);
    }

    // '[変数]管理レコード情報格納クラス.追加可能フラグ ＝ '1'（追加可能）の場合
    if (record.getInsPermitFlg().equalsIgnoreCase(MstConst.CHECK_ON)) {
      formMst0199d02.setInsPermitFlg(true);
    }

    // '[変数]管理レコード情報格納クラス.追加可能フラグ ＝ '0'（追加不可）の場合
    if (record.getInsPermitFlg().equalsIgnoreCase(MstConst.CHECK_OFF)) {
      formMst0199d02.setInsPermitFlg(false);
    }

    // '[変数]管理レコード情報格納クラス.削除可能フラグ ＝ '1'（削除可能）の場合
    if (record.getDelPermitFlg().equalsIgnoreCase(MstConst.CHECK_ON)) {
      formMst0199d02.setDelPermitFlg(true);
    }

    // '[変数]管理レコード情報格納クラス.削除可能フラグ ＝ '0'（削除不可）の場合
    if (record.getDelPermitFlg().equalsIgnoreCase(MstConst.CHECK_OFF)) {
      formMst0199d02.setDelPermitFlg(false);
    }
  }
  
  /**
   * ＜編集エリア＞.
   * 
   * @param formMst0199d02 A model attribute
   * @param model A holder for model attributes
   * @param record 汎用マスタ
   */
  public void initEditArea(FormMst0199d02 formMst0199d02, Model model,
      RstMst0199d02 record) {
    int numColumn = 0;
    if (record.getTarget1() != null && !record.getTarget1().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle1(record.getTarget1());
      model.addAttribute("title1", record.getTarget1());
      numColumn ++;
    }
    if (record.getTarget2() != null && !record.getTarget2().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle2(record.getTarget2());
      model.addAttribute("title2", record.getTarget2());
      numColumn ++;
    }
    if (record.getTarget3() != null && !record.getTarget3().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle3(record.getTarget3());
      model.addAttribute("title3", record.getTarget3());
      numColumn ++;
    }
    if (record.getTarget4() != null && !record.getTarget4().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle4(record.getTarget4());
      model.addAttribute("title4", record.getTarget4());
      numColumn ++;
    }
    if (record.getTarget5() != null && !record.getTarget5().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle5(record.getTarget5());
      model.addAttribute("title5", record.getTarget5());
      numColumn ++;
    }
    if (record.getTarget6() != null && !record.getTarget6().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle6(record.getTarget6());
      model.addAttribute("title6", record.getTarget6());
      numColumn ++;
    }
    if (record.getTarget7() != null && !record.getTarget7().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle7(record.getTarget7());
      model.addAttribute("title7", record.getTarget7());
      numColumn ++;
    }
    if (record.getTarget8() != null && !record.getTarget8().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle8(record.getTarget8());
      model.addAttribute("title8", record.getTarget8());
      numColumn ++;
    }
    if (record.getTarget9() != null && !record.getTarget9().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle9(record.getTarget9());
      model.addAttribute("title9", record.getTarget9());
      numColumn ++;
    }
    if (record.getTarget10() != null && !record.getTarget10().equalsIgnoreCase(CommonConst.EMPTY)) {
      formMst0199d02.setTitle10(record.getTarget10());
      model.addAttribute("title10", record.getTarget10());
      numColumn ++;
    }
    model.addAttribute("numColumn", numColumn);
    if (numColumn > 0) {
      model.addAttribute("colWidth", String.valueOf(90 / numColumn) + "%");
    }
    model.addAttribute("viewMode", formMst0199d02.getViewMode());
    if (formMst0199d02.isInsPermitFlg()) {
      formMst0199d02.setEditMode(MstConst.ADD_MODE); // （追加）
    } else {
      formMst0199d02.setEditMode(MstConst.UPDATE_MODE); // （更新）
    }
    if (formMst0199d02.getViewMode().equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      formMst0199d02.setTargetAttr1(record.getTargetAttr1());
      formMst0199d02.setTargetAttr2(record.getTargetAttr2());
      formMst0199d02.setTargetAttr3(record.getTargetAttr3());
      formMst0199d02.setTargetAttr4(record.getTargetAttr4());
      formMst0199d02.setTargetAttr5(record.getTargetAttr5());
      formMst0199d02.setTargetAttr6(record.getTargetAttr6());
      formMst0199d02.setTargetAttr7(record.getTargetAttr7());
      formMst0199d02.setTargetAttr8(record.getTargetAttr8());
      formMst0199d02.setTargetAttr9(record.getTargetAttr9());
      formMst0199d02.setTargetAttr10(record.getTargetAttr10());

      formMst0199d02.setTargetDigit1(record.getTargetDigit1());
      formMst0199d02.setTargetDigit2(record.getTargetDigit2());
      formMst0199d02.setTargetDigit3(record.getTargetDigit3());
      formMst0199d02.setTargetDigit4(record.getTargetDigit4());
      formMst0199d02.setTargetDigit5(record.getTargetDigit5());
      formMst0199d02.setTargetDigit6(record.getTargetDigit6());
      formMst0199d02.setTargetDigit7(record.getTargetDigit7());
      formMst0199d02.setTargetDigit8(record.getTargetDigit8());
      formMst0199d02.setTargetDigit9(record.getTargetDigit9());
      formMst0199d02.setTargetDigit10(record.getTargetDigit10());

      formMst0199d02.setTargetExp1(record.getTargetExp1());
      formMst0199d02.setTargetExp2(record.getTargetExp2());
      formMst0199d02.setTargetExp3(record.getTargetExp3());
      formMst0199d02.setTargetExp4(record.getTargetExp4());
      formMst0199d02.setTargetExp5(record.getTargetExp5());
      formMst0199d02.setTargetExp6(record.getTargetExp6());
      formMst0199d02.setTargetExp7(record.getTargetExp7());
      formMst0199d02.setTargetExp8(record.getTargetExp8());
      formMst0199d02.setTargetExp9(record.getTargetExp9());
      formMst0199d02.setTargetExp10(record.getTargetExp10());

      model.addAttribute("targetExp1", record.getTargetExp1());
      model.addAttribute("targetExp2", record.getTargetExp2());
      model.addAttribute("targetExp3", record.getTargetExp3());
      model.addAttribute("targetExp4", record.getTargetExp4());
      model.addAttribute("targetExp5", record.getTargetExp5());
      model.addAttribute("targetExp6", record.getTargetExp6());
      model.addAttribute("targetExp7", record.getTargetExp7());
      model.addAttribute("targetExp8", record.getTargetExp8());
      model.addAttribute("targetExp9", record.getTargetExp9());
      model.addAttribute("targetExp10", record.getTargetExp10());
    }

  }
  
  /**
   * Defaultメッセージの取得.
   * 
   * @param model A holder for model attributes
   */
  public void getDefaultMess(Model model) {
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E1");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", null));
    ArrayList<DefaultMessages> defaultMessages = new ArrayList<DefaultMessages>();
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E2");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", null));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM027-E");
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess = new ArrayList<String>();
    paramMess.add("コード");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM027-E", paramMess));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST001-I");
    paramMess = new ArrayList<String>();
    paramMess.add("汎用区分設定一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST001-I", paramMess));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST002-I");
    paramMess = new ArrayList<String>();
    paramMess.add("汎用区分設定一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST002-I", paramMess));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I1");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I2");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMessages.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM001-E", null));
    defaultMessages.add(defaultMsg);
    
    model.addAttribute("defaultMessages", defaultMessages);
  }
  
  /**
   * (登録画面)登録ボタン.
   * 
   * @param castData0199d02 A non-empty description.
   * @param userCode 担当者コード
   */
  public void register(CastData0199d02 castData0199d02, String userCode) {
    DefaultTransactionDefinition def = null;
    TransactionStatus status = null;

    Map<String, Object> parms = new HashMap<String, Object>();
    // [変数]コードWK
    String glCodeWk = "";
    // [画面]汎用区分設定一覧
    ArrayList<Gen0199d02Data> list = castData0199d02.getDataList();
    if (checkInput(list, castData0199d02)) {
      return;
    }
    // [画面]区分
    String glKb = castData0199d02.getGlKb();
    ArrayList<MstGeneral> generals = null;
    MstGeneral general = null;
    MstGeneral record = null;
    ArrayList<String> deleteCodeList = new ArrayList<String>();

    for (Gen0199d02Data data : list) {
      if (data.getGlCode().equalsIgnoreCase("---")) {
        glCodeWk = MstConst.FIXED_VALUE;
      } else {
        glCodeWk = data.getGlCode();
      }
      parms = new HashMap<String, Object>();
      parms.put("Gl_Code", glCodeWk);
      parms.put("Gl_Kb", glKb);
      try {
        generals = mst0199d00Dao.getMst0199d00Mapper().searchGeneralData(parms);
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }
      int haitaDate = Integer.parseInt(castData0199d02.getHaitaDate());
      int haitaTime = Integer.parseInt(castData0199d02.getHaitaTime());
      if (generals != null && generals.size() > 0) {
        general = generals.get(0);
        if (haitaDate < Integer.parseInt(general.getUpdYmd())) {
          // レコードでも排他エラーとなった場合は、処理を終了する。
          castData0199d02.setMessage(readPropertiesFileService
              .getMessage("COM031-E", null));
          castData0199d02.setDataList(null);
          return;
        } else if (haitaDate == Integer.parseInt(general.getUpdYmd())) {
          if (haitaTime < Integer.parseInt(general.getUpdTime())) {
            // レコードでも排他エラーとなった場合は、処理を終了する。
            castData0199d02.setMessage(readPropertiesFileService
                .getMessage("COM031-E", null));
            castData0199d02.setDataList(null);
            return;
          }
        }
      }
    }

    int numRecordUpdate = 0;
    try {
      def = new DefaultTransactionDefinition();
      status = txManager.getTransaction(def);
      for (Gen0199d02Data data : list) {
        if (data.getGlCode().equalsIgnoreCase("---")) {
          glCodeWk = MstConst.FIXED_VALUE;
        } else {
          glCodeWk = data.getGlCode();
        }
        parms = new HashMap<String, Object>();
        parms.put("Gl_Code", glCodeWk);
        parms.put("Gl_Kb", glKb);
        generals = mst0199d00Dao.getMst0199d00Mapper().searchGeneralData(parms);
        if (generals != null && generals.size() > 0) {
          general = generals.get(0);
          if (data.getUpdateFlg().equalsIgnoreCase(MstConst.FLAG_ENABLE)) {
            // 該当レコードが存在する場合、汎用マスタレコードを更新する。
            record = setGeneralRecord(data, general, userCode, "MST01-99D02");

            mst0199d00Dao.getMstGeneralMapper().updateByPrimaryKeySelective(
                record);
            numRecordUpdate++;

          }
          if (data.getDeleteFlg().equalsIgnoreCase(MstConst.FLAG_ENABLE)) {
            deleteCodeList.add(data.getGlCode());
          }
        } else {
          if (data.getAddFlg().equalsIgnoreCase(MstConst.FLAG_ENABLE)) {
            record = newGeneralRecord(data, userCode, "MST01-99D02", glCodeWk,
                glKb, castData0199d02
                    .getGlKbNm());
            mst0199d00Dao.getMstGeneralMapper().insert(record);
            numRecordUpdate++;
          }
        }
      }
      // 【削除コード削除処理】
      if (deleteCodeList != null && deleteCodeList.size() > 0) {
        parms.clear();
        parms.put("Gl_Kb", glKb);
        parms.put("deleteCodeList", deleteCodeList);
        mst0199d00Dao.getMst0199d00Mapper().deleteCode(parms);
        numRecordUpdate++;
      }
      txManager.commit(status);
    } catch (MyBatisSystemException e) {
      txManager.rollback(status);
      logger.error(e.getMessage());
      throw e;
    }

    if (numRecordUpdate > 0) {
      // 正常処理
      ArrayList<String> paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタの登録");
      castData0199d02.setMessage(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      setHaitaDate(castData0199d02);
    } else {
      castData0199d02.setMessage(readPropertiesFileService.getMessage(
          "COM034-E", null));
      castData0199d02.setDataList(null);
    }
  }
  
  /**
   * Check input for the item when register data.
   * 
   * @param list オンライン得意先変換マスタ一覧
   * @return  true if the error occurs; false otherwise
   */
  private boolean checkInput(ArrayList<Gen0199d02Data> list,
      CastData0199d02 castData0199d02) {
    String errorCode = "";
    String errorMsg = "";
    ArrayList<String> paramMsg = null;
    Gen0199d02Data gen0199d02Data = null;
    String line = "";
    for (int i = 0; i < list.size(); i++) {
      line = String.valueOf(i + 1);
      gen0199d02Data = list.get(i);
      // コード
      errorCode = InputCheckCom.chkEmpty(gen0199d02Data.getGlCode());
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("コード");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }
      
      String title = null;
      String itemValue = null;
      String targetAttr = null;
      int targetDigit = -1;
      for (int colIndex = 1; colIndex <= 10; colIndex ++) {
        title = castData0199d02.getTitle(colIndex);
        if (title != null && !title.equalsIgnoreCase("")) {
          targetAttr = castData0199d02.getTargetAttr(colIndex);
          targetDigit = new BigDecimal(castData0199d02.getTargetDigit(colIndex)).intValue();
          itemValue = gen0199d02Data.getTarget(colIndex);
          errorMsg += checkItem(colIndex, itemValue, title, line, targetAttr, targetDigit);
        }
      }
    }

    if (errorMsg != null && !errorMsg.equalsIgnoreCase("")) {
      castData0199d02.setMessage(errorMsg);
      castData0199d02.setDataList(null);
      return true;
    }
    return false;
  }
  
  /**
   * 入力チェック処理.
   * 
   * @param colIndex The column index
   * @param itemValue 項目(値)
   * @param title The column title
   * @param line The line
   * @param targetAttr 項目（属性） 
   * @param targetDigit 項目（桁数）
   * @return The error messages
   */
  private String checkItem(int colIndex, String itemValue, String title,
      String line, String targetAttr, int targetDigit) {
    String errorCode = "";
    String errorMsg = "";
    ArrayList<String> paramMsg = null;
    errorCode = InputCheckCom.chkEmpty(itemValue);
    if (errorCode != null) {
      errorCode = returnMessageCode(errorCode);
      paramMsg = new ArrayList<String>();
      paramMsg.add(title);
      paramMsg.add(line);
      errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
          + "<br>";
      return errorMsg;
    }

    if (targetAttr.equalsIgnoreCase(MstConst.NUMERICAL_VALUE) && InputCheckCom.chkNum(itemValue,
        false) != null) {
      paramMsg = new ArrayList<String>();
      paramMsg.add("[画面]" + title + "数値で入力してください");
      errorMsg += readPropertiesFileService.getMessage("MST013-E", paramMsg)
          + "<br>";
      return errorMsg;
    }

    int length = itemValue.length();
    if (targetDigit < length) {
      paramMsg = new ArrayList<String>();
      paramMsg.add("[画面]" + title + "（" + targetDigit + "）以内で入力してください");
      errorMsg += readPropertiesFileService.getMessage("MST013-E", paramMsg)
          + "<br>";
      return errorMsg;
    }

    return "";
  }
  /**
   * Replaces the error code.
   * 
   * @param errorCode エラーコード
   * @return The new error code
   */
  private String returnMessageCode(String errorCode) {
    if (errorCode.equalsIgnoreCase("COM001-E")) {
      errorCode = "COM036-E";
    }
    
    if (errorCode.equalsIgnoreCase("COM006-E")) {
      errorCode = "COM035-E";
    }
    return errorCode;
  }
  /**
   * 排他の設定.
   * 
   * @param castData0199d02:フォーム
   */
  public void setHaitaDate(CastData0199d02 castData0199d02) {
    castData0199d02.setHaitaDate(DateUtil.getSysDate());
    castData0199d02.setHaitaTime(DateUtil.getSysTime());
  }
  /**
   * 共通項目の設定.
   * 
   * @param gen0199d02Data A non-empty description.
   * @param record 汎用マスタ
   * @param userId 担当者コード
   * @param pgId プログラムID
   * @return 汎用マスタ
   */
  private MstGeneral setGeneralRecord(Gen0199d02Data gen0199d02Data, MstGeneral record,
      String userId, String pgId) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    record.setTarget1(gen0199d02Data.getTarget1());
    record.setTarget2(gen0199d02Data.getTarget2());
    record.setTarget3(gen0199d02Data.getTarget3());
    record.setTarget4(gen0199d02Data.getTarget4());
    record.setTarget5(gen0199d02Data.getTarget5());
    record.setTarget6(gen0199d02Data.getTarget6());
    record.setTarget7(gen0199d02Data.getTarget7());
    record.setTarget8(gen0199d02Data.getTarget8());
    record.setTarget9(gen0199d02Data.getTarget9());
    record.setTarget10(gen0199d02Data.getTarget10());

    record.setUpdUserid(userId);
    record.setUpdPgid(pgId);
    record.setUpdYmd(currentDate);
    record.setUpdTime(currentTime);
    return record;
  }

  /**
   * 共通項目の設定.
   * 
   * @param gen0199d02Data A non-empty description.
   * @param userId 担当者コード
   * @param pgId プログラムID
   * @param glCodeWk コード
   * @param glKb 区分
   * @param kbNm 区分名
   * @return 汎用マスタ
   */
  public MstGeneral newGeneralRecord(Gen0199d02Data gen0199d02Data, String userId, String pgId,
      String glCodeWk, String glKb, String kbNm) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    MstGeneral record = new MstGeneral();
    record.setGlCode(glCodeWk);
    record.setGlKb(glKb);
    record.setKbNm(kbNm);
    record.setTarget1(gen0199d02Data.getTarget1());
    record.setTarget2(gen0199d02Data.getTarget2());
    record.setTarget3(gen0199d02Data.getTarget3());
    record.setTarget4(gen0199d02Data.getTarget4());
    record.setTarget5(gen0199d02Data.getTarget5());
    record.setTarget6(gen0199d02Data.getTarget6());
    record.setTarget7(gen0199d02Data.getTarget7());
    record.setTarget8(gen0199d02Data.getTarget8());
    record.setTarget9(gen0199d02Data.getTarget9());
    record.setTarget10(gen0199d02Data.getTarget10());

    record.setInsUserid(userId);
    record.setInsPgid(pgId);
    record.setInsYmd(currentDate);
    record.setInsTime(currentTime);
    record.setUpdUserid(userId);
    record.setUpdPgid(pgId);
    record.setUpdYmd(currentDate);
    record.setUpdTime(currentTime);
    return record;
  }
}
