package com.kh.ecoFriend.controller;
import com.kh.ecoFriend.dao.InquireDAO;
import com.kh.ecoFriend.dao.AnswerDAO;
import com.kh.ecoFriend.vo.Inquire;
import com.kh.ecoFriend.vo.Answer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://192.168.110.68:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/inquires")
public class InquireController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InquireController.class);
  //  private static final InquireDAO inquireDAO = new InquireDAO();

    @Autowired
    private InquireDAO inquireDAO;

    @PostMapping
    @ApiOperation(value = "Create Inquire", notes = "Create a new Inquire in the database.")
    public ResponseEntity<String> createInquire(@RequestBody Map<String, String> request) {
        Inquire inquire = Inquire.builder()
                .email(request.get("email"))
                .inqTitle(request.get("inqTitle"))
                .inqContent(request.get("inqContent"))
                .build();
        if (inquireDAO.createInquire(inquire)) {
            return new ResponseEntity<>("Inquire created successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to create Inquire.", HttpStatus.BAD_REQUEST);
        }
    }

    // 문의내역 조회
    @GetMapping("/{email}")
    public ResponseEntity<List<Inquire>> getInquiresByEmail(@PathVariable String email) {
        try {
            List<Inquire> inquires = inquireDAO.getInquiresByEmail(email);
            if (inquires.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(inquires, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Failed to get inquires", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 미답변 내역 조회
    @GetMapping("/unanswered")
    public ResponseEntity<List<Inquire>> getUnansweredInquiries() {
        List<Inquire> inquiries = inquireDAO.getUnansweredInquiries();
        for (Inquire e : inquiries) {
            System.out.println(e.toString());
        }
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    // InquireController.java

    // 답변하기
    @PostMapping("/inquire/update")
    @ApiOperation(value = "Update answer content")
    public ResponseEntity<Boolean> updateInquiry(@RequestBody Map<String, String> data) {
        int inqNo = Integer.parseInt(data.get("inqNo"));
        String answerContent = data.get("answerContent");

        boolean result = inquireDAO.updateInquiry(inqNo, answerContent);
        if (result) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }


    // Similarly, implement other CRUD operations for Inquires
}

