package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.SellBook;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPayment;
import lk.ucsc.NovelGeek.model.request.NewPaymentTemp;
import lk.ucsc.NovelGeek.model.request.NewSelling;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.SellingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("selling")
public class SellingController {

    @Autowired
    private SellingService sellingService;

    @Autowired
    private AWSS3Service awsService;

    @Autowired
    private AuthRepository authRepository;

    //to get current user
    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

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

    @PostMapping("/editpost")
    public ResponseEntity<?> editPost(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("merchantid") String merchantid,
                                        @RequestParam("telephone") String telephone,
                                        @RequestParam("price") float price,
                                        @RequestParam("filepath") String filepath,
                                        @RequestParam("sellingid") long sellingid,
                                        @RequestParam(value = "file", required = false) MultipartFile file)
    {
        String filePath;
        if (file == null) {
            filePath = filepath;
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

        SellBook editedBook = new SellBook();
        BeanUtils.copyProperties(newpost, editedBook);
        editedBook.setSellingid(sellingid);
        editedBook.setPublishedDate(new Date());
        editedBook.setUsers(this.getCurrentUser());
        return ResponseEntity.ok(sellingService.editPost(editedBook));
    }

    @PostMapping("/newpayment")
    public void storePayment(@RequestBody NewPayment data){
        sellingService.storePayment(data);
    }


    @GetMapping("/allposts")
    public ResponseEntity<?> getAllPosts(){
        return ResponseEntity.ok(sellingService.getAllPosts());
    }

    @GetMapping("/myposts")
    public ResponseEntity<?> getMyPosts(){
        return ResponseEntity.ok(sellingService.getMyPosts());
    }

    @GetMapping("/getpost/{sellingid}")
    public ResponseEntity<?> getPost(@PathVariable(value="sellingid")long sellingid){
        return ResponseEntity.ok(sellingService.getPost(sellingid));
    }

    @DeleteMapping("/delete/{sellingid}")
    public ResponseEntity<?> deletePost(@PathVariable(value="sellingid") long sellingid ){
        return ResponseEntity.ok(sellingService.deletePost(sellingid));
    }

    @PostMapping("/soldbook")
    public ResponseEntity<?> soldBook(@RequestBody NewPaymentTemp data){
        return ResponseEntity.ok( sellingService.soldBook(data));
    }

    @GetMapping("/getpurchasedata/{sellingid}")
    public ResponseEntity<?> getPurchaseData(@PathVariable(value="sellingid")long sellingid){
        return ResponseEntity.ok(sellingService.getPurchaseData(sellingid));
    }
}
