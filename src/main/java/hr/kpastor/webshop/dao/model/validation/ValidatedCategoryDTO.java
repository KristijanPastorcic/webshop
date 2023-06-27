package hr.kpastor.webshop.dao.model.validation;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class ValidatedCategoryDTO {

    @NotNull
    private String name;
    @NotNull
    private String desc;

}
