package lk.ucsc.NovelGeek.service;

import com.fasterxml.jackson.core.PrettyPrinter;
import lk.ucsc.NovelGeek.model.*;
import lk.ucsc.NovelGeek.model.request.NewPaymentCutomer;
import lk.ucsc.NovelGeek.model.request.NewPaymentData;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.request.NewSelling;
import lk.ucsc.NovelGeek.model.response.PostResponse;
import lk.ucsc.NovelGeek.model.response.SellBookResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.PaymentCustomerRepository;
import lk.ucsc.NovelGeek.repository.PaymentDataRepository;
import lk.ucsc.NovelGeek.repository.SellingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellingService {
    @Autowired
    private SellingRepository sellingRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PaymentDataRepository paymentDataRepository;

    @Autowired
    private PaymentCustomerRepository paymentCustomerRepository;

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

            response.setSold(false);

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


            response.setSold(false);

            return response;

        }).collect(Collectors.toList());

        return posts;
    }

    public void storePayment(NewPaymentData request){
        if(request.getStatus_code()==2){
            PaymentData newpayment = new PaymentData();
            BeanUtils.copyProperties(request, newpayment);

            paymentDataRepository.save(newpayment);
        }


    }

    public NewPaymentCutomer storeCustomer(NewPaymentCutomer request){
        PaymentCustomer newcustomer = new PaymentCustomer();
        BeanUtils.copyProperties(request, newcustomer);

        PaymentCustomer res = paymentCustomerRepository.save(newcustomer);
        NewPaymentCutomer response = new NewPaymentCutomer();

        BeanUtils.copyProperties(res, response);

        return response;
    }
}
