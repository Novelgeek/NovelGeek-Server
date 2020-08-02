package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.request.NewComment;
import lk.ucsc.NovelGeek.model.request.NewPaymentCutomer;
import lk.ucsc.NovelGeek.model.request.NewPaymentData;
import lk.ucsc.NovelGeek.model.request.NewSelling;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.SellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("selling")
public class SellingController {

    @Autowired
    private SellingService sellingService;

    @Autowired
    private AWSS3Service awsService;

    @PostMapping("/newpost")
    public ResponseEntity<?> createPost(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("merchantid") String merchantid,
                                        @RequestParam("telephone") String telephone,
                                        @RequestParam("price") float price,
                                        @RequestParam(value = "file", required = false) MultipartFile file)
    {
        String filePath;
        if (file == null) {
            filePath = null;
        } else {
            filePath = awsService.uploadFile(file);
        }

        NewSelling newpost = new NewSelling();
        newpost.setTitle(title);
        newpost.setDescription(description);
        newpost.setMerchantid(merchantid);
        newpost.setTelephone(telephone);
        newpost.setPrice(price);
        newpost.setImagePath(filePath);

        return ResponseEntity.ok(sellingService.createPost(newpost));
    }

    @PostMapping("/newpayment")
    public void storePayment(@RequestBody NewPaymentData data){
        sellingService.storePayment(data);
    }

    @PostMapping("/newcustomer")
    public ResponseEntity<?> storeCustomer (@RequestBody NewPaymentCutomer data){
        return ResponseEntity.ok(sellingService.storeCustomer(data));
    }

    @GetMapping("/allposts")
    public ResponseEntity<?> getAllPosts(){
        return ResponseEntity.ok(sellingService.getAllPosts());
    }

    @GetMapping("/myposts")
    public ResponseEntity<?> getMyPosts(){
        return ResponseEntity.ok(sellingService.getMyPosts());
    }


}
