package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.SellBook;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.request.NewSelling;
import lk.ucsc.NovelGeek.model.response.PostResponse;
import lk.ucsc.NovelGeek.model.response.SellBookResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.SellingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SellingService {
    @Autowired
    private SellingRepository sellingRepository;

    @Autowired
    private AuthRepository authRepository;

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
}
