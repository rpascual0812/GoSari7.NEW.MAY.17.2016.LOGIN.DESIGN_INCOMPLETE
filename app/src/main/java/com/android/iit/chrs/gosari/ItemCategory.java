package com.android.iit.chrs.gosari;



/**
 * Created by greg on 4/23/16.
 */
public class ItemCategory {

    private String pk;
    private String category;
    private String description;
    private String archived;
    private String image;


    public ItemCategory(){

    }

    public ItemCategory(String pk, String category,String archived,String image){
        super();
        this.pk=pk;
        this.category=category;
        this.archived=archived;
        this.image=image;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
