package uz.sqb.micro_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uz.sqb.micro_service.model.JournalDto;

import java.util.List;


@RestController
@RequestMapping("/remote")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MainController {

    RestTemplate restTemplate;

    private static final String BY_STATUS = "/status/{id}";
    private static final String ALWAYS_REPORT = "/report-always/{id}/{name}/{date}/";

    @GetMapping(BY_STATUS)
    public ResponseEntity getByStatus(@PathVariable Long id){
        String object = restTemplate.getForObject("http://localhost:1818/main/by-status-id/" + id, String.class);
        switch (object) {
            case "empty":
                return ResponseEntity.ok("List is empty");
            case "not_found":
                return ResponseEntity.ok("Status is not found");
            case "error":
                return ResponseEntity.ok("Some error");
            default:
                JournalDto[] array = new Gson().fromJson(object, JournalDto[].class);
                List<JournalDto> list = List.of(array);
                return ResponseEntity.ok(list);
        }
    }

    @GetMapping(ALWAYS_REPORT)
    public void alwaysReport(@PathVariable("id") Long id,
                                       @PathVariable("name") String name,
                                       @PathVariable("date") String date){
        System.out.println(id + " " + name + " " + date);
    }


}
