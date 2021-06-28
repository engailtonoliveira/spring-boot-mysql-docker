package br.com.agoliveira.springbootcommysql.controller;

import br.com.agoliveira.springbootcommysql.exeption.ResourceNotFoundException;
import br.com.agoliveira.springbootcommysql.model.User;
import br.com.agoliveira.springbootcommysql.payload.*;
import br.com.agoliveira.springbootcommysql.repository.PostRepository;
import br.com.agoliveira.springbootcommysql.repository.UserRepository;
import br.com.agoliveira.springbootcommysql.repository.VoteRepository;
import br.com.agoliveira.springbootcommysql.security.CurrentUser;
import br.com.agoliveira.springbootcommysql.security.UserPrincipal;
import br.com.agoliveira.springbootcommysql.service.PostService;
import br.com.agoliveira.springbootcommysql.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser final UserPrincipal currentUser) {

        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") final String username) {
        return new UserIdentityAvailability(!userRepository.existsByUsername(username));
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") final String email) {

        return new UserIdentityAvailability(!userRepository.existsByEmail(email));
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        final long postCount = postRepository.countByCreatedBy(user.getId());
        final long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user, postCount, voteCount);

        System.out.printf("Profile id=[%d] found (username=[%s]).", userProfile.getId(), username);
        //log.info("Profile id=[%d] found (username=[%s]).", userProfile.getId(), username);
        return userProfile;
    }

    @GetMapping("/users/{username}/posts")
    public PagedResponse<PostResponse> getPostsCreatedBy(
            @PathVariable(value = "username") final String username,
            @CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) final int size) {

        return postService.getPostsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/votes")
    public PagedResponse<PostResponse> getPostsVotedBy(
            @PathVariable(value = "username") final String username,
            @CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) final int size) {

        return postService.getPostsVotedBy(username, currentUser, page, size);
    }
}
