package com.example.ex21_transaction_not;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.ex21_transaction_not.service.BuyTicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MyController {

  @Autowired
  BuyTicketService buyTicketService;

  @RequestMapping("/")
  public @ResponseBody String root() throws Exception {
    return "Transaction 미적용";
  }

  @RequestMapping("/buy-ticket")
  public String buyTicket() {
    return "buy-ticket";
  }

  @RequestMapping("/buy-ticket-card")
  public String buyTicketCard(
    @RequestParam("consumerId") String consumerId,
    @RequestParam("amount") String amount,
    @RequestParam("error") String error,
    Model model
    ) {

    // db에 저장
    int result = buyTicketService.buy(consumerId, Integer.parseInt(amount), error);
    
    // 화면 렌더링을 위해 model 객체에 넣어보냄
    model.addAttribute("consumerId", consumerId);
    model.addAttribute("amount", amount);

    if (result == 1) {
      return "buy-ticket-end";
    } else {
      return "buy-ticket-error";
    }
  }
  
}
