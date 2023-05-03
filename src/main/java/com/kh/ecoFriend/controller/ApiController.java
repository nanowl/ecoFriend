package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.api.entity.Item;
import com.kh.ecoFriend.api.PublicApiDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {


  @PostMapping("/list")
  public ResponseEntity<List<Item>> testResponse(@RequestBody Map<String, String> request) {
    PublicApiDAO dao = new PublicApiDAO();
    ResponseEntity<String> jsonData = dao.getData(request);
    return new ResponseEntity<>(dao.getItem(jsonData), HttpStatus.OK);
  }
}
