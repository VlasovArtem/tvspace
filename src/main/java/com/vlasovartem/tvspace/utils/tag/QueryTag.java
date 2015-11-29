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

    public void setQuery(String query) {
        this.query = query;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if (Objects.nonNull(query) && Objects.nonNull(genre) && query.matches(".*genre=\\w+.*")) {
            JspWriter out = getJspContext().getOut();
            out.print("/series/search?" + query.replaceFirst("genre=\\w+", "genre=" + genre));
        }
    }
}
