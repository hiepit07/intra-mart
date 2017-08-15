package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d01Service;

@Controller
@RequestMapping(value = "/com/0102/d08/")

public class TestCom0102d04Controller extends AbsController {

  @Autowired
  @Qualifier("com0102d01Service")
  private Com0102d01Service com0102d01Service;

  @RequestMapping(value = "/")
  public String top(Model model, FormCom0102d01 formCom0102d01) {
    return "com/testcom0102d05";
  }

}
