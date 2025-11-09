package com.scm.helper;


import lombok.*;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    private String content;
    @Builder.Default
    private MessageType type=MessageType.blue;
    }
