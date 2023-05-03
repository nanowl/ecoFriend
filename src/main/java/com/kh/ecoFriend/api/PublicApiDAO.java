package com.kh.ecoFriend.api;

import com.kh.ecoFriend.api.entity.Item;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublicApiDAO {

  //@Value("${public.key}")
  private final static String serviceKey = "GIO1tKfe1qPaC8jL2aiwEoaqw0BXwp/1lh3KQw4qRXysur1FXfXLpgMXjp9vfq2lIoM0Q4R1tP+dkbyYLTG4sg==" ;
  private final static String ENDPOINT = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList";

//  public ResponseEntity<String> get(String url) {
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//
//    HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
//
//    ResponseEntity<String> response = restTemplate.exchange(
//      URI.create(url), HttpMethod.GET, entity, String.class
//    );
//
//    return response;
//  }


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

    return response;
  }

  public List<Item> getItem(ResponseEntity<String> jsonObject) {
    JSONObject object = new JSONObject(jsonObject.getBody());
    JSONObject response = (JSONObject) object.get("response");
    JSONObject body = (JSONObject)  response.get("body");
    JSONObject items = (JSONObject)  body.get("items");
    JSONArray jsonItem = (JSONArray) items.get("item");
    System.out.println(response);
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
    return itemList;
  }
}
