package com.jayneel.socialmedia.Model

data class PoastData(var username:String?=null,var post:String?=null,var userprofile:String?=null)
{
    constructor():this("","","")
}