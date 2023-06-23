package com.kasbus.kasbusapp.Containers;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Subject implements Parcelable {
    @SerializedName("subjectId")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("faculty")
    private String faculty;
    @SerializedName("credits")
    private Integer credits;
    @SerializedName("delivery")
    private String delivery;
    @SerializedName("lecturers")
    private String lecturers;
    @SerializedName("language")
    private String language;
    @SerializedName("exam")
    private Boolean exam;
    @SerializedName("hours")
    private Integer hours;
    @SerializedName("link")
    private String link;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getFaculty() {
        return faculty;
    }
    public Integer getCredits() {
        return credits;
    }
    public String getDelivery() {
        return delivery;
    }
    public String getLecturers() {
        return lecturers;
    }
    public String getLanguage() {
        return language;
    }
    public Boolean getExam() {
        return exam;
    }
    public Integer getHours() {
        return hours;
    }
    public String getLink() {
        return link;
    }

    // implementing parcelable interface
    // This is needed to be able to use intent to store object
    protected Subject(Parcel in) {
        id = in.readString();
        name = in.readString();
        faculty = in.readString();
        credits = in.readInt();
        delivery = in.readString();
        lecturers = in.readString();
        language = in.readString();
        exam = in.readByte() != 0;
        hours = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(faculty);
        dest.writeInt(credits);
        dest.writeString(delivery);
        dest.writeString(lecturers);
        dest.writeString(language);
        dest.writeByte((byte) (exam ? 1 : 0));
        dest.writeInt(hours);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };
}
