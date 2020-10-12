package lk.ucsc.NovelGeek.controller;

import com.amazonaws.Response;
import com.amazonaws.auth.AWS3Signer;
import lk.ucsc.NovelGeek.model.request.NewComment;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.model.request.NewReply;
import lk.ucsc.NovelGeek.model.response.PostResponse;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired //inject the service instance that created already
    private PostService postService;

    @Autowired
    private AWSS3Service awsService;


    @PostMapping("/newpost")
    public ResponseEntity<?> createPost(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("sharedtype") String sharedtype,
                                        @RequestParam(value = "file", required = false) MultipartFile file)
    {
        String filePath;
        if (file == null) {
            filePath = null;
        } else {
            filePath = awsService.uploadFile(file);
        }

        NewPost newpost = new NewPost();
        newpost.setTitle(title);
        newpost.setDescription(description);
        newpost.setSharedType(sharedtype);
        newpost.setImagePath(filePath);

        return ResponseEntity.ok(postService.createPost(newpost));
    }


    @GetMapping("/allposts")
    public ResponseEntity<?> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/myposts")
    public ResponseEntity<?> getMyPosts(){
        return ResponseEntity.ok(postService.getMyPosts());
    }

    @GetMapping("/likepost/{postid}")
    public ResponseEntity<?> likePost(@PathVariable(value = "postid") long postid){
        return ResponseEntity.ok(postService.likePost(postid));
    }

    @DeleteMapping("/unlikepost/{postid}")
    public ResponseEntity<?> unLikePost(@PathVariable(value = "postid") long postid){
        return ResponseEntity.ok(postService.unLikePost(postid));
    }

    @DeleteMapping("/delete/{postid}")
    public ResponseEntity<?> deletePost(@PathVariable(value="postid") long postid ){
        return ResponseEntity.ok(postService.deletePost(postid));
    }

    @PostMapping("report/{postid}")
    public ResponseEntity<?> reportPost(@RequestBody String reason , @PathVariable(value="postid") long postid){
        return  ResponseEntity.ok(postService.reportPost(postid, reason));
    }

    @DeleteMapping("unreport/{postid}")
    public ResponseEntity<?> unReportPost(@PathVariable(value="postid") long postid){
        return ResponseEntity.ok(postService.unReportPost(postid));
    }

    @GetMapping("/likes/{postid}")
    public ResponseEntity<?> getLikes(@PathVariable(value="postid") long postid){
        return ResponseEntity.ok(postService.getLikedUsers(postid));
    }

    @PostMapping("/addcomment/{postid}")
    public ResponseEntity<?> addComment(@RequestBody NewComment comment, @PathVariable(value="postid")long postid){
        return ResponseEntity.ok(postService.addComment(comment.getComment(), postid));
    }

    @GetMapping("/getcomments/{postid}")
    public ResponseEntity<?> getComments(@PathVariable(value="postid")long postid){
        return ResponseEntity.ok(postService.getComments(postid));
    }


    @GetMapping("/userpost/{email}")
    public ResponseEntity<?> getUserPost(@PathVariable(value ="email")String email){
        return  ResponseEntity.ok(postService.getUserPost(email));
    }


    @DeleteMapping("/deletecomment/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable(value="commentid") long commentid ){
        return ResponseEntity.ok(postService.deleteComment(commentid));
    }

    @PostMapping("/addreply/{commentid}")
    public ResponseEntity<?> addReply(@RequestBody NewReply comment, @PathVariable(value="commentid")long commentid){
        return ResponseEntity.ok(postService.addReply(comment.getComment(), commentid));
    }

    @GetMapping("/getreplies/{commentid}")
    public ResponseEntity<?> getReplies(@PathVariable(value="commentid")long commentid){
        return ResponseEntity.ok(postService.getReplies(commentid));
    }

    @DeleteMapping("/deletereply/{replyid}")
    public ResponseEntity<?> deleteReply(@PathVariable(value="replyid") long replyid ){
        return ResponseEntity.ok(postService.deleteReply(replyid));
    }

    @GetMapping("/reportnotifications")
    public  ResponseEntity<?> getReportNotifications(){
        return ResponseEntity.ok(postService.getReportNotifications());
    }

    @GetMapping("/commentnotifications")
    public  ResponseEntity<?> getCommentNotifications(){
        return ResponseEntity.ok(postService.getCommentNotifications());
    }

    @GetMapping("/replynotifications")
    public  ResponseEntity<?> getReplyNotifications(){
        return ResponseEntity.ok(postService.getReplyNotifications());
    }

    @DeleteMapping("/deletenotifications/{notiid}")
    public ResponseEntity<?> deleteNotifications(@PathVariable(value="notiid") long notiid){
        return ResponseEntity.ok(postService.deleteNotifications(notiid));
    }
}
