package com.mxdl.faq;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description: <Book><br>
 * Author:      mxdl<br>
 * Date:        2019/9/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class Book implements Parcelable {
    private int id;
    private String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}