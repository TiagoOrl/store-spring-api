package com.asm.estore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailModel implements Serializable{
    private String from;
    private String to;
    private String subject;
    private String text;
    private LocalDateTime sent;

    public EmailModel(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
