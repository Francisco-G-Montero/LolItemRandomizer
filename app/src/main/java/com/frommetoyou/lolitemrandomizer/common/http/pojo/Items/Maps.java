
package com.frommetoyou.lolitemrandomizer.common.http.pojo.Items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maps {

    @SerializedName("1")
    @Expose
    private Boolean _1;
    @SerializedName("8")
    @Expose
    private Boolean _8;
    @SerializedName("10")
    @Expose
    private Boolean _10;
    @SerializedName("12")
    @Expose
    private Boolean _12;

    public Boolean get1() {
        return _1;
    }

    public void set1(Boolean _1) {
        this._1 = _1;
    }

    public Boolean get8() {
        return _8;
    }

    public void set8(Boolean _8) {
        this._8 = _8;
    }

    public Boolean get10() {
        return _10;
    }

    public void set10(Boolean _10) {
        this._10 = _10;
    }

    public Boolean get12() {
        return _12;
    }

    public void set12(Boolean _12) {
        this._12 = _12;
    }

}
