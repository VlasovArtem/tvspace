package com.vlasovartem.tvspace.utils.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by artemvlasov on 26/11/15.
 */
public class QueryTag extends SimpleTagSupport {
    private String query;
    private String genre;
    private String initQuery;

    public void setQuery(String query) {
        this.query = query;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setInitQuery(String initQuery) {
        this.initQuery = initQuery;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.print(queryBuilde(query.split("&")));
    }

    /**
     * Create query from the list of url parameters
     * @param queryParams converted query parameters
     * @return Builded query
     */
    private String queryBuilde (String... queryParams) {
        StringBuilder builder = new StringBuilder();
        builder.append(initQuery);
        boolean genreUpdate = false;
        for (int i = 0; i < queryParams.length; i++) {
            if(queryParams[i].matches("genre=\\w+") || "".equals(query)) {
                builder.append("genre=").append(genre);
                genreUpdate = true;
            } else {
                builder.append(queryParams[i]);
            }
            if(i != queryParams.length - 1) {
                builder.append("&");
            }
        }
        if(!genreUpdate) {
            builder.append("&").append("genre=").append(genre);
        }
        return builder.toString();
    }
}
