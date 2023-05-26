package com.kh.ecoFriend.dao;

import com.kh.ecoFriend.api.config.email.RegisterMail;
import com.kh.ecoFriend.api.entity.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiDAO {

  //@Value("${public.key}")
  private final static String serviceKey = "JF8AcB8IEJi0h4O+hefnYvhn0VRJMtVfQDjmhKOih2v8ciOGRD4Ksj4uY0RmiyWulA0s4nj/CoNPZ2PVo0MUPA==" ;
  private final static String ENDPOINT = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList";
  private static RegisterMail registerMail = new RegisterMail();

  // 공공데이터를 가져옴
  public ResponseEntity<String> getData(Map<String, String> request) {
    String pageNum = request.get("pageNum");
    String numOfRows = request.get("numOfRows");
    String addr = request.get("addr");

    StringBuilder URL = new StringBuilder("serviceKey=" + URLEncoder.encode(serviceKey));
    URL.append("&pageNum=" + pageNum)
      .append("&numOfRows=" + numOfRows)
      .append("&addr=" + URLEncoder.encode(addr));

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange(
      URI.create(ENDPOINT + "?" + URL.toString()), HttpMethod.GET, entity, String.class
    );

    System.out.println("Response: " + response.getBody());

    return response;
  }

 //  json 객체 파싱
  public List<Item> getItem(ResponseEntity<String> jsonObject) {
    JSONObject object = new JSONObject(jsonObject.getBody());
    JSONObject response = (JSONObject) object.get("response");
    JSONObject body = (JSONObject)  response.get("body");
    JSONObject items = (JSONObject)  body.get("items");
    JSONArray jsonItem = (JSONArray) items.get("item");
    List<Item> itemList = new ArrayList<>();
    for (int i = 0; i < jsonItem.length(); i++) {
      JSONObject array = (JSONObject) jsonItem.get(i);
//      System.out.println(array.get("addr"));
      Item item = Item.builder()
        .addr(array.getString("addr"))
        .chargeTp(array.getInt("chargeTp"))
        .cpId(array.getInt("cpId"))
        .cpNm(array.getString("cpNm"))
        .cpStat(array.getInt("cpStat"))
        .cpTp(array.getInt("cpTp"))
        .csId(array.getInt("csId"))
        .csNm(array.getString("csNm"))
        .lat(array.getDouble("lat"))
        .lng(array.getDouble("longi"))
        .statUpdateDatetime(array.getString("statUpdateDatetime"))
        .build();
      itemList.add(item);
    }
    System.out.println("itemList: " + itemList);
    return itemList;
  }

  
  
  // 키 값 비교
  public boolean getConfirmKey(String email, String keyCode) {
    boolean isConfirm = false;
    String confirmKey = registerMail.getConKey(email);
    System.out.println("서버에 저장된 키 값 : " + confirmKey);
    System.out.println("서버에 입력된 키 값 : " + keyCode);
    if (confirmKey.equals(keyCode)) {
      isConfirm = true;
      registerMail.deleteConKey(email);
    }
    return isConfirm;
  }
}
