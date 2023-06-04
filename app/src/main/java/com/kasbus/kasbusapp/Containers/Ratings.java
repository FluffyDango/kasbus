package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Ratings {
    @SerializedName("category1")
    public Double category1;
    @SerializedName("category2")
    public Double category2;
    @SerializedName("category3")
    public Double category3;
    @SerializedName("category4")
    public Double category4;

    public Double getCategory1(){
        return category1;
    }

    public void setCategory1(Double newCategory1){
        this.category1 = newCategory1;
    }
    
    public Double getCategory2(){
        return category2;
    }

    public void setCategory2(Double newCategory2){
        this.category2 = newCategory2;
    }

    public Double getCategory3(){
        return category3;
    }

    public void setCategory3(Double newCategory3){
        this.category3 = newCategory3;
    }

    public Double getCategory4(){
        return category4;
    }

    public void setCategory4(Double newCategory4){
        this.category4 = newCategory4;
    }
}
