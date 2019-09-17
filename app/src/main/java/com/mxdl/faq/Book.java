package com.mxdl.faq;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;

/**
 * Description: <Book><br>
 * Author:      mxdl<br>
 * Date:        2019/9/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class Book implements Parcelable {

    RemoteCallbackList mCallbackList;
    private int id;
    private String name;
    static int value = 1;
    @Override
    public int describeContents() {
        return 0;
    }
    static void setView(int v){
        value = v;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);

        //mCallbackList.unregister()
    }

    public Book() {
        //Handler handler;
        //handler.obtainMessage(1,"aaaa").sendToTarget();
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