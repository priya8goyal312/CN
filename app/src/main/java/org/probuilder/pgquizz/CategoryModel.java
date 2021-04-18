package org.probuilder.pgquizz;

public class CategoryModel {

    private String name;
    private String url;
    private int sets;

    public CategoryModel(){
        //ye firebase li liye hota hai agar data aa raha hai to handle karne ke liye
    }

    public CategoryModel(String name, String url, int sets) {
        this.name = name;
        this.url = url;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
