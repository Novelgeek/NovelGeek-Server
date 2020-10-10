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
import java.util.stream.Stream;

//
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private CommentReplyRepository commentReplyRepository;

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

    @Autowired
    private PostNotificationRepository postNotificationRepository;

    //to get current user
    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    public List<PostResponse> getAllPosts(){
        //List <Posts> posts = postRepository.findBySharedtype("public");
        List <PostResponse> publicPosts = postRepository.findBySharedtype("public").stream().map(post->{
           PostResponse response = new PostResponse();
           BeanUtils.copyProperties(post, response);

           //set owner
            response.setUsername(post.getUsers().getUsername());
            //set user image
            response.setUserimg(post.getUsers().getImageUrl());
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

        List <PostResponse> friendsOnlyPosts = postRepository.findBySharedtype("friends only").stream().map(post->{
            Friends isfriend = friendRepository.findByUser1AndUser2(this.getCurrentUser(), post.getUsers());
            if(isfriend != null){
                PostResponse response = new PostResponse();
                BeanUtils.copyProperties(post, response);

                //set owner
                response.setUsername(post.getUsers().getUsername());
                //set user image
                response.setUserimg(post.getUsers().getImageUrl());
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
            }else{
                return null;
            }

        }).collect(Collectors.toList());

        List<PostResponse> posts = Stream.concat(publicPosts.stream(), friendsOnlyPosts.stream())
                .collect(Collectors.toList());

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
            //set user image
            response.setUserimg(this.getCurrentUser().getImageUrl());

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
        //set user image
        response.setUserimg(this.getCurrentUser().getImageUrl());

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

        PostNotification commentNotification = new PostNotification();
        commentNotification.setUser(postRepository.findById(id).getUsers());
        commentNotification.setCommentor(this.getCurrentUser());
        commentNotification.setPost(postRepository.findById(id));
        //commentNotification.setPostTitle(postRepository.findById(id).getTitle());
        commentNotification.setNotificationType("Comment");
        commentNotification.setDate(new Date());
        postNotificationRepository.save(commentNotification);

        CommentResponse response = new CommentResponse();
        response.setCommentid(returncomment.getCommentid());
        response.setComment(returncomment.getComment());
        response.setUsername(this.getCurrentUser().getUsername());
        response.setImagePath(this.getCurrentUser().getImageUrl());
        response.setOwned(true);
        return response;

    }

    public List<CommentResponse> getComments(long id){
        Posts post = postRepository.findById(id);

        List<CommentResponse> comments = postCommentRepository.findByPosts(post).stream().map(entry ->{
            CommentResponse response = new CommentResponse();
            response.setCommentid(entry.getCommentid());
            response.setComment(entry.getComment());
            response.setUsername(entry.getUsers().getUsername());
            response.setImagePath(entry.getUsers().getImageUrl());

            //check whether the request is send by owner or not
            if(entry.getUsers().getId()==this.getCurrentUser().getId()){
                response.setOwned(true);
            }else{
                response.setOwned(false);
            }

            return response;
        }).collect(Collectors.toList());

        return comments;
    }


    public long deleteComment(long id){
        long response = id;
        postCommentRepository.deleteById(id);
        return response;
    }

    public CommentReplyResponse addReply(String comment, long id ){
        CommentReply newReply = new CommentReply();
        newReply.setComment(comment);
        newReply.setPostscomments(postCommentRepository.findById(id));
        //newReply.setPostscomments(postCommentRepository.findById(id));
        newReply.setUsers(this.getCurrentUser());

        CommentReply returnreply = commentReplyRepository.save(newReply);

        PostNotification replyNotification = new PostNotification();
        replyNotification.setUser(postCommentRepository.findById(id).getUsers());
        replyNotification.setReplier(this.getCurrentUser());
        replyNotification.setPost(postCommentRepository.findById(id).getPosts());
        //replyNotification.setPostTitle(postCommentRepository.findById(id).getPosts().getTitle());
        replyNotification.setNotificationType("Reply");
        replyNotification.setDate(new Date());
        postNotificationRepository.save(replyNotification);

        CommentReplyResponse response = new CommentReplyResponse();
        response.setReplyid(returnreply.getReplyid());
        response.setComment(returnreply.getComment());
        response.setUsername(this.getCurrentUser().getUsername());
        response.setImagePath(this.getCurrentUser().getImageUrl());
        response.setOwned(true);
        return response;
    }

    public List<CommentReplyResponse> getReplies(long id){
        PostsComments comment = postCommentRepository.findById(id);
        //Optional<PostsComments> comment = postCommentRepository.findById(id);
        List<CommentReplyResponse> replies = commentReplyRepository.findByPostscomments(comment).stream().map(entry ->{
            CommentReplyResponse response = new CommentReplyResponse();
            response.setReplyid(entry.getReplyid());
            response.setComment(entry.getComment());
            response.setUsername(entry.getUsers().getUsername());
            response.setImagePath(entry.getUsers().getImageUrl());

            //check whether the request is send by owner or not
            if(entry.getUsers().getId()==this.getCurrentUser().getId()){
                response.setOwned(true);
            }else{
                response.setOwned(false);
            }

            return response;
        }).collect(Collectors.toList());

        return replies;
    }

    public long deleteReply(long id){
        long response = id;
        commentReplyRepository.deleteById(id);
        return response;
    }

    //ADMIN POST REPORT HANDLING
    public List<ReportsAdminResponse> getReports(){

        List<ReportsAdminResponse> reports = postReportRepository.findUniquePosts().stream().map(entry ->{
           ReportsAdminResponse response = new ReportsAdminResponse();

           long k = entry.longValue();
            Posts post = postRepository.findById(k);
            response.setId(post.getUsers().getId());
            response.setPostid(post.getPostid());
            response.setUserimg(post.getUsers().getImageUrl());
            response.setUsername(post.getUsers().getUsername());
            response.setReportcount(postReportRepository.countReports(k));
            return response;
        }).collect(Collectors.toList());

        return reports;
    }

    public PostAdminResponse getReportedPost(long postid){
        Posts post = postRepository.findById(postid);
        PostAdminResponse response = new PostAdminResponse();
        response.setPostid(post.getPostid());
        response.setUsername(post.getUsers().getUsername());
        response.setUserimg(post.getUsers().getImageUrl());
        response.setTitle(post.getTitle());
        response.setDescription(post.getDescription());
        response.setImagePath(post.getImagePath());
        response.setCommentcount(postCommentRepository.countComments(postid));
        response.setLikecount(postLikeRepository.countLikes(postid));

        return response;
    }


    public List<PostReportsData> getReportedData(long postid){
        Posts post = postRepository.findById(postid);
        List <PostReportsData> response = postReportRepository.findByPosts(post).stream().map(entry->{
            PostReportsData temp = new PostReportsData();
            temp.setId(entry.getUsers().getId());
            temp.setUserimg(entry.getUsers().getImageUrl());
            temp.setUsername(entry.getUsers().getUsername());
            temp.setReportid(entry.getReportid());
            temp.setReason(entry.getReason());
            return  temp;
        }).collect(Collectors.toList());

        return  response;
    }

    public long deleteReportedPost (long postid){
        Posts post = postRepository.findById(postid);

        PostNotification deletePostNotification = new PostNotification();
        deletePostNotification.setUser(post.getUsers());
        deletePostNotification.setPostTitle(post.getTitle());
        deletePostNotification.setNotificationType("Deleted");
        deletePostNotification.setDate(new Date());

        postNotificationRepository.save(deletePostNotification);

        postReportRepository.deleteByPosts(post);
        postRepository.deleteById(postid);
        return postid;
    }

    public long cancelReportedPost (long postid) {
        Posts post = postRepository.findById(postid);
        postReportRepository.deleteByPosts(post);
        //postReportRepository.deleteReports(postid);
        return postid;
    }

    public long deleteNotifications(long notificationid){
        postNotificationRepository.deleteById(notificationid);
        return notificationid;
    }

    public List<?> getReportNotifications(){
        List<String> response = postNotificationRepository.findByUserAndNotificationType(this.getCurrentUser(), "Deleted").stream().map(noti->{
            return noti.getPostTitle();
        }).collect(Collectors.toList());

        return response;
    }

    public List<CommentNotificationResponse> getCommentNotifications(){
        List<CommentNotificationResponse> response = postNotificationRepository.findByUserAndNotificationType(this.getCurrentUser(), "Comment").stream().map(noti->{
            CommentNotificationResponse temp = new CommentNotificationResponse();
            temp.setCommentor(noti.getCommentor().getUsername());
            temp.setPostTitle(noti.getPost().getTitle());
            temp.setPostid(noti.getPost().getPostid());
            return temp;
        }).collect(Collectors.toList());
        return response;
    }

    public List<ReplyNotificationResponse> getReplyNotifications(){
        List<ReplyNotificationResponse> response = postNotificationRepository.findByUserAndNotificationType(this.getCurrentUser(), "Reply").stream().map(noti->{
            ReplyNotificationResponse temp = new ReplyNotificationResponse();
            temp.setReplier(noti.getReplier().getUsername());
            temp.setPostid(noti.getPost().getPostid());
            temp.setPostTitle(noti.getPost().getTitle());

            return temp;
        }).collect(Collectors.toList());
        return response;
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
}
