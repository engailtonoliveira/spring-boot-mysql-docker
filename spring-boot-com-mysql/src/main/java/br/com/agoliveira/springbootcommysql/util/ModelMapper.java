package br.com.agoliveira.springbootcommysql.util;

import br.com.agoliveira.springbootcommysql.model.Post;
import br.com.agoliveira.springbootcommysql.model.User;
import br.com.agoliveira.springbootcommysql.payload.ChoiceResponse;
import br.com.agoliveira.springbootcommysql.payload.PostResponse;

import java.util.Map;

public class ModelMapper {

    public static PostResponse mapPostToPostResponse(final Post post, final Map<Long, Long> choiceVotesMap, final User creator, final Long userVote) {

        final PostResponse postResponse = new PostResponse(post, choiceVotesMap, creator);

        if (userVote != null) {
            postResponse.setSelectedChoice(userVote);
        }

        postResponse.setTotalVotes(postResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum());

        return postResponse;
    }
}
