package br.gov.ce.sop.convenios.model.dto.email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Link {
    private String href;
    private String content;

    public Link(String href, String content) {
        this.href = href;
        this.content = content.toUpperCase();
    }
}
