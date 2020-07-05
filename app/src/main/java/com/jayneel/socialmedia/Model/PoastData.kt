package com.jayneel.socialmedia.Model

data class PoastData(var uid:String?=null,var img:String?=null,var poastid:String?=null,var disc:String?=null,var username:String?=null)
{
    constructor():this("","","","","")
}