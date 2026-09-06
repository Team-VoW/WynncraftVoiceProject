/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.feedback;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class QuestFeedbackScreen extends Screen {
    private final FeedbackSubmission submission;
    private Button submit;
    private String error = "";

    public QuestFeedbackScreen(FeedbackSubmission submission) {
        super(Component.literal("Quest voice-acting feedback"));
        this.submission = submission;
    }

    public FeedbackSubmission submission() {
        return submission;
    }

    @Override
    protected void init() {
        int boxWidth = Math.min(420, width - 30);
        MultiLineEditBox input = addRenderableWidget(MultiLineEditBox.builder()
                .setX((width - boxWidth) / 2)
                .setY(65)
                .setPlaceholder(Component.literal("What could we improve?"))
                .build(
                        font,
                        boxWidth,
                        Math.max(30, height - 140),
                        Component.literal("Feedback (maximum 2,000 characters)")));
        input.setCharacterLimit(2000);
        input.setValue(submission.draft);
        input.setValueListener(value -> submission.draft = value);
        submit = addRenderableWidget(Button.builder(Component.literal(error.isEmpty() ? "Submit" : "Retry"), button -> {
                    QuestFeedback.comment(submission, message -> {
                        if (message == null) {
                            if (minecraft.screen == this) onClose();
                        } else {
                            error = message;
                            submit.setMessage(Component.literal("Retry"));
                        }
                    });
                })
                .bounds(width / 2 - 105, height - 30, 100, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("Cancel"), button -> onClose())
                .bounds(width / 2 + 5, height - 30, 100, 20)
                .build());
        setInitialFocus(input);
    }

    @Override
    public void tick() {
        submit.active = !submission.busy && !submission.draft.isBlank() && QuestFeedback.active(submission);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFFFF);
        graphics.drawCenteredString(
                font,
                Component.literal(submission.quest + " · " + submission.score + " / 5"),
                width / 2,
                30,
                0xFFFFFFFF);
        graphics.drawCenteredString(
                font, Component.literal(submission.draft.length() + " / 2,000 characters"), width / 2, 48, 0xFFAAAAAA);
        if (!error.isEmpty())
            graphics.drawWordWrap(font, Component.literal(error), 15, height - 68, width - 30, 0xFFFF9999);
    }

    @Override
    public void onClose() {
        minecraft.setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
