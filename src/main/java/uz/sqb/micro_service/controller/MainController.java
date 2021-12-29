package uz.sqb.micro_service.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uz.sqb.micro_service.model.*;
import uz.sqb.micro_service.service.JournalService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/remote")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MainController {

    RestTemplate restTemplate;

    private static final String BY_STATUS = "/status/{id}";
    private static final String ALWAYS_REPORT = "/report-always";
    private static final String BY_DAY  = "/day/{day}";
    private static final String BY_BUYER_ID = "/by-buyer-id/{id}";
    private static final String UPDATE_BUYER_STATUS = "/update/{buyer_id}/{status_id}";

    JournalService journalService;

    @GetMapping(BY_STATUS)
    public ResponseEntity getJournalByStatus(@PathVariable Long id){
        ApiResponse response = restTemplate.getForObject("http://localhost:1818/main/report-by-status/" + id, ApiResponse.class);
        return ResponseEntity.ok(response != null ? response.getObject() : "error");
    }

    @PostMapping(ALWAYS_REPORT)
    @Description("Постоянно отправлять данные о новых заказов на телеграм бот")
    public void alwaysReport(@RequestBody ReportBuyer object){
        Long id = object.getId();
        String name = object.getName();
        String date = object.getDate();
        String phone = object.getPhone();
        Integer count = object.getProduct_count();
        Double price = object.getPrice();
        System.out.println("Buyer id : " + id + " |Buyer name:  " + name + " | date of register-" + date + "| phone-" + phone + "|count-" + count + "|price-" + price);
    }


    @GetMapping(BY_DAY)
    @Description("Подключние на Bektexno и получить заказы по дню добавление")
    public ResponseEntity getJournalByDay(
            @PathVariable Integer day) throws IOException {
        String object = restTemplate.getForObject("http://localhost:1818/main/report-by-date/" + day, String.class);
        List<JournalByStatus> list = journalService.getExcelReportByDate(object);
        return ResponseEntity.ok(list);
    }

    @GetMapping(UPDATE_BUYER_STATUS)
    public ResponseEntity updateStatus(
            @PathVariable Long buyer_id,
            @PathVariable Long status_id){
        StatusUpdate object = new StatusUpdate(buyer_id, status_id);
        String url = "http://localhost:1818/main/status/update";
        ApiResponse response = restTemplate.postForObject(url, object, ApiResponse.class);
        return ResponseEntity.ok(response != null ? response.getObject() : "Incorrect Data");
    }

    @GetMapping(BY_BUYER_ID)
    public ResponseEntity getJournalByBuyerId(
            @PathVariable Long id) throws NoSuchFieldException {
        String url = "http://localhost:1818/main/by-buyer-id/?buyer_id=" + id;
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        return ResponseEntity.ok(response != null ? response.getObject() : "Empty Response");
    }

    @GetMapping("/test/")
    public void getProduct() throws JSONException {
        String string = restTemplate.getForObject("http://localhost:1818/main/test/", String.class);
        JSONArray jsonArr = new JSONArray(string);
        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Product product = new Product();
            product.setPrice(jsonObj.getDouble("price"));
            product.setId(jsonObj.getLong("id"));
            product.setShort_name(jsonObj.getString("short_name"));
            System.out.println(product);
            System.out.println(" ");
        }
    }
}
