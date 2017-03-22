package com.app.skreppis.skreppis.models;

/**
 * Created by Nökkvi on 21.3.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RegisterResponse {

        @SerializedName("username")
        @Expose
        public String username;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("email2")
        @Expose
        public String email2;

        @SerializedName("first_name")
        @Expose
        public String first_name;

        @SerializedName("last_name")
        @Expose
        public String last_name;

        public String getUsername(){
                return username;
        }

        public String getEmail(){
                return email;
        }

        public String getEmail2(){
                return email2;
        }

        public String getFirst_name(){
                return first_name;
        }

        public String getLast_name(){
                return last_name;
        }
}
