package com.itpetshelter.itpetshelter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;


    private String type;

    private String keyword;


    private String link;


    private String link2;


    public String[] getTypes() {
        if (type == null || type.isEmpty()) {
            return null;
        }

        return type.split("");
    }


    public Pageable getPageable(String... props) {

        return PageRequest.of(this.page - 1, 10, Sort.by(props).descending());
    }


    public String getLink() {
        if (link == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("page=" + this.page);
            stringBuilder.append("&size=" + this.size);


            if (type != null && type.length() > 0) {
                stringBuilder.append("&type=" + type);
            }


            if (keyword != null) {
                try {
                    stringBuilder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            link = stringBuilder.toString();

        }
        return link;
    }

    public String getLink2() {
        if (link2 == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("page=" + this.page);
            stringBuilder.append("&size=" + this.size);

            link2 = stringBuilder.toString();

        }
        return link2;
    }

}


