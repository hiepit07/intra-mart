/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:GetCorrectKbGet0105d00.java
 * 
 *<p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 1.00 HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * CSV用のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class GetCorrectKbGet0105d00 {
    private String urikakeMonth;
 // エラーメッセージ
    private String message;
    private String lstId;
    private String type;

    // エラーメッセージ
    private String haitaDate;
    private String haitaTime;
    
    public String getUrikakeMonth() {
        return urikakeMonth;
    }
    public void setUrikakeMonth(String urikakeMonth) {
        this.urikakeMonth = urikakeMonth;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getLstId() {
        return lstId;
    }
    public void setLstId(String lstId) {
        this.lstId = lstId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
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
}
