package com.legion.picturebackend.model.vo;

import lombok.Data;

import java.util.List;


@Data
public class PictureTagCategory {
    private List<String> tagList;

    public List<String> categoryList;
}
