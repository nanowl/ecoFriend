package com.kh.ecoFriend.controller;


import com.kh.ecoFriend.dao.MemberDAO;
import com.kh.ecoFriend.dao.PaymentDAO;
import com.kh.ecoFriend.util.SessionManager;
import com.kh.ecoFriend.vo.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PaymentController {

    @PostMapping("/payment")
    public ResponseEntity<Boolean> chargeMoney(@RequestBody Map<String, String> regData) {
        String getName = regData.get("payname");
        String getEmail = regData.get("email");
        String getCredit = regData.get("credit");
        String getCardNum = regData.get("cardNum");
        String getEndDate = regData.get("endDate");
        String getCVC = regData.get("cvc");
        String getPrice = regData.get("price");
        PaymentDAO dao = new PaymentDAO();
        boolean isTrue = dao.chargeMoney(getName, getEmail, getCredit, getCardNum, getEndDate, getCVC, getPrice);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    private final PaymentDAO paymentDAO;

    @Autowired
    public PaymentController(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @GetMapping("/user/{email}/charge")
    public ResponseEntity<Integer> getChargeMoney(@PathVariable String email) {
        int total = paymentDAO.getChargeMoney(email);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

}