package org.kagura.advisor;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class UsageInfoAdvisor implements BaseAdvisor {

    @NonNull
    @Override
    public ChatClientRequest before(@NonNull ChatClientRequest chatClientRequest,
                                    @NonNull AdvisorChain advisorChain) {
        return chatClientRequest;
    }

    @NonNull
    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse,
                                    @NonNull AdvisorChain advisorChain) {
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        if (Objects.nonNull(chatResponse)) {
            Usage usage = chatResponse.getMetadata().getUsage();
            log.debug("usage info: promptTokens={}, completionTokens={}, totalTokens={}",
                    usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
        }
        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
