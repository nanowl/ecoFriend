package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.dao.PaymentDAO;
import com.kh.ecoFriend.vo.PayLog;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final static PaymentDAO paymentDAO = new PaymentDAO();

  @GetMapping("/paid/log")
  @ApiOperation(value = "결제내역 조회")
  public ResponseEntity<List<PayLog>> MyPaid(@RequestParam(required = false) String email) {
    List<PayLog> list = null;
    list = paymentDAO.getMyPayLog(email);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
  @PostMapping("/pay/new")
  @ApiOperation(value = "결제")
  public ResponseEntity<Boolean> payPrice(@RequestBody Map<String, String> data) {
    boolean isResult = false;
    isResult = paymentDAO.payPrice(data);
    return new ResponseEntity<>(isResult, HttpStatus.OK);
  }

}
