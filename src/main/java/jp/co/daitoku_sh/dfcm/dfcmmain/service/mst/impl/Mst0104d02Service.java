/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0104d02Service.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourseExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourseKey;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourseList;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourseListExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourseListKey;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShopExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.CastData0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.CrsLst0104d02Data;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCusJgyo0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstDeliveryMst0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0104d02Dao;

/**
 * サービスクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0104d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0104d02Dao")
  private Mst0104d02Dao mst0104d02Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private ApplicationContext appContext;

  /** トランザクション Commit / Rollback. */
  PlatformTransactionManager txManager;

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  // DAOの定義
  public Mst0104d02Dao getMst0401d02Dao() {
    return mst0104d02Dao;
  }

  public void setMst0104d02Dao(Mst0104d02Dao mst0104d02Dao) {
    this.mst0104d02Dao = mst0104d02Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * 一覧画面から条件値を設定する。（hiddenで保存した）
   * 
   * @param formMst0104d02:フォーム
   * @param searchCondMst0104d01:一覧画面から条件値
   * @param viewMode:モード値
   */
  public void init(FormMst0104d02 formMst0104d02,
      SearchCondMst0104d01 searchCondMst0104d01, String viewMode) {
    // [入力]検索条件保持クラスを退避する
    formMst0104d02.setForm1JigyoCode(searchCondMst0104d01.getJigyoCode());
    formMst0104d02.setForm1CourseCode(searchCondMst0104d01.getCourseCode());
    formMst0104d02.setForm1CourseName(searchCondMst0104d01.getCourseName());
    formMst0104d02.setForm1HaisoKb(searchCondMst0104d01.getHaisoKb());
    formMst0104d02.setForm1CancelData(searchCondMst0104d01.getCancelData());
    formMst0104d02.setMode(viewMode);
  }

  /**
   * Defaultメッセージの取得
   * 
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    DefaultMessages defaultMsg = new DefaultMessages();
    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsg.setMessageCode("COM001-I01");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E01");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("配送区分は１または２で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E02");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("状況コードは１または９で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E03");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("便区分は１から９の間で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E04");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("状況コードは１または９で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM030-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("店舗コード");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM030-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM027-E");
    paramMess = new ArrayList<String>();
    paramMess.add("納入先");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM027-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    // message add success
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST001-I");
    paramMess = new ArrayList<String>();
    paramMess.add("納入先一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    // message update success
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST002-I");
    paramMess = new ArrayList<String>();
    paramMess.add("納入先一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST002-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I02");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    // 画面のデータが変更されていない場合のメッセージを作成する
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", null));
    
    defaultMsgLst.add(defaultMsg);
    
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * 事業所マスタから事業所情報を取得する
   * @param model:モデル
   * @param formMst0104d02:フォーム
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setMstSJigyo(Model model, FormMst0104d02 formMst0104d02) {
    List<MstSJigyo> lstJgy = null;
    MstSJigyoExample jgyExp = new MstSJigyoExample();
    MstSJigyoExample.Criteria criteria = jgyExp.createCriteria();
    Integer businessDate = formMst0104d02.getBusinessDate();

    ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();

    try {
      criteria.andDelflgEqualTo(MstConst.DEL_FLAG);
      criteria.andStrymdLessThanOrEqualTo(businessDate);
      criteria.andEndymdGreaterThanOrEqualTo(businessDate);
      if (!formMst0104d02.getSysAdminFlag().equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
        criteria.andJgycdEqualTo(formMst0104d02.getLoginJigyouShoCode());
      }
      jgyExp.setOrderByClause("JGYCD");
      lstJgy = mst0104d02Dao.getMstSJgyoMapper().selectByExample(jgyExp);
      if (lstJgy != null && lstJgy.size() > MstConst.SIZE_ZERO) {
        ObjCombobox objectCombobox = new ObjCombobox();
        objectCombobox.setCode("");
        objectCombobox.setName("");
        lstMstSJigyoInfoReturn.add(objectCombobox);
        for (MstSJigyo mstSJigyoInfo : lstJgy) {
          objectCombobox = new ObjCombobox();
          objectCombobox.setCode(mstSJigyoInfo.getJgycd());
          objectCombobox.setName(mstSJigyoInfo.getJgycd() + " : "
              + mstSJigyoInfo
                  .getJgymei());
          lstMstSJigyoInfoReturn.add(objectCombobox);
        }
        model.addAttribute("JigyoCodeClassList", lstMstSJigyoInfoReturn);
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
   * 汎用マスタから納品区分情報を取得する
   * 
   * @param model:モデル
   * @param formMst0104d02:フォーム
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setDeliveryGen(Model model, FormMst0104d02 formMst0104d02) {
    List<MstGeneralData> lstGen = null;

    ArrayList<ObjCombobox> lstMstGenInfoReturn = new ArrayList<ObjCombobox>();
    CommonGetSystemCom sysCom = new CommonGetSystemCom(getCommonDao(), null,
        null, readPropertiesFileService);
    try {
      lstGen = sysCom.getMstGeneralConf("Deli_Kb", null);
      if (lstGen != null && lstGen.size() > MstConst.SIZE_ZERO) {
        ObjCombobox objectCombobox = new ObjCombobox();
        objectCombobox.setCode("");
        objectCombobox.setName("");
        lstMstGenInfoReturn.add(objectCombobox);
        for (MstGeneralData mstGenInfo : lstGen) {
          objectCombobox = new ObjCombobox();
          objectCombobox.setCode(mstGenInfo.getGlCode());
          objectCombobox.setName(mstGenInfo.getGlCode() + " : " + mstGenInfo
              .getTarget1());
          lstMstGenInfoReturn.add(objectCombobox);
        }
        model.addAttribute("GenClassList", lstMstGenInfoReturn);
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
   * コース情報取得
   * 
   * @param jigyoCode:事業所
   * @param mstCourseCode:コースコード
   * @return MstCourse
   */
  public MstCourse keyMstCourse(String jigyoCode, String courseCode) {
    MstCourse csr = null;
    MstCourseKey key = new MstCourseKey();
    try {
      key.setJigyoCode(jigyoCode);
      key.setCosCode(courseCode);
      csr = mst0104d02Dao.getMstCourseMapper().selectByPrimaryKey(key);

    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return csr;
  }

  /**
   * get key of MstCourseList table
   * 
   * @param jigyoCode:事業所
   * @param cosCode:コースコード
   * @param custCode:得意先コード
   * @param shopCode:店舗コード
   * @param binKb:便区分
   * @return MstCourseList
   */
  private MstCourseList keyMstCourseList(String jigyoCode, String cosCode,
      String custCode,
      String shopCode, String binKb) {
    MstCourseList crl = null;
    MstCourseListKey key = new MstCourseListKey();
    try {
      key.setJigyoCode(jigyoCode);
      key.setCosCode(cosCode);
      key.setCustCode(custCode);
      key.setShopCode(shopCode);
      key.setBinKb(binKb);
      crl = mst0104d02Dao.getMstCourseListMapper().selectByPrimaryKey(key);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return crl;
  }

  /**
   * set data when click クリアボタン_フッダー button
   * 
   * @param formMst0104d02:フォーム
   * @param mstCourse:MstCourse
   */
  public void setViewDataClearFooter(FormMst0104d02 formMst0104d02,
      MstCourse mstCourse) {
    formMst0104d02.setDdlJigyoCode(mstCourse.getJigyoCode());
    formMst0104d02.setTxtCourseCode(mstCourse.getCosCode());
    formMst0104d02.setTxtCourseName(mstCourse.getCosNm());
    formMst0104d02.setTxtCourseNameR(mstCourse.getCosNmR());

    String[] arrHaisoShipTime;
    arrHaisoShipTime = setArr(mstCourse.getHaisoTime());
    if (arrHaisoShipTime != null) {
      formMst0104d02.setTxtHaisoTime01(arrHaisoShipTime[0]);
      formMst0104d02.setTxtHaisoTime02(arrHaisoShipTime[1]);
    }

    arrHaisoShipTime = setArr(mstCourse.getShipUpdtTime());
    if (arrHaisoShipTime != null) {
      formMst0104d02.setTxtShipUpdateTime01(arrHaisoShipTime[0]);
      formMst0104d02.setTxtShipUpdateTime02(arrHaisoShipTime[1]);
    }

    formMst0104d02.setTxtHaisoKb(mstCourse.getHaisoKb());
    formMst0104d02.setTxtStsCode01(mstCourse.getStsCode());
  }

  /**
   * 照会以外画面は値を設定する
   * 
   * @param model:モデル
   * @param viewMode:モード値
   * @param formMst0104d02:フォーム
   * @param mstCourse:MstCourse
   */
  public void setView(Model model, String viewMode,
      FormMst0104d02 formMst0104d02, MstCourse mstCourse) {
    if (mstCourse != null) {
      if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
        formMst0104d02.setDdlJigyoCode(mstCourse.getJigyoCode());
        formMst0104d02.setTxtCourseCode(mstCourse.getCosCode());
        formMst0104d02.setTxtCourseName(mstCourse.getCosNm());
        formMst0104d02.setTxtCourseNameR(mstCourse.getCosNmR());

        String[] arrHaisoShipTime;
        if (mstCourse.getHaisoTime() != null) {
          arrHaisoShipTime = setArr(mstCourse.getHaisoTime());
          if (arrHaisoShipTime != null) {
            formMst0104d02.setTxtHaisoTime01(arrHaisoShipTime[0].trim());
            formMst0104d02.setTxtHaisoTime02(arrHaisoShipTime[1].trim());
          }
        }
        if (mstCourse.getShipUpdtTime() != null) {
          arrHaisoShipTime = setArr(mstCourse.getShipUpdtTime());
          if (arrHaisoShipTime != null) {
            formMst0104d02.setTxtShipUpdateTime01(arrHaisoShipTime[0].trim());
            formMst0104d02.setTxtShipUpdateTime02(arrHaisoShipTime[1].trim());
          }
        }

        formMst0104d02.setTxtHaisoKb(mstCourse.getHaisoKb().trim());
        if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
          formMst0104d02.setTxtStsCode01(MstConst.TORIKESHI);
        } else {
          formMst0104d02.setTxtStsCode01(mstCourse.getStsCode());
        }
        List<MstDeliveryMst0104d02> listDelivery = new ArrayList<MstDeliveryMst0104d02>();
        listDelivery = searchDelivery(formMst0104d02);
        if (listDelivery != null) {
          for (MstDeliveryMst0104d02 item : listDelivery) {
            item.setShopCode(item.getShopCode().trim());
          }
          model.addAttribute("lstDelivery", listDelivery);
        }
      }
     }
  }

  /**
   * 配送時間と出荷更新時間はSplitする
   * @param strInput:string
   * @return String[] ２部分
   */
  private String[] setArr(String strInput) {
    String[] arrStrOutput = null;
    if (strInput != null && !strInput.equalsIgnoreCase("")) {
      arrStrOutput = strInput.split(":", 2);
    }

    return arrStrOutput;
  }
 
  /**
   * コース納入先明細マスタから検索条件に合致する納入先一覧情報を取得する
   * @param formMst0104d02:フォーム
   * @return list
   */
  private List<MstDeliveryMst0104d02> searchDelivery(
      FormMst0104d02 formMst0104d02) {
    List<MstDeliveryMst0104d02> lstDeli = new ArrayList<MstDeliveryMst0104d02>();
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("jigyoCode", formMst0104d02.getDdlJigyoCode());
      params.put("cosCode", formMst0104d02.getTxtCourseCode());
      params.put("businessDate", String.valueOf(formMst0104d02.getBusinessDate()));
      lstDeli = mst0104d02Dao.getMst0104d02Mapper().searchDeliveryList(params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstDeli;
  }

  /**
   * check input when register data
   * @param castData :class contain data from screen
   * @return Boolean false: 普通 true: エラーが発生する。
   */
  public boolean checkInputRegister(CastData0104d02 castData) {
    String strError;
    String strErrorId = "";
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // 事業所
    if (castData.getJigyoCode().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlJigyoCode" + MstConst.DELIMITER_ERROR;
    }
    // コースコード
    strError = checkItem(castData.getCourseCode(), true,
        InputCheckCom.TYPE_NUM, 5);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("コースコード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCourseCode" + MstConst.DELIMITER_ERROR;
    }
    // コース名称
    strError = checkItem(castData.getCourseName(), true,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("コース名称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCourseName" + MstConst.DELIMITER_ERROR;
    }

    // コース略称
    strError = checkItem(castData.getCourseNameR(), true,
        InputCheckCom.TYPE_EM, 10);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("コース略称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCourseNameR" + MstConst.DELIMITER_ERROR;
    }
    // 状況コード
    strError = checkItem(castData.getStsCode01(), true,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStsCode01" + MstConst.DELIMITER_ERROR;
    }

    // [画面]状況コード ≠ NULL and ([画面]状況コード ≠ '1' or [画面]状況コード ≠ '9')
    // の場合エラーとし、次の通り処理を行う。
    if (!castData.getStsCode01().equalsIgnoreCase("") && (!castData.getStsCode01().equals(
        MstConst.TOROKU)
        && !castData.getStsCode01().equals(MstConst.TORIKESHI))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コードは１または９で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStsCode01" + MstConst.DELIMITER_ERROR;
    }

    // 配送時間 
    strError = checkItem(castData.getHaisoTime(), false,
        InputCheckCom.TYPE_NUM, 4);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("配送時間");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtHaisoTime01" + MstConst.DELIMITER_ERROR
          + "txtHaisoTime02" + MstConst.DELIMITER_ERROR;
    }
    // 出荷更新時間
    strError = checkItem(castData.getShipUpdTime(), false,
        InputCheckCom.TYPE_NUM, 4);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("出荷更新時間");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtShipUpdateTime01" + MstConst.DELIMITER_ERROR
          + "txtShipUpdateTime02" + MstConst.DELIMITER_ERROR;
    }
    // 配送区分
    if (!castData.getHaisoKb().equals("") && (!castData.getHaisoKb().equals(
        MstConst.JISHA_HAISO)
        && !castData.getHaisoKb().equals(MstConst.TAKUHAIBIN))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("配送区分は１または２で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtHaisoKb" + MstConst.DELIMITER_ERROR;
    }
    ArrayList<CrsLst0104d02Data> listDataCrs = castData.getDataList();
    String error = "";
    String line = "";
    int i = 0;
    for (CrsLst0104d02Data item : listDataCrs) {
      i++;
      line = String.valueOf(i);
      // 得意先コード
      strError = checkItem(item.getCustCode(), true,
          InputCheckCom.TYPE_NUM, 7);
      if (strError != null) {
        strError = returnMessageCode(strError);
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先コード");
        paramMess.add(line);
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
      // 店舗コード
      if (!item.getShopCode().equalsIgnoreCase("---")) {
        strError = checkItem(item.getShopCode(), true,
            InputCheckCom.TYPE_NUM, 8);
        if (strError != null) {
          strError = returnMessageCode(strError);
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("店舗コード");
          paramMess.add(line);
          errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
              paramMess));
          lstErrMess.add(errMess);
          error = "error";
        }
      }
      // 納品区分
      strError = checkItem(item.getDeliKb(), true,
          InputCheckCom.TYPE_NUM, 1);
      if (strError != null) {
        strError = returnMessageCode(strError);
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("納品区分");
        paramMess.add(line);
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
      // 状況コード
      strError = checkItem(item.getStsCode(), true,
          InputCheckCom.TYPE_NUM, 1);
      if (strError != null) {
        strError = returnMessageCode(strError);
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("状況コード");
        paramMess.add(line);
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
      // 便区分
      strError = checkItem(item.getBinKb().replace("便", ""), true,
          InputCheckCom.TYPE_NUM, 1);
      if (strError != null) {
        strError = returnMessageCode(strError);
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("便区分");
        paramMess.add(line);
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
      if (!item.getBinKb().replace("便", "").equalsIgnoreCase("") && (Integer.valueOf(item
          .getBinKb().replace("便", "")) < MstConst.CHECK_BINKB_VALUE_1 || Integer.valueOf(item
              .getBinKb().replace("便", "")) > MstConst.CHECK_BINKB_VALUE_9)) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("便区分は１から９の間で入力してください");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "MST013-E",
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
      // 状況コード check format
      if (!item.getStsCode().equalsIgnoreCase("") && (!item.getStsCode()
          .equalsIgnoreCase(MstConst.TOROKU) && !item.getStsCode()
              .equalsIgnoreCase(MstConst.TORIKESHI))) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("状況コードは１または９で入力してください");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "MST013-E",
            paramMess));
        lstErrMess.add(errMess);
        error = "error";
      }
    }

    if (!strErrorId.equalsIgnoreCase("") || !error.equalsIgnoreCase("")) {
      castData.setErrorMess(lstErrMess);
      castData.setStrId(strErrorId);
      return true;
    }
    return false;
  }
  
  /**
   * 存在チェック
   * @param formMst0104d02:フォーム
   */
  public void checkExistEditArea(FormMst0104d02 formMst0104d02) {
    String strMessage = "";
    String strId = "";
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    if (!formMst0104d02.getTxtCustomerCode().equalsIgnoreCase("")) {
      List<MstCusJgyo0104d02> lstCus = new ArrayList<MstCusJgyo0104d02>();
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("customerCode", formMst0104d02.getTxtCustomerCode());
      params.put("jigyoCode", formMst0104d02.getDdlJigyoCode());
      lstCus = mst0104d02Dao.getMst0104d02Mapper().searchCustomer(params);

      // 該当レコードが存在する場合、
      if (lstCus.size() > 0) {
        formMst0104d02.setLblCustomerNameR(lstCus.get(0).getCustNmR());
        strMessage += "lblCustomerNameR" + MstConst.DELIMITER_ERROR;
        if (lstCus.get(0).getShopKb().equalsIgnoreCase(MstConst.SHOPKB_HAS_SHOP) && formMst0104d02
            .getTxtShopCode().equalsIgnoreCase("")) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("店舗コード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM006-E",
              paramMess));
          lstErrMess.add(errMess);
          strId += "txtShopCode" + MstConst.DELIMITER_ERROR;
        } else if (lstCus.get(0).getShopKb().equalsIgnoreCase(MstConst.SHOPKB_HASNOT_SHOP)
            && !formMst0104d02.getTxtShopCode().equalsIgnoreCase("")) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("なし");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "MST014-E",
              paramMess));
          lstErrMess.add(errMess);
          strId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
        }
      } else { // 該当レコードが存在しない場合、
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタ");
        paramMess.add("入力された得意先");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        lstErrMess.add(errMess);
        strId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
        formMst0104d02.setLblCustomerNameR("");
        strMessage += "lblCustomerNameR" + MstConst.DELIMITER_ERROR;
      }
    }
    if (!formMst0104d02.getTxtCustomerCode().equalsIgnoreCase("")
        && !formMst0104d02.getTxtShopCode().equalsIgnoreCase("")) {
      String paramCustCode = formMst0104d02.getTxtCustomerCode();
      String paramShopCode = formMst0104d02.getTxtShopCode();
      List<MstShop> lstShp = new ArrayList<MstShop>();
      MstShopExample shpExp = new MstShopExample();
      shpExp.createCriteria().andCustCodeEqualTo(paramCustCode)
          .andShopCodeEqualTo(paramShopCode);
      lstShp = mst0104d02Dao.getMstShopMapper().selectByExample(shpExp);
      if (lstShp.size() > MstConst.SIZE_ZERO) {
        formMst0104d02.setLblShopNameR(lstShp.get(0).getShopNmR());
        strMessage += "lblShopNameR" + MstConst.DELIMITER_ERROR;
      } else {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        lstErrMess.add(errMess);
        strId += "txtShopCode" + MstConst.DELIMITER_ERROR;
        formMst0104d02.setLblShopNameR("");
        strMessage += "lblShopNameR" + MstConst.DELIMITER_ERROR;
      }
    }
    if (lstErrMess.size() > MstConst.SIZE_ZERO) {
      formMst0104d02.setErrorMessage(lstErrMess);
      formMst0104d02.setStrId(strId);
    }
    if (!strMessage.equalsIgnoreCase("")) {
      formMst0104d02.setMsgExist(strMessage);
    }
  }

  /**
   * 入力チェック
   * @param formMst0104d02:フォーム
   */
  public void checkExistHeader(FormMst0104d02 formMst0104d02) {
    String strId = "";
    if (!formMst0104d02.getDdlJigyoCode().equals("") && !formMst0104d02
        .getTxtCourseCode().equals("")) {
      List<MstCourse> lstCourse = null;
      MstCourseExample courseExp = new MstCourseExample();
      courseExp.createCriteria().andJigyoCodeEqualTo(formMst0104d02
          .getDdlJigyoCode())
          .andCosCodeEqualTo(formMst0104d02.getTxtCourseCode());
      lstCourse = mst0104d02Dao.getMstCourseMapper().selectByExample(courseExp);
      if (lstCourse.size() > MstConst.SIZE_ZERO) {
        final ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
        ErrorMessages errMess = new ErrorMessages();
        // メッセージのParam
        ArrayList<String> paramMess = new ArrayList<String>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("コース情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM027-E",
            paramMess));
        lstErrMess.add(errMess);
        formMst0104d02.setErrorMessage(lstErrMess);
        strId = "ddlJigyoCode" + MstConst.DELIMITER_ERROR + "txtCourseCode"
            + MstConst.DELIMITER_ERROR;
        formMst0104d02.setStrId(strId);
      } else {
        formMst0104d02.setStrId("NOT_EXIST");
      }
    }
  }

  /**
   * 入力されたコース情報をDBへ登録及び更新を行う
   * @param castData0104d02 :class contain data from screen
   * @param userCode:ログインユーザコード
   * @return string
   * @throws Exception エラー画面
   */
  public String register(CastData0104d02 castData0104d02, String userCode) throws Exception {
    MstCourse mstCourse = null;
    MstCourse recordCrs = null;
    MstCourseList recordCrl = null;
    boolean resultCheck = true;
    ArrayList<CrsLst0104d02Data> list = castData0104d02.getDataList();
    String err = "";
    ErrorMessages errMess = new ErrorMessages();
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    String mode = castData0104d02.getMode();
    // 画面表示モード ＝ 1 の場合、入力項目の存在チェックを行う（キー重複チェック）
    if (mode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      if (!castData0104d02.getJigyoCode().equalsIgnoreCase("")
          && !castData0104d02.getCourseCode().equalsIgnoreCase("")) {
        mstCourse = keyMstCourse(castData0104d02.getJigyoCode(), castData0104d02
            .getCourseCode());
        // 該当レコードが存在する場合、エラーメッセージ表示
        if (mstCourse != null) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("コース情報");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM027-E",
              paramMess));
          castData0104d02.setErrorMessage(errMess);
          // 共通部品を使って、エラー時の画面制御を行う
          castData0104d02.setErrorControl("errorControl");
          err = "error";
          return err;
        } else {
          recordCrs = newCourseRecord(castData0104d02, userCode, "MST01-04D02");
          // コースマスタ登録
          try {
            status = txManager.getTransaction(def);
            mst0104d02Dao.getMstCourseMapper().insert(recordCrs);
            txManager.commit(status);
          } catch (MyBatisSystemException e) {
            txManager.rollback(status);
            logger.error(e.getMessage());
            throw e;
          }
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          for (CrsLst0104d02Data data : list) {
            List<MstCourseList> lstCrl = null;
            String shopCodeWk = "";
            String configCode = readPropertiesFileService.getSetting(
                "TEN_CODE_NONE");
            if (data.getShopCode().equalsIgnoreCase("---")) {
              shopCodeWk = configCode;
            } else if (!data.getShopCode().equalsIgnoreCase("---")) {
              shopCodeWk = data.getShopCode();
            }
            String binKb = data.getBinKb().replace("便", "");
            lstCrl = getCourseList(castData0104d02.getJigyoCode(), castData0104d02
                .getCourseCode(),
                data.getCustCode(), shopCodeWk, binKb);
            if (lstCrl.size() > 0) {
              errMess = new ErrorMessages();
              paramMess = new ArrayList<String>();
              paramMess.add(data.getRenBan());
              errMess.setMessageContent(readPropertiesFileService.getMessage(
                  "MST016-E",
                  paramMess));
              castData0104d02.setErrorMessage(errMess);
              err = "error";
              return err;
            } else {
              recordCrl = newCourseListRecord(castData0104d02, data, userCode,
                  "MST01-04D02");
              // 該当レコードが存在しない場合、コース納入先明細マスタに登録を行う。
              try {
                mst0104d02Dao.getMstCourseListMapper().insert(recordCrl);
              } catch (MyBatisSystemException e) {
                resultCheck = false;
                txManager.rollback(status);
                logger.error(e.getMessage());
                throw e;
              }
            }
          }
          if (resultCheck) {
            txManager.commit(status);
          }
        }
      }
      // 画面表示モード ＝ 3 の場合、コース情報を訂正更新する
    } else if (mode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      // 排他チェックを行う。
      if (checkHaitaCrs(castData0104d02.getJigyoCode(), castData0104d02
          .getCourseCode(), castData0104d02.getHaitaDate(), castData0104d02
              .getHaitaTime())) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        castData0104d02.setErrorMessage(errMess);
        err = "ERROR_CHECK_HAITA";
        return err;
      }
      for (CrsLst0104d02Data dataCheckHaita : list) {
        
        if (dataCheckHaita.getUpdateFlg().equalsIgnoreCase(MstConst.UPDATE_FLAG)
            && dataCheckHaita.getAddFlg().equalsIgnoreCase(MstConst.ADD_FLAG)) {
          String binKbHaita = dataCheckHaita.getBinKb().replace("便", "");
          if (checkHaitaCrl(castData0104d02.getJigyoCode(), castData0104d02
              .getCourseCode(), dataCheckHaita.getCustCode(), dataCheckHaita
                  .getShopCode(), binKbHaita,
              castData0104d02.getHaitaDate(), castData0104d02.getHaitaTime())) {
            errMess = new ErrorMessages();
            errMess.setMessageContent(readPropertiesFileService.getMessage(
                "COM031-E", null));
            castData0104d02.setErrorMessage(errMess);
            err = "ERROR_CHECK_HAITA";
            return err;
          }
        }
      }
      MstCourse course = null;
      course = keyMstCourse(castData0104d02.getJigyoCode(), castData0104d02
          .getCourseCode());
      if (course != null) {
        recordCrs = setCourseRecord(castData0104d02, course, userCode,
            "MST01-04D02");
        try {
          status = txManager.getTransaction(def);
          mst0104d02Dao.getMstCourseMapper().updateByPrimaryKeySelective(
              recordCrs);
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          txManager.rollback(status);
          logger.error(e.getMessage());
          throw e;
        }
      }
      def = new DefaultTransactionDefinition();
      status = txManager.getTransaction(def);
      for (CrsLst0104d02Data data : list) {
        if (!data.getUpdateFlg().equalsIgnoreCase(MstConst.UPDATE_FLAG_NOT)) {
          List<MstCourseList> lstCrl = null;
          MstCourseList courseItem = null;
          String shopCodeWk = "";
          String configCode = readPropertiesFileService.getSetting(
              "TEN_CODE_NONE");
          if (data.getShopCode().equalsIgnoreCase("---")) {
            shopCodeWk = configCode;
          } else if (!data.getShopCode().equalsIgnoreCase("---")) {
            shopCodeWk = data.getShopCode();
          }
          String binKbCrl = data.getBinKb().replace("便", "");
          lstCrl = getCourseList(castData0104d02.getJigyoCode(), castData0104d02
              .getCourseCode(),
              data.getCustCode(), shopCodeWk, binKbCrl);
          // 該当レコードが存在しない場合、コース納入先明細マスタに登録を行う。
          if (lstCrl == null || lstCrl.size() <= MstConst.SIZE_ZERO) {
            recordCrl = newCourseListRecord(castData0104d02, data, userCode,
                "MST01-04D02");
            try {
              mst0104d02Dao.getMstCourseListMapper().insert(recordCrl);
            } catch (Exception e) {
              resultCheck = false;
              txManager.rollback(status);
              logger.error(e.getMessage());
              throw e;
            }
            // 該当レコードが存在する場合、コース納入先明細マスタレコードを更新する。
          } else if (lstCrl != null || lstCrl.size() > MstConst.SIZE_ZERO) {
            courseItem = lstCrl.get(0);
            recordCrl = setCourseListRecord(castData0104d02, data, courseItem,
                userCode, "MST01-04D02");
            try {
              mst0104d02Dao.getMstCourseListMapper()
                  .updateByPrimaryKeySelective(recordCrl);
            } catch (Exception e) {
              txManager.rollback(status);
              logger.error(e.getMessage());
              throw e;
            }
          }
        }
      }
      if (resultCheck) {
         txManager.commit(status);
      }
    } else if (mode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      // 排他チェックを行う。
      if (checkHaitaCrs(castData0104d02.getJigyoCode(), castData0104d02
          .getCourseCode(), castData0104d02.getHaitaDate(), castData0104d02
              .getHaitaTime())) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        castData0104d02.setErrorMessage(errMess);
        err = "ERROR_CHECK_HAITA";
        return err;
      }
      for (CrsLst0104d02Data dataCheckHaita : list) {
        String binKbHaitaCrl = dataCheckHaita.getBinKb().replace("便", "");
        if (checkHaitaCrl(castData0104d02.getJigyoCode(), castData0104d02
            .getCourseCode(), dataCheckHaita.getCustCode(), dataCheckHaita
                .getShopCode(), binKbHaitaCrl,
            castData0104d02.getHaitaDate(), castData0104d02.getHaitaTime())) {
          errMess = new ErrorMessages();
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM031-E", null));
          castData0104d02.setErrorMessage(errMess);
          err = "ERROR_CHECK_HAITA";
          return err;
        }
      }
      MstCourse course = null;
      course = keyMstCourse(castData0104d02.getJigyoCode(), castData0104d02
          .getCourseCode());
      if (course != null) {
        course.setStsCode(MstConst.TORIKESHI);
        course = setCommonDataCrs(course, userCode, "MST01-04D02", false);
        // コースマスタ取消更新
        try {
          status = txManager.getTransaction(def);
          mst0104d02Dao.getMstCourseMapper().updateByPrimaryKey(course);
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          txManager.rollback(status);
          logger.error(e.getMessage());
          throw e;
        }
      }
      def = new DefaultTransactionDefinition();
      status = txManager.getTransaction(def);
      // 納入先一覧の内容にて、コース納入先明細マスタを更新する。
      for (CrsLst0104d02Data data : list) {
        MstCourseList courseItem = null;
        String shopCodeWk = "";
        String configCode = readPropertiesFileService.getSetting(
            "TEN_CODE_NONE");
        if (data.getShopCode().equalsIgnoreCase("---")) {
          shopCodeWk = configCode;
        } else if (!data.getShopCode().equalsIgnoreCase("---")) {
          shopCodeWk = data.getShopCode();
        }
        courseItem = keyMstCourseList(castData0104d02.getJigyoCode(),
            castData0104d02.getCourseCode(),
            data.getCustCode(), shopCodeWk, data.getBinKb().replace("便", ""));
        if (courseItem != null) {
          courseItem.setStsCode(MstConst.TORIKESHI);
          courseItem = setCommonDataCrl(courseItem, userCode, "MST01-04D02",
              false);
          try {
            mst0104d02Dao.getMstCourseListMapper()
                .updateByPrimaryKey(courseItem);
          } catch (Exception e) {
            resultCheck = false;
            txManager.rollback(status);
            logger.error(e.getMessage());
            throw e;
          }
        }
      }
      if (resultCheck) {
        txManager.commit(status);
      }
    }
    return err;
  }
  
  /**
   * 排他の設定.
   * @param castData :class contain data from screen
   */
  public void setHaitaDate(CastData0104d02 castData) {
    castData.setHaitaDate(DateUtil.getSysDate());
    castData.setHaitaTime(DateUtil.getSysTime());
  }
  
  /**
   * 共通項目の設定.
   * 
   * @param dataCourse:コースマスタデータ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 担当者データ
   */
  private MstCourse setCommonDataCrs(MstCourse dataCourse, String strUserId,
      String strProgId,
      boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      dataCourse.setInsUserid(strUserId);
      dataCourse.setInsPgid(strProgId);
      dataCourse.setInsYmd(currentDate);
      dataCourse.setInsTime(currentTime);
    }

    dataCourse.setUpdUserid(strUserId);
    dataCourse.setUpdPgid(strProgId);
    dataCourse.setUpdYmd(currentDate);
    dataCourse.setUpdTime(currentTime);

    return dataCourse;
  }

  /**
   * 共通項目の設定.
   * @param dataCourseList:コース納入先明細マスタデータ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 担当者データ
   */
  private MstCourseList setCommonDataCrl(MstCourseList dataCourseList,
      String strUserId, String strProgId,
      boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      dataCourseList.setInsUserid(strUserId);
      dataCourseList.setInsPgid(strProgId);
      dataCourseList.setInsYmd(currentDate);
      dataCourseList.setInsTime(currentTime);
    }

    dataCourseList.setUpdUserid(strUserId);
    dataCourseList.setUpdPgid(strProgId);
    dataCourseList.setUpdYmd(currentDate);
    dataCourseList.setUpdTime(currentTime);

    return dataCourseList;
  }
  /**
   * create a object MstCourseList
   * @param castData0104d02 :class contain data from screen
   * @param listCourse:CrsLst0104d02Data
   * @param userId:担当者コード
   * @param pgId:プログラムID
   * @return コース納入先明細マスタ
   */
  private MstCourseList newCourseListRecord(CastData0104d02 castData0104d02,
      CrsLst0104d02Data listCourse, String userId, String pgId) {
    MstCourseList recodCourseList = new MstCourseList();
    recodCourseList.setJigyoCode(castData0104d02.getJigyoCode());
    recodCourseList.setCosCode(castData0104d02.getCourseCode());
    recodCourseList.setCustCode(listCourse.getCustCode());
    recodCourseList.setShopCode(listCourse.getShopCode());
    recodCourseList.setBinKb(listCourse.getBinKb().replace("便", ""));
    recodCourseList.setDeliKb(listCourse.getDeliKb());
    recodCourseList.setStsCode(listCourse.getStsCode());

    recodCourseList = setCommonDataCrl(recodCourseList, userId, pgId, true);

    return recodCourseList;
  }
  /**
   * set information for each record in MstCourselist
   * @param castData0104d02 :class contain data from screen
   * @param courseData :data of CrsLst0104d02Data
   * @param recordCourseLst :record MstCourseList
   * @param userId:担当者コード
   * @param pgId:プログラムID
   * @return コース納入先明細マスタ
   */
  private MstCourseList setCourseListRecord(CastData0104d02 castData0104d02,
      CrsLst0104d02Data courseData, MstCourseList recordCourseLst,
      String userId, String pgId) {
    recordCourseLst.setDeliKb(courseData.getDeliKb());
    recordCourseLst.setStsCode(courseData.getStsCode());

    recordCourseLst = setCommonDataCrl(recordCourseLst, userId, pgId, false);

    return recordCourseLst;
  }
  /**
   * set information for each record in MstCourse
   * @param castData0104d02 :class contain data from screen
   * @param recordCourse :record MstCourse
   * @param userId:担当者コード
   * @param pgId:プログラムID
   * @return コースマスタ
   */
  private MstCourse setCourseRecord(CastData0104d02 castData0104d02,
      MstCourse recordCourse, String userId, String pgId) {
    recordCourse.setCosNm(castData0104d02.getCourseName());
    recordCourse.setCosNmR(castData0104d02.getCourseNameR());
    if ((!castData0104d02.getHaisoTime01().equalsIgnoreCase(""))
        && (castData0104d02.getHaisoTime02().equalsIgnoreCase(""))) {
      recordCourse.setHaisoTime(castData0104d02.getHaisoTime01() + ":");
    } else if ((castData0104d02.getHaisoTime01().equalsIgnoreCase(""))
        && (!castData0104d02.getHaisoTime02().equalsIgnoreCase(""))) {
      recordCourse.setHaisoTime(":" + castData0104d02.getHaisoTime02());
    } else if ((!castData0104d02.getHaisoTime01().equalsIgnoreCase(""))
        && (!castData0104d02.getHaisoTime02().equalsIgnoreCase(""))) {
      recordCourse.setHaisoTime(castData0104d02.getHaisoTime01() + ":"
          + castData0104d02.getHaisoTime02());
    } else {
      recordCourse.setHaisoTime(null);
    }
    
    if ((!castData0104d02.getShipUpdTime01().equalsIgnoreCase(""))
        && (castData0104d02.getShipUpdTime02().equalsIgnoreCase(""))) {
      recordCourse.setShipUpdtTime(castData0104d02.getShipUpdTime01() + ":");
    } else if ((castData0104d02.getShipUpdTime01().equalsIgnoreCase(""))
        && (!castData0104d02.getShipUpdTime02().equalsIgnoreCase(""))) {
      recordCourse.setShipUpdtTime(":" + castData0104d02.getShipUpdTime02());
    } else if ((!castData0104d02.getShipUpdTime01().equalsIgnoreCase(""))
        && (!castData0104d02.getShipUpdTime02().equalsIgnoreCase(""))) {
      recordCourse.setShipUpdtTime(castData0104d02.getShipUpdTime01() + ":"
          + castData0104d02.getShipUpdTime02());
    } else {
      recordCourse.setShipUpdtTime(null);
    }
    recordCourse.setHaisoKb(castData0104d02.getHaisoKb());
    recordCourse.setStsCode(castData0104d02.getStsCode01());

    recordCourse = setCommonDataCrs(recordCourse, userId, pgId, false);

    return recordCourse;
  }
  /**
   * create a object MstCourse
   * @param castData0104d02 :class contain 
   * @param userId:担当者コード
   * @param pgId:プログラムID
   * @return コースマスタ
   */
  private MstCourse newCourseRecord(CastData0104d02 castData0104d02,
      String userId, String pgId) {
    MstCourse recordCourse = new MstCourse();
    recordCourse.setJigyoCode(castData0104d02.getJigyoCode());
    recordCourse.setCosCode(castData0104d02.getCourseCode());
    recordCourse.setCosNm(castData0104d02.getCourseName());
    recordCourse.setCosNmR(castData0104d02.getCourseNameR());
    if (!castData0104d02.getHaisoTime01().equalsIgnoreCase("")
        && castData0104d02.getHaisoTime02().equalsIgnoreCase("")) {
      recordCourse.setHaisoTime(castData0104d02.getHaisoTime01() + ":");
    } else if (castData0104d02.getHaisoTime01().equalsIgnoreCase("")
        && !castData0104d02.getHaisoTime02().equalsIgnoreCase("")) {
      recordCourse.setHaisoTime(":" + castData0104d02.getHaisoTime02());
    } else if (!castData0104d02.getHaisoTime01().equalsIgnoreCase("")
        && !castData0104d02.getHaisoTime02().equalsIgnoreCase("")) {
      recordCourse.setHaisoTime(castData0104d02.getHaisoTime01() + ":"
          + castData0104d02.getHaisoTime02());
    }
    if (!castData0104d02.getShipUpdTime01().equalsIgnoreCase("")
        && castData0104d02.getShipUpdTime02().equalsIgnoreCase("")) {
      recordCourse.setShipUpdtTime(castData0104d02.getShipUpdTime01() + ":");
    } else if (castData0104d02.getShipUpdTime01().equalsIgnoreCase("")
        && !castData0104d02.getShipUpdTime02().equalsIgnoreCase("")) {
      recordCourse.setShipUpdtTime(":" + castData0104d02.getShipUpdTime02());
    } else if (!castData0104d02.getShipUpdTime01().equalsIgnoreCase("")
        && !castData0104d02.getShipUpdTime02().equalsIgnoreCase("")) {
      recordCourse.setShipUpdtTime(castData0104d02.getShipUpdTime01() + ":"
          + castData0104d02.getShipUpdTime02());
    }
    
    recordCourse.setHaisoKb(castData0104d02.getHaisoKb());
    recordCourse.setStsCode(castData0104d02.getStsCode01());

    recordCourse = setCommonDataCrs(recordCourse, userId, pgId, true);

    return recordCourse;
  }
  /**
   * Get data form MstCourseList table
   * @param paramJigyoCode:事業所
   * @param cosCode:コースコード
   * @param custCode:得意先コード
   * @param shopCode:店舗コード
   * @param binKb:便区分
   * @return list MstCourseList
   */
  private List<MstCourseList> getCourseList(String paramJigyoCode,
      String cosCode, String custCode, String shopCode, String binKb) {
    MstCourseListExample crsLstExp = new MstCourseListExample();
    MstCourseListExample.Criteria criteria = crsLstExp.createCriteria();
    criteria.andJigyoCodeEqualTo(paramJigyoCode);
    criteria.andCosCodeEqualTo(cosCode);
    criteria.andCustCodeEqualTo(custCode);
    criteria.andShopCodeEqualTo(shopCode);
    criteria.andBinKbEqualTo(binKb);
    List<MstCourseList> courseList = null;
    courseList = mst0104d02Dao.getMstCourseListMapper().selectByExample(
        crsLstExp);
    return courseList;
  }
  
  /**
   * check haita for MST_COURSE
   * @param jigyoCode:事業所
   * @param courseCode:コースコード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  private boolean checkHaitaCrs(String jigyoCode, String courseCode,
      String strHaitaDate, String strHaitaTime) {
    MstCourse mstCourse = new MstCourse();
    mstCourse = this.keyMstCourse(jigyoCode, courseCode);
    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstCourse.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstCourse.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }

  /**
   * 排他チェック.
   * @param strJigyoCode:事業所
   * @param strCosCode:コースコード
   * @param strCustCode:得意先コード
   * @param strShopCode:店舗コード
   * @param strBinKb:便区分
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  private boolean checkHaitaCrl(String strJigyoCode, String strCosCode,
      String strCustCode, String strShopCode, String strBinKb,
      String strHaitaDate,
      String strHaitaTime) {
    MstCourseList mstCourseLst = new MstCourseList();
    mstCourseLst = this.keyMstCourseList(strJigyoCode, strCosCode, strCustCode,
        strShopCode, strBinKb);

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstCourseLst.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstCourseLst.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
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
}