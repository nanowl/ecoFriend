package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.dao.AnswerDAO;
import com.kh.ecoFriend.vo.Answer;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/answers")
public class AnswerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);
    private static final AnswerDAO answerDAO = new AnswerDAO();

    @PostMapping
    @ApiOperation(value = "Create Answer", notes = "Create a new Answer in the database.")
    public ResponseEntity<String> createAnswer(@RequestBody Map<String, String> request) {
        Answer answer = Answer.builder()
                .aNo(Integer.parseInt(request.get("aNo")))
                .inqNo(Integer.parseInt(request.get("inqNo")))
                .answer(request.get("answer"))
                .aTitle(request.get("aTitle"))
                .aContent(request.get("aContent"))
                .build();
        if (answerDAO.createAnswer(answer)) {
            return new ResponseEntity<>("Answer created successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to create Answer.", HttpStatus.BAD_REQUEST);
        }
    }
}
