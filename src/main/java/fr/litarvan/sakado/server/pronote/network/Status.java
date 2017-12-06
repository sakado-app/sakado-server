package fr.litarvan.sakado.server.pronote.network;

import com.google.gson.annotations.SerializedName;

public enum Status
{
    @SerializedName("success")
    SUCCESS,

    @SerializedName("failed")
    FAILED,

    @SerializedName("error")
    ERROR,

    @SerializedName("internalError")
    INTERNAL_ERROR,

    // Theorically can't be handled
    @SerializedName("malformed")
    MALFORMED
}
