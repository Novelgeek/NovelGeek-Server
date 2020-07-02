package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.PostsComments;
import lk.ucsc.NovelGeek.model.PostsLikes;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.response.*;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.PostCommentRepository;
import lk.ucsc.NovelGeek.repository.PostLikeRepository;
import lk.ucsc.NovelGeek.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

//
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

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

    public List<PostResponse> getAllPosts(){
        //List <Posts> posts = postRepository.findBySharedtype("public");
        List <PostResponse> posts = postRepository.findBySharedtype("public").stream().map(post->{
           PostResponse response = new PostResponse();
           BeanUtils.copyProperties(post, response);

           //set owner
            response.setUsername(post.getUsers().getUsername());

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

           //setcomment count of post
            count = postCommentRepository.countComments(post.getPostid());
            response.setCommentcount(count);
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
            response.setUsername(this.getCurrentUser().getUsername());

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

            //setcomment count of post
            count = postCommentRepository.countComments(post.getPostid());
            response.setCommentcount(count);

            return response;
        }).collect(Collectors.toList());

        return myPosts;
    }

    public PostResponse createPost(NewPost newpostrequest){

        Posts newpost= new Posts();
        BeanUtils.copyProperties(newpostrequest,newpost);

        newpost.setPublishedDate(new Date());

        Users currentUser = this.getCurrentUser();
        newpost.setUsers(currentUser);

        Posts returnpost = postRepository.save(newpost);

        PostResponse response = new PostResponse();
        BeanUtils.copyProperties(returnpost, response);

        //set ownername
        response.setUsername(this.getCurrentUser().getUsername());
        //set owner
        response.setOwned(true);
        //set is liked
        response.setLiked(false);
        //set like count
        response.setLikecount(0);
        //set comment count
        response.setCommentcount(0);

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

    public List<LikedUsersResponse> getLikedUsers(long id){
        Posts post = postRepository.findById(id);
        List <LikedUsersResponse> likedUsers = postLikeRepository.findByPosts(post).stream().map(entry ->{
            LikedUsersResponse response = new LikedUsersResponse();
            response.setLikedUser(entry.getUsers().getUsername());
            response.setImagePath(entry.getUsers().getImageUrl());
            return response;
        }).collect(Collectors.toList());
        return likedUsers;
    }

    public CommentResponse addComment(String comment, long id){

        PostsComments newComment = new PostsComments();
        newComment.setComment(comment);
        newComment.setPosts(postRepository.findById(id));
        newComment.setUsers(this.getCurrentUser());

        PostsComments returncomment = postCommentRepository.save(newComment);

        CommentResponse response = new CommentResponse();
        response.setComment(returncomment.getComment());
        response.setUsername(this.getCurrentUser().getUsername());
        response.setImagePath(this.getCurrentUser().getImageUrl());

        return response;

    }

    public List<CommentResponse> getComments(long id){
        Posts post = postRepository.findById(id);

        List<CommentResponse> comments = postCommentRepository.findByPosts(post).stream().map(entry ->{
            CommentResponse response = new CommentResponse();
            response.setComment(entry.getComment());
            response.setUsername(entry.getUsers().getUsername());
            response.setImagePath(entry.getUsers().getImageUrl());

            return response;
        }).collect(Collectors.toList());

        return comments;
    }
}