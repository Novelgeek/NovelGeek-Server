package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.*;
import lk.ucsc.NovelGeek.model.group.Group;
import lk.ucsc.NovelGeek.model.group.GroupPosts;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.response.*;
import lk.ucsc.NovelGeek.repository.*;
import lk.ucsc.NovelGeek.repository.group.GroupPostRepository;
import lk.ucsc.NovelGeek.repository.group.GroupRepository;
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
    private PostReportRepository postReportRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AWSS3Service awsService;

    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private GroupRepository groupRepository;

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

            //check whether the current user reported the post or not
            if(postReportRepository.checkIsReported(post.getPostid(), this.getCurrentUser().getId())==1){
                response.setReported(true);
            }else if(postReportRepository.checkIsReported(post.getPostid(), this.getCurrentUser().getId())==0){
                response.setReported(false);
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
        //set is reported
        response.setReported(false);
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

    public ReportPostResponse reportPost(long id, String reason){
        ReportPostResponse response = new ReportPostResponse();

        //check existency
        if(!postRepository.existsById(id)){
            response.setExist(false);
            response.setReported(false);

        }else{
            Posts post = postRepository.findById(id);
            Users currentUser = this.getCurrentUser();

            PostsReports newreport = new PostsReports();
            newreport.setPosts(post);
            newreport.setUsers(currentUser);
            newreport.setReason(reason);

            postReportRepository.save(newreport);

            response.setReported(true);
            response.setExist(true);

        }

        return response;
    }

    public ReportPostResponse unReportPost(long postid){

        ReportPostResponse response = new ReportPostResponse();

        //check existency
        if(!postRepository.existsById((postid))){
            response.setExist(false);
            response.setReported(false);

        }else{

            Posts post = postRepository.findById(postid);
            long idx = postReportRepository.getEntry(post.getPostid(), this.getCurrentUser().getId());

            postReportRepository.deleteById(idx);

            response.setReported(false);
            response.setExist(true);
        }

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

    public Object createGroupPost(NewPost newpostrequest, Long groupId) {
        Posts newpost= new Posts();
        BeanUtils.copyProperties(newpostrequest,newpost);

        newpost.setPublishedDate(new Date());

        Users currentUser = this.getCurrentUser();
        newpost.setUsers(currentUser);

        Posts createdPost = postRepository.save(newpost);

        GroupPosts groupPosts = new GroupPosts();
        Optional<Group> group = groupRepository.findById(groupId);
        groupPosts.setGroup(group.get());
        groupPosts.setPosts(createdPost);

        groupPostRepository.save(groupPosts);
        return createdPost;
    }


    public List<PostResponse> getUserPost(String email) {

        Users user = authRepository.findByEmail(email);
        List<PostResponse> userPosts = user.getPosts().stream().map(post ->{
            PostResponse response = new PostResponse();
            BeanUtils.copyProperties(post, response);

            //set owner
            response.setUsername(user.getUsername());

            //set owner
            response.setOwned(false);

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

        return userPosts;
    }
}
