package com.jmu.bibasedmanage.vo;

import java.util.List;

/**
 * Created by ljc on 2018/1/8.
 */
public class Menu {
    private String title;
    private String icon = "fa-cubes";
    private Boolean isSpread = false;
    private List<ChildMenu> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getSpread() {
        return isSpread;
    }

    public void setSpread(Boolean spread) {
        isSpread = spread;
    }

    public List<ChildMenu> getChildren() {
        return children;
    }

    public void setChildren(List<ChildMenu> children) {
        this.children = children;
    }
}
