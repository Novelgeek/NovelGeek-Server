package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.*;
import lk.ucsc.NovelGeek.model.request.NewPayment;
import lk.ucsc.NovelGeek.model.request.NewPaymentTemp;
import lk.ucsc.NovelGeek.model.request.NewSelling;
import lk.ucsc.NovelGeek.model.response.DeletePostResponse;
import lk.ucsc.NovelGeek.model.response.PurchaseResponse;
import lk.ucsc.NovelGeek.model.response.SellBookResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.PaymentsRepository;
import lk.ucsc.NovelGeek.repository.SellingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SellingService {
    @Autowired
    private SellingRepository sellingRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private AWSS3Service awsService;

    //to get current user
    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    //create a post
    public SellBookResponse createPost(NewSelling newrequest){

        SellBook newpost= new SellBook();
        BeanUtils.copyProperties(newrequest,newpost);

        newpost.setPublishedDate(new Date());

        Users currentUser = this.getCurrentUser();
        newpost.setUsers(currentUser);

        SellBook returnpost = sellingRepository.save(newpost);

        SellBookResponse response = new SellBookResponse();
        BeanUtils.copyProperties(returnpost, response);

        //set ownername
        response.setUsername(this.getCurrentUser().getUsername());
        //set owner
        response.setOwned(true);
        //set is sold
        response.setSold(false);


        return response;
    }

    public List<SellBookResponse> getAllPosts(){

        List posts = sellingRepository.findAll().stream().map(post->{

            SellBookResponse response = new SellBookResponse();
            BeanUtils.copyProperties(post, response);

            response.setUsername(post.getUsers().getUsername());

            if(post.getUsers().getId()==this.getCurrentUser().getId()){
                response.setOwned(true);
            }else{
                response.setOwned(false);
            }

            if(paymentsRepository.checkIsSold(post.getSellingid())==1){
                response.setSold(true);
            }else{
                response.setSold(false);
            }

            return response;

        }).collect(Collectors.toList());

        return posts;
    }

    public List<SellBookResponse> getMyPosts(){

        Users currentUser = this.getCurrentUser();

        List posts = currentUser.getSelling().stream().map(post->{

            SellBookResponse response = new SellBookResponse();
            BeanUtils.copyProperties(post, response);

            response.setUsername(post.getUsers().getUsername());

            response.setOwned(true);


            if(paymentsRepository.checkIsSold(post.getSellingid())==1){
                response.setSold(true);
            }else{
                response.setSold(false);
            };

            return response;

        }).collect(Collectors.toList());

        return posts;
    }

    public SellBookResponse getPost(long sellingid){

        SellBook book = sellingRepository.findById(sellingid);
        SellBookResponse response = new SellBookResponse();
        BeanUtils.copyProperties(book, response);
        response.setUsername(book.getUsers().getUsername());

        response.setOwned(true);

        if(paymentsRepository.checkIsSold(book.getSellingid())==1){
            response.setSold(true);
        }else{
            response.setSold(false);
        }

        return response;
    }

    public SellBookResponse editPost(SellBook newbook){
        SellBook book = sellingRepository.save(newbook);

        SellBookResponse response = new SellBookResponse();
        BeanUtils.copyProperties(book, response);

        response.setUsername(book.getUsers().getUsername());

        response.setOwned(true);

        if(paymentsRepository.checkIsSold(book.getSellingid())==1){
            response.setSold(true);
        }else{
            response.setSold(false);
        }

        return response;
    }

    public long soldBook(NewPaymentTemp data){

        Payments payment = new Payments();
        payment.setSellbook(sellingRepository.findById(data.getOrder_id()));
        payment.setStatus_code(data.getStatus_code());
        payment.setStatus_message(data.getStatus_message());

        Random rand = new Random();
        long random_no = rand.nextLong();
        String payment_id = String.valueOf(random_no);
        payment.setPayment_id(payment_id);

        payment.setUsers(this.getCurrentUser());
        payment.setPaidDate(new Date());

        Payments result = paymentsRepository.save(payment);

        return  result.getPayid();
    }

    public PurchaseResponse getPurchaseData(long sellingid){
        Payments payment = paymentsRepository.findBySellbook(sellingRepository.findById(sellingid));
        PurchaseResponse response = new PurchaseResponse();
        response.setBuyer_name(payment.getUsers().getUsername());
        response.setImagePath(payment.getUsers().getImageUrl());
        response.setEmail(payment.getUsers().getEmail());
        response.setPayment_id(payment.getPayment_id());
        response.setPaid_date(payment.getPaidDate());
        response.setOrder_id(payment.getSellbook().getSellingid());

        return  response;
    }

    public void storePayment(NewPayment data){
        Payments newdata = new Payments();
        newdata.setPayment_id(data.getPayment_id());
        newdata.setStatus_code(data.getStatus_code());
        newdata.setStatus_message(data.getStatus_message());
        //BeanUtils.copyProperties(data, newdata);
        newdata.setUsers(this.getCurrentUser());
        newdata.setPaidDate(new Date());
        paymentsRepository.save(newdata);
    }

    public long deletePost(long id){

        sellingRepository.deleteById(id);

        return id;
    }
}
