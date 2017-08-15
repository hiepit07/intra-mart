package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.FormCom0102d05;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.com.impl.Com0102d05Service;

@Controller
@RequestMapping(value = "/com/0102/d07/")

public class TestCom0102d05Controller extends AbsController{
  @Autowired
  @Qualifier("com0102d05Service")
  private Com0102d05Service com0105d01Service;

  @RequestMapping(value = "/")
  public String top(Model model, FormCom0102d05 formCom0102d05) {
    return "com/testcom0102d04";
  }
}
