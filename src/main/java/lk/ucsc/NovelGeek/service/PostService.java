package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.PostsLikes;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.response.DeletePostResponse;
import lk.ucsc.NovelGeek.model.response.LikePostResponse;
import lk.ucsc.NovelGeek.model.response.PostResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.PostLikeRepository;
import lk.ucsc.NovelGeek.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
//
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private AuthRepository authRepository;

    //to get current user
    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    public List<PostResponse> getAllPosts(){
        //List <Posts> posts = postRepository.findBySharedtype("public");
        List <PostResponse> posts = postRepository.findBySharedtype("public").stream().map(post->{
           PostResponse response = new PostResponse();
           BeanUtils.copyProperties(post, response);

           //check whether the request is send by owner or not
           if(post.getUsers().getId()==this.getCurrentUser().getId()){
               response.setOwned(true);
           }else{
               response.setOwned(false);
           }
           //check whether already liked or not
            if(postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==1){
                response.setLiked(true);
            }else if (postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==0){
                response.setLiked(false);
            }

           //setlikes counts of post
           long count = postLikeRepository.countLikes(post.getPostid());
           response.setLikecount(count);
           return response;
        }).collect(Collectors.toList());
        return posts;
    }


    public List<PostResponse> getMyPosts(){
        Users currentUser = this.getCurrentUser();
        List<PostResponse> myPosts = currentUser.getPosts().stream().map(post ->{
            PostResponse response = new PostResponse();
            BeanUtils.copyProperties(post, response);

            //set owner
            response.setOwned(true);

            //check whether already liked or not
            if(postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==1){
                response.setLiked(true);
            }else if (postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==0){
                response.setLiked(false);
            }

            //setlikes count
            long count = postLikeRepository.countLikes(post.getPostid());
            response.setLikecount(count);

            return response;
        }).collect(Collectors.toList());

        return myPosts;
    }

    public PostResponse createPost(NewPost newpostrequest){

        Posts newpost= new Posts();
        BeanUtils.copyProperties(newpostrequest,newpost);

        newpost.setPublishedDate(new Date());
        newpost.setImagePath("path");

        Users currentUser = this.getCurrentUser();
        newpost.setUsers(currentUser);

        Posts returnpost = postRepository.save(newpost);

        PostResponse response = new PostResponse();
        BeanUtils.copyProperties(returnpost, response);

        //set owner
        response.setOwned(true);
        //set is liked
        response.setLiked(false);
        //set like count
        response.setLikecount(0);

        return response;
    }

    public DeletePostResponse deletePost(long id){
        DeletePostResponse response = new DeletePostResponse();
        response.setPostid(id);
        postRepository.deleteById(id);

        return response;
    }

    public LikePostResponse likePost(long id){
        LikePostResponse response = new LikePostResponse();

        //check existency
        if(!postRepository.existsById(id)){
            response.setExist(false);
            response.setLiked(false);

        }else{

            Posts post = postRepository.findById(id);
            Users currentUser = this.getCurrentUser();

            PostsLikes newlike = new PostsLikes();
            newlike.setPosts(post);
            newlike.setUsers(currentUser);
            postLikeRepository.save(newlike);

            response.setLiked(true);
            response.setExist(true);
            postLikeRepository.save(newlike);
        }

        return response;
    }

    public LikePostResponse unLikePost(long id){
        LikePostResponse response = new LikePostResponse();

        if(!postRepository.existsById(id)){
            response.setExist(false);
            response.setLiked(false);

        }else{

            //find post
            Posts post = postRepository.findById(id);
            //find entry in posts_likes
             long idx = postLikeRepository.getEntry(post.getPostid(), this.getCurrentUser().getId());
            //delete it
            postLikeRepository.deleteById(idx);
            response.setLiked(false);
            response.setExist(true);

        }

        return response;
    }



}
