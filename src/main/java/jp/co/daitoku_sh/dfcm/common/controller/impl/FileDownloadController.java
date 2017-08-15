/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller.impl
 * ファイル名:FileDownloadController.java
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

package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * サーバからファイルをダウンロードする処理
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/file")
public class FileDownloadController {

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  /**
   * File download.
   * 
   * @param res:httpレスポンス
   * @param fileName:ファイル名
   * @throws IOException　エラー画面
   */
  @RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
  public void file(HttpServletResponse res,
      @PathVariable("fileName") String fileName) throws IOException {
    // CSVファイルの保存場所
    File file = new File(readPropertiesFileService.getSetting("PATH_CSV") + "\\"
        + fileName);
    final FileInputStream inputStream = new FileInputStream(file);
    res.setContentType("application/octet-stream");
    // 文字化け対応
    String jpFileName = new String(file.getName().getBytes("Windows-31J"),
        "8859_1");
    res.setHeader("Content-Disposition", "attachment; filename=\"" + jpFileName
        + "\"");
    res.setHeader("Content-Length", file.length() + "\n");
    IOUtils.copy(inputStream, res.getOutputStream());
  }

}