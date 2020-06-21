package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(value= "/s3")
public class AWSS3Ctrl {

    @Autowired
    private AWSS3Service service;

//    @PostMapping(value= "/upload")
//    public ResponseEntity<String> uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
//        //service.uploadFile(multipartFile);
//        System.out.println(payload.get("boom"));
//        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping(value= "/upload")
    public ResponseEntity<String> uploadFile2(@RequestParam("boom") String model, @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        String fileUrl = service.uploadFile(multipartFile);
//        System.out.println(model);
        //final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    }
}