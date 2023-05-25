package com.kh.ecoFriend.controller;

import com.kh.ecoFriend.api.entity.Item;
import com.kh.ecoFriend.dao.ApiDAO;
import com.kh.ecoFriend.dao.ChargerDAO;
import com.kh.ecoFriend.vo.WishStation;
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
@RequestMapping("/charger")
public class ChargerController {

  private final static ChargerDAO chargerDAO = new ChargerDAO();
  private final static ApiDAO apiDAO = new ApiDAO();

  @PostMapping("/new")
  @ApiOperation(value = "DB에 공공데이터 저장")
  public ResponseEntity<Boolean> addChargerData(@RequestBody Map<String, String> data) {
    ResponseEntity<String> jsonData = apiDAO.getData(data);
    List<Item> list = apiDAO.getItem(jsonData);
    boolean charger = chargerDAO.setChargerDB(list);
    boolean isResult = false;
    if (charger) isResult = true;
    return new ResponseEntity<>(isResult, HttpStatus.OK);
  }

  @PostMapping("/station/new")
  @ApiOperation(value = "DB에 공공데이터 저장")
  public ResponseEntity<Boolean> addCharStationData(@RequestBody Map<String, String> data) {
    ResponseEntity<String> jsonData = apiDAO.getData(data);
    List<Item> list = apiDAO.getItem(jsonData);
    chargerDAO.setCharStationDB(list);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/wish/find")
  @ApiOperation(value = "관심충전소 호출")
  public ResponseEntity<List<WishStation>> findMyWish(@RequestParam(required = false) String email) {
    List<WishStation> wishStation = chargerDAO.getWishStation(email);
    return new ResponseEntity<>(wishStation, HttpStatus.OK);
  }

  @PostMapping("/wish/add")
  @ApiOperation(value = "관심충전소 등록")
  public ResponseEntity<Boolean> addMyWish(@RequestBody Map<String, String> data) {
    boolean result = false;
    result = chargerDAO.setWishStation(data);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping("/wish/delete")
  @ApiOperation(value = "관심충전소 제거")
  public ResponseEntity<Boolean> deleteMyWish(@RequestBody Map<String, String> data) {
    System.out.println(data.get("csId"));
    boolean result = false;
    result = chargerDAO.deleteWishStation(data);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
