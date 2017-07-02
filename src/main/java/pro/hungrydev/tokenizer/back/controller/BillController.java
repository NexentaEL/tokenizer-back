package pro.hungrydev.tokenizer.back.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {

    private final static Logger LOGGER = Logger.getLogger(BillController.class);
}
