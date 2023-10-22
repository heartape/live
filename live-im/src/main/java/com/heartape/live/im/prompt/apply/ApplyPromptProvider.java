package com.heartape.live.im.prompt.apply;

import com.heartape.live.im.context.PromptContext;
import com.heartape.live.im.prompt.Prompt;
import com.heartape.live.im.prompt.PromptProvider;
import com.heartape.live.im.send.PromptSend;
import com.heartape.live.im.send.Send;

/**
 * @since 0.0.1
 * @author heartape
 * @see PromptProvider
 * @see ApplyPrompt
 */
public class ApplyPromptProvider implements PromptProvider {

    @Override
    public Send process(PromptContext promptContext) {
        Prompt prompt = promptContext.getPrompt();
        return new PromptSend(promptContext.getPurpose(), promptContext.getPurposeType(), promptContext.getPromptType(), prompt);
    }
}
