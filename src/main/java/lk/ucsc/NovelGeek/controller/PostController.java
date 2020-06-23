package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired //inject the service instance that created already
    private PostService postService;

    @PostMapping("/newpost")
    public ResponseEntity<?> createPost(@RequestBody NewPost newpost){
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

    //@GetMapping()

}
