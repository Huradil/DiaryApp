package com.example.diaryapp.data;

import android.os.Parcelable;
import android.os.Parcel;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String text;
    @ColumnInfo
    public long timestamp;

    public Note() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(text, note.text) && Objects.equals(title, note.title) && timestamp == note.timestamp;
    }

    @Override
    public  int hashCode() { return Objects.hash(id, title, text, timestamp);}

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        text = in.readString();
        timestamp = in.readLong();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeLong(timestamp);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

}
