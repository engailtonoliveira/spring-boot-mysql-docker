package br.com.agoliveira.springbootcommysql.controller;

import br.com.agoliveira.springbootcommysql.model.Post;
import br.com.agoliveira.springbootcommysql.payload.ApiResponse;
import br.com.agoliveira.springbootcommysql.payload.PagedResponse;
import br.com.agoliveira.springbootcommysql.payload.PostRequest;
import br.com.agoliveira.springbootcommysql.payload.PostResponse;
import br.com.agoliveira.springbootcommysql.payload.VoteRequest;
import br.com.agoliveira.springbootcommysql.security.CurrentUser;
import br.com.agoliveira.springbootcommysql.security.UserPrincipal;
import br.com.agoliveira.springbootcommysql.service.PostService;
import br.com.agoliveira.springbootcommysql.util.AppConstants;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(AppConstants.MAPPING_POSTS)
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public PagedResponse<PostResponse> getPosts(@CurrentUser final UserPrincipal currentUser,
                                                @RequestParam(value = AppConstants.PARAM_NAME_PAGE, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) final int page,
                                                @RequestParam(value = AppConstants.PARAM_NAME_SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) final int size) {

        return postService.getAllPosts(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize(AppConstants.AUTH_ROLE_USER)
    public ResponseEntity<?> createPost(@Valid @RequestBody final PostRequest postRequest) {

        final Post post = postService.createPost(postRequest);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(AppConstants.MAPPING_GET_POST).buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Post Created Successfully"));
    }

    @GetMapping(AppConstants.MAPPING_GET_POST)
    public PostResponse getPostById(@CurrentUser final UserPrincipal currentUser, @PathVariable final Long postId) {

        return postService.getPostById(postId, currentUser);
    }

    @PostMapping(AppConstants.MAPPING_VOTE)
    @PreAuthorize(AppConstants.AUTH_ROLE_USER)
    public PostResponse castVote(@CurrentUser final UserPrincipal currentUser, @PathVariable final Long postId, @Valid @RequestBody final VoteRequest voteRequest) {

        return postService.castVoteAndGetUpdatedPost(postId, voteRequest, currentUser);
    }

}
