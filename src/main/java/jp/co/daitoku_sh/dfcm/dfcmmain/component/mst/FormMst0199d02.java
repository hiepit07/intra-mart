/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:FormMst0199d02.java
 * 
 * 作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import java.math.BigDecimal;
import java.util.List;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.Gen0199d02Data;

/**
 * フォーム(jspの modelAttribute="FormMst0199d02" とリンク）
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormMst0199d02 {

  /** [画面]分類.分類コード */
  private String category;
  /** [画面]区分名称 */
  private String kbNm;

  private String glCode;

  private String viewMode;

  private String errMessage;

  private String messageType;
  /** [画面]権限（更新） */
  private boolean updPermitFlg;
  /** [画面]権限（追加） */
  private boolean insPermitFlg;
  /** [画面]権限（削除） */
  private boolean delPermitFlg;

  private String errorControl;

  private String haitaDate;

  private String haitaTime;
  /** コード桁数 */
  private BigDecimal codeDigit;

  /** 画面_hidden]編集モード */
  private String editMode;

  /** [画面_hidden]対象行No */
  private String no;

  private List<Gen0199d02Data> dataList;

  private String errorItem;
  private String gpCode;
  private String title1;
  private String title2;
  private String title3;
  private String title4;
  private String title5;
  private String title6;
  private String title7;
  private String title8;
  private String title9;
  private String title10;

  /** [画面]項目１（値） */
  private String itemValue1;
  private String itemValue2;
  private String itemValue3;
  private String itemValue4;
  private String itemValue5;
  private String itemValue6;
  private String itemValue7;
  private String itemValue8;
  private String itemValue9;
  private String itemValue10;

  /** [画面_hidden]項目１（属性） */
  private String targetAttr1;
  private String targetAttr2;
  private String targetAttr3;
  private String targetAttr4;
  private String targetAttr5;
  private String targetAttr6;
  private String targetAttr7;
  private String targetAttr8;
  private String targetAttr9;
  private String targetAttr10;

  /** [画面_hidden]項目１（桁数） */
  private BigDecimal targetDigit1;
  private BigDecimal targetDigit2;
  private BigDecimal targetDigit3;
  private BigDecimal targetDigit4;
  private BigDecimal targetDigit5;
  private BigDecimal targetDigit6;
  private BigDecimal targetDigit7;
  private BigDecimal targetDigit8;
  private BigDecimal targetDigit9;
  private BigDecimal targetDigit10;

  /** [画面_hidden]項目１（説明） */
  private String targetExp1;
  private String targetExp2;
  private String targetExp3;
  private String targetExp4;
  private String targetExp5;
  private String targetExp6;
  private String targetExp7;
  private String targetExp8;
  private String targetExp9;
  private String targetExp10;

  public String getHaitaDate() {
    return haitaDate;
  }

  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }

  public String getHaitaTime() {
    return haitaTime;
  }

  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getErrorItem() {
    return errorItem;
  }

  public void setErrorItem(String errorItem) {
    this.errorItem = errorItem;
  }

  public String getTargetAttr1() {
    return targetAttr1;
  }

  public void setTargetAttr1(String targetAttr1) {
    this.targetAttr1 = targetAttr1;
  }

  public String getTargetAttr2() {
    return targetAttr2;
  }

  public void setTargetAttr2(String targetAttr2) {
    this.targetAttr2 = targetAttr2;
  }

  public String getTargetAttr3() {
    return targetAttr3;
  }

  public void setTargetAttr3(String targetAttr3) {
    this.targetAttr3 = targetAttr3;
  }

  public String getTargetAttr4() {
    return targetAttr4;
  }

  public void setTargetAttr4(String targetAttr4) {
    this.targetAttr4 = targetAttr4;
  }

  public String getTargetAttr5() {
    return targetAttr5;
  }

  public void setTargetAttr5(String targetAttr5) {
    this.targetAttr5 = targetAttr5;
  }

  public String getTargetAttr6() {
    return targetAttr6;
  }

  public void setTargetAttr6(String targetAttr6) {
    this.targetAttr6 = targetAttr6;
  }

  public String getTargetAttr7() {
    return targetAttr7;
  }

  public void setTargetAttr7(String targetAttr7) {
    this.targetAttr7 = targetAttr7;
  }

  public String getTargetAttr8() {
    return targetAttr8;
  }

  public void setTargetAttr8(String targetAttr8) {
    this.targetAttr8 = targetAttr8;
  }

  public String getTargetAttr9() {
    return targetAttr9;
  }

  public void setTargetAttr9(String targetAttr9) {
    this.targetAttr9 = targetAttr9;
  }

  public String getTargetAttr10() {
    return targetAttr10;
  }

  public void setTargetAttr10(String targetAttr10) {
    this.targetAttr10 = targetAttr10;
  }

  public BigDecimal getTargetDigit1() {
    return targetDigit1;
  }

  public void setTargetDigit1(BigDecimal targetDigit1) {
    this.targetDigit1 = targetDigit1;
  }

  public BigDecimal getTargetDigit2() {
    return targetDigit2;
  }

  public void setTargetDigit2(BigDecimal targetDigit2) {
    this.targetDigit2 = targetDigit2;
  }

  public BigDecimal getTargetDigit3() {
    return targetDigit3;
  }

  public void setTargetDigit3(BigDecimal targetDigit3) {
    this.targetDigit3 = targetDigit3;
  }

  public BigDecimal getTargetDigit4() {
    return targetDigit4;
  }

  public void setTargetDigit4(BigDecimal targetDigit4) {
    this.targetDigit4 = targetDigit4;
  }

  public BigDecimal getTargetDigit5() {
    return targetDigit5;
  }

  public void setTargetDigit5(BigDecimal targetDigit5) {
    this.targetDigit5 = targetDigit5;
  }

  public BigDecimal getTargetDigit6() {
    return targetDigit6;
  }

  public void setTargetDigit6(BigDecimal targetDigit6) {
    this.targetDigit6 = targetDigit6;
  }

  public BigDecimal getTargetDigit7() {
    return targetDigit7;
  }

  public void setTargetDigit7(BigDecimal targetDigit7) {
    this.targetDigit7 = targetDigit7;
  }

  public BigDecimal getTargetDigit8() {
    return targetDigit8;
  }

  public void setTargetDigit8(BigDecimal targetDigit8) {
    this.targetDigit8 = targetDigit8;
  }

  public BigDecimal getTargetDigit9() {
    return targetDigit9;
  }

  public void setTargetDigit9(BigDecimal targetDigit9) {
    this.targetDigit9 = targetDigit9;
  }

  public BigDecimal getTargetDigit10() {
    return targetDigit10;
  }

  public void setTargetDigit10(BigDecimal targetDigit10) {
    this.targetDigit10 = targetDigit10;
  }

  public String getTargetExp1() {
    return targetExp1;
  }

  public void setTargetExp1(String targetExp1) {
    this.targetExp1 = targetExp1;
  }

  public String getTargetExp2() {
    return targetExp2;
  }

  public void setTargetExp2(String targetExp2) {
    this.targetExp2 = targetExp2;
  }

  public String getTargetExp3() {
    return targetExp3;
  }

  public void setTargetExp3(String targetExp3) {
    this.targetExp3 = targetExp3;
  }

  public String getTargetExp4() {
    return targetExp4;
  }

  public void setTargetExp4(String targetExp4) {
    this.targetExp4 = targetExp4;
  }

  public String getTargetExp5() {
    return targetExp5;
  }

  public void setTargetExp5(String targetExp5) {
    this.targetExp5 = targetExp5;
  }

  public String getTargetExp6() {
    return targetExp6;
  }

  public void setTargetExp6(String targetExp6) {
    this.targetExp6 = targetExp6;
  }

  public String getTargetExp7() {
    return targetExp7;
  }

  public void setTargetExp7(String targetExp7) {
    this.targetExp7 = targetExp7;
  }

  public String getTargetExp8() {
    return targetExp8;
  }

  public void setTargetExp8(String targetExp8) {
    this.targetExp8 = targetExp8;
  }

  public String getTargetExp9() {
    return targetExp9;
  }

  public void setTargetExp9(String targetExp9) {
    this.targetExp9 = targetExp9;
  }

  public String getTargetExp10() {
    return targetExp10;
  }

  public void setTargetExp10(String targetExp10) {
    this.targetExp10 = targetExp10;
  }

  public String getEditMode() {
    return editMode;
  }

  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  public BigDecimal getCodeDigit() {
    return codeDigit;
  }

  public void setCodeDigit(BigDecimal codeDigit) {
    this.codeDigit = codeDigit;
  }

  public String getErrorControl() {
    return errorControl;
  }

  public void setErrorControl(String errorControl) {
    this.errorControl = errorControl;
  }

  public boolean isUpdPermitFlg() {
    return updPermitFlg;
  }

  public String getGpCode() {
    return gpCode;
  }

  public void setGpCode(String gpCode) {
    this.gpCode = gpCode;
  }

  public String getItemValue1() {
    return itemValue1;
  }

  public void setItemValue1(String itemValue1) {
    this.itemValue1 = itemValue1;
  }

  public String getItemValue2() {
    return itemValue2;
  }

  public void setItemValue2(String itemValue2) {
    this.itemValue2 = itemValue2;
  }

  public String getItemValue3() {
    return itemValue3;
  }

  public void setItemValue3(String itemValue3) {
    this.itemValue3 = itemValue3;
  }

  public String getItemValue4() {
    return itemValue4;
  }

  public void setItemValue4(String itemValue4) {
    this.itemValue4 = itemValue4;
  }

  public String getItemValue5() {
    return itemValue5;
  }

  public void setItemValue5(String itemValue5) {
    this.itemValue5 = itemValue5;
  }

  public String getItemValue6() {
    return itemValue6;
  }

  public void setItemValue6(String itemValue6) {
    this.itemValue6 = itemValue6;
  }

  public String getItemValue7() {
    return itemValue7;
  }

  public void setItemValue7(String itemValue7) {
    this.itemValue7 = itemValue7;
  }

  public String getItemValue8() {
    return itemValue8;
  }

  public void setItemValue8(String itemValue8) {
    this.itemValue8 = itemValue8;
  }

  public String getItemValue9() {
    return itemValue9;
  }

  public void setItemValue9(String itemValue9) {
    this.itemValue9 = itemValue9;
  }

  public String getItemValue10() {
    return itemValue10;
  }

  public void setItemValue10(String itemValue10) {
    this.itemValue10 = itemValue10;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public List<Gen0199d02Data> getDataList() {
    return dataList;
  }

  public void setDataList(List<Gen0199d02Data> dataList) {
    this.dataList = dataList;
  }

  public void setUpdPermitFlg(boolean updPermitFlg) {
    this.updPermitFlg = updPermitFlg;
  }

  public boolean isInsPermitFlg() {
    return insPermitFlg;
  }

  public void setInsPermitFlg(boolean insPermitFlg) {
    this.insPermitFlg = insPermitFlg;
  }

  public boolean isDelPermitFlg() {
    return delPermitFlg;
  }

  public void setDelPermitFlg(boolean delPermitFlg) {
    this.delPermitFlg = delPermitFlg;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getKbNm() {
    return kbNm;
  }

  public void setKbNm(String kbNm) {
    this.kbNm = kbNm;
  }

  public String getGlCode() {
    return glCode;
  }

  public void setGlCode(String glCode) {
    this.glCode = glCode;
  }

  public String getViewMode() {
    return viewMode;
  }

  public void setViewMode(String viewMode) {
    this.viewMode = viewMode;
  }

  public String getTitle1() {
    return title1;
  }

  public void setTitle1(String title1) {
    this.title1 = title1;
  }

  public String getTitle2() {
    return title2;
  }

  public void setTitle2(String title2) {
    this.title2 = title2;
  }

  public String getTitle3() {
    return title3;
  }

  public void setTitle3(String title3) {
    this.title3 = title3;
  }

  public String getTitle4() {
    return title4;
  }

  public void setTitle4(String title4) {
    this.title4 = title4;
  }

  public String getTitle5() {
    return title5;
  }

  public void setTitle5(String title5) {
    this.title5 = title5;
  }

  public String getTitle6() {
    return title6;
  }

  public void setTitle6(String title6) {
    this.title6 = title6;
  }

  public String getTitle7() {
    return title7;
  }

  public void setTitle7(String title7) {
    this.title7 = title7;
  }

  public String getTitle8() {
    return title8;
  }

  public void setTitle8(String title8) {
    this.title8 = title8;
  }

  public String getTitle9() {
    return title9;
  }

  public void setTitle9(String title9) {
    this.title9 = title9;
  }

  public String getTitle10() {
    return title10;
  }

  public void setTitle10(String title10) {
    this.title10 = title10;
  }

}
